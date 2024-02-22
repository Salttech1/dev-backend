// Developed By  - 	sandesh.c
// Developed on - 13-11-23
// Mode  - Data Entry
// Purpose - Loancompanyaddress Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import org.springframework.http.ResponseEntity;

//import kraheja.sales.bean.request.LoancompanyaddressRequestBean;
public interface LoancompanyaddressService {

	ResponseEntity<?> fetchLoancompanyaddressByLoancoycodeAndLoanbranchcode(String  loancoycode, String  loanbranchcode) ;

//	ResponseEntity<?> addLoancompanyaddress(LoancompanyaddressRequestBean loancompanyaddressRequestBean)  ;

	//ResponseEntity<?> updateLoancompanyaddress(LoancompanyaddressRequestBean loancompanyaddressRequestBean)  ;

	//ResponseEntity<?> deleteLoancompanyaddress(String  loancoycode, String  loanbranchcode) ; 

	//ResponseEntity<?> checkLoancoycodeAndLoanbranchcodeExists(String  loancoycode, String  loanbranchcode) ;
}