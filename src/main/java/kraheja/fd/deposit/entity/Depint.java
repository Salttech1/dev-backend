package kraheja.fd.deposit.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
 * The persistent class for the DEPINT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "DEPINT1", columnList = "dinDepositor Asc, dinReceiptnum Asc, dinIntfrom Asc, dinIntupto Asc, dinCoy Asc, dinChqnum Asc", unique = true), 
})

public class Depint implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DepintCK depintCK;

	@Column(name="DIN_BANKCODE")
	private String dinBankcode ;

	@Column(name="DIN_CANCELDATE")
	private LocalDate dinCanceldate ;

	@Column(name="DIN_INTEREST")
	private Double dinInterest ;

	@Column(name="DIN_NEWPRIN")
	private Double dinNewprin ;

	@Column(name="DIN_ORIGSITE")
	private String dinOrigsite ;

	@Column(name="DIN_PROPRIETOR")
	private String dinProprietor ;

	@Column(name="DIN_SITE")
	private String dinSite ;

	@Column(name="DIN_SESSID")
	private Double dinSessid ;

	@Column(name="DIN_STAFFALLOW")
	private Double dinStaffallow ;

	@Column(name="DIN_TAXALWAYSYN")
	private String dinTaxalwaysyn ;

	@Column(name="DIN_TDS")
	private Double dinTds ;

	@Column(name="DIN_TDS15HYN")
	private String dinTds15hyn ;

	@Column(name="DIN_TODAY")
	private LocalDateTime dinToday ;

	@Column(name="DIN_TRANSER")
	private String dinTranser ;

	@Column(name="DIN_USERID")
	private String dinUserid ;
	
	@Column(name="DIN_FROMDATE")
	private LocalDate dinFromdate ;
	
	@Column(name="DIN_TODATE")
	private LocalDate dinTodate ;

}