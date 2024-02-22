package kraheja.commons.bean.response;

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

public class ReportJobsTransactionResponseBean {

	private String createdBy ;
	private String createdDate ;
	private Integer id ;
	private String reportLocation ;
	private Long reportMasterId ;
	private String status ;
	private String reportName;
	private String updatedDate;
	private String exportType;
}