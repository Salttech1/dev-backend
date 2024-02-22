package kraheja.sales.bookings.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.FlatsRequestBean;

public interface FlatsService {
	ResponseEntity<?> deleteFlatByBldgCodeAndWingAndFlatnum(String bldgCode,String Wing,String flatNum);
	
	ResponseEntity<?> fetchFlatsByBldgcodeAndWingAndFlatnum(String  bldgcode, String  wing, String  flatnum) ;

	ResponseEntity<?> addFlats(FlatsRequestBean flatsRequestBean)  ;

	ResponseEntity<?> updateFlats(FlatsRequestBean flatsRequestBean)  ;

	ResponseEntity<?> deleteFlats(String  bldgcode, String  wing, String  flatnum) ; 

	ResponseEntity<?> checkBldgcodeAndWingAndFlatnumExists(String  bldgcode, String  wing, String  flatnum) ;
}
