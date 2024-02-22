package kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdmAdvancePaymentFetchResponse {

	Admadvance1 admAdvance;
	String partyName;
	String companyName;
	String buildingName;
	Double payAmount;
}
