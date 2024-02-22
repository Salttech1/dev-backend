// Developed By  - 	kalpana.m
// Developed on - 17-02-24
// Mode  - Data Entry
// Purpose - Salaryplan Entry / Edit
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
 * The persistent class for the SALARYPLAN database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "SALARYPLAN1", columnList = "splanSalarytype Asc, splanCoy Asc, splanPaymonth Asc", unique = true)
})

public class Salaryplan implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SalaryplanCK salaryplanCK;

	@Column(name="SPLAN_INITDONEYN")
	private String splanInitdoneyn ;

	@Column(name="SPLAN_IPADDRESS")
	private String splanIpaddress ;

	@Column(name="SPLAN_MACHINENAME")
	private String splanMachinename ;

	@Column(name="SPLAN_MODIFIEDON")
	private LocalDateTime splanModifiedon ;

	@Column(name="SPLAN_MODULE")
	private String splanModule ;

	@Column(name="SPLAN_PAIDYN")
	private String splanPaidyn ;

	@Column(name="SPLAN_PAYSEPARATEYN")
	private String splanPayseparateyn ;

	@Column(name="SPLAN_SITE")
	private String splanSite ;

	@Column(name="SPLAN_USERID")
	private String splanUserid ;

}