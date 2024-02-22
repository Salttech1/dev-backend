package kraheja.payroll.computationofsalary.mappers;

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
import kraheja.payroll.bean.EmppaymonthBean;
import kraheja.payroll.bean.request.EmppaymonthRequestBean;
import kraheja.payroll.bean.response.EmppaymonthResponseBean;
import kraheja.payroll.bean.response.EmppaymonthResponseBean.EmppaymonthResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Emppaymonth;
import kraheja.payroll.entity.EmppaymonthCK;

public interface EmppaymonthEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Emppaymonth>, List<EmppaymonthResponseBean>> fetchEmppaymonthEntityPojoMapper = emppaymonthEntityList -> {
return emppaymonthEntityList.stream().map(emppaymonthEntity -> {
return EmppaymonthResponseBean.builder()
.empcode(emppaymonthEntity.getEmppaymonthCK().getEmpmEmpcode())
					.salarytype(emppaymonthEntity.getEmppaymonthCK().getEmpmSalarytype())
					.paymonth(emppaymonthEntity.getEmppaymonthCK().getEmpmPaymonth())
					.actranser(emppaymonthEntity.getEmpmActranser())
					.acvounum(emppaymonthEntity.getEmpmAcvounum())
					.daysabsent(emppaymonthEntity.getEmpmDaysabsent())
					.daysadjlastmth(emppaymonthEntity.getEmpmDaysadjlastmth())
					.daysadjprvlastmth(emppaymonthEntity.getEmpmDaysadjprvlastmth())
					.daysarrears(emppaymonthEntity.getEmpmDaysarrears())
					.daysencashed(emppaymonthEntity.getEmpmDaysencashed())
					.daysnhpay(emppaymonthEntity.getEmpmDaysnhpay())
					.dayspaid(emppaymonthEntity.getEmpmDayspaid())
					.daysunionfund(emppaymonthEntity.getEmpmDaysunionfund())
					.instrumentbank(emppaymonthEntity.getEmpmInstrumentbank())
					.instrumentdate(Objects.nonNull(emppaymonthEntity.getEmpmInstrumentdate()) ? emppaymonthEntity.getEmpmInstrumentdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.instrumentno(emppaymonthEntity.getEmpmInstrumentno())
					.instrumenttype(emppaymonthEntity.getEmpmInstrumenttype())
					.ipaddress(emppaymonthEntity.getEmpmIpaddress())
					.itdeclrevno(emppaymonthEntity.getEmpmItdeclrevno())
					.machinename(emppaymonthEntity.getEmpmMachinename())
					.modifiedon(emppaymonthEntity.getEmpmModifiedon())
					.module(emppaymonthEntity.getEmpmModule())
					.othours(emppaymonthEntity.getEmpmOthours())
					.othrslastmth(emppaymonthEntity.getEmpmOthrslastmth())
					.othrsprvlastmth(emppaymonthEntity.getEmpmOthrsprvlastmth())
					.paidfrom(Objects.nonNull(emppaymonthEntity.getEmpmPaidfrom()) ? emppaymonthEntity.getEmpmPaidfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.paidupto(Objects.nonNull(emppaymonthEntity.getEmpmPaidupto()) ? emppaymonthEntity.getEmpmPaidupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.paydate(Objects.nonNull(emppaymonthEntity.getEmpmPaydate()) ? emppaymonthEntity.getEmpmPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.paystatus(emppaymonthEntity.getEmpmPaystatus())
					.reg_settle(emppaymonthEntity.getEmpmReg_Settle())
					.remark(emppaymonthEntity.getEmpmRemark())
					.site(emppaymonthEntity.getEmpmSite())
					.userid(emppaymonthEntity.getEmpmUserid())
.build(); 
}).collect(Collectors.toList());

};

	public static Function<List<EmppaymonthBean>, List <Emppaymonth>> addEmppaymonthEntityMapper = (emppaymonthRequestBeanList) -> {
		return emppaymonthRequestBeanList.stream().map(emppaymonthRequestBean -> {
			return Emppaymonth.builder().emppaymonthCK(EmppaymonthCK.builder()
					.empmEmpcode(emppaymonthRequestBean.getEmpcode())
					.empmSalarytype(emppaymonthRequestBean.getSalarytype())
					.empmPaymonth(emppaymonthRequestBean.getPaymonth())
		.build())
					.empmActranser(emppaymonthRequestBean.getActranser())
					.empmAcvounum(emppaymonthRequestBean.getAcvounum())
					.empmDaysabsent(Objects.nonNull(emppaymonthRequestBean.getDaysabsent()) ? emppaymonthRequestBean.getDaysabsent().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysadjlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjlastmth()) ? emppaymonthRequestBean.getDaysadjlastmth().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysadjprvlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjprvlastmth()) ? emppaymonthRequestBean.getDaysadjprvlastmth().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysarrears(Objects.nonNull(emppaymonthRequestBean.getDaysarrears()) ? emppaymonthRequestBean.getDaysarrears().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysencashed(Objects.nonNull(emppaymonthRequestBean.getDaysencashed()) ? emppaymonthRequestBean.getDaysencashed().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysnhpay(Objects.nonNull(emppaymonthRequestBean.getDaysnhpay()) ? emppaymonthRequestBean.getDaysnhpay().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDayspaid(Objects.nonNull(emppaymonthRequestBean.getDayspaid()) ? emppaymonthRequestBean.getDayspaid().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmDaysunionfund(Objects.nonNull(emppaymonthRequestBean.getDaysunionfund()) ? emppaymonthRequestBean.getDaysunionfund().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmInstrumentbank(emppaymonthRequestBean.getInstrumentbank())
					.empmInstrumentdate(Objects.nonNull(emppaymonthRequestBean.getInstrumentdate()) ? LocalDate.parse(emppaymonthRequestBean.getInstrumentdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmInstrumentno(emppaymonthRequestBean.getInstrumentno())
					.empmInstrumenttype(emppaymonthRequestBean.getInstrumenttype().toString())
					.empmIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.empmItdeclrevno(Objects.nonNull(emppaymonthRequestBean.getItdeclrevno()) ? emppaymonthRequestBean.getItdeclrevno().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.empmModifiedon(LocalDateTime.now())
					.empmModule(emppaymonthRequestBean.getModule())
					.empmOthours(Objects.nonNull(emppaymonthRequestBean.getOthours()) ? emppaymonthRequestBean.getOthours().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmOthrslastmth(Objects.nonNull(emppaymonthRequestBean.getOthrslastmth()) ? emppaymonthRequestBean.getOthrslastmth().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmOthrsprvlastmth(Objects.nonNull(emppaymonthRequestBean.getOthrsprvlastmth()) ? emppaymonthRequestBean.getOthrsprvlastmth().doubleValue() : BigInteger.ZERO.doubleValue())
					.empmPaidfrom(Objects.nonNull(emppaymonthRequestBean.getPaidfrom()) ? LocalDate.parse(emppaymonthRequestBean.getPaidfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaidupto(Objects.nonNull(emppaymonthRequestBean.getPaidupto()) ? LocalDate.parse(emppaymonthRequestBean.getPaidupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaydate(Objects.nonNull(emppaymonthRequestBean.getPaydate()) ? LocalDate.parse(emppaymonthRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaystatus(emppaymonthRequestBean.getPaystatus().toString())
					.empmReg_Settle(emppaymonthRequestBean.getReg_settle().toString())
					.empmRemark(emppaymonthRequestBean.getRemark())
					.empmSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite())? GenericAuditContextHolder.getContext().getSite() : "MUM")
					.empmUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
					.build();
		}).collect(Collectors.toList());
	};

//	addEmppaymonthPojoEntityMapper auto generated with Rahul oracle function but not used in initialisation program. Above function addEmppaymonthEntityMapper is used  for adding
	public static Function<List<EmppaymonthRequestBean>, List <Emppaymonth>> addEmppaymonthPojoEntityMapper = (emppaymonthRequestBeanList) -> { 
return emppaymonthRequestBeanList.stream().map(emppaymonthRequestBean -> {
return Emppaymonth.builder().emppaymonthCK(EmppaymonthCK.builder()
					.empmEmpcode(emppaymonthRequestBean.getEmpcode())
					.empmSalarytype(emppaymonthRequestBean.getSalarytype())
					.empmPaymonth(emppaymonthRequestBean.getPaymonth())
		.build())
					.empmActranser(emppaymonthRequestBean.getActranser())
					.empmAcvounum(emppaymonthRequestBean.getAcvounum())
					.empmDaysabsent(Objects.nonNull(emppaymonthRequestBean.getDaysabsent()) ? emppaymonthRequestBean.getDaysabsent() : BigInteger.ZERO.doubleValue())
					.empmDaysadjlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjlastmth()) ? emppaymonthRequestBean.getDaysadjlastmth() : BigInteger.ZERO.doubleValue())
					.empmDaysadjprvlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjprvlastmth()) ? emppaymonthRequestBean.getDaysadjprvlastmth() : BigInteger.ZERO.doubleValue())
					.empmDaysarrears(Objects.nonNull(emppaymonthRequestBean.getDaysarrears()) ? emppaymonthRequestBean.getDaysarrears() : BigInteger.ZERO.doubleValue())
					.empmDaysencashed(Objects.nonNull(emppaymonthRequestBean.getDaysencashed()) ? emppaymonthRequestBean.getDaysencashed() : BigInteger.ZERO.doubleValue())
					.empmDaysnhpay(Objects.nonNull(emppaymonthRequestBean.getDaysnhpay()) ? emppaymonthRequestBean.getDaysnhpay() : BigInteger.ZERO.doubleValue())
					.empmDayspaid(Objects.nonNull(emppaymonthRequestBean.getDayspaid()) ? emppaymonthRequestBean.getDayspaid() : BigInteger.ZERO.doubleValue())
					.empmDaysunionfund(Objects.nonNull(emppaymonthRequestBean.getDaysunionfund()) ? emppaymonthRequestBean.getDaysunionfund() : BigInteger.ZERO.doubleValue())
					.empmInstrumentbank(emppaymonthRequestBean.getInstrumentbank())
					.empmInstrumentdate(Objects.nonNull(emppaymonthRequestBean.getInstrumentdate()) ? LocalDate.parse(emppaymonthRequestBean.getInstrumentdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmInstrumentno(emppaymonthRequestBean.getInstrumentno())
					.empmInstrumenttype(emppaymonthRequestBean.getInstrumenttype())
					.empmIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.empmItdeclrevno(Objects.nonNull(emppaymonthRequestBean.getItdeclrevno()) ? emppaymonthRequestBean.getItdeclrevno() : BigInteger.ZERO.doubleValue())
					.empmMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.empmModifiedon(LocalDateTime.now())
					.empmModule(emppaymonthRequestBean.getModule())
					.empmOthours(Objects.nonNull(emppaymonthRequestBean.getOthours()) ? emppaymonthRequestBean.getOthours() : BigInteger.ZERO.doubleValue())
					.empmOthrslastmth(Objects.nonNull(emppaymonthRequestBean.getOthrslastmth()) ? emppaymonthRequestBean.getOthrslastmth() : BigInteger.ZERO.doubleValue())
					.empmOthrsprvlastmth(Objects.nonNull(emppaymonthRequestBean.getOthrsprvlastmth()) ? emppaymonthRequestBean.getOthrsprvlastmth() : BigInteger.ZERO.doubleValue())
					.empmPaidfrom(Objects.nonNull(emppaymonthRequestBean.getPaidfrom()) ? LocalDate.parse(emppaymonthRequestBean.getPaidfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaidupto(Objects.nonNull(emppaymonthRequestBean.getPaidupto()) ? LocalDate.parse(emppaymonthRequestBean.getPaidupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaydate(Objects.nonNull(emppaymonthRequestBean.getPaydate()) ? LocalDate.parse(emppaymonthRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.empmPaystatus(emppaymonthRequestBean.getPaystatus())
					.empmReg_Settle(emppaymonthRequestBean.getReg_settle())
					.empmRemark(emppaymonthRequestBean.getRemark())
					.empmSite(GenericAuditContextHolder.getContext().getSite())
					.empmUserid(GenericAuditContextHolder.getContext().getUserid())
		
.build();
}).collect(Collectors.toList());
} ;
	public static BiFunction<Emppaymonth, EmppaymonthRequestBean, Emppaymonth> updateEmppaymonthEntityPojoMapper = (emppaymonthEntity, emppaymonthRequestBean) -> {
		emppaymonthEntity.getEmppaymonthCK().setEmpmEmpcode(Objects.nonNull(emppaymonthRequestBean.getEmpcode()) ? emppaymonthRequestBean.getEmpcode().trim() : emppaymonthEntity.getEmppaymonthCK().getEmpmEmpcode());
		emppaymonthEntity.getEmppaymonthCK().setEmpmSalarytype(Objects.nonNull(emppaymonthRequestBean.getSalarytype()) ? emppaymonthRequestBean.getSalarytype().trim() : emppaymonthEntity.getEmppaymonthCK().getEmpmSalarytype());
		emppaymonthEntity.getEmppaymonthCK().setEmpmPaymonth(Objects.nonNull(emppaymonthRequestBean.getPaymonth()) ? emppaymonthRequestBean.getPaymonth().trim() : emppaymonthEntity.getEmppaymonthCK().getEmpmPaymonth());
		emppaymonthEntity.setEmpmActranser(Objects.nonNull(emppaymonthRequestBean.getActranser()) ? emppaymonthRequestBean.getActranser().trim() : emppaymonthEntity.getEmpmActranser());
		emppaymonthEntity.setEmpmAcvounum(Objects.nonNull(emppaymonthRequestBean.getAcvounum()) ? emppaymonthRequestBean.getAcvounum().trim() : emppaymonthEntity.getEmpmAcvounum());
		emppaymonthEntity.setEmpmDaysabsent(Objects.nonNull(emppaymonthRequestBean.getDaysabsent()) ? emppaymonthRequestBean.getDaysabsent() : emppaymonthEntity.getEmpmDaysabsent());
		emppaymonthEntity.setEmpmDaysadjlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjlastmth()) ? emppaymonthRequestBean.getDaysadjlastmth() : emppaymonthEntity.getEmpmDaysadjlastmth());
		emppaymonthEntity.setEmpmDaysadjprvlastmth(Objects.nonNull(emppaymonthRequestBean.getDaysadjprvlastmth()) ? emppaymonthRequestBean.getDaysadjprvlastmth() : emppaymonthEntity.getEmpmDaysadjprvlastmth());
		emppaymonthEntity.setEmpmDaysarrears(Objects.nonNull(emppaymonthRequestBean.getDaysarrears()) ? emppaymonthRequestBean.getDaysarrears() : emppaymonthEntity.getEmpmDaysarrears());
		emppaymonthEntity.setEmpmDaysencashed(Objects.nonNull(emppaymonthRequestBean.getDaysencashed()) ? emppaymonthRequestBean.getDaysencashed() : emppaymonthEntity.getEmpmDaysencashed());
		emppaymonthEntity.setEmpmDaysnhpay(Objects.nonNull(emppaymonthRequestBean.getDaysnhpay()) ? emppaymonthRequestBean.getDaysnhpay() : emppaymonthEntity.getEmpmDaysnhpay());
		emppaymonthEntity.setEmpmDayspaid(Objects.nonNull(emppaymonthRequestBean.getDayspaid()) ? emppaymonthRequestBean.getDayspaid() : emppaymonthEntity.getEmpmDayspaid());
		emppaymonthEntity.setEmpmDaysunionfund(Objects.nonNull(emppaymonthRequestBean.getDaysunionfund()) ? emppaymonthRequestBean.getDaysunionfund() : emppaymonthEntity.getEmpmDaysunionfund());
		emppaymonthEntity.setEmpmInstrumentbank(Objects.nonNull(emppaymonthRequestBean.getInstrumentbank()) ? emppaymonthRequestBean.getInstrumentbank().trim() : emppaymonthEntity.getEmpmInstrumentbank());
		emppaymonthEntity.setEmpmInstrumentdate(Objects.nonNull(emppaymonthRequestBean.getInstrumentdate()) ? LocalDate.parse(emppaymonthRequestBean.getInstrumentdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppaymonthEntity.getEmpmInstrumentdate());
		emppaymonthEntity.setEmpmInstrumentno(Objects.nonNull(emppaymonthRequestBean.getInstrumentno()) ? emppaymonthRequestBean.getInstrumentno().trim() : emppaymonthEntity.getEmpmInstrumentno());
		emppaymonthEntity.setEmpmInstrumenttype(Objects.nonNull(emppaymonthRequestBean.getInstrumenttype()) ? emppaymonthRequestBean.getInstrumenttype().trim() : emppaymonthEntity.getEmpmInstrumenttype());
		emppaymonthEntity.setEmpmIpaddress(Objects.nonNull(emppaymonthRequestBean.getIpaddress()) ? emppaymonthRequestBean.getIpaddress().trim() : emppaymonthEntity.getEmpmIpaddress());
		emppaymonthEntity.setEmpmItdeclrevno(Objects.nonNull(emppaymonthRequestBean.getItdeclrevno()) ? emppaymonthRequestBean.getItdeclrevno() : emppaymonthEntity.getEmpmItdeclrevno());
		emppaymonthEntity.setEmpmMachinename(Objects.nonNull(emppaymonthRequestBean.getMachinename()) ? emppaymonthRequestBean.getMachinename().trim() : emppaymonthEntity.getEmpmMachinename());
		emppaymonthEntity.setEmpmModifiedon(LocalDateTime.now()) ; 
		emppaymonthEntity.setEmpmModule(Objects.nonNull(emppaymonthRequestBean.getModule()) ? emppaymonthRequestBean.getModule().trim() : emppaymonthEntity.getEmpmModule());
		emppaymonthEntity.setEmpmOthours(Objects.nonNull(emppaymonthRequestBean.getOthours()) ? emppaymonthRequestBean.getOthours() : emppaymonthEntity.getEmpmOthours());
		emppaymonthEntity.setEmpmOthrslastmth(Objects.nonNull(emppaymonthRequestBean.getOthrslastmth()) ? emppaymonthRequestBean.getOthrslastmth() : emppaymonthEntity.getEmpmOthrslastmth());
		emppaymonthEntity.setEmpmOthrsprvlastmth(Objects.nonNull(emppaymonthRequestBean.getOthrsprvlastmth()) ? emppaymonthRequestBean.getOthrsprvlastmth() : emppaymonthEntity.getEmpmOthrsprvlastmth());
		emppaymonthEntity.setEmpmPaidfrom(Objects.nonNull(emppaymonthRequestBean.getPaidfrom()) ? LocalDate.parse(emppaymonthRequestBean.getPaidfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppaymonthEntity.getEmpmPaidfrom());
		emppaymonthEntity.setEmpmPaidupto(Objects.nonNull(emppaymonthRequestBean.getPaidupto()) ? LocalDate.parse(emppaymonthRequestBean.getPaidupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppaymonthEntity.getEmpmPaidupto());
		emppaymonthEntity.setEmpmPaydate(Objects.nonNull(emppaymonthRequestBean.getPaydate()) ? LocalDate.parse(emppaymonthRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : emppaymonthEntity.getEmpmPaydate());
		emppaymonthEntity.setEmpmPaystatus(Objects.nonNull(emppaymonthRequestBean.getPaystatus()) ? emppaymonthRequestBean.getPaystatus().trim() : emppaymonthEntity.getEmpmPaystatus());
		emppaymonthEntity.setEmpmReg_Settle(Objects.nonNull(emppaymonthRequestBean.getReg_settle()) ? emppaymonthRequestBean.getReg_settle().trim() : emppaymonthEntity.getEmpmReg_Settle());
		emppaymonthEntity.setEmpmRemark(Objects.nonNull(emppaymonthRequestBean.getRemark()) ? emppaymonthRequestBean.getRemark().trim() : emppaymonthEntity.getEmpmRemark());
		emppaymonthEntity.setEmpmSite(GenericAuditContextHolder.getContext().getSite()) ; 
		emppaymonthEntity.setEmpmUserid(GenericAuditContextHolder.getContext().getUserid()) ; 


		return emppaymonthEntity;
	};

}