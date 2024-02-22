// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.payload.GenericResponse;
import kraheja.sales.bean.request.LpropRequestBean;
import kraheja.sales.lessorrent.service.LpropService;

@RestController
@RequestMapping("/lprop")
public class LpropController {

	@Autowired
	private LpropService lpropService;

	@GetMapping("/fetch-lprop-by-Propcode")
	public ResponseEntity<?> fetchLpropByPropcode(@RequestParam(value = "propcode") String  propcode) {
		return this.lpropService.fetchLpropByPropcode(propcode) ; 
	}

		@PostMapping("/add-lprop")
	public ResponseEntity<?> addLprop(@Valid @RequestBody LpropRequestBean lpropRequestBean)  {
		return this.lpropService.addLprop(lpropRequestBean);
	}

//	@PutMapping("/update-lprop")
//	public ResponseEntity<?> updateLprop(@Valid @RequestBody LpropRequestBean lpropRequestBean)  {
//		return this.lpropService.updateLprop(lpropRequestBean);
//	}

//	@GetMapping("/check-Propcode-exists")
//	public ResponseEntity<?> checkPropcodeExists(@RequestParam(value = "propcode") String  propcode)  {
//		return this.lpropService.checkPropcodeExists(propcode);
//	}

	@DeleteMapping("/delete-lprop")
	public ResponseEntity<GenericResponse> deleteLprop(@RequestParam(value = "propcode") String  propcode)  {
		 GenericResponse response = this.lpropService.deleteLprop(propcode) ; 
		 return ResponseEntity.ok(response);
	}

}