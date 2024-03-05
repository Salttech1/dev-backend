package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.DbEntityService;

@RestController
@RequestMapping("/entity")
public class DbEntityController {

	@Autowired
	private DbEntityService dbEntityService;

	@GetMapping("/fetch-by-class")
	public ResponseEntity<?> fetchByClass(String clazz) {
		return this.dbEntityService.fetchByClass(clazz);
	}

	@GetMapping("/fetch-num1-by-class-and-id")
	public ResponseEntity<?> fetchNum1ByClassAndId(String clazz, String id) {
		return this.dbEntityService.fetchNum1ByClassAndId(clazz, id);
	}

	// 21.12.23 RS
	@GetMapping("/fetch-char1-by-class-and-id")
	public ResponseEntity<?> fetchChar1ByClassAndId(String clazz, String id, String extraWhereClause) {
		return this.dbEntityService.fetchChar1ByClassAndId(clazz, id, extraWhereClause);
	}
}
