// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empfamily Entry / Edit
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.payroll.bean.request.EmpeducationRequestBean;
import kraheja.payroll.bean.request.EmpfamilyRequestBean;
import kraheja.payroll.bean.response.EmpfamilyResponseBean;
import kraheja.payroll.bean.response.EmpfamilyResponseBean.EmpfamilyResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empfamily;
import kraheja.payroll.entity.EmpfamilyCK;

public interface EmpfamilyEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empfamily>, List<EmpfamilyResponseBean>> fetchEmpfamilyEntityPojoMapper = empfamilyEntityList -> {
return empfamilyEntityList.stream().map(empfamilyEntity -> {
return EmpfamilyResponseBean.builder()
.empcode(empfamilyEntity.getEmpfamilyCK().getEfamEmpcode())
					.srno(empfamilyEntity.getEmpfamilyCK().getEfamSrno())
					.birthdate(Objects.nonNull(empfamilyEntity.getEfamBirthdate()) ? empfamilyEntity.getEfamBirthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.fullname(empfamilyEntity.getEfamFullname())
					.ipaddress(empfamilyEntity.getEfamIpaddress())
					.machinename(empfamilyEntity.getEfamMachinename())
					.modifiedon(empfamilyEntity.getEfamModifiedon())
					.module(empfamilyEntity.getEfamModule())
					.occupation(empfamilyEntity.getEfamOccupation())
					.relation(empfamilyEntity.getEfamRelation())
					.site(empfamilyEntity.getEfamSite())
					.userid(empfamilyEntity.getEfamUserid())
					.weddingdate(Objects.nonNull(empfamilyEntity.getEfamWeddingdate()) ? empfamilyEntity.getEfamWeddingdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
.build(); 
}).collect(Collectors.toList());

};


	public static Function<Object[], List <Empfamily>> addEmpfamilyPojoEntityMapper = (objectArray) -> { 
//		empfamilyRequestBeanList
		List<EmpfamilyRequestBean> empfamilyRequestBeanList = (List<EmpfamilyRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String empcode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String module = (String) (Objects.nonNull(objectArray[2]) ? objectArray[2] : null);
		AtomicInteger variableCounter = new AtomicInteger(0);

		return empfamilyRequestBeanList.stream().map(empfamilyRequestBean -> {
			int currentCount = variableCounter.incrementAndGet();
			return Empfamily.builder().empfamilyCK(EmpfamilyCK.builder()
					.efamEmpcode(empcode)
					.efamSrno(currentCount)
		.build())
					.efamBirthdate(Objects.nonNull(empfamilyRequestBean.getBirthdate()) ? LocalDate.parse(empfamilyRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.efamFullname(empfamilyRequestBean.getFullname())
					.efamIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.efamMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.efamModifiedon(LocalDateTime.now())
					.efamModule(module)
					.efamOccupation(empfamilyRequestBean.getOccupation())
					.efamRelation(empfamilyRequestBean.getRelation())
					.efamSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.efamUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
					.efamWeddingdate(Objects.nonNull(empfamilyRequestBean.getWeddingdate()) ? LocalDate.parse(empfamilyRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Empfamily, EmpfamilyRequestBean, Empfamily> updateEmpfamilyEntityPojoMapper = (empfamilyEntity, empfamilyRequestBean) -> {
		empfamilyEntity.getEmpfamilyCK().setEfamEmpcode(Objects.nonNull(empfamilyRequestBean.getEmpcode()) ? empfamilyRequestBean.getEmpcode().trim() : empfamilyEntity.getEmpfamilyCK().getEfamEmpcode());
		empfamilyEntity.getEmpfamilyCK().setEfamSrno(Objects.nonNull(empfamilyRequestBean.getSrno()) ? empfamilyRequestBean.getSrno() : empfamilyEntity.getEmpfamilyCK().getEfamSrno());
		empfamilyEntity.setEfamBirthdate(Objects.nonNull(empfamilyRequestBean.getBirthdate()) ? LocalDate.parse(empfamilyRequestBean.getBirthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empfamilyEntity.getEfamBirthdate());
		empfamilyEntity.setEfamFullname(Objects.nonNull(empfamilyRequestBean.getFullname()) ? empfamilyRequestBean.getFullname().trim() : empfamilyEntity.getEfamFullname());
		empfamilyEntity.setEfamIpaddress(Objects.nonNull(empfamilyRequestBean.getIpaddress()) ? empfamilyRequestBean.getIpaddress().trim() : empfamilyEntity.getEfamIpaddress());
		empfamilyEntity.setEfamMachinename(Objects.nonNull(empfamilyRequestBean.getMachinename()) ? empfamilyRequestBean.getMachinename().trim() : empfamilyEntity.getEfamMachinename());
		empfamilyEntity.setEfamModifiedon(LocalDateTime.now()) ; 
		empfamilyEntity.setEfamModule(Objects.nonNull(empfamilyRequestBean.getModule()) ? empfamilyRequestBean.getModule().trim() : empfamilyEntity.getEfamModule());
		empfamilyEntity.setEfamOccupation(Objects.nonNull(empfamilyRequestBean.getOccupation()) ? empfamilyRequestBean.getOccupation().trim() : empfamilyEntity.getEfamOccupation());
		empfamilyEntity.setEfamRelation(Objects.nonNull(empfamilyRequestBean.getRelation()) ? empfamilyRequestBean.getRelation().trim() : empfamilyEntity.getEfamRelation());
		empfamilyEntity.setEfamSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empfamilyEntity.setEfamUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		empfamilyEntity.setEfamWeddingdate(Objects.nonNull(empfamilyRequestBean.getWeddingdate()) ? LocalDate.parse(empfamilyRequestBean.getWeddingdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empfamilyEntity.getEfamWeddingdate());


		return empfamilyEntity;
	};

}
