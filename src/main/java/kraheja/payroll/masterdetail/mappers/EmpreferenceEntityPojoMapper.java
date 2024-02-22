// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empreference Entry / Edit
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
import kraheja.payroll.bean.request.EmpreferenceRequestBean;
import kraheja.payroll.bean.response.EmpreferenceResponseBean;
import kraheja.payroll.bean.response.EmpreferenceResponseBean.EmpreferenceResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empreference;
import kraheja.payroll.entity.EmpreferenceCK;

public interface EmpreferenceEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empreference>, List<EmpreferenceResponseBean>> fetchEmpreferenceEntityPojoMapper = empreferenceEntityList -> {
return empreferenceEntityList.stream().map(empreferenceEntity -> {
return EmpreferenceResponseBean.builder()
.empcode(empreferenceEntity.getEmpreferenceCK().getErefEmpcode())
					.srno(empreferenceEntity.getEmpreferenceCK().getErefSrno())
					.companyname(empreferenceEntity.getErefCompanyname())
					.ipaddress(empreferenceEntity.getErefIpaddress())
					.knownfrom(Objects.nonNull(empreferenceEntity.getErefKnownfrom()) ? empreferenceEntity.getErefKnownfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.machinename(empreferenceEntity.getErefMachinename())
					.modifiedon(empreferenceEntity.getErefModifiedon())
					.module(empreferenceEntity.getErefModule())
					.post(empreferenceEntity.getErefPost())
					.referenceaddress(empreferenceEntity.getErefReferenceaddress())
					.referencecellno(empreferenceEntity.getErefReferencecellno())
					.referencetelno(empreferenceEntity.getErefReferencetelno())
					.refrelation(empreferenceEntity.getErefRefrelation())
					.refrencename(empreferenceEntity.getErefRefrencename())
					.site(empreferenceEntity.getErefSite())
					.userid(empreferenceEntity.getErefUserid())
.build(); 
}).collect(Collectors.toList());

};

//empreferenceRequestBean
	public static Function<Object[], List <Empreference>> addEmpreferencePojoEntityMapper = objectArray -> {
		List<EmpreferenceRequestBean> empreferenceRequestBeanList = (List<EmpreferenceRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String empcode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String module = (String) (Objects.nonNull(objectArray[2]) ? objectArray[2] : null);
		AtomicInteger variableCounter = new AtomicInteger(0);

		return empreferenceRequestBeanList.stream().map(empreferenceRequestBean -> {
			int currentCount = variableCounter.incrementAndGet();
			return Empreference.builder().empreferenceCK(EmpreferenceCK.builder()
					.erefEmpcode(empcode)
					.erefSrno(currentCount)
		.build())
					.erefCompanyname(empreferenceRequestBean.getCompanyname())
					.erefIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.erefKnownfrom(Objects.nonNull(empreferenceRequestBean.getKnownfrom()) ? LocalDate.parse(empreferenceRequestBean.getKnownfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.erefMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.erefModifiedon(LocalDateTime.now())
					.erefModule(module)
					.erefPost(empreferenceRequestBean.getPost())
					.erefReferenceaddress(empreferenceRequestBean.getReferenceaddress())
					.erefReferencecellno(empreferenceRequestBean.getReferencecellno())
					.erefReferencetelno(empreferenceRequestBean.getReferencetelno())
					.erefRefrelation(empreferenceRequestBean.getRefrelation())
					.erefRefrencename(empreferenceRequestBean.getRefrencename())
					.erefSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.erefUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Empreference, EmpreferenceRequestBean, Empreference> updateEmpreferenceEntityPojoMapper = (empreferenceEntity, empreferenceRequestBean) -> {
		empreferenceEntity.getEmpreferenceCK().setErefEmpcode(Objects.nonNull(empreferenceRequestBean.getEmpcode()) ? empreferenceRequestBean.getEmpcode().trim() : empreferenceEntity.getEmpreferenceCK().getErefEmpcode());
		empreferenceEntity.getEmpreferenceCK().setErefSrno(Objects.nonNull(empreferenceRequestBean.getSrno()) ? empreferenceRequestBean.getSrno() : empreferenceEntity.getEmpreferenceCK().getErefSrno());
		empreferenceEntity.setErefCompanyname(Objects.nonNull(empreferenceRequestBean.getCompanyname()) ? empreferenceRequestBean.getCompanyname().trim() : empreferenceEntity.getErefCompanyname());
		empreferenceEntity.setErefIpaddress(Objects.nonNull(empreferenceRequestBean.getIpaddress()) ? empreferenceRequestBean.getIpaddress().trim() : empreferenceEntity.getErefIpaddress());
		empreferenceEntity.setErefKnownfrom(Objects.nonNull(empreferenceRequestBean.getKnownfrom()) ? LocalDate.parse(empreferenceRequestBean.getKnownfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empreferenceEntity.getErefKnownfrom());
		empreferenceEntity.setErefMachinename(Objects.nonNull(empreferenceRequestBean.getMachinename()) ? empreferenceRequestBean.getMachinename().trim() : empreferenceEntity.getErefMachinename());
		empreferenceEntity.setErefModifiedon(LocalDateTime.now()) ; 
		empreferenceEntity.setErefModule(Objects.nonNull(empreferenceRequestBean.getModule()) ? empreferenceRequestBean.getModule().trim() : empreferenceEntity.getErefModule());
		empreferenceEntity.setErefPost(Objects.nonNull(empreferenceRequestBean.getPost()) ? empreferenceRequestBean.getPost().trim() : empreferenceEntity.getErefPost());
		empreferenceEntity.setErefReferenceaddress(Objects.nonNull(empreferenceRequestBean.getReferenceaddress()) ? empreferenceRequestBean.getReferenceaddress().trim() : empreferenceEntity.getErefReferenceaddress());
		empreferenceEntity.setErefReferencecellno(Objects.nonNull(empreferenceRequestBean.getReferencecellno()) ? empreferenceRequestBean.getReferencecellno().trim() : empreferenceEntity.getErefReferencecellno());
		empreferenceEntity.setErefReferencetelno(Objects.nonNull(empreferenceRequestBean.getReferencetelno()) ? empreferenceRequestBean.getReferencetelno().trim() : empreferenceEntity.getErefReferencetelno());
		empreferenceEntity.setErefRefrelation(Objects.nonNull(empreferenceRequestBean.getRefrelation()) ? empreferenceRequestBean.getRefrelation().trim() : empreferenceEntity.getErefRefrelation());
		empreferenceEntity.setErefRefrencename(Objects.nonNull(empreferenceRequestBean.getRefrencename()) ? empreferenceRequestBean.getRefrencename().trim() : empreferenceEntity.getErefRefrencename());
		empreferenceEntity.setErefSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empreferenceEntity.setErefUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return empreferenceEntity;
	};

}
