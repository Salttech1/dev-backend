package kraheja.payroll.computationofsalary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.payroll.computationofsalary.service.SalaryInitialisationService;
import kraheja.payroll.computationofsalary.service.SalaryPaymentDateService;
import kraheja.payroll.repository.SalaryInitialisationRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ComputationOfSalary")
public class ComputationOfSalaryController {
	
	@Autowired
	private SalaryInitialisationService salaryInitialisationService;
	
	@Autowired
	private SalaryPaymentDateService salaryPaymentDateService;
	
	@Autowired
	private SalaryInitialisationRepository salaryInitialisationRepository;
	
	@GetMapping("/salaryInit-CheckInput")
	public ResponseEntity<?> fetchSalInitInputDetails(String coyCode,String salarytype) throws Exception{
		return this.salaryInitialisationService.GetCheckInputDetails(coyCode, salarytype) ;
	}

	@GetMapping("/salaryInit-CompletedStatus")
	public ResponseEntity<?> StartCompleteSalaryInitialisation(String coyCode,String salarytype) throws Exception{
		return this.salaryInitialisationService.StartSalaryInitialisation(coyCode, salarytype) ;
	}
	
	@GetMapping("/salaryPaymentDate-FetchInputData")
	public ResponseEntity<?> GetCheckInputDetailsforPaymentDate(String paymonth, String salarytype,
			String instrumenttype, String company)throws Exception{
		return this.salaryPaymentDateService.GetCheckInputDetailsforPaymentDate(paymonth, salarytype, instrumenttype, company);
	}
	
	@GetMapping("/salaryPaymentDate-FetchPaymonth")
	public ResponseEntity<?> GetPaymonthByCoyAndSalarytype( String company, String salarytype)throws Exception{
		return this.salaryPaymentDateService.GetPaymonthByCoyAndSalarytype(company, salarytype);
	}
}
