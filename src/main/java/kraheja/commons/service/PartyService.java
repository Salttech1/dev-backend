package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;

public interface PartyService {
	
	ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(String  partycode, String  partytype) ;

}
