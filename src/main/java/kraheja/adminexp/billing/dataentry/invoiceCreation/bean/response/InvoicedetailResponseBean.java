package kraheja.adminexp.billing.dataentry.invoiceCreation.bean.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class InvoicedetailResponseBean {

	private String acmajor ;
	private String acminor ;
	private Integer cgstpayable ;
	private Integer cgstper ;
	private String code ;
	private String gstyn ;
	private String hsncode ;
	private Integer igstpayable ;
	private Integer igstper ;
	private String invoiceno ;
	private String minortype ;
	private String narration ;
	private String origsite ;
	private Integer quantity ;
	private Integer rate ;
	private Integer sgstpayable ;
	private Integer sgstper ;
	private String site ;
	private Integer srno ;
	private LocalDateTime today ;
	private Integer tranamtgstpayable ;
	private String trtype ;
	private Integer ugstpayable ;
	private Integer ugstper ;
	private String userid ;
}