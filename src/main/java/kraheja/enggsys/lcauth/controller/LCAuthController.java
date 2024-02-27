package kraheja.enggsys.lcauth.controller;


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
import kraheja.enggsys.bean.request.LCAuthPrintRequestBean;
import kraheja.enggsys.bean.request.LCAuthViewRequestBean;
import kraheja.enggsys.bean.request.LCAuthPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.LCAuthPrintDetailResponseBean;
import kraheja.enggsys.lcauth.service.LCAuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lcauth")
public class LCAuthController {
	
	@Autowired
	private LCAuthService LCAuthService;
	
	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;
	
	@PostMapping("/merge-pdf")
	public ResponseEntity<?> mergePdf(@RequestBody LCAuthPrintDetailResponseBean LCAuthPrintDetailResponseBean){
		return this.LCAuthService.mergePdf(LCAuthPrintDetailResponseBean);
	}

	@PostMapping("/insert-into-material-payment-temp")
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(@RequestBody LCAuthPrintRequestBean lcAuthPrintRequestBean){
		return this.LCAuthService.insertIntoMaterialPaymentTemp(lcAuthPrintRequestBean);
	}
	
	@PutMapping("/update-print-status")
	public ResponseEntity<?> updatePrintStatus(@RequestBody LCAuthPrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean){
		return this.LCAuthService.updateLCAuthPrintStatus(printStatusUpdateDetailRequestBean);
	}

	@DeleteMapping("/delete-temp-table-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.LCAuthService.deleteTempTableFromSessionId(sessionId);
	}
	
}
