package kraheja.purch.bean.request;

import java.time.LocalDateTime;

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

public class DbnotedRequestBean {

	private Double amount ;
	private String dbnoteser ;
	private Double dequantity ;
	private Double derate ;
	private String deuom ;
	private String itemcode ;
	private String length ;
	private Double lineno ;
	private String matcode ;
	private String matgroup ;
	private Double number ;
	private String origsite ;
	private String partycode ;
	private Double quantity ;
	private Double rate ;
	private String site ;
	private String suppbillno ;
	private LocalDateTime today ;
	private String uom ;
	private String userid ;
}