// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatpay Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.FlatpayRequestBean;

public interface FlatpayService {

	//ResponseEntity<?> fetchFlatpayByBldgcodeAndFlatnumAndOwneridAndDuedateAndPaiddateAndNarrative(String  bldgcode, String  flatnum, String  ownerid, LocalDateTime  duedate, LocalDateTime  paiddate, String  narrative) ;
	
	ResponseEntity<?> addFlatpay(List <FlatpayRequestBean> flatpayRequestBean)  ;

	ResponseEntity<?> updateFlatpay(List <FlatpayRequestBean > flatpayRequestBean,String  ownerid)  ;
	
	ResponseEntity<?> deleteFlatpay(String  bldgcode, String  flatnum, String  ownerid, LocalDateTime  duedate, LocalDateTime  paiddate, String  narrative) ; 

	ResponseEntity<?> checkBldgcodeAndFlatnumAndOwneridAndDuedateAndPaiddateAndNarrativeExists(String  bldgcode, String  flatnum, String  ownerid, LocalDateTime  duedate, LocalDateTime  paiddate, String  narrative) ;

	ResponseEntity<?> fetchFlatpayByBldgcodeAndFlatnumAndOwneridAndDuedateAndPaiddateAndNarrative(String bldgcode,
			String flatnum, String ownerid, String duedate, String paiddate, String narrative);
}