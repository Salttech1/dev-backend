package kraheja.adminexp.billing.dataentry.adminBill.service.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.repository.AdmadvanceRepository1;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbilldRepository;
import kraheja.adminexp.billing.dataentry.adminBill.repository.AdmbillhRepository;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdminBillCancellationService;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.ActrandCK;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ActranhCK;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
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
public class AdminBillCancellationServiceImpl implements AdminBillCancellationService {

	private final AdmbillhRepository admbillhRepository;
	private final AdmbilldRepository admbilldRepository;
	private final ActrandRepository actrandRepository;
	private final AddressRepository addressRepository;
	private final PartyRepository partyRepository;
	private final CompanyRepository companyRepository;
	private final BuildingRepository buildingRepository;
	private final GenericAccountingLogic genericAccountingLogic;
	private final AdmadvanceRepository1 admadvanceRepository;
	private final ActranhRepository actranhRepository;
	private final EntityRepository entityRepository;

	public AdminBillCancellationServiceImpl(AdmbillhRepository admbillhRepository,
			AdmbilldRepository admbilldRepository, ActrandRepository actrandRepository, PartyRepository partyRepository,
			AddressRepository addressRepository, CompanyRepository companyRepository,
			BuildingRepository buildingRepository, GenericAccountingLogic genericAccountingLogic,
			ActranhRepository actranhRepository, AdmadvanceRepository1 admadvanceRepository,
			EntityRepository entityRepository) {
		this.admbillhRepository = admbillhRepository;
		this.admbilldRepository = admbilldRepository;
		this.actranhRepository = actranhRepository;
		this.actrandRepository = actrandRepository;
		this.companyRepository = companyRepository;
		this.buildingRepository = buildingRepository;
		this.addressRepository = addressRepository;
		this.partyRepository = partyRepository;
		this.genericAccountingLogic = genericAccountingLogic;
		this.admadvanceRepository = admadvanceRepository;
		this.entityRepository = entityRepository;
	}

	@Override
	public GenericResponse<AdminBillResponseBean> fetchAdmbillhBySer(String ser) {
		Admbillh admbillhEntity = this.admbillhRepository.findByAdmbillhCK_AdblhSer(ser);
		log.info("AdmbillhEntity :: {}", admbillhEntity);
		if (admbillhEntity != null) {
			Actranh oldActranh = actranhRepository.findByActranhCK_ActhTranser(ser);

			if ((Objects.isNull(oldActranh.getActhReverseyn()) ? "" : oldActranh.getActhReverseyn())
					.equalsIgnoreCase("Y")) {
				return new GenericResponse<>(false, "Admin Bill# " + ser + " already reversed.");
			}
			if ((Objects.isNull(admbillhEntity.getAdblhStatus()) ? 0
					: Double.parseDouble(admbillhEntity.getAdblhStatus())) != 7) {
				Party partyEntity = this.partyRepository
						.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(
								admbillhEntity.getAdblhPartycode().trim(),
								CommonUtils.INSTANCE.closeDateInLocalDateTime(),
								admbillhEntity.getAdblhPartytype().trim());

				Address addressEntity = this.addressRepository
						.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
								admbillhEntity.getAdblhPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment,
								CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
				log.info("Address :: {}", addressEntity);

				Building building = buildingRepository
						.findByBuildingCK_BldgCode(admbillhEntity.getAdblhBldgcode().trim());

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
				String stateName = entityRepository.fetchStateNameByEntClassAndEntId(addressEntity.getAdrState());
				log.info("stateName", stateName);

				List<Actrand> actrand = actrandRepository.findByActdTranser(ser);

				AdminBillResponseBean adminBillResponseBean = AdminBillResponseBean.builder()
						.admbilld(admbilldEntityList).admbillh(admbillhEntity).gstNo(partyEntity.getParGstno())
						.stateCode(addressEntity.getAdrState()).stateName(stateName).totPaidAdvance(totPaidAdvn)
						.adjustedAdvn(adjustedAdvn).docParCode(actrand.get(0).getActdDocparcode())
						.docParType(actrand.get(0).getActdDocpartype()).refPartyDesc(partyEntity.getParPartyname())
						.bldgDesc(building.getBldgName()).build();
				log.info("adminBillResponseBean", adminBillResponseBean);

				return new GenericResponse<>(true, "Admin Bill Retrieved Successfully", adminBillResponseBean);
			} else {
				return new GenericResponse<>(false, "This bill has already been paid.");
			}
		}

		return new GenericResponse<>(false, "No record found for your selections in Admbillh");
	}

	@Override
	public GenericResponse<String> cancelAdminBill(String ser) {

		log.info("Inside Fetch Admin Advance Bill Cancellation Service Implementation .");

		Admbillh admbillhEntity = this.admbillhRepository.findByAdmbillhCK_AdblhSer(ser);

		if (Objects.nonNull(admbillhEntity)) {
			if ((Objects.isNull(admbillhEntity.getAdblhStatus()) ? 0
					: Double.parseDouble(admbillhEntity.getAdblhStatus())) != 7) {

				if ((Objects.isNull(admbillhEntity.getAdblhDebitamt()) ? 0d : admbillhEntity.getAdblhDebitamt()) <= 0) {

					Actranh oldActranh = actranhRepository.findByActranhCK_ActhTranser(ser);

					if ((Objects.isNull(oldActranh.getActhReverseyn()) ? "" : oldActranh.getActhReverseyn()) != "Y") {
						List<Actrand> oldActrandList = actrandRepository.findActdByTranserNo(ser);

						String newTranSer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#BO");

						Actranh newActranh = new Actranh();
						ActranhCK newActranhCK = new ActranhCK();
						List<Actrand> newActrandList = new ArrayList<>();
						BeanUtils.copyProperties(oldActranh, newActranh);

						newActranhCK.setActhTranser(newTranSer);
						newActranhCK.setActhCoy(oldActranh.getActranhCK().getActhCoy());

						newActranh.setActranhCK(newActranhCK);
						newActranh.setActhNarrative("Reversal of #".concat(ser).concat(" for Invoice#")
								.concat(admbillhEntity.getAdblhSuppbillno()));
						newActranh.setActhTranamt(newActranh.getActhTranamt() * -1);
						newActranh.setActhReverseyn("Y");
						newActranh.setActhSite(GenericAuditContextHolder.getContext().getSite());
						newActranh.setActhUserid(GenericAuditContextHolder.getContext().getUserid());
						newActranh.setActhToday(LocalDateTime.now());

						for (Actrand oldActrand : oldActrandList) {
							Actrand newActrand = new Actrand();
							ActrandCK oldActrandCK = new ActrandCK();
							BeanUtils.copyProperties(oldActrand, newActrand);

							oldActrandCK.setActdTranser(newTranSer);
							oldActrandCK.setActdBunum(oldActrand.getActrandCK().getActdBunum());
							oldActrandCK.setActdCoy(oldActrand.getActrandCK().getActdCoy());

							newActrand.setActdSite(GenericAuditContextHolder.getContext().getSite());
							newActrand.setActdUserid(GenericAuditContextHolder.getContext().getUserid());
							newActrand.setActdToday(LocalDateTime.now());
							newActrand.setActrandCK(oldActrandCK);
							newActrand.setActdTranamt(newActrand.getActdTranamt() * -1);

							newActrandList.add(newActrand);
						}

						log.info("newActranh after updating values : {}", newActranh);
						log.info("newActrand after updating values : {}", newActrandList);

						oldActranh.setActhReverseyn("Y");
						oldActranh.setActhSite(GenericAuditContextHolder.getContext().getSite());
						oldActranh.setActhUserid(GenericAuditContextHolder.getContext().getUserid());
						oldActranh.setActhToday(LocalDateTime.now());

						// For deleting passed Admin Bill entries from actrand and actranh.
						actrandRepository.deleteActrand(admbillhEntity.getAdblhActranser());
						actranhRepository.deleteActranh(admbillhEntity.getAdblhActranser());

						// For deleting Admin Bill records from admbillh and admbilld after bill
						// cancellation.
						admbilldRepository.deleteByAdmbilldCK_AdbldSerNum(ser);
						admbillhRepository.deleteByAdmbillhCK_AdblhSer(ser);

						// Change the flag of acthReverseyn to Y after reversing the admin bill.
						actranhRepository.save(oldActranh);

						// Adding contra entries for balancing accounts data in actranh after reversing
						// the admin bill.
						actranhRepository.save(newActranh);

						// Adding contra entries for balancing accounts data in actrand after reversing
						// the admin bill.
						actrandRepository.saveAll(newActrandList);

						return new GenericResponse<>(true, "Admin Bill cancellation successful.");

					} else {
						return new GenericResponse<>(false, "Admin Bill# " + ser + " already reversed.", "4");
					}

				} else {
					return new GenericResponse<>(false,
							"Debit note entry has been done. Please delete debit note before deleting the bill.", "3");
				}
			} else {
				return new GenericResponse<>(false, "This bill has already been paid.", "2");
			}

		} else {
			return new GenericResponse<>(false, "Record not found.", "1");
		}
	}

}
