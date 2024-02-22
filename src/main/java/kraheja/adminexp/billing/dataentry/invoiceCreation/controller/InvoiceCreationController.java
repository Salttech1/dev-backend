package kraheja.adminexp.billing.dataentry.invoiceCreation.controller;

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

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity2;
import kraheja.adminexp.billing.dataentry.invoiceCreation.service.InvoiceCreationService;

@RestController
@RequestMapping("/invoice-creation")
public class InvoiceCreationController {

	@Autowired
	private InvoiceCreationService invoiceCreationService;

	@GetMapping("/fetch-invPartyMaster-list")
	public GenericResponse<List<Map<String, Object>>> fetchInvPartyMaster() {
		return invoiceCreationService.fetchInvPartMasterDetails();
	}

	@GetMapping("/fetch-invoice-bill")
	public GenericResponse<CombinedEntity2> fetchInvoiceDetails(@RequestParam String invoiceNum) {
		return invoiceCreationService.retreiveInvoiceDetails(invoiceNum);
	}

	@PostMapping("/save-invoice-bill")
	public GenericResponse<String> saveInvoiceDetails(@RequestBody CombinedEntity combinedEntity) {
		return invoiceCreationService.saveInvoiceDetails(combinedEntity);
	}

	@PutMapping("/modify-invoice-bill")
	public GenericResponse<String> modifyInvoiceDetails(@RequestBody CombinedEntity combinedEntity) {
		return invoiceCreationService.saveInvoiceDetails(combinedEntity);
	}
	
	@PostMapping("/post-invoice-bill")
	public GenericResponse<String> postInvoiceBill(@RequestBody CombinedEntity combinedEntity){
		return invoiceCreationService.postInvoiceBill(combinedEntity);
	}

	
	@GetMapping("/fetch-party-code-exists")
	public GenericResponse<Boolean> fetchPartyCodeExists(@RequestParam String invoiceNum) {
		return invoiceCreationService.fetchPartyCodeExists(invoiceNum);
	}
}
