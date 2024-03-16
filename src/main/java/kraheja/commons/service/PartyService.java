package kraheja.commons.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.PartyAdditionRequestBean;
import kraheja.commons.bean.response.GenericResponse;
import kraheja.commons.bean.response.ServiceResponseBean;

public interface PartyService {
	
	ResponseEntity<ServiceResponseBean> getPartyNameGstInfo(String  partycode, String  partytype) ;
	
	GenericResponse<Void> addParty(PartyAdditionRequestBean partyAdditonRequestBean  );
	
	GenericResponse<HashMap<String,Object>> getPartyInfo(String partyType, String partyCode);
	
	GenericResponse<List<String>> getPartyName (String firstChar);

}
