package kraheja.fd.deposit.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.DepintRequestBean;

public interface DepIntService {

	ResponseEntity<ServiceResponseBean> fetchChequeByInterestFromDateAndInterestUpToDate(String intFrom, String intUpTo, String coy);

	ResponseEntity<ServiceResponseBean> fetchChequeByDepositorAndReceiptNumAndCoy(String depositorId, String receiptNum, String coy);
	
	ResponseEntity<ServiceResponseBean> updateBankCodeAndChequeNumber(DepintRequestBean depintRequestBean);

	ResponseEntity<ServiceResponseBean> updateChequeByDepositorAndReceiptNumAndCoy(DepintRequestBean depintRequestBean);

	ResponseEntity<ServiceResponseBean> fetchInterestByDepositorAndReceiptNumAndCoy(String depositorId, String receiptNum, String coy, String fromdate, String todate);
}
