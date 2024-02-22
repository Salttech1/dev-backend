package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.fd.deposit.bean.TDepintBean;

public interface InterestCalculationService {

	ResponseEntity<?> fetchInterestCalculationReport(String companyCode, String calculateUpTo, String site, String userId);

	ResponseEntity<?> truncateTempTable();

	ResponseEntity<?> saveInterestCalculation(TDepintBean depintBean);
	
	ResponseEntity<?> downloadInterestPaymentSummary(TDepintBean tdepintBean);
}
