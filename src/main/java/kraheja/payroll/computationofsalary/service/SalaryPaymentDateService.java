package kraheja.payroll.computationofsalary.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface SalaryPaymentDateService {
	ResponseEntity<?> GetCheckInputDetailsforPaymentDate(String paymonth, String salarytype, String instrumenttype, String company);
	
	ResponseEntity<?> GetPaymonthByCoyAndSalarytype(String company, String salarytype);

}
