package kraheja.payroll.Reports.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.payroll.Reports.service.ReportParametersService;
import kraheja.payroll.bean.response.ReportParametersRequestBean;

@RestController
@RequestMapping("/payrollreports")
public class ReportParametersController {
	
	@Autowired
	private ReportParametersService reportParametersService;
	
	
	@GetMapping("/reportparameters")
	public ResponseEntity<?> ReportParameters(){
		return this.reportParametersService.GetReportParameters();
	}
	
	@GetMapping("/export-pf-report")
	public ResponseEntity<?> pfExcelCreation(String comanyCode, String paymonth){
		return this.reportParametersService.pfExcelCreation(comanyCode,paymonth);
	}
	
	@GetMapping("/employeewise-monthly-summary")
	public ResponseEntity<?> empwiseMonthlySummary(String coyCode, String deptCodes,
			String empCodes, String salaryTypes, String paymonth, String paymentDate, String empType ){
		return this.reportParametersService.empwiseMonthlySummary(coyCode, deptCodes, empCodes, salaryTypes, paymonth, paymentDate,  empType);
	}
	
	@GetMapping("/gratuity-form")
	public ResponseEntity<?> gratuityForm(String templateFileWithPath, String[][] fieldsList){
		return this.reportParametersService.gratuityForm(templateFileWithPath, fieldsList);
	}
	
	@GetMapping("/export-socsalarydet") 
	public ResponseEntity<?> socSalaryDetExcelCreation(String empCodes, String paymonthfrom, String paymonthto){
		return this.reportParametersService.socSalaryDetExcelCreation(empCodes,paymonthfrom,paymonthto);
	}

	@GetMapping("/export-grosssalary") 
	public ResponseEntity<?> grossSalaryDetExcelCreation(String paymonthfrom){
		return this.reportParametersService.gorssSalaryDetExcelCreation(paymonthfrom);
	}

	@GetMapping("/employeewise-monthly-summary-pt")
	public ResponseEntity<?> empwiseMonthlySummaryPT(String coyCode, String deptCodes,
			String empCodes, String salaryTypes, String paymonth, String paymentDate, String empType ){
		return this.reportParametersService.empwiseMonthlySummaryPT(coyCode, deptCodes, empCodes, salaryTypes, paymonth, paymentDate,  empType);
	}
	
	@GetMapping("/employee-salary-details")
	public ResponseEntity<?> empSalaryDetails (String coyCode, String empFrom, String empTo, String deptFrom, String deptTo, String payDate){
		return this.reportParametersService.empSalaryDetails(coyCode, empFrom, empTo, deptFrom, deptTo, payDate);
	}
	
	
}