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

public class EmppaymonthRequestBean {

	private String actranser ;
	private String acvounum ;
	private Double daysabsent ;
	private Double daysadjlastmth ;
	private Double daysadjprvlastmth ;
	private Double daysarrears ;
	private Double daysencashed ;
	private Double daysnhpay ;
	private Double dayspaid ;
	private Double daysunionfund ;
	private String empcode ;
	private String instrumentbank ;
	private String instrumentdate ;
	private String instrumentno ;
	private String instrumenttype ;
	private String ipaddress ;
	private Double itdeclrevno ;
	private String machinename ;
	private LocalDateTime modifiedon ;
	private String module ;
	private Double othours ;
	private Double othrslastmth ;
	private Double othrsprvlastmth ;
	private String paidfrom ;
	private String paidupto ;
	private String paydate ;
	private String paymonth ;
	private String paystatus ;
	private String reg_settle ;
	private String remark ;
	private String salarytype ;
	private String site ;
	private String userid ;
	//add or update flag
	@Default
	private Boolean isUpdate = Boolean.FALSE;
}