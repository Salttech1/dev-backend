package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

public interface YearEndProcessingService {
	ResponseEntity<?> intitaliseOrRecalculateYTD(String companyCode, String fromDate, String uptoDate);

	ResponseEntity<?> recalculateBrokerYTD(String companyCode, String fromDate, String uptoDate);

	
}
