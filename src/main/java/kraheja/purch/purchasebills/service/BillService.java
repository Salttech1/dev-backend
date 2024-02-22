package kraheja.purch.purchasebills.service;


import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.PartyAddressRequestBean;
import kraheja.purch.bean.request.PbillhRequestBean;
import kraheja.purch.bean.request.PurchBillOutstandingRequestBean;

public interface BillService {

	ResponseEntity<?> fetchPurchaseBillBySer(String ser, Boolean isPurchBill) ;
	
	ResponseEntity<?> fetchPurchaseBillBySerForReversal(String ser) ;

	ResponseEntity<?> addNewGstBill(PbillhRequestBean pbillhRequestBean);
	
	ResponseEntity<?> fetchPartyAndAddressDetails(String partyCode);
	
	ResponseEntity<?> updatePartyAndAddressDetails(PartyAddressRequestBean partyAddressRequestBean);

	ResponseEntity<?> checkSupplierBillNumberExists(String supplierNumber, String partyCode);
	
	ResponseEntity<?> checkIsBillTypeValid(String partyCode, String billType);

	ResponseEntity<?> checkIsDebitTypeValid(String buildingCode, String debitType);
	
	ResponseEntity<?> fetchUomByMatGroupAndMatLevel(String matGroup, String matLevel, String suppBillDate, String billType, String deuom);
	
	ResponseEntity<?> addIntoPurchBillOutstandingTempTable(PurchBillOutstandingRequestBean purchBillOutstandingRequestBean);

	ResponseEntity<?> calculateTds(String suppbilldt, Double amount, String billtype, String partycode, String coy);

	ResponseEntity<?> validateSuppBillDate(String date);

	ResponseEntity<?> validateChallanDetail(String dcno, String partycode, String serNo, String suppbilldt);

	ResponseEntity<?> updatePurchBillAdjustement(String ser);
	
	ResponseEntity<?> reversePurchaseBill(String ser, String reverseBillType);

	ResponseEntity<?> validateUomDetail(String deUom);
	
	ResponseEntity<?> fetchPbillSerialNoByBuildingAndPartyAndSuppBilNo(String blgCode, String partyCode, String supplierNumber);

	ResponseEntity<?> uomConversionAndRate(String matgroup, String deUom, Double amount, Double qty);
	
	ResponseEntity<?> findPblhAmountBySuppNumberAndPartyCodeAndBuildingCodeAndSuppBilldtAndAuthNum(String supplierNumber, String partyCode, String buildingCode, String  suppBilldt, String authNum);

	ResponseEntity<?> addIntoAgeingTempTable(PurchBillOutstandingRequestBean purchBillOutstandingRequestBean);

	ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId, Boolean isAgeing);
}