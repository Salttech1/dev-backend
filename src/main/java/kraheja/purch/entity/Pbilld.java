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
 * The persistent class for the PBILLD database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PBILLD1", columnList = "pbldSer Asc, pbldLineno Asc", unique = true), 
})

public class Pbilld implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PbilldCK pbilldCK;

	@Column(name="PBLD_AMOUNT")
	private Double pbldAmount ;

	@Column(name="PBLD_AUTHDATE")
	private LocalDate pbldAuthdate ;

	@Column(name="PBLD_AUTHNUM")
	private String pbldAuthnum ;

	@Column(name="PBLD_BLDGCODE")
	private String pbldBldgcode ;

	@Column(name="PBLD_CESER")
	private String pbldCeser ;

	@Column(name="PBLD_DBQTY")
	private Double pbldDbqty ;

	@Column(name="PBLD_DEQUANTITY")
	private Double pbldDequantity ;

	@Column(name="PBLD_DERATE")
	private Double pbldDerate ;

	@Column(name="PBLD_DEUOM")
	private String pbldDeuom ;

	@Column(name="PBLD_GRNLN")
	private Double pbldGrnln ;

	@Column(name="PBLD_GRNNO")
	private String pbldGrnno ;

	@Column(name="PBLD_INDLN")
	private Double pbldIndln ;

	@Column(name="PBLD_INDNO")
	private String pbldIndno ;

	@Column(name="PBLD_ITEMCODE")
	private String pbldItemcode ;

	@Column(name="PBLD_LOTNO")
	private String pbldLotno ;

	@Column(name="PBLD_MATCODE")
	private String pbldMatcode ;

	@Column(name="PBLD_MATGROUP")
	private String pbldMatgroup ;
	
	@Column(name="PBLD_ORIGSITE")
	private String pbldOrigsite ;

	@Column(name="PBLD_POLN")
	private Double pbldPoln ;

	@Column(name="PBLD_PONO")
	private String pbldPono ;

	@Column(name="PBLD_QUANTITY")
	private Double pbldQuantity ;

	@Column(name="PBLD_RATE")
	private Double pbldRate ;

	@Column(name="PBLD_SITE")
	private String pbldSite ;

	@Column(name="PBLD_SUPPBILLDT")
	private LocalDate pbldSuppbilldt ;
	
	@Column(name="PBLD_PARTYCODE")
	private String pbldPartycode;

	@Column(name="PBLD_SUPPBILLNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String pbldSuppbillno ;

	@Column(name="PBLD_TODAY")
	private LocalDateTime pbldToday;

	@Column(name="PBLD_UOM")
	private String pbldUom ;

	@Column(name="PBLD_USERID")
	private String pbldUserid ;

}