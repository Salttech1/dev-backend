package kraheja.arch.projbldg.dataentry.entity;
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
 * The persistent class for the BUILDING database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "BLDG1", columnList = "bldgCode Asc", unique = true)
})

public class Building implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BuildingCK buildingCK;

	@Column(name="BLDG_ACCLEARYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgAcclearyn ;

	@Column(name="BLDG_ADMINRATE")
	private Double bldgAdminrate ;

	@Column(name="BLDG_ALTBLDG")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgAltbldg ;

	@Column(name="BLDG_AMENCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgAmencoy ;

	@Column(name="BLDG_AREAARCH")
	private Double bldgAreaarch ;

	@Column(name="BLDG_AREAENGG")
	private Double bldgAreaengg ;

	@Column(name="BLDG_AREASALES")
	private Double bldgAreasales ;

	@Column(name="BLDG_BHK")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgBhk ;

	@Column(name="BLDG_BLDGNUM")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgBldgnum ;

	@Column(name="BLDG_BLDGTYPE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgBldgtype ;

	@Column(name="BLDG_CCDATE")
	private LocalDate bldgCcdate ;

	@Column(name="BLDG_CITY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgCity ;

	@Column(name="BLDG_CLOSEDATE")
	private LocalDate bldgClosedate ;

	@Column(name="BLDG_COLLRECDWOBJ")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgCollrecdwobj ;

	@Column(name="BLDG_COMPLEX")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgComplex ;

	@Column(name="BLDG_CONCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgConcoy ;

	@Column(name="BLDG_COY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgCoy ;

	@Column(name="BLDG_COY4")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgCoy4 ;

	@Column(name="BLDG_DEBSOCYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgDebsocyn ;

	@Column(name="BLDG_EA")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgEa ;

	@Column(name="BLDG_ELECSUPP")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgElecsupp ;

	@Column(name="BLDG_ENG_GROUP")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgEng_Group ;

	@Column(name="BLDG_FLATSSOLD")
	private Double bldgFlatssold ;

	@Column(name="BLDG_GARAGECOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgGaragecoy ;

	@Column(name="BLDG_HO2SALES")
	private LocalDate bldgHo2sales ;

	@Column(name="BLDG_ICSALEYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgIcsaleyn ;

	@Column(name="BLDG_INFRAYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgInfrayn ;

	@Column(name="BLDG_INTBROKCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgIntbrokcoy ;

	@Column(name="BLDG_INTRATE")
	private Double bldgIntrate ;

	@Column(name="BLDG_LASTYRCOMP")
	private Double bldgLastyrcomp ;

	@Column(name="BLDG_LASTYRINC")
	private Double bldgLastyrinc ;

	@Column(name="BLDG_LASTYRMAT")
	private Double bldgLastyrmat ;

	@Column(name="BLDG_LASTYRWORK")
	private Double bldgLastyrwork ;

	@Column(name="BLDG_LEASINGCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgLeasingcoy ;

	@Column(name="BLDG_MAINBLDG")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgMainbldg ;

	@Column(name="BLDG_MAINTCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgMaintcoy ;

	@Column(name="BLDG_MAINTRATE")
	private Double bldgMaintrate ;

	@Column(name="BLDG_MISBLDG")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgMisbldg ;

	@Column(name="BLDG_MISPROJECT")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgMisproject ;

	@Column(name="BLDG_MODAGDOC")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgModagdoc ;

	@Column(name="BLDG_NAME")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgName ;

	@Column(name="BLDG_OCCDATE")
	private LocalDate bldgOccdate ;

	@Column(name="BLDG_OPENDATE")
	private LocalDate bldgOpendate ;

	@Column(name="BLDG_OPT2ENGG")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgOpt2engg ;

	@Column(name="BLDG_OPT2SALES")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgOpt2sales ;

	@Column(name="BLDG_ORIGSITE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgOrigsite ;

	@Column(name="BLDG_OUTFORMAT")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgOutformat ;

	@Column(name="BLDG_PARENTBLDG")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgParentbldg ;

	@Column(name="BLDG_PAYCOY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgPaycoy ;

	@Column(name="BLDG_POSSDATE")
	private LocalDate bldgPossdate ;

	@Column(name="BLDG_PROGSTAT")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgProgstat ;

	@Column(name="BLDG_PROJECT")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgProject ;

	@Column(name="BLDG_PROP")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgProp ;

	@Column(name="BLDG_PROPBUCAAREA")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgPropbucaarea ;

	@Column(name="BLDG_PROPERTY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgProperty ;

	@Column(name="BLDG_REGION")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgRegion ;

	@Column(name="BLDG_REMARK")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgRemark ;

	@Column(name="BLDG_SALESADLINE1")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesadline1 ;

	@Column(name="BLDG_SALESADLINE2")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesadline2 ;

	@Column(name="BLDG_SALESADLINE3")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesadline3 ;

	@Column(name="BLDG_SALESADLINE4")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesadline4 ;

	@Column(name="BLDG_SALESADLINE5")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesadline5 ;

	@Column(name="BLDG_SALESCITY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalescity ;

	@Column(name="BLDG_SALESCOUNTRY")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalescountry ;

	@Column(name="BLDG_SALESCTSNUM") //this is varchar field.
	private String bldgSalesctsnum ;

	@Column(name="BLDG_SALESIMAGES")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesimages ;

	@Column(name="BLDG_SALESNAME")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesname ;

	@Column(name="BLDG_SALESNAME1")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesname1 ;

	@Column(name="BLDG_SALESNAME2")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesname2 ;

	@Column(name="BLDG_SALESPINCODE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalespincode ;

	@Column(name="BLDG_SALESSTATE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalesstate ;

	@Column(name="BLDG_SALESTOWNSHIP")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSalestownship ;

	@Column(name="BLDG_SALEYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSaleyn ;

	@Column(name="BLDG_SCHPOSS")
	private LocalDate bldgSchposs ;

	@Column(name="BLDG_SHORTNAME")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgShortname ;

	@Column(name="BLDG_SITE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSite ;

	@Column(name="BLDG_STAXCOST")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgStaxcost ;

	@Column(name="BLDG_SURVEYNUM")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgSurveynum ;

	@Column(name="BLDG_TENURE")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgTenure ;

	@Column(name="BLDG_THISYRCOMP")
	private Double bldgThisyrcomp ;

	@Column(name="BLDG_THISYRINC")
	private Double bldgThisyrinc ;

	@Column(name="BLDG_THISYRMAT")
	private Double bldgThisyrmat ;

	@Column(name="BLDG_THISYRWORK")
	private Double bldgThisyrwork ;

	@Column(name="BLDG_TODATECOMP")
	private Double bldgTodatecomp ;

	@Column(name="BLDG_TODATEINC")
	private Double bldgTodateinc ;

	@Column(name="BLDG_TODATEMAT")
	private Double bldgTodatemat ;

	@Column(name="BLDG_TODATEWORK")
	private Double bldgTodatework ;

	@Column(name="BLDG_TODAY")
	private LocalDateTime bldgToday ;

	@Column(name="BLDG_TOTALFLATS")
	private Double bldgTotalflats ;

	@Column(name="BLDG_TOWNSHIP")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgTownship ;

	@Column(name="BLDG_ULCDATE")
	private LocalDate bldgUlcdate ;

	@Column(name="BLDG_USERID")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgUserid ;

	@Column(name="BLDG_VALIDYN")
	@Type(type = "kraheja.commons.utils.CharType")//NS 03.02.2023
	private String bldgValidyn ;

	@Column(name="BLDG_XFERED")
	private LocalDate bldgXfered ;

}