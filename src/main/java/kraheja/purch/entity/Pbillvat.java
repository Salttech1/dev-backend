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
 * The persistent class for the PBILLVAT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PBILLVAT1", columnList = "pblvSer Asc, pblvLineno Asc", unique = true)
})

public class Pbillvat implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PbillvatCK pbillvatCK;

	@Column(name="PBLV_AMOUNT")
	private Double pblvAmount ;

	@Column(name="PBLV_CGSTAMT")
	private Double pblvCgstamt ;

	@Column(name="PBLV_CGSTPERC")
	private Double pblvCgstperc ;

	@Column(name="PBLV_DISCOUNTAMT")
	private Double pblvDiscountamt ;

	@Column(name="PBLV_FOTOAMT")
	private Double pblvFotoamt ;

	@Column(name="PBLV_HSNCODE")
	private String pblvHsncode ;

	@Column(name="PBLV_HSNDESC")
	private String pblvHsndesc ;

	@Column(name="PBLV_IGSTAMT")
	private Double pblvIgstamt ;

	@Column(name="PBLV_IGSTPERC")
	private Double pblvIgstperc ;

	@Column(name="PBLV_ITEMCODE")
	private String pblvItemcode ;

	@Column(name="PBLV_MATCODE")
	private String pblvMatcode ;

	@Column(name="PBLV_MATGROUP")
	private String pblvMatgroup ;

	@Column(name="PBLV_ORIGSITE")
	private String pblvOrigsite ;

	@Column(name="PBLV_QUANTITY")
	private Double pblvQuantity ;

	@Column(name="PBLV_RATE")
	private Double pblvRate ;

	@Column(name="PBLV_SGSTAMT")
	private Double pblvSgstamt ;

	@Column(name="PBLV_SGSTPERC")
	private Double pblvSgstperc ;

	@Column(name="PBLV_SITE")
	private String pblvSite ;

	@Column(name="PBLV_TAXABLEAMT")
	private Double pblvTaxableamt ;

	@Column(name="PBLV_TODAY")
	private LocalDateTime pblvToday ;

	@Column(name="PBLV_UGSTAMT")
	private Double pblvUgstamt ;

	@Column(name="PBLV_UGSTPERC")
	private Double pblvUgstperc ;

	@Column(name="PBLV_UOM")
	private String pblvUom ;

	@Column(name="PBLV_USERID")
	private String pblvUserid ;

	@Column(name="PBLV_VATAMOUNT")
	private Double pblvVatamount ;

	@Column(name="PBLV_VATPERCENT")
	private Double pblvVatpercent ;

}