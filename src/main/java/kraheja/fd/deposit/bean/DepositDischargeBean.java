package kraheja.fd.deposit.bean;

import java.util.Map;

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
public class DepositDischargeBean {
	
	private String receiptNumber;
	
	private String breakupType;
	
	private Integer amount;
	
	private String dischargeDate;
	
	private String chequeDate;
	
	private Integer depositAmount;
	
	private String bankCode;
	
	private String cheque;
	
	private String transer;
	
	private Integer depmonths;
	
	private String interestUptoDate;
	
	private Long interestAmountForMarch;

	private Long interestAmountForApril;
	
	private Long tdsAmountForMarch;

	private Long tdsAmountForApril;

	private Long tds;
	
	private Long recoveryAmount;
	
	private String intUptoDate;
	
	private Map<String, String> narrationMap;
	
}
