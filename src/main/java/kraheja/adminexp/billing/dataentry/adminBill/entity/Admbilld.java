package kraheja.adminexp.billing.dataentry.adminBill.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
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
 * The persistent class for the ADMBILLD database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "ADBILLD", columnList = "adbldSer Asc, adbldLineno Asc", unique = true)
})

public class Admbilld implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdmbilldCK admbilldCK;

	@Column(name="ADBLD_AMOUNT")
	private Double adbldAmount ;

	@Column(name="ADBLD_CGSTAMT")
	private Double adbldCgstamt ;

	@Column(name="ADBLD_CGSTPERC")
	private Double adbldCgstperc ;

	@Column(name="ADBLD_DBAMT")
	private Double adbldDbamt ;

	@Column(name="ADBLD_DBQTY")
	private Double adbldDbqty ;

	@Column(name="ADBLD_DISCOUNTAMT")
	private Double adbldDiscountamt ;

	@Column(name="ADBLD_HSNSACCODE")
	private String adbldHsnsaccode ;

	@Column(name="ADBLD_IGSTAMT")
	private Double adbldIgstamt ;

	@Column(name="ADBLD_IGSTPERC")
	private Double adbldIgstperc ;

	@Column(name="ADBLD_ITEMDESC")
	private String adbldItemdesc ;

	@Column(name="ADBLD_ORIGSITE")
	private String adbldOrigsite ;

	@Column(name="ADBLD_QUANTITY")
	private Double adbldQuantity ;

	@Column(name="ADBLD_RATE")
	private Double adbldRate ;

	@Column(name="ADBLD_SGSTAMT")
	private Double adbldSgstamt ;

	@Column(name="ADBLD_SGSTPERC")
	private Double adbldSgstperc ;

	@Column(name="ADBLD_SITE")
	private String adbldSite ;

	@Column(name="ADBLD_TAXABLEAMT")
	private Double adbldTaxableamt ;

	@Column(name="ADBLD_TODAY")
	private LocalDateTime adbldToday ;

	@Column(name="ADBLD_UGSTAMT")
	private Double adbldUgstamt ;

	@Column(name="ADBLD_UGSTPERC")
	private Double adbldUgstperc ;

	@Column(name="ADBLD_UOM")
	private String adbldUom ;

	@Column(name="ADBLD_USERID")
	private String adbldUserid ;

}