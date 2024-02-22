package kraheja.arch.projbldg.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.arch.projbldg.dataentry.bean.request.BldgmapRequestBean;
public interface BldgmapService {

	ResponseEntity<?> fetchBldgmapByEbldgcodeAndAbldgcode(String  ebldgcode, String  abldgcode) ;

	ResponseEntity<?> addBldgmap(BldgmapRequestBean bldgmapRequestBean) ;

	ResponseEntity<?> updateBldgmap(BldgmapRequestBean bldgmapRequestBean) ;

	ResponseEntity<?> checkEbldgcodeAndAbldgcodeExists(String  ebldgcode, String  abldgcode) ;
}