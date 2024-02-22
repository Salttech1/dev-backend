package kraheja.commons.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the PAYINSLIP database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(indexes = {
		@Index(name = "PSLIP1", columnList = "pslipProprietor ASC, pslipCoy ASC, pslipBank ASC, pslipBooknum ASC, pslipLeafnum ASC, pslipTranser ASC", unique = true)
})
@NamedQuery(name="Payinslip.findAll", query="SELECT p FROM Payinslip p")
public class Payinslip implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PayinslipCK payinslipCK;
	
	@Column(name="PSLIP_AMOUNT")
	private Double pslipAmount;

	@Column(name="PSLIP_DEPDATE")
	private LocalDate pslipDepdate;


	@Column(name="PSLIP_ORIGSITE")
	private String pslipOrigsite;

	@Column(name="PSLIP_SITE")
	private String pslipSite;

	@Column(name="PSLIP_TODAY")
	private LocalDateTime pslipToday;

	@Column(name="PSLIP_USERID")
	private String pslipUserid;

}