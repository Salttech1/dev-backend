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
public class TdsRequest {

	String partyType;
	String partyCode;
	LocalDate suppBillDate;
}