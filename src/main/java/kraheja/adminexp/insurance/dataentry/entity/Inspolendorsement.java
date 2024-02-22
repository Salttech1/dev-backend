package kraheja.adminexp.insurance.dataentry.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the INSPOLENDORSEMENT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INSPOLENDORSEMENT1", columnList = "inpePolendorseid Asc", unique = true), 
})

public class Inspolendorsement implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InspolendorsementCK inspolendorsementCK;
	
	@Column(name="INPE_POLENDORSENUM")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpePolendorsenum;
	
	@Column(name="INPE_POLICYID")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpepolicyid;
	
	
	@Column(name="INPE_POLICYNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpepolicyno;

	@Column(name="INPE_CERTNUM")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeCertnum ;

	@Column(name="INPE_CERTSTAT")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeCertstat ;

	@Column(name="INPE_ENDORSETP")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeEndorsetp ;

	@Column(name="INPE_ENDRDETAIL")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeEndrdetail ;

	@Column(name="INPE_ENDRFRDT")
	private LocalDate inpeEndrfrdt ;

	@Column(name="INPE_ENDRINSVAL")
	private Double inpeEndrinsval ;

	@Column(name="INPE_ENDRPAYBY")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeEndrpayby ;

	@Column(name="INPE_ENDRPAYMODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeEndrpaymode ;

	@Column(name="INPE_ENDRPREMIUM")
	private Double inpeEndrpremium ;

	@Column(name="INPE_ENDRUPTODT")
	private LocalDate inpeEndruptodt ;

	@Column(name="INPE_IPADDRESS")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeIpaddress ;

	@Column(name="INPE_MACHINENAME")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeMachinename ;

	@Column(name="INPE_MODIFIEDON")
	private LocalDateTime inpeModifiedon ;

	@Column(name="INPE_MODULE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeModule ;

	@Column(name="INPE_POLENDORSEDT")
	private LocalDate inpePolendorsedt ;

	@Column(name="INPE_SITE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeSite ;

	@Column(name="INPE_TRANSER")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeTranser ;

	@Column(name="INPE_USERID")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String inpeUserid ;

}