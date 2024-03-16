// Developed By  - 	kalpana.m
// Developed on - 25-07-23
// Mode  - Data Entry
// Purpose - Empjobinfo Entry / Edit
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
import kraheja.payroll.bean.request.EmpjobinfoRequestBean;
import kraheja.payroll.bean.response.EmpjobinfoResponseBean;
import kraheja.payroll.bean.response.EmpjobinfoResponseBean.EmpjobinfoResponseBeanBuilder;
import java.util.stream.Collectors;
import kraheja.payroll.entity.Empjobinfo;
import kraheja.payroll.entity.EmpjobinfoCK;

public interface EmpjobinfoEntityPojoMapper {
	@SuppressWarnings("unchecked")
public static Function	<List<Empjobinfo>, List<EmpjobinfoResponseBean>> fetchEmpjobinfoEntityPojoMapper = empjobinfoEntityList -> {
return empjobinfoEntityList.stream().map(empjobinfoEntity -> {
return EmpjobinfoResponseBean.builder()
.empcode(empjobinfoEntity.getEmpjobinfoCK().getEjinEmpcode())
					.effectivefrom(Objects.nonNull(empjobinfoEntity.getEmpjobinfoCK().getEjinEffectivefrom()) ? empjobinfoEntity.getEmpjobinfoCK().getEjinEffectivefrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.appliedon(Objects.nonNull(empjobinfoEntity.getEjinAppliedon()) ? empjobinfoEntity.getEjinAppliedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.appointedby(empjobinfoEntity.getEjinAppointedby())
					.appointedon(Objects.nonNull(empjobinfoEntity.getEjinAppointedon()) ? empjobinfoEntity.getEjinAppointedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.bank(empjobinfoEntity.getEjinBank())
					.bankacno(empjobinfoEntity.getEjinBankacno())
					.bankactype(empjobinfoEntity.getEjinBankactype())
					.bankbranch(empjobinfoEntity.getEjinBankbranch())
					.branch(empjobinfoEntity.getEjinBranch())
					.company(empjobinfoEntity.getEjinCompany())
					.costcentre(empjobinfoEntity.getEjinCostcentre())
					.department(empjobinfoEntity.getEjinDepartment())
					.deputedincoy(empjobinfoEntity.getEjinDeputedincoy())
					.desigpost(empjobinfoEntity.getEjinDesigpost())
					.desigtax(empjobinfoEntity.getEjinDesigtax())
					.directoryn(empjobinfoEntity.getEjinDirectoryn())
					.division(empjobinfoEntity.getEjinDivision())
					.dutyhours(empjobinfoEntity.getEjinDutyhours())
					.effectiveupto(Objects.nonNull(empjobinfoEntity.getEjinEffectiveupto()) ? empjobinfoEntity.getEjinEffectiveupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.emptype(empjobinfoEntity.getEjinEmptype())
					.grade(empjobinfoEntity.getEjinGrade())
					.gratuitydate(Objects.nonNull(empjobinfoEntity.getEjinGratuitydate()) ? empjobinfoEntity.getEjinGratuitydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.gratuitypaymonth(empjobinfoEntity.getEjinGratuitypaymonth())
					.ipaddress(empjobinfoEntity.getEjinIpaddress())
					.jobfrom(Objects.nonNull(empjobinfoEntity.getEjinJobfrom()) ? empjobinfoEntity.getEjinJobfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.jobstatus(empjobinfoEntity.getEjinJobstatus())
					.jobtype(empjobinfoEntity.getEjinJobtype())
					.jobupto(Objects.nonNull(empjobinfoEntity.getEjinJobupto()) ? empjobinfoEntity.getEjinJobupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.joindate(Objects.nonNull(empjobinfoEntity.getEjinJoindate()) ? empjobinfoEntity.getEjinJoindate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.joinedyn(empjobinfoEntity.getEjinJoinedyn())
					.level(empjobinfoEntity.getEjinLevel())
					.location(empjobinfoEntity.getEjinLocation())
					.machinename(empjobinfoEntity.getEjinMachinename())
					.modifiedon(empjobinfoEntity.getEjinModifiedon())
					.module(empjobinfoEntity.getEjinModule())
					.noticedays(empjobinfoEntity.getEjinNoticedays())
					.oldbank(empjobinfoEntity.getEjinOldbank())
					.oldbankacno(empjobinfoEntity.getEjinOldbankacno())
					.origjoindate(Objects.nonNull(empjobinfoEntity.getEjinOrigjoindate()) ? empjobinfoEntity.getEjinOrigjoindate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.paymode(empjobinfoEntity.getEjinPaymode())
					.prevcoy(empjobinfoEntity.getEjinPrevcoy())
					.probationupto(Objects.nonNull(empjobinfoEntity.getEjinProbationupto()) ? empjobinfoEntity.getEjinProbationupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.probmonths(empjobinfoEntity.getEjinProbmonths())
					.punchcardno(empjobinfoEntity.getEjinPunchcardno())
					.reasonofleav(empjobinfoEntity.getEjinReasonofleav())
					.region(empjobinfoEntity.getEjinRegion())
					.remark(empjobinfoEntity.getEjinRemark())
					.reportingto(empjobinfoEntity.getEjinReportingto())
					.resignationdate(Objects.nonNull(empjobinfoEntity.getEjinResignationdate()) ? empjobinfoEntity.getEjinResignationdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.salarygrpcode(empjobinfoEntity.getEjinSalarygrpcode())
					.section(empjobinfoEntity.getEjinSection())
					.settlementdate(Objects.nonNull(empjobinfoEntity.getEjinSettlementdate()) ? empjobinfoEntity.getEjinSettlementdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.shiftcode(empjobinfoEntity.getEjinShiftcode())
					.site(empjobinfoEntity.getEjinSite())
					.termdate(Objects.nonNull(empjobinfoEntity.getEjinTermdate()) ? empjobinfoEntity.getEjinTermdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.ticketno(empjobinfoEntity.getEjinTicketno())
					.traineeupto(Objects.nonNull(empjobinfoEntity.getEjinTraineeupto()) ? empjobinfoEntity.getEjinTraineeupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.userid(empjobinfoEntity.getEjinUserid())
					.worksite(empjobinfoEntity.getEjinWorksite())
					.xferdate(Objects.nonNull(empjobinfoEntity.getEjinXferdate()) ? empjobinfoEntity.getEjinXferdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
.build(); 
}).collect(Collectors.toList());

};

public static Function<EmpjobinfoRequestBean, Empjobinfo> addEmpjobinfoPojoEntityMapper = empjobinfoRequestBean -> {
return Empjobinfo.builder().empjobinfoCK(EmpjobinfoCK.builder()
				.ejinEmpcode(empjobinfoRequestBean.getEmpcode())
				.ejinEffectivefrom(Objects.nonNull(empjobinfoRequestBean.getEffectivefrom()) ? LocalDate.parse(empjobinfoRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
	.build())
				.ejinAppliedon(Objects.nonNull(empjobinfoRequestBean.getAppliedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppliedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinAppointedby(empjobinfoRequestBean.getAppointedby())
				.ejinAppointedon(Objects.nonNull(empjobinfoRequestBean.getAppointedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppointedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinBank(empjobinfoRequestBean.getBank())
				.ejinBankacno(empjobinfoRequestBean.getBankacno())
				.ejinBankactype(empjobinfoRequestBean.getBankactype())
				.ejinBankbranch(empjobinfoRequestBean.getBankbranch())
				.ejinBranch(empjobinfoRequestBean.getBranch())
				.ejinCompany(empjobinfoRequestBean.getCompany())
				.ejinCostcentre(empjobinfoRequestBean.getCostcentre())
				.ejinDepartment(empjobinfoRequestBean.getDepartment())
				.ejinDeputedincoy(empjobinfoRequestBean.getDeputedincoy())
				.ejinDesigpost(empjobinfoRequestBean.getDesigpost())
				.ejinDesigtax(empjobinfoRequestBean.getDesigtax())
				.ejinDirectoryn(empjobinfoRequestBean.getDirectoryn())
				.ejinDivision(empjobinfoRequestBean.getDivision())
				.ejinDutyhours(Objects.nonNull(empjobinfoRequestBean.getDutyhours()) ? empjobinfoRequestBean.getDutyhours() : BigInteger.ZERO.intValue())
				.ejinEffectiveupto(Objects.nonNull(empjobinfoRequestBean.getEffectiveupto()) ? LocalDate.parse(empjobinfoRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinEmptype(empjobinfoRequestBean.getEmptype())
				.ejinGrade(empjobinfoRequestBean.getGrade())
				.ejinGratuitydate(Objects.nonNull(empjobinfoRequestBean.getGratuitydate()) ? LocalDate.parse(empjobinfoRequestBean.getGratuitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinGratuitypaymonth(empjobinfoRequestBean.getGratuitypaymonth())
				.ejinIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
				.ejinJobfrom(Objects.nonNull(empjobinfoRequestBean.getJobfrom()) ? LocalDate.parse(empjobinfoRequestBean.getJobfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinJobstatus(empjobinfoRequestBean.getJobstatus())
				.ejinJobtype(empjobinfoRequestBean.getJobtype())
				.ejinJobupto(Objects.nonNull(empjobinfoRequestBean.getJobupto()) ? LocalDate.parse(empjobinfoRequestBean.getJobupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinJoindate(Objects.nonNull(empjobinfoRequestBean.getJoindate()) ? LocalDate.parse(empjobinfoRequestBean.getJoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinJoinedyn(empjobinfoRequestBean.getJoinedyn())
				.ejinLevel(empjobinfoRequestBean.getLevel())
				.ejinLocation(empjobinfoRequestBean.getLocation())
				.ejinMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
				.ejinModifiedon(LocalDateTime.now())
				.ejinModule(empjobinfoRequestBean.getModule())
				.ejinNoticedays(Objects.nonNull(empjobinfoRequestBean.getNoticedays()) ? empjobinfoRequestBean.getNoticedays() : BigInteger.ZERO.intValue())
				.ejinOldbank(empjobinfoRequestBean.getOldbank())
				.ejinOldbankacno(empjobinfoRequestBean.getOldbankacno())
				.ejinOrigjoindate(Objects.nonNull(empjobinfoRequestBean.getOrigjoindate()) ? LocalDate.parse(empjobinfoRequestBean.getOrigjoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinPaymode(empjobinfoRequestBean.getPaymode())
				.ejinPrevcoy(empjobinfoRequestBean.getPrevcoy())
				.ejinProbationupto(Objects.nonNull(empjobinfoRequestBean.getProbationupto()) ? LocalDate.parse(empjobinfoRequestBean.getProbationupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinProbmonths(Objects.nonNull(empjobinfoRequestBean.getProbmonths()) ? empjobinfoRequestBean.getProbmonths() : BigInteger.ZERO.intValue())
				.ejinPunchcardno(empjobinfoRequestBean.getPunchcardno())
				.ejinReasonofleav(empjobinfoRequestBean.getReasonofleav())
				.ejinRegion(empjobinfoRequestBean.getRegion())
				.ejinRemark(empjobinfoRequestBean.getRemark())
				.ejinReportingto(empjobinfoRequestBean.getReportingto())
				.ejinResignationdate(Objects.nonNull(empjobinfoRequestBean.getResignationdate()) ? LocalDate.parse(empjobinfoRequestBean.getResignationdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinSalarygrpcode(empjobinfoRequestBean.getSalarygrpcode())
				.ejinSection(empjobinfoRequestBean.getSection())
				.ejinSettlementdate(Objects.nonNull(empjobinfoRequestBean.getSettlementdate()) ? LocalDate.parse(empjobinfoRequestBean.getSettlementdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinShiftcode(empjobinfoRequestBean.getShiftcode())
				.ejinSite(Objects.nonNull(GenericAuditContextHolder.getContext().getSite()) ? GenericAuditContextHolder.getContext().getSite() : "MUM")
				.ejinTermdate(Objects.nonNull(empjobinfoRequestBean.getTermdate()) ? LocalDate.parse(empjobinfoRequestBean.getTermdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinTicketno(empjobinfoRequestBean.getTicketno())
				.ejinTraineeupto(Objects.nonNull(empjobinfoRequestBean.getTraineeupto()) ? LocalDate.parse(empjobinfoRequestBean.getTraineeupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.ejinUserid(Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid() : "KRaheja")
				.ejinWorksite(empjobinfoRequestBean.getWorksite())
				.ejinXferdate(Objects.nonNull(empjobinfoRequestBean.getXferdate()) ? LocalDate.parse(empjobinfoRequestBean.getXferdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
	
.build();
} ;


//	public static Function<List<EmpjobinfoRequestBean>, List <Empjobinfo>> addEmpjobinfoPojoEntityMapper = (empjobinfoRequestBeanList) -> { 
//return empjobinfoRequestBeanList.stream().map(empjobinfoRequestBean -> {
//return Empjobinfo.builder().empjobinfoCK(EmpjobinfoCK.builder()
//					.ejinEmpcode(empjobinfoRequestBean.getEmpcode())
//					.ejinEffectivefrom(Objects.nonNull(empjobinfoRequestBean.getEffectivefrom()) ? LocalDate.parse(empjobinfoRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//		.build())
//					.ejinAppliedon(Objects.nonNull(empjobinfoRequestBean.getAppliedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppliedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinAppointedby(empjobinfoRequestBean.getAppointedby())
//					.ejinAppointedon(Objects.nonNull(empjobinfoRequestBean.getAppointedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppointedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinBank(empjobinfoRequestBean.getBank())
//					.ejinBankacno(empjobinfoRequestBean.getBankacno())
//					.ejinBankactype(empjobinfoRequestBean.getBankactype())
//					.ejinBankbranch(empjobinfoRequestBean.getBankbranch())
//					.ejinBranch(empjobinfoRequestBean.getBranch())
//					.ejinCompany(empjobinfoRequestBean.getCompany())
//					.ejinCostcentre(empjobinfoRequestBean.getCostcentre())
//					.ejinDepartment(empjobinfoRequestBean.getDepartment())
//					.ejinDeputedincoy(empjobinfoRequestBean.getDeputedincoy())
//					.ejinDesigpost(empjobinfoRequestBean.getDesigpost())
//					.ejinDesigtax(empjobinfoRequestBean.getDesigtax())
//					.ejinDirectoryn(empjobinfoRequestBean.getDirectoryn())
//					.ejinDivision(empjobinfoRequestBean.getDivision())
//					.ejinDutyhours(Objects.nonNull(empjobinfoRequestBean.getDutyhours()) ? empjobinfoRequestBean.getDutyhours() : BigInteger.ZERO.intValue())
//					.ejinEffectiveupto(Objects.nonNull(empjobinfoRequestBean.getEffectiveupto()) ? LocalDate.parse(empjobinfoRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinEmptype(empjobinfoRequestBean.getEmptype())
//					.ejinGrade(empjobinfoRequestBean.getGrade())
//					.ejinGratuitydate(Objects.nonNull(empjobinfoRequestBean.getGratuitydate()) ? LocalDate.parse(empjobinfoRequestBean.getGratuitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinGratuitypaymonth(empjobinfoRequestBean.getGratuitypaymonth())
//					.ejinIpaddress(empjobinfoRequestBean.getIpaddress())
//					.ejinJobfrom(Objects.nonNull(empjobinfoRequestBean.getJobfrom()) ? LocalDate.parse(empjobinfoRequestBean.getJobfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinJobstatus(empjobinfoRequestBean.getJobstatus())
//					.ejinJobtype(empjobinfoRequestBean.getJobtype())
//					.ejinJobupto(Objects.nonNull(empjobinfoRequestBean.getJobupto()) ? LocalDate.parse(empjobinfoRequestBean.getJobupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinJoindate(Objects.nonNull(empjobinfoRequestBean.getJoindate()) ? LocalDate.parse(empjobinfoRequestBean.getJoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinJoinedyn(empjobinfoRequestBean.getJoinedyn())
//					.ejinLevel(empjobinfoRequestBean.getLevel())
//					.ejinLocation(empjobinfoRequestBean.getLocation())
//					.ejinMachinename(empjobinfoRequestBean.getMachinename())
//					.ejinModifiedon(LocalDateTime.now())
//					.ejinModule(empjobinfoRequestBean.getModule())
//					.ejinNoticedays(Objects.nonNull(empjobinfoRequestBean.getNoticedays()) ? empjobinfoRequestBean.getNoticedays() : BigInteger.ZERO.intValue())
//					.ejinOldbank(empjobinfoRequestBean.getOldbank())
//					.ejinOldbankacno(empjobinfoRequestBean.getOldbankacno())
//					.ejinOrigjoindate(Objects.nonNull(empjobinfoRequestBean.getOrigjoindate()) ? LocalDate.parse(empjobinfoRequestBean.getOrigjoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinPaymode(empjobinfoRequestBean.getPaymode())
//					.ejinPrevcoy(empjobinfoRequestBean.getPrevcoy())
//					.ejinProbationupto(Objects.nonNull(empjobinfoRequestBean.getProbationupto()) ? LocalDate.parse(empjobinfoRequestBean.getProbationupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinProbmonths(Objects.nonNull(empjobinfoRequestBean.getProbmonths()) ? empjobinfoRequestBean.getProbmonths() : BigInteger.ZERO.intValue())
//					.ejinPunchcardno(empjobinfoRequestBean.getPunchcardno())
//					.ejinReasonofleav(empjobinfoRequestBean.getReasonofleav())
//					.ejinRegion(empjobinfoRequestBean.getRegion())
//					.ejinRemark(empjobinfoRequestBean.getRemark())
//					.ejinReportingto(empjobinfoRequestBean.getReportingto())
//					.ejinSalarygrpcode(empjobinfoRequestBean.getSalarygrpcode())
//					.ejinSection(empjobinfoRequestBean.getSection())
//					.ejinSettlementdate(Objects.nonNull(empjobinfoRequestBean.getSettlementdate()) ? LocalDate.parse(empjobinfoRequestBean.getSettlementdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinShiftcode(empjobinfoRequestBean.getShiftcode())
//					.ejinSite(GenericAuditContextHolder.getContext().getSite())
//					.ejinTermdate(Objects.nonNull(empjobinfoRequestBean.getTermdate()) ? LocalDate.parse(empjobinfoRequestBean.getTermdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinTicketno(empjobinfoRequestBean.getTicketno())
//					.ejinTraineeupto(Objects.nonNull(empjobinfoRequestBean.getTraineeupto()) ? LocalDate.parse(empjobinfoRequestBean.getTraineeupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.ejinUserid(GenericAuditContextHolder.getContext().getUserid())
//					.ejinWorksite(empjobinfoRequestBean.getWorksite())
//					.ejinXferdate(Objects.nonNull(empjobinfoRequestBean.getXferdate()) ? LocalDate.parse(empjobinfoRequestBean.getXferdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//		
//.build();
//}).collect(Collectors.toList());
//} ;
	public static BiFunction<Empjobinfo, EmpjobinfoRequestBean, Empjobinfo> updateEmpjobinfoEntityPojoMapper = (empjobinfoEntity, empjobinfoRequestBean) -> {
		empjobinfoEntity.getEmpjobinfoCK().setEjinEmpcode(Objects.nonNull(empjobinfoRequestBean.getEmpcode()) ? empjobinfoRequestBean.getEmpcode().trim() : empjobinfoEntity.getEmpjobinfoCK().getEjinEmpcode());
		empjobinfoEntity.getEmpjobinfoCK().setEjinEffectivefrom(Objects.nonNull(empjobinfoRequestBean.getEffectivefrom()) ? LocalDate.parse(empjobinfoRequestBean.getEffectivefrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEmpjobinfoCK().getEjinEffectivefrom());
		empjobinfoEntity.setEjinAppliedon(Objects.nonNull(empjobinfoRequestBean.getAppliedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppliedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinAppliedon());
		empjobinfoEntity.setEjinAppointedby(Objects.nonNull(empjobinfoRequestBean.getAppointedby()) ? empjobinfoRequestBean.getAppointedby().trim() : empjobinfoEntity.getEjinAppointedby());
		empjobinfoEntity.setEjinAppointedon(Objects.nonNull(empjobinfoRequestBean.getAppointedon()) ? LocalDate.parse(empjobinfoRequestBean.getAppointedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinAppointedon());
		empjobinfoEntity.setEjinBank(Objects.nonNull(empjobinfoRequestBean.getBank()) ? empjobinfoRequestBean.getBank().trim() : empjobinfoEntity.getEjinBank());
		empjobinfoEntity.setEjinBankacno(Objects.nonNull(empjobinfoRequestBean.getBankacno()) ? empjobinfoRequestBean.getBankacno().trim() : empjobinfoEntity.getEjinBankacno());
		empjobinfoEntity.setEjinBankactype(Objects.nonNull(empjobinfoRequestBean.getBankactype()) ? empjobinfoRequestBean.getBankactype().trim() : empjobinfoEntity.getEjinBankactype());
		empjobinfoEntity.setEjinBankbranch(Objects.nonNull(empjobinfoRequestBean.getBankbranch()) ? empjobinfoRequestBean.getBankbranch().trim() : empjobinfoEntity.getEjinBankbranch());
		empjobinfoEntity.setEjinBranch(Objects.nonNull(empjobinfoRequestBean.getBranch()) ? empjobinfoRequestBean.getBranch().trim() : empjobinfoEntity.getEjinBranch());
		empjobinfoEntity.setEjinCompany(Objects.nonNull(empjobinfoRequestBean.getCompany()) ? empjobinfoRequestBean.getCompany().trim() : empjobinfoEntity.getEjinCompany());
		empjobinfoEntity.setEjinCostcentre(Objects.nonNull(empjobinfoRequestBean.getCostcentre()) ? empjobinfoRequestBean.getCostcentre().trim() : empjobinfoEntity.getEjinCostcentre());
		empjobinfoEntity.setEjinDepartment(Objects.nonNull(empjobinfoRequestBean.getDepartment()) ? empjobinfoRequestBean.getDepartment().trim() : empjobinfoEntity.getEjinDepartment());
		empjobinfoEntity.setEjinDeputedincoy(Objects.nonNull(empjobinfoRequestBean.getDeputedincoy()) ? empjobinfoRequestBean.getDeputedincoy().trim() : empjobinfoEntity.getEjinDeputedincoy());
		empjobinfoEntity.setEjinDesigpost(Objects.nonNull(empjobinfoRequestBean.getDesigpost()) ? empjobinfoRequestBean.getDesigpost().trim() : empjobinfoEntity.getEjinDesigpost());
		empjobinfoEntity.setEjinDesigtax(Objects.nonNull(empjobinfoRequestBean.getDesigtax()) ? empjobinfoRequestBean.getDesigtax().trim() : empjobinfoEntity.getEjinDesigtax());
		empjobinfoEntity.setEjinDirectoryn(Objects.nonNull(empjobinfoRequestBean.getDirectoryn()) ? empjobinfoRequestBean.getDirectoryn().trim() : empjobinfoEntity.getEjinDirectoryn());
		empjobinfoEntity.setEjinDivision(Objects.nonNull(empjobinfoRequestBean.getDivision()) ? empjobinfoRequestBean.getDivision().trim() : empjobinfoEntity.getEjinDivision());
		empjobinfoEntity.setEjinDutyhours(Objects.nonNull(empjobinfoRequestBean.getDutyhours()) ? empjobinfoRequestBean.getDutyhours() : empjobinfoEntity.getEjinDutyhours());
		empjobinfoEntity.setEjinEffectiveupto(Objects.nonNull(empjobinfoRequestBean.getEffectiveupto()) ? LocalDate.parse(empjobinfoRequestBean.getEffectiveupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinEffectiveupto());
		empjobinfoEntity.setEjinEmptype(Objects.nonNull(empjobinfoRequestBean.getEmptype()) ? empjobinfoRequestBean.getEmptype().trim() : empjobinfoEntity.getEjinEmptype());
		empjobinfoEntity.setEjinGrade(Objects.nonNull(empjobinfoRequestBean.getGrade()) ? empjobinfoRequestBean.getGrade().trim() : empjobinfoEntity.getEjinGrade());
		empjobinfoEntity.setEjinGratuitydate(Objects.nonNull(empjobinfoRequestBean.getGratuitydate()) ? LocalDate.parse(empjobinfoRequestBean.getGratuitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinGratuitydate());
		empjobinfoEntity.setEjinGratuitypaymonth(Objects.nonNull(empjobinfoRequestBean.getGratuitypaymonth()) ? empjobinfoRequestBean.getGratuitypaymonth().trim() : empjobinfoEntity.getEjinGratuitypaymonth());
		empjobinfoEntity.setEjinIpaddress(Objects.nonNull(empjobinfoRequestBean.getIpaddress()) ? empjobinfoRequestBean.getIpaddress().trim() : empjobinfoEntity.getEjinIpaddress());
		empjobinfoEntity.setEjinJobfrom(Objects.nonNull(empjobinfoRequestBean.getJobfrom()) ? LocalDate.parse(empjobinfoRequestBean.getJobfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinJobfrom());
		empjobinfoEntity.setEjinJobstatus(Objects.nonNull(empjobinfoRequestBean.getJobstatus()) ? empjobinfoRequestBean.getJobstatus().trim() : empjobinfoEntity.getEjinJobstatus());
		empjobinfoEntity.setEjinJobtype(Objects.nonNull(empjobinfoRequestBean.getJobtype()) ? empjobinfoRequestBean.getJobtype().trim() : empjobinfoEntity.getEjinJobtype());
		empjobinfoEntity.setEjinJobupto(Objects.nonNull(empjobinfoRequestBean.getJobupto()) ? LocalDate.parse(empjobinfoRequestBean.getJobupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinJobupto());
		empjobinfoEntity.setEjinJoindate(Objects.nonNull(empjobinfoRequestBean.getJoindate()) ? LocalDate.parse(empjobinfoRequestBean.getJoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinJoindate());
		empjobinfoEntity.setEjinJoinedyn(Objects.nonNull(empjobinfoRequestBean.getJoinedyn()) ? empjobinfoRequestBean.getJoinedyn().trim() : empjobinfoEntity.getEjinJoinedyn());
		empjobinfoEntity.setEjinLevel(Objects.nonNull(empjobinfoRequestBean.getLevel()) ? empjobinfoRequestBean.getLevel().trim() : empjobinfoEntity.getEjinLevel());
		empjobinfoEntity.setEjinLocation(Objects.nonNull(empjobinfoRequestBean.getLocation()) ? empjobinfoRequestBean.getLocation().trim() : empjobinfoEntity.getEjinLocation());
		empjobinfoEntity.setEjinMachinename(Objects.nonNull(empjobinfoRequestBean.getMachinename()) ? empjobinfoRequestBean.getMachinename().trim() : empjobinfoEntity.getEjinMachinename());
		empjobinfoEntity.setEjinModifiedon(LocalDateTime.now()) ; 
		empjobinfoEntity.setEjinModule(Objects.nonNull(empjobinfoRequestBean.getModule()) ? empjobinfoRequestBean.getModule().trim() : empjobinfoEntity.getEjinModule());
		empjobinfoEntity.setEjinNoticedays(Objects.nonNull(empjobinfoRequestBean.getNoticedays()) ? empjobinfoRequestBean.getNoticedays() : empjobinfoEntity.getEjinNoticedays());
		empjobinfoEntity.setEjinOldbank(Objects.nonNull(empjobinfoRequestBean.getOldbank()) ? empjobinfoRequestBean.getOldbank().trim() : empjobinfoEntity.getEjinOldbank());
		empjobinfoEntity.setEjinOldbankacno(Objects.nonNull(empjobinfoRequestBean.getOldbankacno()) ? empjobinfoRequestBean.getOldbankacno().trim() : empjobinfoEntity.getEjinOldbankacno());
		empjobinfoEntity.setEjinOrigjoindate(Objects.nonNull(empjobinfoRequestBean.getOrigjoindate()) ? LocalDate.parse(empjobinfoRequestBean.getOrigjoindate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinOrigjoindate());
		empjobinfoEntity.setEjinPaymode(Objects.nonNull(empjobinfoRequestBean.getPaymode()) ? empjobinfoRequestBean.getPaymode().trim() : empjobinfoEntity.getEjinPaymode());
		empjobinfoEntity.setEjinPrevcoy(Objects.nonNull(empjobinfoRequestBean.getPrevcoy()) ? empjobinfoRequestBean.getPrevcoy().trim() : empjobinfoEntity.getEjinPrevcoy());
		empjobinfoEntity.setEjinProbationupto(Objects.nonNull(empjobinfoRequestBean.getProbationupto()) ? LocalDate.parse(empjobinfoRequestBean.getProbationupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinProbationupto());
		empjobinfoEntity.setEjinProbmonths(Objects.nonNull(empjobinfoRequestBean.getProbmonths()) ? empjobinfoRequestBean.getProbmonths() : empjobinfoEntity.getEjinProbmonths());
		empjobinfoEntity.setEjinPunchcardno(Objects.nonNull(empjobinfoRequestBean.getPunchcardno()) ? empjobinfoRequestBean.getPunchcardno().trim() : empjobinfoEntity.getEjinPunchcardno());
		empjobinfoEntity.setEjinReasonofleav(Objects.nonNull(empjobinfoRequestBean.getReasonofleav()) ? empjobinfoRequestBean.getReasonofleav().trim() : empjobinfoEntity.getEjinReasonofleav());
		empjobinfoEntity.setEjinRegion(Objects.nonNull(empjobinfoRequestBean.getRegion()) ? empjobinfoRequestBean.getRegion().trim() : empjobinfoEntity.getEjinRegion());
		empjobinfoEntity.setEjinRemark(Objects.nonNull(empjobinfoRequestBean.getRemark()) ? empjobinfoRequestBean.getRemark().trim() : empjobinfoEntity.getEjinRemark());
		empjobinfoEntity.setEjinReportingto(Objects.nonNull(empjobinfoRequestBean.getReportingto()) ? empjobinfoRequestBean.getReportingto().trim() : empjobinfoEntity.getEjinReportingto());
		empjobinfoEntity.setEjinResignationdate(Objects.nonNull(empjobinfoRequestBean.getResignationdate()) ? LocalDate.parse(empjobinfoRequestBean.getResignationdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinResignationdate());
		empjobinfoEntity.setEjinSalarygrpcode(Objects.nonNull(empjobinfoRequestBean.getSalarygrpcode()) ? empjobinfoRequestBean.getSalarygrpcode().trim() : empjobinfoEntity.getEjinSalarygrpcode());
		empjobinfoEntity.setEjinSection(Objects.nonNull(empjobinfoRequestBean.getSection()) ? empjobinfoRequestBean.getSection().trim() : empjobinfoEntity.getEjinSection());
		empjobinfoEntity.setEjinSettlementdate(Objects.nonNull(empjobinfoRequestBean.getSettlementdate()) ? LocalDate.parse(empjobinfoRequestBean.getSettlementdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinSettlementdate());
		empjobinfoEntity.setEjinShiftcode(Objects.nonNull(empjobinfoRequestBean.getShiftcode()) ? empjobinfoRequestBean.getShiftcode().trim() : empjobinfoEntity.getEjinShiftcode());
		empjobinfoEntity.setEjinSite(GenericAuditContextHolder.getContext().getSite()) ; 
		empjobinfoEntity.setEjinTermdate(Objects.nonNull(empjobinfoRequestBean.getTermdate()) ? LocalDate.parse(empjobinfoRequestBean.getTermdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinTermdate());
		empjobinfoEntity.setEjinTicketno(Objects.nonNull(empjobinfoRequestBean.getTicketno()) ? empjobinfoRequestBean.getTicketno().trim() : empjobinfoEntity.getEjinTicketno());
		empjobinfoEntity.setEjinTraineeupto(Objects.nonNull(empjobinfoRequestBean.getTraineeupto()) ? LocalDate.parse(empjobinfoRequestBean.getTraineeupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinTraineeupto());
		empjobinfoEntity.setEjinUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		empjobinfoEntity.setEjinWorksite(Objects.nonNull(empjobinfoRequestBean.getWorksite()) ? empjobinfoRequestBean.getWorksite().trim() : empjobinfoEntity.getEjinWorksite());
		empjobinfoEntity.setEjinXferdate(Objects.nonNull(empjobinfoRequestBean.getXferdate()) ? LocalDate.parse(empjobinfoRequestBean.getXferdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : empjobinfoEntity.getEjinXferdate());


		return empjobinfoEntity;
	};

}
