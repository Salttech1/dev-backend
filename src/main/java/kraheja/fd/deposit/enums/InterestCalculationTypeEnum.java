package kraheja.fd.deposit.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum InterestCalculationTypeEnum {
	ONLY_NRI("Only NRI"),
	
	INDIVIDUAL_AND_COMPANY("Individual And Company"),
	
	ALL_DEPOSITORS("All Depositors");
	
	@Getter
	private String value;
	
	public static InterestCalculationTypeEnum getValue(String value) {
		return Arrays.asList(InterestCalculationTypeEnum.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
	
	public static List<String> getAllValues() {
		return Arrays.asList(InterestCalculationTypeEnum.values()).stream().map(data -> data.value).collect(Collectors.toList());
	}
}
