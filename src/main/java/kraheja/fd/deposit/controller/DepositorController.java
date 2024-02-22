package kraheja.fd.deposit.controller;

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

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.DepositorRequestBean;
import kraheja.fd.deposit.service.DepositorService;


@RestController
@RequestMapping("/depositor")
public class DepositorController {

	@Autowired
	private DepositorService  depositorService; 

	@PostMapping("/fetch-depositor-by-companycode")
	public ResponseEntity<?> fetchDepositorByCompanycode(@RequestBody Map<String, String> body){
		return this.depositorService.fetchDepositorByCompanycode(body.get("companyCode"));
	}

	@PostMapping("/fetch-depositor-by-depositorid-and-companycode")
	public ResponseEntity<?> fetchDepositorByDepositorIdAndCompanycode(@RequestBody DepositorRequestBean depositorRequestBean) {
		return this.depositorService.fetchDepositorByDepositorIdAndCompanycode(depositorRequestBean);
	}

	@PostMapping("/add-depositor")
	public ResponseEntity<ServiceResponseBean> addDepositor(@Valid @RequestBody DepositorRequestBean depositorRequestBean)  {
		return this.depositorService.addDepositor(depositorRequestBean);
	}

	@PutMapping("/update-depositor")
	public ResponseEntity<?> updateDepositor(@Valid @RequestBody DepositorRequestBean depositorRequestBean) {
		return this.depositorService.updateDepositor(depositorRequestBean);
	}
	
	@GetMapping("/check-depositorid-and-companycode-exists")
	public ResponseEntity<?> checkDepositorIdAndCompanyCodeExists(@RequestParam(value = "depositorId") String depositorId, @RequestParam(value = "companyCode") String companyCode) {
		return this.depositorService.checkDepositorIdAndCompanyCodeExists(depositorId, companyCode);
	}
	
	@GetMapping("/age-by-depositorid-and-companycode")
	public ResponseEntity<?> ageByDepositorIdAndCompanycode(@RequestParam(value = "depositorId") String depositorId, @RequestParam(value = "companyCode") String companyCode) {
		return this.depositorService.ageByDepositorIdAndCompanycode(depositorId, companyCode);
	}
	
	@GetMapping("/form15hg-by-depositorid-and-companycode")
	public ResponseEntity<?> form15hgByDepositorIdAndCompanycode(@RequestParam(value = "depositorId") String depositorId, @RequestParam(value = "companyCode") String companyCode) {
		return this.depositorService.form15hgByDepositorIdAndCompanycode(depositorId, companyCode);
	}
}
