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

public class InvpartymasterResponseBean {

	private String acmajor ;
	private String billnote ;
	private String billprocesstype ;
	private String billtype ;
	private String coycode ;
	private String emailid ;
	private String hsnsac ;
	private String item_bill_code ;
	private String item_bill_desc ;
	private String narration ;
	private String partycode ;
	private String partytype ;
	private Integer qty ;
	private Double rate ;
	private String servicenature ;
	private String signauth ;
	private String site ;
	private String subject ;
	private LocalDateTime today ;
	private String user ;
}
