// Developed By  - 	kalpana.m
// Developed on - 21-08-23
// Mode  - Data Entry
// Purpose - Emppayformula Entry / Edit
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
 * The persistent class for the EMPPAYFORMULA database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPPAYFORMULA", columnList = "eforCoy Asc, eforEarndedcode Asc, eforEffectivefrom Asc, eforEmptype Asc, eforJobtype Asc", unique = true)
})

public class Emppayformula implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmppayformulaCK emppayformulaCK;

	@Column(name="EFOR_CONDITION")
	private String eforCondition ;

	@Column(name="EFOR_EFFECTIVEUPTO")
	private LocalDate eforEffectiveupto ;

	@Column(name="EFOR_FORMULA")
	private String eforFormula ;

	@Column(name="EFOR_FORMULADESC")
	private String eforFormuladesc ;

	@Column(name="EFOR_MACHINENAME")
	private String eforMachinename ;

	@Column(name="EFOR_MODIFIEDON")
	private LocalDateTime eforModifiedon ;

	@Column(name="EFOR_MODULE")
	private String eforModule ;

	@Column(name="EFOR_ORDERBY")
	private String eforOrderby ;

	@Column(name="EFOR_SITE")
	private String eforSite ;

	@Column(name="EFOR_TABLE")
	private String eforTable ;

	@Column(name="EFOR_USERID")
	private String eforUserid ;

}