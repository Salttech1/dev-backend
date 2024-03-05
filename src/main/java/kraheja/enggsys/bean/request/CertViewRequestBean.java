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

public class CertViewRequestBean {

	
	private Boolean isUnprintedCertChecked ;
	private String dateFrom ;
	private String dateTo ;
	private String CertNumbersFrom ;
	private String CertNumbersTo;
}