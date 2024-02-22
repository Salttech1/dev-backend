package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the TDEPINT database table.
 * 
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tdepint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TdepintCK tDepintCK;
	
	@Column(name="TDEP_ACCINT")
	private Double tdepAccint;

	@Column(name="TDEP_COY")
	private String tdepCoy;

	@Column(name="TDEP_DEPOSITOR")
	private String tdepDepositor;

	@Column(name="TDEP_INTPAIDYTD")
	private Double tdepIntpaidytd;

	@Column(name="TDEP_RECEIPTNUM")
	private String tdepReceiptnum;

	@Column(name="TDEP_SITE")
	private String tdepSite;

	@Column(name="TDEP_TAXPAIDYTD")
	private Double tdepTaxpaidytd;

	@Column(name="TDEP_TDS")
	private Double tdepTds;

	@Column(name="TDEP_TODAY")
	private LocalDateTime tdepToday;

	@Column(name="TDEP_USERID")
	private String tdepUserid;

	@Column(name="TDEPTR_ACCINT")
	private Double tdeptrAccint;

	@Column(name="TDEPTR_COY")
	private String tdeptrCoy;

	@Column(name="TDEPTR_DEPOSITOR")
	private String tdeptrDepositor;

	@Column(name="TDEPTR_INTPAIDYTD")
	private Double tdeptrIntpaidytd;

	@Column(name="TDEPTR_SITE")
	private String tdeptrSite;

	@Column(name="TDEPTR_TAXPAIDYTD")
	private Double tdeptrTaxpaidytd;

	@Column(name="TDEPTR_TAXRECYTD")
	private Double tdeptrTaxrecytd;

	@Column(name="TDEPTR_TDS")
	private Double tdeptrTds;

	@Column(name="TDEPTR_TODAY")
	private LocalDateTime tdeptrToday;

	@Column(name="TDEPTR_USERID")
	private String tdeptrUserid;

	@Column(name="TDIN_BANKCODE")
	private String tdinBankcode;

	@Column(name="TDIN_CANCELDATE")
	private LocalDate tdinCanceldate;

	@Column(name="TDIN_CHQNUM")
	private String tdinChqnum;

	@Column(name="TDIN_FROMDATE")
	private LocalDate tdinFromdate;

	@Column(name="TDIN_INTEREST")
	private Double tdinInterest;

	@Column(name="TDIN_INTFROM")
	private LocalDate tdinIntfrom;
	
	@Column(name="TDIN_NEWPRIN")
	private Double tdinNewprin;

	@Column(name="TDIN_ORIGSITE")
	private String tdinOrigsite;

	@Column(name="TDIN_PROPRIETOR")
	private String tdinProprietor;

	@Column(name="TDIN_SESSID")
	private Double tdinSessid;

	@Column(name="TDIN_SITE")
	private String tdinSite;

	@Column(name="TDIN_STAFFALLOW")
	private Double tdinStaffallow;

	@Column(name="TDIN_TAXALWAYSYN")
	private String tdinTaxalwaysyn;

	@Column(name="TDIN_TDS")
	private Double tdinTds;

	@Column(name="TDIN_TDS15HYN")
	private String tdinTds15hyn;

	@Column(name="TDIN_TODATE")
	private LocalDate tdinTodate;

	@Column(name="TDIN_TODAY")
	private LocalDateTime tdinToday;

	@Column(name="TDIN_TRANSER")
	private String tdinTranser;

	@Column(name="TDIN_USERID")
	private String tdinUserid;

}
