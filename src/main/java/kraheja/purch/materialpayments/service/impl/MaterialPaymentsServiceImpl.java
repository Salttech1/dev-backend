package kraheja.purch.materialpayments.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.bean.response.BuildingResponseBean;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.CodeHelpPartyBean;
import kraheja.commons.bean.ExnarrBean;
import kraheja.commons.bean.request.InchqRequestBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.bean.response.CompanyResponseBean;
import kraheja.commons.bean.response.EpworksResponseBean;
import kraheja.commons.bean.response.HsnsacmasterResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.enums.HSMSTypeEnum;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.PrintStatusEnum;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.ExnarrMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.EpworksRepository;
import kraheja.commons.repository.ExnarrRepository;
import kraheja.commons.repository.HsnsacmasterRepository;
import kraheja.commons.repository.InchqRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.CurrencyConverterUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.GenericExnarrLogic;
import kraheja.commons.utils.ValueContainer;
import kraheja.feign.internal.ReportInternalFeignClient;
import kraheja.purch.bean.PreviousAuthDetailBean;
import kraheja.purch.bean.TempMatAuthPrintDetailBean;
import kraheja.purch.bean.request.Auth_DRequestBean;
import kraheja.purch.bean.request.CancelMaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialDetailRequestBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;
import kraheja.purch.bean.request.MaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialPaymentViewRequestBean;
import kraheja.purch.bean.request.PassMaterialPaymentRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.purch.bean.request.RemarkDetailRequestBean;
import kraheja.purch.bean.response.AuthDCancelMaterialBean;
import kraheja.purch.bean.response.AuthHCancelMaterialBean;
import kraheja.purch.bean.response.AuthHPassMaterialBean;
import kraheja.purch.bean.response.AuthorisationEnquiryResponseBean;
import kraheja.purch.bean.response.ItemDetailResponseBean;
import kraheja.purch.bean.response.MaterialBillDetailResponseBean;
import kraheja.purch.bean.response.MaterialPaymentStatusEnquiryResponseBean;
import kraheja.purch.bean.response.MaterialResponseBean;
import kraheja.purch.bean.response.PaidBillResponseBean;
import kraheja.purch.bean.response.TempMatAuthPrintDetailResponseBean;
import kraheja.purch.bean.response.WorkMatNarrationResponseBean;
import kraheja.purch.entity.Auth_D;
import kraheja.purch.entity.Auth_H;
import kraheja.purch.entity.Bldgmatbillfinal;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.DbnotedCK;
import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.DbnotehCK;
import kraheja.purch.entity.Matcertestimateactual;
import kraheja.purch.entity.Material;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.purch.entity.TempMatAuthprint;
import kraheja.purch.entity.TempMatAuthprintCK;
import kraheja.purch.enums.AuthTypeEnum;
import kraheja.purch.enums.BillTypeEnum;
import kraheja.purch.enums.CodeHelpTableTypeEnum;
import kraheja.purch.materialpayments.mappers.AdvrecvoucherEntityPojoMapper;
import kraheja.purch.materialpayments.mappers.Auth_DEntityPojoMapper;
import kraheja.purch.materialpayments.mappers.Auth_HEntityPojoMapper;
import kraheja.purch.materialpayments.mappers.AuthmatgroupnarrdtlEntityPojoMapper;
import kraheja.purch.materialpayments.service.MaterialPaymentsService;
import kraheja.purch.repository.AdvrecvoucherRepository;
import kraheja.purch.repository.AuthDRepository;
import kraheja.purch.repository.AuthHRepository;
import kraheja.purch.repository.AuthmatgroupnarrdtlRepository;
import kraheja.enggsys.repository.ContractdebitRepository;
import kraheja.purch.repository.BldgmatbillfinalRepository;
import kraheja.purch.repository.DbnotedRepository;
import kraheja.purch.repository.DbnotehRepository;
import kraheja.purch.repository.MatcertestimateactualRepository;
import kraheja.purch.repository.MaterialRepository;
import kraheja.purch.repository.PbilldRepository;
import kraheja.purch.repository.PbillhRepository;
import kraheja.purch.repository.TempMatAuthprintRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MaterialPaymentsServiceImpl implements MaterialPaymentsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AuthHRepository authHRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AuthDRepository authDRepository;

	@Autowired
	private PbillhRepository pbillhRepository;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private InchqRepository inchqRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private AdvrecvoucherRepository advrecvoucherRepository;

	@Autowired
	private AuthmatgroupnarrdtlRepository authmatgroupnarrdtlRepository;

	@Autowired
	private ContractdebitRepository contractdebitRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private DbnotehRepository dbnotehRepository;

	@Autowired
	private DbnotedRepository dbnotedRepository;

	@Autowired
	private ExnarrRepository exnarrRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MatcertestimateactualRepository matcertestimateactualRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private BldgmatbillfinalRepository bldgmatbillfinalRepository;

	@Autowired
	private TempMatAuthprintRepository tempMatAuthprintRepository;

	@Autowired
	private EpworksRepository epworksRepository;

	@Autowired
	private HsnsacmasterRepository hsnsacmasterRepository;

	@Autowired
	private PbilldRepository pbilldRepository;

	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;

	@Value("${report-jobs-path}")
	private String reportJobPath;

	// @Autowired
	// private EntityRepository entityRepository;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchAuthorisationEnquiry(String supplierCode, String authNos) {
		String whereCondition = "";
		if (Objects.nonNull(supplierCode) && StringUtils.isNotBlank(supplierCode)) {
			whereCondition = whereCondition + " H.auth_partycode = '".concat(supplierCode).concat("'");
		}
		if (Objects.nonNull(authNos) && StringUtils.isNotBlank(authNos)) {
			String authNoCondition = (StringUtils.isNotBlank(whereCondition) ? " and" : "")
					+ " H.auth_authnum IN (".concat(authNos).concat(") ");
			whereCondition = whereCondition + authNoCondition;
		}
		Query authEnquiryQuery = this.entityManager.createNativeQuery(
				"select A.autd_suppbillno,A.autd_suppbilldt,A.Autd_Authqty,DECODE(A.autd_authtype,'L',P.pblh_retainos,(A.autd_authamount-A.autd_retainamt+A.autd_advadj+A.autd_authtdsamt))from auth_d A left outer join auth_h H on (H.AUTH_AUTHNUM=A.AUTD_AUTHNUM) left outer join PBILLH P on (P.PBLH_SUPPBILLNO = A.AUTD_SUPPBILLNO)and (P.PBLH_SUPPBILLDT=A.AUTD_SUPPBILLDT) WHERE "
						.concat(whereCondition));
		List<Object[]> resultList = authEnquiryQuery.getResultList();
		if (!resultList.isEmpty()) {
			List<AuthorisationEnquiryResponseBean> authorisationEnquiryData = new ArrayList<>();
			for (int i = 0; i < resultList.size(); i++) {
				authorisationEnquiryData
						.add(AuthorisationEnquiryResponseBean.builder()
								.billNo(Objects.nonNull(resultList.get(i)[0]) ? resultList.get(i)[0].toString()
										: CommonConstraints.INSTANCE.BLANK_STRING)
								.billDate(Objects.nonNull(resultList.get(i)[1])
										? LocalDateTime
												.parse(resultList.get(i)[1].toString(),
														DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
												.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
										: CommonConstraints.INSTANCE.BLANK_STRING)
								.qty(Objects.nonNull(resultList.get(i)[2]) ? resultList.get(i)[2].toString()
										: CommonConstraints.INSTANCE.BLANK_STRING)
								.netAmt(Objects.nonNull(resultList.get(i)[3]) ? resultList.get(i)[3].toString()
										: CommonConstraints.INSTANCE.BLANK_STRING)
								.build());
			}
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(authorisationEnquiryData).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data present to display.").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchMaterialPaymentStatusEnquiry(String buildingCodes, String materialCodes,
			String supplierCodes, String authNos, String authTypes, String authFromDate, String authToDate) {
		String whereCondition = " ('ALL' IN (" + authNos + ") OR auth_authnum in (" + authNos + ")) "
				+ "	and ('ALL' IN (" + supplierCodes + ") OR auth_partycode in (" + supplierCodes + ")) "
				+ "and ('ALL' IN (" + buildingCodes + ") OR auth_bldgcode in  (" + buildingCodes + ")) "
				+ "and ('ALL' IN (" + materialCodes + ") OR auth_matgroup in (" + materialCodes + ")) "
				+ "and ('ALL' IN (" + authTypes + ") OR auth_authtype in  (" + authTypes + "))";

		if (StringUtils.isNotBlank(authFromDate) && StringUtils.isNotBlank(authToDate)) {
			whereCondition = whereCondition
					+ "and to_date(to_char(auth_authdate,'dd/MM/yyyy'),'dd/MM/yyyy') BETWEEN to_date('${" + authFromDate
					+ "}','dd/MM/yyyy') and to_date('${" + authToDate + "}','dd/MM/yyyy')";
		}
		Query materialPaymentStatusEnquiryQuery = this.entityManager.createNativeQuery(
				"SELECT AUTH_AUTHNUM as AUTH_AUTHNUM , to_char(AUTH_AUTHDATE,'dd/mm/yyyy') as AUTH_AUTHDATE, AUTH_AUTHTYPE as AUTH_AUTHTYPE, AUTH_BLDGCODE as AUTH_BLDGCODE,AUTH_MATGROUP as AUTH_MATGROUP, (select par_partyname from party where par_partycode=AUTH_PARTYCODE and par_partytype ='S' and par_closedate='01-Jan-2050') as AUTH_PARTYCODE, AUTH_PAYAMOUNT as AUTH_PAYAMOUNT, AUTH_PAYREF as AUTH_PAYREF, AUTH_PAYDATE as AUTH_PAYDATE, AUTH_PASSEDON AS AUTH_PASSEDON , NVL((SELECT SUM(NVL(AUTD_ADVADJ,0)) FROM AUTH_D WHERE AUTD_AUTHNUM = AUTH_AUTHNUM),0) as AUTD_ADVADJ FROM AUTH_H where "
						+ whereCondition
						+ "order by AUTH_BLDGCODE Asc,AUTH_MATGROUP Asc,AUTH_PARTYCODE Asc,AUTH_AUTHNUM Desc");
		List<Object[]> queryResult = materialPaymentStatusEnquiryQuery.getResultList();
		if (!queryResult.isEmpty()) {
			List<MaterialPaymentStatusEnquiryResponseBean> materialStatusEnquiryList = new ArrayList<>();
			for (int i = 0; i < queryResult.size(); i++) {
				materialStatusEnquiryList.add(MaterialPaymentStatusEnquiryResponseBean.builder()
						.authNo(Objects.nonNull(queryResult.get(i)[0]) ? queryResult.get(i)[0].toString().trim()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.authDate(Objects.nonNull(queryResult.get(i)[1]) ? queryResult.get(i)[1].toString()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.type(Objects.nonNull(queryResult.get(i)[2]) ? queryResult.get(i)[2].toString().trim()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.building(Objects.nonNull(queryResult.get(i)[3]) ? queryResult.get(i)[3].toString()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.material(Objects.nonNull(queryResult.get(i)[4]) ? queryResult.get(i)[4].toString()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.party(Objects.nonNull(queryResult.get(i)[5]) ? queryResult.get(i)[5].toString().trim()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.payAmnt(Objects.nonNull(queryResult.get(i)[6]) ? queryResult.get(i)[6].toString() : "0")
						.chequeNum(
								Objects.nonNull(queryResult.get(i)[7]) ? queryResult.get(i)[7].toString().trim()
										: CommonConstraints.INSTANCE.BLANK_STRING)
						.paidDate(
								Objects.nonNull(queryResult.get(i)[8])
										? LocalDateTime
												.parse(queryResult.get(i)[8].toString(),
														DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
												.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
										: CommonConstraints.INSTANCE.BLANK_STRING)
						.passedOn(Objects.nonNull(queryResult.get(i)[9])
								? LocalDateTime
										.parse(queryResult.get(i)[9].toString(),
												DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.advAdj(Objects.nonNull(queryResult.get(i)[7]) ? queryResult.get(i)[10].toString() : "0")
						.build());
			}

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(materialStatusEnquiryList).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data present to display.").build());
	}

	@Override
	public ResponseEntity<?> addMaterial(MaterialDetailRequestBean materialDetailRequestBean) {
		String authNo = materialDetailRequestBean.getAuthHRequestBean().getAuthnum();
		log.info("Logger :: {}", authNo);

		ValueContainer<String> StrPriCoy = new ValueContainer<>();
		String StrPriProject = null;
		String StrPriProperty = null;
		String StrPriMisProject = null;
		String StrPriMisBldg = null;
		String StrPriProp = null;
		Double DecPriBmpAuth = 0.0;
		Double DecPriBmAuth = 0.0;
		Double DecPriBAuth = 0.0;
		Double DecPriTotRelretAmt = 0.0; // ----> TO CALCULATE IN AUTH TYPE R
		Double DecPriTotretadjAmt = 0.0;// ----> TO CALCULATE IN AUTH TYPE R
		String StrPriPrvAuthno = null;
		Boolean BlnLocIsNullPrvAuthno = false;
		String StrPriPrvType = null;
		LocalDate DtPriPrvDate = null;
		Double DecPriAdvGranted = 0.0;
		Double DecPriAdvAdjustpastC = 0.0;
		Double DecPriAdvAdjustpastN = 0.0;
		Double DecPriAdvAdjustPast = 0.0;
		List<String> dbnoteSerList = new ArrayList<>();

		// to cheak if adv adj/release retention / retention adj
		Query query;

		// List<MaterialBillDetailResponseBean> materialBillDetailResponseBeanList = new
		// ArrayList<>();
		if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("N")) {
			query = this.entityManager.createNativeQuery(
					"SELECT PBLH_SUPPBILLNO,PBLH_SUPPBILLDT,PBLH_AMOUNT,PBLH_DEBITAMT,PBLH_TDSAMOUNT,PBLH_RETAINOS,PBLH_ADVANCEADJ,pblh_paidamt,pbld_quantity,pblh_retention,pblh_billtype,pblh_ser,pbld_matcode,pbld_matgroup,pblh_omspurcyn,pbld_uom FROM PBILLD,PBILLH   WHERE PBLD_SER = PBLH_SER and ((trim(PBLH_PARTYCODE) = :partyCode) AND PBLH_SUPPBILLNO in ("
							+ String.join(",", materialDetailRequestBean.getAuthDRequestBeanList().stream()
									.map(authDRequestBean -> {
										return authDRequestBean.getSuppbillno();
									}).collect(Collectors.toSet()).stream().map(name -> ("'" + name + "'"))
									.collect(Collectors.toList()))
							+ ") AND (PBLH_AUTHNUM is NULL) AND (PBLH_AUTHDATE is NULL) AND (PBLD_LINENO = 1)) ORDER BY PBLH_SUPPBILLNO ASC,PBLH_SUPPBILLDT ASC,PBLH_AMOUNT DESC",
					Tuple.class);
			query.setParameter("partyCode", materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim());

			List<Tuple> resultSetList = query.getResultList();
			if (CollectionUtils.isNotEmpty(resultSetList)) {

				for (Tuple t : resultSetList) {
					BigDecimal payamount = BigDecimal.ZERO;
					payamount = t.get(2, BigDecimal.class)
							.subtract(t.get(6, BigDecimal.class).add(t.get(9, BigDecimal.class))
									.add(t.get(3, BigDecimal.class)).add(t.get(4, BigDecimal.class))); // --------->
																										// payamt = bill
																										// - (advadj +
																										// retention +
																										// debitamt +
																										// tdsamt)
					log.info("payamount", payamount);

					for (Auth_DRequestBean auth_DRequestBean : materialDetailRequestBean.getAuthDRequestBeanList()) {
						if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
								&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
								&& (Objects.nonNull(auth_DRequestBean.getAdvadj())
										? auth_DRequestBean.getAdvadj().doubleValue()
										: BigInteger.ZERO.doubleValue()) > t.get(2, BigDecimal.class).doubleValue())
							return ResponseEntity.ok(ServiceResponseBean.builder()
									.message("Advance Adjusted cannot be greater then bill amount")
									.status(Boolean.FALSE).build());

						if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
								&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
								&& (Objects.nonNull(auth_DRequestBean.getPayamount())
										? auth_DRequestBean.getPayamount().doubleValue()
										: BigInteger.ZERO.doubleValue()) > payamount.doubleValue())
							return ResponseEntity.ok(ServiceResponseBean.builder()
									.message("Pay amount cannot be greater then bill amount").status(Boolean.FALSE)
									.build());

					}

				}
			}
		}

		if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("L")
				|| materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("R")) {
			query = this.entityManager.createNativeQuery(
					"SELECT DISTINCT PBLH_SUPPBILLNO,PBLH_SUPPBILLDT,PBLH_AMOUNT,PBLH_DEBITAMT,PBLH_TDSAMOUNT,PBLH_RETAINOS,PBLH_ADVANCEADJ,pblh_paidamt,pbld_quantity,pblh_retention,pblh_billtype,pblh_ser,pbld_matcode,pbld_matgroup,pblh_omspurcyn,pbld_uom FROM PBILLD,PBILLH WHERE (PBLD_PARTYCODE = PBLH_PARTYCODE) and (PBLD_SUPPBILLNO = PBLH_SUPPBILLNO)and ((trim(PBLH_PARTYCODE) = :partyCode)AND (PBLH_SUPPBILLNO in ("
							+ String.join(",", materialDetailRequestBean.getAuthDRequestBeanList().stream()
									.map(authDRequestBean -> {
										return authDRequestBean.getSuppbillno();
									}).collect(Collectors.toSet()).stream().map(name -> ("'" + name + "'"))
									.collect(Collectors.toList()))
							+ ")) AND (PBLH_RETENTION > 0) AND (PBLD_LINENO = 1))",
					Tuple.class);
			query.setParameter("partyCode", materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim());

			List<Tuple> resultSetList = query.getResultList();
			if (CollectionUtils.isNotEmpty(resultSetList)) {
				if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("L")) {
					for (Tuple t : resultSetList) {
						BigDecimal payamount = BigDecimal.ZERO;
						payamount = t.get(2, BigDecimal.class)
								.subtract(t.get(6, BigDecimal.class).add(t.get(9, BigDecimal.class))
										.add(t.get(3, BigDecimal.class)).add(t.get(4, BigDecimal.class))); // --------->
																											// payamt =
																											// bill -
																											// (advadj +
																											// retention
																											// +
																											// debitamt
																											// + tdsamt)
						log.info("payamount", payamount);

						for (Auth_DRequestBean auth_DRequestBean : materialDetailRequestBean
								.getAuthDRequestBeanList()) {
							if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
									&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
									&& (Objects.nonNull(auth_DRequestBean.getRelretamt())
											? auth_DRequestBean.getRelretamt().doubleValue()
											: BigInteger.ZERO.doubleValue()) > t.get(5, BigDecimal.class).doubleValue())
								return ResponseEntity.ok(ServiceResponseBean.builder().message(
										"You cannot release more than outstanding retention amount of this bill.")
										.status(Boolean.FALSE).build());

							if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
									&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
									&& (Objects.nonNull(auth_DRequestBean.getPayamount())
											? auth_DRequestBean.getPayamount().doubleValue()
											: BigInteger.ZERO.doubleValue()) > t.get(5, BigDecimal.class).doubleValue())
								return ResponseEntity.ok(ServiceResponseBean.builder()
										.message("Pay amount cannot be greater then bill amount").status(Boolean.FALSE)
										.build());
						}

					}
				}

				if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("R")) {
					for (Tuple t : resultSetList) {
						for (Auth_DRequestBean auth_DRequestBean : materialDetailRequestBean
								.getAuthDRequestBeanList()) {
							if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
									&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
									&& (Objects.nonNull(auth_DRequestBean.getRetentionadj())
											? auth_DRequestBean.getRetentionadj().doubleValue()
											: BigInteger.ZERO.doubleValue()) > (t.get(5, BigDecimal.class)
													.doubleValue()))
								return ResponseEntity.ok(ServiceResponseBean.builder().message(
										"You cannot adjust more than outstanding retention amount of this bill.")
										.status(Boolean.FALSE).build());

							if (t.get(11, String.class).trim().equals(auth_DRequestBean.getSer().trim())
									&& t.get(0, String.class).trim().equals(auth_DRequestBean.getSuppbillno().trim())
									&& BigDecimal.ZERO.doubleValue() != auth_DRequestBean.getPayamount().doubleValue())
								return ResponseEntity.ok(ServiceResponseBean.builder()
										.message("Pay amount has to be zero for Retention Adjustment")
										.status(Boolean.FALSE).build());
						}

					}
				}
			}
		}

		Building buildingEntity = this.buildingRepository
				.findByBuildingCK_BldgCode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim());
		log.info("Building :: {}", buildingEntity);
		if (Objects.nonNull(buildingEntity)) {

			Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
					CommonUtils.INSTANCE.closeDate());
			log.info("Company :: {}", companyEntity);

			StrPriCoy.setValue(buildingEntity.getBldgPaycoy());
			StrPriProject = buildingEntity.getBldgProject();
			StrPriProperty = buildingEntity.getBldgProperty();
			StrPriMisProject = buildingEntity.getBldgMisproject();
			StrPriMisBldg = buildingEntity.getBldgMisbldg();
			StrPriProp = Objects.nonNull(companyEntity) ? companyEntity.getCompanyCK().getCoyProp()
					: buildingEntity.getBldgProp(); // -------> TO ASK WAT TO INSERT WHEN IT IS NULL
		}

		// //Get the no. of authorisations for this building + partycode + material
		// group combination ...
		// DecPriBmpAuth =
		// this.authHRepository.findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6",
		// authNo.trim());
		// log.info("No. of authorisations for this building + partycode + material
		// group :: {}", DecPriBmpAuth);
		//
		// //Get the no. of authorisations for this building + material group
		// combination ...
		// DecPriBmAuth =
		// this.authHRepository.findCountByAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6",
		// authNo.trim());
		// log.info("No. of authorisations for this building + material group :: {}",
		// DecPriBmAuth);
		//
		// //Get the no. of authorisations for this building combination ...
		// DecPriBAuth =
		// this.authHRepository.findCountByAuthBldgcodeAndAuthAuthstatusAndAuthAuthNo(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
		// "6", authNo.trim());
		// log.info("No. of authorisations for this building :: {}", DecPriBAuth);

		// Get the no. of authorisations for this building + partycode + material group
		// combination ...
		DecPriBmpAuth = this.authHRepository
				.findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", LocalDate.now());
		log.info("No. of authorisations for this building + partycode + material group :: {}", DecPriBmpAuth);

		// Get the no. of authorisations for this building + material group combination
		// ...
		DecPriBmAuth = this.authHRepository.findCountByAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(
				materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
				materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", LocalDate.now());
		log.info("No. of authorisations for this building + material group :: {}", DecPriBmAuth);

		// Get the no. of authorisations for this building combination ...
		DecPriBAuth = this.authHRepository.findCountByAuthBldgcodeAndAuthAuthstatusAndAuthAuthdate(
				materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(), "6", LocalDate.now());
		log.info("No. of authorisations for this building  :: {}", DecPriBAuth);

		// For advance adjusted amount for
		// **********************************
		// Get the previuos authorisation number for this
		// building + party + material combination ...
		// max(authnum) query should be changed ---> kept same as per discussion with
		// vikram sir

		// StrPriPrvAuthno =
		// this.authHRepository.findMaxAuthNoByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6",
		// authNo.trim());

		StrPriPrvAuthno = this.authHRepository
				.findMaxAuthNoByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", LocalDate.now());
		log.info("Previous Auth No  :: {}", DecPriBAuth);
		if (Objects.isNull(StrPriPrvAuthno)) {
			StrPriPrvAuthno = "";
			BlnLocIsNullPrvAuthno = true;
		}

		// Get previous details for the party from authorisation number...
		if (!BlnLocIsNullPrvAuthno) {
			Auth_H authhEntityForPerviousAuthNo = this.authHRepository
					.findAuthHByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(
							materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
							materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
							materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6",
							StrPriPrvAuthno.trim());
			log.info("previous details for the party from authorisation number", authhEntityForPerviousAuthNo);
			DtPriPrvDate = authhEntityForPerviousAuthNo.getAuthAuthdate();
			StrPriPrvType = authhEntityForPerviousAuthNo.getAuthAuthtype();
		}

		// Get the sum of the authorisation amount, authorisation quantity
		// for this bldg_code + party_code + mat_group combination ...
		// PreviousAuthDetailBean previousAuthDetailBean =
		// this.authHRepository.findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
		// materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6",
		// authNo.trim());//-------> recently changed
		// log.info("PreviousAuthDetailBean :: {}", previousAuthDetailBean);

		PreviousAuthDetailBean previousAuthDetailBean = this.authHRepository
				.findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", LocalDate.now());// ------->
																													// recently
																													// changed
		log.info("PreviousAuthDetailBean  :: {}", previousAuthDetailBean);

		// test for merge
		Double DecPriPrvAuthamt = Objects.nonNull(previousAuthDetailBean.getAuthamt())
				? previousAuthDetailBean.getAuthamt()
				: BigInteger.ZERO.doubleValue();
		Double DecPriPrvAuthquanty = Objects.nonNull(previousAuthDetailBean.getAuthquanty())
				? previousAuthDetailBean.getAuthquanty()
				: BigInteger.ZERO.doubleValue();
		Double DecLocPrvRelret = Objects.nonNull(previousAuthDetailBean.getRelret())
				? previousAuthDetailBean.getRelret()
				: BigInteger.ZERO.doubleValue();
		Double DecPriPrvRetamt = Objects.nonNull(previousAuthDetailBean.getRetamt())
				? previousAuthDetailBean.getRetamt()
				: BigInteger.ZERO.doubleValue();
		Double DecLocPrvretadj = Objects.nonNull(previousAuthDetailBean.getRetadj())
				? previousAuthDetailBean.getRetadj()
				: BigInteger.ZERO.doubleValue();

		DecPriAdvGranted = this.authHRepository
				.findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", "A",
						LocalDate.now());
		log.info("DecPriAdvGranted  :: {}", DecPriAdvGranted);

		if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("A"))
			DecPriAdvGranted = DecPriAdvGranted
					+ (Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAdvgranted())
							? materialDetailRequestBean.getAuthHRequestBean().getAdvgranted()
							: BigInteger.ZERO.doubleValue());

		// Past Authorisation Amount for Recover & Credit authtype -> 'C' ...
		DecPriAdvAdjustpastC = this.authHRepository
				.findAuthadvadjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", "C",
						LocalDate.now());

		// Past Advance Adjusted Amount for Normal Authorisation - 'N' ...
		DecPriAdvAdjustpastN = this.authHRepository
				.findAuthadvadjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthAuthdate(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", "N",
						LocalDate.now());

		// Total advance adjusted amount ...
		DecPriAdvAdjustPast = DecPriAdvAdjustpastN + DecPriAdvAdjustpastC;

		// ' Get the other party amounts ...
		PreviousAuthDetailBean otherAuthDetailBean = this.authHRepository
				.findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatus(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6");
		log.info("PreviousAuthDetailBean For Other :: {}", otherAuthDetailBean);
		Double DecPriOprAuthamt = Objects.nonNull(otherAuthDetailBean.getAuthamt()) ? otherAuthDetailBean.getAuthamt()
				: BigInteger.ZERO.doubleValue();
		Double DecPriOprAuthqty = Objects.nonNull(otherAuthDetailBean.getAuthquanty())
				? otherAuthDetailBean.getAuthquanty()
				: BigInteger.ZERO.doubleValue();
		Double DecPriOprRetain = Objects.nonNull(otherAuthDetailBean.getRelret()) ? otherAuthDetailBean.getRelret()
				: BigInteger.ZERO.doubleValue();
		Double DecPriOprRelret = Objects.nonNull(otherAuthDetailBean.getRetamt()) ? otherAuthDetailBean.getRetamt()
				: BigInteger.ZERO.doubleValue();
		Double DecPriOprretadj = Objects.nonNull(otherAuthDetailBean.getRetadj()) ? otherAuthDetailBean.getRetadj()
				: BigInteger.ZERO.doubleValue();

		// Get the total advance amount granted other suppliers
		// for this building for this material - Matgroup
		// Advance Granted Amount ...
		Double DecPriOprAdvance = this.authHRepository
				.findAuthAmountSumByAuthPartycodeNotInAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6", "A");
		log.info("DecPriOprAdvance", DecPriOprAdvance);

		// ' Past Advance Adjusted Amount for Normal Authorisation - 'N' ...
		Double DecPriOprAdvadj = this.authHRepository
				.findAdvAdjustSumByAuthPartycodeNotINauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
						materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "6");
		log.info("DecPriOprAdvAdjustpastN", DecPriOprAdvadj);

		if (Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean())) {
			materialDetailRequestBean.getAuthHRequestBean().setAuthquanty(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setTdsamount(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setAdvadjust(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setRetained(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setRelretain(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setRetentionadj(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setPerdone(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean()
					.setAuthamount(materialDetailRequestBean.getAuthHRequestBean().getAdvgranted()); // for auth type a

			materialDetailRequestBean.getAuthHRequestBean().setAuthnum(authNo);
			materialDetailRequestBean.getAuthHRequestBean().setCoy(StrPriCoy.getValue());
			materialDetailRequestBean.getAuthHRequestBean().setProp(StrPriProp);
			materialDetailRequestBean.getAuthHRequestBean().setProject(StrPriProject);
			materialDetailRequestBean.getAuthHRequestBean().setProperty(StrPriProperty);
			materialDetailRequestBean.getAuthHRequestBean().setMisproject(StrPriMisProject);
			materialDetailRequestBean.getAuthHRequestBean().setMisbldg(StrPriMisBldg);
			materialDetailRequestBean.getAuthHRequestBean().setMisbldg(StrPriMisBldg);
			materialDetailRequestBean.getAuthHRequestBean().setPrvadvance(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setPrvadvadj(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setOpradvadj(BigInteger.ZERO.doubleValue());
			materialDetailRequestBean.getAuthHRequestBean().setPartytype(PartyType.S.toString());
			materialDetailRequestBean.getAuthHRequestBean().setAuthstatus(BigInteger.ONE.toString());

			// party
			materialDetailRequestBean.getAuthHRequestBean().setPartyauth(DecPriBmpAuth);
			materialDetailRequestBean.getAuthHRequestBean().setMatbldauth(DecPriBmAuth);
			materialDetailRequestBean.getAuthHRequestBean().setBldgauthno(DecPriBAuth);
			materialDetailRequestBean.getAuthHRequestBean().setRelretain(DecPriTotRelretAmt);
			materialDetailRequestBean.getAuthHRequestBean().setRetentionadj(DecPriTotretadjAmt);
			materialDetailRequestBean.getAuthHRequestBean().setAdvadjpast(DecPriAdvAdjustPast);
			materialDetailRequestBean.getAuthHRequestBean().setAdvgranted(DecPriAdvGranted);

			// pre
			materialDetailRequestBean.getAuthHRequestBean().setPrvauthamt(DecPriPrvAuthamt);
			materialDetailRequestBean.getAuthHRequestBean().setPrvauthqty(DecPriPrvAuthquanty);
			materialDetailRequestBean.getAuthHRequestBean().setPrvretamt(DecPriPrvRetamt);
			materialDetailRequestBean.getAuthHRequestBean().setPrvrelret(DecLocPrvRelret);
			materialDetailRequestBean.getAuthHRequestBean().setPrvretentionadj(DecLocPrvretadj);
			materialDetailRequestBean.getAuthHRequestBean()
					.setPrvdate(Objects.nonNull(DtPriPrvDate)
							? DtPriPrvDate.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null);
			materialDetailRequestBean.getAuthHRequestBean().setPrvauthno(StrPriPrvAuthno);
			materialDetailRequestBean.getAuthHRequestBean().setPrvtype(StrPriPrvType);

			// Other
			materialDetailRequestBean.getAuthHRequestBean().setOpradvadj(DecPriOprAdvadj);
			materialDetailRequestBean.getAuthHRequestBean().setOprretentionadj(DecPriOprretadj);
			materialDetailRequestBean.getAuthHRequestBean().setOprrelret(DecPriOprRelret);
			materialDetailRequestBean.getAuthHRequestBean().setOprretain(DecPriOprRetain);
			materialDetailRequestBean.getAuthHRequestBean().setOprauthqty(DecPriOprAuthqty);
			materialDetailRequestBean.getAuthHRequestBean().setOprauthamt(DecPriOprAuthamt);
			materialDetailRequestBean.getAuthHRequestBean().setOpradvance(DecPriOprAdvance);

			if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("C")) {
				materialDetailRequestBean.getAuthHRequestBean().setOpradvance(BigInteger.ZERO.doubleValue());
				materialDetailRequestBean.getAuthHRequestBean()
						.setAdvadjust(materialDetailRequestBean.getAuthHRequestBean().getPayamount()); // Advance Amt
																										// LABEL ON
																										// FRONTEND FOR
																										// AUTH TYPE C
				materialDetailRequestBean.getAuthHRequestBean().setAuthamount(BigInteger.ZERO.doubleValue());
				materialDetailRequestBean.getAuthHRequestBean()
						.setPayamount(materialDetailRequestBean.getAuthHRequestBean().getPayamount() * -1);
			}

			if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("N")) {
				// Double StrLocAdvadjSupplier = 0.0;
				// if(materialDetailRequestBean.getAuthHRequestBean().getTotalAdvadjust() > 0.0)
				// StrLocAdvadjSupplier =
				// materialDetailRequestBean.getAuthHRequestBean().getAdvadjust();

				Double DecPriTotAuthAmt = materialDetailRequestBean.getAuthHRequestBean().getTotalBillamount()
						- materialDetailRequestBean.getAuthHRequestBean().getTotalDebitamount();// ----------> REMOVED
																								// TDS MINUS AS DISSCUED
																								// WITH VIKRAM SIR

				materialDetailRequestBean.getAuthHRequestBean().setAuthamount(DecPriTotAuthAmt);// total auth amount to
																								// do
				materialDetailRequestBean.getAuthHRequestBean()
						.setAuthquanty(materialDetailRequestBean.getAuthHRequestBean().getTotalQuantity());
				materialDetailRequestBean.getAuthHRequestBean()
						.setTdsamount(materialDetailRequestBean.getAuthHRequestBean().getTotalTds());
				materialDetailRequestBean.getAuthHRequestBean()
						.setAdvadjust(materialDetailRequestBean.getAuthHRequestBean().getTotalAdvadjust());
				materialDetailRequestBean.getAuthHRequestBean()
						.setRetained(materialDetailRequestBean.getAuthHRequestBean().getTotalRetention());
				materialDetailRequestBean.getAuthHRequestBean()
						.setPayamount(materialDetailRequestBean.getAuthHRequestBean().getTotalPayamount());

				if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())) {
					materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
						authDRequestBean.setRetentionadj(BigInteger.ZERO.doubleValue());
						authDRequestBean.setRelretamt(BigInteger.ZERO.doubleValue());
						authDRequestBean.setBldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode());
						authDRequestBean.setMatgroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup());
						authDRequestBean.setPartycode(materialDetailRequestBean.getAuthHRequestBean().getPartycode());
						authDRequestBean.setAuthtype(materialDetailRequestBean.getAuthHRequestBean().getAuthtype());
						authDRequestBean.setAuthamount(authDRequestBean.getAuthamount()
								- (Objects.nonNull(authDRequestBean.getDebitamt()) ? authDRequestBean.getDebitamt()
										: BigInteger.ZERO.doubleValue())); // -----------> as discussed with vikram sir
																			// authamount - debit amount
						// authDRequestBean.setAuthamount(DecPriOprAuthamt);
						return authDRequestBean;
					}).collect(Collectors.toList());

					List<Pbilld> pbilldEntityList = this.pbilldRepository.findByBySerIn(
							materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
								return authDRequestBean.getSer();
							}).collect(Collectors.toSet()));
					LOGGER.info("pbilldEntityList : {} ", pbilldEntityList);

					if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())
							&& CollectionUtils.isNotEmpty(pbilldEntityList)) {
						materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
							pbilldEntityList.stream().filter(filter -> {
								return filter.getPbldSuppbillno().trim().equals(authDRequestBean.getSuppbillno().trim())
										&& filter.getPbldSuppbilldt()
												.equals(LocalDate.parse(authDRequestBean.getSuppbilldt(),
														CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
							}).map(pbilldEntity -> {
								authDRequestBean.setMatcode(
										Objects.nonNull(pbilldEntity.getPbldMatcode()) ? pbilldEntity.getPbldMatcode()
												: null);
								return pbilldEntity;
							}).collect(Collectors.toList());
							return authDRequestBean;
						}).collect(Collectors.toList());
					}
					this.authDRepository.saveAll(Auth_DEntityPojoMapper.addAuth_DEntityPojoMapper
							.apply(new Object[] { materialDetailRequestBean.getAuthDRequestBeanList(), authNo }));
				}

				List<Pbillh> pbillhEntityList = this.pbillhRepository.findByPbillhCK_PblhSerIn(
						materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
							return authDRequestBean.getSer();
						}).collect(Collectors.toSet()));
				LOGGER.info("pbillhEntityList : {} ", pbillhEntityList);

				if (CollectionUtils.isNotEmpty(pbillhEntityList)) {
					this.pbillhRepository.saveAll(pbillhEntityList.stream().map(pbillhEntity -> {
						materialDetailRequestBean.getAuthDRequestBeanList().stream().filter(filter -> {
							return pbillhEntity.getPblhSuppbillno().trim().equals(filter.getSuppbillno().trim())
									&& pbillhEntity.getPbillhCK().getPblhSer().trim().equals(filter.getSer().trim());
						}).map(authDRequestBean -> {
							pbillhEntity.setPblhAuthnum(authNo);
							pbillhEntity.setPblhAuthdate(
									Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
											? LocalDate.parse(
													materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
													CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
											: null);
							pbillhEntity.setPblhRetainos(authDRequestBean.getRetainamt());
							pbillhEntity.setPblhPaidamt(authDRequestBean.getPayamount());
							pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid());
							pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite());
							pbillhEntity.setPblhToday(LocalDateTime.now());
							return authDRequestBean;
						}).collect(Collectors.toList());

						return pbillhEntity;
					}).collect(Collectors.toList()));
				}

				List<Dbnoteh> dbnotehEntityList = this.dbnotehRepository
						.dbnotehSerByCoyAndBuildingAndSupplierAndSuppBillNoIn(
								materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
								materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
								materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
									return authDRequestBean.getSuppbillno().trim();
								}).collect(Collectors.toSet()));
				LOGGER.info("dbnotehEntityList : {} ", dbnotehEntityList);

				if (CollectionUtils.isNotEmpty(dbnotehEntityList)) {
					this.dbnotehRepository.saveAll(dbnotehEntityList.stream().map(dbnotehEntity -> {
						dbnotehEntity.setDbnhAuthno(authNo);
						dbnotehEntity.setDbnhAuthdate(
								Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
										? LocalDate.parse(materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
												CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
										: null);
						dbnotehEntity.setDbnhUserid(GenericAuditContextHolder.getContext().getUserid());
						dbnotehEntity.setDbnhSite(GenericAuditContextHolder.getContext().getSite());
						dbnotehEntity.setDbnhToday(LocalDateTime.now());
						return dbnotehEntity;
					}).collect(Collectors.toList()));
				}

			}
			if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("R")) {
				materialDetailRequestBean.getAuthHRequestBean()
						.setAuthstatus(CommonConstraints.INSTANCE.FIVE.toString());
				materialDetailRequestBean.getAuthHRequestBean().setAuthamount(BigInteger.ZERO.doubleValue());
				materialDetailRequestBean.getAuthHRequestBean()
						.setRetentionadj(materialDetailRequestBean.getAuthHRequestBean().getTotalRetAdvadjust());
				materialDetailRequestBean.getAuthHRequestBean()
						.setPayamount(materialDetailRequestBean.getAuthHRequestBean().getTotalPayamount());
				materialDetailRequestBean.getAuthHRequestBean()
						.setPassedon(materialDetailRequestBean.getAuthHRequestBean().getAuthdate());

				if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())) {
					materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
						authDRequestBean.setRelretamt(BigInteger.ZERO.doubleValue());
						authDRequestBean.setAuthtype(materialDetailRequestBean.getAuthHRequestBean().getAuthtype());
						authDRequestBean.setAuthamount(BigInteger.ZERO.doubleValue());
						authDRequestBean.setAuthtdsamt(BigInteger.ZERO.doubleValue());
						authDRequestBean.setBldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode());
						authDRequestBean.setMatgroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup());
						authDRequestBean.setPartycode(materialDetailRequestBean.getAuthHRequestBean().getPartycode());
						return authDRequestBean;
					}).collect(Collectors.toList());

					List<Pbilld> pbilldEntityList = this.pbilldRepository.findByBySerIn(
							materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
								return authDRequestBean.getSer();
							}).collect(Collectors.toSet()));
					LOGGER.info("pbilldEntityList : {} ", pbilldEntityList);

					if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())
							&& CollectionUtils.isNotEmpty(pbilldEntityList)) {
						materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
							pbilldEntityList.stream().filter(filter -> {
								return filter.getPbldSuppbillno().trim().equals(authDRequestBean.getSuppbillno().trim())
										&& filter.getPbldSuppbilldt()
												.equals(LocalDate.parse(authDRequestBean.getSuppbilldt(),
														CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
							}).map(pbilldEntity -> {
								authDRequestBean.setMatcode(
										Objects.nonNull(pbilldEntity.getPbldMatcode()) ? pbilldEntity.getPbldMatcode()
												: null);

								return pbilldEntity;
							}).collect(Collectors.toList());
							return authDRequestBean;
						}).collect(Collectors.toList());
					}
					this.authDRepository.saveAll(Auth_DEntityPojoMapper.addAuth_DEntityPojoMapper
							.apply(new Object[] { materialDetailRequestBean.getAuthDRequestBeanList(), authNo }));
				}
				String project = StrPriProject;
				String prop = StrPriProp;
				String misProject = StrPriMisProject;
				String misBldg = StrPriMisBldg;
				List<Dbnoteh> dbnotehList = new ArrayList<Dbnoteh>();
				List<Dbnoted> dbnotedList = new ArrayList<Dbnoted>();
				List<ActranhBean> actranhList = new ArrayList<ActranhBean>();
				List<ActrandBean> actrandList = new ArrayList<ActrandBean>();
				List<Pbillh> pbillhList = new ArrayList<Pbillh>();
				Material materialEntity = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(
						materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "1");
				LOGGER.info("MaterialEntity :: {}", materialEntity);

				if (Objects.nonNull(materialEntity))
					materialDetailRequestBean.getAuthDRequestBeanList().stream().map(retentionTableRow -> {
						String dbnoteSer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "DNSER",
								GenericAuditContextHolder.getContext().getSite().trim());
						LOGGER.info(dbnoteSer);
						dbnoteSerList.add(dbnoteSer);

						// FOR MATCODE
						Pbilld pbilldEntity = this.pbilldRepository.findByPbldSuppbillnoAndPbldSuppbilldt(
								retentionTableRow.getSuppbillno().trim(),
								LocalDate.parse(retentionTableRow.getSuppbilldt(),
										CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
						LOGGER.info("pbilldEntity FOR MAT CODE :: {}", pbilldEntity);

						// Make a debit note header entry
						dbnotehList.add(Dbnoteh.builder().dbnhPartytype(PartyType.S.toString())
								.dbnhPartycode(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim())
								.dbnhBldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim())
								.dbnhCoy(StrPriCoy.getValue()).dbnhSuppbillno(retentionTableRow.getSuppbillno())
								.dbnhDate(Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
										? LocalDate.parse(materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
												CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
										: null)
								.dbnhSuppbilldt(Objects.nonNull(retentionTableRow.getSuppbilldt())
										? LocalDate.parse(retentionTableRow.getSuppbilldt(),
												CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
										: null)
								.dbnhBilltype(retentionTableRow.getBilltype())
								.dbnhAmount(retentionTableRow.getRetentionadj().doubleValue())
								.dbnhTdsperc(BigInteger.ZERO.doubleValue()).dbnhTdsamount(BigInteger.ZERO.doubleValue())
								.dbnhNarration("Bill/Retention Not payable Ref Auth#" + authNo)
								.dbnhDescription1(StringUtils
										.isNotBlank(materialDetailRequestBean.getAuthHRequestBean().getDescription())
										&& materialDetailRequestBean.getAuthHRequestBean().getDescription()
												.length() > 50
														? materialDetailRequestBean.getAuthHRequestBean()
																.getDescription().substring(0, 50)
														: materialDetailRequestBean.getAuthHRequestBean()
																.getDescription())
								.dbnhDescription2(StringUtils
										.isNotBlank(materialDetailRequestBean.getAuthHRequestBean().getDescription())
										&& materialDetailRequestBean.getAuthHRequestBean().getDescription()
												.length() >= 51
														? materialDetailRequestBean.getAuthHRequestBean()
																.getDescription().substring(51,
																		materialDetailRequestBean.getAuthHRequestBean()
																				.getDescription().length())
														: CommonConstraints.INSTANCE.BLANK_STRING)
								.dbnhNoofprint(BigInteger.ZERO.doubleValue()).dbnhTranser(dbnoteSer)
								.dbnhSite(GenericAuditContextHolder.getContext().getSite())
								.dbnhUserid(GenericAuditContextHolder.getContext().getUserid())
								.dbnhToday(LocalDateTime.now()).dbnhProp(prop).dbnhProject(project)
								.dbnhOmspurcyn(retentionTableRow.getOmspurcyn()).dbnhMisbldg(misBldg)
								.dbnhMisproject(misProject)
								.dbnhAuthdate(
										Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
												? LocalDate.parse(
														materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
														CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
												: null)
								.dbnhAuthno(authNo).dbnotehCK(DbnotehCK.builder().dbnhDbnoteser(dbnoteSer).build())
								.build());

						// Make a debit note detail entry
						dbnotedList.add(Dbnoted.builder()
								.dbnotedCK(DbnotedCK.builder().dbndDbnoteser(dbnoteSer)
										.dbndLineno(BigInteger.ONE.doubleValue()).build())
								.dbndMatGroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim())
								.dbndMatcode(Objects.nonNull(pbilldEntity.getPbldMatcode())
										? pbilldEntity.getPbldMatcode().trim()
										: null)
								.dbndDeuom(
										Objects.nonNull(retentionTableRow.getUom()) ? retentionTableRow.getUom().trim()
												: null)
								.dbndDequantity(BigInteger.ZERO.doubleValue())
								.dbndAmount(retentionTableRow.getRetentionadj())
								.dbndSuppBillNo(retentionTableRow.getSuppbillno())
								.dbndPartyCode(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim())
								.dbndUom(Objects.nonNull(retentionTableRow.getUom()) ? retentionTableRow.getUom().trim()
										: null)
								.dbndQuantity(BigInteger.ZERO.doubleValue()).dbndDerate(BigInteger.ZERO.doubleValue())
								.dbndRate(BigInteger.ZERO.doubleValue()).dbndNumber(BigInteger.ZERO.doubleValue())
								.dbndSite(GenericAuditContextHolder.getContext().getSite())
								.dbndUserid(GenericAuditContextHolder.getContext().getUserid())
								.dbndToday(LocalDateTime.now()).build());

						// Make actranh entry
						actranhList.add(ActranhBean.builder().transer(dbnoteSer).trantype(TranTypeEnum.D.toString())
								.trandate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate()).ledgcode("PL")
								.partytype(PartyType.S.toString())
								.partycode(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim())
								.tranamt(retentionTableRow.getRetentionadj()).voudate(retentionTableRow.getSuppbilldt())
								.vounum(retentionTableRow.getSuppbillno()).postedyn("N").balancedyn("Y")
								.closingjvyn("N").bbookyn("N").cbookyn("N")
								.narrative("Bill/Retention Not payable Ref Auth#" + authNo).proprietor(prop)
								.coy(StrPriCoy.getValue()).site(GenericAuditContextHolder.getContext().getSite())
								.userid(GenericAuditContextHolder.getContext().getUserid()).clearacyn("N")
								.reverseyn("N").crepno(null).build());

						String acMajor;
						String acMinor;
						if (retentionTableRow.getBilltype().trim().equals(BillTypeEnum.TRANSPORT.toString())) {
							acMajor = "40503005";
							acMinor = " ";
						} else {
							acMajor = materialEntity.getMatAcmajor();
							acMinor = materialEntity.getMatAcminor();
						}
						AccountingBean accoutingDataForTran = GenericAccountingLogic.gatherMinorPartyDetails("11401019",
								null, "", PartyType.S.toString(),
								materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(), project,
								acMinor);
						AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(
								acMajor, null, "", "",
								materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(), project,
								acMinor);

						actrandList.add(ActrandBean.builder().transer(dbnoteSer).bunum(1)
								.trantype(TranTypeEnum.D.toString())
								.trandate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate()).ledgcode("PL")
								.proprietor(prop).coy(StrPriCoy.getValue()).mintype(accoutingDataForTran.getMinType())
								.partytype(accoutingDataForTran.getPartyType())
								.partycode(accoutingDataForTran.getPartyCode())
								.acmajor(accoutingDataForTran.getAcMajor()).acminor(accoutingDataForTran.getAcminor())
								.mincode(accoutingDataForTran.getMinCode()).vounum(retentionTableRow.getSuppbillno())
								.voudate(retentionTableRow.getSuppbilldt()).tranamt(retentionTableRow.getRetentionadj())
								.project(accoutingDataForTran.getProject())
								.narrative(StringUtils
										.isNotBlank(materialDetailRequestBean.getAuthHRequestBean().getDescription())
										&& materialDetailRequestBean.getAuthHRequestBean().getDescription()
												.length() > 40
														? materialDetailRequestBean.getAuthHRequestBean()
																.getDescription().substring(0, 40)
														: materialDetailRequestBean.getAuthHRequestBean()
																.getDescription())
								.bldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode())
								.matgroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup())
								.matcode(Objects.nonNull(pbilldEntity.getPbldMatcode())
										? pbilldEntity.getPbldMatcode().trim()
										: null)
								.sku(Objects.nonNull(retentionTableRow.getUom()) ? retentionTableRow.getUom().trim()
										: null) // TODO: Need to tell frontend to bind this freom kevins response
								.itemqty(BigInteger.ZERO.doubleValue()).docnum(retentionTableRow.getSuppbillno())
								.docdate(retentionTableRow.getSuppbilldt()).doctype(retentionTableRow.getBilltype())
								.docpartype(PartyType.S.toString())
								.docparcode(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim())
								// X columns (Contra entry)
								.xproject(accoutingDataForContraTran.getProject())
								.xacmajor(accoutingDataForContraTran.getAcMajor())
								.xacminor(accoutingDataForContraTran.getAcminor())
								.xmintype(accoutingDataForContraTran.getMinType())
								.xpartycode(accoutingDataForContraTran.getPartyCode())
								.xpartytype(accoutingDataForContraTran.getPartyType()).xrefbunum(2)
								.xreftranser(dbnoteSer)
								.xreftrandate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
								.site(GenericAuditContextHolder.getContext().getSite())
								.userid(GenericAuditContextHolder.getContext().getUserid()).today(LocalDateTime.now())
								.build());

						// Contra Entry
						actrandList.add(ActrandBean.builder().transer(dbnoteSer).bunum(2)
								.trantype(TranTypeEnum.D.toString())
								.trandate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate()).ledgcode("PL")
								.proprietor(prop).coy(StrPriCoy.getValue())
								.mintype(accoutingDataForContraTran.getMinType())
								.mincode(accoutingDataForContraTran.getMinCode())
								.partytype(accoutingDataForContraTran.getPartyType())
								.partycode(accoutingDataForContraTran.getPartyCode())
								.project(accoutingDataForContraTran.getProject())
								.acminor(accoutingDataForContraTran.getAcminor())
								.acmajor(accoutingDataForContraTran.getAcMajor())
								.vounum(retentionTableRow.getSuppbillno()).voudate(retentionTableRow.getSuppbilldt())
								.tranamt(retentionTableRow.getRetentionadj() * -1)
								.narrative(StringUtils
										.isNotBlank(materialDetailRequestBean.getAuthHRequestBean().getDescription())
										&& materialDetailRequestBean.getAuthHRequestBean().getDescription()
												.length() > 40
														? materialDetailRequestBean.getAuthHRequestBean()
																.getDescription().substring(0, 40)
														: materialDetailRequestBean.getAuthHRequestBean()
																.getDescription())
								.project(accoutingDataForContraTran.getProject())
								.bldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode())
								.matgroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup())
								.matcode(Objects.nonNull(pbilldEntity.getPbldMatcode())
										? pbilldEntity.getPbldMatcode().trim()
										: null)
								.sku(Objects.nonNull(retentionTableRow.getUom()) ? retentionTableRow.getUom().trim()
										: null) // TODO: Need to tell frontend to bind this freom kevins response
								.itemqty(BigInteger.ZERO.doubleValue()).docnum(retentionTableRow.getSuppbillno())
								.docdate(retentionTableRow.getSuppbilldt()).doctype(retentionTableRow.getBilltype())
								.docpartype(PartyType.S.toString())
								.docparcode(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim())
								// X columns (Contra entry)
								.xacminor(accoutingDataForTran.getAcminor()) // Need to check because in vb it is
																				// partycode
								.xacmajor(accoutingDataForTran.getAcMajor()).xmintype(accoutingDataForTran.getMinType()) // null
								.xpartycode(accoutingDataForTran.getPartyCode())
								.xproject(accoutingDataForTran.getProject()).xreftranser(dbnoteSer)
								.xreftrandate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
								.xpartytype(accoutingDataForTran.getPartyType()).xrefbunum(1)
								.site(GenericAuditContextHolder.getContext().getSite())
								.userid(GenericAuditContextHolder.getContext().getUserid()).today(LocalDateTime.now())
								.build());

						// Update Pbillh
						Pbillh pbillhEntity = this.pbillhRepository
								.findByPbillhCK_PblhSer(retentionTableRow.getSer().trim());
						LOGGER.info("pbillhEntityList : {} ", pbillhEntity);

						if (Objects.nonNull(pbillhEntity)) {
							pbillhEntity.setPblhAuthnum(authNo);
							pbillhEntity.setPblhAuthdate(
									Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
											? LocalDate.parse(
													materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
													CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
											: null);
							pbillhEntity.setPblhRetainos(
									retentionTableRow.getRetainos() - retentionTableRow.getRetentionadj());
							pbillhEntity.setPblhDebitamt(
									pbillhEntity.getPblhDebitamt() + retentionTableRow.getRetentionadj());
							pbillhEntity.setPblhPaidamt(retentionTableRow.getPayamount());
							pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid());
							pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite());
							pbillhEntity.setPblhToday(LocalDateTime.now());
							pbillhList.add(pbillhEntity);
						}

						return retentionTableRow;
					}).collect(Collectors.toList());

				// LOGIC FOR EXNAAR
				actrandList.stream().map(actrand -> {
					List<ExnarrBean> exnarrBeanList = GenericExnarrLogic.generateExnarrBean(actrand,
							materialDetailRequestBean.getAuthHRequestBean().getDescription());
					if (CollectionUtils.isNotEmpty(exnarrBeanList))
						this.exnarrRepository.saveAll(ExnarrMapper.addExnarrMapperList.apply(exnarrBeanList));
					return actrand;
				}).collect(Collectors.toList());

				this.dbnotehRepository.saveAll(dbnotehList);
				this.dbnotedRepository.saveAll(dbnotedList);
				this.actranhRepository.saveAll(AddPojoEntityMapper.addActranhPojoEntityListMapping.apply(actranhList));
				this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));
				this.pbillhRepository.saveAll(pbillhList);
			}

			if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("L")) {
				materialDetailRequestBean.getAuthHRequestBean()
						.setAuthamount(materialDetailRequestBean.getAuthHRequestBean().getTotalRelretamount());
				materialDetailRequestBean.getAuthHRequestBean()
						.setRelretain(materialDetailRequestBean.getAuthHRequestBean().getTotalRelretamount());
				materialDetailRequestBean.getAuthHRequestBean()
						.setPayamount(materialDetailRequestBean.getAuthHRequestBean().getTotalPayamount());

				if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())) {
					materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
						authDRequestBean.setRetentionadj(BigInteger.ZERO.doubleValue());
						authDRequestBean.setAuthtype(materialDetailRequestBean.getAuthHRequestBean().getAuthtype());
						authDRequestBean.setBldgcode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode());
						authDRequestBean.setMatgroup(materialDetailRequestBean.getAuthHRequestBean().getMatgroup());
						authDRequestBean.setPartycode(materialDetailRequestBean.getAuthHRequestBean().getPartycode());
						authDRequestBean.setAuthtdsamt(BigInteger.ZERO.doubleValue()); // ----> as discussed with vikram
																						// it shows on screen but goes
																						// zero from old system
						authDRequestBean.setAuthamount(authDRequestBean.getAuthamount()
								- (Objects.nonNull(authDRequestBean.getDebitamt()) ? authDRequestBean.getDebitamt()
										: BigInteger.ZERO.doubleValue())); // -----------> as discussed with vikram sir
																			// authamount - debit amount
						return authDRequestBean;
					}).collect(Collectors.toList());

					List<Pbilld> pbilldEntityList = this.pbilldRepository.findByBySerIn(
							materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
								return authDRequestBean.getSer();
							}).collect(Collectors.toSet()));
					LOGGER.info("pbilldEntityList : {} ", pbilldEntityList);

					if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthDRequestBeanList())
							&& CollectionUtils.isNotEmpty(pbilldEntityList)) {
						materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
							pbilldEntityList.stream().filter(filter -> {
								return filter.getPbldSuppbillno().trim().equals(authDRequestBean.getSuppbillno().trim())
										&& filter.getPbldSuppbilldt()
												.equals(LocalDate.parse(authDRequestBean.getSuppbilldt(),
														CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
							}).map(pbilldEntity -> {
								authDRequestBean.setMatcode(
										Objects.nonNull(pbilldEntity.getPbldMatcode()) ? pbilldEntity.getPbldMatcode()
												: null);
								return pbilldEntity;
							}).collect(Collectors.toList());
							return authDRequestBean;
						}).collect(Collectors.toList());
					}
					this.authDRepository.saveAll(Auth_DEntityPojoMapper.addAuth_DEntityPojoMapper
							.apply(new Object[] { materialDetailRequestBean.getAuthDRequestBeanList(), authNo }));
				}

				List<Pbillh> pbillhEntityList = this.pbillhRepository.findByPbillhCK_PblhSerIn(
						materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean -> {
							return authDRequestBean.getSer();
						}).collect(Collectors.toSet()));
				LOGGER.info("pbillhEntityList : {} ", pbillhEntityList);

				if (CollectionUtils.isNotEmpty(pbillhEntityList)) {
					this.pbillhRepository.saveAll(pbillhEntityList.stream().map(pbillhEntity -> {
						materialDetailRequestBean.getAuthDRequestBeanList().stream().filter(filter -> {
							return pbillhEntity.getPblhSuppbillno().trim().equals(filter.getSuppbillno().trim())
									&& pbillhEntity.getPbillhCK().getPblhSer().trim().equals(filter.getSer().trim());
						}).map(authDRequestBean -> {
							pbillhEntity.setPblhAuthnum(authNo);
							pbillhEntity.setPblhAuthdate(
									Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
											? LocalDate.parse(
													materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
													CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
											: null);
							pbillhEntity
									.setPblhRetainos(authDRequestBean.getRetainos() - authDRequestBean.getRelretamt());
							pbillhEntity.setPblhPaidamt(authDRequestBean.getPayamount());
							pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid());
							pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite());
							pbillhEntity.setPblhToday(LocalDateTime.now());
							return authDRequestBean;
						}).collect(Collectors.toList());

						return pbillhEntity;
					}).collect(Collectors.toList()));
				}
				// dbnoth not updating in old system for L type
				// List<Dbnoteh> dbnotehEntityList =
				// this.dbnotehRepository.dbnotehSerByCoyAndBuildingAndSupplierAndSuppBillNoIn(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(),
				// materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
				// materialDetailRequestBean.getAuthDRequestBeanList().stream().map(authDRequestBean
				// ->{
				// return authDRequestBean.getSuppbillno().trim();
				// }).collect(Collectors.toSet()));
				// LOGGER.info("dbnotehEntityList : {} ", dbnotehEntityList);
				//
				// if(CollectionUtils.isNotEmpty(dbnotehEntityList)) {
				// dbnotehEntityList.stream().map(dbnotehEntity -> {
				// dbnotehEntity.setDbnhAuthno(authNo);
				// dbnotehEntity.setDbnhAuthdate(Objects.nonNull(materialDetailRequestBean.getAuthHRequestBean().getAuthdate())
				// ?
				// LocalDate.parse(materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
				// CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);
				// dbnotehEntity.setDbnhUserid(GenericAuditContextHolder.getContext().getUserid());
				// dbnotehEntity.setDbnhSite(GenericAuditContextHolder.getContext().getSite());
				// dbnotehEntity.setDbnhToday(LocalDateTime.now());
				// this.dbnotehRepository.save(dbnotehEntity);
				// return dbnotehEntity;
				// }).collect(Collectors.toList());
				// }

			}
			this.authHRepository.save(Auth_HEntityPojoMapper.addAuth_HEntityPojoMapper
					.apply(new Object[] { materialDetailRequestBean.getAuthHRequestBean() }));
		}

		if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthmatgroupnarrdtlRequestBeanList())) {
			// if(CollectionUtils.isNotEmpty(materialDetailRequestBean.getAuthmatgroupnarrdtlRequestBeanList())
			// &&
			// materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("A"))
			// {
			materialDetailRequestBean.getAuthmatgroupnarrdtlRequestBeanList().stream()
					.map(authmatgroupnarrdtlRequestBean -> {
						authmatgroupnarrdtlRequestBean.setAmount(BigInteger.ZERO.doubleValue());
						authmatgroupnarrdtlRequestBean.setQuantity(BigInteger.ZERO.doubleValue());
						return authmatgroupnarrdtlRequestBean;
					}).collect(Collectors.toList());
			this.authmatgroupnarrdtlRepository.saveAll(
					AuthmatgroupnarrdtlEntityPojoMapper.addAuthmatgroupnarrdtlEntityPojoMapper.apply(new Object[] {
							materialDetailRequestBean.getAuthmatgroupnarrdtlRequestBeanList(), authNo }));
		}

		if (CollectionUtils.isNotEmpty(materialDetailRequestBean.getAdvrecvoucherRequestBeanList())) {
			materialDetailRequestBean.getAdvrecvoucherRequestBeanList().stream().map(advrecvoucherRequestBean -> {
				advrecvoucherRequestBean.setOrigsys("AU");
				advrecvoucherRequestBean.setPartytype(PartyType.S.toString());
				advrecvoucherRequestBean.setCoy(StrPriCoy.getValue());
				advrecvoucherRequestBean.setOrigdocdate(materialDetailRequestBean.getAuthHRequestBean().getAuthdate());
				return advrecvoucherRequestBean;
			}).collect(Collectors.toList());

			this.advrecvoucherRepository.saveAll(AdvrecvoucherEntityPojoMapper.addAdvrecvoucherEntityPojoMapper
					.apply(new Object[] { materialDetailRequestBean.getAdvrecvoucherRequestBeanList(), authNo }));
		}
		GenericAuditContextHolder.getContext().setTransactionNo(authNo);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Data Added Successfully with Auth No : ".concat(authNo)
						.concat(CollectionUtils.isNotEmpty(dbnoteSerList)
								? " and Debit Note No :".concat(String.join(",", dbnoteSerList))
								: " "))
				.build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchItemByBldgCodeAndMatGrp(String bldgCode, String matGrp) {
		Query query = this.entityManager.createNativeQuery(
				"SELECT mcim_srno AS cwnd_srno,mcim_itemdesc AS cwnd_itemdesc,0 AS cwnd_amount FROM  matcertitemmaster \r\n"
						+ "WHERE mcim_closedate = '01-JAN-2050' and mcim_matcerttype = 'Material' \r\n"
						+ "AND mcim_bldgtype = (SELECT ENT_CHAR1 FROM ENTITY \r\n"
						+ "WHERE ENT_CLASS = 'BTYPE' AND TRIM(ENT_ID) = (SELECT TRIM(bldg_bldgtype) FROM building WHERE TRIM(bldg_code) = :bldgCode)) AND \r\n"
						+ "TRIM(mcim_matcertcode) = :matGrp ORDER BY mcim_srno",
				Tuple.class);

		query.setParameter("bldgCode", Objects.nonNull(bldgCode) ? bldgCode.trim() : " ");
		query.setParameter("matGrp", Objects.nonNull(matGrp) ? matGrp.trim() : " ");
		List<Tuple> tuplesList = query.getResultList();
		List<ItemDetailResponseBean> itemDetailResponseBeanList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(tuplesList)) {
			tuplesList.stream().map(t -> {
				itemDetailResponseBeanList.add(ItemDetailResponseBean.builder().srno(t.get(0, BigDecimal.class))
						.itemdesc(t.get(1, String.class)).build());
				return t;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(itemDetailResponseBeanList).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("No item narrationwise amount data available for this Material Group").build());
	}

	@Override
	public ResponseEntity<?> fetchAuthorisationsForPartyAndBuildingAndMatGroup(String partyCode, String buildingCode,
			String matGroup) {

		List<AuthHCancelMaterialBean> authorisationsCancelMaterialList = this.authHRepository
				.findByPartyCodeAndBuildingAndMatGroup(partyCode.trim(), buildingCode.trim(), matGroup.trim());
		if (!authorisationsCancelMaterialList.isEmpty()) {
			List<AuthHCancelMaterialBean> authCancelMaterialResponse = new ArrayList<>();
			for (int i = 0; i < authorisationsCancelMaterialList.size(); i++) {
				authCancelMaterialResponse.add(AuthHCancelMaterialBean.builder()
						.authamount(Objects.nonNull(authorisationsCancelMaterialList.get(i).getAuthamount())
								? authorisationsCancelMaterialList.get(i).getAuthamount()
								: 0)
						.authdate(Objects.nonNull(authorisationsCancelMaterialList.get(i).getAuthdate())
								? authorisationsCancelMaterialList.get(i).getAuthdate()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.authnum(Objects.nonNull(authorisationsCancelMaterialList.get(i).getAuthnum())
								? authorisationsCancelMaterialList.get(i).getAuthnum()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.authstatus(Objects.nonNull(authorisationsCancelMaterialList.get(i).getAuthstatus())
								? authorisationsCancelMaterialList.get(i).getAuthstatus()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.authtype(Objects.nonNull(authorisationsCancelMaterialList.get(i).getAuthtype())
								? authorisationsCancelMaterialList.get(i).getAuthtype()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.partycode(Objects.nonNull(authorisationsCancelMaterialList.get(i).getPartycode())
								? authorisationsCancelMaterialList.get(i).getPartycode()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.passedon(Objects.nonNull(authorisationsCancelMaterialList.get(i).getPassedon())
								? authorisationsCancelMaterialList.get(i).getPassedon()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.paydate(Objects.nonNull(authorisationsCancelMaterialList.get(i).getPaydate())
								? authorisationsCancelMaterialList.get(i).getPaydate()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.payref(Objects.nonNull(authorisationsCancelMaterialList.get(i).getPayref())
								? authorisationsCancelMaterialList.get(i).getPayref()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.transer(Objects.nonNull(authorisationsCancelMaterialList.get(i).getTranser())
								? authorisationsCancelMaterialList.get(i).getTranser()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.payamount(Objects.nonNull(authorisationsCancelMaterialList.get(i).getPayamount())
								? authorisationsCancelMaterialList.get(i).getPayamount()
								: 0)
						.bldgCode(Objects.nonNull(authorisationsCancelMaterialList.get(i).getBldgCode())
								? authorisationsCancelMaterialList.get(i).getBldgCode()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.matGroup(Objects.nonNull(authorisationsCancelMaterialList.get(i).getMatGroup())
								? authorisationsCancelMaterialList.get(i).getMatGroup()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.build());
			}
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(authCancelMaterialResponse).build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("No Authorisations have been created of followning Combination -" + partyCode + ","
							+ buildingCode + "," + matGroup)
					.build());
		}
	}

	@Override
	public ResponseEntity<?> fetchPassMaterialPaymentList() {
		List<AuthHPassMaterialBean> authHEntity = this.authHRepository
				.findByAuthAuthstatusLessThanAndAuthAuthtypeNotAndAuthPrintedonNotNullAndAuthPassedonOrderByAuthhCK_AuthAuthnumAndAuthsite(
						"5", "M", CommonUtils.INSTANCE.closeDateInLocalDateTime(),
						GenericAuditContextHolder.getContext().getSite());
		if (CollectionUtils.isNotEmpty(authHEntity)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(Auth_HEntityPojoMapper.fetchAuthHPassEntityPojoMapper.apply(authHEntity)).build());
		}

		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Unpaid authorizations.").build());
	}

	@Override
	public ResponseEntity<?> fetchAuthorisationDetailsByAuthNum(String authNum) {
		List<AuthDCancelMaterialBean> authCancelMaterialDetails = this.authDRepository
				.findAuthDetailsByAuthNum(authNum.trim());
		if (!authCancelMaterialDetails.isEmpty()) {
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(authCancelMaterialDetails).build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("There are no bills for this authorisations..").build());
		}
	}

	@Override
	public ResponseEntity<?> checkIsAuthorisationValidForCancelation(String authTranser, String authStatus) {
		if (authStatus.equals("7")) {
			String authReverseYN = this.actranhRepository.findActranhReversedYNByTranser(authTranser);
			if (Objects.nonNull(authReverseYN)) {
				if (authReverseYN.trim().equals(CommonConstraints.INSTANCE.BLANK_STRING)) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("Warning Can't cancel the authorisation since payment is been done").build());
				}
				if (authReverseYN.trim().equals("N")) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(
							"Warning - This Authorisation is passed and possibly paid off Please contact Accounts Department to cancel this Authorisation.")
							.build());
				}
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> cancelMaterialPayment(
			List<CancelMaterialPaymentRequestBean> cancelMaterialPaymentRequestBeanList) {
		List<String> authNos = new ArrayList<>();
		for (int i = 0; i < cancelMaterialPaymentRequestBeanList.size(); i++) {
			authNos.add(cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
			LOGGER.info("Auth Num: {}", cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
			String authReverseYN = this.actranhRepository
					.findActranhReversedYNByTranser(cancelMaterialPaymentRequestBeanList.get(i).getTranser().trim());
			String reversedYN = Objects.nonNull(authReverseYN) ? authReverseYN : "";
			List<Auth_D> authDEntityList = this.authDRepository
					.findByAuthdCK_AutdAuthnum(cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
			LOGGER.info("Auth_D List: {} ", authDEntityList);
			if (cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().equals("1")
					|| cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().trim().equals("2")
					|| cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().equals("3")
					|| cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().equals("5")) {

				if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("N")) {
					// Updated Bill Updation for Material Cancellation
					for (int m = 0; m < authDEntityList.size(); m++) {
						this.dbnotehRepository.revertDbnotehAuthorisation(
								authDEntityList.get(m).getAuth_dCK().getAutdAuthnum().trim(),
								GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
								GenericAuditContextHolder.getContext().getUserid());
						this.pbillhRepository
								.updatePbillhAuthNumAndAuthDateAndAdvPaidAndAdvAdjByPartyCodeAndBldCodeAndMatGroup(
										GenericAuditContextHolder.getContext().getUserid(),
										GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
										cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
										authDEntityList.get(m).getAuth_dCK().getAutdSuppbillno().trim(),
										authDEntityList.get(m).getAutdSuppbilldt());
					}
				}
				if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("L")
						&& !authDEntityList.isEmpty()) {
					for (int j = 0; j < authDEntityList.size(); j++) {
						// this.pbillhRepository.updatePbillhAuthNumAndAuthDateAndRetainNosByAuthNumAndSuppBillNoAndSuppBillDate(authDEntityList.get(j).getAutdRelretamt(),
						// GenericAuditContextHolder.getContext().getUserid(),
						// GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
						// cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim(),
						// authDEntityList.get(0).getAuth_dCK().getAutdSuppbillno().trim() ,
						// authDEntityList.get(j).getAutdSuppbilldt());
						this.pbillhRepository
								.updatePbillhAuthNumAndAuthDateAndRetainNosByPartyCodeAndBldCodeAndMatGroup(
										authDEntityList.get(j).getAutdRelretamt(),
										GenericAuditContextHolder.getContext().getUserid(),
										GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
										cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
										authDEntityList.get(j).getAuth_dCK().getAutdSuppbillno().trim(),
										authDEntityList.get(j).getAutdSuppbilldt());
					}
				}
				if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("R")
						&& !authDEntityList.isEmpty()) {
					for (int k = 0; k < authDEntityList.size(); k++) {
						this.pbillhRepository
								.updatePbillhAuthNumAndAuthDateAndRetainNosAndDebitAmntByPartyCodeAndBldgCodeAndMatGroupAndSuppBillNoAndSuppBillDate(
										authDEntityList.get(k).getAutdRetentionadj(),
										authDEntityList.get(k).getAutdRetentionadj(),
										GenericAuditContextHolder.getContext().getUserid(),
										GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
										cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
										cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
										authDEntityList.get(k).getAuth_dCK().getAutdSuppbillno().trim(),
										authDEntityList.get(k).getAutdSuppbilldt());
					}
					List<String> dbnotehSers = this.dbnotehRepository.fetchDbnotehSerListByAuthNum(
							cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
					for (String dbnotehSer : dbnotehSers) {
						Dbnoteh dbnoteh = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(dbnotehSer.trim());
						if (Objects.nonNull(dbnoteh)) {
							String reversalTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER",
									"DNSER", GenericAuditContextHolder.getContext().getSite());
							LOGGER.info("Reversal Transer No : {}", reversalTranserNo);
							this.dbnotehRepository.delete(dbnoteh);
							insertIntoAccountingTableForMaterialPaymentCancellation(dbnotehSer.trim(),
									reversalTranserNo,
									cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim());
							this.actranhRepository.updateActranhReverseYNWithUserAndSiteAndToday("Y", dbnotehSer.trim(),
									GenericAuditContextHolder.getContext().getUserid(),
									GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now());
							this.dbnotedRepository.deleteDbnotedBySer(dbnotehSer.trim());
						}

					}
				}
				if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("A")) {
					this.advrecvoucherRepository.deleteAdvreVoucherByOrigSysAndOrigDocNum("AU",
							cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());

				}
				if (!cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("R")) {
					this.actrandRepository
							.deleteActrand(cancelMaterialPaymentRequestBeanList.get(i).getTranser().trim());
					this.actranhRepository
							.deleteActranh(cancelMaterialPaymentRequestBeanList.get(i).getTranser().trim());
					this.exnarrRepository
							.deleteExnarrByTranser(cancelMaterialPaymentRequestBeanList.get(i).getTranser().trim());
				}
				this.authHRepository
						.deleteAuthHByAuthNum(cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
				this.authDRepository
						.deleteAuthDByAuthNum(cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
				this.authmatgroupnarrdtlRepository.deleteAuthmatgroupnarrdtlByAuthNum(
						cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
				this.contractdebitRepository.deleteContractdebitByAuthNum(
						cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());

			}
			if (cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().trim().equals("7")
					|| cancelMaterialPaymentRequestBeanList.get(i).getAuthstatus().equals("8")) {
				if (reversedYN.equals("Y")) {

					this.authHRepository.updateAuthHStatusByAuthNum("6",
							GenericAuditContextHolder.getContext().getSite(),
							GenericAuditContextHolder.getContext().getUserid(), LocalDateTime.now());
					if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("N")) {
						for (int m = 0; m < authDEntityList.size(); m++) {
							this.dbnotehRepository.revertDbnotehAuthorisation(
									authDEntityList.get(m).getAuth_dCK().getAutdAuthnum().trim(),
									GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
									GenericAuditContextHolder.getContext().getUserid());
							this.pbillhRepository
									.updatePbillhAuthNumAndAuthDateAndAdvPaidAndAdvAdjByPartyCodeAndBldCodeAndMatGroup(
											GenericAuditContextHolder.getContext().getUserid(),
											GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
											cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
											authDEntityList.get(m).getAuth_dCK().getAutdSuppbillno().trim(),
											authDEntityList.get(m).getAutdSuppbilldt());
						}
						// this.dbnotehRepository.revertDbnotehAuthorisation(cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim(),
						// GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
						// GenericAuditContextHolder.getContext().getUserid());
						// this.pbillhRepository.updatePbillhAuthNumAndAuthDateAndAdvPaidAndAdvAdjByPartyCodeAndBldCodeAndMatGroup(GenericAuditContextHolder.getContext().getUserid(),
						// GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
						// cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
						// cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
						// cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
						// authDEntityList.get(0).getAuth_dCK().getAutdSuppbillno().trim(),
						// authDEntityList.get(0).getAutdSuppbilldt());
					}
					if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("L")
							&& !authDEntityList.isEmpty()) {
						for (int j = 0; j < authDEntityList.size(); j++) {
							this.pbillhRepository
									.updatePbillhAuthNumAndAuthDateAndRetainNosByPartyCodeAndBldCodeAndMatGroup(
											authDEntityList.get(j).getAutdRelretamt(),
											GenericAuditContextHolder.getContext().getUserid(),
											GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
											cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
											authDEntityList.get(j).getAuth_dCK().getAutdSuppbillno().trim(),
											authDEntityList.get(j).getAutdSuppbilldt());
							// this.pbillhRepository.updatePbillhAuthNumAndAuthDateAndRetainNosByAuthNumAndSuppBillNoAndSuppBillDate(authDEntityList.get(0).getAutdRelretamt(),
							// GenericAuditContextHolder.getContext().getUserid(),
							// GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
							// cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim(),
							// authDEntityList.get(0).getAuth_dCK().getAutdSuppbillno().trim() ,
							// authDEntityList.get(0).getAutdSuppbilldt());
						}
					}
					if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("R")
							&& !authDEntityList.isEmpty()) {
						for (int k = 0; k < authDEntityList.size(); k++) {
							this.pbillhRepository
									.updatePbillhAuthNumAndAuthDateAndRetainNosAndDebitAmntByPartyCodeAndBldgCodeAndMatGroupAndSuppBillNoAndSuppBillDate(
											authDEntityList.get(k).getAutdRetentionadj(),
											authDEntityList.get(k).getAutdRetentionadj(),
											GenericAuditContextHolder.getContext().getUserid(),
											GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
											cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getBldgCode().trim(),
											cancelMaterialPaymentRequestBeanList.get(i).getMatGroup().trim(),
											authDEntityList.get(k).getAuth_dCK().getAutdSuppbillno().trim(),
											authDEntityList.get(k).getAutdSuppbilldt());
							// this.pbillhRepository.updatePbillhAuthNumAndAuthDateAndRetainNosAndDebitAmntByAuthNumAndSuppBillNoAndSuppBillDate(authDEntityList.get(0).getAutdRetentionadj(),
							// authDEntityList.get(0).getAutdRetentionadj(),
							// GenericAuditContextHolder.getContext().getUserid(),
							// GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now(),
							// cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim(),
							// authDEntityList.get(0).getAuth_dCK().getAutdSuppbillno().trim() ,
							// authDEntityList.get(k).getAutdSuppbilldt());
						}
						List<String> dbnotehSers = this.dbnotehRepository.fetchDbnotehSerListByAuthNum(
								cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
						for (String dbnotehSer : dbnotehSers) {
							Dbnoteh dbnoteh = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(dbnotehSer.trim());
							if (Objects.nonNull(dbnoteh)) {
								String reversalTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite(
										"#NSER", "DNSER", GenericAuditContextHolder.getContext().getSite());
								LOGGER.info("Reversal Transer No : {}", reversalTranserNo);
								this.dbnotehRepository.delete(dbnoteh);
								insertIntoAccountingTableForMaterialPaymentCancellation(dbnotehSer.trim(),
										reversalTranserNo,
										cancelMaterialPaymentRequestBeanList.get(i).getPartycode().trim());
								this.actranhRepository.updateActranhReverseYNWithUserAndSiteAndToday("Y",
										dbnotehSer.trim(), GenericAuditContextHolder.getContext().getUserid(),
										GenericAuditContextHolder.getContext().getSite(), LocalDateTime.now());
								this.dbnotedRepository.deleteDbnotedBySer(dbnotehSer.trim());
							}

						}
					}
					if (cancelMaterialPaymentRequestBeanList.get(i).getAuthtype().trim().equals("A")) {
						this.advrecvoucherRepository.deleteAdvreVoucherByOrigSysAndOrigDocNum("AU",
								cancelMaterialPaymentRequestBeanList.get(i).getAuthnum().trim());
					}
				}

			}

		}

//		GenericAuditContextHolder.getContext().setTransactionNo(String.join("", authNos).length() > 21 ? String.join("", authNos).substring(0,20) : String.join("", authNos));
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		GenericAuditContextHolder.getContext().setTransactionList(authNos);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Authorisations " + String.join(",", authNos) + " Cancelled Successfully").build());
	}

	public void insertIntoAccountingTableForMaterialPaymentCancellation(String transerNo, String newTranserNo,
			String partyCode) {
		Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndReverseYN(transerNo.trim(), "Y");
		LOGGER.info("ActranH Entity : {} ", actranh);
		if (Objects.nonNull(actranh)) {// PostedYN Condition Remoeved as Per Discussion
			ActranhBean actranhBean = ActranhBean.builder().transer(newTranserNo)
					.trantype(Objects.nonNull(actranh.getActhTrantype()) ? actranh.getActhTrantype() : null)
					.trandate(Objects.nonNull(actranh.getActhTrandate())
							? actranh.getActhTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					.ledgcode(Objects.nonNull(actranh.getActhLedgcode()) ? actranh.getActhLedgcode() : null)
					.partytype(Objects.nonNull(actranh.getActhPartytype()) ? actranh.getActhPartytype() : null)
					.partycode(Objects.nonNull(actranh.getActhPartycode()) ? actranh.getActhPartycode() : null)
					.tranamt(actranh.getActhTranamt() * -1)
					.voudate(Objects.nonNull(actranh.getActhVoudate())
							? actranh.getActhVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					.vounum(actranh.getActhVounum())
					.bankcode(Objects.nonNull(actranh.getActhBankcode()) ? actranh.getActhBankcode() : null)
					.postedyn(Objects.nonNull(actranh.getActhPostedyn()) ? actranh.getActhPostedyn() : null)
					.balancedyn(Objects.nonNull(actranh.getActhBalancedyn()) ? actranh.getActhBalancedyn() : null)
					.closingjvyn(Objects.nonNull(actranh.getActhClosingjvyn()) ? actranh.getActhClosingjvyn() : null)
					.narrative(("Reversal of Transer# '" + transerNo + "' for suppbillno '" + actranh.getActhVounum()
							+ "'").length() > 60
									? ("Reversal of Transer# '" + transerNo + "' for suppbillno '"
											+ actranh.getActhVounum() + "'").substring(0, 60)
									: "Reversal of Transer# '" + transerNo + "' for suppbillno '"
											+ actranh.getActhVounum() + "'")
					.proprietor(Objects.nonNull(actranh.getActhProprietor()) ? actranh.getActhProprietor() : null)
					.coy(Objects.nonNull(actranh.getActranhCK().getActhCoy()) ? actranh.getActranhCK().getActhCoy()
							: null)
					.bbookyn(Objects.nonNull(actranh.getActhBbookyn()) ? actranh.getActhBbookyn() : null)
					.cbookyn(Objects.nonNull(actranh.getActhCbookyn()) ? actranh.getActhCbookyn() : null)
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid(GenericAuditContextHolder.getContext().getUserid()).today(LocalDateTime.now())
					.reverseyn("Y").crepno(null)
					.clearacyn(Objects.nonNull(actranh.getActhClearacyn()) ? actranh.getActhClearacyn() : null)
					.provyn(Objects.nonNull(actranh.getActhProvyn()) ? actranh.getActhProvyn() : null).build();

			this.actranhRepository
					.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] { actranhBean }));

			List<Actrand> actrandList = this.actrandRepository.findActdByTranserNo(transerNo);
			LOGGER.info("ActranD List: {}", actrandList.size());
			List<ActrandBean> actrandBeanList = new ArrayList<>();
			for (int i = 0; i < actrandList.size(); i++) {
				actrandBeanList.add(ActrandBean.builder()
						.acmajor(Objects.nonNull(actrandList.get(i).getActdAcmajor())
								? actrandList.get(i).getActdAcmajor()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.acminor(Objects.nonNull(actrandList.get(i).getActdAcminor())
								? actrandList.get(i).getActdAcminor()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.bldgcode(Objects.nonNull(actrandList.get(i).getActdBldgcode())
								? actrandList.get(i).getActdBldgcode()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.bunum(actrandList.get(i).getActrandCK().getActdBunum())
						.cfcode(CommonConstraints.INSTANCE.BLANK_STRING)
						.cfgroup(CommonConstraints.INSTANCE.BLANK_STRING)
						.coy(Objects.nonNull(actrandList.get(i).getActrandCK().getActdCoy())
								? actrandList.get(i).getActrandCK().getActdCoy()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.docnum(Objects.nonNull(actrandList.get(i).getActdDocnum()) ? actrandList.get(i).getActdDocnum()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.docdate(Objects.nonNull(actrandList.get(i).getActdDocdate())
								? actrandList.get(i).getActdDocdate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.doctype(Objects.nonNull(actrandList.get(i).getActdDoctype())
								? actrandList.get(i).getActdDoctype()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.docparcode(partyCode).docpartype("S")
						.matcode(Objects.nonNull(actrandList.get(i).getActdMatcode())
								? actrandList.get(i).getActdMatcode()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.matgroup(Objects.nonNull(actrandList.get(i).getActdMatgroup())
								? actrandList.get(i).getActdMatgroup()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.mincode(Objects.nonNull(actrandList.get(i).getActdMincode())
								? actrandList.get(i).getActdMincode()
								: null)
						.mintype(Objects.nonNull(actrandList.get(i).getActdMintype())
								? actrandList.get(i).getActdMintype()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.itemqty(Objects.nonNull(actrandList.get(i).getActdItemqty())
								? actrandList.get(i).getActdItemqty()
								: 0)
						.ledgcode("PL")
						.partycode((Objects.nonNull(actrandList.get(i).getActdPartycode()))
								? actrandList.get(i).getActdPartycode()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.partytype((Objects.nonNull(actrandList.get(i).getActdPartytype()))
								? actrandList.get(i).getActdPartytype()
								: null)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.project(Objects.nonNull(actrandList.get(i).getActdProject())
								? actrandList.get(i).getActdProject()
								: "GL")
						.property(Objects.nonNull(actrandList.get(i).getActdProperty())
								? actrandList.get(i).getActdProperty()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.proprietor(actrandList.get(i).getActdProprietor())
						.narrative(Objects.nonNull(actrandList.get(i).getActdNarrative())
								? actrandList.get(i).getActdNarrative()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.sku(Objects.nonNull(actrandList.get(i).getActdSku()) ? actrandList.get(i).getActdSku()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.voudate(Objects.nonNull(actrandList.get(i).getActdVoudate())
								? actrandList.get(i).getActdVoudate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.vounum(Objects.nonNull(actrandList.get(i).getActdVounum()) ? actrandList.get(i).getActdVounum()
								: CommonConstraints.INSTANCE.BLANK_STRING)
						.trandate(actrandList.get(i).getActdTrandate()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.tranamt(actrandList.get(i).getActdTranamt() * -1).trantype("D")
						.xproject(Objects.nonNull(actrandList.get(i).getActdXproject())
								? actrandList.get(i).getActdXproject()
								: null)
						.transer(newTranserNo)
						.xacminor(Objects.nonNull(actrandList.get(i).getActdXacminor())
								? actrandList.get(i).getActdXacminor()
								: null)
						.xacmajor(Objects.nonNull(actrandList.get(i).getActdXacmajor())
								? actrandList.get(i).getActdXacmajor()
								: null)
						.site(GenericAuditContextHolder.getContext().getSite().trim())
						.userid(GenericAuditContextHolder.getContext().getUserid().trim()).build());
			}
			this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
		}
	}

	@Override
	public ResponseEntity<?> passMaterialPayment(PassMaterialPaymentRequestBean materialPaymentRequestBean) {
		if (Objects.nonNull(materialPaymentRequestBean)) {
			Map<String, String> transerMap = new HashMap<>();
			List<String> transerNoList = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(materialPaymentRequestBean.getMaterialPaymentRequestBeanList()))
				materialPaymentRequestBean.getMaterialPaymentRequestBeanList().stream().map(matpayment -> {
					String transer;
					String trantype;
					if (matpayment.getProject().trim().equals("") || matpayment.getProject().equals("GL"))
						matpayment.setProject("GL");

					if (matpayment.getPayamount() < BigInteger.ZERO.doubleValue()) {
						transer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "RCSER",
								GenericAuditContextHolder.getContext().getSite());
						trantype = "RC";
					} else {
						transer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "PMSER",
								GenericAuditContextHolder.getContext().getSite());
						trantype = "PF";
					}
					List<InchqRequestBean> inchqRequestBeanList = new ArrayList<>();

					String propreitor = this.companyRepository
							.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(matpayment.getCoy().trim(),
									CommonUtils.INSTANCE.closeDate());
					inchqRequestBeanList.add(InchqRequestBean.builder().num(matpayment.getChqnum())
							.amount(matpayment.getAmount()).chqDate(matpayment.getChqDate()).bank(matpayment.getBank())
							.transer(transer).outstat(matpayment.getOutstat()).coy(matpayment.getCoy())
							.proprietor(propreitor).origsys("AC").partycode(matpayment.getPartycode())
							.recnum(matpayment.getAuthnum()).origsite(GenericAuditContextHolder.getContext().getSite())
							.site(GenericAuditContextHolder.getContext().getSite())
							.userid(GenericAuditContextHolder.getContext().getUserid()).build());
					if (matpayment.getPayamount() < BigInteger.ZERO.doubleValue())
						this.inchqRepository
								.saveAll(AddPojoEntityMapper.addInchqPojoEntityMappingList.apply(new Object[] {
										inchqRequestBeanList, GenericAuditContextHolder.getContext().getSite() }));

					Material materialEntity = this.materialRepository
							.findByMaterialCK_MatMatgroupAndMatLevelAndMatClosedate(matpayment.getMatgroup(), "1",
									null);
					matpayment.setMatname(materialEntity.getMatMatname());
					Building buildingEntity = this.buildingRepository
							.findByBuildingCK_BldgCode(matpayment.getBldgcode());
					List<Auth_D> authdEntityList = this.authDRepository
							.findByAuthdCK_AutdAuthnum(matpayment.getAuthnum());
					this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping
							.apply(new Object[] { ActranhBean.builder().transer(transer).trantype(trantype)
									.trandate(LocalDateTime.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.ledgcode("PL").partytype(CommonConstraints.INSTANCE.SUPPLIERS)
									.partycode(matpayment.getPartycode()).tranamt(Math.abs(matpayment.getPayamount()))
									.proprietor(matpayment.getProp()).postedyn("N").provyn("N").coy(matpayment.getCoy())
									.site(GenericAuditContextHolder.getContext().getSite())
									.userid(GenericAuditContextHolder.getContext().getUserid()).clearacyn("Y")
									.reverseyn("N").build() }));

					List<ActrandBean> actrandBeanList = new ArrayList<ActrandBean>();
					String doctype;
					String docnum;
					String docdate;
					String narrative;
					if (Objects.nonNull(matpayment.getAuthtype()))
						switch (AuthTypeEnum.getValue(matpayment.getAuthtype().trim())) {
						case A: {
							String findSumOfGstPerc = advrecvoucherRepository.findSumOfGstPerc(matpayment.getAuthnum());
							Party partyEntity = this.partyRepository
									.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
											matpayment.getPartycode(), CommonUtils.INSTANCE.closeDateInLocalDateTime(),
											PartyType.S.toString());
							Integer breakupnum = BigInteger.ONE.intValue();
							doctype = "A";
							docnum = matpayment.getMatgroup().concat(CommonConstraints.INSTANCE.FORWARD_SLASH_STRING)
									.concat(matpayment.getAuthnum());
							docdate = matpayment.getAuthdate();
							narrative = "Advance Money";

							if (Objects.nonNull(partyEntity) && StringUtils.isBlank(partyEntity.getParGstno())) {
								AccountingBean accoutingDataForTran = GenericAccountingLogic.gatherMinorPartyDetails(
										"11401019", "", "", PartyType.S.toString(), matpayment.getPartycode(),
										buildingEntity.getBldgProject(), matpayment.getPartycode());
								// ' For Company, Credit bank / cash account = "80000006"
								// ' 80000006 - BANK ACCOUNT
								AccountingBean accoutingDataForContraTran = GenericAccountingLogic
										.gatherMinorPartyDetails("80000006", "", "", PartyType.S.toString(),
												matpayment.getPartycode(), buildingEntity.getBldgProject(), " ");
								AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019",
										trantype);
								AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic
										.getCashFlow("80000006", trantype);
								actrandBeanList.addAll(insActrand(breakupnum, matpayment, accoutingDataForTran,
										accoutingDataForContraTran, accoutingDataCashFlow,
										accoutingDataCashFlowForContra, matpayment.getPayamount(),
										matpayment.getBldgcode(), buildingEntity.getBldgProperty(), transer, trantype,
										doctype, docnum, docdate, narrative));
							} else {
								Double tranamt = BigInteger.ZERO.doubleValue();
								if (StringUtils.isNotBlank(findSumOfGstPerc)) {
									String[] gstSumAmounts = findSumOfGstPerc
											.split(CommonConstraints.INSTANCE.COMMA_STRING);
									tranamt = Double.valueOf(gstSumAmounts[0]) + Double.valueOf(gstSumAmounts[1])
											+ Double.valueOf(gstSumAmounts[2]) + Double.valueOf(gstSumAmounts[3])
											+ Double.valueOf(gstSumAmounts[4]);
								}
								AccountingBean accoutingDataForTran = GenericAccountingLogic.gatherMinorPartyDetails(
										"11401019", "", "", PartyType.S.toString(), matpayment.getPartycode(),
										buildingEntity.getBldgProject(), matpayment.getPartycode());
								// ' For Company, Credit bank / cash account = "80000006"
								// ' 80000006 - BANK ACCOUNT
								AccountingBean accoutingDataForContraTran = GenericAccountingLogic
										.gatherMinorPartyDetails("80000006", " ", " ", "", "", "GL", " ");
								AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019",
										trantype);
								AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic
										.getCashFlow("80000006", trantype);
								actrandBeanList.addAll(insActrand(breakupnum, matpayment, accoutingDataForTran,
										accoutingDataForContraTran, accoutingDataCashFlow,
										accoutingDataCashFlowForContra, tranamt, matpayment.getBldgcode(),
										buildingEntity.getBldgProperty(), transer, trantype, doctype, docnum, docdate,
										narrative));
							}

							break;
						}
						// '...........................................................
						// ' R e l e a s e o f R e t e n t i o n
						// '...........................................................
						case L: {
							if (CollectionUtils.isEmpty(authdEntityList))
								return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
										.message("Authorisation Bill Details are not present.").build());
							else {
								Integer breakupnum = BigInteger.ONE.intValue();
								Boolean isMultipleEntries = Boolean.FALSE;
								for (Auth_D authd : authdEntityList) {
									if (isMultipleEntries)
										breakupnum += 2;
									doctype = "S";
									docnum = authd.getAuth_dCK().getAutdSuppbillno();
									docdate = authd.getAutdSuppbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
									narrative = "(Release of Retention)";
									if (Objects.nonNull(authd.getAutdRelretamt()) && authd.getAutdRelretamt() > 0) {
										AccountingBean accoutingDataForTran = GenericAccountingLogic
												.gatherMinorPartyDetails("11401019", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														matpayment.getPartycode());
										// ' For Company, Credit bank / cash account = "80000006"
										// ' 80000006 - BANK ACCOUNT
										AccountingBean accoutingDataForContraTran = GenericAccountingLogic
												.gatherMinorPartyDetails("80000006", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														" ");
										AccountingBean accoutingDataCashFlow = GenericAccountingLogic
												.getCashFlow("11401019", trantype);
										AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic
												.getCashFlow("80000006", trantype);
										actrandBeanList.addAll(insActrand(breakupnum, matpayment, accoutingDataForTran,
												accoutingDataForContraTran, accoutingDataCashFlow,
												accoutingDataCashFlowForContra, authd.getAutdRelretamt(),
												matpayment.getBldgcode(), buildingEntity.getBldgProperty(), transer,
												trantype, doctype, docnum, docdate, narrative));
										isMultipleEntries = Boolean.TRUE;
									}
								}
							}
							break;
						}
						// ...........................................................
						// A d v a n c e s R e t u r n e d f r o m S u p p l i e r s
						// ...........................................................
						case C: {
							Integer breakupnum = BigInteger.ONE.intValue();
							doctype = "A";
							docnum = matpayment.getMatgroup();
							docdate = matpayment.getAuthdate();
							narrative = "Advance Returned";
							// ' Advance Returned from Supppliers ...
							AccountingBean accoutingDataForTran = GenericAccountingLogic.gatherMinorPartyDetails(
									"11401019", "", "", PartyType.S.toString(), matpayment.getPartycode(),
									buildingEntity.getBldgProject(), matpayment.getPartycode());
							// ' For Company, Credit bank / cash account = "80000006"
							// ' 80000006 - BANK ACCOUNT
							// ' Amount is added to company's bank account ...
							AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(
									"80000006", "", "", PartyType.S.toString(), matpayment.getPartycode(),
									buildingEntity.getBldgProject(), " ");
							AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019",
									trantype);
							AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic
									.getCashFlow("80000006", trantype);
							actrandBeanList.addAll(insActrand(breakupnum, matpayment, accoutingDataForTran,
									accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra,
									matpayment.getPayamount(), matpayment.getBldgcode(),
									buildingEntity.getBldgProperty(), transer, trantype, doctype, docnum, docdate,
									narrative));

							break;
						}
						// '.........................................................
						// ' N o r m a l o r R e g u l a r a u t h o r i s a t i o n
						// '...........................................................
						case N: {
							if (CollectionUtils.isEmpty(authdEntityList))
								return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
										.message("Authorisation Bill Details are not present.").build());
							else {
								Integer breakupnum = BigInteger.ONE.intValue();
								Boolean isMultipleEntries = Boolean.FALSE;
								for (Auth_D authd : authdEntityList) {
									if (isMultipleEntries)
										breakupnum += 2; // Increaming bunum counter by 2 because we insert 2 records at
															// a time in actrand
									doctype = "S";
									docnum = authd.getAuth_dCK().getAutdSuppbillno();
									docdate = authd.getAutdSuppbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
									narrative = "";
									AccountingBean accoutingDataForTran = GenericAccountingLogic
											.gatherMinorPartyDetails("11401019", "", "", PartyType.S.toString(),
													matpayment.getPartycode(), buildingEntity.getBldgProject(),
													matpayment.getPartycode());
									// ' For Company, Credit bank / cash account = "80000006"
									// ' 80000006 - BANK ACCOUNT
									AccountingBean accoutingDataForContraTran = GenericAccountingLogic
											.gatherMinorPartyDetails("80000006", "", "", PartyType.S.toString(),
													matpayment.getPartycode(), buildingEntity.getBldgProject(), " ");
									AccountingBean accoutingDataCashFlow = GenericAccountingLogic
											.getCashFlow("11401019", trantype);
									AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic
											.getCashFlow("80000006", trantype);
									actrandBeanList.addAll(insActrand(breakupnum, matpayment, accoutingDataForTran,
											accoutingDataForContraTran, accoutingDataCashFlow,
											accoutingDataCashFlowForContra,
											authd.getAutdAuthamount() - authd.getAutdAuthtdsamt(),
											matpayment.getBldgcode(), buildingEntity.getBldgProperty(), transer,
											trantype, doctype, docnum, docdate, narrative));
									isMultipleEntries = Boolean.TRUE;
									// 'If there is any advance adjusted amount
									if (Objects.nonNull(authd.getAutdAdvadj()) && authd.getAutdAdvadj() > 0) {
										breakupnum += 2;
										doctype = "A";
										docnum = authd.getAuth_dCK().getAutdSuppbillno();
										docdate = authd.getAutdSuppbilldt()
												.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
										narrative = "Advance Adjusted";
										AccountingBean accoutingDataForTranForRetained = GenericAccountingLogic
												.gatherMinorPartyDetails("11401019", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														matpayment.getPartycode());
										// ' For Company, Credit bank / cash account = "80000006"
										// ' 80000006 - BANK ACCOUNT
										AccountingBean accoutingDataForContraTranForRetained = GenericAccountingLogic
												.gatherMinorPartyDetails("80000006", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														" ");
										AccountingBean accoutingDataCashFlowForRetained = GenericAccountingLogic
												.getCashFlow("11401019", trantype);
										AccountingBean accoutingDataCashFlowForContraForRetained = GenericAccountingLogic
												.getCashFlow("80000006", trantype);
										actrandBeanList.addAll(insActrand(breakupnum, matpayment,
												accoutingDataForTranForRetained, accoutingDataForContraTranForRetained,
												accoutingDataCashFlowForRetained,
												accoutingDataCashFlowForContraForRetained, authd.getAutdAdvadj() * -1,
												matpayment.getBldgcode(), buildingEntity.getBldgProperty(), transer,
												trantype, doctype, docnum, docdate, narrative));
										isMultipleEntries = Boolean.TRUE;
									}
									// 'If there is any retained amount
									if (Objects.nonNull(authd.getAutdRetainamt()) && authd.getAutdRetainamt() > 0) {
										breakupnum += 2;
										doctype = "A";
										docnum = authd.getAuth_dCK().getAutdSuppbillno();
										docdate = authd.getAutdSuppbilldt()
												.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
										narrative = "Retention Money";
										AccountingBean accoutingDataForTranForRetained = GenericAccountingLogic
												.gatherMinorPartyDetails("11401019", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														matpayment.getPartycode());
										// ' For Company, Credit bank / cash account = "80000006"
										// ' 80000006 - BANK ACCOUNT
										AccountingBean accoutingDataForContraTranForRetained = GenericAccountingLogic
												.gatherMinorPartyDetails("80000006", "", "", PartyType.S.toString(),
														matpayment.getPartycode(), buildingEntity.getBldgProject(),
														" ");
										AccountingBean accoutingDataCashFlowForRetained = GenericAccountingLogic
												.getCashFlow("11401019", trantype);
										AccountingBean accoutingDataCashFlowForContraForRetained = GenericAccountingLogic
												.getCashFlow("80000006", trantype);
										actrandBeanList.addAll(insActrand(breakupnum, matpayment,
												accoutingDataForTranForRetained, accoutingDataForContraTranForRetained,
												accoutingDataCashFlowForRetained,
												accoutingDataCashFlowForContraForRetained,
												authd.getAutdRetainamt() * -1, matpayment.getBldgcode(),
												buildingEntity.getBldgProperty(), transer, trantype, doctype, docnum,
												docdate, narrative));
										isMultipleEntries = Boolean.TRUE;
									}
								}
							}
							break;
						}
						default:
							break;
						}
					// inserting records in actrand
					this.actrandRepository
							.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));
					// Updating auth
					Auth_H authhEntity = this.authHRepository.findByAuthhCK_AuthAuthnum(matpayment.getAuthnum());
					authhEntity.setAuthPassedon(LocalDate.now());
					authhEntity.setAuthTranser(transer);
					authhEntity.setAuthAuthstatus("5");
					authhEntity.setAuthSite(GenericAuditContextHolder.getContext().getSite());
					authhEntity.setAuthUserid(GenericAuditContextHolder.getContext().getUserid());
					authhEntity.setAuthToday(LocalDateTime.now());
					this.authHRepository.save(authhEntity);

					transerMap.put(matpayment.getAuthnum(), transer);
					transerNoList.add(transer);
					return matpayment;
				}).collect(Collectors.toList());
//			GenericAuditContextHolder.getContext().setTransactionNo(String.join("", transerNoList).length() > 21 ? String.join("", transerNoList).substring(0,20) : String.join("", transerNoList));
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			GenericAuditContextHolder.getContext().setTransactionList(transerNoList);
			;
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.message("Please note down transer no.. ").data(transerMap).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Check your selections..").build());
	}

	@Override
	public ResponseEntity<?> fetchAdvancePaidDetail(String partyCode, String buildingCode, String matGroup,
			String advanceAdjust) {
		Double DecLocAdvancePaid, DecLocAdvanceReturn, DecLocAdvanceAdjust, DecLocOtherAdvance,
				DecLocOtherAdvanceReturn, DecLocOtherAdvanceAdjust, DecLocOtherAdvos = 0.0, DecLocTotAdvadjusted,
				DecLocAdvanceOs;

		DecLocAdvancePaid = this.authHRepository
				.findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
						partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "A");
		log.info("DecLocAdvancePaid", DecLocAdvancePaid);

		DecLocAdvanceReturn = this.authHRepository
				.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
						partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "C");
		log.info("DecLocAdvanceReturn", DecLocAdvanceReturn);

		DecLocAdvanceAdjust = this.authHRepository
				.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
						partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "N");
		log.info("DecLocAdvanceAdjust", DecLocAdvanceAdjust);

		// AS per discussing with Vikram sir this logic is kept same as VB as told by
		// him.
		if (Objects.nonNull(advanceAdjust)) {
			DecLocOtherAdvance = this.authHRepository
					.findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "A");
			log.info("DecLocOtherAdvance", DecLocOtherAdvance);

			DecLocOtherAdvanceReturn = this.authHRepository
					.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "C");
			log.info("DecLocOtherAdvanceReturn", DecLocOtherAdvanceReturn);

			DecLocOtherAdvanceAdjust = this.authHRepository
					.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							partyCode.trim(), buildingCode.trim(), matGroup.trim(), "6", "N");
			log.info("DecLocOtherAdvanceAdjust", DecLocOtherAdvanceAdjust);

			// DecLocOtherAdvos = DecLocOtherAdvance - (DecLocOtherAdvanceReturn +
			// DecLocOtherAdvanceAdjust); //TO ASK ABOUT MINUS

			DecLocOtherAdvos = DecLocOtherAdvance - (DecLocOtherAdvanceReturn + DecLocOtherAdvanceReturn);
		}
		DecLocTotAdvadjusted = DecLocAdvanceReturn + DecLocAdvanceAdjust;
		DecLocAdvanceOs = DecLocOtherAdvos + DecLocAdvancePaid - DecLocTotAdvadjusted;

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(DecLocAdvanceOs).build());
	}

	private List<ActrandBean> insActrand(Integer bunumCounter, MaterialPaymentRequestBean materialPaymentRequestBean,
			AccountingBean accoutingDataForTran, AccountingBean accoutingDataForContraTran,
			AccountingBean accoutingDataCashFlow, AccountingBean accoutingDataCashFlowForContra, Double tranamt,
			String bldgcode, String property, String ser, String trantype, String doctype, String docnum,
			String docdate, String narrative) {
		List<ActrandBean> actrandBeanList = new ArrayList<>();
		String narrationMsg;
		String exnNarrationMsg = "";
		if (Objects.nonNull(materialPaymentRequestBean.getMatname()) && (materialPaymentRequestBean.getMatname().trim()
				.concat(CommonConstraints.INSTANCE.SPACE_STRING).concat(narrative)).length() < 40) {
			narrationMsg = materialPaymentRequestBean.getMatname().trim()
					.concat(CommonConstraints.INSTANCE.SPACE_STRING).concat(narrative);
		} else {
			narrationMsg = materialPaymentRequestBean.getMatname();
			exnNarrationMsg = narrative;
		}
		for (int i = 1; i <= 2; i++) {
			if (i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder().transer(ser).bunum(bunumCounter).trantype(trantype)
						.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).ledgcode("PL")
						.proprietor(materialPaymentRequestBean.getProp())
						.coy(materialPaymentRequestBean.getCoy().trim())
						.mintype(StringUtils.isNotEmpty(accoutingDataForTran.getMinType())
								? accoutingDataForTran.getMinType()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.partytype(accoutingDataForTran.getPartyType()).partycode(accoutingDataForTran.getPartyCode())
						.acmajor(accoutingDataForTran.getAcMajor())
						.acminor(StringUtils.isNotEmpty(accoutingDataForTran.getAcminor())
								? accoutingDataForTran.getAcminor()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.mincode(accoutingDataForTran.getMinCode()).vounum(materialPaymentRequestBean.getAuthnum())
						.voudate(materialPaymentRequestBean.getAuthdate()).tranamt(tranamt).narrative(narrationMsg)
						.project(accoutingDataForTran.getProject()).period(CommonConstraints.INSTANCE.BLANK_STRING)
						.property(property).bldgcode(bldgcode).domain("M")
						.matgroup(materialPaymentRequestBean.getMatgroup()).cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode()).doctype(doctype).docnum(docnum).docdate(docdate)
						.docpartype(PartyType.S.toString()).docparcode(materialPaymentRequestBean.getPartycode())
						// X columns (Contra entry)
						.xproject(accoutingDataForContraTran.getProject())
						.xacmajor(accoutingDataForContraTran.getAcMajor())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(accoutingDataForContraTran.getPartyCode())
						.xpartytype(accoutingDataForContraTran.getPartyType()).xreftranser(ser)
						.xrefbunum(bunumCounter + 1)
						.xreftrandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid()).today(LocalDateTime.now()).build());
			} else {
				actrandBeanList.add(ActrandBean.builder().transer(ser).bunum(bunumCounter + 1).trantype(trantype)
						.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).ledgcode("PL")
						.proprietor(materialPaymentRequestBean.getProp())
						.coy(materialPaymentRequestBean.getCoy().trim())
						.mintype(StringUtils.isNotEmpty(accoutingDataForContraTran.getMinType())
								? accoutingDataForContraTran.getMinType()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(StringUtils.isNotEmpty(accoutingDataForContraTran.getAcminor())
								? accoutingDataForContraTran.getAcminor()
								: CommonConstraints.INSTANCE.SPACE_STRING)
						.acmajor(accoutingDataForContraTran.getAcMajor())
						.vounum(materialPaymentRequestBean.getAuthnum())
						.voudate(materialPaymentRequestBean.getAuthdate()).tranamt(tranamt * -1)
						.period(CommonConstraints.INSTANCE.BLANK_STRING).narrative(narrationMsg)
						.project(accoutingDataForContraTran.getProject()).bldgcode(bldgcode).property(property)
						.domain("M").matgroup(materialPaymentRequestBean.getMatgroup())
						.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
						.cfcode(accoutingDataCashFlowForContra.getCfCode()).doctype(doctype).docnum(docnum)
						.docdate(docdate).docpartype(PartyType.S.toString())
						.docparcode(materialPaymentRequestBean.getPartycode())
						// X columns (Contra entry)
						.xreftranser(ser).xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(accoutingDataForTran.getAcMajor()).xmintype(accoutingDataForTran.getMinType())
						.xpartycode(accoutingDataForTran.getPartyCode()).xproject(accoutingDataForTran.getProject())
						.xreftrandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.xpartytype(accoutingDataForTran.getPartyType()).xrefbunum(bunumCounter)
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid()).today(LocalDateTime.now()).build());
			}
			if (StringUtils.isNotBlank(exnNarrationMsg))
				this.exnarrRepository.save(ExnarrMapper.addExnarrMapper.apply(ExnarrBean.builder().transer(ser)
						.bunum(bunumCounter.doubleValue()).coy(materialPaymentRequestBean.getCoy().trim())
						.linenum(Double.valueOf(i)).narrtype("S").today(LocalDateTime.now())
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid()).exnarr(exnNarrationMsg).build()));

		}
		return actrandBeanList;
	}

	@Override
	public ResponseEntity<?> fetchExcludeBuildingDetail() {
		List<String> enityList = this.entityRepository.findEntRemarkByEntityCk_EntClass("OSREP");
		log.info("EnityList {} ::", enityList);
		if (CollectionUtils.isNotEmpty(enityList)) {
			StringBuilder splittedString = new StringBuilder();

			enityList.stream().map(remark -> {
				return splittedString.append(remark.replaceAll("'", CommonConstraints.INSTANCE.BLANK_STRING).trim());
			}).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(splittedString).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> fetchMaterialPaymentView(MaterialPaymentViewRequestBean materialPaymentViewRequestBean) {
		if (Objects.nonNull(materialPaymentViewRequestBean)) {
			if (materialPaymentViewRequestBean.getIsUnprintedAuthChecked()) {
				List<Auth_H> authHEntityList = this.authHRepository.findByAuthPrintedonAndAuthUserid(null,
						GenericAuditContextHolder.getContext().getUserid());// Note: It is purposely passed as null to
																			// find where printedon is null;
				if (CollectionUtils.isNotEmpty(authHEntityList)) {

				} else
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
							.message("There are no Unprinted authorisation for printing...").build());
			} else {
				return null;
			}
		} else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("Please check your selections.").build());
		return null;
	}

	@Override
	public ResponseEntity<?> fetchRemarksFromSuppbillno(RemarkDetailRequestBean remarkDetailRequestBean) {
		List<Pbillh> pbillhEntityList = this.pbillhRepository
				.findByPblhPartycodeAndPblhBldgcodeAndPblhSuppbillnoInOrderByPbillhCK_PblhSer(
						remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
						remarkDetailRequestBean.getSuppbillno());
		log.info("PbillhEntityList {} ::", pbillhEntityList);
		if (CollectionUtils.isNotEmpty(pbillhEntityList)) {
			return ResponseEntity.ok(ServiceResponseBean
					.builder().data(String.join(",", pbillhEntityList.stream()
							.map(ser -> (ser.getPbillhCK().getPblhSer())).collect(Collectors.toList())))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchBillDetail(RemarkDetailRequestBean remarkDetailRequestBean) {
		Double runningSer = 0.0;
		Double DecLocAdvGranted = 0.0;
		Double DecLocAdvAdjustpastC = 0.0;
		Double DecLocAdvAdjustpastN = 0.0;
		String warningMessage = "";

		runningSer = this.authHRepository.findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthNoNotLike(
				remarkDetailRequestBean.getBldgcode(), remarkDetailRequestBean.getPartycode(),
				remarkDetailRequestBean.getMatgroup());
		log.info("running Ser {} ::", runningSer);// -----> TO RETUTRN
		if (!remarkDetailRequestBean.getAuthType().trim().equals("L")
				&& !remarkDetailRequestBean.getAuthType().trim().equals("R")) {

			List<Pbillh> pbillhEntityList = this.pbillhRepository
					.findByPblhPartycodeAndPblhBldgcodeAndPblhSuppbillnoInAndPblhAuthdateAndpblhAuthNumOrderByPbillhCK_PblhSer(
							remarkDetailRequestBean.getPartycode(), remarkDetailRequestBean.getBldgcode(),
							remarkDetailRequestBean.getSuppbillno());
			log.info("PbillhEntityList {} ::", pbillhEntityList);

			Double DecLocTotNetAmt = pbillhEntityList.stream().mapToDouble(pbillhEntity -> pbillhEntity.getPblhAmount()
					- pbillhEntity.getPblhDebitamt() - pbillhEntity.getPblhTdsamount()).sum();
			log.info("DecLocTotNetAmt {} ::", DecLocTotNetAmt);
		}
		List<Auth_H> authHEntityList = this.authHRepository.findUnpassedDetail(
				remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
				remarkDetailRequestBean.getMatgroup().trim(), "6");
		log.info("authHEntityList {} ::", authHEntityList);

		if (CollectionUtils.isNotEmpty(authHEntityList)) {
			Double DecLoclastUnpaidAmt = authHEntityList.stream()
					.mapToDouble(authHEntity -> authHEntity.getAuthPayamount()).sum();

			warningMessage = "There are " + authHEntityList.size() + " unpassed authorisations of pay amount "
					+ DecLoclastUnpaidAmt + " ."; // to return
			log.info("warningMessage {} ::", warningMessage);

		}
		if (remarkDetailRequestBean.getAuthType().trim().equals("A")) {
			// Get the total advance amount granted to this supplier - partycode for this
			// building - bldgcode for this material - Matgroup Advance Granted Amount ...
			DecLocAdvGranted = this.authHRepository
					.findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
							remarkDetailRequestBean.getMatgroup().trim(), "6", "A");
			log.info("DecLocAdvGranted", DecLocAdvGranted);

			DecLocAdvAdjustpastC = this.authHRepository
					.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
							remarkDetailRequestBean.getMatgroup().trim(), "6", "C");
			log.info("DecLocAdvAdjustpastC", DecLocAdvAdjustpastC);

			DecLocAdvAdjustpastN = this.authHRepository
					.findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(
							remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
							remarkDetailRequestBean.getMatgroup().trim(), "6", "N");
			log.info("DecLocAdvAdjustpastN", DecLocAdvAdjustpastN);

			// ' Total advance adjusted amount ...
			Double DecLocAdvAdjustpast = DecLocAdvAdjustpastN + DecLocAdvAdjustpastC;
			if ((DecLocAdvGranted - DecLocAdvAdjustpast) > 0.0) {
				return ResponseEntity.ok(ServiceResponseBean
						.builder().data("Cannot Allot further advance as Adv.Amount = "
								+ (DecLocAdvGranted - DecLocAdvAdjustpast) + " Not yet Adjusted")
						.status(Boolean.FALSE).build());
			}
		}

		Query query;

		List<MaterialBillDetailResponseBean> materialBillDetailResponseBeanList = new ArrayList<>();
		if (remarkDetailRequestBean.getAuthType().trim().equals("N")) {
			query = this.entityManager.createNativeQuery(
					"SELECT PBLH_SUPPBILLNO,PBLH_SUPPBILLDT,PBLH_AMOUNT,PBLH_DEBITAMT,PBLH_TDSAMOUNT,PBLH_RETAINOS,PBLH_ADVANCEADJ,pblh_paidamt,pbld_quantity,pblh_retention,pblh_billtype,pblh_ser,pbld_matcode,pbld_matgroup,pblh_omspurcyn,pbld_uom FROM PBILLD,PBILLH   WHERE PBLD_SER = PBLH_SER and ((trim(PBLH_PARTYCODE) = :partyCode) AND PBLH_SUPPBILLNO in ( "
							+ String.join(",",
									remarkDetailRequestBean.getSuppbillno().stream().map(name -> ("'" + name + "'"))
											.collect(Collectors.toList()))
							+ ") AND (PBLH_AUTHNUM is NULL) AND (PBLH_AUTHDATE is NULL) AND (PBLD_LINENO = 1)) ORDER BY PBLH_SUPPBILLNO ASC,PBLH_SUPPBILLDT ASC,PBLH_AMOUNT DESC",
					Tuple.class);
			query.setParameter("partyCode", remarkDetailRequestBean.getPartycode());

			List<Tuple> resultSetList = query.getResultList();
			if (CollectionUtils.isNotEmpty(resultSetList)) {
				resultSetList.stream().map(t -> {
					BigDecimal payamount = BigDecimal.ZERO;
					payamount = t.get(2, BigDecimal.class)
							.subtract(t.get(6, BigDecimal.class).add(t.get(9, BigDecimal.class))
									.add(t.get(3, BigDecimal.class)).add(t.get(4, BigDecimal.class))); // --------->
																										// payamt = bill
																										// - (advadj +
																										// retention +
																										// debitamt +
																										// tdsamt)
					log.info("payamount", payamount);

					materialBillDetailResponseBeanList
							.add(MaterialBillDetailResponseBean.builder().suppbillno(t.get(0, String.class))
									.suppbilldt(Objects.nonNull(t.get(1, Timestamp.class))
											? t.get(1, Timestamp.class).toLocalDateTime().toLocalDate().format(
													CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
											: null)
									.authamount(t.get(2, BigDecimal.class)).debitamt(t.get(3, BigDecimal.class))
									.authtdsamt(t.get(4, BigDecimal.class)).retainos(t.get(5, BigDecimal.class))
									.advadj(t.get(6, BigDecimal.class)).payamount(payamount)
									.authqty(t.get(8, BigDecimal.class)).retainamt(t.get(9, BigDecimal.class))
									.billtype(t.get(10, Character.class)).ser(t.get(11, String.class))
									.matcode(t.get(12, String.class)).matgroup(t.get(13, String.class))
									.omspurcyn(t.get(14, Character.class)).uom(t.get(15, String.class)).build());
					return t;
				}).collect(Collectors.toList());

				LOGGER.info("materialBillDetailResponseBeanList :: {} " + materialBillDetailResponseBeanList);

				return ResponseEntity.ok(ServiceResponseBean.builder().data(materialBillDetailResponseBeanList)
						.message(warningMessage).status(Boolean.TRUE).build());

			}
		}

		if (remarkDetailRequestBean.getAuthType().trim().equals("L")
				|| remarkDetailRequestBean.getAuthType().trim().equals("R")) {
			query = this.entityManager.createNativeQuery(
					"SELECT DISTINCT PBLH_SUPPBILLNO,PBLH_SUPPBILLDT,PBLH_AMOUNT,PBLH_DEBITAMT,PBLH_TDSAMOUNT,PBLH_RETAINOS,PBLH_ADVANCEADJ,pblh_paidamt,pbld_quantity,pblh_retention,pblh_billtype,pblh_ser,pbld_matcode,pbld_matgroup,pblh_omspurcyn,pbld_uom FROM PBILLD,PBILLH WHERE (PBLD_PARTYCODE = PBLH_PARTYCODE) and (PBLD_SUPPBILLNO = PBLH_SUPPBILLNO)and ((trim(PBLH_PARTYCODE) = :partyCode)AND (PBLH_SUPPBILLNO in ("
							+ String.join(",",
									remarkDetailRequestBean.getSuppbillno().stream().map(name -> ("'" + name + "'"))
											.collect(Collectors.toList()))
							+ ")) AND (PBLH_RETENTION > 0) AND (PBLD_LINENO = 1))",
					Tuple.class);
			query.setParameter("partyCode", remarkDetailRequestBean.getPartycode());

			List<Tuple> resultSetList = query.getResultList();
			if (CollectionUtils.isNotEmpty(resultSetList)) {
				resultSetList.stream().map(t -> {
					// BigDecimal payamount = BigDecimal.ZERO;
					// payamount = t.get(2, BigDecimal.class).subtract(t.get(6,
					// BigDecimal.class).add(t.get(9, BigDecimal.class)).add(t.get(3,
					// BigDecimal.class)).add(t.get(4, BigDecimal.class))); // ---------> payamt =
					// bill - (advadj + retention + debitamt + tdsamt)
					// log.info("payamount",payamount);

					materialBillDetailResponseBeanList.add(MaterialBillDetailResponseBean.builder()
							.suppbillno(t.get(0, String.class)).matcode(t.get(12, String.class))
							.matgroup(t.get(13, String.class)).omspurcyn(t.get(14, Character.class))
							.uom(t.get(15, String.class))
							.suppbilldt(Objects.nonNull(t.get(1, Timestamp.class))
									? t.get(1, Timestamp.class).toLocalDateTime().toLocalDate().format(
											CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: null)
							.authamount(t.get(2, BigDecimal.class)).debitamt(t.get(3, BigDecimal.class))
							.authtdsamt(t.get(4, BigDecimal.class)).retainos(t.get(5, BigDecimal.class))
							.advadj(t.get(6, BigDecimal.class)).relretamt(BigDecimal.ZERO)
							.payamount(remarkDetailRequestBean.getAuthType().trim().equals("R") ? BigDecimal.ZERO
									: t.get(7, BigDecimal.class))
							.retainamt(t.get(9, BigDecimal.class)).ser(t.get(11, String.class))
							.billtype(t.get(10, Character.class)).build());
					return t;
				}).collect(Collectors.toList());

				LOGGER.info("materialBillDetailResponseBeanList :: {} " + materialBillDetailResponseBeanList);

				return ResponseEntity.ok(ServiceResponseBean.builder().data(materialBillDetailResponseBeanList)
						.message(warningMessage).extraData(Objects.nonNull(runningSer) ? runningSer.toString() : null)
						.status(Boolean.TRUE).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().data(runningSer).status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> fetchPaidBillDetail(RemarkDetailRequestBean remarkDetailRequestBean) {
		Integer matgroupCount = this.pbillhRepository.findCountPartycodeBldgCodeAndMatgroup(
				remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
				remarkDetailRequestBean.getMatgroup().trim());
		if (matgroupCount == 1) {
			List<PaidBillResponseBean> paidBillResponseBean = this.pbillhRepository.findbyPartycodeBldgCodeAndMatgroup(
					remarkDetailRequestBean.getPartycode().trim(), remarkDetailRequestBean.getBldgcode().trim(),
					remarkDetailRequestBean.getMatgroup().trim());
			return ResponseEntity.ok(CollectionUtils.isNotEmpty(paidBillResponseBean)
					? ServiceResponseBean.builder().data(paidBillResponseBean).status(Boolean.TRUE).build()
					: ServiceResponseBean.builder()
							.message("No paid bills for this Party + Building + Material  combination.")
							.status(Boolean.FALSE).build());
		} else
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.message("No paid bills for this Party + Building + Material  combination.").status(Boolean.FALSE)
					.build());
	}

	@Override
	public ResponseEntity<?> saveMaterialPayment(MaterialDetailRequestBean materialDetailRequestBean) {
		String authNumber = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "AUSER",
				GenericAuditContextHolder.getContext().getSite().trim());
		materialDetailRequestBean.getAuthHRequestBean().setAuthnum(authNumber);

		Integer materialCount = this.materialRepository.findByMaterialGrpCount(
				materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "1", "****", "********",
				LocalDate.parse(materialDetailRequestBean.getAuthHRequestBean().getAuthdate(),
						CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
				CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
		if (materialCount == 0)
			return ResponseEntity
					.ok(ServiceResponseBean.builder().message("Invalid Material group.").status(Boolean.FALSE).build());

		if (isMatLevel2Allowed(materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(), "1") == 1) {
			Material materialEntity = this.materialRepository
					.findByMaterialCK_MatMatgroupAndMaterialCK_MatMatcodeAndMaterialCK_MatItemcodeAndMatLevel(
							materialDetailRequestBean.getAuthHRequestBean().getMatgroup(),
							materialDetailRequestBean.getAuthHRequestBean().getMatcode(), "********", "2");
			if (Objects.isNull(materialCount))
				return ResponseEntity.ok(
						ServiceResponseBean.builder().message("Invalid Material code.").status(Boolean.FALSE).build());
		}

		// if(StringUtils.isNotEmpty(materialDetailRequestBean.getAuthHRequestBean().getDebsocyn()))
		// if(!materialDetailRequestBean.getAuthHRequestBean().getDebsocyn().trim().equals("S")
		// &&
		// !materialDetailRequestBean.getAuthHRequestBean().getDebsocyn().trim().equals("A")
		// &&
		// !materialDetailRequestBean.getAuthHRequestBean().getDebsocyn().trim().equals("M")
		// &&
		// !materialDetailRequestBean.getAuthHRequestBean().getDebsocyn().trim().equals("G"))
		// return ResponseEntity.ok(ServiceResponseBean.builder().message("Invalid debit
		// type.").status(Boolean.FALSE).build());

		if (checkIsDebitTypeValid(materialDetailRequestBean.getAuthHRequestBean().getBldgcode(),
				materialDetailRequestBean.getAuthHRequestBean().getDebsocyn()))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("This Debit To type is blocked ...Can't proceed.").build());

		if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("N")
				|| materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("A")) {
			Building buildingEntity = this.buildingRepository
					.findByBuildingCK_BldgCode(materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim());
			if (Objects.nonNull(buildingEntity)) {
				if (Objects.nonNull(buildingEntity.getBldgBldgtype()))
					if (buildingEntity.getBldgBldgtype().trim().equals("H")
							|| buildingEntity.getBldgBldgtype().trim().equals("M")) {
						// ' Don't validate if not a single logic note is entered for the project
						Query queryForTotLogicNotes = this.entityManager.createNativeQuery(
								"SELECT count(*) FROM matcertlnhdr WHERE trim(mclh_projcode) = :projcode");

						queryForTotLogicNotes.setParameter("projcode",
								materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim());
						BigDecimal countForTotLogicNotes = (BigDecimal) queryForTotLogicNotes.getSingleResult();
						LOGGER.info("countForTotLogicNotes : ",countForTotLogicNotes);

						if (countForTotLogicNotes.intValue() > 0) {
							// ' Don't validate if logic note is not entered for the project and the matgroup combination
							Query queryCountForMatcertlnhdr = this.entityManager.createNativeQuery(
									"SELECT count(*) FROM v_lnvendormatcertamtdtls WHERE trim(mclh_projcode) = :projcode AND mclw_matcerttype = 'Material' AND trim(mclw_matcertcode) = :matgroup");
							queryCountForMatcertlnhdr.setParameter("projcode",materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim());
							queryCountForMatcertlnhdr.setParameter("matgroup",materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim());
							
							LOGGER.info("projcode : {} matgroup: {} partycode: {}",materialDetailRequestBean.getAuthHRequestBean().getBldgcode(), materialDetailRequestBean.getAuthHRequestBean().getMatgroup(), materialDetailRequestBean.getAuthHRequestBean().getPartycode());
							
							BigDecimal countForMatcertlnhdr = (BigDecimal) queryCountForMatcertlnhdr.getSingleResult();
							LOGGER.info("countForMatcertlnhdr : ",countForMatcertlnhdr);

							if (countForMatcertlnhdr.intValue() > 0) {
								Query queryForLogicNote = this.entityManager.createNativeQuery("SELECT nvl(finalbillcloseyn,'N') FROM v_lnvendormatcertamtdtls WHERE trim(mclh_projcode) = :projcode AND mclw_matcerttype = 'Material' AND trim(mclw_matcertcode) = :matgroup AND mcvh_partytype = 'S' AND trim(mcvh_partycode) = :partycode");
								queryForLogicNote.setParameter("projcode",materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim());
								queryForLogicNote.setParameter("matgroup",materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim());
								queryForLogicNote.setParameter("partycode",materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim());
								LOGGER.info("queryForLogicNote : {}",queryForLogicNote.toString());
								String outputForLogicNote = "";
								 List resultList = queryForLogicNote.getResultList();
								 for (Object logicNotefinalBillCloseyn : resultList) {
									 LOGGER.info("resultList1 : {}",logicNotefinalBillCloseyn);
									 outputForLogicNote = (String) logicNotefinalBillCloseyn;
								}
								
								LOGGER.info("resultList1 : {}",resultList);
								LOGGER.info("outputForLogicNote : {}",outputForLogicNote);
								
								
								String finalBillCloseYN = CommonConstraints.INSTANCE.BLANK_STRING;
								if (StringUtils.isNotBlank(outputForLogicNote)) {
									finalBillCloseYN = outputForLogicNote;
								} else {
									String dbentity = this.entityRepository
											.fetchByChar1EntityCk_EntClassAndEntityCk_EntIdAndChar1ToChar4("ENGG",
													"FINBL",
													materialDetailRequestBean.getAuthHRequestBean().getPartycode()
															.trim(),
													PartyType.S.toString(), materialDetailRequestBean
															.getAuthHRequestBean().getBldgcode().trim(),
													"ALLNEWPART");
									//FALSE
									if (StringUtils.isBlank(dbentity) || !dbentity.equals(materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim()))
										
										return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
												.message("Logic note for this project+vendor is not entered. Please check.....")
												.build());
								}	
								if (finalBillCloseYN.equals("Y")) {
									// ' Don't allow N or A type authorisation if final bill is made
									String char2 = this.entityRepository
											.fetchByEntityCk_EntClassAndEntityCk_EntIdAndEntityCk_EntChar1("ENGG",
													"FINBL", "ALLCERTYPE");
									char2 = StringUtils.isNotBlank(char2)
											? char2.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]
											: CommonConstraints.INSTANCE.BLANK_STRING;
									char2 = char2.trim();
									if (char2.equals("N"))
										// TODO FRONTEND RESET CONTROLS
										return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
												.message(
														"Normal or Advance type authorisation is not allowed as Final billing is already done.....")
												.build());
								}
							}
						}
					}
			}
			Matcertestimateactual matcertEntity = this.matcertestimateactualRepository
					.findByMatcertestimateactualCK_ClseProjcodeAndMatcertestimateactualCK_ClseMatcerttypeAndMatcertestimateactualCK_ClseMatcertcodeAndMatcertestimateactualCK_ClsePartycodeAndMatcertestimateactualCK_ClsePartytype(
							materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim(), "Material",
							materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim(),
							materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim(),
							PartyType.S.toString());
			if (Objects.nonNull(matcertEntity)) {
				Double decloctotamtpaidtosupp = CommonResultsetGenerator.getNumericSingleQueryValue(
						"SELECT NVL(SUM(case AUTH_AUTHTYPE when 'A' then 0 when 'L' then 0  else NVL(AUTH_AUTHAMOUNT,0) end) + SUM(case AUTH_AUTHTYPE when 'A' then NVL(AUTH_AUTHAMOUNT,0) else 0 end) - SUM(case AUTH_AUTHTYPE when 'A' then 0 else NVL(auth_advadjust,0) end),0) from auth_h where auth_authstatus not in ('6','8') and"
								+ " trim(auth_partycode)= '"
								+ materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim()
								+ "' AND trim(auth_bldgcode)='"
								+ materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim()
								+ "' AND trim(auth_matgroup)= '"
								+ materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim() + "'");

				Double decloccontractvalue = CommonResultsetGenerator.getNumericSingleQueryValue(
						"select clse_contractval  + round(0.02 * clse_contractval,0) from matcertestimateactual where clse_matcerttype = 'Material' and clse_partytype = 'S' and clse_partycode='"
								+ materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim()
								+ "' AND clse_projcode= '"
								+ materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim()
								+ "' AND	clse_matcertcode = '"
								+ materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim() + "'");

				Double declocestimatevalue = CommonResultsetGenerator.getNumericSingleQueryValue(
						"select clse_estimateamt from matcertestimateactual where clse_matcerttype = 'Material' and clse_partytype = 'S' and "
								+ "trim(clse_partycode)='"
								+ materialDetailRequestBean.getAuthHRequestBean().getPartycode().trim()
								+ "' AND clse_projcode= '"
								+ materialDetailRequestBean.getAuthHRequestBean().getBldgcode().trim() + "' AND	"
								+ "clse_matcertcode = '"
								+ materialDetailRequestBean.getAuthHRequestBean().getMatgroup().trim() + "'");

				if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("N"))
					decloctotamtpaidtosupp = decloctotamtpaidtosupp
							+ materialDetailRequestBean.getAuthHRequestBean().getAuthamount() /* TotAmount?? */
							- materialDetailRequestBean.getAuthHRequestBean().getTotalAdvadjust()
							- materialDetailRequestBean.getAuthHRequestBean().getTotalRetention();
				else if (materialDetailRequestBean.getAuthHRequestBean().getAuthtype().trim().equals("A"))
					decloctotamtpaidtosupp = decloctotamtpaidtosupp
							+ materialDetailRequestBean.getAuthHRequestBean().getAdvgranted();
				Boolean bollocallowpayment = Boolean.FALSE;
				if (decloccontractvalue == 0 && declocestimatevalue > 0)
					bollocallowpayment = Boolean.TRUE;
				if (decloctotamtpaidtosupp > decloccontractvalue && (!bollocallowpayment))
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(
							"The total payment to supplier is exceeding the contract value...can not process the payment.....")
							.build());
			}

		}
		try {
			this.authmatgroupnarrdtlRepository.deleteAuthmatgroupnarrdtlByAuthNum(authNumber);
		} catch (Exception e) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("Error while deleting old records from authmatgroupnarrdtl").build());
		}
		// Calling KP Method to actually insert and save data after validation
		return addMaterial(materialDetailRequestBean);
	}

	private Integer isMatLevel2Allowed(String matGroup, String matLevel) {
		return this.materialRepository.materialLevel2Count(matGroup, matLevel, "****", "********", "LEVL2");
	}

	public Boolean checkIsDebitTypeValid(String buildingCode, String debitType) {
		Query debitTypeCheckQuery = this.entityManager.createNativeQuery("SELECT INSTR(bmap_blockcerttype,'"
				.concat(Objects.nonNull(debitType) && StringUtils.isNotBlank(debitType) ? debitType.trim()
						: CommonConstraints.INSTANCE.SPACE_STRING)
				.concat("') from bldgmap where bmap_ebldgcode = '").concat(buildingCode.trim()).concat("'"));
		if (!debitTypeCheckQuery.getResultList().isEmpty() && (debitTypeCheckQuery.getResultList().get(0) != null
				&& Integer.parseInt(debitTypeCheckQuery.getResultList().get(0).toString()) > 0)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public ResponseEntity<?> fetchDebitTypeByBuilding(String buildingCode) {
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(buildingCode.trim());
		return ResponseEntity.ok(ServiceResponseBean.builder()
				.status(Objects.nonNull(buildingEntity.getBldgDebsocyn()) ? Boolean.TRUE : Boolean.FALSE)
				.data(Objects.nonNull(buildingEntity.getBldgDebsocyn()) ? buildingEntity.getBldgDebsocyn()
						: "No Data Found")
				.build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(
			MaterialPaymentPrintRequestBean materialPaymentPrintRequestBean) {
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		String whereCondition = "";
		String andString = "AND";
		List<Auth_H> authHList = this.authHRepository.findByAuthPrintedonAndAuthUserid(null,
				GenericAuditContextHolder.getContext().getUserid().trim());
		if (materialPaymentPrintRequestBean.getIsUnprintedAuths()) {
			if (CollectionUtils.isEmpty(authHList))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("There are no Unprinted authorisation for printing...").build());
			else {
				String commaSepratedAuthNums = String.join(",", authHList.stream()
						.map(name -> ("'" + name.getAuthhCK().getAuthAuthnum() + "'")).collect(Collectors.toList()));
				whereCondition = " a.auth_authnum in (".concat(commaSepratedAuthNums).concat(")");
			}
		} else {
			if (StringUtils.isNotBlank(materialPaymentPrintRequestBean.getAuthDateFrom())
					&& StringUtils.isNotBlank(materialPaymentPrintRequestBean.getAuthDateTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				// authdate is with time
				whereCondition += andString + " a.auth_authdate between '"
						+ materialPaymentPrintRequestBean.getAuthDateFrom() + "' and '"
						+ materialPaymentPrintRequestBean.getAuthDateTo() + "'";
			}

			if (StringUtils.isNotBlank(materialPaymentPrintRequestBean.getAuthNumberFrom())
					&& StringUtils.isNotBlank(materialPaymentPrintRequestBean.getAuthNumberTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				else
					andString = "AND";
				whereCondition += andString + " a.auth_authnum BETWEEN '"
						+ materialPaymentPrintRequestBean.getAuthNumberFrom() + "' and '"
						+ materialPaymentPrintRequestBean.getAuthNumberTo() + "'";
			}
		}

		Query authQuery = this.entityManager.createNativeQuery("SELECT * FROM AUTH_H a where " + whereCondition,
				Auth_H.class);
		List<Auth_H> authHConditionBasedList = authQuery.getResultList();
		LOGGER.info("AUTH QUERY :: {}", authHConditionBasedList);

		if (CollectionUtils.isNotEmpty(authHConditionBasedList)) {
			try {
				for (Auth_H authH : authHConditionBasedList) {
					String authStatus = "";
					Integer noOfPrint = BigInteger.ZERO.intValue();
					String payCover = "";
					Double payAmt = authH.getAuthPayamount();
					Double authAmt = authH.getAuthAuthamount();// -----------> for auth type c it is authpayamount but
																// negative
					String amountInWords = "";
					String address2 = "";
					String certStat = "";
					Address addressEntity = null;
					Party partyName = null;
					Double sumAdvAdj = BigInteger.ZERO.doubleValue();
					List<TempMatAuthprint> tempMatAuthprintList = new ArrayList<>();

					if (((Objects.isNull(authH.getAuthPrinted()) || authH.getAuthPrinted() <= 0))
							&& Integer.valueOf(authH.getAuthAuthstatus()) < 5)
						authStatus = PrintStatusEnum.ORIGINAL.getValue();
					else
						authStatus = PrintStatusEnum.REPRINT.getValue();
					if (authH.getAuthAuthtype().trim().equals("R"))
						authStatus = PrintStatusEnum.ORIGINAL.getValue();

					if (!authH.getAuthAuthtype().trim().equals("R") && Integer.valueOf(authH.getAuthAuthstatus()) >= 5)
						noOfPrint = authH.getAuthPrinted().intValue() + 1;

					List<Auth_D> authDList = this.authDRepository
							.findByAuthdCK_AutdAuthnum(authH.getAuthhCK().getAuthAuthnum());
					LOGGER.info("authDList size :: {}", authDList.size());
					LOGGER.info("authDList  :: {}", authDList);
					if (CollectionUtils.isNotEmpty(authDList))
						sumAdvAdj = authDList.stream().filter(f -> f.getAutdAdvadj() != null)
								.mapToDouble(Auth_D::getAutdAdvadj).sum();

					if (authH.getAuthAuthtype().trim().equals("R") || authH.getAuthAuthtype().trim().equals("L"))
						sumAdvAdj += authH.getAuthAuthamount();

					if (authH.getAuthAuthtype().trim().equals("C")) {
						payCover = "RECOVER";
						payAmt = authH.getAuthPayamount() * -1;
						authAmt = authH.getAuthPayamount() * -1;
					} else
						payCover = "PAY";

					// amountInWords = "RUPEES
					// ".concat(CommonUtils.convert(authH.getAuthPayamount().intValue()).concat("
					// ONLY"));
					amountInWords = CurrencyConverterUtils.convertToIndianCurrency(
							authH.getAuthPayamount() < 0 ? String.valueOf(authH.getAuthPayamount() * -1)
									: String.valueOf(authH.getAuthPayamount()));

					if (authH.getAuthPayamount().intValue() == 0) {
						amountInWords = "Rupees  Zero And Paise Zero Only.";
					}
					// To convert into capital case - YP 10/04/2023
					// amountInWords = CommonUtils.INSTANCE.convertToCapitalizeCase(amountInWords);

					if (Objects.nonNull(authH.getAuthPartycode())) {
						partyName = this.partyRepository
								.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
										authH.getAuthPartycode().trim(),
										CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
						LOGGER.info("Party Name :: {}", partyName);

						addressEntity = this.addressRepository
								.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(
										authH.getAuthPartycode().trim(), AdSegment.PARTY.toString(),
										AdType.LOC.toString());
						LOGGER.info("Address Entity :: {}", addressEntity);

						if (Objects.nonNull(addressEntity)) {
							String cityName = Objects.nonNull(addressEntity.getAdrCity())
									? this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("CITY",
											addressEntity.getAdrCity().trim())
									: null;

							String townName = Objects.nonNull(addressEntity.getAdrTownship())
									? this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("TOWN",
											addressEntity.getAdrTownship().trim())
									: null;
							String adline4 = Objects.nonNull(addressEntity.getAdrAdline4())
									? addressEntity.getAdrAdline4().trim()
									: " ";
							String adline5 = Objects.nonNull(addressEntity.getAdrAdline5())
									? addressEntity.getAdrAdline5().trim()
									: " ";
							if (StringUtils.isNotBlank(townName) || StringUtils.isNotBlank(cityName)) {
								address2 += (Objects.nonNull(adline4) ? adline4.trim() : "") + " "
										+ (Objects.nonNull(adline5) ? adline5.trim() : "") + " "
										+ (Objects.nonNull(townName) ? townName.trim() : "")
										+ (Objects.nonNull(cityName) ? cityName.trim() : "")
										+ (Objects.nonNull(addressEntity.getAdrPincode())
												? "-" + addressEntity.getAdrPincode()
												: "");
							}

						}
					}

					Building buildingName = Objects.nonNull(authH.getAuthBldgcode())
							? this.buildingRepository.findByBuildingCK_BldgCode(authH.getAuthBldgcode().trim())
							: null;

					Material materialName = Objects.nonNull(authH.getAuthMatgroup()) ? this.materialRepository
							.findByMaterialCK_MatMatgroupAndMatLevel(authH.getAuthMatgroup().trim(), "1") : null;

					Company companyName = Objects.nonNull(authH.getAuthCoy())
							? this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
									authH.getAuthCoy().trim(), CommonUtils.INSTANCE.closeDate())
							: null;

					Bldgmatbillfinal bldgmatbillfinalEntity = this.bldgmatbillfinalRepository
							.findByBldgmatbillfinalCK_BmbfBldgcodeAndBldgmatbillfinalCK_BmbfMgrcode(
									authH.getAuthBldgcode().trim(), authH.getAuthMatgroup().trim());
					Boolean finalDatePresent = true;
					if (Objects.nonNull(bldgmatbillfinalEntity)
							&& Objects.isNull(bldgmatbillfinalEntity.getBmbfBillfinaldate())) {
						finalDatePresent = false;
					}
					if (Objects.nonNull(authH.getAuthAuthdate()))
						certStat = "N";
					else {
						if (authH.getAuthAuthdate().compareTo(bldgmatbillfinalEntity.getBmbfBillfinaldate()) > 0
								&& finalDatePresent)
							certStat = "Y";
						else
							certStat = "N";
					}

					// String materialWorkCode =
					// this.materialRepository.findDistinctByMaterialCK_MatMatgroup(authH.getAuthMatgroup().trim());
					// LOGGER.info("materialWorkCode:: {}", materialWorkCode);

					Query query = this.entityManager.createNativeQuery(
							"SELECT sum(cert_certamount - cert_advadjusted) FROM CERT WHERE CERT_BLDGCODE  = '"
									+ authH.getAuthBldgcode().trim() + "' and CERT_WORKCODE = '"
									+ (Objects.nonNull(materialName.getMatWorkcode())
											? materialName.getMatWorkcode().trim()
											: CommonConstraints.INSTANCE.SPACE_STRING)
									+ "' and CERT_CERTSTATUS > '4' and cert_certtype not in ('A','L','M')  GROUP BY CERT_BLDGCODE,CERT_WORKCODE,CERT_PARTYCODE",
							Tuple.class);

					List<Tuple> resultSetList = query.getResultList();
					Double certAmt = CollectionUtils.isNotEmpty(resultSetList)
							? resultSetList.stream()
									.mapToDouble(result -> result.get(0, BigDecimal.class).doubleValue()).sum()
							: BigInteger.ZERO.doubleValue();
					String cfBldgWork = "Total for labour : " + certAmt.intValue();
					LOGGER.info("cfBldgWork :: {}", cfBldgWork);

					Query queryForTempMatAuthPrintDetail = null;
					if (authH.getAuthAuthtype().trim().equals("N")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               AUTH_AUTHNUM,       AUTH_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, AUTH_H                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (PBLH_AUTHNUM = AUTD_AUTHNUM )                                 and (AUTD_AUTHNUM = AUTH_AUTHNUM)                                  AND (AUTH_PARTYCODE = pblh_partycode)                              and (AUTH_AUTHNUM = PBLH_AUTHNUM)                                  and (AUTD_AUTHNUM = :authNum)",
								Tuple.class);
					}
					if (authH.getAuthAuthtype().trim().equals("L")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               AUTH_AUTHNUM,       AUTH_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, AUTH_H                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (AUTD_AUTHNUM = AUTH_AUTHNUM)                                  AND (AUTH_PARTYCODE = pblh_partycode)                              and (AUTD_AUTHNUM = :authNum)",
								Tuple.class);
					}
					if (authH.getAuthAuthtype().trim().equals("R")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               AUTH_AUTHNUM,       AUTH_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, AUTH_H                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (AUTD_AUTHNUM = AUTH_AUTHNUM)                                  AND (AUTH_PARTYCODE = pblh_partycode)                              and (AUTD_AUTHNUM = :authNum)",
								Tuple.class);
					}
					if (authH.getAuthAuthtype().trim().equals("C")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             0 as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,  '' as AUTD_AUTHNUM,        auth_authtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 AUTH_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               AUTH_AUTHNUM,       AUTH_PARTYCODE       from   AUTH_H                                                  where (AUTH_AUTHNUM = :authNum)",
								Tuple.class);
					}

					if (authH.getAuthAuthtype().trim().equals("A")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             AUTH_PAYAMOUNT as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,   '' as AUTD_AUTHNUM,        auth_authtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 AUTH_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               AUTH_AUTHNUM,       AUTH_PARTYCODE       from   AUTH_H                                                  where (AUTH_AUTHNUM = :authNum)",
								Tuple.class);
					}

					queryForTempMatAuthPrintDetail.setParameter("authNum", authH.getAuthhCK().getAuthAuthnum());
					List<Tuple> queryForTempMatAuthPrintDetailResultSetList = queryForTempMatAuthPrintDetail
							.getResultList();

					if (CollectionUtils.isNotEmpty(queryForTempMatAuthPrintDetailResultSetList)) {
						List<TempMatAuthPrintDetailBean> tempMatAuthPrintDetailBeanList = queryForTempMatAuthPrintDetailResultSetList
								.stream().map(t -> {
									return TempMatAuthPrintDetailBean.builder()
											.autdsuppbillno(Objects.nonNull(t.get(0, String.class))
													? t.get(0, String.class).trim()
													: CommonConstraints.INSTANCE.SPACE_STRING)
											.autdsuppbilldt(Objects.nonNull(t.get(1, Timestamp.class))
													? t.get(1, Timestamp.class).toLocalDateTime().toLocalDate()
													: null)
											.autdauthqty(Objects.nonNull(t.get(2, BigDecimal.class))
													? t.get(2, BigDecimal.class).doubleValue()
													: null)
											.autdauthamount(Objects.nonNull(t.get(3, BigDecimal.class))
													? t.get(3, BigDecimal.class).doubleValue()
													: null)
											.autdauthtdsamt(Objects.nonNull(t.get(4, BigDecimal.class))
													? t.get(4, BigDecimal.class).doubleValue()
													: null)
											.autdadvadj(Objects.nonNull(t.get(5, BigDecimal.class))
													? t.get(5, BigDecimal.class).doubleValue()
													: null)
											.autdrelretamt(Objects.nonNull(t.get(6, BigDecimal.class))
													? t.get(6, BigDecimal.class).doubleValue()
													: null)
											.autdretentionadj(Objects.nonNull(t.get(7, BigDecimal.class))
													? t.get(7, BigDecimal.class).doubleValue()
													: null)
											.autdauthnum(Objects.nonNull(t.get(8, String.class))
													? t.get(8, String.class).trim()
													: null)
											.autdauthtype(Objects.nonNull(t.get(9, String.class))
													? t.get(9, String.class).trim()
													: null)
											.autdretainamt(Objects.nonNull(t.get(10, BigDecimal.class))
													? t.get(10, BigDecimal.class).doubleValue()
													: null)
											.pblhretainos(Objects.nonNull(t.get(11, BigDecimal.class))
													? t.get(11, BigDecimal.class).doubleValue()
													: null)
											.pblhser(Objects.nonNull(t.get(12, String.class))
													? t.get(12, String.class).trim()
													: null)
											.pblhpartycode(Objects.nonNull(t.get(13, String.class))
													? t.get(13, String.class).trim()
													: null)
											.pblhauthnum(Objects.nonNull(t.get(14, String.class))
													? t.get(14, String.class).trim()
													: null)
											.pbldceser(Objects.nonNull(t.get(15, String.class))
													? t.get(15, String.class).trim()
													: null)
											.authauthnum(Objects.nonNull(t.get(16, String.class))
													? t.get(16, String.class).trim()
													: null)
											.authpartycode(Objects.nonNull(t.get(17, String.class))
													? t.get(17, String.class).trim()
													: null)
											.build();
								}).collect(Collectors.toList());
						LOGGER.info("TempMatAuthPrintDetailBean List Size :: {}",
								tempMatAuthPrintDetailBeanList.size());
						LOGGER.info("TempMatAuthPrintDetailBean :: {}", tempMatAuthPrintDetailBeanList);

						for (TempMatAuthPrintDetailBean tempMatAuthPrintDetailBean : tempMatAuthPrintDetailBeanList) {
							if (tempMatAuthPrintDetailBean.getAuthauthnum().trim()
									.equals(authH.getAuthhCK().getAuthAuthnum().trim())) {
								tempMatAuthprintList.add(TempMatAuthprint.builder()
										.tempmatauthprintCK(TempMatAuthprintCK.builder()
												.sessid(Double.valueOf(sessionId))
												.authAuthnum(tempMatAuthPrintDetailBean.getAuthauthnum())
												.autdSuppbillno(tempMatAuthPrintDetailBean.getAutdsuppbillno()).build())
										.Address1(Objects.nonNull(addressEntity)
												? addressEntity.getAdrAdline1().concat(" ")
														.concat(Objects.nonNull(addressEntity.getAdrAdline2())
																? addressEntity.getAdrAdline2().trim()
																: CommonConstraints.INSTANCE.SPACE_STRING)
														.concat(Objects.nonNull(addressEntity.getAdrAdline3())
																? " ".concat(addressEntity.getAdrAdline3().trim())
																: CommonConstraints.INSTANCE.SPACE_STRING)
												: CommonConstraints.INSTANCE.SPACE_STRING)
										.Address2(address2).amtIn_Words(amountInWords)
										.autdAuthnum(tempMatAuthPrintDetailBean.getAutdauthnum())
										.autdAdvadj(tempMatAuthPrintDetailBean.getAutdadvadj())
										.autdAuthamount(tempMatAuthPrintDetailBean.getAutdauthamount())
										.autdAuthqty(tempMatAuthPrintDetailBean.getAutdauthqty())
										.autdAuthtdsamt(tempMatAuthPrintDetailBean.getAutdauthtdsamt())
										.autdAuthtype(tempMatAuthPrintDetailBean.getAutdauthtype())
										.autdRelretamt(tempMatAuthPrintDetailBean.getAutdrelretamt())
										.autdRetainamt(tempMatAuthPrintDetailBean.getAutdretainamt())
										.autdRetentionadj(tempMatAuthPrintDetailBean.getAutdretentionadj())
										.autdSuppbilldt(tempMatAuthPrintDetailBean.getAutdsuppbilldt())
										.authAuthtype(Objects.nonNull(authH.getAuthAuthtype())
												? authH.getAuthAuthtype().trim()
												: null)
										.authAmt(authAmt).authAuthstatus(authH.getAuthAuthstatus()).authStat(authStatus)
										.bldgName(buildingName.getBldgName()).certStat(certStat).cfBldg_Work(cfBldgWork)
										.coyName(companyName.getCoyName()).Currdate(LocalDateTime.now().toLocalDate())
										.matgroupName(materialName.getMatMatname()).noOf_Print(noOfPrint)
										.partyName(partyName.getParPartyname()).payAmt(payAmt).payCover(payCover)
										.pbldCeser(tempMatAuthPrintDetailBean.getPbldceser())
										.pblhRetainos(tempMatAuthPrintDetailBean.getPblhretainos()).Totcertamt(certAmt)
										.totAdjvamt(sumAdvAdj).authPartycode(authH.getAuthPartycode())
										.pblhSer(tempMatAuthPrintDetailBean.getPblhser())
										.pblhAuthnum(tempMatAuthPrintDetailBean.getPblhauthnum())
										.Userid(GenericAuditContextHolder.getContext().getUserid()).build());
								LOGGER.info("tempMatAuthprintList :: {} ", tempMatAuthprintList);
							}
						}

						if (CollectionUtils.isNotEmpty(tempMatAuthprintList)) {
							this.tempMatAuthprintRepository.saveAllAndFlush(tempMatAuthprintList);
						} else {
							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						}
					}
					if (authH.getAuthAuthtype().trim().equals("L") || authH.getAuthAuthtype().trim().equals("R")) {
						Query queryForRAndL = this.entityManager.createNativeQuery(
								"Select autd_suppbillno,    autd_suppbilldt,     autd_authqty,     autd_authamount,    autd_authtdsamt,     autd_advadj,      autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,    autd_retainamt,     AUTH_AUTHNUM,        AUTH_PARTYCODE,    (select pblh_retainos from PBILLH where PBLH_suppbillno =  autd_suppbillno and (PBLH_AUTHNUM = :authNum)) as pblh_retainos        from AUTH_D,AUTH_H where (AUTD_AUTHNUM = AUTH_AUTHNUM) and (AUTD_AUTHNUM = :authNum)",
								Tuple.class);
						queryForRAndL.setParameter("authNum", authH.getAuthhCK().getAuthAuthnum());
						List<Tuple> queryForRAndLResultSetList = queryForRAndL.getResultList();

						if (CollectionUtils.isNotEmpty(queryForRAndLResultSetList)) {
							List<TempMatAuthPrintDetailBean> tempMatAuthPrintDetailForRAndLBeanList = queryForRAndLResultSetList
									.stream().map(t -> {
										return TempMatAuthPrintDetailBean.builder()
												.autdsuppbillno(Objects.nonNull(t.get(0, String.class))
														? t.get(0, String.class).trim()
														: CommonConstraints.INSTANCE.SPACE_STRING)
												.autdsuppbilldt(Objects.nonNull(t.get(1, Timestamp.class))
														? t.get(1, Timestamp.class).toLocalDateTime().toLocalDate()
														: null)
												.autdauthqty(Objects.nonNull(t.get(2, BigDecimal.class))
														? t.get(2, BigDecimal.class).doubleValue()
														: null)
												.autdauthamount(Objects.nonNull(t.get(3, BigDecimal.class))
														? t.get(3, BigDecimal.class).doubleValue()
														: null)
												.autdauthtdsamt(Objects.nonNull(t.get(4, BigDecimal.class))
														? t.get(4, BigDecimal.class).doubleValue()
														: null)
												.autdadvadj(Objects.nonNull(t.get(5, BigDecimal.class))
														? t.get(5, BigDecimal.class).doubleValue()
														: null)
												.autdrelretamt(Objects.nonNull(t.get(6, BigDecimal.class))
														? t.get(6, BigDecimal.class).doubleValue()
														: null)
												.autdretentionadj(Objects.nonNull(t.get(7, BigDecimal.class))
														? t.get(7, BigDecimal.class).doubleValue()
														: null)
												.autdauthnum(Objects.nonNull(t.get(8, String.class))
														? t.get(8, String.class).trim()
														: null)
												.autdauthtype(Objects.nonNull(t.get(9, String.class))
														? t.get(9, String.class).trim()
														: null)
												.autdretainamt(Objects.nonNull(t.get(10, BigDecimal.class))
														? t.get(10, BigDecimal.class).doubleValue()
														: null)
												.authauthnum(Objects.nonNull(t.get(11, String.class))
														? t.get(11, String.class).trim()
														: null)
												.authpartycode(Objects.nonNull(t.get(12, String.class))
														? t.get(12, String.class).trim()
														: null)
												.pblhretainos(Objects.nonNull(t.get(13, BigDecimal.class))
														? t.get(13, BigDecimal.class).doubleValue()
														: null)
												.build();
									}).collect(Collectors.toList());
							LOGGER.info("tempMatAuthPrintDetailForRAndLBeanList List Size :: {}",
									tempMatAuthPrintDetailForRAndLBeanList.size());
							LOGGER.info("tempMatAuthPrintDetailForRAndLBeanList :: {}",
									tempMatAuthPrintDetailForRAndLBeanList);

							if (CollectionUtils.isNotEmpty(tempMatAuthprintList)) {
								tempMatAuthprintList.stream().map(tempMatAuthprint -> {
									tempMatAuthPrintDetailForRAndLBeanList.stream().filter(f -> {
										return tempMatAuthprint.getTempmatauthprintCK().getAuthAuthnum().trim()
												.equals(f.getAuthauthnum().trim())
												&& tempMatAuthprint.getTempmatauthprintCK().getAutdSuppbillno().trim()
														.equals(f.getAutdsuppbillno().trim());
									}).map(tempMatAuthPrintDetailForRAndLBean -> {
										TempMatAuthprint tempMatAuthprintEntity = this.tempMatAuthprintRepository
												.findByTempmatauthprintCK_SessidAndTempmatauthprintCK_AuthAuthnumAndTempmatauthprintCK_AutdSuppbillno(
														Double.valueOf(sessionId),
														tempMatAuthPrintDetailForRAndLBean.getAuthauthnum().trim(),
														tempMatAuthPrintDetailForRAndLBean.getAutdsuppbillno().trim());
										LOGGER.info("TempMatAuthprintEntity After save  :: {}", tempMatAuthprintEntity);
										if (Objects.nonNull(tempMatAuthprintEntity)) {
											tempMatAuthprintEntity.setCurrdate(LocalDate.now());
											tempMatAuthprintEntity.setAutdRetainamt(
													tempMatAuthPrintDetailForRAndLBean.getAutdretainamt());
											tempMatAuthprintEntity.setPblhSer(tempMatAuthprint.getPblhSer());
											tempMatAuthprintEntity.setPblhAuthnum(tempMatAuthprint.getPblhAuthnum());
											tempMatAuthprintEntity.setPbldCeser(tempMatAuthprint.getPbldCeser());
											tempMatAuthprintEntity.setAutdRelretamt(
													tempMatAuthPrintDetailForRAndLBean.getAutdrelretamt());
											tempMatAuthprintEntity.setAutdRetentionadj(
													tempMatAuthPrintDetailForRAndLBean.getAutdretentionadj());
											tempMatAuthprintEntity.setPblhRetainos(
													tempMatAuthPrintDetailForRAndLBean.getPblhretainos());
											this.tempMatAuthprintRepository.saveAndFlush(tempMatAuthprintEntity);
										}
										return tempMatAuthPrintDetailForRAndLBean;
									}).collect(Collectors.toList());

									return tempMatAuthprint;
								}).collect(Collectors.toList());// --------------------> update temp_autprint for auth
																// type l and r

							}

						}
					}
				}

				List<Dbnoteh> dbnotehEntityList = this.dbnotehRepository
						.findByDbnhAuthnoIn(authHConditionBasedList.stream()
								.sorted((p1, p2) -> p1.getAuthhCK().getAuthAuthnum()
										.compareTo(p2.getAuthhCK().getAuthAuthnum()))
								.map(name -> name.getAuthhCK().getAuthAuthnum()).collect(Collectors.toSet()));
				LOGGER.info("dbnotehEntity :: {}", dbnotehEntityList);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
						.data(TempMatAuthPrintDetailResponseBean.builder().sessionId(sessionId)
								.authNumberFrom(authHConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getAuthhCK().getAuthAuthnum()
												.compareTo(p2.getAuthhCK().getAuthAuthnum()))
										.map(name -> name.getAuthhCK().getAuthAuthnum()).findFirst().get())
								.authNumberTo(authHConditionBasedList.stream()
										.sorted((p1, p2) -> p2.getAuthhCK().getAuthAuthnum()
												.compareTo(p1.getAuthhCK().getAuthAuthnum()))
										.map(name -> name.getAuthhCK().getAuthAuthnum()).findFirst().get())
								.serList(CollectionUtils.isNotEmpty(dbnotehEntityList) ? dbnotehEntityList.stream()
										.sorted((p1, p2) -> p2.getDbnotehCK().getDbnhDbnoteser()
												.compareTo(p1.getDbnotehCK().getDbnhDbnoteser()))
										.collect(Collectors.toMap(db -> db.getDbnotehCK().getDbnhDbnoteser(),
												Dbnoteh::getDbnhAuthno))
										: null)
								.authNumList(authHConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getAuthhCK().getAuthAuthnum()
												.compareTo(p2.getAuthhCK().getAuthAuthnum()))
										.map(name -> name.getAuthhCK().getAuthAuthnum()).collect(Collectors.toList()))
								.build())
						.build());
			} catch (Exception e) {
				return ResponseEntity
						.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("System error...").build());
			}
		} else {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> mergePdf(TempMatAuthPrintDetailResponseBean tempMatAuthPrintDetailResponseBean) {
		List<String> finalReportLocationList = new ArrayList<>();
		File file = null;

		try {
			for (String authNum : tempMatAuthPrintDetailResponseBean.getAuthNumList()) {

				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sessid", tempMatAuthPrintDetailResponseBean.getSessionId());
					map.put("authFrom", authNum);
					map.put("authTo", authNum);

					// Make the Feign client request
					byte[] ogByteArray = this.reportInternalFeignClient.generateReportWithMultipleConditionAndParameter(
							ReportMasterRequestBean.builder().name("MaterialPayment_AuthPrint_1.rpt")
									.reportParameters(map).seqId(1).isPrint(false).build());

					byte[] duplicateByteArray = this.reportInternalFeignClient
							.generateReportWithMultipleConditionAndParameter(
									ReportMasterRequestBean.builder().name("Copy_MaterialPayment_AuthPrint_1.rpt")
											.reportParameters(map).seqId(1).isPrint(false).build());

					// GENERATE REPORT OG AND DUPLICATE
					String ogRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(authNum.concat("OG"));
					String ogReportLocation = reportJobPath.concat(ogRandomUUID).concat(".pdf");
					String duplicateRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(authNum.concat("DUPLICATE"));
					String duplicateReportLocation = reportJobPath.concat(duplicateRandomUUID).concat(".pdf");

					try (FileOutputStream fos = new FileOutputStream(ogReportLocation)) {
						fos.write(ogByteArray);
						finalReportLocationList.add(ogReportLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}

					try (FileOutputStream fos = new FileOutputStream(duplicateReportLocation)) {
						fos.write(duplicateByteArray);
						finalReportLocationList.add(duplicateReportLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (tempMatAuthPrintDetailResponseBean.getSerList() != null
							&& !tempMatAuthPrintDetailResponseBean.getSerList().isEmpty()) {
						for (Map.Entry<String, String> entry : tempMatAuthPrintDetailResponseBean.getSerList()
								.entrySet()) {
							if (authNum.equals(entry.getValue())) {
								Map<String, Object> debitNoteParamMap = new HashMap<String, Object>();
								// debitNoteParamMap.put("sessid",tempMatAuthPrintDetailResponseBean.getSessionId());
								debitNoteParamMap.put("serFrom", entry.getKey());
								debitNoteParamMap.put("serTo", entry.getKey());

								byte[] debitNoteByteArray = this.reportInternalFeignClient
										.generateReportWithMultipleConditionAndParameter(ReportMasterRequestBean
												.builder().name("MaterialPymt_Auth_DB_Print_1_New.rpt")
												.reportParameters(debitNoteParamMap).seqId(1).isPrint(false).build());

								for (int i = 0; i < 3; i++) {
									String debitNoteRandomUUID = CommonUtils.INSTANCE
											.uniqueIdentifier(authNum.concat("DebitNote"));
									String debitNoteReportLocation = reportJobPath.concat(debitNoteRandomUUID)
											.concat(".pdf");

									try (FileOutputStream debitNoteFos = new FileOutputStream(
											debitNoteReportLocation)) {
										debitNoteFos.write(debitNoteByteArray);
										finalReportLocationList.add(debitNoteReportLocation);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					// Handle the 404 error
					continue; // Continue to the next iteration
				}
			}
			LOGGER.info("finalReportLocationList :: {}", finalReportLocationList);
			if (CollectionUtils.isNotEmpty(finalReportLocationList)) {
				PDFMergerUtility merger = new PDFMergerUtility();
				// Add input PDF files to the merger
				for (String reportLocation : finalReportLocationList) {
					merger.addSource(reportLocation);
				}
				String randomUUID = CommonUtils.INSTANCE.uniqueIdentifier("MERGED");

				String reportLocation = reportJobPath.concat(randomUUID).concat(".pdf");
				// Set the output file location
				merger.setDestinationFileName(reportLocation);

				// Merge the PDF files
				merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
				file = new File(reportLocation);

				// DELETE TEMPORY FILES CREATED
				deleteFiles(finalReportLocationList);

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING
								.concat(tempMatAuthPrintDetailResponseBean.getAuthNumberFrom().concat("-")
										.concat(tempMatAuthPrintDetailResponseBean.getAuthNumberTo()).concat(".pdf")))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.body(new InputStreamResource(new FileInputStream(file)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@Override
	public ResponseEntity<?> updatePrintStatus(PrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean) {
		List<TempMatAuthprint> tempMatAuthprintEntityList = this.tempMatAuthprintRepository
				.findByTempmatauthprintCK_Sessid(Double.valueOf(printStatusUpdateDetailRequestBean.getSessionId()));
		LOGGER.info("tempMatAuthprintEntityList :: {}", tempMatAuthprintEntityList);
		List<Dbnoteh> dbnotehEntityList = null;

		if (CollectionUtils.isNotEmpty(tempMatAuthprintEntityList)) {
			List<Auth_H> authHEntityList = this.authHRepository
					.findByAuthhCK_AuthAuthnumIn(tempMatAuthprintEntityList.stream().map(tempMatAuthprintEntity -> {
						return tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum();
					}).collect(Collectors.toList()));
			LOGGER.info("authHEntityList :: {}", authHEntityList);

			if (Objects.nonNull(printStatusUpdateDetailRequestBean.getSerList())) {
				dbnotehEntityList = this.dbnotehRepository
						.findByDbnotehCK_DbnhDbnoteserIn(printStatusUpdateDetailRequestBean.getSerList());
				LOGGER.info("dbnotehEntityList :: {}", dbnotehEntityList);
			}
			if (CollectionUtils.isNotEmpty(authHEntityList)) {
				for (TempMatAuthprint tempMatAuthprintEntity : tempMatAuthprintEntityList) {
					for (Auth_H authHEntity : authHEntityList) {
						if (tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum().trim()
								.equals(authHEntity.getAuthhCK().getAuthAuthnum().trim())) {
							authHEntity.setAuthPrinted(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
									? Double.valueOf(tempMatAuthprintEntity.getNoOf_Print())
									: null);
							authHEntity.setAuthPrintedon(LocalDateTime.now());
							authHEntity.setAuthToday(LocalDateTime.now());
							authHEntity.setAuthUserid(GenericAuditContextHolder.getContext().getUserid());
							authHEntity.setAuthSite(GenericAuditContextHolder.getContext().getSite());
							authHEntity.setAuthAuthstatus(authHEntity.getAuthAuthstatus().trim().equals("1") ? "2"
									: authHEntity.getAuthAuthstatus());
							this.authHRepository.save(authHEntity);
						}
					}
					if (CollectionUtils.isNotEmpty(dbnotehEntityList)) {
						for (Dbnoteh dbnotehEntity : dbnotehEntityList) {
							if (tempMatAuthprintEntity.getTempmatauthprintCK().getAutdSuppbillno().trim()
									.equals(dbnotehEntity.getDbnhSuppbillno().trim())) {
								dbnotehEntity.setDbnhNoofprint(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
										? Double.valueOf(tempMatAuthprintEntity.getNoOf_Print())
										: null);
								dbnotehEntity.setDbnhPrints(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
										? Double.valueOf(tempMatAuthprintEntity.getNoOf_Print())
										: null);
								dbnotehEntity.setDbnhPrintdate(LocalDateTime.now());
								dbnotehEntity.setDbnhSite(GenericAuditContextHolder.getContext().getSite());
								dbnotehEntity.setDbnhUserid(GenericAuditContextHolder.getContext().getUserid());
								dbnotehEntity.setDbnhPrintuser(GenericAuditContextHolder.getContext().getUserid());
								dbnotehEntity.setDbnhToday(LocalDateTime.now());
								this.dbnotehRepository.save(dbnotehEntity);
							}
						}
					}

				}

			}

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Record Found").build());

	}

	@Override
	public ResponseEntity<?> fetchCodeHelpDetail(String tableName, String matLevel) {
		switch (CodeHelpTableTypeEnum.getValue(tableName)) {
		case BUILDING: {
			List<Building> buildingEntityList = this.buildingRepository.findByBldgClosedateIsNullOrBldgClosedate(
					CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			List<BuildingResponseBean> buildingResponseBeanList = new ArrayList<>();
			buildingEntityList.stream().map(buildingEntity -> {
				buildingResponseBeanList.add(BuildingResponseBean.builder()
						.code(buildingEntity.getBuildingCK().getBldgCode()).name(buildingEntity.getBldgName()).build());
				return buildingEntity;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(buildingResponseBeanList).build());
		}
		case COMPANY: {
			List<Company> companyEntityList = this.companyRepository
					.findByCompanyCK_CoyClosedate(CommonUtils.INSTANCE.closeDate());
			List<CompanyResponseBean> companyResponseBeanList = new ArrayList<>();
			companyEntityList.stream().map(companyEntity -> {
				companyResponseBeanList.add(CompanyResponseBean.builder()
						.code(companyEntity.getCompanyCK().getCoyCode()).name(companyEntity.getCoyName()).build());
				return companyEntity;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(companyResponseBeanList).build());
		}
		case PARTY: {
			List<Tuple> partyEntityList = this.partyRepository
					.fetchPartyWithAddress(CommonUtils.INSTANCE.closeDateInLocalDateTime());
			System.out.println("Test" + partyEntityList.get(0).get(4));
			List<CodeHelpPartyBean> codeHelpPartyResponseBeanList = new ArrayList<>();
			partyEntityList.stream().map(t -> {
				codeHelpPartyResponseBeanList.add(CodeHelpPartyBean.builder().code(t.get(0, String.class))
						.name(t.get(1, String.class)).type(t.get(2, Character.class)).pmtacnum(t.get(3, String.class))
						.gstno(t.get(4, String.class)).address(t.get(5, String.class)).build());
				return t;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(codeHelpPartyResponseBeanList).build());
		}
		case WORK: {
			Query queryForepWorks = this.entityManager.createNativeQuery(
					"SELECT * FROM EPWORKS WHERE EP_CLOSEDATE is NULL order by EP_WORKCODE", Tuple.class);// used native
																											// query
																											// because
																											// uniqne
																											// index is
																											// null
			List<Tuple> epWorksEntityList = queryForepWorks.getResultList();
			List<EpworksResponseBean> epWorksResponseBeanList = new ArrayList<>();
			epWorksEntityList.stream().map(t -> {
				epWorksResponseBeanList.add(EpworksResponseBean.builder().code(t.get(0, String.class))
						.name(t.get(1, String.class)).build());
				return t;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(epWorksResponseBeanList).build());
		}
		case MATERIAL: {
			if (Objects.isNull(matLevel))
				matLevel = "1";
			List<Material> materialEntityList = this.materialRepository.findByMatLevel(matLevel);
			List<MaterialResponseBean> materialResponseBeanList = new ArrayList<>();
			materialEntityList.stream().map(materialEntity -> {
				materialResponseBeanList.add(MaterialResponseBean.builder()
						.code(Objects.nonNull(materialEntity) ? materialEntity.getMaterialCK().getMatMatcode() : null)
						.name(Objects.nonNull(materialEntity) ? materialEntity.getMatMatname() : null)
						.matgroup(Objects.nonNull(materialEntity) ? materialEntity.getMaterialCK().getMatMatgroup()
								: null)
						.itemcode(Objects.nonNull(materialEntity) ? materialEntity.getMaterialCK().getMatItemcode()
								: null)
						.build());
				return materialEntity;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(materialResponseBeanList).build());
		}
		case HSN: {
			List<Hsnsacmaster> hsnsacMasterEntityList = this.hsnsacmasterRepository
					.findByHsnsacmasterCK_HsmsTypeAndHsmsClosedate(HSMSTypeEnum.HSN.toString(),
							CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			LOGGER.info("hsnsacMasterEntityList :: {}", hsnsacMasterEntityList.size());

			List<HsnsacmasterResponseBean> hsnsacmasterResponseBean = new ArrayList<>();
			hsnsacMasterEntityList.stream().map(hsnsacMasterEntity -> {
				hsnsacmasterResponseBean.add(
						HsnsacmasterResponseBean.builder().code(hsnsacMasterEntity.getHsnsacmasterCK().getHsmsCode())
								.desc(hsnsacMasterEntity.getHsmsDesc()).cgstperc(hsnsacMasterEntity.getHsmsCgstperc())
								.sgstperc(hsnsacMasterEntity.getHsmsSgstperc())
								.igstperc(hsnsacMasterEntity.getHsmsIgstperc()).build());
				return hsnsacMasterEntity;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(hsnsacmasterResponseBean).build());
		}
		case SAC: {
			List<Hsnsacmaster> hsnsacMasterEntityList = this.hsnsacmasterRepository
					.findByHsnsacmasterCK_HsmsTypeAndHsmsClosedate(HSMSTypeEnum.SAC.toString(),
							CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			LOGGER.info("hsnsacMasterEntityList :: {}", hsnsacMasterEntityList.size());

			List<HsnsacmasterResponseBean> hsnsacmasterResponseBean = new ArrayList<>();
			hsnsacMasterEntityList.stream().map(hsnsacMasterEntity -> {
				hsnsacmasterResponseBean.add(
						HsnsacmasterResponseBean.builder().code(hsnsacMasterEntity.getHsnsacmasterCK().getHsmsCode())
								.desc(hsnsacMasterEntity.getHsmsDesc()).cgstperc(hsnsacMasterEntity.getHsmsCgstperc())
								.sgstperc(hsnsacMasterEntity.getHsmsSgstperc())
								.igstperc(hsnsacMasterEntity.getHsmsIgstperc()).build());
				return hsnsacMasterEntity;
			}).collect(Collectors.toList());
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(hsnsacmasterResponseBean).build());
		}
		case WORKMATNARRATION: {
			Query queryForNarration = this.entityManager.createNativeQuery(
					"SELECT (SELECT trim(ENT_NAME) FROM ENTITY WHERE ENT_CLASS = 'BTYPE' AND ENT_ID = MCIM_BLDGTYPE) AS Bldg_Type,MCIM_MATCERTTYPE,MCIM_MATCERTCODE,(CASE mcim_matcerttype WHEN 'Labour' Then (SELECT ep_workname FROM epworks WHERE ep_workcode = mcim_matcertcode AND ((ep_closedate IS NULL OR ep_closedate = '01/JAN/2050' OR ep_closedate = '28/OCT/2015' OR ep_closedate = '31/MAR/2016'))) WHEN 'Material' THEN (SELECT mat_matname FROM material WHERE mat_matgroup = mcim_matcertcode AND mat_level = '1'AND ((mat_closedate IS NULL OR mat_closedate = '01/JAN/2050' OR mat_closedate = '28/OCT/2015' OR mat_closedate = '31/MAR/2016'))) END) AS MatCertName ,MCIM_ITEMDESC FROM MATCERTITEMMASTER where mcim_closedate = '01-JAN-2050' ORDER BY MCIM_GROUPCODE,MCIM_SUBGROUPCODE,MCIM_MATCERTTYPE,MCIM_MATCERTCODE,MCIM_BLDGTYPE,MCIM_SRNO",
					Tuple.class);// used native query because uniqne index is null
			List<Tuple> epWorksEntityList = queryForNarration.getResultList();
			List<WorkMatNarrationResponseBean> workMatNarrationResponseBeanList = new ArrayList<>();
			epWorksEntityList.stream().map(t -> {
				workMatNarrationResponseBeanList
						.add(WorkMatNarrationResponseBean.builder().bldgType(t.get(0, String.class))
								.matCertType(t.get(1, String.class)).matCertCode(t.get(2, String.class))
								.matCertName(t.get(3, String.class)).matItemDesc(t.get(4, String.class)).build());
				return t;
			}).collect(Collectors.toList());
			return ResponseEntity.ok(
					ServiceResponseBean.builder().status(Boolean.TRUE).data(workMatNarrationResponseBeanList).build());
		}
		default:
			break;
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Record Found").build());
	}

	@Override
	public ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId) {
		List<TempMatAuthprint> tempMatAuthprintEntityList = this.tempMatAuthprintRepository
				.findByTempmatauthprintCK_Sessid(Double.valueOf(sessionId));
		LOGGER.info("tempMatAuthprintEntityList :: {}", tempMatAuthprintEntityList);

		if (CollectionUtils.isNotEmpty(tempMatAuthprintEntityList)) {
			this.tempMatAuthprintRepository.deleteBySessid(Double.valueOf(sessionId));
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Record Found").build());
	}

	public static void deleteFiles(List<String> fileLocations) {
		for (String fileLocation : fileLocations) {
			File file = new File(fileLocation);
			if (file.exists()) {
				if (file.delete()) {
				} else {
				}
			} else {
			}
		}
	}
}
