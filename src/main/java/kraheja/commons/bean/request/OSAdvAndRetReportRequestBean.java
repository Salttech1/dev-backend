package kraheja.commons.bean.request;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OSAdvAndRetReportRequestBean {
	
	List<String> bldgCode;
	
	List<String> matCode;

	List<String> partyCode;

	List<String> coy;

	List<String> region;

	List<String> exclude;
	
	Boolean radioButton;
	
	String formname;
	
	String exportType;
}
