package kraheja.fd.deposit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.ExcelUtils;
import kraheja.fd.deposit.bean.request.NeftReportRequestBean;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.repository.DepositRepository;
import kraheja.fd.deposit.service.DepositorService;
import kraheja.fd.deposit.service.FDReportService;

@Service
@Transactional
public class FDReportServiceImpl implements FDReportService {

	private static final Logger logger = LoggerFactory.getLogger(FDReportServiceImpl.class);

	@Autowired
	private EntityRepository entityRepository;
	
	@Autowired
	private DepositRepository depositRepository;
	
	@Autowired
	private DepositorService depositorService;
	
	@Autowired
	private ActranhRepository actranhRepository;
	
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Override
	public ResponseEntity<ServiceResponseBean> fetchNeftReportFromDateAndTodateAndCoyBank() {
		String dateFromDBEntity = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntChar1("INTCH","Y");
		logger.info("dateFromDBEntity :: {}" , dateFromDBEntity);

		Map<String, String> reponseMap = new HashMap<String,String>();  
		reponseMap.put("coyBankAC", "6629009300000199");
		if(StringUtils.isNotBlank(dateFromDBEntity)) {
			String year = dateFromDBEntity.substring(0, 4);
			logger.info("year :: {}" , year);

			String month = dateFromDBEntity.substring(4, 6);
			logger.info("month :: {}" , month);

			if(Integer.valueOf(month) == 4) {
				String fromDate = "01/11/".concat(String.valueOf(Integer.valueOf(year) - 1));
				logger.info("fromDate :: {}" , fromDate);

				String toDate = "30/04/".concat(String.valueOf(Integer.valueOf(year)));
				logger.info("toDate :: {}" , toDate);

				reponseMap.put("fromDate", fromDate);
				reponseMap.put("toDate", toDate);
			}
			else if(Integer.valueOf(month) == 10) {
				String fromDate = "01/05/".concat(String.valueOf(Integer.valueOf(year)));
				logger.info("fromDate :: {}" , fromDate);

				String toDate = "31/10/".concat(String.valueOf(Integer.valueOf(year)));
				logger.info("toDate :: {}" , toDate);

				reponseMap.put("fromDate", fromDate);
				reponseMap.put("toDate", toDate);
			}else
				return ResponseEntity.ok(ServiceResponseBean.builder().message("Invalid Date in DataBase").status(Boolean.FALSE).build());

			return ResponseEntity.ok(ServiceResponseBean.builder().data(reponseMap).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> exportNeftReport(NeftReportRequestBean neftReportRequestBean) {
		String sqlQuery;
		String dateCondition;
		String depositorCondition = " and din.DIN_DEPOSITOR in ( ";
		
		String dateFromDBEntity = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntChar1("INTCH","Y");
		logger.info("dateFromDBEntity :: {}" , dateFromDBEntity);
		
		String month = dateFromDBEntity.substring(4, 6);
		logger.info("month :: {}" , month);
		
		if(neftReportRequestBean.getDepositorsList().isEmpty()) {
			depositorCondition = depositorCondition.concat("Select deptr_depositor from Depositor where deptr_coy = '".concat(neftReportRequestBean.getCompanyCode().trim()).concat("' )"));
		}
		else {
			depositorCondition = depositorCondition.concat(StringUtils.join(neftReportRequestBean.getDepositorsList(),",").concat(" )"));
		}
		if(month.equalsIgnoreCase("04")){
			dateCondition = CommonConstraints.INSTANCE.LESSTHAN_STRING;
		}
		else {
			dateCondition = CommonConstraints.INSTANCE.GREATERTHAN_STRING;
		}
//		following formate was for PNB changes done by kalpana on 10/10/2023
//		sqlQuery = "SELECT  DIN_DEPOSITOR, "
//				+ "LTRIM('".concat(neftReportRequestBean.getCompanyBankAc()).concat("') as AccountNo, ")
//				+ "sum(din.DIN_INTEREST)-sum(din.DIN_tds) as Amt, "
//				+ "'FD Int' as TranParticular, "
//				+ "(select par_payeeifsc1 from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') as ifsc, "
//				+ "(select LTRIM(par_payeeacnum1) from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') as acnum, "
//				+ "Replace(Replace(Replace(upper(Replace(Trim(dep.DEPTR_NAME),'(', '')),')',''),'.',''),'`','') as Names, "
//				+ "'MUMBAI' as ADDR1, 'Unique FD Int' as SenderInfo1,  'Unique FD Int' as SenderInfo2, "
//				+ "LTRIM('".concat(neftReportRequestBean.getCompanyBankAc()).concat( "') as chargeacc, ")
//				+ "'".concat(neftReportRequestBean.getChequeAlpha()).concat("' as chequeAlpha, ")
//				+ "LTRIM(TO_CHAR('".concat(neftReportRequestBean.getChequeNum()).concat("','000000')) as ChequeNo, ")
//				+ "LTRIM(TO_CHAR('".concat(neftReportRequestBean.getChequeDate()).concat("','00000000')) as ChequeDate ")
//				+ "FROM DEPINT din,DEPOSITOR dep "
//				+ "where din.DIN_COY = dep.DEPTR_COY "
//				+ "and din.DIN_DEPOSITOR = dep.DEPTR_DEPOSITOR " 
//				+ "and din.DIN_coy='".concat(neftReportRequestBean.getCompanyCode().trim()).concat("' ")
//				+ "and din.DIN_INTFROM >= to_date('" + neftReportRequestBean.getFromDate().trim() +"','dd/MM/yyyy') "
//				+ "and din.DIN_INTUPTO ".concat(dateCondition).concat("= to_date('".concat(neftReportRequestBean.getToDate().trim()).concat("','dd/MM/yyyy') "))
//				+ "and din.DIN_SESSID is not null "
//				+ "and (select substr(par_payeeifsc1,1,4) from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') ".concat(neftReportRequestBean.getBankString()).concat(depositorCondition)
//				+ " group by  DIN_DEPOSITOR,Replace(Replace(Replace(upper(Replace(Trim(dep.DEPTR_NAME),'(', '')),')',''),'.',''),'`',''),Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) "
//				+ " order by DIN_DEPOSITOR ";	
		
//		following formate is for Indian Bank
		sqlQuery = "SELECT  DIN_DEPOSITOR, "
				+ "(select LTRIM(par_payeeacnum1) from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') as BeneficiaryAccount, "
				+ "sum(din.DIN_INTEREST)-sum(din.DIN_tds) as AmountToBeCredited, "
				+ "0 as CommissionAmount, "
				+ "(select par_payeeifsc1 from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') as ValidIFSCCode , "
				+ "'Interest' as Details, "
				+ "Replace(Replace(Replace(upper(Replace(Trim(dep.DEPTR_NAME),'(', '')),')',''),'.',''),'`','') as BeneficiaryName, "
				+ "(select adr_phoneoff from address where Trim(adr_adowner) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR)) as ValidMobileNo "
				+ "FROM DEPINT din,DEPOSITOR dep "
				+ "where din.DIN_COY = dep.DEPTR_COY "
				+ "and din.DIN_DEPOSITOR = dep.DEPTR_DEPOSITOR " 
				+ "and din.DIN_coy='".concat(neftReportRequestBean.getCompanyCode().trim()).concat("' ")
				+ "and din.DIN_INTFROM >= to_date('" + neftReportRequestBean.getFromDate().trim() +"','dd/MM/yyyy') "
				+ "and din.DIN_INTUPTO ".concat(dateCondition).concat("= to_date('".concat(neftReportRequestBean.getToDate().trim()).concat("','dd/MM/yyyy') "))
				+ "and din.DIN_SESSID is not null "
				+ " group by  DIN_DEPOSITOR,Replace(Replace(Replace(upper(Replace(Trim(dep.DEPTR_NAME),'(', '')),')',''),'.',''),'`',''),Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) "
				+ " order by DIN_DEPOSITOR ";	
		logger.debug("Query :: {}", sqlQuery);
//		+ "and (select substr(par_payeeifsc1,1,4) from party where trim(par_partycode) = Trim(din.DIN_COY)||Trim(din.DIN_DEPOSITOR) and par_partytype = 'D') ".concat(neftReportRequestBean.getBankString()).concat(depositorCondition)
		try {
			File excelFile = ExcelUtils.INSTANCE.export(sqlQuery, dbUrl, dbUsername, dbPassword);

			String fileName = CommonConstraints.INSTANCE.DD_MM_YYYY_HH_MM_SS_FORMATTER.format(LocalDateTime.now()).concat(CommonConstraints.INSTANCE.XLS_EXTENSION);
			logger.debug("FileName :: {}", fileName);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.body(new InputStreamResource(new FileInputStream(excelFile)));
		} catch (FileNotFoundException e) {
			logger.info("Exception :: {} ", e.getMessage());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Error while creating report.").build());
	}

	@Override
	public ResponseEntity<?> fetchFixedDepositRecieptReportParameters(String comanyCode, String depositorId,
			String receiptNum) {
		Deposit depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositorId.trim(), comanyCode.trim(), receiptNum.trim());
		Deposit existingDeposit = this.depositRepository.findByCompanyCodeAndReceiptNum(comanyCode.trim(), depositEntity.getDepOrigreceipt().trim());
		logger.debug("DepositEntity :: {}", depositEntity);
		String renewalDesc = " of Fixed Deposit receipt No. " + depositEntity.getDepOrigreceipt().trim() + " issued on " + CommonUtils.INSTANCE.convertLocalDateTimeToString(existingDeposit.getDepDepdate()) + " for Rs. " + Math.round(depositEntity.getDepDepamount()) + " expiring on " + CommonUtils.INSTANCE.convertLocalDateTimeToString(existingDeposit.getDepMatdate());
		JSONObject json = new JSONObject();
		Object responseObject = new Object();
		try {
			json.put("amtinwords","RUPEES  ".concat(CommonUtils.convert(Math.round(depositEntity.getDepDepamount()))).concat(" ONLY."));
			json.put("address",this.depositorService.fetchDepositorAddress(comanyCode, depositorId));
			json.put("nominee", Objects.nonNull(depositEntity.getDepNominee()) ? "Nominee: "+depositEntity.getDepNominee() : "");
			json.put("renewalDesc", depositEntity.getDepDeporigin().equalsIgnoreCase("N") ? "" :  depositEntity.getDepDeporigin().equalsIgnoreCase("T") ? "TRANSFER".concat(renewalDesc) : "RENEWAL".concat(renewalDesc));
			ObjectMapper m = new ObjectMapper();
			responseObject = m.readValue(json.toString(), Object.class);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(responseObject).build());

	}

	@Override
	public ResponseEntity<?> findMaxranDateByTranserNos(List<String> transerNos) {
		String maxTranDate = this.actranhRepository.findMaxranDateByTranserNos(transerNos);
		if(Objects.nonNull(maxTranDate)){
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(maxTranDate).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());

	}
}