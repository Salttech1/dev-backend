package kraheja.enggsys.certificatesystem.dataentry.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.enggsys.bean.request.CertificateRequestBean;
import kraheja.enggsys.bean.request.ContractRequestBean;
import kraheja.enggsys.bean.request.ContractdebitRequestBean;
import kraheja.enggsys.certificatesystem.dataentry.service.CertificateService;

@RestController
@RequestMapping("/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	/**
	 * API start for Create Rec Id Screen
	 */
	@GetMapping("/fetch-contract-details-by-recid")
	public ResponseEntity<ServiceResponseBean> fetchContractDetailsByRecId(@RequestParam String recid){
		return this.certificateService.fetchContractDetailsByRecId(recid); 
	}
	
	@GetMapping("/fetch-coy-name-for-enggsys")
	public ResponseEntity<ServiceResponseBean> fetchCoynameForEnggsys(@RequestParam String bldgcode){
		return this.certificateService.fetchCoynameForEnggsys(bldgcode); 
	}
	
	@GetMapping("/fetch-certnum-by-recid")
	public ResponseEntity<ServiceResponseBean> fetchCertnumRecid(@RequestParam String recid){
		return this.certificateService.fetchCertnumRecid(recid); 
	}
	
	@PostMapping("/add-contract-detail")
	public ResponseEntity<ServiceResponseBean> addContractDetail(@RequestBody ContractRequestBean contractRequestBean){
		return this.certificateService.addContractDetail(contractRequestBean); 
	}
	
	@PostMapping("/save-contract-detail")
	public ResponseEntity<ServiceResponseBean> saveContractDetail(@RequestBody ContractRequestBean contractRequestBean){
		return this.certificateService.saveContractDetail(contractRequestBean); 
	}
	/**
	 * API end for Create Rec Id Screen

	=====================================================================

	 * API start for Contract Bill Entry/Edit
	 */
	@GetMapping("/fetch-contract-bill-detail-by-ser")
	public ResponseEntity<ServiceResponseBean> fetchContractBillDetailBySer(@RequestParam String ser){
		return this.certificateService.fetchContractBillDetailBySer(ser); 
	}
	
	@GetMapping("/add-new-contract-bill-detail")
	public ResponseEntity<ServiceResponseBean> addNewContractBillDetailBySer(@RequestBody ContractRequestBean contractRequestBean){
		return this.certificateService.addNewContractBillDetailBySer(contractRequestBean); 
	}
	/**
	 * API end for Contract Bill Entry/Edit
	 */
	
	@GetMapping("/fetch-certificate-entry-by-certnum")
	public ResponseEntity<ServiceResponseBean> fetchCertificateEntryByCertnum(@RequestParam String certnum){
		return this.certificateService.fetchCertificateEntryByCertnum(certnum); 
	}
	
	@PostMapping("/add-contract-debit")
	public ResponseEntity<ServiceResponseBean> addContractDebit(@RequestBody List<ContractdebitRequestBean> contractdebitRequestBean){
		return this.certificateService.addContractDebit(contractdebitRequestBean); 
	}
	
	
	@PutMapping("/update-contract-debit")
	public ResponseEntity<ServiceResponseBean> updateContractDebit(@RequestBody List<ContractdebitRequestBean> contractdebitRequestBean){
		return this.certificateService.updateContractDebit(contractdebitRequestBean); 
	}
	
	@PutMapping("/reverse-contract-debit")
	public ResponseEntity<ServiceResponseBean> reverseContratcDebit(@RequestBody List<ContractdebitRequestBean> contractdebitRequestBean){
		return this.certificateService.reverseContratcDebit(contractdebitRequestBean); 
	}
	
	
	@GetMapping("/check-contract-debit-reversal-by-debittype-authnum")
	public ResponseEntity<ServiceResponseBean> checkIsContractDebitAuthorised(String debitType, String authnum){
		return this.certificateService.checkIsContractDebitAuthorised(debitType,authnum); 
	}
	
	
	@GetMapping("/fetch-bldgcode-partycode-workcode-by-contractid")
	public ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndPartyCodeAndWorkCodeByContractId(@RequestParam String contractId){
		return this.certificateService.fetchContractBlgdCodeAndPartyCodeAndWorkCodeByContractId(contractId); 
	}
	
	@GetMapping("/fetch-bldgcode-coy-contractor-by-contractid")
	public ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndCoyAndContractorByContractId(@RequestParam String contractId){
		return this.certificateService.fetchContractBlgdCodeAndCoyAndContractorByContractId(contractId); 
	}
	
	@GetMapping("/fetch-bldgcode-coy-by-authnum")
	public ResponseEntity<ServiceResponseBean> fetchAuthBldgCodeAndCoy(@RequestParam String authnum){
		return this.certificateService.fetchAuthBldgCodeAndCoy(authnum); 
	}
	
	@GetMapping("/fetch-contract-debitnote-by-ser")
	public ResponseEntity<ServiceResponseBean> fetchContractDebitNoteBySer(@RequestParam String dbnoteser){
		return this.certificateService.fetchContractDebitNoteBySer(dbnoteser); 
	}
	
	@GetMapping("/fetch-contract-bill-by-recid-and-billno")
	public ResponseEntity<ServiceResponseBean> fetchContractBillByRecIdAndBillNo(@RequestParam  String recId, 
			@RequestParam	String billNo){
		return this.certificateService.fetchContractBillByRecIdAndBillNo(recId, billNo); 
	}
	
	@PostMapping("/fetch-contract-debit-by-debittype-authnum-recId-certType-runser-bldg")
	public ResponseEntity<ServiceResponseBean> fetchContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg
			(@RequestBody Map<String, Object> data) {
		return this.certificateService.fetchContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg(data);
	}
	
	
	@GetMapping("/fetch-partycode-by-recId")
	public ResponseEntity<ServiceResponseBean> fetchPartyByRecId
			(String recId) {
		return this.certificateService.fetchPartyByRecId(recId);
	}
	
	@GetMapping("/check-debit-amount-valid")
	public ResponseEntity<ServiceResponseBean> checkIsDebitAmountValid
	(String recId, String billNo, Double debitAmount) {
		return this.certificateService.checkIsDebitAmountValid(recId, billNo, debitAmount);
	}
	
	@PostMapping("/add-certificate-detail")
	public ResponseEntity<ServiceResponseBean> addCertificateDetail(@RequestBody CertificateRequestBean certificateRequestBean){
		return this.certificateService.addCertificateDetail(certificateRequestBean); 
	}

}