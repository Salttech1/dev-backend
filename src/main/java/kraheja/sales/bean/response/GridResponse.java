package kraheja.sales.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GridResponse {
	private String monthName;
	private String narrationCode;
	private String narration;
	private double auxiPaid;
	private double intPaid;
	private double admin;
	private double cgst;
	private double sgst;
	private double igst;
	private double cgstPercent;
	private double sgstPercent;
	private double igstPercent;
	private double tds;
}
