package kraheja.arch.projbldg.dataentry.service.serviceimpl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.arch.projbldg.dataentry.bean.request.ProjectRequestBean;
import kraheja.arch.projbldg.dataentry.entity.Project;
import kraheja.arch.projbldg.dataentry.mappers.ProjectEntityPojoMapper;
import kraheja.arch.projbldg.dataentry.repository.ProjectRepository;
import kraheja.arch.projbldg.dataentry.service.ProjectService;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private EntityRepository entityRepository;
	
	
	//Commented by shahaji (05/01/2023)
//	@Override
//	public ResponseEntity<?> fetchProjectByProprietorAndCompanyAndCode(String  proprietor, String  company, String  code) {
//		Project projectEntity = this.projectRepository.findByProprietorAndCompanyAndCode(proprietor, company, code);
//		logger.info("ProjectEntity :: {}", projectEntity);
//		if (projectEntity != null) {
//			return ResponseEntity.ok(ServiceResponseBean.builder()
//									.data(ProjectEntityPojoMapper.fetchProjectEntityPojoMapper
//									.apply(new Object[] { Arrays.asList(projectEntity) })
//									.get(BigInteger.ZERO.intValue()))
//									.status(Boolean.TRUE).build());
//			}
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Project").build());
//	}
	
	//Added by shahaji (26/12/2022)
	@Override
	public ResponseEntity<?> fetchProjectByCode(String  code) {
		Project projectEntity = this.projectRepository.findByCode(code);
		logger.info("ProjectEntity :: {}", projectEntity);
		if (projectEntity != null) {
			
			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					code, AdSegment.PROJ.toString(), AdType.LOC.toString(), 
					CommonConstraints.INSTANCE.adrAdserZero);
			logger.info("AddressEntity :: {} " , addressEntity);
			
			return ResponseEntity.ok(ServiceResponseBean.builder()
									.data(ProjectEntityPojoMapper.fetchProjectEntityPojoMapper
									.apply(new Object[] { Arrays.asList(projectEntity), addressEntity }))
									.status(Boolean.TRUE).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Project").build());
	}

	@Override
	public ResponseEntity<?> addProject(ProjectRequestBean projectRequestBean) throws ParseException {
		String site = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);		
		String projectCode = projectRequestBean.getCode().trim() ; 
		logger.info("projectCode :: {}", projectCode);
		this.projectRepository.saveAndFlush(ProjectEntityPojoMapper.addProjectEntityPojoMapper.apply(new Object[] {projectRequestBean,site,projectCode}));
		
		projectRequestBean.getAddressRequestBean().setAdsegment(AdSegment.PROJ.toString());
		projectRequestBean.getAddressRequestBean().setSer(CommonConstraints.INSTANCE.adrAdserZero);
		projectRequestBean.getAddressRequestBean().setAdtype(AdType.LOC.toString());
		projectRequestBean.getAddressRequestBean().setTopser(CommonConstraints.INSTANCE.adrAdser);
//		projectRequestBean.getAddressRequestBean().setState(projectRequestBean.getGstValdiationBean().state);

		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {projectRequestBean.getAddressRequestBean(), site, projectCode}));
		
		GenericAuditContextHolder.getContext().setTransactionNo(projectRequestBean.getCode());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Project Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateProject(ProjectRequestBean projectRequestBean) throws ParseException {
		//Project projectEntity = this.projectRepository.findByProprietorAndCompanyAndCode(projectRequestBean.getProprietor().trim() , projectRequestBean.getCompany().trim() , projectRequestBean.getCode().trim() ) ; 
		Project projectEntity = this.projectRepository.findByCode(projectRequestBean.getCode().trim());    //Above code commented and added this new line ...Shahaji(28/12/2022)
		logger.info("ProjectEntity :: {}", projectEntity);
		
		if(Objects.nonNull(projectEntity)) {
			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);
			
			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					projectRequestBean.getCode().trim(), AdSegment.PROJ.toString(), AdType.LOC.toString(),
					CommonConstraints.INSTANCE.adrAdserZero);
			logger.info("AddressEntity :: {}" , addressEntity);

			if(Objects.nonNull(projectRequestBean))
				this.projectRepository.save(ProjectEntityPojoMapper.updateProjectEntityPojoMapper.apply(projectEntity, projectRequestBean));

			if(Objects.isNull(addressEntity)) {
				projectRequestBean.getAddressRequestBean().setAdsegment(AdSegment.PROJ.toString());
				projectRequestBean.getAddressRequestBean().setSer(CommonConstraints.INSTANCE.adrAdserZero);
				projectRequestBean.getAddressRequestBean().setAdtype(AdType.LOC.toString());
				projectRequestBean.getAddressRequestBean().setTopser(CommonConstraints.INSTANCE.adrAdser);
				this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {projectRequestBean.getAddressRequestBean(), SiteFromDBEntity, projectRequestBean.getCode().trim()}));
			}
			else {
				this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressEntity, projectRequestBean.getAddressRequestBean()));
			}

			GenericAuditContextHolder.getContext().setTransactionNo(projectRequestBean.getCode());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);		
					
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		} 
		else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	//Commented by shahaji (05/01/2023)
//	@Override
//	public ResponseEntity<?> checkProprietorAndCompanyAndCodeExists(String proprietor, String company, String code) {
//		// TODO Auto-generated method stub
//		return null;
//	}




}