package kraheja.payroll.computationofsalary.service;

import java.util.List;

import org.springframework.http.ResponseEntity;


public interface SalaryInitialisationService {
	ResponseEntity<?> GetCheckInputDetails(String coyCode, String salarytype);
	
	ResponseEntity<?> StartSalaryInitialisation(String coyCode, String salarytype);
	
	
}
