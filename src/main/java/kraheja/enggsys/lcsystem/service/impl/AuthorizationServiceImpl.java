package kraheja.enggsys.lcsystem.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.constant.ApiResponseCode;
import kraheja.constant.ApiResponseMessage;
import kraheja.constant.Result;
import kraheja.enggsys.entity.Lcauth;
import kraheja.enggsys.entity.Lcauthboe;
import kraheja.enggsys.lcsystem.mapper.AuthorizationMapper;
import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;
import kraheja.enggsys.lcsystem.payload.request.LcDetails;
import kraheja.enggsys.lcsystem.service.AuthorizationService;
import kraheja.enggsys.repository.LcauthRepository;
import kraheja.enggsys.repository.LcauthboeRepository;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {
	
	private final BuildingRepository buildingRepository;
	private final LcauthRepository lcauthRepository;
	private final LcauthboeRepository lcauthboeRepository;
	private final AuthorizationMapper authorizationMapper;
	
	public AuthorizationServiceImpl(BuildingRepository buildingRepository, LcauthRepository lcauthRepository, 
			LcauthboeRepository lcauthboeRepository, AuthorizationMapper authorizationMapper) {
		this.buildingRepository = buildingRepository;
		this.lcauthRepository = lcauthRepository;
		this.lcauthboeRepository = lcauthboeRepository;
		this.authorizationMapper = authorizationMapper;
	}

	@Override
	public SupplierDBResponse getSupplierDetail(String supplier, String building, String authType, String authnum) {
		return buildingRepository.findSupplierDetails(building);
	}

	@Override
	public AuthorizationRequest retrieveAuthorization(String supplier, String building, String authType, String authnum) {
		Lcauth lcauth = lcauthRepository.findLcauthByLcauthCKLcahAuthnum(authnum);
		log.debug("lcauth: {}", lcauth);
		
		List<Lcauthboe> lcauthboe = lcauthboeRepository.findLcauthboeByLcauthboeCKLcabAuthnum(authnum);
		log.debug("LC Auth Boe: {}", lcauthboe);
		
		return authorizationMapper.lcauthRequestMap(lcauth, lcauthboe);
	}

	@Override
	public GenericResponse makeAuthorization(AuthorizationRequest request, String supplier, String building,String authType, String authnum) {
		log.debug("authorization request obtaint: {}", request);
		
		Lcauth prvLcauth = this.fetchLastAuthorizationData(supplier, building,authType, authnum,request.getTranType());
		log.debug("prvLcauth db response obtaint: {}", prvLcauth);
		
		SupplierDBResponse supplierDetail = this.getSupplierDetail(supplier, building, authType, authnum);
		request.setSupplierResponse(supplierDetail);
		
		if (request.getTranType().equals("N")) {
			authnum = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "LCAUT");
			log.debug("created new certificate authnum: {}", authnum);
		}
		List<LcDetails> lcDetailsList = request.getLcDetailsList();
		log.debug("lcDetailsList request obtaint: {}", lcDetailsList);
		
		Lcauth lcauth = authorizationMapper.requestLcauthMap(request);
		log.debug("afetr converted authorization request to lcauth obtaint: {}", lcauth);
		lcauth.getLcauthCK().setLcahAuthnum(authnum);
		lcauth.getLcauthCK().setLcahBldgcode(building);
		lcauth.getLcauthCK().setLcahPartycode(supplier);
		lcauth.getLcauthCK().setLcahAuthtype(authType);
		
		// SET PRIVIOUS DATA
		lcauth.setLcahPrvauthno(prvLcauth.getLcauthCK().getLcahAuthnum());
		lcauth.setLcahPrvdate(prvLcauth.getLcahAuthdate());
		lcauth.setLcahPrvtype(prvLcauth.getLcauthCK().getLcahAuthtype());
		lcauth.setLcahPrvauthamt(prvLcauth.getLcahAuthamount().intValue());
		lcauth.setLcahPrvauthqty(prvLcauth.getLcahAuthquanty());
		
		List<Lcauthboe> lcauthboe = authorizationMapper.lcDetailToLcauthboe(lcDetailsList, authnum, supplierDetail, building, request.getDocumentNo(), request.getDocumentDate());
		log.debug("afetr converted authorization request to lcauthboe obtaint: {}", lcauthboe);
		
		List<Lcauthboe> saveAll = lcauthboeRepository.saveAll(lcauthboe);
		log.debug("saveAll: {}", saveAll);
		lcauthRepository.save(lcauth);
		log.debug("lcauth: {}", lcauth);
		return GenericResponse.builder().result(Result.SUCCESS).responseCode(ApiResponseCode.SUCCESS).message(ApiResponseMessage.SUCCESSFULLY_PERSIST).build();
	}

	private Lcauth fetchLastAuthorizationData(String supplier, String building, String authType, String authnum, String tranType) {
//		String maxAuthNum = authnum;
//		if (tranType.equalsIgnoreCase("N")) {
//			maxAuthNum = lcauthRepository.findLastLcauthNumBySupplierAndBuildingAndAuthType(supplier,building,authType);
//			log.debug("maxAuthNum : {}", maxAuthNum);
//		}
		return lcauthRepository.findLastLcauthBySupplierAndBuildingAndAuthTypeAuthnum(authnum);
	}

	@Override
	public String fetchlastauthNumber(String supplier, String building, String authType) {
		String maxAuthNum = lcauthRepository.findLastLcauthNumBySupplierAndBuildingAndAuthType(supplier,building,authType);
		log.debug("maxAuthNum : {}", maxAuthNum);
		return maxAuthNum;
	}

}
