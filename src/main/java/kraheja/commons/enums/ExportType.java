package kraheja.commons.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExportType {

	PDF("PDF"),
	EXCEL_REPORT_DATA_FORMAT("EXCEL_REPORT_DATA_FORMAT"),
	EXCEL_TEMPLATE_FORMAT("EXCEL_TEMPLATE_FORMAT"),
	EXCEL("EXCEL"), // only used to specify background report type
	WORD("WORD");
	
	@Getter
	private String value;
	
	public static ExportType getValue(String value) {
		return Arrays.asList(ExportType.values()).stream().filter(data -> data.value.equalsIgnoreCase(value)).findAny().get();
	}
}