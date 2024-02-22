package kraheja.sales.bean.request;

import java.util.Set;

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

public class InfraDefaultersListRequestBean {
	String custType;
	Set<String> bldgCode;
	String cutOffDate;
	String osAmtYN;
	String summaryYN;
	String chargeCode;
	Double osAmt;
	String sessionId;
}