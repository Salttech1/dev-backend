package kraheja.adminexp.overheads.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsRequestBean;

public interface OverheadconsService {
	ResponseEntity<?> findByohdhconnocode(String  connocode) ;

	ResponseEntity<?> addOverheadcons(OverheadconsRequestBean overheadconsRequestBean) ;

	ResponseEntity<?> updateOverheadcons(OverheadconsRequestBean overheadconsRequestBean) ;

	ResponseEntity<?> checkConnocodeExists(String  connocode) ;
	
	ResponseEntity<?> checkConsumnerNoExists(String  consumerno) ;

	//fetchLocnamePaycoynameBillcoyname
	ResponseEntity<?> fetchLocnamePaycoynameBillcoyname(String  connocode) ;

	
}
