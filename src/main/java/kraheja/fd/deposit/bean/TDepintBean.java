package kraheja.fd.deposit.bean;


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

public class TDepintBean {

	private String bankcode ;
	private String canceldate ;
	private String chqnum ;
	private String oldChequeNum;
	private String coy ;
	private String depositor ;
	private String fromdate ;
	private Double interest ;
	private String intfrom ;
	private String intupto ;
	private Double newprin ;
	private String origsite ;
	private String proprietor ;
	private String receiptnum ;
	private Double sessid ;
	private String site ;
	private Double staffallow ;
	private String taxalwaysyn ;
	private Double tds ;
	private String tds15hyn ;
	private String todate ;
	private String today ;
	private String transer ;
	private String userid ;
	
	//
	private Long totalInterestAmount;
	private Long totalInterestAmountForStaff;
	private Long totalDepositAmount;
}