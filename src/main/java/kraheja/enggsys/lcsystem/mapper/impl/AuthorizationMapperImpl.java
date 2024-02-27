package kraheja.enggsys.lcsystem.mapper.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.enggsys.entity.Lcauth;
import kraheja.enggsys.entity.LcauthCK;
import kraheja.enggsys.entity.Lcauthboe;
import kraheja.enggsys.entity.LcauthboeCK;
import kraheja.enggsys.lcsystem.mapper.AuthorizationMapper;
import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;
import kraheja.enggsys.lcsystem.payload.request.LcDetails;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AuthorizationMapperImpl implements AuthorizationMapper {

	@Override
	public AuthorizationRequest lcauthRequestMap(Lcauth lcauth, List<Lcauthboe> lcauthboe) {
		List<LcDetails> lcAuthboeList = new ArrayList<>();
		
		for (Lcauthboe authboe : lcauthboe) {
			lcAuthboeList.add(LcDetails.builder()
					.srlNo(authboe.getLcauthboeCK().getLcabSrno())
					.boeNo(authboe.getLcabBoeno())
					.boeDate(authboe.getLcabBoedate())
					.pendingItems(authboe.getLcabPendingitems() != null ? authboe.getLcabPendingitems() : 0)
					.docNo(authboe.getLcabDocumentno())
					.docDate(authboe.getLcabDocumentdate())
					.epcgNo(authboe.getLcabEpcgno())
					.epcgDate(authboe.getLcabEpcgdate())
					.dutyFreeNo(authboe.getLcabDutyfreeno())
					.dutyFreeDate(authboe.getLcabEpcgdate())
					.lcNo(authboe.getLcabLcno())
					.inspectionDate(authboe.getLcabInspectiondate())
					.shipDate(authboe.getLcabLastshipmentdate())
					.docsRecdDate(authboe.getLcabShipdocrecddate())
					.build());
		}
		
		return AuthorizationRequest.builder()
		.result(Result.SUCCESS)
		.responseCode(ApiResponseCode.SUCCESS)
		.message(ApiResponseMessage.FETCH_SUCCESSFULLY)
//		.supplierResponse(SupplierDBResponse
//				.builder()
//				.bldgCoy(lcauth.getLcahCoy())
//				.bldgProp(lcauth.getLcahProp())
//				.bldgProject(lcauth.getLcahProject())
//				.bldgMisproject(lcauth.getLcahMisproject())
//				.bldgProperty(lcauth.getLcahProperty())
//				.bldgMisbldg(lcauth.getLcahMisbldg())
//				.build())
		.tranType("E")
		.preparedBy(lcauth.getLcahPreparedby())
		.noOfDays(lcauth.getLcahNoofdays())
		.uom(lcauth.getLcahUom())
		.authDate(lcauth.getLcahAuthdate())
		.payMode(lcauth.getLcahPaymode())
		.masterAuthNo(lcauth.getLcahMasterauthno())
		.matGroup(lcauth.getLcauthCK().getLcahMatgroup())
		.currency(lcauth.getLcahCurrency())
		.quantity(lcauth.getLcahAuthquanty())
		.authAmount(lcauth.getLcahAuthamount())
		.bankCharges(lcauth.getLcahBankcharges())
		.payAmount(lcauth.getLcahPayamount())
		.documentNo(lcauth.getLcahDocumentno())
		.documentDate(lcauth.getLcahDocumentdate())
		.category(lcauth.getLcahCategory())
		.lcNo(lcauth.getLcahLcno())
		.fileNo(lcauth.getLcahFileno())
		.countryOfOrigin(lcauth.getLcahOrigcountry())
		.cityOfOrigin(lcauth.getLcahOrigcity())
		.remarks(lcauth.getLcahRemarks())
		.purpose(lcauth.getLcahDescription())
		.debitParty(lcauth.getLcahDebitingparty())
		.debitCurrency(lcauth.getLcahDebitingcurr())
		.debitAmount(lcauth.getLcahDebitingamt())
		.convRate(lcauth.getLcahDebitingcurrconvrate())
		.debitReason(lcauth.getLcahDebitingreason())
		.lcOpenDate(lcauth.getLcahLcopendate())
		.lcExpiryDate(lcauth.getLcahExpirydate())
		.lastShipmntDate(lcauth.getLcahLastshipmentdate())
		.epcgNo(lcauth.getLcahEpcgno())
		.dutyFreeNo(lcauth.getLcahDutyfreeno())
		.epcgDate(lcauth.getLcahEpcgdate())
		.masterAuthYN(lcauth.getLcahMasterlcyn())
		.status(lcauth.getLcahAuthstatus())
		.lcDetailsList(lcAuthboeList)
		.build();
	}

	@Override
	public Lcauth requestLcauthMap(AuthorizationRequest request) {
		String siteName = GenericAuditContextHolder.getContext().getSite();
		String userId = GenericAuditContextHolder.getContext().getUserid();
		SupplierDBResponse supplier = request.getSupplierResponse();
		return Lcauth.builder()
				.lcauthCK(LcauthCK.builder()
//						.lcahBldgcode(supplier.getBldgCoy())
						.lcahMatgroup(request.getMatGroup())
//						.lcahPartycode(null) // set into service class
//						.lcahAuthnum(null)
//						.lcahAuthtype(null)
						.build())
				.lcahPreparedby(request.getPreparedBy())
				.lcahAuthamount(request.getAuthAmount())
				.lcahAuthdate(request.getAuthDate())
				.lcahAuthquanty(request.getQuantity())
				.lcahAuthstatus("1")
				.lcahBankcharges(request.getBankCharges())
				.lcahBldgauthno(request.getBldgAuthNo())
				.lcahCategory(request.getCategory())
				.lcahCoy(supplier.getBldgCoy())
				.lcahCurrency(request.getCurrency())
				.lcahDebitingamt(request.getDebitAmount())
				.lcahDebitingcurr(request.getDebitCurrency())
				.lcahDebitingcurrconvrate(request.getConvRate())
				.lcahDebitingparty(request.getDebitParty())
				.lcahDebitingreason(request.getDebitReason())
				.lcahDescription(request.getPurpose())
				.lcahDocumentdate(request.getDocumentDate())
				.lcahDocumentno(request.getDocumentNo())
				.lcahDutyfreedate(request.getDutyFreeDate())
				.lcahDutyfreeno(request.getDutyFreeNo())
				.lcahEpcgdate(request.getEpcgDate())
				.lcahEpcgno(request.getEpcgNo())
				.lcahEpcg_Dutyfree(request.getEpcgDutyfree())
				.lcahExpirydate(request.getLcExpiryDate())
				.lcahFileno(request.getFileNo())
				.lcahLcno(request.getLcNo())
				.lcahLcopendate(request.getLcOpenDate())
//				.lcahLicdate(request.getLcOpenDate())
//				.lcahLicnum(request.getLcNo())
				.lcahMasterauthno(request.getMasterAuthNo())
				.lcahMasterlcyn(request.getMasterAuthYN())
				.lcahMisbldg(supplier.getBldgMisbldg())
				.lcahMisproject(supplier.getBldgMisproject())
				.lcahNoofdays(request.getNoOfDays())
				.lcahOrigcity(request.getCityOfOrigin())
				.lcahOrigcountry(request.getCountryOfOrigin())
				.lcahOrigsite(siteName)
				.lcahPartytype("S")
				.lcahPayamount(request.getPayAmount())
				.lcahPaymode(request.getPayMode())
//				.lcahPrinted(0)
				.lcahProject(supplier.getBldgProject())
				.lcahProp(supplier.getBldgProp())
				.lcahProperty(supplier.getBldgProperty())
				.lcahRemarks(request.getRemarks())
				.lcahSite(siteName)
				.lcahToday(LocalDateTime.now())
				.lcahUom(request.getUom())
				.lcahUserid(userId)
//				.lcahPrvauthamt(null)
//				.lcahPayref(null)
//				.lcahPaydate(null)
//				.lcahPrintedon(null)
//				.lcahPartyauth(LCAH_PARTYCODE)
//				.lcahPrvauthno(null)
//				.lcahPrvauthqty(null)
//				.lcahPrvdate(null)
//				.lcahPrvtype(null)
//				.lcahPurauthnum(null)
//				.lcahOprauthamt(null)
//				.lcahOprauthqty(null)
//				.lcahInspectiondate(request.in)
//				.lcahLastshipmentdate(null)
//				.lcahShipdocrecddate(null)
//				.lcahTranser(null)
//				.lcahMatbldauth(null)
				.build();
	}

	public List<Lcauthboe> lcDetailToLcauthboe(List<LcDetails> lcDetailsList, String authNum, SupplierDBResponse supplier, String bldgCode, String docNo, LocalDate docDate){
		String siteName = GenericAuditContextHolder.getContext().getSite();
		String userId = GenericAuditContextHolder.getContext().getUserid();
		List<Lcauthboe> lcauthBoeList = new ArrayList<>();
		Integer slrNo = 1 ;
		for (LcDetails lcDetails : lcDetailsList) {
			log.debug("lcDetails: {}", lcDetails);
			if (StringUtils.isNoneEmpty(lcDetails.getDutyFreeNo())) {
				lcDetails.setEpcgNo("DUTYFREE");
			}
			lcauthBoeList.add(Lcauthboe.builder()
					.lcauthboeCK(LcauthboeCK.builder().lcabAuthnum(authNum).lcabSrno(slrNo).build())
					.lcabBldgcode(bldgCode)
					.lcabBoedate(lcDetails.getBoeDate())
					.lcabBoeno(lcDetails.getBoeNo())
					.lcabCoy(supplier.getBldgCoy())
					.lcabDocumentdate(docDate)
					.lcabDocumentno(docNo)
					.lcabDutyfreedate(lcDetails.getDutyFreeDate())
					.lcabDutyfreeno(lcDetails.getDutyFreeNo())
					.lcabEpcgdate(lcDetails.getEpcgDate())
					.lcabEpcgno(lcDetails.getEpcgNo())
					.lcabInspectiondate(lcDetails.getInspectionDate())
					.lcabInstcertsubmdt(lcDetails.getInstCertSubmdt())
					.lcabLastshipmentdate(lcDetails.getShipDate())
					.lcabLcno(lcDetails.getLcNo())
					.lcabOrigsite(siteName)
					.lcabPendingitems(lcDetails.getPendingItems() != null ? lcDetails.getPendingItems() : 0)
					.lcabProject(supplier.getBldgProject())
					.lcabShipdocrecddate(lcDetails.getDocsRecdDate())
					.lcabSite(siteName)
					.lcabToday(LocalDateTime.now())
					.lcabUserid(userId)
					.build());
			slrNo++;
		}
		return lcauthBoeList;
	}
}
