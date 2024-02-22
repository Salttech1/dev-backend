package kraheja.commons.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.bean.response.ReportJobsTransactionResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.ReportJobsTransaction;
import kraheja.commons.entity.ReportMaster;
import kraheja.commons.enums.ExportType;
import kraheja.commons.enums.ReportProgressStatusEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.DatasourceMasterRepository;
import kraheja.commons.repository.ReportJobsTransactionRepository;
import kraheja.commons.repository.ReportMasterRepository;
import kraheja.commons.service.ReportMasterService;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.feign.internal.ReportInternalFeignClient;

@Service
@Transactional
public class ReportMasterServiceImpl implements ReportMasterService {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private  ReportMasterRepository reportMasterRepository;

	@Autowired
	private  DatasourceMasterRepository datasourceMasterRepository;

	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;

	@Autowired
	private  ReportJobsTransactionRepository reportJobsTransactionRepository;

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Override
	public ResponseEntity<?> fetchReportJobTransactionByStatusAndUserid() {
		List<ReportJobsTransaction> reportJobsTransactionList = this.reportJobsTransactionRepository.findByCreatedByAndStatusIn(GenericAuditContextHolder.getContext().getUserid(), ReportProgressStatusEnum.getAllValues());
		logger.info("ReportJobsTransaction :: {}", reportJobsTransactionList);

		if(CollectionUtils.isNotEmpty(reportJobsTransactionList)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().data(
					reportJobsTransactionList.stream().sorted(Comparator.comparing(ReportJobsTransaction:: getCreatedDate).reversed()).map(reportJobsTransaction -> {
						return	ReportJobsTransactionResponseBean.builder()
								.id(reportJobsTransaction.getId())
								.reportMasterId(reportJobsTransaction.getReportMasterId())
								.reportLocation(reportJobsTransaction.getReportLocation())
								.status(reportJobsTransaction.getStatus())
								.exportType(reportJobsTransaction.getExportType())
								.createdBy(reportJobsTransaction.getCreatedBy())
								.reportName(reportJobsTransaction.getReportName())
								.updatedDate(Objects.nonNull(reportJobsTransaction.getUpdatedDate()) ? reportJobsTransaction.getUpdatedDate().format(CommonConstraints.INSTANCE.DDMMYYYY_HH_MM_SS_FORMATTER) : null)
								.createdDate(Objects.nonNull(reportJobsTransaction.getCreatedDate()) ? reportJobsTransaction.getCreatedDate().format(CommonConstraints.INSTANCE.DDMMYYYY_HH_MM_SS_FORMATTER) : null)
								.build();
					}).collect(Collectors.toList())
					).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> fetchReportJobTransactionByReportid(Integer reportJobTransactionId) {
		Optional<ReportJobsTransaction> reportJobsTransaction = this.reportJobsTransactionRepository.findById(reportJobTransactionId);
		logger.info("ReportJobsTransaction :: {}", reportJobsTransaction);

		if(reportJobsTransaction.isPresent()) {
			File file = new File(reportJobsTransaction.get().getReportLocation());
			try {
				return getReportExportResponse(reportJobsTransaction.get(), file);
				//				return ResponseEntity.ok()
				//						.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.pdf"))
				//						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				//						.body(new InputStreamResource(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> deleteReportJobTransactionByReportid(Integer reportJobTransactionId) {
		Optional<ReportJobsTransaction> reportJobsTransaction = this.reportJobsTransactionRepository.findById(reportJobTransactionId);
		logger.info("ReportJobsTransaction :: {}", reportJobsTransaction);

		if(reportJobsTransaction.isPresent()) { 
			this.reportJobsTransactionRepository.deleteById(reportJobTransactionId);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Removed successfully").build());
		}    	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> generateReport(ReportMasterRequestBean reportMasterRequestBean) {
		Optional<ReportMaster> reportMaster = this.reportMasterRepository.findByName(reportMasterRequestBean.getName());
		System.out.println("ReportMaster :: "+ reportMaster);
		FileOutputStream fos = null;
		try {
			if(reportMaster.get().getIsInstantReport()) {
				byte[] byteArray = new byte[]{};

				if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-report-ttx")) {			
					byteArray = this.reportInternalFeignClient.generateReportTtx(reportMasterRequestBean);
				}
				else if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-ttx-report-with-only-formula")) {			
					byteArray = this.reportInternalFeignClient.generateTtxReportWithOnlyFormula(reportMasterRequestBean);

				}
				else if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-report-parameter")) {			
					byteArray = this.reportInternalFeignClient.generateReportWithParameter(reportMasterRequestBean);
				}
				else if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-report-ttx-condition")) {			
					byteArray = this.reportInternalFeignClient.generateReportTtxWithCondition(reportMasterRequestBean);
				}
				else if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-report-condition-and-parameter")) {			
					byteArray = this.reportInternalFeignClient.generateReportWithConditionAndParameter(reportMasterRequestBean);
				}
				else if(GenericAuditContextHolder.getContext().getUri().equals("/report/generate-report-with-multiple-condition-and-parameter")) {			
					byteArray = this.reportInternalFeignClient.generateReportWithMultipleConditionAndParameter(reportMasterRequestBean);
				}

				logger.info("BYTE ARRAY :: {}", byteArray.length);
				File tempFile;

				tempFile = File.createTempFile("Filename", "suffix", null);
				fos = new FileOutputStream(tempFile);
				fos.write(byteArray);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.pdf")) //don't worry about the tempfile.pdf
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.body(new InputStreamResource(new FileInputStream(tempFile)));
			}else {
				String writeValueAsString = CommonUtils.INSTANCE.objectMapper.writeValueAsString(reportMasterRequestBean);
				this.reportJobsTransactionRepository.save(
						ReportJobsTransaction.builder()
						.reportMasterId(reportMaster.get().getId())
						.id(Integer.parseInt(GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#RJOB")))
						.status(ReportProgressStatusEnum.IN_PROGRESS.toString())
						.value(writeValueAsString)
						.createdBy(GenericAuditContextHolder.getContext().getUserid())
						.createdDate(LocalDateTime.now())
						.reportName(reportMaster.get().getName())
						.build());
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Report added successfully in background processing..").build());
			}
		}catch (Exception e) {
			return ResponseEntity.notFound().build();
		}finally{
			try {
				if(Objects.nonNull(fos))
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}	

	private ResponseEntity<?> getReportExportResponse(ReportJobsTransaction reportJobsTransaction, File file) throws FileNotFoundException{
		switch(ExportType.getValue(reportJobsTransaction.getExportType().trim())) {
		case PDF: {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.pdf"))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.body(new InputStreamResource(new FileInputStream(file)));
		}
		case EXCEL: {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING.concat("TempFile.xls"))
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
					.body(new InputStreamResource(new FileInputStream(file)));
		}
		default :{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
		}
	}
}
