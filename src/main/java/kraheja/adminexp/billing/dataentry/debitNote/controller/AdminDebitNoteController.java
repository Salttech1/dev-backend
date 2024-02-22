package kraheja.adminexp.billing.dataentry.debitNote.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.CancelDebitNoteRequest;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.DebitNoteRequest;
import kraheja.adminexp.billing.dataentry.debitNote.bean.response.DebitNoteResponse;
import kraheja.adminexp.billing.dataentry.debitNote.service.AdminDebitNoteService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/debit-note")
@Log4j2
public class AdminDebitNoteController {
	
	@Autowired
	AdminDebitNoteService adminDebitNoteService;
	
	@GetMapping("/fetch-debit-note-by-Ser")
	public GenericResponse<Map<String,Object>> fetchDebitNoteBySer(@RequestParam(value = "ser") String ser) {
		log.info("In Debit Note Fetch Method");
		return this.adminDebitNoteService.fetchDebitNoteBySer(ser);
	}

	@PostMapping("/save-debit-note-bill")
	public GenericResponse<String> addAdminDebitNote(@Valid @RequestBody DebitNoteRequest debitNoteRequest)
			throws ParseException {
		log.info("In Debit Note Save Method");
		return this.adminDebitNoteService.addDebitNote(debitNoteRequest);
	}
	
	@PutMapping("/update-debit-note-bill")
	public GenericResponse<String> updateAdminDebitNote(@Valid @RequestBody DebitNoteRequest debitNoteRequest)
			throws ParseException {
		log.info("In Debit Note Update Method");
		return this.adminDebitNoteService.addDebitNote(debitNoteRequest);
	}
	
	@GetMapping("/fetch-debit-note-by-invoiceNum")
	public GenericResponse<Map<String,Object>> fetchDebitNoteByInvoiceNum(@RequestParam(value = "partyType") String partyType,@RequestParam(value = "partyCode") String partyCode,@RequestParam(value = "invoiceNum") String invoiceNum) {
		log.info("In Debit Note Fetch Method");
		return this.adminDebitNoteService.retreieveDebitNoteByInvoiceNum(partyType, partyCode, invoiceNum);
	}
	
	@PutMapping("/cancel-debit-note")
	public GenericResponse<Map<String,Object>> CancelDebitNoteBySer(@Valid @RequestBody CancelDebitNoteRequest cancelDebitNoteRequest) {
		log.info("In Debit Note Fetch Method");
		return this.adminDebitNoteService.cancelDebitNoteBySer(cancelDebitNoteRequest);
	}
	
}
