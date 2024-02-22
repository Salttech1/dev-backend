package kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity; 

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
* The persistent class for the ADMADVANCE database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Admadvance",indexes = {
		@Index(name = "ADMADVANCE1", columnList = "advnSer Asc", unique = true)
})

public class Admadvance1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmadvanceCK1 admadvanceCK;

	@Column(name="ADVN_ACTRANSER")
	private String advnActranser ;

	@Column(name="ADVN_ADVANCEAMT")
	private Double advnAdvanceamt ;

	@Column(name="ADVN_BASICAMT")
	private Double advnBasicamt ;

	@Column(name="ADVN_BLDGCODE")
	private String advnBldgcode ;

	@Column(name="ADVN_COY")
	private String advnCoy ;

	@Column(name="ADVN_DATE")
	private LocalDate advnDate ;

	@Column(name="ADVN_FOTOAMOUNT")
	private Double advnFotoamount ;

	@Column(name="ADVN_GSTAMT")
	private Double advnGstamt ;

	@Column(name="ADVN_GSTPERC")
	private Double advnGstperc ;

	@Column(name="ADVN_NARRATION")
	private String advnNarration ;

	@Column(name="ADVN_ORDERBY")
	private String advnOrderby ;

	@Column(name="ADVN_ORIGSITE")
	private String advnOrigsite ;

	@Column(name="ADVN_PAIDAMOUNT")
	private Double advnPaidamount ;

	@Column(name="ADVN_PAIDDATE")
	private LocalDate advnPaiddate ;

	@Column(name="ADVN_PAIDREF")
	private String advnPaidref ;

	@Column(name="ADVN_PARTYCODE")
	private String advnPartycode ;

	@Column(name="ADVN_PARTYTYPE")
	private String advnPartytype ;

	@Column(name="ADVN_PASSEDON")
	private LocalDateTime advnPassedon ;

	@Column(name="ADVN_PINVDATE")
	private LocalDate advnPinvdate ;

	@Column(name="ADVN_PINVNO")
	private String advnPinvno ;

	@Column(name="ADVN_PROJECT")
	private String advnProject ;

	@Column(name="ADVN_SITE")
	private String advnSite ;

	@Column(name="ADVN_STATUS")
	private String advnStatus ;

	@Column(name="ADVN_TDSACMAJOR")
	private String advnTdsacmajor ;

	@Column(name="ADVN_TDSAMOUNT")
	private Double advnTdsamount ;

	@Column(name="ADVN_TDSPERC")
	private Double advnTdsperc ;

	@Column(name="ADVN_TODAY")
	private LocalDateTime advnToday ;

	@Column(name="ADVN_USERID")
	private String advnUserid ;

}