// Developed By  - 	kalpana.m
// Developed on - 20-07-23
// Mode  - Data Entry
// Purpose - Empschemeinfo Entry / Edit
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

public class EmpschemeinfoResponseBean {

	private String applicableyn ;
	private String effectivefrom ;
	private String effectiveupto ;
	private String empcode ;
	private String empschemeno ;
	private String entrydate ;
	private String ipaddress ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private String remark ;
	private Double schemeamount ;
	private String schemecentre ;
	private String schemecode ;
	private String schemeenddate ;
	private Integer schemepercentage ;
	private String site ;
	private String userid ;
}