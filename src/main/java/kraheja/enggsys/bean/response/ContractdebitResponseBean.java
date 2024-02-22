package kraheja.enggsys.bean.response;

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

public class ContractdebitResponseBean {

	private Double amountadj ;
	private String authnum ;
	private String bldgcode ;
	private String certtype ;
	private String contract ;
	private String contracontract ;
	private String debitno ;
	private String debittype ;
	private Double debitamount ;
	private String remarks ;
	private String runser ;
	private String site ;
	private String workCode;
	private String partyCode;
	private LocalDateTime today ;
	private String userid ;
}