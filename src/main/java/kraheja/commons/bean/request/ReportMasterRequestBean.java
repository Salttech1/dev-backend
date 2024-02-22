package kraheja.commons.bean.request;

import java.util.Map;

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
public class ReportMasterRequestBean {
	
	private String exportType;
	
	public String name;

	public String postFilter;

	public String reportMetaData;
    
	public String reportType;
	
	public String rptPath;
	
	public Map<String, Object> reportParameters;
	
	public Integer seqId;
	
	public Integer conditionId;
	
	public Boolean userid;
	
	public Boolean isPrint;
}
