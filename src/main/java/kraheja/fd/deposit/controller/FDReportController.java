package kraheja.fd.deposit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.ActiveDepositReportRequestBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.utils.CommonConstraints;
import kraheja.fd.deposit.bean.request.NeftReportRequestBean;
import kraheja.fd.deposit.service.FDReportService;
import kraheja.feign.internal.ReportInternalFeignClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/fd-report")
public class FDReportController {

	@Autowired
	private FDReportService fdReportService; 

	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;

	@GetMapping("/fetch-neft-report-fromDate-and-toDate-and-coyBankAC")
	public ResponseEntity<ServiceResponseBean> fetchNeftReportFromDateAndTodate() {
		return this.fdReportService.fetchNeftReportFromDateAndTodateAndCoyBank();
	}
	
	@PostMapping("/fetch-max-trandate-by-transernos")
	public ResponseEntity<?> findMaxranDateByTranserNos(@RequestBody List<String> transerNos){
		return this.fdReportService.findMaxranDateByTranserNos(transerNos);
	}

	@PostMapping("/export-neft-report")
	public ResponseEntity<?> exportNeftReport(@Valid @RequestBody NeftReportRequestBean neftReportRequestBean){
		return this.fdReportService.exportNeftReport(neftReportRequestBean);
	}

	@GetMapping("/fetch-fixed-deposit-receipt-parameters")
	public ResponseEntity<?> fetchFixedDepositReceiptParamaters(String companyCode, String depositorId, String receiptNum){
		return this.fdReportService.fetchFixedDepositRecieptReportParameters(companyCode, depositorId, receiptNum);
	}

	//API FOR ACTIVE DEPOSIT REPORTS PERIOD WISE
	@PostMapping(value = "/generate-active-deposit-report")
	public ResponseEntity<InputStreamResource> generateReportWithMultipleConditionAndParameter(@RequestBody ActiveDepositReportRequestBean activeDepositReportRequestBean) {
		File tempFile;
		FileOutputStream fos = null;
		try {
			byte[] byteArray = this.reportInternalFeignClient.generateActiveDepositReport(activeDepositReportRequestBean);
			log.info("BYTE ARRAY :: {}", byteArray.length);

			tempFile = File.createTempFile("Filename", "suffix", null);
		
		fos = new FileOutputStream(tempFile);
		fos.write(byteArray);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.pdf"))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.body(new InputStreamResource(new FileInputStream(tempFile)));
		}catch (Exception e) {
			if(e.getCause().getMessage().equals(HttpStatus.NOT_FOUND.toString()))
				return ResponseEntity.notFound().build();
			if(e.getCause().getMessage().equals(HttpStatus.BAD_REQUEST.toString()))
				return ResponseEntity.badRequest().build();
			e.printStackTrace();
		}finally{
				try {
					if(Objects.nonNull(fos))
						fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
