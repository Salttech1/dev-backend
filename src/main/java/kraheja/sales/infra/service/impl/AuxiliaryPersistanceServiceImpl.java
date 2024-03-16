package kraheja.sales.infra.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ActranhCK;
import kraheja.commons.entity.Actranhx;
import kraheja.commons.entity.ActranhxCK;
import kraheja.commons.entity.Inchq;
import kraheja.commons.entity.InchqCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.ActranhxRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.commons.repository.InchqRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.exception.ConstraintViolationException;
import kraheja.sales.bean.entitiesresponse.AuxiBuildingDBResponse;
import kraheja.sales.bean.entitiesresponse.GlchartDBResponse;
import kraheja.sales.bean.request.ChequeRequest;
import kraheja.sales.bean.request.InchequeRequest;
import kraheja.sales.bean.response.GridResponse;
import kraheja.sales.bean.response.InchequeDetailResponse;
import kraheja.sales.bean.response.InchequeResponse;
import kraheja.sales.entity.Outinfra;
import kraheja.sales.entity.OutinfraCK;
import kraheja.sales.infra.service.AuxiliaryPersistanceService;
import kraheja.sales.repository.OutinfraRepository;
import kraheja.utility.DateUtill;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * this class is use to persist data row wise in database.
 * </p>
 * 
 * @author sazzad.alom
 * @since 28-OCTBER-2023
 * @version 1.0.0
 */
@Log4j2
@Service
@Transactional
public class AuxiliaryPersistanceServiceImpl implements AuxiliaryPersistanceService {

	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private GlchartRepository glchartRepository;
	@Autowired
	private InchqRepository inchqRepository;

	@Autowired
	private OutinfraRepository outinfraRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private ActranhxRepository actranhxRepository;

	/**
	 * <p>
	 * this method is use to get party code and minor map.
	 * </p>
	 */
	private Map<String, String> getPartyCodeAndMinor(String bldgCode, String wing, String reqFlatNumber,
			String chargeCode) {

		Map<String, String> partyCodeMinorMap = new HashMap<>();
		String partyCode = "";
		String lastOwnerid = "";
		int ownerIdCount = 0;
		String ownerid = "";
		String minor = "";
		String flatNumber = reqFlatNumber.trim();
		
		if (flatNumber.length() == 5) {
			flatNumber = flatNumber + " ";
		}

		partyCode = bldgCode + wing + flatNumber + "%";

		lastOwnerid = partyRepository.getPartyCode(partyCode, "F");
		log.debug("partyCode from db : {}", lastOwnerid);

		if (lastOwnerid.equals("")) {
			ownerIdCount = 1;
		} else {
			ownerIdCount = Integer.parseInt(lastOwnerid.substring(lastOwnerid.length() - 1));
		}
		ownerid = bldgCode + wing + flatNumber;
		if (ownerid.length() <= 10) {
			partyCode = bldgCode + wing + flatNumber + " " + String.valueOf(ownerIdCount);
			minor = bldgCode + wing + chargeCode + flatNumber + " " + String.valueOf(ownerIdCount);
		} else {
			partyCode = bldgCode + wing + flatNumber + String.valueOf(ownerIdCount);
			minor = bldgCode + wing + chargeCode + flatNumber + String.valueOf(ownerIdCount);
		}
		partyCodeMinorMap.put("partyCode", partyCode);
		partyCodeMinorMap.put("minor", minor);
		return partyCodeMinorMap;
	}

	@Override
	public InchequeResponse saveIncheqe(String bldgCode, String reqWing, String flatNumber, String chargeCode, String billType,
			InchequeRequest inchequeRequest) {
		log.debug("inchequeRequest: {}", inchequeRequest);
		
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String sysTime = " " + currentTime.format(formatter);
		String recDate = inchequeRequest.getRecDate() + sysTime;
		
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    LocalDateTime localDateTime = LocalDateTime.parse(recDate, inputFormatter);
	    inchequeRequest.setReceiptDate(localDateTime);
	    
		String wing = "";
		String remarks = "";
		String result = Result.FAILED;
		String message = ApiResponseMessage.INCHEQ_DETAIL_FAILED_TO_SAVE;
		String responseCode = ApiResponseCode.FAILED;
		String ownerId = "";
		String chequeNo = "";
		String siteName = GenericAuditContextHolder.getContext().getSite();
		String userId = GenericAuditContextHolder.getContext().getUserid();
		double totalAmt = inchequeRequest.getOgAmt();
		double adminCharge = inchequeRequest.getAdmin();
		
		if (!flatNumber.equals(" "))
			
		if (!reqWing.equals(" ")) {
			 wing = reqWing.trim();
			ownerId = bldgCode + wing + flatNumber;
		}
		else if (reqWing.equals(" ")) {
			wing = " ";
			ownerId = bldgCode + " " + flatNumber;
		}
		
		String receiptNumber = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "#SIRC", siteName);
		log.debug("receiptNumber: {}", receiptNumber);
		
		String transer = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "RCSER", siteName);
		log.debug("transer: {}", transer);

		String recieptDate = DateUtill.dateFormatter(inchequeRequest.getReceiptDate());

		String inchqMode = "Q";

		Map<String, String> partyCodeAndMinor = getPartyCodeAndMinor(bldgCode, wing, flatNumber, chargeCode);
		String partyCode = partyCodeAndMinor.get("partyCode");
		String minor = partyCodeAndMinor.get("minor");
		log.debug("partyCode : {} minor: {}", partyCode, minor);

		AuxiBuildingDBResponse buildingDBResponse = buildingRepository.findBuildingByCode(bldgCode);
		log.debug("buildingDBResponse : {} ", buildingDBResponse);

		GlchartDBResponse glchartDBResponse = glchartRepository.fetchChartCfrecgroup("11401233");
		log.debug("glchartDBResponse : {}", glchartDBResponse);

		String rgroupc = String.format("%1$6s", glchartDBResponse.getChartRgroupc());
		String cfrecgroup = glchartDBResponse.getChartCfrecgroup();

		if (rgroupc.length() > 6) {
			rgroupc = rgroupc.substring(rgroupc.length() - 6);
		}
		if (rgroupc.equals("XXXXXX")) {
			rgroupc = "";
		}
		if (cfrecgroup.equals("XXXXXX")) {
			cfrecgroup = "";
		}

		List<ChequeRequest> cheques = inchequeRequest.getCheques();
		List<GridResponse> gridRequest = inchequeRequest.getGridRequest();
		List<InchequeDetailResponse> chequeResponse = new ArrayList<>();
		
		String periodFrom = DateUtill.startDayOfMonthFromYYYYMM(inchequeRequest.getStartMonth()); 
		String periodUpto = DateUtill.lastDayOfMonthFromYYYYMM(inchequeRequest.getEndMonth());
		log.debug("periodFrom: {} periodUpto: {}", periodFrom, periodUpto);
		
		try {
			remarks = gridRequest.get(0).getNarration();
			
			String outInfraPersistResult = this.saveOutInfra(inchequeRequest.getGridRequest(), userId, siteName,
					bldgCode, wing, flatNumber, chargeCode, billType, inchequeRequest.getReceiptDate(), receiptNumber);
			log.debug("out infra persist result: {}", outInfraPersistResult);
			
			for (ChequeRequest chequeRequest : cheques) {
				String cheqDateStr = chequeRequest.getCheqDate() + sysTime;
			    LocalDateTime cheqDate = LocalDateTime.parse(cheqDateStr, inputFormatter);
			    chequeRequest.setChequeDate(cheqDate);
			    
				chequeNo = chequeRequest.getChequeNumber();
				double chqAmt = Double.parseDouble(chequeRequest.getChequeAmount());
				inchqMode = chequeNo != null ? "Q" : "C";
				
				LocalDateTime chequeDate = chequeRequest.getChequeDate().withHour(0).withMinute(0).withSecond(0).withNano(0);
				InchqCK ck = InchqCK.builder()
						.inchqNum(chequeRequest.getChequeNumber()) // 1
						.inchqBank(chequeRequest.getBank()) // 4
						.inchqCoy(buildingDBResponse.getBldgCoy())
						.inchqTranser(transer)
						.inchqRecnum(receiptNumber)
						.build();

				Inchq inchqEntity = Inchq.builder()
						.inchqCk(ck)
						.inchqPaymode(inchqMode) // 0
						.inchqAmount(chqAmt) // 2
						.inchqDate(chequeDate) // 3
						.inchqOutstat(chequeRequest.getOutstat()) // 5
						.inchqResubcount(0.00)
//						.inchqRemark(remarks)
						.inchqProprietor(buildingDBResponse.getBldgProp())
						.inchqOrigsys("IN")
						.inchqPartycode(partyCode)
						.inchqFund(chequeRequest.getFundSource())
						.inchqActype(chequeRequest.getAcType())
						.inchqSite(siteName)
						.inchqUserid(userId)
						.inchqLoanyn("N")
						.inchqOrigsite(siteName)
						.inchqToday(LocalDateTime.now())
						.build();

				log.debug("inchqEntity : {}", inchqEntity);
				Inchq save = inchqRepository.save(inchqEntity);

				InchequeDetailResponse inchequeDetailResponse = new InchequeDetailResponse();
				if (!(save.getInchqCk().getInchqBank()).equals(null)) {
					inchequeDetailResponse.setChequeNumber(save.getInchqCk().getInchqNum());
					inchequeDetailResponse.setChequeAmount(save.getInchqAmount().toString());
					inchequeDetailResponse.setMessage(ApiResponseMessage.INCHEQUE_SAVE_SUCCESS);
					result = Result.SUCCESS;
					responseCode = ApiResponseCode.SUCCESS;
					message = "incheq detail save successfully.";
				} else {
					inchequeDetailResponse.setChequeNumber(save.getInchqCk().getInchqNum());
					inchequeDetailResponse.setChequeAmount(save.getInchqAmount().toString());
					inchequeDetailResponse.setMessage(ApiResponseMessage.INCHEQ_DETAIL_FAILED_TO_SAVE);
				}
				chequeResponse.add(inchequeDetailResponse);
				double basicAmt = inchequeRequest.getOgAmt() + inchequeRequest.getIntPaid() + inchequeRequest.getTdsAmt();
				log.debug("basicAmt: {}", basicAmt);
			}
			
			/*
			 * THIS METHOD IS USE FOR INSERT INT ACTRANH TABLE.
			 */
			this.updateActranh(transer, buildingDBResponse, partyCode, inchequeRequest, receiptNumber, siteName, userId);
//			this.updateActranh(actranh);
			
			String savedIntoActrand = savedIntoActrand(buildingDBResponse,totalAmt, adminCharge, inchequeRequest, bldgCode, wing, flatNumber, transer, receiptNumber, chargeCode, partyCode, periodFrom, periodUpto, recieptDate);
			log.debug("savedIntoActrand : {}", savedIntoActrand);

			return InchequeResponse.builder().result(result).message(message).responseCode(responseCode)
					.receptNumber(receiptNumber).chequeResponse(chequeResponse).build();
		} catch (Exception pe) {
			throw new ConstraintViolationException(pe.getMessage());
		}
	}
	
	private String savedIntoActrand(AuxiBuildingDBResponse buildingDBResponse,double basicAmt, double adminCharge, InchequeRequest inchequeRequest,String bldgCode, String wing, String flatNumber,String transer,String receiptNumber,
									String chargeCode, String partyCode, String periodFrom, String periodUpto, String recieptDate) {
		double totalAmt = basicAmt + adminCharge;
		
		List<ActrandBean> actrandList = new ArrayList<>();
		int bunum = 1;

		if (basicAmt > 0) {
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "11401233", "C", chargeCode, "F",
					partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, basicAmt,
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(), "", "", "", periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		
		if (inchequeRequest.getCgstAmt() > 0) {
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "11402431", "", "",
					"F", partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, inchequeRequest.getCgstAmt(),
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(),"", "", "",periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		if (inchequeRequest.getSgstAmt() > 0) {
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "11402433", "", "",
					"F", partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, inchequeRequest.getSgstAmt(),
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(), "", "", "",periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		if (inchequeRequest.getIgst() > 0) {
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "11402435", "", "",
					"", partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, inchequeRequest.getIgst(),
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(),"", "", "",periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		
		if (inchequeRequest.getAdmin() > 0) {
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "30114103", "B", bldgCode,
					"", partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, inchequeRequest.getAdmin(),
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(), "", "", "",periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		
		if (inchequeRequest.getTdsAmt() > 0) { //TODO create a method to accessment-year
			actrandList.addAll(GenericAccountingLogic.initialiseActrandBreakups("RC", "20404561", "A", getAssYear(inchequeRequest.getReceiptDate()),
					"", partyCode, "GL", "", "80000006", "", "", "", "GL", "", totalAmt, -(inchequeRequest.getTdsAmt()),
					bldgCode,wing, flatNumber, "", "", DateUtill.dateFormatter(LocalDateTime.now()), bunum, "",
					transer, "PL", buildingDBResponse.getBldgProp(),
					buildingDBResponse.getBldgCoy(), "", "", "",periodFrom, periodUpto,"Q", "", "",
					"", 0.00, receiptNumber, recieptDate, "", "F", partyCode));
			bunum = bunum + 2;
		}
		actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));
		return Result.SUCCESS;
	}

	private String getAssYear(LocalDateTime receiptDate) {
		String recDate = DateUtill.formattedDate(receiptDate);
		return actrandRepository.getAssYear(recDate);
	}

	private String updateActranh(String transer, AuxiBuildingDBResponse buildingDBResponse, String  partyCode, InchequeRequest inchequeRequest, String receiptNumber, String siteName, String userId) {
//		double tranAmt = inchequeRequest.getTransactionAmt() + inchequeRequest.getIntPaid() + inchequeRequest.getTdsAmt();
		double tranAmt = inchequeRequest.getTransactionAmt() - inchequeRequest.getTdsAmt();
		LocalDateTime tranDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		
		ActranhCK actranhCk = ActranhCK.builder().acthTranser(transer)
				.acthCoy(buildingDBResponse.getBldgCoy()).build();
		
		Actranh actranh = Actranh.builder().actranhCK(actranhCk).acthTrantype("RC")
				.acthTrandate(tranDate).acthLedgcode("").acthPartytype("F").acthPartycode(partyCode)
				.acthTranamt(tranAmt).acthProprietor(buildingDBResponse.getBldgProp())
				.acthVounum(receiptNumber).acthVoudate(LocalDateTime.now().toLocalDate()).acthPostedyn("N")
				.acthSite(siteName).acthUserid(userId).acthToday(LocalDateTime.now()).acthClearacyn("N")
				.acthReverseyn("N").acthProvyn("N").build();
		log.debug("actranh persistance data: {}", actranh);
		
		
		
		Actranh hasRecord = actranhRepository.fetchActranh(actranh.getActranhCK().getActhTranser());
		log.debug("actranh has already recorded: {}", hasRecord);
		if (Objects.nonNull(hasRecord)) {
			ActranhxCK actranhxCK = ActranhxCK.builder().acthCoy(actranh.getActranhCK().getActhCoy())
					.acthRevision(actranh.getActhReverseyn()).acthToday(LocalDateTime.now())
					.acthTranser(actranh.getActranhCK().getActhTranser()).build();
			Actranhx actranhx = Actranhx.builder().actranhxCK(actranhxCK).acthTrantype(actranh.getActhTrantype())
					.acthTrandate(actranh.getActhTrandate().toLocalDate()).acthPartytype(actranh.getActhPartytype())
					.acthPartycode(actranh.getActhPartycode()).acthTranamt(actranh.getActhTranamt())
					.acthProprietor(actranh.getActhProprietor()).acthVounum(actranh.getActhVounum())
					.acthVoudate(actranh.getActhVoudate()).acthPostedyn(actranh.getActhPostedyn())
					.acthSite(actranh.getActhSite()).acthUserid(actranh.getActhUserid()).build();
			Actranhx save = actranhxRepository.save(actranhx);
			log.debug("actranhx data obtaint: {}", save);
		}
		Actranh save = actranhRepository.save(actranh);
		if (Objects.nonNull(save)) {
			return Result.SUCCESS;
		}
		return Result.FAILED;
	}

	private String saveOutInfra(List<GridResponse> gridRequest, String userId, String siteName, String bldgCode,
			String wing, String flatNumber, String chargeCode, String billType, LocalDateTime receiptDate, String receiptNumber) {
		log.debug("saveOutInfra obtain gridRequest: {} userId: {} siteName: {} bldgCode : {} wing: {} flatNumber: {} chargeCode: {} receiptDate: {} receiptNumber : {}",gridRequest, userId, siteName, bldgCode, wing, flatNumber, chargeCode, receiptDate, receiptNumber );

		
		String outInfraSaveResult = Result.FAILED;

		String ownerId = bldgCode + wing + flatNumber;
		AuxiBuildingDBResponse buildingDBResponse = buildingRepository.findBuildingByCode(bldgCode);
		if (flatNumber.length() == 5) {
			flatNumber = flatNumber + " ";
		}

		if (wing.equals("")) {
			wing = "";
		}
		if (wing.equals("")) {
			ownerId = bldgCode + " " + flatNumber;
			wing = " ";
		}

		for (GridResponse grid : gridRequest) {
			double tds = (grid.getTds() > 0) ? -grid.getTds() : 0.00;
			
			OutinfraCK outinfraCK = OutinfraCK.builder().infRecnum(receiptNumber).infOwnerid(ownerId)
					.infBldgcode(bldgCode).infMonth(grid.getMonthName()).infNarrcode(grid.getNarrationCode().trim()).build();

			Outinfra outinfra = Outinfra.builder().outinfraCK(outinfraCK).infWing(wing).infFlatnum(flatNumber)
					.infCoy(buildingDBResponse.getBldgCoy()).infAmtdue(0.00).infAmtpaid(grid.getAuxiPaid())
					.infAmtos(0.00).infAmtint(grid.getIntPaid()).infOrigint(0.00).infChargecode(chargeCode)
					.infRecdate(receiptDate.toLocalDate()).infRecprintyn("N")
					.infCancelledyn("N").infRemarks(grid.getNarration()).infSite(siteName).infUserid(userId)
					.infToday(LocalDateTime.now()).infOrigsite(siteName).infGstyn("Y").infRectype(billType)
					.infCgst(grid.getCgst()).infSgst(grid.getSgst()).infIgst(grid.getIgst()).infCancelledyn("N").infSwachhcess(0.00).infKrishicess(0.00)
					.infAdmincharges(grid.getAdmin()).infCgstperc(grid.getCgstPercent()).infSgstperc(grid.getSgstPercent())
					.infIgstperc(grid.getIgstPercent()).infTds(tds).infServtax(0.00).infSwachhcess(0.00).build();

			log.debug("outinfra : {}", outinfra);

			Outinfra saveOutinfra = outinfraRepository.save(outinfra);
//			Outinfra saveOutinfra = new Outinfra();

			if (Objects.nonNull(saveOutinfra)) {
				outInfraSaveResult = Result.SUCCESS;
			}
		}
		return outInfraSaveResult;
	}

}