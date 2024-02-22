package kraheja.purch.bean.request;

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

public class MaterialPaymentViewRequestBean {

	private Boolean isUnprintedAuthChecked ;
	private String dateFrom ;
	private String dateTo ;
	private String authNumbersFrom ;
	private String authNumbersTo;
}