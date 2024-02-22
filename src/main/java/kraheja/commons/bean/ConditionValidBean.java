package kraheja.commons.bean;

import java.util.List;

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
public class ConditionValidBean {

	public String fieldName;
	
	public Integer seqId;
	
	public Integer conditionId;
	
	public String condition;
	
	public List<String> multipleWhere;
	
	public String tableName;
	
	public String subReportQuery;
}
