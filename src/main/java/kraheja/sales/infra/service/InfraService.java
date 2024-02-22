package kraheja.sales.infra.service;


import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.InfraDefaultersListRequestBean;

public interface InfraService {

	ResponseEntity<?> addIntoInfraDefaultersListTempTable(InfraDefaultersListRequestBean infraDefaultersListRequestBean);

	ResponseEntity<?> deleteInfraDefaultersListFromSessionId(Integer sessionId);
	
	ResponseEntity<?> fetchGstFlag(String recNum);
	
	ResponseEntity<?> fetchCarParks(String bldgCode , String wing, String flatNo);

	ResponseEntity<?> fetchAdvanceFlag(String bldgCode , String wing, String flatNo,String recNum,String gstYN);
}