package kraheja.payroll.bean.response;
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
public class GetInputScreenBean {
	private String  Earndedcode;
	private String  Salarytype;
	private String  Coy;
	private String  Paymonth;
	private double  YrsalRevNo;
	private double  MthsalRevNo;
	private String  CurrBonusDate;
	private String  LastBonusDate;
	private String  BonusPaid;
	private String  BonusPeriod;
	private String  YearStartMonth;
	private String  YearEndMonth;
	private String  ItDedDiffMth;
	private String  ItDedNextMth;
	private String  SalaryTyName;
	private long  onhold;
	private long  freshcompute;
	private long  recompute;
}