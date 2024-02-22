package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.service.DynaPopService;



@RestController
@RequestMapping("/dynapop")
public class DynaPopController {

	@Autowired
	private DynaPopService  dynaPopService;
	
	@GetMapping
	public ResponseEntity<?> dynaPop(@RequestParam(required = true) String dynaPopId, @RequestParam(required = false) String queryConditon){
		return this.dynaPopService.dynaPop(dynaPopId, queryConditon);
	}
	
	@GetMapping("dynapop-find-by-search-text")
	public ResponseEntity<?> dynaPopFindBySearchText(@RequestParam(required = true) String dynaPopId, @RequestParam(required = false) String queryConditon, @RequestParam(required = true) String searchText){
		return this.dynaPopService.dynaPopFindBySearchText(dynaPopId, queryConditon, searchText.toUpperCase());
	}
}
