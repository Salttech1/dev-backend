package kraheja.enggsys.certificatesystem.reports.service.impl;

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
import kraheja.enggsys.bean.CertPrintDetailBean;
import kraheja.enggsys.bean.MatcertLinkPrintCertBean;
import kraheja.purch.bean.request.Auth_DRequestBean;
import kraheja.enggsys.bean.request.CertdetailsRequestBean;

import kraheja.purch.bean.request.CancelMaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialDetailRequestBean;
import kraheja.enggsys.bean.request.CertPrintRequestBean;
import kraheja.purch.bean.request.MaterialPaymentRequestBean;
import kraheja.enggsys.bean.request.CertPrRequestBean;
import kraheja.purch.bean.request.MaterialPaymentViewRequestBean;
import kraheja.enggsys.bean.request.CertViewRequestBean;
import kraheja.purch.bean.request.PassMaterialPaymentRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.request.CertPrintStatusUpdateDetailRequestBean;
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
import kraheja.enggsys.bean.response.CertPrintDetailResponseBean;

import kraheja.purch.bean.response.WorkMatNarrationResponseBean;
import kraheja.purch.entity.Auth_D;
import kraheja.purch.entity.Auth_H;
import kraheja.enggsys.entity.Certdetails;
import kraheja.enggsys.entity.Cert;

import kraheja.purch.entity.Bldgmatbillfinal;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.DbnotedCK;
import kraheja.enggsys.entity.Cdbnoted;
import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.DbnotehCK;
import kraheja.enggsys.entity.Cdbnoteh;
import kraheja.purch.entity.Matcertestimateactual;
import kraheja.purch.entity.Material;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.enggsys.entity.Cbilld;
import kraheja.enggsys.entity.Cbillh;
import kraheja.purch.entity.TempMatAuthprint;
import kraheja.purch.entity.TempMatAuthprintCK;
import kraheja.enggsys.entity.TempCertprint;
import kraheja.enggsys.entity.TempCertprintCK;
import kraheja.purch.enums.AuthTypeEnum;
import kraheja.enggsys.enums.CertTypeEnum;
import kraheja.purch.enums.BillTypeEnum;
import kraheja.purch.enums.CodeHelpTableTypeEnum;
import kraheja.purch.materialpayments.mappers.AdvrecvoucherEntityPojoMapper;
import kraheja.purch.materialpayments.mappers.Auth_DEntityPojoMapper;
import kraheja.purch.materialpayments.mappers.Auth_HEntityPojoMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CertdetailsMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CertMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CertworknarrdtlMapper;
import kraheja.purch.materialpayments.mappers.AuthmatgroupnarrdtlEntityPojoMapper;
import kraheja.purch.materialpayments.service.MaterialPaymentsService;
import kraheja.enggsys.certificatesystem.reports.service.CertService;
import kraheja.purch.repository.AdvrecvoucherRepository;
import kraheja.purch.repository.AuthDRepository;
import kraheja.enggsys.repository.CertdetailsRepository;
import kraheja.purch.repository.AuthHRepository;
import kraheja.enggsys.repository.CertRepository;
import kraheja.purch.repository.AuthmatgroupnarrdtlRepository;
import kraheja.enggsys.repository.CertworknarrdtlRepository;
import kraheja.enggsys.repository.ContractdebitRepository;
import kraheja.purch.repository.BldgmatbillfinalRepository;
import kraheja.purch.repository.DbnotedRepository;
import kraheja.enggsys.repository.CdbnotedRepository;
import kraheja.purch.repository.DbnotehRepository;
import kraheja.enggsys.repository.CdbnotehRepository;
import kraheja.purch.repository.MatcertestimateactualRepository;
import kraheja.purch.repository.MaterialRepository;
import kraheja.purch.repository.PbilldRepository;
import kraheja.purch.repository.PbillhRepository;
import kraheja.enggsys.repository.CbilldRepository;
import kraheja.enggsys.repository.CbillhRepository;
import kraheja.purch.repository.TempMatAuthprintRepository;
import kraheja.enggsys.repository.TempCertprintRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class CertServiceImpl implements CertService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private AuthHRepository authHRepository;

	@Autowired
	private CertRepository certRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private AuthDRepository authDRepository;

	@Autowired
	private CertdetailsRepository certDetailsRepository;

	@Autowired
	private PbillhRepository pbillhRepository;

	@Autowired
	private CbillhRepository cbillhRepository;

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
	private CertworknarrdtlRepository certworknarrdtlRepository;

	@Autowired
	private ContractdebitRepository contractdebitRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private DbnotehRepository dbnotehRepository;

	@Autowired
	private DbnotedRepository dbnotedRepository;

	@Autowired
	private CdbnotehRepository cdbnotehRepository;

	@Autowired
	private CdbnotedRepository cdbnotedRepository;

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
	private TempCertprintRepository tempCertprintRepository;

	@Autowired
	private EpworksRepository epworksRepository;

	@Autowired
	private HsnsacmasterRepository hsnsacmasterRepository;

	@Autowired
	private PbilldRepository pbilldRepository;

	@Autowired
	private CbilldRepository cbilldRepository;

	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;

	@Value("${report-jobs-path}")
	private String reportJobPath;

	// @Autowired
	// private EntityRepository entityRepository;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(CertPrintRequestBean certPrintRequestBean) {
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		String whereCondition = "";
		String andString = "AND";
		List<Cert> certList = this.certRepository
				.findByCertPrintedonISNULLAndCertUserid(GenericAuditContextHolder.getContext().getUserid().trim());
		if (certPrintRequestBean.getIsUnprintedAuths()) {
			if (CollectionUtils.isEmpty(certList))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("There are no unprinted Certificates for printing...").build());
			else {
				String commaSepratedAuthNums = String.join(",", certList.stream()
						.map(name -> ("'" + name.getCertCK().getCertCertnum() + "'")).collect(Collectors.toList()));
				whereCondition = " a.cert_certnum in (".concat(commaSepratedAuthNums).concat(")");
			}
		} else {
			if (StringUtils.isNotBlank(certPrintRequestBean.getAuthDateFrom())
					&& StringUtils.isNotBlank(certPrintRequestBean.getAuthDateTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				// certdate is with time
				whereCondition += andString + " a.cert_certdate between '" + certPrintRequestBean.getAuthDateFrom()
						+ "' and '" + certPrintRequestBean.getAuthDateTo() + "'";
			}

			Boolean isBlank1 = StringUtils.isNotBlank(certPrintRequestBean.getAuthNumberFrom());
			Boolean isBlank2 = StringUtils.isNotBlank(certPrintRequestBean.getAuthDateTo());

			if (StringUtils.isNotBlank(certPrintRequestBean.getAuthNumberFrom())
					&& StringUtils.isNotBlank(certPrintRequestBean.getAuthNumberTo())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				else
					andString = "AND";
				whereCondition += andString + " a.cert_certnum BETWEEN '" + certPrintRequestBean.getAuthNumberFrom()
						+ "' and '" + certPrintRequestBean.getAuthNumberTo() + "'";
			}
			Boolean isBlank3 = StringUtils.isNotBlank(certPrintRequestBean.getCoycode());

			if (StringUtils.isNotBlank(certPrintRequestBean.getCoycode())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				else
					andString = "AND";
				whereCondition += andString + " a.cert_coy = '" + certPrintRequestBean.getCoycode() + "' ";
			}
			Boolean isBlank4 = StringUtils.isNotBlank(certPrintRequestBean.getPartycode());

			if (StringUtils.isNotBlank(certPrintRequestBean.getPartycode())) {
				if (StringUtils.isBlank(whereCondition))
					andString = "";
				else
					andString = "AND";
				whereCondition += andString + " a.cert_partycode = '" + certPrintRequestBean.getPartycode() + "' ";
			}
		}

		Query certQuery = this.entityManager.createNativeQuery("SELECT * FROM Cert a where " + whereCondition,
				Cert.class);

		List<Cert> certConditionBasedList = certQuery.getResultList();
		LOGGER.info("CERT QUERY :: {}", certConditionBasedList);

		if (CollectionUtils.isNotEmpty(certConditionBasedList)) {
			try {
				for (Cert cert : certConditionBasedList) {
					String certStatus = "";
					Integer noOfPrint = BigInteger.ZERO.intValue();
					String payCover = "";
					Double payAmt = cert.getCertPayamount();
					Double certAmt = cert.getCertPayamount();// -----------> for cert type c it is certpayamount but
					// negative
					String amountInWords = "";
					String address2 = "";
					String certStat = "";
					Address addressEntity = null;
					Party partyName = null;
					Double sumAdvAdj = BigInteger.ZERO.doubleValue();
					List<TempMatAuthprint> tempMatAuthprintList = new ArrayList<>();
					List<TempCertprint> tempCertprintList = new ArrayList<>();

					if (((Objects.isNull(cert.getCertPrinted()) || cert.getCertPrinted() <= 0))
							&& Integer.valueOf(cert.getCertCertstatus()) < 5)
						certStatus = PrintStatusEnum.ORIGINAL.getValue();
					else
						certStatus = PrintStatusEnum.REPRINT.getValue();
					if (cert.getCertCerttype().trim().equals("R"))
						certStatus = PrintStatusEnum.ORIGINAL.getValue();

					// if (!cert.getCertCerttype().trim().equals("R") &&
					// Integer.valueOf(cert.getCertCertstatus()) >= 5)
					// if (!cert.getCertCerttype().trim().equals("R"))
					noOfPrint = cert.getCertPrinted().intValue() + 1;

					List<Auth_D> authDList = this.authDRepository
							.findByAuthdCK_AutdAuthnum(cert.getCertCK().getCertCertnum());
					LOGGER.info("authDList size :: {}", authDList.size());
					LOGGER.info("authDList  :: {}", authDList);
					if (CollectionUtils.isNotEmpty(authDList))
						sumAdvAdj = authDList.stream().filter(f -> f.getAutdAdvadj() != null)
								.mapToDouble(Auth_D::getAutdAdvadj).sum();

					List<Certdetails> certDetailsList = this.certDetailsRepository
							.findByCertdetailsCK_CrtdCertnum(cert.getCertCK().getCertCertnum());
					LOGGER.info("certDetailsList size :: {}", certDetailsList.size());
					LOGGER.info("certDetailsList  :: {}", certDetailsList);
					if (CollectionUtils.isNotEmpty(certDetailsList))
						sumAdvAdj = certDetailsList.stream().filter(f -> f.getCrtdAdvadjusted() != null)
								.mapToDouble(Certdetails::getCrtdAdvadjusted).sum();

					if (cert.getCertCerttype().trim().equals("M") || cert.getCertCerttype().trim().equals("L"))
						sumAdvAdj += cert.getCertCertamount();

					if (cert.getCertCerttype().trim().equals("B")) {
						payCover = "RECOVER";
						payAmt = cert.getCertPayamount() * -1;
						certAmt = cert.getCertPayamount() * -1;
					} else
						payCover = "PAY";

					// amountInWords = "RUPEES
					// ".concat(CommonUtils.convert(cert.getCertrPayamount().intValue()).concat("
					// ONLY"));
					amountInWords = CurrencyConverterUtils.convertToIndianCurrency(
							cert.getCertPayamount() < 0 ? String.valueOf(cert.getCertPayamount() * -1)
									: String.valueOf(cert.getCertPayamount()));

					if (cert.getCertPayamount().intValue() == 0) {
						amountInWords = "Rupees  Zero And Paise Zero Only.";
					}
					// To convert into capital case - YP 10/04/2023
					// amountInWords = CommonUtils.INSTANCE.convertToCapitalizeCase(amountInWords);

					if (Objects.nonNull(cert.getCertPartycode())) {
						partyName = this.partyRepository
								.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
										cert.getCertPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(),
										PartyType.E.toString());
						LOGGER.info("Party Name :: {}", partyName);

						addressEntity = this.addressRepository
								.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(
										cert.getCertPartycode().trim(), AdSegment.PARTY.toString(),
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

					Building buildingName = Objects.nonNull(cert.getCertBldgcode())
							? this.buildingRepository.findByBuildingCK_BldgCode(cert.getCertBldgcode().trim())
							: null;

					Material materialName = Objects.nonNull(cert.getCertWorkcode())
							? this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(cert.getCertWorkcode(),
									"1")
							: null;

					Company companyName = Objects.nonNull(cert.getCertCoy())
							? this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
									cert.getCertCoy().trim(), CommonUtils.INSTANCE.closeDate())
							: null;

					Bldgmatbillfinal bldgmatbillfinalEntity = this.bldgmatbillfinalRepository
							.findByBldgmatbillfinalCK_BmbfBldgcodeAndBldgmatbillfinalCK_BmbfMgrcode(
									cert.getCertBldgcode().trim(), cert.getCertWorkcode().trim());
					Boolean finalDatePresent = true;
					if (Objects.nonNull(bldgmatbillfinalEntity)
							&& Objects.isNull(bldgmatbillfinalEntity.getBmbfBillfinaldate())) {
						finalDatePresent = false;
					}
					if (Objects.nonNull(cert.getCertCertdate()))
						certStat = "N";
					else {
						if (cert.getCertCertdate().compareTo(bldgmatbillfinalEntity.getBmbfBillfinaldate()) > 0
								&& finalDatePresent)
							certStat = "Y";
						else
							certStat = "N";
					}

					// String materialWorkCode =
					// this.materialRepository.findDistinctByMaterialCK_MatMatgroup(cert.getCertWorkcode().trim());
					// LOGGER.info("materialWorkCode:: {}", materialWorkCode);

					Query query = this.entityManager.createNativeQuery(
							"SELECT sum(cert_certamount - cert_advadjusted) FROM CERT WHERE CERT_BLDGCODE  = '"
									+ cert.getCertBldgcode().trim() + "' and CERT_WORKCODE = '"
									+ CommonConstraints.INSTANCE.SPACE_STRING
									+ "' and CERT_CERTSTATUS > '4' and trim(cert_certtype) not in ('A','L','M')  GROUP BY CERT_BLDGCODE,CERT_WORKCODE,CERT_PARTYCODE",
							Tuple.class);

					List<Tuple> resultSetList = query.getResultList();
					Double certAmt1 = CollectionUtils.isNotEmpty(resultSetList)
							? resultSetList.stream()
									.mapToDouble(result -> result.get(0, BigDecimal.class).doubleValue()).sum()
							: BigInteger.ZERO.doubleValue();
					String cfBldgWork = "Total for labour : " + certAmt1.intValue();
					LOGGER.info("cfBldgWork :: {}", cfBldgWork);

					Query queryForTempMatAuthPrintDetail = null;
					if (cert.getCertCerttype().trim().equals("L")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("M")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("F")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("B")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("L")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("S")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("I")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("F")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								Tuple.class);
					}
					if (cert.getCertCerttype().trim().equals("R")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								// "Select crtd_contbillno, cblh_contbilldt, cbld_quantity, crtd_billamount,
								// crtd_tdsamt, crtd_advadjusted, crtd_relretamt, cert_writeoffamt,
								// crtd_certnum, CERT_CERTTYPE, cblh_retention, cblh_retainos, cblh_ser,
								// cblh_partycode, cblh_certnum, cblh_omsserviceyn, cert_CERTNUM, cert_PARTYCODE
								// from certdetails, CBILLH, CBILLD, Cert where (crtd_contbillno =
								// cblh_contbillno) and (crtd_contract = cblh_contract) and (cbld_ser =
								// cblh_ser) and (cblh_certnum = crtd_certnum ) and (crtd_certnum =
								// cert_certNUM) AND (cert_PARTYCODE = cblh_partycode) and (cert_certNUM =
								// cblh_certnum) and (crtd_certnum = :certNum)",
								"Select crtd_contbillno,cblh_contbilldt,cbld_quantity,crtd_billamount,crtd_tdsamt,crtd_advadjusted,crtd_relretamt,cert_writeoffamt,crtd_certnum,CERT_CERTTYPE,cblh_retention,cblh_retainos,cblh_ser,cblh_partycode,cblh_certnum,cblh_omsserviceyn,cert_CERTNUM,cert_PARTYCODE FROM  Cert LEFT JOIN certdetails ON (crtd_certnum = cert_certnum) LEFT JOIN CBILLH ON (cblh_certnum = cert_certnum and cblh_contract = cert_contract and cblh_partycode = cert_PARTYCODE and cblh_contbillno = crtd_contbillno) LEFT JOIN CBILLD ON (cbld_ser = cblh_ser) WHERE cert_certnum = :certNum",
								// "Select '' as crtd_contbillno, null as cblh_contbilldt, 0.0 as cbld_quantity,
								// cert_PAYAMOUNT as autd_authamount, 0 as crtd_tdsamt, 0 as crtd_advadjusted, 0
								// as crtd_relretamt, 0 as cert_writeoffamt, '' as crtd_certnum, CERT_CERTTYPE,
								// 0 as cblh_retention, '' as cblh_retainos, '' as cblh_ser, '' as
								// cblh_partycode, '' as cblh_certnum, '' as cblh_omsserviceyn, cert_CERTNUM,
								// cert_PARTYCODE from Cert where (cert_certNUM = :certNum)",
								Tuple.class);
					}

					if (cert.getCertCerttype().trim().equals("A")) {
						queryForTempMatAuthPrintDetail = this.entityManager.createNativeQuery(
								"Select     '' as crtd_contbillno,   null as  cblh_contbilldt,     0.0 as cbld_quantity,            cert_PAYAMOUNT as autd_authamount,    0 as crtd_tdsamt,         0 as crtd_advadjusted,         0 as crtd_relretamt,     0 as cert_writeoffamt,  '' as crtd_certnum,        CERT_CERTTYPE,            0 as cblh_retention,     '' as cblh_retainos,     '' as  cblh_ser,            '' as     cblh_partycode,     '' as cblh_certnum,        '' as cblh_omsserviceyn,               cert_CERTNUM,       cert_PARTYCODE  from   Cert                                                  where (cert_certNUM = :certNum)",
								Tuple.class);
					}

					queryForTempMatAuthPrintDetail.setParameter("certNum", cert.getCertCK().getCertCertnum());
					List<Tuple> queryForTempMatAuthPrintDetailResultSetList = queryForTempMatAuthPrintDetail
							.getResultList();
					if (CollectionUtils.isNotEmpty(queryForTempMatAuthPrintDetailResultSetList)) {
						List<CertPrintDetailBean> CertPrintDetailBeanList = queryForTempMatAuthPrintDetailResultSetList
								.stream().map(t -> {
									return CertPrintDetailBean.builder()
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
						LOGGER.info("CertPrintDetailBean List Size :: {}", CertPrintDetailBeanList.size());
						LOGGER.info("CertPrintDetailBean :: {}", CertPrintDetailBeanList);

						for (CertPrintDetailBean CertPrintDetailBean : CertPrintDetailBeanList) {
							if (CertPrintDetailBean.getAuthauthnum().trim()
									.equals(cert.getCertCK().getCertCertnum().trim())) {
								tempMatAuthprintList.add(TempMatAuthprint.builder()
										.tempmatauthprintCK(TempMatAuthprintCK.builder()
												.sessid(Double.valueOf(sessionId))
												.authAuthnum(CertPrintDetailBean.getAuthauthnum())
												.autdSuppbillno(CertPrintDetailBean.getAutdsuppbillno()).build())
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
										.autdAuthnum(CertPrintDetailBean.getAutdauthnum())
										.autdAdvadj(CertPrintDetailBean.getAutdadvadj())
										.autdAuthamount(CertPrintDetailBean.getAutdauthamount())
										.autdAuthqty(CertPrintDetailBean.getAutdauthqty())
										.autdAuthtdsamt(CertPrintDetailBean.getAutdauthtdsamt())
										.autdAuthtype(CertPrintDetailBean.getAutdauthtype())
										.autdRelretamt(CertPrintDetailBean.getAutdrelretamt())
										.autdRetainamt(CertPrintDetailBean.getAutdretainamt())
										.autdRetentionadj(CertPrintDetailBean.getAutdretentionadj())
										.autdSuppbilldt(CertPrintDetailBean.getAutdsuppbilldt())
										.authAuthtype(
												Objects.nonNull(cert.getCertCerttype()) ? cert.getCertCerttype().trim()
														: null)
										.authAmt(certAmt).authAuthstatus(cert.getCertCertstatus()).authStat(certStatus)
										.bldgName(buildingName.getBldgName()).certStat(certStat).cfBldg_Work(cfBldgWork)
										.coyName(companyName.getCoyName()).Currdate(LocalDateTime.now().toLocalDate())
										.matgroupName(CommonConstraints.INSTANCE.SPACE_STRING).noOf_Print(noOfPrint)
										.partyName(partyName.getParPartyname()).payAmt(payAmt).payCover(payCover)
										.pbldCeser(CertPrintDetailBean.getPbldceser())
										.pblhRetainos(CertPrintDetailBean.getPblhretainos()).Totcertamt(certAmt)
										.totAdjvamt(sumAdvAdj).authPartycode(cert.getCertPartycode())
										.pblhSer(CertPrintDetailBean.getPblhser())
										.pblhAuthnum(CertPrintDetailBean.getPblhauthnum())
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
					if (cert.getCertCerttype().trim().equals("L") || cert.getCertCerttype().trim().equals("M")) {
						Query queryForRAndL = this.entityManager.createNativeQuery(
								"Select     crtd_contbillno,    cert_billdate,     	0,             crtd_billamount,    crtd_tdsamt,     crtd_advadjusted,              crtd_relretamt,     cert_writeoffamt ,  crtd_certnum,        CERT_CERTTYPE,            (select cblh_retention from CBILLH where CBLH_contbillno =  crtd_contbillno and (cblh_certnum = :certNum)) as cblh_retention,       cert_certNUM,                 cert_PARTYCODE,(select cblh_retainos from CBILLH where CBLH_contbillno =  crtd_contbillno and (cblh_certnum = :certNum)) as cblh_retainos        from certdetails,Cert where (crtd_certnum = cert_certNUM) and (crtd_certnum = :certNum)",
								Tuple.class);
						queryForRAndL.setParameter("certNum", cert.getCertCK().getCertCertnum());
						List<Tuple> queryForRAndLResultSetList = queryForRAndL.getResultList();

						if (CollectionUtils.isNotEmpty(queryForRAndLResultSetList)) {
							List<CertPrintDetailBean> tempMatAuthPrintDetailForRAndLBeanList = queryForRAndLResultSetList
									.stream().map(t -> {
										return CertPrintDetailBean.builder()
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
// start 1st of added by vicky for matcertlink
//	                 Workcode total amount (To print on certificate copy)					
					Query queryForTempCertprint = null;
					queryForTempCertprint = this.entityManager.createNativeQuery(

//							"SELECT Trim(mcl_code) AS WorkCode , mcl_groupcode , mcl_desc ,(SELECT ep_workname FROM epworks WHERE ep_workcode = mcl_code AND (ep_closedate is null OR (ep_closedate = '28/OCT/2015'  OR ep_closedate = '31/MAR/2016'))) AS WorkName , Nvl(sum(cert_certamount),0) AS CertAmt FROM matcertlink , cert WHERE trim(cert_bldgcode) (+) =  :StrLocBldgCode AND (cert.cert_workcode (+) = matcertlink.mcl_code) AND mcl_groupcode IN (SELECT ep_printgroup FROM epworks WHERE trim(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL) AND mcl_type = 'W' AND trim(cert_workcode) (+) <> :StrLocWorkCode AND trim(cert_coy) (+) = :StrLocCoyCode AND cert_certtype (+) NOT IN ('A', 'L', 'M') AND cert_certstatus (+) > '4' AND cert_certstatus (+) NOT IN ('6','8') GROUP BY cert_bldgcode , mcl_code , mcl_groupcode , mcl_desc",
							"SELECT TRIM(mcl_code) AS WorkCode, mcl_groupcode, mcl_desc, (SELECT ep_workname FROM epworks WHERE ep_workcode = mcl_code AND (ep_closedate IS NULL OR ep_closedate IN ('28/OCT/2015', '31/MAR/2016'))) AS WorkName, NVL(SUM(cert_certamount), 0) AS CertAmt FROM matcertlink LEFT JOIN cert ON TRIM(cert_bldgcode) = :StrLocBldgCode AND cert.cert_workcode = matcertlink.mcl_code LEFT JOIN epworks ON mcl_groupcode = ep_printgroup WHERE TRIM(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL AND mcl_type = 'W' AND TRIM(cert_workcode) <> :StrLocWorkCode AND TRIM(cert_coy) = :StrLocCoyCode AND cert_certtype NOT IN ('A', 'L', 'M') AND cert_certstatus > '4' AND cert_certstatus NOT IN ('6', '8') GROUP BY cert_bldgcode, mcl_code, mcl_groupcode, mcl_desc",
							Tuple.class);
					queryForTempCertprint.setParameter("StrLocBldgCode", cert.getCertBldgcode().trim());
					queryForTempCertprint.setParameter("StrLocWorkCode", cert.getCertWorkcode().trim());
					queryForTempCertprint.setParameter("StrLocCoyCode", cert.getCertCoy().trim());
					List<Tuple> queryForTempCertprintResultSetList = queryForTempCertprint.getResultList();
					if (CollectionUtils.isNotEmpty(queryForTempCertprintResultSetList)) {
						List<MatcertLinkPrintCertBean> MatcertLinkPrintCertBeanList = queryForTempCertprintResultSetList
								.stream().map(t -> {
									return MatcertLinkPrintCertBean.builder()
											.tmpCode(Objects.nonNull(t.get(0, String.class))
													? t.get(0, String.class).trim()
													: CommonConstraints.INSTANCE.SPACE_STRING)
											.tmpGroupcode(Objects.nonNull(t.get(1, String.class))
													? t.get(1, String.class).trim()
													: CommonConstraints.INSTANCE.SPACE_STRING)
											.tmpGroupdesc(Objects.nonNull(t.get(2, String.class))
													? t.get(2, String.class).trim()
													: CommonConstraints.INSTANCE.SPACE_STRING)
											.tmpCodedesc(Objects.nonNull(t.get(3, String.class))
													? t.get(3, String.class).trim()
													: CommonConstraints.INSTANCE.SPACE_STRING)
											.tmpTotamt(Objects.nonNull(t.get(4, BigDecimal.class))
													? t.get(4, BigDecimal.class).doubleValue()
													: null)
											.build();
								}).collect(Collectors.toList());
						LOGGER.info("MatcertLinkPrintCertBean List Size :: {}", MatcertLinkPrintCertBeanList.size());
						LOGGER.info("MatcertLinkPrintCertBean :: {}", MatcertLinkPrintCertBeanList);

						for (MatcertLinkPrintCertBean MatcertLinkPrintCertBean : MatcertLinkPrintCertBeanList) {
							if (!(MatcertLinkPrintCertBean.getTmpCode().trim().equals(cert.getCertWorkcode().trim()))) {
								tempCertprintList
										.add(TempCertprint.builder()
												.tempcertprintCK(TempCertprintCK.builder()
														.tmpCertnum(cert.getCertCK().getCertCertnum()).tmpType("W")
														.tmpCode(MatcertLinkPrintCertBean.getTmpCode())
														.tmpSessionid(Integer.valueOf(sessionId)).build())
												.tmpCodedesc(MatcertLinkPrintCertBean.getTmpCodedesc())
												.tmpGroupcode(MatcertLinkPrintCertBean.getTmpGroupcode())
												.tmpGroupdesc(MatcertLinkPrintCertBean.getTmpGroupdesc())
												.tmpTotamt(MatcertLinkPrintCertBean.getTmpTotamt().intValue())
												.tmpToday(LocalDateTime.now())
												.tmpSite(GenericAuditContextHolder.getContext().getSite())
												.tmpUserid(GenericAuditContextHolder.getContext().getUserid()).build());
								LOGGER.info("tempCertprintList :: {} ", tempCertprintList);
							}
						}

						if (CollectionUtils.isNotEmpty(tempCertprintList)) {
							this.tempCertprintRepository.saveAllAndFlush(tempCertprintList);
						} else {
							return new ResponseEntity<>(HttpStatus.NOT_FOUND);
						}
					}
// end of 1st added by vicky for matcertlink					
// start of 2nd added by vicky for matcertlink
//                Material group total amount (To print on certificate copy)					
				Query queryForTempCertprint1 = null;
				queryForTempCertprint1 = this.entityManager.createNativeQuery(

//						"SELECT Trim(mcl_code) AS MatCode , mcl_groupcode , mcl_desc ,(SELECT mat_matname FROM material WHERE Trim(mat_matgroup) = Trim(mcl_code) and mat_level = '1') AS MatName ,Nvl(sum(auth_authamount),0) AS AuthAmt FROM matcertlink , auth_h WHERE trim(auth_bldgcode) (+) =:StrLocBldgCode  AND (auth_matgroup (+) = matcertlink.mcl_code) AND trim(auth_coy) (+) = :StrLocCoyCode AND auth_authtype (+) not in ('A', 'L', 'C') AND auth_authstatus (+) > '4' AND auth_authstatus (+) NOT IN ('6','8') AND mcl_type = 'M' AND mcl_groupcode IN (SELECT mcl_groupcode FROM matcertlink WHERE mcl_groupcode IN ( SELECT ep_printgroup FROM epworks WHERE TRIM(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL) AND mcl_type = 'W' AND trim(mcl_code) = :StrLocWorkCode ) GROUP BY  mcl_code , mcl_groupcode , mcl_des",
						"SELECT TRIM(mcl_code) AS MatCode, mcl_groupcode, mcl_desc, (SELECT mat_matname FROM material WHERE TRIM(mat_matgroup) = TRIM(mcl_code) AND mat_level = '1') AS MatName, NVL(SUM(auth_authamount), 0) AS AuthAmt FROM matcertlink LEFT JOIN auth_h ON TRIM(auth_matgroup) = TRIM(matcertlink.mcl_code) WHERE TRIM(auth_bldgcode) = :StrLocBldgCode AND TRIM(auth_coy) = :StrLocCoyCode AND auth_authtype NOT IN ('A', 'L', 'C') AND auth_authstatus > '4' AND auth_authstatus NOT IN ('6', '8') AND mcl_type = 'M' AND mcl_groupcode IN (SELECT mcl_groupcode FROM matcertlink WHERE mcl_groupcode IN (SELECT ep_printgroup FROM epworks WHERE TRIM(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL) AND mcl_type = 'W' AND TRIM(mcl_code) = :StrLocWorkCode) GROUP BY mcl_code, mcl_groupcode, mcl_desc",
						Tuple.class);
				queryForTempCertprint1.setParameter("StrLocBldgCode", cert.getCertBldgcode().trim());
				queryForTempCertprint1.setParameter("StrLocWorkCode", cert.getCertWorkcode().trim());
				queryForTempCertprint1.setParameter("StrLocCoyCode", cert.getCertCoy().trim());
				List<Tuple> queryForTempCertprint1ResultSetList = queryForTempCertprint1.getResultList();
				if (CollectionUtils.isNotEmpty(queryForTempCertprint1ResultSetList)) {
					List<MatcertLinkPrintCertBean> MatcertLinkPrintCertBeanList = queryForTempCertprint1ResultSetList
							.stream().map(t -> {
								return MatcertLinkPrintCertBean.builder()
										.tmpCode(Objects.nonNull(t.get(0, String.class))
												? t.get(0, String.class).trim()
												: CommonConstraints.INSTANCE.SPACE_STRING)
										.tmpGroupcode(Objects.nonNull(t.get(1, String.class))
												? t.get(1, String.class).trim()
												: CommonConstraints.INSTANCE.SPACE_STRING)
										.tmpGroupdesc(Objects.nonNull(t.get(2, String.class))
												? t.get(2, String.class).trim()
												: CommonConstraints.INSTANCE.SPACE_STRING)
										.tmpCodedesc(Objects.nonNull(t.get(3, String.class))
												? t.get(3, String.class).trim()
												: CommonConstraints.INSTANCE.SPACE_STRING)
										.tmpTotamt(Objects.nonNull(t.get(4, BigDecimal.class))
												? t.get(4, BigDecimal.class).doubleValue()
												: null)
										.build();
							}).collect(Collectors.toList());
					LOGGER.info("MatcertLinkPrintCertBean List Size :: {}", MatcertLinkPrintCertBeanList.size());
					LOGGER.info("MatcertLinkPrintCertBean :: {}", MatcertLinkPrintCertBeanList);

					for (MatcertLinkPrintCertBean MatcertLinkPrintCertBean : MatcertLinkPrintCertBeanList) {
							tempCertprintList
									.add(TempCertprint.builder()
											.tempcertprintCK(TempCertprintCK.builder()
													.tmpCertnum(cert.getCertCK().getCertCertnum()).tmpType("W")
													.tmpCode(MatcertLinkPrintCertBean.getTmpCode())
													.tmpSessionid(Integer.valueOf(sessionId)).build())
											.tmpCodedesc(MatcertLinkPrintCertBean.getTmpCodedesc())
											.tmpGroupcode(MatcertLinkPrintCertBean.getTmpGroupcode())
											.tmpGroupdesc(MatcertLinkPrintCertBean.getTmpGroupdesc())
											.tmpTotamt(MatcertLinkPrintCertBean.getTmpTotamt().intValue())
											.tmpToday(LocalDateTime.now())
											.tmpSite(GenericAuditContextHolder.getContext().getSite())
											.tmpUserid(GenericAuditContextHolder.getContext().getUserid()).build());
							LOGGER.info("tempCertprintList :: {} ", tempCertprintList);
					}

					if (CollectionUtils.isNotEmpty(tempCertprintList)) {
						this.tempCertprintRepository.saveAllAndFlush(tempCertprintList);
					} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					}
				}
//end of 2nd added by vicky for matcertlink					
//// start of 3rd added by vicky for workmatlink
////              Material group total amount (To print on certificate copy)					
//				Query queryForTempCertprint1 = null;
//				queryForTempCertprint1 = this.entityManager.createNativeQuery(
//
////						"SELECT Trim(mcl_code) AS MatCode , mcl_groupcode , mcl_desc ,(SELECT mat_matname FROM material WHERE Trim(mat_matgroup) = Trim(mcl_code) and mat_level = '1') AS MatName ,Nvl(sum(auth_authamount),0) AS AuthAmt FROM matcertlink , auth_h WHERE trim(auth_bldgcode) (+) =:StrLocBldgCode  AND (auth_matgroup (+) = matcertlink.mcl_code) AND trim(auth_coy) (+) = :StrLocCoyCode AND auth_authtype (+) not in ('A', 'L', 'C') AND auth_authstatus (+) > '4' AND auth_authstatus (+) NOT IN ('6','8') AND mcl_type = 'M' AND mcl_groupcode IN (SELECT mcl_groupcode FROM matcertlink WHERE mcl_groupcode IN ( SELECT ep_printgroup FROM epworks WHERE TRIM(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL) AND mcl_type = 'W' AND trim(mcl_code) = :StrLocWorkCode ) GROUP BY  mcl_code , mcl_groupcode , mcl_des",
//						"SELECT TRIM(mcl_code) AS MatCode, mcl_groupcode, mcl_desc, (SELECT mat_matname FROM material WHERE TRIM(mat_matgroup) = TRIM(mcl_code) AND mat_level = '1') AS MatName, NVL(SUM(auth_authamount), 0) AS AuthAmt FROM matcertlink LEFT JOIN auth_h ON TRIM(auth_matgroup) = TRIM(matcertlink.mcl_code) WHERE TRIM(auth_bldgcode) = :StrLocBldgCode AND TRIM(auth_coy) = :StrLocCoyCode AND auth_authtype NOT IN ('A', 'L', 'C') AND auth_authstatus > '4' AND auth_authstatus NOT IN ('6', '8') AND mcl_type = 'M' AND mcl_groupcode IN (SELECT mcl_groupcode FROM matcertlink WHERE mcl_groupcode IN (SELECT ep_printgroup FROM epworks WHERE TRIM(ep_workcode) = :StrLocWorkCode AND ep_closedate IS NULL) AND mcl_type = 'W' AND TRIM(mcl_code) = :StrLocWorkCode) GROUP BY mcl_code, mcl_groupcode, mcl_desc",
//						Tuple.class);
//				queryForTempCertprint1.setParameter("StrLocBldgCode", cert.getCertBldgcode().trim());
//				queryForTempCertprint1.setParameter("StrLocWorkCode", cert.getCertWorkcode().trim());
//				queryForTempCertprint1.setParameter("StrLocCoyCode", cert.getCertCoy().trim());
//				List<Tuple> queryForTempCertprint1ResultSetList = queryForTempCertprint1.getResultList();
//				if (CollectionUtils.isNotEmpty(queryForTempCertprint1ResultSetList)) {
//					List<MatcertLinkPrintCertBean> MatcertLinkPrintCertBeanList = queryForTempCertprint1ResultSetList
//							.stream().map(t -> {
//								return MatcertLinkPrintCertBean.builder()
//										.tmpCode(Objects.nonNull(t.get(0, String.class))
//												? t.get(0, String.class).trim()
//												: CommonConstraints.INSTANCE.SPACE_STRING)
//										.tmpGroupcode(Objects.nonNull(t.get(1, String.class))
//												? t.get(1, String.class).trim()
//												: CommonConstraints.INSTANCE.SPACE_STRING)
//										.tmpGroupdesc(Objects.nonNull(t.get(2, String.class))
//												? t.get(2, String.class).trim()
//												: CommonConstraints.INSTANCE.SPACE_STRING)
//										.tmpCodedesc(Objects.nonNull(t.get(3, String.class))
//												? t.get(3, String.class).trim()
//												: CommonConstraints.INSTANCE.SPACE_STRING)
//										.tmpTotamt(Objects.nonNull(t.get(4, BigDecimal.class))
//												? t.get(4, BigDecimal.class).doubleValue()
//												: null)
//										.build();
//							}).collect(Collectors.toList());
//					LOGGER.info("MatcertLinkPrintCertBean List Size :: {}", MatcertLinkPrintCertBeanList.size());
//					LOGGER.info("MatcertLinkPrintCertBean :: {}", MatcertLinkPrintCertBeanList);
//
//					for (MatcertLinkPrintCertBean MatcertLinkPrintCertBean : MatcertLinkPrintCertBeanList) {
//							tempCertprintList
//									.add(TempCertprint.builder()
//											.tempcertprintCK(TempCertprintCK.builder()
//													.tmpCertnum(cert.getCertCK().getCertCertnum()).tmpType("W")
//													.tmpCode(MatcertLinkPrintCertBean.getTmpCode())
//													.tmpSessionid(Integer.valueOf(sessionId)).build())
//											.tmpCodedesc(MatcertLinkPrintCertBean.getTmpCodedesc())
//											.tmpGroupcode(MatcertLinkPrintCertBean.getTmpGroupcode())
//											.tmpGroupdesc(MatcertLinkPrintCertBean.getTmpGroupdesc())
//											.tmpTotamt(MatcertLinkPrintCertBean.getTmpTotamt().intValue())
//											.tmpToday(LocalDateTime.now())
//											.tmpSite(GenericAuditContextHolder.getContext().getSite())
//											.tmpUserid(GenericAuditContextHolder.getContext().getUserid()).build());
//							LOGGER.info("tempCertprintList :: {} ", tempCertprintList);
//					}
//
//					if (CollectionUtils.isNotEmpty(tempCertprintList)) {
//						this.tempCertprintRepository.saveAllAndFlush(tempCertprintList);
//					} else {
//						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//					}
//				}
////end of 3rd added by vicky for workmatlink					

				}

				// List<Dbnoteh> dbnotehEntityList = this.dbnotehRepository
				// .findByDbnhAuthnoIn(certConditionBasedList.stream()
				// .sorted((p1, p2) -> p1.getCertCK().getCertCertnum()
				// .compareTo(p2.getCertCK().getCertCertnum()))
				// .map(name -> name.getCertCK().getCertCertnum()).collect(Collectors.toSet()));
				// LOGGER.info("dbnotehEntity :: {}", dbnotehEntityList);

				List<Cdbnoteh> cdbnotehEntityList = this.cdbnotehRepository.findByCdbnhCertnoIn(certConditionBasedList
						.stream()
						.sorted((p1, p2) -> p1.getCertCK().getCertCertnum().compareTo(p2.getCertCK().getCertCertnum()))
						.map(name -> name.getCertCK().getCertCertnum()).collect(Collectors.toSet()));
				LOGGER.info("cdbnotehEntity :: {}", cdbnotehEntityList);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
						.data(CertPrintDetailResponseBean.builder().sessionId(sessionId)
								.authNumberFrom(certConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getCertCK().getCertCertnum()
												.compareTo(p2.getCertCK().getCertCertnum()))
										.map(name -> name.getCertCK().getCertCertnum()).findFirst().get())
								.authNumberTo(
										certConditionBasedList.stream()
												.sorted((p1, p2) -> p2.getCertCK().getCertCertnum()
														.compareTo(p1.getCertCK().getCertCertnum()))
												.map(name -> name.getCertCK().getCertCertnum()).findFirst().get())
								.serList(CollectionUtils.isNotEmpty(cdbnotehEntityList) ? cdbnotehEntityList.stream()
										.sorted((p1, p2) -> p2.getCdbnotehCK().getCdbnhDbnoteser()
												.compareTo(p1.getCdbnotehCK().getCdbnhDbnoteser()))
										.collect(Collectors.toMap(db -> db.getCdbnotehCK().getCdbnhDbnoteser(),
												Cdbnoteh::getCdbnhCertno))
										: null)
								.authNumList(certConditionBasedList.stream()
										.sorted((p1, p2) -> p1.getCertCK().getCertCertnum()
												.compareTo(p2.getCertCK().getCertCertnum()))
										.map(name -> name.getCertCK().getCertCertnum()).collect(Collectors.toList()))
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
	public ResponseEntity<?> mergePdf(CertPrintDetailResponseBean certPrintDetailResponseBean) {
		List<String> finalReportLocationList = new ArrayList<>();
		File file = null;

		try {
			for (String certNum : certPrintDetailResponseBean.getAuthNumList()) {

				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sessid", certPrintDetailResponseBean.getSessionId());
					map.put("authFrom", certNum);
					map.put("authTo", certNum);

					// Make the Feign client request
					byte[] ogByteArray = this.reportInternalFeignClient.generateReportWithMultipleConditionAndParameter(
							ReportMasterRequestBean.builder().name("Engg_RP_CertificatePrint_java.rpt")
									.reportParameters(map).seqId(1).isPrint(false).build());

					// byte[] duplicateByteArray = this.reportInternalFeignClient
					// .generateReportWithMultipleConditionAndParameter(
					// ReportMasterRequestBean.builder().name("Engg_RP_CertificatePrint_java_copy.rpt")
					// .reportParameters(map).seqId(1).isPrint(false).build());

					// GENERATE REPORT OG AND DUPLICATE
					String ogRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(certNum.concat("OG"));
					String ogReportLocation = reportJobPath.concat(ogRandomUUID).concat(".pdf");
					String duplicateRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(certNum.concat("DUPLICATE"));
					// String duplicateReportLocation =
					// reportJobPath.concat(duplicateRandomUUID).concat(".pdf");

					try (FileOutputStream fos = new FileOutputStream(ogReportLocation)) {
						fos.write(ogByteArray);
						finalReportLocationList.add(ogReportLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}

					// try (FileOutputStream fos = new FileOutputStream(duplicateReportLocation)) {
					// fos.write(duplicateByteArray);
					// finalReportLocationList.add(duplicateReportLocation);
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					if (certPrintDetailResponseBean.getSerList() != null
							&& !certPrintDetailResponseBean.getSerList().isEmpty()) {
						for (Map.Entry<String, String> entry : certPrintDetailResponseBean.getSerList().entrySet()) {
							if (certNum.equals(entry.getValue())) {
								Map<String, Object> cdebitNoteParamMap = new HashMap<String, Object>();
								// debitNoteParamMap.put("sessid",certPrintDetailResponseBean.getSessionId());
								cdebitNoteParamMap.put("serFrom", entry.getKey());
								cdebitNoteParamMap.put("serTo", entry.getKey());

								// byte[] debitNoteByteArray = this.reportInternalFeignClient
								// .generateReportWithMultipleConditionAndParameter(ReportMasterRequestBean
								// .builder().name("Engg_RP_ContractDebitNotePrint.rpt")
								// .reportParameters(cdebitNoteParamMap).seqId(1).isPrint(false).build());

								byte[] cdebitNoteByteArray = this.reportInternalFeignClient
										.generateReportWithMultipleConditionAndParameter(ReportMasterRequestBean
												.builder().name("Engg_RP_ContractDebitNotePrint.rpt")
												.reportParameters(cdebitNoteParamMap).seqId(1).isPrint(false).build());
								for (int i = 0; i < 1; i++) {
									String cdebitNoteRandomUUID = CommonUtils.INSTANCE
											.uniqueIdentifier(certNum.concat("DebitNote"));
									String cdebitNoteReportLocation = reportJobPath.concat(cdebitNoteRandomUUID)
											.concat(".pdf");

									try (FileOutputStream debitNoteFos = new FileOutputStream(
											cdebitNoteReportLocation)) {
										debitNoteFos.write(cdebitNoteByteArray);
										finalReportLocationList.add(cdebitNoteReportLocation);
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
						.header(HttpHeaders.CONTENT_DISPOSITION,
								CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING
										.concat(certPrintDetailResponseBean.getAuthNumberFrom().concat("-")
												.concat(certPrintDetailResponseBean.getAuthNumberTo()).concat(".pdf")))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.body(new InputStreamResource(new FileInputStream(file)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@Override
	public ResponseEntity<?> updateCertPrintStatus(
			CertPrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean) {
		List<TempMatAuthprint> tempMatAuthprintEntityList = this.tempMatAuthprintRepository
				.findByTempmatauthprintCK_Sessid(Double.valueOf(printStatusUpdateDetailRequestBean.getSessionId()));
		LOGGER.info("tempMatAuthprintEntityList :: {}", tempMatAuthprintEntityList);
		List<Cdbnoteh> cdbnotehEntityList = null;

		if (CollectionUtils.isNotEmpty(tempMatAuthprintEntityList)) {
			List<Cert> certEntityList = this.certRepository
					.findByCertCK_CertCertnumIn(tempMatAuthprintEntityList.stream().map(tempMatAuthprintEntity -> {
						return tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum();
					}).collect(Collectors.toList()));
			LOGGER.info("certEntityList :: {}", certEntityList);

			if (Objects.nonNull(printStatusUpdateDetailRequestBean.getSerList())) {
				cdbnotehEntityList = this.cdbnotehRepository
						.findByCdbnhCertnoIn(printStatusUpdateDetailRequestBean.getSerList());
				LOGGER.info("cdbnotehEntityList :: {}", cdbnotehEntityList);
			}
			if (CollectionUtils.isNotEmpty(certEntityList)) {
				for (TempMatAuthprint tempMatAuthprintEntity : tempMatAuthprintEntityList) {
					for (Cert certEntity : certEntityList) {
						if (tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum().trim()
								.equals(certEntity.getCertCK().getCertCertnum().trim())) {
							certEntity.setCertPrinted(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
									? Integer.valueOf(tempMatAuthprintEntity.getNoOf_Print())
									: null);
							certEntity.setCertPrintedon(LocalDate.now());
							certEntity.setCertToday(LocalDateTime.now());
							certEntity.setCertUserid(GenericAuditContextHolder.getContext().getUserid());
							certEntity.setCertSite(GenericAuditContextHolder.getContext().getSite());
							certEntity.setCertCertstatus(certEntity.getCertCertstatus().trim().equals("1") ? "2"
									: certEntity.getCertCertstatus());
							this.certRepository.save(certEntity);
						}
					}
					if (CollectionUtils.isNotEmpty(cdbnotehEntityList)) {
						for (Cdbnoteh cdbnotehEntity : cdbnotehEntityList) {
							if (tempMatAuthprintEntity.getTempmatauthprintCK().getAutdSuppbillno().trim()
									.equals(cdbnotehEntity.getCdbnhContbillno().trim())) {
								cdbnotehEntity.setCdbnhNoofprint(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
										? Integer.valueOf(tempMatAuthprintEntity.getNoOf_Print())
										: null);
								cdbnotehEntity.setCdbnhPrints(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
										? Integer.valueOf(tempMatAuthprintEntity.getNoOf_Print())
										: null);
								cdbnotehEntity.setCdbnhPrintdate(LocalDate.now());
								cdbnotehEntity.setCdbnhSite(GenericAuditContextHolder.getContext().getSite());
								cdbnotehEntity.setCdbnhUserid(GenericAuditContextHolder.getContext().getUserid());
								cdbnotehEntity.setCdbnhPrintuser(GenericAuditContextHolder.getContext().getUserid());
								cdbnotehEntity.setCdbnhToday(LocalDateTime.now());
								this.cdbnotehRepository.save(cdbnotehEntity);
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
		List<TempCertprint> tempCertprintEntityList = this.tempCertprintRepository
				.findByTempcertprintCK_TmpSessionid(Integer.valueOf(sessionId));
		LOGGER.info("tempCertprintEntityList :: {}", tempCertprintEntityList);

		if (CollectionUtils.isNotEmpty(tempCertprintEntityList)) {
			this.tempCertprintRepository.deleteByTmpSessionid(Integer.valueOf(sessionId));
		}
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
