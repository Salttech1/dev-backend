// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatchar Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.sales.bean.request.FlatcharRequestBean;
import kraheja.sales.bookings.dataentry.service.FlatcharService;

@RestController
@RequestMapping("/flatchar")
public class FlatcharController {

	@Autowired
	private FlatcharService flatcharService;

	@GetMapping("/fetch-flatchar-by-Bldgcode-and-Flatnum-and-Accomtype-and-Chargecode-and-Wing")
	public ResponseEntity<?> fetchFlatcharByBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWing(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "accomtype") String  accomtype, @RequestParam(value = "chargecode") String  chargecode, @RequestParam(value = "wing") String  wing) {
		return this.flatcharService.fetchFlatcharByBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWing(bldgcode, flatnum, accomtype, chargecode, wing) ; 
	}

	@PostMapping("/add-flatchar")
	public ResponseEntity<?> addFlatchar(@Valid @RequestBody List<FlatcharRequestBean> flatcharRequestBean)  {
		return this.flatcharService.addFlatchar(flatcharRequestBean);
	}

	@PutMapping("/update-flatchar")
	public ResponseEntity<?> updateFlatchar(@Valid @RequestBody List<FlatcharRequestBean> flatcharRequestBean,@RequestParam(value = "bldgcode") String  bldgcode,@RequestParam(value = "flatnum") String  flatnum,@RequestParam(value = "wing") String  wing)  {
		return this.flatcharService.updateFlatchar(flatcharRequestBean,bldgcode,flatnum,wing);
	}

	@GetMapping("/check-Bldgcode-and-Flatnum-and-Accomtype-and-Chargecode-and-Wing-exists")
	public ResponseEntity<?> checkBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWingExists(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "accomtype") String  accomtype, @RequestParam(value = "chargecode") String  chargecode, @RequestParam(value = "wing") String  wing)  {
		return this.flatcharService.checkBldgcodeAndFlatnumAndAccomtypeAndChargecodeAndWingExists(bldgcode, flatnum, accomtype, chargecode, wing);
	}

	@DeleteMapping("/delete-flatchar")
	public ResponseEntity<?> deleteFlatchar(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "accomtype") String  accomtype, @RequestParam(value = "chargecode") String  chargecode, @RequestParam(value = "wing") String  wing)  {
		return this.flatcharService.deleteFlatchar(bldgcode, flatnum, accomtype, chargecode, wing) ; 
	}
}