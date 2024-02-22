package kraheja.sales.entity;

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
 * The persistent class for the LOANHISTORY database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		@Index(name = "PK_LOANHISTORY", columnList = "lhistLoanco ASC, lhistLoannum ASC, lhistLoanclosedate ASC, lhistOwnerid ASC", unique = true)
})

public class Loanhistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LoanhistoryCK loanhistoryCK;

	@Column(name="LHIST_LOANAMT")
	private Double lhistLoanamt;

	@Column(name="LHIST_LOANBRANCH")
	private String lhistLoanbranch;

	@Column(name="LHIST_LOANPAID")
	private Double lhistLoanpaid;

	@Column(name="LHIST_NOCDT")
	private LocalDate lhistNocdt;

	@Column(name="LHIST_NOCRCVDDATE")
	private LocalDate lhistNocrcvddate;

	@Column(name="LHIST_NOCTYPE")
	private String lhistNoctype;

	@Column(name="LHIST_ORIGSITE")
	private String lhistOrigsite;

	@Column(name="LHIST_SITE")
	private String lhistSite;

	@Column(name="LHIST_TODAY")
	private LocalDateTime lhistToday;

	@Column(name="LHIST_USERID")
	private String lhistUserid;

}