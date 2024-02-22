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

public class Auth_HRequestBean {
	private Double advadjpast ;
	private Double advadjust ;
	private Double advgranted ;
	private Double authamount ;
	private String authdate ;
	private String authnum ;
	private Double authquanty ;
	private String authstatus ;
	private String authtype ;
	private Double bldgauthno ;
	private String bldgcode ;
	private String coy ;
	private String debsocyn ;
	private String description ;
	private String expenstype ;
	private String lotno ;
	private Double matbldauth ;
	private String matcode ;
	private String matgroup ;
	private String misbldg ;
	private String misproject ;
	private Double opradvadj ;
	private Double opradvance ;
	private Double oprauthamt ;
	private Double oprauthqty ;
	private Double oprrelret ;
	private Double oprretain ;
	private Double oprretentionadj ;
	private String origsite ;
	private Double partyauth ;
	private String partycode ;
	private String partycode2 ;
	private String partytype ;
	private String passedon ;
	private Double payamount ;
	private String paydate ;
	private String payref ;
	private String paytender ;
	private Double perdone ;
	private String preparedby ;
	private Double printed ;
	private String printedon ;
	private String project ;
	private String prop ;
	private String property ;
	private Double prvadvadj ;
	private Double prvadvance ;
	private Double prvauthamt ;
	private String prvauthno ;
	private Double prvauthqty ;
	private String prvdate ;
	private Double prvrelret ;
	private Double prvretamt ;
	private Double prvretentionadj ;
	private String prvtype ;
	private Double relretain ;
	private String remarks ;
	private Double retained ;
	private Double retentionadj ;
	private String site ;
	private Double tdsamount ;
	private LocalDateTime today ;
	private String transer ;
	private String userid ;
	
	//total
	private Double totalBillamount;
	private Double totalQuantity;
	private Double totalTds;
	private Double totalAdvadjust;
	private Double totalRetention;
	private Double totalPayamount;
	private Double totalDebitamount;
	private Double totalRetAdvadjust;
	private Double totalRelretamount;

}