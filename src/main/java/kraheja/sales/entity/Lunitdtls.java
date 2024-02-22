// Developed By  - 	vikram.p
// Developed on - 16-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
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
 * The persistent class for the LUNITDTLS database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LUNITDTLS", columnList = "lessorPropcode Asc, lessorUnitid Asc, lessorUnitno Asc", unique = true)
})

public class Lunitdtls implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LunitdtlsCK lunitdtlsCK;

	@Column(name="LESSOR_AMENITY")
	private String lessorAmenity ;

	@Column(name="LESSOR_CONENDDATE")
	private LocalDate lessorConenddate ;

	@Column(name="LESSOR_CONSTARTDATE")
	private LocalDate lessorConstartdate ;

	@Column(name="LESSOR_CONTACTPERSON")
	private String lessorContactperson ;

	@Column(name="LESSOR_CONTTENUARE")
	private String lessorConttenuare ;

	@Column(name="LESSOR_DEPOSITEAMT")
	private Double lessorDepositeamt ;

	@Column(name="LESSOR_FLATDESC")
	private String lessorFlatdesc ;

	@Column(name="LESSOR_FURNITURE")
	private String lessorFurniture ;

	@Column(name="LESSOR_IPADDRESS")
	private String lessorIpaddress ;

	@Column(name="LESSOR_LOCKFROMDATE")
	private LocalDate lessorLockfromdate ;

	@Column(name="LESSOR_LOCKTODATE")
	private LocalDate lessorLocktodate ;

	@Column(name="LESSOR_MACHINENAME")
	private String lessorMachinename ;

	@Column(name="LESSOR_PARTYCODE")
	private String lessorPartycode ;

	@Column(name="LESSOR_PAYCYCLE")
	private String lessorPaycycle ;

	@Column(name="LESSOR_PAYSENDINGMODE")
	private String lessorPaysendingmode ;

	@Column(name="LESSOR_PAYSTARTDATE")
	private LocalDate lessorPaystartdate ;

	@Column(name="LESSOR_PAYSTATUS")
	private String lessorPaystatus ;

	@Column(name="LESSOR_PERSON_PAY")
	private String lessorPerson_Pay ;

	@Column(name="LESSOR_PERSON_REC")
	private String lessorPerson_Rec ;

	@Column(name="LESSOR_REMARKS")
	private String lessorRemarks ;

	@Column(name="LESSOR_SERVTAX")
	private Double lessorServtax ;

	@Column(name="LESSOR_SHARECERTSTATUS")
	private String lessorSharecertstatus ;

	@Column(name="LESSOR_SITE")
	private String lessorSite ;

	@Column(name="LESSOR_TDS")
	private Double lessorTds ;

	@Column(name="LESSOR_TODAY")
	private LocalDateTime lessorToday ;

	@Column(name="LESSOR_UNITAREA")
	private Double lessorUnitarea ;

	@Column(name="LESSOR_UNITFLATSTATUS")
	private String lessorUnitflatstatus ;

	@Column(name="LESSOR_UNITGROUP")
	private String lessorUnitgroup ;

	@Column(name="LESSOR_UNITSTATUS")
	private String lessorUnitstatus ;

	@Column(name="LESSOR_UNITTYPE")
	private String lessorUnittype ;

	@Column(name="LESSOR_USERID")
	private String lessorUserid ;

}