package kraheja.arch.projbldg.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.arch.projbldg.dataentry.bean.request.BldgmapRequestBean;
import kraheja.arch.projbldg.dataentry.service.BldgmapService;

@RestController
@RequestMapping("/bldgmap")
public class BldgmapController {

	@Autowired
	private BldgmapService bldgmapService;

	@GetMapping("/fetch-bldgmap-by-Ebldgcode-and-Abldgcode")
	public ResponseEntity<?> fetchBldgmapByEbldgcodeAndAbldgcode(@RequestParam(value = "ebldgcode") String  ebldgcode, @RequestParam(value = "abldgcode") String  abldgcode) throws ParseException {
		return this.bldgmapService.fetchBldgmapByEbldgcodeAndAbldgcode(ebldgcode, abldgcode) ;
		
	}

	@PostMapping("/add-bldgmap")
	public ResponseEntity<?> addBldgmap(@Valid @RequestBody BldgmapRequestBean bldgmapRequestBean) throws ParseException {
		return this.bldgmapService.addBldgmap(bldgmapRequestBean);
	}

	@PutMapping("/update-bldgmap")
	public ResponseEntity<?> updateBldgmap(@Valid @RequestBody BldgmapRequestBean bldgmapRequestBean) throws ParseException {
		return this.bldgmapService.updateBldgmap(bldgmapRequestBean);
	}

	@GetMapping("/check-Ebldgcode-and-Abldgcode-exists")
	public ResponseEntity<?> checkEbldgcodeAndAbldgcodeExists(@RequestParam(value = "ebldgcode") String  ebldgcode, @RequestParam(value = "abldgcode") String  abldgcode) throws ParseException {
		return this.bldgmapService.checkEbldgcodeAndAbldgcodeExists(ebldgcode, abldgcode);
	}

}