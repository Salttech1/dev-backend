package kraheja.sales.outgoing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.sales.bean.request.OutgoingDefaultersReportRequestBean;
import kraheja.sales.bean.request.OutgoingReportsRequestBean;
import kraheja.sales.bean.request.OutgoingSocietyAccountsReportRequestBean;
import kraheja.sales.bean.request.OutgoingSummaryRequestBean;
//import kraheja.sales.outgoing.GstInfoDto;
import kraheja.sales.outgoing.service.OutgoingReportsService;

@RestController
@RequestMapping("/outgoingreports")
public class OutgoingReportsController {

	@Autowired
	private OutgoingReportsService outgoingReportsService;

//	@PostMapping("/add-into-infra-defaulters-list-temp-table")
//	public ResponseEntity<?> addIntoInfraDefaultersListTempTable(@RequestBody InfraDefaultersListRequestBean infraDefaultersListRequestBean){
//		return this.infraService.addIntoInfraDefaultersListTempTable(infraDefaultersListRequestBean); 
//	}

//    @GetMapping("/get-gst-data")
//    public ResponseEntity<?> getGstData(@RequestBody GstInfoDto gstInfoDto )
//    {
//        return this.outgoingReportsService.getGstData(gstInfoDto);
//    }

//  @GetMapping("/get-first-og-date")
//  public ResponseEntity<?> getFirstOgDate(String flatOwner)
//  {
//      return this.outgoingReportsService.getFirstOgDate(flatOwner);
//  }

	@PostMapping("/generate-billdata")
	public ResponseEntity<?> inserttempowner(@RequestBody OutgoingReportsRequestBean outgoingReportsRequestBean) {
		return this.outgoingReportsService.processOgBills(outgoingReportsRequestBean);
	}

	@PostMapping("/add-into-outgoing-summary-report-temp-table")
	public ResponseEntity<?> addIntoOGSummaryTempTables(
			@RequestBody OutgoingSummaryRequestBean outgoingSummaryRequestBean) {
		System.out.println("data");

		return this.outgoingReportsService.addIntoOGSummaryTempTables(outgoingSummaryRequestBean);

	}

//	@DeleteMapping("/delete-temp-table-from-sessionId")
//	public ResponseEntity<?> truncateTempTable(Integer sessionId, Boolean isAgeing) {
//		return this.outgoingReportsService.deleteTempTableFromSessionId(sessionId);
//	}

	@PostMapping("/add-into-outgoing-defaulters-report-temp-table")
	public ResponseEntity<?> addIntoOGDefaultersListTempTables(
			// 07.11.23 RS -- Add data in temp table for Defaulters List Report
			@RequestBody OutgoingDefaultersReportRequestBean outgoingDefaultersReportRequestBean) {

		return this.outgoingReportsService.addIntoOGDefaultersListTempTables(outgoingDefaultersReportRequestBean);

	}

	@PostMapping("/add-into-society-accounts-report-temp-table")
	public ResponseEntity<?> processSocietyAccountsReport(
			// 24.11.23 RS -- Add data in temp table for Society Accounts Report
			@RequestBody OutgoingSocietyAccountsReportRequestBean outgoingSocietyAccountsReportRequestBean) {

		return this.outgoingReportsService.processSocietyAccountsReport(outgoingSocietyAccountsReportRequestBean);

	}

//	@DeleteMapping("/delete-soc-acc-temp-data")
//	public ResponseEntity<?> deleteSocAccTempData(Integer sessionId, Boolean isAgeing) {
//		return this.outgoingReportsService.deleteSocAccTempData(sessionId);
//	}

}
