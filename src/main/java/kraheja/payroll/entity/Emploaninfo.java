package kraheja.payroll.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
 * The persistent class for the EMPLOANINFO database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "EMPLOANINFO", columnList = "eloanEmpcode Asc, eloanLoancode Asc, eloanIssuedate Asc", unique = true)
})

public class Emploaninfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmploaninfoCK emploaninfoCK;

	@Column(name="ELOAN_AUTHBY")
	private String eloanAuthby ;

	@Column(name="ELOAN_DEDSTART")
	private String eloanDedstart ;

	@Column(name="ELOAN_INSTAMT")
	private Double eloanInstamt ;

	@Column(name="ELOAN_INTPERC")
	private Integer eloanIntperc ;

	@Column(name="ELOAN_INTYN")
	private String eloanIntyn ;

	@Column(name="ELOAN_IPADDRESS")
	private String eloanIpaddress ;

	@Column(name="ELOAN_LOANAMT")
	private Double eloanLoanamt ;

	@Column(name="ELOAN_MACHINENAME")
	private String eloanMachinename ;

	@Column(name="ELOAN_MODIFIEDON")
	private LocalDateTime eloanModifiedon ;

	@Column(name="ELOAN_MODULE")
	private String eloanModule ;

	@Column(name="ELOAN_OSAMT")
	private Double eloanOsamt ;

	@Column(name="ELOAN_PAIDAMT")
	private Double eloanPaidamt ;

	@Column(name="ELOAN_PERQINTPERC")
	private Integer eloanPerqintperc ;

	@Column(name="ELOAN_REASON")
	private String eloanReason ;

	@Column(name="ELOAN_REFDATE")
	private LocalDate eloanRefdate ;

	@Column(name="ELOAN_REFNO")
	private String eloanRefno ;

	@Column(name="ELOAN_SITE")
	private String eloanSite ;

	@Column(name="ELOAN_USERID")
	private String eloanUserid ;

}