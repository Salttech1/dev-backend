package kraheja.payroll.masterdetail.service;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;

import kraheja.payroll.bean.EmployeeDetailsRequestBean;


public interface EmployeeDetailsEntryEditService {
	
	ResponseEntity<?> fetchEmplDetails(String empcode) throws Exception;
	
	ResponseEntity<?> addEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean);
	
	ResponseEntity<?> updateNewEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean);
	
	ResponseEntity<?> updateOldEmplDetails(EmployeeDetailsRequestBean employeeDetailsRequestBean);

	ResponseEntity<?> fetchAllSalaryPackage(String empcode,Character CurrentAll);

	ResponseEntity<?> fetchCompanySalPackage(String coycode,String closeDate);
	
	ResponseEntity<?> fetchCompanySalDedPackage(String coycode,String closeDate);
	
	ResponseEntity<?> fetchCompanySchemeDetails(String coycode,String joinpaymonth,String closeDate);
	
	ResponseEntity<?> fetchCompanyLeaveDetails(String coycode,String joindate,String emptype);

}
