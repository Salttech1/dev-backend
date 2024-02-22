package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface CompanyService {
	ResponseEntity<?> fecthCoyCloseDate(String companyCode);
	
	ResponseEntity<?> fetchPropByCoyAndClosedate(String companyCode, String closedate);
	
	ResponseEntity<?> fetchPropDetailsByCoycode(String companyCode);
}
