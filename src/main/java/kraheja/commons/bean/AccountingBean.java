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
public class AccountingBean {

	private String minType;
	
	private String minCode;
	
	private String partyType;
	
	private String partyCode;
	
	private String project;
	
	private String Acminor;
	
	private String acMajor;
	
	private String xAcMajor;
	
	//CASHFLOW
	private String cfGroup;
	
	private String cfCode;
}
