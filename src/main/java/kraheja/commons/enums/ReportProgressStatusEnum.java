package kraheja.commons.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReportProgressStatusEnum{

	COMPLETED("COMPLETED"),
	
	IN_PROGRESS("IN_PROGRESS"),
	
	NO_DATA_FOUND("NO_DATA_FOUND"),
	
	DD_CHARGES("FAILED");
	
	@Getter
	private String value;

	public static Set<String> getAllValues() {
		return Arrays.asList(ReportProgressStatusEnum.values()).stream().map(data -> data.value).collect(Collectors.toSet());
	}
}

