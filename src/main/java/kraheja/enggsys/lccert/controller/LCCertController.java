package kraheja.enggsys.lccert.controller;


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
import kraheja.enggsys.bean.request.LCCertPrintRequestBean;
import kraheja.enggsys.bean.request.LCCertViewRequestBean;
import kraheja.enggsys.bean.request.LCCertPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.LCCertPrintDetailResponseBean;
import kraheja.enggsys.lccert.service.LCCertService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lccert")
public class LCCertController {
	
	@Autowired
	private LCCertService LCCertService;
	
	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;
	
	@PostMapping("/merge-pdf")
	public ResponseEntity<?> mergePdf(@RequestBody LCCertPrintDetailResponseBean LCCertPrintDetailResponseBean){
		return this.LCCertService.mergePdf(LCCertPrintDetailResponseBean);
	}

	@PostMapping("/insert-into-material-payment-temp")
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(@RequestBody LCCertPrintRequestBean lcCertPrintRequestBean){
		return this.LCCertService.insertIntoMaterialPaymentTemp(lcCertPrintRequestBean);
	}
	
	@PutMapping("/update-print-status")
	public ResponseEntity<?> updatePrintStatus(@RequestBody LCCertPrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean){
		return this.LCCertService.updateLCcertPrintStatus(printStatusUpdateDetailRequestBean);
	}

	@DeleteMapping("/delete-temp-table-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.LCCertService.deleteTempTableFromSessionId(sessionId);
	}
	
}
