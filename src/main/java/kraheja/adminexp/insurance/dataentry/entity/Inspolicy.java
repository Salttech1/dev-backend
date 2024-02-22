package kraheja.adminexp.insurance.dataentry.entity;

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
 * The persistent class for the INSPOLICY database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INSPOLICY1", columnList = "inpPolicyid Asc", unique = true), 
})

public class Inspolicy implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InspolicyCK inspolicyCK;

	@Column(name="INP_ACHOLDERNAME")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpAcholdername ;

	@Column(name="INP_ACNUMBER")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpAcnumber ;

	@Column(name="INP_AGENT")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpAgent ;

	@Column(name="INP_ASSETLOCATION")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpAssetlocation ;

	@Column(name="INP_BANK")
	private String inpBank ;

	@Column(name="INP_BLDGCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpBldgcode ;

	@Column(name="INP_BRANCH")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpBranch ;

	@Column(name="INP_COVUNDER")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpCovunder ;

	@Column(name="INP_COYCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpCoycode ;

	@Column(name="INP_DEDUCTIBLE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpDeductible ;

	@Column(name="INP_FROMDATE")
	private LocalDate inpFromdate ;

	@Column(name="INP_IFSC")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpIfsc ;

	@Column(name="INP_INSCOY")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpInscoy ;

	@Column(name="INP_INSUREDVAL")
	private Double inpInsuredval ;

	@Column(name="INP_IPADDRESS")
	private String inpIpaddress ;

	@Column(name="INP_IT_SECTION")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpIt_Section ;

	@Column(name="INP_MACHINENAME")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpMachinename ;

	@Column(name="INP_MATURITYDATE")
	private LocalDate inpMaturitydate ;

	@Column(name="INP_MODIFIEDON")
	private LocalDateTime inpModifiedon ;

	@Column(name="INP_MODULE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpModule ;

	@Column(name="INP_NOMINATIONDET")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpNominationdet ;

	@Column(name="INP_PAYBY")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPayby ;

	@Column(name="INP_PAYMODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPaymode ;

	@Column(name="INP_PERSONINSURED")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPersoninsured ;

	@Column(name="INP_POLICYSUBTYPE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPolicysubtype ;

	@Column(name="INP_POLICYTYPE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPolicytype ;

	@Column(name="INP_PREMIUMFREQ")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPremiumfreq ;

	@Column(name="INP_PREVPOLICYNUMBER")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpPrevpolicynumber ;

	@Column(name="INP_RENEWEDYN") 
	private String inpRenewedyn ;

	@Column(name="INP_SITE")
	private String inpSite ;

	@Column(name="INP_STAFF1")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpStaff1 ;

	@Column(name="INP_STAFF2")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpStaff2 ;

	@Column(name="INP_STATUS")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpStatus ;

	@Column(name="INP_USERID") 
	private String inpUserid ;

}