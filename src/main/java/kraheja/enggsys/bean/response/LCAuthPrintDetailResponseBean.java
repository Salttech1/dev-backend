package kraheja.enggsys.bean.response;

import java.util.List;
import java.util.Map;

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

public class LCAuthPrintDetailResponseBean {
	String sessionId;

	String authNumberFrom;
	
	String authNumberTo;
	
	Map<String, String> serList;
	
	List<String> authNumList;
}	