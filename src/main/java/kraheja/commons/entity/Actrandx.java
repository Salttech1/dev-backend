package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
 * The persistent class for the ACTRANDX database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ACTRDX1", columnList = "actdRevision Asc, actdToday Asc, actdTranser Asc, actdBunum Asc", unique = true)
})

public class Actrandx implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActrandxCK actrandxCK;

	@Column(name="ACTD_BLDGCODE")
	private String actdBldgcode ;

	@Column(name="ACTD_CFCODE")
	private String actdCfcode ;

	@Column(name="ACTD_CFGROUP")
	private String actdCfgroup ;

	@Column(name="ACTD_CONTRACT")
	private String actdContract ;

	@Column(name="ACTD_DOCDATE")
	private LocalDate actdDocdate ;

	@Column(name="ACTD_DOCNUM")
	private String actdDocnum ;

	@Column(name="ACTD_DOCTYPE")
	private String actdDoctype ;

	@Column(name="ACTD_FLAT")
	private String actdFlat ;

	@Column(name="ACTD_LEDGCODE")
	private String actdLedgcode ;

	@Column(name="ACTD_MATCODE")
	private String actdMatcode ;

	@Column(name="ACTD_MATGROUP")
	private String actdMatgroup ;

	@Column(name="ACTD_MINCODE")
	private String actdMincode ;

	@Column(name="ACTD_MINTYPE")
	private String actdMintype ;

	@Column(name="ACTD_NARRATIVE")
	private String actdNarrative ;

	@Column(name="ACTD_PARTYTYPE")
	private String actdPartytype ;

	@Column(name="ACTD_PERIOD")
	private String actdPeriod ;

	@Column(name="ACTD_PROJECT")
	private String actdProject ;

	@Column(name="ACTD_REFFROM")
	private LocalDateTime actdReffrom ;

	@Column(name="ACTD_REFUPTO")
	private LocalDateTime actdRefupto ;

	@Column(name="ACTD_SITE")
	private String actdSite ;

	@Column(name="ACTD_TRANAMT")
	private Double actdTranamt ;

	@Column(name="ACTD_TRANTYPE")
	private String actdTrantype ;

	@Column(name="ACTD_USERID")
	private String actdUserid ;

	@Column(name="ACTD_VOUDATE")
	private LocalDate actdVoudate ;

	@Column(name="ACTD_VOUNUM")
	private String actdVounum ;

	@Column(name="ACTD_WING")
	private String actdWing ;

	@Column(name="ACTD_WORKCODE")
	private String actdWorkcode ;

	@Column(name="ACTD_WORKGROUP")
	private String actdWorkgroup ;

	@Column(name="ACTD_XACMAJOR")
	private String actdXacmajor ;

	@Column(name="ACTD_XACMINOR")
	private String actdXacminor ;

	@Column(name="ACTD_XMINTYPE")
	private String actdXmintype ;

	@Column(name="ACTD_XPARTYCODE")
	private String actdXpartycode ;

	@Column(name="ACTD_XPARTYTYPE")
	private String actdXpartytype ;

	@Column(name="ACTD_XPROJECT")
	private String actdXproject ;

	@Column(name="ACTD_XREF_BUNUM")
	private Integer actdXrefBunum ;

	@Column(name="ACTD_XREF_TRANDATE")
	private LocalDate actdXrefTrandate ;

	@Column(name="ACTD_XREF_TRANSER")
	private String actdXrefTranser ;

}