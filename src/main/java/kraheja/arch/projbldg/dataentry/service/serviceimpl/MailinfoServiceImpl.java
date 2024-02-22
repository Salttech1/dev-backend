package kraheja.arch.projbldg.dataentry.service.serviceimpl;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.DbEntity;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;

import kraheja.arch.projbldg.dataentry.bean.request.MailinfoRequestBean;
import kraheja.arch.projbldg.dataentry.entity.Mailinfo;
import kraheja.arch.projbldg.dataentry.mappers.MailinfoEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.repository.MailinfoRepository;
import kraheja.arch.projbldg.dataentry.service.MailinfoService;

@Service
@Transactional
public class MailinfoServiceImpl implements MailinfoService {

	private static final Logger logger = LoggerFactory.getLogger(BldgmapServiceImpl.class);

	@Autowired
	private MailinfoRepository mailinfoRepository;

	@Autowired
	private  EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchMailinfoByBldgcode(String  bldgcode) {
		Mailinfo mailinfoEntity = this.mailinfoRepository.findByMailinfoCK_MinfBldgcode(bldgcode);
		if (mailinfoEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(MailinfoEntityPojoMapper.fetchMailinfoEntityPojoMapper
									.apply(new Object[] { Arrays.asList(mailinfoEntity) }))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Mailinfo").build());
	}

	@Override
	public ResponseEntity<?> addMailinfo(MailinfoRequestBean mailinfoRequestBean)  {
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		
		this.mailinfoRepository.save(MailinfoEntityPojoMapper.addMailinfoEntityPojoMapper.apply(new Object[] {mailinfoRequestBean,siteFromDBEntity,mailinfoRequestBean.getBldgcode().toString()}));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateMailinfo(MailinfoRequestBean mailinfoRequestBean)  {
		Mailinfo mailinfoEntity = this.mailinfoRepository.findByMailinfoCK_MinfBldgcode(mailinfoRequestBean.getBldgcode() ) ; 

		if(Objects.nonNull(mailinfoEntity)) {
			String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			
			if(Objects.nonNull(mailinfoRequestBean))
				this.mailinfoRepository.save(MailinfoEntityPojoMapper.updateMailinfoEntityPojoMapper.apply(mailinfoEntity, mailinfoRequestBean));
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}


}
