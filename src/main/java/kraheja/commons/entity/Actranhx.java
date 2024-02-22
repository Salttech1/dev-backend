package kraheja.commons.entity;

import java.io.Serializable;
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
 * The persistent class for the ACTRANHX database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ACTHX1", columnList = "acthRevision Asc, acthToday Asc, acthTranser Asc", unique = true)
})

public class Actranhx implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ActranhxCK actranhxCK;
	
	@Column(name="ACTH_PROPRIETOR")
	private String acthProprietor ;
	
	@Column(name="ACTH_TRANDATE")
	private LocalDate acthTrandate;
	
	@Column(name="ACTH_TRANTYPE")
	private String acthTrantype;
	
	@Column(name="ACTH_VOUNUM")
	private String acthVounum;


	@Column(name="ACTH_BANKCODE")
	private String acthBankcode ;

	@Column(name="ACTH_CREPNO")
	private Double acthCrepno ;

	@Column(name="ACTH_LEDGCODE")
	private String acthLedgcode ;

	@Column(name="ACTH_NARRATIVE")
	private String acthNarrative ;

	@Column(name="ACTH_NREPNO")
	private Integer acthNrepno ;

	@Column(name="ACTH_PARTYCODE")
	private String acthPartycode ;

	@Column(name="ACTH_PARTYTYPE")
	private String acthPartytype ;

	@Column(name="ACTH_POSTEDYN")
	private String acthPostedyn ;

	@Column(name="ACTH_SITE")
	private String acthSite ;

	@Column(name="ACTH_TRANAMT")
	private Double acthTranamt ;

	@Column(name="ACTH_USERID")
	private String acthUserid ;

	@Column(name="ACTH_VOUDATE")
	private LocalDate acthVoudate ;

}