package kraheja.purch.entity;

import java.io.Serializable;
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
 * The persistent class for the DBNOTED database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DBNOTED1", columnList = "dbndDbnoteser Asc, dbndLineno Asc", unique = true), 
})

public class Dbnoted implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DbnotedCK dbnotedCK;

	@Column(name="DBND_AMOUNT")
	private Double dbndAmount ;

	@Column(name="DBND_DEQUANTITY")
	private Double dbndDequantity ;

	@Column(name="DBND_DERATE")
	private Double dbndDerate ;

	@Column(name="DBND_DEUOM")
	private String dbndDeuom ;

	@Column(name="DBND_ITEMCODE")
	private String dbndItemcode ;

	@Column(name="DBND_LENGTH")
	private String dbndLength ;

	@Column(name="DBND_MATCODE")
	private String dbndMatcode ;

	
	@Column(name="DBND_MATGROUP")
	private String dbndMatGroup ;

	@Column(name="DBND_NUMBER")
	private Double dbndNumber ;

	@Column(name="DBND_ORIGSITE")
	private String dbndOrigsite ;

	@Column(name="DBND_QUANTITY")
	private Double dbndQuantity ;
	
	@Column(name="DBND_PARTYCODE")
	private String dbndPartyCode;

	@Column(name="DBND_RATE")
	private Double dbndRate ;
	
	@Column(name="DBND_SUPPBILLNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String dbndSuppBillNo;

	@Column(name="DBND_SITE")
	private String dbndSite ;

	@Column(name="DBND_TODAY")
	private LocalDateTime dbndToday ;

	@Column(name="DBND_UOM")
	private String dbndUom ;

	@Column(name="DBND_USERID")
	private String dbndUserid ;

}