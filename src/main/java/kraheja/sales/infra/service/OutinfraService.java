package kraheja.sales.infra.service;
 
import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.OutinfraRequestBean;
public interface OutinfraService {

	ResponseEntity<?> fetchOutinfraByBldgcodeAndOwneridAndRecnumAndMonthAndNarrcode(String  bldgcode, String  ownerid, String  recnum, String  month, String  narrcode) ;
	
	ResponseEntity<?> fetchFlatOwnerByBldgcodeAndFlatnumAndWing(String bldgcode, String flatnum, String wing); //NS 15.05.2023
	
	ResponseEntity<?> fetchStartDateByBldgcodeAndWingAndFlatnoAndBilltype(String bldgcode, String wing, String flatno , String billtype); //NS 19.05.2023
	
//	ResponseEntity<?> fetchGstRates();//NS 29.05.2023
	ResponseEntity<?> fetchStartdateAndEnddateByBldgcodeAndWingAndFlatnumAndbilltype(String bldgcode, String wing, String flatnum, String billtype) ; //NS 04.09.2023
	
	ResponseEntity<?> fetchGstRates();//NS 31.08.2023 implementing new rule which was not implemented by Monish as monish has hardcoded it in old code.
	
	ResponseEntity<?> fetchPreviousOgRecords(String ownerid, String startDate, String typeAuxi, String billType);//NS 07.06.2023
	
	ResponseEntity<?> fetchMaintainanceRate(String bldgcode, String wing, String flatnum, String billType);//NS 14.08.2023
	
	ResponseEntity<?> fetchAdminRate(String bldgcode, String wing, String flatnum, String billType);//NS 17.08.2023
	
	ResponseEntity<?> fetchTDSRate(String bldgcode, String wing, String flatnum, String billType);//NS 17.08.2023
	
	ResponseEntity<?> addOutinfra(OutinfraRequestBean outinfraRequestBean)  throws ParseException ;

	ResponseEntity<?> updateOutinfra(OutinfraRequestBean outinfraRequestBean)  throws ParseException ;

	ResponseEntity<?> deleteOutinfra(String  bldgcode, String  ownerid, String  recnum, String  month, String  narrcode) throws ParseException; 

}