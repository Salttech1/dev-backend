package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kraheja.sales.bean.request.BldgwingmapRequestBean;
import kraheja.sales.entity.Bldgwingmap;
import kraheja.sales.bookings.dataentry.service.BldgwingmapService;
import kraheja.sales.bookings.dataentry.mappers.BldgwingmapEntityPojoMapper;
import kraheja.sales.repository.BldgwingmapRepository;
import kraheja.commons.bean.response.ServiceResponseBean;
//import kraheja.commons.repository.EntityRepository;

@Service
@Transactional
public class BldgwingmapServiceImpl implements BldgwingmapService {
	
	@SuppressWarnings("unchecked")
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private BldgwingmapRepository bldgwingmapRepository;

//	@Autowired
//	private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchBldgwingmapByBldgcodeAndBldgwing(String bldgcode, String bldgwing) {

 		Bldgwingmap bldgwingmap = this.bldgwingmapRepository
				.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(
						bldgcode, bldgwing);
		
		if (Objects.nonNull(bldgwingmap)) {
			return ResponseEntity
					
					.ok(ServiceResponseBean.builder()
							.data(BldgwingmapEntityPojoMapper.fetchBldgwingmapEntityPojoMapper
							.apply(new Object[] {bldgwingmap}))
							.status(Boolean.TRUE).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}
	
	@Override
	public ResponseEntity<?> addBldgwingmap(BldgwingmapRequestBean bldgwingmapRequestBean) {
		
		String StrBldgcode="",StrWing="";
		StrBldgcode=bldgwingmapRequestBean.getBldgcode();
		StrWing=bldgwingmapRequestBean.getBldgwing();
		Bldgwingmap bldgwingmap = this.bldgwingmapRepository
				.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(
						bldgwingmapRequestBean.getBldgcode(),bldgwingmapRequestBean.getBldgwing());

		if (bldgwingmap == null) {
			this.bldgwingmapRepository.save(BldgwingmapEntityPojoMapper.addBldgwingmapPojoEntityMapper
					.apply(new Object[] { bldgwingmapRequestBean }));
			
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Inserted.").build());
	
	}

	@Override
	public ResponseEntity<?> updateBldgwingmap(BldgwingmapRequestBean bldgwingmapRequestBean) {
		// TODO Auto-generated method stub
		
		String StrBldgCode="",StrWing="";
		StrBldgCode=bldgwingmapRequestBean.getBldgcode();
		StrWing=bldgwingmapRequestBean.getBldgwing();		
		Bldgwingmap bldgwingmap = this.bldgwingmapRepository
				.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(
						bldgwingmapRequestBean.getBldgcode(),bldgwingmapRequestBean.getBldgwing()
						);
		
		logger.info("bldgwingmap :: {}", bldgwingmap);
		if (bldgwingmap != null) {
			this.bldgwingmapRepository.save(
					BldgwingmapEntityPojoMapper.updateBldgwingmapEntityPojoMapper.apply(bldgwingmap, bldgwingmapRequestBean));
			
			return ResponseEntity
					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data updated Sucessfully").build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not updated.").build());
	}

	@Override
	public ResponseEntity<?> deleteBldgwingmap(String bldgcode, String bldgwing) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkBldgcodeAndBldgwingExists(String bldgcode, String bldgwing) {
		// TODO Auto-generated method stub
		Bldgwingmap bldgwingmapEntity = this.bldgwingmapRepository
				.findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(bldgcode, bldgwing);
				
		if (Objects.isNull(bldgwingmapEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("Data alreadly Exist.").build());
		
	}
}
