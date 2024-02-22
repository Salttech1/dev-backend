package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.overheads.dataentry.bean.request.LocationRequestBean;
import kraheja.adminexp.overheads.dataentry.entity.Location;
import kraheja.adminexp.overheads.dataentry.mappers.LocationEntityPojoMapper;
import kraheja.adminexp.overheads.dataentry.repository.LocationRepository;
import kraheja.adminexp.overheads.dataentry.service.LocationService;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;


@Service
@Transactional

public class LocationServiceImpl implements LocationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	
	@Override
	public ResponseEntity<?> fetchLocationByCode(String code) {
		// TODO Auto-generated method stub
		Location LocationEntity = this.locationRepository.findByCode(code);
		LOGGER.info("LocationEntity :: {}", LocationEntity);
		if (LocationEntity != null)
		{
			LOGGER.info("Location Code :: {}" , code);
			return ResponseEntity.ok(ServiceResponseBean.builder()
				.data(LocationEntityPojoMapper.fetchLocationEntityPojoMapper
				.apply(new Object[] {LocationEntity }))
				.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Location Code : " + code + " not found").build());
	}

	@Override
	public ResponseEntity<?> addLocation(LocationRequestBean locationRequestBean) {
		// TODO Auto-generated method stub

		String siteFromDBEntity = this.entityRepository
				.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, 
						CommonConstraints.INSTANCE.ENTITY_CHAR1);
		
		this.locationRepository.save(LocationEntityPojoMapper.
				addLocationEntityPojoMapper.apply(new Object[] {locationRequestBean,siteFromDBEntity}));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Location Code Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateLocation(LocationRequestBean locationRequestBean) {
		// TODO Auto-generated method stub
		String LocationCode = locationRequestBean.getCode().trim();
		Location LocationEntity = this.locationRepository.findByCode(LocationCode);
		LOGGER.info("LocationEntity :: {}", LocationEntity);
		if (LocationEntity != null)
		{
			this.locationRepository.save(LocationEntityPojoMapper
					.updateLocationEntityPojoMapper
					.apply(LocationEntity, locationRequestBean));
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Location Code updated Sucessfully").build());
		
	}
}
