package kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.serviceImpl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.mappers.AdmadvanceEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.repository.AdmadvanceRepository1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.AdminAdvancePaymentPassingService;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Company;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class AdminAdvancePaymentPassingServiceImpl implements AdminAdvancePaymentPassingService {

	@Autowired
	AdmadvanceRepository1 admadvanceRepository;

	@Autowired
	ActranhRepository actranhRepository;

	@Autowired
	ActrandRepository actrandRepository;

	@Autowired
	PartyRepository partyRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	private GenericAccountingLogic genericAccountingLogic;

	@SuppressWarnings("unused")
	@Override
	public GenericResponse<Admadvance1> fetchAdmadvanceByPinvAndSer(String pinv, String ser) {
		log.info("Inside Fetch Admin Advance Payment Passing Service Implementation .");

		List<Admadvance1> admadvanceEntityList = this.admadvanceRepository.findByAdmadvanceCK_AdvnSerOrAdvnPinvno(ser,
				pinv);

		Admadvance1 admadvanceEntity = admadvanceEntityList.get(0);

		log.info("Admadvance Entity : {} ", admadvanceEntity.toString());

		if (admadvanceEntity == null) {

			return new GenericResponse<>(false, "No record found for your selections in Admadvance");

		}

		if (Objects.nonNull(admadvanceEntity.getAdvnStatus())) {

			return new GenericResponse<>(false, "Payment already passed for the given serial number");

		}

		return new GenericResponse<>(true, "Data fetched successfully", admadvanceEntity);

	}

	@SuppressWarnings("unused")
	@Override
	public GenericResponse<Void> updateAdmadvance(String ser) {
		log.info("Inside Update Admin Advance Payment Passing Service Implementation .");
		Admadvance1 admadvanceEntity = this.admadvanceRepository.findByAdmadvanceCK_AdvnSer(ser);

		if (admadvanceEntity != null) {

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					admadvanceEntity.getAdvnCoy().trim(), CommonUtils.INSTANCE.closeDate());
			List<Actrand> actrandEntityList = new ArrayList<Actrand>();
			Integer bunumCounter = 1;
			/* same in all breakups */
			String tranSer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "PMSER",
					GenericAuditContextHolder.getContext().getSite());

			Double tranAmount = Double
					.valueOf(admadvanceEntity.getAdvnAdvanceamt() - admadvanceEntity.getAdvnTdsamount());

			genericAccountingLogic.updateActranh(tranSer, new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "GL",
					admadvanceEntity.getAdvnPartytype(), admadvanceEntity.getAdvnPartycode(), tranAmount,
					admadvanceEntity.getAdvnActranser(), admadvanceEntity.getAdvnActranser(), "N", null, null, null, null,
					admadvanceEntity.getAdvnNarration(), admadvanceEntity.getAdvnCoy().trim(), "Y", "N", "N", "PF",
					false);
			List<ActrandBean> basicAmountBreakup = new ArrayList<>();
			List<ActrandBean> gstAmountBreakup = new ArrayList<>();
			List<ActrandBean> tdsAmountBreakup = new ArrayList<>();
			
			Double basicAmount=admadvanceEntity.getAdvnBasicamt() + admadvanceEntity.getAdvnFotoamount();

			basicAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("PF", "11401026",
					admadvanceEntity.getAdvnPartytype(), "", admadvanceEntity.getAdvnPartytype(),
					admadvanceEntity.getAdvnPartycode(), admadvanceEntity.getAdvnProject(),
					admadvanceEntity.getAdvnPartycode(), "80000006", " ", "", "", "GL",
					admadvanceEntity.getAdvnPartycode(), -1 * basicAmount,
					admadvanceEntity.getAdvnBldgcode(), "", "", new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
					bunumCounter, "Advance Money - Basic Amount", tranSer, "GL",
					companyEntity.getCompanyCK().getCoyProp(), admadvanceEntity.getAdvnCoy(),
					admadvanceEntity.getAdmadvanceCK().getAdvnSer(), admadvanceEntity.getAdvnPinvdate().toString(), "", "", "", "",
					0.0, admadvanceEntity.getAdmadvanceCK().getAdvnSer(),
					admadvanceEntity.getAdvnDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "A",
					admadvanceEntity.getAdvnPartytype(), admadvanceEntity.getAdvnPartycode());

			bunumCounter += 2;

			log.info("basicAmountbreakup : {}", basicAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			if (admadvanceEntity.getAdvnGstamt() > 0) {
				gstAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("PF", "11401026",
						admadvanceEntity.getAdvnPartytype(), " ", admadvanceEntity.getAdvnPartytype(),
						admadvanceEntity.getAdvnPartycode(), admadvanceEntity.getAdvnProject(),
						admadvanceEntity.getAdvnPartycode(), "80000006", " ", "", "", "GL",
						admadvanceEntity.getAdvnPartycode(), -1 * admadvanceEntity.getAdvnGstamt(),
						admadvanceEntity.getAdvnBldgcode(), "", "",
						new SimpleDateFormat("dd/MM/yyyy").format(new Date()), bunumCounter, "Advance Money - GST",
						tranSer, "GL", companyEntity.getCompanyCK().getCoyProp(), admadvanceEntity.getAdvnCoy(),
						admadvanceEntity.getAdmadvanceCK().getAdvnSer(), admadvanceEntity.getAdvnPinvdate().toString(), "", "", "",
						"", 0.0, admadvanceEntity.getAdmadvanceCK().getAdvnSer(),
						admadvanceEntity.getAdvnDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "A",
						admadvanceEntity.getAdvnPartytype(), admadvanceEntity.getAdvnPartycode());

				bunumCounter += 2;
			}

			log.info("gstAmountbreakup : {}", gstAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			// Made changes to fix the error encountered because of .isBlank()
			if (admadvanceEntity.getAdvnTdsamount() > 0 && !admadvanceEntity.getAdvnTdsacmajor().isEmpty()) {
				tdsAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("PF",
						admadvanceEntity.getAdvnTdsacmajor().trim(), " ", "", admadvanceEntity.getAdvnPartytype(),
						admadvanceEntity.getAdvnPartycode(), admadvanceEntity.getAdvnProject(),
						admadvanceEntity.getAdvnPartycode(), "80000006", " ", "", "", "GL",
						admadvanceEntity.getAdvnPartycode(),admadvanceEntity.getAdvnTdsamount(),
						admadvanceEntity.getAdvnBldgcode(), "", "",
						new SimpleDateFormat("dd/MM/yyyy").format(new Date()), bunumCounter, "Advance Money - TDS",
						tranSer, "GL", companyEntity.getCompanyCK().getCoyProp(), admadvanceEntity.getAdvnCoy(),
						admadvanceEntity.getAdmadvanceCK().getAdvnSer(), admadvanceEntity.getAdvnPinvdate().toString(), "", "", "",
						"", 0.0, admadvanceEntity.getAdmadvanceCK().getAdvnSer(),
						admadvanceEntity.getAdvnDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "A",
						admadvanceEntity.getAdvnPartytype(), admadvanceEntity.getAdvnPartycode());

				bunumCounter += 2;
			}

			log.info("tdsAmountbreakup : {}", basicAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			List<List<ActrandBean>> listOfLists = new ArrayList<>();
			listOfLists.add(basicAmountBreakup);
			listOfLists.add(gstAmountBreakup);
			if (Objects.nonNull(tdsAmountBreakup)) {
				listOfLists.add(tdsAmountBreakup);
			}

			List<ActrandBean> actrandList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());

			log.info("actrandList : {}", actrandList);

			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

			for (ActrandBean actrandBean : actrandList) {
				Date date;
				try {
					date = inputFormat.parse(actrandBean.getVoudate());
				} catch (ParseException e) {
					return new GenericResponse<>(false, "Admin advance Payment could not be passed.");
				}
				String formattedDate = outputFormat.format(date);
				actrandBean.setVoudate(formattedDate);

			}

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			actrandRepository.saveAll(actrandEntityList);

			admadvanceEntity.setAdvnActranser(tranSer);
			admadvanceEntity.setAdvnPaidamount(tranAmount);
			admadvanceEntity.setAdvnStatus("5");
			admadvanceEntity.setAdvnPassedon(LocalDateTime.now());
			admadvanceEntity.setAdvnSite(GenericAuditContextHolder.getContext().getSite());
			admadvanceEntity.setAdvnUserid(GenericAuditContextHolder.getContext().getUserid());
			admadvanceEntity.setAdvnToday(LocalDateTime.now());

			admadvanceRepository.save(admadvanceEntity);
			return new GenericResponse<>(true, "Admin advance Bill Passed Successfully.");

		}

		else {
			return new GenericResponse<>(false, "Admin advance Payment could not be passed.");
		}

	}

}