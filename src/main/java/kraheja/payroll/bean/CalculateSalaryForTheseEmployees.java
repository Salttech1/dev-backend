package kraheja.payroll.bean;
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
public class CalculateSalaryForTheseEmployees {
	private String  company;
	private String  department;
	private String  employeeType;
	private String  empcode;
	private String  paymode;
	private String  empfullname;
	private String  emptypename;
	private String  paymonth;
}