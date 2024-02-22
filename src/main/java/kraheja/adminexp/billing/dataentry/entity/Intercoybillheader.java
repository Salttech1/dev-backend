package kraheja.adminexp.billing.dataentry.entity;

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
 * The persistent class for the INTERCOYBILLHEADER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INTERCOYBILLHEADER1", columnList = "icbehGroupinvoiceno Asc, icbehInvoiceno Asc", unique = true)
})

public class Intercoybillheader implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IntercoybillheaderCK intercoybillheaderCK;

	@Column(name="ICBEH_BILLTRANSER")
	private String icbehBilltranser ;

	@Column(name="ICBEH_COY")
	private String icbehCoy ;

	@Column(name="ICBEH_INVOICETRANSER")
	private String icbehInvoicetranser ;

	@Column(name="ICBEH_NARRATION")
	private String icbehNarration ;

	@Column(name="ICBEH_ORIGSITE")
	private String icbehOrigsite ;

	@Column(name="ICBEH_PARTYCODE")
	private String icbehPartycode ;

	@Column(name="ICBEH_PARTYTYPE")
	private String icbehPartytype ;

	@Column(name="ICBEH_PERIODFROM")
	private LocalDate icbehPeriodfrom ;

	@Column(name="ICBEH_PERIODTO")
	private LocalDate icbehPeriodto ;

	@Column(name="ICBEH_POSTEDYN")
	private String icbehPostedyn ;

	@Column(name="ICBEH_PROJCODE")
	private String icbehProjcode ;

	@Column(name="ICBEH_RECBILLPROJCODE")
	private String icbehRecbillprojcode ;

	@Column(name="ICBEH_SITE")
	private String icbehSite ;

	@Column(name="ICBEH_TODAY")
	private LocalDateTime icbehToday ;

	@Column(name="ICBEH_TRANAMT")
	private Double icbehTranamt ;

	@Column(name="ICBEH_TRANDATE")
	private LocalDate icbehTrandate ;

	@Column(name="ICBEH_USERID")
	private String icbehUserid ;

}