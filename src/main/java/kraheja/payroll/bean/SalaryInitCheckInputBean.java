package kraheja.payroll.bean;

import java.math.BigDecimal;

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
public class SalaryInitCheckInputBean {
	private String  SalaryType;
	private String  Company;
	private String  CurrentMth;
	private String  NewMth;
	private String  Status;
	private Character  SalType;
	private BigDecimal  YrSalRevNo;

}
