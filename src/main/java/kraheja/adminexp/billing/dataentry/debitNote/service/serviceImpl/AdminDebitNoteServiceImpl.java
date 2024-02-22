package kraheja.adminexp.billing.dataentry.debitNote.service.serviceImpl;

import java.math.BigDecimal;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.AdbnotedRequestBean;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.AdbnotehRequestBean;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.CancelDebitNoteRequest;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.DebitNoteRequest;
import kraheja.adminexp.billing.dataentry.debitNote.entity.Adbnoted;
import kraheja.adminexp.billing.dataentry.debitNote.entity.Adbnoteh;
import kraheja.adminexp.billing.dataentry.debitNote.mappers.AdbnotedEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.debitNote.mappers.AdbnotehEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.debitNote.repository.AdbnotedRepository;
import kraheja.adminexp.billing.dataentry.debitNote.repository.AdbnotehRepository;
import kraheja.adminexp.billing.dataentry.debitNote.service.AdminDebitNoteService;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbilldRepository;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbillhRepository;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class AdminDebitNoteServiceImpl implements AdminDebitNoteService {

	@Autowired
	AdbnotehRepository adbnotehRepository;

	@Autowired
	AdbnotedRepository adbnotedRepository;

	@Autowired
	GenericCounterIncrementLogicUtil counterIncrementLogicUtil;

	@Autowired
	GenericAccountingLogic genericAccountingLogic;

	@Autowired
	EntityRepository entityRepository;

	@Autowired
	PartyRepository partyRepository;

	@Autowired
	AdmbillhRepository admbillhRepository;

	@Autowired
	AdmbilldRepository admbilldRepository;

	@Autowired
	ActranhRepository actranhRepository;

	@Autowired
	ActrandRepository actrandRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Override
	public GenericResponse<Map<String, Object>> fetchDebitNoteBySer(String ser) {

		List<Object[]> adbnoteh = adbnotehRepository.findByAdbnhDbnoteserJoinQuery(ser);

		log.info("adbnoteh : {}", adbnoteh);

		List<Map<String, Object>> keyValueList = new ArrayList<>();

		String[] adbnotehAttributes = { "ADBNH_PARTYTYPE", "partydesc", "ADBNH_PARTYCODE", "par_partyname",
				"ADBNH_BLDGCODE", "bldg_name", "ADBNH_COY", "coy_name", "proj_name", "ADBNH_INVBILLNO", "ADBNH_DATE",
				"ADBNH_INVBILLDT", "ADBNH_BILLTYPE", "ADBNH_AMOUNT", "ADBNH_TDSPERC", "ADBNH_TDSAMOUNT",
				"ADBNH_NARRATION", "ADBNH_DESCRIPTION1", "ADBNH_DBNOTESER", "ADBNH_PROP", "ADBNH_PROJECT",
				"ADBNH_FOTOAMT" };

		Map<String, Object> keyValueMap = new HashMap<>();
		Map<String, Object> keyValueMap2 = new HashMap<>();

		for (Object[] row : adbnoteh) {

			for (int i = 0; i < adbnotehAttributes.length; i++) {
				keyValueMap.put(adbnotehAttributes[i], row[i]);
			}
			keyValueList.add(keyValueMap);
		}

		String partyType = String.valueOf((Character) keyValueMap.get("ADBNH_PARTYTYPE"));
		String partyCode = (String) keyValueMap.get("ADBNH_PARTYCODE");
		String suppBillNo = (String) keyValueMap.get("ADBNH_INVBILLNO");

		Admbillh admbillh = admbillhRepository.findByPartyTypeAndPartyCodeAndSuppBillNo(partyType.trim(),
				partyCode.trim(), suppBillNo.trim());

		List<Object[]> adbnoted = adbnotedRepository.findByAdbndDbnoteser(ser);

		log.info("adbnoted : {}", adbnoted);

		List<Map<String, Object>> keyValueList2 = new ArrayList<>();

		String[] adbnotedAttributes = { "ADBND_SACCODE", "ADBND_SACDESC", "ADBND_QUANTITY", "ADBND_RATE",
				"ADBND_AMOUNT", "ADBND_DISCOUNTAMT", "ADBND_TAXABLEAMT", "ADBND_CGSTPERC", "ADBND_CGSTAMT",
				"ADBND_SGSTPERC", "ADBND_SGSTAMT", "ADBND_IGSTPERC", "ADBND_IGSTAMT", "ADBND_UGSTPERC",
				"ADBND_UGSTAMT" };

		for (Object[] row : adbnoted) {

			for (int i = 0; i < adbnotedAttributes.length; i++) {
				keyValueMap2.put(adbnotedAttributes[i], row[i]);
			}
			keyValueList2.add(keyValueMap2);
		}

		Double totalAmount = Objects.isNull(admbillh) ? null : admbillh.getAdblhBillamount();

		Map<String, Object> finalKeyValueList = new HashMap<String, Object>();
		finalKeyValueList.put("adbnotehAttributes", keyValueList);
		finalKeyValueList.put("adbnotedAttributes", keyValueList2);
		finalKeyValueList.put("billStatus", Objects.isNull(admbillh) ? null : admbillh.getAdblhStatus());
		finalKeyValueList.put("totalAmount", totalAmount);

		log.info("finalKeyValueList : {}", finalKeyValueList);

		return new GenericResponse<>(true, "Data fetched successfully", finalKeyValueList);

	}

	@Override
	public GenericResponse<Map<String, Object>> retreieveDebitNoteByInvoiceNum(String partyType, String partyCode,
			String invoiceNum) {

		try {
			Admbillh admbillh = admbillhRepository.findByPartyTypeAndPartyCodeAndSuppBillNo(partyType, partyCode,
					invoiceNum);

			List<Admbilld> admbilld = admbilldRepository
					.findByAdmbilldCK_AdbldSer(admbillh.getAdmbillhCK().getAdblhSer());

			Map<String, Object> finalKeyValueList = new HashMap<String, Object>();

			finalKeyValueList.put("AdmBillh", admbillh);
			finalKeyValueList.put("AdmBilld", admbilld);
			return new GenericResponse<>(true, "Data retreived successfully .", finalKeyValueList);
		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Data retreival falied .");
		}
	}

	@Override
	public GenericResponse<String> addDebitNote(DebitNoteRequest debitNoteRequest) {

		try {
			String tranSer = "";

			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			Party party = partyRepository.findByPartyCodeAndPartytype(
					debitNoteRequest.getAdbnotehRequestBean().getPartycode().trim(),
					debitNoteRequest.getAdbnotehRequestBean().getPartytype().trim());

			Building building = buildingRepository
					.findByBuildingCK_BldgCode(debitNoteRequest.getAdbnotehRequestBean().getBldgcode());

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					debitNoteRequest.getAdbnotehRequestBean().getCoy().trim(), CommonUtils.INSTANCE.closeDate());

			Admbillh admbillh = admbillhRepository.findByPartyTypeAndPartyCodeAndSuppBillNo(
					debitNoteRequest.getAdbnotehRequestBean().getPartytype().trim(),
					debitNoteRequest.getAdbnotehRequestBean().getPartycode().trim(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbillno().trim());

			String admbillSerNo = admbillh.getAdmbillhCK().getAdblhSer();

			List<Admbilld> admbilldList = new ArrayList<>();

			for (AdbnotedRequestBean e : debitNoteRequest.getAdbnotedRequestBean()) {

				Admbilld admbilld = admbilldRepository.findByAdmbilldCK_AdbldSerAndAdmbilldCK_AdbldLineno(admbillSerNo,
						e.getLineno().toString());

				Adbnoted previousAdbnoted = adbnotedRepository.findByAdbnotedCK_AdbndDbnoteserAndAdbnotedCK_AdbndLineno(
						debitNoteRequest.getAdbnotehRequestBean().getDbnoteser(), e.getLineno());

				if (debitNoteRequest.getIsUpdate()) {
					admbilld.setAdbldDbamt(
							admbilld.getAdbldDbamt() - previousAdbnoted.getAdbndAmount() + e.getAmount());
					admbilld.setAdbldDbqty(
							admbilld.getAdbldDbqty() - previousAdbnoted.getAdbndQuantity() + e.getQuantity());
				} else {
					admbilld.setAdbldDbamt(e.getTaxableamt());
					admbilld.setAdbldDbqty(e.getQuantity());
				}
				admbilld.setAdbldSite(GenericAuditContextHolder.getContext().getSite());
				admbilld.setAdbldOrigsite(GenericAuditContextHolder.getContext().getSite());
				admbilld.setAdbldUserid(GenericAuditContextHolder.getContext().getUserid());
				admbilld.setAdbldToday(LocalDateTime.now());

				log.info("admbilld : {}", admbilld);

				admbilldList.add(admbilld);
			}

			Actrand actrand = actrandRepository.findActdMinorByTranserAndBnum(admbillSerNo, 1);

			Adbnoteh previousAdbnoteh = adbnotehRepository
					.findByAdbnotehCK_AdbnhDbnoteser(debitNoteRequest.getAdbnotehRequestBean().getDbnoteser());

			AdbnotehRequestBean adbnotehRequestBean = debitNoteRequest.getAdbnotehRequestBean();

			List<AdbnotedRequestBean> adbnotedRequestBean = debitNoteRequest.getAdbnotedRequestBean();

			tranSer = GenericCounterIncrementLogicUtil.generateTranNo("#DNA", "#DNA");

			if (debitNoteRequest.getIsUpdate()) {
				tranSer = debitNoteRequest.getAdbnotehRequestBean().getDbnoteser();
				adbnotehRepository.deleteByAdbnotehCK_AdbnhDbnoteser(
						debitNoteRequest.getAdbnotehRequestBean().getDbnoteser().trim());
				adbnotedRepository.deleteByAdbnotedCK_AdbndDbnoteser(
						debitNoteRequest.getAdbnotehRequestBean().getDbnoteser().trim());
				actrandRepository.deleteActrand(debitNoteRequest.getAdbnotehRequestBean().getDbnoteser());
				actranhRepository.deleteActranh(debitNoteRequest.getAdbnotehRequestBean().getDbnoteser());
			}

			String STRLOCACTD_MINCODE = setValueInParameters(actrand.getActdMincode());
			String STRLOCACTD_XACMAJOR = setValueInParameters(actrand.getActdXacmajor());
			String STRLOCACTD_XACMINOR = setValueInParameters(actrand.getActdXacminor());
			String STRLOCACTD_XMINTYPE = setValueInParameters(actrand.getActdXmintype());

			Integer bunumCounter = 1;

			List<ActrandBean> debitAmountBreakup = new ArrayList<>();
			List<ActrandBean> cgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> sgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> igstAmountBreakup = new ArrayList<>();
			List<ActrandBean> ugstAmountBreakup = new ArrayList<>();
			List<ActrandBean> tdsAmountBreakup = new ArrayList<>();
			List<ActrandBean> actrandList = new ArrayList<>();
			List<Actrand> actrandEntityList = new ArrayList<Actrand>();

			Double debitAmount = adbnotedRequestBean.stream().mapToDouble(AdbnotedRequestBean::getTaxableamt).sum();
			Double cgstAmount = adbnotedRequestBean.stream().mapToDouble(AdbnotedRequestBean::getCgstamt).sum();
			Double sgstAmount = adbnotedRequestBean.stream().mapToDouble(AdbnotedRequestBean::getSgstamt).sum();
			Double ugstAmount = adbnotedRequestBean.stream().mapToDouble(AdbnotedRequestBean::getUgstamt).sum();
			Double igstAmount = adbnotedRequestBean.stream().mapToDouble(AdbnotedRequestBean::getIgstamt).sum();

			debitAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
					STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
					building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
					STRLOCACTD_XACMAJOR, STRLOCACTD_XMINTYPE, " ",
					debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z", STRLOCACTD_XACMINOR, -1 * debitAmount,
					debitNoteRequest.getAdbnotehRequestBean().getBldgcode(), building.getBldgProperty(), "",
					debitNoteRequest.getAdbnotehRequestBean().getDate(), bunumCounter,
					debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
					companyEntity.getCompanyCK().getCoyProp(), debitNoteRequest.getAdbnotehRequestBean().getCoy(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
							.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
					"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
							.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
					"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

			bunumCounter += 2;

			log.info("debitAmountBreakup : {}", debitAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			if (Objects.nonNull(party)) {
				if (Objects.isNull(party.getParGstno())) {
					if (cgstAmount > 0 && sgstAmount > 0) {
						cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402441", " ",
								STRLOCACTD_MINCODE, " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								STRLOCACTD_XACMAJOR, " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"Z", "20404395", -1*cgstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
								building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
								bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

						bunumCounter += 2;

						log.info("Non-Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

						sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402443", " ",
								STRLOCACTD_MINCODE, " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								STRLOCACTD_XACMAJOR, " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"Z", "20404396", -1*sgstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
								building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
								bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z",debitNoteRequest.getAdbnotehRequestBean().getPartycode());
						bunumCounter += 2;

						log.info("Non-Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

						if (igstAmount > 0) {
							igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402445", " ",
									STRLOCACTD_MINCODE, " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
									building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
									STRLOCACTD_XACMAJOR, " ", " ",
									debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z", "20404397",
									-1*igstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
									building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
									bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer,
									"PL", companyEntity.getCompanyCK().getCoyProp(),
									debitNoteRequest.getAdbnotehRequestBean().getCoy(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
									"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
									"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

							bunumCounter += 2;

							log.info("Non-Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
							log.info("bunumCounter : {}", bunumCounter);

						}
						if (ugstAmount > 0) {
							ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402447", " ",
									STRLOCACTD_MINCODE, " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
									building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
									STRLOCACTD_XACMAJOR, " ", " ",
									debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z", "20404398",
									-1*ugstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
									building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
									bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer,
									"PL", companyEntity.getCompanyCK().getCoyProp(),
									debitNoteRequest.getAdbnotehRequestBean().getCoy(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
									"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
									debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
											.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
									"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());
							bunumCounter += 2;

							log.info("Non-Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
							log.info("bunumCounter : {}", bunumCounter);

						}
					}
				} else {
					if (cgstAmount > 0 && sgstAmount > 0) {
						cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"20404391", " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z",
								STRLOCACTD_XACMINOR, -1*cgstAmount,
								debitNoteRequest.getAdbnotehRequestBean().getBldgcode(), building.getBldgProperty(), "",
								debitNoteRequest.getAdbnotehRequestBean().getDate(), bunumCounter,
								debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z",debitNoteRequest.getAdbnotehRequestBean().getPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

						sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"20404392", " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z",
								"20404392", -1*sgstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
								building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
								bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
					if (igstAmount > 0) {
						igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"20404393", " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z",
								"20404393", -1*igstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
								building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
								bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}

					if (ugstAmount > 0) {
						ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
								"20404394", " ", " ", debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z",
								"20404394",-1* ugstAmount, debitNoteRequest.getAdbnotehRequestBean().getBldgcode(),
								building.getBldgProperty(), "", debitNoteRequest.getAdbnotehRequestBean().getDate(),
								bunumCounter, debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
								companyEntity.getCompanyCK().getCoyProp(),
								debitNoteRequest.getAdbnotehRequestBean().getCoy(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
								debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
										.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
								"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
				}
			}

			if (debitNoteRequest.getAdbnotehRequestBean().getTdsamount() > 0) {
				tdsAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", "Z",
						STRLOCACTD_MINCODE, "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
						building.getBldgProject(), debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
						admbillh.getAdblhTdsacmajor().trim(), " ", " ",
						debitNoteRequest.getAdbnotehRequestBean().getPartycode(), "Z", STRLOCACTD_XACMINOR,
						debitNoteRequest.getAdbnotehRequestBean().getTdsamount(),
						debitNoteRequest.getAdbnotehRequestBean().getBldgcode(), building.getBldgProperty(), "",
						debitNoteRequest.getAdbnotehRequestBean().getDate(), bunumCounter,
						debitNoteRequest.getAdbnotehRequestBean().getNarration(), tranSer, "PL",
						companyEntity.getCompanyCK().getCoyProp(), debitNoteRequest.getAdbnotehRequestBean().getCoy(),
						debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
						debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"", "O", "", "", 0.0, debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
						debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
								.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER),
						"", "Z", debitNoteRequest.getAdbnotehRequestBean().getPartycode());

				log.info("tdsAmountBreakup : {}", tdsAmountBreakup);
				log.info("bunumCounter : {}", bunumCounter);

			}
			actrandList = setActrandBeanList(debitAmountBreakup, cgstAmountBreakup, sgstAmountBreakup,
					igstAmountBreakup, ugstAmountBreakup, tdsAmountBreakup);

			log.info("actrandList : {}", actrandList);

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			log.info("actrand Entity List : {}", actrandEntityList);

			adbnotehRequestBean.setDbnoteser(tranSer);

			Adbnoteh adbnoteh = AdbnotehEntityPojoMapper.addAdbnotehPojoEntityMapper.apply(adbnotehRequestBean);

			log.info("adbnoteh : {}", adbnoteh);

			List<Adbnoted> adbnoted = AdbnotedEntityPojoMapper.addAdbnotedPojoEntityMapper.apply(adbnotedRequestBean,
					tranSer);

			log.info("adbnoted : {}", adbnoted);

			adbnotehRepository.save(adbnoteh);

			adbnotedRepository.saveAll(adbnoted);

			Double totalDebitAmount = adbnotehRepository
					.findtotalDebitAmountByAdbnhInvbillno(adbnoteh.getAdbnhInvbillno().trim());

			admbillh.setAdblhDebitamt(totalDebitAmount);
			admbillh.setAdblhOrigsite(GenericAuditContextHolder.getContext().getSite());
			admbillh.setAdblhSite(GenericAuditContextHolder.getContext().getSite());
			admbillh.setAdblhUserid(GenericAuditContextHolder.getContext().getUserid());
			admbillh.setAdblhToday(LocalDateTime.now());

			log.info("admbillh : {}", admbillh);

			admbillhRepository.save(admbillh);

			admbilldRepository.saveAll(admbilldList);

			genericAccountingLogic.updateActranh(tranSer,
					LocalDate.parse(debitNoteRequest.getAdbnotehRequestBean().getDate(), inputFormatter).format(
							outputFormatter),
					"PL", debitNoteRequest.getAdbnotehRequestBean().getPartytype().trim(),
					debitNoteRequest.getAdbnotehRequestBean().getPartycode(),
					debitNoteRequest.getAdbnotehRequestBean().getAmount()
							- debitNoteRequest.getAdbnotehRequestBean().getTdsamount(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbillno(),
					debitNoteRequest.getAdbnotehRequestBean().getInvbilldt()
							.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					"Y", "Y", "N", "N", "N", debitNoteRequest.getAdbnotehRequestBean().getNarration(),
					debitNoteRequest.getAdbnotehRequestBean().getCoy(), "Y", "N", "N", "DA", false);

			actrandRepository.saveAll(actrandEntityList);

			if (debitNoteRequest.getIsUpdate()) {
				return new GenericResponse<>(true, "Debit note updated successfully .", tranSer);
			}

			return new GenericResponse<>(true, "Debit note created successfully .", tranSer);

		} catch (Exception e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Debit Note Creation Falied .");
		}
	}

	@Override
	public GenericResponse<Map<String, Object>> cancelDebitNoteBySer(CancelDebitNoteRequest cancelDebitNoteRequest) {

		try {

			Map<String, Object> finalKeyValueList = new HashMap<String, Object>();
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			String oldTranserNum = cancelDebitNoteRequest.getSerNo();

			String newTranserNum = GenericCounterIncrementLogicUtil.generateTranNo("#DNA", "#DNA");

			Actranh actranh = actranhRepository.findByActranhCK_ActhTranser(oldTranserNum);

			Adbnoteh adbnoteh = adbnotehRepository.findByAdbnotehCK_AdbnhDbnoteser(oldTranserNum);
			List<Adbnoted> adbnoted = adbnotedRepository.findByAdbnotedCK_AdbndDbnoteser(oldTranserNum);

			Admbillh admbillh = admbillhRepository.findByPartyTypeAndPartyCodeAndSuppBillNo(
					cancelDebitNoteRequest.getPartyType(), cancelDebitNoteRequest.getPartyCode(),
					cancelDebitNoteRequest.getSuppBillNo());

			List<Admbilld> admbilldList = new ArrayList<>();
			for (Adbnoted e : adbnoted) {
				Admbilld admbilld = admbilldRepository.findByAdmbilldCK_AdbldSerAndAdbldHsnsaccode(
						admbillh.getAdmbillhCK().getAdblhSer(), e.getAdbndSaccode());

				admbilld.setAdbldDbamt(admbilld.getAdbldDbamt() - e.getAdbndAmount());
				admbilld.setAdbldDbqty(admbilld.getAdbldDbqty() - e.getAdbndQuantity());
				admbilld.setAdbldSite(GenericAuditContextHolder.getContext().getSite());
				admbilld.setAdbldOrigsite(GenericAuditContextHolder.getContext().getSite());
				admbilld.setAdbldUserid(GenericAuditContextHolder.getContext().getUserid());
				admbilld.setAdbldToday(LocalDateTime.now());

				admbilldList.add(admbilld);
			}

			Party party = partyRepository.findByPartyCodeAndPartytype(adbnoteh.getAdbnhPartycode().trim(),
					adbnoteh.getAdbnhPartytype());

			Building building = buildingRepository.findByBuildingCK_BldgCode(adbnoteh.getAdbnhBldgcode().trim());

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					adbnoteh.getAdbnhCoy().trim(), CommonUtils.INSTANCE.closeDate());

			Actrand actrand = actrandRepository.findActdMinorByTranserAndBnum(oldTranserNum, 1);

			actranh.setActhReverseyn("Y");
			actranh.setActhUserid(GenericAuditContextHolder.getContext().getUserid());
			actranh.setActhSite(GenericAuditContextHolder.getContext().getSite());
			actranh.setActhToday(LocalDateTime.now());

			admbillh.setAdblhDebitamt(Objects.isNull(admbillh.getAdblhDebitamt())?0:admbillh.getAdblhDebitamt() - adbnoteh.getAdbnhAmount());
			admbillh.setAdblhOrigsite(GenericAuditContextHolder.getContext().getSite());
			admbillh.setAdblhSite(GenericAuditContextHolder.getContext().getSite());
			admbillh.setAdblhUserid(GenericAuditContextHolder.getContext().getUserid());
			admbillh.setAdblhToday(LocalDateTime.now());

			genericAccountingLogic.updateActranh(newTranserNum, adbnoteh.getAdbnhDate().format(outputFormatter), "PL",
					adbnoteh.getAdbnhPartytype(), adbnoteh.getAdbnhPartycode(),
					-1 * (adbnoteh.getAdbnhAmount() - adbnoteh.getAdbnhTdsamount()), adbnoteh.getAdbnhInvbillno(),
					adbnoteh.getAdbnhInvbilldt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "Y", "Y", "N", "N",
					"N", "Reversal of Transer#" + oldTranserNum + "for Invoiceno " + adbnoteh.getAdbnhInvbillno(),
					adbnoteh.getAdbnhCoy().trim(), "Y", "Y", "N", "DA", false);

			String STRLOCACTD_MINCODE = setValueInParameters(actrand.getActdMincode());
			String STRLOCACTD_XACMAJOR = setValueInParameters(actrand.getActdXacmajor());
			String STRLOCACTD_XACMINOR = setValueInParameters(actrand.getActdXacminor());
			String STRLOCACTD_XMINTYPE = setValueInParameters(actrand.getActdXmintype());

			Integer bunumCounter = 1;

			List<ActrandBean> debitAmountBreakup = new ArrayList<>();
			List<ActrandBean> cgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> sgstAmountBreakup = new ArrayList<>();
			List<ActrandBean> igstAmountBreakup = new ArrayList<>();
			List<ActrandBean> ugstAmountBreakup = new ArrayList<>();
			List<ActrandBean> tdsAmountBreakup = new ArrayList<>();

			Double debitAmount = adbnoted.stream().mapToDouble(Adbnoted::getAdbndTaxableamt).sum();
			Double cgstAmount = adbnoted.stream().mapToDouble(Adbnoted::getAdbndCgstamt).sum();
			Double sgstAmount = adbnoted.stream().mapToDouble(Adbnoted::getAdbndSgstamt).sum();
			Double ugstAmount = adbnoted.stream().mapToDouble(Adbnoted::getAdbndUgstamt).sum();
			Double igstAmount = adbnoted.stream().mapToDouble(Adbnoted::getAdbndIgstamt).sum();

			debitAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
					STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
					adbnoteh.getAdbnhPartycode(), STRLOCACTD_XACMAJOR, STRLOCACTD_XMINTYPE, " ",
					adbnoteh.getAdbnhPartycode(), "Z", STRLOCACTD_XACMINOR, debitAmount,
					adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
					adbnoteh.getAdbnhDate().toString(), bunumCounter,
					Objects.isNull(adbnoteh.getAdbnhNarration()) ? " " : adbnoteh.getAdbnhNarration().trim(),
					newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
					adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "", 0.0,
					adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
					adbnoteh.getAdbnhPartycode());

			bunumCounter += 2;

			log.info("debitAmountBreakup : {}", debitAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			if (Objects.nonNull(party)) {
				if (Objects.isNull(party.getParGstno())) {
					if (cgstAmount > 0 && sgstAmount > 0) {
						cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402441", " ",
								STRLOCACTD_MINCODE, " ", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404395", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404395", cgstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Non-Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

						sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402443", " ",
								STRLOCACTD_MINCODE, " ", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404396", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404396", sgstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());
						bunumCounter += 2;

						log.info("Non-Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
					if (igstAmount > 0) {
						igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11402445", " ",
								STRLOCACTD_MINCODE, " ", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404397", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404397", igstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Non-Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
					if (ugstAmount > 0) {
						ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "  ", " ",
								STRLOCACTD_MINCODE, " ", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404398", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404398", ugstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhInvbillno().trim());
						bunumCounter += 2;

						log.info("Non-Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
				} else {
					if (cgstAmount > 0 && sgstAmount > 0) {
						cgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404391", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", STRLOCACTD_XACMINOR, cgstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> cgstAmountBreakup : {}", cgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

						sgstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404392", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404392", sgstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> sgstAmountBreakup : {}", sgstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
					if (igstAmount > 0) {
						igstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404393", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404393", igstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> igstAmountBreakup : {}", igstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
					if (ugstAmount > 0) {
						ugstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", " ",
								STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
								adbnoteh.getAdbnhPartycode(), "20404394", " ", " ",
								adbnoteh.getAdbnhPartycode(), "Z", "20404394", ugstAmount,
								adbnoteh.getAdbnhBldgcode().trim(), building.getBldgProperty(), "",
								adbnoteh.getAdbnhDate().toString(), bunumCounter,
								Objects.isNull(adbnoteh.getAdbnhNarration()) ? " "
										: adbnoteh.getAdbnhNarration().trim(),
								newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
								adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "",
								0.0, adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
								adbnoteh.getAdbnhPartycode());

						bunumCounter += 2;

						log.info("Registered Party -> ugstAmountBreakup : {}", ugstAmountBreakup);
						log.info("bunumCounter : {}", bunumCounter);

					}
				}

			}
			if (adbnoteh.getAdbnhTdsamount() > 0) {
				tdsAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("DA", "11401026", "Z",
						STRLOCACTD_MINCODE, "Z", adbnoteh.getAdbnhPartycode(), building.getBldgProject(),
						adbnoteh.getAdbnhPartycode(), admbillh.getAdblhTdsacmajor().trim(), " ", " ", adbnoteh.getAdbnhPartycode(), "Z",
						STRLOCACTD_XACMINOR, -1 * adbnoteh.getAdbnhTdsamount(), adbnoteh.getAdbnhBldgcode().trim(),
						building.getBldgProperty(), "", adbnoteh.getAdbnhDate().toString(), bunumCounter,
						Objects.isNull(adbnoteh.getAdbnhNarration()) ? " " : adbnoteh.getAdbnhNarration().trim(),
						newTranserNum, "PL", companyEntity.getCompanyCK().getCoyProp(), adbnoteh.getAdbnhCoy(),
						adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "O", "", "", 0.0,
						adbnoteh.getAdbnhInvbillno(), adbnoteh.getAdbnhInvbilldt().toString(), "", "Z",
						adbnoteh.getAdbnhPartycode());

				log.info("tdsAmountBreakup : {}", tdsAmountBreakup);
				log.info("bunumCounter : {}", bunumCounter);

			}

			List<ActrandBean> actrandList = setActrandBeanList(debitAmountBreakup, cgstAmountBreakup, sgstAmountBreakup,
					igstAmountBreakup, ugstAmountBreakup, tdsAmountBreakup);

			List<Actrand> actrandEntityList = new ArrayList<Actrand>();

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			log.info("actrand Entity List : {}", actrandEntityList);

			admbillhRepository.save(admbillh);

			admbilldRepository.saveAll(admbilldList);
			adbnotehRepository.deleteByAdbnotehCK_AdbnhDbnoteser(oldTranserNum);
			adbnotedRepository.deleteByAdbnotedCK_AdbndDbnoteser(oldTranserNum);
			actrandRepository.saveAll(actrandEntityList);
			actranhRepository.save(actranh);
			finalKeyValueList.put("newTranserNum", newTranserNum);
			finalKeyValueList.put("oldTranserNum", oldTranserNum);

			return new GenericResponse<>(true, "Debit Note cancellation Successful .", finalKeyValueList);

		} catch (ParseException e) {
			log.info("Error :", e);
			return new GenericResponse<>(false, "Debit Note cancellation Falied .");
		}

	}

	public List<ActrandBean> setActrandBeanList(List<ActrandBean> debitAmountBreakup,
			List<ActrandBean> cgstAmountBreakup, List<ActrandBean> sgstAmountBreakup,
			List<ActrandBean> igstAmountBreakup, List<ActrandBean> ugstAmountBreakup,
			List<ActrandBean> tdsAmountBreakup) throws ParseException {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

		List<List<ActrandBean>> listOfLists = new ArrayList<>();
		listOfLists.add(debitAmountBreakup);
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
		}
		log.info("actrandList : {}", actrandList);
		return actrandList;
	}

	private static boolean isValidDateFormat(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		try {
			// Parsing the input string
			Date parsedDate = dateFormat.parse(input);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public Double returnGstValue(Double igst, Double cgst, Double sgst, Double ugst) {

		if (igst > 0) {
			return igst;
		}
		if (cgst > 0) {
			return cgst + sgst;
		}
		if (ugst > 0) {
			return ugst;
		}
		return 0d;
	}

	public String setValueInParameters(String parameter) {
		if (Objects.isNull(parameter) || parameter.isEmpty()) {
			return " ";
		} else {
			return parameter;
		}
	}
}
