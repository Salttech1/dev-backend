package kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.serviceImpl;

import java.text.ParseException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmadvanceResponseBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.mappers.AdmadvanceEntityPojoMapper;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.repository.AdmadvanceRepository1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.AdminAdvanceBillPaymentService;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class AdminAdvancePaymentImpl implements AdminAdvanceBillPaymentService {

	@Autowired
	AdmadvanceRepository1 admadvanceRepository;

	@Autowired
	BuildingRepository buildingRepository;

	@Override
	public GenericResponse<AdmadvanceResponseBean> fetchAdmadvanceBySer(String ser) {
		log.info("Inside fetch Admin Advance Bill Service Implementation .");
		Admadvance1 admadvanceEntity = this.admadvanceRepository.findByAdmadvanceCK_AdvnSer(ser);
		try {
			if (admadvanceEntity != null) {

				if (Objects.nonNull(admadvanceEntity.getAdvnStatus())) {
					return new GenericResponse<>(false,
							"You can't modify this bill. This bill is already passed/paid.");
				}

				AdmadvanceResponseBean admadvanceResponseBean = AdmadvanceEntityPojoMapper.fetchAdmadvanceEntityPojoMapper
						.apply(new Object[] { admadvanceEntity });
				log.info("admadvanceResponseBean : {}",admadvanceResponseBean);
				return new GenericResponse<>(true, "Data fetched successfully", admadvanceResponseBean);

			}
			return new GenericResponse<>(false, "No record found for your selections in Admadvance");
		} catch (NumberFormatException nfe) {
			return new GenericResponse<>(false, "Error occured while fetching data.");
		}

	}

	@Override
	public GenericResponse<String> addAdmadvance(AdmadvanceRequestBean admadvanceRequestBean) throws ParseException {
		log.info("Inside add Admin Advance Bill Service Impelentation .");

		if (admadvanceRequestBean != null) {

			String ser = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "#AAB",
					GenericAuditContextHolder.getContext().getSite());

			Building building = buildingRepository
					.findByBuildingCK_BldgCode(admadvanceRequestBean.getBldgcode().trim());

			log.info("Admin Advance Serial :: {}", ser);
			admadvanceRequestBean.setProject(building.getBldgProject());

			Admadvance1 admadvance = AdmadvanceEntityPojoMapper.addAdmadvancePojoEntityMapper(ser)
					.apply(admadvanceRequestBean);

			this.admadvanceRepository.save(admadvance);

			return new GenericResponse<>(true, "Admin advance Bill Saved Successfully.", ser);
		}
		return new GenericResponse<>(false, "Admin advance Bill could not be saved.");
	}

	@Override
	public GenericResponse<Void> updateAdmadvance(AdmadvanceRequestBean admadvanceRequestBean) throws ParseException {

		log.info("Inside Update Admin Advance Bill Service Implementation .");
		Admadvance1 admadvanceEntity = this.admadvanceRepository
				.findByAdmadvanceCK_AdvnSer(admadvanceRequestBean.getSer());

		if (admadvanceEntity != null) {

			Long status;
			String advnStatus = admadvanceEntity.getAdvnStatus();

			if (advnStatus == null) {
				status = 0L;
			} else {
				status = Long.parseLong(admadvanceEntity.getAdvnStatus());
			}

			if (status == 5 || status == 7) {

				return new GenericResponse<>(false, "You can't modify this bill. This bill is already passed/paid.");

			} else {

				Admadvance1 admadvance = AdmadvanceEntityPojoMapper.updateAdmadvanceEntityPojoMapper
						.apply(admadvanceEntity, admadvanceRequestBean);

				this.admadvanceRepository.save(admadvance);

				return new GenericResponse<>(true, "Admin advance Bill Updated Successfully.");
			}
		}
		return new GenericResponse<>(false, "No record found for your selections in Admadvance");
	}

}
