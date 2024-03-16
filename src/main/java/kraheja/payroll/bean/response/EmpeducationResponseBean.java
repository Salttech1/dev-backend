// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empeducation Entry / Edit
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

public class EmpeducationResponseBean {

	private String attendedfromdate ;
	private String attendedtodate ;
	private String educlass ;
	private String coursename ;
	private String degree ;
	private Integer educsrno ;
	private String empcode ;
	private String institute ;
	private String ipaddress ;
	private String machinename ;
	private String mainsubjetcs ;
	private LocalDateTime modifiedon ;
	private String module ;
	private Double percofmarks ;
	private String site ;
	private String userid ;
}