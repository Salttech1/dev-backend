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
 * The persistent class for the OUTINFRA database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INF1", columnList = "infBldgcode Asc, infOwnerid Asc, infRecnum Asc, infMonth Asc, infNarrcode Asc", unique = true)
})

public class Outinfra implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OutinfraCK outinfraCK;

	@Column(name="INF_ADMINCHARGES")
	private Double infAdmincharges ;

	@Column(name="INF_AMTDUE")
	private Double infAmtdue ;

	@Column(name="INF_AMTINT")
	private Double infAmtint ;

	@Column(name="INF_AMTOS")
	private Double infAmtos ;

	@Column(name="INF_AMTPAID")
	private Double infAmtpaid ;

	@Column(name="INF_CANCELDATE")
	private LocalDate infCanceldate ;

	@Column(name="INF_CANCELLEDYN")
	private String infCancelledyn ;

	@Column(name="INF_CGST")
	private Double infCgst ;

	@Column(name="INF_CGSTPERC")
	private Double infCgstperc ;

	@Column(name="INF_CHARGECODE")
	private String infChargecode ;

	@Column(name="INF_COY")
	private String infCoy ;

	@Column(name="INF_FLATNUM")
	private String infFlatnum ;

	@Column(name="INF_GSTYN")
	private String infGstyn ;

	@Column(name="INF_IGST")
	private Double infIgst ;

	@Column(name="INF_IGSTPERC")
	private Double infIgstperc ;

	@Column(name="INF_KRISHICESS")
	private Double infKrishicess ;

	@Column(name="INF_ORIGINT")
	private Double infOrigint ;

	@Column(name="INF_ORIGSITE")
	private String infOrigsite ;

	@Column(name="INF_RECDATE")
	private LocalDate infRecdate ;

	@Column(name="INF_RECPRINTON")
	private LocalDate infRecprinton ;

	@Column(name="INF_RECPRINTYN")
	private String infRecprintyn ;

	@Column(name="INF_RECREV")
	private Integer infRecrev ;

	@Column(name="INF_RECTYPE")
	private String infRectype ;

	@Column(name="INF_REMARKS")
	private String infRemarks ;

	@Column(name="INF_SERVTAX")
	private Double infServtax ;

	@Column(name="INF_SGSTPERC")
	private Double infSgstperc ;

	@Column(name="INF_SITE")
	private String infSite ;

	@Column(name="INF_SWACHHCESS")
	private Double infSwachhcess ;

	@Column(name="INF_TDS")
	private Double infTds ;

	@Column(name="INF_TODAY")
	private LocalDateTime infToday ;

	@Column(name="INF_USERID")
	private String infUserid ;

	@Column(name="INF_WING")
	private String infWing ;

	@Column(name="INF_SGST")
	private Double infSgst ;

}