package kraheja.adminexp.billing.dataentry.adminBill.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import kraheja.purch.entity.Pbillh;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the ADMBILLH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADBLH1", columnList = "adblhSer Asc", unique = true)
})

public class Admbillh implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmbillhCK admbillhCK;

	@Column(name="ADBLH_ACMAJOR")
	private String adblhAcmajor ;

	@Column(name="ADBLH_ACMINOR")
	private String adblhAcminor ;

	@Column(name="ADBLH_ACTRANSER")
	private String adblhActranser ;

	@Column(name="ADBLH_ADVNADJUST")
	private Double adblhAdvnadjust ;

	@Column(name="ADBLH_BILLAMOUNT")
	private Double adblhBillamount ;

	@Column(name="ADBLH_BILLTYPE")
	private String adblhBilltype ;

	@Column(name="ADBLH_BLDGCODE")
	private String adblhBldgcode ;

	@Column(name="ADBLH_CLEARACDATE")
	private LocalDate adblhClearacdate ;

	@Column(name="ADBLH_CLEARACPERSON")
	private String adblhClearacperson ;

	@Column(name="ADBLH_COY")
	private String adblhCoy ;

	@Column(name="ADBLH_DEBITAMT")
	private Double adblhDebitamt ;

	@Column(name="ADBLH_EXPTYPE")
	private String adblhExptype ;

	@Column(name="ADBLH_FOTOAMOUNT")
	private Double adblhFotoamount ;

	@Column(name="ADBLH_FROMDATE")
	private LocalDate adblhFromdate ;

	@Column(name="ADBLH_GSTREVCHGYN")
	private String adblhGstrevchgyn ;

	@Column(name="ADBLH_MINTYPE")
	private String adblhMintype ;

	@Column(name="ADBLH_NARRATION")
	private String adblhNarration ;

	@Column(name="ADBLH_ORDEREDBY")
	private String adblhOrderedby ;

	@Column(name="ADBLH_ORIGSITE")
	private String adblhOrigsite ;

	@Column(name="ADBLH_ORIGSYS")
	private String adblhOrigsys ;

	@Column(name="ADBLH_PAIDAMOUNT")
	private Double adblhPaidamount ;

	@Column(name="ADBLH_PAIDDATE")
	private LocalDate adblhPaiddate ;

	@Column(name="ADBLH_PAIDREF")
	private String adblhPaidref ;

	@Column(name="ADBLH_PARTYCODE")
	private String adblhPartycode ;

	@Column(name="ADBLH_PARTYTYPE")
	private String adblhPartytype ;

	@Column(name="ADBLH_PASSEDON")
	private LocalDate adblhPassedon ;

	@Column(name="ADBLH_PROJECT")
	private String adblhProject ;

	@Column(name="ADBLH_RUNDATE")
	private LocalDate adblhRundate ;

	@Column(name="ADBLH_SITE")
	private String adblhSite ;

	@Column(name="ADBLH_STATUS")
	private String adblhStatus ;

	@Column(name="ADBLH_SUNCLASS")
	private String adblhSunclass ;

	@Column(name="ADBLH_SUNID")
	private String adblhSunid ;

	@Column(name="ADBLH_SUPPBILLDT")
	private LocalDate adblhSuppbilldt ;

	@Column(name="ADBLH_SUPPBILLNO")
	private String adblhSuppbillno ;

	@Column(name="ADBLH_TDSACMAJOR")
	private String adblhTdsacmajor ;

	@Column(name="ADBLH_TDSAMOUNT")
	private Double adblhTdsamount ;

	@Column(name="ADBLH_TDSPERC")
	private Double adblhTdsperc ;

	@Column(name="ADBLH_TODATE")
	private LocalDate adblhTodate ;

	@Column(name="ADBLH_TODAY")
	private LocalDateTime adblhToday ;

	@Column(name="ADBLH_USERID")
	private String adblhUserid ;

}