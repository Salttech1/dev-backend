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

public class EmpeducationRequestBean {

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
	private Integer percofmarks ;
	private String site ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}