package kraheja.fd.deposit.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.fd.deposit.bean.request.DepositDischargeRequestBean;
import kraheja.fd.deposit.bean.request.DepositRequestBean;
import kraheja.fd.deposit.bean.request.DepositTransferRequestBean;
import kraheja.fd.deposit.service.DepositService;


@RestController
@RequestMapping("/deposit")
public class DepositController {

	@Autowired
	private DepositService depositService; 

	@GetMapping("/fetch-deposit-by-companycode-and-depositorid-and-receiptno")
	public ResponseEntity<?> fetchDepositByCompanycodeAndDepositoridAndReceiptno(String depositorId, String companyCode, String recieptNo) {
		return this.depositService.fetchDepositByCompanycodeAndDepositoridAndReceiptno(depositorId, companyCode, recieptNo);
	}
	
	@GetMapping("/validate-deposit-printrev")
	public ResponseEntity<?> validateDepositPrintRev(String depositorId, String companyCode, String recieptNo) {
		return this.depositService.validateDepositPrintRev(depositorId, companyCode, recieptNo);
	}
	
	
	@GetMapping("/fetch-deposits-by-companycode-and-depositorid")
	public ResponseEntity<?> fetchDepositsByCompanycodeAndDepositorid(String depositorId, String companyCode) {
		return this.depositService.fetchDepositsByCompanycodeAndDepositorid(depositorId, companyCode);
	}
	
	@PostMapping("/add-deposit")
	public ResponseEntity<?> addDeposit(@Valid @RequestBody DepositRequestBean depositRequestBean) {
		return this.depositService.addDeposit(depositRequestBean);
	}
	
	@GetMapping("/check-reciept-number-exists")
	public ResponseEntity<?> checkRecieptNumberExists(String depositorId, String recieptNo) {
		return this.depositService.checkRecieptNumberExists(depositorId, recieptNo);
	}
	
	@PostMapping("/add-deposit-renew")
	public ResponseEntity<?> addDepositRenew(@Valid @RequestBody DepositRequestBean depositRequestBean) {
		return this.depositService.addDepositRenew(depositRequestBean);
	}
	
	@GetMapping("/fetch-interest-rate")
	public ResponseEntity<?> fetchInterestRate(String depositor, String companyCode, String receiptNumber, String maturityDate, String dischargeDate) {
		return this.depositService.fetchInterestRate(depositor, companyCode, receiptNumber, maturityDate, dischargeDate);
	}
	
	@PostMapping("/calculate-interest-for-discharge")
	public ResponseEntity<?> calculateInterestForDischarge(@Valid @RequestBody DepositDischargeRequestBean depositDischargeRequestBean) {
		return this.depositService.calculateInterestForDischarge(depositDischargeRequestBean);
	}
	
	@PostMapping("/discharge-deposit")
	public ResponseEntity<?> dischargeDeposit(@Valid @RequestBody DepositDischargeRequestBean depositDischargeRequestBean) {
		return this.depositService.dischargeDeposit(depositDischargeRequestBean);
	}
	
	@PostMapping("/transfer-deposit")
	public ResponseEntity<?> transferDeposit(@Valid @RequestBody DepositTransferRequestBean depositTransferRequestBean) {
		return this.depositService.transferDeposit(depositTransferRequestBean);
	}
}
