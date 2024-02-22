package kraheja.adminexp.insurance.dataentry.service;

import java.text.ParseException;
import org.springframework.http.ResponseEntity;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolicyRequestBean;
public interface InspolicyService {

	ResponseEntity<?> fetchInspolicyByPolicyid(String  policyid) ;

	ResponseEntity<?> addInspolicy(InspolicyRequestBean inspolicyRequestBean)  throws ParseException ;

	ResponseEntity<?> updateInspolicy(InspolicyRequestBean inspolicyRequestBean)  throws ParseException ;

	ResponseEntity<?> checkPolicyidExists(String  policyid) ;
	
	ResponseEntity<?> checkPolicynumExists(String  policynum) ;
	
}