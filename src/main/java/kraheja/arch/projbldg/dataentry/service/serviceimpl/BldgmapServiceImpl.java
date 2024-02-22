package kraheja.arch.projbldg.dataentry.service.serviceimpl;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.DbEntity;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.arch.projbldg.dataentry.bean.request.BldgmapRequestBean;
import kraheja.arch.projbldg.dataentry.bean.request.BuildingRequestBean;
import kraheja.arch.projbldg.dataentry.service.BldgmapService;
import kraheja.arch.projbldg.dataentry.entity.Bldgmap;
import kraheja.arch.projbldg.dataentry.mappers.BldgmapEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.mappers.BuildingEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.repository.BldgmapRepository;

@Service
@Transactional
public class BldgmapServiceImpl implements BldgmapService {

	private static final Logger logger = LoggerFactory.getLogger(BldgmapServiceImpl.class);

	@Autowired
	private BldgmapRepository bldgmapRepository;
	
	@Autowired
	private  EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchBldgmapByEbldgcodeAndAbldgcode(String  ebldgcode, String  abldgcode) {
		Bldgmap bldgmapEntity = this.bldgmapRepository.findByBldgmapCK_BmapEbldgcodeAndBldgmapCK_BmapAbldgcode(ebldgcode, abldgcode);
		logger.info("BldgmapEntity :: {}", bldgmapEntity);
		if (bldgmapEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(BldgmapEntityPojoMapper.fetchBldgmapEntityPojoMapper
									.apply(new Object[] { Arrays.asList(bldgmapEntity) }))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Bldgmap").build());
	}

	@Override
	public ResponseEntity<?> addBldgmap(BldgmapRequestBean bldgmapRequestBean)  {
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);		

		this.bldgmapRepository.save(BldgmapEntityPojoMapper.addBldgmapEntityPojoMapper.apply(new Object[] {bldgmapRequestBean,siteFromDBEntity,bldgmapRequestBean.getEbldgcode().toString()}));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateBldgmap(BldgmapRequestBean bldgmapRequestBean)  {
		Bldgmap bldgmapEntity = this.bldgmapRepository.findByBldgmapCK_BmapEbldgcodeAndBldgmapCK_BmapAbldgcode(bldgmapRequestBean.getEbldgcode() , bldgmapRequestBean.getAbldgcode() ) ; 

		if(Objects.nonNull(bldgmapEntity)) {

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

			if(Objects.nonNull(bldgmapRequestBean))
				this.bldgmapRepository.save(BldgmapEntityPojoMapper.updateBldgmapEntityPojoMapper.apply(bldgmapEntity, bldgmapRequestBean));
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> checkEbldgcodeAndAbldgcodeExists(String ebldgcode, String abldgcode) {
		// TODO Auto-generated method stub
		return null;
	}



}