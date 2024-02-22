// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Bldgwingmap Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.service;

import org.springframework.http.ResponseEntity;
import kraheja.sales.bean.request.BldgwingmapRequestBean;
public interface BldgwingmapService {

	ResponseEntity<?> fetchBldgwingmapByBldgcodeAndBldgwing(String  bldgcode, String  bldgwing) ;

	ResponseEntity<?> addBldgwingmap(BldgwingmapRequestBean bldgwingmapRequestBean)  ;

	ResponseEntity<?> updateBldgwingmap(BldgwingmapRequestBean bldgwingmapRequestBean)  ;

	ResponseEntity<?> deleteBldgwingmap(String  bldgcode, String  bldgwing) ; 

	ResponseEntity<?> checkBldgcodeAndBldgwingExists(String  bldgcode, String  bldgwing) ;
}