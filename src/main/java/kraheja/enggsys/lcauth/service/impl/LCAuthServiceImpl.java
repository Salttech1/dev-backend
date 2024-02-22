package kraheja.enggsys.lcauth.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
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

import kraheja.arch.projbldg.dataentry.bean.response.BuildingResponseBean;
import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.CodeHelpPartyBean;
import kraheja.commons.bean.ExnarrBean;
import kraheja.commons.bean.request.InchqRequestBean;
import kraheja.commons.bean.request.ReportMasterRequestBean;
import kraheja.commons.bean.response.CompanyResponseBean;
import kraheja.commons.bean.response.EpworksResponseBean;
import kraheja.commons.bean.response.HsnsacmasterResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.enums.HSMSTypeEnum;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.PrintStatusEnum;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.ExnarrMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.EpworksRepository;
import kraheja.commons.repository.ExnarrRepository;
import kraheja.commons.repository.HsnsacmasterRepository;
import kraheja.commons.repository.InchqRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonResultsetGenerator;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.CurrencyConverterUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.GenericExnarrLogic;
import kraheja.commons.utils.ValueContainer;
import kraheja.feign.internal.ReportInternalFeignClient;
import kraheja.enggsys.bean.LCAuthPrintDetailBean;
import kraheja.enggsys.bean.request.LCAuthPrintRequestBean;
import kraheja.enggsys.bean.request.LCAuthRequestBean;
import kraheja.enggsys.bean.request.LCAuthViewRequestBean;
import kraheja.enggsys.bean.request.LCAuthPrintStatusUpdateDetailRequestBean;
import kraheja.enggsys.bean.response.ItemDetailResponseBean;
import kraheja.enggsys.bean.response.LCAuthPrintDetailResponseBean;
import kraheja.enggsys.lcauth.service.LCAuthService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LCAuthServiceImpl implements LCAuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@PersistenceContext
	private EntityManager entityManager;


	@Autowired
	private CompanyRepository companyRepository;


	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	ReportInternalFeignClient reportInternalFeignClient;

	@Value("${report-jobs-path}")
	private String reportJobPath;

	// @Autowired
	// private EntityRepository entityRepository;

	@Override
	public ResponseEntity<?> mergePdf(LCAuthPrintDetailResponseBean LCAuthPrintDetailResponseBean) {
		List<String> finalReportLocationList = new ArrayList<>();
		File file = null;

		try {
			for (String authNum : LCAuthPrintDetailResponseBean.getAuthNumList()) {

				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("LCauthFrom", authNum);
					map.put("LCauthTo", authNum);

					// Make the Feign client request
					byte[] ogByteArray = this.reportInternalFeignClient.generateReportWithMultipleConditionAndParameter(
							ReportMasterRequestBean.builder().name("MaterialPayment_LCAuthPrint.rpt")
									.reportParameters(map).seqId(1).isPrint(false).build());

					byte[] duplicateByteArray = this.reportInternalFeignClient
							.generateReportWithMultipleConditionAndParameter(
									ReportMasterRequestBean.builder().name("MaterialPayment_LCAuthPrint - Copy.rpt")
											.reportParameters(map).seqId(1).isPrint(false).build());

					// GENERATE REPORT OG AND DUPLICATE
					String ogRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(authNum.concat("OG"));
					String ogReportLocation = reportJobPath.concat(ogRandomUUID).concat(".pdf");
					String duplicateRandomUUID = CommonUtils.INSTANCE.uniqueIdentifier(authNum.concat("DUPLICATE"));
					String duplicateReportLocation = reportJobPath.concat(duplicateRandomUUID).concat(".pdf");

					try (FileOutputStream fos = new FileOutputStream(ogReportLocation)) {
						fos.write(ogByteArray);
						finalReportLocationList.add(ogReportLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}

					try (FileOutputStream fos = new FileOutputStream(duplicateReportLocation)) {
						fos.write(duplicateByteArray);
						finalReportLocationList.add(duplicateReportLocation);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (LCAuthPrintDetailResponseBean.getSerList() != null
							&& !LCAuthPrintDetailResponseBean.getSerList().isEmpty()) {
						for (Map.Entry<String, String> entry : LCAuthPrintDetailResponseBean.getSerList()
								.entrySet()) {
							if (authNum.equals(entry.getValue())) {
								Map<String, Object> debitNoteParamMap = new HashMap<String, Object>();
								debitNoteParamMap.put("serFrom", entry.getKey());
								debitNoteParamMap.put("serTo", entry.getKey());

								byte[] debitNoteByteArray = this.reportInternalFeignClient
										.generateReportWithMultipleConditionAndParameter(ReportMasterRequestBean
												.builder().name("MaterialPymt_Auth_DB_Print_1_New.rpt")
												.reportParameters(debitNoteParamMap).seqId(1).isPrint(false).build());

								for (int i = 0; i < 3; i++) {
									String debitNoteRandomUUID = CommonUtils.INSTANCE
											.uniqueIdentifier(authNum.concat("DebitNote"));
									String debitNoteReportLocation = reportJobPath.concat(debitNoteRandomUUID)
											.concat(".pdf");

									try (FileOutputStream debitNoteFos = new FileOutputStream(
											debitNoteReportLocation)) {
										debitNoteFos.write(debitNoteByteArray);
										finalReportLocationList.add(debitNoteReportLocation);
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				} catch (Exception ex) {
					// Handle the 404 error
					continue; // Continue to the next iteration
				}
			}
			LOGGER.info("finalReportLocationList :: {}", finalReportLocationList);
			if (CollectionUtils.isNotEmpty(finalReportLocationList)) {
				PDFMergerUtility merger = new PDFMergerUtility();
				// Add input PDF files to the merger
				for (String reportLocation : finalReportLocationList) {
					merger.addSource(reportLocation);
				}
				String randomUUID = CommonUtils.INSTANCE.uniqueIdentifier("MERGED");

				String reportLocation = reportJobPath.concat(randomUUID).concat(".pdf");
				// Set the output file location
				merger.setDestinationFileName(reportLocation);

				// Merge the PDF files
				merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
				file = new File(reportLocation);

				// DELETE TEMPORY FILES CREATED
				deleteFiles(finalReportLocationList);

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, CommonConstraints.INSTANCE.ATTACHMENT_FILENAME_STRING
								.concat(LCAuthPrintDetailResponseBean.getAuthNumberFrom().concat("-")
										.concat(LCAuthPrintDetailResponseBean.getAuthNumberTo()).concat(".pdf")))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
						.body(new InputStreamResource(new FileInputStream(file)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

	@Override
	public ResponseEntity<?> updateLCAuthPrintStatus(LCAuthPrintStatusUpdateDetailRequestBean LCAuthprintStatusUpdateDetailRequestBean) {
//		List<LCAuthprint> LCAuthprintEntityList = this.LCAuthprintRepository
//				.findByTempmatauthprintCK_Sessid(Double.valueOf(LCAuthprintStatusUpdateDetailRequestBean.getSessionId()));
//		LOGGER.info("LCAuthprintEntityList :: {}", LCAuthprintEntityList);
//
//		if (CollectionUtils.isNotEmpty(LCAuthprintEntityList)) {
//			List<Auth_H> authHEntityList = this.authHRepository
//					.findByAuthhCK_AuthAuthnumIn(LCAuthprintEntityList.stream().map(tempMatAuthprintEntity -> {
//						return tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum();
//					}).collect(Collectors.toList()));
//			LOGGER.info("authHEntityList :: {}", authHEntityList);
//
//			if (Objects.nonNull(LCAuthprintStatusUpdateDetailRequestBean.getSerList())) {
//				dbnotehEntityList = this.dbnotehRepository
//						.findByDbnotehCK_DbnhDbnoteserIn(LCAuthprintStatusUpdateDetailRequestBean.getSerList());
//				LOGGER.info("dbnotehEntityList :: {}", dbnotehEntityList);
//			}
//			if (CollectionUtils.isNotEmpty(authHEntityList)) {
//				for (LCAuthprint tempMatAuthprintEntity : LCAuthprintEntityList) {
//					for (Auth_H authHEntity : authHEntityList) {
//						if (tempMatAuthprintEntity.getTempmatauthprintCK().getAuthAuthnum().trim()
//								.equals(authHEntity.getAuthhCK().getAuthAuthnum().trim())) {
//							authHEntity.setAuthPrinted(Objects.nonNull(tempMatAuthprintEntity.getNoOf_Print())
//									? Double.valueOf(tempMatAuthprintEntity.getNoOf_Print())
//									: null);
//							authHEntity.setAuthPrintedon(LocalDateTime.now());
//							authHEntity.setAuthToday(LocalDateTime.now());
//							authHEntity.setAuthUserid(GenericAuditContextHolder.getContext().getUserid());
//							authHEntity.setAuthSite(GenericAuditContextHolder.getContext().getSite());
//							authHEntity.setAuthAuthstatus(authHEntity.getAuthAuthstatus().trim().equals("1") ? "2"
//									: authHEntity.getAuthAuthstatus());
//							this.authHRepository.save(authHEntity);
//						}
//					}
//					
//
//				}
//
//			}
//
//			return ResponseEntity
//					.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
//		}
		return ResponseEntity
				.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Record Found").build());

	}


	public static void deleteFiles(List<String> fileLocations) {
		for (String fileLocation : fileLocations) {
			File file = new File(fileLocation);
			if (file.exists()) {
				if (file.delete()) {
				} else {
				}
			} else {
			}
		}
	}
}
