package kraheja.adminexp.billing.dataentry.debitNote.entity;

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
 * The persistent class for the ADBNOTED database table.
 **/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { @Index(name = "ADBNOTED1", columnList = "adbndDbnoteser Asc, adbndLineno Asc", unique = true) })

public class Adbnoted implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdbnotedCK adbnotedCK;

	@Column(name = "ADBND_AMOUNT")
	private Double adbndAmount;

	@Column(name = "ADBND_CGSTAMT")
	private Double adbndCgstamt;

	@Column(name = "ADBND_CGSTPERC")
	private Double adbndCgstperc;

	@Column(name = "ADBND_DISCOUNTAMT")
	private Double adbndDiscountamt;

	@Column(name = "ADBND_IGSTAMT")
	private Double adbndIgstamt;

	@Column(name = "ADBND_IGSTPERC")
	private Double adbndIgstperc;

	@Column(name = "ADBND_ORIGSITE")
	private String adbndOrigsite;

	@Column(name = "ADBND_QUANTITY")
	private Double adbndQuantity;

	@Column(name = "ADBND_RATE")
	private Double adbndRate;

	@Column(name = "ADBND_SACCODE")
	private String adbndSaccode;

	@Column(name = "ADBND_SACDESC")
	private String adbndSacdesc;

	@Column(name = "ADBND_SGSTAMT")
	private Double adbndSgstamt;

	@Column(name = "ADBND_SGSTPERC")
	private Double adbndSgstperc;

	@Column(name = "ADBND_SITE")
	private String adbndSite;

	@Column(name = "ADBND_TAXABLEAMT")
	private Double adbndTaxableamt;

	@Column(name = "ADBND_TODAY")
	private LocalDateTime adbndToday;

	@Column(name = "ADBND_UGSTAMT")
	private Double adbndUgstamt;

	@Column(name = "ADBND_UGSTPERC")
	private Double adbndUgstperc;

	@Column(name = "ADBND_USERID")
	private String adbndUserid;

}