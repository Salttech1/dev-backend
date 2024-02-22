package kraheja.adminexp.billing.dataentry.adminBill.service;

import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;


public interface AdmbillPassingService {
	GenericResponse<Admbillh> fetchAdmbillhSer(String ser);
	GenericResponse<String> adminBillPassing(String ser);
}
