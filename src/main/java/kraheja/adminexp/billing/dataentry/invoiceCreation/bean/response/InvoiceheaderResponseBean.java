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

public class InvoiceheaderResponseBean {

	private String actranser;
	private String billtype;
	private String bldgcode;
	private String carno;
	private String chasisno;
	private String coy;
	private String invoiceno;
	private String irnno;
	private String model;
	private String origsite;
	private String partycode;
	private String partytype;
	private String periodfrom;
	private String periodto;
	private String postedyn;
	private String remarks;
	private String site;
	private String subtitle;
	private LocalDateTime today;
	private Double tranamt;
	private String trandate;
	private String userid;
}
