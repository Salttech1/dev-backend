package kraheja.adminexp.billing.dataentry.intercompany.bean.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddInterCompanyData {
	private String acmajor;
	private String majorName;
	private String acminor;
	private String minorName;
	private String acMinType;
	private Double acAmount;
	private Map<String, Double> localCompanyData;
}
