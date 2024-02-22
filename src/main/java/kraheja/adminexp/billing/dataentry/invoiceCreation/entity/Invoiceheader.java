package kraheja.adminexp.billing.dataentry.invoiceCreation.entity;

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
 * The persistent class for the INVOICEHEADER database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "INVOICEHEADER1", columnList = "invhInvoiceno Asc", unique = true)
})

public class Invoiceheader implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvoiceheaderCK invoiceheaderCK;

	@Column(name="INVH_ACTRANSER")
	private String invhActranser ;

	@Column(name="INVH_BILLTYPE")
	private String invhBilltype ;

	@Column(name="INVH_BLDGCODE")
	private String invhBldgcode ;

	@Column(name="INVH_CARNO")
	private String invhCarno ;

	@Column(name="INVH_CHASISNO")
	private String invhChasisno ;

	@Column(name="INVH_COY")
	private String invhCoy ;

	@Column(name="INVH_IRNNO")
	private String invhIrnno ;

	@Column(name="INVH_MODEL")
	private String invhModel ;

	@Column(name="INVH_ORIGSITE")
	private String invhOrigsite ;

	@Column(name="INVH_PARTYCODE")
	private String invhPartycode ;

	@Column(name="INVH_PARTYTYPE")
	private String invhPartytype ;

	@Column(name="INVH_PERIODFROM")
	private LocalDate invhPeriodfrom ;

	@Column(name="INVH_PERIODTO")
	private LocalDate invhPeriodto ;

	@Column(name="INVH_POSTEDYN")
	private String invhPostedyn ;

	@Column(name="INVH_REMARKS")
	private String invhRemarks ;

	@Column(name="INVH_SITE")
	private String invhSite ;

	@Column(name="INVH_SUBTITLE")
	private String invhSubtitle ;

	@Column(name="INVH_TODAY")
	private LocalDateTime invhToday ;

	@Column(name="INVH_TRANAMT")
	private Double invhTranamt ;

	@Column(name="INVH_TRANDATE")
	private LocalDate invhTrandate ;
	
	@Column(name="INVH_QRCODE")
	private String invhQrcode;

	@Column(name="INVH_USERID")
	private String invhUserid ;

}
