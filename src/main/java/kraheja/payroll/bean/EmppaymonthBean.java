package kraheja.payroll.bean;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class EmppaymonthBean {
	private String empcode ; 
	private String paidfrom ;
	private String paidupto ;
	private String paymonth ;
	private Character paystatus ;
	private String salarytype ;
	private BigDecimal dayspaid ;
	private BigDecimal daysencashed ;
	private BigDecimal othours ;
	private BigDecimal othrslastmth ;
	private BigDecimal othrsprvlastmth ;
	private String remark ;
	private String paydate ;
	private Character instrumenttype ;
	private String instrumentno ;
	private String instrumentbank ;
	private String instrumentdate ;
	private BigDecimal daysunionfund ;
	private BigDecimal daysabsent ;
	private BigDecimal daysadjlastmth ;
	private BigDecimal daysadjprvlastmth ;
	private BigDecimal daysnhpay ;
	private BigDecimal daysarrears ;
	private String acvounum ;
	private String actranser ;
	private BigDecimal itdeclrevno ;
	private Character reg_settle ;
	private String module ;
}