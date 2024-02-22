package kraheja.payroll.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EmpexperienceRequestBean {

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
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}