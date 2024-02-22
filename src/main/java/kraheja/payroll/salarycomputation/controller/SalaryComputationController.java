package kraheja.payroll.salarycomputation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.payroll.bean.ComputationDetailRequestBean;
import kraheja.payroll.salarycomputation.service.SalaryComputationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payroll")
public class SalaryComputationController {
	
	@Autowired
	private SalaryComputationService salaryComputationService;
	

	@PostMapping("/salary-compute")
	public ResponseEntity<?> fetchEmplForCalculation(@RequestBody ComputationDetailRequestBean computationDetailRequestBean){
		return this.salaryComputationService.fetchEmplForCalculation(computationDetailRequestBean) ;
	}
	
	
}	
