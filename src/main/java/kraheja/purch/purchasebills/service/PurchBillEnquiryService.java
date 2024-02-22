package kraheja.purch.purchasebills.service;

import org.springframework.http.ResponseEntity;

public interface PurchBillEnquiryService {

	ResponseEntity<?> fetchBillDetails(String suppCode, String billNo, String challanNo, String matGroup, String matCode, String itemCode, String radioButton);

	ResponseEntity<?> fetchNewBillDetails(String partyCode, String suppCode);
}