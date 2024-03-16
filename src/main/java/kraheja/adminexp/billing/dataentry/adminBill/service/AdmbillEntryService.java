package kraheja.adminexp.billing.dataentry.adminBill.service;

import java.text.ParseException;
import java.util.Map;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.AdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchAdminBillRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.FetchPartyAlreadyExistsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.request.TdsRequest;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.PartyIsLegalOrSecurityResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.HsnResponse;

public interface AdmbillEntryService {

	GenericResponse<AdminBillResponseBean> fetchAdmbillhBySer(String ser);

	GenericResponse<String> addAdminbill(AdminBillRequestBean adminBillRequestBean) throws ParseException;

	GenericResponse<Map<String, Object>> fetchAdminBillDetails(FetchAdminBillRequestBean fetchAdminBillRequestBean);
	
	GenericResponse<PartyIsLegalOrSecurityResponseBean> fetchPartyIsLegalOrSecurity(String Acmajor);

	GenericResponse<Double> fetchTdsPercentage(TdsRequest tdsRequest);

	GenericResponse<Boolean> fetchPartyAlreadyExistsForPeriod(FetchPartyAlreadyExistsRequest fetchPartyAlreadyExistsRequest);
	
	GenericResponse<HsnResponse> fetchHsnData(String hsnCode, String suppBillDate, String partyCode, String stateCode,String buildingCode);
}