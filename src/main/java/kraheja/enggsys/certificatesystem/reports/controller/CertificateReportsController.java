package kraheja.enggsys.certificatesystem.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.certificatesystem.reports.bean.request.ListOfInterimCertificateReportRequestBean;
import kraheja.enggsys.certificatesystem.reports.service.CertificateReportsService;

@RestController
@RequestMapping("/certificatereports")
public class CertificateReportsController {

	@Autowired
	CertificateReportsService certificateReportsService;

	@PostMapping("/add-into-listof-interim-certificate-report-temp-table")
	public ResponseEntity<?> processListofInterimCertificateReport(
			@RequestBody ListOfInterimCertificateReportRequestBean listOfInterimCertificateReportRequestBean) {
		return this.certificateReportsService
				.processListofInterimCertificateReport(listOfInterimCertificateReportRequestBean);

	}
}
