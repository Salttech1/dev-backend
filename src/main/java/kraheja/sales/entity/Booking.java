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
 * The persistent class for the BOOKING database table.
 * 
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "BOOK1", columnList = "bookBldgcode ASC, bookWing ASC, bookFlatnum ASC, bookOwnerid ASC", unique = true),
		@Index(name = "BOOK2", columnList = "bookOwnerid ASC, bookbldgcode ASC, bookWing ASC, bookFlatnum ASC", unique = true)
})


public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BookingCK bookingCK;

	@Column(name="BOOK_ACCOMTYPE")
	private String bookAccomtype;

	@Column(name="BOOK_AGPRICE")
	private Double bookAgprice;

	@Column(name="BOOK_AMTOS")
	private Double bookAmtos;

	@Column(name="BOOK_AMTREC")
	private Double bookAmtrec;

	@Column(name="BOOK_AREA")
	private String bookArea;

	@Column(name="BOOK_BOOKEDBY")
	private String bookBookedby;

	@Column(name="BOOK_BROKENT")
	private Double bookBrokent;

	@Column(name="BOOK_BROKER")
	private String bookBroker;

	@Column(name="BOOK_BROKOS")
	private Double bookBrokos;

	@Column(name="BOOK_BROKPAID")
	private Double bookBrokpaid;

	@Column(name="BOOK_BROKTDSAMD")
	private Double bookBroktdsamd;

	@Column(name="BOOK_BROKTDSAMT")
	private Double bookBroktdsamt;

	@Column(name="BOOK_BROKTDSPER")
	private Double bookBroktdsper;

	@Column(name="BOOK_BROKTRANSER")
	private String bookBroktranser;

	@Column(name="BOOK_CANCELLED")
	private LocalDate bookCancelled;

	@Column(name="BOOK_COMMUNITY")
	private String bookCommunity;

	@Column(name="BOOK_CONTRACTON")
	private LocalDate bookContracton;

	@Column(name="BOOK_CUSTOMERCOY")
	private String bookCustomercoy;

	@Column(name="BOOK_CUSTTYPE")
	private String bookCusttype;

	@Column(name="BOOK_DATE")
	private LocalDate bookDate;

	@Column(name="BOOK_DESIGNATION")
	private String bookDesignation;

	@Column(name="BOOK_DISCOUNT")
	private Double bookDiscount;

	@Column(name="BOOK_FIRSTVISITDATE")
	private LocalDate bookFirstvisitdate;
	
	@Column(name="BOOK_FIRSTVISITEXEC")
	private String bookFirstvisitexec;

	@Column(name="BOOK_FLOOR")
	private String bookFloor;

	@Column(name="BOOK_GSTNO")
	private String bookGstno;

	@Column(name="BOOK_HO2OWNER")
	private LocalDate bookHo2owner;

	@Column(name="BOOK_JOBPROFILE")
	private String bookJobprofile;

	@Column(name="BOOK_LEADDATE")
	private LocalDate bookLeaddate;

	@Column(name="BOOK_LEASEDTO")
	private String bookLeasedto;

	@Column(name="BOOK_LEASEREF")
	private String bookLeaseref;

	@Column(name="BOOK_LEDBY")
	private String bookLedby;

	@Column(name="BOOK_MAINTRATE")
	private Double bookMaintrate;

	@Column(name="BOOK_MPAIDDATE")
	private LocalDate bookMpaiddate;

	@Column(name="BOOK_MPAIDREF")
	private String bookMpaidref;

	@Column(name="BOOK_MPAIDYYMM")
	private String bookMpaidyymm;

	@Column(name="BOOK_ORIGSITE")
	private String bookOrigsite;

	@Column(name="BOOK_OVERON")
	private LocalDate bookOveron;

	@Column(name="BOOK_PANUM")
	private String bookPanum;

	@Column(name="BOOK_POACODE")
	private String bookPoacode;

	@Column(name="BOOK_POANAME")
	private String bookPoaname;

	@Column(name="BOOK_PSIND")
	private String bookPsind;

	@Column(name="BOOK_PUBLIC")
	private String bookPublic;

	@Column(name="BOOK_REGFEES")
	private Double bookRegfees;

	@Column(name="BOOK_REGNO")
	private String bookRegno;

	@Column(name="BOOK_REGPRICE")
	private Double bookRegprice;

	@Column(name="BOOK_REMARKS")
	private String bookRemarks;

	@Column(name="BOOK_SALESTATUS")
	private String bookSalestatus;

	@Column(name="BOOK_SALETYPE")
	private String bookSaletype;

	@Column(name="BOOK_SCHEDULEDPOSSESSION")
	private String bookScheduledpossession;

	@Column(name="BOOK_SERIALNUM")
	private String bookSerialnum;

	@Column(name="BOOK_SITE")
	private String bookSite;

	@Column(name="BOOK_SOI")
	private String bookSoi;

	@Column(name="BOOK_TODAY")
	private LocalDateTime bookToday;

	@Column(name="BOOK_USERID")
	private String bookUserid;

	@Column(name="BOOK_VALIDTILL")
	private LocalDate bookValidtill;

}