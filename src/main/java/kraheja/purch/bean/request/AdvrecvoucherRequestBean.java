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

public class AdvrecvoucherRequestBean {

	private Double amount ;
	private String bldgcode ;
	private Double cgstamt ;
	private Double cgstperc ;
	private String coy ;
	private String hsnsaccode ;
	private Double igstamt ;
	private Double igstperc ;
	private String itemdesc ;
	private Double lineno ;
	private String matcertcode ;
	private String origdocdate ;
	private String origdocnum ;
	private String origsite ;
	private String origsys ;
	private String partycode ;
	private String partytype ;
	private String profinvdt ;
	private String profinvno ;
	private String recvoudt ;
	private String recvounum ;
	private Double sgstamt ;
	private Double sgstperc ;
	private String site ;
	private LocalDateTime today ;
	private Double ugstamt ;
	private Double ugstperc ;
	private String userid ;
}