package kraheja.payroll.bean;

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

public class EmpsalaryprocessRequestBean {

	private String company ;
	private Double computecount ;
	private Double computeno ;
	private Double currrate ;
	private Double earndedamt ;
	private String earndedcode ;
	private String empcode ;
	private String ipaddress ;
	private Double lastmthrate ;
	private Double lastyrrate ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private Double onhold ;
	private String paidfrom ;
	private String paidupto ;
	private String paymonth ;
	private Double prevmthrate ;
	private Double recompcount ;
	private String salarytype ;
	private String salrevison ;
	private String schemeapplyn ;
	private Double sessionid ;
	private String site ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}