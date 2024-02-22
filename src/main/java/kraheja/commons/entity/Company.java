package kraheja.commons.entity;

import java.io.Serializable;
import java.util.Date;

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
 * The persistent class for the COMPANY database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "COY1", columnList = "coyCode ASC, coyClosedate ASC ", unique = true),
		@Index(name = "COY2", columnList = "coyCode ASC, coyProp ASC, coyClosedate ASC", unique = true),
})
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CompanyCK companyCK;

	@Column(name="COY_ACTIVE")
	private String coyActive;

	@Column(name="COY_APPLYAC")
	private String coyApplyac;

	@Column(name="COY_APPLYCF")
	private String coyApplycf;

	@Column(name="COY_AUTHPERSDESG")
	private String coyAuthpersdesg;

	@Column(name="COY_AUTHPERSFATHER")
	private String coyAuthpersfather;

	@Column(name="COY_AUTHPERSON")
	private String coyAuthperson;

	@Column(name="COY_AUTHPERSPAN")
	private String coyAuthperspan;

	@Column(name="COY_CASHYN")
	private String coyCashyn;

	@Column(name="COY_CINNO")
	private String coyCinno;

	@Column(name="COY_CITY")
	private String coyCity;

	@Column(name="COY_CONSTT")
	private String coyConstt;

	@Column(name="COY_CONSTT2")
	private String coyConstt2;

	@Column(name="COY_CST_TIN")
	private String coyCstTin;

	@Column(name="COY_CURREPNUM")
	private Integer coyCurrepnum;

	@Column(name="COY_CURRPERIOD")
	private String coyCurrperiod;

	@Column(name="COY_CURRYEAR")
	private String coyCurryear;

	@Column(name="COY_DEFBANK")
	private String coyDefbank;

	@Column(name="COY_ESICAREA")
	private String coyEsicarea;

	@Column(name="COY_ESICNO")
	private String coyEsicno;

	@Column(name="COY_FDDEFINT12")
	private Integer coyFddefint12;

	@Column(name="COY_FDDEFINT24")
	private Integer coyFddefint24;

	@Column(name="COY_FDDEFINT36")
	private Integer coyFddefint36;

	@Column(name="COY_FDINTFREQ")
	private String coyFdintfreq;

	@Column(name="COY_FDINTTYPE")
	private String coyFdinttype;

	@Column(name="COY_FDRECNUM")
	private Integer coyFdrecnum;

	@Column(name="COY_FDYN")
	private String coyFdyn;

	@Column(name="COY_FICTITIOUS")
	private String coyFictitious;

	@Column(name="COY_GSTNO")
	private String coyGstno;

	@Column(name="COY_INTYYMM")
	private String coyIntyymm;

	@Column(name="COY_LASTADJNO")
	private Double coyLastadjno;

	@Column(name="COY_LASTJVL1")
	private String coyLastjvl1;

	@Column(name="COY_LASTJVLY")
	private String coyLastjvly;

	@Column(name="COY_LASTJVNO")
	private Double coyLastjvno;

	@Column(name="COY_LASTJVTY")
	private String coyLastjvty;

	@Column(name="COY_LASTYRCOMP")
	private Double coyLastyrcomp;

	@Column(name="COY_LASTYRINC")
	private Double coyLastyrinc;

	@Column(name="COY_LASTYRMAT")
	private Double coyLastyrmat;

	@Column(name="COY_LASTYRWORK")
	private Double coyLastyrwork;

	@Column(name="COY_MAINCOY")
	private String coyMaincoy;

	@Column(name="COY_NAME")
	private String coyName;

	@Column(name="COY_NAME4IT")
	private String coyName4it;

	@Column(name="COY_OPENDATE")
	private Date coyOpendate;

	@Column(name="COY_ORIGSITE")
	private String coyOrigsite;

	@Column(name="COY_PFACNO")
	private String coyPfacno;

	@Column(name="COY_PMTACNUM")
	private String coyPmtacnum;

	@Column(name="COY_RCNUM")
	private String coyRcnum;

	@Column(name="COY_SARECNUM")
	private Integer coySarecnum;

	@Column(name="COY_SEGMENT")
	private String coySegment;

	@Column(name="COY_SERVICETAXNUM")
	private String coyServicetaxnum;

	@Column(name="COY_SITE")
	private String coySite;

	@Column(name="COY_STAXREGN")
	private String coyStaxregn;

	@Column(name="COY_TANREGNUM")
	private String coyTanregnum;

	@Column(name="COY_TAXDEDACNUM")
	private String coyTaxdedacnum;

	@Column(name="COY_TDSACNO")
	private String coyTdsacno;

	@Column(name="COY_TDSCIRCLE")
	private String coyTdscircle;

	@Column(name="COY_THISYRCOMP")
	private Double coyThisyrcomp;

	@Column(name="COY_THISYRINC")
	private Double coyThisyrinc;

	@Column(name="COY_THISYRMAT")
	private Double coyThisyrmat;

	@Column(name="COY_THISYRWORK")
	private Double coyThisyrwork;

	@Column(name="COY_TODAY")
	private Date coyToday;

	@Column(name="COY_TYPETDS")
	private String coyTypetds;

	@Column(name="COY_USERID")
	private String coyUserid;

	@Column(name="COY_VAT_DATE")
	private Date coyVatDate;

	@Column(name="COY_VAT_TIN")
	private String coyVatTin;

	@Column(name="COY_YEANENDMM")
	private String coyYeanendmm;

}