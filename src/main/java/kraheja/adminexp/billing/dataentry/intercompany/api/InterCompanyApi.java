package kraheja.adminexp.billing.dataentry.intercompany.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.intercompany.bean.request.InterCompanyRequest;
import kraheja.adminexp.billing.dataentry.intercompany.bean.response.AddInterCompanyResponse;
import kraheja.adminexp.billing.dataentry.intercompany.service.InterCompanyService;
import kraheja.adminexp.billing.dataentry.intercompany.service.InterCoyPostingToAccountService;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * this api created for fetch inter company data calculate it condition wise.
 * </p>
 * 
 * @author sazzad.alom
 * @version 1.0.0
 * @since 21-NOV-2023
 */

@Log4j2
@RestController
@RequestMapping("/inter-company")
public class InterCompanyApi {

	@Autowired
	private InterCompanyService interCompanyService;
	@Autowired private InterCoyPostingToAccountService postingToAccount;

	@PostMapping("/fetch-greid")
	public ResponseEntity<AddInterCompanyResponse> fetchDateWithAll(@RequestBody InterCompanyRequest request) {
		log.debug("/post/request/fetch-greid/inter-company/add InterCompanyRequest: {}",request);

		AddInterCompanyResponse interCompanyAddResponse = interCompanyService.fetchInterCompanyDetails(request);
		log.debug("/post/response/fetch-greid/interCompanyAddResponse: {}", interCompanyAddResponse);

		return ResponseEntity.ok(interCompanyAddResponse);
	}
	
	@PostMapping("/retrive-greid")
	public ResponseEntity<AddInterCompanyResponse> retriveData(@RequestBody InterCompanyRequest request) {
		log.debug("/post/request/retrive-greid/inter-company/add InterCompanyRequest: {}",request);
		
		AddInterCompanyResponse interCompanyAddResponse = interCompanyService.retriveInterCompanyDetails(request);
		log.debug("/post/response/retrive-greid/interCompanyAddResponse: {}", interCompanyAddResponse);
		
		return ResponseEntity.ok(interCompanyAddResponse);
	}
	
	@PostMapping("/add")
	public ResponseEntity<GenericResponse> addApi(@RequestBody AddInterCompanyResponse request) {
		log.debug("/post/request/inter-company/add siteName: {} userId: {} InterCompanyRequest: {}", request);
	
		GenericResponse response = interCompanyService.persistInterCompany(request);
		log.debug("/post/request/addResponse: {}", response);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/post-inter-coy")
	public ResponseEntity<GenericResponse> addApi(@RequestParam(value = "groupInvoiceNum") String groupInvoiceNum) {
		log.debug("groupInvoiceNum : {} ", groupInvoiceNum);
	
		GenericResponse response = postingToAccount.postInterCoy(groupInvoiceNum);
		log.debug("/post/request/addResponse: {}", response);
		
		return ResponseEntity.ok(response);
	}
	
}
