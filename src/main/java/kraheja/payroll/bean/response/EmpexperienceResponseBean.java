// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empexperience Entry / Edit
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

public class EmpexperienceResponseBean {

	private String companyname ;
	private String empcode ;
	private String endgrade ;
	private Double endgrosspermth ;
	private String endlevel ;
	private Double endpackage ;
	private String endpost ;
	private String endreportingto ;
	private String ipaddress ;
	private String jobprofile ;
	private Integer jobsrno ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String reasonforleaving ;
	private String site ;
	private String startgrade ;
	private Double startgrosspermth ;
	private String startlevel ;
	private Double startpackage ;
	private String startpost ;
	private String startreportingto ;
	private Integer totalservice ;
	private String userid ;
	private String workedfrom ;
	private String workedupto ;
}