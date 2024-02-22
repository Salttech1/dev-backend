// Developed By  - 	vikram.p
// Developed on - 29-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.lessorrent.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kraheja.sales.bean.request.LunitdtlsRequestBean;
import kraheja.sales.lessorrent.service.LunitdtlsService;

@RestController
@RequestMapping("/lunitdtls")
public class LunitdtlsController {

	@Autowired
	private LunitdtlsService lunitdtlsService;

	@GetMapping("/fetch-lunitdtls-by-Propcode-and-Unitid-and-Unitno")
	public ResponseEntity<?> fetchLunitdtlsByPropcodeAndUnitidAndUnitno(@RequestParam(value = "propcode") String  propcode, @RequestParam(value = "unitid") String  unitid, @RequestParam(value = "unitno") String  unitno) {
		return this.lunitdtlsService.fetchLunitdtlsByPropcodeAndUnitidAndUnitno(propcode, unitid, unitno) ; 
	}

	@PostMapping("/add-lunitdtls")
	public ResponseEntity<?> addLunitdtls(@Valid @RequestBody LunitdtlsRequestBean lunitdtlsRequestBean)  {
		return this.lunitdtlsService.addLunitdtls(lunitdtlsRequestBean);
	}

	@PutMapping("/update-lunitdtls")
	public ResponseEntity<?> updateLunitdtls(@Valid @RequestBody LunitdtlsRequestBean lunitdtlsRequestBean)  {
		return this.lunitdtlsService.updateLunitdtls(lunitdtlsRequestBean);
	}

	@GetMapping("/check-Propcode-and-Unitid-and-Unitno-exists")
	public ResponseEntity<?> checkPropcodeAndUnitidAndUnitnoExists(@RequestParam(value = "propcode") String  propcode, @RequestParam(value = "unitid") String  unitid, @RequestParam(value = "unitno") String  unitno)  {
		return this.lunitdtlsService.checkPropcodeAndUnitidAndUnitnoExists(propcode, unitid, unitno);
	}

	@DeleteMapping("/delete-lunitdtls")
	public ResponseEntity<?> deleteLunitdtls(@RequestParam(value = "propcode") String  propcode, @RequestParam(value = "unitid") String  unitid, @RequestParam(value = "unitno") String  unitno)  {
		return this.lunitdtlsService.deleteLunitdtls(propcode, unitid, unitno) ; 
	}
}