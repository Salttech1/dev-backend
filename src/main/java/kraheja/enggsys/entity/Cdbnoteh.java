package kraheja.enggsys.entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the CDBNOTEH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "CDBNOTEH1", columnList = "cdbnhDbnoteser Asc", unique = true)
})

public class Cdbnoteh implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CdbnotehCK cdbnotehCK;

	@Column(name="CDBNH_AMOUNT")
	private Double cdbnhAmount ;

	@Column(name="CDBNH_BILLTYPE")
	private String cdbnhBilltype ;

	@Column(name="CDBNH_BLDGCODE")
	private String cdbnhBldgcode ;

	@Column(name="CDBNH_CERTDATE")
	private LocalDate cdbnhCertdate ;

	@Column(name="CDBNH_CERTNO")
	private String cdbnhCertno ;

	@Column(name="CDBNH_CONTBILLDT")
	private LocalDate cdbnhContbilldt ;

	@Column(name="CDBNH_CONTBILLNO")
	private String cdbnhContbillno ;

	@Column(name="CDBNH_CONTRACT")
	private String cdbnhContract ;

	@Column(name="CDBNH_COY")
	private String cdbnhCoy ;

	@Column(name="CDBNH_DATE")
	private LocalDate cdbnhDate ;

	@Column(name="CDBNH_DESCRIPTION1")
	private String cdbnhDescription1 ;

	@Column(name="CDBNH_DESCRIPTION2")
	private String cdbnhDescription2 ;

	@Column(name="CDBNH_DESCRIPTION3")
	private String cdbnhDescription3 ;

	@Column(name="CDBNH_DESCRIPTION4")
	private String cdbnhDescription4 ;

	@Column(name="CDBNH_DESCRIPTION5")
	private String cdbnhDescription5 ;

	@Column(name="CDBNH_MISBLDG")
	private String cdbnhMisbldg ;

	@Column(name="CDBNH_MISPROJECT")
	private String cdbnhMisproject ;

	@Column(name="CDBNH_NARRATION")
	private String cdbnhNarration ;

	@Column(name="CDBNH_NOOFPRINT")
	private Integer cdbnhNoofprint ;

	@Column(name="CDBNH_OMSSERVYN")
	private String cdbnhOmsservyn ;

	@Column(name="CDBNH_ORIGSITE")
	private String cdbnhOrigsite ;

	@Column(name="CDBNH_PARTYCODE")
	private String cdbnhPartycode ;

	@Column(name="CDBNH_PARTYTYPE")
	private String cdbnhPartytype ;

	@Column(name="CDBNH_PASSEDBY")
	private String cdbnhPassedby ;

	@Column(name="CDBNH_PREPBY")
	private String cdbnhPrepby ;

	@Column(name="CDBNH_PRINTDATE")
	private LocalDate cdbnhPrintdate ;

	@Column(name="CDBNH_PRINTS")
	private Integer cdbnhPrints ;

	@Column(name="CDBNH_PRINTUSER")
	private String cdbnhPrintuser ;

	@Column(name="CDBNH_PROJECT")
	private String cdbnhProject ;

	@Column(name="CDBNH_PROP")
	private String cdbnhProp ;

	@Column(name="CDBNH_RECDBY")
	private String cdbnhRecdby ;

	@Column(name="CDBNH_SITE")
	private String cdbnhSite ;

	@Column(name="CDBNH_TDSAMOUNT")
	private Integer cdbnhTdsamount ;

	@Column(name="CDBNH_TDSPERC")
	private Integer cdbnhTdsperc ;

	@Column(name="CDBNH_TODAY")
	private LocalDateTime cdbnhToday ;

	@Column(name="CDBNH_USERID")
	private String cdbnhUserid ;

	@Column(name="CDBNH_WORKCODE")
	private String cdbnhWorkcode ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static  class CdbnotehCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cdbnhDbnoteser ; 
	}

}