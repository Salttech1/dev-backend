package kraheja.fd.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.fd.deposit.service.YearEndProcessingService;

@RestController
@RequestMapping("/yearend-process")
public class YearEndProcessingController {

	@Autowired
	private YearEndProcessingService yearEndProcessingService;
	
	@PutMapping("/initialise-ytd")
	public ResponseEntity<?> initialiseYTD(String companyCode, String fromDate, String uptoDate){
		return yearEndProcessingService.intitaliseOrRecalculateYTD(companyCode, fromDate, uptoDate);
	}

	@PutMapping("/recalculate-broker-ytd")
	public ResponseEntity<?> recalculateBrokerYTD(String companyCode, String fromDate, String uptoDate){
		return yearEndProcessingService.recalculateBrokerYTD(companyCode, fromDate, uptoDate);
	}
}
