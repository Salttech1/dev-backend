package kraheja.payroll.salarycomputation.service;

import org.springframework.http.ResponseEntity;

import kraheja.payroll.bean.ComputationDetailRequestBean;


public interface SalaryComputationService {
//	ResponseEntity<?> fetchEmplForCalculation(Set<String> coy, String empcode, String emptype, String saltype);

	ResponseEntity<?> fetchEmplForCalculation(ComputationDetailRequestBean computationDetailRequestBean);
	

}