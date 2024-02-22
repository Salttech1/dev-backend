package kraheja.purch.materialpayments.controller;


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
import kraheja.purch.bean.request.CancelMaterialPaymentRequestBean;
import kraheja.purch.bean.request.MaterialDetailRequestBean;
import kraheja.purch.bean.request.MaterialPaymentPrintRequestBean;
import kraheja.purch.bean.request.MaterialPaymentViewRequestBean;
import kraheja.purch.bean.request.PassMaterialPaymentRequestBean;
import kraheja.purch.bean.request.PrintStatusUpdateDetailRequestBean;
import kraheja.purch.bean.request.RemarkDetailRequestBean;
import kraheja.purch.bean.response.TempMatAuthPrintDetailResponseBean;
import kraheja.purch.materialpayments.service.MaterialPaymentsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/material-payments")
public class MaterialPaymentsController {
	
	@Autowired
	private MaterialPaymentsService materialPaymentsService;
	
	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;
	
	@GetMapping("/authorisation-enquiry")
	public ResponseEntity<?> fetchAuthorisationEnquiry(String supplierCode, String authNos){
		return this.materialPaymentsService.fetchAuthorisationEnquiry(supplierCode, authNos);
	}
	
	@GetMapping("/material-payments-status-enquiry")
	public ResponseEntity<?> fetchMaterialPaymentStatusEnquiry(String buildingCodes, String materialCodes,
			String supplierCodes, String authNos, String authTypes, String authFromDate, String authToDate){
		return this.materialPaymentsService.fetchMaterialPaymentStatusEnquiry(buildingCodes, materialCodes, supplierCodes, authNos, authTypes, authFromDate, authToDate);
	}
	
	@PostMapping("/add-material")
	public ResponseEntity<?> addMaterial(@RequestBody MaterialDetailRequestBean materialDetailRequestBean){
		return this.materialPaymentsService.addMaterial(materialDetailRequestBean);
	}
	
	@GetMapping("/fetch-item-by-bldgCode-and-matGrp")
	public ResponseEntity<?> fetchItemByBldgCodeAndMatGrp(String bldgCode, String matGrp){
		return this.materialPaymentsService.fetchItemByBldgCodeAndMatGrp(bldgCode, matGrp);
	}
	
	@GetMapping("/fetch-authorisations-by-partyCode-buildingCode-matGroup")
	public ResponseEntity<?> fetchAuthorisationsByPartyCodeAndBuildingCodeAndMatGroup(String partyCode, String buildingCode,
			String matGroup){
		return this.materialPaymentsService.fetchAuthorisationsForPartyAndBuildingAndMatGroup(partyCode, buildingCode, matGroup);
	}
	
	@GetMapping("/fetch-authorisations-details-by-authNum")
	public ResponseEntity<?> fetchAuthorisationDetailsByAuthNum(String authNum){
		return this.materialPaymentsService.fetchAuthorisationDetailsByAuthNum(authNum);
	}
	
	@GetMapping("/fetch-pass-material-payment-list")
	public ResponseEntity<?> fetchPassMaterialPaymentList(){
		return this.materialPaymentsService.fetchPassMaterialPaymentList();
	}

	
	@GetMapping("/check-authorisation-valid-for-cancelation")
	public ResponseEntity<?> checkIsAuthorisationValidForCancelation(String authTranser, String authStatus){
		return this.materialPaymentsService.checkIsAuthorisationValidForCancelation(authTranser, authStatus);
	}

	
	@PostMapping("/pass-material-payment")
	public ResponseEntity<?> passMaterialPayment(@RequestBody PassMaterialPaymentRequestBean materialPaymentRequestBean){
		return this.materialPaymentsService.passMaterialPayment(materialPaymentRequestBean);
	}
	
	@GetMapping("/fetch-advance-paid-detail")
	public ResponseEntity<?> fetchAdvancePaidDetail(String partyCode, String buildingCode, String matGroup, String advanceAdjust){
		return this.materialPaymentsService.fetchAdvancePaidDetail(partyCode, buildingCode, matGroup, advanceAdjust);
	}

	@PutMapping("/cancel-material-payments")
	public ResponseEntity<?> cancelMaterialPayments(@RequestBody List<CancelMaterialPaymentRequestBean> cancelMaterialPaymentRequestBeanList){
		return this.materialPaymentsService.cancelMaterialPayment(cancelMaterialPaymentRequestBeanList);
	}

	@GetMapping("/fetch-exclude-building-detail")
	public ResponseEntity<?> fetchExcludeBuildingDetail(){
		return this.materialPaymentsService.fetchExcludeBuildingDetail();
	}
	
	@PostMapping("/fetch-remarks-from-suppbillno")
	public ResponseEntity<?> fetchRemarksFromSuppbillno(@RequestBody RemarkDetailRequestBean remarkDetailRequestBean){
		return this.materialPaymentsService.fetchRemarksFromSuppbillno(remarkDetailRequestBean);
	}
	
	@PostMapping("/fetch-bill-detail")
	public ResponseEntity<?> fetchBillDetail(@RequestBody RemarkDetailRequestBean remarkDetailRequestBean){
		return this.materialPaymentsService.fetchBillDetail(remarkDetailRequestBean);
	}
	
	//API for OutStanding Advance And Retention Report
	@PostMapping(value = "/generate-outstanding-adv-and-reten-report")
	public ResponseEntity<InputStreamResource> generateReportWithMultipleConditionAndParameter(@RequestBody OSAdvAndRetReportRequestBean OSAdvAndRetReportRequestBean) {
		File tempFile;
		FileOutputStream fos = null;
		try {
			byte[] byteArray = this.reportInternalFeignClient.generateOutStandingAdvanceAndRetentionReport(OSAdvAndRetReportRequestBean);
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

	@GetMapping("/fetch-material-payment-view")
	public ResponseEntity<?> fetchMaterialPaymentView(@RequestBody MaterialPaymentViewRequestBean materialPaymentViewRequestBean){
		return this.materialPaymentsService.fetchMaterialPaymentView(materialPaymentViewRequestBean);
	}
	
	@PostMapping("/fetch-paid-bill-detail")
	public ResponseEntity<?> fetchPaidBillDetail(@RequestBody RemarkDetailRequestBean remarkDetailRequestBean){
		return this.materialPaymentsService.fetchPaidBillDetail(remarkDetailRequestBean);
	}
	
	@PostMapping("/save-material-payment")
	public ResponseEntity<?> saveMaterialPayment(@RequestBody MaterialDetailRequestBean materialDetailRequestBean){
		return this.materialPaymentsService.saveMaterialPayment(materialDetailRequestBean);
	}
	
	@GetMapping("/fetch-debit-type-by-building")
	public ResponseEntity<?> fetchDebitTypeByBuilding(String buildingCode){
		return this.materialPaymentsService.fetchDebitTypeByBuilding(buildingCode);
	}
	
	@PostMapping("/insert-into-material-payment-temp")
	public ResponseEntity<?> insertIntoMaterialPaymentTemp(@RequestBody MaterialPaymentPrintRequestBean materialPaymentPrintRequestBean){
		return this.materialPaymentsService.insertIntoMaterialPaymentTemp(materialPaymentPrintRequestBean);
	}
	
	@PutMapping("/update-print-status")
	public ResponseEntity<?> updatePrintStatus(@RequestBody PrintStatusUpdateDetailRequestBean printStatusUpdateDetailRequestBean){
		return this.materialPaymentsService.updatePrintStatus(printStatusUpdateDetailRequestBean);
	}
	
	@GetMapping("/fetch-code-help-detail")
	public ResponseEntity<?> fetchCodeHelpDetail(String tableName, String matLevel){
		return this.materialPaymentsService.fetchCodeHelpDetail(tableName, matLevel);
	}
	
	@DeleteMapping("/delete-temp-table-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.materialPaymentsService.deleteTempTableFromSessionId(sessionId);
	}
	
	@PostMapping("/merge-pdf")
	public ResponseEntity<?> mergePdf(@RequestBody TempMatAuthPrintDetailResponseBean TempMatAuthPrintDetailResponseBean){
		return this.materialPaymentsService.mergePdf(TempMatAuthPrintDetailResponseBean);
	}
	
}
