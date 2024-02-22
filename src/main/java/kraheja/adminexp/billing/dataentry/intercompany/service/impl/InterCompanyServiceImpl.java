package kraheja.adminexp.billing.dataentry.intercompany.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraheja.adminexp.billing.dataentry.bean.response.db.SearchACMejor;
import kraheja.adminexp.billing.dataentry.bean.response.db.SpecifyedAcMejor;
import kraheja.adminexp.billing.dataentry.bean.response.db.SpecifyedInnerCoy;
import kraheja.adminexp.billing.dataentry.entity.Intercoybilldetail;
import kraheja.adminexp.billing.dataentry.entity.IntercoybilldetailCK;
import kraheja.adminexp.billing.dataentry.entity.Intercoybillheader;
import kraheja.adminexp.billing.dataentry.entity.IntercoybillheaderCK;
import kraheja.adminexp.billing.dataentry.intercompany.bean.db.InvoiceDateRetriveDBResponse;
import kraheja.adminexp.billing.dataentry.intercompany.bean.request.InterCompanyRequest;
import kraheja.adminexp.billing.dataentry.intercompany.bean.response.AddInterCompanyData;
import kraheja.adminexp.billing.dataentry.intercompany.bean.response.AddInterCompanyResponse;
import kraheja.adminexp.billing.dataentry.intercompany.service.InterCompanyService;
import kraheja.adminexp.billing.dataentry.repository.IntercoybillDetailRepository;
import kraheja.adminexp.billing.dataentry.repository.IntercoybillheaderRepository;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.payload.GenericResponse;
import kraheja.utility.DateUtill;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * this @service created for fetch inter-company data calculate it condition
 * wise.
 * </p>
 * 
 * @author sazzad.alom
 * @version 1.0.0
 * @since 21-NOV-2023
 */

@Log4j2
@Service
@Transactional
public class InterCompanyServiceImpl implements InterCompanyService {

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private IntercoybillheaderRepository billheaderRepository;

	@Autowired
	private IntercoybillDetailRepository billdetailRepository;

	@Autowired
	private EntityRepository entityRepository;
	@Autowired
	private GenericCounterIncrementLogicUtil counterIncrementLogicUtil;

	@Override
	public AddInterCompanyResponse fetchInterCompanyDetails(InterCompanyRequest request) {

		List<SearchACMejor> searchACMejorList = new ArrayList<>();
		List<SpecifyedAcMejor> specifyedAcMejorList = new ArrayList<>();
		List<SpecifyedInnerCoy> specifyedInnerCoyList = new ArrayList<>();
		List<AddInterCompanyData> interCompanyDataList = new ArrayList<>();

		String companyCode = request.getCompanyCode().trim();
		String projectCode = request.getProjectCode().trim();
		LocalDate billDate = null, billFrom = null, billTo = null;
		
		/**
		 * REQUEST DATE CONVERTED FROM STRING TO LOCALDATELIME
		 * */
		request.setBillDate(DateUtill.convertStringToDateFormat(request.getTransactionDate()).atStartOfDay());
		request.setBillFromDate(DateUtill.convertStringToDateFormat(request.getTransactionFromDate()).atStartOfDay());
		request.setBillToDate(DateUtill.convertStringToDateFormat(request.getTransactionToDate()).atStartOfDay());
		

		try {
			LocalDateTime maxPeriod = billheaderRepository.fetchMaxPeriod(companyCode, projectCode);
			log.debug("maxPeriod obtaint: {}", maxPeriod);

			if (maxPeriod == null) {
				LocalDate today = LocalDate.now();
				int year = today.getYear();
				String startApr = "01/04/" + year;
				String endJun = "30/06/" + year;
				billDate = DateUtill.convertStringToDateFormat(startApr);
				billFrom = billDate;
				billTo = DateUtill.convertStringToDateFormat(endJun);
			} else {
				int year = maxPeriod.getYear();
				if (maxPeriod.getMonth() == Month.DECEMBER) {
					year++;
					billDate = DateUtill.convertStringToDateFormat("31/03/" + year);
					billFrom = DateUtill.convertStringToDateFormat("01/01/" + year);
					billTo = DateUtill.convertStringToDateFormat("31/03/" + year);
				} else {
					LocalDateTime oneMonthsLater = maxPeriod.plusMonths(1).withDayOfMonth(1);

					billDate = oneMonthsLater.toLocalDate();
					billFrom = oneMonthsLater.toLocalDate();

					// Add three months to the current date and time
					LocalDateTime futureDateTime = maxPeriod.plusMonths(3);

					// Extract the date
					LocalDate futureDate = futureDateTime.toLocalDate();

					// Get the last date of the month
					billTo = futureDate.with(TemporalAdjusters.lastDayOfMonth());
				}

				log.debug("billDate : {} billFrom: {} billTo: {}", billDate, billFrom, billTo);
			}

			if (StringUtils.isNoneEmpty(request.getInvoiceNumber())) {
				// TODO SEARCH STARTDATE AND ENDDATE BY INVOICE GROUPID
			}
			List<Tuple> fetchTraiBalance = actrandRepository.fetchTraiBalance(companyCode, projectCode,
					request.getBillFromDate().toLocalDate(), request.getBillToDate().toLocalDate());

			// SEARCH AC-MEJOR AND BALANCE AMOUNT WHICH IS TAKEN FROM ACTRANH AND ACTRAND
			for (Tuple traiBalanceTuple : fetchTraiBalance) {
				searchACMejorList.add(SearchACMejor.builder().acmajor(traiBalanceTuple.get("ac_acmajor", String.class))
						.majorName(traiBalanceTuple.get("acname", String.class).trim())
						.acminor(traiBalanceTuple.get("ac_acminor", String.class) == null ? ""
								: traiBalanceTuple.get("ac_acminor", String.class))
						.minorName(traiBalanceTuple.get("acminorname", String.class) == null ? ""
								: traiBalanceTuple.get("acminorname", String.class))
						.acMinType(traiBalanceTuple.get("ac_mintype", Character.class).toString() == null ? ""
								: traiBalanceTuple.get("ac_mintype", Character.class).toString())
						.acAmount(traiBalanceTuple.get("ac_amount", BigDecimal.class).doubleValue()).build());
			}
			log.debug("searchACMejorList : {}", searchACMejorList);

			// LISTED AC-MEJOR
			List<Tuple> entityAcMejor = entityRepository.fetchEntityAcMejor();
			for (Tuple tuple : entityAcMejor) {
				specifyedAcMejorList.add(SpecifyedAcMejor.builder().description(tuple.get(0, String.class).trim())
						.acMejor(tuple.get(1, String.class)).build());
			}
			log.debug("specifyedAcMejorList : {}", specifyedAcMejorList);

			List<Tuple> companyEntityList = entityRepository.fetchCompanyEntity(companyCode);
			for (Tuple tuple : companyEntityList) {
				specifyedInnerCoyList.add(SpecifyedInnerCoy.builder().char1(tuple.get("ent_char1", String.class))
						.char2(tuple.get("ent_char2", String.class)).char3(tuple.get("ent_char3", String.class).trim())
						.char4(tuple.get("ent_char4", String.class).trim())
						.num1(tuple.get("ent_num1", BigDecimal.class).doubleValue())
						.num2(tuple.get("ent_num2", BigDecimal.class).doubleValue()).build());
			}
			log.debug("specifyedInnerCoyList : {}", specifyedInnerCoyList);

			for (SearchACMejor searchACMejor : searchACMejorList) {
				String fetchAcMajor = searchACMejor.getAcmajor().trim();
				Map<String, Double> innerCoyMap = new HashMap<>();
				for (SpecifyedInnerCoy innerCoy : specifyedInnerCoyList) {
					double innerCompanyAmt = 0.00;
					String innerCompanyName = innerCoy.getChar3() + "-" + innerCoy.getChar4();

					for (SpecifyedAcMejor specifyedAcMejor : specifyedAcMejorList) {
						String spcAcMejor = specifyedAcMejor.getAcMejor().trim();
						if (spcAcMejor.equals(fetchAcMajor)) {
							innerCompanyAmt = (searchACMejor.getAcAmount() / 100) * innerCoy.getNum2();
							break;
						} else {
							innerCompanyAmt = (searchACMejor.getAcAmount() / 100) * innerCoy.getNum1();
						}
						innerCoyMap.put(innerCompanyName, innerCompanyAmt);
					}
				}
				log.debug("innerCoyMap: {}", innerCoyMap);
				// SET ACMAJOR WISE DATE FOR GRID
				interCompanyDataList.add(AddInterCompanyData.builder().acmajor(fetchAcMajor)
						.majorName(searchACMejor.getMajorName()).acminor(searchACMejor.getAcminor())
						.minorName(searchACMejor.getMinorName()).acMinType(searchACMejor.getAcMinType())
						.acAmount(searchACMejor.getAcAmount()).localCompanyData(innerCoyMap).build());
			}
			log.debug("interCompanyDataList: {}", interCompanyDataList);

		} catch (Exception e) {
			throw new InternalError("error occured due to internal issue");
		}
		return AddInterCompanyResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
				.message(ApiResponseMessage.DATA_FETCH_SUCCESSFULLY).interCompanyData(interCompanyDataList).build();
	}

	@Override
	public AddInterCompanyResponse retriveInterCompanyDetails(InterCompanyRequest request) {
		List<SearchACMejor> searchACMejorList = new ArrayList<>();
		List<AddInterCompanyData> interCompanyDataList = new ArrayList<>();
		InvoiceDateRetriveDBResponse retriveDate = InvoiceDateRetriveDBResponse.builder().build();
		/**
		 * HERE WE ARE RETRIVE THE TRANSACTION DATE, TRANSACTION FROM DATE AND
		 * TRANSACTION TO DATEFROM INTER COMPANY HEADER TABLE
		 */
		Tuple retriveTuple = billheaderRepository.retriveInvoiceDate(request.getInvoiceNumber());
		retriveDate.setInvoiceDate(retriveTuple.get(0, Timestamp.class).toLocalDateTime().toLocalDate());
		retriveDate.setInvoiceFromDate(retriveTuple.get(1, Timestamp.class).toLocalDateTime().toLocalDate());
		retriveDate.setInvoiceToDate(retriveTuple.get(2, Timestamp.class).toLocalDateTime().toLocalDate());
		retriveDate.setNarration(retriveTuple.get(3, String.class));
		retriveDate.setCoyCode(retriveTuple.get(4, String.class));
		retriveDate.setProjectCode(retriveTuple.get(5, String.class));
		log.debug("retriveDate : {}", retriveDate);

		/**
		 * HERE SELECT DETAIL DATA FROM INTER COMPANY DETAIL TABLE
		 **/
		List<Tuple> detailTuple = billdetailRepository.fetchMajorFromDetail(request.getInvoiceNumber());

		/**
		 * ITERATE ONE BY ONE AND CHECK CONDITION FOR NOT NULL PROPERTY AND TRIMED THE
		 * WHITE SPACES. SAFE FROM NULL POINTER EXCEPTION.
		 **/
		for (Tuple tuple : detailTuple) {
			int srNo = ((BigDecimal) tuple.get(0)).intValue();
			String acMajor = (tuple.get(1, String.class) != null) ? tuple.get(1, String.class).trim() : "N";
			String acMajorName = (tuple.get(2, String.class) != null) ? tuple.get(2, String.class).trim() : "N";
			String acMinor = (tuple.get(3, String.class) != null) ? tuple.get(3, String.class).trim() : "N";
			String acMinorName = (tuple.get(4, String.class) != null) ? tuple.get(4, String.class).trim() : "N";

			String minType = (tuple.get(5, Character.class) != null) ? (tuple.get(5, Character.class).toString().trim())
					: "N";
//			String partyType = (tuple.get(6, Character.class) != null) ? (tuple.get(6, Character.class).toString().trim()) : "N";
			double acAmount = tuple.get(7, BigDecimal.class).doubleValue();

			searchACMejorList.add(SearchACMejor.builder().srNo(srNo).acmajor(acMajor).majorName(acMajorName)
					.acminor(acMinor).minorName(acMinorName).acMinType(minType).acAmount(acAmount).build());
		}
		log.debug("searchACMejorList : {}", searchACMejorList);

		/**
		 * complete header details
		 */
		List<Intercoybillheader> headerList = billheaderRepository.retriveHeader(request.getInvoiceNumber());
		log.debug("headerList : {}", headerList);
		List<AddInterCompanyData> majorList = new ArrayList<>();

		/**
		 * iterate one by one invoice or party
		 */
		for (Intercoybillheader header : headerList) {
			String partyInvoice = header.getIntercoybillheaderCK().getIcbehInvoiceno();
			String partyCode = header.getIcbehPartycode().trim();
			String projectCode = header.getIcbehRecbillprojcode().trim();
			String party = partyCode + "-" + projectCode;

			List<Intercoybilldetail> detailForParty = billdetailRepository.headerWisePartyDate(partyInvoice);
			log.debug("detailForParty : {}", detailForParty);

			// major list for a specific party FEHO-CHBF
			for (Intercoybilldetail detail : detailForParty) {
				Map<String, Double> coyMap = new HashMap<>();
				String acmajor = (detail.getIcbedAcmajor() != null) ? detail.getIcbedAcmajor().trim() : "N";
				String minor = (detail.getIcbedMinor() != null) ? detail.getIcbedMinor().trim() : "N";
				String mintype = (detail.getIcbedMintype() != null) ? detail.getIcbedMintype().trim() : "N";
				double tranAmt = detail.getIcbedTranamt();
				coyMap.put(party, tranAmt);
				log.debug("acmajor:{} minor:{} mintype:{} party:{} tranAmt:{}", acmajor, minor, mintype, party,
						tranAmt);
				majorList.add(AddInterCompanyData.builder().acmajor(acmajor).acminor(minor).acMinType(mintype)
						.localCompanyData(coyMap).build());

			}
		}
		log.debug("MajorList : {}", majorList);

//		List<Intercoybilldetail> headerWiseParty = billdetailRepository.headerWiseParty(request.getInvoiceNumber());

		for (SearchACMejor searchACMejor : searchACMejorList) {
			String acMajor = searchACMejor.getAcmajor();
			String acMinor = searchACMejor.getAcminor();
			String minType = searchACMejor.getAcMinType();

			Map<String, Double> innerCoyMap = new HashMap<>();
			for (AddInterCompanyData majorObj : majorList) {
				String acmajor = majorObj.getAcmajor();
				String acminor = majorObj.getAcminor();
				String mintype = majorObj.getAcMinType();
				Map<String, Double> companyMap = majorObj.getLocalCompanyData();
				if (acMajor.equals(acmajor) && acMinor.equals(acminor) && minType.equals(mintype)) {
					for (Entry<String, Double> entry : companyMap.entrySet()) {
						innerCoyMap.put(entry.getKey(),Double.valueOf(Math.round(entry.getValue())));
					}
				}
			}
			String majorname = (searchACMejor.getMajorName().equals("N")) ? null : searchACMejor.getMajorName();
			String minor = (searchACMejor.getAcminor().equals("N")) ? null : searchACMejor.getAcminor();
			String minorname = (searchACMejor.getMinorName().equals("N")) ? null : searchACMejor.getMinorName();
			String mintype = (searchACMejor.getAcMinType().equals("N")) ? null : searchACMejor.getAcMinType();

			// SET ACMAJOR WISE DATE FOR GRID
			interCompanyDataList.add(AddInterCompanyData.builder().acmajor(acMajor).majorName(majorname).acminor(minor)
					.minorName(minorname).acMinType(mintype).acAmount(searchACMejor.getAcAmount())
					.localCompanyData(innerCoyMap).build());
		}

		String remark = (retriveDate.getNarration() != null) ? retriveDate.getNarration().trim() : null;

		String posted = billheaderRepository.isPosted(request.getInvoiceNumber());
		log.debug("isPosted: {}", posted);

		return AddInterCompanyResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
				.message(ApiResponseMessage.FETCH_SUCCESSFULLY).companyCode(retriveDate.getCoyCode())
				.projectCode(retriveDate.getProjectCode()).billDate(retriveDate.getInvoiceDate().atStartOfDay())
				.billFromDate(retriveDate.getInvoiceFromDate().atStartOfDay())
				.billToDate(retriveDate.getInvoiceToDate().atStartOfDay()).remarks(remark)
				.interCompanyData(interCompanyDataList).isPosted(posted).build();
	}

	@Override
	public GenericResponse persistInterCompany(AddInterCompanyResponse request) {
		log.debug("AddInterCompanyRequest : {}", request);
		String userId = GenericAuditContextHolder.getContext().getUserid();
		String siteName = GenericAuditContextHolder.getContext().getSite();

		/**
		 * REQUEST DATE CONVERTED FROM STRING TO LOCALDATELIME
		 * */
		request.setBillDate(DateUtill.convertStringToDateFormat(request.getTransactionDate()).atStartOfDay());
		request.setBillFromDate(DateUtill.convertStringToDateFormat(request.getTransactionFromDate()).atStartOfDay());
		request.setBillToDate(DateUtill.convertStringToDateFormat(request.getTransactionToDate()).atStartOfDay());
		
		List<Intercoybillheader> headerList = new ArrayList<>();
		List<Intercoybilldetail> billDetailList = new ArrayList<>();

		if (StringUtils.isNoneEmpty(request.getGroupInvoiceNumber())) {
			
			String priGroupInvoiceNo = request.getGroupInvoiceNumber();

			// CHECK HERE IS ALREADY POSTED
			String posted = billheaderRepository.isPosted(priGroupInvoiceNo);
			if (posted.equals("Y")) {
				return GenericResponse.builder().result(Result.FAILED).responseCode(ApiResponseCode.FAILED)
						.message(ApiResponseMessage.INTERCOMPANY_FAILED_TO_UPDATE).build();
			}

			List<AddInterCompanyData> interCompanyDataList = request.getInterCompanyData();

			Map<String, Double> sumMap = new HashMap<>();
			for (AddInterCompanyData addInterCompanyData : interCompanyDataList) {
				addInterCompanyData.getLocalCompanyData().forEach((key, value) -> sumMap.merge(key,
						(double) Math.round(value + ((value * 18) / 100)), Double::sum));
			}

			List<String> localInvoiceList = new ArrayList<>();
			for (AddInterCompanyData addInterCompanyData : interCompanyDataList) {

				String acmajor = addInterCompanyData.getAcmajor();
				Double acAmount = addInterCompanyData.getAcAmount();

				String localPartyGSTYN = entityRepository.fetchLocalPartyGSTYN(acmajor);
				Map<String, Double> localCompanyDataMap = addInterCompanyData.getLocalCompanyData();

				for (Entry<String, Double> partyMapMajorWise : localCompanyDataMap.entrySet()) {
					String[] party = partyMapMajorWise.getKey().split("-");

					String partyCode = party[0];
					String locProjectCode = party[1];

					String locPartyInvoiceNo = billheaderRepository.findLocCoyInvoiceNumber(
							request.getCompanyCode().trim(), partyCode, priGroupInvoiceNo, locProjectCode);
					log.debug("generateLocPartyInvoiceNo: {}", locPartyInvoiceNo);
					localInvoiceList.add(locPartyInvoiceNo);

					Double totalAmount = partyMapMajorWise.getValue();
					Double cgstAmount = 0.00;
					Double sgstAmount = 0.00;
					if (localPartyGSTYN == null) {
						String strLocalPartyGSTAmount = entityRepository.fetchLocalPartyGSTAmount(acmajor);
						if (strLocalPartyGSTAmount == null) {
							cgstAmount = (double) Math.round(((partyMapMajorWise.getValue() * 9) / 100));
							sgstAmount = (double) Math.round(((partyMapMajorWise.getValue() * 9) / 100));
						} else {
							cgstAmount = (double) Math.round(cgstAmount + ((cgstAmount
									* (Double.valueOf(strLocalPartyGSTAmount) * Double.valueOf(strLocalPartyGSTAmount)))
									/ 100));
							sgstAmount = (double) Math.round(sgstAmount + ((sgstAmount
									* (Double.valueOf(strLocalPartyGSTAmount) * Double.valueOf(strLocalPartyGSTAmount)))
									/ 100));
						}
						totalAmount = totalAmount + cgstAmount + sgstAmount;
					}

					// SEARCH HEARE FOR UPDATE GET COMPLETE OBJECT
					Intercoybilldetail existingParty = billdetailRepository.findParty(priGroupInvoiceNo,
							locPartyInvoiceNo, acmajor, acAmount);
					log.debug("existingParty : {}", existingParty);

					existingParty.setIcbedTranamt(partyMapMajorWise.getValue());
					existingParty.setIcbedToday(LocalDateTime.now());
					existingParty.setIcbedUserid(userId);
					existingParty.setIcbedOrigsite(siteName);
					existingParty.setIcbedCgstamt(cgstAmount);
					existingParty.setIcbedSgstamt(sgstAmount);
					billDetailList.add(existingParty);
				}

				log.debug("billDetailList : {}", billDetailList);

			}

			for (Entry<String, Double> party : sumMap.entrySet()) {
				String[] split = party.getKey().split("-");
				String partyCode = split[0];
				String locProjectCode = split[1];

				Intercoybillheader existingHeader = billheaderRepository.findHeaders(request.getCompanyCode().trim(),
						partyCode, priGroupInvoiceNo, locProjectCode);
				log.debug("existingHeader : {}", existingHeader);

				existingHeader.setIcbehNarration(request.getRemarks());
				existingHeader.setIcbehSite(siteName);
				existingHeader.setIcbehUserid(userId);
				existingHeader.setIcbehTranamt(party.getValue());
				existingHeader.setIcbehToday(LocalDateTime.now());
				existingHeader.setIcbehTrandate(request.getBillDate().toLocalDate());
				existingHeader.setIcbehPeriodto(request.getBillToDate().toLocalDate());
				existingHeader.setIcbehPeriodfrom(request.getBillFromDate().toLocalDate());
				headerList.add(existingHeader);
			}
			log.debug("headerList : {}", headerList);

		} else {
			String groupInvoiceNo = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#ICSR");
			List<AddInterCompanyData> interCompanyDataList = request.getInterCompanyData();
			Map<String, Double> sumMap = new LinkedHashMap<>();
			
			for (AddInterCompanyData addInterCompanyData : interCompanyDataList) {
				addInterCompanyData.getLocalCompanyData().forEach((key, value) -> sumMap.merge(key,(double) Math.round(value + ((value * 18) / 100)), Double::sum));
			}
			log.debug("sumMap: {}", sumMap);
			
			for (Entry<String, Double> party : sumMap.entrySet()) {
				String companyInvoice = counterIncrementLogicUtil.generateInvoiceNo(request.getCompanyCode().trim(), "%#INC","IA", siteName, financialYearGetter(request.getBillDate()));
				log.debug("generateLocPartyInvoiceNo: {}", companyInvoice);
				String[] split = party.getKey().split("-");
				String partyCode = split[0];
				String locProjectCode = split[1];

				IntercoybillheaderCK headerCK = IntercoybillheaderCK.builder().icbehInvoiceno(companyInvoice)
						.icbehGroupinvoiceno(groupInvoiceNo).build();
				Intercoybillheader headerEntity = Intercoybillheader.builder().intercoybillheaderCK(headerCK)
						.icbehCoy(request.getCompanyCode()).icbehProjcode(request.getProjectCode())
						.icbehTrandate(request.getBillDate().toLocalDate())
						.icbehPeriodfrom(request.getBillFromDate().toLocalDate())
						.icbehPeriodto(request.getBillToDate().toLocalDate()).icbehTranamt(party.getValue())
						.icbehPartytype("C").icbehPartycode(partyCode).icbehRecbillprojcode(locProjectCode)
						.icbehPostedyn("N").icbehInvoicetranser("") // AS EMPTY INSERTED IN
						.icbehBilltranser("") // AS EMPTY INSERTED IN PREVIOUS APPLICATION
						.icbehNarration(request.getRemarks()).icbehSite(siteName).icbehUserid(userId)
						.icbehToday(LocalDateTime.now()).icbehOrigsite(siteName).build();
				
				int slNo = 1;
				for (AddInterCompanyData addInterCompanyData : interCompanyDataList) {
					String acmajor = addInterCompanyData.getAcmajor();
					String acminor = addInterCompanyData.getAcminor();
					String acMinType = addInterCompanyData.getAcMinType();
					Double acAmount = addInterCompanyData.getAcAmount();

					String localPartyGSTYN = entityRepository.fetchLocalPartyGSTYN(acmajor);
					Map<String, Double> localCompanyDataMap = addInterCompanyData.getLocalCompanyData();

					
					for (Entry<String, Double> partyMapMajorWise : localCompanyDataMap.entrySet()) {
						if ((party.getKey()).equals(partyMapMajorWise.getKey())) {
							Double totalAmount = partyMapMajorWise.getValue();
							Double cgstAmount = 0.00;
							Double sgstAmount = 0.00;
							if (localPartyGSTYN == null) {
								String strLocalPartyGSTAmount = entityRepository.fetchLocalPartyGSTAmount(acmajor);
								if (strLocalPartyGSTAmount == null) {
									cgstAmount = (double) Math.round(((partyMapMajorWise.getValue() * 9) / 100));
									sgstAmount = (double) Math.round(((partyMapMajorWise.getValue() * 9) / 100));
								} else {
									cgstAmount = (double) Math.round(cgstAmount + ((cgstAmount
											* (Double.valueOf(strLocalPartyGSTAmount) * Double.valueOf(strLocalPartyGSTAmount)))
											/ 100));
									sgstAmount = (double) Math.round(sgstAmount + ((sgstAmount
											* (Double.valueOf(strLocalPartyGSTAmount) * Double.valueOf(strLocalPartyGSTAmount)))
											/ 100));
								}
								totalAmount = totalAmount + cgstAmount + sgstAmount;
							}

							IntercoybilldetailCK ck = IntercoybilldetailCK.builder().icbedInvoiceno(companyInvoice)
									.icbedGroupinvoiceno(groupInvoiceNo).icbedSrno(slNo).build();

							Intercoybilldetail billDetailEntity = Intercoybilldetail.builder().intercoybilldetailCK(ck).icbedAcmajor(acmajor)
									.icbedMinor(acminor).icbedPartytype("").icbedMintype(acMinType).icbedAcamount(acAmount)
									.icbedTranamt(partyMapMajorWise.getValue()).icbedCgstper(9).icbedSgstper(9).icbedIgstper(0)
									.icbedUgstper(0).icbedCgstamt(cgstAmount).icbedSgstamt(cgstAmount).icbedIgstamt(0)
									.icbedUgstamt(0).icbedHsncode("XXXXXXXXXXXXXXH").icbedSite(siteName).icbedUserid(userId)
									.icbedToday(LocalDateTime.now()).icbedOrigsite(siteName).build();
							
							log.debug("billDetailEntity : {}", billDetailEntity);
							billdetailRepository.save(billDetailEntity);
							slNo++;
						}
					}
					
				}
				log.debug("headerEntity ready to persist: {}", headerEntity);
				billheaderRepository.save(headerEntity);
			}
		}

		return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS)
				.message(ApiResponseMessage.SUCCESSFULLY_PERSIST).build();
	}

	private String financialYearGetter(LocalDateTime invoiceDate) {
		int month = invoiceDate.getMonthValue();
		int year = invoiceDate.getYear();
		if (month < 4) {
			year = year - 1;
		}
		return String.valueOf(year);
	}

}
