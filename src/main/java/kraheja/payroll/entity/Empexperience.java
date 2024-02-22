// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empexperience Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

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
 * The persistent class for the EMPEXPERIENCE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPEXPERIENCE1", columnList = "eexpEmpcode Asc, eexpJobsrno Asc", unique = true)
})

public class Empexperience implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpexperienceCK empexperienceCK;

	@Column(name="EEXP_COMPANYNAME")
	private String eexpCompanyname ;

	@Column(name="EEXP_ENDGRADE")
	private String eexpEndgrade ;

	@Column(name="EEXP_ENDGROSSPERMTH")
	private Double eexpEndgrosspermth ;

	@Column(name="EEXP_ENDLEVEL")
	private String eexpEndlevel ;

	@Column(name="EEXP_ENDPACKAGE")
	private Double eexpEndpackage ;

	@Column(name="EEXP_ENDPOST")
	private String eexpEndpost ;

	@Column(name="EEXP_ENDREPORTINGTO")
	private String eexpEndreportingto ;

	@Column(name="EEXP_IPADDRESS")
	private String eexpIpaddress ;

	@Column(name="EEXP_JOBPROFILE")
	private String eexpJobprofile ;

	@Column(name="EEXP_MACHINENAME")
	private String eexpMachinename ;

	@Column(name="EEXP_MODIFIEDON")
	private LocalDateTime eexpModifiedon ;

	@Column(name="EEXP_MODULE")
	private String eexpModule ;

	@Column(name="EEXP_REASONFORLEAVING")
	private String eexpReasonforleaving ;

	@Column(name="EEXP_SITE")
	private String eexpSite ;

	@Column(name="EEXP_STARTGRADE")
	private String eexpStartgrade ;

	@Column(name="EEXP_STARTGROSSPERMTH")
	private Double eexpStartgrosspermth ;

	@Column(name="EEXP_STARTLEVEL")
	private String eexpStartlevel ;

	@Column(name="EEXP_STARTPACKAGE")
	private Double eexpStartpackage ;

	@Column(name="EEXP_STARTPOST")
	private String eexpStartpost ;

	@Column(name="EEXP_STARTREPORTINGTO")
	private String eexpStartreportingto ;

	@Column(name="EEXP_TOTALSERVICE")
	private Integer eexpTotalservice ;

	@Column(name="EEXP_USERID")
	private String eexpUserid ;

	@Column(name="EEXP_WORKEDFROM")
	private LocalDate eexpWorkedfrom ;

	@Column(name="EEXP_WORKEDUPTO")
	private LocalDate eexpWorkedupto ;

}