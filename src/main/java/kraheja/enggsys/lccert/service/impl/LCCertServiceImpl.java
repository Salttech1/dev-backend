package kraheja.enggsys.lccert.service.impl;

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
import java.util.Set;
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
import kraheja.enggsys.bean.LCCertPrintDetailBean;
import kraheja.purch.bean.request.Auth_DRequestBean;
import kraheja.purch.bean.request.CancelMaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialDetailRequestBean;
import kraheja.enggsys.bean.request.LCCertPrintRequestBean;
import kraheja.purch.bean.request.MaterialPaymentRequestBean;
import kraheja.enggsys.bean.request.LCCertPrRequestBean;
import kraheja.purch.bean.request.MaterialPaymentViewRequestBean;
import kraheja.enggsys.bean.request.LCCertViewRequestBean;
import kraheja.purch.bean.request.PassMaterialPaymentRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.request.LCCertPrintStatusUpdateDetailRequestBean;
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
import kraheja.enggsys.bean.response.LCCertPrintDetailResponseBean;

import kraheja.purch.bean.response.WorkMatNarrationResponseBean;
import kraheja.purch.entity.Auth_D;
import kraheja.purch.entity.Auth_H;
import kraheja.enggsys.entity.Lccert;
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
import kraheja.enggsys.lccert.service.LCCertService;
import kraheja.purch.repository.AdvrecvoucherRepository;
import kraheja.purch.repository.AuthDRepository;
import kraheja.purch.repository.AuthHRepository;
import kraheja.enggsys.repository.LccertRepository;
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
public class LCCertServiceImpl implements LCCertService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AuthHRepository authHRepository;

	@Autowired
	private LccertRepository lccertRepository;

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
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(
			LCCertPrintRequestBean LCCertPrintRequestBean) {
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		String whereCondition = "";
		String andString = "AND";
		List<Lccert> lccertList = this.lccertRepository.findByLcerPrintedonAndLcerUserid(null,
				GenericAuditContextHolder.getContext().getUserid().trim());
		if (LCCertPrintRequestBean.getIsUnprintedAuths()) {
			if (CollectionUtils.isEmpty(lccertList))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("There are no Unprinted LC certificates for printing...").build());
			else {
				String commaSepratedAuthNums = String.join(",", lccertList.stream()
						.map(name -> ("'" + name.getLccertCK().getLcerCertnum() + "'")).collect(Collectors.toList()));
				whereCondition = " a.Lcer_certnum in (".concat(commaSepratedAuthNums).concat(")");
			}
		} else {
			if (StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthDateFrom())
					&& StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthDateTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				// certdate is with time
				whereCondition += andString + " a.Lcer_certdate between '"
						+ LCCertPrintRequestBean.getAuthDateFrom() + "' and '"
						+ LCCertPrintRequestBean.getAuthDateTo() + "'";
			}

			
			Boolean isBlank1=StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthNumberFrom());
			Boolean isBlank2=StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthDateTo());
			
			if (StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthNumberFrom())
					&& StringUtils.isNotBlank(LCCertPrintRequestBean.getAuthNumberTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				else
					andString = "AND";
				whereCondition += andString + " a.Lcer_certnum BETWEEN '"
						+ LCCertPrintRequestBean.getAuthNumberFrom() + "' and '"
						+ LCCertPrintRequestBean.getAuthNumberTo() + "'";
			}
		}

		Query lccertQuery = this.entityManager.createNativeQuery("SELECT * FROM LCCERT a where " + whereCondition,
				Lccert.class);
		
		List<Lccert> lccertConditionBasedList = lccertQuery.getResultList();
		LOGGER.info("CERT QUERY :: {}", lccertConditionBasedList);

		if (CollectionUtils.isNotEmpty(lccertConditionBasedList)) {
			try {
				for (Lccert lccert : lccertConditionBasedList) {
					String certStatus = "";
					Integer noOfPrint = BigInteger.ZERO.intValue();
					String payCover = "";
					Double payAmt = lccert.getLcerPayamount();
					Double certAmt = lccert.getLcerPayamount();// -----------> for cert type c it is certpayamount but
																// negative
					String amountInWords = "";
					String address2 = "";
					String certStat = "";
					Address addressEntity = null;
					Party partyName = null;
					Double sumAdvAdj = BigInteger.ZERO.doubleValue();
					List<TempMatAuthprint> tempMatAuthprintList = new ArrayList<>();

					if (((Objects.isNull(lccert.getLcerPrinted()) || lccert.getLcerPrinted() <= 0))
							&& Integer.valueOf(lccert.getLcerCertstatus()) < 5)
						certStatus = PrintStatusEnum.ORIGINAL.getValue();
					else
						certStatus = PrintStatusEnum.REPRINT.getValue();
					if (lccert.getLcerCerttype().equals("R"))
						certStatus = PrintStatusEnum.ORIGINAL.getValue();

//					if (!lccert.getLcerCerttype().trim().equals("R") && Integer.valueOf(lccert.getLcerCertstatus()) >= 5)
//					if (!lccert.getLcerCerttype().trim().equals("R"))
						noOfPrint = lccert.getLcerPrinted().intValue() + 1;

					List<Auth_D> authDList = this.authDRepository
							.findByAuthdCK_AutdAuthnum(lccert.getLccertCK().getLcerCertnum());
					LOGGER.info("authDList size :: {}", authDList.size());
					LOGGER.info("authDList  :: {}", authDList);
					if (CollectionUtils.isNotEmpty(authDList))
						sumAdvAdj = authDList.stream().filter(f -> f.getAutdAdvadj() != null)
								.mapToDouble(Auth_D::getAutdAdvadj).sum();

					if (lccert.getLcerCerttype().trim().equals("R") || lccert.getLcerCerttype().trim().equals("L"))
						sumAdvAdj += lccert.getLcerCertamount();

					if (lccert.getLcerCerttype().trim().equals("C")) {
						payCover = "RECOVER";
						payAmt = lccert.getLcerPayamount() * -1;
						certAmt = lccert.getLcerPayamount() * -1;
					} else
						payCover = "PAY";

					// amountInWords = "RUPEES
					// ".concat(CommonUtils.convert(lccert.getLcerrPayamount().intValue()).concat("
					// ONLY"));
					amountInWords = CurrencyConverterUtils.convertToIndianCurrency(
							lccert.getLcerPayamount() < 0 ? String.valueOf(lccert.getLcerPayamount() * -1)
									: String.valueOf(lccert.getLcerPayamount()));

					if (lccert.getLcerPayamount().intValue() == 0) {
						amountInWords = "Rupees  Zero And Paise Zero Only.";
					}
					// To convert into capital case - YP 10/04/2023
					// amountInWords = CommonUtils.INSTANCE.convertToCapitalizeCase(amountInWords);

					if (Objects.nonNull(lccert.getLcerPartycode())) {
						partyName = this.partyRepository
								.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
										lccert.getLcerPartycode().trim(),
										CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.E.toString());
						LOGGER.info("Party Name :: {}", partyName);

						addressEntity = this.addressRepository
								.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(
										lccert.getLcerPartycode().trim(), AdSegment.PARTY.toString(),
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

					Building buildingName = Objects.nonNull(lccert.getLcerBldgcode())
							? this.buildingRepository.findByBuildingCK_BldgCode(lccert.getLcerBldgcode().trim())
							: null;

					Material materialName = Objects.nonNull(lccert.getLcerWorkcode()) ? this.materialRepository
							.findByMaterialCK_MatMatgroupAndMatLevel(lccert.getLcerWorkcode(), "1") : null;


					Company companyName = Objects.nonNull(lccert.getLcerCoy())
							? this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
									lccert.getLcerCoy().trim(), CommonUtils.INSTANCE.closeDate())
							: null;

					Bldgmatbillfinal bldgmatbillfinalEntity = this.bldgmatbillfinalRepository
							.findByBldgmatbillfinalCK_BmbfBldgcodeAndBldgmatbillfinalCK_BmbfMgrcode(
									lccert.getLcerBldgcode().trim(), lccert.getLcerWorkcode().trim());
					Boolean finalDatePresent = true;
					if (Objects.nonNull(bldgmatbillfinalEntity)
							&& Objects.isNull(bldgmatbillfinalEntity.getBmbfBillfinaldate())) {
						finalDatePresent = false;
					}
					if (Objects.nonNull(lccert.getLcerCertdate()))
						certStat = "N";
					else {
						if (lccert.getLcerCertdate().compareTo(bldgmatbillfinalEntity.getBmbfBillfinaldate()) > 0
								&& finalDatePresent)
							certStat = "Y";
						else
							certStat = "N";
					}

					// String materialWorkCode =
					// this.materialRepository.findDistinctByMaterialCK_MatMatgroup(lccert.getLcerWorkcode().trim());
					// LOGGER.info("materialWorkCode:: {}", materialWorkCode);

					Query query = this.entityManager.createNativeQuery(
							"SELECT sum(cert_certamount - cert_advadjusted) FROM CERT WHERE CERT_BLDGCODE  = '"
									+ lccert.getLcerBldgcode().trim() + "' and CERT_WORKCODE = '"
									+  CommonConstraints.INSTANCE.SPACE_STRING
									+ "' and CERT_CERTSTATUS > '4' and cert_certtype not in ('A','L','M')  GROUP BY CERT_BLDGCODE,CERT_WORKCODE,CERT_PARTYCODE",
							Tuple.class);

					
					List<Tuple> resultSetList = query.getResultList();
					Double certAmt1 = CollectionUtils.isNotEmpty(resultSetList)
							? resultSetList.stream()
									.mapToDouble(result -> result.get(0, BigDecimal.class).doubleValue()).sum()
							: BigInteger.ZERO.doubleValue();
					String cfBldgWork = "Total for labour : " + certAmt1.intValue();
					LOGGER.info("cfBldgWork :: {}", cfBldgWork);

					Query queryForTempMatAuthPrintDetail = null;
					if (lccert.getLcerCerttype().trim().equals("N")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               lcer_CERTNUM,       lcer_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, LCcert                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (PBLH_AUTHNUM = AUTD_AUTHNUM )                                 and (AUTD_AUTHNUM = lcer_certNUM)                                  AND (lcer_PARTYCODE = pblh_partycode)                              and (lcer_certNUM = PBLH_AUTHNUM)                                  and (AUTD_AUTHNUM = :certNum)",
								Tuple.class);
					}
					if (lccert.getLcerCerttype().trim().equals("L")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, LCcert                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (AUTD_AUTHNUM = lcer_certNUM)                                  AND (lcer_PARTYCODE = pblh_partycode)                              and (AUTD_AUTHNUM = :certNum)",
								Tuple.class);
					}
//					if (lccert.getLcerCerttype().trim().equals("R")) {
//						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
//								"Select     autd_suppbillno,    autd_suppbilldt,     autd_authqty,             autd_authamount,    autd_authtdsamt,     autd_advadj,              autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,            autd_retainamt,     pblh_retainos,       pblh_SER,                 pblh_partycode,     PBLH_AUTHNUM,        PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from AUTH_D,  PBILLH, PBILLD, LCcert                               where (AUTD_SUPPBILLNO = PBLH_SUPPBILLNO)                          and (AUTD_SUPPBILLDT = PBLH_SUPPBILLDT)                            and (PBLD_SER = PBLH_SER)                                          and (AUTD_AUTHNUM = lcer_certNUM)                                  AND (lcer_PARTYCODE = pblh_partycode)                              and (AUTD_AUTHNUM = :certNum)",
//								Tuple.class);
//					}
					if (lccert.getLcerCerttype().trim().equals("C")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             0 as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,  '' as AUTD_AUTHNUM,        lcer_certtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 lcer_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from   LCCERT                                                  where (lcer_certNUM = :certNum)",
								Tuple.class);
					}

					if (lccert.getLcerCerttype().trim().equals("I")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             lcer_PAYAMOUNT as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,   '' as AUTD_AUTHNUM,        lcer_certtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 lcer_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from   LCcert                                                  where (lcer_certNUM = :certNum)",
								Tuple.class);
					}

						if (lccert.getLcerCerttype().trim().equals("R")) {
							queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
									"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             lcer_PAYAMOUNT as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,   '' as AUTD_AUTHNUM,        lcer_certtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 lcer_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from   LCcert                                                  where (lcer_certNUM = :certNum)",
									Tuple.class);
						}

					if (lccert.getLcerCerttype().trim().equals("A")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as autd_suppbillno,   null as  autd_suppbilldt,     0.0 as autd_authqty,             lcer_PAYAMOUNT as autd_authamount,    0 as autd_authtdsamt,     0 as autd_advadj,              0 as autd_relretamt,     0 as autd_retentionadj,   '' as AUTD_AUTHNUM,        lcer_certtype as autd_authtype,            0 as autd_retainamt,     '' as pblh_retainos,       '' as pblh_SER,                 lcer_PARTYCODE as pblh_partycode,     '' as PBLH_AUTHNUM,        '' as PBLD_CESER,               lcer_certNUM,       lcer_PARTYCODE       from   LCcert                                                  where (lcer_certNUM = :certNum)",
								Tuple.class);
					}

					queryForTempMatAuthPrintDetail.setParameter("certNum", lccert.getLccertCK().getLcerCertnum());
					List<Tuple> queryForTempMatAuthPrintDetailResultSetList = queryForTempMatAuthPrintDetail
							.getResultList();

					if (CollectionUtils.isNotEmpty(queryForTempMatAuthPrintDetailResultSetList)) {
						List<LCCertPrintDetailBean> LCCertPrintDetailBeanList = queryForTempMatAuthPrintDetailResultSetList
								.stream().map(t -> {
									return LCCertPrintDetailBean.builder()
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
						LOGGER.info("LCCertPrintDetailBean List Size :: {}",
								LCCertPrintDetailBeanList.size());
						LOGGER.info("LCCertPrintDetailBean :: {}", LCCertPrintDetailBeanList);

						for (LCCertPrintDetailBean LCCertPrintDetailBean : LCCertPrintDetailBeanList) {
							if (LCCertPrintDetailBean.getAuthauthnum().trim()
									.equals(lccert.getLccertCK().getLcerCertnum().trim())) {
								tempMatAuthprintList.add(TempMatAuthprint.builder()
										.tempmatauthprintCK(TempMatAuthprintCK.builder()
												.sessid(Double.valueOf(sessionId))
												.authAuthnum(LCCertPrintDetailBean.getAuthauthnum())
												.autdSuppbillno(LCCertPrintDetailBean.getAutdsuppbillno()).build())
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
										.autdAuthnum(LCCertPrintDetailBean.getAutdauthnum())
										.autdAdvadj(LCCertPrintDetailBean.getAutdadvadj())
										.autdAuthamount(LCCertPrintDetailBean.getAutdauthamount())
										.autdAuthqty(LCCertPrintDetailBean.getAutdauthqty())
										.autdAuthtdsamt(LCCertPrintDetailBean.getAutdauthtdsamt())
										.autdAuthtype(LCCertPrintDetailBean.getAutdauthtype())
										.autdRelretamt(LCCertPrintDetailBean.getAutdrelretamt())
										.autdRetainamt(LCCertPrintDetailBean.getAutdretainamt())
										.autdRetentionadj(LCCertPrintDetailBean.getAutdretentionadj())
										.autdSuppbilldt(LCCertPrintDetailBean.getAutdsuppbilldt())
										.authAuthtype(Objects.nonNull(lccert.getLcerCerttype())
												? lccert.getLcerCerttype().trim()
												: null)
										.authAmt(certAmt).authAuthstatus(lccert.getLcerCertstatus()).authStat(certStatus)
										.bldgName(buildingName.getBldgName()).certStat(certStat).cfBldg_Work(cfBldgWork)
										.coyName(companyName.getCoyName()).Currdate(LocalDateTime.now().toLocalDate())
										.matgroupName(CommonConstraints.INSTANCE.SPACE_STRING).noOf_Print(noOfPrint)
										.partyName(partyName.getParPartyname()).payAmt(payAmt).payCover(payCover)
										.pbldCeser(LCCertPrintDetailBean.getPbldceser())
										.pblhRetainos(LCCertPrintDetailBean.getPblhretainos()).Totcertamt(certAmt)
										.totAdjvamt(sumAdvAdj).authPartycode(lccert.getLcerPartycode())
										.pblhSer(LCCertPrintDetailBean.getPblhser())
										.pblhAuthnum(LCCertPrintDetailBean.getPblhauthnum())
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
					if (lccert.getLcerCerttype().trim().equals("L") || lccert.getLcerCerttype().trim().equals("R")) {
						Query queryForRAndL = this.entityManager.createNativeQuery(
								"Select autd_suppbillno,    autd_suppbilldt,     autd_authqty,     autd_authamount,    autd_authtdsamt,     autd_advadj,      autd_relretamt,     autd_retentionadj,  AUTD_AUTHNUM,        autd_authtype,    autd_retainamt,     lcer_certNUM,        lcer_PARTYCODE,    (select pblh_retainos from PBILLH where PBLH_suppbillno =  autd_suppbillno and (PBLH_AUTHNUM = :certNum)) as pblh_retainos        from AUTH_D,LCcert where (AUTD_AUTHNUM = lcer_certNUM) and (AUTD_AUTHNUM = :certNum)",
								Tuple.class);
						queryForRAndL.setParameter("certNum", lccert.getLccertCK().getLcerCertnum());
						List<Tuple> queryForRAndLResultSetList = queryForRAndL.getResultList();

						if (CollectionUtils.isNotEmpty(queryForRAndLResultSetList)) {
							List<LCCertPrintDetailBean> tempMatAuthPrintDetailForRAndLBeanList = queryForRAndLResultSetList
									.stream().map(t -> {
										return LCCertPrintDetailBean.builder()
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
						.findByDbnhAuthnoIn(lccertConditionBasedList.stream()
								.sorted((p1, p2) -> p1.getLccertCK().getLcerCertnum()
										.compareTo(p2.getLccertCK().getLcerCertnum()))
								.map(name -> name.getLccertCK().getLcerCertnum()).collect(Collectors.toSet()));
				LOGGER.info("dbnotehEntity :: {}", dbnotehEntityList);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
						.data(LCCertPrintDetailResponseBean.builder().sessionId(sessionId)
								.authNumberFrom(lccertConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getLccertCK().getLcerCertnum()
												.compareTo(p2.getLccertCK().getLcerCertnum()))
										.map(name -> name.getLccertCK().getLcerCertnum()).findFirst().get())
								.authNumberTo(lccertConditionBasedList.stream()
										.sorted((p1, p2) -> p2.getLccertCK().getLcerCertnum()
												.compareTo(p1.getLccertCK().getLcerCertnum()))
										.map(name -> name.getLccertCK().getLcerCertnum()).findFirst().get())
								.serList(CollectionUtils.isNotEmpty(dbnotehEntityList) ? dbnotehEntityList.stream()
										.sorted((p1, p2) -> p2.getDbnotehCK().getDbnhDbnoteser()
												.compareTo(p1.getDbnotehCK().getDbnhDbnoteser()))
										.collect(Collectors.toMap(db -> db.getDbnotehCK().getDbnhDbnoteser(),
												Dbnoteh::getDbnhAuthno))
										: null)
								.authNumList(lccertConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getLccertCK().getLcerCertnum()
												.compareTo(p2.getLccertCK().getLcerCertnum()))
										.map(name -> name.getLccertCK().getLcerCertnum()).collect(Collectors.toList()))
								.build())
						.build());
			} catch (Exception e) {
				LOGGER.info("exception :: {}", e);
				return ResponseEntity
						.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("System error...").build());
			}
		} else {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<?> mergePdf(LCCertPrintDetailResponseBean lccertPrintDetailResponseBean) {
		List<String> finalReportLocationList = new ArrayList<>();
		File file = null;

		try {
			for (String certNum : lccertPrintDetailResponseBean.getAuthNumList()) {

				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sessid", lccertPrintDetailResponseBean.getSessionId());
					map.put("authFrom", certNum);
					map.put("authTo", certNum);

					// Make the Feign client request
					byte[] ogByteArray = this.reportInternalFeignClient.generateReportWithMultipleConditionAndParameter(
							ReportMasterRequestBean.builder().name("Engg_RP_LCTTCertificate.rpt")
									.reportParameters(map).seqId(1).isPrint(false).build());

					byte[] duplicateByteArray = this.reportInternalFeignClient
							.generateReportWithMultipleConditionAndParameter(
									ReportMasterRequestBean.builder().name("Engg_RP_LCTTCertificate - Copy.rpt")
											.reportParameters(map).seqId(1).isPrint(false).build());

					// GENERATE REPORT OG AND DUPLICATE
					String ogRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(certNum.concat("OG"));
					String ogReportLocation = reportJobPath.concat(ogRandomUUID).concat(".pdf");
					String duplicateRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(certNum.concat("DUPLICATE"));
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
					if (lccertPrintDetailResponseBean.getSerList() != null
							&& !lccertPrintDetailResponseBean.getSerList().isEmpty()) {
						for (Map.Entry<String, String> entry : lccertPrintDetailResponseBean.getSerList()
								.entrySet()) {
							if (certNum.equals(entry.getValue())) {
								Map<String, Object> debitNoteParamMap = new HashMap<String, Object>();
								// debitNoteParamMap.put("sessid",lcCertPrintDetailResponseBean.getSessionId());
								debitNoteParamMap.put("serFrom", entry.getKey());
								debitNoteParamMap.put("serTo", entry.getKey());

								byte[] debitNoteByteArray = this.reportInternalFeignClient
										.generateReportWithMultipleConditionAndParameter(ReportMasterRequestBean
												.builder().name("MaterialPymt_Auth_DB_Print_1_New.rpt")
												.reportParameters(debitNoteParamMap).seqId(1).isPrint(false).build());

								for (int i = 0; i < 3; i++) {
									String debitNoteRandomUUID = CommonUtils.INSTANCE
											.uniqueIdentifier(certNum.concat("DebitNote"));
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
								.concat(lccertPrintDetailResponseBean.getAuthNumberFrom().concat("-")
										.concat(lccertPrintDetailResponseBean.getAuthNumberTo()).concat(".pdf")))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.body(new InputStreamResource(new FileInputStream(file)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@Override
	public ResponseEntity<?> updateLCcertPrintStatus(LCCertPrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean) {
		List<TempMatAuthprint> tempMatAuthprintEntityList = this.tempMatAuthprintRepository
				.findByTempmatauthprintCK_Sessid(Double.valueOf(printStatusUpdateDetailRequestBean.getSessionId()));
		LOGGER.info("tempMatAuthprintEntityList :: {}", tempMatAuthprintEntityList);
		List<Dbnoteh> dbnotehEntityList = null;

		if (CollectionUtils.isNotEmpty(tempMatAuthprintEntityList)) {
			List<Lccert> lccertEntityList = this.lccertRepository
					.findByLccertCK_LcerCertnumIn(tempMatAuthprintEntityList.stream().map(tempMatAuthprintEntity -> {
						return tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum();
					}).collect(Collectors.toList()));
			LOGGER.info("lccertEntityList :: {}", lccertEntityList);

			if (Objects.nonNull(printStatusUpdateDetailRequestBean.getSerList())) {
				dbnotehEntityList = this.dbnotehRepository
						.findByDbnotehCK_DbnhDbnoteserIn(printStatusUpdateDetailRequestBean.getSerList());
				LOGGER.info("dbnotehEntityList :: {}", dbnotehEntityList);
			}
			if (CollectionUtils.isNotEmpty(lccertEntityList)) {
				for (TempMatAuthprint tempMatAuthprintEntity : tempMatAuthprintEntityList) {
					for (Lccert lccertEntity : lccertEntityList) {
						if (tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum().trim()
								.equals(lccertEntity.getLccertCK().getLcerCertnum().trim())) {
							lccertEntity.setLcerPrinted(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
									? Integer.valueOf(tempMatAuthprintEntity.getNoOf_Print())
									: null);
							lccertEntity.setLcerPrintedon(LocalDate.now());
							lccertEntity.setLcerToday(LocalDateTime.now());
							lccertEntity.setLcerUserid(GenericAuditContextHolder.getContext().getUserid());
							lccertEntity.setLcerSite(GenericAuditContextHolder.getContext().getSite());
							lccertEntity.setLcerCertstatus(lccertEntity.getLcerCertstatus().trim().equals("1") ? "2"
									: lccertEntity.getLcerCertstatus());
							this.lccertRepository.save(lccertEntity);
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
