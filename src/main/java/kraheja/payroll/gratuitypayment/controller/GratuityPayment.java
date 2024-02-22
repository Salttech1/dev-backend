package kraheja.payroll.gratuitypayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.payroll.gratuitypayment.service.SettlementOfGratuityService;


@RestController
@RequestMapping("/gratuitypayment/reports")
public class GratuityPayment {

	@Autowired
	private SettlementOfGratuityService settlementOfGratuityService;
	
	@GetMapping("/gratuitymonthbyempcode")
	public ResponseEntity<?> GratuityMonthByEmpCode(String empCode){
		return this.settlementOfGratuityService.GetGratuityMonthForEmpl(empCode);
	}	
	
}


