package kraheja.payroll.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.payroll.bean.response.EmpsalarypackageResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeSalDetailsResponseBean {
	private List<EmpsalarypackageEarnDedBean> empsalarypackageResponseBean;
	private List<EmpsalarypackageEarnDedBean> empsalarypackagededResponseBean;

}