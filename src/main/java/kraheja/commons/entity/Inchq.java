package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the INCHQ database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inchq implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private InchqCK inchqCk;

	@Column(name="INCHQ_ACTYPE")
	private String inchqActype;

	@Column(name="INCHQ_AMOUNT")
	private Double inchqAmount;

	@Column(name="INCHQ_BOUNCEDATE")
	private LocalDateTime inchqBouncedate;

	@Column(name="INCHQ_BOUNREVON")
	private LocalDateTime inchqBounrevon;

	@Column(name="INCHQ_BOUNREVYN")
	private String inchqBounrevyn;

	@Column(name="INCHQ_CANCELEDATE")
	private LocalDateTime inchqCanceledate;

	@Column(name="INCHQ_COYBANK")
	private String inchqCoybank;

	@Column(name="INCHQ_DATE")
	private LocalDateTime inchqDate;

	@Column(name="INCHQ_FUND")
	private String inchqFund;

	@Column(name="INCHQ_LOANYN")
	private String inchqLoanyn;

	@Column(name="INCHQ_ORIGSITE")
	private String inchqOrigsite;

	@Column(name="INCHQ_ORIGSYS")
	private String inchqOrigsys;

	@Column(name="INCHQ_OUTSTAT")
	private String inchqOutstat;

	@Column(name="INCHQ_PARTYCODE")
	private String inchqPartycode;

	@Column(name="INCHQ_PAYMODE")
	private String inchqPaymode;

	@Column(name="INCHQ_PROPRIETOR")
	private String inchqProprietor;

	@Column(name="INCHQ_RECONDATE")
	private LocalDateTime inchqRecondate;

	@Column(name="INCHQ_RECONSTMT")
	private String inchqReconstmt;

	@Column(name="INCHQ_REMARK")
	private String inchqRemark;

	@Column(name="INCHQ_RESUBCOUNT")
	private Double inchqResubcount;

	@Column(name="INCHQ_RESUBDATE")
	private LocalDateTime inchqResubdate;

	@Column(name="INCHQ_SITE")
	private String inchqSite;

	@Column(name="INCHQ_SLIPNUM")
	private String inchqSlipnum;

	@Column(name="INCHQ_TODAY")
	private LocalDateTime inchqToday;

	@Column(name="INCHQ_USERID")
	private String inchqUserid;
}