package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.sales.bean.request.FlatpayRequestBean;
import kraheja.sales.bookings.dataentry.mappers.FlatpayEntityPojoMapper;
import kraheja.sales.bookings.dataentry.service.FlatpayService;
import kraheja.sales.entity.Flatpay;
import kraheja.sales.repository.FlatpayRepository;

@Service
@Transactional
public class FlatpayServiceImpl implements FlatpayService {

	@Autowired
	private FlatpayRepository flatpayRepository;

//	@Autowired
//	private EntityRepository entityRepository;

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final LocalDateTime NULL = null;

	@Override
	public ResponseEntity<?> fetchFlatpayByBldgcodeAndFlatnumAndOwneridAndDuedateAndPaiddateAndNarrative(
			String bldgcode, String flatnum, String ownerid, String duedate, String paiddate,
			String narrative) {

		List<Flatpay> flatpayEntity = this.flatpayRepository
								.FindByFlatpayOwnerId(ownerid);
				
		logger.info("FlatpayEntity :: {}", flatpayEntity);
		
		if (Objects.nonNull(flatpayEntity)) {

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(FlatpayEntityPojoMapper.fetchFlatpayEntityPojoMapper.apply(flatpayEntity))
					.build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());

	}

	@Override
	public ResponseEntity<?> addFlatpay(List<FlatpayRequestBean> flatpayRequestBean) {
		// TODO Auto-generated method stub

		this.flatpayRepository
				.saveAll(FlatpayEntityPojoMapper.addFlatpayPojoEntityMapper.apply( flatpayRequestBean));

		return ResponseEntity.ok(
				ServiceResponseBean.builder().status(Boolean.TRUE).message("Flat Owner Added Successfully").build());

	}
	
	@Override
	public ResponseEntity<?> updateFlatpay(List<FlatpayRequestBean> flatpayRequestBeanList,String ownerId) {
		
		//List<Flatpay> flatpayEntityList=new ArrayList<>();
		
		List<Flatpay> flatpayEntityList = this.flatpayRepository
				.findByFlatpayCK_FpayOwnerid(ownerId);
				
		if(Objects.nonNull(flatpayEntityList)) {
			
			//this.flatpayRepository.deleteAll(flatpayEntityList);
			this.flatpayRepository.deleteflatPayByOwnerId(ownerId);
			
			flatpayEntityList.addAll(FlatpayEntityPojoMapper
					.addFlatpayPojoEntityMapper.apply(flatpayRequestBeanList));
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
			
//			flatpayRequestBeanList.stream().map(flatpayRequestBean->{
//				
//				if(flatpayRequestBean.getIsUpdate()) {
//					deleteFlatpay(
//							flatpayRequestBean.getBldgcode(),
//							flatpayRequestBean.getFlatnum(),
//							flatpayRequestBean.getOwnerid(),
//							NULL,
//							NULL,
//							flatpayRequestBean.getNarrative());
//	
//					}
//				else
//					{
//					Flatpay flatpayEntity = this.flatpayRepository
//							.findByFlatpayCK_FpayBldgcodeAndFlatpayCK_FpayFlatnumAndFlatpayCK_FpayOwneridAndFlatpayCK_FpayDuedateAndFlatpayCK_FpayNarrative(
//									flatpayRequestBean.getBldgcode(),
//									flatpayRequestBean.getFlatnum(),
//									flatpayRequestBean.getOwnerid(),
//									"",
//									flatpayRequestBean.getNarrative());
//						
//						if(Objects.nonNull(flatpayEntity )) {
//							
//							flatpayEntityList.add(FlatpayEntityPojoMapper
//									.updateFlatpayEntityPojoMapper.apply(flatpayEntity, flatpayRequestBean));
//						}
//					else
//						{
//						
//						flatpayEntityList.addAll(FlatpayEntityPojoMapper
//								.addFlatpayPojoEntityMapper.apply(Arrays.asList(flatpayRequestBean)));
//						
//						}
//				}
//				
//   				return flatpayRequestBean ;
//			}).collect(Collectors.toList());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not updated In FlatPay.").build());

	}

	@Override
	public ResponseEntity<?> deleteFlatpay(String bldgcode, String flatnum, String ownerid, LocalDateTime duedate,
			LocalDateTime paiddate, String narrative) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkBldgcodeAndFlatnumAndOwneridAndDuedateAndPaiddateAndNarrativeExists(String bldgcode,
			String flatnum, String ownerid, LocalDateTime duedate, LocalDateTime paiddate, String narrative) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
