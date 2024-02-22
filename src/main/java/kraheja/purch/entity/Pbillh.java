package kraheja.purch.entity;

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
 * The persistent class for the PBILLH database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PBILLH1", columnList = "pblhSer Asc", unique = true), 
})
public class Pbillh implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private PbillhCK pbillhCK;

	@Column(name="PBLH_ADVANCEADJ")
	private Double pblhAdvanceadj;

	@Column(name="PBLH_ADVANCEPAID")
	private Double pblhAdvancepaid;

	@Column(name="PBLH_AMOUNT")
	private Double pblhAmount;

	@Column(name="PBLH_AUTHDATE")
	private LocalDate pblhAuthdate;

	@Column(name="PBLH_AUTHNUM")
	private String pblhAuthnum;

	@Column(name="PBLH_BANKCODE")
	private String pblhBankcode;

	@Column(name="PBLH_BILLTYPE")
	private String pblhBilltype;

	@Column(name="PBLH_BLDGCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String pblhBldgcode;

	@Column(name="PBLH_CHEQUENO")
	private String pblhChequeno;

	@Column(name="PBLH_COY")
	private String pblhCoy;

	@Column(name="PBLH_CRDAYS")
	private Integer pblhCrdays;

	@Column(name="PBLH_DATE")
	private LocalDate pblhDate;

	@Column(name="PBLH_DEBITAMT")
	private Double pblhDebitamt;

	@Column(name="PBLH_DEBSOCYN")
	private String pblhDebsocyn;

	@Column(name="PBLH_MISBLDG")
	private String pblhMisbldg;

	@Column(name="PBLH_MISPROJECT")
	private String pblhMisproject;

	@Column(name="PBLH_NARRATION")
	private String pblhNarration;

	@Column(name="PBLH_OMSPURCYN")
	private String pblhOmspurcyn;

	@Column(name="PBLH_ORDEREDBY")
	private String pblhOrderedby;

	@Column(name="PBLH_ORIGSITE")
	private String pblhOrigsite;

	@Column(name="PBLH_PAIDAMT")
	private Double pblhPaidamt;

	@Column(name="PBLH_PAIDDATE")
	private LocalDate pblhPaiddate;

	@Column(name="PBLH_PAIDREF")
	private String pblhPaidref;

	@Column(name="PBLH_PARTYCODE")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String pblhPartycode;

	@Column(name="PBLH_PARTYTYPE")
	private String pblhPartytype;

	@Column(name="PBLH_PROJECT")
	private String pblhProject;

	@Column(name="PBLH_PROP")
	private String pblhProp;

	@Column(name="PBLH_PROPERTY")
	private String pblhProperty;

	@Column(name="PBLH_RETAINOS")
	private Double pblhRetainos;

	@Column(name="PBLH_RETENTION")
	private Double pblhRetention;

	@Column(name="PBLH_SITE")
	private String pblhSite;

	@Column(name="PBLH_SUPPBILLDT")
	private LocalDate pblhSuppbilldt;

	
	@Column(name="PBLH_SUPPBILLNO")
	@Type(type = "kraheja.commons.utils.CharType") 
	private String pblhSuppbillno;

	@Column(name="PBLH_TCSAMOUNT")
	private Double pblhTcsamount;

	@Column(name="PBLH_TDCERTDT")
	private LocalDate pblhTdcertdt;

	@Column(name="PBLH_TDSAMOUNT")
	private Double pblhTdsamount;

	@Column(name="PBLH_TDSBANKCODE")
	private String pblhTdsbankcode;

	@Column(name="PBLH_TDSCERTNO")
	private String pblhTdscertno;

	@Column(name="PBLH_TDSCHALDT")
	private LocalDate pblhTdschaldt;

	@Column(name="PBLH_TDSCHALNO")
	private String pblhTdschalno;

	@Column(name="PBLH_TDSPERC")
	private Double pblhTdsperc;

	@Column(name="PBLH_TDSPRINT")
	private Integer pblhTdsprint;

	@Column(name="PBLH_TODAY")
	private LocalDateTime pblhToday;

	@Column(name="PBLH_USERID")
	private String pblhUserid;

}