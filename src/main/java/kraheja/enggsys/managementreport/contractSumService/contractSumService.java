package kraheja.enggsys.managementreport.contractSumService;

import org.springframework.http.ResponseEntity;

import kraheja.enggsys.bean.request.contractSumRequestBean;


public interface contractSumService {

	ResponseEntity<?> addIntoContractSummaryTempTable(contractSumRequestBean contractSumRequestBean);

	ResponseEntity<?> deleteContractSummaryFromSessionId(Integer sessionId);

	ResponseEntity<?> fetchMatcertGroup(String bldgCode);
	
}