package kraheja.enggsys.certificatesystem.reports.service;

import org.springframework.http.ResponseEntity;

import kraheja.enggsys.certificatesystem.reports.bean.request.ListOfInterimCertificateReportRequestBean;

public interface CertificateReportsService {

	ResponseEntity<?> processListofInterimCertificateReport(
			ListOfInterimCertificateReportRequestBean listOfInterimCertificateReportRequestBean);

}
