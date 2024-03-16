package kraheja.commons.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.PartyAdditionRequestBean;
import kraheja.commons.bean.response.GenericResponse;
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
	
	@GetMapping("/fetch-partyName")
	public GenericResponse<List<String>> getPartyName(@RequestParam(value = "firstChar") String  firstChar){
		return this.partyservice.getPartyName(firstChar);
	}
	
	@GetMapping("/fetch-partyInfo")
	public GenericResponse<HashMap<String,Object>> getPartyInfo(String partyType, String partyCode){
		return this.partyservice.getPartyInfo(partyType,  partyCode);
	}
	
	@PostMapping("/add-party")
	public GenericResponse<Void> addParty (PartyAdditionRequestBean partyAdditionRequestBean )
	{
		return this.partyservice.addParty(partyAdditionRequestBean);
	}
}
