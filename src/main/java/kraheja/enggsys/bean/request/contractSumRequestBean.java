package kraheja.enggsys.bean.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class contractSumRequestBean {
	
	Set<String> bldgCode;
	String asOnDate;
	String sessionId;
	public Object getBldgCode() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getasOnDate() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setasOnDate(String format) {
		// TODO Auto-generated method stub
		
	}
}