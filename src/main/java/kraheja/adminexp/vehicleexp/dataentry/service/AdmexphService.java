package kraheja.adminexp.vehicleexp.dataentry.service;

import java.text.ParseException;
import org.springframework.http.ResponseEntity;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexphRequestBean;
public interface AdmexphService {

//	ResponseEntity<?> fetchAdmexphByCertnumAndCoyAndCerttype(String  certnum, String  coy) ;
	
	ResponseEntity<?> fetchAdmexphByCertnumAndEquipid(String  certnum, String  equipid) ;

	ResponseEntity<?> addAdmexph(AdmexphRequestBean admexphRequestBean)  ;

	ResponseEntity<?> updateAdmexph(AdmexphRequestBean admexphRequestBean)  ;

	ResponseEntity<?> deleteAdmexph(String  certnum, String  coy); 

	ResponseEntity<?> checkCertnumAndCoyAndCerttypeExists(String  certnum, String  coy) ;
	
	ResponseEntity<?> getVehicleInfo(String  vehnum) ;
	
	ResponseEntity<?> getPartyInfo(String partycode, String vehicleno);
	
	ResponseEntity<?> passVehicleCert(AdmexphRequestBean admexphRequestBean)  ;
	
	ResponseEntity<?> findByEquipid(String  equipid) ;
	
	ResponseEntity<?> cancelUnPostedCertificate(String certnum, String transer);

}