package kraheja.enggsys.bean.response;

// Developed By  - 	Dev4
// Developed on - 05-06-23
// Mode  - Data Entry
// Purpose - Cbilld Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

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
public class CbilldResponseBean {

	private Double amount ;
	private String certdate ;
	private String certnum ;
	private Double cgstamt ;
	private Double cgstperc ;
	private Double dbamt ;
	private Double dbqty ;
	private Double discountamt ;
	private Double igstamt ;
	private Double igstperc ;
	private Integer lineno ;
	private String lotno ;
	private String origsite ;
	private Double quantity ;
	private Double rate ;
	private String saccode ;
	private String sacdesc ;
	private String ser ;
	private Double sgstamt ;
	private Double sgstperc ;
	private String site ;
	private Double taxableamt ;
	private LocalDateTime today ;
	private Double ugstamt ;
	private Double ugstperc ;
	private String uom ;
	private String userid ;
	private Integer workorderlineno ;
	private String workorderno ;
}