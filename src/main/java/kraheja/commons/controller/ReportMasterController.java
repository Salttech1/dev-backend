package kraheja.commons.controller;

import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.entity.ReportMaster;
import kraheja.commons.repository.ReportJobsTransactionRepository;
import kraheja.commons.repository.ReportMasterRepository;
import kraheja.commons.service.ReportMasterService;

@RestController
@RequestMapping("/report")
public class ReportMasterController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ReportMasterRepository reportMasterRepository;
    
    private final ReportMasterService reportMasterService;
    
	@Autowired
	private  ReportJobsTransactionRepository reportJobsTransactionRepository;
    
    @Autowired
    public ReportMasterController(ReportMasterRepository reportMasterRepository,
			ReportMasterService reportMasterService) {
		this.reportMasterRepository = reportMasterRepository;
		this.reportMasterService = reportMasterService;
	}

    @GetMapping
    public ResponseEntity<?> getReports(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reportMasterRepository.findAll(PageRequest.of(page, size)));
    }

	@GetMapping(value = "/{id}")
    public ResponseEntity<?> getReport(@PathVariable long id) {
        Optional<ReportMaster> report = reportMasterRepository.findById(id);
        if (report.isPresent()) {
            return ResponseEntity.ok(report.get());
        }
        return ResponseEntity.notFound().build();
    }

//    @PostMapping
//    public ResponseEntity<?> addReport(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
//    	if(Objects.nonNull(reportMasterRequestBean))
//    		return this.reportMasterService.addReport(reportMasterRequestBean);
//    	return ResponseEntity.badRequest().build();
//    }
//    
//    @PutMapping
//    public ResponseEntity<?> updateReport(@RequestBody ReportMasterRequestBean reportMasterRequestBean){
//    	if(Objects.nonNull(reportMasterRequestBean))
//    			return this.reportMasterService.updateReport(reportMasterRequestBean);
//    	return ResponseEntity.badRequest().build();
//    }
//    
    @PostMapping(value = "/generate-report-parameter")
    public ResponseEntity<?> generateReportWithParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
    	if(Objects.nonNull(reportMasterRequestBean))
    		return this.reportMasterService.generateReport(reportMasterRequestBean);
    	return ResponseEntity.badRequest().build();
    }
//    
//    @PostMapping(value = "/generate-report-condition")
//    public ResponseEntity<?> generateReportWithCondition(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
//    	if(Objects.nonNull(reportMasterRequestBean))
//    		return this.reportMasterService.generateReportWithCondition(reportMasterRequestBean);
//    	return ResponseEntity.badRequest().build();
//    }
//    
    @PostMapping(value = "/generate-report-condition-and-parameter")
    public ResponseEntity<?> generateReportWithConditionAndParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
		return this.reportMasterService.generateReport(reportMasterRequestBean);
    }
    
    @PostMapping(value = "/generate-report-ttx")
    public ResponseEntity<?> generateReportTtx(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
		return this.reportMasterService.generateReport(reportMasterRequestBean);
    }
    
    @GetMapping(value = "/fetch-report-job-transaction-by-status-and-userid")
    public ResponseEntity<?> fetchReportJobTransactionByStatusAndUserid() {
		return this.reportMasterService.fetchReportJobTransactionByStatusAndUserid();
    }
    
    @GetMapping(value = "/fetch-report-job-transaction-by-reportid")
    public ResponseEntity<?> fetchReportJobTransactionByReportid(Integer reportJobTransactionId) {
		return this.reportMasterService.fetchReportJobTransactionByReportid(reportJobTransactionId);
    }
    
    @DeleteMapping(value = "/delete-report-job-transaction-by-reportid")
    public ResponseEntity<?> deleteReportJobTransactionByReportid(Integer reportJobTransactionId) {
		return this.reportMasterService.deleteReportJobTransactionByReportid(reportJobTransactionId);
    }
    
    @PostMapping(value = "/generate-report-ttx-condition")
    public ResponseEntity<?> generateReportTtxWithCondition(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
    	if(Objects.nonNull(reportMasterRequestBean))
    		return this.reportMasterService.generateReport(reportMasterRequestBean);
    	return ResponseEntity.badRequest().build();
    }
    
    @PostMapping(value = "/generate-ttx-report-with-only-formula")
    public ResponseEntity<?> generateTtxReportWithOnlyFormula(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
		return this.reportMasterService.generateReport(reportMasterRequestBean);
    }
    
    @PostMapping(value = "/generate-report-with-multiple-condition-and-parameter")
    public ResponseEntity<?> generateReportWithMultipleConditionAndParameter(@RequestBody ReportMasterRequestBean reportMasterRequestBean) {
		return this.reportMasterService.generateReport(reportMasterRequestBean);
    }
}