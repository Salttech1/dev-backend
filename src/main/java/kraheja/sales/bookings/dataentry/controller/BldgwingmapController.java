// Developed By  - 	sandesh.c
// Developed on - 23-08-23
// Mode  - Data Entry
// Purpose - Bldgwingmap Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.controller;

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

import kraheja.sales.bean.request.BldgwingmapRequestBean;
import kraheja.sales.bookings.dataentry.service.BldgwingmapService;

@RestController
@RequestMapping("/bldgwingmap")
public class BldgwingmapController {

	@Autowired
	private BldgwingmapService bldgwingmapService;

	@GetMapping("/fetch-bldgwingmap-by-Bldgcode-and-Bldgwing")
	public ResponseEntity<?> fetchBldgwingmapByBldgcodeAndBldgwing(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "bldgwing") String  bldgwing) {
		return this.bldgwingmapService.fetchBldgwingmapByBldgcodeAndBldgwing(bldgcode, bldgwing) ; 
	}

	@PostMapping("/add-bldgwingmap")
	public ResponseEntity<?> addBldgwingmap(@Valid @RequestBody BldgwingmapRequestBean bldgwingmapRequestBean)  {
		return this.bldgwingmapService.addBldgwingmap(bldgwingmapRequestBean);
	}

	@PutMapping("/update-bldgwingmap")
	public ResponseEntity<?> updateBldgwingmap(@Valid @RequestBody BldgwingmapRequestBean bldgwingmapRequestBean)  {
		return this.bldgwingmapService.updateBldgwingmap(bldgwingmapRequestBean);
	}

	@GetMapping("/check-Bldgcode-and-Bldgwing-exists")
	public ResponseEntity<?> checkBldgcodeAndBldgwingExists(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "bldgwing") String  bldgwing)  {
		return this.bldgwingmapService.checkBldgcodeAndBldgwingExists(bldgcode, bldgwing);
	}

	@DeleteMapping("/delete-bldgwingmap")
	public ResponseEntity<?> deleteBldgwingmap(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "bldgwing") String  bldgwing)  {
		return this.bldgwingmapService.deleteBldgwingmap(bldgcode, bldgwing) ; 
	}
}