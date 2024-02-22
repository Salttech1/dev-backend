package kraheja.purch.bean.response;


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

public class AuthDCancelMaterialBean {

	
	private String suppbillno ;
	private String suppbilldt ;
	private Double authqty ;
	private Double authamount ;
	private Double authtdsamt ;
	private Double retainamt ;
	private Double relretamt ;
	private Double advadj ;
	private Double retentionadj ;	
}
