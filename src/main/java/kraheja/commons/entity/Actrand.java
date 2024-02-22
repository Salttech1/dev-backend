package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the ACTRAND database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "ACTRD1", columnList = "actdCoy ASC, actdTranser ASC, actdBunum ASC", unique = true),
})
@NamedQuery(name="Actrand.findAll", query="SELECT a FROM Actrand a")
public class Actrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActrandCK actrandCK;

	@Column(name="ACTD_PROPRIETOR")
	private String actdProprietor;

	@Column(name="ACTD_TRANDATE")
	private LocalDateTime actdTrandate;

	@Column(name="ACTD_PARTYCODE")
	private String actdPartycode;

	@Column(name="ACTD_ACMAJOR")
	private String actdAcmajor;

	@Column(name="ACTD_ACMINOR")
	private String actdAcminor;

	@Column(name="ACTD_BLDGCODE")
	private String actdBldgcode;

	@Column(name="ACTD_CFCODE")
	private String actdCfcode;

	@Column(name="ACTD_CFGROUP")
	private String actdCfgroup;

	@Column(name="ACTD_CONTRABUNUM")
	private Integer actdContrabunum;

	@Column(name="ACTD_CONTRACT")
	private String actdContract;

	@Column(name="ACTD_DOCDATE")
	private LocalDate actdDocdate;

	@Column(name="ACTD_DOCNUM")
	private String actdDocnum;

	@Column(name="ACTD_DOCPARCODE")
	private String actdDocparcode;

	@Column(name="ACTD_DOCPARTYPE")
	private String actdDocpartype;

	@Column(name="ACTD_DOCTYPE")
	private String actdDoctype;

	@Column(name="ACTD_DOMAIN")
	private String actdDomain;

	@Column(name="ACTD_FLAT")
	private String actdFlat;

	@Column(name="ACTD_ITEMQTY")
	private Double actdItemqty;

	@Column(name="ACTD_LEDGCODE")
	private String actdLedgcode;

	@Column(name="ACTD_MATCODE")
	private String actdMatcode;

	@Column(name="ACTD_MATGROUP")
	private String actdMatgroup;

	@Column(name="ACTD_MINCODE")
	private String actdMincode;

	@Column(name="ACTD_MINTYPE")
	private String actdMintype;

	@Column(name="ACTD_NARRATIVE")
	private String actdNarrative;

	@Column(name="ACTD_OLDBUNUM")
	private Integer actdOldbunum;

	@Column(name="ACTD_PARTYTYPE")
	private String actdPartytype;

	@Column(name="ACTD_PAYMODE")
	private String actdPaymode;

	@Column(name="ACTD_PERIOD")
	private String actdPeriod;

	@Column(name="ACTD_PROJECT")
	private String actdProject;

	@Column(name="ACTD_PROPERTY")
	private String actdProperty;

	@Column(name="ACTD_REFFROM")
	private LocalDateTime actdReffrom;

	@Column(name="ACTD_REFUPTO")
	private LocalDateTime actdRefupto;

	@Column(name="ACTD_SITE")
	private String actdSite;

	@Column(name="ACTD_SKU")
	private String actdSku;

	@Column(name="ACTD_TODAY")
	private LocalDateTime actdToday;

	@Column(name="ACTD_TRANAMT")
	private Double actdTranamt;

	@Column(name="ACTD_TRANTYPE")
	private String actdTrantype;

	@Column(name="ACTD_USERID")
	private String actdUserid;

	@Column(name="ACTD_VOUDATE")
	private LocalDate actdVoudate;

	@Column(name="ACTD_VOUNUM")
	private String actdVounum;

	@Column(name="ACTD_WING")
	private String actdWing;

	@Column(name="ACTD_WORKCODE")
	private String actdWorkcode;

	@Column(name="ACTD_WORKGROUP")
	private String actdWorkgroup;

	@Column(name="ACTD_XACMAJOR")
	private String actdXacmajor;

	@Column(name="ACTD_XACMINOR")
	private String actdXacminor;

	@Column(name="ACTD_XMINTYPE")
	private String actdXmintype;

	@Column(name="ACTD_XPARTYCODE")
	private String actdXpartycode;

	@Column(name="ACTD_XPARTYTYPE")
	private String actdXpartytype;

	@Column(name="ACTD_XPROJECT")
	private String actdXproject;

	@Column(name="ACTD_XREF_BUNUM")
	private Integer actdXrefBunum;

	@Column(name="ACTD_XREF_TRANDATE")
	private LocalDate actdXrefTrandate;

	@Column(name="ACTD_XREF_TRANSER")
	private String actdXrefTranser;
}