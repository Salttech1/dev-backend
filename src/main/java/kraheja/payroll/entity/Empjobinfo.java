package kraheja.payroll.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the EMPJOBINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPJOBINFO1", columnList = "ejinEmpcode Asc, ejinEffectivefrom Asc", unique = true)
})

public class Empjobinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpjobinfoCK empjobinfoCK;

	@Column(name="EJIN_APPLIEDON")
	private LocalDate ejinAppliedon ;

	@Column(name="EJIN_APPOINTEDBY")
	private String ejinAppointedby ;

	@Column(name="EJIN_APPOINTEDON")
	private LocalDate ejinAppointedon ;

	@Column(name="EJIN_BANK")
	private String ejinBank ;

	@Column(name="EJIN_BANKACNO")
	private String ejinBankacno ;

	@Column(name="EJIN_BANKACTYPE")
	private String ejinBankactype ;

	@Column(name="EJIN_BANKBRANCH")
	private String ejinBankbranch ;

	@Column(name="EJIN_BRANCH")
	private String ejinBranch ;

	@Column(name="EJIN_COMPANY")
	private String ejinCompany ;

	@Column(name="EJIN_COSTCENTRE")
	private String ejinCostcentre ;

	@Column(name="EJIN_DEPARTMENT")
	private String ejinDepartment ;

	@Column(name="EJIN_DEPUTEDINCOY")
	private String ejinDeputedincoy ;

	@Column(name="EJIN_DESIGPOST")
	private String ejinDesigpost ;

	@Column(name="EJIN_DESIGTAX")
	private String ejinDesigtax ;

	@Column(name="EJIN_DIRECTORYN")
	private String ejinDirectoryn ;

	@Column(name="EJIN_DIVISION")
	private String ejinDivision ;

	@Column(name="EJIN_DUTYHOURS")
	private Integer ejinDutyhours ;

	@Column(name="EJIN_EFFECTIVEUPTO")
	private LocalDate ejinEffectiveupto ;

	@Column(name="EJIN_EMPTYPE")
	private String ejinEmptype ;

	@Column(name="EJIN_GRADE")
	private String ejinGrade ;

	@Column(name="EJIN_GRATUITYDATE")
	private LocalDate ejinGratuitydate ;

	@Column(name="EJIN_GRATUITYPAYMONTH")
	private String ejinGratuitypaymonth ;

	@Column(name="EJIN_IPADDRESS")
	private String ejinIpaddress ;

	@Column(name="EJIN_JOBFROM")
	private LocalDate ejinJobfrom ;

	@Column(name="EJIN_JOBSTATUS")
	private String ejinJobstatus ;

	@Column(name="EJIN_JOBTYPE")
	private String ejinJobtype ;

	@Column(name="EJIN_JOBUPTO")
	private LocalDate ejinJobupto ;

	@Column(name="EJIN_JOINDATE")
	private LocalDate ejinJoindate ;

	@Column(name="EJIN_JOINEDYN")
	private String ejinJoinedyn ;

	@Column(name="EJIN_LEVEL")
	private String ejinLevel ;

	@Column(name="EJIN_LOCATION")
	private String ejinLocation ;

	@Column(name="EJIN_MACHINENAME")
	private String ejinMachinename ;

	@Column(name="EJIN_MODIFIEDON")
	private LocalDateTime ejinModifiedon ;

	@Column(name="EJIN_MODULE")
	private String ejinModule ;

	@Column(name="EJIN_NOTICEDAYS")
	private Integer ejinNoticedays ;

	@Column(name="EJIN_OLDBANK")
	private String ejinOldbank ;

	@Column(name="EJIN_OLDBANKACNO")
	private String ejinOldbankacno ;

	@Column(name="EJIN_ORIGJOINDATE")
	private LocalDate ejinOrigjoindate ;

	@Column(name="EJIN_PAYMODE")
	private String ejinPaymode ;

	@Column(name="EJIN_PREVCOY")
	private String ejinPrevcoy ;

	@Column(name="EJIN_PROBATIONUPTO")
	private LocalDate ejinProbationupto ;

	@Column(name="EJIN_PROBMONTHS")
	private Integer ejinProbmonths ;

	@Column(name="EJIN_PUNCHCARDNO")
	private String ejinPunchcardno ;

	@Column(name="EJIN_REASONOFLEAV")
	private String ejinReasonofleav ;

	@Column(name="EJIN_REGION")
	private String ejinRegion ;

	@Column(name="EJIN_REMARK")
	private String ejinRemark ;

	@Column(name="EJIN_REPORTINGTO")
	private String ejinReportingto ;

	@Column(name="EJIN_SALARYGRPCODE")
	private String ejinSalarygrpcode ;

	@Column(name="EJIN_SECTION")
	private String ejinSection ;

	@Column(name="EJIN_SETTLEMENTDATE")
	private LocalDate ejinSettlementdate ;

	@Column(name="EJIN_SHIFTCODE")
	private String ejinShiftcode ;

	@Column(name="EJIN_SITE")
	private String ejinSite ;

	@Column(name="EJIN_TERMDATE")
	private LocalDate ejinTermdate ;

	@Column(name="EJIN_TICKETNO")
	private String ejinTicketno ;

	@Column(name="EJIN_TRAINEEUPTO")
	private LocalDate ejinTraineeupto ;

	@Column(name="EJIN_USERID")
	private String ejinUserid ;

	@Column(name="EJIN_WORKSITE")
	private String ejinWorksite ;

	@Column(name="EJIN_XFERDATE")
	private LocalDate ejinXferdate ;

}