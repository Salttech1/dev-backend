package kraheja.purch.entity;
import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the MATCERTLNHDR database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "MATCERTLNHDR1", columnList = "mclhTendernum Asc, mclhLogicnotenum Asc", unique = true)
})

public class Matcertlnhdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MatcertlnhdrCK matcertlnhdrCK;

	@Column(name="MCLH_BRAND")
	private String mclhBrand ;

	@Column(name="MCLH_COMMDESC1")
	private String mclhCommdesc1 ;

	@Column(name="MCLH_COMMDESC2")
	private String mclhCommdesc2 ;

	@Column(name="MCLH_COMMDESC3")
	private String mclhCommdesc3 ;

	@Column(name="MCLH_COMMERCIALTERM")
	private String mclhCommercialterm ;

	@Column(name="MCLH_FINALBILLCLOSEYN")
	private String mclhFinalbillcloseyn ;

	@Column(name="MCLH_GROUPLNNUM")
	private String mclhGrouplnnum ;

	@Column(name="MCLH_IMPORTUNDER")
	private String mclhImportunder ;

	@Column(name="MCLH_IMPSPECIFICATION")
	private String mclhImpspecification ;

	@Column(name="MCLH_LOCATION")
	private String mclhLocation ;

	@Column(name="MCLH_LOGICDATE")
	private LocalDate mclhLogicdate ;

	@Column(name="MCLH_LOGICNOTEDESC")
	private String mclhLogicnotedesc ;

	@Column(name="MCLH_LOI_DT")
	private LocalDate mclhLoi_Dt ;

	@Column(name="MCLH_PACKAGENAME")
	private String mclhPackagename ;

	@Column(name="MCLH_PAYMENTREMARKS")
	private String mclhPaymentremarks ;

	@Column(name="MCLH_PROJCODE")
	private String mclhProjcode ;

	@Column(name="MCLH_RECOMMJUSTIFICATION")
	private String mclhRecommjustification ;

	@Column(name="MCLH_RESOURCEALLOCATION")
	private String mclhResourceallocation ;

	@Column(name="MCLH_REVISIONNO")
	private String mclhRevisionno ;

	@Column(name="MCLH_SITE")
	private String mclhSite ;

	@Column(name="MCLH_TECHDESC")
	private String mclhTechdesc ;

	@Column(name="MCLH_TENDERREMARKS")
	private String mclhTenderremarks ;

	@Column(name="MCLH_TODAY")
	private LocalDateTime mclhToday ;

	@Column(name="MCLH_USERID")
	private String mclhUserid ;

	@Column(name="MCLH_WARRENTYINFO")
	private String mclhWarrentyinfo ;

	@Column(name="MCLH_WORKSCOPE")
	private String mclhWorkscope ;

	@Column(name="MCLH_WO_PO_DT")
	private LocalDate mclhWo_Po_Dt ;

}