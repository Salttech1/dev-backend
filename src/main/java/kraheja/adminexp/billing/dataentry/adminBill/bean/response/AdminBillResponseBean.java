package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

import java.util.List;

import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
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
public class AdminBillResponseBean {

	
	private String gstNo;
	private String stateCode;
	private String stateName;
	private Admbillh admbillh;
	private List<Admbilld> admbilld;
	private Double totPaidAdvance;
	private Double adjustedAdvn;
	private String docParType;
	private String docParCode;
	private String bldgDesc;
	private String refPartyDesc;
	
}
