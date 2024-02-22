package kraheja.fd.deposit.bean.request;

import java.util.List;

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
public class NeftReportRequestBean {
	
	private String companyCode;
	private String fromDate;
	private String toDate;
	private String companyBankAc;
	private String chequeNum;
	private String chequeAlpha;
	private String chequeDate;
	private String bankString;
	private List<String> depositorsList;

}
