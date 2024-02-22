package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;

public interface CommonMethodsService {

	ResponseEntity<ServiceResponseBean> deleteRowsFromTempTable(String tempTableName, String sesIdColName,
			String sessionId);

}
