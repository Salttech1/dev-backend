package kraheja.sales.infra.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.HsnsacmasterRepository;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.exception.InternalServerError;
import kraheja.sales.bean.entitiesresponse.DBResponseForNewInfrBill;
import kraheja.sales.bean.entitiesresponse.InfrBillDBResponse;
import kraheja.sales.bean.response.GetNextBillDateResponse;
import kraheja.sales.entity.Infrbill;
import kraheja.sales.infra.bean.request.InfraAuxiBillRequest;
import kraheja.sales.infra.bean.response.BillResponse;
import kraheja.sales.infra.service.BillGenerationService;
import kraheja.sales.repository.FlatownerRepository;
import kraheja.sales.repository.FlatsOutrateRepository;
import kraheja.sales.repository.InfrBillRepository;
import kraheja.sales.repository.OutinfraRepository;
import kraheja.sales.repository.OutrateRepository;
import kraheja.utility.DateUtill;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * this service implementation for generate a bill.
 * </p>
 * 
 * @author sazzad.alom
 * @since 20-OCTOBER-2023
 * @version 1.0.0
 */

@Log4j2
@Service
@Transactional
public class BillGenerationServiceImpl implements BillGenerationService {

	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private HsnsacmasterRepository hsnsacmasterRepository;
	@Autowired
	private EntityRepository entityRepository;
	@Autowired
	private FlatsOutrateRepository flatsOutrateRepository;
	@Autowired
	private FlatownerRepository flatownerRepository;

	@Autowired
	private OutrateRepository outrateRepository;

	@Autowired
	private InfrBillRepository infrBillRepository;

	@Autowired
	private OutinfraRepository outinfraRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public BillResponse getBillDetail(InfraAuxiBillRequest billRequest) {
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		log.debug("session id obtaint: {}", sessionId);

		String billDate = billRequest.getBillDate();

		String yearMonth = DateUtill.startYearMonthFromInput(billDate);
		log.debug("yearMonth obtaint: {}", yearMonth);
		
		String ownerIdTo = billRequest.getOwnerIdTo().trim();

		// Accept the value like 202311
		String quaterEndYearMonth = this.getQuaterEndYearMonth(ownerIdTo, yearMonth, billRequest.getChargeCode(), billRequest.getBillType(), billDate);
		log.debug("quaterEndYearMonth: {}", quaterEndYearMonth);

		int year = Integer.parseInt(String.valueOf(quaterEndYearMonth).substring(0, 4));
		int month = Integer.parseInt(quaterEndYearMonth.substring(4, 6));
		if (month == 0) {
			month = 12;
			year = year - 1;
		}

		String month2D = String.format("%02d", month);

		int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
		String lastDateOfQuater = lastDayOfMonth + "/" + month2D + "/" + year;
		// 31/12/2023
		LocalDate billToDate = DateUtill.convertStringToDateFormat(lastDateOfQuater);

		String flatOwnerId1 = flatsOutrateRepository.findDistinctFlatOwnerIds(yearMonth, quaterEndYearMonth,
				billRequest.getOwnerIdFrom(), billRequest.getOwnerIdTo(), billRequest.getBillType());
		if (StringUtils.isEmpty(flatOwnerId1)) {
			flatOwnerId1 = billRequest.getOwnerIdTo();
		}
		log.debug("flatOwnerIdList obtaint: {}", flatOwnerId1);

		if (Objects.nonNull(flatOwnerId1)) {
			String flatOwnerId = flatOwnerId1.trim();
			String bldgCode = flatOwnerId.substring(0, 4);

			String billMode = flatownerRepository.getBillMode(flatOwnerId);
			log.debug("billMode obtaint: {}", billMode);
			String bldg = buildingRepository.findBldgCompanyByBldgCode(bldgCode);
			String companyName = companyRepository.findByCompanyCKCoyCode(bldg);
			
			/**
			 * IF ALREADY HAVE LAST BILL IN THE CASE OF FIRST BILL.
			 * */
			if (billRequest.getBillType().equals("F")) {
				String lastBillNumber = infrBillRepository.fetchLastBillNumber(flatOwnerId, billRequest.getChargeCode(), billRequest.getBillType());
				if (StringUtils.isNotEmpty(lastBillNumber)) {
					Timestamp sqlTimestamp = infrBillRepository.fetchLastBillDate(flatOwnerId, billRequest.getChargeCode(), billRequest.getBillType(), lastBillNumber);
					LocalDate lastBillToDate = sqlTimestamp.toLocalDateTime().toLocalDate();
					InfrBillDBResponse infrBillDBResponse = this.infrBillRepository.fetchDetail(lastBillToDate, flatOwnerId,billRequest.getChargeCode(), billRequest.getBillType());
					log.debug("infrBillDBResponseList obtaint : {}", infrBillDBResponse);
					
					return this.alreadyHaveBill(infrBillDBResponse, sessionId, companyName, billMode);
					}
			}
			
			
			// CHECK IF BILL ALREADY THERE
			InfrBillDBResponse infrBillDBResponse = this.infrBillRepository.fetchDetail(billToDate, flatOwnerId,billRequest.getChargeCode(), billRequest.getBillType());
			log.debug("infrBillDBResponseList obtaint : {}", infrBillDBResponse);
			
			if (Objects.nonNull(infrBillDBResponse)) {
					return this.alreadyHaveBill(infrBillDBResponse, sessionId, companyName, billMode);
			} else {

				billRequest.setOwnerIdFrom(flatOwnerId);
				return this.newBillCreation(billRequest, sessionId, companyName, yearMonth, billMode,
						quaterEndYearMonth);
			}
		}
		return BillResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED)
				.message(ApiResponseMessage.FLAT_OWNER_ID_NOT_AVAILABLE).build();
	}

	private BillResponse newBillCreation(InfraAuxiBillRequest request, String sessionId, String companyName,String yearMonth, String billMode, String quaterEndYearMonth) {
		String bldgCode = request.getOwnerIdFrom().substring(0, 4);
		String wing = request.getOwnerIdFrom().substring(4, 5);
		String flatNum = "";
		if (request.getOwnerIdFrom().length() == 10)
			flatNum = request.getOwnerIdFrom().substring(5, 10);
		else
		 flatNum = request.getOwnerIdFrom().substring(5, 11);
		
		String billDesc = "";

		double infrRate = 0.0, adminRate = 0.0;

		Map<String, Double> gstRate = this.getGstRate(request);
		log.debug("gstRate obtaint: {}", gstRate);

		int startQuarterMonth = Integer.parseInt(yearMonth);
		String ogStartMonth  = null , getOGEndMonth = null;
		if (wing.equals(" ")) {
			ogStartMonth = outrateRepository.fetchStartDate(bldgCode,flatNum, request.getBillType()).get(0);
			getOGEndMonth = outrateRepository.fetchEndDate(bldgCode, flatNum, request.getBillType()).get(0);
		}else {
			 ogStartMonth = outrateRepository.fetchStartDate(bldgCode, wing, flatNum, request.getBillType()).get(0);
			 getOGEndMonth = outrateRepository.fetchEndDate(bldgCode, wing, flatNum, request.getBillType()).get(0);
		}
		

		if (ogStartMonth == null || getOGEndMonth == null) {
			throw new InternalServerError(ApiResponseMessage.ADMIN_AND_MAINTANACE_RATE_ZERO);
		}

		Map<String, Double> rate = this.getRate(bldgCode, wing, flatNum, request.getBillType(), request.getChargeCode(),ogStartMonth);
		log.debug("infraRate and adminRate obtaint: {}", rate);

		
		Map<String, Integer> gstAmt = new LinkedHashMap<>();
		String day = request.getBillDate().substring(0,2);
		int startYear = Integer.parseInt(String.valueOf(startQuarterMonth).substring(0, 4));
		int startMonth = Integer.parseInt(String.valueOf(startQuarterMonth).substring(4, 6));
		String startMonth2D = String.format("%02d", startMonth);
		String billFromDate = day + "/" + startMonth2D + "/" + startYear;
		LocalDate startQuaterDate = DateUtill.convertStringToDateFormat(billFromDate);
		
		int endYear = Integer.parseInt(String.valueOf(quaterEndYearMonth).substring(0, 4));
		int endMonth = Integer.parseInt(String.valueOf(quaterEndYearMonth).substring(4, 6));
		String endMonth2D = String.format("%02d", endMonth);

		int lastDayOfMonth = YearMonth.of(endYear, endMonth).lengthOfMonth();
		String billToDate = lastDayOfMonth + "/" + endMonth2D + "/" + endYear;
		LocalDate endQuaterDate = DateUtill.convertStringToDateFormat(billToDate);
		
		gstAmt.put("cgstAmount", 0);
		gstAmt.put("sgstAmount", 0);
		gstAmt.put("igstAmount", 0);
		while(endQuaterDate.isEqual(startQuaterDate) || endQuaterDate.isAfter(startQuaterDate)) {
				if (billMode.equals("Q")) {
					billDesc = "Quaterly";
					infrRate = (rate.get("infrRate")) + infrRate;
					adminRate = rate.get("adminRate") + adminRate;
					gstAmt = this.totalGstGetter(request.getBillType(), gstRate, rate.get("infrRate"), rate.get("adminRate"), gstAmt );
				} else {
					billDesc = "Monthly";
					infrRate = (rate.get("infrRate")) + infrRate;
					adminRate = rate.get("adminRate") + adminRate;
					gstAmt = this.totalGstGetter(request.getBillType(), gstRate, rate.get("infrRate"), rate.get("adminRate"), gstAmt );
				}
				
				startQuaterDate = startQuaterDate.plusMonths(1);
		}
		startQuaterDate =  DateUtill.convertStringToDateFormat(billFromDate);
		
			String billNumber = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "INBIL",
					GenericAuditContextHolder.getContext().getSite());
			log.debug("billNumber: {}", billNumber);

			Map<String, Double> bill = this.getBillForOwner(request, bldgCode, wing, flatNum, quaterEndYearMonth);

			return BillResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
					.message(ApiResponseMessage.BILL_CALCULATED_SUCCESSFULLY).billNumber(billNumber)
					.ownerId(request.getOwnerIdFrom()).month(yearMonth)
					.billDate(DateUtill.convertStringToDateFormat(request.getBillDate())).billFromDate(startQuaterDate)
					.billToDate(endQuaterDate).billAmount(infrRate).billArrears(bill.get("calArrears"))
					.interest(bill.get("billInterest")).interestArrears(bill.get("interestArrears")).admin(adminRate).infrRate((rate.get("infrRate")))
					.cgst(gstAmt.get("cgstAmount")).sgst(gstAmt.get("sgstAmount")).igst(gstAmt.get("igstAmount")).cgstPerc(gstRate.get("cgstPer"))
					.sgstPerc(gstRate.get("sgstPer")).igstPerc(gstRate.get("igstPer")).invoiceNumber("").billMode(billDesc)
					.sessionId(sessionId).buildingCode(bldgCode).companyName(companyName).build();
	}

	private BillResponse alreadyHaveBill(InfrBillDBResponse infrBillDBResponse, String sessionId, String companyName, String billMode) {
		String billDesc = "";
		if (billMode.equals("Q"))
			billDesc = "Quaterly";
		else
			billDesc = "Monthly";
		
		return BillResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
				.message(ApiResponseMessage.FETCH_SUCCESSFULLY).billNumber(infrBillDBResponse.getInfrBillnum())
				.ownerId(infrBillDBResponse.getInfrOwnerId()).month(infrBillDBResponse.getInfrMonth())
				.billDate(infrBillDBResponse.getInfrBilldate()).billFromDate(infrBillDBResponse.getInfrFromdate())
				.billToDate(infrBillDBResponse.getInfrTodate()).billAmount(infrBillDBResponse.getInfrAmtos()).infrRate(infrBillDBResponse.getInfrRate())
				.billArrears(infrBillDBResponse.getInfrArrears()).interest(infrBillDBResponse.getInfrInterest())
				.interestArrears(infrBillDBResponse.getInfrIntarrears()).admin(infrBillDBResponse.getInfrAdmincharges())
				.cgst(infrBillDBResponse.getInfrCgst()).sgst(infrBillDBResponse.getInfrSgst())
				.igst(infrBillDBResponse.getInfrIgst()).cgstPerc(infrBillDBResponse.getInfrCgstperc())
				.sgstPerc(infrBillDBResponse.getInfrSgstperc()).igstPerc(infrBillDBResponse.getInfrIgstperc())
				.invoiceNumber(infrBillDBResponse.getInfrInvoiceNo()).sessionId(sessionId)
				.buildingCode(infrBillDBResponse.getInfrBldgCode()).companyName(companyName).irnno(infrBillDBResponse.getInfrIrnNo())
				.billMode(billDesc)
				.build();
	}

	private Map<String, Double> getBillForOwner(InfraAuxiBillRequest request, String bldgCode, String wing,	String flatNum, String quaterEndYearMonth) {
		Double billArrears = 0.0;
		Double oldBalance = 0.0;
		Double intArrears = 0.0;
		Double billInterest = 0.0;
		Double interestArrears = 0.0;
		LocalDate lastBillDate = null;
		String flatOwnerId = request.getOwnerIdFrom();
		LocalDate billDate = DateUtill.convertStringToDateFormat(request.getBillDate());

		String lastBillNumber = infrBillRepository.fetchBillNumber(flatOwnerId, request.getChargeCode(),request.getBillType(), billDate);
		log.debug("lastBillNumber : {}", lastBillNumber);

		// ADDED DEFAULT VALUE 199001
		String getMaxMonth = infrBillRepository.getInfrMonth(flatOwnerId, request.getChargeCode(), lastBillNumber,request.getBillType());
		log.debug("getMaxMonth : {}", getMaxMonth);

		// (String ownerId, String chargeCode, String billNum, String billType);
		Timestamp fetchFromDate1 = infrBillRepository.fetchFromDate(flatOwnerId, request.getChargeCode(),lastBillNumber, request.getBillType());
		
		double intrestRate = 0.00;
		double advanceAmt = 0.00;
		double intAdvanceAmt = 0.00;
		double calculateArrears = 0.0;
		LocalDate convBillDate = null;
		LocalDate fetchFromDate = LocalDate.now();
		List<DBResponseForNewInfrBill> newInfrBill1 = null;
		if (fetchFromDate1 != null) {
			fetchFromDate = fetchFromDate1.toLocalDateTime().toLocalDate();
			log.debug("fromDate : {}", fetchFromDate);

			// ownerId, chargeCode, month, billtype
			 newInfrBill1 = infrBillRepository
					.fetchBillDateAndOldBalanceAndArearsAndInterestAndIntArears(flatOwnerId, request.getChargeCode(),
							DateUtill.dateFormatterLocalDateToYYYYMM(fetchFromDate), request.getBillType());
			log.debug("date and balance and arearsandinterest and intarears obtaint : {}", newInfrBill1);

//			String billYearMonth = DateUtill.dateToyearMonth(lastBillDate);
//			String quaterEndYearMonth = this.getQuaterEndYearMonth(billYearMonth);
			convBillDate = DateUtill.convertStringToDateFormat(request.getBillDate());
				
		}else {
			fetchFromDate = DateUtill.convertStringToDateFormat(request.getBillDate());
		}
		

		Map<String, Double> interestMap = new HashMap<>();
		if (newInfrBill1 != null) {
			for (DBResponseForNewInfrBill newInfrBill : newInfrBill1) {
				billArrears = newInfrBill.getArrears();
				oldBalance = newInfrBill.getBalance();
				intArrears = newInfrBill.getIntarrears();
				billInterest = newInfrBill.getInterest();
				interestArrears = newInfrBill.getIntarrears();
				if (newInfrBill.getBillDate() != null) {
					lastBillDate = newInfrBill.getBillDate();
				} else {
					lastBillDate = DateUtill.convertStringToDateFormat("01/01/1900");
				}
			}
		}else {
			lastBillDate = DateUtill.convertStringToDateFormat(request.getBillDate());
		}
		
	//if Advance is more than oldbalance 
		if (billArrears < 0) {
			if (Math.abs(billArrears) >= oldBalance) {
				billArrears = billArrears + oldBalance;
				oldBalance = 0.0;
			} else {
				oldBalance = oldBalance + billArrears;
				billArrears = 0.0;
			}
		}

		intArrears = intArrears + billInterest;
		billInterest = 0.0;

		// Initialize variables
		calculateArrears = billArrears;
		interestArrears = intArrears;

		if (lastBillDate == null) {
			lastBillDate = LocalDate.now();
		}

		// -------------INTEREST RATES-------------
		if (bldgCode.equals("OM04") || bldgCode.equals("OM21")) {
			intrestRate = 15.00;
		} else {
			intrestRate = entityRepository.fetchIntereRate();
		}
		if (intrestRate == 0) {
			throw new InternalServerError("interest rates zero");
		}

//		Timestamp lastTimeStamp = outinfraRepository.fetchLastReceptDate(flatOwnerId, request.getChargeCode(),request.getBillType());
//		Timestamp sqlTimestamp = lastTimeStamp;
//		LocalDate lastRecDate = LocalDate.now();
//		if (sqlTimestamp != null) {
//			lastRecDate = sqlTimestamp.toLocalDateTime().toLocalDate();
//		}
		// List<Tuple> fetchRecDateAndAmtPaidAndIntPaid(String ownerId, String
		// chargecode,LocalDate lastbillDateFrom, LocalDate lastbillDateTo, String
		// billtype);
		Timestamp sqlTimestamp = null;
		List<Tuple> tupleObj = null;
		LocalDate infRecdate = null;
		if (request.getBillType().equals("F")) {
			// RECEIPT CALCULATION STARTS FROM HERE
			String lastRecNum = outinfraRepository.lastRecNum(flatOwnerId, request.getChargeCode(), request.getBillType());
			if (StringUtils.isNotEmpty(lastRecNum)) {
//				tupleObj = outinfraRepository.fetchRecDateAndAmtPaidAndIntPaid(flatOwnerId, request.getChargeCode(),lastBillDate, DateUtill.convertStringToDateFormat(request.getBillDate()), request.getBillType());
				tupleObj = outinfraRepository.fetchRecDateAndAmtPaidAndIntPaid(lastRecNum);
				infRecdate = lastBillDate;
			}
		}else {
			tupleObj = outinfraRepository.fetchRecDateAndAmtPaidAndIntPaid(flatOwnerId, request.getChargeCode(),lastBillDate, DateUtill.convertStringToDateFormat(request.getBillDate()), request.getBillType());
			infRecdate = lastBillDate;
		}
		
		
		long totalDays = 0l;
		double amtpaid, intPaid, perDayInterest;
		log.debug("tupleObj: {}", tupleObj);

		if(Objects.nonNull(tupleObj)) {
			for (Tuple tuple : tupleObj) {
				if (tuple.get(0) == null) {
					infRecdate = DateUtill.convertStringToDateFormat(quaterEndYearMonth); // actual value is null
				} else {
					sqlTimestamp = tuple.get(0, Timestamp.class);
					infRecdate = sqlTimestamp.toLocalDateTime().toLocalDate();
				}
				totalDays = ChronoUnit.DAYS.between(lastBillDate, infRecdate);

				amtpaid = tuple.get(1, BigDecimal.class).doubleValue();
				intPaid = tuple.get(2, BigDecimal.class).doubleValue();

				// ------------- START CALCULATING INTEREST ON AMTPAID -------------
				if (amtpaid > 0) {
					if (amtpaid >= billArrears) {
						amtpaid = amtpaid - billArrears;
						if (billArrears > 0) {
							perDayInterest = ((billArrears / 100) * intrestRate) / 365;
							billInterest = billInterest + Math.round(perDayInterest * totalDays);
						}
						billArrears = 0.00;
						calculateArrears = 0.00;

						int month = lastBillDate.getMonthValue();
						int year = lastBillDate.getYear();
						int lastDate = DateUtill.getDaysInMonth(year, month);
						String startMonth2D = String.format("%02d", month);
						String lastDateOfBill = lastDate + "/" + startMonth2D + "/" + year;
						
						if (amtpaid >= oldBalance) {
							amtpaid = amtpaid - oldBalance;

							LocalDate convertlastDateOfBill = DateUtill.convertStringToDateFormat(lastDateOfBill);
							log.debug("lastDate : {}", convertlastDateOfBill);

							if (infRecdate.isAfter(convertlastDateOfBill)) {
								perDayInterest = ((oldBalance / 100) * intrestRate) / 365;
								LocalDate billDate1 = DateUtill.convertStringToDateFormat(lastDateOfBill);
								totalDays = ChronoUnit.DAYS.between(billDate1, infRecdate);
								billInterest = billInterest + Math.round(perDayInterest * totalDays);
							}
							oldBalance = 0.00;
							advanceAmt = advanceAmt + amtpaid; // EXCESS RECEIVED AMOUNT
						} else {
							oldBalance = oldBalance - amtpaid;
//							lastDate = DateUtill.getDaysInMonth(convBillDate.getYear(), convBillDate.getMonthValue());
//							startMonth2D = String.format("%02d", month);
//							lastDateOfBill = lastDate + "/" + startMonth2D + "/" + year;
//							LocalDate convertlastDateOfBill = DateUtill.convertStringToDateFormat(lastDateOfBill);
							LocalDate billdate = DateUtill.convertStringToDateFormat(request.getBillDate());
							LocalDate convertlastDateOfBill = billdate.withDayOfMonth(billdate.lengthOfMonth());
							
							if (infRecdate.isAfter(convertlastDateOfBill)) {
								perDayInterest = ((oldBalance / 100) * intrestRate) / 365;
								totalDays = ChronoUnit.DAYS.between(infRecdate,
										DateUtill.convertStringToDateFormat(lastDateOfBill));
								billInterest = billInterest + Math.round(perDayInterest * totalDays);
							}
						}
					} else {
						billArrears = billArrears - amtpaid;
						calculateArrears = billArrears;
						perDayInterest = ((amtpaid / 100) * intrestRate) / 365;
						billInterest = billInterest + Math.round(perDayInterest * totalDays);
						amtpaid = 0.00;
					}
				}
				// ------------- END CALCULATING INTEREST ON AMTPAID -------------

				// ------------- START CALCULATING INTEREST ON INT PAID -------------
				if (intPaid > 0) {
					if (intPaid >= intArrears) {
						intPaid = intPaid - intArrears;
						if (intArrears > 0) {
							totalDays = ChronoUnit.DAYS.between(infRecdate, convBillDate);
							perDayInterest = ((intArrears / 100) * intrestRate) / 365;
							billInterest = billInterest + Math.round(perDayInterest * totalDays);
						}
						intArrears = 0.00;
						interestArrears = intArrears;
						intAdvanceAmt = intAdvanceAmt + intPaid;
					} else {
						totalDays = ChronoUnit.DAYS.between(infRecdate, convBillDate);
						intArrears = intArrears - intPaid;
						interestArrears = intArrears;
						perDayInterest = ((intPaid / 100) * intrestRate) / 365;
						billInterest = billInterest + Math.round(perDayInterest * totalDays);
						intPaid = 0.00;
					}
				}
				// ------------- END CALCULATING INTEREST ON INT PAID -------------
				amtpaid = 0.00;
				intPaid = 0.00;
			}
		}

		
		if (request.getBillType().equals("F")) {
			LocalDate afterEndDate = DateUtill.parseYYYYMM(quaterEndYearMonth);
			totalDays = ChronoUnit.DAYS.between(billDate, afterEndDate);
		}else {
			
			totalDays = ChronoUnit.DAYS.between(lastBillDate, convBillDate);
		}

		if (billArrears > 0) {
			perDayInterest = ((billArrears / 100) * intrestRate) / 365; // 27.215 35.215
			billInterest = billInterest + Math.round(perDayInterest * totalDays);
		}
		if (oldBalance > 0) {
			perDayInterest = ((oldBalance / 100) * intrestRate) / 365;
			billInterest = billInterest + Math.round(perDayInterest * totalDays);
		}
		if (intArrears > 0) {
			perDayInterest = ((intArrears / 100) * intrestRate) / 365;
			billInterest = billInterest + Math.round(perDayInterest * totalDays);
		}
		if (billInterest < 25) {
			billInterest = 0.00;
		}
		if (advanceAmt > 0) {
			billArrears = advanceAmt * -1;
			calculateArrears = billArrears;
			advanceAmt = 0.00;
		} else {
			billArrears = billArrears + oldBalance;
			calculateArrears = billArrears;
			oldBalance = 0.00;
		}
		if (intAdvanceAmt > 0) {
			billInterest = billInterest - intAdvanceAmt;
			intAdvanceAmt = 0.00;
		}
		
		interestMap.put("billInterest", billInterest);
		interestMap.put("calArrears", calculateArrears);
		interestMap.put("interestArrears", interestArrears);

		return interestMap;
	}

	private Map<String, Integer> totalGstGetter(String billType, Map<String, Double> gstRate, Double infraRate,Double adminRate, Map<String, Integer> gstMap) {
		Integer cgstAmount = 0, sgstAmount = 0, igstAmount = 0;

		if (billType.equals("F")) {
			cgstAmount = (int) Math.round((gstRate.get("cgstPer") * adminRate) / 100.0) + cgstAmount;
			sgstAmount = (int) Math.round((gstRate.get("sgstPer") * adminRate) / 100.0) + sgstAmount;
			igstAmount = (int) Math.round((gstRate.get("igstPer") * adminRate) / 100.0) + igstAmount;

			cgstAmount = (int) Math.round((gstRate.get("cgstPer") * infraRate) / 100.0) + cgstAmount;
			sgstAmount = (int) Math.round((gstRate.get("sgstPer") * infraRate) / 100.0) + sgstAmount;
			igstAmount = (int) Math.round((gstRate.get("igstPer") * infraRate) / 100.0) + igstAmount;
		} else {
			cgstAmount = (int) Math.ceil((gstRate.get("cgstPer") * adminRate) / 100.0) + cgstAmount;
			sgstAmount = (int) Math.ceil((gstRate.get("sgstPer") * adminRate) / 100.0) + sgstAmount;
			igstAmount = (int) Math.ceil((gstRate.get("igstPer") * adminRate) / 100.0) + igstAmount;

			cgstAmount = (int) Math.ceil((gstRate.get("cgstPer") * infraRate) / 100.0) + cgstAmount;
			sgstAmount = (int) Math.ceil((gstRate.get("sgstPer") * infraRate) / 100.0) + sgstAmount;
			igstAmount = (int) Math.ceil((gstRate.get("igstPer") * infraRate) / 100.0) + igstAmount;
		}
		
		gstMap.put("cgstAmount", gstMap.get("cgstAmount") + cgstAmount  );
		gstMap.put("sgstAmount", gstMap.get("sgstAmount") + sgstAmount);
		gstMap.put("igstAmount", gstMap.get("igstAmount") + igstAmount);
		return gstMap;
	}

	/**
	 * HERE FETCH MAINTANACE RATE FOR EMPTY WING AND WITH PROPER WING VALUE. WE ARE
	 * TRIM FLAT NUMBER WHERE APPENDED EMPTY STRING AT LAST FOR THAT. HERE RETRIVE
	 * AS STRING INSTATE OF DOUBLE BECAUSE OF NULL VALUE TO DOUBLE CONVERSION THOWS
	 * NULL POINTER EXCEPTION.
	 */
	private Map<String, Double> getRate(String buildingCode, String wing, String flatNum, String billType,
			String chargeCode, String ogStartMonth) {
		log.debug("getRate receipt value bldgCode:{} wing: {} flatNum: {} billType: {} chargeCode: {}", buildingCode,
				wing, flatNum, billType, chargeCode);

		String infrRate = "0", adminRate = "0", tdsRate = "0";
		Map<String, Double> rate = new HashMap<>();
		if (chargeCode.equals("INAP")) {

			if (wing.equals(" ")) {
				infrRate = outrateRepository.fetchInfrRateForInfraWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
				adminRate = outrateRepository.fetchAdminRateForInfraWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
				tdsRate = outrateRepository.fetchTdsRateForInfraWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
			} else {
				infrRate = outrateRepository.fetchInfrRateForInfra(buildingCode, flatNum, wing, ogStartMonth, billType);
				adminRate = outrateRepository.fetchAdminRateForInfra(buildingCode, flatNum, wing, ogStartMonth,
						billType);
				tdsRate = outrateRepository.fetchTdsRateForInfra(buildingCode, flatNum, wing, ogStartMonth, billType);
			}

		} else {
			if (wing.equals(" ")) {
				infrRate = outrateRepository.fetchInfrRateForAuxiWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
				adminRate = outrateRepository.fetchAdminRateForAuxiWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
				tdsRate = outrateRepository.fetchTdsRateForAuxiWithEmptyWing(buildingCode, flatNum, ogStartMonth,
						billType);
			} else {
				infrRate = outrateRepository.fetchInfrRateForAuxi(buildingCode, wing, flatNum, billType, ogStartMonth);
				adminRate = outrateRepository.fetchAdminRateForAuxi(buildingCode, wing, flatNum, billType,
						ogStartMonth);
				tdsRate = outrateRepository.fetchTdsRateForAuxi(buildingCode, wing, flatNum, billType, ogStartMonth);
			}
		}
		if (infrRate == null || !StringUtils.isNoneEmpty(infrRate)) {
			infrRate = "0";
		}
		if (adminRate == null || !StringUtils.isNoneEmpty(adminRate)) {
			adminRate = "0";
		}
		if (tdsRate == null || !StringUtils.isNoneEmpty(tdsRate)) {
			tdsRate = "0";
		}
		rate.put("infrRate", Double.valueOf(infrRate));
		rate.put("adminRate", Double.valueOf(adminRate));
		rate.put("tdsRate", Double.valueOf(tdsRate));
		return rate;
	}

//	private String getOGStartMonth(String bldgCode, String wing, String flatNum, String billType, String date) {
//		return outrateRepository.fetchStartDateBybldgCodeWingFlatBillTypeAndBetweenDate(bldgCode, wing, flatNum, billType,date);
//	}

//	private String getOGEndMonth(String bldgCode, String wing, String flatNum, String billType, String date) {
//		return outrateRepository.fetchEndDateBybldgCodeWingFlatBillTypeAndBetweenDate(bldgCode, wing, flatNum, billType,date);
//	}

	/**
	 * <p>
	 * this method use for get GST Percent state wise.
	 * </p>
	 */
	private Map<String, Double> getGstRate(InfraAuxiBillRequest request) {
		Map<String, Double> gstMap = new HashMap<>();

		String buildingState = buildingRepository
				.findBldgSalesstateByBuildingCK_BldgCode(request.getOwnerIdFrom().substring(0, 4));
		log.debug("building state obtaint: {}", buildingState);

		Integer entityCount = entityRepository.getEntityCount(buildingState);
		log.debug("entityCount obtaint: {}", entityCount);

		Hsnsacmaster gstRate = hsnsacmasterRepository.gstRate("995419",
				DateUtill.convertStringToDateFormat(request.getBillDate()));
		log.debug("gstRate : {}", gstRate);

		gstMap.put("cgstPer", 0.00);
		gstMap.put("sgstPer", 0.00);
		gstMap.put("igstPer", 0.00);
		if (entityCount > 0) {
			gstMap.put("ugstPer", gstRate.getHsmsUgstperc());
		}
		if (buildingState.equals("MAH")) {
			gstMap.put("cgstPer", gstRate.getHsmsCgstperc());
			gstMap.put("sgstPer", gstRate.getHsmsSgstperc());
		} else {
			gstMap.put("igstPer", gstRate.getHsmsIgstperc());
		}
		return gstMap;
	}

	private String getQuaterEndYearMonth(String ownerId, String billDate, String chargeCode, String billType, String reqBillDate) {
		log.debug("billDate receipt the value into getQuaterEndYearMonth: {}", billDate);

		String quaterMonth, quaterYear;
		int intMonth, IntYear;
		// 202311
		intMonth = Integer.parseInt(billDate.substring(4, 6));
		IntYear = Integer.parseInt(billDate.substring(0, 4));

		switch (intMonth) {
		case 1:
		case 2:
		case 3:
			quaterMonth = "03";
			quaterYear = IntYear + quaterMonth;
			break;
		case 4:
		case 5:
		case 6:
			quaterMonth = "06";
			quaterYear = IntYear + quaterMonth;
			break;
		case 7:
		case 8:
		case 9:
			quaterMonth = "09";
			quaterYear = IntYear + quaterMonth;
			break;
		case 10:
		case 11:
		case 12:
			quaterMonth = "12";
			quaterYear = IntYear + quaterMonth;
			break;
		default:
			// Handle invalid month here, if needed
			quaterYear = ""; // or some other default value
			break;
		}
		
		if (billType.equals("F")) {
			Integer monthCount = 0;
			if (chargeCode.equals("AUXI")) {
				monthCount = flatownerRepository.getMonthCountAUXI(ownerId);
				if (monthCount == null) 
					monthCount = 120;
			}
			else {
				monthCount = flatownerRepository.getMonthCountINAP(ownerId);
				if (monthCount == null) 
					monthCount = 60;
			}
			quaterYear = flatownerRepository.getMonthYearAcordingToChargeCode(reqBillDate, monthCount-1);
		}
		return quaterYear;
	}

	/**
	 * The method is created for get next bill date
	 * */
	@Override
	public GetNextBillDateResponse getNextBillDate(InfraAuxiBillRequest billRequest) {
		String ownerId = billRequest.getOwnerIdFrom().trim();
		String bldgCode = ownerId.substring(0, 4);
		String wing = ownerId.substring(4, 5);
		String flatNum = ownerId.substring(5, ownerId.length());
		String chargeCode = billRequest.getChargeCode().trim();
		String billType = billRequest.getBillType().trim();
		LocalDate nextBillDate = LocalDate.now();
		List<Infrbill> infrbill = infrBillRepository.fetchLastBill(ownerId, chargeCode, billType);
		if (infrbill.size() > 0) {
			Infrbill infrbill1 = infrbill.get(0);
			LocalDate infrTodate = infrbill1.getInfrTodate();
			nextBillDate = infrTodate.plusDays(1);
		}else {
			String startMonth  = "";
			if (wing.equals(" ")) {
				startMonth = outrateRepository.fetchStartDate(bldgCode,flatNum, billType).get(0);
			}else {
				startMonth = outrateRepository.fetchStartDate(bldgCode, wing, flatNum, billType).get(0);
			}
			nextBillDate = DateUtill.parseYYYYMM(startMonth);
		}
		return GetNextBillDateResponse.builder()
				.result(Result.SUCCESS)
				.message("Successfully fetch next bill date.")
				.responseCode(ApiResponseCode.SUCCESS)
				.nextBillDate(nextBillDate)
				.build();
	}

}
