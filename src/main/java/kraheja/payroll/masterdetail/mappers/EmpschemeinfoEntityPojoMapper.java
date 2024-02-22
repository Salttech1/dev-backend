// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empschemeinfo Entry / Edit
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
import kraheja.payroll.bean.request.EmpsalarypackageRequestBean;
import kraheja.payroll.bean.request.EmpschemeinfoRequestBean;
import kraheja.payroll.bean.response.EmpschemeinfoResponseBean;
import kraheja.payroll.bean.response.EmpschemeinfoResponseBean.EmpschemeinfoResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empschemeinfo;
import kraheja.payroll.entity.EmpschemeinfoCK;

public interface EmpschemeinfoEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empschemeinfo>, List<EmpschemeinfoResponseBean>> fetchEmpschemeinfoEntityPojoMapper = empschemeinfoEntityList -> {
return empschemeinfoEntityList.stream().map(empschemeinfoEntity -> {
return EmpschemeinfoResponseBean.builder()
.empcode(empschemeinfoEntity.getEmpschemeinfoCK().getEschEmpcode())
					.schemecode(empschemeinfoEntity.getEmpschemeinfoCK().getEschSchemecode())
					.effectivefrom(Objects.nonNull(empschemeinfoEntity.getEmpschemeinfoCK().getEschEffectivefrom()) ? empschemeinfoEntity.getEmpschemeinfoCK().getEschEffectivefrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.applicableyn(empschemeinfoEntity.getEschApplicableyn())
					.effectiveupto(Objects.nonNull(empschemeinfoEntity.getEschEffectiveupto()) ? empschemeinfoEntity.getEschEffectiveupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empschemeno(empschemeinfoEntity.getEschEmpschemeno())
					.entrydate(Objects.nonNull(empschemeinfoEntity.getEschEntrydate()) ? empschemeinfoEntity.getEschEntrydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.ipaddress(empschemeinfoEntity.getEschIpaddress())
					.machinename(empschemeinfoEntity.getEschMachinename())
					.modifiedon(empschemeinfoEntity.getEschModifiedon())
					.module(empschemeinfoEntity.getEschModule())
					.remark(empschemeinfoEntity.getEschRemark())
					.schemeamount(empschemeinfoEntity.getEschSchemeamount())
					.schemecentre(empschemeinfoEntity.getEschSchemecentre())
					.schemeenddate(Objects.nonNull(empschemeinfoEntity.getEschSchemeenddate()) ? empschemeinfoEntity.getEschSchemeenddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.schemepercentage(empschemeinfoEntity.getEschSchemepercentage())
					.site(empschemeinfoEntity.getEschSite())
					.userid(empschemeinfoEntity.getEschUserid())
.build(); 
}).collect(Collectors.toList());

};

	public static Function<Object[], List <Empschemeinfo>> addEmpschemeinfoPojoEntityMapper = objectArray -> { 
		List<EmpschemeinfoRequestBean> empschemeinfoRequestBeanList = (List<EmpschemeinfoRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String empcode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String module = (String) (Objects.nonNull(objectArray[2]) ? objectArray[2] : null);
		String effectivefrom = (String) (Objects.nonNull(objectArray[3]) ? objectArray[3] : null);
		String effectiveupto = (String) (Objects.nonNull(objectArray[4]) ? objectArray[4] : null);
		
		return empschemeinfoRequestBeanList.stream().map(empschemeinfoRequestBean -> {
			return Empschemeinfo.builder().empschemeinfoCK(EmpschemeinfoCK.builder()
					.eschEmpcode(empcode)
					.eschSchemecode(empschemeinfoRequestBean.getSchemecode())
					.eschEffectivefrom(Objects.nonNull(effectivefrom) ? LocalDate.parse(effectivefrom, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.build())
					.eschApplicableyn(empschemeinfoRequestBean.getApplicableyn())
					.eschEffectiveupto(Objects.nonNull(effectiveupto) ? LocalDate.parse(effectiveupto, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eschEmpschemeno(empschemeinfoRequestBean.getEmpschemeno())
					.eschEntrydate(Objects.nonNull(empschemeinfoRequestBean.getEntrydate()) ? LocalDate.parse(empschemeinfoRequestBean.getEntrydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eschIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.eschMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.eschModifiedon(LocalDateTime.now())
					.eschModule(module)
					.eschRemark(empschemeinfoRequestBean.getRemark())
					.eschSchemeamount(Objects.nonNull(empschemeinfoRequestBean.getSchemeamount()) ? empschemeinfoRequestBean.getSchemeamount() : BigInteger.ZERO.doubleValue())
					.eschSchemecentre(empschemeinfoRequestBean.getSchemecentre())
					.eschSchemeenddate(Objects.nonNull(empschemeinfoRequestBean.getSchemeenddate()) ? LocalDate.parse(empschemeinfoRequestBean.getSchemeenddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eschSchemepercentage(Objects.nonNull(empschemeinfoRequestBean.getSchemepercentage()) ? empschemeinfoRequestBean.getSchemepercentage() : BigInteger.ZERO.intValue())
					.eschSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.eschUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Empschemeinfo, EmpschemeinfoRequestBean, Empschemeinfo> updateEmpschemeinfoEntityPojoMapper = (empschemeinfoEntity, empschemeinfoRequestBean) -> {
		empschemeinfoEntity.getEmpschemeinfoCK().setEschEmpcode(Objects.nonNull(empschemeinfoRequestBean.getEmpcode()) ? empschemeinfoRequestBean.getEmpcode().trim() : empschemeinfoEntity.getEmpschemeinfoCK().getEschEmpcode());
		empschemeinfoEntity.getEmpschemeinfoCK().setEschSchemecode(Objects.nonNull(empschemeinfoRequestBean.getSchemecode()) ? empschemeinfoRequestBean.getSchemecode().trim() : empschemeinfoEntity.getEmpschemeinfoCK().getEschSchemecode());
		empschemeinfoEntity.getEmpschemeinfoCK().setEschEffectivefrom(Objects.nonNull(empschemeinfoRequestBean.getEffectivefrom()) ? LocalDate.parse(empschemeinfoRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empschemeinfoEntity.getEmpschemeinfoCK().getEschEffectivefrom());
		empschemeinfoEntity.setEschApplicableyn(Objects.nonNull(empschemeinfoRequestBean.getApplicableyn()) ? empschemeinfoRequestBean.getApplicableyn().trim() : empschemeinfoEntity.getEschApplicableyn());
		empschemeinfoEntity.setEschEffectiveupto(Objects.nonNull(empschemeinfoRequestBean.getEffectiveupto()) ? LocalDate.parse(empschemeinfoRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empschemeinfoEntity.getEschEffectiveupto());
		empschemeinfoEntity.setEschEmpschemeno(Objects.nonNull(empschemeinfoRequestBean.getEmpschemeno()) ? empschemeinfoRequestBean.getEmpschemeno().trim() : empschemeinfoEntity.getEschEmpschemeno());
		empschemeinfoEntity.setEschEntrydate(Objects.nonNull(empschemeinfoRequestBean.getEntrydate()) ? LocalDate.parse(empschemeinfoRequestBean.getEntrydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empschemeinfoEntity.getEschEntrydate());
		empschemeinfoEntity.setEschIpaddress(Objects.nonNull(empschemeinfoRequestBean.getIpaddress()) ? empschemeinfoRequestBean.getIpaddress().trim() : empschemeinfoEntity.getEschIpaddress());
		empschemeinfoEntity.setEschMachinename(Objects.nonNull(empschemeinfoRequestBean.getMachinename()) ? empschemeinfoRequestBean.getMachinename().trim() : empschemeinfoEntity.getEschMachinename());
		empschemeinfoEntity.setEschModifiedon(LocalDateTime.now()) ; 
		empschemeinfoEntity.setEschModule(Objects.nonNull(empschemeinfoRequestBean.getModule()) ? empschemeinfoRequestBean.getModule().trim() : empschemeinfoEntity.getEschModule());
		empschemeinfoEntity.setEschRemark(Objects.nonNull(empschemeinfoRequestBean.getRemark()) ? empschemeinfoRequestBean.getRemark().trim() : empschemeinfoEntity.getEschRemark());
		empschemeinfoEntity.setEschSchemeamount(Objects.nonNull(empschemeinfoRequestBean.getSchemeamount()) ? empschemeinfoRequestBean.getSchemeamount() : empschemeinfoEntity.getEschSchemeamount());
		empschemeinfoEntity.setEschSchemecentre(Objects.nonNull(empschemeinfoRequestBean.getSchemecentre()) ? empschemeinfoRequestBean.getSchemecentre().trim() : empschemeinfoEntity.getEschSchemecentre());
		empschemeinfoEntity.setEschSchemeenddate(Objects.nonNull(empschemeinfoRequestBean.getSchemeenddate()) ? LocalDate.parse(empschemeinfoRequestBean.getSchemeenddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empschemeinfoEntity.getEschSchemeenddate());
		empschemeinfoEntity.setEschSchemepercentage(Objects.nonNull(empschemeinfoRequestBean.getSchemepercentage()) ? empschemeinfoRequestBean.getSchemepercentage() : empschemeinfoEntity.getEschSchemepercentage());
		empschemeinfoEntity.setEschSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empschemeinfoEntity.setEschUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return empschemeinfoEntity;
	};

}
