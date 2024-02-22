package kraheja.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.service.PartyService;

@RestController
@RequestMapping("/party")
public class PartyContoller {
	@Autowired
	private PartyService partyservice;
	
	@GetMapping("/fetch-partyNameGst")
	public ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(@RequestParam(value = "partycode") String  partycode, @RequestParam(value = "partytype") String  partytype){
		return this.partyservice.getPartyNameGstInfo(partycode,partytype);
	}

}
