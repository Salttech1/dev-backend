package kraheja.enggsys.management.dataentry.logicnote.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.enggsys.entity.Matcertlntendercommittee;
import kraheja.enggsys.entity.MatcertlntendercommitteeCK;
import kraheja.enggsys.entity.Matcertlnvendorhdr;
import kraheja.enggsys.entity.MatcertlnvendorhdrCK;
import kraheja.enggsys.entity.Matcertlnworkcodedtls;
import kraheja.enggsys.entity.MatcertlnworkcodedtlsCK;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.Commitee;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.NarrationAreaRequest;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.Vendor;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.WorkCodeDetailRequest;
import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;
import kraheja.enggsys.management.dataentry.logicnote.service.LogicNoteService;
import kraheja.enggsys.repository.MatcertlntendercommitteeRepository;
import kraheja.enggsys.repository.MatcertlnvendorhdrRepository;
import kraheja.enggsys.repository.MatcertlnworkcodedtlsRepository;
import kraheja.payload.GenericResponse;
import kraheja.purch.entity.Matcertlnhdr;
import kraheja.purch.entity.MatcertlnhdrCK;
import kraheja.purch.repository.MatcertlnhdrRepository;


@Service
@Transactional
public class LogicNoteServiceImpl implements LogicNoteService {

	private final MatcertlnhdrRepository matcertlnhdrRepository;
	private final MatcertlnworkcodedtlsRepository matcertlnworkcodedtlsRepository;
	private final MatcertlntendercommitteeRepository matcertlntendercommitteeRepository;
	private final MatcertlnvendorhdrRepository vendorhdrRepository;
	
	
	public LogicNoteServiceImpl(MatcertlnhdrRepository matcertlnhdrRepository, MatcertlnworkcodedtlsRepository matcertlnworkcodedtlsRepository, 
			MatcertlntendercommitteeRepository matcertlntendercommitteeRepository,
			MatcertlnvendorhdrRepository vendorhdrRepository) {
		this.matcertlnhdrRepository = matcertlnhdrRepository;
		this.matcertlnworkcodedtlsRepository = matcertlnworkcodedtlsRepository;
		this.matcertlntendercommitteeRepository = matcertlntendercommitteeRepository;
		this.vendorhdrRepository = vendorhdrRepository;
	}

	@Override
	public LogicNoteResponse retriveLogicNote(String projectCode, String logicNoteNum, String tenderCode) {
		NarrationAreaRequest headerRequest = this.fetchMatcertlnhdr(tenderCode, logicNoteNum);
		WorkCodeDetailRequest detailRequest = this.fetchDetail(logicNoteNum);
		return LogicNoteResponse.builder().tranType("E").logicNoteHeaderRequest(headerRequest).detailRequest(detailRequest).result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message(ApiResponseMessage.SUCCESS).build();
	}

	@Override
	public GenericResponse createLogicNote(String projectCode,String logicNote, String tenderCode, LogicNoteResponse logicNoteRequest) {
		String userId = GenericAuditContextHolder.getContext().getUserid();
		String siteName = GenericAuditContextHolder.getContext().getSite();
		LocalDateTime today = LocalDateTime.now();
		try {
			Matcertlnworkcodedtls matcertlnworkcodedtls = Matcertlnworkcodedtls.builder().build();
			List<Matcertlntendercommittee> commiteeEntityList = new ArrayList<>();
			List<Matcertlnvendorhdr> vendorHdrList = new ArrayList<>();
			Matcertlnhdr matcertlnhdr = Matcertlnhdr.builder().build();
			
			NarrationAreaRequest logicNoteHeaderRequest = logicNoteRequest.getLogicNoteHeaderRequest();
			WorkCodeDetailRequest detailRequest = logicNoteRequest.getDetailRequest();
			
			if (logicNoteRequest.getTranType().equals("E")) {
				String finalBillYN = matcertlnhdrRepository.findByMatcertlnhdrCK_MclhLogicnotenum(logicNote);
				if (finalBillYN.equals("Y")) {
					return GenericResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED).message(ApiResponseMessage.CANNOT_MODIFY_THIS_REOCRD).build();
				}
			}else {
				logicNote = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "LOGNT");
			}
				/*Here we well added all fields for persist. in @Field tranType condition use only for create logic note number and validate for final bill logic note. */
				
				// Create @Entity MATCERTLNHDR
				MatcertlnhdrCK matcertlnhdrCK = MatcertlnhdrCK.builder().mclhLogicnotenum(logicNote).mclhTendernum(tenderCode).build();
				matcertlnhdr.setMatcertlnhdrCK(matcertlnhdrCK);
				matcertlnhdr.setMclhPackagename(logicNoteHeaderRequest.getShortSubTask());
				matcertlnhdr.setMclhLocation(logicNoteHeaderRequest.getLocation());
				matcertlnhdr.setMclhImportunder(logicNoteHeaderRequest.getImportUnder());
				matcertlnhdr.setMclhRevisionno(logicNoteHeaderRequest.getRevision());
				matcertlnhdr.setMclhLogicnotedesc(logicNoteHeaderRequest.getPackageName());
				matcertlnhdr.setMclhLogicdate(logicNoteHeaderRequest.getDate());
				matcertlnhdr.setMclhWorkscope(logicNoteHeaderRequest.getScopeOfWork());
				matcertlnhdr.setMclhCommdesc1(logicNoteHeaderRequest.getCommercialDescription1());
				matcertlnhdr.setMclhCommdesc2(logicNoteHeaderRequest.getCommercialDescription2());
				matcertlnhdr.setMclhCommdesc3(logicNoteHeaderRequest.getFinalConsideration());
				matcertlnhdr.setMclhTechdesc(logicNoteHeaderRequest.getTechnicalDescription());
				matcertlnhdr.setMclhRecommjustification(logicNoteHeaderRequest.getRecommendationWithJustification());
				matcertlnhdr.setMclhCommercialterm(logicNoteHeaderRequest.getDeliverySchedule());
				matcertlnhdr.setMclhWarrentyinfo(logicNoteHeaderRequest.getWarrantyInformation());
				matcertlnhdr.setMclhResourceallocation(logicNoteHeaderRequest.getResourceAllocation());
				matcertlnhdr.setMclhTenderremarks(logicNoteHeaderRequest.getTenderNarrative());
				matcertlnhdr.setMclhImpspecification(logicNoteHeaderRequest.getImportantSpecification());
				matcertlnhdr.setMclhUserid(userId);
				matcertlnhdr.setMclhToday(today);
				matcertlnhdr.setMclhSite(siteName);
				
				
				// CREATE ENTITY MATCERTLNWORKCODEDTLS 
				matcertlnworkcodedtls.setMatcertlnworkcodedtlsCK(MatcertlnworkcodedtlsCK.builder()
						.mclwLogicnotenum(logicNote)
						.mclwGroupcode(detailRequest.getGroupCode())
						.mclwSubgroupcode(detailRequest.getSubGroup())
						.mclwMatcerttype(detailRequest.getCertType())
						.mclwMatcertcode(detailRequest.getCertCode())
						.build());
				
				matcertlnworkcodedtls.setMclwAmount(detailRequest.getAmount());
				matcertlnworkcodedtls.setMclwUserid(userId);
				matcertlnworkcodedtls.setMclwSite(siteName);
				matcertlnworkcodedtls.setMclwToday(today);
				
				// CREATE ENTITY COMMITEE 
				List<Commitee> commiteeList = detailRequest.getCommiteeList();
				Integer commiteeSlNo = 1;
				for (Commitee commitee : commiteeList) {
					commiteeEntityList.add(Matcertlntendercommittee.builder()
					.matcertlntendercommitteeCK(MatcertlntendercommitteeCK.builder()
							.mclcEntrynum(logicNote)
							.mclcPersoncode(commitee.getCommiteeCode())
							.build())
					.mclcSite(siteName)
					.mclcSrno(String.valueOf(commiteeSlNo))
					.mclcToday(today)
					.mclcUserid(userId)
					.build());
					
					commiteeSlNo++;
				}
				
				List<Vendor> vendorList = detailRequest.getVendorList();
				Integer vendorSlNo = 1;
				for (Vendor vendor : vendorList) {
					vendorHdrList.add(Matcertlnvendorhdr.builder()
							.matcertlnvendorhdrCK(MatcertlnvendorhdrCK.builder().mcvhLogicnotenum(logicNote).mcvhVendorsrno(String.valueOf(vendorSlNo)).build())
							.mcvhVendorname(vendor.getVenderName())
//							.mcvhVendorcode(vendor.get)
//							.mcvhBgreceivedate(null)
//							.mcvhGrouplnnum(siteName)
							.mcvhVendorselectedyn(vendor.getYesNo())
							.mcvhSite(siteName)
							.mcvhUserid(userId)
							.mcvhToday(today)
							.mcvhPartytype(vendor.getPartyType())
							.mcvhPartycode(vendor.getPartyCode())
							.mcvhBrand(vendor.getBrand())
							.mcvhCurrencyrate(vendor.getCurrRate())
							.mcvhCurrencytype(vendor.getCurrType())
							.mcvhCurrencyamt(vendor.getCurrAmt())
							.mcvhQuotedate(vendor.getQuoteDate())
							.mcvhOrderdate(vendor.getOrderDate())
							.mcvhDeliverydate(vendor.getDeliveryDate())
							.mcvhDeliveryweeks(vendor.getDeliveryWeeks())
							.build());
					vendorSlNo++;
				}
				
				matcertlnhdrRepository.save(matcertlnhdr);
				matcertlnworkcodedtlsRepository.save(matcertlnworkcodedtls);
				matcertlntendercommitteeRepository.saveAll(commiteeEntityList);
				vendorhdrRepository.saveAll(vendorHdrList);
				
			return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message(ApiResponseMessage.SUCCESSFULLY_PERSIST).build();
		
		} catch (Exception e) {
			return GenericResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.INETNAL_SERVER_ERROR).message(ApiResponseMessage.INETNAL_SERVER_ERROR).build(); 
		}
}

	
	
	private WorkCodeDetailRequest fetchDetail(String logicNoteNum) {
		Matcertlnworkcodedtls detail = matcertlnworkcodedtlsRepository.findByMatcertlnworkcodedtlsCKMclwLogicnotenum(logicNoteNum);
		List<Matcertlntendercommittee> commiteeEntityList = matcertlntendercommitteeRepository.findByMatcertlntendercommitteeCKMclcEntrynumOrderByMclcSrno(logicNoteNum);
		List<Commitee> commiteeList = new ArrayList<>();
		
		for (Matcertlntendercommittee committeeEntity : commiteeEntityList) {
			commiteeList.add(Commitee.builder().commiteeCode(committeeEntity.getMatcertlntendercommitteeCK().getMclcPersoncode()).build());
		}
		
		
		List<Matcertlnvendorhdr> vendorHdrList = vendorhdrRepository.findByMatcertlnvendorhdrCKMcvhLogicnotenumOrderByMatcertlnvendorhdrCKMcvhVendorsrno(logicNoteNum);
		
		List<Vendor> venderList = new ArrayList<>();
		for (Matcertlnvendorhdr matcertlnvendorhdr : vendorHdrList) {
			venderList.add(Vendor.builder()
			.partyType(matcertlnvendorhdr.getMcvhPartytype())
			.partyCode(matcertlnvendorhdr.getMcvhPartycode())
			.venderName(matcertlnvendorhdr.getMcvhVendorname())
			.brand(matcertlnvendorhdr.getMcvhBrand())
			.yesNo(matcertlnvendorhdr.getMcvhVendorselectedyn())
			.groupLogicNote(matcertlnvendorhdr.getMcvhGrouplnnum())
			.currType(matcertlnvendorhdr.getMcvhCurrencytype())
			.currRate(matcertlnvendorhdr.getMcvhCurrencyrate())
			.currAmt(matcertlnvendorhdr.getMcvhCurrencyamt())
			.quoteDate(matcertlnvendorhdr.getMcvhQuotedate())
			.orderDate(matcertlnvendorhdr.getMcvhOrderdate())
			.deliveryDate(matcertlnvendorhdr.getMcvhDeliverydate())
			.deliveryWeeks(matcertlnvendorhdr.getMcvhDeliveryweeks())
			.build());
		}
		
		return WorkCodeDetailRequest.builder()
				.certType(detail.getMatcertlnworkcodedtlsCK().getMclwMatcerttype())
				.certCode(detail.getMatcertlnworkcodedtlsCK().getMclwMatcertcode())
				.subGroup(detail.getMatcertlnworkcodedtlsCK().getMclwSubgroupcode())
				.groupCode(detail.getMatcertlnworkcodedtlsCK().getMclwGroupcode())
				.amount(detail.getMclwAmount())
				.commiteeList(commiteeList)
				.vendorList(venderList)
				.build();
	}

	private NarrationAreaRequest fetchMatcertlnhdr(String tenderCode, String logicNoteNum) {
		Matcertlnhdr matcertlnhdr = matcertlnhdrRepository.findByMatcertlnhdrCK_MclhTendernumAndMatcertlnhdrCK_MclhLogicnotenum(tenderCode, logicNoteNum);
		return NarrationAreaRequest.builder()
				.shortSubTask(matcertlnhdr.getMclhPackagename())
				.location(matcertlnhdr.getMclhLocation())
				.importUnder(matcertlnhdr.getMclhImportunder())
				.revision(matcertlnhdr.getMclhRevisionno())
				.packageName(matcertlnhdr.getMclhLogicnotedesc())
				.date(matcertlnhdr.getMclhLogicdate())
				.scopeOfWork(matcertlnhdr.getMclhWorkscope())
				.commercialDescription1(matcertlnhdr.getMclhCommdesc1())
				.commercialDescription2(matcertlnhdr.getMclhCommdesc2())
				.finalConsideration(matcertlnhdr.getMclhCommdesc3())
				.technicalDescription(matcertlnhdr.getMclhTechdesc())
				.recommendationWithJustification(matcertlnhdr.getMclhRecommjustification())
				.deliverySchedule(matcertlnhdr.getMclhCommercialterm())
				.warrantyInformation(matcertlnhdr.getMclhWarrentyinfo())
				.resourceAllocation(matcertlnhdr.getMclhResourceallocation())
				.tenderNarrative(matcertlnhdr.getMclhTenderremarks())
				.importantSpecification(matcertlnhdr.getMclhImpspecification())
				.build();
	}

}
