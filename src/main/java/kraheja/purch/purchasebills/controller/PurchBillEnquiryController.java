package kraheja.purch.purchasebills.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.purch.purchasebills.service.PurchBillEnquiryService;

@RestController
@RequestMapping("/purchase-bills-enquiry")
public class PurchBillEnquiryController {

	@Autowired
	private PurchBillEnquiryService purchBillEnquiryService;

	@GetMapping("/fetch-bill-details")
	public ResponseEntity<?> fetchBillDetails(String suppCode, String billNo, String challanNo, String matGroup, String matCode, String itemCode, String radioButton){
		return this.purchBillEnquiryService.fetchBillDetails(suppCode, billNo, challanNo, matGroup, matCode , itemCode, radioButton); 
	}
	
	@GetMapping("/fetch-new-bill-details")
	public ResponseEntity<?> fetchNewBillDetails(String partyCode, String suppCode){
		return this.purchBillEnquiryService.fetchNewBillDetails(partyCode, suppCode); 
	}
}