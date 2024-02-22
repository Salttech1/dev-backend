package kraheja.fd.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.DepintRequestBean;
import kraheja.fd.deposit.service.DepIntService;


@RestController
@RequestMapping("/depint")
public class DepIntController {

	@Autowired
	private DepIntService depIntService; 

	@GetMapping("/fetch-cheque-by-interestFromDate-and-interestUpToDate-and-companyCode")
	public ResponseEntity<ServiceResponseBean> fetchChequeByInterestFromDateAndInterestUpToDate(String intFrom, String intUpTo, String coy) {
		return this.depIntService.fetchChequeByInterestFromDateAndInterestUpToDate(intFrom, intUpTo, coy);
	}
	
	@GetMapping("/fetch-cheque-by-depositorId-and-recieptNum-and-companyCode")
	public ResponseEntity<ServiceResponseBean> fetchChequeByDepositorAndReceiptNumAndCoy(String depositorId, String receiptNum, String coy) {
		return this.depIntService.fetchChequeByDepositorAndReceiptNumAndCoy(depositorId, receiptNum, coy);
	}
	
	@PutMapping("/update-bankCode-and-chequeNumber")
	public ResponseEntity<ServiceResponseBean> updateBankCodeAndChequeNumber(@RequestBody DepintRequestBean depintRequestBean) {
		return this.depIntService.updateBankCodeAndChequeNumber(depintRequestBean);
	}
	
	@PutMapping("/update-cheque-by-depositorId-and-recieptNum-and-companyCode")
	public ResponseEntity<ServiceResponseBean> updateChequeByDepositorAndReceiptNumAndCoy(@RequestBody DepintRequestBean depintRequestBean) {
		return this.depIntService.updateChequeByDepositorAndReceiptNumAndCoy(depintRequestBean);
	}
	
	@GetMapping("/fetch-interest-by-depositorId-and-recieptNum-and-companyCode")
	public ResponseEntity<ServiceResponseBean> fetchInterestByDepositorAndReceiptNumAndCoy(String depositorId, String receiptNum, String coy, String fromdate, String todate) {
		return this.depIntService.fetchInterestByDepositorAndReceiptNumAndCoy(depositorId, receiptNum, coy,fromdate, todate);
	}
}
