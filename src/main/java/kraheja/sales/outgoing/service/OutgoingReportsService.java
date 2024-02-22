package kraheja.sales.outgoing.service;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.OutgoingDefaultersReportRequestBean;
import kraheja.sales.bean.request.OutgoingReportsRequestBean;
import kraheja.sales.bean.request.OutgoingSocietyAccountsReportRequestBean;
import kraheja.sales.bean.request.OutgoingSummaryRequestBean;

public interface OutgoingReportsService {

//	ResponseEntity<?> inserttempowner();

	ResponseEntity<?> processOgBills(OutgoingReportsRequestBean outgoingReportsRequestBean);

	ResponseEntity<?> addIntoOGSummaryTempTables(OutgoingSummaryRequestBean outgoingSummaryRequestBean);

//	 ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId);

	ResponseEntity<?> addIntoOGDefaultersListTempTables(
			OutgoingDefaultersReportRequestBean outgoingDefaultersReportRequestBean);

	ResponseEntity<?> processSocietyAccountsReport(
			OutgoingSocietyAccountsReportRequestBean outgoingSocietyAccountsReportRequestBean);

//	 ResponseEntity<?> deleteSocAccTempData(Integer sessionId);

//	int inserttempowner(OutgoingReportsRequestBean outgoingReportsRequestBean);

//	ResponseEntity<?> getFirstOgDate(String flatOwner);

}
