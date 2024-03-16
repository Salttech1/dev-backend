package kraheja.adminexp.billing.dataentry.adminBill.service.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.repository.AdmadvanceRepository1;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.AdmbilldRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.AdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchAdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchPartyAlreadyExistsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.TdsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.PartyIsLegalOrSecurityResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.HsnResponse;
import kraheja.adminexp.billing.dataentry.adminBill.mappers.AdmbilldEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.adminBill.mappers.AdmbillhEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbilldRepository;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbillhRepository;
import kraheja.adminexp.billing.dataentry.adminBill.repository.ReferRepository;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdmbillEntryService;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Detnarr;
import kraheja.commons.entity.DetnarrCK;
import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.DetnarrRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.commons.repository.HsnsacmasterRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class AdmbillServiceImpl implements AdmbillEntryService {

	private final AdmbillhRepository admbillhRepository;
	private final AdmadvanceRepository1 admadvanceRepository;
	private final GenericAccountingLogic genericAccountingLogic;
	private final AdmbilldRepository admbilldRepository;
	private final AddressRepository addressRepository;
	private final PartyRepository partyRepository;
	private final ActranhRepository actranhRepository;
	private final ActrandRepository actrandRepository;
	private final DetnarrRepository detnarrRepository;
	private final BuildingRepository buildingRepository;
	private final EntityRepository entityRepository;
	private final CompanyRepository companyRepository;
	private final GlchartRepository glchartRepository;
	private final HsnsacmasterRepository hsnsacmasterRepository;
	private final ReferRepository referRepository;

	public AdmbillServiceImpl(AdmbillhRepository admbillhRepository, AdmadvanceRepository1 admadvanceRepository,
			GenericAccountingLogic genericAccountingLogic, AdmbilldRepository admbilldRepository,
			AddressRepository addressRepository, PartyRepository partyRepository, ActranhRepository actranhRepository,
			ActrandRepository actrandRepository, DetnarrRepository detnarrRepository,
			BuildingRepository buildingRepository, EntityRepository entityRepository,
			CompanyRepository companyRepository, GlchartRepository glchartRepository,
			HsnsacmasterRepository hsnsacmasterRepository, ReferRepository referRepository) {
		this.admbillhRepository = admbillhRepository;
		this.admadvanceRepository = admadvanceRepository;
		this.genericAccountingLogic = genericAccountingLogic;
		this.admbilldRepository = admbilldRepository;
		this.addressRepository = addressRepository;
		this.partyRepository = partyRepository;
		this.actranhRepository = actranhRepository;
		this.actrandRepository = actrandRepository;
		this.detnarrRepository = detnarrRepository;
		this.buildingRepository = buildingRepository;
		this.entityRepository = entityRepository;
		this.companyRepository = companyRepository;
		this.glchartRepository = glchartRepository;
		this.hsnsacmasterRepository = hsnsacmasterRepository;
		this.referRepository = referRepository;

	}

	@Override
	public GenericResponse<AdminBillResponseBean> fetchAdmbillhBySer(String ser) {
		Admbillh admbillhEntity = this.admbillhRepository.findByAdmbillhCK_AdblhSer(ser);
		log.info("AdmbillhEntity :: {}", admbillhEntity);
		if (admbillhEntity != null) {
			Party partyEntity = this.partyRepository
					.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
							admbillhEntity.getAdblhPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(),
							admbillhEntity.getAdblhPartytype().trim());

			Address addressEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							admbillhEntity.getAdblhPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment,
							CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);

			String stateName = "";
			String stateCode = "";

			if (Objects.nonNull(addressEntity)) {
				if (Objects.isNull(addressEntity.getAdrState())) {
					stateName = " ";

				} else {
					stateName = entityRepository.fetchStateNameByEntClassAndEntId(addressEntity.getAdrState());
				}

				if (Objects.isNull(addressEntity.getAdrState())) {
					stateCode = " ";
				} else {
					stateCode = addressEntity.getAdrState();

				}
			}

			log.info("Address :: {}", addressEntity);

			Building building = buildingRepository.findByBuildingCK_BldgCode(admbillhEntity.getAdblhBldgcode().trim());

			List<Admbilld> admbilldEntityList = this.admbilldRepository.findByAdmbilldCK_AdbldSer(ser.trim());
			log.info("AdmbilldEntity :: {}", admbilldEntityList);

			double totPaidAdvn = this.admadvanceRepository
					.findAdvnAmountSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhstatusAndAdblhtype(
							admbillhEntity.getAdblhPartycode().trim(), admbillhEntity.getAdblhBldgcode().trim(),
							admbillhEntity.getAdblhCoy().trim(), "Z");
			log.info("totPaidAdvn", totPaidAdvn);

			Double adjustedAdvn = this.admbillhRepository
					.findAdblhAdjustSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhtypeAndadblhadvanceAdjust(
							admbillhEntity.getAdblhPartycode().trim(), admbillhEntity.getAdblhBldgcode().trim(),
							admbillhEntity.getAdblhCoy().trim(), "Z");
			log.info("DecLocAdjustedAdvn", adjustedAdvn);
			log.info("stateName", stateName);

			List<Actrand> actrand = actrandRepository.findByActdTranser(ser);

			AdminBillResponseBean adminBillResponseBean = AdminBillResponseBean.builder().admbilld(admbilldEntityList)
					.admbillh(admbillhEntity).gstNo(partyEntity.getParGstno()).stateCode(stateCode).stateName(stateName)
					.totPaidAdvance(totPaidAdvn).adjustedAdvn(adjustedAdvn)
					.docParCode(actrand.get(0).getActdDocparcode()).docParType(actrand.get(0).getActdDocpartype())
					.refPartyDesc(partyEntity.getParPartyname()).bldgDesc(building.getBldgName()).build();
			log.info("adminBillResponseBean", adminBillResponseBean);

			return new GenericResponse<>(true, "Admin Bill Retrieved Successfully", adminBillResponseBean);
		}
		return new GenericResponse<>(false, "No record found .");
	}

	@Override
	public GenericResponse<Map<String, Object>> fetchAdminBillDetails(
			FetchAdminBillRequestBean fetchAdminBillRequestBean) {
		Admbillh admbillh = admbillhRepository.findByAdblhSuppBillNo(fetchAdminBillRequestBean.getSuppBillNo().trim());
		if (Objects.isNull(admbillh)) {
			Party partyEntity = this.partyRepository
					.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
							fetchAdminBillRequestBean.getPartyCode().trim(),
							CommonUtils.INSTANCE.closeDateInLocalDateTime(),
							fetchAdminBillRequestBean.getPartyType().trim());

			Address addressEntity = this.addressRepository
					.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
							fetchAdminBillRequestBean.getPartyCode().trim(), CommonConstraints.INSTANCE.adrAdsegment,
							CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
			log.info("Address :: {}", addressEntity);

			double totPaidAdvn = this.admadvanceRepository
					.findAdvnAmountSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhstatusAndAdblhtype(
							fetchAdminBillRequestBean.getPartyCode().trim(),
							fetchAdminBillRequestBean.getBuildingCode().trim(),
							fetchAdminBillRequestBean.getCompanyCode().trim(), "Z");
			log.info("totPaidAdvn", totPaidAdvn);

			Double adjustedAdvn = this.admbillhRepository
					.findAdblhAdjustSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhtypeAndadblhadvanceAdjust(
							fetchAdminBillRequestBean.getPartyCode().trim(),
							fetchAdminBillRequestBean.getBuildingCode().trim(),
							fetchAdminBillRequestBean.getCompanyCode().trim(), "Z");
			log.info("DecLocAdjustedAdvn", adjustedAdvn);

			String stateName = "";
			String stateCode = "";

			if (Objects.nonNull(addressEntity)) {
				if (Objects.isNull(addressEntity.getAdrState())) {
					stateName = " ";

				} else {
					stateName = entityRepository.fetchStateNameByEntClassAndEntId(addressEntity.getAdrState());
				}

				if (Objects.isNull(addressEntity.getAdrState())) {
					stateCode = " ";
				} else {
					stateCode = addressEntity.getAdrState();

				}
			}

			log.info("stateName", stateName);

			Boolean isPartyLimited = false;
			String partyName = partyEntity.getParPartyname().trim();

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					fetchAdminBillRequestBean.getCompanyCode().trim(), CommonUtils.INSTANCE.closeDate());

			String companyGstNo = Objects.isNull(companyEntity) ? ""
					: Objects.isNull(companyEntity.getCoyGstno()) ? "" : companyEntity.getCoyGstno();

			if (partyName.toUpperCase().contains("LTD") || partyName.toUpperCase().contains("LIMITED")) {
				isPartyLimited = true;
			}
			Map<String, Object> keyValueMap = new HashMap<>();
			keyValueMap.put("partyGstNumber", Objects.isNull(partyEntity) ? " "
					: Objects.isNull(partyEntity.getParGstno()) ? " " : partyEntity.getParGstno());
			keyValueMap.put("stateName", stateName);
			keyValueMap.put("stateCode", stateCode);
			keyValueMap.put("totPaidAdvance", totPaidAdvn);
			keyValueMap.put("adjustedAdvn", adjustedAdvn);
			keyValueMap.put("isPartyLimited", isPartyLimited);
			keyValueMap.put("companyGstNo", companyGstNo);

			return new GenericResponse<>(true, "Data retreived successfully", keyValueMap);
		}

		return new GenericResponse<>(false, "Invoice Number already exists.");

	}

	@Override
	public GenericResponse<PartyIsLegalOrSecurityResponseBean> fetchPartyIsLegalOrSecurity(String acMajor) {

		try {
			long legalAcMajorCount = entityRepository.countByGSTRCConditions(acMajor);
			long securityAcMajorCount = entityRepository.countByEntClassAndEntChar1(acMajor);
			String validMinors = glchartRepository.findValidMinorByChartAcnum(acMajor);
			String expClass = referRepository.findByReferCK_RefReftypeAndReferCK_RefRefcodeAndRefAcmajor(acMajor);

			PartyIsLegalOrSecurityResponseBean partyIsLegalOrSecurityResponseBean = PartyIsLegalOrSecurityResponseBean
					.builder().isLegal(legalAcMajorCount > 0 ? true : false)
					.isSecurity(securityAcMajorCount > 0 ? true : false)
					.isDisabled(validMinors == null || validMinors.isEmpty()).expClass(expClass).build();

			return new GenericResponse<>(true, "Data retreived successfully .", partyIsLegalOrSecurityResponseBean);

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Data retreival falied .");
		}
	}

	@Override
	public GenericResponse<HsnResponse> fetchHsnData(String hsnCode, String suppBillDate, String partyCode,
			String stateCode, String buildingCode) {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");

			Hsnsacmaster hsnsacmaster = hsnsacmasterRepository.gstRate(hsnCode.trim(),
					LocalDate.parse(suppBillDate, formatter));

			if (Objects.isNull(hsnsacmaster)) {
				return new GenericResponse<>(false, "No Records found for selected hsnCode.");
			}

			String country = this.addressRepository.fetchCountryDetails(partyCode.trim());
			String state = this.addressRepository.fetchStateDetails(buildingCode.trim());
			String entityResult = this.entityRepository.fetchStateNameByEntClassAndEntId(stateCode.trim());

			if (Objects.nonNull(country) && !(country.equalsIgnoreCase("INDIA"))) {
				HsnResponse hsnResponse = HsnResponse.builder().hsnCode(hsnCode).hsnDesc(hsnsacmaster.getHsmsDesc())
						.cgstPerc(0d).sgstPerc(0d).igstPerc(hsnsacmaster.getHsmsIgstperc()).ugstPerc(0d).build();
				return new GenericResponse<>(true, "Data retreived successfully .", hsnResponse);
			} else if (Objects.nonNull(state) && state.trim().equalsIgnoreCase(stateCode.trim())) {
				if (Objects.nonNull(entityResult) && entityResult.trim().equalsIgnoreCase("U")) {
					HsnResponse hsnResponse = HsnResponse.builder().hsnCode(hsnCode).hsnDesc(hsnsacmaster.getHsmsDesc())
							.cgstPerc(hsnsacmaster.getHsmsCgstperc()).sgstPerc(0d).igstPerc(0d)
							.ugstPerc(hsnsacmaster.getHsmsUgstperc()).build();
					return new GenericResponse<>(true, "Data retreived successfully .", hsnResponse);
				} else {
					HsnResponse hsnResponse = HsnResponse.builder().hsnCode(hsnCode).hsnDesc(hsnsacmaster.getHsmsDesc())
							.cgstPerc(hsnsacmaster.getHsmsCgstperc()).sgstPerc(hsnsacmaster.getHsmsSgstperc())
							.igstPerc(0d).ugstPerc(0d).build();
					return new GenericResponse<>(true, "Data retreived successfully .", hsnResponse);
				}
			} else {
				HsnResponse hsnResponse = HsnResponse.builder().hsnCode(hsnCode).hsnDesc(hsnsacmaster.getHsmsDesc())
						.cgstPerc(0d).sgstPerc(0d).igstPerc(hsnsacmaster.getHsmsIgstperc()).ugstPerc(0d).build();
				return new GenericResponse<>(true, "Data retreived successfully .", hsnResponse);
			}

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Data retreival falied .");
		}
	}

	@SuppressWarnings("unused")
	@Override
	public GenericResponse<String> addAdminbill(AdminBillRequestBean adminBillRequestBean) throws ParseException {

		try {
			String ser = "";
			String tranDate = "";
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			Building building = buildingRepository
					.findByBuildingCK_BldgCode(adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim());

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(), CommonUtils.INSTANCE.closeDate());

			Double tranAmount = 0d;

			if (adminBillRequestBean.getIsUpdate()) {
				Admbillh oldAdminBillh = admbillhRepository
						.findByAdmbillhCK_AdblhSer(adminBillRequestBean.getAdmbillhRequestBean().getSer());
				log.info("Admbillh :: {}", oldAdminBillh);
				if (Objects.nonNull(oldAdminBillh)) {

					admbilldRepository
							.deleteByAdmbilldCK_AdbldSerNum(adminBillRequestBean.getAdmbillhRequestBean().getSer());

					Admbillh admbillh = AdmbillhEntityPojoMapper.updateAdmbillhEntityPojoMapper.apply(oldAdminBillh,
							adminBillRequestBean.getAdmbillhRequestBean());

					List<Admbilld> newAdmbilld = AdmbilldEntityPojoMapper
							.addAdmbilldEntityPojoMapper(adminBillRequestBean.getAdmbillhRequestBean().getSer())
							.apply(adminBillRequestBean.getAdmbilldRequestBean());

					ser = adminBillRequestBean.getAdmbillhRequestBean().getSer();
					admbillhRepository.save(admbillh);
					admbilldRepository.saveAll(newAdmbilld);
					detnarrRepository.deleteDetnarr(ser);
					List<Actrand> actrandList = actrandRepository
							.findActdByTranserNo(adminBillRequestBean.getAdmbillhRequestBean().getSer());
					tranDate = actrandList.get(0).getActdTrandate()
							.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
					actrandRepository.deleteActrand(adminBillRequestBean.getAdmbillhRequestBean().getSer());
					actranhRepository.deleteActranh(adminBillRequestBean.getAdmbillhRequestBean().getSer());
				}
			} else {
				ser = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#BO");
				tranDate = admbillhRepository.findGstTranDate(adminBillRequestBean.getAdmbillhRequestBean()
						.getSuppbilldt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				Admbillh admbillh = AdmbillhEntityPojoMapper.addAdmbillhEntityPojoMapper(ser)
						.apply(adminBillRequestBean.getAdmbillhRequestBean());
				List<Admbilld> admbilld = AdmbilldEntityPojoMapper.addAdmbilldEntityPojoMapper(ser)
						.apply(adminBillRequestBean.getAdmbilldRequestBean());
				admbillhRepository.save(admbillh);
				admbilldRepository.saveAll(admbilld);
			}

			Integer bunumCounter = 1;

			List<ActrandBean> adminBillAmountBreakup = new ArrayList<>();
			List<ActrandBean> cgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> sgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> igstAmountBreakup = new ArrayList<>();
			List<ActrandBean> ugstAmountBreakup = new ArrayList<>();
			List<ActrandBean> tdsAmountBreakup = new ArrayList<>();

			Double cgstTotal = adminBillRequestBean.getAdmbilldRequestBean().stream()
					.mapToDouble(AdmbilldRequestBean::getCgstamt).sum();
			Double sgstTotal = adminBillRequestBean.getAdmbilldRequestBean().stream()
					.mapToDouble(AdmbilldRequestBean::getSgstamt).sum();
			Double igstTotal = adminBillRequestBean.getAdmbilldRequestBean().stream()
					.mapToDouble(AdmbilldRequestBean::getIgstamt).sum();
			Double ugstTotal = adminBillRequestBean.getAdmbilldRequestBean().stream()
					.mapToDouble(AdmbilldRequestBean::getUgstamt).sum();

			Double gstTotal = cgstTotal + sgstTotal + igstTotal + ugstTotal;

			if (adminBillRequestBean.getGstNo().trim() != ""
					& adminBillRequestBean.getAdmbillhRequestBean().getGstrevchgyn() != "Y") {
				tranAmount = adminBillRequestBean.getAdmbillhRequestBean().getBillamount() - gstTotal;
			} else {
				tranAmount = adminBillRequestBean.getAdmbillhRequestBean().getBillamount();
			}

			List<Detnarr> detNarrList = new ArrayList<>();

			adminBillAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11401026",
					adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
					adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
					adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
					adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
					adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
					adminBillRequestBean.getAdmbillhRequestBean().getAcmajor(),
					adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
					adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
					adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
					adminBillRequestBean.getAdmbillhRequestBean().getProject(),
					adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, tranAmount,
					adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
					building.getBldgProperty(), "", tranDate, bunumCounter,
					adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
					companyEntity.getCompanyCK().getCoyProp(),
					adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
							.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
					"",
					Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
							? adminBillRequestBean.getAdmbillhRequestBean().getFromdate().format(
									CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: "",
					Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate()) ? adminBillRequestBean
							.getAdmbillhRequestBean().getTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: "",
					"", "O", "", "",
					Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
							? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
							: 0d,
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
							.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
					"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

			Detnarr billAmountDetnarr1 = Detnarr.builder()
					.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
							.detTranser(ser).detBunum(bunumCounter).build())
					.detDettype("E")
					.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
							: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
					.detSite(GenericAuditContextHolder.getContext().getSite())
					.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
					.build();

			Detnarr billAmountDetnarr2 = Detnarr.builder()
					.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
							.detTranser(ser).detBunum(bunumCounter + 1).build())
					.detDettype("E")
					.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
							: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
					.detSite(GenericAuditContextHolder.getContext().getSite())
					.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
					.build();

			detNarrList.add(billAmountDetnarr1);
			detNarrList.add(billAmountDetnarr2);

			bunumCounter += 2;

			log.info("debitAmountBreakup : {}", adminBillAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			if (Objects.isNull(adminBillRequestBean.getGstNo())) {
				if (cgstTotal > 0 && sgstTotal > 0) {
					cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11402441",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404391",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, cgstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr cgstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr cgstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(cgstDetnarr1);
					detNarrList.add(cgstDetnarr2);

					bunumCounter += 2;

					log.info("Non-Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

					sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11402443",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404392",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, sgstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr sgstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr sgstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(sgstDetnarr1);
					detNarrList.add(sgstDetnarr2);

					bunumCounter += 2;

					log.info("Non-Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

				}
				if (igstTotal > 0) {
					igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11402445",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404397",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, igstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr igstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr igstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(igstDetnarr1);
					detNarrList.add(igstDetnarr2);

					bunumCounter += 2;

					log.info("Non-Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

				}
				if (ugstTotal > 0) {
					ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11402447",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404398",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, ugstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr ugstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr ugstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(ugstDetnarr1);
					detNarrList.add(ugstDetnarr2);

					bunumCounter += 2;

					log.info("Non-Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

				}
			} else {
				if (cgstTotal > 0 && sgstTotal > 0) {
					cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11401026",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404391",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, cgstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr cgstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr cgstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(cgstDetnarr1);
					detNarrList.add(cgstDetnarr2);

					bunumCounter += 2;

					log.info("Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

					sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11401026",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404392",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, sgstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr sgstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr sgstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(sgstDetnarr1);
					detNarrList.add(sgstDetnarr2);

					bunumCounter += 2;

					log.info("Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

				}
				if (igstTotal > 0) {
					igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11401026",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404393",
							adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
							adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
							adminBillRequestBean.getAdmbillhRequestBean().getProject(),
							adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, igstTotal,
							adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
							building.getBldgProperty(), "", tranDate, bunumCounter,
							adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
							companyEntity.getCompanyCK().getCoyProp(),
							adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
									? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
									? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
									: "",
							"", "O", "", "",
							Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
									? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
									: 0d,
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
							adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
							"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

					Detnarr igstDetnarr1 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					Detnarr igstDetnarr2 = Detnarr.builder()
							.detnarrCK(
									DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
											.detTranser(ser).detBunum(bunumCounter + 1).build())
							.detDettype("E")
							.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
									: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
							.detSite(GenericAuditContextHolder.getContext().getSite())
							.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
							.build();

					detNarrList.add(igstDetnarr1);
					detNarrList.add(igstDetnarr2);

					bunumCounter += 2;

					log.info("Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
					log.info("bunumCounter : {}", bunumCounter);

				}
			}
			if (ugstTotal > 0) {
				ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11402447",
						adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), "20404394",
						adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
						adminBillRequestBean.getAdmbillhRequestBean().getProject(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d, ugstTotal,
						adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
						building.getBldgProperty(), "", tranDate, bunumCounter,
						adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
						companyEntity.getCompanyCK().getCoyProp(),
						adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"",
						Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
								? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: "",
						Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
								? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: "",
						"", "O", "", "",
						Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
								? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
								: 0d,
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

				Detnarr ugstDetnarr1 = Detnarr.builder()
						.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
								.detTranser(ser).detBunum(bunumCounter).build())
						.detDettype("E")
						.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
								: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
						.detSite(GenericAuditContextHolder.getContext().getSite())
						.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
						.build();

				Detnarr ugstDetnarr2 = Detnarr.builder()
						.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
								.detTranser(ser).detBunum(bunumCounter + 1).build())
						.detDettype("E")
						.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
								: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
						.detSite(GenericAuditContextHolder.getContext().getSite())
						.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
						.build();

				detNarrList.add(ugstDetnarr1);
				detNarrList.add(ugstDetnarr2);
				bunumCounter += 2;

				log.info("Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
				log.info("bunumCounter : {}", bunumCounter);

			}

			if (adminBillRequestBean.getAdmbillhRequestBean().getTdsamount() > 0) {
				tdsAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("BO", "11401026",
						adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartycode(), building.getBldgProject(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(),
						adminBillRequestBean.getAdmbillhRequestBean().getTdsacmajor(),
						adminBillRequestBean.getAdmbillhRequestBean().getMintype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
						adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
						adminBillRequestBean.getAdmbillhRequestBean().getProject(),
						adminBillRequestBean.getAdmbillhRequestBean().getAcminor(), 0d,
						adminBillRequestBean.getAdmbillhRequestBean().getTdsamount() * -1,
						adminBillRequestBean.getAdmbillhRequestBean().getBldgcode().trim(), "", "",
						building.getBldgProperty(), "", tranDate, bunumCounter,
						adminBillRequestBean.getAdmbillhRequestBean().getNarration(), ser, "PL",
						companyEntity.getCompanyCK().getCoyProp(),
						adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"",
						Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getFromdate())
								? adminBillRequestBean.getAdmbillhRequestBean().getFromdate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: "",
						Objects.nonNull(adminBillRequestBean.getAdmbillhRequestBean().getTodate())
								? adminBillRequestBean.getAdmbillhRequestBean().getTodate()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
								: "",
						"", "O", "", "",
						Objects.nonNull(adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity())
								? adminBillRequestBean.getAdmbilldRequestBean().get(0).getQuantity()
								: 0d,
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
						adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"", adminBillRequestBean.getRefPartyType(), adminBillRequestBean.getRefPartyCode());

				log.info("tdsAmountBreakup : {}", tdsAmountBreakup);
				log.info("bunumCounter : {}", bunumCounter);

				Detnarr tdsDetnarr1 = Detnarr.builder()
						.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
								.detTranser(ser).detBunum(bunumCounter).build())
						.detDettype("E")
						.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
								: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
						.detSite(GenericAuditContextHolder.getContext().getSite())
						.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
						.build();

				Detnarr tdsDetnarr2 = Detnarr.builder()
						.detnarrCK(DetnarrCK.builder().detCoy(adminBillRequestBean.getAdmbillhRequestBean().getCoy())
								.detTranser(ser).detBunum(bunumCounter + 1).build())
						.detDettype("E")
						.detNarrative(Objects.isNull(adminBillRequestBean.getAdmbillhRequestBean().getSunid()) ? ""
								: adminBillRequestBean.getAdmbillhRequestBean().getSunid().replace("'", "''"))
						.detSite(GenericAuditContextHolder.getContext().getSite())
						.detUserid(GenericAuditContextHolder.getContext().getUserid()).detToday(LocalDateTime.now())
						.build();

				detNarrList.add(tdsDetnarr1);
				detNarrList.add(tdsDetnarr2);

			}
			List<ActrandBean> actrandList = setActrandBeanList(adminBillAmountBreakup, cgstAmountBreakup,
					sgstAmountBreakup, igstAmountBreakup, ugstAmountBreakup, tdsAmountBreakup);

			List<Actrand> actrandEntityList = new ArrayList<Actrand>();

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			log.info("actrand Entity List : {}", actrandEntityList);

			log.info("detNarrList : {}", detNarrList);

			detnarrRepository.saveAll(detNarrList);

			genericAccountingLogic.updateActranh(ser,
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt().format(outputFormatter), "PL",
					adminBillRequestBean.getAdmbillhRequestBean().getPartytype(),
					adminBillRequestBean.getAdmbillhRequestBean().getPartycode(),
					adminBillRequestBean.getAdmbillhRequestBean().getBillamount()
							- adminBillRequestBean.getAdmbillhRequestBean().getTdsamount(),
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbillno(),
					adminBillRequestBean.getAdmbillhRequestBean().getSuppbilldt()
							.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					"Y", "Y", "N", "N", "N", adminBillRequestBean.getAdmbillhRequestBean().getNarration(),
					adminBillRequestBean.getAdmbillhRequestBean().getCoy().trim(), "Y", "N", null, "BO", false);

			actrandRepository.saveAll(actrandEntityList);
			if (adminBillRequestBean.getIsUpdate()) {
				return new GenericResponse<>(true, "Admin bill updated successfully .", ser);
			}

			return new GenericResponse<>(true, "Admin bill created successfully .", ser);

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Admin bill Creation/Update Falied .");
		}
	}

	public List<ActrandBean> setActrandBeanList(List<ActrandBean> adminBillAmountBreakup,
			List<ActrandBean> cgstAmountBreakup, List<ActrandBean> sgstAmountBreakup,
			List<ActrandBean> igstAmountBreakup, List<ActrandBean> ugstAmountBreakup,
			List<ActrandBean> tdsAmountBreakup) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

		List<List<ActrandBean>> listOfLists = new ArrayList<>();
		listOfLists.add(adminBillAmountBreakup);
		if (Objects.nonNull(cgstAmountBreakup) && Objects.nonNull(sgstAmountBreakup)) {
			listOfLists.add(cgstAmountBreakup);
			listOfLists.add(sgstAmountBreakup);
		}
		if (Objects.isNull(cgstAmountBreakup) && Objects.isNull(sgstAmountBreakup)
				&& Objects.nonNull(igstAmountBreakup)) {
			listOfLists.add(igstAmountBreakup);
		}
		if (Objects.isNull(cgstAmountBreakup) && Objects.isNull(sgstAmountBreakup)
				&& Objects.nonNull(ugstAmountBreakup)) {
			listOfLists.add(ugstAmountBreakup);
		}
		if (Objects.nonNull(tdsAmountBreakup)) {
			listOfLists.add(tdsAmountBreakup);
		}

		List<ActrandBean> actrandList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());

		log.info("actrandList : {}", actrandList);

		for (ActrandBean actrandBean : actrandList) {
			Date tranDate, xtranDate, docDate, vouDate;
			if (isValidDateFormat(actrandBean.getTrandate())) {
				tranDate = inputFormat.parse(actrandBean.getTrandate());
				actrandBean.setTrandate(outputFormat.format(tranDate));
			}
			if (isValidDateFormat(actrandBean.getXreftrandate())) {
				xtranDate = inputFormat.parse(actrandBean.getXreftrandate());
				actrandBean.setXreftrandate(outputFormat.format(xtranDate));
			}
			if (isValidDateFormat(actrandBean.getDocdate())) {
				docDate = inputFormat.parse(actrandBean.getDocdate());
				actrandBean.setDocdate(outputFormat.format(docDate));
			}
			if (isValidDateFormat(actrandBean.getVoudate())) {
				vouDate = inputFormat.parse(actrandBean.getVoudate());
				actrandBean.setVoudate(outputFormat.format(vouDate));
			}
			if (Objects.isNull(actrandBean.getPartytype())) {
				actrandBean.setPartytype(" ");
			}
			if (Objects.isNull(actrandBean.getPartycode())) {
				actrandBean.setPartycode(" ");
			}
			if (Objects.isNull(actrandBean.getXpartytype())) {
				actrandBean.setXpartytype(" ");
			}
			if (Objects.isNull(actrandBean.getXpartycode())) {
				actrandBean.setXpartycode(" ");
			}
		}
		log.info("actrandList : {}", actrandList);
		return actrandList;
	}

	private static boolean isValidDateFormat(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		try {
			Date parsedDate = dateFormat.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	@Override
	public GenericResponse<Boolean> fetchPartyAlreadyExistsForPeriod(
			FetchPartyAlreadyExistsRequest fetchPartyAlreadyExistsRequest) {

		try {

			Integer billCount = admbillhRepository.fetchBillCount(fetchPartyAlreadyExistsRequest.getCompanyCode(),
					fetchPartyAlreadyExistsRequest.getPartyType(), fetchPartyAlreadyExistsRequest.getPartyCode(),
					fetchPartyAlreadyExistsRequest.getBuildingCode(), fetchPartyAlreadyExistsRequest.getAcMajor(),
					fetchPartyAlreadyExistsRequest.getFromDate(), fetchPartyAlreadyExistsRequest.getToDate());

			return new GenericResponse<>(true, "Data retreived successfully .", billCount > 0 ? true : false);

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Data retreival falied .");
		}
	}

	@Override
	public GenericResponse<Double> fetchTdsPercentage(TdsRequest tdsRequest) {
		try {
			Double tdsPercentage = 0d;
			LocalDate date = LocalDate.now();

			Party party = partyRepository.findByPartyCodeAndParPartytypeAndBillDate(tdsRequest.getPartyCode(),
					tdsRequest.getPartyType(), date);

			String aadharPanLinkedYN = Objects.isNull(party.getParAadharPanLinkedYN()) ? ""
					: party.getParAadharPanLinkedYN();

			if (aadharPanLinkedYN.equalsIgnoreCase("N")) {
				tdsPercentage = entityRepository
						.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates2(tdsRequest.getSuppBillDate());
			}

			return new GenericResponse<>(true, "Data retreived successfully .", tdsPercentage);

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Data retreival falied .");
		}
	}

}
