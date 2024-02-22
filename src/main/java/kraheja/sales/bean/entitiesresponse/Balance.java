package kraheja.sales.bean.entitiesresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
	private double amtPaid;
	private double amtint;
	private double cgst;
	private double adminharges;
	private String month;
	private String ownerid;
	private double sgst;
	private double igst;
	private double tds;
}