package kraheja.fd.deposit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ActranhCK;
import kraheja.commons.entity.Company;
import kraheja.commons.enums.ConnectionStatusEnum;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.service.ReportMasterService;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.GenericExnarrLogic;
import kraheja.commons.utils.ValueContainer;
import kraheja.fd.deposit.bean.ActiveDepositBean;
import kraheja.fd.deposit.bean.DepositDepositorBean;
import kraheja.fd.deposit.bean.TDepintBean;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.entity.Tdepint;
import kraheja.fd.deposit.entity.Tdepint.TdepintBuilder;
import kraheja.fd.deposit.entity.TdepintCK;
import kraheja.fd.deposit.mappers.FdEntityEntityMapper;
import kraheja.fd.deposit.repository.DepintRepository;
import kraheja.fd.deposit.repository.DepositRepository;
import kraheja.fd.deposit.repository.DepositorRepository;
import kraheja.fd.deposit.repository.TDepintRepository;
import kraheja.fd.deposit.service.InterestCalculationService;
import kraheja.fd.deposit.utils.InterestCalculationLogic;
import kraheja.feign.internal.ReportInternalFeignClient;

@Service
@Transactional
public class InterestCalculationServiceImpl implements InterestCalculationService {

	private static final Logger logger = LoggerFactory.getLogger(InterestCalculationServiceImpl.class);

	@Autowired
	private DepintRepository depintRepository;

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private DepositorRepository depositorRepository;

	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private TDepintRepository tDepintRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private ReportMasterService reportMasterService;
	
	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<?> fetchInterestCalculationReport(String companyCode, String calculateUpTo, String site, String userId) {

		this.tDepintRepository.truncate();
		/* Fetching active deposit list from query  */
		Map<String, List<ActiveDepositBean>> activeDepositList = this.depositRepository.fetchActiveDeposits(companyCode, LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay()).stream().collect(Collectors.groupingBy(ActiveDepositBean::getDepDepositor));
		logger.info("ActiveDepositBeanList :: {}", activeDepositList);

		if(MapUtils.isNotEmpty(activeDepositList)) {
			String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

			List<Tdepint> tDepintList = new ArrayList<>();
			ValueContainer<List<Tdepint>> tDepintListVC = new ValueContainer<List<Tdepint>>(tDepintList);
			ValueContainer<Double> totalInterestAmountWithoutStaff = new ValueContainer<Double>(BigInteger.ZERO.doubleValue());
			ValueContainer<Double> totalInterestAmountForStaff = new ValueContainer<Double>(BigInteger.ZERO.doubleValue());
			ValueContainer<Long> totalDepositAmount = new ValueContainer<Long>(BigInteger.ZERO.longValue());
			activeDepositList.entrySet().stream()
			.map(entrySet -> {			
				Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(entrySet.getKey(), companyCode);

				ValueContainer<Double> totalInterestAmountForDepositor = new ValueContainer<Double>(depositorEntity.getDeptrIntpaidytd());
				ValueContainer<Double> totalTaxPaidAmountForDepositor = new ValueContainer<Double>(depositorEntity.getDeptrTaxpaidytd());
				ValueContainer<Double> totalAccintAmountForDepositor = new ValueContainer<Double>(depositorEntity.getDeptrAccint());
				ValueContainer<Double> totalTdsAmountForDepositor = new ValueContainer<Double>(depositorEntity.getDeptrTds());

				TdepintBuilder tDepintBuilder = Tdepint.builder();
				entrySet.getValue().stream().filter(filter -> qualifyForInterest(filter.getMaxIntupto(), filter.getDepIntfreq().intValue(), filter.getDepMatdate(), calculateUpTo, filter.getDepDepdate()) == Boolean.TRUE).map(deposit -> {
					try{
						simpMessagingTemplate.convertAndSend("/fd/interest-calc-socket", new DepositDepositorBean(deposit.getDepDepositor(), deposit.getDepReceiptnum()));
						logger.info("Deposit :: {}", deposit);
						if(Objects.isNull(deposit.getMaxIntupto()))
							deposit.setMaxIntupto(deposit.getDepDepdate().toLocalDate());
						else 
							deposit.setMaxIntupto(deposit.getMaxIntupto().plus(1, ChronoUnit.DAYS));

						Double staffIntRate = BigInteger.ZERO.doubleValue();
						if(deposit.getDepStaffyn().equals("Y")) {
							staffIntRate = Double.valueOf(this.entityRepository.findByNumsEntityCk_EntClassAndEntityCk_EntId("FDINT", "FDINT").split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);
						}
						Double interestAmount = InterestCalculationLogic.calculateInterest(deposit.getDepDepamount().doubleValue(), deposit.getMaxIntupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), calculateUpTo, deposit.getDepIntrate(), deposit.getDepIntfreq().intValue()).doubleValue();
						Double interestAmountForStaff = InterestCalculationLogic.calculateInterestForStaff(deposit.getDepDepamount().doubleValue(), deposit.getMaxIntupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), calculateUpTo, deposit.getDepIntrate(), deposit.getDepStaffyn(), staffIntRate).doubleValue();
						/* To calculate staff and without staff interest for final report */
						totalInterestAmountWithoutStaff.setValue(totalInterestAmountWithoutStaff.getValue() + interestAmount);		
						totalInterestAmountForStaff.setValue(totalInterestAmountForStaff.getValue() + interestAmountForStaff);

						/* Calculate interest amount for each depositor */
						totalInterestAmountForDepositor.setValue(totalInterestAmountForDepositor.getValue() + interestAmount + interestAmountForStaff); 
						totalAccintAmountForDepositor.setValue(totalAccintAmountForDepositor.getValue() + interestAmount + interestAmountForStaff); 

						Long tdsAmount = calcTds(deposit, interestAmount, calculateUpTo, totalInterestAmountForDepositor.getValue());
						totalTdsAmountForDepositor.setValue(totalTdsAmountForDepositor.getValue() + tdsAmount); 
						totalTaxPaidAmountForDepositor.setValue(totalTaxPaidAmountForDepositor.getValue() + tdsAmount);

						totalDepositAmount.setValue(totalDepositAmount.getValue() + deposit.getDepDepamount().longValue());

						tDepintList.add(tDepintBuilder
								.tDepintCK(TdepintCK.builder().tdinCoy(deposit.getDepCoy()).tdinDepositor(deposit.getDepDepositor()).tdinReceiptnum(deposit.getDepReceiptnum()).tdinIntupto(LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).build())
								.tdinFromdate(deposit.getDepDepdate().toLocalDate())
								.tdinTodate(deposit.getDepMatdate().toLocalDate())
								.tdinProprietor(deposit.getDepProprietor())
								.tdinInterest(interestAmount)
								.tdinIntfrom(deposit.getMaxIntupto())
								.tdinTds(tdsAmount.doubleValue())
								.tdinSessid(Double.valueOf(sessionId))
								.tdinSite(site)
								.tdinUserid(userId)
								.tdinToday(LocalDateTime.now())

								//deposit table
								.tdepCoy(deposit.getDepCoy())
								.tdepDepositor(deposit.getDepDepositor())
								.tdepReceiptnum(deposit.getDepReceiptnum())
								.tdepSite(site)
								.tdepUserid(userId)
								.tdepIntpaidytd(deposit.getDepIntpaidytd() + interestAmount)
								.tdepAccint(deposit.getDepAccint() + interestAmount)
								.tdepTaxpaidytd(deposit.getDepTaxpaidytd() + tdsAmount)
								.tdepTds(deposit.getDepTds() + tdsAmount)

								//depositor
								.tdeptrCoy(companyCode)
								.tdeptrDepositor(deposit.getDepDepositor())
								.tdeptrToday(LocalDateTime.now())
								.tdeptrSite(site)
								.tdeptrUserid(userId)
								.tdepToday(LocalDateTime.now())
								.build());
						return deposit;
					}catch(Exception e) {return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Depositor : "+ entrySet.getKey() + "Reciept : "+ deposit.getDepReceiptnum()).build());}
				}).collect(Collectors.toList());

				tDepintListVC.setValue(tDepintList.stream().map(depint -> {
					GenericExnarrLogic.generateExnarrBean(new ActrandBean(), "");
					if(entrySet.getKey().equals(depint.getTDepintCK().getTdinDepositor())) {
						depint.setTdeptrIntpaidytd(totalInterestAmountForDepositor.getValue());
						depint.setTdeptrTaxpaidytd(totalTaxPaidAmountForDepositor.getValue());
						depint.setTdeptrAccint(totalAccintAmountForDepositor.getValue());
						depint.setTdeptrTds(totalTdsAmountForDepositor.getValue());
					}
					return depint;
				}).collect(Collectors.toList()));
				return entrySet;
			}).collect(Collectors.toList());
			logger.info("Tdepint List :: {}", tDepintListVC.getValue());
			simpMessagingTemplate.convertAndSend("/fd/interest-calc-socket", ConnectionStatusEnum.DISCONNECT.toString());
			this.tDepintRepository.saveAllAndFlush(tDepintListVC.getValue());

			Map<String, Object> reportParamsMap = new HashMap<String, Object>();
			reportParamsMap.put("sessionId", sessionId);
			reportParamsMap.put("closeDate",  new java.sql.Date(CommonUtils.INSTANCE.closeDate().getTime()).toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
			reportParamsMap.put("isSaveEnabled", InterestCalculationLogic.isInterestCalculationSaveEnabled());
			reportParamsMap.put("totalInterestAmount",  totalInterestAmountWithoutStaff);
			reportParamsMap.put("totalInterestAmountForStaff", totalInterestAmountForStaff);
			reportParamsMap.put("totalDepositAmount", totalDepositAmount);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(reportParamsMap).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	private Boolean qualifyForInterest(LocalDate maxIntupto, Integer intFreq, LocalDateTime matDate, String calculateUpTo, LocalDateTime depDate) {
		if(Objects.isNull(maxIntupto))
			maxIntupto = depDate.toLocalDate();
		else
			maxIntupto = maxIntupto.plus(1, ChronoUnit.DAYS);
		LocalDate DtPrinowpayupto;
		//		maxIntupto = maxIntupto.plus(1, ChronoUnit.DAYS);
		DtPrinowpayupto = maxIntupto;
		DtPrinowpayupto = DtPrinowpayupto.plus(intFreq, ChronoUnit.MONTHS);

		// Does not qualify then return zero
		if (matDate.compareTo(maxIntupto.atStartOfDay()) < 0) {
			return Boolean.FALSE;
		} else {
			if (maxIntupto.compareTo(LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) > 0) {
				return Boolean.FALSE;
			}
			if (DtPrinowpayupto.compareTo(LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) > 0) {
				DtPrinowpayupto = LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
				return Boolean.TRUE;
			}
			if (DtPrinowpayupto.compareTo(LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) < 0) {
				DtPrinowpayupto = LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
				return Boolean.TRUE;
			}
			return Boolean.TRUE;
		}
	}

	/* Check and calculate applicable tds amount */
	private Long calcTds(ActiveDepositBean deposit, Double currentInterestAmount, String calculateUpTo, Double totalInterestAmountForDepositor) {
		Boolean isTaxDeductable = Boolean.TRUE;
		Double decimalTdsAmount = BigInteger.ZERO.doubleValue();

		String ldTdsFetch  = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdAndEntityCk_EntChar1("TDS", "LD", deposit.getDepDepositor(), deposit.getDepMatdate().toLocalDate(), deposit.getDepDepdate().toLocalDate());
		Double tdsLimit = BigInteger.ZERO.doubleValue();
		Double tdsRate = BigInteger.ZERO.doubleValue();
		Double tdsRateLd = BigInteger.ZERO.doubleValue();
		Double tdsLimitLd = BigInteger.ZERO.doubleValue();
		logger.info("ldTdsFetch :: {}", ldTdsFetch);

		if(deposit.getDeptrTaxalwaysyn().equals("N"))
			isTaxDeductable = Boolean.FALSE;
		else
			if(deposit.getDeptrTds15hyn().equals("Y") || deposit.getDeptrTds15gyn().equals("Y"))
				isTaxDeductable = Boolean.FALSE;
		if(isTaxDeductable) {
			if(StringUtils.isNotBlank(ldTdsFetch)) {
				tdsLimit = Double.valueOf(ldTdsFetch.split(CommonConstraints.INSTANCE.COMMA_STRING)[0]);
				tdsRate = Double.valueOf(ldTdsFetch.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);
				tdsRateLd= Double.valueOf(ldTdsFetch.split(CommonConstraints.INSTANCE.COMMA_STRING)[2]);
				tdsLimitLd = Double.valueOf(ldTdsFetch.split(CommonConstraints.INSTANCE.COMMA_STRING)[3]);
				if((deposit.getDepIntpaidytd() + currentInterestAmount) > tdsLimitLd) // If interest exceeds 10 Lakh
					tdsRate = tdsRateLd;
			}else {
				String tdsLimitAndRate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDS", "INSEC", LocalDate.parse(calculateUpTo, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				tdsLimit = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[0]);
				tdsRate = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);
			}

			if (deposit.getDeptrTds15hyn().equals("N") && deposit.getDeptrTds15gyn().equals("N")) {
				if (totalInterestAmountForDepositor > tdsLimit) {
					if ((totalInterestAmountForDepositor - currentInterestAmount) > tdsLimit)
						decimalTdsAmount = (currentInterestAmount / 100D) * tdsRate;
					else
						decimalTdsAmount = ((totalInterestAmountForDepositor) / 100D) * tdsRate;
				} else
					decimalTdsAmount = BigInteger.ZERO.doubleValue();
			}
		}
		return Math.round(decimalTdsAmount);
	}

	@Override
	public ResponseEntity<?> truncateTempTable() {
		this.tDepintRepository.truncate();
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> downloadInterestPaymentSummary(TDepintBean tdepintBean){
		List<Tdepint> tempDepintList = this.tDepintRepository.findByTdinSessid(tdepintBean.getSessid());

		if(CollectionUtils.isNotEmpty(tempDepintList)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("h1", "'Interest Payment Register.'");
			map.put("h3", "'Run statistics'");
			map.put("formname", "'FrmCalculateInterest'");
			map.put("noofd", "'"+this.depositorRepository.countOfAllDepositors(tdepintBean.getCoy())+"'");
			map.put("noofdepositorc_1", "'"+tempDepintList.stream().collect(Collectors.groupingBy(Tdepint::getTdeptrDepositor, Collectors.counting())).values().stream().count()+"'");
			map.put("noofdepositorc", "'"+tempDepintList.stream().collect(Collectors.groupingBy(Tdepint::getTdeptrDepositor, Collectors.counting())).values().stream().count()+"'");
			map.put("TotIAmt", "'"+tdepintBean.getTotalInterestAmount()+"'");
			map.put("staffallow", "'"+tdepintBean.getTotalInterestAmountForStaff()+"'");
			map.put("TotIntAmtExclStaff", "'"+(tdepintBean.getTotalInterestAmount() + tdepintBean.getTotalInterestAmountForStaff())+"'");
			map.put("noofdepositort", "'"+this.tDepintRepository.findDistinctTdsCount()+"'");
			map.put("tottaxdeducted", "'"+tempDepintList.stream().mapToLong(depint -> depint.getTdinTds().longValue()).sum()+"'");
			map.put("TotChequeAmt", "'"+((tdepintBean.getTotalInterestAmount() + tdepintBean.getTotalInterestAmountForStaff()) - tempDepintList.stream().mapToLong(depint -> depint.getTdinTds().longValue()).sum())+"'");
			map.put("noofdeposits", "'"+this.depositRepository.countOfAllDeposits(tdepintBean.getCoy())+"'");
			map.put("noofdepositsc", "'"+this.depositRepository.countOfConsideredDeposits(tdepintBean.getCoy())+"'");
			map.put("noofdepositsc_1", "'"+this.depositRepository.countOfConsideredDeposits(tdepintBean.getCoy())+"'");
			map.put("totdepositamt", "'"+tdepintBean.getTotalDepositAmount()+"'");
		
			byte[] byteArray = this.reportInternalFeignClient.generateTtxReportWithOnlyFormula(ReportMasterRequestBean.builder().name("Int_Payment_Reg.rpt")
					.reportParameters(map)
					.isPrint(false)
					.build());
			
			File tempFile;
			FileOutputStream fos = null;
			try {
				tempFile = File.createTempFile("Filename", "suffix", null);
			
			fos = new FileOutputStream(tempFile);
			fos.write(byteArray);
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.pdf"))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.body(new InputStreamResource(new FileInputStream(tempFile)));
			}catch (Exception e) {
				if(e.getCause().getMessage().equals(HttpStatus.NOT_FOUND.toString()))
					return ResponseEntity.notFound().build();
				if(e.getCause().getMessage().equals(HttpStatus.BAD_REQUEST.toString()))
					return ResponseEntity.badRequest().build();
				e.printStackTrace();
			}finally{
					try {
						if(Objects.nonNull(fos))
							fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Some error occured.").build());
	}
	
	@Override
	public ResponseEntity<?> saveInterestCalculation(TDepintBean tdepintBean) {

		List<Tdepint> tempDepintList = this.tDepintRepository.findByTdinSessid(tdepintBean.getSessid());

		if(CollectionUtils.isNotEmpty(tempDepintList)) {
			String transer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#DIP");
			Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(tdepintBean.getCoy(), CommonUtils.INSTANCE.closeDate());
			this.depintRepository.saveAll(FdEntityEntityMapper.addDepositToTdepintFdPojoEntityMapping.apply(new Object[] {tempDepintList, transer}));
			this.depositRepository.updateDepositReceiptsOnInterestCalc(tdepintBean.getCoy(), tdepintBean.getSessid(), tdepintBean.getSite(), tdepintBean.getUserid(), LocalDateTime.now());
			this.depositorRepository.updateDepositorsYtdInterestCalc(tdepintBean.getCoy(), tdepintBean.getSessid(), tdepintBean.getSite().trim(), tdepintBean.getUserid(), LocalDateTime.now());

			String tranDate = tdepintBean.getIntupto();
			String reffFrom = CommonConstraints.INSTANCE.BLANK_STRING;
			/* This is to manipulate the tran and voucher date in the entry when the interest is calculated on  */
			if(tdepintBean.getIntupto().substring(0,5).equals("31/03")) {
				tranDate = "30/04/".concat(tdepintBean.getIntupto().substring(tdepintBean.getIntupto().length() - 4));
				reffFrom = "01/11/".concat(String.valueOf(Integer.valueOf(tdepintBean.getIntupto().substring(tdepintBean.getIntupto().length() - 4)) - 1));
			}
			else if(tdepintBean.getIntupto().substring(0,5).equals("31/10"))
				reffFrom = "01/05/".concat(tdepintBean.getIntupto().substring(tdepintBean.getIntupto().length() - 4));
			else if(tdepintBean.getIntupto().substring(0,5).equals("30/04"))
				reffFrom = "01/04/".concat(tdepintBean.getIntupto().substring(tdepintBean.getIntupto().length() - 4));

			Actranh build = Actranh.builder()
					.actranhCK(ActranhCK.builder().acthTranser(transer).acthCoy(tdepintBean.getCoy())
							.build())
					.acthProprietor(companyEntity.getCompanyCK().getCoyProp()) 
					.acthTrandate(LocalDate.parse(tranDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay())
					.acthTrantype(TranTypeEnum.PF.toString())
					.acthVounum("SUMMARY")
					.acthLedgcode("GL")
					.acthPartytype(CommonConstraints.INSTANCE.DEPOSITORS)
					.acthPartycode("DEPOSITOR")
					.acthTranamt(tempDepintList.stream().map(deposit -> BigDecimal.valueOf(deposit.getTdinInterest()).subtract(BigDecimal.valueOf(deposit.getTdinTds())))
							.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue())
					.acthVoudate(LocalDate.parse(tranDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
					.acthReverseyn("N")
					.acthClearacyn("Y")
					.acthProvyn("N")
					.acthPostedyn("N")
					.acthSite(tdepintBean.getSite().trim())
					.acthUserid(tdepintBean.getUserid())
					.acthToday(LocalDateTime.now())
					.build();
			this.actranhRepository.save(build);

			/* Summary entry in actrand for the month of march generating 1 breakup for interest and TDS */
			List<ActrandBean> actrandBeanList = new ArrayList<>();
			if(tdepintBean.getIntupto().substring(0,5).equals("31/03")) {
				String acMajor = "11401764";
				String acMajorForContra = "80000006";
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", "DEPOSITOR", null, null);
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorForContra, null, null, "Q", "DEPOSITOR", null, null);
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, "PF");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorForContra, "PF");

				for(int i=1; i <= 2; i++){
					if(i % 2 == 1) {
						actrandBeanList.add(ActrandBean.builder()
								.transer(transer)
								.bunum(i)
								.trantype("PF")
								.trandate(tranDate)
								.proprietor(companyEntity.getCompanyCK().getCoyProp())
								.coy(tdepintBean.getCoy())
								.mintype(accoutingDataForTran.getMinType())
								.mincode(accoutingDataForTran.getMinCode())
								.partytype(accoutingDataForTran.getPartyType())
								.partycode(accoutingDataForTran.getPartyCode())
								.acmajor(acMajor)
								.project(accoutingDataForTran.getProject())
								.acminor(accoutingDataForTran.getAcminor())
								.ledgcode("GL")
								.vounum("SUMMARY")
								.voudate(tranDate)
								.tranamt(tempDepintList.stream().map(p -> BigDecimal.valueOf(p.getTdinInterest()).subtract(BigDecimal.valueOf(p.getTdinTds())))
										.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue())
								.reffrom(reffFrom)
								.refupto(tdepintBean.getIntupto())
								.cfgroup(accoutingDataCashFlow.getCfGroup())
								.cfcode(accoutingDataCashFlow.getCfCode())
								.paymode("Q")
								//X columns (Contra entry)
								.xreftranser(transer)
								.xacminor(accoutingDataForContraTran.getAcminor())
								.xacmajor(acMajorForContra)
								.xmintype(accoutingDataForContraTran.getMinType())
								.xpartycode(accoutingDataForContraTran.getPartyCode())
								.xproject(accoutingDataForContraTran.getProject())
								.xreftrandate(tranDate)
								.xpartytype(accoutingDataForContraTran.getPartyType())
								.xrefbunum(i+1)
								.site(tdepintBean.getSite())
								.userid(tdepintBean.getUserid())
								.today(LocalDateTime.now())
								.build()
								);
					}else {
						actrandBeanList.add(ActrandBean.builder()
								.transer(transer)
								.bunum(i)
								.trantype("PF")
								.trandate(tranDate)
								.proprietor(companyEntity.getCompanyCK().getCoyProp())
								.coy(tdepintBean.getCoy())
								.mintype(accoutingDataForContraTran.getMinType())
								.mincode(accoutingDataForContraTran.getMinCode())
								.partytype(accoutingDataForContraTran.getPartyType())
								.partycode(accoutingDataForContraTran.getPartyCode())
								.project(accoutingDataForContraTran.getProject())
								.acminor(accoutingDataForContraTran.getAcminor())
								.acmajor(acMajorForContra)
								.ledgcode("GL")
								.vounum("SUMMARY")
								.voudate(tranDate)
								.tranamt(tempDepintList.stream().map(p -> BigDecimal.valueOf(p.getTdinInterest()).subtract(BigDecimal.valueOf(p.getTdinTds())))
										.reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue())
								.reffrom(reffFrom)
								.refupto(tdepintBean.getIntupto())
								.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
								.cfcode(accoutingDataCashFlowForContra.getCfCode())
								.paymode("Q")
								//X columns (Contra entry)
								.xreftranser(transer)
								.xacminor(accoutingDataForTran.getAcminor())
								.xacmajor(acMajor)
								.xmintype(accoutingDataForTran.getMinType())
								.xpartycode(accoutingDataForTran.getPartyCode())
								.xproject(accoutingDataForTran.getProject())
								.xreftrandate(tranDate)
								.xpartytype(accoutingDataForTran.getPartyType())
								.xrefbunum(i-1)
								.site(tdepintBean.getSite())
								.userid(tdepintBean.getUserid())
								.today(LocalDateTime.now())
								.build()
								);
					}
				}
			}
			else {
				/* Summary entry in actrand for the month of april or october generating 2 breakups for interest and TDS */
				String acMajor = CommonConstraints.INSTANCE.BLANK_STRING;
				String acMajorForContra = "80000006";
				AccountingBean accoutingDataForTran = null;
				AccountingBean accoutingDataForContraTran = null;
				AccountingBean accoutingDataCashFlow = null;
				AccountingBean accoutingDataCashFlowForContra = null;
				Double tranAmount = BigInteger.ZERO.doubleValue();
				for(int i=1; i <= 4; i++){
					if(i <= 2) {
						acMajor = "40704004";
						accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", "DEPOSITOR", null, null);
						accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorForContra, null, null, "Q", "DEPOSITOR", null, null);
						accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, "PF");
						accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorForContra, "PF");
						tranAmount = tempDepintList.stream().mapToDouble(Tdepint::getTdinInterest).sum();
					}else {
						acMajor = "11401831";
						accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", "DEPOSITOR", null, null);
						accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorForContra, null, null, "Q", "DEPOSITOR", null, null);
						accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, "PF");
						accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorForContra, "PF");
						tranAmount = tempDepintList.stream().mapToDouble(Tdepint::getTdinTds).sum() * -1;
					}
					if(i % 2 == 1) {
						actrandBeanList.add(ActrandBean.builder()
								.transer(transer)
								.bunum(i)
								.trantype("PF")
								.trandate(tranDate)
								.proprietor(companyEntity.getCompanyCK().getCoyProp())
								.coy(tdepintBean.getCoy())
								.mintype(accoutingDataForTran.getMinType())
								.mincode(accoutingDataForTran.getMinCode())
								.partytype(accoutingDataForTran.getPartyType())
								.partycode(accoutingDataForTran.getPartyCode())
								.acmajor(acMajor)
								.project(accoutingDataForTran.getProject())
								.acminor(accoutingDataForTran.getAcminor())
								.vounum("SUMMARY")
								.ledgcode("GL")
								.voudate(tranDate)
								.tranamt(tranAmount)
								.reffrom(reffFrom)
								.refupto(tdepintBean.getIntupto())
								.cfgroup(accoutingDataCashFlow.getCfGroup())
								.cfcode(accoutingDataCashFlow.getCfCode())
								.paymode("Q")
								//X columns (Contra entry)
								.xreftranser(transer)
								.xacminor(accoutingDataForContraTran.getAcminor())
								.xacmajor(acMajorForContra)
								.xmintype(accoutingDataForContraTran.getMinType())
								.xpartycode(accoutingDataForContraTran.getPartyCode())
								.xproject(accoutingDataForContraTran.getProject())
								.xreftrandate(tranDate)
								.xpartytype(accoutingDataForContraTran.getPartyType())
								.xrefbunum(i+1)
								.site(tdepintBean.getSite())
								.userid(tdepintBean.getUserid())
								.today(LocalDateTime.now())
								.build()
								);
					}else {
						actrandBeanList.add(ActrandBean.builder()
								.transer(transer)
								.bunum(i)
								.trantype("PF")
								.trandate(tranDate)
								.proprietor(companyEntity.getCompanyCK().getCoyProp())
								.coy(tdepintBean.getCoy())
								.mintype(accoutingDataForContraTran.getMinType())
								.mincode(accoutingDataForContraTran.getMinCode())
								.partytype(accoutingDataForContraTran.getPartyType())
								.partycode(accoutingDataForContraTran.getPartyCode())
								.project(accoutingDataForContraTran.getProject())
								.acminor(accoutingDataForContraTran.getAcminor())
								.acmajor(acMajorForContra)
								.vounum("SUMMARY")
								.voudate(tranDate)
								.tranamt(tranAmount * -1)
								.reffrom(reffFrom)
								.refupto(tdepintBean.getIntupto())
								.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
								.cfcode(accoutingDataCashFlowForContra.getCfCode())
								.paymode("Q")
								.ledgcode("GL")
								//X columns (Contra entry)
								.xreftranser(transer)
								.xacminor(accoutingDataForTran.getAcminor())
								.xacmajor(acMajor)
								.xmintype(accoutingDataForTran.getMinType())
								.xpartycode(accoutingDataForTran.getPartyCode())
								.xproject(accoutingDataForTran.getProject())
								.xreftrandate(tranDate)
								.xpartytype(accoutingDataForTran.getPartyType())
								.xrefbunum(i-1)
								.site(tdepintBean.getSite())
								.userid(tdepintBean.getUserid())
								.today(LocalDateTime.now())
								.build()
								);
					}
				}
			}
			this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
			companyEntity.setCoyIntyymm(LocalDate.parse(tdepintBean.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).format(CommonConstraints.INSTANCE.YYYYMM_FORMATTER));
			this.companyRepository.save(companyEntity);
			
			GenericAuditContextHolder.getContext().setTransactionNo(CommonConstraints.INSTANCE.SPACE_STRING);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Please note the DIP number : "+transer).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Some error occured.").build());
	}
}