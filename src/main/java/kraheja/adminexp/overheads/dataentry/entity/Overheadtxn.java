package kraheja.adminexp.overheads.dataentry.entity;

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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "OHTN1", columnList = "ohddConnocode Asc, ohddBillperiod Asc, ohddSupplementarybill Asc", unique = true)
})
public class Overheadtxn implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OverheadtxnCK overheadtxnCK;

	@Column(name="OHDD_ADVANCE")
	private Double ohddAdvance ;

	@Column(name="OHDD_BILENTDATE")
	private LocalDateTime ohddBilentdate ;

	@Column(name="OHDD_BILLAMT")
	private Double ohddBillamt ;

	@Column(name="OHDD_BILLNO")
	private String ohddBillno ;

	@Column(name="OHDD_BILLTRANSER")
	private String ohddBilltranser ;

	@Column(name="OHDD_CGST")
	private Double ohddCgst ;

	@Column(name="OHDD_CGSTPER")
	private Double ohddCgstper ;

	@Column(name="OHDD_CUMAMT")
	private Double ohddCumamt ;

	@Column(name="OHDD_CUSTBILLDATE")
	private LocalDate ohddCustbilldate ;

	@Column(name="OHDD_FROMDATE")
	private LocalDate ohddFromdate ;
	
	@Column(name="OHDD_TODATE")
	private LocalDate ohddTodate ;

	@Column(name="OHDD_HSNCODE")
	private String ohddHsncode ;

	@Column(name="OHDD_IGST")
	private Double ohddIgst ;

	@Column(name="OHDD_IGSTPER")
	private Double ohddIgstper ;

	@Column(name="OHDD_INTREST")
	private Double ohddIntrest ;

	@Column(name="OHDD_NOOFPRINT")
	private Double ohddNoofprint ;

	@Column(name="OHDD_PAYAMT")
	private Double ohddPayamt ;

	@Column(name="OHDD_PAYDATE")
	private LocalDate ohddPaydate ;

	@Column(name="OHDD_PAYREF")
	private String ohddPayref ;

	@Column(name="OHDD_PRINTFLAG")
	private String ohddPrintflag ;

	@Column(name="OHDD_PRVACTPAY")
	private Double ohddPrvactpay ;

	@Column(name="OHDD_PRVADVAMT")
	private Double ohddPrvadvamt ;

	@Column(name="OHDD_REMARKS")
	private String ohddRemarks ;

	@Column(name="OHDD_SGST")
	private Double ohddSgst ;

	@Column(name="OHDD_SGSTPER")
	private Double ohddSgstper ;

	@Column(name="OHDD_SITE")	
	private String ohddSite ;
	
	@Column(name="OHDD_TODAY")
	private LocalDateTime ohddToday ;

	@Column(name="OHDD_TRANSER")
	private String ohddTranser ;

	@Column(name="OHDD_UNITNO")
	private String ohddUnitno ;

	@Column(name="OHDD_USERID")
	private String ohddUserid ;

}
