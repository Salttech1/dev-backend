package kraheja.enggsys.lcsystem.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.enggsys.entity.Contract;
import kraheja.enggsys.entity.Lcauthboe;
import kraheja.enggsys.entity.LcauthboeCK;
import kraheja.enggsys.entity.Lccert;
import kraheja.enggsys.entity.LccertCK;
import kraheja.enggsys.lcsystem.payload.request.LcCertificateRequest;
import kraheja.enggsys.lcsystem.payload.request.LcDetails;
import kraheja.enggsys.lcsystem.payload.response.ContractResponse;
import kraheja.enggsys.lcsystem.payload.response.LcCertificateResponse;
import kraheja.enggsys.lcsystem.service.LcCertificateService;
import kraheja.enggsys.repository.ContractRepository;
import kraheja.enggsys.repository.LcauthboeRepository;
import kraheja.enggsys.repository.LccertRepository;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class LcCertificateServiceImpl implements LcCertificateService {
	private final LccertRepository lccertRepository;
	private final LcauthboeRepository lcauthboeRepository;
	private final ContractRepository contractRepository;

	public LcCertificateServiceImpl(LccertRepository lccertRepository, LcauthboeRepository lcauthboeRepository,
			ContractRepository contractRepository) {
		this.lccertRepository = lccertRepository;
		this.lcauthboeRepository = lcauthboeRepository;
		this.contractRepository = contractRepository;
	}

	@Override
	public ContractResponse getContract(String recId, String certType, String lcerCertnum) {
		Tuple tuple = lccertRepository.fetchHeaderDetailsForLCCert(recId);
		String pCertNum = (tuple.get(0, String.class) != null) ? tuple.get(0, String.class) : null;
		String pCertType = (tuple.get(1, String.class) != null) ? tuple.get(1, String.class) : null;
		Integer pCertRunSer = (tuple.get(2, BigDecimal.class) != null) ? tuple.get(2, BigDecimal.class).intValue()
				: null;
		LocalDate pCertDate = (tuple.get(3, Timestamp.class) != null)
				? tuple.get(3, Timestamp.class).toLocalDateTime().toLocalDate()
				: null;
		LocalDate fromDate = (tuple.get(4, Timestamp.class) != null)
				? tuple.get(4, Timestamp.class).toLocalDateTime().toLocalDate()
				: null;
		LocalDate toDate = (tuple.get(5, Timestamp.class) != null)
				? tuple.get(5, Timestamp.class).toLocalDateTime().toLocalDate()
				: null;
		Double pCertAmt = (tuple.get(6, BigDecimal.class) != null) ? tuple.get(6, BigDecimal.class).doubleValue()
				: 0.00;
		Double totalAmt = (tuple.get(7, BigDecimal.class) != null) ? tuple.get(7, BigDecimal.class).doubleValue()
				: 0.00;
		Double totTwoptc = (tuple.get(8, BigDecimal.class) != null) ? tuple.get(8, BigDecimal.class).doubleValue()
				: 0.00;

		Contract contract = contractRepository.findByContractCK_ConttContract(recId);
//		log.debug("contract : {}", contract);
		ContractResponse contractResponse = ContractResponse.builder().prvCertNum(pCertNum).prvCertType(pCertType)
				.prvRunSer(pCertRunSer).prvCertDate(pCertDate).proprietor(contract.getConttProprietor())
				.company(contract.getConttCoy()).project(contract.getConttProject())
				.proprty(contract.getConttProperty()).building(contract.getConttBldgcode())
				.workCode(contract.getConttWorkcode()).partyCode(contract.getConttPartycode())
				.partyType(contract.getConttPartytype()).prvCertAmt(pCertAmt).totalAmtCertified(totalAmt)
				.prvTotTwoptc(totTwoptc).durationFrom(fromDate).durationUpto(toDate).build();
//		log.debug("contractResponse obtaint: {}", contractResponse);

		return contractResponse;
	}

	@Override
	public LcCertificateRequest retrieveCertificate(String recId, String certType, String lcerCertnum) {
		Lccert lccert = lccertRepository.findLccertByLccertCKLcerCertnum(lcerCertnum);
		log.debug("LC Certificates: {}", lccert);

		if (Objects.isNull(lccert)) {
			return LcCertificateRequest.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED)
					.message(ApiResponseMessage.RECORD_NOT_FOUNDED).build();
		} else {
			recId = lccert.getLcerContract().trim();
			certType = lccert.getLcerCerttype().trim();
			ContractResponse contractResponse = this.getContract(recId, certType, lcerCertnum);
			log.debug("contractResponse: {}", contractResponse);

			List<Lcauthboe> lcauthboe = lcauthboeRepository.findLcauthboeByLcauthboeCKLcabAuthnum(lcerCertnum);
			log.debug("LC Auth Boe: {}", lcauthboe);

			List<LcDetails> lcAuthboeList = new ArrayList<>();
			for (Lcauthboe authboe : lcauthboe) {
				lcAuthboeList.add(LcDetails.builder().srlNo(authboe.getLcauthboeCK().getLcabSrno())
						.epcgNo(authboe.getLcabEpcgno()).dutyFreeNo(authboe.getLcabDutyfreeno())
						.lcNo(authboe.getLcabLcno()).inspectionDate(authboe.getLcabInspectiondate())
						.shipDate(authboe.getLcabLastshipmentdate()).docsRecdDate(authboe.getLcabShipdocrecddate())
						.boeNo(authboe.getLcabBoeno()).boeDate(authboe.getLcabBoedate()).build());
			}
			return LcCertificateRequest.builder().result(Result.SUCCESS)
					.responseCode(ApiResponseCode.SUCCESS)
					.message(ApiResponseMessage.DATA_FETCH_SUCCESSFULLY)
					.tranType("E")
					.preparedBy(lccert.getLcerOriginator())
					.certificateDate(lccert.getLcerCertdate())
					.noOfDays(lccert.getLcerNoofdays())
					.durationFrom(lccert.getLcerDurfrom())
					.durationUpto(lccert.getLcerDurto())
					.payMode(lccert.getLcerPaymode())
					.quantity(lccert.getLcerQty())
					.uom(lccert.getLcerUom())
					.currency(lccert.getLcerCurrency())
					.amount(lccert.getLcerCertamount())
					.bankCharges((lccert.getLcerBankcharges() != null) ? lccert.getLcerBankcharges() : null)
					.payAmount(lccert.getLcerPayamount())
					.documentNo(lccert.getLcerDocumentno())
					.documentDate(lccert.getLcerDocumentdate())
					.masterCertificateNo(lccert.getLcerMastercertno())
					.masterCertificateYN(lccert.getLcerMasterlcyn())
					.category(lccert.getLcerCategory())
					.lcNo(lccert.getLcerLcno())
					.fileNo(lccert.getLcerFileno())
					.remarks(lccert.getLcerRemarks())
					.purpose(lccert.getLcerPurpose())
					.revNum(lccert.getLcerCertrevnum())
					.lcDetailsList(lcAuthboeList)
					.build();
		}
	}

	@Override
	public GenericResponse makeCertificate(LcCertificateRequest request, String recId, String certType,
			String lcerCertnum) {
		log.debug("lc request obtaint in lc certificate service implementation: {} recId: {} certType: {}", request,
				recId, certType, lcerCertnum);

		try {
			Integer runser = 0;
			String siteName = GenericAuditContextHolder.getContext().getSite();
			String userId = GenericAuditContextHolder.getContext().getUserid();

			ContractResponse contractResponse = this.getContract(recId, certType, lcerCertnum);
			log.debug("contractResponse: {}", contractResponse);

			/*
			 * The tranType is decidede it will update or create entry. it tranType will
			 * added after performed an action on the button.
			 */
			if (request.getTranType().equals("N")) {
				lcerCertnum = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "LCCER");
				log.debug("created new certificate number: {}", lcerCertnum);
				
				runser = contractResponse.getPrvRunSer() + 1;
				
			} else {
				Lccert lccert = lccertRepository.findLccertByLccertCKLcerCertnum(lcerCertnum);
				log.debug("LC Certificates: {}", lccert);

				if (Objects.nonNull(lccert)) {
					request.setRevNum(lccert.getLcerCertrevnum() + 1);
					
					Double lastTotalAmt = contractResponse.getTotalAmtCertified() - lccert.getLcerCertamount();
					contractResponse.setTotalAmtCertified(lastTotalAmt);
					contractResponse.setPrvCertNum(lccert.getLcerPrv_Certnum());
					contractResponse.setPrvCertDate(lccert.getLcerPrv_Certdate());
					contractResponse.setDurationFrom(lccert.getLcerPrv_Durfrom());
					contractResponse.setDurationUpto(lccert.getLcerPrv_Durto());
					runser = lccert.getLcerRunser();
					contractResponse.setPrvRunSer(lccert.getLcerPrv_Certrunser());
					contractResponse.setPrvCertAmt(lccert.getLcerPrv_Certamt());

					/**
					 * delete this details from LCAuthBOE table.
					 */
					lcauthboeRepository.deleteLcauthboeByLcauthboeCKLcabAuthnum(lcerCertnum);
				}
			}

			List<LcDetails> lcDetailsList = request.getLcDetailsList();
			log.debug("lcDetailsList : {}", lcDetailsList);
			
			this.saveIntoLcauthboe(lcDetailsList, contractResponse, lcerCertnum, userId, siteName,
					request.getDocumentNo(), request.getDocumentDate());
			this.saveIntoLcCert(request, contractResponse, recId, certType, lcerCertnum, userId, siteName, runser, lcDetailsList.get(lcDetailsList.size()-1).getLcNo());

		} catch (Exception e) {
			log.debug("exception occored into: {}", e.getMessage());
			return LcCertificateResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED)
					.message(e.getMessage()).build();
		}
		
		if (request.getTranType().equals("E")) {
			return LcCertificateResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
					.lccertNumber(lcerCertnum).message(ApiResponseMessage.LC_CERTIFICATE_SUCCESSFULLY_UPDATED).build();
		}
		return LcCertificateResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
				.lccertNumber(lcerCertnum).message(ApiResponseMessage.LC_CERTIFICATE_SUCCESSFULLY_SAVED).build();
	}

	private void saveIntoLcauthboe(List<LcDetails> lcDetailsList, ContractResponse contractResponse, String lcerCertnum,
			String userId, String siteName, String docNum, LocalDate docDate) {
		if (Objects.nonNull(lcDetailsList)) {
			int serialNo = 1;
			for (LcDetails lcDetails : lcDetailsList) {
				LcauthboeCK lcauthboeCK = LcauthboeCK.builder()
						.lcabSrno(serialNo)
						.lcabAuthnum(lcerCertnum)
						.build();
				log.debug("lcauthboeCK obtaint: {}", lcauthboeCK);

				if (lcDetails.getDutyFreeNo() != null) {
					
				}
				
				Lcauthboe lcauthboe = Lcauthboe.builder()
						.lcauthboeCK(lcauthboeCK)
						.lcabBldgcode(contractResponse.getBuilding())
						.lcabProject(contractResponse.getProject())
						.lcabCoy(contractResponse.getCompany())
						.lcabEpcgno(lcDetails.getDutyFreeNo() != null ? "DUTYFREE" : lcDetails.getEpcgNo())
//						.lcabEpcgno("DUTYFREE")
						.lcabDutyfreeno(lcDetails.getDutyFreeNo())
						.lcabLcno(lcDetails.getLcNo())
						.lcabInspectiondate(lcDetails.getInspectionDate())
						.lcabLastshipmentdate(lcDetails.getShipDate())
						.lcabShipdocrecddate(lcDetails.getDocsRecdDate())
						.lcabBoeno(lcDetails.getBoeNo())
						.lcabBoedate(lcDetails.getBoeDate())
						.lcabPendingitems(lcDetails.getPendingItems() != null ? lcDetails.getPendingItems() : 0)
						.lcabToday(LocalDateTime.now()).lcabDocumentno(docNum)
						.lcabDocumentdate(docDate)
						.lcabUserid(userId).lcabSite(siteName)
						.lcabOrigsite(siteName)
						.build();

				log.debug("Lcauthboe entity ready to save: {}", lcauthboe);
				lcauthboeRepository.save(lcauthboe);

				serialNo++;
			}
		}
	}

	private void saveIntoLcCert(LcCertificateRequest request, ContractResponse contractResponse, String recId,
			String certType, String lcerCertnum, String userId, String siteName, Integer runser, String lcNo) {
		LccertCK lccertCK = LccertCK.builder()
				.lcerCertnum(lcerCertnum)
				.build();
		Lccert lccert = Lccert.builder()
				.lccertCK(lccertCK)
				.lcerCertdate(request.getCertificateDate())
				.lcerCertrevnum(request.getRevNum())
				.lcerRunser(runser)
				.lcerContract(recId)
				.lcerCerttype(certType)
				.lcerOriginator(request.getPreparedBy())
				.lcerPaymode(request.getPayMode())
//				.lcerPrinted(null)
//				.lcerPrintedon(null)
//				.lcerPassedon(null)
				.lcerCertstatus("1")
				.lcerProp(contractResponse.getProprietor())
				.lcerCoy(contractResponse.getCompany()) 
				.lcerPaycoy(contractResponse.getCompany())
				.lcerProject(contractResponse.getProject())
				.lcerProperty(contractResponse.getProprty())
				.lcerBldgcode(contractResponse.getBuilding())
				.lcerWorkcode(contractResponse.getWorkCode())
				.lcerPartytype(contractResponse.getPartyType())
				.lcerPartycode(contractResponse.getPartyCode())
				.lcerDurfrom(request.getDurationFrom())
				.lcerDurto(request.getDurationUpto())
				.lcerNoofdays(request.getNoOfDays())
				.lcerCurrency(request.getCurrency())
				.lcerCertamount(request.getAmount())
				.lcerBankcharges(request.getBankCharges())
				.lcerPayamount(request.getPayAmount())
				.lcerDocumentno(request.getDocumentNo())
				.lcerDocumentdate(request.getDocumentDate())
//				.lcerPayref(null)
//				.lcerPaydate(null)
//				.lcerTranser(null)
//				.lcerEnggcertno(null)
				.lcerRemarks(request.getRemarks())
				.lcerPrv_Certnum(contractResponse.getPrvCertNum())
				.lcerPrv_Certdate(contractResponse.getPrvCertDate())
				.lcerPrv_Certrunser(contractResponse.getPrvRunSer())
				.lcerPrv_Certtype(contractResponse.getPrvCertType())
				.lcerPrv_Certamt(contractResponse.getPrvCertAmt())
				.lcerPrv_Durfrom(contractResponse.getDurationFrom())
				.lcerPrv_Durto(contractResponse.getDurationUpto())
				.lcerTot_Payment(contractResponse.getTotalAmtCertified() + request.getAmount())
				.lcerTot_Twoptc(contractResponse.getPrvTotTwoptc())
				.lcerOrigsite(siteName).lcerSite(siteName)
				.lcerUserid(userId).lcerToday(LocalDateTime.now())
				.lcerQty(request.getQuantity())
				.lcerUom(request.getUom())
				.lcerCategory(request.getCategory())
				.lcerFileno(request.getFileNo())
//				.lcerEpcg_Dutyfree(null)
//				.lcerLicnum(null)
//				.lcerLicdate(null)
				.lcerLcno(lcNo)
				.lcerMastercertno(request.getMasterCertificateNo())
				.lcerMasterlcyn(request.getMasterCertificateYN())
				.lcerPurpose(request.getPurpose())
//				.lcerEpcgno(null)
//				.lcerEpcgdate(null)
//				.lcerInspectiondate(null)
//				.lcerLastshipmentdate(null)
//				.lcerLastshipmentdate(null)
//				.lcerLcopendate(null)
//				.lcerExpirydate(null)
//				.lcerDutyfreedate(null)
//				.lcerDutyfreeno(null)
				.build();

		log.debug("Lccert entity ready to save: {}", lccert);
		lccertRepository.save(lccert);
	}

}
