// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.entity;

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
 * The persistent class for the LPROP database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LPROP", columnList = "lessorPropcode Asc", unique = true)
})

public class Lprop implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LpropCK lpropCK;

	@Column(name="LESSOR_COY")
	private String lessorCoy ;

	@Column(name="LESSOR_DESCRIPTION")
	private String lessorDescription ;

	@Column(name="LESSOR_IPADDRESS")
	private String lessorIpaddress ;

	@Column(name="LESSOR_LOCATION")
	private String lessorLocation ;

	@Column(name="LESSOR_MACHINENAME")
	private String lessorMachinename ;

	@Column(name="LESSOR_PAYTYPE")
	private String lessorPaytype ;

	@Column(name="LESSOR_PROPNAME")
	private String lessorPropname ;

	@Column(name="LESSOR_PROPRITOR")
	private String lessorPropritor ;

	@Column(name="LESSOR_PROPTYPE")
	private String lessorProptype ;

	@Column(name="LESSOR_SITE")
	private String lessorSite ;

	@Column(name="LESSOR_TODAY")
	private LocalDateTime lessorToday ;

	@Column(name="LESSOR_USERID")
	private String lessorUserid ;

}
