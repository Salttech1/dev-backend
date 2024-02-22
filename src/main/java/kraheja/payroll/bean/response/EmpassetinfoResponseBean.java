// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empassetinfo Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.payroll.bean.response;

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

public class EmpassetinfoResponseBean {

	private String assetcode ;
	private String assetdesc ;
	private String assetname ;
	private String authby ;
	private String empcode ;
	private String ipaddress ;
	private String issuedate ;
	private String issuedby ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String receivedby ;
	private String remark ;
	private String returndate ;
	private String site ;
	private String userid ;
}