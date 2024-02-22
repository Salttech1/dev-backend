package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.sales.bean.request.FlatownerRequestBean;
import kraheja.sales.bookings.dataentry.mappers.FlatownerEntityPojoMapper;
import kraheja.sales.bookings.dataentry.service.FlatownerService;
import kraheja.sales.entity.Flatowner;
import kraheja.sales.repository.FlatownerRepository;

@Service
@Transactional
public class FlatownerServiceImpl implements FlatownerService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private FlatownerRepository flatownerRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchFlatownerByOwneridAndBldgcodeAndWingAndFlatnumAndOwnertype(String ownerid,
			String bldgcode, String wing, String flatnum, String ownertype) {

//		Flatowner flatownerEntity = this.flatownerRepository
//				.findByFlatownerCK_FownOwneridAndFlatownerCK_FownBldgcodeAndFlatownerCK_FownWingAndFlatownerCK_FownFlatnumAndFlatownerCK_FownOwnertype(
//						ownerid, bldgcode, wing, flatnum, ownertype);
		
		List<Flatowner> flatownerEntity = this.flatownerRepository
				.findByFlatownerCK_FownOwnerid(ownerid);
		
		logger.info("FlatownerEntity :: {}", flatownerEntity);
		
		if (flatownerEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					FlatownerEntityPojoMapper.fetchFlatownerEntityPojoMapper.apply( flatownerEntity ))
					.build());
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	
	@Override
	public ResponseEntity<?> updateFlatowner(List<FlatownerRequestBean> flatownerRequestBeanList,String ownerId) {
	
		//List<Flatowner> flatownerEntityList=new ArrayList<>();
		
		List<Flatowner> flatownerEntityList = this.flatownerRepository
				.findByFlatownerCK_FownOwnerid(ownerId);
		
		if(Objects.nonNull(flatownerEntityList)) {
			
			//deleteAllFlatowner(flatownerRequestBean.getOwnerid());
			//this.flatownerRepository.deleteAll(flatownerEntityList);
			this.flatownerRepository.deleteflatOwnerByOwnerId(ownerId);
			this.flatownerRepository.saveAll(FlatownerEntityPojoMapper.addFlatownerPojoEntityMapper.apply(flatownerRequestBeanList));
			
//			flatownerEntityList.addAll(FlatownerEntityPojoMapper
//					.addFlatownerPojoEntityMapper.apply(flatownerRequestBeanList));
//			flatownerRequestBeanList.stream().map(flatownerRequestBean->{
//			
//				flatownerEntityList.addAll(FlatownerEntityPojoMapper
//						.addFlatownerPojoEntityMapper.apply(Arrays.asList(flatownerRequestBean)));
//				
//   				return flatownerRequestBean ;
//			}).collect(Collectors.toList());
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		}
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not updated In FlatOwner.").build());

	}

	@Override
	public ResponseEntity<?> deleteAllFlatowner(String ownerid) {
		
	List<Flatowner> flatowner =this.flatownerRepository.findByFlatownerCK_FownOwnerid(ownerid);
	if(Objects.nonNull(flatowner)) {
		this.flatownerRepository.deleteAll(flatowner);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Flat Owner Deleted Successfully!").build());
	}
	return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Flat Found.").build());
	}
	
	
	@Override
	public ResponseEntity<?> deleteFlatowner(String ownerid, String bldgcode, String wing, String flatnum,
			String ownertype) {

		Flatowner flatowner = this.flatownerRepository
				.findByFlatownerCK_FownOwneridAndFlatownerCK_FownBldgcodeAndFlatownerCK_FownWingAndFlatownerCK_FownFlatnumAndFlatownerCK_FownOwnertype(
						ownerid, bldgcode, wing, flatnum, ownertype);

		logger.info("Flat Entity :: {}", flatowner);
		if (Objects.nonNull(flatowner)) {
			this.flatownerRepository.delete(flatowner);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.message("Flat Owner Deleted Successfully!").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Flat Found.").build());

	}

	@Override
	public ResponseEntity<?> checkOwneridAndBldgcodeAndWingAndFlatnumAndOwnertypeExists(String ownerid, String bldgcode,
			String wing, String flatnum, String ownertype) {
		// TODO Auto-generated method stub

		Flatowner flatowner = this.flatownerRepository
				.findByFlatownerCK_FownOwneridAndFlatownerCK_FownBldgcodeAndFlatownerCK_FownWingAndFlatownerCK_FownFlatnumAndFlatownerCK_FownOwnertype(
						ownerid, bldgcode, wing, flatnum, ownertype);

		if (Objects.isNull(flatowner))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(
				ServiceResponseBean.builder().status(Boolean.FALSE).message("This Flat Owner alreadly Exist.").build());

	}

	@Override
	public ResponseEntity<?> addFlatowner(List<FlatownerRequestBean> flatownerRequestBean) {
		// TODO Auto-generated method stub
		this.flatownerRepository
		.saveAll(FlatownerEntityPojoMapper.addFlatownerPojoEntityMapper.apply(flatownerRequestBean));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Flat Owner Added Successfully").build());

	}


	@Override
	public ResponseEntity<?> checkOwneridExists(String ownerid) {
		// TODO Auto-generated method stub
		return null;
	}


	
}