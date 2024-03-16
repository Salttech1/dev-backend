// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empeducation Entry / Edit
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
 * The persistent class for the EMPEDUCATION database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPEDUCATION1", columnList = "eeduEmpcode Asc, eeduEducsrno Asc", unique = true)
})

public class Empeducation implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpeducationCK empeducationCK;

	@Column(name="EEDU_ATTENDEDFROMDATE")
	private LocalDate eeduAttendedfromdate ;

	@Column(name="EEDU_ATTENDEDTODATE")
	private LocalDate eeduAttendedtodate ;

	@Column(name="EEDU_CLASS")
	private String eeduClass ;

	@Column(name="EEDU_COURSENAME")
	private String eeduCoursename ;

	@Column(name="EEDU_DEGREE")
	private String eeduDegree ;

	@Column(name="EEDU_INSTITUTE")
	private String eeduInstitute ;

	@Column(name="EEDU_IPADDRESS")
	private String eeduIpaddress ;

	@Column(name="EEDU_MACHINENAME")
	private String eeduMachinename ;

	@Column(name="EEDU_MAINSUBJETCS")
	private String eeduMainsubjetcs ;

	@Column(name="EEDU_MODIFIEDON")
	private LocalDateTime eeduModifiedon ;

	@Column(name="EEDU_MODULE")
	private String eeduModule ;

	@Column(name="EEDU_PERCOFMARKS")
	private Double eeduPercofmarks ;

	@Column(name="EEDU_SITE")
	private String eeduSite ;

	@Column(name="EEDU_USERID")
	private String eeduUserid ;

}