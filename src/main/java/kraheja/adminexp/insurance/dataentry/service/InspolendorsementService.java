package kraheja.adminexp.insurance.dataentry.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;


import kraheja.adminexp.insurance.dataentry.bean.request.InspolendorsementRequestBean;
public interface InspolendorsementService {

	ResponseEntity<?> addInspolendorsement(InspolendorsementRequestBean inspolendorsementRequestBean)  throws ParseException ;
	
	ResponseEntity<?> updateInspolendorsement(InspolendorsementRequestBean inspolendorsementRequestBean)  throws ParseException ;

	ResponseEntity<?> checkPolendorseidExists(String  polendorseid) ;

	ResponseEntity<?> fetchInspolendorsementByPolendorseid(String Polendorseid, Boolean isAdd, String policyNo);
}