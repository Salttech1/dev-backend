package kraheja.fd.deposit.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DischargeBreakUp {
	PRINCIPLE_AMOUNT("Principle Amount"),
	
	INTEREST_AMOUNT("Interest Amount"),
	
	RECOVERY_AMOUNT("Recovery Amount"),
	
	TDS_AMOUNT("TDS Amount"),
	
	DD_CHARGES("DD Charges"),
	
	BROKERAGE_AMOUNT("Brokerage Amount");
	
	@Getter
	private String value;
	
	public static DischargeBreakUp getValue(String value) {
		return Arrays.asList(DischargeBreakUp.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}
