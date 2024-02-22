package kraheja.adminexp.billing.dataentry.adminAdvancePayment.service;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;

public interface AdminAdvancePaymentPassingService {
	GenericResponse<Admadvance1> fetchAdmadvanceByPinvAndSer(String ser, String pinv);

	GenericResponse<Void> updateAdmadvance(String ser);
}
