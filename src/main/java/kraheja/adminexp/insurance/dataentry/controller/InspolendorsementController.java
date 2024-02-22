package kraheja.adminexp.insurance.dataentry.controller;


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


import kraheja.adminexp.insurance.dataentry.bean.request.InspolendorsementRequestBean;
import kraheja.adminexp.insurance.dataentry.service.InspolendorsementService;

@RestController
@RequestMapping("/inspolendorsement")
public class InspolendorsementController {

	@Autowired
	private InspolendorsementService inspolendorsementService;

	@GetMapping("/fetch-inspolendorsement-by-Polendorseid")
	public ResponseEntity<?> fetchInspolendorsementByPolendorseid(String  polendorseid, Boolean isAdd, String policyNo) throws ParseException {
		return this.inspolendorsementService.fetchInspolendorsementByPolendorseid(polendorseid, isAdd, policyNo) ; 
	}

	@PostMapping("/add-inspolendorsement")
	public ResponseEntity<?> addInspolendorsement(@Valid @RequestBody InspolendorsementRequestBean inspolendorsementRequestBean) throws ParseException {
		return this.inspolendorsementService.addInspolendorsement(inspolendorsementRequestBean);
	}

	@PutMapping("/update-inspolendorsement")
	public ResponseEntity<?> updateInspolendorsement(@Valid @RequestBody InspolendorsementRequestBean inspolendorsementRequestBean) throws ParseException {
		return this.inspolendorsementService.updateInspolendorsement(inspolendorsementRequestBean);
	}

}