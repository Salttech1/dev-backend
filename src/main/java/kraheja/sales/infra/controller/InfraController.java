package kraheja.sales.infra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.sales.bean.request.InfraDefaultersListRequestBean;
import kraheja.sales.infra.service.InfraService;

@RestController
@RequestMapping("/infra")
public class InfraController {

	@Autowired
	private InfraService infraService;

	@PostMapping("/add-into-infra-defaulters-list-temp-table")
	public ResponseEntity<?> addIntoInfraDefaultersListTempTable(
			@RequestBody InfraDefaultersListRequestBean infraDefaultersListRequestBean) {
		return this.infraService.addIntoInfraDefaultersListTempTable(infraDefaultersListRequestBean);
	}

	@DeleteMapping("/delete-infra-defaulters-list-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.infraService.deleteInfraDefaultersListFromSessionId(sessionId);
	}

	@GetMapping("/fetch-infra-gst-flag")
	public ResponseEntity<?> fetchGstFlag(String recNum) {
		return this.infraService.fetchGstFlag(recNum);
	}

	@GetMapping("/fetch-infra-car-parks")
	public ResponseEntity<?> fetchCarParks(String bldgCode, String wing, String flatNo) {
		return this.infraService.fetchCarParks(bldgCode, wing, flatNo);

	}

	@GetMapping("/fetch-infra-advance-flag")
	public ResponseEntity<?> fetchAdvanceFlag(String bldgCode, String wing, String flatNo, String recNum,
			String gstYN) {
		return this.infraService.fetchAdvanceFlag(bldgCode, wing, flatNo, recNum, gstYN);

	}
}