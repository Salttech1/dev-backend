package kraheja.enggsys.bean.request;

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

public class LCAuthPrintRequestBean {

	private Boolean isUnprintedAuths;
	private String authDateFrom;
	private String authDateTo;
	private String authNumberFrom;
	private String authNumberTo;
}

