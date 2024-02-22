package kraheja.commons.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PrintStatusEnum{

	ORIGINAL("ORIGINAL"),
	REPRINT("REPRINT #");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(PrintStatusEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

