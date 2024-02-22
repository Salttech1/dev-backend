package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.request.ReportMasterRequestBean;


public interface ReportMasterService {

//	ResponseEntity<?> addReport(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> updateReport(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> generateReportWithParameter(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> generateReportWithCondition(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> generateReportWithConditionAndParameter(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> generateReportWithTtx(ReportMasterRequestBean reportMasterRequestBean);
//
//	ResponseEntity<?> generateTtxReportWithOnlyFormula(ReportMasterRequestBean reportMasterRequestBean);
	
	ResponseEntity<?> fetchReportJobTransactionByStatusAndUserid();
	
	ResponseEntity<?> generateReport(ReportMasterRequestBean reportMasterRequestBean);

	ResponseEntity<?> fetchReportJobTransactionByReportid(Integer reportJobTransactionId);

	ResponseEntity<?> deleteReportJobTransactionByReportid(Integer reportJobTransactionId);
	
//	ResponseEntity<?> generateTtxReportWitCondition(ReportMasterRequestBean reportMasterRequestBean);
}
