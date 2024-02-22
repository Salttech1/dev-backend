package kraheja.purch.bean.request;

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

public class PurchBillOutstandingRequestBean {
	Set<String> coyCode;
	Set<String> bldgCode;
	Set<String> matGroup;
	Set<String> partyCode;
	String suppBillFromDate;
	String suppBillToDate;
	String sessionId;
}