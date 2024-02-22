package kraheja.fd.deposit.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.ExnarrBean;
import kraheja.commons.bean.request.OutchqRequestBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Inchq;
import kraheja.commons.entity.Outchq;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.ExnarrMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.ExnarrRepository;
import kraheja.commons.repository.InchqRepository;
import kraheja.commons.repository.OutchqRepository;
import kraheja.commons.repository.PayinslipRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.GenericExnarrLogic;
import kraheja.commons.utils.ValueContainer;
import kraheja.fd.deposit.bean.DepintBean;
import kraheja.fd.deposit.bean.DepositDischargeBean;
import kraheja.fd.deposit.bean.request.DepositDischargeRequestBean;
import kraheja.fd.deposit.bean.request.DepositRequestBean;
import kraheja.fd.deposit.bean.request.DepositTransferRequestBean;
import kraheja.fd.deposit.bean.request.DepositorRequestBean;
import kraheja.fd.deposit.bean.response.DepositDischargeResponseBean;
import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.enums.DischargeBreakUp;
import kraheja.fd.deposit.mappers.FdEntityPojoMapper;
import kraheja.fd.deposit.mappers.FdPojoEntityMapper;
import kraheja.fd.deposit.repository.DepintRepository;
import kraheja.fd.deposit.repository.DepositRepository;
import kraheja.fd.deposit.repository.DepositorRepository;
import kraheja.fd.deposit.service.DepositService;
import kraheja.fd.deposit.service.DepositorService;
import kraheja.fd.deposit.utils.InterestCalculationLogic;

@Service
@Transactional
public class DepositServiceImpl implements DepositService {

	private static final Logger logger = LoggerFactory.getLogger(DepositServiceImpl.class);

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private DepositorRepository depositorRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private InchqRepository inchqRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private DepintRepository depintRepository;

	@Autowired
	private OutchqRepository outchqRepository;

	@Autowired
	private DepositorService depositorService;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ExnarrRepository exnarrRepository;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private PayinslipRepository payinslipRepository;

	@Override
	public ResponseEntity<?> fetchDepositByCompanycodeAndDepositoridAndReceiptno(String depositorId,String companyCode, String recieptNo) {
		Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositorId, companyCode, recieptNo);
		logger.info("DepositEntity :: {}", depositEntity);
		List<Inchq> inchqEntity  = this.inchqRepository.findByPartyCodeAndRecieptNum(companyCode + depositorId, recieptNo);
		logger.info("InchqEntity :: {}", inchqEntity);
		if(depositEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder().data(FdEntityPojoMapper.fetchDepositEntityPojoMapper.apply(new Object[] {Arrays.asList(depositEntity), inchqEntity, null}).get(BigInteger.ZERO.intValue())).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> addDeposit(DepositRequestBean depositRequestBean) {

		Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositRequestBean.getCoy().trim(),CommonUtils.INSTANCE.closeDate());
		logger.info("Company :: {}" , companyEntity);

		logger.info("Testing :: {} ", depositRequestBean);

		Integer receiptNumber = companyEntity.getCoyFdrecnum();

		String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		logger.info("Entity :: {}" , SiteFromDBEntity);

		String transerStr = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#R");
		logger.info("TranserStr :: {}" , transerStr);

		String proprietor = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositRequestBean.getCoy().trim(),CommonUtils.INSTANCE.closeDate());
		depositRequestBean.setTranser(transerStr);
		depositRequestBean.setOrigreceipt(receiptNumber.toString());

		this.depositRepository.save(FdPojoEntityMapper.addDepositFdPojoEntityMapping.apply(new Object[] {depositRequestBean, SiteFromDBEntity, receiptNumber.toString()}));

		String partyCode = depositRequestBean.getCoy().trim().concat(depositRequestBean.getDepositor());
		depositRequestBean.getInchqRequestBean().stream().map(inchqRequestBean -> {
			inchqRequestBean.setPartycode(partyCode);
			inchqRequestBean.setLoanyn(CommonConstraints.INSTANCE.LoanN);
			inchqRequestBean.setActype(BigInteger.ONE.toString());
			inchqRequestBean.setFund(BigInteger.ONE.toString());
			inchqRequestBean.setOrigsys(CommonConstraints.INSTANCE.ORIGSYS);
			inchqRequestBean.setPaymode(CommonConstraints.INSTANCE.CHEQUE);
			inchqRequestBean.setRecnum(receiptNumber.toString());
			inchqRequestBean.setTranser(transerStr);
			inchqRequestBean.setCoyBank(depositRequestBean.getBankcode());
			inchqRequestBean.setSlipnum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null);
			return inchqRequestBean;
		}).collect(Collectors.toList());

		this.inchqRepository.saveAll(AddPojoEntityMapper.addInchqPojoEntityMappingList.apply(new Object[] {depositRequestBean.getInchqRequestBean(), SiteFromDBEntity}));

		ActranhBean actranhBean = ActranhBean.builder()
				.transer(transerStr)
				.trantype(TranTypeEnum.RC.toString())
				.trandate(depositRequestBean.getDepdate())
				.partytype(CommonConstraints.INSTANCE.DEPOSITORS)
				.partycode(partyCode)
				.tranamt(depositRequestBean.getDepamount().doubleValue())
				.voudate(depositRequestBean.getDepdate())
				.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
				.bankcode(depositRequestBean.getBankcode())
				.postedyn("Y")
				.proprietor(proprietor)
				.coy(depositRequestBean.getCoy().trim())
				.site(SiteFromDBEntity)
				.userid(depositRequestBean.getUserid())
				.reverseyn("N")
				.clearacyn("Y")
				.provyn("N")
				.crepno(BigInteger.ONE.doubleValue())
				.build();

		this.actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));

		List<ActrandBean> actrandBeanList = new ArrayList<>();
		Integer intFrequency = depositRequestBean.getIntfreq().intValue();
		String acMajor;
		Integer depmonths = depositRequestBean.getDepmonths().intValue();
		String narrativeMsg = "Rate ".concat(depositRequestBean.getIntrate().toString()).concat("%");
		switch (depmonths) {
		case 3:
			acMajor = "11326052";
			break;
		case 6:
			acMajor = "11326011";
			break;
		case 12:
			acMajor = "11326024";
			break;
		case 18:
			acMajor = "11326078";
			break;
		case 24:
			acMajor = "11326037";
			break;
		case 30:
			acMajor = "11326081";
			break;
		case 36:
			acMajor = "11326040";
			break;
		case 48:
			acMajor = "11326065";
			break;
		default:
			acMajor ="11326024";
		}

		switch (intFrequency) {
		case 3:
			narrativeMsg =	narrativeMsg.concat(" , Quaterly");
			break;
		case 6:
			narrativeMsg =	narrativeMsg.concat(" , Half Yearly");
			break;
		case 12:
			narrativeMsg =	narrativeMsg.concat(" , Yearly");
			break;
		default:
			narrativeMsg =	narrativeMsg.concat(" , Half Yearly");
		}

		String acMajorContra = "80000006";
		AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", partyCode, null, null);
		AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorContra, null, null, "Q", depositRequestBean.getBankcode(), null, null);
		AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, "RC");
		AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorContra, "RC");
		Integer listCounter = 0;
		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositRequestBean.getTranser())
						.bunum(i)
						.trantype("RC")
						.trandate(depositRequestBean.getDepdate())
						.proprietor(depositRequestBean.getCoy())
						.coy(depositRequestBean.getCoy())
						.mintype(accoutingDataForTran.getMinType())
						.mincode(accoutingDataForTran.getMinCode())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(acMajor)
						.project(accoutingDataForTran.getProject())
						.acminor(accoutingDataForTran.getAcminor())
						.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
						.voudate(depositRequestBean.getReceiptdate())
						.tranamt(depositRequestBean.getDepamount().doubleValue() * -1)
						.reffrom(depositRequestBean.getDepdate())
						.refupto(depositRequestBean.getMatdate())
						.narrative(narrativeMsg)
						.cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode())
						.docdate(depositRequestBean.getReceiptdate())
						.paymode("Q")
						.doctype("RC")
						.docpartype("D")
						.docparcode(partyCode)
						.docnum(receiptNumber.toString())
						//X columns (Contra entry)
						.xreftranser(depositRequestBean.getTranser())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xacmajor(acMajorContra)
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(accoutingDataForContraTran.getPartyCode())
						.xproject(accoutingDataForContraTran.getProject())
						.xreftrandate(depositRequestBean.getDepdate())
						.xpartytype(accoutingDataForContraTran.getPartyType())
						.xrefbunum(i+1)
						.site(SiteFromDBEntity)
						.userid(depositRequestBean.getUserid())
						.today(LocalDateTime.now())
						.build()
						);
			}else {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositRequestBean.getTranser())
						.bunum(i)
						.trantype("RC")
						.trandate(depositRequestBean.getDepdate())
						.proprietor(depositRequestBean.getCoy())
						.coy(depositRequestBean.getCoy())
						.mintype(accoutingDataForContraTran.getMinType())
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(accoutingDataForContraTran.getAcminor())
						.acmajor(acMajorContra)
						.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
						.voudate(depositRequestBean.getReceiptdate())
						.tranamt(depositRequestBean.getDepamount().doubleValue())
						.reffrom(depositRequestBean.getDepdate())
						.refupto(depositRequestBean.getMatdate())
						.narrative(narrativeMsg)
						.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
						.cfcode(accoutingDataCashFlowForContra.getCfCode())
						.docdate(depositRequestBean.getReceiptdate())
						.paymode("Q")
						.doctype("RC")
						.docpartype("D")
						.docparcode(partyCode)
						.docnum(receiptNumber.toString())
						//X columns (Contra entry)
						.xreftranser(depositRequestBean.getTranser())
						.xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(acMajor)
						.xmintype(accoutingDataForTran.getMinType())
						.xpartycode(accoutingDataForTran.getPartyCode())
						.xproject(accoutingDataForTran.getProject())
						.xreftrandate(depositRequestBean.getDepdate())
						.xpartytype(accoutingDataForTran.getPartyType())
						.xrefbunum(i-1)
						.site(SiteFromDBEntity)
						.userid(depositRequestBean.getUserid())
						.today(LocalDateTime.now())
						.build()
						);
				listCounter++;
			}
		}

		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
		companyEntity.setCoyFdrecnum(receiptNumber + 1);
		this.companyRepository.save(companyEntity);
		
		this.payinslipRepository.updatePayInSlipByProprietorAndCoyAndBankAndLeafNum(Double.valueOf(depositRequestBean.getDepamount().toString()), transerStr, LocalDate.parse(depositRequestBean.getDepdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) , GenericAuditContextHolder.getContext().getSite(), GenericAuditContextHolder.getContext().getUserid(), LocalDateTime.now(), depositRequestBean.getProprietor().trim(), depositRequestBean.getCoy().trim(), depositRequestBean.getBankcode().trim(), depositRequestBean.getVouNum().trim());
		
		GenericAuditContextHolder.getContext().setTransactionNo(receiptNumber.toString());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().message("Please note down the Receipt No. : "+receiptNumber + "\n" + "Please note down the Transer No. : "+transerStr).status(Boolean.TRUE).build()); 
	}

	@Override
	public ResponseEntity<?> checkRecieptNumberExists(String depositorId, String recieptNumber) {
		List<String> recieptNumbers = this.depositRepository.findRecieptNumbersByDepositorId(depositorId);
		System.out.println("RecieptNumber List: " + recieptNumbers);
		System.out.println("DepositorID: " + depositorId);
		if(recieptNumbers.contains(recieptNumber)){
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("RecieptNo " +recieptNumber+ " already exists").build()); 
		}else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build()); 
		}
	}

	@Override
	public ResponseEntity<?> fetchDepositsByCompanycodeAndDepositorid(String depositorId, String companyCode) {
		List<Deposit> depositEntityList = this.depositRepository.findByDepositorIdAndCompanyCode(depositorId.trim(), companyCode.trim());
		logger.info("DepositEntity :: {}", depositEntityList);

		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositorId.trim(), companyCode.trim());
		logger.info("DepositorEntity :: {}", depositorEntity);

		depositEntityList = depositEntityList.stream().filter(depositEntity ->  Objects.isNull(depositEntity.getDepDisdate())).sorted(Comparator.comparing(deposit -> deposit.getDepositCK().getDepReceiptnum())).collect(Collectors.toList());	
		if(CollectionUtils.isNotEmpty(depositEntityList)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().data(FdEntityPojoMapper.fetchDepositEntityPojoMapper.apply(new Object[] {depositEntityList, null, depositorEntity.getDeptrName()}).stream().collect(Collectors.toList())).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> addDepositRenew(DepositRequestBean depositRequestBean) {

		Deposit existingDeposit = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositRequestBean.getDepositor().trim(), depositRequestBean.getCoy().trim(), depositRequestBean.getReceiptnum().trim());		
		Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositRequestBean.getCoy().trim(),CommonUtils.INSTANCE.closeDate());
		logger.info("Company :: {}" , companyEntity);

		Integer receiptNumber = companyEntity.getCoyFdrecnum();

		String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		logger.info("Entity :: {}" , SiteFromDBEntity);

		String transerStr = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#J");
		logger.info("TranserStr :: {}" , transerStr);

		depositRequestBean.setTranser(transerStr);

		String proprietor = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositRequestBean.getCoy().trim(),CommonUtils.INSTANCE.closeDate());

		List<ActrandBean> actrandBeanList = new ArrayList<>();
		//		Integer intFrequency = depositRequestBean.getIntfreq().intValue();
		String acMajor, acMajorContra;
		Integer depmonths = depositRequestBean.getDepmonths().intValue();
		Integer newDepmonths = depositRequestBean.getDepmonthnew().intValue();
		depositRequestBean.setReceiptdate(depositRequestBean.getDepdatenew());
		String oldDepDate = depositRequestBean.getDepdate();
		depositRequestBean.setDepdate(depositRequestBean.getDepdatenew());
		this.depositRepository.save(FdPojoEntityMapper.addDepositFdPojoEntityMapping.apply(new Object[] {depositRequestBean, SiteFromDBEntity, receiptNumber.toString()}));
		this.depositRepository.updateMaturedRecieptDisDate(existingDeposit.getDepMatdate(), depositRequestBean.getDepositor().trim(), depositRequestBean.getReceiptnum().trim(), LocalDateTime.now(), SiteFromDBEntity, depositRequestBean.getUserid());
		ServiceResponseBean vouNum =  GenericAccountingLogic.funcGetJVNumber(depositRequestBean.getCoy().trim(), existingDeposit.getDepMatdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		logger.info("Voucher Number :: {}" , vouNum);

		if(vouNum.getMessage() != null)
			return ResponseEntity.ok(ServiceResponseBean.builder().message(vouNum.getMessage()).status(Boolean.FALSE).build()); 

		String narrativeMsgForActranh = "RENEWAL OF DEPOSIT";
		ActranhBean actranhBean = ActranhBean.builder()
				.transer(transerStr)
				.trantype(TranTypeEnum.JV.toString())
				.trandate(depositRequestBean.getDepdate())
				.tranamt(BigInteger.ZERO.doubleValue())
				.voudate(depositRequestBean.getDepdate())
				.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
				//				.bankcode(depositRequestBean.getBankcode())
				.postedyn("Y")
				.proprietor(proprietor)
				.coy(depositRequestBean.getCoy().trim())
				.site(SiteFromDBEntity)
				.userid(depositRequestBean.getUserid())
				.reverseyn("N")
				.clearacyn("Y")
				.provyn("N")
				.balancedyn("Y")
				.narrative(narrativeMsgForActranh)
				.crepno(BigInteger.ONE.doubleValue())
				.voudate(depositRequestBean.getDepdate())
				.vounum(vouNum.getData().toString())
				.ledgcode("GL")
				.build();

		this.actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));

		switch (depmonths) {
		case 3:
			acMajor = "11326052";
			break;
		case 6:
			acMajor = "11326011";
			break;
		case 12:
			acMajor = "11326024";
			break;
		case 18:
			acMajor = "11326078";
			break;
		case 24:
			acMajor = "11326037";
			break;
		case 30:
			acMajor = "11326081";
			break;
		case 36:
			acMajor = "11326040";
			break;
		case 48:
			acMajor = "11326065";
			break;
		default:
			acMajor ="11326024";
		}

		switch (newDepmonths) {
		case 3:
			acMajorContra = "11326052";
			break;
		case 6:
			acMajorContra = "11326011";
			break;
		case 12:
			acMajorContra = "11326024";
			break;
		case 18:
			acMajorContra = "11326078";
			break;
		case 24:
			acMajorContra = "11326037";
			break;
		case 30:
			acMajorContra = "11326081";
			break;
		case 36:
			acMajorContra = "11326040";
			break;
		case 48:
			acMajorContra = "11326065";
			break;
		default:
			acMajorContra ="11326024";
		}
		String partyCode = depositRequestBean.getCoy().trim().concat(depositRequestBean.getDepositor());
		AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", partyCode, null, null);// removed partycode ------------>
		AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorContra, null, null, "D", partyCode, null, null);//------------>TO DO
		AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, TranTypeEnum.JV.toString());
		AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorContra, TranTypeEnum.JV.toString());
		Integer listCounter = 0;


		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositRequestBean.getTranser())
						.bunum(i)
						.trantype("JV")
						.trandate(depositRequestBean.getDepdate())
						.proprietor(depositRequestBean.getCoy())
						.coy(depositRequestBean.getCoy())
						.mintype(accoutingDataForTran.getMinType())
						.mincode(accoutingDataForTran.getMinCode())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(acMajor)
						.project(accoutingDataForTran.getProject())
						.acminor(accoutingDataForTran.getAcminor())
						.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
						.voudate(depositRequestBean.getReceiptdate())
						.tranamt(depositRequestBean.getDepamount().doubleValue())
//						.reffrom(oldDepDate)
//						.refupto(existingDeposit.getDepMatdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.narrative("IntRate : ".concat(depositRequestBean.getIntrateold().toString()).concat(" Rcpt # ").concat(depositRequestBean.getReceiptnum()).concat("-DISCH"))
						.cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode())
						.docdate(depositRequestBean.getReceiptdate())
						//						.paymode("Q")
						//						.doctype("RC")
						.docpartype("D")
						//						.docparcode(partyCode)
//						.docnum(receiptNumber.toString())
						//X columns (Contra entry)
						.xreftranser(depositRequestBean.getTranser())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xacmajor(acMajorContra)
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(accoutingDataForContraTran.getPartyCode())
						.xproject(accoutingDataForContraTran.getProject())
						.xreftrandate(depositRequestBean.getDepdate())
						.xpartytype(accoutingDataForContraTran.getPartyType())
						.xrefbunum(i+1)
						.site(SiteFromDBEntity)
						.userid(depositRequestBean.getUserid())
						.ledgcode("GL")
						.docparcode(partyCode)
						.oldbunum(i)
						.contrabunum(i)
						.voudate(depositRequestBean.getDepdate())
						.vounum(vouNum.getData().toString())
						.docdate(depositRequestBean.getDepdate())
						.today(LocalDateTime.now())
						.build()
						);
			}else {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositRequestBean.getTranser())
						.bunum(i)
						.trantype("JV")
						.trandate(depositRequestBean.getDepdate())
						.proprietor(depositRequestBean.getCoy())
						.coy(depositRequestBean.getCoy())
						.mintype(accoutingDataForContraTran.getMinType())
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(accoutingDataForContraTran.getAcminor())
						.acmajor(acMajorContra)
						.vounum(StringUtils.isNotBlank(depositRequestBean.getVouNum()) ? depositRequestBean.getVouNum().trim() : null)
						.voudate(depositRequestBean.getReceiptdate())
						.tranamt(depositRequestBean.getDepamount().doubleValue() * -1)
//						.reffrom(depositRequestBean.getDepdate())
//						.refupto(depositRequestBean.getMatdate())
						.narrative("IntRate : ".concat(depositRequestBean.getIntrate().toString()).concat(" Rcpt # ").concat(receiptNumber.toString()).concat("-RENEW"))
						.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
						.cfcode(accoutingDataCashFlowForContra.getCfCode())
						.docdate(depositRequestBean.getReceiptdate())
						//						.paymode("Q")
						//						.doctype("RC")
						.docpartype("D")
						//						.docparcode(partyCode)
//						.docnum(receiptNumber.toString())
						//X columns (Contra entry)
						.xreftranser(depositRequestBean.getTranser())
						.xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(acMajor)
						.xmintype(accoutingDataForTran.getMinType())
						.xpartycode(accoutingDataForTran.getPartyCode())
						.xproject(accoutingDataForTran.getProject())
						.xreftrandate(depositRequestBean.getDepdate())
						.xpartytype(accoutingDataForTran.getPartyType())
						.xrefbunum(i-1)
						.site(SiteFromDBEntity)
						.userid(depositRequestBean.getUserid())
						.ledgcode("GL")
						.docparcode(partyCode)
						.oldbunum(i)
						.contrabunum(i)
						.voudate(depositRequestBean.getDepdate())
						.vounum(vouNum.getData().toString())
						.docdate(depositRequestBean.getDepdate())
						.today(LocalDateTime.now())
						.build()
						);
				listCounter++;
			}
		}

		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
		Integer newReceiptNumber = receiptNumber + 1;
		companyEntity.setCoyFdrecnum(newReceiptNumber);
		this.companyRepository.save(companyEntity);
		
		GenericAuditContextHolder.getContext().setTransactionNo(receiptNumber.toString());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().message("Please note down the Receipt No. : "+receiptNumber + "\n" + "Transer No. : "+transerStr+"/"+vouNum.getData().toString()).status(Boolean.TRUE).build()); 
	}

	@Override
	public ResponseEntity<?> fetchInterestRate(String depositor, String companyCode, String receiptNumber, String maturityDate, String dischargeDate) {
		Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositor.trim(), companyCode.trim(), receiptNumber.trim());
		logger.info("DEPOSIT :: {}", depositEntity);

		Date maturityDateFormat = CommonUtils.INSTANCE.stringToDateFormatter(maturityDate);
		Date dischargeDateFormat = CommonUtils.INSTANCE.stringToDateFormatter(dischargeDate);
		Double finalInterestRate = 0D;
		Integer interestRateDeduction = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("FD", "DRINT").intValue();
		finalInterestRate = dischargeDateFormat.compareTo(maturityDateFormat) < 0 ? depositEntity.getDepIntrate() - interestRateDeduction : depositEntity.getDepIntrate();

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(finalInterestRate).build());
	}

	@Override
	public ResponseEntity<?> calculateInterestForDischarge(DepositDischargeRequestBean depositDischargeRequestBean) {
		if(CollectionUtils.isNotEmpty(depositDischargeRequestBean.getDepositRequestBeanList())) {
			List<DepositDischargeResponseBean> depositDischargeResponseBeanList = new ArrayList<DepositDischargeResponseBean>();

			depositDischargeRequestBean.getDepositRequestBeanList().stream().map(depositRequestBean -> {
				DepositDischargeResponseBean depositDischargeResponseBean = new DepositDischargeResponseBean();
				Map<String, String> narrationMap = new HashMap<>();
				
				Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositRequestBean.getDepositor().trim(), depositRequestBean.getCoy().trim(), depositRequestBean.getReceiptnum().trim());
				logger.info("DepositEntity :: {}", depositEntity);

				Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositRequestBean.getDepositor().trim(), depositRequestBean.getCoy().trim());
				logger.info("Depositor :: {}", depositorEntity);

				List<Map<String, Object>> queryToResultSetBuilder = CommonResultsetGenerator.queryToResultSetBuilder("SELECT din_intfrom, din_intupto FROM Depint WHERE trim(din_receiptnum)='".concat(depositRequestBean.getReceiptnum().trim().concat("'")));
				logger.info("fromToDates :: {}", queryToResultSetBuilder);

				String dtIntUpto = this.depintRepository.findMaxIntUpto(depositRequestBean.getReceiptnum().trim(), depositRequestBean.getDepositor().trim(), depositRequestBean.getCoy().trim());
				logger.info("Uptodate :: {}", dtIntUpto);                                                      

				dtIntUpto = StringUtils.isNotBlank(dtIntUpto) ? dtIntUpto : null;
				logger.info("dtIntUpto :: {}", dtIntUpto);

				Long interestAmount = BigInteger.ZERO.longValue();
				Long interestAmountForStaff = BigInteger.ZERO.longValue();
				Long recoveryAmount = BigInteger.ZERO.longValue();
				Long tdsAmount = BigInteger.ZERO.longValue();
				Integer ddCharges = BigInteger.ZERO.intValue();
				Long brokerageCharges = BigInteger.ZERO.longValue();
				/* Principle amount breakup */
				if(CommonUtils.INSTANCE.checkIfDateInBetween(LocalDate.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), null, LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 6L, Boolean.TRUE) && Objects.isNull(dtIntUpto))
					narrationMap.put(DischargeBreakUp.PRINCIPLE_AMOUNT.toString(), "As premature discharge is within 6 months from the date of deposit, no interest has been paid.");
				else
					narrationMap.put(DischargeBreakUp.PRINCIPLE_AMOUNT.toString(), CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getDisdate()).compareTo(CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getMatdate())) < 0 ? "Premature Discharge of Deposit." : "Discharge of Deposit on Maturity.");
				
				/* Recovery amount calculation breakup */
				if(StringUtils.isNotBlank(dtIntUpto)) {
					if(isRecoverable(CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getMatdate()), CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getDisdate()))){
						ValueContainer<Long> totalAmountWithOgInt = new ValueContainer<Long>(BigInteger.ZERO.longValue());
						ValueContainer<Long> totalAmountWithPenaltyInt = new ValueContainer<Long>(BigInteger.ZERO.longValue());

						/* Calculate sum of interest already paid at original interest rate */
						queryToResultSetBuilder.stream().map(fromToDate -> {
							String intFrom = CommonUtils.INSTANCE.stringToDateTimeFormatterConverter(fromToDate.get(fromToDate.keySet().toArray()[0]).toString());
							String intUpto = CommonUtils.INSTANCE.stringToDateTimeFormatterConverter(fromToDate.get(fromToDate.keySet().toArray()[1]).toString());
							totalAmountWithOgInt.setValue(totalAmountWithOgInt.getValue() + InterestCalculationLogic.calculateInterest(Double.valueOf(depositRequestBean.getDepamount()), intFrom, intUpto, depositEntity.getDepIntrate(), null));
							return fromToDate;		
						}).collect(Collectors.toList());

						/* Check if discharge is before 6 months if yes and recover all the interest paid till date. */
						if(!CommonUtils.INSTANCE.checkIfDateInBetween(LocalDate.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), null, LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 6L, Boolean.TRUE))
							/* Calculate sum of interest already paid at penalty interest rate */
							queryToResultSetBuilder.stream().map(fromToDate -> {
								String intFrom = CommonUtils.INSTANCE.stringToDateTimeFormatterConverter(fromToDate.get(fromToDate.keySet().toArray()[0]).toString());
								String intUpto = CommonUtils.INSTANCE.stringToDateTimeFormatterConverter(fromToDate.get(fromToDate.keySet().toArray()[1]).toString());
								totalAmountWithPenaltyInt.setValue(totalAmountWithPenaltyInt.getValue() + InterestCalculationLogic.calculateInterest(Double.valueOf(depositRequestBean.getDepamount()), intFrom, intUpto, depositRequestBean.getIntrate(), null));
								return fromToDate;		
							}).collect(Collectors.toList());

						recoveryAmount = totalAmountWithPenaltyInt.getValue() - totalAmountWithOgInt.getValue();
						logger.info("Recovery Amount :: {}", recoveryAmount);

					}
				}else 
					dtIntUpto = depositRequestBean.getDepdate();  /* If no interest is paid until now then depdate is the date from which interest is to be paid */

				String tdsLimitAndRate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDS", "INSEC", LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				Double tdsLimit = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[0]);
				Double tdsRate = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);

				if(!CommonUtils.INSTANCE.checkIfDateInBetween(LocalDate.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), null, LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 6L, Boolean.TRUE)) {
					Double staffIntRate = BigInteger.ZERO.doubleValue();
					if(depositEntity.getDepStaffyn().equals("Y")) {
						staffIntRate = Double.valueOf(this.entityRepository.findByNumsEntityCk_EntClassAndEntityCk_EntId("FDINT", "FDINT").split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);
					}
					if(CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(depositRequestBean.getDisdate())) {
						Long interestUptoMarch = InterestCalculationLogic.calculateInterest(Double.valueOf(depositRequestBean.getDepamount()), CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue()), "31/03/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), depositRequestBean.getIntrate(), null);
						Long interestUptoMarchForStaff = InterestCalculationLogic.calculateInterestForStaff(Double.valueOf(depositRequestBean.getDepamount()), CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue()), "31/03/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), depositRequestBean.getIntrate(), depositEntity.getDepStaffyn(), staffIntRate);
						logger.info("Interest Amount For March:: {}", interestUptoMarch);

						Long tdsForMarch = calculateTdsForAmount("31/03/", Double.valueOf(interestUptoMarch + interestUptoMarchForStaff), depositRequestBean, depositRequestBean.getDepdate(), depositEntity, depositorEntity);
						String narrForMarch = "";
						if(tdsForMarch > 0 && depositorEntity.getDeptrTds() > tdsLimit)
							narrForMarch = "(Gross " + (interestUptoMarch + interestUptoMarchForStaff) + " - TDS " + tdsForMarch + ")";
						else
							tdsForMarch = BigInteger.ZERO.longValue();
						narrationMap.put(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("MARCH"), "Int @" + depositRequestBean.getIntrate() + "% on " + depositEntity.getDepDepamount().intValue()+ " for " + ((int) ChronoUnit.DAYS.between(LocalDate.parse(CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue()), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), LocalDate.parse("31/03/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) + BigInteger.ONE.intValue()) + " days." + narrForMarch);
						tdsForMarch = tdsForMarch * -1;

						Long interestForApril = InterestCalculationLogic.calculateInterest(Double.valueOf(depositRequestBean.getDepamount()), "01/04/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), depositRequestBean.getDisdate(), depositRequestBean.getIntrate(), null);
						Long interestUptoAprilForStaff = InterestCalculationLogic.calculateInterestForStaff(Double.valueOf(depositRequestBean.getDepamount()), CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue()), "31/03/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), depositRequestBean.getIntrate(), depositEntity.getDepStaffyn(), staffIntRate);
						logger.info("Interest Amount For April:: {}", interestForApril);

						Long tdsForApril = calculateTdsForAmount("01/04/", Double.valueOf(interestForApril + interestUptoAprilForStaff), depositRequestBean, depositRequestBean.getDepdate(), depositEntity, depositorEntity);
						String narrForApril = "";
						if(tdsForApril > 0 && tdsForApril > tdsLimit)
							narrForApril = "(Gross " + (interestForApril + interestUptoAprilForStaff)+ " - TDS " + tdsForApril + ")";
						else 
							tdsForApril = BigInteger.ZERO.longValue();
						narrationMap.put(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("APRIL"), "Int @" + depositRequestBean.getIntrate() + "% on " + depositEntity.getDepDepamount().intValue() + " for " + ((int) ChronoUnit.DAYS.between(LocalDate.parse("01/04/".concat(depositRequestBean.getDisdate().substring(depositRequestBean.getDisdate().length() - 4)), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) , LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) + BigInteger.ONE.intValue()) + " days." + narrForApril);
						tdsForApril = tdsForApril * -1;

						interestAmount = interestUptoMarch + interestUptoMarchForStaff + tdsForMarch + interestForApril + interestUptoAprilForStaff+ tdsForApril;
						logger.info("Final Interest Amount In april month:: {}", interestAmount);
						depositDischargeResponseBean.setInterestAmountForMarch(interestUptoMarch);// + tdsForMarch); removed Net calc interest and made it gross
						depositDischargeResponseBean.setInterestAmountForApril(interestForApril);
						depositDischargeResponseBean.setTdsAmountForMarch(tdsForMarch);
						depositDischargeResponseBean.setTdsAmountForApril(tdsForApril);
					}else {
						if(!dtIntUpto.equals(depositRequestBean.getDepdate()))
							dtIntUpto = CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue());

						interestAmount = InterestCalculationLogic.calculateInterest(Double.valueOf(depositRequestBean.getDepamount()), dtIntUpto, depositRequestBean.getDisdate(), depositRequestBean.getIntrate(), null);
						interestAmountForStaff = InterestCalculationLogic.calculateInterestForStaff(Double.valueOf(depositRequestBean.getDepamount()), CommonUtils.INSTANCE.addDaysInString(dtIntUpto, BigInteger.ONE.intValue()), depositRequestBean.getDisdate(), depositRequestBean.getIntrate(), depositEntity.getDepStaffyn(), staffIntRate);
						logger.info("Interest Amount  :: {}", interestAmount);

						if(!interestAmount.equals(BigInteger.ZERO.longValue())) {
							if(recoveryAmount.equals(BigInteger.ZERO.longValue()))   
								narrationMap.put(DischargeBreakUp.INTEREST_AMOUNT.toString(), "Int @" + depositRequestBean.getIntrate() + "% on " + depositEntity.getDepDepamount().intValue() + " for " + ((int) ChronoUnit.DAYS.between(LocalDate.parse(dtIntUpto, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) + BigInteger.ONE.intValue()) + " days.");
							else if(!recoveryAmount.equals(BigInteger.ZERO.longValue()))
								narrationMap.put(DischargeBreakUp.INTEREST_AMOUNT.toString(), "Int @" + depositRequestBean.getIntrate() + "% on " + depositEntity.getDepDepamount().intValue() + " for " + ((int) ChronoUnit.DAYS.between(LocalDate.parse(dtIntUpto, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) + BigInteger.ONE.intValue()) + " days Rs. "+ interestAmount + " Less Recovery Rs. " + Math.abs(recoveryAmount) + " = Net Amount Rs. " + (interestAmount + recoveryAmount)); // we are adding recovery amount because it is negative
							else if(CommonUtils.INSTANCE.checkIfDateInBetween(LocalDate.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), null, LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 6L, Boolean.TRUE))	
								narrationMap.put(DischargeBreakUp.INTEREST_AMOUNT.toString(), "As premature discharge is within 6 months from the date of deposit, interest paid earlier has been recovered");
						}
					}
				}

				/* Check if TDS applicable and calculate if yes */
				if(!depositDischargeRequestBean.getIsProvision())
					//As tds is on pre-mature and mature no need for this condition ->					if(CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getDisdate()).compareTo(CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getMatdate())) < 0)
					if(!CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(depositRequestBean.getDisdate()))
						if(depositorEntity.getDeptrTds15hyn().equals("N") && depositorEntity.getDeptrTds15gyn().equals("N")) {
							if(!CommonUtils.INSTANCE.checkIfDateInBetween(LocalDate.parse(depositRequestBean.getDepdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), null, LocalDate.parse(depositRequestBean.getDisdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), 6L, Boolean.TRUE))
								if((depositorEntity.getDeptrIntpaidytd() + interestAmount + interestAmountForStaff) > tdsLimit) {
									Double decimalTdsAmount;
									if(depositorEntity.getDeptrIntpaidytd() > tdsLimit)
										decimalTdsAmount = ((interestAmount + interestAmountForStaff + recoveryAmount) / 100D) * tdsRate;
									else
										decimalTdsAmount = ((depositorEntity.getDeptrIntpaidytd() + interestAmount + interestAmountForStaff + recoveryAmount) / 100D) * tdsRate;
									tdsAmount = Math.negateExact(Math.round(decimalTdsAmount));
									logger.info("TDS Amount :: {}", tdsAmount);	
									if(!tdsAmount.equals(BigInteger.ZERO.longValue())) {
										narrationMap.put(DischargeBreakUp.TDS_AMOUNT.toString(), "TDS @" + tdsRate + "% on Rs. " + (interestAmount + recoveryAmount)); // we are adding recovery amount because it is negative
										depositDischargeResponseBean.setTds(tdsAmount);
									}
								}
						}

				/* Check if Brokerage charges applicable and calculate if yes */
				if(isRecoverable(CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getMatdate()), CommonUtils.INSTANCE.stringToDateFormatter(depositRequestBean.getDisdate()))) {
					if(StringUtils.isNotBlank(depositEntity.getDepBroker())) {
						Integer brokeragePercentage = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("FDBRO", "FDBRO").intValue();
						Double brokerageRecoveryPercentage = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityNum2AndNum3("FDBRC", "0000", depositEntity.getDepDepmonths());
						Long previousBrokerageAmount = Math.round((depositEntity.getDepDepamount() * brokeragePercentage) / 100D);
						Long newBrokerageAmount = Math.round((depositEntity.getDepDepamount() * (brokeragePercentage - brokerageRecoveryPercentage)) / 100D);
						brokerageCharges = newBrokerageAmount - previousBrokerageAmount;
						logger.info("Recovery Amount :: {}", brokerageCharges);	

						if(!newBrokerageAmount.equals(BigInteger.ZERO.longValue())) 
							narrationMap.put(DischargeBreakUp.BROKERAGE_AMOUNT.toString(), "Due To Premature Discharge Brok. Rate Applied @" + (brokeragePercentage - brokerageRecoveryPercentage) +"%. Brok. Paid @" + brokeragePercentage + "% Rs." + previousBrokerageAmount + ". o Pay @" + (brokeragePercentage - brokerageRecoveryPercentage) + "% Rs." + newBrokerageAmount + " To Recover = (" + (previousBrokerageAmount - newBrokerageAmount) + ") = Rs." + (previousBrokerageAmount - newBrokerageAmount));
					}
				}

				/* Check if DD charges applicable and calculate if yes */
				if(depositRequestBean.getDdCharges() != null)
					ddCharges = depositRequestBean.getDdCharges();

				List<DepositDischargeBean> depositDischargeBeanList = generateReponseForDischarge(depositRequestBean.getReceiptnum(), depositRequestBean.getDisdate(), depositRequestBean.getDepamount(), interestAmount, recoveryAmount, tdsAmount, ddCharges, brokerageCharges, depositEntity.getDepDepmonths().intValue(), dtIntUpto, depositDischargeRequestBean.getIsGlEntry());
				for(DepositDischargeBean depositDischargeBean : depositDischargeBeanList) {
					depositDischargeBean.setIntUptoDate(dtIntUpto);
					depositDischargeBean.setNarrationMap(narrationMap);
					depositDischargeBean.setInterestAmountForMarch(depositDischargeResponseBean.getInterestAmountForMarch());
					depositDischargeBean.setInterestAmountForApril(depositDischargeResponseBean.getInterestAmountForApril());
					depositDischargeBean.setTdsAmountForMarch(depositDischargeResponseBean.getTdsAmountForMarch());
					depositDischargeBean.setTdsAmountForApril(depositDischargeResponseBean.getTdsAmountForApril());
					depositDischargeBean.setTds(tdsAmount.longValue());
					depositDischargeBean.setRecoveryAmount(recoveryAmount);
				}
				depositDischargeResponseBean.setDepositDischargeBeanList(depositDischargeBeanList);
				depositDischargeResponseBeanList.add(depositDischargeResponseBean);
				return depositRequestBean;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(depositDischargeResponseBeanList).build());//.stream().map(depositDischargeBean -> depositDischargeBean.getDepositDischargeBeanList().stream().sorted(Comparator.comparing(DepositDischargeBean :: getReceiptNumber)).collect(Collectors.toList()))
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please check your selection...").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> dischargeDeposit(DepositDischargeRequestBean depositDischargeRequestBean) {
		if(CollectionUtils.isNotEmpty(depositDischargeRequestBean.getDepositDischargeBeanList())) {
			List<ActrandBean> actrandBeanList = new ArrayList<>();
			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);

			String proprietor = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositDischargeRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());
			String partyCode = depositDischargeRequestBean.getCoy().trim().concat(depositDischargeRequestBean.getDepositor());

			Map<String, List<DepositDischargeBean>> depositDischargeReceiptToListMap = depositDischargeRequestBean.getDepositDischargeBeanList().stream().collect(Collectors.groupingBy(DepositDischargeBean::getBankCode));
			logger.info("DepositDischargeReceiptToListMap :: {}", depositDischargeReceiptToListMap);

			ValueContainer<String> transerStr = new ValueContainer<>();
			List<DepositDischargeBean> bankCodeAndTranserList = new ArrayList<>();
			ValueContainer<Integer> breakupNumberCounter = new ValueContainer<Integer>(BigInteger.ZERO.intValue());
			/* Inserting data into accounting tables -> ACTRANH, ACTRAND, OTUCHQ */
			depositDischargeReceiptToListMap.entrySet().stream().map(entrySet -> {
				transerStr.setValue(GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#P"));
				logger.info("TranserStr :: {}" , transerStr);

				DepositDischargeBean depositDischargeFirstElementBean = entrySet.getValue().get(BigInteger.ZERO.intValue());
				logger.info("DepositDischargeBean :: {}" , depositDischargeFirstElementBean);

				this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
						.transer(transerStr.getValue())
						.trantype(TranTypeEnum.PF.toString())
						.trandate(depositDischargeFirstElementBean.getChequeDate())						
						.ledgcode("GL")
						.partytype(CommonConstraints.INSTANCE.DEPOSITORS)
						.partycode(partyCode)
						.tranamt(Double.valueOf(entrySet.getValue().stream().collect(Collectors.summarizingInt(DepositDischargeBean::getAmount)).getSum()))
						.voudate(depositDischargeFirstElementBean.getChequeDate())
						.vounum(depositDischargeFirstElementBean.getCheque().toString())
						.bankcode(entrySet.getKey())
						.postedyn("Y")
						.proprietor(proprietor)
						.coy(depositDischargeRequestBean.getCoy().trim())
						.site(SiteFromDBEntity)
						.userid(depositDischargeRequestBean.getUserId())
						.reverseyn("N")
						.clearacyn("Y")
						.provyn("N")
						.build()}));


				Outchq outchqEntity = this.outchqRepository.findByOutchqCK_OutchqProprietorAndOutchqCK_OutchqCoyAndOutchqCK_OutchqBankAndOutchqCK_OutchqNum(proprietor, depositDischargeRequestBean.getCoy().trim(), entrySet.getKey(), depositDischargeFirstElementBean.getCheque().toString().trim());
				logger.info("outchqEntity :: {}" , outchqEntity);

				this.outchqRepository.save(FdPojoEntityMapper.updateOutchqEntityPojoMapper.apply(outchqEntity, OutchqRequestBean.builder()
						.proprietor(proprietor)
						.coy(depositDischargeRequestBean.getCoy().trim())
						.bank(entrySet.getKey())
						.series(StringUtils.isNotBlank(depositDischargeFirstElementBean.getCheque().toString()) ? depositDischargeFirstElementBean.getCheque().toString().substring(0, 4) : null)
						.num(depositDischargeFirstElementBean.getCheque().toString())
						.amount(Double.valueOf(entrySet.getValue().stream().collect(Collectors.summarizingInt(DepositDischargeBean::getAmount)).getSum()))
						.transer(transerStr.getValue())
						.partytype(CommonConstraints.INSTANCE.DEPOSITORS)
						.partycode(partyCode)
						.paymode(CommonConstraints.INSTANCE.banks)
						.payref(depositDischargeRequestBean.getDepositDischargeBeanList().get(depositDischargeRequestBean.getDepositDischargeBeanList().size() - BigInteger.ONE.intValue()).getReceiptNumber())
						.paydate(depositDischargeFirstElementBean.getChequeDate())
						.site(SiteFromDBEntity)
						.userid(depositDischargeRequestBean.getUserId().trim())
						.origsite(SiteFromDBEntity)						
						.build()));

				Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositDischargeRequestBean.getDepositor().trim(), depositDischargeRequestBean.getCoy().trim());
				logger.info("DEPOSITOR :: {}", depositorEntity);

				entrySet.getValue().stream().map(depositDischargeBean -> {
					Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositDischargeRequestBean.getDepositor().trim(), depositDischargeRequestBean.getCoy().trim(), depositDischargeBean.getReceiptNumber().trim());
					logger.info("DEPOSIT :: {}", depositEntity);
					
					//For April month entry as it requires splitting into 2 entries
					if(depositDischargeBean.getBreakupType().equalsIgnoreCase(DischargeBreakUp.INTEREST_AMOUNT.getValue()) && CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(depositDischargeBean.getDischargeDate())) {
						Map<String, String> acMajorAndNarrationForBreakupType = getAcMajorAndNarrationForBreakupType(depositDischargeRequestBean, depositDischargeBean, depositDischargeRequestBean.getIsProvision());
						String acMajorContra = "80000006";
						AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorContra, null, null, "Q", entrySet.getKey(), null, null);
						AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorContra, "PF");
						AccountingBean accoutingDataForTran;
						AccountingBean accoutingDataCashFlow;
						Double interestAmount = BigInteger.ZERO.doubleValue();
						Double tdsAmount = BigInteger.ZERO.doubleValue();
						String narration; //for first 40 char
						String narrative; //from 41st char till the end
						for(int i=1; i <= 4; i++){
							if(i <= 2) { 
								accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajorAndNarrationForBreakupType.get("acMajor".concat("MARCH")), null, null, "D", partyCode, null, null);
								accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajorAndNarrationForBreakupType.get("acMajor".concat("MARCH")), "PF");
								interestAmount = depositDischargeBean.getInterestAmountForMarch().doubleValue();
								tdsAmount = depositDischargeBean.getTdsAmountForMarch().doubleValue();
								narration = fetchFirstFortyCharFromNarrative(depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("MARCH"))); 
							}else { 
								accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajorAndNarrationForBreakupType.get("acMajor".concat("APRIL")), null, null, "D", partyCode, null, null);
								accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajorAndNarrationForBreakupType.get("acMajor".concat("APRIL")), "PF");
								interestAmount = depositDischargeBean.getInterestAmountForApril().doubleValue();
								tdsAmount = depositDischargeBean.getTdsAmountForApril().doubleValue();
								narration = fetchFirstFortyCharFromNarrative(depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("APRIL")));
							}
							breakupNumberCounter.setValue(breakupNumberCounter.getValue() + 1);
							if(i % 2 == 1) {
								ActrandBean actrandBean = ActrandBean.builder()
										.transer(transerStr.getValue())
										.bunum(breakupNumberCounter.getValue())
										.trantype("PF")
										.trandate(depositDischargeFirstElementBean.getChequeDate())
										.proprietor(proprietor)
										.coy(depositDischargeRequestBean.getCoy())
										.mintype(accoutingDataForTran.getMinType())
										.mincode(accoutingDataForTran.getMinCode())
										.partytype(accoutingDataForTran.getPartyType())
										.partycode(accoutingDataForTran.getPartyCode())
										.acmajor(accoutingDataForTran.getAcMajor())
										.project(accoutingDataForTran.getProject())
										.acminor(accoutingDataForTran.getAcminor())
										.vounum(depositDischargeBean.getCheque().toString())
										.voudate(depositDischargeFirstElementBean.getChequeDate())
										.tranamt(interestAmount + tdsAmount)
										.narrative(narration)
										.cfgroup(accoutingDataCashFlow.getCfGroup())
										.cfcode(accoutingDataCashFlow.getCfCode())
										.docdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.paymode("Q")
										.doctype("PF")
										.docpartype("D")
										.docparcode(partyCode)
										.docnum(depositDischargeBean.getReceiptNumber())
										/* X columns (Contra entry) */
										.xreftranser(transerStr.getValue())
										.xacmajor(acMajorContra)
										.xacminor(accoutingDataForContraTran.getAcminor())
										.xmintype(accoutingDataForContraTran.getMinType())
										.xpartycode(accoutingDataForContraTran.getPartyCode())
										.xproject(accoutingDataForContraTran.getProject())
										.xreftrandate(depositDischargeBean.getDischargeDate())
										.xpartytype(accoutingDataForContraTran.getPartyType())
										.xrefbunum(breakupNumberCounter.getValue()+1)
										.site(SiteFromDBEntity)
										.userid(depositDischargeRequestBean.getUserId())
										.today(LocalDateTime.now())
										.build();

								/* To set correct reff dates we are setting here */
								if(i <= 2) {
									actrandBean.setReffrom(CommonUtils.INSTANCE.addDaysInString(depositDischargeBean.getIntUptoDate(), 1));
									actrandBean.setRefupto("31/03/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)));
									narrative = depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("MARCH"));
								}else {
									actrandBean.setReffrom("01/04/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)));
									actrandBean.setRefupto(depositDischargeBean.getDischargeDate());
									narrative = depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("APRIL"));
								}
								actrandBeanList.add(actrandBean);
								List<ExnarrBean> exnarrBeanList = GenericExnarrLogic.generateExnarrBean(actrandBean, narrative);
								if(CollectionUtils.isNotEmpty(exnarrBeanList))
									this.exnarrRepository.saveAll(ExnarrMapper.addExnarrMapperList.apply(exnarrBeanList));
							} else { //for inserting actrand records for every month except for april
								ActrandBean actrandBean = ActrandBean.builder()
										.transer(transerStr.getValue())
										.bunum(breakupNumberCounter.getValue())
										.trantype("PF")
										.trandate(depositDischargeFirstElementBean.getChequeDate())
										.proprietor(proprietor)
										.coy(depositDischargeRequestBean.getCoy())
										.mintype(accoutingDataForContraTran.getMinType())
										.mincode(accoutingDataForContraTran.getMinCode())
										.partytype(accoutingDataForContraTran.getPartyType())
										.partycode(accoutingDataForContraTran.getPartyCode())
										.project(accoutingDataForContraTran.getProject())
										.acminor(accoutingDataForContraTran.getAcminor())
										.acmajor(acMajorContra)
										.vounum(depositDischargeBean.getCheque().toString())
										.voudate(depositDischargeFirstElementBean.getChequeDate())
										.tranamt(Double.valueOf(Math.negateExact(interestAmount.intValue() + tdsAmount.intValue())))
										.narrative(narration)
										.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
										.cfcode(accoutingDataCashFlowForContra.getCfCode())
										.docdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.paymode("Q")
										.doctype("PF")
										.docpartype("D")
										.docparcode(partyCode)
										.docnum(depositDischargeBean.getReceiptNumber())
										/* X columns (Contra entry) */
										.xreftranser(transerStr.getValue())
										.xacminor(accoutingDataForTran.getAcminor())
										.xacmajor(accoutingDataForTran.getAcMajor())
										.xmintype(accoutingDataForTran.getMinType())
										.xpartycode(accoutingDataForTran.getPartyCode())
										.xproject(accoutingDataForTran.getProject())
										.xreftrandate(depositDischargeBean.getDischargeDate())
										.xpartytype(accoutingDataForTran.getPartyType())
										.xrefbunum(breakupNumberCounter.getValue()-1)
										.site(SiteFromDBEntity)
										.userid(depositDischargeRequestBean.getUserId())
										.today(LocalDateTime.now())
										.build();

								/* To set correct reff dates we are setting here */
								if(i <= 2) {
									actrandBean.setReffrom(CommonUtils.INSTANCE.addDaysInString(depositDischargeBean.getIntUptoDate(), 1));
									actrandBean.setRefupto("31/03/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)));
									narrative = depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("MARCH"));
								}else {
									actrandBean.setReffrom("01/04/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)));
									actrandBean.setRefupto(depositDischargeBean.getDischargeDate());
									narrative = depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("APRIL"));
								}
								actrandBeanList.add(actrandBean);
								List<ExnarrBean> exnarrBeanList = GenericExnarrLogic.generateExnarrBean(actrandBean, narrative);
								if(CollectionUtils.isNotEmpty(exnarrBeanList))
									this.exnarrRepository.saveAll(ExnarrMapper.addExnarrMapperList.apply(exnarrBeanList));
							}
							if(i==2) { /* To add in depint after inserting March month record  */
								DepintBean depintBean = DepintBean.builder()
										.depositor(depositorEntity.getDepositorCK().getDeptrDepositor())
										.receiptnum(depositDischargeBean.getReceiptNumber())
										.proprietor(proprietor)
										.coy(depositDischargeRequestBean.getCoy())
										.interest(interestAmount)
										.tds(Math.abs(tdsAmount))
										.bankcode(entrySet.getKey())
										.chqnum(StringUtils.isNotBlank(depositDischargeBean.getCheque()) ? depositDischargeBean.getCheque().trim() : null)
										.fromdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
										.todate(depositEntity.getDepMatdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.intfrom(CommonUtils.INSTANCE.addDaysInString(depositDischargeBean.getIntUptoDate(), 1))
										.intupto("31/03/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)))
										.transer(transerStr.getValue())
										.site(SiteFromDBEntity)
										.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.userid(depositDischargeRequestBean.getUserId())
										.build();

								depositorEntity.setDeptrAccint(Objects.nonNull(depositorEntity.getDeptrAccint()) ? depositorEntity.getDeptrAccint() : BigInteger.ZERO.doubleValue());
								depositorEntity.setDeptrTaxpaidytd(Objects.nonNull(depositorEntity.getDeptrTaxpaidytd()) ? depositorEntity.getDeptrTaxpaidytd() : BigInteger.ZERO.doubleValue());
								depositorEntity.setDeptrTds(Objects.nonNull(depositorEntity.getDeptrTds()) ? depositorEntity.getDeptrTds() : BigInteger.ZERO.doubleValue());
								depositorEntity.setDeptrIntpaidytd(Objects.nonNull(depositorEntity.getDeptrIntpaidytd()) ? depositorEntity.getDeptrIntpaidytd() : BigInteger.ZERO.doubleValue());

								DepositorRequestBean updateDepositor = DepositorRequestBean.builder()
										.accint(Objects.nonNull(interestAmount) ? depositorEntity.getDeptrAccint() + interestAmount : depositorEntity.getDeptrAccint())
										.taxpaidytd(Objects.nonNull(tdsAmount) ? depositorEntity.getDeptrTaxpaidytd() + Math.abs(tdsAmount) : depositorEntity.getDeptrTaxpaidytd())
										.tds(Objects.nonNull(tdsAmount) ? depositorEntity.getDeptrTds() + Math.abs(tdsAmount) : depositorEntity.getDeptrTaxpaidytd())
										.intpayedytd(Objects.nonNull(interestAmount) ? depositorEntity.getDeptrIntpaidytd() + interestAmount : depositorEntity.getDeptrIntpaidytd())
										.site(SiteFromDBEntity)
										.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.userid(depositDischargeRequestBean.getUserId())
										.build();

								depositEntity.setDepAccint(Objects.nonNull(depositEntity.getDepAccint()) ? depositEntity.getDepAccint() : BigInteger.ZERO.doubleValue());
								depositEntity.setDepTaxpaidytd(Objects.nonNull(depositEntity.getDepTaxpaidytd()) ? depositEntity.getDepTaxpaidytd() : BigInteger.ZERO.doubleValue());
								depositEntity.setDepTds(Objects.nonNull(depositEntity.getDepTds()) ? depositEntity.getDepTds() : BigInteger.ZERO.doubleValue());
								depositEntity.setDepIntpaidytd(Objects.nonNull(depositEntity.getDepIntpaidytd()) ? depositEntity.getDepIntpaidytd() : BigInteger.ZERO.doubleValue());

								DepositRequestBean updateDeposit = DepositRequestBean.builder()
										.accint(Objects.nonNull(interestAmount) ? depositEntity.getDepAccint() + interestAmount : depositEntity.getDepAccint())
										.taxpaidytd(Objects.nonNull(tdsAmount) ? depositEntity.getDepTaxpaidytd() + Math.abs(tdsAmount) : depositEntity.getDepTaxpaidytd())
										.tds(Objects.nonNull(tdsAmount) ? depositEntity.getDepTds() + Math.abs(tdsAmount) : depositEntity.getDepTds())
										.intpaidytd(Objects.nonNull(interestAmount) ? depositEntity.getDepIntpaidytd() + interestAmount : depositEntity.getDepIntpaidytd())
										.site(SiteFromDBEntity)
										.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.userid(depositDischargeRequestBean.getUserId())
										.build();
								insertIntoDepintAndUpdateDepTables("31/03/", depintBean, depositEntity, updateDeposit, depositorEntity, updateDepositor, SiteFromDBEntity);

							}else if(i == 4) { /* To add in depint after inserting april month record */
								DepintBean depintBean = DepintBean.builder()
										.depositor(depositorEntity.getDepositorCK().getDeptrDepositor())
										.receiptnum(depositDischargeBean.getReceiptNumber())
										.proprietor(proprietor)
										.coy(depositDischargeRequestBean.getCoy())
										.interest(interestAmount)
										.tds(Math.abs(tdsAmount))
										.bankcode(entrySet.getKey())
										.chqnum(StringUtils.isNotBlank(depositDischargeBean.getCheque()) ? depositDischargeBean.getCheque().trim() : null)
										.transer(transerStr.getValue())
										.site(SiteFromDBEntity)
										.fromdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
										.todate(depositEntity.getDepMatdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.intfrom("01/04/".concat(depositDischargeBean.getDischargeDate().substring(depositDischargeBean.getDischargeDate().length() - 4)))
										.intupto(depositDischargeBean.getDischargeDate())
										.userid(depositDischargeRequestBean.getUserId())
										.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
										.build();
								insertIntoDepintAndUpdateDepTables("01/04/", depintBean, null, null, null, null, SiteFromDBEntity);
							}

						}
					}else {
						if(!depositDischargeBean.getBreakupType().equalsIgnoreCase(DischargeBreakUp.RECOVERY_AMOUNT.getValue())){
							if(depositDischargeBean.getBreakupType().equalsIgnoreCase(DischargeBreakUp.INTEREST_AMOUNT.getValue()))
								depositDischargeBean.setAmount(depositDischargeBean.getAmount().intValue() + depositDischargeBean.getRecoveryAmount().intValue());
							String projectCode = null;
							Map<String, String> acMajorAndNarrationForBreakupType = getAcMajorAndNarrationForBreakupType(depositDischargeRequestBean, depositDischargeBean, depositDischargeRequestBean.getIsProvision());
							String acMajorContra = "80000006";
							if(acMajorAndNarrationForBreakupType.get("acMajor").equals("40709005")) // check if bank charges are there then it project code should be GL
								projectCode = "GL";
							AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajorAndNarrationForBreakupType.get("acMajor"), null, null, "D", partyCode, projectCode, null);
							AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acMajorContra, null, null, "Q", entrySet.getKey(), projectCode, null);
							AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajorAndNarrationForBreakupType.get("acMajor"), TranTypeEnum.PF.toString());
							AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acMajorContra, TranTypeEnum.PF.toString());
							String reffrom = null;
							if(StringUtils.isNotBlank(depositDischargeBean.getIntUptoDate()))
								reffrom = depositDischargeBean.getIntUptoDate();
							else
								reffrom = depositEntity.getDepDepdate().toLocalDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
							for(int i=1; i <= 2; i++){
								breakupNumberCounter.setValue(breakupNumberCounter.getValue() + 1);
								if(i % 2 == 1) {
									ActrandBean actrandBean = ActrandBean.builder()
											.transer(transerStr.getValue())
											.bunum(breakupNumberCounter.getValue())
											.trantype("PF")
											.trandate(depositDischargeFirstElementBean.getChequeDate())
											.proprietor(proprietor)
											.coy(depositDischargeRequestBean.getCoy())
											.mintype(accoutingDataForTran.getMinType())
											.mincode(accoutingDataForTran.getMinCode())
											.partytype(accoutingDataForTran.getPartyType())
											.partycode(accoutingDataForTran.getPartyCode())
											.acmajor(acMajorAndNarrationForBreakupType.get("acMajor"))
											.project(accoutingDataForTran.getProject())
											.acminor(accoutingDataForTran.getAcminor())
											.vounum(depositDischargeBean.getCheque().toString())
											.voudate(depositDischargeFirstElementBean.getChequeDate())
											.tranamt(depositDischargeBean.getAmount().doubleValue())
											.reffrom(reffrom)
											.refupto(depositDischargeBean.getDischargeDate())
											.narrative(fetchFirstFortyCharFromNarrative(acMajorAndNarrationForBreakupType.get("narrative")))
											.cfgroup(accoutingDataCashFlow.getCfGroup())
											.cfcode(accoutingDataCashFlow.getCfCode())
											.docdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
											.paymode("Q")
											.doctype("PF")
											.docpartype("D")
											.docparcode(partyCode)
											.docnum(depositDischargeBean.getReceiptNumber())
											/* X columns (Contra entry) */
											.xreftranser(transerStr.getValue())
											.xacmajor(acMajorContra)
											.xacminor(accoutingDataForContraTran.getAcminor())
											.xmintype(accoutingDataForContraTran.getMinType())
											.xpartycode(accoutingDataForContraTran.getPartyCode())
											.xproject(accoutingDataForContraTran.getProject())
											.xreftrandate(depositDischargeBean.getDischargeDate())
											.xpartytype(accoutingDataForContraTran.getPartyType())
											.xrefbunum(breakupNumberCounter.getValue()+1)
											.site(SiteFromDBEntity)
											.userid(depositDischargeRequestBean.getUserId())
											.today(LocalDateTime.now())
											.build();

									actrandBeanList.add(actrandBean);
									List<ExnarrBean> exnarrBeanList = GenericExnarrLogic.generateExnarrBean(actrandBean, acMajorAndNarrationForBreakupType.get("narrative"));
									if(CollectionUtils.isNotEmpty(exnarrBeanList))
										this.exnarrRepository.saveAll(ExnarrMapper.addExnarrMapperList.apply(exnarrBeanList));
								} else {
									ActrandBean actrandBean = ActrandBean.builder()
											.transer(transerStr.getValue())
											.bunum(breakupNumberCounter.getValue())
											.trantype("PF")
											.trandate(depositDischargeFirstElementBean.getChequeDate())
											.proprietor(proprietor)
											.coy(depositDischargeRequestBean.getCoy())
											.mintype(accoutingDataForContraTran.getMinType())
											.mincode(accoutingDataForContraTran.getMinCode())
											.partytype(accoutingDataForContraTran.getPartyType())
											.partycode(accoutingDataForContraTran.getPartyCode())
											.project(accoutingDataForContraTran.getProject())
											.acminor(accoutingDataForContraTran.getAcminor())
											.acmajor(acMajorContra)
											.vounum(depositDischargeBean.getCheque().toString())
											.voudate(depositDischargeFirstElementBean.getChequeDate())
											.tranamt(Double.valueOf(Math.negateExact(depositDischargeBean.getAmount())))
											.reffrom(reffrom)
											.refupto(depositDischargeBean.getDischargeDate())
											.narrative(fetchFirstFortyCharFromNarrative(acMajorAndNarrationForBreakupType.get("narrative")))
											.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
											.cfcode(accoutingDataCashFlowForContra.getCfCode())
											.docdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
											.paymode("Q")
											.doctype("PF")
											.docpartype("D")
											.docparcode(partyCode)
											.docnum(depositDischargeBean.getReceiptNumber())
											/* X columns (Contra entry) */
											.xreftranser(transerStr.getValue())
											.xacminor(accoutingDataForTran.getAcminor())
											.xacmajor(acMajorAndNarrationForBreakupType.get("acMajor"))
											.xmintype(accoutingDataForTran.getMinType())
											.xpartycode(accoutingDataForTran.getPartyCode())
											.xproject(accoutingDataForTran.getProject())
											.xreftrandate(depositDischargeBean.getDischargeDate())
											.xpartytype(accoutingDataForTran.getPartyType())
											.xrefbunum(breakupNumberCounter.getValue()-1)
											.site(SiteFromDBEntity)
											.userid(depositDischargeRequestBean.getUserId())
											.today(LocalDateTime.now())
											.build();

									actrandBeanList.add(actrandBean);
									List<ExnarrBean> exnarrBeanList = GenericExnarrLogic.generateExnarrBean(actrandBean, acMajorAndNarrationForBreakupType.get("narrative"));
									if(CollectionUtils.isNotEmpty(exnarrBeanList))
										this.exnarrRepository.saveAll(ExnarrMapper.addExnarrMapperList.apply(exnarrBeanList));
								}
							}
						}
						if(depositDischargeBean.getBreakupType().equals(CommonConstraints.INSTANCE.INTEREST_AMOUNT)){
							DepintBean depintBean = DepintBean.builder()
									.depositor(depositorEntity.getDepositorCK().getDeptrDepositor())
									.receiptnum(depositDischargeBean.getReceiptNumber())
									.proprietor(proprietor)
									.coy(depositDischargeRequestBean.getCoy())
									.interest(depositDischargeBean.getAmount().doubleValue() + depositDischargeBean.getRecoveryAmount()) // because recovery amount is negative to we are using + sign
									.fromdate(depositEntity.getDepDepdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
									.todate(depositEntity.getDepMatdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.tds(Objects.nonNull(depositDischargeBean.getTds()) ? Math.abs(depositDischargeBean.getTds().doubleValue()) : BigInteger.ZERO.doubleValue())
									.bankcode(entrySet.getKey())
									.chqnum(StringUtils.isNotBlank(depositDischargeBean.getCheque()) ? depositDischargeBean.getCheque().trim() : null)
									.intfrom(depositDischargeBean.getIntUptoDate())
									.intupto(depositDischargeBean.getDischargeDate())
									.transer(transerStr.getValue())
									.site(SiteFromDBEntity)
									.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.userid(depositDischargeRequestBean.getUserId())
									.build();

							depositorEntity.setDeptrAccint(Objects.nonNull(depositorEntity.getDeptrAccint()) ? depositorEntity.getDeptrAccint() : BigInteger.ZERO.doubleValue());
							depositorEntity.setDeptrTaxpaidytd(Objects.nonNull(depositorEntity.getDeptrTaxpaidytd()) ? depositorEntity.getDeptrTaxpaidytd() : BigInteger.ZERO.doubleValue());
							depositorEntity.setDeptrTds(Objects.nonNull(depositorEntity.getDeptrTds()) ? depositorEntity.getDeptrTds() : BigInteger.ZERO.doubleValue());
							depositorEntity.setDeptrIntpaidytd(Objects.nonNull(depositorEntity.getDeptrIntpaidytd()) ? depositorEntity.getDeptrIntpaidytd() : BigInteger.ZERO.doubleValue());

							DepositorRequestBean updateDepositor = DepositorRequestBean.builder()
									.accint(Objects.nonNull(depositDischargeBean.getAmount()) ? depositorEntity.getDeptrAccint() + (depositDischargeBean.getAmount().doubleValue() - depositDischargeBean.getRecoveryAmount()) : depositorEntity.getDeptrAccint())
									.taxpaidytd(Objects.nonNull(depositDischargeBean.getTds()) ? depositorEntity.getDeptrTaxpaidytd() + Math.abs(depositDischargeBean.getTds()) : depositorEntity.getDeptrTaxpaidytd())
									.tds(Objects.nonNull(depositDischargeBean.getTds()) ? depositorEntity.getDeptrTds() + Math.abs(depositDischargeBean.getTds()) : depositorEntity.getDeptrTaxpaidytd())
									.intpayedytd(Objects.nonNull(depositDischargeBean.getAmount()) ? depositorEntity.getDeptrIntpaidytd() + (depositDischargeBean.getAmount().doubleValue() - depositDischargeBean.getRecoveryAmount()) : depositorEntity.getDeptrIntpaidytd())
									.site(SiteFromDBEntity)
									.userid(depositDischargeRequestBean.getUserId())
									.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.build();

							depositEntity.setDepAccint(Objects.nonNull(depositEntity.getDepAccint()) ? depositEntity.getDepAccint() : BigInteger.ZERO.doubleValue());
							depositEntity.setDepTaxpaidytd(Objects.nonNull(depositEntity.getDepTaxpaidytd()) ? depositEntity.getDepTaxpaidytd() : BigInteger.ZERO.doubleValue());
							depositEntity.setDepTds(Objects.nonNull(depositEntity.getDepTds()) ? depositEntity.getDepTds() : BigInteger.ZERO.doubleValue());
							depositEntity.setDepIntpaidytd(Objects.nonNull(depositEntity.getDepIntpaidytd()) ? depositEntity.getDepIntpaidytd() : BigInteger.ZERO.doubleValue());

							DepositRequestBean updateDeposit = DepositRequestBean.builder()
									.accint(Objects.nonNull(depositDischargeBean.getAmount()) ? depositEntity.getDepAccint() + (depositDischargeBean.getAmount().doubleValue() - depositDischargeBean.getRecoveryAmount()) : depositEntity.getDepAccint())
									.taxpaidytd(Objects.nonNull(depositDischargeBean.getTds()) ? depositEntity.getDepTaxpaidytd() + Math.abs(depositDischargeBean.getTds()) : depositorEntity.getDeptrTaxpaidytd())
									.tds(Objects.nonNull(depositDischargeBean.getTds()) ? depositEntity.getDepTds() + Math.abs(depositDischargeBean.getTds()) : depositEntity.getDepTds())
									.intpaidytd(Objects.nonNull(depositDischargeBean.getAmount()) ? depositEntity.getDepIntpaidytd() + (depositDischargeBean.getAmount().doubleValue() - depositDischargeBean.getRecoveryAmount()) : depositEntity.getDepIntpaidytd())
									.site(SiteFromDBEntity)
									.userid(depositDischargeRequestBean.getUserId())
									.today(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.build();
							insertIntoDepintAndUpdateDepTables(CommonConstraints.INSTANCE.BLANK_STRING, depintBean, depositEntity, updateDeposit, depositorEntity, updateDepositor, SiteFromDBEntity);
						}
					}

					return depositDischargeBean;
				}).collect(Collectors.toList());
				bankCodeAndTranserList.add(DepositDischargeBean.builder().bankCode(entrySet.getKey()).transer(transerStr.getValue()).build());
				return entrySet;
			}).collect(Collectors.toList());
			this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
			/* Set Discharge date for receipts */
			if(!depositDischargeRequestBean.getIsGlEntry()) {
				List<DepositDischargeBean> uniqueReceiptList = depositDischargeRequestBean.getDepositDischargeBeanList().stream()
						.collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(DepositDischargeBean::getReceiptNumber))),
								ArrayList::new)); //SHOULD ONLY RETURN COUNT OF ROWS
				uniqueReceiptList.stream().map(dischargeReceipts -> {
					this.depositRepository.updateMaturedRecieptDisDate(LocalDate.parse(dischargeReceipts.getDischargeDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay(), depositDischargeRequestBean.getDepositor().trim(), dischargeReceipts.getReceiptNumber().trim(), LocalDateTime.now(), SiteFromDBEntity, depositDischargeRequestBean.getUserId());
					return dischargeReceipts;
				}).collect(Collectors.toList());	
			}
			
			GenericAuditContextHolder.getContext().setTransactionNo(String.join(",", bankCodeAndTranserList.stream().map(data -> data.getTranser()).collect(Collectors.toList())));
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Please note Transer nos. "+bankCodeAndTranserList.stream().map(data -> data.getTranser()).collect(Collectors.toList())).data(bankCodeAndTranserList).build());
		}else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("ReqestBody cannot be null.").build());		
	}

	private String acMajorForDurationOfDeposit(Integer duration) {
		switch(duration) {
		case 3 :      /* Fixed Deposits Received for three months */
			return "11326052";
		case 6 :      /* Fixed Deposits Received for six months */
			return "11326011";
		case 12 :     /* Fixed Deposits Received for twelve months */
			return "11326024";
		case 18 :     /* Fixed Deposits Received for eighteen months */
			return "11326078";
		case 24 :     /* Fixed Deposits Received for twenty four months */
			return "11326037";
		case 30 :    /* Fixed Deposits Received for thirty months */	
			return "11326081";
		case 36 :     /* Fixed Deposits Received for thirty six months */
			return "11326040";
		case 48 :     /* Fixed Deposits Received for forty eight months */
			return "11326065";
		default : return "11326024";
		}
	}

	private Map<String, String> getAcMajorAndNarrationForBreakupType(DepositDischargeRequestBean depositDischargeRequestBean, DepositDischargeBean depositDischargeBean, Boolean isProvisionEntry) {
		Map<String, String> acMajorAndNarrationMap = new HashMap<>();
		switch(DischargeBreakUp.getValue(depositDischargeBean.getBreakupType())) {
		case PRINCIPLE_AMOUNT : 
			acMajorAndNarrationMap.put("acMajor", acMajorForDurationOfDeposit(depositDischargeBean.getDepmonths()));
			acMajorAndNarrationMap.put("narrative", depositDischargeBean.getNarrationMap().get(DischargeBreakUp.PRINCIPLE_AMOUNT.toString()));
			break;
		case INTEREST_AMOUNT : 
			if(CommonUtils.INSTANCE.isDateInBetweenIncludingEndPoints(depositDischargeBean.getDischargeDate())) {
				acMajorAndNarrationMap.put("acMajor".concat("MARCH"), "11401764");
				depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("MARCH"));

				acMajorAndNarrationMap.put("acMajor".concat("APRIL"), "40704004");
				depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString().concat("APRIL"));
			}else {
				acMajorAndNarrationMap.put("acMajor", isProvisionEntry ? "11401764" : "40704004");
				acMajorAndNarrationMap.put("narrative", depositDischargeBean.getNarrationMap().get(DischargeBreakUp.INTEREST_AMOUNT.toString()));
			}
			break;
		case TDS_AMOUNT : 
			if(!isProvisionEntry) {
				acMajorAndNarrationMap.put("acMajor", "11401831");
				acMajorAndNarrationMap.put("narrative", depositDischargeBean.getNarrationMap().get(DischargeBreakUp.TDS_AMOUNT.toString()));
			}
			break;
		case RECOVERY_AMOUNT : 
			acMajorAndNarrationMap.put("acMajor", isProvisionEntry ? "11401764" : "40704004");
			acMajorAndNarrationMap.put("narrative", depositDischargeBean.getNarrationMap().get(DischargeBreakUp.RECOVERY_AMOUNT.toString()));
			break;
		case BROKERAGE_AMOUNT : {
			acMajorAndNarrationMap.put("acMajor", isProvisionEntry ? "11401802" : "40804000");
			acMajorAndNarrationMap.put("narrative", depositDischargeBean.getNarrationMap().get(DischargeBreakUp.BROKERAGE_AMOUNT.toString()));
			break;
		}
		case DD_CHARGES : 
			acMajorAndNarrationMap.put("acMajor", "40709005");
			acMajorAndNarrationMap.put("narrative", "D.D. Charges");
			break;
		default : 
			acMajorAndNarrationMap.put("acMajor", CommonConstraints.INSTANCE.BLANK_STRING);
			acMajorAndNarrationMap.put("narrative", CommonConstraints.INSTANCE.BLANK_STRING);
		}
		return acMajorAndNarrationMap;
	}

	private List<DepositDischargeBean> generateReponseForDischarge(String receiptnum, String disdate, Float depamount,
			Long interestAmount, Long recoveryAmount, Long tdsAmount, Integer ddCharges, Long brokerageCharges, Integer depmonths, String dtIntUpto, Boolean isGlEntry) {
		List<DepositDischargeBean> finalDepositDischargeBean = new ArrayList<DepositDischargeBean>(); 

		/* Builder for Principle Amount */
		if(Objects.nonNull(depamount) && !depamount.equals(BigInteger.ZERO.floatValue()) && !isGlEntry)
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.PRINCIPLE_AMOUNT)
					.amount(depamount.intValue())
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());	

		/* Builder for Interest Amount */
		if(Objects.nonNull(interestAmount) && !interestAmount.equals(BigInteger.ZERO.longValue()))
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.INTEREST_AMOUNT)
					.amount(interestAmount.intValue())
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());	

		/* Builder for Recovery Amount */
		if(Objects.nonNull(recoveryAmount) && !recoveryAmount.equals(BigInteger.ZERO.longValue()))
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.RECOVERY_AMOUNT)
					.amount(recoveryAmount.intValue())
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());	

		/* Builder for TDS Amount */
		if(Objects.nonNull(tdsAmount) && !tdsAmount.equals(BigInteger.ZERO.longValue()))
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.TDS_AMOUNT)
					.amount(tdsAmount.intValue())
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());

		/* Builder for DD Charges */
		if(Objects.nonNull(ddCharges) && !ddCharges.equals(BigInteger.ZERO.intValue()))
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.DD_CHARGES)
					.amount(ddCharges * -1)
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());

		/* Builder for Brokerage Charges */
		if(Objects.nonNull(brokerageCharges) && !brokerageCharges.equals(BigInteger.ZERO.longValue()))
			finalDepositDischargeBean.add(DepositDischargeBean.builder()
					.receiptNumber(receiptnum)
					.breakupType(CommonConstraints.INSTANCE.BROKERAGE_AMOUNT)
					.amount(brokerageCharges.intValue())
					.dischargeDate(disdate)
					.depositAmount(depamount.intValue())
					.chequeDate(disdate)
					.depmonths(depmonths)
					.interestUptoDate(dtIntUpto)
					.build());

		return finalDepositDischargeBean;
	}

	private Boolean isRecoverable(Date maturityDate, Date dischargeDate) {
		return dischargeDate.compareTo(maturityDate) < 0 ? Boolean.TRUE : Boolean.FALSE;
	}

	private String fetchFirstFortyCharFromNarrative(String narrative) {
		return narrative.length() > 40 ? narrative.substring(0, 40) : narrative;
	}

	private Long calculateTdsForAmount(String date, Double interestAmount, DepositRequestBean depositRequestBean, String depdate, Deposit deposit, Depositor depositorEntity) {
		Double currentTds = BigInteger.ZERO.doubleValue();
		if(depositorEntity.getDeptrTds15hyn().equals("N") && depositorEntity.getDeptrTds15gyn().equals("N")) {
			String tdsLimitAndRate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDS", "INSEC", LocalDate.parse(depdate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
			Double tdsLimit = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[0]);
			Double tdsRate = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);

			Double interestPaidYtd = BigInteger.ZERO.doubleValue();
			Double taxPaidYtd = BigInteger.ZERO.doubleValue();
			if(date.equals("31/03/")) {
				Double interestPaidYtdFromDb = this.depositorRepository.findSumOfIntPaidByCoyAndDepositor(depositRequestBean.getCoy().trim(), depositRequestBean.getDepositor().trim());
				if(Objects.nonNull(interestPaidYtdFromDb))
					interestPaidYtd = interestPaidYtdFromDb;

				Double taxPaidYtdFromDb = this.depositorRepository.findSumOfTaxPaidByCoyAndDepositor(depositRequestBean.getCoy().trim(), depositRequestBean.getDepositor().trim());
				if(Objects.nonNull(taxPaidYtdFromDb))
					taxPaidYtd = taxPaidYtdFromDb;
			}else {
				Query sumInterestQuery = entityManager.createQuery("Select SUM(e.dinInterest) FROM Depint e WHERE e.depintCK.dinCoy = ?1 and e.depintCK.dinDepositor = ?2 and e.depintCK.dinIntfrom >= ?3 and e.depintCK.dinIntupto <= ?4 "); // 
				sumInterestQuery.setParameter(1, depositRequestBean.getCoy());
				sumInterestQuery.setParameter(2, depositRequestBean.getDepositor());
				sumInterestQuery.setParameter(3, LocalDate.parse(depdate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				sumInterestQuery.setParameter(4, LocalDate.parse("31/03/".concat(String.valueOf(Integer.valueOf(depdate.substring(depdate.length() - 4)) + 1)), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				interestPaidYtd = (Double) sumInterestQuery.getSingleResult();

				//Sum of tax
				Query sumTdsQuery = entityManager.createQuery("Select SUM(e.dinTds) FROM Depint e WHERE e.depintCK.dinCoy = ?1 and e.depintCK.dinDepositor = ?2 and e.depintCK.dinIntfrom >= ?3 and e.depintCK.dinIntupto <= ?4 "); // 
				sumTdsQuery.setParameter(1, depositRequestBean.getCoy());
				sumTdsQuery.setParameter(2, depositRequestBean.getDepositor());
				sumTdsQuery.setParameter(3, LocalDate.parse(depdate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				sumTdsQuery.setParameter(4, LocalDate.parse("31/03/".concat(String.valueOf(Integer.valueOf(depdate.substring(depdate.length() - 4)) + 1)), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				taxPaidYtd = (Double) sumTdsQuery.getSingleResult();
			}
			interestPaidYtd += interestAmount;
			if(interestPaidYtd > tdsLimit) {
				currentTds = (interestPaidYtd * tdsRate) / 100;
				currentTds = currentTds - taxPaidYtd;
			}
			return Math.round(currentTds);
		}
		return currentTds.longValue();
	}

	private Boolean insertIntoDepintAndUpdateDepTables(String uptoDate, DepintBean depintBean, Deposit deposit, DepositRequestBean depositRequestBean, Depositor depositorEntity, DepositorRequestBean depositorRequestBean, String site) {
		this.depintRepository.save(FdPojoEntityMapper.addDepintEntityPojoMapper.apply(new Object[] { depintBean, site }));
		if(uptoDate.equals("31/03/") || uptoDate.equals(CommonConstraints.INSTANCE.BLANK_STRING)) {
			this.depositRepository.save(FdPojoEntityMapper.updateDepositPojoEntityMapper.apply(deposit, depositRequestBean));
			this.depositorRepository.save(FdPojoEntityMapper.updateDepositorPojoEntityMapping.apply(depositorEntity, depositorRequestBean));
			return true;
		}
		return false;
	}

	@Override
	public ResponseEntity<?> transferDeposit(DepositTransferRequestBean depositTransferRequestBean) {
		ValueContainer<String> newDepositorId = new ValueContainer<>();
		String newDepIdMessage = "";
		if(StringUtils.isNotBlank(depositTransferRequestBean.getDepositorRequestBean().getDepositorId())) {
			this.depositorService.updateDepositor(depositTransferRequestBean.getDepositorRequestBean());
			newDepositorId.setValue(depositTransferRequestBean.getDepositorRequestBean().getDepositorId().trim());
		}
		else {
			ResponseEntity<ServiceResponseBean> serviceResponseBean = this.depositorService.addDepositor(depositTransferRequestBean.getDepositorRequestBean());			
			newDepositorId.setValue(serviceResponseBean.getBody().getData().toString());	
			newDepIdMessage = " ,Dep ID: " + serviceResponseBean.getBody().getData().toString();
			depositTransferRequestBean.getDepositRequestBean().setDepositor(newDepositorId.getValue());
		}

		String transferReceiptNumber = GenericCounterIncrementLogicUtil.generateTranNo("FDRTF", depositTransferRequestBean.getDepositRequestBean().getCoy().trim());
		logger.info("TransferReceiptNumber :: {}" , transferReceiptNumber);

		String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		logger.info("Entity :: {}" , SiteFromDBEntity);

		String transerStr = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#J");
		logger.info("TranserStr :: {}" , transerStr);
		depositTransferRequestBean.getDepositRequestBean().setTranser(transerStr);

		Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositTransferRequestBean.getDepositRequestBean().getOldDepositor().trim(),
				depositTransferRequestBean.getDepositRequestBean().getCoy().trim(), depositTransferRequestBean.getDepositRequestBean().getReceiptnum().trim());
		DepositRequestBean depositUpdateBean  =  new DepositRequestBean();
		depositUpdateBean.setInstructions("Transfered To " + transferReceiptNumber.toString());
		depositUpdateBean.setTransfer(depositTransferRequestBean.getDepositRequestBean().getTransfer());
		depositUpdateBean.setExpirystatus("T");
		depositUpdateBean.setDisdate(depositTransferRequestBean.getDepositRequestBean().getDisdate());

		String proprietor = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(depositTransferRequestBean.getDepositRequestBean().getCoy().trim(),CommonUtils.INSTANCE.closeDate());

		List<ActrandBean> actrandBeanList = new ArrayList<>();
		String acMajor ;
		Integer depmonths = depositTransferRequestBean.getDepositRequestBean().getDepmonths().intValue();
		ServiceResponseBean vouNum =  GenericAccountingLogic.funcGetJVNumber(depositTransferRequestBean.getDepositRequestBean().getCoy().trim(),  depositTransferRequestBean.getDepositRequestBean().getTransfer());
		logger.info("Voucher Number :: {}" , vouNum);


		List<Depint> depintEntityList = this.depintRepository.findByDepintCK_DinDepositorAndDepintCK_DinReceiptnumAndDepintCK_DinCoy(depositTransferRequestBean.getDepositRequestBean().getOldDepositor().trim(), depositTransferRequestBean.getDepositRequestBean().getReceiptnum().trim(), depositTransferRequestBean.getDepositRequestBean().getCoy().trim());
		logger.info(" Depint List:: {}" , depintEntityList);
		if(!CollectionUtils.isEmpty(depintEntityList)) {
			List<Depint> depintBeanList = new ArrayList<>();

			depintEntityList.stream().map(depintEntity -> {
				depintBeanList.add(FdPojoEntityMapper.addDepintEntityPojoMapper.apply(new Object[] {DepintBean.builder()
						.bankcode(depintEntity.getDinBankcode())
						.chqnum(depintEntity.getDepintCK().getDinChqnum())
						.coy(depintEntity.getDepintCK().getDinCoy())
						.depositor(newDepositorId.getValue())
						.interest(BigInteger.ZERO.doubleValue())
						.fromdate(Objects.nonNull(depintEntity.getDinFromdate()) ? depintEntity.getDinFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.todate(Objects.nonNull(depintEntity.getDinTodate()) ? depintEntity.getDinTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.intfrom(Objects.nonNull(depintEntity.getDepintCK().getDinIntfrom()) ? depintEntity.getDepintCK().getDinIntfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.intupto(Objects.nonNull(depintEntity.getDepintCK().getDinIntupto()) ? depintEntity.getDepintCK().getDinIntupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.newprin(BigInteger.ZERO.doubleValue())
						.origsite(depintEntity.getDinOrigsite())
						.proprietor(depintEntity.getDinProprietor())
						.receiptnum(transferReceiptNumber)   // depintEntity.getDepintCK().getDinReceiptnum()
						.sessid(depintEntity.getDinSessid())
						.staffallow(depintEntity.getDinStaffallow())
						.taxalwaysyn(depintEntity.getDinTaxalwaysyn())
						.tds(depintEntity.getDinTds())
						.transer(depintEntity.getDinTranser())
						.userid(depositTransferRequestBean.getDepositRequestBean().getUserid())
						.canceldate(Objects.nonNull(depintEntity.getDinCanceldate()) ? depintEntity.getDinCanceldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.build(), SiteFromDBEntity})
						);
				return depintEntity;
			}).collect(Collectors.toList());	

			this.depintRepository.saveAll(depintBeanList);
		}
		if(vouNum.getMessage() != null)
			return ResponseEntity.ok(ServiceResponseBean.builder().message(vouNum.getMessage()).status(Boolean.FALSE).build()); 

		String narrativeMsgForActranh = "TRANSFER OF DEPOSIT";
		ActranhBean actranhBean = ActranhBean.builder()
				.transer(transerStr)
				.trantype(TranTypeEnum.JV.toString())
				.trandate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
				.tranamt(BigInteger.ZERO.doubleValue())
				.voudate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
				.vounum(StringUtils.isNotBlank(depositTransferRequestBean.getDepositRequestBean().getVouNum()) ? depositTransferRequestBean.getDepositRequestBean().getVouNum().trim() : null)
				//				.bankcode(depositTransferRequestBean.getDepositRequestBean().getBankcode())
				.postedyn("Y")
				.proprietor(proprietor)
				.coy(depositTransferRequestBean.getDepositRequestBean().getCoy().trim())
				.site(SiteFromDBEntity)
				.userid(depositTransferRequestBean.getDepositRequestBean().getUserid())
				.reverseyn("N")
				.clearacyn("Y")
				.provyn("N")
				.balancedyn("Y")
				.narrative(narrativeMsgForActranh)
				.crepno(BigInteger.ONE.doubleValue())
				.vounum(vouNum.getData().toString())
				//				.ledgcode("GL")
				.build();

		this.actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));

		switch (depmonths) {
		case 3:
			acMajor = "11326052";
			break;
		case 6:
			acMajor = "11326011";
			break;
		case 12:
			acMajor = "11326024";
			break;
		case 18:
			acMajor = "11326078";
			break;
		case 24:
			acMajor = "11326037";
			break;
		case 30:
			acMajor = "11326081";
			break;
		case 36:
			acMajor = "11326040";
			break;
		case 48:
			acMajor = "11326065";
			break;
		default:
			acMajor ="11326024";
		}
		String partyCode = depositTransferRequestBean.getDepositRequestBean().getCoy().trim().concat(depositTransferRequestBean.getDepositRequestBean().getDepositor());
		String transferDepositorPartyCode = depositTransferRequestBean.getDepositRequestBean().getCoy().trim().concat(depositTransferRequestBean.getDepositRequestBean().getOldDepositor());

		AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", partyCode, null, null);// removed partycode ------------>
		AccountingBean transferDepositorAccoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, null, null, "D", transferDepositorPartyCode, null, null);// removed partycode ------------>
		AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, TranTypeEnum.JV.toString());

		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositTransferRequestBean.getDepositRequestBean().getTranser())
						.bunum(i)
						.trantype(TranTypeEnum.JV.toString())
						.trandate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.proprietor(depositTransferRequestBean.getDepositRequestBean().getCoy())
						.coy(depositTransferRequestBean.getDepositRequestBean().getCoy())
						.mintype(accoutingDataForTran.getMinType())
						.mincode(accoutingDataForTran.getMinCode())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(acMajor)
						.project(accoutingDataForTran.getProject())
						.acminor(accoutingDataForTran.getAcminor())
						.vounum(StringUtils.isNotBlank(depositTransferRequestBean.getDepositRequestBean().getVouNum()) ? depositTransferRequestBean.getDepositRequestBean().getVouNum().trim() : null)
						.voudate(depositTransferRequestBean.getDepositRequestBean().getReceiptdate())
						.tranamt(depositTransferRequestBean.getDepositRequestBean().getDepamount().doubleValue() * -1)
						.reffrom(depositTransferRequestBean.getDepositRequestBean().getDepdate())
						.refupto(depositTransferRequestBean.getDepositRequestBean().getMatdate())
						.narrative("Dep. Rs." .concat(String.valueOf(depositTransferRequestBean.getDepositRequestBean().getDepamount().doubleValue())).concat("/- for ").concat(depmonths.toString()).concat("mon.@").concat((depositTransferRequestBean.getDepositRequestBean().getIntrate().toString()).concat("%p.a")))
						.cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode())
						//						.doctype(TranTypeEnum.JV.toString())
						.docpartype(TranTypeEnum.D.toString())
						//						.docparcode(partyCode)
						.docnum(transferReceiptNumber.toString())
						//X columns (Contra entry)
						.xreftranser(depositTransferRequestBean.getDepositRequestBean().getTranser())
						.xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(acMajor)
						.xmintype(accoutingDataForTran.getMinType())
						.xpartycode(accoutingDataForTran.getPartyCode())
						.xproject(accoutingDataForTran.getProject())
						.xreftrandate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.xpartytype(accoutingDataForTran.getPartyType())
						.xrefbunum(i+1)
						.site(SiteFromDBEntity)
						.userid(depositTransferRequestBean.getDepositRequestBean().getUserid())
						//						.ledgcode("GL")
						.docparcode(partyCode)
						.oldbunum(i)
						.contrabunum(i)
						.voudate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.vounum(vouNum.getData().toString())
						.docdate(depositTransferRequestBean.getDepositRequestBean().getDepdate())
						// depositTransferRequestBean.getDepositRequestBean().getTransfer()
						.today(LocalDateTime.now())
						.build()
						);
			}else {
				actrandBeanList.add(ActrandBean.builder()
						.transer(depositTransferRequestBean.getDepositRequestBean().getTranser())
						.bunum(i)
						.trantype(TranTypeEnum.JV.toString())
						.trandate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.proprietor(depositTransferRequestBean.getDepositRequestBean().getCoy())
						.coy(depositTransferRequestBean.getDepositRequestBean().getCoy())
						.mintype(transferDepositorAccoutingDataForTran.getMinType())
						.mincode(transferDepositorAccoutingDataForTran.getMinCode())
						.partytype(transferDepositorAccoutingDataForTran.getPartyType())
						.partycode(transferDepositorAccoutingDataForTran.getPartyCode())
						.project(transferDepositorAccoutingDataForTran.getProject())
						.acminor(transferDepositorAccoutingDataForTran.getAcminor())
						.acmajor(acMajor)
						.vounum(StringUtils.isNotBlank(depositTransferRequestBean.getDepositRequestBean().getVouNum()) ? depositTransferRequestBean.getDepositRequestBean().getVouNum().trim() : null)
						.voudate(depositTransferRequestBean.getDepositRequestBean().getReceiptdate())
						.tranamt(depositTransferRequestBean.getDepositRequestBean().getDepamount().doubleValue())
						.reffrom(depositTransferRequestBean.getDepositRequestBean().getDepdate())
						.refupto(depositTransferRequestBean.getDepositRequestBean().getMatdate())
						.narrative("Dep. Rs." .concat(String.valueOf(depositTransferRequestBean.getDepositRequestBean().getDepamount().doubleValue())).concat("/- for ").concat(depmonths.toString()).concat("mon.@").concat((depositTransferRequestBean.getDepositRequestBean().getIntrate().toString()).concat("%p.a")))
						.cfgroup(transferDepositorAccoutingDataForTran.getCfGroup())
						.cfcode(transferDepositorAccoutingDataForTran.getCfCode())
//						.docdate(depositTransferRequestBean.getDepositRequestBean().getReceiptdate())
						//						.paymode("Q")
						//						.doctype(TranTypeEnum.JV.toString())
						.docpartype(TranTypeEnum.D.toString())
						//						.docparcode(partyCode)
						.docnum(depositTransferRequestBean.getDepositRequestBean().getReceiptnum())
						//transferReceiptNumber.toString()
						//X columns (Contra entry)
						.xreftranser(depositTransferRequestBean.getDepositRequestBean().getTranser())
						.xacminor(transferDepositorAccoutingDataForTran.getAcminor())
						.xacmajor(acMajor)
						.xmintype(transferDepositorAccoutingDataForTran.getMinType())
						.xpartycode(transferDepositorAccoutingDataForTran.getPartyCode())
						.xproject(transferDepositorAccoutingDataForTran.getProject())
						.xreftrandate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.xpartytype(transferDepositorAccoutingDataForTran.getPartyType())
						.xrefbunum(i-1)
						.site(SiteFromDBEntity)
						.userid(depositTransferRequestBean.getDepositRequestBean().getUserid())
						//						.ledgcode("GL")
						.docparcode(transferDepositorPartyCode)
						.oldbunum(i)
						.contrabunum(i)
						.voudate(depositTransferRequestBean.getDepositRequestBean().getTransfer())
						.vounum(vouNum.getData().toString())
						.docdate(depositTransferRequestBean.getDepositRequestBean().getDepdate())
						.today(LocalDateTime.now())
						.build()
						);
			}}	
		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

		depositTransferRequestBean.getDepositRequestBean().setTranser(transerStr);
		depositTransferRequestBean.getDepositRequestBean().setTransfer(null);
		depositTransferRequestBean.getDepositRequestBean().setDisdate(null);

		this.depositRepository.save(FdPojoEntityMapper.updateDepositPojoEntityMapper.apply(depositEntity, depositUpdateBean));
		this.depositRepository.save(FdPojoEntityMapper.addDepositFdPojoEntityMapping.apply(new Object[] {depositTransferRequestBean.getDepositRequestBean(), SiteFromDBEntity, transferReceiptNumber.toString()}));
		
		GenericAuditContextHolder.getContext().setTransactionNo(transferReceiptNumber);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message(vouNum.getData().toString() +" /" +transerStr+" ,Receipt No. "+transferReceiptNumber + newDepIdMessage).build());
	}

	@Override
	public ResponseEntity<?> validateDepositPrintRev(String depositor, String companyCode, String receiptNum) {
		Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositor, companyCode, receiptNum);
		if(depositEntity!=null) {
			Double depPrintRev = depositEntity.getDepPrintrev();
			if(depPrintRev == null)
				depPrintRev = BigInteger.ZERO.doubleValue();
			if(depPrintRev < 3) {
				DepositRequestBean depositUpdateBean = new DepositRequestBean();
				depositUpdateBean.setPrintrev(depPrintRev + 1);
				this.depositRepository.save(FdPojoEntityMapper.updateDepositPojoEntityMapper.apply(depositEntity, depositUpdateBean));
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Print Rev Updated Succesfully").build());
			}
			else {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Already 3 Reprints taken for this Reciept" + "\n"+ " Please select other receipt#").build());
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}