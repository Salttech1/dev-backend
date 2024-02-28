package kraheja.enggsys.management.dataentry.logicnote.service.impl;

import org.springframework.stereotype.Service;

import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.enggsys.entity.Matcertlnworkcodedtls;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.NarrationAreaRequest;
import kraheja.enggsys.management.dataentry.logicnote.payload.request.WorkCodeDetailRequest;
import kraheja.enggsys.management.dataentry.logicnote.payload.response.LogicNoteResponse;
import kraheja.enggsys.management.dataentry.logicnote.service.LogicNoteService;
import kraheja.enggsys.repository.MatcertlntendercommitteeRepository;
import kraheja.enggsys.repository.MatcertlnworkcodedtlsRepository;
import kraheja.purch.entity.Matcertlnhdr;
import kraheja.purch.repository.MatcertlnhdrRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class LogicNoteServiceImpl implements LogicNoteService {

	private final MatcertlnhdrRepository matcertlnhdrRepository;
	private final MatcertlnworkcodedtlsRepository matcertlnworkcodedtlsRepository;
	private final MatcertlntendercommitteeRepository matcertlntendercommitteeRepository;
	
	
	public LogicNoteServiceImpl(MatcertlnhdrRepository matcertlnhdrRepository, MatcertlnworkcodedtlsRepository matcertlnworkcodedtlsRepository, MatcertlntendercommitteeRepository matcertlntendercommitteeRepository) {
		this.matcertlnhdrRepository = matcertlnhdrRepository;
		this.matcertlnworkcodedtlsRepository = matcertlnworkcodedtlsRepository;
		this.matcertlntendercommitteeRepository = matcertlntendercommitteeRepository;
	}

	@Override
	public LogicNoteResponse retriveLogicNote(String projectCode, String logicNoteNum, String tenderCode) {
		NarrationAreaRequest headerRequest = this.fetchMatcertlnhdr(tenderCode, logicNoteNum);
		WorkCodeDetailRequest detailRequest = this.fetchDetail(logicNoteNum);
		return LogicNoteResponse.builder().logicNoteHeaderRequest(headerRequest).detailRequest(detailRequest).result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message(ApiResponseMessage.SUCCESS).build();
	}

	
	
	
	private WorkCodeDetailRequest fetchDetail(String logicNoteNum) {
		Matcertlnworkcodedtls detail = matcertlnworkcodedtlsRepository.findByMatcertlnworkcodedtlsCKMclwLogicnotenum(logicNoteNum);
	
		return WorkCodeDetailRequest.builder()
				.certType(detail.getMatcertlnworkcodedtlsCK().getMclwMatcerttype())
				.certCode(detail.getMatcertlnworkcodedtlsCK().getMclwMatcertcode())
				.subGroup(detail.getMatcertlnworkcodedtlsCK().getMclwSubgroupcode())
				.groupCode(detail.getMatcertlnworkcodedtlsCK().getMclwGroupcode())
				.amount(detail.getMclwAmount())
				.build();
	}

	private NarrationAreaRequest fetchMatcertlnhdr(String tenderCode, String logicNoteNum) {
		Matcertlnhdr matcertlnhdr = matcertlnhdrRepository.findByMatcertlnhdrCK_MclhTendernumAndMatcertlnhdrCK_MclhLogicnotenum(tenderCode, logicNoteNum);
		return NarrationAreaRequest.builder()
				.tranType("E")
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
