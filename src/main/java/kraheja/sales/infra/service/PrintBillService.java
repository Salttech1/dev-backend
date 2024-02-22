package kraheja.sales.infra.service;

import kraheja.payload.GenericResponse;
import kraheja.sales.infra.bean.response.BillResponse;

public interface PrintBillService {
	GenericResponse printBill(BillResponse printRequest, String chargeCode, String billType, double sessionId);
	GenericResponse deleteBill(double sessionId, String ownerId);
	GenericResponse viewBill(BillResponse printRequest, String chargeCode, String billType, double sessionId);
}
