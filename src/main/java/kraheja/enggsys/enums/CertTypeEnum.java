package kraheja.enggsys.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CertTypeEnum{
	A("A"),
	B("B"),	
	F("F"),	
	I("I"),
	L("L"),
	M("M"),
	R("R"),
	S("S");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(CertTypeEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

