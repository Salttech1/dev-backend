package kraheja.arch.projbldg.dataentry.service;

import org.springframework.http.ResponseEntity;
import kraheja.arch.projbldg.dataentry.bean.request.BuildingRequestBean;
public interface BuildingService {

	ResponseEntity<?> fetchBuildingByCode(String  code) ;

	ResponseEntity<?> addBuilding(BuildingRequestBean buildingRequestBean) ;

	ResponseEntity<?> updateBuilding(BuildingRequestBean buildingRequestBean) ;

	ResponseEntity<?> checkCodeExists(String  code) ;
	
	ResponseEntity<?> fetchProjectCompanyByCode(String  code) ; //NS 16.03.2023 retrieves the project company.
}