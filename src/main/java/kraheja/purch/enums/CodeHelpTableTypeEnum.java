package kraheja.purch.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CodeHelpTableTypeEnum {
	BUILDING("BUILDING"),
	
	COMPANY("COMPANY"),
	
	PARTY("PARTY"),
	
	WORK("WORK"),
	
	MATERIAL("MATERIAL"),
	
	HSN("HSN MASTER"),
	
	SAC("SAC MASTER"),
	
	WORKMATNARRATION("WORK/MATRIAL NARRATION");
	
	
	@Getter
	private String value;
	
	public static CodeHelpTableTypeEnum getValue(String value) {
		return Arrays.asList(CodeHelpTableTypeEnum.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}
