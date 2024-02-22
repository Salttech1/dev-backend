package kraheja.enggsys.bean.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class ContractdebitRequestBean {

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
	private LocalDateTime today ;
	private String userid ;
	//add or dele flag
	@Default
	private Boolean isDelete = Boolean.FALSE;
	@Default
	private Boolean isAdd = Boolean.FALSE;
}