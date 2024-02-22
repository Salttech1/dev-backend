package kraheja.sales.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class OutinfraRequestBean {

	private Double admincharges ;
	private Double amtdue ;
	private Double amtint ;
	private Double amtos ;
	private Double amtpaid ;
	private String bldgcode ;
	private String canceldate ;
	private String cancelledyn ;
	private Double cgst ;
	private Double cgstperc ;
	private String chargecode ;
	private String coy ;
	private String flatnum ;
	private String gstyn ;
	private Double igst ;
	private Double igstperc ;
	private Double krishicess ;
	private String month ;
	private String narrcode ;
	private Double origint ;
	private String origsite ;
	private String ownerid ;
	private String recdate ;
	private String recnum ;
	private String recprinton ;
	private String recprintyn ;
	private Integer recrev ;
	private String rectype ;
	private String remarks ;
	private Double servtax ;
	private Double sgst ;
	private Double sgstperc ;
	private String site ;
	private Double swachhcess ;
	private Double tds ;
	private LocalDateTime today ;
	private String userid ;
	private String wing ;
}