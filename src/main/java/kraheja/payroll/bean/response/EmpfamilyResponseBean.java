// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empfamily Entry / Edit
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

public class EmpfamilyResponseBean {

	private String birthdate ;
	private String empcode ;
	private String fullname ;
	private String ipaddress ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String occupation ;
	private String relation ;
	private String site ;
	private Integer srno ;
	private String userid ;
	private String weddingdate ;
}