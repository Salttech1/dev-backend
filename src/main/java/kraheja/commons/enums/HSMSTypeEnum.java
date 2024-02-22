package kraheja.commons.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum HSMSTypeEnum {
	HSN("HSN"),
	
	SAC("SAC");
	
	@Getter
	private String value;
	
	public static HSMSTypeEnum getValue(String value) {
		return Arrays.asList(HSMSTypeEnum.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}
