// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatpay Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bean.response;

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

public class FlatpayResponseBean {

	private String bldgcode ;
	private Double dueamount ;
	private String duedate ;
	private String flatnum ;
	private String narrative ;
	private String origsite ;
	private String ownerid ;
	private Double paidamount ;
	private String paiddate ;
	private String paidref ;
	private String site ;
	private LocalDateTime today ;
	private String userid ;
	private String wing ;
}