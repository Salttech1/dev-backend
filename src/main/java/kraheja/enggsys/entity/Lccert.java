package kraheja.enggsys.entity;


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
 * The persistent class for the LCCERT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LCCERT1", columnList = "lcerCertnum Asc", unique = true)
})

public class Lccert implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LccertCK lccertCK;

	@Column(name="LCER_BANKCHARGES")
	private Double lcerBankcharges ;

	@Column(name="LCER_BLDGCODE")
	private String lcerBldgcode ;

	@Column(name="LCER_CATEGORY")
	private String lcerCategory ;

	@Column(name="LCER_CERTAMOUNT")
	private Double lcerCertamount ;

	@Column(name="LCER_CERTDATE")
	private LocalDate lcerCertdate ;

	@Column(name="LCER_CERTREVNUM")
	private Integer lcerCertrevnum ;

	@Column(name="LCER_CERTSTATUS")
	private String lcerCertstatus ;

	@Column(name="LCER_CERTTYPE")
	private String lcerCerttype ;

	@Column(name="LCER_CONTRACT")
	private String lcerContract ;

	@Column(name="LCER_COY")
	private String lcerCoy ;

	@Column(name="LCER_CURRENCY")
	private String lcerCurrency ;

	@Column(name="LCER_DOCUMENTDATE")
	private LocalDate lcerDocumentdate ;

	@Column(name="LCER_DOCUMENTNO")
	private String lcerDocumentno ;

	@Column(name="LCER_DURFROM")
	private LocalDate lcerDurfrom ;

	@Column(name="LCER_DURTO")
	private LocalDate lcerDurto ;

	@Column(name="LCER_DUTYFREEDATE")
	private LocalDate lcerDutyfreedate ;

	@Column(name="LCER_DUTYFREENO")
	private String lcerDutyfreeno ;

	@Column(name="LCER_ENGGCERTNO")
	private String lcerEnggcertno ;

	@Column(name="LCER_EPCGDATE")
	private LocalDate lcerEpcgdate ;

	@Column(name="LCER_EPCGNO")
	private String lcerEpcgno ;

	@Column(name="LCER_EPCG_DUTYFREE")
	private String lcerEpcg_Dutyfree ;

	@Column(name="LCER_EXPIRYDATE")
	private LocalDate lcerExpirydate ;

	@Column(name="LCER_FILENO")
	private String lcerFileno ;

	@Column(name="LCER_INSPECTIONDATE")
	private LocalDate lcerInspectiondate ;

	@Column(name="LCER_LASTSHIPMENTDATE")
	private LocalDate lcerLastshipmentdate ;

	@Column(name="LCER_LCNO")
	private String lcerLcno ;

	@Column(name="LCER_LCOPENDATE")
	private LocalDate lcerLcopendate ;

	@Column(name="LCER_LICDATE")
	private LocalDate lcerLicdate ;

	@Column(name="LCER_LICNUM")
	private String lcerLicnum ;

	@Column(name="LCER_MASTERCERTNO")
	private String lcerMastercertno ;

	@Column(name="LCER_MASTERLCYN")
	private String lcerMasterlcyn ;

	@Column(name="LCER_NOOFDAYS")
	private Integer lcerNoofdays ;

	@Column(name="LCER_ORIGINATOR")
	private String lcerOriginator ;

	@Column(name="LCER_ORIGSITE")
	private String lcerOrigsite ;

	@Column(name="LCER_PARTYCODE")
	private String lcerPartycode ;

	@Column(name="LCER_PARTYTYPE")
	private String lcerPartytype ;

	@Column(name="LCER_PASSEDON")
	private LocalDate lcerPassedon ;

	@Column(name="LCER_PAYAMOUNT")
	private Double lcerPayamount ;

	@Column(name="LCER_PAYCOY")
	private String lcerPaycoy ;

	@Column(name="LCER_PAYDATE")
	private LocalDate lcerPaydate ;

	@Column(name="LCER_PAYMODE")
	private String lcerPaymode ;

	@Column(name="LCER_PAYREF")
	private String lcerPayref ;

	@Column(name="LCER_PRINTED")
	private Integer lcerPrinted ;

	@Column(name="LCER_PRINTEDON")
	private LocalDate lcerPrintedon ;

	@Column(name="LCER_PROJECT")
	private String lcerProject ;

	@Column(name="LCER_PROP")
	private String lcerProp ;

	@Column(name="LCER_PROPERTY")
	private String lcerProperty ;

	@Column(name="LCER_PRV_CERTAMT")
	private Double lcerPrv_Certamt ;

	@Column(name="LCER_PRV_CERTDATE")
	private LocalDate lcerPrv_Certdate ;

	@Column(name="LCER_PRV_CERTNUM")
	private String lcerPrv_Certnum ;

	@Column(name="LCER_PRV_CERTRUNSER")
	private Integer lcerPrv_Certrunser ;

	@Column(name="LCER_PRV_CERTTYPE")
	private String lcerPrv_Certtype ;

	@Column(name="LCER_PRV_DURFROM")
	private LocalDate lcerPrv_Durfrom ;

	@Column(name="LCER_PRV_DURTO")
	private LocalDate lcerPrv_Durto ;

	@Column(name="LCER_PURPOSE")
	private String lcerPurpose ;

	@Column(name="LCER_QTY")
	private Double lcerQty ;

	@Column(name="LCER_REMARKS")
	private String lcerRemarks ;

	@Column(name="LCER_RUNSER")
	private Integer lcerRunser ;

	@Column(name="LCER_SHIPDOCRECDDATE")
	private LocalDate lcerShipdocrecddate ;

	@Column(name="LCER_SITE")
	private String lcerSite ;

	@Column(name="LCER_TODAY")
	private LocalDateTime lcerToday ;

	@Column(name="LCER_TOT_PAYMENT")
	private Double lcerTot_Payment ;

	@Column(name="LCER_TOT_TWOPTC")
	private Double lcerTot_Twoptc ;

	@Column(name="LCER_TRANSER")
	private String lcerTranser ;

	@Column(name="LCER_UOM")
	private String lcerUom ;

	@Column(name="LCER_USERID")
	private String lcerUserid ;

	@Column(name="LCER_WORKCODE")
	private String lcerWorkcode ;

}