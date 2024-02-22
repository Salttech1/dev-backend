package kraheja.adminexp.billing.dataentry.adminBill.bean.request;

import java.util.List;

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
public class AdminBillRequestBean {

	Boolean isUpdate;
	AdmbillhRequestBean admbillhRequestBean;
	List<AdmbilldRequestBean> admbilldRequestBean;
	String refPartyType;
	String refPartyCode;
	String gstNo;
}
