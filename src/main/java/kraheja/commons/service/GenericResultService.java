package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface GenericResultService {
	
	ResponseEntity<?> fetchResultByTableColumn(String fetchcolumn, String tableName, String columnName, String columnValue);
}
