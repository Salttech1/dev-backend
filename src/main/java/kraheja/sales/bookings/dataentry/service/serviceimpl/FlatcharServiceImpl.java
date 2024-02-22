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
import kraheja.sales.bean.request.FlatcharRequestBean;
import kraheja.sales.bookings.dataentry.mappers.FlatcharEntityPojoMapper;
import kraheja.sales.bookings.dataentry.service.FlatcharService;
import kraheja.sales.entity.Flatchar;
import kraheja.sales.repository.FlatcharRepository;

@Service
@Transactional
public class FlatcharServiceImpl implements FlatcharService {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private FlatcharRepository flatcharRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchFlatcharByBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWing(String bldgcode,
			String flatnum, String accomtype, String chargecode, String wing) {

		List<Flatchar> flatcharEntity = this.flatcharRepository
				.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(bldgcode, flatnum, wing);

		logger.info("FlatcharEntity :: {}", flatcharEntity);

		if (Objects.nonNull(flatcharEntity)) {
			logger.info("flat Char :: {}", bldgcode, flatnum, accomtype, chargecode, wing);
			return ResponseEntity.ok(ServiceResponseBean.builder()
					.data(FlatcharEntityPojoMapper.fetchFlatcharEntityPojoMapper.apply(flatcharEntity))
					.status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@Override
	public ResponseEntity<?> addFlatchar(List<FlatcharRequestBean> flatcharRequestBean) {

		this.flatcharRepository
				.saveAll(FlatcharEntityPojoMapper.addFlatcharPojoEntityMapper.apply(flatcharRequestBean));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("flat Char Data Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateFlatchar(List<FlatcharRequestBean> flatcharRequestBeanList,String bldgcode, String flatnum,String wing) 
	{

		// List<Flatchar> flatcharEntityList=new ArrayList<>();
		List<Flatchar> flatcharEntityList = this.flatcharRepository
				.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(null, null, null);

		if (Objects.nonNull(flatcharEntityList)) {
			this.flatcharRepository.deleteAll(flatcharEntityList);

			flatcharEntityList
					.addAll(FlatcharEntityPojoMapper.addFlatcharPojoEntityMapper.apply(flatcharRequestBeanList));

			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Data Not updated In flatChar Table.").build());

		// List<Flatowner> flatownerEntityList = this.flatownerRepository
		// .findByFlatownerCK_FownOwnerid(ownerId);

		// findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing
//		if(Objects.nonNull(flatcharRequestBeanList)) {
//		
//			flatcharRequestBeanList.stream().map(flatcharRequestBean->{
//				
//				if(flatcharRequestBean.getIsUpdate()) {
//					deleteFlatchar(
//							flatcharRequestBean.getBldgcode(),
//							flatcharRequestBean.getFlatnum(),
//							flatcharRequestBean.getAccomtype(),
//							flatcharRequestBean.getChargecode(),
//							flatcharRequestBean.getWing());
//	
//					}
////				else
////					{
////					Flatchar flatcharEntity = this.flatcharRepository
////							.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchAccomtypeAndFlatcharCK_FchChargecodeAndFlatcharCK_FchWing(
////						flatcharRequestBean.getBldgcode(), flatcharRequestBean.getFlatnum(),
////						flatcharRequestBean.getAccomtype(), flatcharRequestBean.getChargecode(),
////						flatcharRequestBean.getWing());
////						
////						if(Objects.nonNull(flatcharEntity )) {
////							flatcharEntityList.add(FlatcharEntityPojoMapper
////									.updateFlatcharEntityPojoMapper.apply(flatcharEntity, flatcharRequestBean));
////						}
////					else
////						{
////						flatcharEntityList.addAll(FlatcharEntityPojoMapper
////								.addFlatcharPojoEntityMapper.apply(Arrays.asList(flatcharRequestBean)));						
////						}
////				}
//				
//				flatcharEntityList.addAll(FlatcharEntityPojoMapper
//						.addFlatcharPojoEntityMapper.apply(Arrays.asList(flatcharRequestBean)));	
//				
//   				return flatcharRequestBean ;
//			}).collect(Collectors.toList());
//		}
//		
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());

	}

	@Override
	public ResponseEntity<?> deleteFlatchar(String bldgcode, String flatnum, String accomtype, String chargecode,
			String wing) {
		// TODO Auto-generated method stub
//		Flatchar flatcharEntity = this.flatcharRepository.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchAccomtypeAndFlatcharCK_FchChargecodeAndFlatcharCK_FchWing(
//				bldgcode, 
//				flatnum, 
//				accomtype, 
//				chargecode, 
//				wing);
//				
//		logger.info("flatcharEntity :: {}", flatcharEntity);
//		if (flatcharEntity != null)
//		{
//			this.flatcharRepository.save(FlatcharEntityPojoMapper
//					.updateFlatcharEntityPojoMapper
//					.apply(flatcharEntity));
//		}
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Flat Char updated Sucessfully").build());
//		
		return null;

	}

	@Override
	public ResponseEntity<?> checkBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWingExists(String bldgcode,
			String flatnum, String accomtype, String chargecode, String wing) {
		// TODO Auto-generated method stub

		Flatchar flatcharEntity = this.flatcharRepository
				.findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchAccomtypeAndFlatcharCK_FchChargecodeAndFlatcharCK_FchWing(
						bldgcode, flatnum, accomtype, chargecode, wing);

		if (Objects.isNull(flatcharEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("flat char Data Not found.").build());

		// return null;
	}
}