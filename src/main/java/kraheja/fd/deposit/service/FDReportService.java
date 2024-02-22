package kraheja.fd.deposit.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.NeftReportRequestBean;

public interface FDReportService {

	ResponseEntity<ServiceResponseBean> fetchNeftReportFromDateAndTodateAndCoyBank();
	
	ResponseEntity<?> exportNeftReport(NeftReportRequestBean neftReportRequestBean);
	
	ResponseEntity<?> findMaxranDateByTranserNos(List<String> transerNos);
	
	ResponseEntity<?> fetchFixedDepositRecieptReportParameters(String comanyCode, String depositorId, String receiptNum);

}
