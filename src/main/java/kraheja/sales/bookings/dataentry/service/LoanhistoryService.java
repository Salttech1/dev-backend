// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Loanhistory Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import kraheja.sales.bean.request.LoanhistoryRequestBean;

public interface LoanhistoryService {

	ResponseEntity<?> fetchLoanhistoryByLoancoAndLoannumAndLoanclosedateAndOwnerid(String  loanco, String  loannum, LocalDateTime  loanclosedate, String  ownerid) ;

	ResponseEntity<?> findByLoanhistoryCK_LhistOwnerid(String  ownerid);
	
	ResponseEntity<?> addLoanhistory(List <LoanhistoryRequestBean > loanhistoryRequestBean)  ;

	ResponseEntity<?> updateLoanhistory(List <LoanhistoryRequestBean > loanhistoryRequestBean)  ;

	ResponseEntity<?> deleteLoanhistory(String  loanco, String  loannum, LocalDateTime  loanclosedate, String  ownerid) ; 

	ResponseEntity<?> checkLoancoAndLoannumAndLoanclosedateAndOwneridExists(String  loanco, String  loannum, LocalDateTime  loanclosedate, String  ownerid) ;
}