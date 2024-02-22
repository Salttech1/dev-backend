// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empassetinfo Entry / Edit
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
 * The persistent class for the EMPASSETINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPASSETINFO", columnList = "eassEmpcode Asc, eassAssetcode Asc", unique = true)
})

public class Empassetinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmpassetinfoCK empassetinfoCK;

	@Column(name="EASS_ASSETDESC")
	private String eassAssetdesc ;

	@Column(name="EASS_ASSETNAME")
	private String eassAssetname ;

	@Column(name="EASS_AUTHBY")
	private String eassAuthby ;

	@Column(name="EASS_IPADDRESS")
	private String eassIpaddress ;

	@Column(name="EASS_ISSUEDATE")
	private LocalDate eassIssuedate ;

	@Column(name="EASS_ISSUEDBY")
	private String eassIssuedby ;

	@Column(name="EASS_MACHINENAME")
	private String eassMachinename ;

	@Column(name="EASS_MODIFIEDON")
	private LocalDateTime eassModifiedon ;

	@Column(name="EASS_MODULE")
	private String eassModule ;

	@Column(name="EASS_RECEIVEDBY")
	private String eassReceivedby ;

	@Column(name="EASS_REMARK")
	private String eassRemark ;

	@Column(name="EASS_RETURNDATE")
	private LocalDate eassReturndate ;

	@Column(name="EASS_SITE")
	private String eassSite ;

	@Column(name="EASS_USERID")
	private String eassUserid ;

}