package kraheja.adminexp.overheads.dataentry.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheaddepositdtlsRequestBean;

public interface OverheaddepositdtlsService {

	ResponseEntity<?> fetchOverheaddepositdtlsByConnocodeAndPeriod(String  connocode, String  period) ;

	ResponseEntity<?> addOverheaddepositdtls(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean)  throws ParseException ;

	ResponseEntity<?> updateOverheaddepositdtls(OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean)  throws ParseException ;

	ResponseEntity<?> deleteOverheaddepositdtls(String  connocode, String  period) throws ParseException; 

	ResponseEntity<?> checkConnocodeAndPeriodExists(String  connocode, String  period) ;
	
	//FetchPrvconnData
		ResponseEntity<?> fetchDepositeAmtbyConnocode(String  connocode) ;
}