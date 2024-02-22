package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping("/fetch-coy-closedate")
	public ResponseEntity<?> fecthCoyCloseDate(String companyCode){
		return this.companyService.fecthCoyCloseDate(companyCode);	
	}

	@GetMapping("/fetch-prop-bycoyandclosedate")
	public ResponseEntity<?> fetchPropByCoyAndClosedate(String companyCode,String closedate){
		return this.companyService.fetchPropByCoyAndClosedate(companyCode, closedate);
	}
	
	@GetMapping("/fetch-prop-details-by-coycode")
	public ResponseEntity<?> fetchPropDetailsByCoycode(String companyCode){
		return this.companyService.fetchPropDetailsByCoycode(companyCode);
	}
	
}
