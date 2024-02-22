package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.GstService;

@RestController
@RequestMapping("/common-gst")
public class GstController {
	
	@Autowired
	private GstService gstService;

	@GetMapping("/fetch-gst-perc")
	public ResponseEntity<?> fetchGstPerc(String hsncode, String partycode, String adowner, String segment, String adrPartySegment, String adPartyType, String adtype, String suppbilldt, String ser, Boolean isUpdate){
		return this.gstService.fetchGstPerc(hsncode, partycode, adowner, segment, adrPartySegment, adPartyType, adtype, suppbilldt, ser, isUpdate); 
	}
}
