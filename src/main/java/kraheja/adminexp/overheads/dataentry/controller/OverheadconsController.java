package kraheja.adminexp.overheads.dataentry.controller;

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

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsRequestBean;
import kraheja.adminexp.overheads.dataentry.repository.OverheadconsRepository;
import kraheja.adminexp.overheads.dataentry.service.OverheadconsService;


@RestController
@RequestMapping("/overheadcons")
public class OverheadconsController {
	@Autowired
	private OverheadconsService overheadconsService;

	@Autowired
	private OverheadconsRepository overheadconsRepository;
	
	@GetMapping("/fetch-overheadcons-by-Connocode")
	public ResponseEntity<?> findByohdhconnocode(String connocode) throws ParseException{
			return this.overheadconsService.findByohdhconnocode(connocode);
	}
	
	@GetMapping("/fetch-LocnamePaycoynameBillcoyname-overheadcons-by-Connocode")
	public ResponseEntity<?> fetchLocnamePaycoynameBillcoyname(String connocode) throws ParseException{
			return this.overheadconsService.fetchLocnamePaycoynameBillcoyname(connocode);
	}
	
	@PostMapping("/add-overheadcons")
	public ResponseEntity<?> addOverheadcons(@Valid @RequestBody OverheadconsRequestBean overheadconsRequestBean) throws ParseException {
		return this.overheadconsService.addOverheadcons(overheadconsRequestBean);
	}

	@PutMapping("/update-overheadcons")
	public ResponseEntity<?> updateOverheadcons(@Valid @RequestBody OverheadconsRequestBean overheadconsRequestBean) throws ParseException {
		return this.overheadconsService.updateOverheadcons(overheadconsRequestBean);
	}

	@GetMapping("/check-Connocode-exists")
	public ResponseEntity<?> checkConnocodeExists(@RequestParam(value = "connocode") String  connocode) throws ParseException {
		return this.overheadconsService.checkConnocodeExists(connocode);
	}

	@GetMapping("/check-Consumer-exists")
	public ResponseEntity<?> checkConsumnerNoExists(@RequestParam(value = "consumerno") String  consumerno) throws ParseException {
		return this.overheadconsService.checkConsumnerNoExists(consumerno);
	}
	
	@GetMapping("/check-Consumer-billexists")
	public String findByBillGeneratedInoverheadtxn(@RequestParam(value = "connocode") String  connocode) throws ParseException {
		return this.overheadconsRepository.findByBillGeneratedInoverheadtxn(connocode);
	}
	
	@GetMapping("/check-MaxYearMonth-InCompany")
	public String findMaxYearmonthIncompay(@RequestParam(value = "ohddCoycode") String  ohddCoycode) throws ParseException {
		return this.overheadconsRepository.findMaxYearmonthIncompay(ohddCoycode);
				
	}
	
}
