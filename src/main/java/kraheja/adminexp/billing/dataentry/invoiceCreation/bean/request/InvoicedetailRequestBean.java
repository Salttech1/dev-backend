package kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Request bean for the INVOICEDETAIL database table.
**/
@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class InvoicedetailRequestBean {

	private String acmajor;
	private String acminor;
	private Double cgstpayable;
	private Double cgstper;
	private String code;
	private String gstyn;
	private String hsncode;
	private Double igstpayable;
	private Double igstper;
	private String invoiceno;
	private String minortype;
	private String narration;
	private String origsite;
	private Integer quantity;
	private Double rate;
	private Double amount;
	private Double sgstpayable;
	private Double sgstper;
	private String site;
	private Integer srno;
	private LocalDateTime today;
	private Double tranamtgstpayable;
	private String trtype;
	private Double ugstpayable;
	private Double ugstper;
	private String userid;
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}
