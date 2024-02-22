package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.GenericResultService;

@RestController
@RequestMapping("/generic-result")
public class GenericResultController {
	
	@Autowired
	private GenericResultService genericResultService;
	
	@GetMapping("/fetch-result-by-table-column")
	public ResponseEntity<?> fetchResultByTableColumn(String fetchcolumn, String tableName,String ColumnName,String ColumnValue){
		return this.genericResultService.fetchResultByTableColumn(fetchcolumn,tableName,ColumnName,ColumnValue);	
	}

	
	
}
