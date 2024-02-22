package kraheja.purch.bean.response;

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

public class Auth_DResponseBean {

	private Double advadj ;
	private Double authamount ;
	private String authnum ;
	private Double authqty ;
	private Double authtdsamt ;
	private String authtype ;
	private String billtype ;
	private String bldgcode ;
	private String matcode ;
	private String matgroup ;
	private String origsite ;
	private String partycode ;
	private Double relretamt ;
	private Double retainamt ;
	private Double retentionadj ;
	private String site ;
	private String suppbilldt ;
	private String suppbillno ;
	private LocalDateTime today ;
	private String userid ;
}