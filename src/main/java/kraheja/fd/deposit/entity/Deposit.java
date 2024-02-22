package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
 * The persistent class for the DEPOSIT database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DEP1", columnList = "depCoy ASC, depDepositor ASC, depReceiptnum ASC", unique = true),
		@Index(name = "DEP2", columnList = "depReceiptnum ASC, depCoy ASC, depDepositor ASC", unique = true)
})
public class Deposit implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DepositCK depositCK;
	
	@Column(name="DEP_ACCINT")
	private Double depAccint;

	@Column(name="DEP_BANKCODE")
	private String depBankcode;

	@Column(name="DEP_BROKCHEQ")
	private String depBrokcheq;

	@Column(name="DEP_BROKCHEQDATE")
	private LocalDateTime depBrokcheqdate;

	@Column(name="DEP_BROKER")
	private String depBroker;

	@Column(name="DEP_BROKERAGE")
	private Double depBrokerage;

	@Column(name="DEP_BROKPER")
	private Double depBrokper;

	@Column(name="DEP_BROKTDS")
	private Double depBroktds;

	@Column(name="DEP_CANCELDATE")
	private LocalDateTime depCanceldate;

	@Column(name="DEP_DEPAMOUNT")
	private Float depDepamount;

	@Column(name="DEP_DEPDATE")
	private LocalDateTime depDepdate;

	@Column(name="DEP_DEPMONTHS")
	private Double depDepmonths;

	@Column(name="DEP_DEPORIGIN")
	private String depDeporigin;

	@Column(name="DEP_DISDATE")
	private LocalDateTime depDisdate;

	@Column(name="DEP_EXPIRY_STATUS")
	private String depExpiryStatus;

	@Column(name="DEP_EXTENDEDYN")
	private String depExtendedyn;

	@Column(name="DEP_GROSSINT")
	private Double depGrossint;

	@Column(name="DEP_INST_BP")
	private String depInstBp;

	@Column(name="DEP_INST_FDR")
	private String depInstFdr;

	@Column(name="DEP_INST_IP")
	private String depInstIp;

	@Column(name="DEP_INSTRUCTIONS")
	private String depInstructions;

	@Column(name="DEP_INTACCYYMM")
	private String depIntaccyymm;

	@Column(name="DEP_INTFREQ")
	private Double depIntfreq;

	@Column(name="DEP_INTPAIDYTD")
	private Double depIntpaidytd;

	@Column(name="DEP_INTRATE")
	private Double depIntrate;

	@Column(name="DEP_INTRATE2")
	private Double depIntrate2;

	@Column(name="DEP_INTRATEOLD")
	private Double depIntrateold;

	@Column(name="DEP_JOWNER1")
	private String depJowner1;

	@Column(name="DEP_JOWNER2")
	private String depJowner2;

	@Column(name="DEP_JOWNER3")
	private String depJowner3;

	@Column(name="DEP_LIQTYPE")
	private String depLiqtype;

	@Column(name="DEP_MATAMOUNT")
	private Double depMatamount;

	@Column(name="DEP_MATDATE")
	private LocalDateTime depMatdate;

	@Column(name="DEP_NGID1")
	private String depNgid1;

	@Column(name="DEP_NGID2")
	private String depNgid2;

	@Column(name="DEP_NGID3")
	private String depNgid3;

	@Column(name="DEP_NGNAME1")
	private String depNgname1;

	@Column(name="DEP_NGNAME2")
	private String depNgname2;

	@Column(name="DEP_NGNAME3")
	private String depNgname3;

	@Column(name="DEP_NOMINEE")
	private String depNominee;

	@Column(name="DEP_ORIGRATE")
	private Double depOrigrate;

	@Column(name="DEP_ORIGRECEIPT")
	private String depOrigreceipt;

	@Column(name="DEP_ORIGSITE")
	private String depOrigsite;

	@Column(name="DEP_PAYAMOUNT")
	private Double depPayamount;

	@Column(name="DEP_PAYDATE")
	private LocalDateTime depPaydate;

	@Column(name="DEP_PAYEECODE")
	private String depPayeecode;

	@Column(name="DEP_PAYREF")
	private String depPayref;

	@Column(name="DEP_PREVMATURITYDATE")
	private LocalDateTime depPrevmaturitydate;

	@Column(name="DEP_PRINTEDON")
	private LocalDateTime depPrintedon;

	@Column(name="DEP_PRINTREV")
	private Double depPrintrev;

	@Column(name="DEP_PROPRIETOR")
	private String depProprietor;

	@Column(name="DEP_RECEIPTDATE")
	private LocalDateTime depReceiptdate;

	@Column(name="DEP_SITE")
	private String depSite;

	@Column(name="DEP_STAFFYN")
	private String depStaffyn;

	@Column(name="DEP_TAXPAIDYTD")
	private Double depTaxpaidytd;

	@Column(name="DEP_TDS")
	private Double depTds;

	@Column(name="DEP_TODAY")
	private LocalDateTime depToday;

	@Column(name="DEP_TRANSFER")
	private LocalDateTime depTransfer;

	@Column(name="DEP_USERID")
	private String depUserid;

	@Column(name="DEP_WAR_ACC")
	private String depWarAcc;

	@Column(name="DEP_WAR_BANK")
	private String depWarBank;
}