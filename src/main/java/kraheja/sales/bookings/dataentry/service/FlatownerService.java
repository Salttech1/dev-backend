// Developed By  - 	sandesh.c
// Developed on - 17-08-23
// Mode  - Data Entry
// Purpose - Flatowner Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.FlatownerRequestBean;
public interface FlatownerService {

	ResponseEntity<?> fetchFlatownerByOwneridAndBldgcodeAndWingAndFlatnumAndOwnertype(String  ownerid, String  bldgcode, String  wing, String  flatnum, String  ownertype) ;
	
	ResponseEntity<?> addFlatowner(List<FlatownerRequestBean> flatownerRequestBean)  ;

	ResponseEntity<?> updateFlatowner(List<FlatownerRequestBean> flatownerRequestBean,String  ownerid)  ;

	ResponseEntity<?> deleteFlatowner(String  ownerid, String  bldgcode, String  wing, String  flatnum, String  ownertype) ; 

	ResponseEntity<?> checkOwneridAndBldgcodeAndWingAndFlatnumAndOwnertypeExists(String  ownerid, String  bldgcode, String  wing, String  flatnum, String  ownertype) ;
	
	ResponseEntity<?> checkOwneridExists(String  ownerid) ;

	ResponseEntity<?> deleteAllFlatowner(String ownerid);

	
}