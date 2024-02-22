package kraheja.fd.deposit.bean.response;

import java.sql.Timestamp;

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

public class Form15hgResponseBean {

	private String coy ; 

	private String acyear ; 

	private String depositor ; 

	private String quarter ; 
	
	private Double amtincomepaid ;

	private String assessedyn ;

	private String assessyear ;

	private String datedeclReceived ;

	private Timestamp dateIncomePaid ;

	private Double estimatedIncome ;

	private Double estimatedtotIncome ;

	private Double incomeFormFiled ;

	private String origsite ;

	private String site ;

	private String today ;

	private Double totalFormFiled ;

	private String uniqueid ;

	private String userid ;
	
	private String form15hg ;
}