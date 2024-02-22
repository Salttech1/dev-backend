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
 * The persistent class for the ACTRANH database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "ACTH1", columnList = "acthTranser ASC, acthCoy ASC", unique = true),
})
@NamedQuery(name="Actranh.findAll", query="SELECT a FROM Actranh a")
public class Actranh implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ActranhCK actranhCK;
	
	@Column(name="ACTH_PROPRIETOR")
	private String acthProprietor;
	
	@Column(name="ACTH_TRANTYPE")
	private String acthTrantype;
	
	@Column(name="ACTH_VOUNUM")
	private String acthVounum;
	
	@Column(name="ACTH_TRANDATE")
	private LocalDateTime acthTrandate;
	
	@Column(name="ACTH_COYTRANSER")
	private String acthCoytranser;

	@Column(name="ACTH_BALANCEDYN")
	private String acthBalancedyn;

	@Column(name="ACTH_BANKCODE")
	private String acthBankcode;

	@Column(name="ACTH_BBOOKYN")
	private String acthBbookyn;

	@Column(name="ACTH_CBOOKYN")
	private String acthCbookyn;

	@Column(name="ACTH_CLEARACYN")
	private String acthClearacyn;

	@Column(name="ACTH_CLOSINGJVYN")
	private String acthClosingjvyn;

	@Column(name="ACTH_CREPNO")
	private Double acthCrepno;

	@Column(name="ACTH_LEDGCODE")
	private String acthLedgcode;

	@Column(name="ACTH_NARRATIVE")
	private String acthNarrative;

	@Column(name="ACTH_PARTYCODE")
	private String acthPartycode;

	@Column(name="ACTH_PARTYTYPE")
	private String acthPartytype;

	@Column(name="ACTH_POSTEDYN")
	private String acthPostedyn;

	@Column(name="ACTH_PROVYN")
	private String acthProvyn;

	@Column(name="ACTH_REVERSEYN")
	private String acthReverseyn;

	@Column(name="ACTH_SITE")
	private String acthSite;

	@Column(name="ACTH_TODAY")
	private LocalDateTime acthToday;

	@Column(name="ACTH_TRANAMT")
	private Double acthTranamt;


	@Column(name="ACTH_USERID")
	private String acthUserid;

	@Column(name="ACTH_VOUDATE")
	private LocalDate acthVoudate;

}