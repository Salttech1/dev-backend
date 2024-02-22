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
 * The persistent class for the PAYMAST database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PAYMAST_X", columnList = "pmstMasttype Asc, pmstMastcode Asc", unique = true)
})

public class Paymast implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PaymastCK paymastCK;

	@Column(name="PMST_MASTNAME")
	private String pmstMastname ;

	@Column(name="PMST_MASTPARA1")
	private Double pmstMastpara1 ;

	@Column(name="PMST_MASTPARA2")
	private Double pmstMastpara2 ;

	@Column(name="PMST_MASTPARA3")
	private LocalDate pmstMastpara3 ;

	@Column(name="PMST_ORIGSITE")
	private String pmstOrigsite ;

	@Column(name="PMST_SITE")
	private String pmstSite ;

	@Column(name="PMST_TODAY")
	private LocalDateTime pmstToday ;

	@Column(name="PMST_USERID")
	private String pmstUserid ;

}