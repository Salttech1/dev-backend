package kraheja.adminexp.billing.dataentry.adminBill.service;

import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;

public interface AdminBillCancellationService {

	public GenericResponse<String> cancelAdminBill(String ser);

	public GenericResponse<AdminBillResponseBean> fetchAdmbillhBySer(String ser);
}
