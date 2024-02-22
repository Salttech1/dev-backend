package kraheja.commons.bean;

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
public class ParameterValidBean {
	
	public String fieldName;
	
	public String rptKeyName;
	
	public String placeholder;

	public String fieldType;
	
	public String formatter;

	public Integer sortOrder;
}
