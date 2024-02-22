package kraheja.adminexp.insurance.dataentry.controller;

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
import java.text.ParseException;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolicyRequestBean;
import kraheja.adminexp.insurance.dataentry.service.InspolicyService;

@RestController
@RequestMapping("/inspolicy")
public class InspolicyController {

	@Autowired
	private InspolicyService inspolicyService;

	@GetMapping("/fetch-inspolicy-by-Policyid")
	public ResponseEntity<?> fetchInspolicyByPolicyid(@RequestParam(value = "policyid") String  policyid) throws ParseException {
		return this.inspolicyService.fetchInspolicyByPolicyid(policyid); 
	}
	
	@GetMapping("/check-Policynum-Exists")
	public ResponseEntity<?> checkPolicynumExists(@RequestParam(value = "policynum") String  policynum) throws ParseException {
		return this.inspolicyService.checkPolicynumExists(policynum);
	}
	
	@PostMapping("/add-inspolicy")
	public ResponseEntity<?> addInspolicy(@Valid @RequestBody InspolicyRequestBean inspolicyRequestBean) throws ParseException {
		return this.inspolicyService.addInspolicy(inspolicyRequestBean);
	}

	@PutMapping("/update-inspolicy")
	public ResponseEntity<?> updateInspolicy(@Valid @RequestBody InspolicyRequestBean inspolicyRequestBean) throws ParseException {
		return this.inspolicyService.updateInspolicy(inspolicyRequestBean);
	}
}