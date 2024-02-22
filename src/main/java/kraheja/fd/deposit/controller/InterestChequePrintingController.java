package kraheja.fd.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.fd.deposit.bean.request.InterestChequePrintingRequestBean;
import kraheja.fd.deposit.service.InterestChequePrintingService;


@RestController
@RequestMapping("/interest-cheque-printing")
public class InterestChequePrintingController {

	@Autowired
	private InterestChequePrintingService interestChequePrintingService; 

	@PostMapping("/fetch-interest-cheque-printing")
	public ResponseEntity<?> fetchInterestCalculationReport(@RequestBody InterestChequePrintingRequestBean interestChequePrintingRequestBean) {
		return this.interestChequePrintingService.fetchInterestCalculationReport(interestChequePrintingRequestBean);
	}
	
	@GetMapping("/truncate-temp-table")
	public ResponseEntity<?> truncateTempTable() {
		return this.interestChequePrintingService.truncateTempTable();
	}
}
