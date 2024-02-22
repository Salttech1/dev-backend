package kraheja.adminexp.billing.dataentry.adminAdvancePayment.service;

import java.text.ParseException;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmadvanceResponseBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;

public interface AdminAdvanceBillPaymentService {
	GenericResponse<AdmadvanceResponseBean> fetchAdmadvanceBySer(String ser);

	GenericResponse<String> addAdmadvance(AdmadvanceRequestBean admadvanceRequestBean) throws ParseException;

	GenericResponse<Void> updateAdmadvance(AdmadvanceRequestBean admadvanceRequestBean) throws ParseException;
}
