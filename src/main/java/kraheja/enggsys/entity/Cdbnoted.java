package kraheja.enggsys.entity;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the CDBNOTED database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cdbnoted implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CdbnotedCK cdbnotedCK;

	@Column(name="CDBND_AMOUNT")
	private Double cdbndAmount ;

	@Column(name="CDBND_CGSTAMT")
	private Double cdbndCgstamt ;

	@Column(name="CDBND_CGSTPERC")
	private Double cdbndCgstperc ;

	@Column(name="CDBND_DISCOUNTAMT")
	private Double cdbndDiscountamt ;

	@Column(name="CDBND_FOTOAMT")
	private Double cdbndFotoamt ;

	@Column(name="CDBND_IGSTAMT")
	private Double cdbndIgstamt ;

	@Column(name="CDBND_IGSTPERC")
	private Double cdbndIgstperc ;

	@Column(name="CDBND_ORIGSITE")
	private String cdbndOrigsite ;

	@Column(name="CDBND_QUANTITY")
	private Double cdbndQuantity ;

	@Column(name="CDBND_RATE")
	private Double cdbndRate ;

	@Column(name="CDBND_SACCODE")
	private String cdbndSaccode ;

	@Column(name="CDBND_SACDESC")
	private String cdbndSacdesc ;

	@Column(name="CDBND_SGSTAMT")
	private Double cdbndSgstamt ;

	@Column(name="CDBND_SGSTPERC")
	private Double cdbndSgstperc ;

	@Column(name="CDBND_SITE")
	private String cdbndSite ;

	@Column(name="CDBND_TAXABLEAMT")
	private Double cdbndTaxableamt ;

	@Column(name="CDBND_TODAY")
	private LocalDateTime cdbndToday ;

	@Column(name="CDBND_UGSTAMT")
	private Double cdbndUgstamt ;

	@Column(name="CDBND_UGSTPERC")
	private Double cdbndUgstperc ;

	@Column(name="CDBND_UOM")
	private String cdbndUom ;

	@Column(name="CDBND_USERID")
	private String cdbndUserid ;

	@Column(name="CDBND_VATAMOUNT")
	private Double cdbndVatamount ;

	@Column(name="CDBND_VATPERCENT")
	private Double cdbndVatpercent ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static  class CdbnotedCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cdbndDbnoteser ; 
		 
		private Integer cdbndLineno ; 
	}

}