package kraheja.sales.outgoing.controller;

import java.text.ParseException;
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

import kraheja.sales.bean.request.OutrateRequestBean;
import kraheja.sales.outgoing.service.OutrateService;

@RestController
@RequestMapping("/outrate")
public class OutrateController {

	@Autowired
	private OutrateService outrateService;

	@GetMapping("/fetch-outrate-by-Bldgcode-and-Flatnum-and-Wing-and-Startdate")
	public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWingAndStartdate(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "wing") String  wing, @RequestParam(value = "startdate") String  startdate) throws ParseException {
		return this.outrateService.fetchOutrateByBldgcodeAndFlatnumAndWing(bldgcode, flatnum, wing) ; 
	}

	@GetMapping("/fetch-outrate-by-Bldgcode-and-Flatnum-and-Wing")
	public ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWing(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "wing") String  wing) throws ParseException {
		return this.outrateService.fetchOutrateByBldgcodeAndFlatnumAndWing(bldgcode, flatnum, wing) ; 
	}

//	@PostMapping("/add-outrate")
//	public ResponseEntity<?> addOutrate(@Valid @RequestBody List <OutrateRequestBean> outrateRequestBean) throws ParseException {
//		return this.outrateService.addOutrate(outrateRequestBean);
//	}

	@PutMapping("/update-outrate")
	public ResponseEntity<?> updateOutrate(@Valid @RequestBody List <OutrateRequestBean> outrateRequestBean) throws ParseException {
		return this.outrateService.updateOutrate(outrateRequestBean);
	}

//	@GetMapping("/check-Bldgcode-and-Flatnum-and-Wing-and-Startdate-exists")
//	public ResponseEntity<?> checkBldgcodeAndFlatnumAndWingAndStartdateExists(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "wing") String  wing, @RequestParam(value = "startdate") String  startdate) throws ParseException {
//		return this.outrateService.checkBldgcodeAndFlatnumAndWingAndStartdateExists(bldgcode, flatnum, wing, startdate);
//	}

	@DeleteMapping("/delete-outrate")
	public ResponseEntity<?> deleteOutrate(@RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "wing") String  wing, @RequestParam(value = "startdate") String  startdate) throws ParseException {
		return this.outrateService.deleteOutrate(bldgcode, flatnum, wing, startdate) ; 
	}
	
	
}