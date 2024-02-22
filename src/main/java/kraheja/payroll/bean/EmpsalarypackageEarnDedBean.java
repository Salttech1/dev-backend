package kraheja.payroll.bean;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class EmpsalarypackageEarnDedBean {
	private String earndedcode ;
	private String earndeddesc ;
	private Character paycycle ;
	private Character ratecycle ;
	private Double earndedrate ;
	private String effectivefrom ;
	private String effectiveupto ;
	private String empcode ;
	private String site ;
	private String userid ;
	private String module ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String ipaddress ;
	private Character state;
	private Character active;
}