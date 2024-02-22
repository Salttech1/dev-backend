package kraheja.sales.outgoing.service;


import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kraheja.sales.bean.request.OutrateRequestBean;
public interface OutrateService {

	ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWing(String  bldgcode, String  flatnum, String  wing) ;
	
//	ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnum(String  bldgcode, String  flatnum) ;

	ResponseEntity<?> fetchOutrateByBldgcodeAndFlatnumAndWingAndStartdate(String  bldgcode, String  flatnum, String  wing, String  startdate) ;
	
//	ResponseEntity<?> addOutrate(List <OutrateRequestBean> outrateRequestBean)  throws ParseException ;

	ResponseEntity<?> updateOutrate(List <OutrateRequestBean>  outrateRequestBean)  throws ParseException ;

	ResponseEntity<?> deleteOutrate(String  bldgcode, String  flatnum, String  wing, String  startdate) throws ParseException; 

//	ResponseEntity<?> checkBldgcodeAndFlatnumAndWingAndStartdateExists(String  bldgcode, String  flatnum, String  wing, String  startdate) ;
}