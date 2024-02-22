package kraheja.commons.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EntityClassEnum{
	NSER("#NSER");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(EntityClassEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

