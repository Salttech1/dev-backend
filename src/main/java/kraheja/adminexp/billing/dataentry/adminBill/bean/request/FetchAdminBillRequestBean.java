package kraheja.adminexp.billing.dataentry.adminBill.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FetchAdminBillRequestBean {
	String companyCode;
	String partyType;
	String partyCode;
	String buildingCode;
	String suppBillNo;
}
