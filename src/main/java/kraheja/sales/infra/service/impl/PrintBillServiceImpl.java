package kraheja.sales.infra.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.Result;
import kraheja.payload.GenericResponse;
import kraheja.sales.entity.Infrbill;
import kraheja.sales.entity.InfrbillCK;
import kraheja.sales.entity.Print;
import kraheja.sales.entity.PrintCK;
import kraheja.sales.infra.bean.response.BillResponse;
import kraheja.sales.infra.service.PrintBillService;
import kraheja.sales.repository.InfrBillRepository;
import kraheja.sales.repository.PrintRepository;
import kraheja.utility.DateUtill;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class PrintBillServiceImpl implements PrintBillService {

	@Autowired
	private PrintRepository printRepository;
	@Autowired
	private InfrBillRepository infrBillRepository;
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private GenericCounterIncrementLogicUtil counterIncrementLogicUtil;

	@Override
	public GenericResponse printBill(BillResponse printRequest, String chargeCode, String billType, double sessionId) {
		String isPrint = "Y";
		String userId = GenericAuditContextHolder.getContext().getUserid();
		String siteName = GenericAuditContextHolder.getContext().getSite();

		String ownerId = printRequest.getOwnerId();
		String bldgCode = printRequest.getBuildingCode();
		String wing = printRequest.getOwnerId().substring(4, 5);
		LocalDate billToDate = printRequest.getBillToDate();
		String flatNum = "";
		if (printRequest.getOwnerId().length() == 10)
			flatNum = printRequest.getOwnerId().substring(5, 10);
		else
			flatNum = printRequest.getOwnerId().substring(5, 11);

		this.saveIntoPrint(printRequest, chargeCode, billType, sessionId, wing, flatNum);
		//added delete api
//		this.deleteBill(sessionId, printRequest.getBillNumber());
		/**
		 * CHECK IF BILL ALREADY PERSIST IN INFRABILL, IF ALREADY AVAILABLE THEN ONLY
		 * UPDATE THAT OTHEROWISE INSERT NEW BILL INTO INFRABILL.
		 */
		Infrbill infrbill = infrBillRepository.findBillNumberByOwnerIdAndMonth(ownerId, billToDate, chargeCode, billType);
		log.debug("infrbill obtaint : {}", infrbill);

		if (Objects.nonNull(infrbill)) {
			infrbill.setInfrInterest(printRequest.getInterest());
			infrbill.setInfrIntarrears(printRequest.getInterestArrears());
			infrbill.setInfrBillamt(printRequest.getBillAmount());
			infrbill.setInfrUserid(userId);
			infrbill.setInfrToday(LocalDateTime.now());
			Infrbill updateInfrBill = infrBillRepository.save(infrbill);
			log.debug("updateInfrBill obtaint: {}", updateInfrBill);
			return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Bill has been saved Successfully").build();
		} else {
			String bldgCity = buildingRepository.findBldgCityByBuildingCK_BldgCode(bldgCode);
			log.debug("bldgCity obtaint: {}", bldgCity);

			if (bldgCity.equals("") || bldgCity.isEmpty()) {
				bldgCity = "MUM";
			}
			String bldgCompany = buildingRepository.findBldgCompanyByBldgCode(bldgCode);
			log.debug("bldgCompany obtaint: {}", bldgCompany);

			String financialYear = financialYearGetter(printRequest.getBillDate());
			String likeChargeCode = "%" + chargeCode + "%";
			String locPartyInvoiceNo = counterIncrementLogicUtil.generateInvoiceNo(bldgCompany, likeChargeCode , "TX", siteName, financialYear);
			log.debug("generateLocPartyInvoiceNo: {}", locPartyInvoiceNo);

			InfrbillCK infrbillCK = InfrbillCK.builder().infrBillnum(printRequest.getBillNumber())
					.infrOwnerId(printRequest.getOwnerId()).infrMonth(printRequest.getMonth()).build();

			Infrbill infrbill1 = Infrbill.builder().infrbillCK(infrbillCK).infrAdmincharges(printRequest.getAdmin())
					.infrAmtos(printRequest.getBillAmount()).infrArrears(printRequest.getBillArrears())
					.infrBillamt(printRequest.getBillAmount()).infrBilldate(printRequest.getBillDate())
					.infrBillprinton(LocalDate.now()).infrBillprintyn(isPrint).infrBillrevnum(0).infrBilltype(billType)
					.infrBldgcode(printRequest.getBuildingCode()).infrCgst(printRequest.getCgst())
					.infrCgstperc(printRequest.getCgstPerc()).infrChargecode(chargeCode).infrFlatnum(flatNum)
					.infrIgst(printRequest.getIgst())
					.infrIgstperc(printRequest.getIgstPerc()).infrIntarrears(printRequest.getInterestArrears())
					.infrInterest(printRequest.getInterest()).infrInvoiceno(locPartyInvoiceNo).infrReminders(0)
					.infrCancelledyn("N").infrOrigsite(siteName).infrServtax(0.00).infrSwachhcess(0.00).infrKrishicess(0.00)
					.infrIntfrom(printRequest.getBillFromDate()).infrAmtpaid(0.0)
					.infrRate(printRequest.getInfrRate()).infrSgst(printRequest.getSgst())
					.infrSgstperc(printRequest.getSgstPerc()).infrSite(siteName).infrUserid(userId)
					.infrToday(LocalDateTime.now()).infrWing(wing)
					.infrFromdate(printRequest.getBillFromDate()).infrTodate(printRequest.getBillToDate())
					.infrGstyn("Y").build();

			log.debug("infrbill before persist into database: {}", infrbill1);
			Infrbill save = infrBillRepository.save(infrbill1);
			
			printRequest.setInvoiceNumber(locPartyInvoiceNo);
			this.saveIntoPrint(printRequest, chargeCode, billType, sessionId, wing, flatNum);
			
			if (Objects.nonNull(save)) {
				return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Bill has been saved Successfully").build();
			}
			
		}
		return GenericResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED).message("Bill failed to saved").build();
	}

	@Override
	public GenericResponse deleteBill(double sessionId, String billNum) {
		printRepository.removeByprintCKSaogrpSessidAndPrintCKSaogrpBillnum(sessionId, billNum);

			return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Bill has been deleted Successfully").build();
	}

	private String financialYearGetter(LocalDate invoiceDate) {
		int month = invoiceDate.getMonthValue();
		int year = invoiceDate.getYear();
		if (month < 4) {
			year = year - 1;
		}
		return String.valueOf(year);
	}

	private GenericResponse saveIntoPrint(BillResponse printRequest, String chargeCode, String billType, double sessionId, String wing, String flatNumber) {

		Print printExist = printRepository.findByprintCKSaogrpSessidAndPrintCKSaogrpBillnum(sessionId, printRequest.getBillNumber());
		
		if (Objects.isNull(printExist)) {
			PrintCK printCK = PrintCK.builder().saogrpBillnum(printRequest.getBillNumber())
					.saogrpInvoiceno(printRequest.getInvoiceNumber()).saogrpSessid(sessionId).build();

			Print print = Print.builder().printCK(printCK)
					.saogrpOwnerid(printRequest.getOwnerId()).saogrpBldgcode(printRequest.getBuildingCode())
					.saogrpWing(wing).saogrpFlatnum(flatNumber)
					.saogrpBillmonth(DateUtill.dateFormatterLocalDateToYYYYMM(printRequest.getBillDate()))
					.saogrpBilldate(printRequest.getBillDate()).saogrpBillamt(printRequest.getBillAmount())
					.saogrpBillarrears(printRequest.getBillArrears()).saogrpInterest(printRequest.getInterest())
					.saogrpIntarrears(printRequest.getInterestArrears()).saogrpBillfrom(printRequest.getBillDate())
					.saogrpBillto(printRequest.getBillToDate()).saogrpOutrate(printRequest.getInfrRate())
					.saogrpAdmincharges(printRequest.getAdmin()).saogrpCgst(printRequest.getCgst())
					.saogrpSgst(printRequest.getSgst()).saogrpIgst(printRequest.getIgst())
					.saogrpCgstperc(printRequest.getCgstPerc()).saogrpSgstperc(printRequest.getSgstPerc())
					.saogrpIgstperc(printRequest.getIgstPerc()).saogrpServicetax(0.00)
					.saogrpBillmode(printRequest.getBillMode()).build();
			printRepository.save(print);
			return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Bill print has been saved Successfully").build();
		}
		String existingInvoiceno = printExist.getPrintCK().getSaogrpInvoiceno();
		if (StringUtils.isEmpty(existingInvoiceno)) {
			String invoice = printRequest.getInvoiceNumber();
			printRepository.updateInvoiceNumber(invoice, printRequest.getBillNumber(), sessionId);
		}
		printRepository.deleteprint(sessionId, printRequest.getBillNumber());
		return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message("Bill print has been saved Successfully").build();
	}

	@Override
	public GenericResponse viewBill(BillResponse printRequest, String chargeCode, String billType, double sessionId) {
		String wing = printRequest.getOwnerId().substring(4, 5);
		String flatNum = "";
		if (printRequest.getOwnerId().length() == 10)
			flatNum = printRequest.getOwnerId().substring(5, 10);
		else
			flatNum = printRequest.getOwnerId().substring(5, 11);
		
		GenericResponse response = this.saveIntoPrint(printRequest, chargeCode, billType, sessionId, wing, flatNum);
		return response;
	}
}
