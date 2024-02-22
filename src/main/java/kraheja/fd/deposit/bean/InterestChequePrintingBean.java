package kraheja.fd.deposit.bean;

import java.math.BigDecimal;

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
 public class InterestChequePrintingBean {
	 
	 String getDinCoy;
	
	 String getDinDepositor;
	
	 BigDecimal getInterest;
	
	 BigDecimal getTds;
	
	 BigDecimal getCheqamt;
	
	 BigDecimal getReceiptyn;
	
	 String getPartyName;
	
	 String getAdrAdline1;
	
	 String getAdrAdline2;
	
	 String getAdrAdline3;
	
	 String getAdrAdline4;
	
	 BigDecimal getIntPaidytd;

	 BigDecimal getaxPaidytd;
}
