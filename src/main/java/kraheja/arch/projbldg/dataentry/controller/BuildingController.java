package kraheja.arch.projbldg.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.arch.projbldg.dataentry.bean.request.BuildingRequestBean;
import kraheja.arch.projbldg.dataentry.service.BuildingService;

@RestController
@RequestMapping("/building")
public class BuildingController {

	@Autowired
	private BuildingService buildingService;

	@GetMapping("/fetch-building-by-Code")
	public ResponseEntity<?> fetchBuildingByCode(@RequestParam(value = "code") String  code) throws ParseException {
		return this.buildingService.fetchBuildingByCode(code) ; 
	}

	@PostMapping("/add-building")
	public ResponseEntity<?> addBuilding(@Valid @RequestBody BuildingRequestBean buildingRequestBean) throws ParseException {
		return this.buildingService.addBuilding(buildingRequestBean);
	}

	@PutMapping("/update-building")
	public ResponseEntity<?> updateBuilding(@Valid @RequestBody BuildingRequestBean buildingRequestBean) throws ParseException {
		return this.buildingService.updateBuilding(buildingRequestBean);
	}
	
	@GetMapping("/fetch-project-coy-by-code") //NS 16.03.2023
	public ResponseEntity<?> fetchProjectCompanyByCode(@RequestParam(value = "code") String code)  throws ParseException {
		return this.buildingService.fetchProjectCompanyByCode(code); 
	}
	

}