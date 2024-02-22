// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empreference Entry / Edit
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
 * The persistent class for the EMPREFERENCE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPREFERENCE1", columnList = "erefEmpcode Asc, erefSrno Asc", unique = true)
})

public class Empreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpreferenceCK empreferenceCK;

	@Column(name="EREF_COMPANYNAME")
	private String erefCompanyname ;

	@Column(name="EREF_IPADDRESS")
	private String erefIpaddress ;

	@Column(name="EREF_KNOWNFROM")
	private LocalDate erefKnownfrom ;

	@Column(name="EREF_MACHINENAME")
	private String erefMachinename ;

	@Column(name="EREF_MODIFIEDON")
	private LocalDateTime erefModifiedon ;

	@Column(name="EREF_MODULE")
	private String erefModule ;

	@Column(name="EREF_POST")
	private String erefPost ;

	@Column(name="EREF_REFERENCEADDRESS")
	private String erefReferenceaddress ;

	@Column(name="EREF_REFERENCECELLNO")
	private String erefReferencecellno ;

	@Column(name="EREF_REFERENCETELNO")
	private String erefReferencetelno ;

	@Column(name="EREF_REFRELATION")
	private String erefRefrelation ;

	@Column(name="EREF_REFRENCENAME")
	private String erefRefrencename ;

	@Column(name="EREF_SITE")
	private String erefSite ;

	@Column(name="EREF_USERID")
	private String erefUserid ;

}