package kraheja.fd.deposit.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;
import kraheja.fd.deposit.bean.InterestChequePrintingBean;
import kraheja.fd.deposit.bean.request.InterestChequePrintingRequestBean;
import kraheja.fd.deposit.bean.response.InterestChequePrintingResponseBean;
import kraheja.fd.deposit.entity.Fdddde08;
import kraheja.fd.deposit.entity.Fdddde08CK;
import kraheja.fd.deposit.repository.DepintRepository;
import kraheja.fd.deposit.repository.Fddde08Repository;
import kraheja.fd.deposit.service.InterestChequePrintingService;

@Service
@Transactional
public class InterestChequePrintingServiceImpl implements InterestChequePrintingService {

	private static final Logger logger = LoggerFactory.getLogger(InterestChequePrintingServiceImpl.class);

	@Autowired
	private DepintRepository depintRepository;
	
	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private Fddde08Repository fddde08Repository;
	
	@PersistenceContext
	private  EntityManager entityManager;
	
	@Override
	public ResponseEntity<?> fetchInterestCalculationReport(InterestChequePrintingRequestBean interestChequePrintingRequestBean) {
		this.fddde08Repository.truncate();
		String dateFromDatabase = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntChar1("INTCH","Y");
		logger.info("Entity :: {}" , dateFromDatabase);
		
		if(StringUtils.isNotBlank(dateFromDatabase)) {
			
			String  IntUptoDate = LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
			Integer folioReportSeqId =1;

			String IntPriMonth = dateFromDatabase.substring(4, 6);
			logger.info("IntPriMonth :: {}" , IntPriMonth);
			
			String IntPriYear = dateFromDatabase.substring(0, 4);
			logger.info("IntPriYear :: {}" , IntPriYear);
			
			LocalDate chequeDateInLocalDate =  LocalDate.parse(interestChequePrintingRequestBean.getChequeDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
			if(interestChequePrintingRequestBean.getIsTestPrint()){
			
			logger.info("Server Year :: {}" , String.valueOf(LocalDate.now().getYear())); 
			if(Integer.parseInt(IntPriYear) != chequeDateInLocalDate.getYear()) 
				 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid year").build());
			
			if(Integer.parseInt(IntPriMonth)  != chequeDateInLocalDate.getMonthValue() || Integer.parseInt(IntPriMonth)  < chequeDateInLocalDate.getMonthValue()) 
				 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid month").build());
			
			LocalDate DtLocChkDate = LocalDate.now();

			if(Integer.parseInt(IntPriMonth) == 4){
			 DtLocChkDate = LocalDate.of(Integer.parseInt(IntPriYear), 4, 27);
			}
			else if(Integer.parseInt(IntPriMonth) == 10){
			 DtLocChkDate = LocalDate.of(Integer.parseInt(IntPriYear), 10, 28);
			}
			
			if(!(DtLocChkDate.compareTo(chequeDateInLocalDate) < 0))
				 return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Cheque date can be less than month-end date by only maximum 3 days.").build());
			}
			
			if(IntPriMonth.equals("04")) {
				folioReportSeqId = 1;
				if(!interestChequePrintingRequestBean.getIsTestPrint()) {
					IntUptoDate =	"31/03/".concat(String.valueOf(LocalDate.now().getYear()));
				}else {
					IntUptoDate = interestChequePrintingRequestBean.getChequeDate();
				}
			}
			else if(IntPriMonth.equals("10")) {
				folioReportSeqId = 2;
				if(!interestChequePrintingRequestBean.getIsTestPrint()) {
                 IntUptoDate = "31/10/" .concat(String.valueOf(LocalDate.now().getYear()));
				}else {
					IntUptoDate = interestChequePrintingRequestBean.getChequeDate();
				}
			}
			
			String dateCondition =">";
			ValueContainer<String> partyName = new ValueContainer<String>("Normal");
			
			if(interestChequePrintingRequestBean.getIsTestPrint())
				 partyName.setValue("Cancel");
			
			if(IntUptoDate.substring(4, 6).equals("10"))//------> changed as discussed with kalpana mam on 28/04/2023
				dateCondition = " ";
			
//		List<Tuple> tuplesList = this.depintRepository.fetchInterestChequePrintingDetail(interestChequePrintingRequestBean.getCompanyCode().trim(), IntUptoDate, LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), interestChequePrintingRequestBean.getDepFrom(), interestChequePrintingRequestBean.getDepTo());
			Query query = this.entityManager.createNativeQuery("select din_coy as dinCoy ,dIN_depositor as dinDepositor,\r\n"
					+ "sum(din_interest) as interest,\r\n"
					+ "sum(din_tds) as tds,\r\n"
					+ "sum(din_interest)  - SUM(din_tds) as cheqamt, \r\n"
					+ "COUNT(*) as receiptyn,\r\n"
					+ "(select upper(Trim(deptr_title)) || ' ' || upper(Trim(deptr_name)) as names from depositor where deptr_depositor = dIN_depositor and deptr_coy = din_coy) as partyName,\r\n"
					+"(SELECT RTRIM(ADR_ADLINE1) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND adr_adowner = rtrim(din_coy) || dIN_depositor || '  ') as adrAdline1,\r\n"
					+ "(SELECT RTRIM(ADR_ADLINE2) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND adr_adowner = rtrim(din_coy) || dIN_depositor || '  ') as adrAdline2,\r\n"
					+ "(SELECT (RTRIM(ADR_ADLINE3) || ' ' || RTRIM(ADR_ADLINE4) || ' ' || RTRIM(ADR_ADLINE5)) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND adr_adowner = rtrim(din_coy) || dIN_depositor || '  ') as adrAdline3,\r\n"
					+ "(SELECT ((Case RTRIM(ADR_TOWNSHIP) when '.' then '' else RTRIM(ADR_TOWNSHIP) end) || ' ' || RTRIM(ADR_CITY) || '-' || RTRIM(ADR_PINCODE)) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND adr_adowner = rtrim(din_coy) || dIN_depositor || '  ') as adrAdline4,"
					+ "(SELECT deptr_intpaidytd FROM depositor WHERE deptr_depositor = dIN_depositor AND deptr_coy = din_coy) as intPaidytd,\r\n"
					+ "(SELECT deptr_taxpaidytd FROM depositor WHERE deptr_depositor = dIN_depositor AND deptr_coy = din_coy) as taxPaidytd\r\n"
					+ "from depint where \r\n"
					+ "TRIM(din_coy) = :coy and \r\n"
					+ "(din_intupto "+ dateCondition +"= '"+IntUptoDate+"' ) and din_transer like 'DIP%' and\r\n"
					+ "(TRIM(din_depositor) BETWEEN :depFrom AND :depTo) \r\n"
					+ "group by (din_coy,din_depositor)", Tuple.class );
			
			query.setParameter("coy", interestChequePrintingRequestBean.getCompanyCode().trim());
			query.setParameter("depFrom", interestChequePrintingRequestBean.getDepFrom());
			query.setParameter("depTo", interestChequePrintingRequestBean.getDepTo());

			List<Tuple> tuplesList = query.getResultList();

		String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		logger.info("Entity :: {}" , SiteFromDBEntity);

		if(CollectionUtils.isNotEmpty(tuplesList)) {
		List<InterestChequePrintingBean> interestChequePrintingBeanList = tuplesList.stream().map(t -> {return new InterestChequePrintingBean(
                t.get(0, String.class),
                t.get(1, String.class),
                t.get(11, BigDecimal.class),
                t.get(12, BigDecimal.class),
                t.get(4, BigDecimal.class),
                t.get(5, BigDecimal.class),
                t.get(6, String.class),
                t.get(7, String.class),
                t.get(8, String.class),
                t.get(9, String.class),
                t.get(10, String.class),
                t.get(2, BigDecimal.class),
                t.get(3, BigDecimal.class));
		}
        ).collect(Collectors.toList());
		logger.info("InterestChequePrintingBean {} :: ", interestChequePrintingBeanList);
		ValueContainer<String> receiptYN = new ValueContainer<String>();
		
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		List<Fdddde08> fdddde08List = new ArrayList<>();
		
		interestChequePrintingBeanList.stream().map(
				interestChequePrintingBean -> {
					
					if(interestChequePrintingBean.getGetReceiptyn().intValue() > 6) 
						receiptYN.setValue("Y");
					fdddde08List.add(
							 Fdddde08.builder()
							 .fdddde08CK(Fdddde08CK.builder()
									 .depSesid(Double.valueOf(sessionId))
									 .depDepositor(interestChequePrintingBean.getGetDinDepositor())
									 .depCoy(interestChequePrintingBean.getGetDinCoy())
									 .build())
							 .depAdline1(interestChequePrintingBean.getGetAdrAdline1())
							 .depAdline2(interestChequePrintingBean.getGetAdrAdline2())
							 .depAdline3(interestChequePrintingBean.getGetAdrAdline3())
							 .depAdline4(interestChequePrintingBean.getGetAdrAdline4())
							 .depAmtinwords(CommonUtils.convert(interestChequePrintingBean.getGetCheqamt().intValue()).concat(" ONLY."))
							 .depChqamt(interestChequePrintingBean.getGetCheqamt().doubleValue())
							 .depChqdate(LocalDate.parse(interestChequePrintingRequestBean.getChequeDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
							 .depChqnum(interestChequePrintingRequestBean.getChequeNum())
							 .depDepname(interestChequePrintingBean.getGetPartyName())
							 .depUserid(interestChequePrintingRequestBean.getUserId())
							 .depInterest(interestChequePrintingBean.getGetInterest().doubleValue())
							 .depIntpaidytd(interestChequePrintingBean.getGetIntPaidytd().doubleValue())
							 .depPartyname(partyName.getValue())
							 .depSite(SiteFromDBEntity)
							 .depTaxpaidytd(interestChequePrintingBean.getGetaxPaidytd().doubleValue())
							 .depTds(interestChequePrintingBean.getGetTds().doubleValue())
							 .depToday(LocalDateTime.now())
							 .recptYn(receiptYN.getValue())
							 .build() 
							);
					return interestChequePrintingBean;
				}
				).collect(Collectors.toList());
		
		this.fddde08Repository.saveAll(fdddde08List);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(InterestChequePrintingResponseBean.builder()
				.sessionId(sessionId)
				.intUpToDate(IntUptoDate)
				.intUpToDatePlusOneMonth(LocalDate.parse(IntUptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).plusMonths(1).format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
				.folioReportSeqId(folioReportSeqId)
				.build()).build());
			}		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found for  Depositor Range ".concat(interestChequePrintingRequestBean.getDepFrom()).concat(" to ".concat(interestChequePrintingRequestBean.getDepTo()))).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Something Went Wrong").build());
	}
	
	@Override
	public ResponseEntity<?> truncateTempTable() {
		this.fddde08Repository.truncate();
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}
}