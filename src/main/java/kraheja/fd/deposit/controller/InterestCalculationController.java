package kraheja.fd.deposit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.fd.deposit.bean.TDepintBean;
import kraheja.fd.deposit.service.InterestCalculationService;


@RestController
@RequestMapping("/interest-calculation")
public class InterestCalculationController {

	@Autowired
	private InterestCalculationService interestCalculationService; 

	@GetMapping("/fetch-interest-calculation-report")
	public ResponseEntity<?> fetchInterestCalculationReport(String companyCode, String calculateUpTo, String site, String userId) {
		return this.interestCalculationService.fetchInterestCalculationReport(companyCode.trim(), calculateUpTo, site, userId);
	}
	
	@GetMapping("/truncate-temp-table")
	public ResponseEntity<?> truncateTempTable() {
		return this.interestCalculationService.truncateTempTable();
	}
	
	@PostMapping("/save-interest-calculation")
	public ResponseEntity<?> saveInterestCalculation(@Valid @RequestBody TDepintBean depintBean) {
		return this.interestCalculationService.saveInterestCalculation(depintBean);
	}
	
	@PostMapping("/fetch-interest-payment-summary-report")
	public ResponseEntity<?> fetchInterestPaymentSummayReport(@Valid @RequestBody TDepintBean depintBean) {
		return this.interestCalculationService.downloadInterestPaymentSummary(depintBean);
	}
}
