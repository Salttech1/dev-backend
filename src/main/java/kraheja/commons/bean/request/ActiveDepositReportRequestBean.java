package kraheja.commons.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ActiveDepositReportRequestBean {
	
	String txtCompanyCd;
	
	String companyName;

	String reportOption;

	String h1;

	String h2;

	String h3;

	@JsonProperty("DtpFromDate")
	String DtpFromDate;

	@JsonProperty("DtpToDate")
	String DtpToDate;

	String formname;

	String ChkGroup1;

	String ChkGroup2;
	
	String exportType;

}
