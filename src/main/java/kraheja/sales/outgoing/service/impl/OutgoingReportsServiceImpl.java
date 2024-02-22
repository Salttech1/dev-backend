package kraheja.sales.outgoing.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
//import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.service.serviceimpl.BuildingServiceImpl;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.sales.bean.request.OutgoingDefaultersReportRequestBean;
import kraheja.sales.bean.request.OutgoingReportsRequestBean;
import kraheja.sales.bean.request.OutgoingSocietyAccountsReportRequestBean;
import kraheja.sales.bean.request.OutgoingSummaryRequestBean;
import kraheja.sales.infra.service.impl.OutinfraServiceImpl;
import kraheja.sales.outgoing.service.OutgoingReportsService;

@Service
@Transactional
@Entity
public class OutgoingReportsServiceImpl implements OutgoingReportsService {

	private static final Logger logger = LoggerFactory.getLogger(OutgoingReportsServiceImpl.class);

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private OutinfraServiceImpl outinfraServiceImpl;

	@Autowired
	private BuildingServiceImpl buildingServiceImpl;

	@PersistenceContext
	private EntityManager entityManager;

	public String initBldgInfo(ResponseEntity<?> bldgInfo, String colNameToSearch, int indexOfSearchChars,
			int extractChars) {
		int indexOfInfo;
		String bldgValToReturn;
		indexOfInfo = bldgInfo.getBody().toString().indexOf(colNameToSearch) + indexOfSearchChars;
		bldgValToReturn = bldgInfo.getBody().toString().substring(indexOfInfo, indexOfInfo + extractChars);
		indexOfInfo = bldgValToReturn.indexOf(",");
		if (indexOfInfo > 0) {
			bldgValToReturn = bldgValToReturn.substring(0, indexOfInfo);
		}
		return bldgValToReturn;
	}

	public String initStartOgMonth(String ownerId, String bldgCode, String flatNo, String wing, String billMonth,
			String billType, String billMode) {
		String startDate, startOgMonth = "";
		Integer month;
		startOgMonth = "";
		Query query;
		for (int i = 0; i < 3; i++) {
			query = this.entityManager.createNativeQuery("SELECT outr_startdate FROM outrate WHERE outr_bldgcode = '"
					+ bldgCode + "' AND outr_flatnum = '" + flatNo + "' AND outr_wing = '" + wing + "' " + "AND "
					+ billMonth + " BETWEEN outr_startdate AND outr_enddate AND outr_billtype = '" + billType + "' ");
			startDate = String.valueOf(query.getSingleResult());
			if (startDate != "") {
				startOgMonth = billMonth;
				break;
			}
			if (billMode == "M") {
				break;
			}
//			month = Integer.parseInt ( "1000")  ;   
			month = Integer.parseInt(billMonth.substring(5, 6)) + 1;
			if (month.toString().length() == 1) {
				billMonth = billMonth.substring(0, 4) + "0" + month.toString();
			} else {
				billMonth = billMonth.substring(0, 4) + month.toString();
			}
		}

		return startOgMonth;
	}

//	public Double initGstInfo(ResponseEntity<?> gstInfo, String colNameToSearch, int indexOfSearchChars,
//			int extractChars) {
//		int indexOfInfo;
//		Double gstValToReturn;
//		indexOfInfo = gstInfo.getBody().toString().indexOf(colNameToSearch) + indexOfSearchChars;
//		gstValToReturn = Double
//				.valueOf(gstInfo.getBody().toString().substring(indexOfInfo, indexOfInfo + extractChars));
//		indexOfInfo = gstValToReturn.toString().indexOf(",");
//		if (indexOfInfo > 0) {
//			gstValToReturn = Double.valueOf(gstValToReturn.toString().substring(0, indexOfInfo));
//		}
//		return gstValToReturn;
//	}

	public String initQtrEndDate(String billDate, String billType, String ownerId) {
		String qtrEndPeriod = "";
		Number ogMonths;

		switch (billDate.substring(3, 5)) {
		case "01":
		case "02":
		case "03":
			qtrEndPeriod = billDate.substring(6).concat("03");
			break;
		case "04":
		case "05":
		case "06":
			qtrEndPeriod = billDate.substring(6).concat("06");
			break;
		case "07":
		case "08":
		case "09":
			qtrEndPeriod = billDate.substring(6).concat("09");
			break;
		case "10":
		case "11":
		case "12":
			qtrEndPeriod = billDate.substring(6).concat("12");
			break;
		}
		Query query;
		if (billType.equals("F")) {
			query = this.entityManager
					.createNativeQuery("SELECT Nvl(fown_ogmonths,0) FROM flatowner WHERE fown_ownerid = '" + ownerId
							+ "' AND fown_ownertype = '0'");
			ogMonths = (Number) query.getSingleResult();
			if (ogMonths.equals(0)) {
				qtrEndPeriod = "";
			} else {
				query = this.entityManager.createNativeQuery("SELECT to_char(add_months(to_date('" + billDate
						+ "','dd/mm/yyyy') ," + String.valueOf(ogMonths) + " -1),'yyyymm') from dual ");
				qtrEndPeriod = String.valueOf(query.getSingleResult());
			}
		}
		return qtrEndPeriod;

	}

	public Double calcParticularsAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId,
			String parkingNo, String carParkOwnerId, String unitBillNo, String billType, String billMode,
			String billDate, String amtColName, String billModeDesc, String invoiceNo, String irnNo, String sessionId,
			Boolean ceilingRequired, String particularDesc, String particularCode) {
//
//		String wing, billNo = "", bldgCode, flatNum, billMonth, startOgMonth, billFromDate, qtrEndDateCalc = "",
//				qtrYear = "", currBillDate = "", currBillMonth, status;
		Double amount = 0.0;
//		Double cGstAmt, sGstAmt, iGstAmt = 0.0;
//		Integer startMonth, endMonth, gstAmt;
//
//		try {
//			bldgCode = ownerId.substring(0, 4);
//
//			if (ownerId == carParkOwnerId) {
//				billNo = unitBillNo;
//			}
//			wing = ownerId.substring(4, 5);
//			flatNum = ownerId.substring(5).trim();
//			billMonth = billDate.substring(6) + billDate.substring(3, 5);
//
//			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
//			if (startOgMonth != "") {
//				if (billMode == "Q") {
//					if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
//						qtrEndDateCalc = startOgMonth;
//					} else {
//						qtrEndDateCalc = qtrEndDate;
//					}
//					startMonth = Integer.parseInt(billDate.substring(3, 5));
//					endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
//					qtrYear = qtrEndDateCalc.substring(6, 10);
//					if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
//							&& startOgMonth.substring(0, 4) == qtrYear) {
//						startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
//						currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
//					}
//					for (startMonth = startMonth; startMonth <= endMonth; startMonth++) {
//						currBillMonth = qtrYear;
//						if (startMonth <= 9) {
//							currBillMonth = currBillMonth + "0" + startMonth;
//						} else {
//							currBillMonth = currBillMonth + startMonth;
//						}
//						amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
//						if (currBillDate == "") {
//							currBillDate = billDate;
//						}
////						status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
////								currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
////								particularCode, billModeDesc, invoiceNo, irnNo);
////						if (status != "SUCCESS") {
////							return -3.0 ;	
////						}
//					}
//					bldgCode = bldgCode;
//				} else {
//					endMonth = Integer.parseInt(billDate.substring(3, 5));
//					qtrYear = billDate.substring(6, 10);
//					if (endMonth <= 9) {
//						currBillMonth = qtrYear + "0" + endMonth.toString();
//					} else {
//						currBillMonth = qtrYear + endMonth.toString();
//					}
//					amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
//					if (currBillDate == "") {
//						currBillDate = billDate;
//					}
////					status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
////							currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
////							particularCode, billModeDesc, invoiceNo, irnNo);
////					if (status != "SUCCESS") {
////						parkingNo = "";
////						return amount;
////					}
//				}
//			} else {
//				return -1.0; // Start OG Month is blank
//			}
//
//		} catch (Exception e) {
//			return -2.0; // Error in amount calculation
//		}
//
////		if (calcGstAmt) {
////			if (LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) > 0
////					|| LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) == 0) {
//////				(int) (0.5 + Double.parseDouble(String.valueOf(query.getSingleResult())));
////
////			}
////		}
//
		return amount;
//
	}

	public ResponseEntity<?> calcInfraAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId,
			String parkingNo, String carParkOwnerId, String billNo, String unitBillNo, String billType, String billMode,
			String billDate, String billModeDesc, String invoiceNo, String irnNo, String sessionId, String parkingNos) {

		String wing, bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "", currBillDate = "",
				currBillMonth, status, amtColName, particularDesc, particularCode;
		Double amount = 0.0;
		Integer startMonth, endMonth, ceilamount;

		particularDesc = "Infra Maintenance Expenses(Service Code " + hsnCode + ")";
		particularCode = "INFR";
		amtColName = "Nvl(outr_infra,0)";

		try {
			bldgCode = ownerId.substring(0, 4);

			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = ownerId.substring(4, 5);
			flatNum = ownerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (billMode == "Q") {
					if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
						qtrEndDateCalc = startOgMonth;
					} else {
						qtrEndDateCalc = qtrEndDate;
					}
					startMonth = Integer.parseInt(billDate.substring(3, 5));
					endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
					qtrYear = qtrEndDateCalc.substring(6, 10);
					if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
							&& startOgMonth.substring(0, 4) == qtrYear) {
						startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
						currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
					}
					for (startMonth = startMonth; startMonth <= endMonth; startMonth++) {
						currBillMonth = qtrYear;
						if (startMonth <= 9) {
							currBillMonth = currBillMonth + "0" + startMonth;
						} else {
							currBillMonth = currBillMonth + startMonth;
						}
						amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
//						if (LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) > 0
//								|| LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) == 0) {
//							ceilamount = (int) (0.5 + amount);
//							amount = Double.parseDouble(String.valueOf(ceilamount));
//						}
						if (CommonUtils.INSTANCE.stringToDateFormatter(billDate)
								.compareTo(CommonUtils.INSTANCE.stringToDateFormatter("01/03/2018")) > 0
								|| CommonUtils.INSTANCE.stringToDateFormatter(billDate)
										.compareTo(CommonUtils.INSTANCE.stringToDateFormatter("01/03/2018")) == 0) {
							ceilamount = (int) (0.5 + amount);
							amount = Double.parseDouble(String.valueOf(ceilamount));
						}
						if (currBillDate == "") {
							currBillDate = billDate;
						}
						status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
								currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
								particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
						if (status != "SUCCESS") {
							return ResponseEntity
									.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
						}
					}
//					bldgCode = bldgCode;
				} else {
					endMonth = Integer.parseInt(billDate.substring(3, 5));
					qtrYear = billDate.substring(6, 10);
					if (endMonth <= 9) {
						currBillMonth = qtrYear + "0" + endMonth.toString();
					} else {
						currBillMonth = qtrYear + endMonth.toString();
					}
					amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
					if (Integer.parseInt(billMonth) >= Integer.parseInt("200804")) {
						if (LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) > 0
								|| LocalDate.parse(billDate).compareTo(LocalDate.parse("01/03/2018")) == 0) {
							ceilamount = (int) (0.5 + amount);
							amount = Double.parseDouble(String.valueOf(ceilamount));
						}
					}
					if (currBillDate == "") {
						currBillDate = billDate;
					}
					status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
							currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
							particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return ResponseEntity
								.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
					}
				}
			} else {
				return ResponseEntity.ok(
						ServiceResponseBean.builder().status(Boolean.FALSE).message("Start OG Month is blank").build());
			}

		} catch (Exception e) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("Error while calculating Infra amount" + e.getMessage()).build());
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
				.message("Added successfully").build());

	}

	public String calcMaintAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId, String parkingNo,
			String carParkOwnerId, String billNo, String unitBillNo, String billType, String billMode, String billDate,
			String billModeDesc, String invoiceNo, String irnNo, String sessionId, Double cGstPerc, Double sGstPerc,
			Double iGstPerc, String parkingNos) {

		String wing, bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "", currBillDate = "",
				currBillMonth, status, amtColName, particularDesc, particularCode;
		Double amount = 0.0;
		Integer startMonth, endMonth;

		bldgCode = ownerId.substring(0, 4);
		if (billType == "F") {
			if (bldgCode.substring(0, 4) == "OIHF") {
				particularDesc = "Adhoc Outgoings (Service Code " + hsnCode + ")";
			} else {
				particularDesc = "Lumpsum Outgoings (Service Code " + hsnCode + ")";
			}
		} else {
			particularDesc = "Other Maintenance Expenses (Service Code " + hsnCode + ")";
		}

		particularCode = "OMEX";
		amtColName = "Nvl(outr_maint,0)";

		try {

			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = ownerId.substring(4, 5);
			flatNum = ownerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
					qtrEndDateCalc = startOgMonth;
				} else {
					qtrEndDateCalc = qtrEndDate;
				}
				startMonth = Integer.parseInt(billDate.substring(3, 5));
				endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
				qtrYear = qtrEndDateCalc.substring(6, 10);
				if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
						&& startOgMonth.substring(0, 4) == qtrYear) {
					startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
					currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
				}
				for (startMonth = startMonth; startMonth <= endMonth; startMonth++) {
					currBillMonth = qtrYear;
					if (startMonth <= 9) {
						currBillMonth = currBillMonth + "0" + startMonth;
					} else {
						currBillMonth = currBillMonth + startMonth;
					}
					amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
					if (currBillDate == "") {
						currBillDate = billDate;
					}
					status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
							currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
							particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(cGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" CGST @" + cGstPerc.toString() + "%.", "CGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(sGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" SGST @" + sGstPerc.toString() + "%.", "SGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(iGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" IGST @" + iGstPerc.toString() + "%.", "IGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
				}
			} else {
				return "Start OG Month is blank";
			}

		} catch (Exception e) {
			return "Error while calculating Maintenance amount" + e.getMessage();
		}

		return "SUCCESS";

	}

	public String calcAdminAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId, String parkingNo,
			String carParkOwnerId, String billNo, String unitBillNo, String billType, String billMode, String billDate,
			String billModeDesc, String invoiceNo, String irnNo, String sessionId, Double cGstPerc, Double sGstPerc,
			Double iGstPerc, String parkingNos) {

		String wing, bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "", currBillDate = "",
				currBillMonth, status, amtColName, particularDesc, particularCode;
		Double amount = 0.0;
		Integer startMonth, endMonth;

		bldgCode = ownerId.substring(0, 4);
		particularDesc = "Admin Charges (Service Code " + hsnCode + ")";

		particularCode = "ADMI";
		amtColName = "Nvl(outr_admincharges,0)";

		try {
			if (billMode == "Q") {

			}
			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = ownerId.substring(4, 5);
			flatNum = ownerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
					qtrEndDateCalc = startOgMonth;
				} else {
					qtrEndDateCalc = qtrEndDate;
				}
				startMonth = Integer.parseInt(billDate.substring(3, 5));
				endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
				qtrYear = qtrEndDateCalc.substring(6, 10);
				if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
						&& startOgMonth.substring(0, 4) == qtrYear) {
					startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
					currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
				}
				for (startMonth = startMonth; startMonth <= endMonth; startMonth++) {
					currBillMonth = qtrYear;
					if (startMonth <= 9) {
						currBillMonth = currBillMonth + "0" + startMonth;
					} else {
						currBillMonth = currBillMonth + startMonth;
					}
					amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
					if (currBillDate == "") {
						currBillDate = billDate;
					}
					status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
							currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
							particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(cGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" CGST @" + cGstPerc.toString() + "%", "CGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(sGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" SGST @" + sGstPerc.toString() + "%", "SGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
					status = processGstAmt(iGstPerc, amount, billDate, ownerId, bldgCode, wing, flatNum, parkingNo,
							" IGST @" + iGstPerc.toString() + "%", "IGST", currBillDate, qtrEndDate, sessionId, billNo,
							unitBillNo, carParkOwnerId, currBillMonth, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
				}
			} else {
				return "Start OG Month is blank";
			}

		} catch (Exception e) {
			return "Error while calculating Admin amount" + e.getMessage();
		}

		return "SUCCESS";

	}

	public String calcArrearsAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId,
			String parkingNo, String carParkOwnerId, String billNo, String unitBillNo, String billType, String billMode,
			String billDate, String billModeDesc, String invoiceNo, String irnNo, String sessionId, String parkingNos) {

		String wing, prevBillNo = "", bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "",
				currBillDate = "", currBillMonth = "", status, particularDesc, particularCode, sqlString, sqlWhere,
				sqlMonthWhere, rectDate;
		Date prevBillDate = new Date();
		Double prevBillTotAmount, totReceiptAmount, arrearsAmount = 0.0;
		Integer startMonth, endMonth;

		bldgCode = carParkOwnerId.substring(0, 4);
		particularDesc = "Arrears";
		particularCode = "ARRE";

		try {

			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = carParkOwnerId.substring(4, 5);
			flatNum = carParkOwnerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
					qtrEndDateCalc = startOgMonth;
				} else {
					qtrEndDateCalc = qtrEndDate;
				}
				startMonth = Integer.parseInt(billDate.substring(3, 5));
				endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
				qtrYear = qtrEndDateCalc.substring(6, 10);
				if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
						&& startOgMonth.substring(0, 4) == qtrYear) {
					startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
					currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
				}
				currBillMonth = qtrYear;
				if (startMonth <= 9) {
					currBillMonth = currBillMonth + "0" + startMonth;
				} else {
					currBillMonth = currBillMonth + startMonth;
				}
			}
			if (currBillDate == "") {
				currBillDate = billDate;
			}

			sqlWhere = "FROM outbill WHERE obill_bldgcode = '" + bldgCode + "' AND obill_wing = '" + wing
					+ "' AND obill_flatnum = '" + flatNum + "' AND obill_billtype = '" + billType + "' ";

			sqlMonthWhere = "and obill_month < '" + billMonth + "')) ";

			sqlString = "SELECT obill_billnum , obill_billdate " + sqlWhere
					+ " and (obill_billnum = (select obill_billnum " + sqlWhere
					+ " and obill_month = (select MAX(obill_month) " + sqlWhere + sqlMonthWhere
					+ ") and (obill_month = (select MAX(obill_month) " + sqlWhere + sqlMonthWhere;

			Query query = this.entityManager.createNativeQuery(sqlString, Tuple.class);
			List<Tuple> billTuple = query.getResultList();

			for (Tuple billList : billTuple) {
				prevBillNo = billList.get(0, String.class);
				prevBillDate = billList.get(1, Date.class);
			}
			if (prevBillDate == null) {
				prevBillDate = new SimpleDateFormat("yyyy-MM-dd").parse("1956-01-01");
			}

			sqlString = "SELECT NVL(sum( nvl(obill_billamt,0) + nvl(obill_arrears,0) + nvl(obill_admincharges,0) + nvl(obill_cgst ,0) + "
					+ "nvl(obill_sgst ,0) + nvl(obill_igst ,0) + nvl(obill_servicetax ,0) + + nvl(obill_swachhcess ,0) + "
					+ "nvl(obill_krishicess ,0) + nvl(obill_water ,0) + nvl(obill_elect ,0) + nvl(obill_natax ,0) ),0) as arrears "
					+ sqlWhere + " AND obill_billnum = '" + prevBillNo + "' ";

			query = this.entityManager.createNativeQuery(sqlString);
			prevBillTotAmount = Double.parseDouble(String.valueOf(query.getSingleResult()));

			sqlString = "SELECT nvl(sum(nvl(out_amtpaid,0) + nvl(out_admincharges,0) + nvl(out_cgst ,0) + nvl(out_sgst ,0) + "
					+ "nvl(out_igst ,0)  + nvl(out_servtax ,0)  + nvl(out_swachhcess ,0)  + nvl(out_krishicess ,0)  + nvl(out_propertytax ,0) + "
					+ "nvl(out_water ,0) + nvl(out_elect ,0) + nvl(out_natax ,0) ),0) as RecTotal FROM outcoll "
					+ "WHERE out_bldgcode = '" + bldgCode + "' AND out_wing = '" + wing + "' AND out_flatnum = '"
					+ flatNum + "' " + "AND to_char(out_recdate,'dd/mm/yyyy') BETWEEN '"
					+ prevBillDate.toString().substring(8, 10) + prevBillDate.toString().substring(5, 7)
					+ prevBillDate.toString().substring(0, 4) + "' AND '" + currBillDate.substring(0, 2)
					+ currBillDate.substring(3, 5) + currBillDate.substring(6, 10) + "' " + "AND out_rectype = '"
					+ billType + "' AND out_cancelledyn = 'N' ";
			query = this.entityManager.createNativeQuery(sqlString);
			totReceiptAmount = Double.parseDouble(String.valueOf(query.getSingleResult()));

			arrearsAmount = prevBillTotAmount - totReceiptAmount;

			rectDate = "01" + "/" + billDate.substring(3, 5) + "/" + billDate.substring(6);

			if (billMode != "Q") {
				qtrEndDate = YearMonth.parse(billDate.substring(6) + "-" + billDate.substring(3, 5),
						DateTimeFormatter.ofPattern("yyyy-MM")).atEndOfMonth().toString();
			}
			status = insertogbilltemp(ownerId, bldgCode, wing, ownerId.substring(5).trim(), parkingNo, particularDesc,
					arrearsAmount, currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId,
					currBillMonth, particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
			if (status != "SUCCESS") {
				return status;
			}

		} catch (Exception e) {
			return "Error while calculating Arrears amount" + e.getMessage();
		}

		return "SUCCESS";

	}

	public String calcInterestAmt(String hsnCode, String qtrEndDate, String qtrEndyyyymm, String ownerId,
			String parkingNo, String carParkOwnerId, String billNo, String unitBillNo, String billType, String billMode,
			String billDate, String billModeDesc, String invoiceNo, String irnNo, String sessionId, String parkingNos) {

		String wing, prevBillNo = "", bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "",
				currBillDate = "", currBillMonth = "", status, particularDesc, particularCode, sqlString, sqlWhere,
				sqlMonthWhere, rectDate, prevBillDate = "", lastBillDate = "", intCalcFrom = "", intCalcFromInt = "",
				billMM, intFrom = "", recDate;
		Double billArrears = 0.0, billMonthlyOsInt = 0.0, rectAmt = 0.0, billInterest = 0.0, rectInterest = 0.0,
				totAmtReceived = 0.0, totIntReceived = 0.0, currBillInterest = 0.0, currBillInterestInterest = 0.0,
				finalIntAmt = 0.0;
		Integer startMonth, endMonth, chqCount = 0;
		long noOfDays = 0;

		bldgCode = carParkOwnerId.substring(0, 4);
		particularDesc = "Interest On Arrears";
		particularCode = "INTC";

		try {

			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = carParkOwnerId.substring(4, 5);
			flatNum = carParkOwnerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
					qtrEndDateCalc = startOgMonth;
				} else {
					qtrEndDateCalc = qtrEndDate;
				}
				startMonth = Integer.parseInt(billDate.substring(3, 5));
				endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
				qtrYear = qtrEndDateCalc.substring(6, 10);
				if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
						&& startOgMonth.substring(0, 4) == qtrYear) {
					startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
					currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
				}
				currBillMonth = qtrYear;
				if (startMonth <= 9) {
					currBillMonth = currBillMonth + "0" + startMonth;
				} else {
					currBillMonth = currBillMonth + startMonth;
				}
			}
			if (currBillDate == "") {
				currBillDate = billDate;
			}

			sqlWhere = "FROM outbill WHERE obill_bldgcode = '" + bldgCode + "' AND obill_wing = '" + wing
					+ "' AND obill_flatnum = '" + flatNum + "' AND obill_billtype = '" + billType + "' ";

			sqlMonthWhere = "and obill_month < '" + billMonth + "')) ";

			sqlString = "SELECT obill_billnum , To_Char(obill_billdate,'dd/mm/yyyy') AS obill_billdate " + sqlWhere
					+ " and (obill_billnum = (select obill_billnum " + sqlWhere
					+ " and obill_month = (select MAX(obill_month) " + sqlWhere + sqlMonthWhere
					+ ") and (obill_month = (select MAX(obill_month) " + sqlWhere + sqlMonthWhere;

			Query query = this.entityManager.createNativeQuery(sqlString, Tuple.class);
			List<Tuple> billTuple = query.getResultList();

			for (Tuple billList : billTuple) {
				prevBillNo = billList.get("obill_billnum", String.class);
				prevBillDate = billList.get("obill_billdate", String.class);
				lastBillDate = billList.get("obill_billdate", String.class);
				intCalcFrom = lastBillDate;
				intCalcFromInt = lastBillDate;
			}
			if (prevBillDate == null) {
				prevBillDate = "01/01/1900";
			}
			if (lastBillDate == "") {
				lastBillDate = billDate;
				intCalcFrom = billDate;
				intCalcFromInt = billDate;
			}

			sqlString = "SELECT NVL(sum( nvl(obill_billamt,0) + nvl(obill_arrears,0) + nvl(obill_admincharges,0) + nvl(obill_cgst ,0) + "
					+ "nvl(obill_sgst ,0) + nvl(obill_igst ,0) + nvl(obill_servicetax ,0) + + nvl(obill_swachhcess ,0) + "
					+ "nvl(obill_krishicess ,0) + nvl(obill_water ,0) + nvl(obill_elect ,0) + nvl(obill_natax ,0) ),0) as arrears , "
					+ "nvl(sum(nvl(obill_interest,0)),0) as intrest " + sqlWhere + " AND obill_billnum = '" + prevBillNo
					+ "' ";

			query = this.entityManager.createNativeQuery(sqlString, Tuple.class);
			List<Tuple> totBillTuple = query.getResultList();
			for (Tuple totBillList : totBillTuple) {
				billArrears = totBillList.get("arrears", BigDecimal.class).doubleValue();
				billInterest = totBillList.get("intrest", BigDecimal.class).doubleValue();
				billMonthlyOsInt = billInterest;
			}
			billMM = lastBillDate;

			// Calculate Interest Amount From Receipt
			sqlString = "SELECT to_char(out_recdate,'dd/mm/yyyy') AS out_recdate , round(nvl(sum(nvl(out_amtpaid,0) + nvl(out_admincharges,0) + nvl(out_cgst ,0) + nvl(out_sgst ,0) + "
					+ "nvl(out_igst ,0)  + nvl(out_servtax ,0)  + nvl(out_swachhcess ,0)  + nvl(out_krishicess ,0)  + nvl(out_propertytax ,0) + "
					+ "nvl(out_water ,0) + nvl(out_elect ,0) + nvl(out_natax ,0) ),0)) as rectotal , round(sum(out_amtint)) as intrecd "
					+ "FROM outcoll WHERE out_bldgcode = '" + bldgCode + "' AND out_wing = '" + wing
					+ "' AND out_flatnum = '" + flatNum + "' " + "AND to_char(out_recdate,'dd/mm/yyyy') > '"
					+ prevBillDate + "' AND to_char(out_recdate,'dd/mm/yyyy') <= '" + currBillDate.substring(0, 2) + "/"
					+ currBillDate.substring(3, 5) + "/" + currBillDate.substring(6, 10) + "' " + "AND out_rectype = '"
					+ billType + "' AND out_cancelledyn = 'N'  group by out_recdate ";

//			String prevBillDateddMMMyyyy, currBillDateddMMMyyyy;
//			here

//			prevBillDateddMMMyyyy = new SimpleDateFormat("dd-MM-yyyy").parse(prevBillDate).toString();
//			currBillDateddMMMyyyy = new SimpleDateFormat("dd-MM-yyyy").parse(currBillDate).toString();

			sqlString = "SELECT to_char(out_recdate,'dd/mm/yyyy') AS out_recdate , round(nvl(sum(nvl(out_amtpaid,0) + nvl(out_admincharges,0) + nvl(out_cgst ,0) + nvl(out_sgst ,0) + "
					+ "nvl(out_igst ,0)  + nvl(out_servtax ,0)  + nvl(out_swachhcess ,0)  + nvl(out_krishicess ,0)  + nvl(out_propertytax ,0) + "
					+ "nvl(out_water ,0) + nvl(out_elect ,0) + nvl(out_natax ,0) ),0)) as rectotal , round(sum(out_amtint)) as intrecd "
					+ "FROM outcoll WHERE out_bldgcode = '" + bldgCode + "' AND out_wing = '" + wing
					+ "' AND out_flatnum = '" + flatNum + "' " + "AND out_recdate > to_date('" + prevBillDate
					+ "','dd/mm/yyyy') AND out_recdate <= to_date('" + currBillDate + "','dd/mm/yyyy') "
					+ "AND out_rectype = '" + billType + "' AND out_cancelledyn = 'N'  group by out_recdate ";

			query = this.entityManager.createNativeQuery(sqlString, Tuple.class);
			List<Tuple> rectTuple = query.getResultList();
			if (rectTuple.size() > 0) {
				for (Tuple rectList : rectTuple) {
//					chqCount is used as logic is different for first Receipt recd as difference is between Last Bill Date and Receipt Date 
					chqCount = chqCount + 1;
					rectAmt = rectList.get("rectotal", BigDecimal.class).doubleValue();
					rectInterest = rectList.get("intrecd", BigDecimal.class).doubleValue();
					recDate = rectList.get("out_recdate", String.class);
					if (recDate == null) { // How can Recdate be null
						billMM = lastBillDate;
						intFrom = billMM;
					} else {
						billMM = recDate.toString();
					}
					totAmtReceived = totAmtReceived + rectAmt;
					totIntReceived = totIntReceived + rectInterest;

					noOfDays = ChronoUnit.DAYS.between(
							LocalDate.parse(lastBillDate, DateTimeFormatter.ofPattern("d/MM/yyyy")),
							LocalDate.parse(billMM, DateTimeFormatter.ofPattern("d/MM/yyyy")));

					if (billArrears > 0) {
						if (chqCount == 1) {
							if (noOfDays > 30) {
								currBillInterest = currBillInterest
										+ procIntAmount(lastBillDate, billMM, billArrears, billMode);
								intCalcFrom = billMM;
							}
						} else {
							if (noOfDays > 30) {
								currBillInterest = currBillInterest
										+ procIntAmount(intCalcFrom, billMM, billArrears, billMode);
								intCalcFrom = billMM;
							}
						}
					}
					billArrears = billArrears - rectAmt;
					// Monthly_os_interest
					if (billMonthlyOsInt > 0) {
						if (chqCount == 1) {
							if (noOfDays > 30) {
								currBillInterestInterest = currBillInterestInterest
										+ procIntAmount(lastBillDate, billMM, billMonthlyOsInt, billMode);
							}
						} else {
							if (noOfDays > 30) {
								currBillInterestInterest = currBillInterestInterest
										+ procIntAmount(intFrom, billMM, billMonthlyOsInt, billMode);
							}
						}
						intCalcFromInt = billMM;
					}
					billMonthlyOsInt = billMonthlyOsInt - rectInterest;

					intFrom = billMM;
					billMM = lastBillDate;
				}
				if (billArrears > 0) {
					currBillInterest = currBillInterest + procIntAmount(intCalcFrom, billDate, billArrears, billMode);
//					if (currBillInterest < 0) {
//						currBillInterest = 0.0;
//					}
				}
				if (billMonthlyOsInt > 0) {
					currBillInterestInterest = currBillInterestInterest
							+ procIntAmount(intCalcFromInt, billDate, billMonthlyOsInt, billMode);
//					if (currBillInterestInterest < 0) {
//						currBillInterestInterest = 0.0;
//					}
				}
				finalIntAmt = billInterest - totIntReceived + currBillInterestInterest + currBillInterest;
			} else { // IF NO RECEIPT RECEIVED THEN FOLLOWING
				if (billArrears > 0) {
					currBillInterest = currBillInterest + procIntAmount(lastBillDate, billDate, billArrears, billMode);
//					if (currBillInterest < 0) {
//						currBillInterest = 0.0;
//					}
				}
				if (billInterest > 0) {
					currBillInterestInterest = currBillInterestInterest
							+ procIntAmount(lastBillDate, billDate, billInterest, billMode);
//					if (currBillInterestInterest < 0) {
//						currBillInterestInterest = 0.0;
//					}
				}
				finalIntAmt = billInterest + currBillInterestInterest + currBillInterest;
			}
			if (finalIntAmt < 0) {
				finalIntAmt = 0.0;
			}

//			if (billMode != "Q") {
//				qtrEndDate = YearMonth.parse(billDate.substring(6) + "-" + billDate.substring(3, 5),
//						DateTimeFormatter.ofPattern("yyyy-MM")).atEndOfMonth().toString();
//			}
			status = insertogbilltemp(ownerId, bldgCode, wing, ownerId.substring(5).trim(), parkingNo, particularDesc,
					finalIntAmt, currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
					particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
			if (status != "SUCCESS") {
				return status;
			}

		} catch (Exception e) {
			return "Error while calculating Interest amount" + e.getMessage();
		}

		return "SUCCESS";

	}

	public String calcWaterElecNaTaxAmt(String particularCode, String particularDesc, String amtColName, String hsnCode,
			String qtrEndDate, String qtrEndyyyymm, String ownerId, String parkingNo, String carParkOwnerId,
			String billNo, String unitBillNo, String billType, String billMode, String billDate, String billModeDesc,
			String invoiceNo, String irnNo, String sessionId, String parkingNos) {

		String wing, bldgCode, flatNum, billMonth, startOgMonth, qtrEndDateCalc = "", qtrYear = "", currBillDate = "",
				currBillMonth, status;
		Double amount = 0.0;
		Integer startMonth, endMonth;

		bldgCode = ownerId.substring(0, 4);

		try {
			if (billMode == "Q") {

			}
			if (ownerId == carParkOwnerId) {
				billNo = unitBillNo;
			}
			wing = ownerId.substring(4, 5);
			flatNum = ownerId.substring(5).trim();
			billMonth = billDate.substring(6) + billDate.substring(3, 5);

			startOgMonth = initStartOgMonth(ownerId, bldgCode, flatNum, wing, billMonth, billType, billMode);
			if (startOgMonth != "") {
				if (Integer.parseInt(startOgMonth) > Integer.parseInt(qtrEndyyyymm)) {
					qtrEndDateCalc = startOgMonth;
				} else {
					qtrEndDateCalc = qtrEndDate;
				}
				startMonth = Integer.parseInt(billDate.substring(3, 5));
				endMonth = Integer.parseInt(qtrEndDate.substring(3, 5));
				qtrYear = qtrEndDateCalc.substring(6, 10);
				if (Integer.parseInt(startOgMonth.substring(4, 6)) > startMonth
						&& startOgMonth.substring(0, 4) == qtrYear) {
					startMonth = Integer.parseInt(startOgMonth.substring(4, 6));
					currBillDate = "01" + "/" + startOgMonth.substring(4, 6) + "/" + qtrYear;
				}
				for (startMonth = startMonth; startMonth <= endMonth; startMonth++) {
					currBillMonth = qtrYear;
					if (startMonth <= 9) {
						currBillMonth = currBillMonth + "0" + startMonth;
					} else {
						currBillMonth = currBillMonth + startMonth;
					}
					amount = fetchOgRate(amtColName, bldgCode, wing, parkingNo, currBillMonth, billType);
					if (LocalDate.parse(billDate).compareTo(LocalDate.parse("2018-03-01")) > 0
							|| LocalDate.parse(billDate).compareTo(LocalDate.parse("2018-03-01")) == 0) {
						int ceilAmt = (int) (0.5 + Double.parseDouble(String.valueOf(amount)));
						amount = Double.parseDouble(String.valueOf(ceilAmt));
					}
					if (currBillDate == "") {
						currBillDate = billDate;
					}
					status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, amount,
							currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
							particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);
					if (status != "SUCCESS") {
						return status;
					}
				}
			} else {
				return "Start OG Month is blank";
			}

		} catch (Exception e) {
			return "Error while calculating Admin amount" + e.getMessage();
		}

		return "SUCCESS";

	}

	public Double procIntAmount(String lastBillDate, String billMM, Double billArrears, String billMode) {

		String sqlString;
		Double intRate = 0.0, dailyInterest = 0.0;
		long noOfDays;
		BigDecimal intAmount;

		sqlString = "SELECT ent_num1 FROM entity WHERE ent_class = '#OGIN' AND ent_id = '#OGIN' AND ent_char1='"
				+ billMode + "' ";
		Query query = this.entityManager.createNativeQuery(sqlString);
		intRate = Double.parseDouble(String.valueOf(query.getSingleResult()));

		noOfDays = ChronoUnit.DAYS.between(LocalDate.parse(lastBillDate, DateTimeFormatter.ofPattern("d/MM/yyyy")),
				LocalDate.parse(billMM, DateTimeFormatter.ofPattern("d/MM/yyyy")));

		dailyInterest = ((billArrears / 100) * intRate) / 365;
		intAmount = BigDecimal.valueOf(dailyInterest * noOfDays);
		if (intAmount.compareTo(BigDecimal.ZERO) < 0) {
			intAmount = BigDecimal.ZERO;
		}
		return intAmount.setScale(0, RoundingMode.HALF_DOWN).doubleValue();
	}

	public String processGstAmt(Double gstPerc, Double amount, String billDate, String ownerId, String bldgCode,
			String wing, String flatNum, String parkingNo, String particularDesc, String particularCode,
			String currBillDate, String qtrEndDate, String sessionId, String billNo, String unitBillNo,
			String carParkOwnerId, String currBillMonth, String billModeDesc, String invoiceNo, String irnNo,
			String parkingNos) {

		int ceilGstAmt;
		String status;
		Double gstAmt = 0.0;
		billDate = billDate.substring(6, 10) + "-" + billDate.substring(3, 5) + "-" + billDate.substring(0, 2);

		try {
			if (gstPerc > 0) {
				if (LocalDate.parse(billDate).compareTo(LocalDate.parse("2018-03-01")) > 0
						|| LocalDate.parse(billDate).compareTo(LocalDate.parse("2018-03-01")) == 0) {
					ceilGstAmt = (int) (0.5 + Double.parseDouble(String.valueOf(amount * gstPerc / 100)));
					gstAmt = Double.parseDouble(String.valueOf(ceilGstAmt));
				} else {
					gstAmt = amount * gstPerc / 100;
				}
				status = insertogbilltemp(ownerId, bldgCode, wing, flatNum, parkingNo, particularDesc, gstAmt,
						currBillDate, qtrEndDate, sessionId, billNo, unitBillNo, carParkOwnerId, currBillMonth,
						particularCode, billModeDesc, invoiceNo, irnNo, parkingNos);

				if (status != "SUCCESS") {
					return status;
				}
			}

		} catch (Exception e) {
			return "Error in amount calculation" + e.getMessage();
		}
		return "SUCCESS";
	}

	public String insertogbilltemp(String ownerId, String bldgCode, String wing, String flatNum, String parkingNo,
			String particulars, Double amount, String currBillDate, String qtrEndDate, String sessionId, String billNo,
			String unitBillNo, String carParkingOwnerId, String billMonth, String particularCode, String billModeDesc,
			String invoiceNo, String irnNo, String parkingNos) {

		String sqlString;

		sqlString = "INSERT INTO saogrp01_print ( "
				+ "saogrp_ownerid , saogrp_bldgcode , saogrp_wing , saogrp_flatnum , saogrp_carpark , saogrp_particulars , saogrp_amount , "
				+ "saogrp_billfrom , saogrp_billto , saogrp_sessid , saogrp_userid , saogrp_billnum , saogrp_unitbillno , saogrp_cfownerid , "
				+ "saogrp_billmonth , saogrp_parcode , saogrp_billmode , saogrp_today , saogrp_invoiceno , saogrp_irnno , saogrp_carparklist) "
				+ "values ('" + ownerId + "' , '" + bldgCode + "' , '" + wing + "' , '" + flatNum + "' , '" + parkingNo
				+ "' , '" + particulars + "' , " + amount.toString() + " , to_date('" + currBillDate
				+ "','dd/mm/yyyy') , to_date('" + qtrEndDate + "','dd/mm/yyyy') , " + sessionId + " , '"
				+ GenericAuditContextHolder.getContext().getUserid() + "' , '" + billNo + "' , '" + unitBillNo + "' , '"
				+ carParkingOwnerId + "' , '" + billMonth + "' , '" + particularCode + "' , '" + billModeDesc
				+ "' , sysdate , '" + invoiceNo + "' , '" + irnNo + "','" + parkingNos + "')";
		try {
			Query query = this.entityManager.createNativeQuery(sqlString);
			Integer rowCount = query.executeUpdate();
			if (rowCount == 0) {
				return "Data not inserted in temp table - saogrp01_print";
			}
		}

		catch (Exception e) {
			return e.getMessage() + "\r\n" + e.getStackTrace().toString();

		}

		return "SUCCESS";
	}

	public Double fetchOgRate(String colName, String bldgCode, String wing, String flatNum, String billMonth,
			String billType) {

		String sqlString = "";
		Double amount = 0.0;

//		if (ceilingRequired = true) {
//			sqlString = "SELECT Trim(ent_char2) FROM entity WHERE ent_class = 'OGBIL' AND ent_id = 'CEIL' AND ent_char1 = '"
//					+ billType + "' ";
//
//			Query query = this.entityManager.createNativeQuery(sqlString);
//			ceilingMonth = String.valueOf(query.getSingleResult());
//
////			if (Integer.parseInt(billMonth) >= Integer.parseInt(ceilingMonth)) {
////				sqlString = "SELECT floor(" + colName + ")";
////			}
//		}
//
////		if (ceilingMonth == "") {
////			sqlString = "SELECT " + colName;
////		}

		sqlString = "SELECT " + colName + " FROM outrate WHERE outr_bldgcode = '" + bldgCode + "' AND outr_wing = '"
				+ wing + "' " + "AND outr_flatnum = '" + flatNum + "' AND '" + billMonth
				+ "' BETWEEN outr_startdate AND outr_enddate AND outr_billtype = '" + billType + "' ";

		Query query = this.entityManager.createNativeQuery(sqlString);
//		if (ceilingMonth == "") {
		amount = Double.parseDouble(String.valueOf(query.getSingleResult()));
//		} else {
//			ceilamount = (int) (0.5 + Double.parseDouble(String.valueOf(query.getSingleResult())));
//			amount = Double.parseDouble(String.valueOf(ceilamount));
//			amount = Math.ceil(Double.parseDouble(String.valueOf(query.getSingleResult())));
//		}

		return amount;
	}

	public ResponseEntity<?> processOgBills(OutgoingReportsRequestBean outgoingReportsRequestBean) {

		Double cGstPerc = 0.0;
		Double sGstPerc = 0.0;
		Double iGstPerc = 0.0;
//		Double amount = 0.0;
		String desc, hsnCode = "995419";
		// Fetch building info (sales state, city, type, company)
		String bldgSalesState, bldgCity, bldgType, bldgCoy, qtrEndDate, qtrEndDateParking, billMonth, qtrEndyyyymm,
				qtrEndyyyymmParking;
		String bldgCode, billType, billMode, billModeDesc, ownerIdFrom, ownerIdUpto;
		Query query;
		int infraBldgCount;
		boolean genInfraAmt = false;
		ResponseEntity<?> calcAmtResponseBean;
		bldgCode = outgoingReportsRequestBean.getFlatOwnerFrom().substring(0, 4);
		billType = outgoingReportsRequestBean.getBillType();

		ResponseEntity<?> bldgInfo = this.buildingServiceImpl.fetchBuildingByCode(bldgCode);

		bldgSalesState = initBldgInfo(bldgInfo, ", salesstate", 13, 3);
		bldgCity = initBldgInfo(bldgInfo, ", city", 7, 5);
		bldgType = initBldgInfo(bldgInfo, ", bldgtype", 11, 1);
		bldgCoy = initBldgInfo(bldgInfo, ", coy", 6, 5);

		// Fetch GST % and SAC Description
		ResponseEntity<?> gstResponseBean = outinfraServiceImpl.fetchGstRates();
		desc = initBldgInfo(gstResponseBean, ", description", 14, 50);
		cGstPerc = Double.valueOf(initBldgInfo(gstResponseBean, "cgstperc", 9, 4));
		sGstPerc = Double.valueOf(initBldgInfo(gstResponseBean, ", sgstperc", 11, 4));
		if (bldgSalesState.equals("MAH")) {
		} else {
			iGstPerc = Double.valueOf(initBldgInfo(gstResponseBean, ", igstperc", 11, 4));
		}

//		cgst = initGstInfo(gstResponseBean, "cgstperc", 9, 4);
//		sgst = initGstInfo(gstResponseBean, ", sgstperc", 11, 4);
//		igst = initGstInfo(gstResponseBean, ", igstperc", 11, 4);
//		String responseData;
//		String gstResponse = gstResponseBean.getBody().toString() ;
//		JSONObject gstJson = new JSONObject(gstResponseBean.getBody());
//
//		try {
//			responseData = gstJson.getString("data");x
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		JSONObject arrJSON = JSON.parse(gstResponseBean)

		ownerIdFrom = outgoingReportsRequestBean.getFlatOwnerFrom();
		ownerIdUpto = outgoingReportsRequestBean.getFlatOwnerUpto();
		qtrEndyyyymm = "";
		qtrEndDate = "";

		String billDate = outgoingReportsRequestBean.getBillGenerationDate();
		billMonth = billDate.substring(6).concat(billDate.substring(3, 5));

		query = this.entityManager.createNativeQuery(
				"SELECT count(*) FROM entity WHERE ent_class = 'OGBIL' AND ent_id = 'INCAL' AND ent_char1 = '"
						+ bldgCode + "' AND " + "to_date('" + billDate
						+ "','dd/mm/yyyy') BETWEEN ent_date1 AND ent_date2",
				Integer.class);
		infraBldgCount = query.getFirstResult();
		if (infraBldgCount != 0) {
			genInfraAmt = true;
		}

		if (billType.equals("F") || bldgType.equals("R")) {
			billMode = "Q";
			billModeDesc = "Quarterly"; // For 1st (Lumpsum) bill or Residential Building
			qtrEndyyyymm = initQtrEndDate(billDate, billType, ownerIdFrom);
		} else {
			billMode = "M";
			billModeDesc = "Monthly"; // For Normal bill and other than Residential Building
			qtrEndyyyymm = billDate.substring(6).concat(billDate.substring(3, 5));
		}
		query = this.entityManager
				.createNativeQuery("SELECT to_char(trunc(last_day(to_date('01/" + qtrEndyyyymm.substring(4) + "/"
						+ qtrEndyyyymm.substring(0, 4) + "','dd/mm/yyyy'))),'dd/mm/yyyy') from dual ");
		qtrEndDate = String.valueOf(query.getSingleResult());

		// Get Flats List for processing (only Main Flats Units. Parking is there in
		// another loop)
		String sqlString = "", flatNo, wing, startOgMonth, invoiceNo = "", irnNo = "", unitBillNo = "", serNumber = "",
				parkingOwnerId, parkingNo, parkingNos, status = "", billNo = ""; // ,

		sqlString = "SELECT distinct flat_ownerid FROM flats, outrate WHERE flat_bldgcode = outr_bldgcode AND flat_wing = outr_wing "
				+ "AND flat_flatnum = outr_flatnum AND flat_soldyn = 'Y' AND substr(flat_flatnum,1,1) IN ('F','H','U','O') AND ('"
				+ billMonth + "' between OUTR_STARTDATE AND OUTR_ENDDATE ";
		if (bldgType.equals("R")) {
			sqlString = sqlString + "OR '" + qtrEndyyyymm + "' BETWEEN OUTR_STARTDATE AND OUTR_ENDDATE";
		}
		sqlString = sqlString + ") AND flat_ownerid between '" + ownerIdFrom + "' AND '" + ownerIdUpto
				+ "' AND outr_billtype = '" + billType + "' ORDER BY flat_ownerid ";

		query = this.entityManager.createNativeQuery(sqlString);
		List<String> flatsList = query.getResultList();
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

		String[] owners = new String[flatsList.size()];
		for (String flatOwner : flatsList) {
			flatNo = flatOwner.substring(5).trim();
			wing = flatOwner.substring(4, 5);
			startOgMonth = initStartOgMonth(flatOwner, bldgCode, flatNo, wing, billMonth, billType, billMode);

			// Fetch invoice, irnno, billnum generated for the selected bill date
			sqlString = " FROM outbill WHERE obill_bldgcode = '" + bldgCode + "' AND obill_ownerid = '" + flatOwner
					+ "' AND obill_month = '" + startOgMonth + "' " + "AND obill_gstyn = 'Y' AND obill_billtype = '"
					+ billType + "' ";

			query = this.entityManager.createNativeQuery(
					"SELECT nvl(obill_invoiceno,' ') AS obill_invoiceno ,  obill_irnno , obill_billnum " + sqlString,
					Tuple.class);
			List<Tuple> billTuple = query.getResultList();
			if (billTuple.size() > 0) {
				for (Tuple billList : billTuple) {
					invoiceNo = billList.get("obill_invoiceno", String.class);
					irnNo = billList.get("obill_irnno", String.class);
					unitBillNo = billList.get("obill_billnum", String.class);
				}
			}

			if (unitBillNo == "") {
//				billNum = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "OGBIL",
//						GenericAuditContextHolder.getContext().getSite());
				unitBillNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "OGBIL", "MUM");
			}

			parkingNos = "";

			sqlString = "SELECT flat_flatnum,flat_ownerid FROM flats, outrate WHERE flat_bldgcode = outr_bldgcode AND flat_wing = outr_wing "
					+ "AND flat_flatnum = outr_flatnum AND flat_soldyn = 'Y' AND flat_accomtype in('C','R','B','G','P') AND ('"
					+ billMonth + "' between OUTR_STARTDATE AND OUTR_ENDDATE ";
			if (bldgType.equals("R")) {
				sqlString = sqlString + "OR '" + qtrEndyyyymm + "' BETWEEN OUTR_STARTDATE AND OUTR_ENDDATE";
			}
			sqlString = sqlString + ") AND flat_flatpark = '" + flatNo + "' AND flat_bldgcode = '" + bldgCode
					+ "' AND flat_wing = '" + wing + "' AND outr_billtype = '" + billType
					+ "' ORDER BY flat_flatnum,flat_ownerid ";

			query = this.entityManager.createNativeQuery(sqlString, Tuple.class);
			List<Tuple> parkingTuple = query.getResultList();

			for (Tuple parkingList : parkingTuple) {
				parkingNo = parkingList.get(0, String.class);
				parkingOwnerId = parkingList.get(1, String.class);

				if (billType.equals("F")) {
					qtrEndyyyymmParking = initQtrEndDate(billDate, billType, parkingOwnerId);
					if (qtrEndyyyymmParking == "" || qtrEndyyyymmParking == "0") {
						continue;
					}
					query = this.entityManager.createNativeQuery("SELECT to_char(trunc(last_day(to_date('01/"
							+ qtrEndyyyymmParking.substring(4) + "/" + qtrEndyyyymmParking.substring(0, 4)
							+ "','dd/mm/yyyy'))),'dd/mm/yyyy') from dual ");
					qtrEndDateParking = String.valueOf(query.getSingleResult());
				} else {
					qtrEndDateParking = qtrEndDate;
					qtrEndyyyymmParking = qtrEndyyyymm;
				}
				parkingNos = parkingNos + ", " + parkingNo;

//				if (ownerId == parkingOwnerId) {
//				billNo = unitBillNo;
//				}
				billNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "OGBIL", "MUM");
				// Calculate Infra Amount
//				if (genInfraAmt == true) {
				calcAmtResponseBean = calcInfraAmt(hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner,
						parkingNo, parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc,
						invoiceNo, irnNo, sessionId, "");

//				ServiceResponseBeanBuilder calcAmtBeanBuilder = ServiceResponseBean.builder();
//				calcAmtBeanBuilder.toString();
//				
//				String[] jsonObject = JSONObject.getNames(calcAmtResponseBean.getBody());
//				JsonValue value = (JsonValue) jsonObject( "username");

				JSONObject objJsonObject = new JSONObject(calcAmtResponseBean.getBody().toString());
				System.out.println(objJsonObject.getString("status"));

				if (calcAmtResponseBean.getStatusCodeValue() < 0) {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}
//				}
				// Calculate Maintenance Amount

				status = calcMaintAmt(hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo,
						parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo,
						irnNo, sessionId, cGstPerc, sGstPerc, iGstPerc, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

				// Calculate Admin Charges Amount (Only for Normal Bill)
				if (billType.equals("N")) {
					status = calcAdminAmt(hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo,
							parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo,
							irnNo, sessionId, cGstPerc, sGstPerc, iGstPerc, "");

					if (status != "SUCCESS") {
						return ResponseEntity
								.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
					}
				}

				// Calculate Arrears
				status = calcArrearsAmt(hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo,
						parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo,
						irnNo, sessionId, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

				// Calculate Interest
				status = calcInterestAmt(hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo,
						parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo,
						irnNo, sessionId, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

				// Calculate Water
				status = calcWaterElecNaTaxAmt("WATE", "Water Charges (Service Code " + hsnCode + ")", "outr_water",
						hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo, parkingOwnerId, billNo,
						unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo, irnNo, sessionId, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

				// Calculate Elect
				status = calcWaterElecNaTaxAmt("ELEC", "Electricity Charges (Service Code " + hsnCode + ")",
						"outr_elect", hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo,
						parkingOwnerId, billNo, unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo,
						irnNo, sessionId, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

				// Calculate NA Tax
				status = calcWaterElecNaTaxAmt("NATA", "NA.TAX Charges (Service Code " + hsnCode + ")", "outr_natax",
						hsnCode, qtrEndDateParking, qtrEndyyyymmParking, flatOwner, parkingNo, parkingOwnerId, billNo,
						unitBillNo, billType, billMode, billDate, billModeDesc, invoiceNo, irnNo, sessionId, "");

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

			}

//			'--------------<<<<<<<<<  MAIN FLAT CALCULATION STARTS 
//            ' Find Out Bill Mode/Description for that particular Flatowner
			// Calculate Infra Amount
			if (genInfraAmt == true) {
//				status = calcInfraAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode,
//						billDate, billModeDesc, invoiceNo, irnNo, sessionId, parkingNos);
//
//				if (status != "SUCCESS") {
//					return ResponseEntity
//							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
//				}

				calcAmtResponseBean = calcInfraAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType,
						billMode, billDate, billModeDesc, invoiceNo, irnNo, sessionId, parkingNos);

				if (calcAmtResponseBean.getStatusCodeValue() < 0) {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}
			}
			// Calculate Maintenance Amount

			status = calcMaintAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode, billDate,
					billModeDesc, invoiceNo, irnNo, sessionId, cGstPerc, sGstPerc, iGstPerc, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

			// Calculate Admin Charges Amount (Only for Normal Bill)
			if (billType.equals("N")) {
				status = calcAdminAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode,
						billDate, billModeDesc, invoiceNo, irnNo, sessionId, cGstPerc, sGstPerc, iGstPerc, parkingNos);

				if (status != "SUCCESS") {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}
			}

			// Calculate Arrears
			status = calcArrearsAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode,
					billDate, billModeDesc, invoiceNo, irnNo, sessionId, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

			// Calculate Interest
			status = calcInterestAmt(hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode,
					billDate, billModeDesc, invoiceNo, irnNo, sessionId, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

			// Calculate Water
			status = calcWaterElecNaTaxAmt("WATE", "Water Charges (Service Code " + hsnCode + ")", "outr_water",
					hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode, billDate, billModeDesc,
					invoiceNo, irnNo, sessionId, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

			// Calculate Elect
			status = calcWaterElecNaTaxAmt("ELEC", "Electricity Charges (Service Code " + hsnCode + ")", "outr_elect",
					hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode, billDate, billModeDesc,
					invoiceNo, irnNo, sessionId, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

			// Calculate NA Tax
			status = calcWaterElecNaTaxAmt("NATA", "NA.TAX Charges (Service Code " + hsnCode + ")", "outr_natax",
					hsnCode, "", "", flatOwner, "", "", billNo, unitBillNo, billType, billMode, billDate, billModeDesc,
					invoiceNo, irnNo, sessionId, parkingNos);

			if (status != "SUCCESS") {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
			}

//			'UPDATES LIST OF PARKING FOR A UNIT

			if (parkingNos != "") {
				parkingNos = parkingNos.substring(2);
			}
			parkingNo = "";

		}

		logger.info("sess :: {} ", sessionId);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
				.message("Added successfully").build());
//		} else {
//			return ResponseEntity
//					.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
//
	}

	@Override
	public ResponseEntity<?> addIntoOGSummaryTempTables(OutgoingSummaryRequestBean outgoingSummaryRequestBean) {

		// Used in Outgoing Summary Report
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
		Query addIntoTempTableQuery;
		Integer rowCount;

		addIntoTempTableQuery = this.entityManager.createNativeQuery(
				"INSERT INTO saogrp04p( \r\n" + "sump_bldgcode,sump_billamt,sump_amtpaid,sump_amtos,sump_sessid) \r\n"
						+ "(select obill_bldgcode,sum(obill_billamt),sum(obill_amtpaid),sum(obill_amtos),:sessionId\r\n"
						+ "FROM outbill WHERE obill_month < :fromMonth GROUP BY	obill_bldgcode)\r\n");
		addIntoTempTableQuery.setParameter("sessionId", sessionId);
		addIntoTempTableQuery.setParameter("fromMonth", outgoingSummaryRequestBean.getFromMonth());
		logger.info("fromMonth :: ", outgoingSummaryRequestBean);
		logger.info("fromMonth :: ", outgoingSummaryRequestBean.getFromMonth());
		logger.info("QUERY :: {}", addIntoTempTableQuery);
		rowCount = addIntoTempTableQuery.executeUpdate();

		if (rowCount == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		addIntoTempTableQuery = this.entityManager.createNativeQuery("INSERT INTO saogrp04c( \r\n"
				+ "sumc_bldgcode,sumc_billamt,sumc_amtpaid,sumc_amtos,sumc_sessid) \r\n"
				+ "(select obill_bldgcode,sum(obill_billamt),sum(obill_amtpaid),sum(obill_amtos),:sessionId \r\n"
				+ "FROM outbill WHERE obill_month BETWEEN :fromMonth AND :toMonth GROUP BY obill_bldgcode)\r\n");
		addIntoTempTableQuery.setParameter("sessionId", sessionId);
		addIntoTempTableQuery.setParameter("fromMonth", outgoingSummaryRequestBean.getFromMonth());
		addIntoTempTableQuery.setParameter("toMonth", outgoingSummaryRequestBean.getToMonth());

		logger.info("QUERY :: {}", addIntoTempTableQuery);
		rowCount = addIntoTempTableQuery.executeUpdate();

		if (rowCount == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		addIntoTempTableQuery = this.entityManager.createNativeQuery(
				"INSERT INTO saogrp04f( \r\n" + "sumf_bldgcode,sumf_billamt,sumf_amtpaid,sumf_amtos,sumf_sessid) \r\n"
						+ "(select obill_bldgcode,sum(obill_billamt),sum(obill_amtpaid),sum(obill_amtos),:sessionId\r\n"
						+ "FROM outbill WHERE obill_month > :toMonth GROUP BY	obill_bldgcode)\r\n");
		addIntoTempTableQuery.setParameter("sessionId", sessionId);
		addIntoTempTableQuery.setParameter("toMonth", outgoingSummaryRequestBean.getToMonth());

		logger.info("QUERY :: {}", addIntoTempTableQuery);
		rowCount = addIntoTempTableQuery.executeUpdate();

		if (rowCount == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
				.message("Added successfully").build());

	}

//	@Override
//	public ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId) {
//		Query deleteBySessIdTempTableQuery;
//		deleteBySessIdTempTableQuery = this.entityManager
//				.createNativeQuery("Delete From saogrp04p where sump_sessid= :sessionId");
//		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
//		deleteBySessIdTempTableQuery.executeUpdate();
//
//		deleteBySessIdTempTableQuery = this.entityManager
//				.createNativeQuery("Delete From saogrp04c where sumc_sessid= :sessionId");
//		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
//		deleteBySessIdTempTableQuery.executeUpdate();
//
//		deleteBySessIdTempTableQuery = this.entityManager
//				.createNativeQuery("Delete From saogrp04f where sumf_sessid= :sessionId");
//		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
//		deleteBySessIdTempTableQuery.executeUpdate();
//
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
//	}

//	@Override
//	public ResponseEntity<?> deleteSocAccTempData(Integer sessionId) {
//		Query deleteBySessIdTempTableQuery;
//		deleteBySessIdTempTableQuery = this.entityManager
//				.createNativeQuery("Delete From saogrp06 where soc_sessid= :sessionId");
//		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
//		deleteBySessIdTempTableQuery.executeUpdate();
//
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
//	}

//	@Override
//	public ResponseEntity<?> deleteRowsFromTempTable(String tempTableName, Integer sessionId) {
//		Query deleteBySessIdTempTableQuery;
//		deleteBySessIdTempTableQuery = this.entityManager
//				.createNativeQuery("Delete From " + tempTableName + " where sump_sessid= :sessionId");
//		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
//		deleteBySessIdTempTableQuery.executeUpdate();
//
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
//	}

	@Override
	public ResponseEntity<?> addIntoOGDefaultersListTempTables(
			OutgoingDefaultersReportRequestBean outgoingDefaultersReportRequestBean) {

		// Used in Outgoing Defaulters List Report
		String uptoMonth, sqlString = "", flatOwners = "", bldgCode, wing, unitNo, cutOffDate, status;
		String[] owners;
		List<String> flatsList, parkingsList;
		Query query;
		Integer sessionId = 0;

		try {
//		month = outgoingDefaultersReportRequestBean.getCutOffDate().substring(3, 5);
//		year = outgoingDefaultersReportRequestBean.getCutOffDate().substring(6, 10);

			uptoMonth = outgoingDefaultersReportRequestBean.getCutOffDate().substring(6, 10)
					+ outgoingDefaultersReportRequestBean.getCutOffDate().substring(3, 5);

			flatOwners = outgoingDefaultersReportRequestBean.getFlatOwner();

//			flatOwners = flatOwners.concat(String.join(",", outgoingDefaultersReportRequestBean.getFlatOwner().stream()
//					.map(name -> ("'" + name + "'")).collect(Collectors.toList())));
			wing = outgoingDefaultersReportRequestBean.getWing();
			logger.info("flatOwners :: ", flatOwners);
			if (wing.equals("'ALL'")) {
				wing = "' '";
			}

			if (flatOwners.equals("'ALL'")) {
				String custTypeWhere = "";
				if (outgoingDefaultersReportRequestBean.getCustType().equals("ALL")) {
					custTypeWhere = " ";
				} else if (outgoingDefaultersReportRequestBean.getCustType().equals("C")) {
					custTypeWhere = " AND flat_custtype = 'C' ";
				} else if (outgoingDefaultersReportRequestBean.getCustType().equals("I")) {
					custTypeWhere = " AND flat_custtype in ('N','I') ";
				}

				query = this.entityManager.createNativeQuery("SELECT flat_ownerid FROM flats WHERE flat_bldgcode IN ("
						+ outgoingDefaultersReportRequestBean.getBldgCode() + ") AND flat_wing in (" + wing
						+ ") AND flat_soldyn='Y' AND flat_accomtype in ('O','F','S','M') " + custTypeWhere);
				flatsList = query.getResultList();
//				owners = new String[flatsList.size()];

			} else {

				query = this.entityManager
						.createNativeQuery("SELECT fown_ownerid FROM flatowner WHERE fown_ownerid IN (" + flatOwners
								+ ") AND fown_ownertype=0 ");
				flatsList = query.getResultList();

//				owners = new String[flatsList.size()];

			}

			if (flatsList.size() == 0) {
				return ResponseEntity
						.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("flatowner not found").build());
			}
			String osAmtCheck;
			Double osAmt;
			sessionId = Integer.parseInt(GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS"));
			cutOffDate = outgoingDefaultersReportRequestBean.getCutOffDate();
			osAmtCheck = outgoingDefaultersReportRequestBean.getOsAmtCheck();
			osAmt = outgoingDefaultersReportRequestBean.getOsAmount();

			for (String flatOwner : flatsList) {
//			whereCondition = "";
//			whereCondition = " out_bldg IN (" + outgoingDefaultersReportRequestBean.getBldgCode() + ") AND out_flatnum IN (" +

				bldgCode = flatOwner.substring(0, 4).trim();
				wing = flatOwner.substring(4, 5);
				unitNo = flatOwner.substring(5).trim();
				status = insertIntoOGDefaultersListTempTable(uptoMonth, unitNo, flatOwner, bldgCode, wing, sessionId,
						cutOffDate, osAmtCheck, osAmt);

				if (status.equals("SUCCESS")) {
				} else {
					return ResponseEntity
							.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
				}

//			List<Map<String, Object>> flatParkings = CommonResultsetGenerator
//					.queryToResultSetBuilder("SELECT flat_ownerid FROM flats WHERE flat_flatpark='" + unitNo
//							+ "' AND flat_soldyn='Y' and flat_accomtype in('P','C','R','B','G') "
//							+ "AND flat_bldgcode = '" + bldgCode + "' AND flat_wing = '" + wing + "' ");

				query = this.entityManager.createNativeQuery(
						"SELECT flat_flatnum,flat_ownerid FROM flats WHERE flat_flatpark='" + unitNo
								+ "' AND flat_soldyn='Y' and flat_accomtype in('P','C','R','B','G') "
								+ "AND flat_bldgcode = '" + bldgCode + "' AND flat_wing = '" + wing + "' ",
						Tuple.class);

				List<Tuple> parkingsTuple = query.getResultList();

				for (Tuple parkingList : parkingsTuple) {
					status = insertIntoOGDefaultersListTempTable(uptoMonth, parkingList.get(0, String.class),
							parkingList.get(1, String.class), bldgCode, wing, sessionId, cutOffDate, osAmtCheck, osAmt);

					if (status.equals("SUCCESS")) {
					} else {
						return ResponseEntity
								.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(status).build());
					}
				}
//			owners =  new String[parkingsList.size()];

			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
					.message("Added successfully").build());

		} catch (Exception e) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("FAILED for " + "     " + e.getMessage()).build());
		}
	}

	public String insertIntoOGDefaultersListTempTable(String uptoMonth, String unitNo, String ownerId, String bldgCode,
			String wing, Integer sessionId, String cutOffDate, String osAmtCheck, Double osAmt) {

		String fromMonth, cutFromDate, outgCoy, maxMonth, maxBillNo = "", maxBillDate = "", ogStartMonth, ogEndMonth,
				nextDayOfMaxBillDate, mainFlatNo;
		Double totBillAmt = 0.0, totBillAdminAmt = 0.0, totBillWaterAmt = 0.0, totBillElectAmt = 0.0,
				toBillNATaxAmt = 0.0, totBillServTaxAmt = 0.0, totBillSwachhCessAmt = 0.0, totBillKrishiAmt = 0.0,
				totBillCGstAmt = 0.0, totBillSGstAmt = 0.0, totBillIGstAmt = 0.0, lastBillAmt = 0.0,
				lastBillIntAmt = 0.0, lastBillArrearsAmt = 0.0, totArrears = 0.0, ogRate = 0.0, totOsAmt = 0.0,
				rectIntAmtAfterCutOffDate = 0.0, rectAmtAfterCutOffDate = 0.0, totRectAmt = 0.0, totRectAdminAmt = 0.0,
				totRectWaterAmt = 0.0, totRectElectAmt = 0.0, totRectNATaxAmt = 0.0, totRectServTaxAmt = 0.0,
				totRectSwachhCessAmt = 0.0, totRectKrishiAmt = 0.0, totRectCGstAmt = 0.0, totRectSGstAmt = 0.0,
				totRectIGstAmt = 0.0;
		List<Tuple> ogRates;
		Query query;

		try {

			if (uptoMonth.compareTo("200603") > 0) {
				fromMonth = "200604";
			} else {
				fromMonth = "000000";
			}

			cutFromDate = "01/04/2006";
			outgCoy = "KRHO";

			// Get Max BillMonth
			query = this.entityManager.createNativeQuery("SELECT Max(obill_month) FROM outbill WHERE obill_bldgcode = '"
					+ bldgCode + "' AND obill_ownerid = '" + ownerId + "' AND obill_month <= '" + uptoMonth
					+ "' AND obill_month >= '" + fromMonth + "' AND obill_billtype = 'N' ");
			maxMonth = String.valueOf(query.getSingleResult());

			if (maxMonth == "" || maxMonth.equals("null")) {
				// Get Min BillMonth
				query = this.entityManager.createNativeQuery("SELECT Min(obill_month) FROM outbill WHERE "
						+ "obill_bldgcode = '" + bldgCode + "' AND obill_ownerid = '" + ownerId
						+ "' AND Trunc(obill_billdate) = Trunc(to_date('01/" + uptoMonth.substring(4, 6) + "/"
						+ uptoMonth.substring(0, 4) + "','dd/mm/yyyy')) AND obill_month >= '" + fromMonth
						+ "' AND obill_billtype = 'N' ");
				maxMonth = String.valueOf(query.getSingleResult());
				if (maxMonth == "" || maxMonth.equals("null")) {
					maxMonth = uptoMonth;
				}
			}

			// Fetch Total Billed Amount
			query = this.entityManager
					.createNativeQuery("SELECT nvl(sum(nvl(obill_billamt,0) + nvl(obill_admincharges,0) + "
							+ "nvl(obill_water,0) +  nvl(obill_elect,0) + nvl(obill_natax,0) + "
							+ "nvl(obill_servicetax,0) + nvl(obill_swachhcess,0) + nvl(obill_krishicess,0) + "
							+ "nvl(obill_cgst,0) +  nvl(obill_sgst,0) + nvl(obill_igst,0)),0) as obillAmt, "
							+ "nvl(sum(nvl(obill_admincharges,0)),0) as obill_admincharges_sum, "
							+ "nvl(sum(nvl(obill_water,0)),0) as obill_water_sum,"
							+ "nvl(sum(nvl(obill_elect,0)),0) as obill_elect_sum,"
							+ "nvl(sum(nvl(obill_natax,0)),0) as obill_natax_sum,"
							+ "nvl(sum(nvl(obill_servicetax,0)),0) as obill_servicetax_sum, "
							+ "nvl(sum(nvl(obill_swachhcess,0)),0) as obill_swachhcess_sum,  "
							+ "nvl(sum(nvl(obill_krishicess,0)),0) as obill_krishicess_sum,  "
							+ "nvl(sum(nvl(obill_cgst,0)),0) as obill_cgst_sum,  "
							+ "nvl(sum(nvl(obill_sgst,0)),0) as obill_sgst_sum,  "
							+ "nvl(sum(nvl(obill_igst,0)),0) as obill_igst_sum FROM outbill WHERE obill_bldgcode ='"
							+ bldgCode + "' AND obill_ownerid = '" + ownerId + "' AND obill_billdate <= to_date('"
							+ cutOffDate + "','dd/mm/yyyy') AND obill_billdate >= to_date('" + cutFromDate
							+ "','dd/mm/yyyy') " + "AND obill_billtype = 'N'", Tuple.class);

			List<Tuple> totBillAmtTuple = query.getResultList();

			for (Tuple billAmtList : totBillAmtTuple) {
				totBillAmt = billAmtList.get("obillAmt", BigDecimal.class).doubleValue();
				totBillAdminAmt = billAmtList.get("obill_admincharges_sum", BigDecimal.class).doubleValue();
				totBillWaterAmt = billAmtList.get("obill_water_sum", BigDecimal.class).doubleValue();
				totBillElectAmt = billAmtList.get("obill_elect_sum", BigDecimal.class).doubleValue();
				toBillNATaxAmt = billAmtList.get("obill_natax_sum", BigDecimal.class).doubleValue();
				totBillServTaxAmt = billAmtList.get("obill_servicetax_sum", BigDecimal.class).doubleValue();
				totBillSwachhCessAmt = billAmtList.get("obill_swachhcess_sum", BigDecimal.class).doubleValue();
				totBillKrishiAmt = billAmtList.get("obill_krishicess_sum", BigDecimal.class).doubleValue();
				totBillCGstAmt = billAmtList.get("obill_cgst_sum", BigDecimal.class).doubleValue();
				totBillSGstAmt = billAmtList.get("obill_sgst_sum", BigDecimal.class).doubleValue();
				totBillIGstAmt = billAmtList.get("obill_igst_sum", BigDecimal.class).doubleValue();
			}

			// Fetch Max Bill No & Bill Date For the Owner ID
			query = this.entityManager.createNativeQuery(
					"SELECT obill_billnum , to_Char(obill_billdate,'dd/mm/yyyy') AS billDate FROM outbill WHERE obill_bldgcode ='"
							+ bldgCode + "' AND obill_ownerid = '" + ownerId + "' AND obill_month = '" + maxMonth
							+ "' AND obill_billtype = 'N' ",
					Tuple.class);

			List<Tuple> maxBillDataTuple = query.getResultList();

			for (Tuple billDataList : maxBillDataTuple) {
				maxBillNo = billDataList.get("obill_billnum", String.class);
				maxBillDate = billDataList.get("billDate", String.class);
			}

			if (maxBillDate == "") {
				maxBillDate = cutFromDate;
			}

			if (maxBillNo == "") {
				lastBillAmt = 0.0;
				lastBillIntAmt = 0.0;
				lastBillArrearsAmt = 0.0;
				totArrears = 0.0;
			} else {
				// Fetch last Bill's amount details
				query = this.entityManager.createNativeQuery(
						"SELECT nvl(sum(obill_billamt + nvl(obill_admincharges,0) + nvl(obill_water,0) + "
								+ "nvl(obill_elect,0) + nvl(obill_natax,0) + nvl(obill_servicetax,0) + nvl(obill_swachhcess,0) + "
								+ "nvl(obill_krishicess,0) + nvl(obill_cgst,0) + nvl(obill_sgst,0) + nvl(obill_igst,0)),0) as billAmt, "
								+ "nvl(sum(obill_arrears),0) as billArrears, nvl(sum(obill_interest),0) as billInterest "
								+ "FROM outbill WHERE obill_bldgcode ='" + bldgCode + "' AND obill_ownerid = '"
								+ ownerId + "' AND obill_billnum = '" + maxBillNo + "' AND obill_billtype = 'N' ",
						Tuple.class);

				List<Tuple> lastBillAmtDataTuple = query.getResultList();

				for (Tuple lastbillAmtDataList : lastBillAmtDataTuple) {
					lastBillAmt = lastbillAmtDataList.get("billAmt", BigDecimal.class).doubleValue();
					lastBillIntAmt = lastbillAmtDataList.get("billInterest", BigDecimal.class).doubleValue();
					lastBillArrearsAmt = lastbillAmtDataList.get("billArrears", BigDecimal.class).doubleValue();
					totArrears = totArrears + lastBillArrearsAmt;
				}
			}

			ogStartMonth = maxBillDate.substring(6, 10) + maxBillDate.substring(3, 5);
			ogEndMonth = cutOffDate.substring(6, 10) + cutOffDate.substring(3, 5);

			// Fetch Outgoing rates
			query = this.entityManager.createNativeQuery(
					"SELECT nvl(outr_maint,0) +  nvl(outr_water,0) + nvl(outr_elect,0) + nvl(outr_natax,0) + nvl(outr_proprate,0) AS OgRate "
							+ "FROM outrate WHERE outr_bldgcode ='" + bldgCode + "' AND outr_wing = '" + wing
							+ "' AND outr_flatnum = '" + unitNo + "' AND outr_startdate <= '" + ogEndMonth + "' "
							+ "AND outr_enddate >= '" + ogStartMonth + "' AND outr_billtype = 'N' ",
					Tuple.class);
//			String ogratetest;

			ogRates = query.getResultList();
			if (ogRates.isEmpty()) {
				ogRate = 0.0;
			} else {
				for (Tuple getOgRates : ogRates) {
					ogRate = getOgRates.get("OgRate", BigDecimal.class).doubleValue();
				}
			}

//			ogRate = Double.parseDouble(String.valueOf(query.getSingleResult()));
			nextDayOfMaxBillDate = CommonUtils.INSTANCE.addDaysInString(maxBillDate, BigInteger.ONE.intValue());

			// Find Out Sum Of Receipt Amount For Particular Owner id Between Last Bill and
			// cut off data
			query = this.entityManager.createNativeQuery(
					"SELECT nvl(sum(nvl(out_amtpaid,0) + nvl(out_servtax,0) + nvl(out_swachhcess,0) + nvl(out_krishicess,0) + "
							+ "nvl(out_admincharges,0)+nvl(out_propertytax,0) + nvl(out_water,0)+ nvl(out_elect,0) + "
							+ "nvl(out_natax,0) + nvl(out_cgst,0)+nvl(out_sgst,0) + nvl(out_igst,0)),0) as amtpaid, "
							+ "nvl(sum(nvl(out_amtint,0)),0) as AmtInt FROM outcoll WHERE out_ownerid ='" + ownerId
							+ "' AND out_recdate between to_date('" + nextDayOfMaxBillDate
							+ "','dd/mm/yyyy') AND to_date('" + cutOffDate
							+ "','dd/mm/yyyy') AND out_cancelledyn='N' AND out_coy = '" + outgCoy
							+ "' AND out_rectype = 'N' ",
					Tuple.class);

			List<Tuple> totRectAmtTuple = query.getResultList();

			for (Tuple totRectAmtDataList : totRectAmtTuple) {
				rectAmtAfterCutOffDate = totRectAmtDataList.get("amtpaid", BigDecimal.class).doubleValue();
				rectIntAmtAfterCutOffDate = totRectAmtDataList.get("AmtInt", BigDecimal.class).doubleValue();
			}

			totRectAmtTuple.clear();

			totArrears = totArrears - rectAmtAfterCutOffDate + lastBillAmt;
			lastBillIntAmt = lastBillIntAmt - rectIntAmtAfterCutOffDate;
			totOsAmt = totArrears + lastBillIntAmt;

			query = this.entityManager.createNativeQuery(
					"SELECT nvl(sum(nvl(out_amtpaid,0) + nvl(out_admincharges,0)+nvl(out_propertytax,0) + "
							+ "nvl(out_water,0) + nvl(out_elect,0)+nvl(out_natax,0)+ nvl(out_servtax,0) + nvl(out_swachhcess,0) + "
							+ "nvl(out_krishicess,0) + nvl(out_cgst,0) + nvl(out_sgst,0) + nvl(out_igst,0) ),0) as amtpaid, "
							+ "nvl(sum(nvl(out_admincharges,0)),0) as Admincharges, nvl(sum(nvl(out_water,0)),0) as water, "
							+ "nvl(sum(nvl(out_elect,0)),0) as elect, nvl(sum(nvl(out_natax,0)),0) as natax, "
							+ "nvl(sum(nvl(out_servtax,0)),0) as serviceTax, nvl(sum(nvl(out_swachhcess,0)),0) as swachhcess, "
							+ "nvl(sum(nvl(out_krishicess,0)),0) as krishicess, nvl(sum(nvl(out_cgst,0)),0) as cgst, "
							+ "nvl(sum(nvl(out_sgst,0)),0) as sgst, nvl(sum(nvl(out_igst,0)),0) as igst "
							+ "FROM outcoll WHERE out_ownerid ='" + ownerId + "' " + "AND out_recdate <= to_date('"
							+ cutOffDate + "','dd/mm/yyyy') " + "AND out_cancelledyn='N' AND out_coy = '" + outgCoy
							+ "' AND out_rectype = 'N' ",
					Tuple.class);

			totRectAmtTuple = query.getResultList();

			for (Tuple totRectAmtDataList : totRectAmtTuple) {
				totRectAmt = totRectAmtDataList.get("amtpaid", BigDecimal.class).doubleValue();
				totRectAdminAmt = totRectAmtDataList.get("Admincharges", BigDecimal.class).doubleValue();
				totRectWaterAmt = totRectAmtDataList.get("water", BigDecimal.class).doubleValue();
				totRectElectAmt = totRectAmtDataList.get("elect", BigDecimal.class).doubleValue();
				totRectNATaxAmt = totRectAmtDataList.get("natax", BigDecimal.class).doubleValue();
				totRectServTaxAmt = totRectAmtDataList.get("serviceTax", BigDecimal.class).doubleValue();
				totRectSwachhCessAmt = totRectAmtDataList.get("swachhcess", BigDecimal.class).doubleValue();
				totRectKrishiAmt = totRectAmtDataList.get("krishicess", BigDecimal.class).doubleValue();
				totRectCGstAmt = totRectAmtDataList.get("cgst", BigDecimal.class).doubleValue();
				totRectSGstAmt = totRectAmtDataList.get("sgst", BigDecimal.class).doubleValue();
				totRectIGstAmt = totRectAmtDataList.get("igst", BigDecimal.class).doubleValue();
			}

//			// Regexp for Parking Nos
//			String[] parkingCheck = { "B", "C", "ST", "P", "R", "M", "L", "UB" };
//			StringBuilder regexp = new StringBuilder();
//			Pattern pattern = Pattern.compile(regexp.toString());
//
//			for (String word : parkingCheck) {
//				regexp.append("(?=.*").append(word).append(")");
//			}
			if ((osAmtCheck.equals("true") && totOsAmt > osAmt) || osAmtCheck.equals("false")) {
//				if (pattern.matcher(unitNo).find()) { // Processing for Parking Unit
				if (unitNo.substring(0, 1).equals("B") || unitNo.substring(0, 1).equals("C")
						|| unitNo.substring(0, 2).equals("ST") || unitNo.substring(0, 1).equals("P")
						|| unitNo.substring(0, 1).equals("R") || unitNo.substring(0, 1).equals("M")
						|| unitNo.substring(0, 1).equals("L") || unitNo.substring(0, 2).equals("UB")) {
					// Fetch Flat No for Parking
					query = this.entityManager
							.createNativeQuery("SELECT flat_flatpark FROM flats WHERE flat_bldgcode ='" + bldgCode
									+ "' AND flat_flatnum = '" + unitNo + "' AND flat_wing = '" + wing + "' ");

					mainFlatNo = String.valueOf(query.getSingleResult());
				} else {
					mainFlatNo = unitNo;
				}

				query = this.entityManager.createNativeQuery("INSERT INTO saogrp05 ( "
						+ "sum_ownerid, sum_bldgcode, sum_wing, sum_flatnum, sum_flatpark, sum_month, sum_monrate, "
						+ "sum_amtrec, sum_amtos, sum_sesid, sum_totbill, sum_obill_admincharges, sum_obill_servicetax, "
						+ "sum_obill_swachhcess, sum_obill_krishicess, sum_totrec, sum_rec_admincharges, sum_rec_servicetax, "
						+ "sum_rec_swachhcess, sum_rec_krishicess, sum_rec_cgst, sum_rec_sgst, sum_rec_igst, sum_rec_water, "
						+ "sum_rec_elect, sum_rec_natax, sum_today, sum_userid) values ('" + ownerId + "' , '"
						+ bldgCode + "' , '" + wing + "' , '" + mainFlatNo + "' , '" + unitNo + "' , '" + maxMonth
						+ "' , " + ogRate.toString() + " , " + totArrears.toString() + " , " + lastBillIntAmt.toString()
						+ " , " + sessionId + " , " + totBillAmt.toString() + " , " + totBillAdminAmt.toString() + " , "
						+ totBillServTaxAmt.toString() + " , " + totBillSwachhCessAmt.toString() + " , "
						+ totBillKrishiAmt.toString() + " , " + totRectAmt.toString() + " , "
						+ totRectAdminAmt.toString() + " , " + totRectServTaxAmt.toString() + " , "
						+ totRectSwachhCessAmt.toString() + " , " + totRectKrishiAmt.toString() + " , "
						+ totRectCGstAmt.toString() + " , " + totRectSGstAmt + " , " + totRectIGstAmt.toString() + " , "
						+ totRectWaterAmt.toString() + " , " + totRectElectAmt.toString() + " , "
						+ totRectNATaxAmt.toString() + " , " + "sysdate , '"
						+ GenericAuditContextHolder.getContext().getUserid() + "' )");
				Integer rowCount = query.executeUpdate();
				if (rowCount == 0) {
					return "FAILED while inserting into temp table" + ownerId;
				}

			}
		} catch (Exception e) {
			return "FAILED for " + ownerId + "    " + e.getMessage();
		}
		return "SUCCESS";

	}

	@Override
	public ResponseEntity<?> processSocietyAccountsReport(
			OutgoingSocietyAccountsReportRequestBean outgoingSocietyAccountsReportRequestBean) {
		String sessionId;
		String proprietor, coy, dateUpto, acStartYear, currPeriod, acbalWhereClause = "", actranWherClause = "",
				acMajorList;
		Query query;
		try {
			proprietor = outgoingSocietyAccountsReportRequestBean.getProprietor();
			coy = outgoingSocietyAccountsReportRequestBean.getCompany();
			dateUpto = outgoingSocietyAccountsReportRequestBean.getDateUpto();

			query = this.entityManager
					.createNativeQuery("SELECT Max(substr(acbal_acyear,1,4)) FROM acbal WHERE acbal_proprietor = '"
							+ proprietor + "' " + "AND acbal_company = '" + coy
							+ "' AND acbal_baltype = 'Y' AND substr(acbal_acyear,1,4) <= substr(getacyear(to_date('"
							+ dateUpto + "','dd/mm/yyyy')),1,4) ");
			acStartYear = String.valueOf(query.getSingleResult());
			currPeriod = acStartYear + "-" + (Integer.parseInt(acStartYear) + 1);
			acMajorList = "'11401233','20404517','11401341','11401989','11401338'";

			acbalWhereClause = "acbal_acyear = '" + currPeriod + "' AND acbal_baltype = 'Y' ";
			actranWherClause = "actd_transer = acth_transer AND actd_coy = acth_coy AND acth_postedyn = 'Y' AND actd_tranamt <> 0 ";

			if (proprietor != "") {
				acbalWhereClause = acbalWhereClause + "AND acbal_proprietor = '" + proprietor + "' ";
				actranWherClause = actranWherClause + "AND acth_proprietor = '" + proprietor + "' ";
			}

			if (coy != "") {
				acbalWhereClause = acbalWhereClause + "AND acbal_company = '" + coy + "' ";
				actranWherClause = actranWherClause + "AND acth_coy = '" + coy + "' ";
			}

			acbalWhereClause = acbalWhereClause + "AND acbal_acmajor IN (" + acMajorList + ") AND acbal_balance <> 0 ";

			actranWherClause = actranWherClause + "AND Trunc(acth_trandate) <= to_date('" + dateUpto
					+ "','dd/mm/yyyy') " + "AND Trunc(acth_trandate) >=  to_date('01/04/" + acStartYear
					+ "','dd/mm/yyyy') " + "AND actd_acmajor IN (" + acMajorList + ") ";

			sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

			query = this.entityManager.createNativeQuery("INSERT INTO saogrp06 ( "
					+ "soc_sessid	,	soc_bldgcode	,	soc_outgamt		, soc_mantamt	, "
					+ "soc_inframt	,	soc_infrcoll	,	soc_sharamt		, soc_otheramt	, "
					+ "soc_payment	, 	soc_site		, 	soc_userid		, soc_today		, soc_auxicoll, soc_sinkcoll ) 		"
					+ "( SELECT " + sessionId.toString()
					+ ",bldg , sum(soc_outgamt) , sum(soc_mantamt) , sum(soc_inframt) , sum(soc_infrcoll) , sum(soc_sharamt) , "
					+ "sum(soc_otheramt) , sum(soc_payment) , '" + GenericAuditContextHolder.getContext().getSite()
					+ "','" + GenericAuditContextHolder.getContext().getUserid()
					+ "' , sysdate , sum(soc_auxicoll) , sum(soc_sinkcoll) FROM ("
					+ "SELECT Substr(acbal_acminor,1,4) AS Bldg , "
					+ "sum( CASE WHEN ((acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('OUTG','PROP','NATX','TRFE')) OR acbal_acmajor = '11401341') "
					+ "THEN acbal_balance ELSE 0 END ) AS soc_outgamt , "
					+ "sum( CASE WHEN ((acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('SOCI','MANT')) OR acbal_acmajor = '11401989') "
					+ "THEN acbal_balance ELSE 0 END ) AS soc_mantamt , "
					+ "sum( CASE WHEN ((acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('APEX','INFR')) OR acbal_acmajor = '11401338') "
					+ "THEN acbal_balance ELSE 0 END ) AS soc_inframt , "
					+ "sum( CASE WHEN (acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('INAP')) THEN acbal_balance ELSE 0 END ) AS soc_infrcoll , "
					+ "sum( CASE WHEN (acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('SHAY')) THEN acbal_balance ELSE 0 END ) AS soc_sharamt , "
					+ "sum( CASE WHEN (acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) NOT IN "
					+ "('OUTG','PROP','NATX','TRFE','SOCI','MANT','APEX','INFR','INAP','SHAY','AUXI','SINK','COST')) THEN acbal_balance ELSE 0 END ) AS soc_otheramt , "
					+ "sum( CASE WHEN acbal_acmajor = '20404517' THEN acbal_balance ELSE 0 END ) AS soc_payment , "
					+ "sum( CASE WHEN (acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('AUXI')) THEN acbal_balance ELSE 0 END ) AS soc_auxicoll , "
					+ "sum( CASE WHEN (acbal_acmajor = '11401233' AND Substr(acbal_acminor,6,4) IN ('SINK')) THEN acbal_balance ELSE 0 END ) AS soc_sinkcoll  "
					+ "	FROM acbal " + " 	WHERE " + acbalWhereClause + " GROUP BY substr(acbal_acminor,1,4)  "
					+ "UNION ALL " + "SELECT nvl(Substr(actd_acminor,1,4),' ') as Bldg , "
					+ "sum( CASE WHEN ((actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('OUTG','PROP','NATX','TRFE')) OR actd_acmajor = '11401341') "
					+ "THEN actd_tranamt ELSE 0 END ) AS soc_outgamt , "
					+ "sum( CASE WHEN ((actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('SOCI','MANT')) OR actd_acmajor = '11401989') "
					+ "THEN actd_tranamt ELSE 0 END ) AS soc_mantamt , "
					+ "sum( CASE WHEN ((actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('APEX','INFR')) OR actd_acmajor = '11401338') "
					+ "THEN actd_tranamt ELSE 0 END ) AS soc_inframt , "
					+ "sum( CASE WHEN (actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('INAP')) THEN actd_tranamt ELSE 0 END ) AS soc_infrcoll , "
					+ "sum( CASE WHEN (actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('SHAY')) THEN actd_tranamt ELSE 0 END ) AS soc_sharamt , "
					+ "sum( CASE WHEN (actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) NOT IN "
					+ "('OUTG','PROP','NATX','TRFE','SOCI','MANT','APEX','INFR','INAP','SHAY','AUXI','SINK','COST')) THEN actd_tranamt ELSE 0 END ) AS soc_otheramt ,"
					+ "sum( CASE WHEN actd_acmajor = '20404517' THEN actd_tranamt ELSE 0 END ) AS soc_payment , "
					+ "sum( CASE WHEN (actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('AUXI')) THEN actd_tranamt ELSE 0 END ) AS soc_auxicoll , "
					+ "sum( CASE WHEN (actd_acmajor = '11401233' AND Substr(actd_acminor,6,4) IN ('SINK')) THEN actd_tranamt ELSE 0 END ) AS soc_sinkcoll "
					+ "FROM actrand,actranh WHERE " + actranWherClause + " GROUP BY Substr(actd_acminor,1,4) "
					+ ") GROUP BY bldg HAVING sum(soc_outgamt) + sum(soc_mantamt) + sum(soc_inframt) + sum(soc_infrcoll) + "
					+ "sum(soc_sharamt) + sum(soc_otheramt) + sum(soc_payment) + sum(soc_auxicoll) + sum(soc_sinkcoll) <> 0 ) ");

			Integer rowCount = query.executeUpdate();

//			query = this.entityManager.createNativeQuery("INSERT INTO saogrp06a ( "
//					+ " gl_prop,            gl_coy,             gl_transer,     "
//					+ "	gl_acmajor,         gl_acminor,         gl_tranamt,     "
//					+ "	gl_sessid,          gl_site,            gl_userid,      "
//					+ "  gl_rowid,           gl_trandate,        gl_today) 		" + "( SELECT "
//					+ "  acbal_proprietor,   acbal_company,      'zzzzzzzzzz', 	"
//					+ "	acbal_acmajor, 	    nvl(acbal_acminor,' ') as acbal_acminor,  acbal_balance,"
//					+ sessionId.toString() + ",'" + GenericAuditContextHolder.getContext().getSite() + "','"
//					+ GenericAuditContextHolder.getContext().getUserid() + "' , " + "	ROWNUM + 1, sysdate, sysdate "
//					+ "	FROM acbal " + " 	WHERE " + acbalWhereClause + " ) ");
//
//			Integer rowCount = query.executeUpdate();
//
//			query = this.entityManager
//					.createNativeQuery("SELECT Nvl(MAX(gl_rowid),0) FROM saogrp06a WHERE gl_sessid = " + sessionId);
//			rowCount = Integer.parseInt(String.valueOf(query.getSingleResult()));
//
//			query = this.entityManager.createNativeQuery("INSERT INTO saogrp06a ("
//					+ " gl_prop,     gl_coy,     gl_transer, " + " gl_acmajor,   gl_acminor, gl_tranamt, "
//					+ " gl_sessid,    gl_site,    gl_userid,  " + " gl_rowid,     gl_trandate,gl_today) " + " ( "
//					+ " SELECT " + " acth_proprietor,  acth_coy,   acth_transer,"
//					+ " actd_acmajor, nvl(actd_acminor,' ') as actd_acminor ,actd_tranamt," + sessionId + ", '"
//					+ GenericAuditContextHolder.getContext().getSite() + "','"
//					+ GenericAuditContextHolder.getContext().getUserid() + "' , ROWNUM + " + rowCount.toString()
//					+ ",acth_trandate, sysdate FROM actrand,actranh " + "WHERE " + actranWherClause + " ) ");
//
//			rowCount = query.executeUpdate();
//			if (rowCount == 0) {
//
//			}
//
//			query = this.entityManager.createNativeQuery(
//					"SELECT gl_acmajor,   substr(gl_acminor,6,4) as Category,     substr(gl_acminor,1,4)as Bldgcode, sum(gl_tranamt) as Amount "
//							+ "FROM saogrp06a WHERE gl_sessid = " + sessionId.toString() + " AND gl_prop = '"
//							+ proprietor + "' AND gl_coy = '" + coy + "' "
//							+ "GROUP BY gl_acmajor, substr(gl_acminor,1,4), substr (gl_acminor,6,4) ORDER BY substr(gl_acminor,1,4), substr(gl_acminor,6,4) ");
//
//			String acMajor, category, bldgCode;
//			Double tranAmt;
//
////			List<Tuple> tempRowsTuple = query.getResultList();
////			for (Tuple tempRowList : tempRowsTuple) {
////				acMajor = tempRowList.get("gl_acmajor", String.class);
////				category = tempRowList.get("Category", String.class);
////				bldgCode = tempRowList.get("Bldgcode", String.class);
////				tranAmt = tempRowList.get("Amount", BigDecimal.class).doubleValue();
////
////			}
//
//			List<Object[]> arr1 = query.getResultList();
//			List<List<Object>> tempRowList1 = new ArrayList<List<Object>>();
//
////			Object[] tempRowList1;
//			for (Object[] tempRowList : arr1) {
//				acMajor = tempRowList[0].toString();
//				category = tempRowList[1].toString();
//				bldgCode = tempRowList[2].toString();
//				tranAmt = Double.valueOf(tempRowList[3].toString());
////				tempRowList.set(2, "Y");
//				tempRowList1.add(Arrays.asList(tempRowList));
////				tempRowList[2] = "Y";
//				tempRowList[1] = tempRowList[1];
//				tempRowList1.get(0).get(0);
////				tempRowList1.set(2, new Object["Y"]);
//			}

		} catch (Exception e) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message("FAILED for " + "     " + e.getMessage()).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId)
				.message("Added successfully").build());
	}
}
