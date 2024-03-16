// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empleaveinfo Entry / Edit
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
import kraheja.payroll.bean.request.EmpleaveinfoRequestBean;
import kraheja.payroll.bean.request.EmpschemeinfoRequestBean;
import kraheja.payroll.bean.response.EmpleaveinfoResponseBean;
import kraheja.payroll.bean.response.EmpleaveinfoResponseBean.EmpleaveinfoResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empleaveinfo;
import kraheja.payroll.entity.EmpleaveinfoCK;

public interface EmpleaveinfoEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empleaveinfo>, List<EmpleaveinfoResponseBean>> fetchEmpleaveinfoEntityPojoMapper = empleaveinfoEntityList -> {
return empleaveinfoEntityList.stream().map(empleaveinfoEntity -> {
return EmpleaveinfoResponseBean.builder()
.empcode(empleaveinfoEntity.getEmpleaveinfoCK().getElinEmpcode())
					.leavecode(empleaveinfoEntity.getEmpleaveinfoCK().getElinLeavecode().charAt(0))
					.acyear(empleaveinfoEntity.getEmpleaveinfoCK().getElinAcyear())
					.compoffearned(empleaveinfoEntity.getElinCompoffearned())
					.dayexcessadj(empleaveinfoEntity.getElinDayexcessadj())
					.daysavailed(empleaveinfoEntity.getElinDaysavailed())
					.daysbf(empleaveinfoEntity.getElinDaysbf())
					.daysencashed(empleaveinfoEntity.getElinDaysencashed())
					.daysentitled(empleaveinfoEntity.getElinDaysentitled())
					.ipaddress(empleaveinfoEntity.getElinIpaddress())
					.machinename(empleaveinfoEntity.getElinMachinename())
					.maxdayscf(empleaveinfoEntity.getElinMaxdayscf())
					.maxdaysenc(empleaveinfoEntity.getElinMaxdaysenc())
					.modifiedon(empleaveinfoEntity.getElinModifiedon())
					.module(empleaveinfoEntity.getElinModule())
					.remark(empleaveinfoEntity.getElinRemark())
					.site(empleaveinfoEntity.getElinSite())
					.userid(empleaveinfoEntity.getElinUserid())
.build(); 
}).collect(Collectors.toList());

};

//List<EmpleaveinfoRequestBean>
//(empleaveinfoRequestBeanList)
	public static Function<Object[], List <Empleaveinfo>> addEmpleaveinfoPojoEntityMapper = objectArray -> { 
		List<EmpleaveinfoRequestBean> empleaveinfoRequestBeanList = (List<EmpleaveinfoRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String empcode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String module = (String) (Objects.nonNull(objectArray[2]) ? objectArray[2] : null);

		return empleaveinfoRequestBeanList.stream().map(empleaveinfoRequestBean -> {
			return Empleaveinfo.builder().empleaveinfoCK(EmpleaveinfoCK.builder()
					.elinEmpcode(empcode)
					.elinLeavecode(empleaveinfoRequestBean.getLeavecode())
					.elinAcyear(empleaveinfoRequestBean.getAcyear())
		.build())
					.elinCompoffearned(Objects.nonNull(empleaveinfoRequestBean.getCompoffearned()) ? empleaveinfoRequestBean.getCompoffearned() : BigInteger.ZERO.doubleValue())
					.elinDayexcessadj(Objects.nonNull(empleaveinfoRequestBean.getDayexcessadj()) ? empleaveinfoRequestBean.getDayexcessadj() : BigInteger.ZERO.doubleValue())
					.elinDaysavailed(Objects.nonNull(empleaveinfoRequestBean.getDaysavailed()) ? empleaveinfoRequestBean.getDaysavailed() : BigInteger.ZERO.doubleValue())
					.elinDaysbf(Objects.nonNull(empleaveinfoRequestBean.getDaysbf()) ? empleaveinfoRequestBean.getDaysbf() : BigInteger.ZERO.doubleValue())
					.elinDaysencashed(Objects.nonNull(empleaveinfoRequestBean.getDaysencashed()) ? empleaveinfoRequestBean.getDaysencashed() : BigInteger.ZERO.doubleValue())
					.elinDaysentitled(Objects.nonNull(empleaveinfoRequestBean.getDaysentitled()) ? empleaveinfoRequestBean.getDaysentitled() : BigInteger.ZERO.doubleValue())
					.elinIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.elinMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.elinMaxdayscf(Objects.nonNull(empleaveinfoRequestBean.getMaxdayscf()) ? empleaveinfoRequestBean.getMaxdayscf() : BigInteger.ZERO.doubleValue())
					.elinMaxdaysenc(Objects.nonNull(empleaveinfoRequestBean.getMaxdaysenc()) ? empleaveinfoRequestBean.getMaxdaysenc() : BigInteger.ZERO.doubleValue())
					.elinModifiedon(LocalDateTime.now())
					.elinModule(module)
					.elinRemark(empleaveinfoRequestBean.getRemark())
					.elinSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.elinUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Empleaveinfo, EmpleaveinfoRequestBean, Empleaveinfo> updateEmpleaveinfoEntityPojoMapper = (empleaveinfoEntity, empleaveinfoRequestBean) -> {
		empleaveinfoEntity.getEmpleaveinfoCK().setElinEmpcode(Objects.nonNull(empleaveinfoRequestBean.getEmpcode()) ? empleaveinfoRequestBean.getEmpcode().trim() : empleaveinfoEntity.getEmpleaveinfoCK().getElinEmpcode());
		empleaveinfoEntity.getEmpleaveinfoCK().setElinLeavecode(Objects.nonNull(empleaveinfoRequestBean.getLeavecode()) ? empleaveinfoRequestBean.getLeavecode().trim() : empleaveinfoEntity.getEmpleaveinfoCK().getElinLeavecode());
		empleaveinfoEntity.getEmpleaveinfoCK().setElinAcyear(Objects.nonNull(empleaveinfoRequestBean.getAcyear()) ? empleaveinfoRequestBean.getAcyear().trim() : empleaveinfoEntity.getEmpleaveinfoCK().getElinAcyear());
		empleaveinfoEntity.setElinCompoffearned(Objects.nonNull(empleaveinfoRequestBean.getCompoffearned()) ? empleaveinfoRequestBean.getCompoffearned() : empleaveinfoEntity.getElinCompoffearned());
		empleaveinfoEntity.setElinDayexcessadj(Objects.nonNull(empleaveinfoRequestBean.getDayexcessadj()) ? empleaveinfoRequestBean.getDayexcessadj() : empleaveinfoEntity.getElinDayexcessadj());
		empleaveinfoEntity.setElinDaysavailed(Objects.nonNull(empleaveinfoRequestBean.getDaysavailed()) ? empleaveinfoRequestBean.getDaysavailed() : empleaveinfoEntity.getElinDaysavailed());
		empleaveinfoEntity.setElinDaysbf(Objects.nonNull(empleaveinfoRequestBean.getDaysbf()) ? empleaveinfoRequestBean.getDaysbf() : empleaveinfoEntity.getElinDaysbf());
		empleaveinfoEntity.setElinDaysencashed(Objects.nonNull(empleaveinfoRequestBean.getDaysencashed()) ? empleaveinfoRequestBean.getDaysencashed() : empleaveinfoEntity.getElinDaysencashed());
		empleaveinfoEntity.setElinDaysentitled(Objects.nonNull(empleaveinfoRequestBean.getDaysentitled()) ? empleaveinfoRequestBean.getDaysentitled() : empleaveinfoEntity.getElinDaysentitled());
		empleaveinfoEntity.setElinIpaddress(Objects.nonNull(empleaveinfoRequestBean.getIpaddress()) ? empleaveinfoRequestBean.getIpaddress().trim() : empleaveinfoEntity.getElinIpaddress());
		empleaveinfoEntity.setElinMachinename(Objects.nonNull(empleaveinfoRequestBean.getMachinename()) ? empleaveinfoRequestBean.getMachinename().trim() : empleaveinfoEntity.getElinMachinename());
		empleaveinfoEntity.setElinMaxdayscf(Objects.nonNull(empleaveinfoRequestBean.getMaxdayscf()) ? empleaveinfoRequestBean.getMaxdayscf() : empleaveinfoEntity.getElinMaxdayscf());
		empleaveinfoEntity.setElinMaxdaysenc(Objects.nonNull(empleaveinfoRequestBean.getMaxdaysenc()) ? empleaveinfoRequestBean.getMaxdaysenc() : empleaveinfoEntity.getElinMaxdaysenc());
		empleaveinfoEntity.setElinModifiedon(LocalDateTime.now()) ; 
		empleaveinfoEntity.setElinModule(Objects.nonNull(empleaveinfoRequestBean.getModule()) ? empleaveinfoRequestBean.getModule().trim() : empleaveinfoEntity.getElinModule());
		empleaveinfoEntity.setElinRemark(Objects.nonNull(empleaveinfoRequestBean.getRemark()) ? empleaveinfoRequestBean.getRemark().trim() : empleaveinfoEntity.getElinRemark());
		empleaveinfoEntity.setElinSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empleaveinfoEntity.setElinUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return empleaveinfoEntity;
	};

}
