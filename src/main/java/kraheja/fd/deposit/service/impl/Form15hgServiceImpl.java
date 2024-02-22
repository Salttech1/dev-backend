package kraheja.fd.deposit.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
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

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Company;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.ExcelUtils;
import kraheja.fd.deposit.bean.request.Form15hgRequestBean;
import kraheja.fd.deposit.bean.response.Form15hgResponseBean;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.entity.Form15hg;
import kraheja.fd.deposit.mappers.FdEntityPojoMapper;
import kraheja.fd.deposit.mappers.FdPojoEntityMapper;
import kraheja.fd.deposit.repository.DepositorRepository;
import kraheja.fd.deposit.repository.Form15hgRepository;
import kraheja.fd.deposit.service.Form15hgService;

@Service
@Transactional
public class Form15hgServiceImpl implements Form15hgService {

	private static final Logger logger = LoggerFactory.getLogger(Form15hgServiceImpl.class);

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Autowired
	private Form15hgRepository form15hgRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private DepositorRepository depositorRepository;

	@Override
	public ResponseEntity<ServiceResponseBean> fetchForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(
			String depositorId, String companyCode, String accountYear, String quater) {
		Form15hg form15hgEntity = this.form15hgRepository
				.findByForm15hgCK_FormDepositorAndForm15hgCK_FormCoyAndForm15hgCK_FormAcyearAndForm15hgCK_FormQuarter(
						depositorId.trim(), companyCode.trim(), accountYear.trim(), quater.trim());
		logger.info("Form15hgEntity :: {}", form15hgEntity);
		if (Objects.nonNull(form15hgEntity)) {
			return ResponseEntity
					.ok(ServiceResponseBean.builder()
							.data(FdEntityPojoMapper.fetchForm15hgEntityPojoMapper
									.apply(new Object[] { Arrays.asList(form15hgEntity) }))
							.status(Boolean.TRUE).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found in form 15hg").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> updateForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(
			Form15hgRequestBean form15hgRequestBean) {
		Form15hg form15hgEntity = this.form15hgRepository
				.findByForm15hgCK_FormDepositorAndForm15hgCK_FormCoyAndForm15hgCK_FormAcyearAndForm15hgCK_FormQuarter(
						form15hgRequestBean.getDepositor().trim(), form15hgRequestBean.getCoy().trim(),
						form15hgRequestBean.getAcyear().trim(), form15hgRequestBean.getQuarter().trim());
		logger.info("Form15hgEntity :: {}", form15hgEntity);
		if (Objects.nonNull(form15hgEntity)) {

			this.form15hgRepository
					.save(FdPojoEntityMapper.updateForm15hgPojoEntityMapper.apply(form15hgEntity, form15hgRequestBean));
			
			GenericAuditContextHolder.getContext().setTransactionNo(form15hgRequestBean.getDepositor().trim());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			
			return ResponseEntity
					.ok(ServiceResponseBean.builder().message("Updated Successfully").status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> deleteForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(
			String depositorId, String companyCode, String accountYear, String quater) {
		Form15hg form15hgEntity = this.form15hgRepository
				.findByForm15hgCK_FormDepositorAndForm15hgCK_FormCoyAndForm15hgCK_FormAcyearAndForm15hgCK_FormQuarter(
						depositorId.trim(), companyCode.trim(), accountYear.trim(), quater.trim());
		logger.info("Form15hgEntity :: {}", form15hgEntity);

		if (Objects.nonNull(form15hgEntity)) {
			this.form15hgRepository.delete(form15hgEntity);
			
			GenericAuditContextHolder.getContext().setTransactionNo(depositorId);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} else
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> addForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(
			Form15hgRequestBean form15hgRequestBean) {

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		logger.info("Entity :: {}", siteFromDBEntity);

		this.form15hgRepository.save(FdPojoEntityMapper.addForm15hgEntityPojoMapper
				.apply(new Object[] { form15hgRequestBean, siteFromDBEntity }));
		
		GenericAuditContextHolder.getContext().setTransactionNo(form15hgRequestBean.getDepositor().trim());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity
				.ok(ServiceResponseBean.builder().message("Added Successfully").status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchUniqueidAndIncomeAndFromDateAndToDate(String depositorId,
			String companyCode, String accountYear, String quater) {
		Form15hg form15hgEntity = this.form15hgRepository
				.findByForm15hgCK_FormDepositorAndForm15hgCK_FormCoyAndForm15hgCK_FormAcyearAndForm15hgCK_FormQuarter(
						depositorId.trim(), companyCode.trim(), accountYear.trim(), quater.trim());
		logger.info("Form15hgEntity :: {}", form15hgEntity);
		if (Objects.isNull(form15hgEntity)) {
			// int year = Calendar.getInstance().get(Calendar.YEAR);
			// StringBuilder accountYear = new StringBuilder();
			// int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			// System.out.println("Financial month : " + month);
			// if (month < 3) {
			// accountYear.append(year - 1);
			// accountYear.append(year);
			// } else {
			// accountYear.append(year);
			// accountYear.append(year + 1);
			// System.out.println(accountYear);
			// }
			Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositorId.trim(),
					companyCode.trim());
			logger.info("Depositor :: {}", depositorEntity);
			if (depositorEntity != null) {

				String uniqueIdFirstCharacter = null;
				if (depositorEntity.getDeptrTds15gyn().equals("Y"))
					uniqueIdFirstCharacter = "G";
				else if (depositorEntity.getDeptrTds15hyn().equals("Y"))
					uniqueIdFirstCharacter = "H";
				else
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Invalid Depositor ID - Not 15H or 15G Depositor").build());

				accountYear.trim();
				String StrLocQStartDt = "", StrLocQEndDt = "", StrLocDIPStartDt = "", StrLocDIPEndDt = "";
				List<Map<String, Object>> amountResultSet = new ArrayList<>();
				List<Map<String, Object>> amountPaidDateResultSet = new ArrayList<>();
				Double amount = 0.0;
				Timestamp incomePaidDate = null;

				if (quater.equalsIgnoreCase("Q1")) {
					StrLocQStartDt = "01/04/" + accountYear.substring(0, 4);
					StrLocQEndDt = "30/06/" + accountYear.substring(0, 4);
					StrLocDIPStartDt = "01/04/" + accountYear.substring(0, 4);
					StrLocDIPEndDt = "30/04/" + accountYear.substring(0, 4);
				} else if (quater.equalsIgnoreCase("Q2")) {
					StrLocQStartDt = "01/07/" + accountYear.substring(0, 4);
					StrLocQEndDt = "30/09/" + accountYear.substring(0, 4);
					StrLocDIPStartDt = "";
					StrLocDIPEndDt = "";
				} else if (quater.equalsIgnoreCase("Q3")) {
					StrLocQStartDt = "01/10/" + accountYear.substring(0, 4);
					StrLocQEndDt = "31/12/" + accountYear.substring(0, 4);
					StrLocDIPStartDt = "01/05/" + accountYear.substring(0, 4);
					StrLocDIPEndDt = "31/10/" + accountYear.substring(0, 4);
				} else if (quater.equalsIgnoreCase("Q4")) {
					StrLocQStartDt = "01/01/" + accountYear.substring(4, 8);
					StrLocQEndDt = "31/03/" + accountYear.substring(4, 8);
					StrLocDIPStartDt = "01/11/" + accountYear.substring(4, 8);
					StrLocDIPEndDt = "31/03/" + accountYear.substring(4, 8);
				}

				Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
						companyCode.trim(), CommonUtils.INSTANCE.closeDate());
				logger.info("CompanyEntity :: {}", companyEntity);

				String StrLocQuater = accountYear.substring(2, 4).concat(accountYear.substring(6, 8))
						.concat(String.valueOf(quater.charAt(1)));
				logger.info("StrLocQuater :: {}", StrLocQuater);

				String StrLocYear = accountYear.substring(0, 4).concat(accountYear.substring(6, 8));
				logger.info("StrLocYear :: {}", StrLocYear);

				String uniqueid = uniqueIdFirstCharacter.concat(StrLocQuater).concat(depositorId.trim())
						.concat(StrLocYear).concat(companyEntity.getCoyTdsacno().trim());
				logger.info("uniqueid :: {}", uniqueid);

				amountResultSet = CommonResultsetGenerator.queryToResultSetBuilder(
						"Select SUM(gross) from (select din_depositor ,(select deptr_name from depositor where deptr_coy = din_coy and deptr_depositor = din_depositor) as Depositor,sum(din_interest) as Gross,sum(din_TDS) as TDS from depint a where din_coy = '"
								.concat(companyCode).concat("' and din_intupto Between to_date('")
								.concat(StrLocQStartDt).concat("','dd/MM/yyyy')  and to_date('").concat(StrLocQEndDt)
								.concat("','dd/MM/yyyy') and din_transer not like 'DIP%' group by din_coy,din_depositor union all select din_depositor ,(select deptr_name from depositor where deptr_coy = din_coy and deptr_depositor = din_depositor) as Depositor,sum(din_interest) as Gross,sum(din_TDS) as TDS from depint a where din_coy = '")
								.concat(companyCode).concat("' and din_intfrom >= to_date('").concat(StrLocDIPStartDt)
								.concat("','dd/MM/yyyy')  and din_intupto <=to_date('").concat(StrLocDIPEndDt)
								.concat("','dd/MM/yyyy') and din_transer like 'DIP%' group by din_coy,din_depositor ) where din_depositor = '")
								.concat(depositorId)
								.concat("' group by din_depositor, Depositor order by din_depositor"));
				logger.info("amountPaidResultSet :: {}", amountResultSet);

				amountPaidDateResultSet = CommonResultsetGenerator.queryToResultSetBuilder(
						"Select max(din_intupto) from (select max(din_intupto) as din_intupto,din_depositor from depint a where din_coy = '"
								.concat(companyCode).concat("' and din_intupto between to_date('")
								.concat(StrLocQStartDt).concat("','dd/MM/yyyy')  and to_date('").concat(StrLocQEndDt)
								.concat("','dd/MM/yyyy') and din_transer not like 'DIP%' and din_interest > 0 group by din_coy,din_depositor union all select max(din_intupto) as din_intupto, din_depositor from depint a where din_coy = '")
								.concat(companyCode).concat("' and din_intfrom >= to_date('").concat(StrLocDIPStartDt)
								.concat("','dd/MM/yyyy')  and din_intupto <=to_date('").concat(StrLocDIPEndDt)
								.concat("','dd/MM/yyyy') and din_transer like 'DIP%' and din_interest > 0 group by din_coy,din_depositor ) where din_depositor = '")
								.concat(depositorId).concat("'"));
				logger.info("amountPaidDateResultSet :: {}", amountPaidDateResultSet);

				if (quater.equalsIgnoreCase("Q2")) {
					amountResultSet = CommonResultsetGenerator.queryToResultSetBuilder(
							"Select SUM(gross) from (select din_depositor ,(select deptr_name from depositor where deptr_coy = din_coy and deptr_depositor = din_depositor) as Depositor,sum(din_interest) as Gross,sum(din_TDS) as TDS from depint a where din_coy = '"
									.concat(companyCode).concat("' and din_intupto between to_date('")
									.concat(StrLocQStartDt).concat("','dd/MM/yyyy')  and to_date('")
									.concat(StrLocQEndDt)
									.concat("','dd/MM/yyyy') group by din_coy,din_depositor ) where din_depositor = '")
									.concat(depositorId)
									.concat("' group by din_depositor, Depositor order by din_depositor"));
					logger.info("amountPaidResultSet :: {}", amountResultSet);

					amountPaidDateResultSet = CommonResultsetGenerator.queryToResultSetBuilder(
							"Select max(din_intupto) from (select max(din_intupto) as din_intupto,din_depositor from depint a where din_coy = '"
									.concat(companyCode).concat("' and din_intupto between to_date('")
									.concat(StrLocQStartDt).concat("','dd/MM/yyyy')  and to_date('")
									.concat(StrLocQEndDt)
									.concat("','dd/MM/yyyy')  and din_interest > 0  group by din_coy,din_depositor ) where din_depositor = '")
									.concat(depositorId).concat("'"));
					logger.info("amountPaidDateResultSet :: {}", amountPaidDateResultSet);
				}

				if (CollectionUtils.isNotEmpty(amountResultSet))
					amount = ((BigDecimal) amountResultSet.get(0).get("SUM(GROSS)")).doubleValue();

				if (CollectionUtils.isNotEmpty(amountPaidDateResultSet))
					incomePaidDate = (Timestamp) amountPaidDateResultSet.get(0).get("MAX(DIN_INTUPTO)");

				return ResponseEntity
						.ok(ServiceResponseBean
								.builder().status(Boolean.TRUE).data(Form15hgResponseBean.builder().form15hg(uniqueIdFirstCharacter).uniqueid(uniqueid)
										.amtincomepaid(amount).dateIncomePaid(incomePaidDate)
										.build())
								.build());
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("No Depositor found with this depositor Id").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Depositor already exist.Reterive to modify it").build());
	}

	@Override
	public ResponseEntity<?> export15hgReport(String companyCode, String accountYear, String quater, String fifteenhg,
			Boolean isIncomeDetails) {

		String StrLocColumn = " ", StrLocColumn1 = " ", StrLocColumn2 = " ";
		String sqlQuery;

		String StrLocQStartDt = "", StrLocQEndDt = "", StrLocDIPStartDt = "", StrLocDIPEndDt = "";
		if (quater.equalsIgnoreCase("Q1")) {
			StrLocQStartDt = "01/04/" + accountYear.substring(0, 4);
			StrLocQEndDt = "30/06/" + accountYear.substring(0, 4);
		} else if (quater.equalsIgnoreCase("Q2")) {
			StrLocQStartDt = "01/07/" + accountYear.substring(0, 4);
			StrLocQEndDt = "30/09/" + accountYear.substring(0, 4);
		} else if (quater.equalsIgnoreCase("Q3")) {
			StrLocQStartDt = "01/10/" + accountYear.substring(0, 4);
			StrLocQEndDt = "31/12/" + accountYear.substring(0, 4);
		} else if (quater.equalsIgnoreCase("Q4")) {
			StrLocQStartDt = "01/01/" + accountYear.substring(4, 8);
			StrLocQEndDt = "31/03/" + accountYear.substring(4, 8);
		}
		String StrLocACStartDt = "01/04/" + accountYear.substring(4, 8);
		String StrLocACEndDt = "31/03/" + String.valueOf(Integer.parseInt(accountYear.substring(4, 8)) + 1);

		if (fifteenhg.equals("G")) {
			StrLocColumn = " (SELECT CASE WHEN deptr_name like '%HUF%' THEN 'HUF (Hindu Undivided Family)' Else 'Individual' end FROM depositor WHERE deptr_coy = Form_Coy AND deptr_depositor = Form_Depositor) AS Status, '' AS Sub_Status, ";
			StrLocColumn1 = " 'RES - Resident' as Residential_Status, ";
			StrLocColumn2 = "  '' as Telephone, ";
		} else {
			StrLocColumn = " (select to_char(DEPTR_Birthdate,'dd-MM-yyyy') from depositor where deptr_coy = Form_Coy and deptr_depositor = Form_Depositor) AS Dep_Birthdate, ";
			StrLocColumn2 = "  '' as StdCode, '' as Telephone, ";
		}

		sqlQuery = "select Form_UniqueId ,  \r\n"
				+ "             (select deptr_name from depositor where deptr_coy = Form_Coy and deptr_depositor = Form_Depositor) as Declarant,  \r\n"
				+ "             (select Trim(deptr_panum1) from depositor where deptr_coy = Form_Coy and deptr_depositor = Form_Depositor) as DecPan,  \r\n"
				+ "             (select par_aadharno from party where par_partycode = 'UNIQ' || Form_Depositor AND par_partytype = 'D') as DepAadharno,  \r\n"
				+ "             ".concat(StrLocColumn).concat(" \r\n"
						+ "             Substr(Form_Acyear, 1, 4) || '-' || Substr(Form_Acyear, 7, 2) as Previous_year,  \r\n"
						+ "               ").concat(StrLocColumn1)
						.concat(" \r\n" + "             'India' As Country, \r\n"
								+ "             	(SELECT  Substr(Trim(adr_adline1) || ' ' || Trim(adr_adline2),1,60)  \r\n"
								+ "             		from address  \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as Flat_door_Premises,  \r\n"
								+ "             	(SELECT  adr_adline3  \r\n"
								+ "             		from address   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as Road_lane,  \r\n"
								+ "             	(SELECT  adr_pincode  \r\n"
								+ "             		from v_address_decode   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as Pin,  \r\n"
								+ "             	(SELECT  adr_adline4  \r\n"
								+ "             		from address   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as PostOffice,  \r\n"
								+ "             	(SELECT  adr_adline4  \r\n"
								+ "             		from address   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as Area_locality,  \r\n"
								+ "             	(SELECT  initcap(Trim(adr_city))  \r\n"
								+ "             		from v_address_decode   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as District,  \r\n"
								+ "             	(SELECT  initcap(Trim(adr_state))  \r\n"
								+ "             		from v_address_decode   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "') as State,  \r\n"
								+ "             	NVL((SELECT  adr_email  \r\n"
								+ "             		from v_address_decode   \r\n"
								+ "             		Where adr_adowner = '" + companyCode.trim()
								+ "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
								+ AdSegment.PARTY.toString() + "'),'') as email,  \r\n" + "                 ")
						.concat(StrLocColumn2).concat(" \r\n")
				+ "             (SELECT ( Case when (trim(adr_phoneoff) = '' or adr_phoneoff IS NULL ) then '9999999999' else trim(adr_phoneoff) end)  \r\n"
				+ "             		from v_address_decode   \r\n" + "             		Where adr_adowner = '"
				+ companyCode.trim() + "'|| Form_Depositor  \r\n" + "             		AND adr_adsegment = '"
				+ AdSegment.PARTY.toString() + "') as Mobile,  \r\n"
				+ "             Case When Form_AssessedYN = 'Y' then 'Yes' else 'No' end as Form_AssessedYN,  \r\n"
				+ "             Case when Form_AssessedYN = 'Y' then Trim(Form_AssessYear) || '-' || To_char(to_number(Substr(Form_AssessYear, 3, 2))+ 1) else '' end as Form_AssessYear,  \r\n"
				+ "             Form_EstimatedIncome,  \r\n" + "             Form_EstimatedTotIncome,  \r\n"
				+ "             Form_TotalFormFiled,  \r\n" + "             Form_IncomeFormFiled,  \r\n"
				+ "             To_char(Form_DateDeclReceived,'dd-Mon-yy') as Form_DateDeclReceived,  \r\n"
				+ "             Form_AmtIncomePaid,  \r\n"
				+ "             To_char(Form_DateIncomePaid,'dd-Mon-yy') as Form_DateIncomePaid  \r\n"
				+ "             from form15hg  \r\n"
				+ "             where substr(Form_UniqueId,1,1) = '".concat(fifteenhg).concat("'   \r\n")
				+ "             and   Form_Acyear = '".concat(accountYear).concat("'  \r\n")
				+ "             and   Form_Quarter = '".concat(quater).concat("' ");

		if (isIncomeDetails) {
			sqlQuery = " select Form_Depositor , Form_UniqueId , (select Substr(rtrim (xmlagg (xmlelement (e, Trim(dep_receiptnum) || ' ' )).extract ('//text()'), ' '),1,60) from deposit where dep_coy = '"
					.concat(companyCode)
					.concat("' and dep_depositor = Form_Depositor and (dep_disdate  is null or dep_disdate > to_date('")
					.concat(StrLocACEndDt)
					.concat("','dd/mm/yyyy')) and (dep_transfer is null or (dep_transfer > to_date('")
					.concat(StrLocACEndDt)
					.concat("','dd/mm/yyyy') AND dep_expiry_status = 'T') or  (dep_transfer < to_date('")
					.concat(StrLocACStartDt)
					.concat("','dd/mm/yyyy') AND dep_expiry_status is null )) and	dep_receiptdate <= to_date('")
					.concat(StrLocQEndDt)
					.concat("','dd/MM/yyyy')  ) as Receiptnum, 'Interest other than Interest on securities' as Nat_Income, 'Sec 194A - Interest other than Interest on securities' as Section, Form_AmtIncomePaid from form15hg\r\n")
					+ "             where substr(Form_UniqueId,1,1) = '".concat(fifteenhg).concat("'   \r\n")
					+ "             and   Form_Acyear = '".concat(accountYear).concat("'  \r\n")
					+ "             and   Form_Quarter = '".concat(quater).concat("' ");
		}
		try {
			File excelFile = ExcelUtils.INSTANCE.export(sqlQuery, dbUrl, dbUsername, dbPassword);
			
			String fileName = CommonConstraints.INSTANCE.DD_MM_YYYY_HH_MM_SS_FORMATTER.format(LocalDateTime.now())
					.concat(CommonConstraints.INSTANCE.XLS_EXTENSION);
			logger.debug("FileName :: {}", fileName);

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.body(new InputStreamResource(new FileInputStream(excelFile)));
		} catch (FileNotFoundException e) {
			logger.info("Exception :: {} ", e.getMessage());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Error while creating report.").build());
	}
}