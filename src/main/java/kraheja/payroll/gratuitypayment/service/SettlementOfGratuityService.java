package kraheja.payroll.gratuitypayment.service;

import org.springframework.http.ResponseEntity;

public interface SettlementOfGratuityService {
	
	ResponseEntity<?> GetGratuityMonthForEmpl(String empCode);	

}
