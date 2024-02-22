package kraheja.adminexp.overheads.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import kraheja.adminexp.overheads.dataentry.bean.request.OverheaddepositdtlsRequestBean;
import kraheja.adminexp.overheads.dataentry.service.OverheaddepositdtlsService;


@RestController
@RequestMapping("/overheaddepositdtls")
public class OverheaddepositdtlsController {

	@Autowired
	private OverheaddepositdtlsService overheaddepositdtlsService;

	@GetMapping("/fetch-overheaddepositdtls-by-Connocode-and-Period")
	public ResponseEntity<?> fetchOverheaddepositdtlsByConnocodeAndPeriod(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "period") String  period) throws ParseException {
		return this.overheaddepositdtlsService.fetchOverheaddepositdtlsByConnocodeAndPeriod(connocode, period) ; 
	}
	
	@GetMapping("/fetch-overheaddeposit-Amount-by-Connocode")
	public ResponseEntity<?> fetchDepositeAmt(@RequestParam(value = "connocode") String  connocode) throws ParseException {
		return this.overheaddepositdtlsService.fetchDepositeAmtbyConnocode(connocode) ; 
	}

	@PostMapping("/add-overheaddepositdtls")
	public ResponseEntity<?> addOverheaddepositdtls(@Valid @RequestBody OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean) throws ParseException {
		return this.overheaddepositdtlsService.addOverheaddepositdtls(overheaddepositdtlsRequestBean);
	}

	@PutMapping("/update-overheaddepositdtls")
	public ResponseEntity<?> updateOverheaddepositdtls(@Valid @RequestBody OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean) throws ParseException {
		return this.overheaddepositdtlsService.updateOverheaddepositdtls(overheaddepositdtlsRequestBean);
	}

	@GetMapping("/check-Connocode-and-Period-exists")
	public ResponseEntity<?> checkConnocodeAndPeriodExists(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "period") String  period) throws ParseException {
		return this.overheaddepositdtlsService.checkConnocodeAndPeriodExists(connocode, period);
	}

	@DeleteMapping("/delete-overheaddepositdtls")
	public ResponseEntity<?> deleteOverheaddepositdtls(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "period") String  period) throws ParseException {
		return this.overheaddepositdtlsService.deleteOverheaddepositdtls(connocode, period) ; 
	}
}