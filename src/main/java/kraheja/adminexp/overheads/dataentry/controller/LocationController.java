package kraheja.adminexp.overheads.dataentry.controller;

import java.text.ParseException;
import java.util.Map;
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
import kraheja.adminexp.overheads.dataentry.bean.request.LocationRequestBean;
import kraheja.adminexp.overheads.dataentry.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {
	@Autowired
	private LocationService locationService;

	@GetMapping("/fetch-location-by-Code")
	public ResponseEntity<?> fetchLocationByCode(String locationcode) throws ParseException{
			return this.locationService.fetchLocationByCode(locationcode);
	}
	
	@PostMapping("/add-location")
	public ResponseEntity<?> addLocation(@RequestBody LocationRequestBean locationRequestBean) throws ParseException {
		return this.locationService.addLocation(locationRequestBean);
	}

	@PutMapping("/update-location")
	public ResponseEntity<?> updateLocation(@Valid @RequestBody LocationRequestBean locationRequestBean) throws ParseException {
		return this.locationService.updateLocation(locationRequestBean);
	}

//	@GetMapping("/check-Code-exists")
//	public ResponseEntity<?> checkCodeExists(@RequestParam(value = "code") String  code) throws ParseException {
//		return this.locationService.checkCodeExists(code);
//	}

}
