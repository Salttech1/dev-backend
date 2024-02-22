package kraheja.enggsys.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
 * The persistent class for the CERT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cert implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CertCK certCK;

	@Column(name="CERT_ADVADJUSTED")
	private Double certAdvadjusted ;

	@Column(name="CERT_ASSTAXFROM")
	private LocalDate certAsstaxfrom ;

	@Column(name="CERT_ASSTAXUPTO")
	private LocalDate certAsstaxupto ;

	@Column(name="CERT_BASICAMT")
	private Integer certBasicamt ;

	@Column(name="CERT_BILLAMOUNT")
	private Double certBillamount ;

	@Column(name="CERT_BILLDATE")
	private LocalDate certBilldate ;

	@Column(name="CERT_BILLNOS")
	private String certBillnos ;

	@Column(name="CERT_BILLREF")
	private String certBillref ;

	@Column(name="CERT_CERTAMOUNT")
	private Double certCertamount ;

	@Column(name="CERT_CERTDATE")
	private LocalDate certCertdate ;

	@Column(name="CERT_CERTREVNUM")
	private Integer certCertrevnum ;

	@Column(name="CERT_CERTSTATUS")
	private String certCertstatus ;

	@Column(name="CERT_CERTTYPE")
	private String certCerttype ;

	@Column(name="CERT_CFCODE")
	private String certCfcode ;

	@Column(name="CERT_CFGROUP")
	private String certCfgroup ;

	@Column(name="CERT_CITY")
	private String certCity ;

	@Column(name="CERT_CLEARACYN")
	private String certClearacyn ;

	@Column(name="CERT_CONAMOUNT")
	private Double certConamount ;

	@Column(name="CERT_CONTRACT")
	private String certContract ;

	@Column(name="CERT_COY")
	private String certCoy ;

	@Column(name="CERT_DEBIT")
	private Double certDebit ;

	@Column(name="CERT_DEBITINGPARTY")
	private String certDebitingparty ;

	@Column(name="CERT_DEBITINGREASON")
	private String certDebitingreason ;

	@Column(name="CERT_DEBSOCYN")
	private String certDebsocyn ;

	@Column(name="CERT_DESCRIPTION")
	private String certDescription ;

	@Column(name="CERT_DOMAIN")
	private String certDomain ;

	@Column(name="CERT_DURFROM")
	private LocalDate certDurfrom ;

	@Column(name="CERT_DURTO")
	private LocalDate certDurto ;

	@Column(name="CERT_EQUIPID")
	private String certEquipid ;

	@Column(name="CERT_FLAT")
	private String certFlat ;

	@Column(name="CERT_KRISHICESSAMT")
	private Integer certKrishicessamt ;

	@Column(name="CERT_KRISHICESSPERC")
	private Integer certKrishicessperc ;

	@Column(name="CERT_MAINMATGROUP")
	private String certMainmatgroup ;

	@Column(name="CERT_MAINPARTY")
	private String certMainparty ;

	@Column(name="CERT_MAINRECID")
	private String certMainrecid ;

	@Column(name="CERT_MATCODE")
	private String certMatcode ;

	@Column(name="CERT_MATGROUP")
	private String certMatgroup ;

	@Column(name="CERT_MISBLDG")
	private String certMisbldg ;

	@Column(name="CERT_MISPROJECT")
	private String certMisproject ;

	@Column(name="CERT_MWCTAXAMOUNT")
	private Double certMwctaxamount ;

	@Column(name="CERT_ORCONAMOUNT")
	private Double certOrconamount ;

	@Column(name="CERT_ORCONTRACT")
	private String certOrcontract ;

	@Column(name="CERT_ORIGINATOR")
	private String certOriginator ;

	@Column(name="CERT_ORIGSITE")
	private String certOrigsite ;

	@Column(name="CERT_ORPARTYCODE")
	private String certOrpartycode ;

	@Column(name="CERT_ORPARTYTYPE")
	private String certOrpartytype ;

	@Column(name="CERT_PARTYTYPE")
	private String certPartytype ;

	@Column(name="CERT_PASSEDON")
	private LocalDate certPassedon ;

	@Column(name="CERT_PAYAMOUNT")
	private Double certPayamount ;

	@Column(name="CERT_PAYDATE")
	private LocalDate certPaydate ;

	@Column(name="CERT_PAYREF")
	private String certPayref ;

	@Column(name="CERT_PAYTENDER")
	private String certPaytender ;

	@Column(name="CERT_PERDONE")
	private Double certPerdone ;

	@Column(name="CERT_PRINTED")
	private Integer certPrinted ;

	@Column(name="CERT_PRINTEDON")
	private LocalDate certPrintedon ;

	@Column(name="CERT_PROJECT")
	private String certProject ;

	@Column(name="CERT_PROP")
	private String certProp ;

	@Column(name="CERT_PROPERTY")
	private String certProperty ;

	@Column(name="CERT_PROVYN")
	private String certProvyn ;

	@Column(name="CERT_PRV_AMT")
	private Double certPrvAmt ;

	@Column(name="CERT_PRV_CERTNUM")
	private String certPrvCertnum ;

	@Column(name="CERT_PRV_DATE")
	private LocalDate certPrvDate ;

	@Column(name="CERT_PRV_TYPE")
	private String certPrvType ;

	@Column(name="CERT_REMARKS")
	private String certRemarks ;

	@Column(name="CERT_REQDATE")
	private LocalDate certReqdate ;

	@Column(name="CERT_REQNUM")
	private String certReqnum ;

	@Column(name="CERT_RETAINED")
	private Double certRetained ;

	@Column(name="CERT_RUNSER")
	private Integer certRunser ;

	@Column(name="CERT_SERVICETAXAMT")
	private Integer certServicetaxamt ;

	@Column(name="CERT_SERVICETAXPERC")
	private Integer certServicetaxperc ;

	@Column(name="CERT_SITE")
	private String certSite ;

	@Column(name="CERT_SOCID")
	private String certSocid ;

	@Column(name="CERT_SWACHHCESSAMT")
	private Integer certSwachhcessamt ;

	@Column(name="CERT_SWACHHCESSPERC")
	private Integer certSwachhcessperc ;

	@Column(name="CERT_TAXLEVEL")
	private String certTaxlevel ;

	@Column(name="CERT_TDSAMOUNT")
	private Double certTdsamount ;

	@Column(name="CERT_TDSSUR")
	private Double certTdssur ;

	@Column(name="CERT_TODAY")
	private LocalDateTime certToday ;

	@Column(name="CERT_TOT_TWOPTC")
	private Double certTotTwoptc ;

	@Column(name="CERT_TOT_TWTPTC")
	private Double certTotTwtptc ;

	@Column(name="CERT_TRANSER")
	private String certTranser ;

	@Column(name="CERT_TRIPS")
	private Integer certTrips ;

	@Column(name="CERT_T_ADVADJ")
	private Double certTAdvadj ;

	@Column(name="CERT_T_ADVOS")
	private Double certTAdvos ;

	@Column(name="CERT_T_ADVPAID")
	private Double certTAdvpaid ;

	@Column(name="CERT_T_DEBIT")
	private Double certTDebit ;

	@Column(name="CERT_T_DEBSOC")
	private Double certTDebsoc ;

	@Column(name="CERT_T_MWCTAXAMT")
	private Double certTMwctaxamt ;

	@Column(name="CERT_T_PAYMENT")
	private Double certTPayment ;

	@Column(name="CERT_T_PERPAID")
	private Double certTPerpaid ;

	@Column(name="CERT_T_RETAINED")
	private Double certTRetained ;

	@Column(name="CERT_T_TDSAMT")
	private Double certTTdsamt ;

	@Column(name="CERT_T_TDSSUR")
	private Double certTTdssur ;

	@Column(name="CERT_T_TRIPS")
	private Double certTTrips ;

	@Column(name="CERT_T_WRITEOFFAMT")
	private Double certTWriteoffamt ;

	@Column(name="CERT_USERID")
	private String certUserid ;

	@Column(name="CERT_VATAMT")
	private Integer certVatamt ;

	@Column(name="CERT_VATPERC")
	private Integer certVatperc ;

	@Column(name="CERT_WING")
	private String certWing ;

	@Column(name="CERT_WORKGROUP")
	private String certWorkgroup ;

	@Column(name="CERT_WRITEOFFAMT")
	private Double certWriteoffamt ;
	
	@Type(type = "kraheja.commons.utils.CharType") 
	private String certBldgcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String certWorkcode ; 

	@Type(type = "kraheja.commons.utils.CharType") 
	private String certPartycode ; 

	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CertCK implements Serializable{
		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String certCertnum ; 

	}

}