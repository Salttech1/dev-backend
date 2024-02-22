package kraheja.purch.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
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
 * The persistent class for the DBNOTEH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DBNOTEH1", columnList = "dbnhDbnoteser Asc", unique = true)
})

public class Dbnoteh implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DbnotehCK dbnotehCK;

	@Column(name="DBNH_AMOUNT")
	private Double dbnhAmount ;

	@Column(name="DBNH_AUTHDATE")
	private LocalDate dbnhAuthdate ;

	@Column(name="DBNH_AUTHNO")
	private String dbnhAuthno ;

	@Column(name="DBNH_BILLTYPE")
	private String dbnhBilltype ;

	@Column(name="DBNH_BLDGCODE")
	private String dbnhBldgcode ;

	@Column(name="DBNH_COY")
	private String dbnhCoy ;

	@Column(name="DBNH_DATE")
	private LocalDate dbnhDate ;

	@Column(name="DBNH_DESCRIPTION1")
	private String dbnhDescription1 ;

	@Column(name="DBNH_DESCRIPTION2")
	private String dbnhDescription2 ;

	@Column(name="DBNH_DESCRIPTION3")
	private String dbnhDescription3 ;

	@Column(name="DBNH_DESCRIPTION4")
	private String dbnhDescription4 ;

	@Column(name="DBNH_DESCRIPTION5")
	private String dbnhDescription5 ;

	@Column(name="DBNH_MISBLDG")
	private String dbnhMisbldg ;

	@Column(name="DBNH_MISPROJECT")
	private String dbnhMisproject ;

	@Column(name="DBNH_NARRATION")
	private String dbnhNarration ;

	@Column(name="DBNH_NOOFPRINT")
	private Double dbnhNoofprint ;

	@Column(name="DBNH_OMSPURCYN")
	private String dbnhOmspurcyn ;

	@Column(name="DBNH_ORIGSITE")
	private String dbnhOrigsite ;

	@Column(name="DBNH_PARTYCODE")
	private String dbnhPartycode ;

	@Column(name="DBNH_PARTYTYPE")
	private String dbnhPartytype ;

	@Column(name="DBNH_PASSEDBY")
	private String dbnhPassedby ;

	@Column(name="DBNH_PREPBY")
	private String dbnhPrepby ;

	@Column(name="DBNH_PRINTDATE")
	private LocalDateTime dbnhPrintdate ;

	@Column(name="DBNH_PRINTS")
	private Double dbnhPrints ;

	@Column(name="DBNH_PRINTUSER")
	private String dbnhPrintuser ;

	@Column(name="DBNH_PROJECT")
	private String dbnhProject ;

	@Column(name="DBNH_PROP")
	private String dbnhProp ;

	@Column(name="DBNH_RECDBY")
	private String dbnhRecdby ;

	@Column(name="DBNH_SITE")
	private String dbnhSite ;

	@Column(name="DBNH_SUPPBILLDT")
	private LocalDate dbnhSuppbilldt ;

	@Column(name="DBNH_SUPPBILLNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String dbnhSuppbillno ;

	@Column(name="DBNH_TDSAMOUNT")
	private Double dbnhTdsamount ;

	@Column(name="DBNH_TDSPERC")
	private Double dbnhTdsperc ;

	@Column(name="DBNH_TODAY")
	private LocalDateTime dbnhToday ;

	@Column(name="DBNH_TRANSER")
	private String dbnhTranser ;

	@Column(name="DBNH_USERID")
	private String dbnhUserid ;

}