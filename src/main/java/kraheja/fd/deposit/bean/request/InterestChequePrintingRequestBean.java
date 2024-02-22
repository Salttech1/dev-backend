package kraheja.fd.deposit.bean.request;

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
 public class InterestChequePrintingRequestBean {
	 
	String companyCode; 
	String depFrom;
	String depTo;
	String chequeDate; 
	String userId;
	String chequeNum; 
	Boolean isTestPrint;
}
