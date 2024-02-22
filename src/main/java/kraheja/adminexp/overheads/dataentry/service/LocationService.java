package kraheja.adminexp.overheads.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.adminexp.overheads.dataentry.bean.request.LocationRequestBean;

public interface LocationService {
	ResponseEntity<?> fetchLocationByCode(String  code) ;

	ResponseEntity<?> addLocation(LocationRequestBean locationRequestBean) ;

	ResponseEntity<?> updateLocation(LocationRequestBean locationRequestBean) ;

	//ResponseEntity<?> checkCodeExists(String  code) ;
}
