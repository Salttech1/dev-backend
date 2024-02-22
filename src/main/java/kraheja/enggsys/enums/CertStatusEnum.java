package kraheja.enggsys.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CertStatusEnum{
	ONE("1"),
	TWO("2"),	
	THREE("3"),	
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(CertStatusEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

