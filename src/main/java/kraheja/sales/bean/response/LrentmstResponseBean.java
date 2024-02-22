// Developed By  - 	vikram.p
// Developed on - 03-01-24
// Mode  - Data Entry
// Purpose - Lrentmst Entry / Edit
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

public class LrentmstResponseBean {

	private Double amt ;
	private String certstatus ;
	private String from ;
	private Double incper ;
	private String ipaddress ;
	private String partycode ;
	private String paycycle ;
	private String propcode ;
	private String remarks ;
	private String servtaxflag ;
	private Double servtaxper ;
	private String site ;
	private String status ;
	private String to ;
	private LocalDateTime today ;
	private String type ;
	private String unitgroup ;
	private String unitid ;
	private String unitno ;
	private String userid ;
}