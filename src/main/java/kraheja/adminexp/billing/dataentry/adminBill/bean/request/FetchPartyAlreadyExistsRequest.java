package kraheja.adminexp.billing.dataentry.adminBill.bean.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FetchPartyAlreadyExistsRequest {
	String companyCode;
	String partyType;
	String partyCode;
	String buildingCode;
	String acMajor;
	LocalDate fromDate;
	LocalDate toDate;
}
