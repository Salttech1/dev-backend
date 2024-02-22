package kraheja.adminexp.billing.dataentry.adminBill.bean.response;

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
public class HsnResponse {

	private String hsnCode;
	private String hsnDesc;
	private Double igstPerc;
	private	Double sgstPerc;
	private Double cgstPerc;
	private Double ugstPerc;
}
