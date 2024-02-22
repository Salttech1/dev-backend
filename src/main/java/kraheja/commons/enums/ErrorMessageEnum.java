package kraheja.commons.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorMessageEnum{
	NO_DATA_FOUND("No Data Found."),
	
	IN_PROGRESS("IN_PROGRESS");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(ErrorMessageEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

