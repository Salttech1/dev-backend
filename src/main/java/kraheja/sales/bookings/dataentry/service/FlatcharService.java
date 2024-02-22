// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatchar Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import  kraheja.sales.bean.request.FlatcharRequestBean;
public interface FlatcharService {

	ResponseEntity<?> fetchFlatcharByBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWing(String  bldgcode, String  flatnum, String  accomtype, String  chargecode, String  wing) ;

	ResponseEntity<?> addFlatchar(List<FlatcharRequestBean> flatcharRequestBean)  ;

	ResponseEntity<?> updateFlatchar(List<FlatcharRequestBean> flatcharRequestBean,String bldgcode, String flatnum,String wing)  ;

	ResponseEntity<?> deleteFlatchar(String  bldgcode, String  flatnum, String  accomtype, String  chargecode, String  wing) ; 

	ResponseEntity<?> checkBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWingExists(String  bldgcode, String  flatnum, String  accomtype, String  chargecode, String  wing) ;
}