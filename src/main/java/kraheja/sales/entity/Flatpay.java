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
 * The persistent class for the FLATPAY database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(indexes = {
		//@Index(name = "FPAY1", columnList = "fpayBldgcode ASC, fpayFlatnum ASC, fpayOwnerid ASC, fpayDuedate ASC, fpayPaiddate ASC, fpayNarrative ASC", unique = true)
		@Index(name = "FPAY1", columnList = "fpayBldgcode ASC, fpayFlatnum ASC, fpayOwnerid ASC, fpayDuedate ASC, fpayNarrative ASC", unique = true)
})

public class Flatpay implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FlatpayCK flatpayCK;
	
	@Column(name="FPAY_DUEAMOUNT")
	private Double fpayDueamount;

	@Column(name="FPAY_ORIGSITE")
	private String fpayOrigsite;

	@Column(name="FPAY_PAIDAMOUNT")
	private Double fpayPaidamount;

	@Column(name="FPAY_PAIDREF")
	private String fpayPaidref;

	@Column(name="FPAY_SITE")
	private String fpaySite;

	@Column(name="FPAY_TODAY")
	private LocalDateTime fpayToday;

	@Column(name="FPAY_USERID")
	private String fpayUserid;

	@Column(name="FPAY_WING")
	private String fpayWing;
	
	@Column(name="FPAY_PAIDDATE")
	private LocalDate fpayPaiddate;
	

}