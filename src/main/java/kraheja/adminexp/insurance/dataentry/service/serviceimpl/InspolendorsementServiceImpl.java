package kraheja.adminexp.insurance.dataentry.service.serviceimpl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.adminexp.insurance.dataentry.bean.request.EndorsementPolRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolendorsementRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.response.EndorsementResponseBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InspolendorsementResponseBean;
import kraheja.adminexp.insurance.dataentry.service.InspolendorsementService;
import kraheja.adminexp.insurance.dataentry.entity.Inspolendorsement;
import kraheja.adminexp.insurance.dataentry.entity.Inspolicy;
import kraheja.adminexp.insurance.dataentry.entity.Insprempaysch;
import kraheja.adminexp.insurance.dataentry.mappers.InspolendorsementEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.mappers.InspolicyEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.mappers.InsprempayschEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.repository.InspolendorsementRepository;
import kraheja.adminexp.insurance.dataentry.repository.InspolicyRepository;

@Service
@Transactional
public class InspolendorsementServiceImpl implements InspolendorsementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InspolendorsementServiceImpl.class);

	@Autowired
	private InspolendorsementRepository inspolendorsementRepository;

	@Autowired
	private InspolicyRepository inspolicyRepository;

	@Autowired
	private  EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> fetchInspolendorsementByPolendorseid(String Polendorseid, Boolean isAdd, String policyNo) {
		Inspolendorsement inspolendorsementEntity = this.inspolendorsementRepository.findByInspolendorsementCK_inpePolendorseid(Polendorseid.trim());
		LOGGER.info("InspolendorsementEntity :: {}", inspolendorsementEntity);
		List<InspolendorsementResponseBean> inspolendorsementList = new ArrayList<>();
		
		if(!isAdd)
			 inspolendorsementList = InspolendorsementEntityPojoMapper.fetchInspolendorsementEntityPojoMapper
			.apply(new Object[] { Arrays.asList(inspolendorsementEntity) });
		
		if (inspolendorsementEntity != null || isAdd) {
			Inspolicy inspolicy = this.inspolicyRepository.findByInspolicyCK_InpPolicynumber(Objects.nonNull(inspolendorsementEntity) ? inspolendorsementEntity.getInpepolicyno().trim(): policyNo);
			LOGGER.info("Inspolicy :: {}", inspolicy);
			if (inspolicy != null) {
				return ResponseEntity.ok(ServiceResponseBean.builder()
						.data(EndorsementResponseBean.builder()
								.inspolendorsementResponseBeanList(inspolendorsementList)
								.inspolicyResponseBeanList(InspolicyEntityPojoMapper.fetchInspolicyEntityPojoMapper 
										.apply(new Object[] { Arrays.asList(inspolicy), null, null, null })
										)
								.build())
						.status(Boolean.TRUE).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Inspolendorsement").build());
	}

@Override
public ResponseEntity<?> addInspolendorsement(InspolendorsementRequestBean inspolendorsementRequestBean) throws ParseException {
	Inspolendorsement inspolendorsementEntity = this.inspolendorsementRepository.findByInpePolendorsenum(inspolendorsementRequestBean.getPolendorsenum().trim()) ; 
	LOGGER.info("Entity :: {}" , inspolendorsementEntity);

	if(Objects.nonNull(inspolendorsementEntity))
	return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Polendorse Id Already exist").build());
	
	String Polendorseid = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "#POLE", GenericAuditContextHolder.getContext().getSite());
	LOGGER.info("Inspolendorsement :: {}", Polendorseid);
	
	this.inspolendorsementRepository.save(InspolendorsementEntityPojoMapper.addInspolendorsementEntityPojoMapper.apply(new Object[] {inspolendorsementRequestBean,Polendorseid}));

	//if(CollectionUtils.isNotEmpty(inspolendorsementRequestBean.getInspolicyRequestBeanList()))
		//this.inspolicyRepository.saveAll(InspolicyEntityPojoMapper.addInspolicyEntityPojoMapper.apply(new Object[] {inspolendorsementRequestBean.getInspolicyRequestBeanList(), Polendorseid}));
	
	GenericAuditContextHolder.getContext().setTransactionNo(Polendorseid);
	GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

	return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully").build());
}

@Override
public ResponseEntity<?> updateInspolendorsement(InspolendorsementRequestBean inspolendorsementRequestBean) throws ParseException {
	Inspolendorsement inspolendorsementEntity = this.inspolendorsementRepository.findByInspolendorsementCK_inpePolendorseid(inspolendorsementRequestBean.getPolendorseid() ) ; 

	if(Objects.nonNull(inspolendorsementEntity)) {

		String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		LOGGER.info("Entity :: {}" , SiteFromDBEntity);

		if(Objects.nonNull(inspolendorsementRequestBean))
			this.inspolendorsementRepository.save(InspolendorsementEntityPojoMapper.updateInspolendorsementEntityPojoMapper.apply(inspolendorsementEntity, inspolendorsementRequestBean));
		

		GenericAuditContextHolder.getContext().setTransactionNo(inspolendorsementRequestBean.getPolendorseid());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
	} else {
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}
}



@Override
public ResponseEntity<?> checkPolendorseidExists(String  polendorseid) {
	return null;
}
}