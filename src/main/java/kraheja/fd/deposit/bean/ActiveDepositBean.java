package kraheja.fd.deposit.bean;

import java.time.LocalDate;
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
public class ActiveDepositBean {
	
	private String depDepositor;
	
	private String depReceiptnum;
	
	private String depStaffyn;
	
	private Float depDepamount;
	
	private LocalDateTime depDepdate;
	
	private Double depIntrate;
	
	private Double depIntfreq;
	
	private LocalDateTime depMatdate;
	
	private String depProprietor;
	
	private LocalDate maxIntupto;
	
	private String depCoy;
	
	private Double depAccint;
	
	private Double depIntpaidytd;
	
	private Double depTaxpaidytd;
	
	private Double depTds;
	
	private Double deptrIntpaidytd;

	private Double deptrTaxpaidytd;
	
	private Double deptrAccint;
	
	private Double deptrTds;
	
	private String deptrTds15hyn;
	
	private String deptrTds15gyn;
	
	private String deptrTaxalwaysyn;
}
