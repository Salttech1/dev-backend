package kraheja.purch.purchasebills.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.PartyAddressRequestBean;
import kraheja.purch.bean.request.PbillhRequestBean;
import kraheja.purch.bean.request.PurchBillOutstandingRequestBean;
import kraheja.purch.purchasebills.service.BillService;

@RestController
@RequestMapping("/purchase-bills")
public class BillController {

	@Autowired
	private BillService pbillhService;

	@GetMapping("/fetch-purchase-bill-by-ser")
	public ResponseEntity<?> fetchPurchaseBillBySer(String ser, Boolean isPurchBill){
		return this.pbillhService.fetchPurchaseBillBySer(ser, isPurchBill); 
	}
	
	@GetMapping("/fetch-purchase-bill-for-reversal-by-ser")
	public ResponseEntity<?> fetchPurchaseBillForReversalBySer(String ser){
		return this.pbillhService.fetchPurchaseBillBySerForReversal(ser) ; 
	}
	
	@PostMapping("/add-new-gst-bill")
	public ResponseEntity<?> addNewGstBill(@Valid @RequestBody PbillhRequestBean pbillhRequestBean){
		return this.pbillhService.addNewGstBill(pbillhRequestBean);
	}

	@GetMapping("/fetch-party-and-address-details")
	public ResponseEntity<?> fetchPartyAndAddressDetails(String partyCode){
		return this.pbillhService.fetchPartyAndAddressDetails(partyCode) ; 
	}

	@GetMapping("/calculate-tds")
	public ResponseEntity<?> calculateTds(String suppbilldt, Double amount, String billtype, String partycode, String coy){
		return this.pbillhService.calculateTds(suppbilldt, amount, billtype, partycode, coy) ; 
	}

	@GetMapping("/fetch-uom-by-mat-group-and-mat-level")
	public ResponseEntity<?> fetchUomByMatGroup(String matGroup, String matLevel, String suppBillDate, String billType, String deuom){
		return this.pbillhService.fetchUomByMatGroupAndMatLevel(matGroup, matLevel, suppBillDate, billType, deuom);
	}

	@PutMapping("/update-party-and-address-details")
	public ResponseEntity<?> updatePartyAndAddressDetails(@Valid @RequestBody PartyAddressRequestBean partyAddressRequestBean){
		return this.pbillhService.updatePartyAndAddressDetails(partyAddressRequestBean) ; 
	}

	@GetMapping("/check-supplier-bill-number-exists")
	public ResponseEntity<?> checkSupplierBillNumberExists(String supplierNumber, String partyCode){
		return this.pbillhService.checkSupplierBillNumberExists(supplierNumber, partyCode) ; 
	}

	@GetMapping("/check-bill-type-valid")
	public ResponseEntity<?> checkIsBillTypeValid(String partyCode, String billType){
		return this.pbillhService.checkIsBillTypeValid(partyCode, billType) ; 
	}

	@GetMapping("/check-debit-type-valid")
	public ResponseEntity<?> checkIsDebitTypeValid(String buildingCode, String debitType){
		return this.pbillhService.checkIsDebitTypeValid(buildingCode, debitType) ; 
	}

	@PostMapping("/add-into-purch-bill-outstanding-temp-table")
	public ResponseEntity<?> addIntoPurchBillOutstandingTempTable(@RequestBody PurchBillOutstandingRequestBean purchBillOutstandingRequestBean){
		return this.pbillhService.addIntoPurchBillOutstandingTempTable(purchBillOutstandingRequestBean); 
	}
	
	@PostMapping("/add-into-ageing-temp-table")
	public ResponseEntity<?> addIntoAgeingTempTable(@RequestBody PurchBillOutstandingRequestBean purchBillOutstandingRequestBean){
		return this.pbillhService.addIntoAgeingTempTable(purchBillOutstandingRequestBean); 
	}

	@GetMapping("/validate-suppbilldt")
	public ResponseEntity<?> validateSuppBillDate(String date){
		return this.pbillhService.validateSuppBillDate(date); 
	}
//Test Commit
	@GetMapping("/validate-challan-detail")
	public ResponseEntity<?> validateChallanDetail(String dcno, String partycode, String serNo, String suppbilldt){
		return this.pbillhService.validateChallanDetail(dcno, partycode, serNo, suppbilldt); 
	}

	@PutMapping("/update-purch-bill-adjustement")
	public ResponseEntity<?> updatePurchBillAdjustement(String ser) {
		return this.pbillhService.updatePurchBillAdjustement(ser);
	}
	
	@GetMapping("/validate-uom-detail")
	public ResponseEntity<?> validateUomDetail(String deUom){
		return this.pbillhService.validateUomDetail(deUom); 
	}
	
	@GetMapping("/validate-uom-conversion-rate")
	public ResponseEntity<?> uomConversionAndRate(String matgroup, String deUom, Double amount, Double qty){
		return this.pbillhService.uomConversionAndRate(matgroup, deUom, amount, qty); 
	}
	
	@GetMapping("/fetch-bill-ser-by-building-and-party-and-supbill-no")
	public ResponseEntity<?> fetchPbillSerialNoByBuildingAndPartyAndSuppBilNo(String blgCode, String partyCode, String supplierNumber){
		return this.pbillhService.fetchPbillSerialNoByBuildingAndPartyAndSuppBilNo(blgCode, partyCode, supplierNumber); 
	}
	
	
	@GetMapping("/fetch-bill-amount-by-supplier-number-party-code-building-code-suppbilldt-autno")
	public ResponseEntity<?> fetchBillAmount(String supplierNumber, String partyCode, String buildingCode, String  suppBilldt, String authNum){
		return this.pbillhService.findPblhAmountBySuppNumberAndPartyCodeAndBuildingCodeAndSuppBilldtAndAuthNum(supplierNumber, partyCode, buildingCode, suppBilldt, authNum);
	}
	
	@PostMapping("/reverse-bill")
	public ResponseEntity<?> reverseBill(String billSer, String reverseType){
		return this.pbillhService.reversePurchaseBill(billSer, reverseType);
	}
	
	@DeleteMapping("/delete-temp-table-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId, Boolean isAgeing) {
		return this.pbillhService.deleteTempTableFromSessionId(sessionId, isAgeing);
	}
}