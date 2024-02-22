package kraheja.enggsys.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the CONTRACT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "CONTT1", columnList = "conttContract Asc", unique = true)
})
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContractCK contractCK;

	@Column(name="CONTT_ADVTAX")
	private Double conttAdvtax ;

	@Column(name="CONTT_ADVTAXCA")
	private Double conttAdvtaxca ;

	@Column(name="CONTT_ADVTAXYR")
	private String conttAdvtaxyr ;

	@Column(name="CONTT_BLDGCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String conttBldgcode ;

	@Column(name="CONTT_CLOSEDATE")
	private LocalDate conttClosedate ;

	@Column(name="CONTT_CONTTOR")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String conttConttor ;

	@Column(name="CONTT_COY")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String conttCoy ;

	@Column(name="CONTT_ESTVAL")
	private Double conttEstval ;

	@Column(name="CONTT_EXMPREF")
	private String conttExmpref ;

	@Column(name="CONTT_KRISHICESSPERC")
	private Integer conttKrishicessperc ;

	@Column(name="CONTT_MATCODE")
	private String conttMatcode ;

	@Column(name="CONTT_MATGROUP")
	private String conttMatgroup ;

	@Column(name="CONTT_NAME")
	private String conttName ;

	@Column(name="CONTT_NEXTADV")
	private Integer conttNextadv ;

	@Column(name="CONTT_NEXTFIN")
	private Integer conttNextfin ;

	@Column(name="CONTT_NEXTINT")
	private Integer conttNextint ;

	@Column(name="CONTT_NEXTMIS")
	private Integer conttNextmis ;

	@Column(name="CONTT_NEXTRRET")
	private Integer conttNextrret ;

	@Column(name="CONTT_NEXTRUN")
	private Integer conttNextrun ;

	@Column(name="CONTT_NEXTSET")
	private Integer conttNextset ;

	@Column(name="CONTT_ORIGSITE")
	private String conttOrigsite ;

	@Column(name="CONTT_ORIGVAL")
	private Double conttOrigval ;

	@Column(name="CONTT_PAIDVAL")
	private Double conttPaidval ;

	@Column(name="CONTT_PARTYCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String conttPartycode ;

	@Column(name="CONTT_PARTYTYPE")
	private String conttPartytype ;

	@Column(name="CONTT_PREVTDS")
	private Double conttPrevtds ;

	@Column(name="CONTT_PROJECT")
	private String conttProject ;

	@Column(name="CONTT_PROPERTY")
	private String conttProperty ;

	@Column(name="CONTT_PROPRIETOR")
	private String conttProprietor ;

	@Column(name="CONTT_RETAINVAL")
	private Double conttRetainval ;

	@Column(name="CONTT_SERVTAXPERC")
	private Integer conttServtaxperc ;

	@Column(name="CONTT_SITE")
	private String conttSite ;

	@Column(name="CONTT_SPTDSPER")
	private Integer conttSptdsper ;

	@Column(name="CONTT_SWACHHCESSPERC")
	private Integer conttSwachhcessperc ;

	@Column(name="CONTT_TDSCODE")
	private String conttTdscode ;

	@Column(name="CONTT_TDSFROM")
	private LocalDate conttTdsfrom ;

	@Column(name="CONTT_TDSPER")
	private Integer conttTdsper ;

	@Column(name="CONTT_TDSPTYPE")
	private String conttTdsptype ;

	@Column(name="CONTT_TDSUPTO")
	private LocalDate conttTdsupto ;

	@Column(name="CONTT_TODAY")
	private LocalDateTime conttToday ;

	@Column(name="CONTT_USERID")
	private String conttUserid ;

	@Column(name="CONTT_VATPERC")
	private Integer conttVatperc ;

	@Column(name="CONTT_WCTAXPER")
	private Integer conttWctaxper ;

	@Column(name="CONTT_WING")
	private String conttWing ;

	@Column(name="CONTT_WORKCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String conttWorkcode ;

	@Column(name="CONTT_WORKGROUP")
	private String conttWorkgroup ;
	
	@Embeddable
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ContractCK implements Serializable{

		private static final long serialVersionUID = 1L;

		@Type(type = "kraheja.commons.utils.CharType") 
		private String conttContract ; 


	}

}