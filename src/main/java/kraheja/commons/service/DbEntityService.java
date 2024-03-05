package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface DbEntityService {
	ResponseEntity<?> fetchByClass(String clazz);

	ResponseEntity<?> fetchNum1ByClassAndId(String clazz, String id);

	ResponseEntity<?> fetchChar1ByClassAndId(String clazz, String id, String extraWhereClause); // 21.12.23 RS
}
