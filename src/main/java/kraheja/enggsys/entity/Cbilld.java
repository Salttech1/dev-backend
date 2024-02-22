package kraheja.enggsys.entity;
import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the CBILLD database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cbilld implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CbilldCK cbilldCK;

	@Column(name="CBLD_AMOUNT")
	private Double cbldAmount ;

	@Column(name="CBLD_CERTDATE")
	private LocalDate cbldCertdate ;

	@Column(name="CBLD_CERTNUM")
	private String cbldCertnum ;

	@Column(name="CBLD_CGSTAMT")
	private Double cbldCgstamt ;

	@Column(name="CBLD_CGSTPERC")
	private Double cbldCgstperc ;

	@Column(name="CBLD_DBAMT")
	private Double cbldDbamt ;

	@Column(name="CBLD_DBQTY")
	private Double cbldDbqty ;

	@Column(name="CBLD_DISCOUNTAMT")
	private Double cbldDiscountamt ;

	@Column(name="CBLD_IGSTAMT")
	private Double cbldIgstamt ;

	@Column(name="CBLD_IGSTPERC")
	private Double cbldIgstperc ;

	@Column(name="CBLD_LOTNO")
	private String cbldLotno ;

	@Column(name="CBLD_ORIGSITE")
	private String cbldOrigsite ;

	@Column(name="CBLD_QUANTITY")
	private Double cbldQuantity ;

	@Column(name="CBLD_RATE")
	private Double cbldRate ;

	@Column(name="CBLD_SACCODE")
	private String cbldSaccode ;

	@Column(name="CBLD_SACDESC")
	private String cbldSacdesc ;

	@Column(name="CBLD_SGSTAMT")
	private Double cbldSgstamt ;

	@Column(name="CBLD_SGSTPERC")
	private Double cbldSgstperc ;

	@Column(name="CBLD_SITE")
	private String cbldSite ;

	@Column(name="CBLD_TAXABLEAMT")
	private Double cbldTaxableamt ;

	@Column(name="CBLD_TODAY")
	private LocalDateTime cbldToday ;

	@Column(name="CBLD_UGSTAMT")
	private Double cbldUgstamt ;

	@Column(name="CBLD_UGSTPERC")
	private Double cbldUgstperc ;

	@Column(name="CBLD_UOM")
	private String cbldUom ;

	@Column(name="CBLD_USERID")
	private String cbldUserid ;

	@Column(name="CBLD_WORKORDERLINENO")
	private Integer cbldWorkorderlineno ;

	@Column(name="CBLD_WORKORDERNO")
	private String cbldWorkorderno ;

	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor

	public static class CbilldCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String cbldSer ; 

		private Integer cbldLineno ; 


	}

}