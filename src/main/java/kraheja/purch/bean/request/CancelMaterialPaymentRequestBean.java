package kraheja.purch.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class CancelMaterialPaymentRequestBean {

	private String authtype ;
	private String authnum ;
	private String authdate ;
	private Double authamount ;
	private String passedon ;
	private String transer ;
	private String payref ;
	private String paydate ;
	private Double payamount ;
	private String authstatus ;
	private String partycode ;
	private String bldgCode;
	private String matGroup;
}
