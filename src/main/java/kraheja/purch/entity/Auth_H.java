package kraheja.purch.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the AUTH_H database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Auth_H implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Auth_HCK authhCK;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String authBldgcode; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String authPartycode; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String authMatgroup;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String authTranser; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType") 
	private String authCoy;

	@Column(name="AUTH_ADVADJPAST")
	private Double authAdvadjpast ;

	@Column(name="AUTH_ADVADJUST")
	private Double authAdvadjust ;

	@Column(name="AUTH_ADVGRANTED")
	private Double authAdvgranted ;

	@Column(name="AUTH_AUTHAMOUNT")
	private Double authAuthamount ;

	@Column(name="AUTH_AUTHDATE")
	private LocalDate authAuthdate ;

	@Column(name="AUTH_AUTHQUANTY")
	private Double authAuthquanty ;

	@Column(name="AUTH_AUTHSTATUS")
	private String authAuthstatus ;
	
	@Column(name="AUTH_AUTHTYPE")
	private String authAuthtype ;
	
	@Column(name="AUTH_BLDGAUTHNO")
	private Double authBldgauthno ;

	@Column(name="AUTH_DEBSOCYN")
	private String authDebsocyn ;

	@Column(name="AUTH_DESCRIPTION")
	private String authDescription ;

	@Column(name="AUTH_EXPENSTYPE")
	private String authExpenstype ;

	@Column(name="AUTH_LOTNO")
	private String authLotno ;

	@Column(name="AUTH_MATBLDAUTH")
	private Double authMatbldauth ;

	@Column(name="AUTH_MATCODE")
	private String authMatcode ;

	@Column(name="AUTH_MISBLDG")
	private String authMisbldg ;

	@Column(name="AUTH_MISPROJECT")
	private String authMisproject ;

	@Column(name="AUTH_OPRADVADJ")
	private Double authOpradvadj ;

	@Column(name="AUTH_OPRADVANCE")
	private Double authOpradvance ;

	@Column(name="AUTH_OPRAUTHAMT")
	private Double authOprauthamt ;

	@Column(name="AUTH_OPRAUTHQTY")
	private Double authOprauthqty ;

	@Column(name="AUTH_OPRRELRET")
	private Double authOprrelret ;

	@Column(name="AUTH_OPRRETAIN")
	private Double authOprretain ;

	@Column(name="AUTH_OPRRETENTIONADJ")
	private Double authOprretentionadj ;

	@Column(name="AUTH_ORIGSITE")
	private String authOrigsite ;

	@Column(name="AUTH_PARTYAUTH")
	private Double authPartyauth ;

	@Column(name="AUTH_PARTYCODE2")
	private String authPartycode2 ;

	@Column(name="AUTH_PARTYTYPE")
	private String authPartytype ;

	@Column(name="AUTH_PASSEDON")
	private LocalDate authPassedon ;

	@Column(name="AUTH_PAYAMOUNT")
	private Double authPayamount ;

	@Column(name="AUTH_PAYDATE")
	private LocalDate authPaydate ;

	@Column(name="AUTH_PAYREF")
	private String authPayref ;

	@Column(name="AUTH_PAYTENDER")
	private String authPaytender ;

	@Column(name="AUTH_PERDONE")
	private Double authPerdone ;

	@Column(name="AUTH_PREPAREDBY")
	private String authPreparedby ;

	@Column(name="AUTH_PRINTED")
	private Double authPrinted ;

	@Column(name="AUTH_PRINTEDON")
	private LocalDateTime authPrintedon ;

	@Column(name="AUTH_PROJECT")
	private String authProject ;

	@Column(name="AUTH_PROP")
	private String authProp ;

	@Column(name="AUTH_PROPERTY")
	private String authProperty ;

	@Column(name="AUTH_PRVADVADJ")
	private Double authPrvadvadj ;

	@Column(name="AUTH_PRVADVANCE")
	private Double authPrvadvance ;

	@Column(name="AUTH_PRVAUTHAMT")
	private Double authPrvauthamt ;

	@Column(name="AUTH_PRVAUTHNO")
	private String authPrvauthno ;

	@Column(name="AUTH_PRVAUTHQTY")
	private Double authPrvauthqty ;

	@Column(name="AUTH_PRVDATE")
	private LocalDate authPrvdate ;

	@Column(name="AUTH_PRVRELRET")
	private Double authPrvrelret ;

	@Column(name="AUTH_PRVRETAMT")
	private Double authPrvretamt ;

	@Column(name="AUTH_PRVRETENTIONADJ")
	private Double authPrvretentionadj ;

	@Column(name="AUTH_PRVTYPE")
	private String authPrvtype ;

	@Column(name="AUTH_RELRETAIN")
	private Double authRelretain ;

	@Column(name="AUTH_REMARKS")
	private String authRemarks ;

	@Column(name="AUTH_RETAINED")
	private Double authRetained ;

	@Column(name="AUTH_RETENTIONADJ")
	private Double authRetentionadj ;

	@Column(name="AUTH_SITE")
	private String authSite ;

	@Column(name="AUTH_TDSAMOUNT")
	private Double authTdsamount ;

	@Column(name="AUTH_TODAY")
	private LocalDateTime authToday ;

	@Type(type = "kraheja.commons.utils.CharType") 
	@Column(name="AUTH_USERID")
	private String authUserid ;
}