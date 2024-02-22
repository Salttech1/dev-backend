// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empassetinfo Entry / Edit
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

import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexpdRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexpdCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.payroll.bean.request.EmpassetinfoRequestBean;
import kraheja.payroll.bean.response.EmpassetinfoResponseBean;
import kraheja.payroll.bean.response.EmpassetinfoResponseBean.EmpassetinfoResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empassetinfo;
import kraheja.payroll.entity.EmpassetinfoCK;

public interface EmpassetinfoEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empassetinfo>, List<EmpassetinfoResponseBean>> fetchEmpassetinfoEntityPojoMapper = empassetinfoEntityList -> {
return empassetinfoEntityList.stream().map(empassetinfoEntity -> {
return EmpassetinfoResponseBean.builder()
.empcode(empassetinfoEntity.getEmpassetinfoCK().getEassEmpcode())
					.assetcode(empassetinfoEntity.getEmpassetinfoCK().getEassAssetcode())
					.assetdesc(empassetinfoEntity.getEassAssetdesc())
					.assetname(empassetinfoEntity.getEassAssetname())
					.authby(empassetinfoEntity.getEassAuthby())
					.ipaddress(empassetinfoEntity.getEassIpaddress())
					.issuedate(Objects.nonNull(empassetinfoEntity.getEassIssuedate()) ? empassetinfoEntity.getEassIssuedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.issuedby(empassetinfoEntity.getEassIssuedby())
					.machinename(empassetinfoEntity.getEassMachinename())
					.modifiedon(empassetinfoEntity.getEassModifiedon())
					.module(empassetinfoEntity.getEassModule())
					.receivedby(empassetinfoEntity.getEassReceivedby())
					.remark(empassetinfoEntity.getEassRemark())
					.returndate(Objects.nonNull(empassetinfoEntity.getEassReturndate()) ? empassetinfoEntity.getEassReturndate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.site(empassetinfoEntity.getEassSite())
					.userid(empassetinfoEntity.getEassUserid())
.build(); 
}).collect(Collectors.toList());

};


//empassetinfoRequestBeanList
//List<AdmexpdRequestBean> admexpdRequestBeanList = (List<AdmexpdRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
//return admexpdRequestBeanList.stream().map(admexpdRequestBean ->{
//return Admexpd.builder().admexpdCK(AdmexpdCK.builder()


	public static Function<Object[], List <Empassetinfo>> addEmpassetinfoPojoEntityMapper = objectArray -> { 
		List<EmpassetinfoRequestBean> empassetinfoRequestBeanList = (List<EmpassetinfoRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String empcode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String module = (String) (Objects.nonNull(objectArray[2]) ? objectArray[2] : null);
		return empassetinfoRequestBeanList.stream().map(empassetinfoRequestBean -> {
		return Empassetinfo.builder().empassetinfoCK(EmpassetinfoCK.builder()
					.eassEmpcode(empcode)
					.eassAssetcode(empassetinfoRequestBean.getAssetcode())
		.build())
					.eassAssetdesc(empassetinfoRequestBean.getAssetdesc())
					.eassAssetname(empassetinfoRequestBean.getAssetname())
					.eassAuthby(empassetinfoRequestBean.getAuthby())
					.eassIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.eassIssuedate(Objects.nonNull(empassetinfoRequestBean.getIssuedate()) ? LocalDate.parse(empassetinfoRequestBean.getIssuedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eassIssuedby(empassetinfoRequestBean.getIssuedby())
					.eassMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.eassModifiedon(LocalDateTime.now())
					.eassModule(module)
					.eassReceivedby(empassetinfoRequestBean.getReceivedby())
					.eassRemark(empassetinfoRequestBean.getRemark())
					.eassReturndate(Objects.nonNull(empassetinfoRequestBean.getReturndate()) ? LocalDate.parse(empassetinfoRequestBean.getReturndate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.eassSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.eassUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
		
.build();
}).collect(Collectors.toList());  
} ;
	public static BiFunction<Empassetinfo, EmpassetinfoRequestBean, Empassetinfo> updateEmpassetinfoEntityPojoMapper = (empassetinfoEntity, empassetinfoRequestBean) -> {
		empassetinfoEntity.getEmpassetinfoCK().setEassEmpcode(Objects.nonNull(empassetinfoRequestBean.getEmpcode()) ? empassetinfoRequestBean.getEmpcode().trim() : empassetinfoEntity.getEmpassetinfoCK().getEassEmpcode());
		empassetinfoEntity.getEmpassetinfoCK().setEassAssetcode(Objects.nonNull(empassetinfoRequestBean.getAssetcode()) ? empassetinfoRequestBean.getAssetcode().trim() : empassetinfoEntity.getEmpassetinfoCK().getEassAssetcode());
		empassetinfoEntity.setEassAssetdesc(Objects.nonNull(empassetinfoRequestBean.getAssetdesc()) ? empassetinfoRequestBean.getAssetdesc().trim() : empassetinfoEntity.getEassAssetdesc());
		empassetinfoEntity.setEassAssetname(Objects.nonNull(empassetinfoRequestBean.getAssetname()) ? empassetinfoRequestBean.getAssetname().trim() : empassetinfoEntity.getEassAssetname());
		empassetinfoEntity.setEassAuthby(Objects.nonNull(empassetinfoRequestBean.getAuthby()) ? empassetinfoRequestBean.getAuthby().trim() : empassetinfoEntity.getEassAuthby());
		empassetinfoEntity.setEassIpaddress(Objects.nonNull(empassetinfoRequestBean.getIpaddress()) ? empassetinfoRequestBean.getIpaddress().trim() : empassetinfoEntity.getEassIpaddress());
		empassetinfoEntity.setEassIssuedate(Objects.nonNull(empassetinfoRequestBean.getIssuedate()) ? LocalDate.parse(empassetinfoRequestBean.getIssuedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empassetinfoEntity.getEassIssuedate());
		empassetinfoEntity.setEassIssuedby(Objects.nonNull(empassetinfoRequestBean.getIssuedby()) ? empassetinfoRequestBean.getIssuedby().trim() : empassetinfoEntity.getEassIssuedby());
		empassetinfoEntity.setEassMachinename(Objects.nonNull(empassetinfoRequestBean.getMachinename()) ? empassetinfoRequestBean.getMachinename().trim() : empassetinfoEntity.getEassMachinename());
		empassetinfoEntity.setEassModifiedon(LocalDateTime.now()) ; 
		empassetinfoEntity.setEassModule(Objects.nonNull(empassetinfoRequestBean.getModule()) ? empassetinfoRequestBean.getModule().trim() : empassetinfoEntity.getEassModule());
		empassetinfoEntity.setEassReceivedby(Objects.nonNull(empassetinfoRequestBean.getReceivedby()) ? empassetinfoRequestBean.getReceivedby().trim() : empassetinfoEntity.getEassReceivedby());
		empassetinfoEntity.setEassRemark(Objects.nonNull(empassetinfoRequestBean.getRemark()) ? empassetinfoRequestBean.getRemark().trim() : empassetinfoEntity.getEassRemark());
		empassetinfoEntity.setEassReturndate(Objects.nonNull(empassetinfoRequestBean.getReturndate()) ? LocalDate.parse(empassetinfoRequestBean.getReturndate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empassetinfoEntity.getEassReturndate());
		empassetinfoEntity.setEassSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empassetinfoEntity.setEassUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return empassetinfoEntity;
	};

}
