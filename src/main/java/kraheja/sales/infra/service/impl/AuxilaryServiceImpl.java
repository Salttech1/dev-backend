package kraheja.sales.infra.service.impl;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.repository.HsnsacmasterRepository;
import kraheja.constant.ApiResponseMessage;
import kraheja.exception.InternalServerError;
import kraheja.exception.OutRateRateException;
import kraheja.sales.bean.entitiesresponse.Balance;
import kraheja.sales.bean.request.AuxilaryRequest;
import kraheja.sales.bean.response.AuxilaryResponse;
import kraheja.sales.bean.response.GridResponse;
import kraheja.sales.infra.service.AuxilaryService;
import kraheja.sales.repository.OutinfraRepository;
import kraheja.sales.repository.OutrateRepository;
import kraheja.utility.DateUtill;

@Service
public class AuxilaryServiceImpl implements AuxilaryService {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private HsnsacmasterRepository hsnsacmasterRepository;
	@Autowired
	private OutinfraRepository outinfraRepository;
	@Autowired
	private OutrateRepository outrateRepository;

	@Override
	public AuxilaryResponse getGridData(AuxilaryRequest request) {
		String strStartDate = "";
		String flatNumber = request.getFlatNum().trim();
		String ownerId = "";
		if (!request.getWing().equals(" ")) {
			String wing = request.getWing().trim();
			request.setWing(wing);
		}
		
		try {
			/**
			 * check here the type of service and type of bill set from here the query.
			 */
			double receiptAmt = Double.parseDouble(request.getTotalAmt());
			ownerId = request.getBuildingCode() + request.getWing() + flatNumber;
			if (request.getWing().equals(" ")) {
				ownerId = request.getBuildingCode() + " " + flatNumber;
			}else {
				request.setWing(request.getWing().trim());
			}
			strStartDate = startYearMonthFromDatabase(request.getBuildingCode(), request.getWing(),
					flatNumber, request.getBillType(), request.getChargeCode());
			log.debug("strStartDate : {}", strStartDate);

			
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String sysTime = " " + currentTime.format(formatter);
			String auxiDate = request.getDate() + sysTime;
			
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		    LocalDateTime date = LocalDateTime.parse(auxiDate, inputFormatter);
		    request.setInputDate(date);
		    
			String strDate = DateUtill.dateFormatter(date);
			request.setDate(strDate);
//			if (strStartDate.equals("201707")) {
//				strStartDate = DateUtill.startYearMonthFromInput(request.getDate());
//			}

			double prevAmtintPaid = 0;
			double prevAdminPaid = 0;
			double prevCGSTtaxPaid = 0;
			double prevSGSTtaxPaid = 0;
			double prevIGSTtaxPaid = 0;
			double prevTDStaxPaid = 0;
			List<GridResponse> respone = null;
			List<Balance> balanceList = outinfraRepository.findPreviousBalance(ownerId, strStartDate,request.getChargeCode(), request.getBillType());
			
			if (!balanceList.isEmpty()) {
				Balance balance = balanceList.get(balanceList.size() - 1);
				prevTDStaxPaid = Math.abs(balance.getTds());
				request.setDate(balance.getMonth());
				double amount = balance.getCgst() + balance.getSgst() + balance.getIgst()
						+ balance.getAdminharges() + balance.getAmtint() + balance.getAmtPaid();
				
				receiptAmt = receiptAmt + amount;
				log.debug("receiptAmt after fetched last balance: {}", receiptAmt);

				 respone = taxCalculation(receiptAmt, request, prevAmtintPaid, prevAdminPaid, prevCGSTtaxPaid,
						prevSGSTtaxPaid, prevIGSTtaxPaid, prevTDStaxPaid, strStartDate);
				 
				 
				 // CALCULATE LAST MONTH PAID AMOUNT AND REMAINING AMOUNT 
				 GridResponse gridResponse1 = respone.get(1); 
				 GridResponse gridResponse0 = respone.get(0);
				 gridResponse0.setAdmin(gridResponse1.getAdmin() - balance.getAdminharges());
				 gridResponse0.setAuxiPaid(gridResponse1.getAuxiPaid() - balance.getAmtPaid());
				 gridResponse0.setCgst(gridResponse1.getCgst() - balance.getCgst());
				 gridResponse0.setSgst(gridResponse1.getSgst() - balance.getSgst());
				 gridResponse0.setIgst(gridResponse1.getIgst() - balance.getIgst());
				 gridResponse0.setTds(prevCGSTtaxPaid); 
				 respone.set(0, gridResponse0);
				 if (respone.get(0).getAuxiPaid() < 1 && respone.get(0).getAdmin() < 1 && respone.get(0).getCgst() < 1) {
					 respone.remove(0);
					 request.setDate(gridResponse1.getMonthName());
				}
				log.debug("respone : {}", respone);
			}else {
//				if (request.getBillType().equals("F")) {
//					request.setDate(DateUtill.dateFormatterLocalDateToYYYYMM(request.getInputDate().toLocalDate()));
//				}else {
//					request.setDate(strStartDate);
//				}
				request.setDate(strStartDate);
				respone = taxCalculation(receiptAmt, request, prevAmtintPaid, prevAdminPaid, prevCGSTtaxPaid,
						prevSGSTtaxPaid, prevIGSTtaxPaid, prevTDStaxPaid, strStartDate);
				log.debug("respone : {}", respone);
			}
			
			String totalMonthCount = String.valueOf(respone.size());
			String startMonth = request.getDate();
			String endMonth = DateUtill.endMonth((respone.size() - 1) + Integer.parseInt(request.getDate()));
			if ("F".equals(request.getBillType())) {
//				startMonth = DateUtill.increaseMonth(startMonth);
				endMonth = respone.get(respone.size()-1).getMonthName();
			}

			return AuxilaryResponse.builder().result("success").responseCode("00").message("successfully fetch.")
					.startMonth(startMonth).endMonth(endMonth).totalMonth(totalMonthCount).data(respone).build();

		} catch (NullPointerException e) {
//			log.debug("exception occured : {}", e.getMessage());
			throw new InternalServerError(ApiResponseMessage.CHECK_ADMIN_OR_MAINTANACE_RATE_ZERO);
		} catch (Exception e) {
//		log.debug("exception occured : {}", e.getMessage());
			throw new InternalServerError(e.getMessage());
		}
	}

	// use this method to fetch start date
	// TODO add if condition for AUXI and INAP
	private String startYearMonthFromDatabase(String buildingCode, String wing, String flatNum, String billType, String chargeCode) {
		String fetchStartDate = "";
		if (wing.equals(" ")) {
			if (chargeCode.equals("INAP")) {
				fetchStartDate = outrateRepository.fetchInapFirstStartDate(buildingCode, flatNum, billType);
			}else {
				fetchStartDate = outrateRepository.fetchInapFirstStartDate(buildingCode, flatNum, billType);
			}
		}else {
			if (chargeCode.equals("INAP")) {
				if (billType.equals("N"))
						fetchStartDate = outrateRepository.fetchInapNormalStartDate(buildingCode, flatNum, wing, billType);
				else 
					fetchStartDate = outrateRepository.fetchInapFirstStartDate(buildingCode, flatNum, wing, billType);
				
			}else {
				if (billType.equals("N"))
					fetchStartDate = outrateRepository.fetchAuxiNormalStartDate(buildingCode, flatNum, wing, billType);
				else
					fetchStartDate = outrateRepository.fetchAuxiFirstStartDate(buildingCode, flatNum, wing, billType);
			}
		}
	
		if (!StringUtils.isNotEmpty(fetchStartDate)) {
			fetchStartDate = "201707";
		}
		return fetchStartDate;
	}

	public List<GridResponse> taxCalculation(double amount, AuxilaryRequest request, double prevAmtintPaid, double prevAdminPaid,
			double prevCGSTtaxPaid, double prevSGSTtaxPaid, double prevIGSTtaxPaid, double prevTDStaxPaid, String strStartDate) {
		List<GridResponse> insertRowList = new ArrayList<>();

		String buildingCode = request.getBuildingCode().trim();
		String wing = request.getWing();
		String flatNum = request.getFlatNum().trim();
		String date = request.getDate().trim();
		String billType = request.getBillType();
		String narration = request.getNarration();
//		double amount = Double.parseDouble(request.getTotalAmt());
		double receiptTds = Double.parseDouble(request.getReceiptAmtTds());
		
		double adminRate = 0,maintRate=0, tdsRate = 0; 
		/**
		 * CHECK CONDITION MONTH INFRA AND AUXI FOR THE OUTRATE RATE
		 */
		try {
			if (wing.equals(" ")) {
				if (request.getChargeCode().equals("INAP")) {
					String adminRateForInfrEmptyWing = outrateRepository.findAdminRateForInfrEmptyWing(buildingCode, flatNum, billType, strStartDate);
					String findAuxiRateForInfrEmptyWing = outrateRepository.findAuxiRateForInfrEmptyWing(buildingCode, flatNum, billType, strStartDate);
					adminRate = Double.valueOf(adminRateForInfrEmptyWing);
					maintRate = Double.valueOf(findAuxiRateForInfrEmptyWing);
				} else {
					adminRate = outrateRepository.findAdminRateForEmptyWing(buildingCode, flatNum, billType, strStartDate);
					maintRate = Double.valueOf(outrateRepository.findAuxiRateForEmptyWing(buildingCode, flatNum, billType, strStartDate));
				}
				tdsRate = outrateRepository.findTdsRateForEmptyWingMonthWise(buildingCode, flatNum, billType, strStartDate);
			}else {
				if (request.getChargeCode().equals("INAP")) {
					adminRate = outrateRepository.findAdminRateMonthWiseForInfra(buildingCode, wing, flatNum, billType, strStartDate);
					maintRate = outrateRepository.findOutrAuxiRateMonthWiseForInfra(buildingCode, wing, flatNum, billType, strStartDate);
				} else {
					String adminRate1 = outrateRepository.findAdminRateMonthWiseForAuxi(buildingCode, wing, flatNum, billType, strStartDate);
					String maintRate1 = outrateRepository.findOutrAuxiRateMonthWiseForAuxi(buildingCode, wing, flatNum, billType, strStartDate);
					adminRate = Double.valueOf(adminRate1);
					maintRate = Double.valueOf(maintRate1);
				}
				tdsRate = outrateRepository.findTdsRateMonthWise(buildingCode, wing, flatNum, billType, strStartDate);
				
			}
		} catch (org.springframework.aop.AopInvocationException e) {
			throw new OutRateRateException(ApiResponseMessage.CHECK_ADMIN_OR_MAINTANACE_RATE_ZERO, 200);
		}
		
		
		log.debug("adminRate from db: {}", adminRate);
		log.debug("auxiRate from db: {}", maintRate);
		log.debug("tdsRate from db: {}", tdsRate);
		

		if (adminRate == 0 && maintRate == 0) {
			throw new InternalServerError(ApiResponseMessage.ADMIN_AND_MAINTANACE_RATE_ZERO);
		}
//		double prevMaintPaid = 0;
//		double prevAmtintPaid = 0;
//		double prevCGSTtaxPaid = 0;
//		double prevSGSTtaxPaid = 0;
//		double prevIGSTtaxPaid = 0;
//		double prevAdminPaid = 0;
//		double prevTDStaxPaid = 0;

		while (amount > 0) {
			Hsnsacmaster gst = hsnsacmasterRepository.findByHsnsacmasterCKHsmsCode("995419");
			Double cgstPerc = gst.getHsmsCgstperc();
			Double sgstPerc = gst.getHsmsSgstperc();
			Double igstPerc = 0.00;

			double prevCGSTAdmin = 0;
			double prevSGSTAdmin;
			double prevIGSTAdmin;
			double prevCGSTMaint = 0;
			double prevSGSTMaint = 0;
			double prevIGSTMaint;
			double cgstOnAdminRate;
			double sgstOnAdminRate;
			double igstOnAdminRate = 0;
			double cgstOnMaintRate;
			double sgstOnMaintRate;
			double igstOnMaintRate;
			double prevCgstAdmin;
			double adjAdmin;
			double adjMaint;
			double adjTDS = 0;
			double adjCGSTAdmin;
			double adjSGSTAdmin;
			double adjIGSTAdmin;
			double adjCGSTMaint;
			double adjSGSTMaint;
			double adjIGSTMaint;

			// Returns Unadjusted Receipt Amount
			if (amount <= 0) {
				continue;
			}

			// Calculate GST ON ADMIN
			if (billType.equals("F")) {
				cgstOnAdminRate = (int) Math.round((cgstPerc * adminRate) / 100.0);
				sgstOnAdminRate = (int) Math.round((sgstPerc * adminRate) / 100.0);
				sgstOnAdminRate = (int) Math.round((igstPerc * adminRate) / 100.0);

				cgstOnMaintRate = (int) Math.round((cgstPerc * maintRate) / 100.0);
				sgstOnMaintRate = (int) Math.round((sgstPerc * maintRate) / 100.0);
				igstOnMaintRate = (int) Math.round((igstPerc * maintRate) / 100.0);
			} else {
				cgstOnAdminRate = (int) Math.ceil((cgstPerc * adminRate) / 100.0);
				sgstOnAdminRate = (int) Math.ceil((sgstPerc * adminRate) / 100.0);
				igstOnAdminRate = (int) Math.ceil((igstPerc * adminRate) / 100.0);

				cgstOnMaintRate = (int) Math.ceil((cgstPerc * maintRate) / 100.0);
				sgstOnMaintRate = (int) Math.ceil((sgstPerc * maintRate) / 100.0);
				igstOnMaintRate = (int) Math.ceil((igstPerc * maintRate) / 100.0);
			}

			// Bifurcate GST into Admin GST and Maint GST
			if (prevCGSTtaxPaid >= cgstOnAdminRate) {
				prevCgstAdmin = cgstOnAdminRate;
			} else {
				prevCgstAdmin = prevCGSTtaxPaid;
			}

			if (prevSGSTtaxPaid >= sgstOnAdminRate) {
				prevSGSTAdmin = sgstOnAdminRate;
				prevSGSTMaint = prevSGSTtaxPaid - prevSGSTAdmin;
			} else {
				prevSGSTAdmin = prevSGSTtaxPaid;
			}

			if (prevIGSTtaxPaid >= igstOnAdminRate) {
				prevIGSTAdmin = igstOnAdminRate;
				prevIGSTMaint = prevIGSTtaxPaid - prevIGSTAdmin;
			} else {
				prevIGSTAdmin = prevIGSTtaxPaid;
				prevIGSTMaint = 0;
			}

			// Check if FULL Amount already received
			if ((prevAdminPaid + prevCGSTAdmin + prevSGSTAdmin + prevIGSTAdmin + prevAmtintPaid + prevCGSTMaint
					+ prevSGSTMaint + prevIGSTMaint) >= (adminRate + cgstOnAdminRate + sgstOnAdminRate + igstOnAdminRate
							+ maintRate + cgstOnMaintRate + sgstOnMaintRate + igstOnMaintRate)) {
				continue;
			} else {
				// If Admin amount remains to be adjusted
				/***************** ADMIN Start **************************************/
				adjAdmin = adminRate - prevAdminPaid;
				adjCGSTAdmin = cgstOnAdminRate - prevCgstAdmin;
				adjSGSTAdmin = sgstOnAdminRate - prevSGSTAdmin;
				adjIGSTAdmin = igstOnAdminRate - prevIGSTAdmin;

				if ((adjAdmin + adjCGSTAdmin + adjSGSTAdmin + adjIGSTAdmin) > 0 && amount > 0) {

					if (amount >= (adjAdmin + adjCGSTAdmin + adjSGSTAdmin + adjIGSTAdmin)) {

						amount = amount - (adjAdmin + adjCGSTAdmin + adjSGSTAdmin + adjIGSTAdmin);
					} else {
						if (amount <= 2) {
							adjAdmin = amount;
							adjCGSTAdmin = 0;
							adjSGSTAdmin = 0;
							adjIGSTAdmin = 0;
							amount = 0;
						} else {
							double factor = (amount / (adjAdmin + adjCGSTAdmin + adjSGSTAdmin + adjIGSTAdmin));
							if (billType.equals("F")) {
								int adjCGSTAdm = (int) Math.round(adjCGSTAdmin * factor);
								adjCGSTAdmin = adjCGSTAdm;

								int adjSGSTAdm = (int) Math.round(adjSGSTAdmin * factor);
								adjSGSTAdmin = adjSGSTAdm;

								int adjIGSTAdm = (int) Math.round(adjIGSTAdmin * factor);
								adjIGSTAdmin = adjIGSTAdm;

							} else {
								adjCGSTAdmin = Math.ceil(adjCGSTAdmin * factor);
								adjSGSTAdmin = Math.ceil(adjSGSTAdmin * factor);
								adjIGSTAdmin = Math.ceil(adjIGSTAdmin * factor);
							}
							adjAdmin = Double.parseDouble(String.format("%.2f",amount - adjCGSTAdmin - adjSGSTAdmin - adjIGSTAdmin));
							amount = 0;
						}
					}

				} else {
					adjAdmin = 0;
					adjCGSTAdmin = 0;
					adjSGSTAdmin = 0;
					adjIGSTAdmin = 0;
				}

				/***************** ADMIN END **************************************/

				// If MAINT amount remains to be adjusted
				/***************** MAINT Start **************************************/
				adjMaint = maintRate - prevAdminPaid;
				adjCGSTMaint = cgstOnMaintRate - prevCGSTMaint;
				adjSGSTMaint = sgstOnMaintRate - prevSGSTMaint;
				adjIGSTMaint = igstOnMaintRate - prevIGSTMaint;

				if ((adjMaint + adjCGSTMaint + adjSGSTMaint + adjIGSTMaint) > 0 && amount > 0) {

					if (amount >= (adjMaint + adjCGSTMaint + adjSGSTMaint + adjIGSTMaint)) {
						amount = amount - (adjMaint + adjCGSTMaint + adjSGSTMaint + adjIGSTMaint);

					} else {
						if (amount <= 2) {
							adjMaint = amount;
							adjCGSTMaint = 0;
							adjSGSTMaint = 0;
							adjIGSTMaint = 0;
							amount = 0;
						} else {
							double factor = amount / (adjMaint + adjCGSTMaint + adjSGSTMaint + adjIGSTMaint);
							if (billType.equals("F")) {
								int adjCGSTMa = (int) Math.round(adjCGSTMaint * factor);
								adjCGSTMaint = adjCGSTMa;

								int adjSGSTMa = (int) Math.round(adjSGSTMaint * factor);
								adjSGSTMaint = adjSGSTMa;

								int adjIGSTMa = (int) Math.round(adjIGSTMaint * factor);
								adjIGSTMaint = adjIGSTMa;

							} else {
								adjCGSTMaint = Math.ceil(adjCGSTMaint * factor);
								adjSGSTMaint = Math.ceil(adjSGSTMaint * factor);
								adjIGSTMaint = Math.ceil(adjIGSTMaint * factor);
							}
							adjMaint = Double.parseDouble(String.format("%.2f", amount - adjCGSTMaint - adjSGSTMaint - adjIGSTMaint));
							amount = 0;
						}
					}
				} else {
					adjMaint = 0;
					adjCGSTMaint = 0;
					adjSGSTMaint = 0;
					adjIGSTMaint = 0;
				}
				/***************** MAINT END **************************************/

				// If TDS amount remains to be adjusted
				/***************** TDS Start **************************************/
				// Check if FULL Amount already received

				if (receiptTds < 0) {
					if (prevTDStaxPaid <= tdsRate) {

					} else {
						adjTDS = tdsRate - prevTDStaxPaid;

						if (adjTDS < 0) {

							if (receiptTds < adjTDS) {

								receiptTds = receiptTds - adjTDS;

							} else {

								adjTDS = receiptTds;

								receiptTds = 0;
							}
						} else {
							adjTDS = 0;
						}
					}
				}
			}
			double cgst = Double.parseDouble(String.format("%.2f",adjCGSTAdmin + adjCGSTMaint));
			double sgst = Double.parseDouble(String.format("%.2f",adjSGSTAdmin + adjSGSTMaint));
			double igst = Double.parseDouble(String.format("%.2f",adjIGSTAdmin + adjIGSTMaint));
			if (billType.equals("F")) {
//				date = DateUtill.increaseMonth(date);
				GridResponse response = insertRow(date, adjAdmin, adjMaint, cgst, sgst, igst, adjTDS, cgstPerc, sgstPerc,
						igstPerc, narration);
				insertRowList.add(response);
			}else {
				GridResponse response = insertRow(date, adjAdmin, adjMaint, cgst, sgst, igst, adjTDS, cgstPerc, sgstPerc,
						igstPerc, narration);
				insertRowList.add(response);
//				date = DateUtill.increaseMonth(date);
			}
			date = DateUtill.increaseMonth(date);
		}
		return insertRowList;
	}

	private GridResponse insertRow(String startDate, double adjAdmin, double adjMaint, double cgst, double sgst,
			double igst, double adjTDS, double cgstPerc, double sgstPerc, double igstPerc, String narration) {
		List<GridResponse> list = new ArrayList<>();

		GridResponse response = GridResponse.builder().monthName(startDate).narration(narration).narrationCode("FP")
				.intPaid(0).cgst(cgst).sgst(sgst).igst(igst).auxiPaid(adjMaint).admin(adjAdmin).cgstPercent(cgstPerc)
				.sgstPercent(sgstPerc).igstPercent(igstPerc).tds(adjTDS).build();

		log.debug("GridResponse : {}", list);
		return response;

	}

}