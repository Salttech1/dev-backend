package kraheja.fd.deposit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.fd.deposit.bean.request.Form15hgRequestBean;
import kraheja.fd.deposit.service.Form15hgService;


@RestController
@RequestMapping("/form-15hg")
public class Form15hgController {

	@Autowired
	private Form15hgService  form15hgService; 

	@GetMapping("/fetch-form15hg-by-depositorId-and-companyCode-and-accountYear-and-quater")
	public ResponseEntity<ServiceResponseBean> fetchForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(String depositorId, String companyCode, String accountYear, String quater) {
		return this.form15hgService.fetchForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(depositorId, companyCode, accountYear, quater);
	}
	
	@PutMapping("/update-form15hg-by-depositorId-and-companyCode-and-accountYear-and-quater")
	public ResponseEntity<ServiceResponseBean> updateForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(@RequestBody Form15hgRequestBean form15hgRequestBean) {
		return this.form15hgService.updateForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(form15hgRequestBean);
	}
	
	@PostMapping("/add-form15hg-by-depositorId-and-companyCode-and-accountYear-and-quater")
	public ResponseEntity<ServiceResponseBean> addForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(@RequestBody Form15hgRequestBean form15hgRequestBean) {
		return this.form15hgService.addForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(form15hgRequestBean);
	}
	
	@DeleteMapping("/delete-form15hg-by-depositorId-and-companyCode-and-accountYear-and-quater")
	public ResponseEntity<ServiceResponseBean> deleteForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(String depositorId, String companyCode, String accountYear, String quater){
		return this.form15hgService.deleteForm15hgByDepositorIdAndCompanyCodeAndCompanyCodeAndAccountYearAndQuater(depositorId, companyCode, accountYear, quater);
	}
	
	@GetMapping("/fetch-uniqueid-and-income-and-fromDate-and-toDate")
	public ResponseEntity<ServiceResponseBean> fetchUniqueidAndIncomeAndFromDateAndToDate(String depositorId, String companyCode, String accountYear, String quater) {
		return this.form15hgService.fetchUniqueidAndIncomeAndFromDateAndToDate(depositorId, companyCode, accountYear, quater);
	}
	
	@GetMapping("/export-15hg-report")
	public ResponseEntity<?> export15hgReport(String companyCode, String accountYear, String quater, String fifteenhg, Boolean isIncomeDetails) {
		return this.form15hgService.export15hgReport(companyCode, accountYear, quater, fifteenhg, isIncomeDetails);
	}
}
