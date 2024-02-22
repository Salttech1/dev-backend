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
 * The persistent class for the LCAUTH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "LCAUTH2", columnList = "lcahBldgcode Asc, lcahMatgroup Asc, lcahPartycode Asc, lcahAuthnum Asc, lcahAuthtype Asc", unique = true)
})

public class Lcauth implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LcauthCK lcauthCK;

	@Column(name="LCAH_AUTHAMOUNT")
	private Double lcahAuthamount ;

	@Column(name="LCAH_AUTHDATE")
	private LocalDate lcahAuthdate ;

	@Column(name="LCAH_AUTHQUANTY")
	private Integer lcahAuthquanty ;

	@Column(name="LCAH_AUTHSTATUS")
	private String lcahAuthstatus ;

	@Column(name="LCAH_BANKCHARGES")
	private Double lcahBankcharges ;

	@Column(name="LCAH_BLDGAUTHNO")
	private Integer lcahBldgauthno ;

	@Column(name="LCAH_CATEGORY")
	private String lcahCategory ;

	@Column(name="LCAH_COY")
	private String lcahCoy ;

	@Column(name="LCAH_CURRENCY")
	private String lcahCurrency ;

	@Column(name="LCAH_DEBITINGAMT")
	private Double lcahDebitingamt ;

	@Column(name="LCAH_DEBITINGCURR")
	private String lcahDebitingcurr ;

	@Column(name="LCAH_DEBITINGCURRCONVRATE")
	private Double lcahDebitingcurrconvrate ;

	@Column(name="LCAH_DEBITINGPARTY")
	private String lcahDebitingparty ;

	@Column(name="LCAH_DEBITINGREASON")
	private String lcahDebitingreason ;

	@Column(name="LCAH_DESCRIPTION")
	private String lcahDescription ;

	@Column(name="LCAH_DOCUMENTDATE")
	private LocalDate lcahDocumentdate ;

	@Column(name="LCAH_DOCUMENTNO")
	private String lcahDocumentno ;

	@Column(name="LCAH_DUTYFREEDATE")
	private LocalDate lcahDutyfreedate ;

	@Column(name="LCAH_DUTYFREENO")
	private String lcahDutyfreeno ;

	@Column(name="LCAH_EPCGDATE")
	private LocalDate lcahEpcgdate ;

	@Column(name="LCAH_EPCGNO")
	private String lcahEpcgno ;

	@Column(name="LCAH_EPCG_DUTYFREE")
	private String lcahEpcg_Dutyfree ;

	@Column(name="LCAH_EXPIRYDATE")
	private LocalDate lcahExpirydate ;

	@Column(name="LCAH_FILENO")
	private String lcahFileno ;

	@Column(name="LCAH_INSPECTIONDATE")
	private LocalDate lcahInspectiondate ;

	@Column(name="LCAH_LASTSHIPMENTDATE")
	private LocalDate lcahLastshipmentdate ;

	@Column(name="LCAH_LCNO")
	private String lcahLcno ;

	@Column(name="LCAH_LCOPENDATE")
	private LocalDate lcahLcopendate ;

	@Column(name="LCAH_LICDATE")
	private LocalDate lcahLicdate ;

	@Column(name="LCAH_LICNUM")
	private String lcahLicnum ;

	@Column(name="LCAH_MASTERAUTHNO")
	private String lcahMasterauthno ;

	@Column(name="LCAH_MASTERLCYN")
	private String lcahMasterlcyn ;

	@Column(name="LCAH_MATBLDAUTH")
	private Integer lcahMatbldauth ;

	@Column(name="LCAH_MISBLDG")
	private String lcahMisbldg ;

	@Column(name="LCAH_MISPROJECT")
	private String lcahMisproject ;

	@Column(name="LCAH_NOOFDAYS")
	private Integer lcahNoofdays ;

	@Column(name="LCAH_OPRAUTHAMT")
	private Integer lcahOprauthamt ;

	@Column(name="LCAH_OPRAUTHQTY")
	private Integer lcahOprauthqty ;

	@Column(name="LCAH_ORIGCITY")
	private String lcahOrigcity ;

	@Column(name="LCAH_ORIGCOUNTRY")
	private String lcahOrigcountry ;

	@Column(name="LCAH_ORIGSITE")
	private String lcahOrigsite ;

	@Column(name="LCAH_PARTYAUTH")
	private Integer lcahPartyauth ;

	@Column(name="LCAH_PARTYTYPE")
	private String lcahPartytype ;

	@Column(name="LCAH_PAYAMOUNT")
	private Double lcahPayamount ;

	@Column(name="LCAH_PAYDATE")
	private LocalDate lcahPaydate ;

	@Column(name="LCAH_PAYMODE")
	private String lcahPaymode ;

	@Column(name="LCAH_PAYREF")
	private String lcahPayref ;

	@Column(name="LCAH_PREPAREDBY")
	private String lcahPreparedby ;

	@Column(name="LCAH_PRINTED")
	private Integer lcahPrinted ;

	@Column(name="LCAH_PRINTEDON")
	private LocalDate lcahPrintedon ;

	@Column(name="LCAH_PROJECT")
	private String lcahProject ;

	@Column(name="LCAH_PROP")
	private String lcahProp ;

	@Column(name="LCAH_PROPERTY")
	private String lcahProperty ;

	@Column(name="LCAH_PRVAUTHAMT")
	private Integer lcahPrvauthamt ;

	@Column(name="LCAH_PRVAUTHNO")
	private String lcahPrvauthno ;

	@Column(name="LCAH_PRVAUTHQTY")
	private Integer lcahPrvauthqty ;

	@Column(name="LCAH_PRVDATE")
	private LocalDate lcahPrvdate ;

	@Column(name="LCAH_PRVTYPE")
	private String lcahPrvtype ;

	@Column(name="LCAH_PURAUTHNUM")
	private String lcahPurauthnum ;

	@Column(name="LCAH_REMARKS")
	private String lcahRemarks ;

	@Column(name="LCAH_SHIPDOCRECDDATE")
	private LocalDate lcahShipdocrecddate ;

	@Column(name="LCAH_SITE")
	private String lcahSite ;

	@Column(name="LCAH_TODAY")
	private LocalDateTime lcahToday ;

	@Column(name="LCAH_TRANSER")
	private String lcahTranser ;

	@Column(name="LCAH_UOM")
	private String lcahUom ;

	@Column(name="LCAH_USERID")
	private String lcahUserid ;

}