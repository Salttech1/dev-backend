package kraheja.purch.bean.response;

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
public class MaterialPaymentStatusEnquiryResponseBean {
	
	private String authNo;
	private String authDate;
	private String type;
	private String building;
	private String material;
	private String party;
	private String payAmnt;
	private String chequeNum;
	private String paidDate;
	private String passedOn;
	private String advAdj;
	
}
