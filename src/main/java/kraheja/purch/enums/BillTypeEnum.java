package kraheja.purch.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum BillTypeEnum {
	TRANSPORT("T"),
	
	OTH_CHARGES("C"),
	
	MATERIAL("M"),
	
	LABOUR("W");
	
	@Getter
	private String value;
	
	public static BillTypeEnum getValue(String value) {
		return Arrays.asList(BillTypeEnum.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}
