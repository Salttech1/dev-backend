package kraheja.adminexp.insurance.dataentry.service.serviceimpl;
import java.math.BigInteger;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.insurance.dataentry.bean.request.InsassetiteminsuredRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolicyRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.request.InsprempayschRequestBean;
import kraheja.adminexp.insurance.dataentry.entity.Insassetiteminsured;
import kraheja.adminexp.insurance.dataentry.entity.Inspolicy;
import kraheja.adminexp.insurance.dataentry.entity.Insprempaysch;
import kraheja.adminexp.insurance.dataentry.mappers.InsassetiteminsuredEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.mappers.InspolicyEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.mappers.InsprempayschEntityPojoMapper;
import kraheja.adminexp.insurance.dataentry.repository.InsassetiteminsuredRepository;
import kraheja.adminexp.insurance.dataentry.repository.InspolicyRepository;
import kraheja.adminexp.insurance.dataentry.repository.InsprempayschRepository;
import kraheja.adminexp.insurance.dataentry.service.InspolicyService;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;

@Service
@Transactional
public class InspolicyServiceImpl implements InspolicyService {

	private static final Logger logger = LoggerFactory.getLogger(InspolicyServiceImpl.class);

	@Autowired
	private InspolicyRepository inspolicyRepository;
	
	@Autowired
	private InsassetiteminsuredRepository insassetiteminsuredRepository;
	
	@Autowired
	private InsprempayschRepository insprempayschRepository;
	
	@Autowired
	private  EntityRepository entityRepository;
	@Override
	public ResponseEntity<?> fetchInspolicyByPolicyid(String  policyid) {
		Inspolicy inspolicyEntity = this.inspolicyRepository.findByInspolicyCK_InpPolicyid(policyid);
		logger.info("InspolicyEntity :: {}", inspolicyEntity);
		if (inspolicyEntity != null) {
			List<Insprempaysch> insprempayschEntityList  = this.insprempayschRepository.findByInsprempayschCK_IppsPolicyidAndInsprempayschCK_IppsPolicynumber(policyid, inspolicyEntity.getInspolicyCK().getInpPolicynumber());
			List<Insassetiteminsured> insassetiteminsuredEntity  = this.insassetiteminsuredRepository.findByInsassetiteminsuredCK_IaiPolicyid(policyid);
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(InspolicyEntityPojoMapper.fetchInspolicyEntityPojoMapper
									.apply(new Object[] { Arrays.asList(inspolicyEntity),insprempayschEntityList,insassetiteminsuredEntity })
									.get(BigInteger.ZERO.intValue()))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Inspolicy").build());
	}

	@Override
	public ResponseEntity<?> addInspolicy(InspolicyRequestBean inspolicyRequestBean) throws ParseException {
		//String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String Policyid = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER","#POL", GenericAuditContextHolder.getContext().getSite());
		//String inspolicypolicyid = inspolicyRequestBean.getPolicyid().trim() ; 
		logger.info("Inspolicy :: {}", Policyid);
		
		if(Objects.nonNull(inspolicyRequestBean)) {
			if(inspolicyRequestBean.getStatus().trim().equals("R")) {
				this.inspolicyRepository.updateRenewedYNStatus("Y", GenericAuditContextHolder.getContext().getSite(), GenericAuditContextHolder.getContext().getUserid().trim(), LocalDateTime.now(), inspolicyRequestBean.getPrevpolicyId().trim());
			}	
			this.inspolicyRepository.saveAll(InspolicyEntityPojoMapper.addInspolicyEntityPojoMapper.apply(new Object[] {Arrays.asList(inspolicyRequestBean), Policyid}));				
			}
			
		if(CollectionUtils.isNotEmpty(inspolicyRequestBean.getInsprempayschRequestBeanList())) {
			
			this.insprempayschRepository.saveAll(InsprempayschEntityPojoMapper.addInsprempayschEntityPojoMapper.apply(new Object[] {inspolicyRequestBean.getInsprempayschRequestBeanList(), Policyid}));
		}
		
		
		if(CollectionUtils.isNotEmpty(inspolicyRequestBean.getInsassetiteminsuredRequestBeanList()))
		this.insassetiteminsuredRepository.saveAll(InsassetiteminsuredEntityPojoMapper.addInsassetiteminsuredEntityPojoMapper.apply(new Object[] {inspolicyRequestBean.getInsassetiteminsuredRequestBeanList(), Policyid}));
		
		GenericAuditContextHolder.getContext().setTransactionNo(Policyid);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Policy Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateInspolicy(InspolicyRequestBean inspolicyRequestBean) throws ParseException {
		Inspolicy inspolicyEntity = this.inspolicyRepository.findByInspolicyCK_InpPolicyid(inspolicyRequestBean.getPolicyid() ) ;
		
		List<Insprempaysch> insprempayschEntityList  = this.insprempayschRepository.findByInsprempayschCK_IppsPolicyidAndInsprempayschCK_IppsPolicynumber(inspolicyRequestBean.getPolicyid(), inspolicyRequestBean.getPolicynumber()) ;
		logger.info("insprempayschEntityList :: {}" , insprempayschEntityList);

		List<Insassetiteminsured>  insassetiteminsuredEntityList = this.insassetiteminsuredRepository.findByInsassetiteminsuredCK_IaiPolicyid(inspolicyRequestBean.getPolicyid());
		logger.info("insprempayschEntityList :: {}" , insprempayschEntityList);

		if(Objects.nonNull(inspolicyEntity)) {

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);

			if(Objects.nonNull(inspolicyRequestBean))
				this.inspolicyRepository.save(InspolicyEntityPojoMapper.updateInspolicyEntityPojoMapper.apply(inspolicyEntity, inspolicyRequestBean));
			
			if(CollectionUtils.isNotEmpty(inspolicyRequestBean.getInsprempayschRequestBeanList())) {
				List<InsprempayschRequestBean>  insprempayschRequestBeanToAdd = new ArrayList<>();
				
				inspolicyRequestBean.getInsprempayschRequestBeanList().stream().filter(filter ->{
					return filter.getIsAdd();
				}).map(insprempayschRequestBean -> {
					insprempayschRequestBean.setPolicynumber(inspolicyEntity.getInspolicyCK().getInpPolicynumber());
					insprempayschRequestBeanToAdd.add(insprempayschRequestBean);
					return  insprempayschRequestBean;
				}).collect(Collectors.toList());
				logger.info("insprempayschRequestBeanToAdd :: {}" , insprempayschRequestBeanToAdd);

				if(CollectionUtils.isNotEmpty(insprempayschRequestBeanToAdd))
				this.insprempayschRepository.saveAll(InsprempayschEntityPojoMapper.addInsprempayschEntityPojoMapper.apply(new Object[] {insprempayschRequestBeanToAdd, inspolicyEntity.getInspolicyCK().getInpPolicyid()}));

				insprempayschEntityList.stream().map(insprempayschEntity -> {
					//Object collectors;
					inspolicyRequestBean.getInsprempayschRequestBeanList().stream().filter(filter ->{
						return insprempayschEntity.getInsprempayschCK().getIppsPolicyid().trim().equals(filter.getPolicyid().trim()) && insprempayschEntity.getInsprempayschCK().getIppsPolicynumber().trim().equals(filter.getPolicynumber().trim()) && insprempayschEntity.getInsprempayschCK().getIppsLinenumber().equals(filter.getLinenumber()) && !filter.getIsAdd();
					}).map(insprempayschRequestBean ->{
							this.insprempayschRepository.save(InsprempayschEntityPojoMapper.updateInsprempayschEntityPojoMapper.apply(insprempayschEntity, insprempayschRequestBean));
						return insprempayschRequestBean;
					}
							).collect(Collectors.toList());
					return insprempayschEntity;
				}).collect(Collectors.toList());
				
			}
				
			if(CollectionUtils.isNotEmpty(inspolicyRequestBean.getInsassetiteminsuredRequestBeanList())) {
				List<InsassetiteminsuredRequestBean> insassetiteminsuredRequestBeanToAdd = new ArrayList<>();
				logger.info("InsassetiteminsuredRequestBeanToAdd :: {}" , insassetiteminsuredRequestBeanToAdd);

				inspolicyRequestBean.getInsassetiteminsuredRequestBeanList().stream().filter(filter -> {
					return filter.getIsAdd();
				}).map(insassetiteminsuredRequestBean ->{
					insassetiteminsuredRequestBean.setPolicynumber(inspolicyEntity.getInspolicyCK().getInpPolicynumber());
					insassetiteminsuredRequestBeanToAdd.add(insassetiteminsuredRequestBean);
					return insassetiteminsuredRequestBean;
				}).collect(Collectors.toList());

				if(CollectionUtils.isNotEmpty(insassetiteminsuredRequestBeanToAdd))
				this.insassetiteminsuredRepository.saveAll(InsassetiteminsuredEntityPojoMapper.addInsassetiteminsuredEntityPojoMapper.apply(new Object[] {insassetiteminsuredRequestBeanToAdd, inspolicyEntity.getInspolicyCK().getInpPolicyid()}));
				
				insassetiteminsuredEntityList.stream().map(insassetiteminsuredEntity -> {
					inspolicyRequestBean.getInsassetiteminsuredRequestBeanList().stream().filter(filter ->{
					return	insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiPolicyid().equals(filter.getPolicyid()) && insassetiteminsuredEntity.getInsassetiteminsuredCK().getIaiLinenumber().equals(filter.getLinenumber()) && !filter.getIsAdd();
					}).map(insassetiteminsuredRequestBean ->{
						this.insassetiteminsuredRepository.save(InsassetiteminsuredEntityPojoMapper.updateInsassetiteminsuredEntityPojoMapper.apply(insassetiteminsuredEntity,insassetiteminsuredRequestBean));
						return	insassetiteminsuredRequestBean;
					}
							).collect(Collectors.toList());
					return insassetiteminsuredEntity;
				}).collect(Collectors.toList());
			}
			
			GenericAuditContextHolder.getContext().setTransactionNo(inspolicyRequestBean.getPolicyid());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Policy Updated Successfully").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	private String getpolicynumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> checkPolicyidExists(String  policyid) {
		return null;
	}
		
	//Method to check policy number is already exists........Shahaji(04/04/2023)
	@Override
	public ResponseEntity<?> checkPolicynumExists(String policynum) {
		Inspolicy inspolicyEntity = this.inspolicyRepository.findByInspolicyCK_InpPolicynumber(policynum.trim());
		logger.info("InspolicyEntity :: {}", inspolicyEntity);
		if (inspolicyEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder().message(policynum +" is exists under Policy ID " + inspolicyEntity.getInspolicyCK().getInpPolicyid()).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}
}
