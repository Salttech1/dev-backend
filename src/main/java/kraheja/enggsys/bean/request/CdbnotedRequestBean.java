package kraheja.enggsys.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class CdbnotedRequestBean {

	private Double amount ;
	private Double cgstamt ;
	private Double cgstperc ;
	private String dbnoteser ;
	private Double discountamt ;
	private Double fotoamt ;
	private Double igstamt ;
	private Double igstperc ;
	private Integer lineno ;
	private String origsite ;
	private Double quantity ;
	private Double rate ;
	private String saccode ;
	private String sacdesc ;
	private Double sgstamt ;
	private Double sgstperc ;
	private String site ;
	private Double taxableamt ;
	private LocalDateTime today ;
	private Double ugstamt ;
	private Double ugstperc ;
	private String uom ;
	private String userid ;
	private Double vatamount ;
	private Double vatpercent ;
}