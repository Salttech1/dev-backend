package kraheja.payroll.masterdetail.controller;


import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.utils.CommonConstraints;
import kraheja.payroll.bean.EmployeeDetailsRequestBean;
import kraheja.payroll.masterdetail.service.EmployeeDetailsEntryEditService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payroll")
public class MasterDetailController {
	
	@Autowired
	private EmployeeDetailsEntryEditService employeeDetailsEntryEditService;
	
	
	@GetMapping("/masterDetail-EntryEdit")
	public ResponseEntity<?> fetchEmplDetails(String empcode) throws Exception{
		return this.employeeDetailsEntryEditService.fetchEmplDetails(empcode) ;
	}
	
	@GetMapping("/salarypackageDetail")
	public ResponseEntity<?> fetchAllSalaryPackage(String empcode,Character currentAll) throws Exception{
		return this.employeeDetailsEntryEditService.fetchAllSalaryPackage(empcode,currentAll) ;
	}
	
	@GetMapping("/companypackageDetails")
	public ResponseEntity<?> fetchCompanySalPackage(String coycode){
		return this.employeeDetailsEntryEditService.fetchCompanySalPackage(coycode,CommonConstraints.INSTANCE.closeDate);
	}
	
	@GetMapping("/companypackageDedDetails")
	public ResponseEntity<?> fetchCompanySalDedPackage(String coycode){
		return this.employeeDetailsEntryEditService.fetchCompanySalDedPackage(coycode,CommonConstraints.INSTANCE.closeDate);
	}
	
	@GetMapping("/companySchemeDetails")
	public ResponseEntity<?> fetchCompanySchemeDetails(String coycode,String joinpaymonth){
		return this.employeeDetailsEntryEditService.fetchCompanySchemeDetails(coycode,joinpaymonth,CommonConstraints.INSTANCE.closeDate);
	}
	
	@GetMapping("/companyLeaveDetails")
	public ResponseEntity<?> fetchCompanyLeaveDetails(String coycode,String joindate,String emptype){
		return this.employeeDetailsEntryEditService.fetchCompanyLeaveDetails(coycode,joindate,emptype);
	}
	
	@PostMapping("/add-empdetails")
	public ResponseEntity<?> addEmplDetails(@Valid @RequestBody EmployeeDetailsRequestBean employeeDetailsRequestBean){
		return this.employeeDetailsEntryEditService.addEmplDetails(employeeDetailsRequestBean);
	}
	
	@PostMapping("/updateNew-empdetails")
	public ResponseEntity<?> updateNewEmplDetails(@Valid @RequestBody EmployeeDetailsRequestBean employeeDetailsRequestBean){
		return this.employeeDetailsEntryEditService.updateNewEmplDetails(employeeDetailsRequestBean);
	}
	
	@PostMapping("/updateOld-empdetails")
	public ResponseEntity<?> updateOldEmplDetails(@Valid @RequestBody EmployeeDetailsRequestBean employeeDetailsRequestBean){
		return this.employeeDetailsEntryEditService.updateOldEmplDetails(employeeDetailsRequestBean);
	}

}	

