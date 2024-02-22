package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.adminexp.overheads.dataentry.bean.OverheadconsPrvBillDetailsBean;
import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsExcelRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.request.OverheadtxnRequestBean;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.entity.Overheadtxn;
import kraheja.adminexp.overheads.dataentry.mappers.OverheadtxnEntityPojoMapper;
import kraheja.adminexp.overheads.dataentry.repository.OverheadconsRepository;
import kraheja.adminexp.overheads.dataentry.repository.OverheadmeterRepository;
import kraheja.adminexp.overheads.dataentry.repository.OverheadtxnRepository;
import kraheja.adminexp.overheads.dataentry.service.OverheadconsService;
import kraheja.adminexp.overheads.dataentry.service.OverheadtxnService;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.DetnarrBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.DetnarrMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.DetnarrRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;

@Service
@Transactional
public class OverheadtxnServiceImpl implements OverheadtxnService {
	static String StrPriCoy = "", StrPriPartycode = "", strPriPartyType = "";
	static String StrPriPropritor = "", StrPriDueFromSociety = "", StrPriBldgCode = "", StrPriStatus = "";
	static String StrPriProjectCode = "", strPriAcmajor = "";
	static String StrPriTranserNo = "", StrPriBillTranserNo = "";
	static String StrPayAC_XACMINOR = "", StrPayAC_XMINTYPE = "", StrPayAC_XPARTYTYPE = "", StrPayAC_XPARTYCODE = "";
	static String StrBillAC_XMINTYPE = "", StrBillAC_XACMINOR = "", StrBillAC_XPARTYTYPE = "",
			StrBillAC_XPARTYCODE = "";
	static String StrLocwhereClause = "";
	static String StrPriWithExcel = "", StrPriBillCoy = "";
	static Integer Counter = 0;
	static Double intTotalBillamt = 0.0;
	static Double intTotalAdvance = 0.0;
	static Double intTotalintrest = 0.0;
	static Double intPayamountNumber = 0.0;

	private static final Logger logger = LoggerFactory.getLogger(OverheadtxnServiceImpl.class);

	@Autowired
	private OverheadtxnRepository overheadtxnRepository;

	@Autowired
	private OverheadmeterRepository overheadmeterRepository;

	@Autowired
	private OverheadconsService overheadconsService;

	@Autowired
	private OverheadconsRepository overheadconsRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private DetnarrRepository detnarrRepository;

	@Autowired
	private EntityManager entityManager;

	// FETCH DATA WITH CONNECTION AND BILL PERIOD
	@Override
	public ResponseEntity<?> fetchOverheadtxnByConnocodeAndBillperiodAndSupplementarybill(String connocode,
			String billperiod, String supplementarybill) {

		Overheadtxn overheadtxnEntity = this.overheadtxnRepository
				.findByOverheadtxnCK_OhddConnocodeAndOverheadtxnCK_OhddBillperiodAndOverheadtxnCK_OhddSupplementarybill(
						connocode.trim(), billperiod.trim(), supplementarybill.trim());
		logger.info("OverheadtxnEntity :: {}", overheadtxnEntity);

		Overheadcons overheadconsEntity = this.overheadconsRepository
				.findByOverheadconsCK_OhdhConnocode(connocode.trim());

		List<Overheadmeter> overheadmeterEntity = this.overheadmeterRepository
				.findByOverheadmeterCK_Ohmeconnocode(connocode.trim());
		logger.info("overheadmeterEntity :: {}", overheadmeterEntity);

		// logger.info("Overhead Connection :: {}" , overheadconsEntity);

		if (overheadtxnEntity != null) {

			String StrLocPayTranser = "";
			StrLocPayTranser = CommonResultsetGenerator.queryToSingalValue(
					"select acth_transer from actranh where  acth_postedyn='Y' and acth_reverseyn='N' and acth_transer='"
							.concat(overheadtxnEntity.getOhddTranser().trim().concat("'")));

			if (StrLocPayTranser == null || StrLocPayTranser.isEmpty()) {
				return ResponseEntity.ok(ServiceResponseBean.builder()
						.data(OverheadtxnEntityPojoMapper.fetchOverheadtxnEntityPojoMapper.apply(new Object[] {
								Arrays.asList(overheadtxnEntity), overheadconsEntity, overheadmeterEntity }))
						.status(Boolean.TRUE).build());
			} else {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("Can Not Modify Bill Already Posted ").build());
			}

		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("No record found for your selections in Overheadtxn").build());
		}

	}

	// FETCH ALL DATA FRO THAT PERTICULAR CONNNECTION
	@Override
	public ResponseEntity<?> fetchOverheadtxnByConnocode(String connocode) {
		List<Overheadtxn> overheadtxnEntity = this.overheadtxnRepository.findByOverheadtxnCK_OhddConnocode(connocode);

		overheadtxnEntity = overheadtxnEntity.stream().sorted((p1, p2) -> p2.getOverheadtxnCK().getOhddBillperiod()
				.compareTo(p1.getOverheadtxnCK().getOhddBillperiod())).collect(Collectors.toList());

//		List result =overheadtxnEntity.stream().sorted((p1, p2)->
//		p2.getOverheadtxnCK().getOhddBillperiod().
//		compareTo(p1.getOverheadtxnCK().getOhddBillperiod()))
//		.map(billperiod -> billperiod.getOverheadtxnCK().getOhddBillperiod()).collect(Collectors.toList());

		logger.info("OverheadtxnEntity :: {}", overheadtxnEntity);

		if (overheadtxnEntity != null) {
			//intTotalBillamt = overheadtxnEntity.stream().mapToDouble(overheadtxn -> overheadtxn.getOhddBillamt()).sum();
			intTotalBillamt = overheadtxnEntity.stream().mapToDouble(overheadtxn -> overheadtxn.getOhddBillamt()).sum();
			intTotalAdvance = overheadtxnEntity.stream().mapToDouble(
					overheadtxn -> Objects.nonNull(overheadtxn.getOhddAdvance()) ? overheadtxn.getOhddAdvance() : 0.0)
					.sum();
			intTotalintrest = overheadtxnEntity.stream().mapToDouble(
					overheadtxn -> Objects.nonNull(overheadtxn.getOhddIntrest()) ? overheadtxn.getOhddIntrest() : 0.0)
					.sum();

			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(OverheadtxnEntityPojoMapper.fetchOverheadtxnConnocodeEntityPojoMapper
							.apply(new Object[] { overheadtxnEntity }))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No record found for your selections in Overheadtxn").build());
	}

	// fetch Prvious Bill data
	@Override
	public ResponseEntity<?> fetchPrvBillData(String connocode) {
		// find previous bill amount details
//		Number intTotalBillamt,intTotalAdvance,intTotalintrest		;
//		List<String> PrvBillDetailsList=new ArrayList<>();
//		PrvBillDetailsList=RetrivePrvTotalBillDetails(connocode);
//		intTotalBillamt=Double.valueOf(PrvBillDetailsList.get(0));
//		intTotalAdvance=Double.valueOf(PrvBillDetailsList.get(1));
//		intTotalintrest=Double.valueOf(PrvBillDetailsList.get(2));
		// List<Double> newList = new ArrayList<Double>();

		List<Tuple> tuplesList = this.overheadtxnRepository.fetchPrvBillData(connocode.trim());
		logger.info("Tupple List: {}", tuplesList);

		List<OverheadconsPrvBillDetailsBean> OverheadconsPrvBillDetailsBean = tuplesList.stream().map(t -> {
			return new OverheadconsPrvBillDetailsBean(t.get(0, Number.class), t.get(1, Number.class),
					t.get(2, Number.class), t.get(3, Number.class), t.get(4, Number.class), t.get(5, Number.class),
					intTotalBillamt, intTotalintrest, intTotalAdvance);

		}).collect(Collectors.toList());
		logger.info("OverheadconsPrvBillDetailsBean {} :: ", OverheadconsPrvBillDetailsBean);

		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(OverheadconsPrvBillDetailsBean).build());
	}

	// UPDATE OVERHEADTXN WITH ACCOUNT DATA
	@Override
	public ResponseEntity<?> updateOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {

		Overheadtxn overheadtxnEntity = this.overheadtxnRepository
				.findByOverheadtxnCK_OhddConnocodeAndOverheadtxnCK_OhddBillperiodAndOverheadtxnCK_OhddSupplementarybill(
						overheadtxnRequestBean.getConnocode(), overheadtxnRequestBean.getBillperiod(),
						overheadtxnRequestBean.getSupplementarybill());

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String StrPriUserID = overheadtxnRequestBean.getUserid();
		String StrPriTodate = StringUtils.isNotBlank(overheadtxnRequestBean.getTodate())
				? overheadtxnRequestBean.getTodate().toString()
				: null;
		overheadtxnRequestBean.setCustbilldate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
				? overheadtxnRequestBean.getBilentdate().toString()
				: null);
		overheadtxnRequestBean.setBilentdate(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		String StrBillEntDate = StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
				? overheadtxnRequestBean.getBilentdate().toString()
				: null;
		String BillNo = overheadtxnRequestBean.getBillno();

		if (Objects.nonNull(overheadtxnEntity)) {

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
					CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			String StrLocMaxBillPeriod = "", StrLocPayTranser = "";
			// StrLocMaxBillPeriod =
			// Cls_Common_Functions.ClsGetDescription.FuncGetDescription(" select
			// max(ohdd_billperiod) from overheadtxn where ohdd_connocode='" &
			// strOhddConncode & "'")
			StrLocMaxBillPeriod = CommonResultsetGenerator
					.queryToSingalValue("select max(ohdd_billperiod) from overheadtxn where ohdd_connocode='"
							.concat(overheadtxnRequestBean.getConnocode().trim().concat("'")));

			if (Integer.parseInt(StrLocMaxBillPeriod) > Integer.parseInt(overheadtxnRequestBean.getBillperiod())) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("Cannot Modify this bill.").build());
			} else {
				StrLocPayTranser = CommonResultsetGenerator.queryToSingalValue(
						"select acth_transer from actranh where  acth_postedyn='Y' and acth_reverseyn='N' and acth_transer='"
								.concat(overheadtxnEntity.getOhddTranser().trim().concat("'")));

				if (StrLocPayTranser == null || StrLocPayTranser.isEmpty()) {
					if (Objects.nonNull(overheadtxnRequestBean)) {
						// delete record from actrand and actranh and insert new record in actrand and
						// actranh with new transer
						this.actrandRepository.deleteActrand(overheadtxnEntity.getOhddTranser().trim());
						this.actranhRepository.deleteActranh(overheadtxnEntity.getOhddTranser().trim());
						// delete DetNarr
						this.detnarrRepository.deleteDetnarr(overheadtxnEntity.getOhddTranser().trim());

						this.actrandRepository.deleteActrand(overheadtxnEntity.getOhddBilltranser().trim());
						this.actranhRepository.deleteActranh(overheadtxnEntity.getOhddBilltranser().trim());

						// delete detnarr Bill transer
						this.detnarrRepository.deleteDetnarr(overheadtxnEntity.getOhddBilltranser().trim());

//						 code for new account entry
						List<String> ActranhdValue = RetriveValueFromOverheadBean(overheadtxnRequestBean);
						;
						overheadtxnRequestBean.setTranser(StrPriTranserNo);
						overheadtxnRequestBean.setBilltranser(StrPriBillTranserNo);
						this.overheadtxnRepository.save(OverheadtxnEntityPojoMapper.updateOverheadtxnEntityPojoMapper
								.apply(overheadtxnEntity, overheadtxnRequestBean));

						List<ActranhBean> actranhBeanList = new ArrayList<>();
						actranhBeanList = InsertDataInActranh(overheadtxnRequestBean, siteFromDBEntity, StrPriUserID,
								StrPriTodate, StrBillEntDate, BillNo);

						// ACTRAND Transaction
						List<ActrandBean> actrandBeanList = InsertdataInActrand(overheadtxnRequestBean,
								siteFromDBEntity, StrPriUserID, StrPriTodate, StrBillEntDate, BillNo);

						// detnarr Transaction
						List<DetnarrBean> detnarrBeanList = InsertdataInDetNarr(overheadtxnRequestBean,
								siteFromDBEntity, StrPriUserID, StrPriTodate, StrPriTranserNo, StrPriBillTranserNo);

						this.actranhRepository
								.saveAll(AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
						this.actrandRepository
								.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

						this.detnarrRepository.saveAll(DetnarrMapper.addDetnarrPojoEntityMapper.apply(detnarrBeanList));

						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
								.message("Updated Successfully").build());

					}
				} else {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Can Not Modify bill,Bill Already Posted ").build());
				}
			}
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("No Data Found").build());
	}
	// ************function to add excel data into overheadtxn********

	@Override
	public ResponseEntity<?> addExcelOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {
		HashMap<String, String> errorMap = new HashMap<String, String>();
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String StrPriUserID = overheadtxnRequestBean.getUserid();

		// added as on 23/05/2023
		// ValueContainer<List<String>> prvBillDataList1 = new
		// ValueContainer<List<String>>();
		List<String> valueList = new ArrayList<String>();
		for (OverheadconsExcelRequestBean overheadconsExcelRequestBean : overheadtxnRequestBean
				.getOverheadconsExcelRequestBean()) {

			String StrLocChkConnocode = "";
			StrLocwhereClause = " ohdh_conno='" + overheadconsExcelRequestBean.getConsumerNumber().concat("'");

			StrLocChkConnocode = CommonResultsetGenerator.queryToSingalValue(
					"select nvl(ohdh_conno,'') from overheadcons where ".concat(StrLocwhereClause.trim()));

			if (StrLocChkConnocode == "") {
				errorMap.put("message", "Consumer No Not found");
				return ResponseEntity.ok(
						ServiceResponseBean.builder().message(errorMap.get("message")).status(Boolean.FALSE).build());

			}

		}

//Boolean isPresent	= overheadtxnRequestBean.getOverheadconsExcelRequestBean().stream()
//		.anyMatch(m -> {
//			return m.getConsumerNumber().equals("900000820312");
//		});
//		
//logger.info("Boolean is Present :: {}", isPresent);

		StrPriWithExcel = "Y";
		// billType
		List<Overheadcons> OverheadconsList = this.overheadconsRepository
				.findByOhdhConnoInAndOhdhBilltype(overheadtxnRequestBean.getOverheadconsExcelRequestBean().stream()
						.map(conncode -> conncode.getConsumerNumber()).collect(Collectors.toSet()));

		logger.info("OverheadconsList :: {}", OverheadconsList);
		if (CollectionUtils.isNotEmpty(OverheadconsList)) {
			overheadtxnRequestBean.getOverheadconsExcelRequestBean().stream().map(overheadconsExcelRequestBean -> {
				OverheadconsList.stream().filter(f -> {
					return f.getOhdhConno().equals(overheadconsExcelRequestBean.getConsumerNumber());
				}).map(overheadcons -> {
					// Overheadtxn.builder()
					overheadtxnRequestBean.setConnocode(overheadcons.getOverheadconsCK().getOhdhConnocode());
					overheadtxnRequestBean.setBillperiod(overheadconsExcelRequestBean.getBillPeriod().toString());
					overheadtxnRequestBean.setSupplementarybill("N");
					errorMap.clear();

					StrLocwhereClause = "";
					StrLocwhereClause = " ohdd_connocode='" + overheadtxnRequestBean.getConnocode().trim()
							+ "' and ohdd_billperiod='" + overheadtxnRequestBean.getBillperiod()
							+ "' and trim(ohdd_supplementarybill)='"
							+ overheadtxnRequestBean.getSupplementarybill().trim().concat("'");

					String str = CommonResultsetGenerator.queryToSingalValue(
							"select ohdd_billperiod from overheadtxn where ".concat(StrLocwhereClause.trim()));
					logger.info("StrLocBillPeriod :: {}", str);
					logger.info("overheadconsExcelRequestBean.getBillPeriod() :: {}",
							overheadconsExcelRequestBean.getBillPeriod());
					logger.info("equals :: {}",
							str.equals(String.valueOf(overheadconsExcelRequestBean.getBillPeriod())));
					if (str.equals(String.valueOf(overheadconsExcelRequestBean.getBillPeriod()))) {
						logger.info("StrLocBillPeriod inside if:: {}");
						errorMap.put("message", "Data Already Exist");
						return overheadcons;
					}
					// find cummlative amt actual previous amt
					Double StrcumulativeAmt = 0.0, StrLocPrvActAmt = 0.0;
					String StrLocMaxBillPeriod = "";

					StrLocMaxBillPeriod = CommonResultsetGenerator
							.queryToSingalValue("select max(ohdd_billperiod) from overheadtxn where ohdd_connocode='"
									.concat(overheadtxnRequestBean.getConnocode().trim().concat("'")));

					StrLocwhereClause = "";
					StrLocwhereClause = " ohdd_connocode='" + overheadtxnRequestBean.getConnocode().trim()
							+ "' and ohdd_billperiod='" + StrLocMaxBillPeriod.concat("'");

					StrcumulativeAmt = CommonResultsetGenerator.getNumericSingleQueryValue(
							"select nvl(ohdd_cumamt,0.0) from overheadtxn where ".concat(StrLocwhereClause.trim()));
					StrcumulativeAmt = StrcumulativeAmt + overheadconsExcelRequestBean.getBillAmount().doubleValue()
							+ (Objects.nonNull(overheadconsExcelRequestBean.getCgst())
									? overheadconsExcelRequestBean.getCgst().doubleValue()
									: 0.0)
							+ (Objects.nonNull(overheadconsExcelRequestBean.getCgst())
									? overheadconsExcelRequestBean.getSgst().doubleValue()
									: 0.0);

					// overheadconsExcelRequestBean.getCgst().doubleValue()+
					// overheadconsExcelRequestBean.getSgst().doubleValue();

					StrLocPrvActAmt = CommonResultsetGenerator.getNumericSingleQueryValue(
							"select nvl(Ohdd_PrvActPay,'0.0') from overheadtxn where ".concat(StrLocwhereClause.trim()));

					// Objects.nonNull(overheadtxn.getOhddAdvance()) ?
					// overheadtxn.getOhddAdvance():0.0)

					overheadtxnRequestBean.setBillno(overheadconsExcelRequestBean.getBillNo().toString());
					overheadtxnRequestBean.setBillamt(overheadconsExcelRequestBean.getBillAmount().doubleValue());
					overheadtxnRequestBean.setPayamt(overheadconsExcelRequestBean.getTotalPayable().doubleValue());
					overheadtxnRequestBean.setUnitno(overheadconsExcelRequestBean.getUnitconsumed().toString());
					overheadtxnRequestBean.setCgst((Objects.nonNull(overheadconsExcelRequestBean.getCgst())
							? overheadconsExcelRequestBean.getCgst().doubleValue()
							: 0.0));
					overheadtxnRequestBean.setSgst((Objects.nonNull(overheadconsExcelRequestBean.getSgst())
							? overheadconsExcelRequestBean.getSgst().doubleValue()
							: 0.0));
					overheadtxnRequestBean.setAdvance(0.0);
					overheadtxnRequestBean.setNoofprint(0.0);
					overheadtxnRequestBean.setPrvadvamt(0.0);
					overheadtxnRequestBean.setIntrest(0.0);
					overheadtxnRequestBean
							.setRemarks("Bill Amt" + overheadconsExcelRequestBean.getBillAmount().doubleValue()
									+ ",Pay Amt" + overheadconsExcelRequestBean.getTotalPayable().doubleValue());
					overheadtxnRequestBean.setCumamt(StrcumulativeAmt.doubleValue());
					overheadtxnRequestBean.setPrvactpay(StrLocPrvActAmt.doubleValue());
					overheadtxnRequestBean
							.setBilentdate(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					overheadtxnRequestBean
							.setCustbilldate(StringUtils.isNotBlank(overheadconsExcelRequestBean.getBillDate())
									? overheadconsExcelRequestBean.getBillDate().toString()
									: null);
					overheadtxnRequestBean
							.setFromdate(StringUtils.isNotBlank(overheadconsExcelRequestBean.getFromdate())
									? overheadconsExcelRequestBean.getFromdate().toString()
									: null);
					overheadtxnRequestBean.setTodate(StringUtils.isNotBlank(overheadconsExcelRequestBean.getTodate())
							? overheadconsExcelRequestBean.getTodate().toString()
							: null);
					overheadcons.setOhdhBilltype(overheadconsExcelRequestBean.getBillType());
					overheadtxnRequestBean.setSite(siteFromDBEntity);
					// .build();
					if (errorMap.isEmpty()) {
						// accounting
						// String StrPriTodate=Objects.nonNull(overheadconsExcelRequestBean.getTodate())
						// ? overheadconsExcelRequestBean.getTodate().toString() : null;
						// String StrPriTodate="09/01/2022"; //LocalDateTime.now().toString();
						String StrPriTodate = StringUtils.isNotBlank(overheadtxnRequestBean.getTodate())
								? overheadtxnRequestBean.getTodate().toString()
								: null;
						String StrBillEntDate = StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
								? overheadtxnRequestBean.getBilentdate().toString()
								: null;
						String BillNo = overheadconsExcelRequestBean.getBillNo().toString();

						List<String> ActranhdValue = new ArrayList<>();
						overheadtxnRequestBean.setOverheadconsRequestBean(OverheadconsRequestBean.builder()
								.ohdhbilltype(overheadconsExcelRequestBean.getBillType()).build());
						ActranhdValue = RetriveValueFromOverheadBean(overheadtxnRequestBean);
						if (StrPriStatus == "H") {
							errorMap.put("message", "This connection is not Active");
							return overheadconsExcelRequestBean;
						}
						// ACTRANh Transaction
						List<ActranhBean> actranhBeanList = new ArrayList<>();
						actranhBeanList = InsertDataInActranh(overheadtxnRequestBean, siteFromDBEntity, StrPriUserID,
								StrPriTodate, StrBillEntDate, BillNo);

						// ACTRAND Transaction
						List<ActrandBean> actrandBeanList = InsertdataInActrand(overheadtxnRequestBean,
								siteFromDBEntity, StrPriUserID, StrPriTodate, StrBillEntDate, BillNo);

						// for detnarr transaction
						List<DetnarrBean> detnarrBeanList = InsertdataInDetNarr(overheadtxnRequestBean,
								siteFromDBEntity, StrPriUserID, StrPriTodate, StrPriTranserNo, StrPriBillTranserNo);

						this.actranhRepository.saveAllAndFlush(
								AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
						this.actrandRepository
								.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

						this.detnarrRepository.saveAll(DetnarrMapper.addDetnarrPojoEntityMapper.apply(detnarrBeanList));

						overheadtxnRequestBean.setTranser(StrPriTranserNo);
						overheadtxnRequestBean.setBilltranser(StrPriBillTranserNo);

						this.overheadtxnRepository.save(OverheadtxnEntityPojoMapper.addOverheadtxnEntityPojoMapper
								.apply(new Object[] { overheadtxnRequestBean, siteFromDBEntity }));
					}
					return overheadcons;
				}).collect(Collectors.toList());
				return overheadtxnRequestBean;
			}).collect(Collectors.toList());

			if (errorMap.isEmpty())
				errorMap.put("message", "Data Added Successfully");
			return ResponseEntity
					.ok(ServiceResponseBean.builder().message(errorMap.get("message")).status(Boolean.TRUE).build());
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data found").build());
		}
	}

	@Override
	public ResponseEntity<?> addOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
				CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String StrPriUserID = overheadtxnRequestBean.getUserid();
		String StrPriTodate = LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		overheadtxnRequestBean.setCustbilldate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
				? overheadtxnRequestBean.getBilentdate().toString()
				: null);
//		overheadtxnRequestBean.setBilentdate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
//				? overheadtxnRequestBean.getBilentdate().toString()
//				: null);
		overheadtxnRequestBean.setBilentdate(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));

		String StrBillEntDate = StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate())
				? overheadtxnRequestBean.getBilentdate().toString()
				: null;

		HashMap<String, String> errorMap = new HashMap<String, String>();
		String BillNo = overheadtxnRequestBean.getBillno();

		List<String> ActranhdValue = new ArrayList<>();
		ActranhdValue = RetriveValueFromOverheadBean(overheadtxnRequestBean);
		// Objects.nonNull(overheadconsdlts.getValue().get(0))?
		// overheadconsdlts.getValue().get(0).toString():"";

		if (StrPriStatus.equalsIgnoreCase("H")) {
			errorMap.put("message", "This Consumer No. Is disconnected. Cannot enter New Bill");
			return ResponseEntity
					.ok(ServiceResponseBean.builder().message(errorMap.get("message")).status(Boolean.TRUE).build());
			// return
			// ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This
			// Consumer No. Is disconnected. Cannot enter New Bill").build());
		}

		// ACTRANh Transaction
		List<ActranhBean> actranhBeanList = new ArrayList<>();
		actranhBeanList = InsertDataInActranh(overheadtxnRequestBean, siteFromDBEntity, StrPriUserID, StrPriTodate,
				StrBillEntDate, BillNo);

		// ACTRAND Transaction

		List<ActrandBean> actrandBeanList = InsertdataInActrand(overheadtxnRequestBean, siteFromDBEntity, StrPriUserID,
				StrPriTodate, StrBillEntDate, BillNo);
		// OverheadtxnRequestBean overheadtxnRequestBean,
		// String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String
		// StrTranser, String strBillTranser

		// for DetNarr

		List<DetnarrBean> detnarrBeanList = InsertdataInDetNarr(overheadtxnRequestBean, siteFromDBEntity, StrPriUserID,
				StrPriTodate, StrPriTranserNo, StrPriBillTranserNo);

		this.actranhRepository
				.saveAllAndFlush(AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhBeanList));
		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));

		this.detnarrRepository.saveAll(DetnarrMapper.addDetnarrPojoEntityMapper.apply(detnarrBeanList));

		overheadtxnRequestBean.setTranser(StrPriTranserNo);
		overheadtxnRequestBean.setBilltranser(StrPriBillTranserNo);
		overheadtxnRequestBean.setSite(siteFromDBEntity);

		this.overheadtxnRepository.save(OverheadtxnEntityPojoMapper.addOverheadtxnEntityPojoMapper
				.apply(new Object[] { overheadtxnRequestBean, siteFromDBEntity }));

		if (errorMap.isEmpty()) {
			errorMap.put("message", "Data Added Successfully");
			return ResponseEntity
					.ok(ServiceResponseBean.builder().message(errorMap.get("message")).status(Boolean.TRUE).build());
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data found").build());
		}

		// return
		// ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data
		// Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> deleteOverheadtxn(String connocode, String billperiod, String supplementarybill)
			throws ParseException {
		Overheadtxn overheadtxnEntity = this.overheadtxnRepository
				.findByOverheadtxnCK_OhddConnocodeAndOverheadtxnCK_OhddBillperiodAndOverheadtxnCK_OhddSupplementarybill(
						connocode, billperiod, supplementarybill);
		String StrLocPayTranser = "";
		Integer IntLocDaysDiff = 0;

		StrLocPayTranser = CommonResultsetGenerator.queryToSingalValue(
				"select nvl(acth_transer,'') from actranh where  acth_postedyn='Y' and acth_reverseyn='N' and acth_transer='"
						.concat(overheadtxnEntity.getOhddTranser().trim().concat("'")));
//		if (StrLocPayTranser != null) {
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
//					.message("Cannot modify/Delete bill,Bill already posted.").build());
//		}

		if (StrLocPayTranser != "") {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("Cannot Delete bill,Bill already posted.").build());
		}

		// find days between two date

		Period period = Period.between(overheadtxnEntity.getOhddToday().toLocalDate(),
				LocalDateTime.now().toLocalDate());
		IntLocDaysDiff = period.getDays();

		if (IntLocDaysDiff > 7) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("Bill is 7 days older,Cannot Delete bill.").build());
		}

		if (Objects.nonNull(overheadtxnEntity)) {

			this.actrandRepository.deleteActrand(overheadtxnEntity.getOhddTranser().trim());
			this.actranhRepository.deleteActranh(overheadtxnEntity.getOhddTranser().trim());
			//for detnarr table delete record
			this.detnarrRepository.deleteDetnarr(overheadtxnEntity.getOhddTranser().trim());
			
			this.actrandRepository.deleteActrand(overheadtxnEntity.getOhddBilltranser().trim());
			this.actranhRepository.deleteActranh(overheadtxnEntity.getOhddBilltranser().trim());

			//for detnarr table delete record
			this.detnarrRepository.deleteDetnarr(overheadtxnEntity.getOhddBilltranser().trim());
			
			this.overheadtxnRepository.delete(overheadtxnEntity);
			
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} else {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> checkConnocodeAndBillperiodAndSupplementarybillExists(String connocode, String billperiod,
			String supplementarybill) {

		Overheadtxn overheadtxnEntity = this.overheadtxnRepository
				.findByOverheadtxnCK_OhddConnocodeAndOverheadtxnCK_OhddBillperiodAndOverheadtxnCK_OhddSupplementarybill(
						connocode, billperiod, supplementarybill);

		if (Objects.isNull(overheadtxnEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Bill for this Connection No And Period already Exist.").build());

	}

	public List<String> RetrivePrvTotalBillDetails(String connocode) {
		String wherecondition = " ohdd_connocode='".concat(connocode.trim().concat("'"));
		String query = "select sum(OHDD_BILLAMT) as a,sum(Ohdd_Intrest) as b,sum(Ohdd_Advance) as c from overheadtxn \r\n"
				+ wherecondition;

		List<Map<String, Object>> queryToResultSetBuilder = CommonResultsetGenerator.queryToResultSetBuilder(query);

		logger.info("OverheadconsDetls :: {}", queryToResultSetBuilder);

		ValueContainer<List<String>> prvBillDataList1 = new ValueContainer<List<String>>();
		List<String> valueList = new ArrayList<String>();
		queryToResultSetBuilder.get(0).entrySet().stream().map(valueMap -> {
			valueList.add(valueMap.getValue().toString());
			prvBillDataList1.setValue(valueList);
			return valueMap;
		}).collect(Collectors.toList());

		return valueList;

	}

	public List<String> RetriveValueFromOverheadBean(OverheadtxnRequestBean overheadtxnRequestBean) {
		List<String> ActranhdValue = new ArrayList<String>();

		List<Map<String, Object>> queryToResultSetBuilder = CommonResultsetGenerator.queryToResultSetBuilder(
				"select loc_bldgcode as a,ohdh_paycoy as b,coy_prop as c,loc_duefromsociety as d,ohdh_status as e,ohdh_billcoy as f from location ,overheadcons ,company  where loc_code=ohdh_location and coy_code=ohdh_paycoy and coy_closedate='01/JAN/2050' and trim(ohdh_connocode)='"
						.concat(overheadtxnRequestBean.getConnocode().trim().concat("'")));

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
		StrLocwhereClause = " ohdd_connocode='" + overheadtxnRequestBean.getConnocode().trim()
				+ "' and ohdd_billperiod='" + overheadtxnRequestBean.getBillperiod()
				+ "' and trim(ohdd_supplementarybill)='"
				+ overheadtxnRequestBean.getSupplementarybill().trim().concat("'");

		StrLocBillPeriod = CommonResultsetGenerator
				.queryToSingalValue("select ohdd_billperiod from overheadtxn where ".concat(StrLocwhereClause.trim()));

		StrLocwhereClause = "";
		if (StrPriWithExcel == "Y") {
			// StrLocwhereClause="'"+overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim()+
			// "' and trim(ent_char1)= '" + StrPriBillCoy.trim().concat("'");
			StrLocwhereClause = "'" + overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim()
					+ "' and trim(ent_char1)= '" + StrPriBillCoy.trim().concat("'");
		} else {
			StrLocwhereClause = "'" + overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim()
					+ "' and trim(ent_char1)= '"
					+ overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbillcoy().trim().concat("'");
		}

		StrPriPartycode = CommonResultsetGenerator
				.queryToSingalValue("Select ent_char2 from entity where ent_class='OHCOY' and trim(ent_id)="
						.concat(StrLocwhereClause.trim()));

		strPriPartyType = "Z";

		if (StrPriDueFromSociety == "Y") {
			StrPriProjectCode = "GL";
		} else if (StrPriBldgCode == "") {
			StrPriProjectCode = "GL";
		} else {
			StrLocwhereClause = "'" + StrPriBldgCode.trim().concat("'");

			StrPriProjectCode = CommonResultsetGenerator.queryToSingalValue(
					"select bldg_project from building where bldg_code=".concat(StrLocwhereClause.trim()));

		}
		// for cheking due from society is present and accourdingly set acmajor

		if (StrPriDueFromSociety == "") {
			// strPriAcmajor="20404517";
			StrLocwhereClause = " ent_class='OHTYP' and ent_id=" + "'"
					+ overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim().concat("'");
			strPriAcmajor = CommonResultsetGenerator
					.queryToSingalValue("select ent_char2 from entity where ".concat(StrLocwhereClause.trim()).trim());

		} else {
			StrLocwhereClause = " ent_class='OHTYP' and ent_id=" + "'"
					+ overheadtxnRequestBean.getOverheadconsRequestBean().getOhdhbilltype().trim().concat("'");
			strPriAcmajor = CommonResultsetGenerator
					.queryToSingalValue("select ent_char1 from entity where ".concat(StrLocwhereClause.trim()).trim());
		}
		strPriAcmajor = strPriAcmajor.trim();
		// StrPriTranserNo = GenericCounterIncrementLogicUtil.generateTranNo("#TSER",
		// "#P");
		StrPriTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "PMSER", "MUM");
		// StrPriBillTranserNo =
		// GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#BO");
		StrPriBillTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "#OBL", "MUM");

		ActranhdValue.add(StrPriTranserNo);
		ActranhdValue.add(StrPriBillTranserNo);
		ActranhdValue.add(strPriPartyType);
		ActranhdValue.add(StrPriPartycode);
		ActranhdValue.add(StrPriPropritor);
		ActranhdValue.add(StrPriBldgCode);
		ActranhdValue.add(StrPriCoy);
		ActranhdValue.add(StrPriStatus);
		ActranhdValue.add(StrPriDueFromSociety);
		ActranhdValue.add(StrPriProjectCode);
		ActranhdValue.add(strPriAcmajor);

		return ActranhdValue;
	}

	public static List<ActranhBean> InsertDataInActranh(OverheadtxnRequestBean overheadtxnRequestBean,
			String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String StrBillEntDate, String BillNo) {

		List<ActranhBean> actranhBeanList = new ArrayList<>();
		Double billTotalAmount = 0.0;
		billTotalAmount = overheadtxnRequestBean.getBillamt().doubleValue()
				+ overheadtxnRequestBean.getCgst().doubleValue() + overheadtxnRequestBean.getSgst().doubleValue();

		if (overheadtxnRequestBean.getPayamt().doubleValue() > (overheadtxnRequestBean.getBillamt().doubleValue()
				+ overheadtxnRequestBean.getCgst().doubleValue() + overheadtxnRequestBean.getSgst().doubleValue())

		) {
			intPayamountNumber = overheadtxnRequestBean.getPayamt().doubleValue();
		} else {
			intPayamountNumber = overheadtxnRequestBean.getBillamt().doubleValue()
					+ overheadtxnRequestBean.getCgst().doubleValue() + overheadtxnRequestBean.getSgst().doubleValue();
		}

		// code for narration in actranh
		String Narration = "";
		String wherecondition = " ohdh_connocode='".concat(overheadtxnRequestBean.getConnocode().trim().concat("'"));
		String query = "select trim(ohdh_consumerno) ||'-' || nvl(trim(ohdh_flatnum),'') from overheadcons where \r\n"
				+ wherecondition;

		Narration = CommonResultsetGenerator.queryToSingalValue(query);
		Narration="Consumer No:" + Narration;
		
		// end

		actranhBeanList = OverheadtxnActranh.billPaymentActranh(StrPriTranserNo, StrPriPartycode, StrPriPropritor,
				StrPriCoy, siteFromDBEntity, StrPriUserID, StrPriTodate, StrPriBillTranserNo, StrBillEntDate, BillNo,
				billTotalAmount.doubleValue(), Narration, intPayamountNumber);

		return actranhBeanList;
	}

	// insert data in DetNarr

	public static List<DetnarrBean> InsertdataInDetNarr(OverheadtxnRequestBean overheadtxnRequestBean,
			String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String StrTranser,
			String strBillTranser) {

		List<DetnarrBean> detnarrBeanList = new ArrayList<DetnarrBean>();

		String dettype = "";
		String ConsumerNo = "";

		String wherecondition = " ohdh_connocode='".concat(overheadtxnRequestBean.getConnocode().trim().concat("'"));
		String query = "select trim(ohdh_consumerno) ||'-' || nvl(trim(ohdh_flatnum),'') from overheadcons where \r\n"
				+ wherecondition;

		ConsumerNo = CommonResultsetGenerator.queryToSingalValue(query);

		dettype = CommonResultsetGenerator.queryToSingalValue(
				"select sun_class from sundata where sun_id='".concat(ConsumerNo.trim().concat("'")));

		detnarrBeanList.add(OverheadtxnActrand.updatedetNarrDetails(StrPriCoy, StrTranser, 1, ConsumerNo, dettype,
				siteFromDBEntity, StrPriUserID));

		detnarrBeanList.add(OverheadtxnActrand.updatedetNarrDetails(StrPriCoy, StrTranser, 2, ConsumerNo, dettype,
				siteFromDBEntity, StrPriUserID));

		detnarrBeanList.add(OverheadtxnActrand.updatedetNarrDetails(StrPriCoy, strBillTranser, 1, ConsumerNo, dettype,
				siteFromDBEntity, StrPriUserID));

		detnarrBeanList.add(OverheadtxnActrand.updatedetNarrDetails(StrPriCoy, strBillTranser, 2, ConsumerNo, dettype,
				siteFromDBEntity, StrPriUserID));

		return detnarrBeanList;
	}

	// ActrandBean actrandBean = new ActrandBean();
	public static List<ActrandBean> InsertdataInActrand(OverheadtxnRequestBean overheadtxnRequestBean,
			String siteFromDBEntity, String StrPriUserID, String StrPriTodate, String StrBillEntDate, String BillNo) {
		List<ActrandBean> actrandBeanList = new ArrayList<ActrandBean>();

		String strPriFrom, strPriTo;

		strPriFrom = StringUtils.isNotBlank(overheadtxnRequestBean.getFromdate()) ? overheadtxnRequestBean.getFromdate()
				: null;

		strPriTo = StringUtils.isNotBlank(overheadtxnRequestBean.getTodate()) ? overheadtxnRequestBean.getTodate()
				: null;

		// *************payment actrand
		Double billTotalAmount = 0.0;
		billTotalAmount = overheadtxnRequestBean.getBillamt().doubleValue()
				+ overheadtxnRequestBean.getCgst().doubleValue() + overheadtxnRequestBean.getSgst().doubleValue();

		if (overheadtxnRequestBean.getPayamt().doubleValue() > (billTotalAmount)

		) {
			intPayamountNumber = overheadtxnRequestBean.getPayamt().doubleValue();
		} else {
			intPayamountNumber = billTotalAmount;
		}

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriTranserNo, 1, 1, 2, 2,
				TranTypeEnum.PF.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor, StrPriCoy,
				PartyType.Z.toString(), StrPriPartycode, "11401026", StrPriPartycode, " ", " ",
				intPayamountNumber.doubleValue(), overheadtxnRequestBean.getBillperiod(), strPriFrom, strPriTo,
				StrPriProjectCode, StrPriBldgCode, overheadtxnRequestBean.getBillno(),
				overheadtxnRequestBean.getBilentdate(), "Q", PartyType.Z.toString(), StrPriPartycode, "GL", "80000006",
				" ", " ", PartyType.Z.toString(), StrPriPartycode, StrPriTranserNo, siteFromDBEntity, StrPriUserID,
				""));

		// *************payment actrand contra

		if (StrPriDueFromSociety == "Y") {
			StrPayAC_XMINTYPE = "B";
			StrPayAC_XACMINOR = StrPriBldgCode;
		} else {
			StrPayAC_XMINTYPE = " ";
			StrPayAC_XACMINOR = StrPriPartycode;
		}

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriTranserNo, 2, 2, 1, 2,
				TranTypeEnum.PF.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor, StrPriCoy,
				PartyType.Q.toString(), " ", "80000006", " ", " ", " ", (intPayamountNumber.doubleValue() * -1),
				overheadtxnRequestBean.getBillperiod(), strPriFrom, strPriTo, "GL", StrPriBldgCode,
				overheadtxnRequestBean.getBillno(), overheadtxnRequestBean.getBilentdate(), "Q", PartyType.Z.toString(),
				StrPriPartycode, StrPriProjectCode, "11401026", StrPriPartycode, StrPayAC_XMINTYPE,
				PartyType.Z.toString(), StrPriPartycode, StrPriTranserNo, siteFromDBEntity, StrPriUserID, ""));

		/// ********** Actrand for bill transaction

		if (StrPriDueFromSociety == "Y") {
			StrBillAC_XMINTYPE = "B";
			StrBillAC_XACMINOR = StrPriBldgCode;
			StrBillAC_XPARTYTYPE = "Z";
			StrBillAC_XPARTYCODE = StrPriPartycode;
		} else {
			StrBillAC_XMINTYPE = " ";
			StrBillAC_XACMINOR = " ";
			StrBillAC_XPARTYTYPE = " ";
			StrBillAC_XPARTYCODE = " ";
		}

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 1, 1, 2, 2,
				TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor, StrPriCoy,
				PartyType.Z.toString(), StrPriPartycode, "11401026", StrPriPartycode, " ", " ",
				(overheadtxnRequestBean.getBillamt().doubleValue() * -1), overheadtxnRequestBean.getBillperiod(),
				strPriFrom, strPriTo, StrPriProjectCode, StrPriBldgCode, overheadtxnRequestBean.getBillno(),
				overheadtxnRequestBean.getBilentdate(), "Q", PartyType.Z.toString(), StrPriPartycode, StrPriProjectCode,
				strPriAcmajor, StrPayAC_XMINTYPE, StrPayAC_XMINTYPE, "", "", StrPriBillTranserNo, siteFromDBEntity,
				StrPriUserID, ""));

		// CONTRA ENTRY FOR BILL
		String StrBillAC_MINTYPE = "", StrBillAC_ACMINOR = "", StrBillAC_MINCODE = "";
		if (StrPriDueFromSociety == "Y") {
			StrBillAC_XMINTYPE = "B";
		} else {
			StrBillAC_XMINTYPE = " ";
		}

		actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 2, 2, 1, 1,
				TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor, StrPriCoy,
				" ", " ", strPriAcmajor, " ", " ", " ", (overheadtxnRequestBean.getBillamt().doubleValue()),
				overheadtxnRequestBean.getBillperiod(), strPriFrom, strPriTo, StrPriProjectCode, StrPriBldgCode,
				overheadtxnRequestBean.getBillno(), overheadtxnRequestBean.getBilentdate(), "Q", PartyType.Z.toString(),
				StrPriPartycode, StrPriProjectCode, "11401026", StrPriPartycode, StrPayAC_XMINTYPE,
				PartyType.Z.toString(), StrPriPartycode, StrPriBillTranserNo, siteFromDBEntity, StrPriUserID, ""));

		// *********** For BILL CGST Amount

		overheadtxnRequestBean
				.setCgst(Objects.nonNull(overheadtxnRequestBean.getCgst()) ? overheadtxnRequestBean.getCgst()
						: BigInteger.ZERO.doubleValue());
		if (overheadtxnRequestBean.getCgst() > 0) {
			actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 3, 3, 4, 4,
					TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor,
					StrPriCoy, PartyType.Z.toString(), StrPriPartycode, "11401026", " ", " ", " ",
					(overheadtxnRequestBean.getCgst().doubleValue() * -1),
					overheadtxnRequestBean.getBillperiod().toString(), strPriFrom, strPriTo, StrPriProjectCode,
					StrPriBldgCode, overheadtxnRequestBean.getBillno().toString(),
					overheadtxnRequestBean.getBilentdate(), "", PartyType.Z.toString(), StrPriPartycode,
					StrPriProjectCode, "20404391", StrPayAC_XMINTYPE, StrPayAC_XMINTYPE, " ", " ", StrPriBillTranserNo,
					siteFromDBEntity, StrPriUserID, ""));

			// ************CONTRA CGST

			actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 4, 4, 3, 3,
					TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor,
					StrPriCoy, " ", " ", "20404391", StrPriPartycode, " ", " ",
					(overheadtxnRequestBean.getCgst().doubleValue()), overheadtxnRequestBean.getBillperiod(),
					strPriFrom, strPriTo, StrPriProjectCode, StrPriBldgCode, overheadtxnRequestBean.getBillno(),
					overheadtxnRequestBean.getBilentdate().toString(), "Q", PartyType.Z.toString(), StrPriPartycode,
					StrPriProjectCode, "11401026", StrPriPartycode, StrPayAC_XMINTYPE, PartyType.Z.toString(),
					StrPriPartycode, StrPriBillTranserNo, siteFromDBEntity, StrPriUserID, ""));

			// **actrandBeanList.add SGST

			actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 5, 5, 6, 6,
					TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor,
					StrPriCoy, PartyType.Z.toString(), StrPriPartycode, "11401026", StrPriPartycode, " ", " ",
					(overheadtxnRequestBean.getSgst().doubleValue() * -1), overheadtxnRequestBean.getBillperiod(),
					strPriFrom, strPriTo, StrPriProjectCode, StrPriBldgCode, overheadtxnRequestBean.getBillno(),
					overheadtxnRequestBean.getBilentdate(), "", PartyType.Z.toString(), StrPriPartycode,
					StrPriProjectCode, "20404392", StrPayAC_XMINTYPE, StrPayAC_XMINTYPE, " ", " ", StrPriBillTranserNo,
					siteFromDBEntity, StrPriUserID, ""));

			// ************* CONTRA SGST

			actrandBeanList.add(OverheadtxnActrand.billPaymentActrand(StrPriBillTranserNo, 6, 6, 5, 5,
					TranTypeEnum.BO.toString(), overheadtxnRequestBean.getBilentdate(), "GL", StrPriPropritor,
					StrPriCoy, " ", " ", "20404392", " ", " ", " ", (overheadtxnRequestBean.getSgst().doubleValue()),
					overheadtxnRequestBean.getBillperiod(), strPriFrom, strPriTo, StrPriProjectCode, StrPriBldgCode,
					overheadtxnRequestBean.getBillno(), overheadtxnRequestBean.getBilentdate(), "Q",
					PartyType.Z.toString(), StrPriPartycode, StrPriProjectCode, "11401026", StrPriPartycode,
					StrPayAC_XMINTYPE, PartyType.Z.toString(), StrPriPartycode, StrPriBillTranserNo, siteFromDBEntity,
					StrPriUserID, ""));
		}
		return actrandBeanList;

	}

}
