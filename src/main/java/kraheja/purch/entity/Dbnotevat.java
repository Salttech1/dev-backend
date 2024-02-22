package kraheja.purch.entity;


import java.io.Serializable;
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
 * The persistent class for the DBNOTEVAT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DENOTEVAT1", columnList = "dbnvSer Asc, dbnvLineno Asc", unique = true)
})

public class Dbnotevat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DbnotevatCK dbnotevatCK;

	@Column(name="DBNV_AMOUNT")
	private Double dbnvAmount ;

	@Column(name="DBNV_CGSTAMT")
	private Double dbnvCgstamt ;

	@Column(name="DBNV_CGSTPERC")
	private Double dbnvCgstperc ;

	@Column(name="DBNV_DISCOUNTAMT")
	private Double dbnvDiscountamt ;

	@Column(name="DBNV_FOTOAMT")
	private Double dbnvFotoamt ;

	@Column(name="DBNV_HSNCODE")
	private String dbnvHsncode ;

	@Column(name="DBNV_HSNDESC")
	private String dbnvHsndesc ;

	@Column(name="DBNV_IGSTAMT")
	private Double dbnvIgstamt ;

	@Column(name="DBNV_IGSTPERC")
	private Double dbnvIgstperc ;

	@Column(name="DBNV_ITEMCODE")
	private String dbnvItemcode ;

	@Column(name="DBNV_MATCODE")
	private String dbnvMatcode ;

	@Column(name="DBNV_MATGROUP")
	private String dbnvMatgroup ;

	@Column(name="DBNV_ORIGSITE")
	private String dbnvOrigsite ;

	@Column(name="DBNV_QUANTITY")
	private Double dbnvQuantity ;

	@Column(name="DBNV_RATE")
	private Double dbnvRate ;

	@Column(name="DBNV_SGSTAMT")
	private Double dbnvSgstamt ;

	@Column(name="DBNV_SGSTPERC")
	private Double dbnvSgstperc ;

	@Column(name="DBNV_SITE")
	private String dbnvSite ;

	@Column(name="DBNV_TAXABLEAMT")
	private Double dbnvTaxableamt ;

	@Column(name="DBNV_TODAY")
	private LocalDateTime dbnvToday ;

	@Column(name="DBNV_UGSTAMT")
	private Double dbnvUgstamt ;

	@Column(name="DBNV_UGSTPERC")
	private Double dbnvUgstperc ;

	@Column(name="DBNV_UOM")
	private String dbnvUom ;

	@Column(name="DBNV_USERID")
	private String dbnvUserid ;

	@Column(name="DBNV_VATAMOUNT")
	private Double dbnvVatamount ;

	@Column(name="DBNV_VATPERCENT")
	private Double dbnvVatpercent ;

}