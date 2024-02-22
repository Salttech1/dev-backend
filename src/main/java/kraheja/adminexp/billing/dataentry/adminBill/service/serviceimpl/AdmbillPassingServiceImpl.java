package kraheja.adminexp.billing.dataentry.adminBill.service.serviceimpl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbillhRepository;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdmbillPassingService;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Company;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;

@Service
@Log4j2
@Transactional
public class AdmbillPassingServiceImpl implements AdmbillPassingService {

	private final AdmbillhRepository admbillhRepository;

	private final ActrandRepository actrandRepository;

	private final CompanyRepository companyRepository;

	private final GenericAccountingLogic genericAccountingLogic;

	public AdmbillPassingServiceImpl(AdmbillhRepository admbillhRepository, ActrandRepository actrandRepository,
			CompanyRepository companyRepository, GenericAccountingLogic genericAccountingLogic) {
		this.admbillhRepository = admbillhRepository;
		this.actrandRepository = actrandRepository;
		this.companyRepository = companyRepository;
		this.genericAccountingLogic = genericAccountingLogic;
	}

	@SuppressWarnings("unused")
	public GenericResponse<Admbillh> fetchAdmbillhSer(String ser) {
		log.info("Inside Fetch Admin Advance Payment Passing Service Implementation .");

		Admbillh admbillhEntity = this.admbillhRepository.findByAdmbillhCK_AdblhSer(ser);

		log.info("Admbillh Entity : {} ", admbillhEntity.toString());

		if (admbillhEntity == null) {

			return new GenericResponse<>(false, "No record found for your selections in Admbillh");

		}

		if (Objects.nonNull(admbillhEntity.getAdblhStatus())) {

			return new GenericResponse<>(false, "Payment already passed for the given serial number");

		}

		return new GenericResponse<>(true, "Data fetched successfully", admbillhEntity);

	}

	@SuppressWarnings("unused")
	public GenericResponse<String> adminBillPassing(String ser) {
		log.info("Inside Update Admin Bill Payment Passing Service Implementation .");
		Admbillh admbillhEntity = this.admbillhRepository.findByAdmbillhCK_AdblhSer(ser);

		if (admbillhEntity != null) {

			Company companyEntity = companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(
					admbillhEntity.getAdblhCoy().trim(), CommonUtils.INSTANCE.closeDate());
			List<Actrand> actrandEntityList = new ArrayList<Actrand>();
			Integer bunumCounter = 1;
//			String tranSer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#TSER", "#P",
//					GenericAuditContextHolder.getContext().getSite());

			String tranSer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#P");

			Double payAmount = admbillhEntity.getAdblhBillamount()
					- (Objects.isNull(admbillhEntity.getAdblhTdsamount()) ? 0d : admbillhEntity.getAdblhTdsamount())
					- (Objects.isNull(admbillhEntity.getAdblhAdvnadjust()) ? 0d : admbillhEntity.getAdblhAdvnadjust())
					- (Objects.isNull(admbillhEntity.getAdblhDebitamt()) ? 0d : admbillhEntity.getAdblhDebitamt());

			
			Double basicAmount= admbillhEntity.getAdblhBillamount()
					- (Objects.isNull(admbillhEntity.getAdblhTdsamount()) ? 0d : admbillhEntity.getAdblhTdsamount());
					
			genericAccountingLogic.updateActranh(tranSer, new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "GL",
					admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode(), payAmount,
					admbillhEntity.getAdmbillhCK().getAdblhSer(), new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
					"N", null, null, null, null, admbillhEntity.getAdblhNarration(),
					admbillhEntity.getAdblhCoy().trim(), "Y", "N", "N", "PF", false);
			List<ActrandBean> basicAmountBreakup = new ArrayList<>();
			List<ActrandBean> adjustedAdvanceBreakup = new ArrayList<>();
			List<ActrandBean> tdsAmountBreakup = new ArrayList<>();

			basicAmountBreakup = GenericAccountingLogic.initialiseActrandBreakups("PF", "11401026", " ", " ",
					admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode(),
					admbillhEntity.getAdblhProject(), admbillhEntity.getAdblhAcminor(), "80000006", " ",
					admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode(), "GL",
					admbillhEntity.getAdblhPartycode(), 0d, basicAmount * -1, admbillhEntity.getAdblhBldgcode(), "", "", "",
					"", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), bunumCounter,
					admbillhEntity.getAdblhNarration(), tranSer, "GL", companyEntity.getCompanyCK().getCoyProp(),
					admbillhEntity.getAdblhCoy(), admbillhEntity.getAdmbillhCK().getAdblhSer(),
					new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "",Objects.nonNull(admbillhEntity.getAdblhFromdate())
					?admbillhEntity.getAdblhFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): "",
					Objects.nonNull(admbillhEntity.getAdblhTodate())?admbillhEntity.getAdblhTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): "",
					"", "", "", "",0.0, admbillhEntity.getAdblhSuppbillno().trim(),
					admbillhEntity.getAdblhSuppbilldt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "",
					admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode());

			bunumCounter += 2;

			log.info("basicAmountbreakup : {}", basicAmountBreakup);
			log.info("bunumCounter : {}", bunumCounter);

			if ((Objects.isNull(admbillhEntity.getAdblhAdvnadjust()) ? 0d : admbillhEntity.getAdblhAdvnadjust()) > 0) {
				adjustedAdvanceBreakup = GenericAccountingLogic.initialiseActrandBreakups("PF", "11401026", " ", " ",
						admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode(),
						admbillhEntity.getAdblhProject(), admbillhEntity.getAdblhAcminor(), "80000006", " ",
						admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode(), "GL",
						admbillhEntity.getAdblhPartycode(), 0d, admbillhEntity.getAdblhAdvnadjust() * -1,
						admbillhEntity.getAdblhBldgcode(), "", "", "", "",
						new SimpleDateFormat("dd/MM/yyyy").format(new Date()), bunumCounter, "Advance Adjusted",
						tranSer, "GL", companyEntity.getCompanyCK().getCoyProp(), admbillhEntity.getAdblhCoy(),
						admbillhEntity.getAdmbillhCK().getAdblhSer(),
						new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "",
						Objects.nonNull(admbillhEntity.getAdblhFromdate())?admbillhEntity.getAdblhFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): "",
						Objects.nonNull(admbillhEntity.getAdblhTodate())?admbillhEntity.getAdblhTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): "",
						"", "", "","", 0.0, admbillhEntity.getAdblhSuppbillno().trim(),
						admbillhEntity.getAdblhSuppbilldt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "",
						admbillhEntity.getAdblhPartytype(), admbillhEntity.getAdblhPartycode());

				
				bunumCounter += 2;
				log.info("adjustedAdvanceBreakup : {}", adjustedAdvanceBreakup);
				log.info("bunumCounter : {}", bunumCounter);
			}

			List<List<ActrandBean>> listOfLists = new ArrayList<>();
			listOfLists.add(basicAmountBreakup);
			listOfLists.add(adjustedAdvanceBreakup);

			List<ActrandBean> actrandList = listOfLists.stream().flatMap(List::stream).collect(Collectors.toList());
			
			log.info("actrandList : {}", actrandList);

			actrandEntityList.addAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));

			for(Actrand e :actrandEntityList) {
				e.setActdNarrative(admbillhEntity.getAdblhNarration());
				e.setActdCfgroup("");
			}
			
			actrandRepository.saveAll(actrandEntityList);

			admbillhEntity.setAdblhActranser(tranSer);
			admbillhEntity.setAdblhPaidamount(payAmount);
			admbillhEntity.setAdblhStatus("5");
			admbillhEntity.setAdblhPassedon(LocalDate.now());
			admbillhEntity.setAdblhSite(GenericAuditContextHolder.getContext().getSite());
			admbillhEntity.setAdblhUserid(GenericAuditContextHolder.getContext().getUserid());
			admbillhEntity.setAdblhToday(LocalDateTime.now());

			admbillhRepository.save(admbillhEntity);
			return new GenericResponse<>(true, "Admin Bill Passed Successfully.", tranSer);

		}

		else {
			return new GenericResponse<>(false, "Admin Bill could not be passed.");
		}

	}

}
