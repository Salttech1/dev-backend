package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.overheads.dataentry.entity.Location;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.mappers.LocationEntityPojoMapper;
import kraheja.sales.bookings.dataentry.mappers.FlatsEntityPojoMapper;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.FlatsRequestBean;
import kraheja.sales.bookings.dataentry.service.FlatsService;
import kraheja.sales.entity.Flats;
import kraheja.sales.repository.FlatsRepository;

@Service
@Transactional
public class FlatsServiceImpl implements FlatsService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private FlatsRepository flatsRepository;

	@Autowired
	private EntityRepository entityRepository;

	
	@Override
	public ResponseEntity<?> fetchFlatsByBldgcodeAndWingAndFlatnum(String bldgcode, String wing, String flatnum) {
		Flats flatsEntity = this.flatsRepository
				.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(bldgcode, wing, flatnum);
		logger.info("FlatsEntity :: {}", flatsEntity);
		if (flatsEntity != null) {

			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(FlatsEntityPojoMapper.fetchFlatsEntityPojoMapper.apply(new Object[] { flatsEntity }))
					.status(Boolean.TRUE).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> addFlats(FlatsRequestBean flatsRequestBean) {

		Flats flats = this.flatsRepository.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(
				flatsRequestBean.getBldgcode(), flatsRequestBean.getWing(), flatsRequestBean.getFlatnum());

		logger.info("LocationEntity :: {}", flats);
		if (flats == null) {

			String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(
					CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

			this.flatsRepository.save(FlatsEntityPojoMapper.addFlatsEntityPojoMapper
					.apply(new Object[] { flatsRequestBean, siteFromDBEntity }));

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Flat Added Successfully").build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Inserted.").build());

	}

	@Override
	public ResponseEntity<?> deleteFlatByBldgCodeAndWingAndFlatnum(String bldgCode, String wing, String flatnum) {
		// TODO Auto-generated method stub
		Flats existingFlat = this.flatsRepository
				.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(bldgCode, wing, flatnum);
		logger.info("Flat Entity :: {}", existingFlat);
		if (Objects.nonNull(existingFlat)) {
			this.flatsRepository.delete(existingFlat);
			return ResponseEntity.ok(
					ServiceResponseBean.builder().status(Boolean.TRUE).message("Flat Deleted Successfully!").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Flat Found.").build());
	}

	@Override
	public ResponseEntity<?> updateFlats(FlatsRequestBean flatsRequestBean) {
		// TODO Auto-generated method stub

		Flats flats = this.flatsRepository.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(
				flatsRequestBean.getBldgcode(), flatsRequestBean.getWing(), flatsRequestBean.getFlatnum());

		logger.info("LocationEntity :: {}", flats);
		if (flats != null) {
			this.flatsRepository.save(FlatsEntityPojoMapper.updateFlatsEntityPojoMapper.apply(flats, flatsRequestBean));
		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Flat updated Sucessfully").build());

	}

	@Override
	public ResponseEntity<?> deleteFlats(String bldgcode, String wing, String flatnum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkBldgcodeAndWingAndFlatnumExists(String bldgcode, String wing, String flatnum) {
		// TODO Auto-generated method stub
		
		Flats existingFlat = this.flatsRepository
				.findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(bldgcode, wing, flatnum);

		if (Objects.isNull(existingFlat))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("This Flat Details alreadly Exist.").build());
		
		
	}

}
