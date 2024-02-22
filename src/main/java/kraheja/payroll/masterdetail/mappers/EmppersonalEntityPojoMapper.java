// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Emppersonal Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.payroll.masterdetail.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.payroll.bean.request.EmppersonalRequestBean;
import kraheja.payroll.bean.response.EmppersonalResponseBean;
import kraheja.payroll.bean.response.EmppersonalResponseBean.EmppersonalResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Emppersonal;
import kraheja.payroll.entity.EmppersonalCK;

public interface EmppersonalEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Emppersonal>, List<EmppersonalResponseBean>> fetchEmppersonalEntityPojoMapper = emppersonalEntityList -> {
return emppersonalEntityList.stream().map(emppersonalEntity -> {
return EmppersonalResponseBean.builder()
.empcode(emppersonalEntity.getEmppersonalCK().getEperEmpcode())
					.effectivefrom(Objects.nonNull(emppersonalEntity.getEmppersonalCK().getEperEffectivefrom()) ? emppersonalEntity.getEmppersonalCK().getEperEffectivefrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.aadhaarno(emppersonalEntity.getEperAadhaarno())
					.birthdate(Objects.nonNull(emppersonalEntity.getEperBirthdate()) ? emppersonalEntity.getEperBirthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.bloodgrp(emppersonalEntity.getEperBloodgrp())
					.effectiveupto(Objects.nonNull(emppersonalEntity.getEperEffectiveupto()) ? emppersonalEntity.getEperEffectiveupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.fullname(emppersonalEntity.getEperFullname())
					.gender(emppersonalEntity.getEperGender())
					.handicapyn(emppersonalEntity.getEperHandicapyn())
					.height(emppersonalEntity.getEperHeight())
					.hobbies(emppersonalEntity.getEperHobbies())
					.ipaddress(emppersonalEntity.getEperIpaddress())
					.machinename(emppersonalEntity.getEperMachinename())
					.maritalstat(emppersonalEntity.getEperMaritalstat())
					.modifiedon(emppersonalEntity.getEperModifiedon())
					.module(emppersonalEntity.getEperModule())
					.mothertongue(emppersonalEntity.getEperMothertongue())
					.nationality(emppersonalEntity.getEperNationality())
					.noofchildren(emppersonalEntity.getEperNoofchildren())
					.panno(emppersonalEntity.getEperPanno())
					.pfuan(emppersonalEntity.getEperPfuan())
					.photopath(emppersonalEntity.getEperPhotopath())
					.religion(emppersonalEntity.getEperReligion())
					.remark(emppersonalEntity.getEperRemark())
					.site(emppersonalEntity.getEperSite())
					.title(emppersonalEntity.getEperTitle())
					.userid(emppersonalEntity.getEperUserid())
					.weddingdate(Objects.nonNull(emppersonalEntity.getEperWeddingdate()) ? emppersonalEntity.getEperWeddingdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.weight(emppersonalEntity.getEperWeight())
.build(); 
}).collect(Collectors.toList());

};


	public static Function<EmppersonalRequestBean, Emppersonal> addEmppersonalPojoEntityMapper = (emppersonalRequestBean) -> { 
		return Emppersonal.builder().emppersonalCK(EmppersonalCK.builder()
					.eperEmpcode(emppersonalRequestBean.getEmpcode())
					.eperEffectivefrom(Objects.nonNull(emppersonalRequestBean.getEffectivefrom()) ? LocalDate.parse(emppersonalRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.build())
					.eperAadhaarno(emppersonalRequestBean.getAadhaarno())
					.eperBirthdate(Objects.nonNull(emppersonalRequestBean.getBirthdate()) ? LocalDate.parse(emppersonalRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eperBloodgrp(emppersonalRequestBean.getBloodgrp())
					.eperEffectiveupto(Objects.nonNull(emppersonalRequestBean.getEffectiveupto()) ? LocalDate.parse(emppersonalRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eperFullname(emppersonalRequestBean.getFullname())
					.eperGender(emppersonalRequestBean.getGender())
					.eperHandicapyn(emppersonalRequestBean.getHandicapyn())
					.eperHeight(Objects.nonNull(emppersonalRequestBean.getHeight()) ? emppersonalRequestBean.getHeight() : BigInteger.ZERO.doubleValue())
					.eperHobbies(emppersonalRequestBean.getHobbies())
					.eperIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.eperMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.eperMaritalstat(emppersonalRequestBean.getMaritalstat())
					.eperModifiedon(LocalDateTime.now())
					.eperModule(emppersonalRequestBean.getModule())
					.eperMothertongue(emppersonalRequestBean.getMothertongue())
					.eperNationality(emppersonalRequestBean.getNationality())
					.eperNoofchildren(Objects.nonNull(emppersonalRequestBean.getNoofchildren()) ? emppersonalRequestBean.getNoofchildren() : BigInteger.ZERO.intValue())
					.eperPanno(emppersonalRequestBean.getPanno())
					.eperPfuan(emppersonalRequestBean.getPfuan())
					.eperPhotopath(emppersonalRequestBean.getPhotopath())
					.eperReligion(emppersonalRequestBean.getReligion())
					.eperRemark(emppersonalRequestBean.getRemark())
					.eperSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite()) ? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.eperTitle(emppersonalRequestBean.getTitle())
					.eperUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid(): "KRaheja")
					.eperWeddingdate(Objects.nonNull(emppersonalRequestBean.getWeddingdate()) ? LocalDate.parse(emppersonalRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eperWeight(Objects.nonNull(emppersonalRequestBean.getWeight()) ? emppersonalRequestBean.getWeight() : BigInteger.ZERO.doubleValue())
		
.build();
} ;
	public static BiFunction<Emppersonal, EmppersonalRequestBean, Emppersonal> updateEmppersonalEntityPojoMapper = (emppersonalEntity, emppersonalRequestBean) -> {
		emppersonalEntity.getEmppersonalCK().setEperEmpcode(Objects.nonNull(emppersonalRequestBean.getEmpcode()) ? emppersonalRequestBean.getEmpcode().trim() : emppersonalEntity.getEmppersonalCK().getEperEmpcode());
		emppersonalEntity.getEmppersonalCK().setEperEffectivefrom(Objects.nonNull(emppersonalRequestBean.getEffectivefrom()) ? LocalDate.parse(emppersonalRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEmppersonalCK().getEperEffectivefrom());
		emppersonalEntity.setEperAadhaarno(Objects.nonNull(emppersonalRequestBean.getAadhaarno()) ? emppersonalRequestBean.getAadhaarno().trim() : emppersonalEntity.getEperAadhaarno());
		emppersonalEntity.setEperBirthdate(Objects.nonNull(emppersonalRequestBean.getBirthdate()) ? LocalDate.parse(emppersonalRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperBirthdate());
		emppersonalEntity.setEperBloodgrp(Objects.nonNull(emppersonalRequestBean.getBloodgrp()) ? emppersonalRequestBean.getBloodgrp().trim() : emppersonalEntity.getEperBloodgrp());
		emppersonalEntity.setEperEffectiveupto(Objects.nonNull(emppersonalRequestBean.getEffectiveupto()) ? LocalDate.parse(emppersonalRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperEffectiveupto());
		emppersonalEntity.setEperFullname(Objects.nonNull(emppersonalRequestBean.getFullname()) ? emppersonalRequestBean.getFullname().trim() : emppersonalEntity.getEperFullname());
		emppersonalEntity.setEperGender(Objects.nonNull(emppersonalRequestBean.getGender()) ? emppersonalRequestBean.getGender().trim() : emppersonalEntity.getEperGender());
		emppersonalEntity.setEperHandicapyn(Objects.nonNull(emppersonalRequestBean.getHandicapyn()) ? emppersonalRequestBean.getHandicapyn().trim() : emppersonalEntity.getEperHandicapyn());
		emppersonalEntity.setEperHeight(Objects.nonNull(emppersonalRequestBean.getHeight()) ? emppersonalRequestBean.getHeight() : emppersonalEntity.getEperHeight());
		emppersonalEntity.setEperHobbies(Objects.nonNull(emppersonalRequestBean.getHobbies()) ? emppersonalRequestBean.getHobbies().trim() : emppersonalEntity.getEperHobbies());
		emppersonalEntity.setEperIpaddress(Objects.nonNull(emppersonalRequestBean.getIpaddress()) ? emppersonalRequestBean.getIpaddress().trim() : emppersonalEntity.getEperIpaddress());
		emppersonalEntity.setEperMachinename(Objects.nonNull(emppersonalRequestBean.getMachinename()) ? emppersonalRequestBean.getMachinename().trim() : emppersonalEntity.getEperMachinename());
		emppersonalEntity.setEperMaritalstat(Objects.nonNull(emppersonalRequestBean.getMaritalstat()) ? emppersonalRequestBean.getMaritalstat().trim() : emppersonalEntity.getEperMaritalstat());
		emppersonalEntity.setEperModifiedon(LocalDateTime.now()) ; 
		emppersonalEntity.setEperModule(Objects.nonNull(emppersonalRequestBean.getModule()) ? emppersonalRequestBean.getModule().trim() : emppersonalEntity.getEperModule());
		emppersonalEntity.setEperMothertongue(Objects.nonNull(emppersonalRequestBean.getMothertongue()) ? emppersonalRequestBean.getMothertongue().trim() : emppersonalEntity.getEperMothertongue());
		emppersonalEntity.setEperNationality(Objects.nonNull(emppersonalRequestBean.getNationality()) ? emppersonalRequestBean.getNationality().trim() : emppersonalEntity.getEperNationality());
		emppersonalEntity.setEperNoofchildren(Objects.nonNull(emppersonalRequestBean.getNoofchildren()) ? emppersonalRequestBean.getNoofchildren() : emppersonalEntity.getEperNoofchildren());
		emppersonalEntity.setEperPanno(Objects.nonNull(emppersonalRequestBean.getPanno()) ? emppersonalRequestBean.getPanno().trim() : emppersonalEntity.getEperPanno());
		emppersonalEntity.setEperPfuan(Objects.nonNull(emppersonalRequestBean.getPfuan()) ? emppersonalRequestBean.getPfuan().trim() : emppersonalEntity.getEperPfuan());
		emppersonalEntity.setEperPhotopath(Objects.nonNull(emppersonalRequestBean.getPhotopath()) ? emppersonalRequestBean.getPhotopath().trim() : emppersonalEntity.getEperPhotopath());
		emppersonalEntity.setEperReligion(Objects.nonNull(emppersonalRequestBean.getReligion()) ? emppersonalRequestBean.getReligion().trim() : emppersonalEntity.getEperReligion());
		emppersonalEntity.setEperRemark(Objects.nonNull(emppersonalRequestBean.getRemark()) ? emppersonalRequestBean.getRemark().trim() : emppersonalEntity.getEperRemark());
		emppersonalEntity.setEperSite(GenericAuditContextHolder.getContext().getSite()) ; 
		emppersonalEntity.setEperTitle(Objects.nonNull(emppersonalRequestBean.getTitle()) ? emppersonalRequestBean.getTitle().trim() : emppersonalEntity.getEperTitle());
		emppersonalEntity.setEperUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		emppersonalEntity.setEperWeddingdate(Objects.nonNull(emppersonalRequestBean.getWeddingdate()) ? LocalDate.parse(emppersonalRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperWeddingdate());
		emppersonalEntity.setEperWeight(Objects.nonNull(emppersonalRequestBean.getWeight()) ? emppersonalRequestBean.getWeight() : emppersonalEntity.getEperWeight());


		return emppersonalEntity;
	};

}




//package kraheja.payroll.masterdetail.mappers;
//
//import java.math.BigInteger;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import org.apache.commons.collections4.CollectionUtils;
//
//import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexphResponseBean;
//import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexphResponseBean.AdmexphResponseBeanBuilder;
//import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
//import kraheja.adminexp.vehicleexp.dataentry.mappers.AdmexpdEntityPojoMapper;
//import kraheja.commons.filter.GenericAuditContextHolder;
//import kraheja.commons.utils.CommonConstraints;
//import kraheja.payroll.bean.EmployeeDetailsResponseBean;
//import kraheja.payroll.bean.request.EmppersonalRequestBean;
//import kraheja.payroll.bean.response.EmppersonalResponseBean;
//import kraheja.payroll.bean.response.EmppersonalResponseBean.EmppersonalResponseBeanBuilder;
//import kraheja.payroll.entity.Emppersonal;
//import kraheja.payroll.entity.EmppersonalCK;
//
//public interface EmppersonalEntityPojoMapper {
//	@SuppressWarnings("unchecked")
//public static Function<Object[], 	List<EmppersonalResponseBean>> fetchEmppersonalEntityPojoMapper = objectArray -> {
//	EmppersonalResponseBeanBuilder emppersonalResponseBeanBuilder = EmppersonalResponseBean.builder();
////Emppersonal emppersonalEntity = (Emppersonal) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
////				? objectArray[BigInteger.ZERO.intValue()] : null);
//List<Emppersonal> emppersonalEntityList = (List<Emppersonal>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
//
//		return emppersonalEntityList.stream().map(emppersonalEntity -> {
//			emppersonalResponseBeanBuilder
//			.empcode(emppersonalEntity.getEmppersonalCK().getEperEmpcode())
//						.effectivefrom(Objects.nonNull(emppersonalEntity.getEmppersonalCK().getEperEffectivefrom()) ? emppersonalEntity.getEmppersonalCK().getEperEffectivefrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//						.aadhaarno(emppersonalEntity.getEperAadhaarno())
//						.birthdate(Objects.nonNull(emppersonalEntity.getEperBirthdate()) ? emppersonalEntity.getEperBirthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//						.bloodgrp(emppersonalEntity.getEperBloodgrp())
//						.effectiveupto(Objects.nonNull(emppersonalEntity.getEperEffectiveupto()) ? emppersonalEntity.getEperEffectiveupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//						.fullname(emppersonalEntity.getEperFullname())
//						.gender(emppersonalEntity.getEperGender())
//						.handicapyn(emppersonalEntity.getEperHandicapyn())
//						.height(emppersonalEntity.getEperHeight())
//						.hobbies(emppersonalEntity.getEperHobbies())
//						.ipaddress(emppersonalEntity.getEperIpaddress())
//						.machinename(emppersonalEntity.getEperMachinename())
//						.maritalstat(emppersonalEntity.getEperMaritalstat())
//						.modifiedon(emppersonalEntity.getEperModifiedon())
//						.module(emppersonalEntity.getEperModule())
//						.mothertongue(emppersonalEntity.getEperMothertongue())
//						.nationality(emppersonalEntity.getEperNationality())
//						.noofchildren(emppersonalEntity.getEperNoofchildren())
//						.panno(emppersonalEntity.getEperPanno())
//						.pfuan(emppersonalEntity.getEperPfuan())
//						.photopath(emppersonalEntity.getEperPhotopath())
//						.religion(emppersonalEntity.getEperReligion())
//						.remark(emppersonalEntity.getEperRemark())
//						.site(emppersonalEntity.getEperSite())
//						.title(emppersonalEntity.getEperTitle())
//						.userid(emppersonalEntity.getEperUserid())
//						.weddingdate(Objects.nonNull(emppersonalEntity.getEperWeddingdate()) ? emppersonalEntity.getEperWeddingdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//						.weight(emppersonalEntity.getEperWeight());
//						return emppersonalResponseBeanBuilder.build();			
//		}).collect(Collectors.toList());
//};
//
//
//	public static Function<EmppersonalRequestBean, Emppersonal> addEmppersonalPojoEntityMapper = emppersonalRequestBean -> {
//return Emppersonal.builder().emppersonalCK(EmppersonalCK.builder()
//					.eperEmpcode(emppersonalRequestBean.getEmpcode())
//					.eperEffectivefrom(Objects.nonNull(emppersonalRequestBean.getEffectivefrom()) ? LocalDate.parse(emppersonalRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//		.build())
//					.eperAadhaarno(emppersonalRequestBean.getAadhaarno())
//					.eperBirthdate(Objects.nonNull(emppersonalRequestBean.getBirthdate()) ? LocalDate.parse(emppersonalRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.eperBloodgrp(emppersonalRequestBean.getBloodgrp())
//					.eperEffectiveupto(Objects.nonNull(emppersonalRequestBean.getEffectiveupto()) ? LocalDate.parse(emppersonalRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.eperFullname(emppersonalRequestBean.getFullname())
//					.eperGender(emppersonalRequestBean.getGender())
//					.eperHandicapyn(emppersonalRequestBean.getHandicapyn())
//					.eperHeight(Objects.nonNull(emppersonalRequestBean.getHeight()) ? emppersonalRequestBean.getHeight() : BigInteger.ZERO.doubleValue())
//					.eperHobbies(emppersonalRequestBean.getHobbies())
//					.eperIpaddress(emppersonalRequestBean.getIpaddress())
//					.eperMachinename(emppersonalRequestBean.getMachinename())
//					.eperMaritalstat(emppersonalRequestBean.getMaritalstat())
//					.eperModifiedon(LocalDateTime.now())
//					.eperModule(emppersonalRequestBean.getModule())
//					.eperMothertongue(emppersonalRequestBean.getMothertongue())
//					.eperNationality(emppersonalRequestBean.getNationality())
//					.eperNoofchildren(Objects.nonNull(emppersonalRequestBean.getNoofchildren()) ? emppersonalRequestBean.getNoofchildren() : BigInteger.ZERO.intValue())
//					.eperPanno(emppersonalRequestBean.getPanno())
//					.eperPfuan(emppersonalRequestBean.getPfuan())
//					.eperPhotopath(emppersonalRequestBean.getPhotopath())
//					.eperReligion(emppersonalRequestBean.getReligion())
//					.eperRemark(emppersonalRequestBean.getRemark())
//					.eperSite(GenericAuditContextHolder.getContext().getSite())
//					.eperTitle(emppersonalRequestBean.getTitle())
//					.eperUserid(GenericAuditContextHolder.getContext().getUserid())
//					.eperWeddingdate(Objects.nonNull(emppersonalRequestBean.getWeddingdate()) ? LocalDate.parse(emppersonalRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.eperWeight(Objects.nonNull(emppersonalRequestBean.getWeight()) ? emppersonalRequestBean.getWeight() : BigInteger.ZERO.doubleValue())
//		
//.build();
//} ;
//	public static BiFunction<Emppersonal, EmppersonalRequestBean, Emppersonal> updateEmppersonalEntityPojoMapper = (emppersonalEntity, emppersonalRequestBean) -> {
//		emppersonalEntity.getEmppersonalCK().setEperEmpcode(Objects.nonNull(emppersonalRequestBean.getEmpcode()) ? emppersonalRequestBean.getEmpcode().trim() : emppersonalEntity.getEmppersonalCK().getEperEmpcode());
//		emppersonalEntity.getEmppersonalCK().setEperEffectivefrom(Objects.nonNull(emppersonalRequestBean.getEffectivefrom()) ? LocalDate.parse(emppersonalRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEmppersonalCK().getEperEffectivefrom());
//		emppersonalEntity.setEperAadhaarno(Objects.nonNull(emppersonalRequestBean.getAadhaarno()) ? emppersonalRequestBean.getAadhaarno().trim() : emppersonalEntity.getEperAadhaarno());
//		emppersonalEntity.setEperBirthdate(Objects.nonNull(emppersonalRequestBean.getBirthdate()) ? LocalDate.parse(emppersonalRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperBirthdate());
//		emppersonalEntity.setEperBloodgrp(Objects.nonNull(emppersonalRequestBean.getBloodgrp()) ? emppersonalRequestBean.getBloodgrp().trim() : emppersonalEntity.getEperBloodgrp());
//		emppersonalEntity.setEperEffectiveupto(Objects.nonNull(emppersonalRequestBean.getEffectiveupto()) ? LocalDate.parse(emppersonalRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperEffectiveupto());
//		emppersonalEntity.setEperFullname(Objects.nonNull(emppersonalRequestBean.getFullname()) ? emppersonalRequestBean.getFullname().trim() : emppersonalEntity.getEperFullname());
//		emppersonalEntity.setEperGender(Objects.nonNull(emppersonalRequestBean.getGender()) ? emppersonalRequestBean.getGender().trim() : emppersonalEntity.getEperGender());
//		emppersonalEntity.setEperHandicapyn(Objects.nonNull(emppersonalRequestBean.getHandicapyn()) ? emppersonalRequestBean.getHandicapyn().trim() : emppersonalEntity.getEperHandicapyn());
//		emppersonalEntity.setEperHeight(Objects.nonNull(emppersonalRequestBean.getHeight()) ? emppersonalRequestBean.getHeight() : emppersonalEntity.getEperHeight());
//		emppersonalEntity.setEperHobbies(Objects.nonNull(emppersonalRequestBean.getHobbies()) ? emppersonalRequestBean.getHobbies().trim() : emppersonalEntity.getEperHobbies());
//		emppersonalEntity.setEperIpaddress(Objects.nonNull(emppersonalRequestBean.getIpaddress()) ? emppersonalRequestBean.getIpaddress().trim() : emppersonalEntity.getEperIpaddress());
//		emppersonalEntity.setEperMachinename(Objects.nonNull(emppersonalRequestBean.getMachinename()) ? emppersonalRequestBean.getMachinename().trim() : emppersonalEntity.getEperMachinename());
//		emppersonalEntity.setEperMaritalstat(Objects.nonNull(emppersonalRequestBean.getMaritalstat()) ? emppersonalRequestBean.getMaritalstat().trim() : emppersonalEntity.getEperMaritalstat());
//		emppersonalEntity.setEperModifiedon(LocalDateTime.now()) ; 
//		emppersonalEntity.setEperModule(Objects.nonNull(emppersonalRequestBean.getModule()) ? emppersonalRequestBean.getModule().trim() : emppersonalEntity.getEperModule());
//		emppersonalEntity.setEperMothertongue(Objects.nonNull(emppersonalRequestBean.getMothertongue()) ? emppersonalRequestBean.getMothertongue().trim() : emppersonalEntity.getEperMothertongue());
//		emppersonalEntity.setEperNationality(Objects.nonNull(emppersonalRequestBean.getNationality()) ? emppersonalRequestBean.getNationality().trim() : emppersonalEntity.getEperNationality());
//		emppersonalEntity.setEperNoofchildren(Objects.nonNull(emppersonalRequestBean.getNoofchildren()) ? emppersonalRequestBean.getNoofchildren() : emppersonalEntity.getEperNoofchildren());
//		emppersonalEntity.setEperPanno(Objects.nonNull(emppersonalRequestBean.getPanno()) ? emppersonalRequestBean.getPanno().trim() : emppersonalEntity.getEperPanno());
//		emppersonalEntity.setEperPfuan(Objects.nonNull(emppersonalRequestBean.getPfuan()) ? emppersonalRequestBean.getPfuan().trim() : emppersonalEntity.getEperPfuan());
//		emppersonalEntity.setEperPhotopath(Objects.nonNull(emppersonalRequestBean.getPhotopath()) ? emppersonalRequestBean.getPhotopath().trim() : emppersonalEntity.getEperPhotopath());
//		emppersonalEntity.setEperReligion(Objects.nonNull(emppersonalRequestBean.getReligion()) ? emppersonalRequestBean.getReligion().trim() : emppersonalEntity.getEperReligion());
//		emppersonalEntity.setEperRemark(Objects.nonNull(emppersonalRequestBean.getRemark()) ? emppersonalRequestBean.getRemark().trim() : emppersonalEntity.getEperRemark());
//		emppersonalEntity.setEperSite(GenericAuditContextHolder.getContext().getSite()) ; 
//		emppersonalEntity.setEperTitle(Objects.nonNull(emppersonalRequestBean.getTitle()) ? emppersonalRequestBean.getTitle().trim() : emppersonalEntity.getEperTitle());
//		emppersonalEntity.setEperUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
//		emppersonalEntity.setEperWeddingdate(Objects.nonNull(emppersonalRequestBean.getWeddingdate()) ? LocalDate.parse(emppersonalRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppersonalEntity.getEperWeddingdate());
//		emppersonalEntity.setEperWeight(Objects.nonNull(emppersonalRequestBean.getWeight()) ? emppersonalRequestBean.getWeight() : emppersonalEntity.getEperWeight());
//
//
//		return emppersonalEntity;
//	};
//
//}
