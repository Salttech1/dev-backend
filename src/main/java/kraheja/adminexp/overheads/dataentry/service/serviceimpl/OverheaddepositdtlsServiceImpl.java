package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheaddepositdtlsRequestBean;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.Overheaddepositdtls;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.mappers.OverheaddepositdtlsEntityPojoMapper;
import kraheja.adminexp.overheads.dataentry.repository.OverheadconsRepository;
import kraheja.adminexp.overheads.dataentry.repository.OverheaddepositdtlsRepository;
import kraheja.adminexp.overheads.dataentry.repository.OverheadmeterRepository;
import kraheja.adminexp.overheads.dataentry.service.OverheadconsService;
import kraheja.adminexp.overheads.dataentry.service.OverheaddepositdtlsService;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;

@Service
@Transactional
public class OverheaddepositdtlsServiceImpl implements OverheaddepositdtlsService {

	private static final Logger logger = LoggerFactory.getLogger(OverheaddepositdtlsServiceImpl.class);

	@Autowired
	private OverheaddepositdtlsRepository overheaddepositdtlsRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private OverheadconsService overheadconsService;

	@Autowired
	private OverheadconsRepository overheadconsRepository;

	@Autowired
	private OverheadmeterRepository overheadmeterRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	static String StrPriTranserNo = "", StrLocwhereClause = "", StrPriPartycode = "", StrPriDueFromSociety = "";
	static String StrPriBldgCode = "", StrPriCoy = "", StrPriPropritor = "", StrPriStatus = "", StrPriBillCoy = "",
			strPriPartyType = "";
	static String StrPriProjectCode = "", strPriAcmajor = "";

	@Override
	public ResponseEntity<?> fetchOverheaddepositdtlsByConnocodeAndPeriod(String connocode, String period) {
		String strLocConno = "";
		Overheaddepositdtls overheaddepositdtlsEntity = this.overheaddepositdtlsRepository
				.findByOverheaddepositdtlsCK_OhdeConnocodeAndOverheaddepositdtlsCK_OhdePeriod(connocode, period);

		strLocConno = CommonResultsetGenerator.queryToSingalValue(
				"select ohdh_connocode from overheadcons where  ohdh_conno='".concat(connocode.trim().concat("'")));

		Overheadcons overheadconsEntity = this.overheadconsRepository
				.findByOverheadconsCK_OhdhConnocode(strLocConno.trim());

		List<Overheadmeter> overheadmeterEntity = this.overheadmeterRepository
				.findByOverheadmeterCK_Ohmeconnocode(strLocConno.trim());
		logger.info("overheadmeterEntity :: {}", overheadmeterEntity);

		logger.info("OverheaddepositdtlsEntity :: {}", overheaddepositdtlsEntity);

		if (overheaddepositdtlsEntity != null) {

//			return ResponseEntity.ok(ServiceResponseBean.builder()
//					.data(OverheaddepositdtlsEntityPojoMapper.fetchOverheaddepositdtlsEntityPojoMapper
//							.apply(new Object[] { Arrays.asList(overheaddepositdtlsEntity), overheadconsEntity,
//									overheadmeterEntity }))
			
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(OverheaddepositdtlsEntityPojoMapper.fetchOverheaddepositdtlsEntityPojoMapper
							.apply(new Object[] { Arrays.asList(overheaddepositdtlsEntity), overheadconsEntity,
									null }))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No record found for your selections in Overheaddepositdtls").build());
	}
		
	@Override
	public ResponseEntity<?> addOverheaddepositdtls(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean)
			throws ParseException {

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String StrPriUserID = overheaddepositdtlsRequestBean.getUserid();

		String StrPriTodate = LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);

		List<String> ActranhdValue = new ArrayList<>();
		ActranhdValue = RetriveValueFromOverheadBean(overheaddepositdtlsRequestBean);

		// ACTRANh Transaction
		List<ActranhBean> actranhBeanList = new ArrayList<>();
		actranhBeanList = InsertDataInActranh(overheaddepositdtlsRequestBean, siteFromDBEntity, StrPriUserID,
				StrPriTodate, "", "");
		// ACTRAND Transaction
		List<ActrandBean> actrandBeanList = InsertdataInActrand(overheaddepositdtlsRequestBean, siteFromDBEntity,
				StrPriUserID, StrPriTodate, "", "");
//		this.actranhRepository
//				.saveAllAndFlush(AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
//		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

		this.actranhRepository
		.saveAll(AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

		overheaddepositdtlsRequestBean.setTranser(StrPriTranserNo);
		overheaddepositdtlsRequestBean.setSite(siteFromDBEntity);

		this.overheaddepositdtlsRepository
				.save(OverheaddepositdtlsEntityPojoMapper.addOverheaddepositdtlsEntityPojoMapper
						.apply(new Object[] { overheaddepositdtlsRequestBean, siteFromDBEntity }));
		
		Double totalDepositeAmt=this.overheaddepositdtlsRepository.
				fetchDepositeAmtbyConnocode(overheaddepositdtlsRequestBean.getConno().trim());
		
				return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").data(totalDepositeAmt)
						.build());
		
	}
	
	@Override
	public ResponseEntity<?>fetchDepositeAmtbyConnocode(String connocode)
	{
		Double totalDepositeAmt=this.overheaddepositdtlsRepository.
				fetchDepositeAmtbyConnocode(connocode.trim());
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.data(totalDepositeAmt).build());
		
	}
		
	@Override
	public ResponseEntity<?> updateOverheaddepositdtls(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean)
			throws ParseException {
		Overheaddepositdtls overheaddepositdtlsEntity = this.overheaddepositdtlsRepository
				.findByOverheaddepositdtlsCK_OhdeConnocodeAndOverheaddepositdtlsCK_OhdePeriod(
						overheaddepositdtlsRequestBean.getConnocode(), overheaddepositdtlsRequestBean.getPeriod());

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String StrPriUserID = overheaddepositdtlsRequestBean.getUserid();
		String StrLocPayTranser = "";
		String StrLocMaxPeriod="";
		if (Objects.nonNull(overheaddepositdtlsEntity)) {
			StrLocwhereClause = "'" + overheaddepositdtlsEntity.getOhdeConno().trim()
					+ "' and ohde_billtype= '"
					+ overheaddepositdtlsEntity.getOhdeBilltype().trim().concat("'");

					StrLocMaxPeriod = CommonResultsetGenerator
							.queryToSingalValue("Select max(ohde_period) from overheaddepositdtls where ohde_conno="
									.concat(StrLocwhereClause.trim()));
					if (!StrLocMaxPeriod.equals(overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdePeriod().trim()))
					
					//if(StrLocMaxPeriod.trim()!=overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdePeriod().trim())
					{
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
								.message("Please enter Max Period For This Connection ").build());
						
					}
					else 
					{
						StrLocPayTranser = CommonResultsetGenerator.queryToSingalValue(
								"select acth_transer from actranh where  acth_postedyn='Y' and acth_reverseyn='N' and acth_transer='"
										.concat(overheaddepositdtlsEntity.getOhdeTranser().trim().concat("'")));
						
						if (StrLocPayTranser == null || StrLocPayTranser.isEmpty()) {
							if (Objects.nonNull(overheaddepositdtlsRequestBean)) 
							{
								// delete record from actrand and actranh and insert new record in actrand and
//								// actranh with new transer
								this.actrandRepository.deleteActrand(overheaddepositdtlsEntity.getOhdeTranser().trim());
								this.actranhRepository.deleteActranh(overheaddepositdtlsEntity.getOhdeTranser().trim());
								// StrPriTranserNo=GenericCounterIncrementLogicUtil.generateTranNo("#TSER",
								// "#P");
		
								List<String> ActranhdValue = RetriveValueFromOverheadBean(overheaddepositdtlsRequestBean);

								overheaddepositdtlsRequestBean.setTranser(StrPriTranserNo);

								this.overheaddepositdtlsRepository
										.save(OverheaddepositdtlsEntityPojoMapper.updateOverheaddepositdtlsEntityPojoMapper
												.apply(overheaddepositdtlsEntity, overheaddepositdtlsRequestBean));

								List<ActranhBean> actranhBeanList = new ArrayList<>();
								actranhBeanList = InsertDataInActranh(overheaddepositdtlsRequestBean, siteFromDBEntity,
										StrPriUserID, "", "", "");

								// ACTRAND Transaction

								List<ActrandBean> actrandBeanList = InsertdataInActrand(overheaddepositdtlsRequestBean,
										siteFromDBEntity, StrPriUserID, "", "", "");
		
								this.actranhRepository.saveAllAndFlush(
										AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
								
								this.actrandRepository
										.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
		
		
								Double totalDepositeAmt=this.overheaddepositdtlsRepository.
										fetchDepositeAmtbyConnocode(overheaddepositdtlsRequestBean.getConno().trim());
								
								return ResponseEntity.ok(
										ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").data(totalDepositeAmt).build());
							}
							else 
							{
								return ResponseEntity
										.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
							}
							
						}
						else {
							return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
									.message("You Cannot Modify Data.Payment Already Posted ").build());
						}
					}
					}
		else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());	
		}

	}
	
	@Override
	public ResponseEntity<?> deleteOverheaddepositdtls(String connocode, String period) throws ParseException {
		Overheaddepositdtls overheaddepositdtlsEntity = this.overheaddepositdtlsRepository
				.findByOverheaddepositdtlsCK_OhdeConnocodeAndOverheaddepositdtlsCK_OhdePeriod(connocode, period);

//		if(Objects.nonNull(overheaddepositdtlsEntity)) {
//
//			this.overheaddepositdtlsRepository.delete(overheaddepositdtlsEntity);
//			GenericAuditContextHolder.getContext().setTransactionNo(overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getReplace with Key Value());
//			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
//
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
//		} 
//		else {
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
//		}
		return null;
	}


@Override
	public ResponseEntity<?> checkConnocodeAndPeriodExists(String connocode, String period) {
		// return null;
		Overheaddepositdtls overheaddepositdtlsEntity = this.overheaddepositdtlsRepository
				.findByOverheaddepositdtlsCK_OhdeConnocodeAndOverheaddepositdtlsCK_OhdePeriod(connocode, period);

		if (Objects.isNull(overheaddepositdtlsEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Deposite for this Connection No And Period already Exist.").build());

	}

public static List<ActranhBean> InsertDataInActranh(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean,
			String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String StrBillEntDate, String BillNo) {

		List<ActranhBean> actranhBeanList = new ArrayList<>();

		actranhBeanList = OverheadtxnActranh.billPaymentActranh(StrPriTranserNo, StrPriPartycode, StrPriPropritor,
				StrPriCoy, "MUM", StrPriUserID, StrPriTodate, "", StrBillEntDate, BillNo,
				overheaddepositdtlsRequestBean.getDepositeamt().doubleValue(),
				overheaddepositdtlsRequestBean.getRemarks(),0.0);

		return actranhBeanList;
	}


public static List<ActrandBean> InsertdataInActrand(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean,
			String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String StrBillEntDate, String BillNo) {
		List<ActrandBean> actrandBeanList = new ArrayList<ActrandBean>();

		// *************payment actrand
		Double depositeAmount = 0.0;
		depositeAmount = overheaddepositdtlsRequestBean.getDepositeamt().doubleValue();

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriTranserNo, 1, 1, 2, 2,
				TranTypeEnum.PF.toString(), "", "GL", StrPriPropritor, StrPriCoy, PartyType.Z.toString(),
				StrPriPartycode, "20404071", " ", " ", " ", depositeAmount.doubleValue(),
				overheaddepositdtlsRequestBean.getPeriod(), "", "", StrPriProjectCode, StrPriBldgCode, "", "", "Q",
				PartyType.Z.toString(), StrPriPartycode, "GL", "80000006", " ", " ", PartyType.Z.toString(),
				StrPriPartycode, StrPriTranserNo, siteFromDBEntity, StrPriUserID,
				overheaddepositdtlsRequestBean.getRemarks()));

		// *************payment actrand contra

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriTranserNo, 2, 2, 1, 2,
				TranTypeEnum.PF.toString(), "", "GL", StrPriPropritor, StrPriCoy, PartyType.Q.toString(), " ",
				"80000006", " ", " ", " ", (depositeAmount.doubleValue() * -1),
				overheaddepositdtlsRequestBean.getPeriod(), "", "", "GL", StrPriBldgCode, "", "", "Q",
				PartyType.Z.toString(), StrPriPartycode, StrPriProjectCode, "20404071", " ", " ",
				PartyType.Z.toString(), StrPriPartycode, StrPriTranserNo, siteFromDBEntity, StrPriUserID,
				overheaddepositdtlsRequestBean.getRemarks()));

		return actrandBeanList;

	}

public List<String> RetriveValueFromOverheadBean(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean) {
		List<String> ActranhdValue = new ArrayList<String>();

		List<Map<String, Object>> queryToResultSetBuilder = CommonResultsetGenerator.queryToResultSetBuilder(
				"select loc_bldgcode as a,ohdh_paycoy as b,coy_prop as c,loc_duefromsociety as d,ohdh_status as e,ohdh_billcoy as f from location ,overheadcons ,company  where loc_code=ohdh_location and coy_code=ohdh_paycoy and coy_closedate='01/JAN/2050' and trim(ohdh_connocode)='"
						.concat(overheaddepositdtlsRequestBean.getConno().trim().concat("'")));

		logger.info("OverheadconsDetls :: {}", queryToResultSetBuilder);

		ValueContainer<List<Object>> overheadconsdlts = new ValueContainer<List<Object>>();
		List<Object> valueList = new ArrayList<Object>();
		queryToResultSetBuilder.get(0).entrySet().stream().map(valueMap -> {
			valueList.add(valueMap.getValue());
			overheadconsdlts.setValue(valueList);
			return valueMap;
		}).collect(Collectors.toList());
		StrPriBldgCode = Objects.nonNull(overheadconsdlts.getValue().get(0))
				? overheadconsdlts.getValue().get(0).toString()
				: "";
		StrPriCoy = overheadconsdlts.getValue().get(1).toString();
		StrPriPropritor = overheadconsdlts.getValue().get(2).toString();
		StrPriDueFromSociety = Objects.nonNull(overheadconsdlts.getValue().get(3))
				? overheadconsdlts.getValue().get(3).toString()
				: "";
		StrPriStatus = Objects.nonNull(overheadconsdlts.getValue().get(4))
				? overheadconsdlts.getValue().get(4).toString()
				: "";
		StrPriBillCoy = Objects.nonNull(overheadconsdlts.getValue().get(5))
				? overheadconsdlts.getValue().get(5).toString()
				: "";
		// StrPriWithExcel
		if (overheadconsdlts.getValue().get(3) == null) {
			StrPriDueFromSociety = "";
		} else {
			StrPriDueFromSociety = overheadconsdlts.getValue().get(2).toString();
		}
		String StrLocBillPeriod = "";
//			StrLocwhereClause=" ohdd_connocode='" + overheaddepositdtlsRequestBean.getConnocode().trim()+"' and ohdd_billperiod='" +
//					overheaddepositdtlsRequestBean.getBillperiod() + "' and trim(ohdd_supplementarybill)='" +
//					overheaddepositdtlsRequestBean.getSupplementarybill().trim().concat("'");
//
//			StrLocBillPeriod=CommonResultsetGenerator
//					.queryToSingalValue("select ohdd_billperiod from overheadtxn where "
//							.concat(StrLocwhereClause.trim()));

		StrLocwhereClause = "";

		StrLocwhereClause = "'" + overheaddepositdtlsRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim()
				+ "' and trim(ent_char1)= '"
				+ overheaddepositdtlsRequestBean.getOverheadconsRequestBean().getOhdhbillcoy().trim().concat("'");

		StrPriPartycode = CommonResultsetGenerator
				.queryToSingalValue("Select ent_char2 from entity where ent_class='OHCOY' and trim(ent_id)="
						.concat(StrLocwhereClause.trim()));

		strPriPartyType = "Z";

		if (StrPriDueFromSociety == "Y") {
			StrPriProjectCode = "GL";
		} else if (StrPriDueFromSociety == "") {
			StrPriProjectCode = "GL";
		} else {
			StrLocwhereClause = "'" + StrPriBldgCode.trim().concat("'");

			StrPriProjectCode = CommonResultsetGenerator.queryToSingalValue(
					"select bldg_project from building where bldg_code=".concat(StrLocwhereClause.trim()));

		}
		
		strPriAcmajor = strPriAcmajor.trim();
		//StrPriTranserNo = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#P");
		StrPriTranserNo=GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "PMSER", "MUM");
		ActranhdValue.add(StrPriTranserNo);
		ActranhdValue.add(strPriPartyType);
		ActranhdValue.add(StrPriPartycode);
		ActranhdValue.add(StrPriPropritor);
		ActranhdValue.add(StrPriBldgCode);
		ActranhdValue.add(StrPriCoy);
		ActranhdValue.add(StrPriStatus);
		ActranhdValue.add(StrPriProjectCode);
		// ActranhdValue.add(StrPriDueFromSociety);
		// ActranhdValue.add(strPriAcmajor);

		return ActranhdValue;
	}
}



