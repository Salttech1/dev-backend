package kraheja.adminexp.billing.dataentry.debitNote.entity;

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
 * The persistent class for the ADBNOTEH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADBNOTEH1", columnList = "adbnhDbnoteser Asc", unique = true)
})

public class Adbnoteh implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdbnotehCK adbnotehCK;

	@Column(name="ADBNH_AMOUNT")
	private Double adbnhAmount ;

	@Column(name="ADBNH_BILLTYPE")
	private String adbnhBilltype ;

	@Column(name="ADBNH_BLDGCODE")
	private String adbnhBldgcode ;

	@Column(name="ADBNH_COY")
	private String adbnhCoy ;

	@Column(name="ADBNH_DATE")
	private LocalDate adbnhDate ;

	@Column(name="ADBNH_DESCRIPTION1")
	private String adbnhDescription1 ;

	@Column(name="ADBNH_FOTOAMT")
	private Double adbnhFotoamt ;

	@Column(name="ADBNH_INVBILLDT")
	private LocalDate adbnhInvbilldt ;

	@Column(name="ADBNH_INVBILLNO")
	private String adbnhInvbillno ;

	@Column(name="ADBNH_NARRATION")
	private String adbnhNarration ;

	@Column(name="ADBNH_PARTYCODE")
	private String adbnhPartycode ;

	@Column(name="ADBNH_PARTYTYPE")
	private String adbnhPartytype ;

	@Column(name="ADBNH_PASSEDBY")
	private String adbnhPassedby ;

	@Column(name="ADBNH_PREPBY")
	private String adbnhPrepby ;

	@Column(name="ADBNH_PROJECT")
	private String adbnhProject ;

	@Column(name="ADBNH_PROP")
	private String adbnhProp ;

	@Column(name="ADBNH_SITE")
	private String adbnhSite ;

	@Column(name="ADBNH_TDSAMOUNT")
	private Double adbnhTdsamount ;

	@Column(name="ADBNH_TDSPERC")
	private Integer adbnhTdsperc ;

	@Column(name="ADBNH_TODAY")
	private LocalDateTime adbnhToday ;

	@Column(name="ADBNH_USERID")
	private String adbnhUserid ;

}