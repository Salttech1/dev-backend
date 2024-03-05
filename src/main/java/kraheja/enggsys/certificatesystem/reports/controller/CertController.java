package kraheja.enggsys.certificatesystem.reports.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.OSAdvAndRetReportRequestBean;
import kraheja.commons.utils.CommonConstraints;
import kraheja.feign.internal.ReportInternalFeignClient;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.request.CertPrintRequestBean;
import kraheja.enggsys.bean.request.CertViewRequestBean;
import kraheja.enggsys.bean.request.CertPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.CertPrintDetailResponseBean;
import kraheja.enggsys.certificatesystem.reports.service.CertService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cert")
public class CertController {
	
	@Autowired
	private CertService CertService;
	
	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;
	
	@PostMapping("/merge-pdf")
	public ResponseEntity<?> mergePdf(@RequestBody CertPrintDetailResponseBean CertPrintDetailResponseBean){
		return this.CertService.mergePdf(CertPrintDetailResponseBean);
	}

	@PostMapping("/insert-into-material-payment-temp")
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(@RequestBody CertPrintRequestBean CertPrintRequestBean){
		return this.CertService.insertIntoMaterialPaymentTemp(CertPrintRequestBean);
	}
	
	@PutMapping("/update-print-status")
	public ResponseEntity<?> updatePrintStatus(@RequestBody CertPrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean){
		return this.CertService.updateCertPrintStatus(printStatusUpdateDetailRequestBean);
	}

	@DeleteMapping("/delete-temp-table-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.CertService.deleteTempTableFromSessionId(sessionId);
	}
	
}
