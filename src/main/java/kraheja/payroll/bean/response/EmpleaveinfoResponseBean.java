// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empleaveinfo Entry / Edit
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

public class EmpleaveinfoResponseBean {

	private String acyear ;
	private Double compoffearned ;
	private Double dayexcessadj ;
	private Double daysavailed ;
	private Double daysbf ;
	private Double daysencashed ;
	private Double daysentitled ;
	private String empcode ;
	private String ipaddress ;
	private Character leavecode ;
	private String machinename ;
	private Double maxdayscf ;
	private Double maxdaysenc ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String remark ;
	private String site ;
	private String userid ;
	private Character active;
}