package kraheja.adminexp.billing.dataentry.invoiceCreation.service;

import java.util.List;
import java.util.Map;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity;
import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.CombinedEntity2;

public interface InvoiceCreationService {
	
GenericResponse<List<Map<String, Object>>> fetchInvPartMasterDetails();
GenericResponse<CombinedEntity2> retreiveInvoiceDetails(String invoiceNo);
GenericResponse<Boolean>fetchPartyCodeExists(String invoiceNo);
GenericResponse<String> saveInvoiceDetails(CombinedEntity combinedEntity);
GenericResponse<String> postInvoiceBill(CombinedEntity combinedEntity);
}
