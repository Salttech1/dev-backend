package kraheja.adminexp.billing.dataentry.intercompany.bean.db;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//kraheja.adminexp.billing.dataentry.intercompany.bean.db.InvoiceDateRetriveDBResponse(h.icbehTrandate, h.icbehPeriodfrom, h.icbehPeriodto) from Intercoybillheader where h.intercoybillheaderCK.icbehGroupinvoiceno= :invoiceGroup
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceDateRetriveDBResponse {
	private LocalDate invoiceDate;
	private LocalDate invoiceFromDate;
	private LocalDate invoiceToDate;
	private String narration;
	private String coyCode;
	private String projectCode;
}
