package kraheja.commons.utils;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Glchart;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.entityentity.ActrandxEntityEntityMapper;
import kraheja.commons.mappers.entityentity.ActranhxEntityEntityMapper;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.UpdatePojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActrandxRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.ActranhxRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.sales.bean.entitiesresponse.GlchartEntityResponse;
import kraheja.sales.repository.FlatownerRepository;

@Component
public class GenericAccountingLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static GlchartRepository glchartRepository;

	private static CompanyRepository companyRepository;

	@Autowired
	private  ActrandxRepository actrandxRepository;

	@Autowired
	private  ActranhxRepository actranhxRepository;

	@Autowired
	private  ActranhRepository actranhRepository;

	@Autowired
	private  ActrandRepository actrandRepository;

	@Autowired
	private  EntityRepository entityRepository;
	
	@PersistenceContext
	private  EntityManager entityManager;
	
	private static FlatownerRepository flatOwnerRepository;


	@Autowired
	public void setEntityRepository(GlchartRepository glchartRepository, CompanyRepository companyRepository, FlatownerRepository flatOwnerRepository) {
		GenericAccountingLogic.glchartRepository = glchartRepository;
		GenericAccountingLogic.companyRepository = companyRepository;
		GenericAccountingLogic.flatOwnerRepository = flatOwnerRepository;
	}

	public static Double getSumDebit(String prop, String coy, String bldgCode, String acMajor) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder accountYear = new StringBuilder();
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		System.out.println("Financial month : " + month);
		if (month < 3) {
			accountYear.append(year - 1);
			accountYear.append(year);
		} else {
			accountYear.append(year);
			accountYear.append(year + 1);
		}
		LOGGER.info("Accounting Year :: {} ",accountYear);
		String	fromDate = "01/04/".concat(accountYear.substring(0, 4));
		String	toDate = "31/03/".concat(accountYear.substring(4, accountYear.length()));

		Double DecLocTotDebitto = CommonResultsetGenerator.getNumericSingleQueryValue("SELECT	nvl(sum(ac_amount),0) FROM v_acbal_actd_incl_reverse WHERE ac_proprietor= '".concat(prop).concat("' AND ac_company = '").concat(coy).concat("' AND \r\n")
				+ "ac_acmajor= '".concat(acMajor).concat("' AND ac_acminor= '").concat(bldgCode).concat("' AND ac_project IN (SELECT bldg_project FROM building \r\n")
				+ "WHERE bldg_code = ac_acminor AND '".concat(toDate).concat("' BETWEEN bldg_opendate AND bldg_closedate) AND\r\n")
				+ "trunc(ac_trandate) BETWEEN '".concat(fromDate).concat("' \r\n")
				+ "AND '".concat(toDate).concat("'"));
		
//		Double DecLocTotDebitto =(Double) ltdQuery.getSingleResult();
		return DecLocTotDebitto;
	}
	public static String acMinorStringBuilder(String acNum, String partyCode, String minorCode, GlchartEntityResponse glchartEntity) {
		String acMinor = "";
		LOGGER.info("GlchartEntityResponse: {} ",glchartEntity);

		//			Glchart glchartEntity = glchartRepository.findBychartAcnum(acNum);
		//			LOGGER.info("GlchartEntity :: {} ",glchartEntity);

		if(Objects.nonNull(glchartEntity)) {
			if( glchartEntity.getPb1Start()== 0 && glchartEntity.getPb1Len() == 0 &&
					glchartEntity.getPb2Start() == 0 && glchartEntity.getPb2Len() == 0 &&
							glchartEntity.getMb1Start() == 0 && glchartEntity.getMb1Len() == 0)
				return CommonConstraints.INSTANCE.BLANK_STRING;
			else {
				String strLocpbit1, strLocpbit2 = "", strLocmbit1 = "";
				if(partyCode.length() >= glchartEntity.getPb1Len())
					strLocpbit1 = partyCode.substring(0, glchartEntity.getPb1Len().intValue());
				else 
					strLocpbit1 = partyCode;

				if(minorCode.length() > 0 )  
					strLocmbit1 = minorCode; // AUXI

				 strLocpbit2 = partyCode.substring(strLocpbit1.length(), partyCode.length()); 

				 acMinor = strLocpbit1 + strLocmbit1 + strLocpbit2;
				 
//				if(StringUtils.isNotBlank(strLocpbit1))
//					acMinor = stringBuilderAndReplacer(acMinor, glchartEntity.getPb1Len().intValue(), glchartEntity.getPb1Start().intValue(), strLocpbit1);
//				else if(StringUtils.isNotBlank(strLocpbit2))
//					acMinor = stringBuilderAndReplacer(acMinor, glchartEntity.getPb1Len().intValue(), glchartEntity.getPb1Start().intValue(),  strLocpbit2);
//				else if(StringUtils.isNotBlank(strLocmbit1))
//					acMinor = stringBuilderAndReplacer(acMinor, glchartEntity.getMb1Len().intValue(), glchartEntity.getMb1Start().intValue(), strLocmbit1);
			}
		}
		return acMinor;
	}

	private static String stringBuilderAndReplacer(String acMinor, Integer subStringStart, Integer subStringLen, String replaceStr) {
		String strLocFirstPart, strLocLastPart;

		strLocFirstPart = acMinor.substring(0, subStringStart);
		strLocLastPart = acMinor.substring(subStringStart + subStringLen + BigInteger.ONE.intValue());
		LOGGER.info("StringBuilderAndReplacer :: {}" + strLocFirstPart.concat(replaceStr).concat(strLocLastPart));

		return strLocFirstPart.concat(replaceStr).concat(strLocLastPart);
	}

	public static AccountingBean getCashFlow(String acNum, String tranType) {
		String entitySelectStatementString = "";
		String chartGroup = null;
		String chartGroupC = null;
		switch(tranType) {
		case "PF":
			entitySelectStatementString = glchartRepository.findPaygroupByCharAcnum(acNum);
			break;
		case "RC":
			entitySelectStatementString = glchartRepository.findRecgroupByCharAcnum(acNum);
			break;
		default: 
			return AccountingBean.builder().cfCode(null).cfGroup(null).build();
		}
		if(StringUtils.isNoneBlank(entitySelectStatementString)) {
			String[] columnNameArray = entitySelectStatementString.split(",");
			chartGroup = columnNameArray[0];
			chartGroupC = columnNameArray[1];
			if((!chartGroup.equals("null") || !chartGroup.equals(" ")) && chartGroup.length() >= 6) {
				chartGroup = chartGroup.substring(0, 6);
				if(chartGroup.equals("XXXXXX"))
					chartGroup = null; //As discussed with Vikram sir on 13/04/2023 we are changing it from blank string to null
			}
			if((!chartGroupC.equals("null") || !chartGroupC.equals(" ")) && chartGroupC.length() >= 6) {
				chartGroupC = chartGroupC.substring(0, 6);
				if(chartGroupC.equals("XXXXXX"))
					chartGroupC = null; //As discussed with Vikram sir on 13/04/2023 we are changing it from blank string to null
			}
		}
		return AccountingBean.builder().cfCode(chartGroup).cfGroup(chartGroupC).build();
	}

	public static AccountingBean gatherMinorPartyDetails(String strPrmACMajor, String strPrmMinType, String strPrmMinCode, String strPrmPartyType, String strPrmPartyCode,String strPrmProject, String strPriAcminor) {
		LOGGER.debug("strPrmACMajor: {} strPrmMinType: {} strPrmMinCode: {}", strPrmACMajor, strPrmMinType, strPrmMinCode);
		LOGGER.debug("strPrmPartyType: {} strPrmProject: {} strPriAcminor: {}", strPrmProject, strPrmProject, strPriAcminor);

		String strLocValidminyn, strLocValidminor, strLocPostProjOnly, strLocPostGLOnly;
		Integer intLocTotMinor, intLocFoundRow, intLocTotParties;
/**
 * <p>CHANGED IT AS PER JAP GUID INSTATE OF STRING ARRAY.</p>
 * */
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	     String closeDate1="01/01/2050";
	     LocalDateTime closeDate = LocalDate.parse(closeDate1, formatter).atStartOfDay();
		GlchartEntityResponse glchartEntity = glchartRepository.findchartMinorAndchartPostByCharAcnum(strPrmACMajor, closeDate);
		LOGGER.info("glchartEntity: {} ",glchartEntity);

//		String[] commaSepratedValuesFromGlchart = glchartEntity.split(CommonConstraints.INSTANCE.COMMA_STRING);
//		LOGGER.info("EntityArray :: {}" + commaSepratedValuesFromGlchart);

		strLocValidminyn = !glchartEntity.getChartMinoryn().equals("") ? glchartEntity.getChartMinoryn().trim() : null;
		strLocValidminor = !glchartEntity.getChartValidminors().equals("") ? glchartEntity.getChartValidminors().trim() : null;
		strLocPostProjOnly = !glchartEntity.getChartPostprojonly().equals("") ? glchartEntity.getChartPostprojonly().trim() : null;
		strLocPostGLOnly = !glchartEntity.getChartPostglonly().equals("") ? glchartEntity.getChartPostglonly().trim() : null;

		String partyTypeFromValidPartyView = "SELECT vpar_partytype FROM v_validparty WHERE  vpar_acmajor = '".concat(strPrmACMajor).concat("' AND vpar_partytype = '").concat(strPrmPartyType).concat("'");
		List<Map<String,Object>> validPartyMapList = CommonResultsetGenerator.queryToResultSetBuilder(partyTypeFromValidPartyView);
		LOGGER.info("Result set output :: {}" + CommonResultsetGenerator.queryToResultSetBuilder(partyTypeFromValidPartyView));

		if(CollectionUtils.isEmpty(validPartyMapList)) {
			strPrmPartyType = " ";
			strPrmPartyCode = " ";
		}

		if(strLocValidminyn.equals("N")) {
			strPrmMinType = " ";
			strPrmMinCode = "";
			strPriAcminor = " ";
		} else {
			List<Map<String,Object>> StrLocTotMinor = CommonResultsetGenerator.queryToResultSetBuilder("SELECT count(*) FROM v_validminor WHERE vmin_acmajor = '".concat(strPrmACMajor).concat("'"));
			if(CollectionUtils.isEmpty(StrLocTotMinor))
				intLocTotMinor = 0;
			else 
				intLocTotMinor = ((BigDecimal) StrLocTotMinor.get(0).get("COUNT(*)")).intValue();

			if(intLocTotMinor <= 0) {
				strPrmMinType = " ";
				strPrmMinCode = "";
				strPriAcminor = acMinorStringBuilder(strPrmACMajor, strPrmPartyCode, "    ", glchartEntity).trim();
				if(StringUtils.isBlank(strPriAcminor))
					strPriAcminor = strPrmPartyCode;
			}else {
				List<Map<String,Object>> StrLocFoundRow = CommonResultsetGenerator.queryToResultSetBuilder("SELECT	count(*)  FROM v_validminor WHERE vmin_acmajor = '".concat(strPrmACMajor).concat("' AND vmin_mintype = '").concat(strPrmMinType).concat("'"));
				if(CollectionUtils.isEmpty(StrLocFoundRow)) 
					intLocFoundRow = 0;
				else 
					intLocFoundRow = ((BigDecimal) StrLocTotMinor.get(0).get("COUNT(*)")).intValue();

				if(intLocFoundRow > 0) {
					if(intLocTotMinor > 0) //IntLocTotParties > 0 && TODO: 
						strPriAcminor = acMinorStringBuilder(strPrmACMajor, strPrmPartyCode, strPrmMinCode, glchartEntity).trim();
					else {
						strPriAcminor = acMinorStringBuilder(strPrmACMajor, "    ", strPrmMinCode, glchartEntity).trim();
						if(StringUtils.isBlank(strPriAcminor))
							strPriAcminor = strPrmMinCode.trim();
					}
				}
			}
		}
		//		if(StrLocPostProjOnly.equals("Y"))
		//				StrPrmProject = StrPrmProject;
		if(strLocPostProjOnly.equals("N") && strLocPostGLOnly.equals("Y"))
			strPrmProject = "GL";

		//As Discusses with Rahul Sir and Vikram Sir these are the final values to be inserted in accouting - minType and Acminor should be space.
		return AccountingBean.builder()
				.minCode(StringUtils.isNotBlank(strPrmMinCode) ? strPrmMinCode : " ")
				.minType(Objects.nonNull(strPrmMinType) ? strPrmMinType : " ")
				.Acminor(Objects.nonNull(strPriAcminor) ? strPriAcminor : " ")
				.partyCode(StringUtils.isNotBlank(strPrmPartyCode) ? strPrmPartyCode : " ")
				.partyType(StringUtils.isNotBlank(strPrmPartyType) ? strPrmPartyType : " ")
				.project(StringUtils.isNotBlank(strPrmProject) ? strPrmProject : null)
				.acMajor(strPrmACMajor)
				.build();
	}

	public static ServiceResponseBean funcGetJVNumber(String strArgComp, String dtArgJvDate) {
		Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(strArgComp.trim(), CommonUtils.INSTANCE.closeDate());
		LOGGER.info("Company :: {}" , companyEntity);   

		//coy_curryear,
		//		coy_lastjvty,
		//		coy_lastjvly,
		//		coy_lastjvl1

		String strLoccurrperiod = (companyEntity.getCoyCurryear().trim().concat("-")).concat(String.valueOf(Integer.parseInt(companyEntity.getCoyCurryear().trim())+ 1));
		LOGGER.info("StrLoccurrperiod :: {}" , strLoccurrperiod);   
		String StrLocnjvnum = null;
		LocalDate fromRequestDateInDateFormat = LocalDate.parse(dtArgJvDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		Integer strLocTranYear = fromRequestDateInDateFormat.getYear();
		Integer strLocTranMonth = fromRequestDateInDateFormat.getMonthValue();
		LOGGER.info("StrLocTranYear :: {}" , strLocTranYear);   
		LOGGER.info("S4trLocTranMonth :: {}" , strLocTranMonth);   

		String strLoctranPeriod = strLocTranMonth > 3 ?  String.valueOf(strLocTranYear).concat("-").concat(String.valueOf(strLocTranYear+ 1)) : String.valueOf(strLocTranYear - 1).concat("-").concat(String.valueOf(strLocTranYear));
		LOGGER.info("StrLoctranPeriod :: {}" , strLoctranPeriod);   

		String strLocNextYear = strLoccurrperiod.substring(5,9); 
		LOGGER.info("StrLocNextYear :: {}" , strLocNextYear);   

		String strLocNextTranYearRight = strLoctranPeriod.substring(5,9);  
		LOGGER.info("StrLocNextTranYearRight :: {}" , strLocNextTranYearRight);

		String strLocNextTranYearLeft = strLoctranPeriod.substring(0,4);  
		LOGGER.info("StrLocNextTranYearLeft :: {}" , strLocNextTranYearLeft);


		if(Integer.valueOf(strLocNextYear) <= Integer.valueOf(strLocNextTranYearRight)) {
			Integer  strLocJVYear = Integer.valueOf(strLocNextTranYearRight) - Integer.valueOf(strLocNextYear);
			if(strLocJVYear > 2) 
				return ServiceResponseBean.builder().message("Transaction data should be between 01/04/".concat(companyEntity.getCoyCurryear().trim()).concat(" and 31/03/").concat(companyEntity.getCoyCurryear().trim()).concat(".")).build();

			switch(strLocJVYear) {
			case 0:
				StrLocnjvnum = companyEntity.getCoyLastjvl1().trim().concat("/").concat(strLoctranPeriod);
				companyEntity.setCoyLastjvl1(String.valueOf(Integer.parseInt(companyEntity.getCoyLastjvl1().trim()) + 1));
				break;
			case 1:
				StrLocnjvnum = companyEntity.getCoyLastjvly().trim().concat("/").concat(strLoctranPeriod);
				companyEntity.setCoyLastjvly(String.valueOf(Integer.parseInt(companyEntity.getCoyLastjvly().trim()) + 1));
				break;
			case 2:
				StrLocnjvnum = companyEntity.getCoyLastjvty().trim().concat("/").concat(strLoctranPeriod);
				companyEntity.setCoyLastjvty(String.valueOf(Integer.parseInt(companyEntity.getCoyLastjvty().trim()) + 1));
				break;
			}

			String strLocfromdate = "1 - APR - ".concat(strLocNextTranYearLeft.trim());
			String strLocTodate = "31 - MAR - ".concat(strLocNextTranYearRight.trim());

			List<Map<String,Object>> StrLocFoundRow = CommonResultsetGenerator.queryToResultSetBuilder("SELECT COUNT(*) FROM actranh WHERE acth_transer like '%' AND	acth_coy = '".concat(strArgComp).concat("' AND	acth_trantype = 'JV' AND acth_trandate Between '").concat(strLocfromdate).concat("' and '".concat(strLocTodate).concat("' AND acth_vounum = '").concat(StrLocnjvnum).concat("'")));
			LOGGER.info("StrLocFoundRow :: {}" , StrLocFoundRow);

			Integer intLocFoundRow = ((BigDecimal) StrLocFoundRow.get(0).get("COUNT(*)")).intValue();
			LOGGER.info("StrLocFoundRow :: {}" , StrLocFoundRow);
			if(intLocFoundRow == 0) {
				companyRepository.save(companyEntity);
				return ServiceResponseBean.builder().data(StrLocnjvnum).build();
			}
			else 
				return ServiceResponseBean.builder().message("Jvnumber ".concat(StrLocnjvnum).concat(" is already entered, please check in JV listing.")).build();

		}else 
			return ServiceResponseBean.builder().message("Transaction Date can't be in previous / current financial year.").build();
	}

	public String getGstTranDate(String tranDate, String ser, Boolean isUpdate) {
		if(isUpdate) {
			Query ltdQuery = this.entityManager.createNativeQuery("SELECT to_char(acth_trandate,'DD/MM/YYYY') FROM actranh WHERE acth_transer = :ser");
			ltdQuery.setParameter("ser", ser);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}else {
			Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:tranDate,'DD/MM/YYYY'))), 'DD/MM/YYYY') from dual");

			ltdQuery.setParameter("tranDate", tranDate);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}
	}

	public String getGstTranDateYYYYMM(String tranDate, String ser, Boolean isUpdate) {
		if(isUpdate) {
			Query ltdQuery = entityManager.createNativeQuery("SELECT to_char(TO_DATE(acth_trandate,'DD/MM/YYYY'),'YYYYMM') FROM actranh WHERE acth_transer = :ser");
			ltdQuery.setParameter("ser", ser);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}else {
			Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:tranDate,'DD/MM/YYYY'))),'YYYYMM') from dual");

			ltdQuery.setParameter("tranDate", tranDate);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}
	}


	public  void updateActranh(String tranSer, String tranDate, String ledgCode, String partyType, String partyCode, Double tranAmt, String vouNum, String vouDate,  
			String postedYn, String balancedYn, String closingjvYn, String bbookYn, String cbookYn,	String narration, String coy, String clearacYn, String reverseYn, String provYn,String tranType, Boolean isUpdate) {
		Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(coy, CommonUtils.INSTANCE.closeDate());
		if(isUpdate) {
			Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndActranhCK_ActhCoy(tranSer, coy);
			if(Objects.nonNull(actranh)) {
				if(StringUtils.isNotBlank(actranh.getActhBbookyn()) && actranh.getActhBbookyn().trim().equals("Y")) {
					Integer counter = entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("#REVN", "#REVN").intValue();

					actranhxRepository.save(ActranhxEntityEntityMapper.addActranhxEntityPojoMapper.apply(new Object[] {actranh, counter.toString(), companyEntity.getCoyCurrepnum() + 1 }));
					companyEntity.setCoyCurrepnum(companyEntity.getCoyCurrepnum() + 1);
					companyRepository.save(companyEntity);
					List<Actrand> actrandList = actrandRepository.findByActrandCK_ActdTranserAndActrandCK_ActdCoy(tranSer, coy);
					if(CollectionUtils.isNotEmpty(actrandList)) {
						actrandxRepository.saveAll(ActrandxEntityEntityMapper.addActrandxEntityPojoMapper.apply(actrandList, counter.toString()));
					}
				}
			}
			ActranhBean actranhBean = ActranhBean.builder()
					.proprietor(companyEntity.getCompanyCK().getCoyProp().trim())
					.coy(coy)
					.partycode(partyCode)
					.tranamt(tranAmt)
					.narrative(narration)
					.vounum(vouNum)
					.voudate(vouDate)
					.bbookyn("N")
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid(GenericAuditContextHolder.getContext().getUserid())
					.build(); 

			actranhRepository.save(UpdatePojoEntityMapper.updateActranhEntityPojoMapper.apply(actranh, actranhBean));
			actrandRepository.deleteActrand(tranSer);
		}else {
			actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
					.transer(tranSer)
					.trantype(tranType)
					.trandate(tranDate)	
					.ledgcode(ledgCode)
					.partytype(partyType)
					.partycode(partyCode)
					.tranamt(tranAmt)
					.voudate(vouDate)
					.vounum(vouNum)
					.postedyn(postedYn)
					.balancedyn(balancedYn)
					.closingjvyn(closingjvYn)
					.bbookyn(bbookYn)
					.cbookyn(cbookYn)
					.narrative(narration)
					.proprietor(companyEntity.getCompanyCK().getCoyProp().trim())
					.coy(coy)
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid( GenericAuditContextHolder.getContext().getUserid())
					.clearacyn("Y")
					.reverseyn(reverseYn.isEmpty()?"N":reverseYn)
					.provyn(provYn)
					.build()}));
		}
	}

	public static AccountingBean getAcMajor(String acMajor, String nonGstAcMajor, String GstXAcMajor, String nonGstXAcMajor, String gstno) {
		return StringUtils.isNotBlank(gstno) ?
				AccountingBean.builder()
				.acMajor(acMajor)
				.xAcMajor(GstXAcMajor)
				.build() :
					AccountingBean.builder()
					.acMajor(nonGstAcMajor)
					.xAcMajor(nonGstXAcMajor)
					.build();
	}

	public static List<ActrandBean>  initialiseActrandBreakups(String tranType, String acMajor, String minType, String minCode, String partyType, String partyCode, String project, String acMinor, 
			String xAcMajor, String xMinType, String xPartyType, String xPartyCode, String xProject, String xAcMinor, Double tranAmt, 
			String bldgCode, String property,String uom, String tranDate,Integer bunumCounter, String narrationMsg, String transer, String ledgCode,
			String prop, String coy, String vouNum, String vouDate, String period, String domain, String matgroup, String matcode, Double itemqty, String docnum, String docdate,
			String doctype, String docpartype, String docparcode) {

		List<ActrandBean> actrandList = new ArrayList<>();
		AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, minType, minCode, partyType, partyCode, project, acMinor);
		AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(xAcMajor, xMinType, minCode, xPartyType, xPartyCode, xProject, xAcMinor);
		AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, tranType);
		AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(xAcMajor, tranType);

		actrandList.add(insActrandBreakup(bunumCounter, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, tranAmt, bldgCode,"","", property, uom,
				transer, tranType, tranDate, ledgCode, prop, coy, vouNum, vouDate, period,"","","", narrationMsg, domain, matgroup, matcode, itemqty, docnum, docdate, doctype,
				docpartype, docparcode, bunumCounter + 1));
		actrandList.add(insActrandBreakup(bunumCounter + 1, accoutingDataForContraTran, accoutingDataForTran, accoutingDataCashFlowForContra, tranAmt * -1, bldgCode,"","", property, uom,
				transer, tranType, tranDate, ledgCode, prop, coy, vouNum, vouDate, period,"","","", narrationMsg, domain, matgroup, matcode, itemqty, docnum, docdate, doctype,
				docpartype, docparcode, bunumCounter));
		return actrandList;
	}
	/**
	 * @author sazzad.alom
	 * ADDED HERE FOR TWO FIELDS STRING PERIODFROM, STRING PERIODUPTO WHIS IS REQUIRED IN AUXILIARY
	 * */
	public static List<ActrandBean>  initialiseActrandBreakups(String tranType, String acMajor, String minType, String minCode, String partyType, String partyCode, String project, String acMinor, 
			String xAcMajor, String xMinType, String xPartyType, String xPartyCode, String xProject, String xAcMinor, Double totalAmt, Double tranAmt, 
			String bldgCode,String wing,String flatNum, String property,String uom, String tranDate,Integer bunumCounter, String narrationMsg, String transer, String ledgCode,
			String prop, String coy, String vouNum, String vouDate, String period, String periodFrom,String periodUpto,String payMode, String domain, String matgroup, String matcode, Double itemqty, String docnum, String docdate,
			String doctype, String docpartype, String docparcode) {

		List<ActrandBean> actrandList = new ArrayList<>();
		AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acMajor, minType, minCode, partyType, partyCode, project, acMinor);
		AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(xAcMajor, xMinType, minCode, xPartyType, xPartyCode, xProject, xAcMinor);
		AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acMajor, tranType);
		AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(xAcMajor, tranType);
		
		narrationMsg = setNarrative(accoutingDataForTran.getAcMajor(), totalAmt, bldgCode,wing, flatNum);
		actrandList.add(insActrandBreakup(bunumCounter, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, tranAmt, bldgCode,wing,flatNum, property, uom,
				transer, tranType, tranDate, ledgCode, prop, coy, vouNum, vouDate, period, periodFrom, periodUpto, payMode, narrationMsg, domain, matgroup, matcode, itemqty, docnum, docdate, doctype,
				docpartype, docparcode, bunumCounter + 1));
		
		narrationMsg = setNarrative(accoutingDataForContraTran.getAcMajor(), totalAmt, bldgCode,wing, flatNum);
		actrandList.add(insActrandBreakup(bunumCounter + 1, accoutingDataForContraTran, accoutingDataForTran, accoutingDataCashFlowForContra, tranAmt * -1, bldgCode,wing,flatNum, property, uom,
				transer, tranType, tranDate, ledgCode, prop, coy, vouNum, vouDate, period, periodFrom, periodUpto, payMode, narrationMsg, domain, matgroup, matcode, itemqty, docnum, docdate, doctype,
				docpartype, docparcode, bunumCounter));
		return actrandList;
	}

	public static ActrandBean insActrandBreakup(Integer buNumCounter, AccountingBean accoutingDataForTran, AccountingBean accoutingDataForContraTran,  
			AccountingBean accoutingDataCashFlow, Double tranamt, String bldgcode,String wing,String flatNum, String property, String deuom, String transer, String trantype, String trandate, String ledgCode, 
			String prop,String coy, String vounum, String voudate, String period, String periodFrom,String periodUpto,String payMode, String narrationMsg, String domain, String matgroup, String matcode, Double itemqty,
			String docnum, String docdate, String doctype, String docpartype, String docparcode, Integer contraBunumCounter) {

		//		List<ActrandBean> actrandBeanList = new ArrayList<>();

		if(StringUtils.isNotBlank(deuom))
			if(deuom.length() > 4)
				deuom = deuom.substring(0,4);
		
		//		actrandBeanList(
		return ActrandBean.builder()
				.transer(transer)
				.bunum(buNumCounter)
				.trantype(trantype)
				.trandate(trandate)
				.ledgcode(ledgCode)
				.proprietor(prop)
				.coy(coy)
				.mintype(accoutingDataForTran.getMinType())
				.partytype(accoutingDataForTran.getPartyType())
				.partycode(accoutingDataForTran.getPartyCode())
				.acmajor(accoutingDataForTran.getAcMajor())
				.acminor(accoutingDataForTran.getAcminor())
				.mincode(accoutingDataForTran.getMinCode())
				.vounum(vounum)
				.voudate(voudate)
				.tranamt(tranamt * -1)
				.period(period)
				.reffrom(periodFrom)
				.refupto(periodUpto)
				.paymode(payMode)
				.narrative(narrationMsg)
				.project(accoutingDataForTran.getProject())
				.bldgcode(bldgcode)
				.wing(wing)
				.flat(flatNum)
				.property(property)
				.domain(domain)
				.matgroup(matgroup)
				.matcode(matcode)
				.sku(deuom)
				.itemqty(itemqty)
				.cfgroup(accoutingDataCashFlow.getCfGroup())
				.cfcode(accoutingDataCashFlow.getCfCode())
				.docnum(docnum)
				.docdate(docdate)
				.doctype(doctype)
				.docpartype(docpartype)
				.docparcode(docparcode)
				//X columns (Contra entry)
				.xproject(accoutingDataForContraTran.getProject())
				.xacmajor(accoutingDataForContraTran.getAcMajor())
				.xacminor(accoutingDataForContraTran.getAcminor())
				.xmintype(accoutingDataForContraTran.getMinType())
				.xpartycode(accoutingDataForContraTran.getPartyCode())
				.xpartytype(accoutingDataForContraTran.getPartyType())
				.xreftranser(transer)
				.xrefbunum(contraBunumCounter)
				.xreftrandate(trandate)
				.site(GenericAuditContextHolder.getContext().getSite())
				.userid(GenericAuditContextHolder.getContext().getUserid())
				.today(LocalDateTime.now())
				.build()
				//				)
				;

		//		return actrandBeanList;
	}

	/**
	 * @author sazzad.alom
	 * This method is used for set narrative for specific major
	 * */
	private static String setNarrative(String acMajor, Double amt,String bldgCode, String wing,String flatNum) {
		String narative = "";
		if (acMajor.equals("11402431"))
			narative = "CGST @ 9 % on Rs." + Math.round(amt);
		
		if (acMajor.equals("11402433"))
			narative = "SGST@9% on Rs." + Math.round(amt);
		
		if (acMajor.equals("11402435"))
			narative = "IGST @ 18% on Rs." + Math.round(amt);
		if(acMajor.equals("20404561")) {
			String ownerId = (bldgCode+wing+flatNum).trim();
			String flatOwnerName = flatOwnerRepository.getFlatOwnerName(ownerId);
			
			LOGGER.debug("flatOwnerName  : " + flatOwnerName + "length : " +  flatOwnerName.length());
			
			if (flatOwnerName.length() >= 22) {
				narative = "TDS DED. BY " + flatOwnerName.substring(0, 21) + "#" + flatNum.trim();
			}else {
				narative = "TDS DED. BY " + flatOwnerName + "#" + flatNum.trim();
			}
		}
		LOGGER.debug("narative : {}", narative);

		return narative;
		
	}

	public  void insertIntoAccountingTableForBillReversal(String transerNo, String newTranserNo, boolean isDebitNote, String partyCode, String partyType, String narrationFor) {
		Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndReverseYN(transerNo, "'Y'");
		LOGGER.info("Actranh Entity: {}" ,actranh);
		List<Actrand> actrandList = actrandRepository.findActdByTranserNo(transerNo.trim());
		LOGGER.info("Actrand List Size: {} " , actrandList.size());
		if(Objects.nonNull(actranh) && !actrandList.isEmpty()) {
			ActranhBean actranhBean = ActranhBean.builder()
					.balancedyn(Objects.nonNull(actranh.getActhBalancedyn()) ? actranh.getActhBalancedyn() : null) 
					.bankcode(Objects.nonNull(actranh.getActhBankcode()) ? actranh.getActhBankcode() : null)
					.bbookyn(Objects.nonNull(actranh.getActhBbookyn()) ? actranh.getActhBbookyn() : null)
					.cbookyn(Objects.nonNull(actranh.getActhCbookyn()) ? actranh.getActhCbookyn() : null)
					.clearacyn(Objects.nonNull(actranh.getActhClearacyn()) ? actranh.getActhClearacyn() : null)				
					.closingjvyn(Objects.nonNull(actranh.getActhClosingjvyn()) ? actranh.getActhClosingjvyn() : null)
					.coy(actranh.getActranhCK().getActhCoy())
					.crepno(Objects.nonNull(actranh.getActhCrepno()) ? actranh.getActhCrepno() : 0)
					.ledgcode(Objects.nonNull(actranh.getActhLedgcode()) ? actranh.getActhLedgcode() : null)
					//					.narrative(("Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'").length() > 60 ? ("Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'").substring(0, 60): "Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'" )
					.narrative(("Reversal of Transer# '" +transerNo+ "' for " + narrationFor + " '" +actranh.getActhVounum().trim()+"'").length() > 60 ? ("Reversal of Transer# '" +transerNo+ "' for " + narrationFor + " '" +actranh.getActhVounum().trim()+"'").substring(0, 60): "Reversal of Transer# '" +transerNo+ "' for " + narrationFor + " '" +actranh.getActhVounum().trim()+"'" )
					.partytype(Objects.nonNull(actranh.getActhPartytype()) ? actranh.getActhPartytype() : null)
					.partycode(Objects.nonNull(actranh.getActhPartycode()) ? actranh.getActhPartycode() : null)
					.postedyn(Objects.nonNull(actranh.getActhPostedyn()) ? actranh.getActhPostedyn() : null)
					.proprietor(actranh.getActhProprietor())
					.provyn(Objects.nonNull(actranh.getActhProvyn()) ? actranh.getActhProvyn() : null)
					.reverseyn("Y")				
					.site(GenericAuditContextHolder.getContext().getSite().trim())
					.userid(GenericAuditContextHolder.getContext().getUserid().trim())
					.voudate(Objects.nonNull(actranh.getActhVoudate()) ? actranh.getActhVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.vounum(Objects.nonNull(actranh.getActhVounum()) ? actranh.getActhVounum() : null)
					.tranamt(Objects.nonNull(actranh.getActhTranamt()) ? actranh.getActhTranamt() * -1 : null)
					.transer(newTranserNo)
					.trantype(actranh.getActhTrantype())
					.trandate(actranh.getActhTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
					.build();	

			actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));
			List<ActrandBean> actrandBeanList = new ArrayList<>();

			for(int i =0 ; i < actrandList.size() ; i++) {
				actrandBeanList.add(ActrandBean.builder()
						.acmajor(actrandList.get(i).getActdAcmajor())
						.acminor(Objects.nonNull(actrandList.get(i).getActdAcminor()) ?  actrandList.get(i).getActdAcminor() : " " )
						.bldgcode(Objects.nonNull(actrandList.get(i).getActdBldgcode()) ? actrandList.get(i).getActdBldgcode() : null)
						.bunum(Objects.nonNull(actrandList.get(i).getActrandCK().getActdBunum()) ? actrandList.get(i).getActrandCK().getActdBunum() : null)
						.cfcode(Objects.nonNull(actrandList.get(i).getActdCfcode()) ?  actrandList.get(i).getActdCfcode() : null)
						.cfgroup(Objects.nonNull(actrandList.get(i).getActdCfgroup()) ? actrandList.get(i).getActdCfgroup() : null)
						.contrabunum(Objects.nonNull(actrandList.get(0).getActdContrabunum()) ? actrandList.get(0).getActdContrabunum() : null)
						.contract(Objects.nonNull(actrandList.get(i).getActdContract()) ?  actrandList.get(i).getActdContract() : null )
						.coy(actrandList.get(0).getActrandCK().getActdCoy())
						.docdate(Objects.nonNull(actrandList.get(i).getActdDocdate()) ? actrandList.get(i).getActdDocdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.docnum(Objects.nonNull(actrandList.get(i).getActdDocnum()) ? actrandList.get(i).getActdDocnum()  : null )
						.docparcode(Objects.nonNull(actrandList.get(i).getActdDocparcode()) && !isDebitNote ? actrandList.get(i).getActdDocparcode() : partyCode )
						.docpartype(Objects.nonNull(actrandList.get(i).getActdDocpartype()) && !isDebitNote ? actrandList.get(i).getActdDocpartype() : partyType)
						.doctype(Objects.nonNull(actrandList.get(i).getActdDoctype()) ? actrandList.get(i).getActdDoctype() : null )
						.domain(Objects.nonNull(actrandList.get(i).getActdDomain()) ? actrandList.get(i).getActdDomain() : null )
						.flat(Objects.nonNull(actrandList.get(i).getActdFlat()) ? actrandList.get(i).getActdFlat() : null)
						.itemqty(Objects.nonNull(actrandList.get(i).getActdItemqty()) ? actrandList.get(i).getActdItemqty() : null)
						.matgroup(Objects.nonNull(actrandList.get(i).getActdMatgroup()) ? actrandList.get(i).getActdMatgroup()  : null )	
						.matcode(Objects.nonNull(actrandList.get(i).getActdMatcode()) ? actrandList.get(i).getActdMatcode() : null )
						.mincode(Objects.nonNull(actrandList.get(i).getActdMincode()) ? actrandList.get(i).getActdMincode() : null )
						.mintype((Objects.nonNull(actrandList.get(i).getActdMintype())) ? actrandList.get(i).getActdMintype() :  null)
						.oldbunum(Objects.nonNull(actrandList.get(i).getActdOldbunum()) ? actrandList.get(i).getActdOldbunum() : null)
						.ledgcode(actrandList.get(0).getActdLedgcode())
						.narrative(Objects.nonNull(actrandList.get(i).getActdNarrative()) ? actrandList.get(i).getActdNarrative() : null )
						.partycode(Objects.nonNull(actrandList.get(i).getActdPartycode()) ? actrandList.get(i).getActdPartycode() :  null)
						.partytype(Objects.nonNull(actrandList.get(i).getActdPartytype()) ? actrandList.get(i).getActdPartytype() : null )
						.paymode(Objects.nonNull(actrandList.get(i).getActdPaymode()) ? actrandList.get(i).getActdPaymode() : null )
						.period(Objects.nonNull(actrandList.get(i).getActdPeriod()) ? actrandList.get(i).getActdPeriod() : null)
						.project((Objects.isNull(actrandList.get(i).getActdProject()) ||  StringUtils.isBlank(actrandList.get(i).getActdProject().trim())) ? "GL" : actrandList.get(i).getActdProject()) 
						.property(Objects.nonNull(actrandList.get(i).getActdProperty()) ? actrandList.get(i).getActdProperty() : null)
						.proprietor(actrandList.get(0).getActdProprietor())
						.reffrom(Objects.nonNull(actrandList.get(i).getActdReffrom()) ? actrandList.get(i).getActdReffrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
						.refupto(Objects.nonNull(actrandList.get(i).getActdRefupto()) ? actrandList.get(i).getActdRefupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null  )
						.sku(Objects.nonNull(actrandList.get(i).getActdSku()) ? actrandList.get(i).getActdSku() : null )
						.tranamt(actrandList.get(i).getActdTranamt() * -1)
						.trandate(actrandList.get(i).getActdTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.transer(newTranserNo)
						.trantype(actrandList.get(i).getActdTrantype())
						.vounum(actrandList.get(i).getActdVounum())
						.voudate(actrandList.get(i).getActdVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
						.workcode(Objects.nonNull(actrandList.get(i).getActdWorkcode()) ? actrandList.get(i).getActdWorkcode() : null )
						.workgroup(Objects.nonNull(actrandList.get(i).getActdWorkgroup()) ? actrandList.get(i).getActdWorkgroup() : null)						
						.wing(Objects.nonNull(actrandList.get(i).getActdWing()) ? actrandList.get(i).getActdWing() : null )
						.xacmajor(Objects.nonNull(actrandList.get(i).getActdXacmajor()) ?  actrandList.get(i).getActdXacmajor() : null)
						.xacminor(Objects.nonNull(actrandList.get(i).getActdXacminor()) ? actrandList.get(i).getActdXacminor() : null )
						.xrefbunum(Objects.nonNull(actrandList.get(i).getActdXrefBunum()) && !isDebitNote ?  actrandList.get(i).getActdXrefBunum() : null)
						.xmintype(Objects.nonNull(actrandList.get(i).getActdXmintype()) ? actrandList.get(i).getActdXmintype() : null )
						.xpartycode(Objects.nonNull(actrandList.get(i).getActdXpartycode())  && !isDebitNote ? actrandList.get(i).getActdXpartycode()  : null )
						.xpartytype(Objects.nonNull(actrandList.get(i).getActdXpartytype())  && !isDebitNote ?  actrandList.get(i).getActdXpartytype() : null)
						.xproject(Objects.nonNull(actrandList.get(i).getActdXproject()) ? actrandList.get(i).getActdXproject()  : null )
						.xreftrandate(Objects.nonNull(actrandList.get(i).getActdXrefTrandate()) && !isDebitNote ? actrandList.get(i).getActdXrefTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
						.xreftranser(Objects.nonNull(actrandList.get(i).getActdXrefTranser()) && !isDebitNote ? actrandList.get(i).getActdXrefTranser() : null )
						.site(GenericAuditContextHolder.getContext().getSite().trim())
						.userid(GenericAuditContextHolder.getContext().getUserid().trim())
						.build());
			}	
			actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));	
		}

	}

}