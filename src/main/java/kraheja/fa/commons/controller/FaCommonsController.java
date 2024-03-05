// 29.02.24 RS  -  FA Common APIs

package kraheja.fa.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.fa.commons.service.FaCommonsService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/fa")
@Log4j2
public class FaCommonsController {

	@Autowired
	private FaCommonsService faCommonsService;

	@GetMapping("/fetch-mergecoy-by-coy-and-mergedate")
	public ResponseEntity<?> fetchMergeCoy(String coy, String mergeDate) {
		log.info("coy : {} mergeDate : {}", coy, mergeDate);
		return this.faCommonsService.fetchMergedCoy(coy, mergeDate);
	}
}
