package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.fd.deposit.bean.request.DepositDischargeRequestBean;
import kraheja.fd.deposit.bean.request.DepositRequestBean;
import kraheja.fd.deposit.bean.request.DepositTransferRequestBean;

public interface DepositService {

	ResponseEntity<?> fetchDepositByCompanycodeAndDepositoridAndReceiptno(String depositorId, String companyCode, String recieptNo);

	ResponseEntity<?> addDeposit(DepositRequestBean depositRequestBean);
	
	ResponseEntity<?> checkRecieptNumberExists(String depositorId, String recieptNumber);

	ResponseEntity<?> fetchDepositsByCompanycodeAndDepositorid(String depositorId, String companyCode);

	ResponseEntity<?> addDepositRenew(DepositRequestBean depositRequestBean);

	ResponseEntity<?> fetchInterestRate(String depositor, String companyCode, String receiptNumber, String maturityDate, String dischargeDate);

	ResponseEntity<?> calculateInterestForDischarge(DepositDischargeRequestBean depositDischargeRequestBean);

	ResponseEntity<?> dischargeDeposit(DepositDischargeRequestBean depositDischargeRequestBean);

	ResponseEntity<?> transferDeposit(DepositTransferRequestBean depositTransferRequestBean);
	
	ResponseEntity<?> validateDepositPrintRev(String depositor, String companyCode, String receiptNum); 
}
