// Developed By  - 	sandesh.c
// Developed on - 13-11-23
// Mode  - Data Entry
// Purpose - Loancompany Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import org.springframework.http.ResponseEntity;

//import kraheja.sales.bean.request.LoancompanyRequestBean;

public interface LoancompanyService {

	ResponseEntity<?> fetchLoancompanyByCode(String  code,String brachCdoe) ;

//	ResponseEntity<?> addLoancompany(LoancompanyRequestBean loancompanyRequestBean)  ;

//	ResponseEntity<?> updateLoancompany(LoancompanyRequestBean loancompanyRequestBean)  ;

	ResponseEntity<?> deleteLoancompany(String  code) ; 

	ResponseEntity<?> checkCodeExists(String  code) ;
}