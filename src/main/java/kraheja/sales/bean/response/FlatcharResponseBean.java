// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatchar Entry / Edit
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

public class FlatcharResponseBean {

	private String accomtype ;
	private Double amtdue ;
	private Double amtpaid ;
	private String bldgcode ;
	private String chargecode ;
	private String flatnum ;
	private String origsite ;
	private String site ;
	private String sqftwiseyn ;
	private LocalDateTime today ;
	private String userid ;
	private String wing ;
}