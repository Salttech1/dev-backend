package kraheja.adminexp.overheads.dataentry.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class OverheadconsExcelRequestBean {

	@JsonProperty("billType")
	String billType;

	@JsonProperty("BillAmount")
	Integer BillAmount;

	@JsonProperty("BillDate")
	String BillDate;
	
	@JsonProperty("BillPeriod")
	Integer BillPeriod;
	
	@JsonProperty("ConsumerNumber")
	String ConsumerNumber;
	
	@JsonProperty("BillNo")
	Long BillNo;
	
	@JsonProperty("TotalPayable")
	Integer TotalPayable;
	
	@JsonProperty("Unitconsumed")
	Integer Unitconsumed;
	
	@JsonProperty("Fromdate")
	String Fromdate ;
	
	@JsonProperty("Todate")
	String Todate ;
	
	@JsonProperty("cgst")
	Integer cgst;
	
	@JsonProperty("sgst")
	Integer sgst;
	
	@JsonProperty("Bilentdate")
	String Bilentdate;
}
