package kraheja.fd.deposit.bean.response;

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
 public class InterestChequePrintingResponseBean {
	 
	 String sessionId;
	 
	 String intUpToDate;

	 String intUpToDatePlusOneMonth;
	 
	 Integer folioReportSeqId;
}
