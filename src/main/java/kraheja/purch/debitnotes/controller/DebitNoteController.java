package kraheja.purch.debitnotes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.purch.bean.request.DbnotehRequestBean;
import kraheja.purch.debitnotes.service.DebitNoteService;


@RestController
@RequestMapping("/dbnote")
public class DebitNoteController {

	@Autowired
	private DebitNoteService debitNoteService;

	@GetMapping("/fetch-dbnoteh-by-ser")
	public ResponseEntity<?> fetchDbnoteByDbnoteser(String  ser)  {
		return this.debitNoteService.fetchDbnoteByDbnoteser(ser) ; 
	}
	
	
	@GetMapping("/fetch-dbnoteh-for-reversal-by-ser")
	public ResponseEntity<?> fetchDbnoteForReversalByDbnoteser(String  ser)  {
		return this.debitNoteService.retrieveDbnoteForReversal(ser); 
	}
	
	@GetMapping("/check-supplier-bill-number")
	public ResponseEntity<?> fetchPbillhBySuppBillNo(String partyCode, String supplierBillNo)  {
		return this.debitNoteService.fetchPbillhByPartyCodeAndSuppBillNo(partyCode,supplierBillNo) ; 
	}
	
	@GetMapping("/check-quantity-valid")
	public ResponseEntity<?> checkIsQuantityValid(String partyCode, String supplierBillNo, String dbNoteSer, String itemQTy, Double debitAmount)  {
		return this.debitNoteService.checkIsQuantityValid(partyCode, supplierBillNo, dbNoteSer, itemQTy, debitAmount) ; 
	}
	
	@GetMapping("/check-debit-amount-valid")
	public ResponseEntity<?> checkIsDebitAmountValid(String partyCode, String supplierBillNo,Double debitAmount)  {
		return this.debitNoteService.checkIsDebitAmountValid(partyCode, supplierBillNo, debitAmount);
	}
	
	@GetMapping("/check-uom-valid")
	public ResponseEntity<?> checkIsUomValid(String matgroup, String code)  {
		return this.debitNoteService.checkIsUomValid(matgroup, code);
	}
	
	@PutMapping("/update-dbnote")
	public ResponseEntity<?> updateDbnote(@Valid @RequestBody DbnotehRequestBean dbnotehRequestBean)  {
		return this.debitNoteService.updateDbnote(dbnotehRequestBean);
	}
	
	@PostMapping("/add-dbnote")
	public ResponseEntity<?> addDbnote(@RequestBody DbnotehRequestBean dbnotehRequestBean)  {
		return this.debitNoteService.addDbnote(dbnotehRequestBean);
	}
	
	@PostMapping("/reverse-dbnote")
	public ResponseEntity<?> reverseDebitNote(String dbNoteSer, String matGroup, Double qty){
		return this.debitNoteService.reverseDbnote(dbNoteSer, matGroup, qty);
	}
}