package kraheja.purch.debitnotes.service;

import org.springframework.http.ResponseEntity;

import kraheja.purch.bean.request.DbnotehRequestBean;

public interface DebitNoteService {
	ResponseEntity<?> fetchDbnoteByDbnoteser(String ser);
	
	ResponseEntity<?> fetchPbillhByPartyCodeAndSuppBillNo(String partyCode,String supplierBillNo);
	
	ResponseEntity<?> updateDbnote(DbnotehRequestBean dbnotehRequestBean);
	
	ResponseEntity<?> addDbnote(DbnotehRequestBean dbnotehRequestBean);
	
	ResponseEntity<?> checkIsQuantityValid(String partyCode, String supplierBillNo, String dbNoteSer, String itemQTy, Double debitAmount);
	
	ResponseEntity<?> checkIsDebitAmountValid(String partyCode, String supplierBillNo,Double debitAmount);

	ResponseEntity<?> retrieveDbnoteForReversal(String dbNoteSer);
	
	ResponseEntity<?> reverseDbnote(String dbNoteSer, String matGroup, Double qty);
	
	ResponseEntity<?> checkIsUomValid(String matgroup, String code);
}