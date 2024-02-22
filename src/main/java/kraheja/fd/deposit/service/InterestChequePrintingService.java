package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.fd.deposit.bean.request.InterestChequePrintingRequestBean;

public interface InterestChequePrintingService {

	ResponseEntity<?> fetchInterestCalculationReport(InterestChequePrintingRequestBean interestChequePrintingRequestBean);

	ResponseEntity<?> truncateTempTable();
}
