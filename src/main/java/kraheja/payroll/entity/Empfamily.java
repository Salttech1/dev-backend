// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empfamily Entry / Edit
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
 * The persistent class for the EMPFAMILY database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPFAMILY1", columnList = "efamEmpcode Asc, efamSrno Asc", unique = true)
})

public class Empfamily implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpfamilyCK empfamilyCK;

	@Column(name="EFAM_BIRTHDATE")
	private LocalDate efamBirthdate ;

	@Column(name="EFAM_FULLNAME")
	private String efamFullname ;

	@Column(name="EFAM_IPADDRESS")
	private String efamIpaddress ;

	@Column(name="EFAM_MACHINENAME")
	private String efamMachinename ;

	@Column(name="EFAM_MODIFIEDON")
	private LocalDateTime efamModifiedon ;

	@Column(name="EFAM_MODULE")
	private String efamModule ;

	@Column(name="EFAM_OCCUPATION")
	private String efamOccupation ;

	@Column(name="EFAM_RELATION")
	private String efamRelation ;

	@Column(name="EFAM_SITE")
	private String efamSite ;

	@Column(name="EFAM_USERID")
	private String efamUserid ;

	@Column(name="EFAM_WEDDINGDATE")
	private LocalDate efamWeddingdate ;

}