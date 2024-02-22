package kraheja.adminexp.billing.dataentry.debitNote.service;

import java.util.Map;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.CancelDebitNoteRequest;
import kraheja.adminexp.billing.dataentry.debitNote.bean.request.DebitNoteRequest;

public interface AdminDebitNoteService {

	GenericResponse<Map<String, Object>> fetchDebitNoteBySer(String ser);

	GenericResponse<String> addDebitNote(DebitNoteRequest debitNoteRequest);
	
	GenericResponse<Map<String,Object>>retreieveDebitNoteByInvoiceNum(String partyType,String partyCode, String invoiceNum);

	GenericResponse<Map<String, Object>> cancelDebitNoteBySer(CancelDebitNoteRequest cancelDebitNoteRequest);
	
}
