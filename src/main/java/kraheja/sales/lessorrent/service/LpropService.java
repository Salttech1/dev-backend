// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.service;

import org.springframework.http.ResponseEntity;

import kraheja.payload.GenericResponse;
import kraheja.sales.bean.request.LpropRequestBean;
public interface LpropService {

	ResponseEntity<?> fetchLpropByPropcode(String  propcode) ;

	ResponseEntity<?> addLprop(LpropRequestBean lpropRequestBean)  ;

//	ResponseEntity<?> updateLprop(LpropRequestBean lpropRequestBean)  ;

	GenericResponse deleteLprop(String  propcode) ; 

	//ResponseEntity<?> checkPropcodeExists(String  propcode) ;
}