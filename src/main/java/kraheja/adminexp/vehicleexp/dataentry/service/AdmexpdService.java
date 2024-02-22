package kraheja.adminexp.vehicleexp.dataentry.service;

import java.text.ParseException;
import org.springframework.http.ResponseEntity;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexpdRequestBean;
public interface AdmexpdService {

	ResponseEntity<?> fetchAdmexpdByCertnumAndBunum(String  certnum, Double  bunum) ;
	
	ResponseEntity<?> fetchAdmexpdByCertnum(String  certnum);

	ResponseEntity<?> addAdmexpd(AdmexpdRequestBean admexpdRequestBean)  throws ParseException ;

	ResponseEntity<?> updateAdmexpd(AdmexpdRequestBean admexpdRequestBean) ;

	ResponseEntity<?> deleteAdmexpd(String  certnum, Double  bunum) throws ParseException; 

	ResponseEntity<?> checkCertnumAndBunumExists(String  certnum, Double  bunum) ;
}