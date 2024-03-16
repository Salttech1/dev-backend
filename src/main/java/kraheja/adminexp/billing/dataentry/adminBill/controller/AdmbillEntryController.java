package kraheja.adminexp.billing.dataentry.adminBill.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminBill.bean.request.AdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchAdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchPartyAlreadyExistsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.TdsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.PartyIsLegalOrSecurityResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.HsnResponse;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdmbillEntryService;

@RestController
@RequestMapping("/admbill")
public class AdmbillEntryController {

	@Autowired
	private AdmbillEntryService admbillhService;

	@GetMapping("/fetch-admbill-by-Ser")
	public GenericResponse<AdminBillResponseBean> fetchAdmbillBySer(@RequestParam(value = "ser") String  ser) {
		return this.admbillhService.fetchAdmbillhBySer(ser.trim()) ; 
	}

	@PostMapping("/add-admbill")
	public GenericResponse<String> addAdmbillh( @RequestBody AdminBillRequestBean adminBillRequestBean) throws ParseException {
		return this.admbillhService.addAdminbill(adminBillRequestBean);
	}

	@PutMapping("/update-admbill")
	public GenericResponse<String> updateAdmbillh( @RequestBody AdminBillRequestBean adminBillRequestBean) throws ParseException {
		return this.admbillhService.addAdminbill(adminBillRequestBean);
	}
	
	@PostMapping("/fetch-admbill-details-new-entry")
	public GenericResponse<Map<String,Object>> fetchAdmbillNewEntry(@RequestBody FetchAdminBillRequestBean fetchAdminBillRequestBean) {
		return this.admbillhService.fetchAdminBillDetails(fetchAdminBillRequestBean) ; 
	}
	
	@GetMapping("/fetch-party-legal-or-security")
	public GenericResponse<PartyIsLegalOrSecurityResponseBean> fetchIsPartyLegalOrSecurity(@RequestParam(value = "acMajor") String acMajor) {
		return this.admbillhService.fetchPartyIsLegalOrSecurity(acMajor.trim());
	}
	
	@GetMapping("/fetch-gst-rates")
	public GenericResponse<HsnResponse> fetchGstRates(@RequestParam(value = "hsnCode") String hsnCode,@RequestParam(value = "suppbillDate") String suppbillDate,@RequestParam(value = "partyCode") String partyCode,
			@RequestParam(value = "stateCode") String stateCode,@RequestParam(value = "buildingCode") String buildingCode) {
		return this.admbillhService.fetchHsnData(hsnCode, suppbillDate, partyCode, stateCode,  buildingCode);
	}
	
	@PostMapping("/fetch-tds-percentage")
	public GenericResponse<Double> fetchTdsPercentage( @RequestBody TdsRequest tdsRequest) {
		return this.admbillhService.fetchTdsPercentage(tdsRequest);		
		}
	
	@PostMapping("/fetch-bill-exists")
	public GenericResponse<Boolean> fetchBillExists( @RequestBody FetchPartyAlreadyExistsRequest fetchPartyAlreadyExistsRequest) {
		return this.admbillhService.fetchPartyAlreadyExistsForPeriod(fetchPartyAlreadyExistsRequest);		
		}
	
}