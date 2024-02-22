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

import kraheja.sales.bean.request.FlatsRequestBean;
import kraheja.sales.bookings.dataentry.service.FlatsService;

@RestController
@RequestMapping("/flats")
public class FlatsController {

	@Autowired
	private FlatsService flatsService;
	
	@DeleteMapping("delete-flat")
	public ResponseEntity<?> deleteFlatByBldgCodeAndWingAndFlatnum(String bldgCode,String wing,String flatnum){
		return this.flatsService.deleteFlatByBldgCodeAndWingAndFlatnum(bldgCode, wing, flatnum);
	}
	
//	@Autowired
//	private FlatsService flatsService;

	@GetMapping("/fetch-flats-by-Bldgcode-and-Wing-and-Flatnum")
	public ResponseEntity<?> fetchFlatsByBldgcodeAndWingAndFlatnum(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum) {
		return this.flatsService.fetchFlatsByBldgcodeAndWingAndFlatnum(bldgcode, wing, flatnum) ; 
	}

	@PostMapping("/add-flats")
	public ResponseEntity<?> addFlats(@Valid @RequestBody FlatsRequestBean flatsRequestBean)  {
		return this.flatsService.addFlats(flatsRequestBean);
	}

	@PutMapping("/update-flats")
	public ResponseEntity<?> updateFlats(@Valid @RequestBody FlatsRequestBean flatsRequestBean)  {
		return this.flatsService.updateFlats(flatsRequestBean);
	}

	@GetMapping("/check-Bldgcode-and-Wing-and-Flatnum-exists")
	public ResponseEntity<?> checkBldgcodeAndWingAndFlatnumExists(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum)  {
		return this.flatsService.checkBldgcodeAndWingAndFlatnumExists(bldgcode, wing, flatnum);
	}

	@DeleteMapping("/delete-flats")
	public ResponseEntity<?> deleteFlats(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum)  {
		return this.flatsService.deleteFlats(bldgcode, wing, flatnum) ; 
	}
}
