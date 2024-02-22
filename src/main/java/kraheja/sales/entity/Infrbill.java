package kraheja.sales.entity;

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
 * The persistent class for the INFRBILL database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INFR2", columnList = "infr_ownerid Asc, infrMonth Asc, infrBillnum Asc", unique = true)
})

public class Infrbill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InfrbillCK infrbillCK;

	@Column(name="INFR_ADMINCHARGES")
	private Double infrAdmincharges ;

	@Column(name="INFR_AMTOS")
	private Double infrAmtos ;

	@Column(name="INFR_AMTPAID")
	private Double infrAmtpaid ;

	@Column(name="INFR_ARREARS")
	private Double infrArrears ;

	@Column(name="INFR_BILLAMT")
	private Double infrBillamt ;

	@Column(name="INFR_BILLDATE")
	private LocalDate infrBilldate ;

	@Column(name="INFR_BILLPRINTON")
	private LocalDate infrBillprinton ;

	@Column(name="INFR_BILLPRINTYN")
	private String infrBillprintyn ;

	@Column(name="INFR_BILLREVNUM")
	private Integer infrBillrevnum ;

	@Column(name="INFR_BILLTYPE")
	private String infrBilltype ;

	@Column(name="INFR_BLDGCODE")
	private String infrBldgcode ;

	@Column(name="INFR_CANCELDATE")
	private LocalDate infrCanceldate ;

	@Column(name="INFR_CANCELLEDYN")
	private String infrCancelledyn ;

	@Column(name="INFR_CGST")
	private Double infrCgst ;

	@Column(name="INFR_CGSTPERC")
	private Double infrCgstperc ;

	@Column(name="INFR_CHARGECODE")
	private String infrChargecode ;

	@Column(name="INFR_FLATNUM")
	private String infrFlatnum ;

	@Column(name="INFR_FROMDATE")
	private LocalDate infrFromdate ;

	@Column(name="INFR_GSTYN")
	private String infrGstyn ;

	@Column(name="INFR_IGST")
	private Double infrIgst ;

	@Column(name="INFR_IGSTPERC")
	private Double infrIgstperc ;

	@Column(name="INFR_INTARREARS")
	private Double infrIntarrears ;

	@Column(name="INFR_INTEREST")
	private Double infrInterest ;

	@Column(name="INFR_INTFROM")
	private LocalDate infrIntfrom ;

	@Column(name="INFR_INVOICENO")
	private String infrInvoiceno ;

	@Column(name="INFR_IRNNO")
	private String infrIrnno ;

	@Column(name="INFR_KRISHICESS")
	private Double infrKrishicess ;

	@Column(name="INFR_ORIGSITE")
	private String infrOrigsite ;

	@Column(name="INFR_RATE")
	private Double infrRate ;

	@Column(name="INFR_REMDATE")
	private LocalDate infrRemdate ;

	@Column(name="INFR_REMINDERS")
	private Integer infrReminders ;

	@Column(name="INFR_SERVTAX")
	private Double infrServtax ;

	@Column(name="INFR_SGST")
	private Double infrSgst ;

	@Column(name="INFR_SGSTPERC")
	private Double infrSgstperc ;

	@Column(name="INFR_SITE")
	private String infrSite ;

	@Column(name="INFR_SWACHHCESS")
	private Double infrSwachhcess ;

	@Column(name="INFR_TODATE")
	private LocalDate infrTodate ;

	@Column(name="INFR_TODAY")
	private LocalDateTime infrToday ;

	@Column(name="INFR_USERID")
	private String infrUserid ;

	@Column(name="INFR_WING")
	private String infrWing ;

}