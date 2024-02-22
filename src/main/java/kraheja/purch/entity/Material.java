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
 * The persistent class for the MATERIAL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MAT1", columnList = "matMatgroup Asc, matMatcode Asc, matItemcode Asc", unique = true)
})

public class Material implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MaterialCK materialCK;

	@Column(name="MAT_ACMAJOR")
	private String matAcmajor ;

	@Column(name="MAT_ACMATGROUP")
	private String matAcmatgroup ;

	@Column(name="MAT_ACMINOR")
	private String matAcminor ;

	@Column(name="MAT_BLOCKDATE")
	private LocalDate matBlockdate ;

	@Column(name="MAT_BRQCONV")
	private Double matBrqconv ;

	@Column(name="MAT_BRQTYTYPE")
	private String matBrqtytype ;

	@Column(name="MAT_CLOSEDATE")
	private LocalDate matClosedate ;

	@Column(name="MAT_CUMAMOUNT")
	private Double matCumamount ;

	@Column(name="MAT_CUMQUANTITY")
	private Double matCumquantity ;

	@Column(name="MAT_DSFTCDM")
	private String matDsftcdm ;

	@Column(name="MAT_DSFTCDW")
	private String matDsftcdw ;

	@Column(name="MAT_DWORKTYPE")
	private String matDworktype ;

	@Column(name="MAT_ENTRIES")
	private Double matEntries ;

	@Column(name="MAT_ESTGROUPCD")
	private String matEstgroupcd ;

	@Column(name="MAT_EXECCODE")
	private String matExeccode ;

	@Column(name="MAT_GROUPCODE")
	private String matGroupcode ;

	@Column(name="MAT_GSTPERCENT")
	private Double matGstpercent ;

	@Column(name="MAT_HSFTCDM")
	private String matHsftcdm ;

	@Column(name="MAT_HSFTCDW")
	private String matHsftcdw ;

	@Column(name="MAT_HSNCODE")
	private String matHsncode ;

	@Column(name="MAT_INDENTABLE")
	private String matIndentable ;

	@Column(name="MAT_LENGTH")
	private String matLength ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="MAT_LEVEL")
	private String matLevel ;

	@Column(name="MAT_MATNAME")
	private String matMatname ;

	@Column(name="MAT_MULITEM")
	private String matMulitem ;

	@Column(name="MAT_MULSMAT")
	private String matMulsmat ;

	@Column(name="MAT_NUMBER")
	private String matNumber ;

	@Column(name="MAT_OPENDATE")
	private LocalDate matOpendate ;

	@Column(name="MAT_ORIGSITE")
	private String matOrigsite ;

	@Column(name="MAT_PJMATCODE")
	private String matPjmatcode ;

	@Column(name="MAT_PJMATNAME")
	private String matPjmatname ;

	@Column(name="MAT_PJSKU")
	private String matPjsku ;

	@Column(name="MAT_PURCMAT")
	private String matPurcmat ;

	@Column(name="MAT_RATE")
	private Double matRate ;

	@Column(name="MAT_SITE")
	private String matSite ;

	@Column(name="MAT_SKU")
	private String matSku ;

	@Column(name="MAT_TODAY")
	private LocalDateTime matToday ;

	@Column(name="MAT_USERID")
	private String matUserid ;

	@Column(name="MAT_VATPERCENT")
	private Double matVatpercent ;

	@Column(name="MAT_WORKCODE")
	private String matWorkcode ;

}
