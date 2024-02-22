// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empleaveinfo Entry / Edit
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
 * The persistent class for the EMPLEAVEINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPLEAVEINFO", columnList = "elinEmpcode Asc, elinLeavecode Asc, elinAcyear Asc", unique = true)
})

public class Empleaveinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpleaveinfoCK empleaveinfoCK;

	@Column(name="ELIN_COMPOFFEARNED")
	private Double elinCompoffearned ;

	@Column(name="ELIN_DAYEXCESSADJ")
	private Double elinDayexcessadj ;

	@Column(name="ELIN_DAYSAVAILED")
	private Double elinDaysavailed ;

	@Column(name="ELIN_DAYSBF")
	private Double elinDaysbf ;

	@Column(name="ELIN_DAYSENCASHED")
	private Double elinDaysencashed ;

	@Column(name="ELIN_DAYSENTITLED")
	private Double elinDaysentitled ;

	@Column(name="ELIN_IPADDRESS")
	private String elinIpaddress ;

	@Column(name="ELIN_MACHINENAME")
	private String elinMachinename ;

	@Column(name="ELIN_MAXDAYSCF")
	private Double elinMaxdayscf ;

	@Column(name="ELIN_MAXDAYSENC")
	private Double elinMaxdaysenc ;

	@Column(name="ELIN_MODIFIEDON")
	private LocalDateTime elinModifiedon ;

	@Column(name="ELIN_MODULE")
	private String elinModule ;

	@Column(name="ELIN_REMARK")
	private String elinRemark ;

	@Column(name="ELIN_SITE")
	private String elinSite ;

	@Column(name="ELIN_USERID")
	private String elinUserid ;

}