package kraheja.purch.bean.response;

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

public class PbillvatResponseBean {

	private Double amount ;
	private Double cgstamt ;
	private Double cgstperc ;
	private Double discountamt ;
	private Double fotoamt ;
	private String hsncode ;
	private String hsndesc ;
	private Double igstamt ;
	private Double igstperc ;
	private String itemcode ;
	private Integer lineno ;
	private String matcode ;
	private String matgroup ;
	private String origsite ;
	private Double quantity ;
	private Double rate ;
	private String ser ;
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
	private String description;
}