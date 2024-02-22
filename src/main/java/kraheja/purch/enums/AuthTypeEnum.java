package kraheja.purch.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AuthTypeEnum {
	A("A"),
	
	C("C"),
	
	N("N"),
	
	L("L"),
	
	R("R");
	
	@Getter
	private String value;
	
	public static AuthTypeEnum getValue(String value) {
		return Arrays.asList(AuthTypeEnum.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}
