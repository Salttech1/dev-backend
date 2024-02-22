package kraheja.arch.projbldg.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import kraheja.commons.entity.Address;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper.AddressEntityPojoMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.arch.projbldg.dataentry.bean.request.ProjectRequestBean;
import kraheja.arch.projbldg.dataentry.bean.response.ProjectResponseBean;
import kraheja.arch.projbldg.dataentry.bean.response.ProjectResponseBean.ProjectResponseBeanBuilder;
import kraheja.arch.projbldg.dataentry.entity.Project;
import kraheja.arch.projbldg.dataentry.entity.ProjectCK;

public interface ProjectEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<ProjectResponseBean>> fetchProjectEntityPojoMapper = objectArray -> {
		ProjectResponseBeanBuilder projectBeanBuilder = ProjectResponseBean.builder();
		List<Project> projectEntityList = (List<Project>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		Address addressEntity = (Address) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);
		return projectEntityList.stream().map(projectEntity -> {
			projectBeanBuilder.proprietor(projectEntity.getProjProprietor())
.company(projectEntity.getProjCompany())
.code(projectEntity.getProjectCK().getProjCode())
					.acclearyn(projectEntity.getProjAcclearyn())
					.amenity1(projectEntity.getProjAmenity1())
					.amenity2(projectEntity.getProjAmenity2())
					.amenity3(projectEntity.getProjAmenity3())
					.amenity4(projectEntity.getProjAmenity4())
					.amenity5(projectEntity.getProjAmenity5())
					.areaarch(projectEntity.getProjAreaarch())
					.areaengg(projectEntity.getProjAreaengg())
					.areasales(projectEntity.getProjAreasales())
					.bcount1(projectEntity.getProjBcount1())
					.bcount2(projectEntity.getProjBcount2())
					.bcount3(projectEntity.getProjBcount3())
					.bcount4(projectEntity.getProjBcount4())
					.bcount5(projectEntity.getProjBcount5())
					.bldgcount(projectEntity.getProjBldgcount())
					.btype1(projectEntity.getProjBtype1())
					.btype2(projectEntity.getProjBtype2())
					.btype3(projectEntity.getProjBtype3())
					.btype4(projectEntity.getProjBtype4())
					.btype5(projectEntity.getProjBtype5())
					.city(projectEntity.getProjCity())
					.desc1(projectEntity.getProjDesc1())
					.desc2(projectEntity.getProjDesc2())
					.desc3(projectEntity.getProjDesc3())
					.desc4(projectEntity.getProjDesc4())
					.desc5(projectEntity.getProjDesc5())
					.details(projectEntity.getProjDetails())
					.estval(projectEntity.getProjEstval())
					.expthisyr(projectEntity.getProjExpthisyr())
					.exptodate(projectEntity.getProjExptodate())
					.grpproject(projectEntity.getProjGrpproject())
					.name(projectEntity.getProjName())
					.origsite(projectEntity.getProjOrigsite())
					.site(projectEntity.getProjSite())
					.surveynum(projectEntity.getProjSurveynum())
					.tenure(projectEntity.getProjTenure())
					.today(projectEntity.getProjToday())
					.township(projectEntity.getProjTownship())
					.userid(projectEntity.getProjUserid())
;
			if(Objects.nonNull(addressEntity)) {
				projectBeanBuilder.addressResponseBean(AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressEntity}));
			}
			
			return projectBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Project> addProjectEntityPojoMapper = objectArray -> {
		ProjectRequestBean projectRequestBean = (ProjectRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		Project.ProjectBuilder projectbuilder = Project.builder();
	
projectbuilder
			.projectCK(ProjectCK.builder()
//.projProprietor(projectRequestBean.getProprietor())
//.projCompany(projectRequestBean.getCompany())
.projCode(projectRequestBean.getCode())
.build())
			.projCompany(projectRequestBean.getCompany())
			.projProprietor(projectRequestBean.getProprietor())
					.projAcclearyn(projectRequestBean.getAcclearyn())
					.projAmenity1(projectRequestBean.getAmenity1())
					.projAmenity2(projectRequestBean.getAmenity2())
					.projAmenity3(projectRequestBean.getAmenity3())
					.projAmenity4(projectRequestBean.getAmenity4())
					.projAmenity5(projectRequestBean.getAmenity5())
					.projAreaarch(projectRequestBean.getAreaarch())
					.projAreaengg(projectRequestBean.getAreaengg())
					.projAreasales(projectRequestBean.getAreasales())
					.projBcount1(projectRequestBean.getBcount1())
					.projBcount2(projectRequestBean.getBcount2())
					.projBcount3(projectRequestBean.getBcount3())
					.projBcount4(projectRequestBean.getBcount4())
					.projBcount5(projectRequestBean.getBcount5())
					.projBldgcount(projectRequestBean.getBldgcount())
					.projBtype1(projectRequestBean.getBtype1())
					.projBtype2(projectRequestBean.getBtype2())
					.projBtype3(projectRequestBean.getBtype3())
					.projBtype4(projectRequestBean.getBtype4())
					.projBtype5(projectRequestBean.getBtype5())
					.projCity(projectRequestBean.getCity())
					.projDesc1(projectRequestBean.getDesc1())
					.projDesc2(projectRequestBean.getDesc2())
					.projDesc3(projectRequestBean.getDesc3())
					.projDesc4(projectRequestBean.getDesc4())
					.projDesc5(projectRequestBean.getDesc5())
					.projDetails(projectRequestBean.getDetails())
					.projEstval(projectRequestBean.getEstval())
					.projExpthisyr(projectRequestBean.getExpthisyr())
					.projExptodate(projectRequestBean.getExptodate())
					.projGrpproject(projectRequestBean.getGrpproject())
					.projName(projectRequestBean.getName())
					.projOrigsite(projectRequestBean.getOrigsite())
//					.projSite(site)
					.projSite(GenericAuditContextHolder.getContext().getSite())
					.projSurveynum(projectRequestBean.getSurveynum())
					.projTenure(projectRequestBean.getTenure())
					.projToday(LocalDateTime.now())
					.projTownship(projectRequestBean.getTownship())
					.projUserid(projectRequestBean.getUserid())
;

		return projectbuilder.build();
};

	public static BiFunction<Project, ProjectRequestBean, Project> updateProjectEntityPojoMapper = (projectEntity, projectRequestBean) -> {
		
		
		projectEntity.setProjProprietor(StringUtils.isNotBlank(projectRequestBean.getProprietor()) ? projectRequestBean.getProprietor().trim() : projectEntity.getProjProprietor());
		projectEntity.setProjCompany(StringUtils.isNotBlank(projectRequestBean.getCompany()) ? projectRequestBean.getCompany().trim() : projectEntity.getProjCompany());
		projectEntity.getProjectCK().setProjCode(StringUtils.isNotBlank(projectRequestBean.getCode()) ? projectRequestBean.getCode().trim() : projectEntity.getProjectCK().getProjCode());
		projectEntity.setProjAcclearyn(StringUtils.isNotBlank(projectRequestBean.getAcclearyn()) ? projectRequestBean.getAcclearyn().trim() : projectEntity.getProjAcclearyn());
		projectEntity.setProjAmenity1(StringUtils.isNotBlank(projectRequestBean.getAmenity1()) ? projectRequestBean.getAmenity1().trim() : projectEntity.getProjAmenity1());
		projectEntity.setProjAmenity2(StringUtils.isNotBlank(projectRequestBean.getAmenity2()) ? projectRequestBean.getAmenity2().trim() : projectEntity.getProjAmenity2());
		projectEntity.setProjAmenity3(StringUtils.isNotBlank(projectRequestBean.getAmenity3()) ? projectRequestBean.getAmenity3().trim() : projectEntity.getProjAmenity3());
		projectEntity.setProjAmenity4(StringUtils.isNotBlank(projectRequestBean.getAmenity4()) ? projectRequestBean.getAmenity4().trim() : projectEntity.getProjAmenity4());
		projectEntity.setProjAmenity5(StringUtils.isNotBlank(projectRequestBean.getAmenity5()) ? projectRequestBean.getAmenity5().trim() : projectEntity.getProjAmenity5());
		projectEntity.setProjAreaarch(Objects.nonNull(projectRequestBean.getAreaarch()) ? projectRequestBean.getAreaarch() : projectEntity.getProjAreaarch());
		projectEntity.setProjAreaengg(Objects.nonNull(projectRequestBean.getAreaengg()) ? projectRequestBean.getAreaengg() : projectEntity.getProjAreaengg());
		projectEntity.setProjAreasales(Objects.nonNull(projectRequestBean.getAreasales()) ? projectRequestBean.getAreasales() : projectEntity.getProjAreasales());
		projectEntity.setProjBcount1(Objects.nonNull(projectRequestBean.getBcount1()) ? projectRequestBean.getBcount1() : projectEntity.getProjBcount1());
		projectEntity.setProjBcount2(Objects.nonNull(projectRequestBean.getBcount2()) ? projectRequestBean.getBcount2() : projectEntity.getProjBcount2());
		projectEntity.setProjBcount3(Objects.nonNull(projectRequestBean.getBcount3()) ? projectRequestBean.getBcount3() : projectEntity.getProjBcount3());
		projectEntity.setProjBcount4(Objects.nonNull(projectRequestBean.getBcount4()) ? projectRequestBean.getBcount4() : projectEntity.getProjBcount4());
		projectEntity.setProjBcount5(Objects.nonNull(projectRequestBean.getBcount5()) ? projectRequestBean.getBcount5() : projectEntity.getProjBcount5());
		projectEntity.setProjBldgcount(Objects.nonNull(projectRequestBean.getBldgcount()) ? projectRequestBean.getBldgcount() : projectEntity.getProjBldgcount());
		projectEntity.setProjBtype1(StringUtils.isNotBlank(projectRequestBean.getBtype1()) ? projectRequestBean.getBtype1().trim() : projectEntity.getProjBtype1());
		projectEntity.setProjBtype2(StringUtils.isNotBlank(projectRequestBean.getBtype2()) ? projectRequestBean.getBtype2().trim() : projectEntity.getProjBtype2());
		projectEntity.setProjBtype3(StringUtils.isNotBlank(projectRequestBean.getBtype3()) ? projectRequestBean.getBtype3().trim() : projectEntity.getProjBtype3());
		projectEntity.setProjBtype4(StringUtils.isNotBlank(projectRequestBean.getBtype4()) ? projectRequestBean.getBtype4().trim() : projectEntity.getProjBtype4());
		projectEntity.setProjBtype5(StringUtils.isNotBlank(projectRequestBean.getBtype5()) ? projectRequestBean.getBtype5().trim() : projectEntity.getProjBtype5());
		projectEntity.setProjCity(StringUtils.isNotBlank(projectRequestBean.getCity()) ? projectRequestBean.getCity().trim() : projectEntity.getProjCity());
		projectEntity.setProjDesc1(StringUtils.isNotBlank(projectRequestBean.getDesc1()) ? projectRequestBean.getDesc1().trim() : projectEntity.getProjDesc1());
		projectEntity.setProjDesc2(StringUtils.isNotBlank(projectRequestBean.getDesc2()) ? projectRequestBean.getDesc2().trim() : projectEntity.getProjDesc2());
		projectEntity.setProjDesc3(StringUtils.isNotBlank(projectRequestBean.getDesc3()) ? projectRequestBean.getDesc3().trim() : projectEntity.getProjDesc3());
		projectEntity.setProjDesc4(StringUtils.isNotBlank(projectRequestBean.getDesc4()) ? projectRequestBean.getDesc4().trim() : projectEntity.getProjDesc4());
		projectEntity.setProjDesc5(StringUtils.isNotBlank(projectRequestBean.getDesc5()) ? projectRequestBean.getDesc5().trim() : projectEntity.getProjDesc5());
		projectEntity.setProjDetails(StringUtils.isNotBlank(projectRequestBean.getDetails()) ? projectRequestBean.getDetails().trim() : projectEntity.getProjDetails());
		projectEntity.setProjEstval(Objects.nonNull(projectRequestBean.getEstval()) ? projectRequestBean.getEstval() : projectEntity.getProjEstval());
		projectEntity.setProjExpthisyr(Objects.nonNull(projectRequestBean.getExpthisyr()) ? projectRequestBean.getExpthisyr() : projectEntity.getProjExpthisyr());
		projectEntity.setProjExptodate(Objects.nonNull(projectRequestBean.getExptodate()) ? projectRequestBean.getExptodate() : projectEntity.getProjExptodate());
		projectEntity.setProjGrpproject(StringUtils.isNotBlank(projectRequestBean.getGrpproject()) ? projectRequestBean.getGrpproject().trim() : projectEntity.getProjGrpproject());
		projectEntity.setProjName(StringUtils.isNotBlank(projectRequestBean.getName()) ? projectRequestBean.getName().trim() : projectEntity.getProjName());
		projectEntity.setProjOrigsite(StringUtils.isNotBlank(projectRequestBean.getOrigsite()) ? projectRequestBean.getOrigsite().trim() : projectEntity.getProjOrigsite());
		projectEntity.setProjSite(GenericAuditContextHolder.getContext().getSite());
		//projectEntity.setProjSite("MUM");
		projectEntity.setProjSurveynum(StringUtils.isNotBlank(projectRequestBean.getSurveynum()) ? projectRequestBean.getSurveynum().trim() : projectEntity.getProjSurveynum());
		projectEntity.setProjTenure(StringUtils.isNotBlank(projectRequestBean.getTenure()) ? projectRequestBean.getTenure().trim() : projectEntity.getProjTenure());
		projectEntity.setProjToday(LocalDateTime.now());
		projectEntity.setProjTownship(StringUtils.isNotBlank(projectRequestBean.getTownship()) ? projectRequestBean.getTownship().trim() : projectEntity.getProjTownship());
		projectEntity.setProjUserid(StringUtils.isNotBlank(projectRequestBean.getUserid()) ? projectRequestBean.getUserid().trim() : projectEntity.getProjUserid());


		return projectEntity;
	};

}