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
 * The persistent class for the CBILLH database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "CBILLH1", columnList = "cblhSer Asc", unique = true)
})

public class Cbillh implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CbillhCK cbillhCK;

	@Column(name="CBLH_ADVANCEADJ")
	private Double cblhAdvanceadj ;

	@Column(name="CBLH_ADVANCEPAID")
	private Double cblhAdvancepaid ;

	@Column(name="CBLH_AMOUNT")
	private Double cblhAmount ;

	@Column(name="CBLH_BANKCODE")
	private String cblhBankcode ;

	@Column(name="CBLH_BILLTYPE")
	private String cblhBilltype ;

	@Column(name="CBLH_BLDGCODE")
	private String cblhBldgcode ;

	@Column(name="CBLH_CERTDATE")
	private LocalDate cblhCertdate ;

	@Column(name="CBLH_CERTNUM")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String cblhCertnum ;

	@Column(name="CBLH_CHEQUENO")
	private String cblhChequeno ;

	@Column(name="CBLH_CONTBILLDT")
	private LocalDate cblhContbilldt ;

	@Column(name="CBLH_CONTBILLNO")
	private String cblhContbillno ;

	@Column(name="CBLH_CONTRACT")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String cblhContract ;

	@Column(name="CBLH_COY")
	private String cblhCoy ;

	@Column(name="CBLH_CRDAYS")
	private Integer cblhCrdays ;

	@Column(name="CBLH_DATE")
	private LocalDate cblhDate ;

	@Column(name="CBLH_DEBITAMT")
	private Double cblhDebitamt ;

	@Column(name="CBLH_DEBSOCYN")
	private String cblhDebsocyn ;

	@Column(name="CBLH_DURFROM")
	private LocalDate cblhDurfrom ;

	@Column(name="CBLH_DURTO")
	private LocalDate cblhDurto ;

	@Column(name="CBLH_FREIGHT")
	private Double cblhFreight ;

	@Column(name="CBLH_MISBLDG")
	private String cblhMisbldg ;

	@Column(name="CBLH_MISPROJECT")
	private String cblhMisproject ;

	@Column(name="CBLH_NARRATION")
	private String cblhNarration ;

	@Column(name="CBLH_OMSSERVICEYN")
	private String cblhOmsserviceyn ;

	@Column(name="CBLH_ORIGSITE")
	private String cblhOrigsite ;

	@Column(name="CBLH_OTHERCHARGES")
	private Double cblhOthercharges ;

	@Column(name="CBLH_PACKING")
	private Double cblhPacking ;

	@Column(name="CBLH_PAIDAMT")
	private Double cblhPaidamt ;

	@Column(name="CBLH_PAIDDATE")
	private LocalDate cblhPaiddate ;

	@Column(name="CBLH_PAIDREF")
	private String cblhPaidref ;

	@Column(name="CBLH_PARTYCODE")
	private String cblhPartycode ;

	@Column(name="CBLH_PARTYTYPE")
	private String cblhPartytype ;

	@Column(name="CBLH_PROJECT")
	private String cblhProject ;

	@Column(name="CBLH_PROP")
	private String cblhProp ;

	@Column(name="CBLH_PROPERTY")
	private String cblhProperty ;

	@Column(name="CBLH_RETAINOS")
	private Double cblhRetainos ;

	@Column(name="CBLH_RETENTION")
	private Double cblhRetention ;

	@Column(name="CBLH_SITE")
	private String cblhSite ;

	@Column(name="CBLH_TDCERTDT")
	private LocalDate cblhTdcertdt ;

	@Column(name="CBLH_TDSAMOUNT")
	private Double cblhTdsamount ;

	@Column(name="CBLH_TDSBANKCODE")
	private String cblhTdsbankcode ;

	@Column(name="CBLH_TDSCERTNO")
	private String cblhTdscertno ;

	@Column(name="CBLH_TDSCHALDT")
	private LocalDate cblhTdschaldt ;

	@Column(name="CBLH_TDSCHALNO")
	private String cblhTdschalno ;

	@Column(name="CBLH_TDSPERC")
	private Double cblhTdsperc ;

	@Column(name="CBLH_TDSPRINT")
	private Integer cblhTdsprint ;

	@Column(name="CBLH_TODAY")
	private LocalDateTime cblhToday ;

	@Column(name="CBLH_TRANSPORT")
	private Double cblhTransport ;

	@Column(name="CBLH_USERID")
	private String cblhUserid ;

	@Column(name="CBLH_WORKCODE")
	private String cblhWorkcode ;
	
@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public static class CbillhCK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Type(type = "kraheja.commons.utils.CharType") 
	private String cblhSer ; 
}
}