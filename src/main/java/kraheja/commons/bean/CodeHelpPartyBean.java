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
public class CodeHelpPartyBean {
	
	private String code;
	
	public String name;
	
	public Character type;

	public String pmtacnum;
	
	public String gstno;

	public String address;
}
