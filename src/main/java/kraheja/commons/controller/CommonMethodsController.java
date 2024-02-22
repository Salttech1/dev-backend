package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.CommonMethodsService;

@RestController
@RequestMapping("/commonmethods")
public class CommonMethodsController {
	@Autowired
	CommonMethodsService commonMethodsService;

	@DeleteMapping("/delete-Temp-Data-For-SessId")
	public ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(
			@RequestParam(value = "tempTableName") String tempTableName,
			@RequestParam(value = "sesIdColName") String partytype,
			@RequestParam(value = "sessionId") String sessionId) {
		return this.commonMethodsService.deleteRowsFromTempTable(tempTableName, partytype, sessionId);
	}
}
