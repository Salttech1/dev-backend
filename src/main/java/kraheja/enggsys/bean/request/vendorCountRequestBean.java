package kraheja.enggsys.bean.request;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class vendorCountRequestBean {
	
	Set<String> logicNoteNum;

	public String getLogicNoteNum() {
		// TODO Auto-generated method stub
		return null;
	}
}