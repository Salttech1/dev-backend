package kraheja.payroll.Reports.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;


public interface ReportParametersService {
	
	ResponseEntity<?> GetReportParameters();
	
	ResponseEntity<?> pfExcelCreation(String comanyCode, String paymonth);	
	
	ResponseEntity<?> empwiseMonthlySummary(String coyCode, String deptCodes,
			String empCodes, String salaryTypes, String paymonth, String paymentDate,  String empType ) ;
	
	ResponseEntity<?> gratuityForm(String templateFileWithPath, String[][] fieldsList);
	
	ResponseEntity<?> WordReplaceText(String templateFileWithPath, String[][] fieldsList);

	ResponseEntity<?> socSalaryDetExcelCreation(String empCodes, String paymonthfrom, String paymonthto);	
	
	ResponseEntity<?> gorssSalaryDetExcelCreation(String paymonthfrom);	

	ResponseEntity<?> empwiseMonthlySummaryPT(String coyCode, String deptCodes,
			String empCodes, String salaryTypes, String paymonth, String paymentDate,  String empType ) ;
	
	ResponseEntity<?> empSalaryDetails (String coyCode, String empFrom, String empTo, String deptFrom, String deptTo, String payDate);
	
}
