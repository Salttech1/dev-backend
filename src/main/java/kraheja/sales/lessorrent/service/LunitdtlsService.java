// Developed By  - 	vikram.p
// Developed on - 29-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.service;

import org.springframework.http.ResponseEntity;
import kraheja.sales.bean.request.LunitdtlsRequestBean;
public interface LunitdtlsService {

	ResponseEntity<?> fetchLunitdtlsByPropcodeAndUnitidAndUnitno(String  propcode, String  unitid, String  unitno) ;

	ResponseEntity<?> addLunitdtls(LunitdtlsRequestBean lunitdtlsRequestBean)  ;

	ResponseEntity<?> updateLunitdtls(LunitdtlsRequestBean lunitdtlsRequestBean)  ;

	ResponseEntity<?> deleteLunitdtls(String  propcode, String  unitid, String  unitno) ; 

	ResponseEntity<?> checkPropcodeAndUnitidAndUnitnoExists(String  propcode, String  unitid, String  unitno) ;
	
	ResponseEntity<?> findCountPropcode(String  propcode) ;
	
}