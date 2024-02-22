//// Developed By  - 	sandesh.c
//// Developed on - 13-11-23
//// Mode  - Data Entry
//// Purpose - Loancompany Entry / Edit
//// Modification Details
//// =======================================================================================================================================================
//// Date		Coder  Version User    Reason              
//// =======================================================================================================================================================
//
//package kraheja.sales.bookings.dataentry.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import kraheja.sales.bean.request.LoancompanyRequestBean;
//import kraheja.sales.bookings.dataentry.service.LoancompanyService;
//
//@RestController
//@RequestMapping("/loancompany")
//public class LoancompanyController {
//
//	@Autowired
//	private LoancompanyService loancompanyService;
//
//	@GetMapping("/fetch-loancompany-by-Code")
//	public ResponseEntity<?> fetchLoancompanyByCode(@RequestParam(value = "code") String  code,@RequestParam(value = "branch") String  branch) {
//		return this.loancompanyService.fetchLoancompanyByCode(code,branch) ; 
//	}
//
//	@PostMapping("/add-loancompany")
//	public ResponseEntity<?> addLoancompany(@Valid @RequestBody LoancompanyRequestBean loancompanyRequestBean)  {
//		return this.loancompanyService.addLoancompany(loancompanyRequestBean);
//	}
//
//	@PutMapping("/update-loancompany")
//	public ResponseEntity<?> updateLoancompany(@Valid @RequestBody LoancompanyRequestBean loancompanyRequestBean)  {
//		return this.loancompanyService.updateLoancompany(loancompanyRequestBean);
//	}
//
//	@GetMapping("/check-Code-exists")
//	public ResponseEntity<?> checkCodeExists(@RequestParam(value = "code") String  code)  {
//		return this.loancompanyService.checkCodeExists(code);
//	}
//
//	@DeleteMapping("/delete-loancompany")
//	public ResponseEntity<?> deleteLoancompany(@RequestParam(value = "code") String  code)  {
//		return this.loancompanyService.deleteLoancompany(code) ; 
//	}
//}