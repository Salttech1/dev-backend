// Developed By  - 	vikram.p
// Developed on - 03-01-24
// Mode  - Data Entry
// Purpose - Lrentmst Entry / Edit
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
 * The persistent class for the LRENTMST database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LRENTMST", columnList = "rentmUnitid Asc, rentmUnitgroup Asc, rentmType Asc, rentmTo Asc, rentmPropcode Asc, rentmUnitno Asc", unique = true)
})

public class Lrentmst implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LrentmstCK lrentmstCK;

	@Column(name="RENTM_AMT")
	private Double rentmAmt ;

	@Column(name="RENTM_CERTSTATUS")
	private String rentmCertstatus ;

	@Column(name="RENTM_FROM")
	private LocalDate rentmFrom ;

	@Column(name="RENTM_INCPER")
	private Double rentmIncper ;

	@Column(name="RENTM_IPADDRESS")
	private String rentmIpaddress ;

	@Column(name="RENTM_PARTYCODE")
	private String rentmPartycode ;

	@Column(name="RENTM_PAYCYCLE")
	private String rentmPaycycle ;

	@Column(name="RENTM_REMARKS")
	private String rentmRemarks ;

	@Column(name="RENTM_SERVTAXFLAG")
	private String rentmServtaxflag ;

	@Column(name="RENTM_SERVTAXPER")
	private Double rentmServtaxper ;

	@Column(name="RENTM_SITE")
	private String rentmSite ;

	@Column(name="RENTM_STATUS")
	private String rentmStatus ;

	@Column(name="RENTM_TODAY")
	private LocalDateTime rentmToday ;

	@Column(name="RENTM_USERID")
	private String rentmUserid ;

}