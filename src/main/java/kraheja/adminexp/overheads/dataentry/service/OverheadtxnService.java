package kraheja.adminexp.overheads.dataentry.service;
import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadtxnRequestBean;
public interface OverheadtxnService {
	ResponseEntity<?> fetchOverheadtxnByConnocodeAndBillperiodAndSupplementarybill(String  connocode, String  billperiod, String  supplementarybill) ;
	
	ResponseEntity<?> fetchOverheadtxnByConnocode(String Connocode);

	ResponseEntity<?> addOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException ;

	//add vacant flat data bill through Excel
	ResponseEntity<?> addExcelOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException ;
	
	ResponseEntity<?> updateOverheadtxn(OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException;

	ResponseEntity<?> deleteOverheadtxn(String  connocode, String  billperiod, String  supplementarybill) throws ParseException; 

	ResponseEntity<?> checkConnocodeAndBillperiodAndSupplementarybillExists(String  connocode, String  billperiod, String  supplementarybill) ;
	
	//FetchPrvconnData
	ResponseEntity<?> fetchPrvBillData(String  connocode) ;
	
}
