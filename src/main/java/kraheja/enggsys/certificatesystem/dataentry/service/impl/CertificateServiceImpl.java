
package kraheja.enggsys.certificatesystem.dataentry.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.BigIntegers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.response.PartyAddressResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.enums.EntityClassEnum;
import kraheja.commons.enums.ErrorMessageEnum;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.EpworksRepository;
import kraheja.commons.repository.GlchartRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.enggsys.bean.CertificateDetailBean;
import kraheja.enggsys.bean.TdsexemptBean;
import kraheja.enggsys.bean.request.CbillhRequestBean;
import kraheja.enggsys.bean.request.CdbnotedRequestBean;
import kraheja.enggsys.bean.request.CdbnotehRequestBean;
import kraheja.enggsys.bean.request.CertificateRequestBean;
import kraheja.enggsys.bean.request.ContractRequestBean;
import kraheja.enggsys.bean.request.ContractdebitRequestBean;
import kraheja.enggsys.bean.response.CbillhResponseBean;
import kraheja.enggsys.bean.response.CdbnotehResponseBean;
import kraheja.enggsys.bean.response.CertificateEntryResponseBean;
import kraheja.enggsys.bean.response.CertificateEntryResponseBean.CertificateEntryResponseBeanBuilder;
import kraheja.enggsys.bean.response.ContractDebitNoteResponse;
import kraheja.enggsys.bean.response.ContractResponseBean;
import kraheja.enggsys.bean.response.ContractdebitResponseBean;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CbdnotedMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CbillhMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CdbnotehMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CertMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.CertworknarrdtlMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.ContractMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.ContractdebitMapper;
import kraheja.enggsys.certificatesystem.dataentry.mappers.TdsexemptMapper;
import kraheja.enggsys.certificatesystem.dataentry.service.CertificateService;
import kraheja.enggsys.entity.Cbilld;
import kraheja.enggsys.entity.Cbillh;
import kraheja.enggsys.entity.Cdbnoted;
import kraheja.enggsys.entity.Cdbnoteh;
import kraheja.enggsys.entity.Cert;
import kraheja.enggsys.entity.Certdetails;
import kraheja.enggsys.entity.Certworknarrdtl;
import kraheja.enggsys.entity.Contract;
import kraheja.enggsys.entity.Contractdebit;
import kraheja.enggsys.entity.Tdsexempt;
import kraheja.enggsys.entity.Tdsexempt.TdsexemptCK;
import kraheja.enggsys.enums.CertStatusEnum;
import kraheja.enggsys.enums.CertTypeEnum;
import kraheja.enggsys.repository.CbilldRepository;
import kraheja.enggsys.repository.CbillhRepository;
import kraheja.enggsys.repository.CdbnotedRepository;
import kraheja.enggsys.repository.CdbnotehRepository;
import kraheja.enggsys.repository.CertRepository;
import kraheja.enggsys.repository.CertdetailsRepository;
import kraheja.enggsys.repository.CertworknarrdtlRepository;
import kraheja.enggsys.repository.ContractRepository;
import kraheja.enggsys.repository.ContractdebitRepository;
import kraheja.enggsys.repository.TdsexemptRepository;
import kraheja.purch.entity.Advrecvoucher;
import kraheja.purch.materialpayments.mappers.AdvrecvoucherEntityPojoMapper;
import kraheja.purch.repository.AdvrecvoucherRepository;
import kraheja.purch.repository.AuthHRepository;
import kraheja.purch.repository.MatcertlnhdrRepository;


@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired
	private ActranhRepository actranhRepository;
	
	@Autowired
	private CbilldRepository cbilldRepository;

	@Autowired
	private ActrandRepository actrandRepository;
	
	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private CertRepository certRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private TdsexemptRepository tdsexemptRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CertworknarrdtlRepository certworknarrdtlRepository;

	@Autowired
	private AdvrecvoucherRepository advrecvoucherRepository;

	@Autowired
	private CertdetailsRepository certdetailsRepository;

	@Autowired
	private ContractdebitRepository contractdebitRepository;

	@Autowired
	private CbillhRepository cbillhRepository;

	@Autowired
	private AuthHRepository authHRepository;
	
	@Autowired
	private CdbnotehRepository cdbnotehRepository;
	
	@Autowired
	private CdbnotedRepository cdbnotedRepository;

	@Autowired
	private EpworksRepository epworksRepository;
	
	@Autowired
	private MatcertlnhdrRepository matcertlnhdrRepository;
	
	private static GlchartRepository glchartRepository;


	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractDetailsByRecId(String recId) {
		Contract contractEntity = this.contractRepository.findByContractCK_ConttContract(recId);
		LOGGER.info("Contract Entity :: {}", contractEntity);

		if(Objects.nonNull(contractEntity)) {
			Party partyEntity = this.partyRepository.findByPartyCodeAndParClosedateAndParPartytype(contractEntity.getConttPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), contractEntity.getConttPartytype().trim());
			LOGGER.info("Party :: {}", partyEntity);

			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(contractEntity.getConttPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype);
			LOGGER.info("Address :: {}", addressEntity);

			String township = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("TOWN", addressEntity.getAdrTownship());
			String city = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("CITY", addressEntity.getAdrCity());
			city = township.concat(CommonConstraints.INSTANCE.COMMA_STRING).concat(city);					
			ContractResponseBean contractResponseBean = ContractMapper.fetchContractEntityPojoMapper.apply(new Object[] {contractEntity, partyEntity, addressEntity, city});
			return ResponseEntity.ok(ServiceResponseBean.builder().data(contractResponseBean).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchCoynameForEnggsys(String bldgcode) {

		Query createNativeQueryForCoycode = this.entityManager.createNativeQuery("select distinct bmap_coy2 from bldgmap where trim(bmap_ebldgcode) = :bldgcode");
		createNativeQueryForCoycode.setParameter("bldgcode", bldgcode.trim());
		String coycode = (String) createNativeQueryForCoycode.getSingleResult();

		Query createNativeQueryForCoyname = this.entityManager.createNativeQuery("select distinct cmap_ecoyname from coymap WHERE trim(cmap_ecoycode) = :coycode");
		createNativeQueryForCoyname.setParameter("coycode", coycode.trim());
		String coyname = (String) createNativeQueryForCoyname.getSingleResult();
		if(StringUtils.isNotBlank(coyname) && StringUtils.isNotBlank(coycode)) {

			return ResponseEntity.ok(ServiceResponseBean.builder().data(new HashMap<String, String>() {{{
				put("coycode", coycode);
				put("coyname", coyname);
			}}}).status(Boolean.TRUE).build()); 
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchCertnumRecid(String recid) {
		String certnum = this.certRepository.findMaxCertNumByRecId(recid.trim());
		if(StringUtils.isNotEmpty(certnum))
			return ResponseEntity.ok(ServiceResponseBean.builder().data(certnum).status(Boolean.TRUE).build()); 
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> addContractDetail(ContractRequestBean contractRequestBean) {
		if(!contractRequestBean.getTdsptypeCheck()) {
			Contract contractEntity = this.contractRepository.findByConttBldgcodeAndConttCoyAndConttWorkcodeAndConttConttor(contractRequestBean.getBldgcode().trim(), contractRequestBean.getCoy().trim(), contractRequestBean.getWorkcode().trim(), contractRequestBean.getConttor().trim());
			LOGGER.info("Contract Entity :: {}", contractEntity);
			if(Objects.nonNull(contractEntity))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Rec ID : " + contractEntity.getContractCK().getConttContract().trim() +" already exists for the above combination...").build());
		}
		Query tdsPercQuery = this.entityManager.createNativeQuery("select ent_num1 from entity where ent_class = 'TDSTP' and trim(ent_id) = :partytype and ent_date2 = to_date('01-01-2050','dd.mm.yyyy')" +
				"AND ent_char2 = CASE FUNC_GetCompanyType('E',:contractorcode,'01.Jan.2050') WHEN 'Individual' THEN 'INHUF     ' WHEN 'HUF' THEN 'INHUF     ' "+
				"WHEN 'NO PAN' THEN 'NO PAN    ' ELSE 'OTHER     ' END  AND ent_char3 IS NULL AND sysdate BETWEEN ent_date1 AND ent_date2");

		tdsPercQuery.setParameter("partytype", contractRequestBean.getTdsptype().trim());
		tdsPercQuery.setParameter("contractorcode", contractRequestBean.getConttor().trim());
		Object singleResult = null;
		try {
			singleResult = tdsPercQuery.getSingleResult();
		}catch(NoResultException e) {}

		Double tdsPerc = Objects.nonNull(singleResult) ? ((BigDecimal)singleResult).doubleValue() : null;
		LOGGER.info("SingleResult :: ", singleResult);
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(contractRequestBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), contractRequestBean.getPartytype().trim());
		LOGGER.info("Party :: {}", partyEntity);

		Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(contractRequestBean.getPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype);
		LOGGER.info("Address :: {}", addressEntity);

		String township = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("TOWN", addressEntity.getAdrTownship());
		String city = this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("CITY", addressEntity.getAdrCity());
		city = township.concat(CommonConstraints.INSTANCE.COMMA_STRING).concat(city);

		PartyAddressResponseBean partyAddressResponseBean = PartyMapper.fetchPartyAndAddressEntityPojoMapper.apply(partyEntity, addressEntity);
		partyAddressResponseBean.getAddressResponseBean().setCity(city);

		return ResponseEntity.ok(ServiceResponseBean.builder().data(partyAddressResponseBean).extraData(tdsPerc).status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> saveContractDetail(ContractRequestBean contractRequestBean) {
		Contract contractEntity = null;
		String contractId = CommonConstraints.INSTANCE.BLANK_STRING;
		if(contractRequestBean.getIsUpdate()) {
			contractEntity = this.contractRepository.findByContractCK_ConttContract(contractRequestBean.getContract().trim());
			if(Objects.isNull(contractEntity))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());	
			else
				contractId = contractEntity.getContractCK().getConttContract();
		}else {
			contractId = GenericCounterIncrementLogicUtil.generateTranNoWithSite(EntityClassEnum.NSER.getValue(), "#CID", GenericAuditContextHolder.getContext().getSite());
		}
		LOGGER.info("Contract Entity :: {}", contractEntity);

		//Validations and calculations
		Double orgTdsPerc = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId("TDSTP", null, contractRequestBean.getPartytype());
		if(Objects.isNull(orgTdsPerc))
			orgTdsPerc = BigInteger.ONE.doubleValue() * -1; // if returned null from database then this is done so it doesn't throw null pointer error

		// **If TDS percentage is exempted or reduced, then follwoing check is applicable ...
		if((contractRequestBean.getTdsper() < orgTdsPerc) && contractRequestBean.getTdsper() > BigInteger.ZERO.intValue()) {
			if(StringUtils.isEmpty(contractRequestBean.getTdsfrom()))
				return ResponseEntity.ok(ServiceResponseBean.builder().message("TDS Applicable from date should be entered.. ").status(Boolean.FALSE).build());

			if(StringUtils.isEmpty(contractRequestBean.getTdsupto()))
				return ResponseEntity.ok(ServiceResponseBean.builder().message("TDS Applicable upto date should be entered.. ").status(Boolean.FALSE).build());

			Tdsexempt tdsexemptEntity = this.tdsexemptRepository.findByTdsexemptCK_TdsxContract(contractId);
			if(Objects.isNull(tdsexemptEntity))
				this.tdsexemptRepository.save(Tdsexempt.builder()
						.tdsexemptCK(TdsexemptCK.builder().tdsxContract(contractId).build())
						.tdsxExmpref(contractId)
						.tdsxOrigsite(GenericAuditContextHolder.getContext().getSite())
						.tdsxSite(GenericAuditContextHolder.getContext().getSite())
						.tdsxTdsfrom(LocalDate.parse(contractRequestBean.getTdsfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.tdsxTdsper(contractRequestBean.getTdsper())
						.tdsxTdsupto(LocalDate.parse(contractRequestBean.getTdsupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.tdsxToday(LocalDateTime.now())
						.tdsxUserid(GenericAuditContextHolder.getContext().getUserid())
						.build());
			else {
				//If found by contract id then update the row
				this.tdsexemptRepository.save(TdsexemptMapper.updateTdsexemptEntityPojoMapper.apply(tdsexemptEntity, TdsexemptBean.builder()
						.tdsper(contractRequestBean.getTdsper())
						.tdsfrom(contractRequestBean.getTdsfrom())
						.tdsupto(contractRequestBean.getTdsupto())
						.build()));
			}
		}

		if(contractRequestBean.getContractorname().length() > 30)
			contractRequestBean.setContractorname(contractRequestBean.getContractorname().substring(0, 30));

		if(contractRequestBean.getIsUpdate()) {
			this.contractRepository.save(ContractMapper.updateContractEntityPojoMapper.apply(contractEntity, contractRequestBean));	
			return ResponseEntity.ok(ServiceResponseBean.builder().message("Rec ID updated successfully :" + contractEntity.getContractCK().getConttContract()).status(Boolean.TRUE).build());
		}else {
			String proprietorCode = this.companyRepository.findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(contractRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());
			Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(contractRequestBean.getBldgcode().trim());
			contractRequestBean.setProprietor(proprietorCode);
			contractRequestBean.setContract(contractId);
			contractRequestBean.setProject(Objects.nonNull(buildingEntity) ? buildingEntity.getBldgProject() : null);
			this.contractRepository.save(ContractMapper.addContractPojoEntityMapper.apply(contractRequestBean));	
			return ResponseEntity.ok(ServiceResponseBean.builder().message("Successfully created new Rec ID :" + contractId).status(Boolean.TRUE).build());
		}
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractBillDetailBySer(String ser) {
		Cbillh cbillhEntity = this.cbillhRepository.findByCblhSer(ser.trim());
		if(Objects.nonNull(cbillhEntity)) {
			List<Cbilld> cbilldEntityList = this.cbilldRepository.findByCbilldCK_CbldSer(ser.trim());
			if(CollectionUtils.isNotEmpty(cbilldEntityList)) {
				Query query = this.entityManager.createNativeQuery("SELECT ent_char2, ent_date3 FROM entity WHERE trim(ent_class) = 'GSTFM' AND trim(ent_id) = '01'", Tuple.class);
				Tuple tuple = (Tuple)query.getSingleResult();

				String strAllowBillUpdate = tuple.get(0, String.class);
				LocalDate dateAllowBillUpdate = tuple.get(1, Timestamp.class).toLocalDateTime().toLocalDate();
				if(StringUtils.isNotBlank(strAllowBillUpdate) && strAllowBillUpdate.equals("NOBILLEDIT")){
					Actranh actranhEntity = this.actranhRepository.findByActranhCK_ActhTranser(ser.trim());
					LocalDate billEntryDate = Objects.nonNull(actranhEntity) ? actranhEntity.getActhTrandate().toLocalDate() : null;
					if(Objects.nonNull(dateAllowBillUpdate) && !dateAllowBillUpdate.equals(billEntryDate))
						if(YearMonth.from(billEntryDate).compareTo(YearMonth.now()) < 0)
							return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please reverse the bill and reenter. You can't modify Prior month's bills.").build()); 
				}
				Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(cbillhEntity.getCblhCoy().trim(), CommonUtils.INSTANCE.closeDate());
				if(Objects.nonNull(companyEntity) && StringUtils.isNotBlank(companyEntity.getCoyGstno())) {
					Party partyEntity = this.partyRepository.findLimitedPartyByCode(cbillhEntity.getCblhPartycode().trim(), cbillhEntity.getCblhPartycode().trim());  
					// If LTD or LIMITED party
					if(Objects.nonNull(partyEntity) && StringUtils.isBlank(partyEntity.getPartyCk().getParPartycode())){
						LocalDate durFrom, durUpto;
						if(Objects.nonNull(cbillhEntity.getCblhDurfrom()))
							durFrom = cbillhEntity.getCblhDurfrom();
						else
							durFrom = LocalDate.of(1956, 01, 01);

						if(Objects.nonNull(cbillhEntity.getCblhDurto()))
							durUpto = cbillhEntity.getCblhDurto();
						else
							durUpto = LocalDate.of(2050, 01, 01);

						Query countQuery = this.entityManager.createNativeQuery("SELECT count(*) FROM entity WHERE ent_class = 'GSTRC' AND ent_char2 = :workcode AND (Format(:durFrom, 'dd/MMM/yyyy') BETWEEN ent_date1 AND ent_date2 OR Format(:durUpto, 'dd/MMM/yyyy') BETWEEN ent_date1 AND ent_date2");
						countQuery.setParameter("workcode", cbillhEntity.getCblhWorkcode().trim());
						countQuery.setParameter("durFrom", durFrom);
						countQuery.setParameter("durUpto", durUpto);

						Object singleResult = countQuery.getSingleResult();
						if(Integer.valueOf(singleResult.toString()) != 0) {
							String gstRevChgYN = "Y";
							//gsttype calc logic not written
						}
					}
				}

				CbillhResponseBean setMasterDataResponse = setMasterData(cbillhEntity, null);
				CbillhResponseBean cbillhResponseBean = CbillhMapper.fetchCbillhEntityPojoMapper.apply(new Object[] {cbillhEntity, cbilldEntityList});
				cbillhResponseBean.setGstno(setMasterDataResponse.getGstno());
				cbillhResponseBean.setStatecode(setMasterDataResponse.getStatecode());
				cbillhResponseBean.setStatename(setMasterDataResponse.getStatename());
				cbillhResponseBean.setBalanceAdvance(setMasterDataResponse.getBalanceAdvance());
				cbillhResponseBean.setAdvanceadj(setMasterDataResponse.getAdvanceadj());
				cbillhResponseBean.setTotalAdvance(setMasterDataResponse.getTotalAdvance());

				return ResponseEntity.ok(ServiceResponseBean.builder().data(cbillhResponseBean).status(Boolean.TRUE).message(Objects.nonNull(setMasterDataResponse.getIsError()) && setMasterDataResponse.getIsError() ? setMasterDataResponse.getErrorMessage() : null).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> addNewContractBillDetailBySer(ContractRequestBean contractRequestBean) {
		Cert certEntity = this.certRepository.fetchByContractIdAndCertTypeNotAndPassedOn(contractRequestBean.getContract().trim(), CertTypeEnum.M.toString(), null);
		if(Objects.nonNull(certEntity)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Previous certificates are not passed for this contract.	").build());
		}

		Tuple tuple = null;//this.contractRepository.findCodesByContract(contractRequestBean.getContract());
		String workcode = tuple.get(0, String.class);
		String partycode = tuple.get(1, String.class);
		String project = tuple.get(2, String.class);
		String bldgtype = tuple.get(3, String.class);

		//logic note
		if(bldgtype.equals("H") || bldgtype.equals("M")) {
			Integer matcertlnhdrCount = this.matcertlnhdrRepository.findMatcertlnhdrCountByProjCode(project);   
			LOGGER.info(" matcertlnhdrCount :{} ", matcertlnhdrCount);
			Query getVendormatcertamtdtlsCountQuery = entityManager.createNativeQuery("SELECT count(*) FROM v_lnvendormatcertamtdtls  WHERE mclh_projcode = '".concat(project).concat("' AND mclw_matcerttype = '").concat("Material").concat("' AND mclw_matcertcode = '").concat(workcode).concat("'"));
			LOGGER.info("getVendormatcertamtdtlsCountQuery :{} ", getVendormatcertamtdtlsCountQuery.getSingleResult());
			Integer matcertAmtdlsCount = ((Number) getVendormatcertamtdtlsCountQuery.getSingleResult()).intValue();
			LOGGER.info(" matcertAmtdlsCount :{} ", matcertAmtdlsCount);
			if(matcertlnhdrCount > 0 && matcertAmtdlsCount >0) {
				Query checkLogicNoteExistsQuery = entityManager.createNativeQuery("SELECT mclh_logicnotenum , finalbillcloseyn FROM v_lnvendormatcertamtdtls WHERE mclh_projcode = '".concat(project)
						.concat("'  AND mclw_matcerttype = '").concat("Material").concat("' AND mclw_matcertcode = '").concat(workcode).concat("' AND mcvh_partytype = '").concat("S").concat("' AND mcvh_partycode = '").concat(partycode).concat("'"));				   
				Tuple logicNoteQueryResult = (Tuple) checkLogicNoteExistsQuery.getResultList();
				if(Objects.isNull(logicNoteQueryResult)) {
					// Don't allow certificate for any project and party if not there in entity and which is not there in logic note
					String entityChar1 = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1_EntChar3(partycode, project);
					String entityPartyCode = Objects.nonNull(entityChar1) ? entityChar1 : CommonConstraints.INSTANCE.BLANK_STRING;
					if(!entityPartyCode.equals(partycode)) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Logic note for this project+vendor is not entered. Please check.....").build());
					}
				}
				else {
					String finalBillCloseYN = Objects.nonNull(logicNoteQueryResult.get(1, String.class)) ? logicNoteQueryResult.get(1, String.class) : CommonConstraints.INSTANCE.BLANK_STRING;
					LOGGER.info("FinalBill Close Y/N : {}", finalBillCloseYN);
					if(finalBillCloseYN.trim().equals("Y")) {
						//' Don't allow other than F & L type certificate if final bill is made
						String finalBillCloseFromEntity = this.entityRepository.findEntityForFinalBill();
						if(finalBillCloseFromEntity.equals("N")) {
							return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Contract bill entry is not allowed as Final billing is already done.....").build());
						}
					}
				}
			}
		}
		
		Cbillh cbillhEntity = this.cbillhRepository.findByCblhContract(contractRequestBean.getContract().trim());
		if(Objects.nonNull(cbillhEntity)) {
			List<Cbilld> cbilldEntityList = this.cbilldRepository.findByCbilldCK_CbldSer(cbillhEntity.getCbillhCK().getCblhSer().trim());
			if(Objects.nonNull(cbilldEntityList)) {
				
			}
		}
		
		// call set master function 
		//call common gst type api
		
		return null;
	}
	
	private CbillhResponseBean setMasterData(Cbillh cbillhEntity, CbillhRequestBean cbillhRequestBean) {
		CbillhResponseBean cbillhResponseBean = CbillhResponseBean.builder().build();
		//when retreive
		if(Objects.nonNull(cbillhEntity)) {
			cbillhResponseBean.setContract(cbillhEntity.getCblhContract());
			cbillhResponseBean.setCoy(cbillhEntity.getCblhCoy());
			cbillhResponseBean.setPartycode(cbillhEntity.getCblhPartycode());
			cbillhResponseBean.setPartytype(cbillhEntity.getCblhPartytype());
		}
		//when add
//		else {
//			
//		}
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(cbillhResponseBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.E.toString());

		if(Objects.nonNull(partyEntity)) {
			if(StringUtils.isBlank(partyEntity.getParGstno())) {
				cbillhResponseBean.setIsError(Boolean.TRUE);	
				cbillhResponseBean.setErrorMessage("GST No is not entered for this party. \r\n If you want to update GST No then exit from this screen and update in party entry screen");
			}
			cbillhResponseBean.setGstno(partyEntity.getParGstno());
		}
		Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtype(cbillhResponseBean.getPartycode().trim(), AdSegment.PARTY.toString(), AdType.LOC.toString());

		String state = CommonConstraints.INSTANCE.BLANK_STRING;
		if(Objects.nonNull(addressEntity)) {
			state = Objects.nonNull(addressEntity.getAdrState()) ? this.entityRepository.findEntNameEntityCk_EntClassAndEntityCk_EntId("STATE", addressEntity.getAdrState().trim()) : null;
		}
		cbillhResponseBean.setStatecode(addressEntity.getAdrState());
		cbillhResponseBean.setStatename(state);
		
		Query advanceQuery = this.entityManager.createNativeQuery("SELECT sum(cert_certamount) AS AdvAmt FROM cert WHERE trim(cert_contract) = :contractid AND trim(cert_certtype) = 'A' AND trim(cert_certstatus) > '4'");
		advanceQuery.setParameter("contractid", cbillhResponseBean.getContract().trim());
		Object singleResult = advanceQuery.getSingleResult();
		Double advance =  Objects.nonNull(singleResult) ? Double.valueOf(singleResult.toString()) : BigInteger.ZERO.doubleValue();
		
		Query advanceAdjQuery = this.entityManager.createNativeQuery("SELECT sum(cblh_advanceadj) + (SELECT Nvl(sum(cert_advadjusted),0) FROM cert WHERE trim(cert_contract) = :contractid AND TRIM(cert_certtype) not in ('A', 'L') AND trim(cert_certstatus) > '4' AND cert_certdate < '01/JUL/2017' ) AS AdvAdjAmt FROM cbillh WHERE trim(cblh_contract) = :contractid GROUP BY cblh_contract");
		advanceAdjQuery.setParameter("contractid", cbillhResponseBean.getContract().trim());
		Object advanceAdjQueryResult = advanceAdjQuery.getSingleResult();
		Double advanceAdj =  Objects.nonNull(advanceAdjQueryResult) ? Double.valueOf(advanceAdjQueryResult.toString()) : BigInteger.ZERO.doubleValue();
		
		cbillhResponseBean.setBalanceAdvance(advance - advanceAdj);
		cbillhResponseBean.setAdvanceadj(advanceAdj);
		cbillhResponseBean.setTotalAdvance(advance);
		return cbillhResponseBean;
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchCertificateEntryByCertnum(String certnum) {

		CertificateEntryResponseBeanBuilder certDetailResponseBean = CertificateEntryResponseBean.builder();

		Cert contractEntity = this.certRepository.findByCertCK_CertCertnum(certnum.trim());
		LOGGER.info("Cert Entity :: {}", contractEntity);

		CertificateDetailBean certificateBean = this.certRepository.fetchCertEntryDetails(contractEntity.getCertContract(), certnum.trim()); 
		LOGGER.info("CertificateDetailBean  :: {}", certificateBean);

		//		List<CertificateDetailBean> certificateDetailBeanList= this.cbillhRepository.fetchCertificateDetailsByBillnoIn(cbillhEntityList.stream().map(cbillhEntity -> cbillhEntity.getCbillhCK().getCblhSer()).collect(Collectors.toSet()));
		//		LOGGER.info("CertificateDetailBean :: {}", certificateDetailBeanList);

		List<Certworknarrdtl> certworknarrdtlEntityList = this.certworknarrdtlRepository.findByCertworknarrdtlCK_CwndCertnum(certnum.trim());
		LOGGER.info("Certworknarrdtl Entity :: {}", certworknarrdtlEntityList);

		List<Advrecvoucher> advrecvoucherEntityList = this.advrecvoucherRepository.findByAdvrecvoucherCK_adrvOrigdocnum(certnum.trim());
		LOGGER.info("Certworknarrdtl Entity :: {}", certworknarrdtlEntityList);

		List<Certdetails> certdetailsEntityList = this.certdetailsRepository.findByCertdetailsCK_CrtdCertnum(certnum.trim());
		LOGGER.info("Certdetails Entity :: {}", certdetailsEntityList);

		if(CollectionUtils.isNotEmpty(certworknarrdtlEntityList)) 
			certDetailResponseBean.certworknarrdtlResponseBeanList(CertworknarrdtlMapper.fetchCertworknarrdtlEntityPojoMapper.apply(new Object[] {certworknarrdtlEntityList})).build();

		if(Objects.nonNull(certificateBean))
			certDetailResponseBean.certificateDetailBean(certificateBean);

		if(CollectionUtils.isNotEmpty(advrecvoucherEntityList)) 
			certDetailResponseBean.advrecvoucherResponseBeanList(AdvrecvoucherEntityPojoMapper.fetchAdvrecvoucherEntityPojoMapper.apply(new Object[] {advrecvoucherEntityList})).build();

		if(Objects.isNull(certDetailResponseBean))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());

		return ResponseEntity.ok(ServiceResponseBean.builder().data(certDetailResponseBean.build()).status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndPartyCodeAndWorkCodeByContractId(
			String contractId) {
		Contract contractEntity = this.contractRepository
				.findByContractCK_ConttContract(contractId.trim());
		if (Objects.nonNull(contractEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
		Map<String, String> data = new HashMap<>();
		data.put("bldgCode",
				Objects.nonNull(contractEntity.getConttBldgcode()) ? contractEntity.getConttBldgcode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
		data.put("partyCode",
				Objects.nonNull(contractEntity.getConttPartycode()) ? contractEntity.getConttPartycode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
		data.put("workCode",
				Objects.nonNull(contractEntity.getConttWorkcode()) ? contractEntity.getConttWorkcode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
		return ResponseEntity.ok(ServiceResponseBean.builder().data(data).status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractBlgdCodeAndCoyAndContractorByContractId(String contractId) {
		Contract contractEntity = this.contractRepository
				.findByContractCK_ConttContract(contractId.trim());
		if (!Objects.nonNull(contractEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());		
		Map<String, String> data = new HashMap<>();
		String coy =  Objects.nonNull(contractEntity.getConttCoy()) ? contractEntity.getConttCoy().trim()
				: CommonConstraints.INSTANCE.BLANK_STRING;
		String contractCode = Objects.nonNull(contractEntity.getConttConttor()) ? contractEntity.getConttConttor().trim()
				: CommonConstraints.INSTANCE.BLANK_STRING;
		Party party = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(contractCode.trim(),CommonUtils.INSTANCE.closeDateInLocalDateTime(), "E");
		data.put("bldgCode",
				Objects.nonNull(contractEntity.getConttBldgcode()) ? contractEntity.getConttBldgcode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
		data.put("coy", coy);
		data.put("coyName", getCoyNameByCoyCode(coy.trim()));
		data.put("contractorCode",
				contractCode);
		data.put("contractorName",
				Objects.nonNull(party) ? party.getParPartyname().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
		return ResponseEntity.ok(ServiceResponseBean.builder().data(data).status(Boolean.TRUE).build());
	}


	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg(Map<String, Object> data) {
		List<Contractdebit> contractDebitList = this.contractdebitRepository
				.findContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg(data.get("debitType").toString().trim(),
						data.get("authnum").toString().trim(),
						data.get("recId").toString().trim(),
						data.get("certType").toString().trim(),
						data.get("runser").toString().trim(),data.get("bldgCode").toString().trim()) ;
		boolean isAdd = (boolean) data.get("isAdd");
		if (!contractDebitList.isEmpty()) {
			
			if(isAdd) 
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("Contract Debit Already Exists").build());
			List<ContractdebitResponseBean> contractdebitResponseBean = ContractdebitMapper.fetchContractdebitEntityPojoMapper.apply(contractDebitList);
			contractdebitResponseBean.stream().forEach(contractDebitResponse -> {	
				Contract contractEntity = this.contractRepository
						.findByContractCK_ConttContract(contractDebitResponse.getContracontract().trim());
				contractDebitResponse.setPartyCode(Objects.nonNull(contractEntity.getConttPartycode()) ? contractEntity.getConttPartycode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
				contractDebitResponse.setWorkCode(Objects.nonNull(contractEntity.getConttWorkcode()) ? contractEntity.getConttWorkcode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);

			});
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(contractdebitResponseBean).build());
		}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(isAdd)
					.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}


	@Override
	public ResponseEntity<ServiceResponseBean> addContractDebit(
			List<ContractdebitRequestBean> contractdebitRequestBean) {

		contractdebitRequestBean.stream().forEach(contractDebitRequest -> {
			String contractDebitNo = GenericCounterIncrementLogicUtil
					.generateTranNoWithSite(EntityClassEnum.NSER.getValue(), "#CDEB", "MUM");// GenericAuditContextHolder.getContext().getSite());
			contractDebitRequest.setDebitno(contractDebitNo);
			this.contractdebitRepository
			.save(ContractdebitMapper.addContractdebitPojoEntityMapper.apply(contractDebitRequest));
		});
		return ResponseEntity.ok(
				ServiceResponseBean.builder().status(Boolean.TRUE).message("Contract Debit Added Succesfully").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> updateContractDebit(
			List<ContractdebitRequestBean> contractdebitRequestBean) {
		contractdebitRequestBean.stream().forEach(contractDebitRequest -> {
			Contractdebit existingContractDebit = this.contractdebitRepository
					.findByContractdebitCK_ccdDebitno(contractDebitRequest.getDebitno().trim());
			if (Objects.nonNull(existingContractDebit)) {
				if (contractDebitRequest.getIsDelete()) {		
					this.contractdebitRepository.delete(existingContractDebit);
				} 
				
			}else {
					if(contractDebitRequest.getIsAdd()) {
						String contractDebitNo = GenericCounterIncrementLogicUtil
								.generateTranNoWithSite(EntityClassEnum.NSER.getValue(), "#CDEB", "MUM");
						contractDebitRequest.setDebitno(contractDebitNo);
						this.contractdebitRepository.save(ContractdebitMapper.addContractdebitPojoEntityMapper.apply(contractDebitRequest));
					}
					else {
						this.contractdebitRepository.save(ContractdebitMapper.updateContractdebitEntityPojoMapper.apply(existingContractDebit,
											contractDebitRequest));
					}
				}
		});
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.message("Contract Debit Updated Succesfully").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchAuthBldgCodeAndCoy(String authnum) {
		List<Object[]>  bldgAndCoy = this.authHRepository.findBldgCodeAndCoyByAuthNum(authnum.trim());
		if(bldgAndCoy.isEmpty())
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
		Map<String, String> data = new HashMap<>();
		String coy =  Objects.nonNull(bldgAndCoy.get(0)[1]) ? bldgAndCoy.get(0)[1].toString().trim() :CommonConstraints.INSTANCE.BLANK_STRING;
		data.put("bldgCode", Objects.nonNull(bldgAndCoy.get(0)[0]) ? bldgAndCoy.get(0)[0].toString().trim() :CommonConstraints.INSTANCE.BLANK_STRING );
		data.put("coy", coy);
		data.put("coyName", getCoyNameByCoyCode(coy.trim()));
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.data(data).build());
	}

	private String getCoyNameByCoyCode(String coy) {
		String coyName= "";
		Company company = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(coy, CommonUtils.INSTANCE.closeDate());
		if(Objects.nonNull(company))
			coyName = company.getCoyName();
		return coyName;
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractDebitNoteBySer(String dbnoteser) {
		Cdbnoteh cbdnotehEntity = this.cdbnotehRepository.findByCdbnotehCK_CdbnhDbnoteser(dbnoteser.trim());
		if(Objects.nonNull(cbdnotehEntity)) {
			ContractDebitNoteResponse contractDebitNoteResponse = new ContractDebitNoteResponse();
			List<Cdbnoted> cbdnotedEntityList = this.cdbnotedRepository.findByCdbnotedCK_CdbndDbnoteser(dbnoteser.trim());
			CdbnotehResponseBean cdbnotehResponseBean = CdbnotehMapper.fetchCdbnotehEntityPojoMapper.apply(new Object[] {cbdnotehEntity,cbdnotedEntityList});
			Cbillh cbillhEntity = this.cbillhRepository.findCblhByRecIdAndContractBillNo(cdbnotehResponseBean.getContract().trim(), cdbnotehResponseBean.getContbillno().trim());
			Query queryForepWorkName = this.entityManager.createNativeQuery("SELECT ep_workname FROM EPWORKS WHERE trim(ep_workcode) = :workcode and (EP_CLOSEDATE IS NULL OR EP_CLOSEDATE = :closeDate)");//used native query because uniqne index is null
			queryForepWorkName.setParameter("workcode", cbdnotehEntity.getCdbnhWorkcode().trim());		
			queryForepWorkName.setParameter("closeDate", CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			String workCodeName = (String) queryForepWorkName.getSingleResult();
			//Epworks epWorksEntity = this.epworksRepository.findByWorkCodeAndCloseDate(cbillhEntity.getCblhWorkcode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			Party partyEntity = this.partyRepository.findByPartyCodeAndParClosedateAndParPartytype(cdbnotehResponseBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), cdbnotehResponseBean.getPartytype().trim());
			Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(cdbnotehResponseBean.getBldgcode().trim());
			contractDebitNoteResponse.setData(cdbnotehResponseBean);
			contractDebitNoteResponse.setWorkName(workCodeName);
			contractDebitNoteResponse.setPartyName(partyEntity.getParPartyname().trim());
			contractDebitNoteResponse.setCoyName(getCoyNameByCoyCode(cbillhEntity.getCblhCoy().trim()));	
			contractDebitNoteResponse.setBldgName(buildingEntity.getBldgName().trim());	
			if(Objects.nonNull(cbillhEntity.getCblhCertnum()) && Objects.nonNull(cbillhEntity.getCblhChequeno())) {
				Double locOsAmnt = cbillhEntity.getCblhAmount() - cbillhEntity.getCblhDebitamt();
				Double locPaidAmnt = cbillhEntity.getCblhPaidamt();
				if(locOsAmnt > locPaidAmnt) {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Partial Payment, Payment of Rs. "+locPaidAmnt+ " for this contract bill".concat(Objects.nonNull(cbillhEntity.getCblhPaiddate()) ? "made on" + CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER.format(cbillhEntity.getCblhPaiddate()) : "")).extraData(Boolean.TRUE)
							.data(contractDebitNoteResponse).build());
				}
				else {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Fully Paid, Fully paid contract bill. Contract Debit Note can't be raised.").extraData(Boolean.FALSE).data(contractDebitNoteResponse).build());
				}
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(contractDebitNoteResponse).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
					.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchContractBillByRecIdAndBillNo(String recId, String billNo) {
		Cbillh cbillhEntity = this.cbillhRepository.findCblhByRecIdAndContractBillNo(recId.trim(),billNo.trim());
		if(Objects.nonNull(cbillhEntity)) {
			
			if(Objects.nonNull(cbillhEntity.getCblhCertnum())) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("Contract Bill of this debite note # is Certified, You Cannot Add Contract Debit Note.......").build());
			}
			String message = "";
			Double locDbAmnt = Objects.nonNull(cbillhEntity.getCblhDebitamt()) ? cbillhEntity.getCblhDebitamt() : BigInteger.ZERO.doubleValue();
			if(locDbAmnt > 0) {
				message = "Contract Debit note of Rs." + cbillhEntity.getCblhDebitamt() + " already entered, Please Check.......";
			}
			ContractDebitNoteResponse contractDebitNoteResponse = new ContractDebitNoteResponse();
			Query queryForepWorkName = this.entityManager.createNativeQuery("SELECT ep_workname FROM EPWORKS WHERE trim(ep_workcode) = :workcode and (EP_CLOSEDATE IS NULL OR EP_CLOSEDATE = :closeDate)");//used native query because uniqne index is null
			queryForepWorkName.setParameter("workcode", cbillhEntity.getCblhWorkcode().trim());		
			queryForepWorkName.setParameter("closeDate", CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			String workCodeName = (String) queryForepWorkName.getSingleResult();
			//Epworks epWorksEntity = this.epworksRepository.findByWorkCodeAndCloseDate(cbillhEntity.getCblhWorkcode().trim(),CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			Party partyEntity = this.partyRepository.findByPartyCodeAndParClosedateAndParPartytype(cbillhEntity.getCblhPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), cbillhEntity.getCblhPartytype().trim());
			Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(cbillhEntity.getCblhBldgcode().trim());
			List<Cbilld> cbilldEntityList = this.cbilldRepository.findByCbilldCK_CbldSer(cbillhEntity.getCbillhCK().getCblhSer().trim());
			CbillhResponseBean responseBean = CbillhMapper.fetchCbillhEntityPojoMapper.apply(new Object[] {cbillhEntity, cbilldEntityList});
			contractDebitNoteResponse.setData(responseBean);
			contractDebitNoteResponse.setWorkName(workCodeName);
			contractDebitNoteResponse.setPartyName(partyEntity.getParPartyname().trim());
			contractDebitNoteResponse.setCoyName(getCoyNameByCoyCode(cbillhEntity.getCblhCoy().trim()));	
			contractDebitNoteResponse.setBldgName(buildingEntity.getBldgName().trim());	
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
					.data(contractDebitNoteResponse)
					.message(StringUtils.isNotBlank(message) ? message : null).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message(ErrorMessageEnum.NO_DATA_FOUND.getValue()).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> reverseContratcDebit(
			List<ContractdebitRequestBean> contractdebitRequestBean) {
				contractdebitRequestBean.stream().forEach(contractDebitRequest -> {
					Contractdebit existingContractDebit = this.contractdebitRepository
							.findByContractdebitCK_ccdDebitno(contractDebitRequest.getDebitno().trim());
					if (Objects.nonNull(existingContractDebit)) {
						String contractDebitNo = GenericCounterIncrementLogicUtil
								.generateTranNoWithSite(EntityClassEnum.NSER.getValue(), "#CDEB", "MUM");// GenericAuditContextHolder.getContext().getSite());
						contractDebitRequest.setDebitno(contractDebitNo);
						contractDebitRequest.setDebitamount(contractDebitRequest.getDebitamount() * -1);
						this.contractdebitRepository
						.save(ContractdebitMapper.addContractdebitPojoEntityMapper.apply(contractDebitRequest));
						} 
				});
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
						.message("Contract Debit Reversed Succesfully").build());		
	}

	@Override
	public ResponseEntity<ServiceResponseBean> checkIsContractDebitAuthorised(String debitType, String authnum) {
			Double debitAmountSum = this.contractdebitRepository.findSumOfDebitAmountByDebitTypeAndAuthnum(debitType.trim(), authnum.trim());
			if(debitAmountSum == 0) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
						.message("This authorisation is reversed. You can not change the details").build());
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchPartyByRecId(String recId) {
		Contract contractEntity = this.contractRepository
				.findByContractCK_ConttContract(recId.trim());
		if(Objects.nonNull(contractEntity)) {
			Party partyEntity = this.partyRepository.findByPartyCodeAndParClosedateAndParPartytype(contractEntity.getConttPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), contractEntity.getConttPartycode().trim());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(new HashMap<String, String>() {{{
				put("partycode", Objects.nonNull(contractEntity.getConttPartycode()) ? contractEntity.getConttPartycode().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
				put("partyName", Objects.nonNull(partyEntity.getParPartyname()) ? partyEntity.getParPartyname().trim()
						: CommonConstraints.INSTANCE.BLANK_STRING);
			}}}).build());
		}		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}
	
	@Override
	public ResponseEntity<ServiceResponseBean> addCertificateDetail(CertificateRequestBean certificateRequestBean) {
		String certNumber = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER" , "#CERT", GenericAuditContextHolder.getContext().getSite().trim());
		LOGGER.info("cert Number :: {}", certNumber);
		Double DecPricertTPayment = BigInteger.ZERO.doubleValue();
		Double DecPriCertTMwctaxamt = BigInteger.ZERO.doubleValue();
		Double DecPriCertTdsamount = BigInteger.ZERO.doubleValue();
		Double DecPriCertAdvadj = BigInteger.ZERO.doubleValue();
		Double DecPriCertRetained= BigInteger.ZERO.doubleValue();
		Double DecPriCertTrips= BigInteger.ZERO.doubleValue();
		Double DecPritdssur = BigInteger.ZERO.doubleValue();
		Double DecPriCertDebit= BigInteger.ZERO.doubleValue();
		Double DecPriWriteOfAmt = BigInteger.ZERO.doubleValue();
		Double DecPriTotalCertAmt = BigInteger.ZERO.doubleValue();
		Double DecPricerttadvpaid = BigInteger.ZERO.doubleValue();
		Double DecLoccertAdvgranted = BigInteger.ZERO.doubleValue();
		Double DecPriCertTotyTwoptc = BigInteger.ZERO.doubleValue();
		Double DecLoccertAdvadjusted = BigInteger.ZERO.doubleValue();
		Double DecPriCertTotyTwtptc = BigInteger.ZERO.doubleValue();
		Double DecLocCertTAdvos = BigInteger.ZERO.doubleValue();
		LocalDate StrLocPrvdate = null; //----------> DtLocCertPrvDate SAME
		String StrLocMaxCertPrvnum =null;
		String StrPriCertPrvType = null;
		Double DecLocCertPrvAmt = BigInteger.ZERO.doubleValue();
		LocalDate DtLocPrvDate = null;
		Double DecPriCertTPerpaid = BigInteger.ZERO.doubleValue();
		Double DecPriCertDebsocyn = BigInteger.ZERO.doubleValue();

		
		//FuncGetPreviousCertDetail START
		List<Cert> totalPrvCertDetailList = this.certRepository.fetchTotalPrvCertDetailByContractIdAndCertStatus(certificateRequestBean.getCertRequestBean().getContract().trim(), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for Totalprvcertdetails :: {}", totalPrvCertDetailList);

		//FuncGetTotalPrvCertDetails START
		StrLocPrvdate = this.certRepository.fetchMaxCertDateByContractIdAndCertStatus(certificateRequestBean.getCertRequestBean().getContract().trim(), CertStatusEnum.FOUR.getValue());
		LOGGER.info("StrLocPrvdate :: {}", StrLocPrvdate);
		
		StrLocMaxCertPrvnum = this.certRepository.fetchMaxCertNumByContractIdAndCertStatusAndCertDate(certificateRequestBean.getCertRequestBean().getContract().trim(), CertStatusEnum.FOUR.getValue(), StrLocPrvdate);
		LOGGER.info("StrLocMaxCertPrvnum :: {}", StrLocMaxCertPrvnum); //------------------> TO ASK

		if(StringUtils.isNotBlank(StrLocMaxCertPrvnum)){
		Cert priCertDetail = this.certRepository.findByCertCK_CertCertnum(StrLocMaxCertPrvnum.trim());
		 StrPriCertPrvType = priCertDetail.getCertCerttype();
		 DecLocCertPrvAmt = priCertDetail.getCertCertamount();
		 DtLocPrvDate = priCertDetail.getCertCertdate();
		}
		
		if(Objects.nonNull(certificateRequestBean.getCertRequestBean().getDebsocyn()) && (certificateRequestBean.getCertRequestBean().getDebsocyn().trim().equals("S") || certificateRequestBean.getCertRequestBean().getDebsocyn().trim().equals("A")))		
			DecPriCertDebsocyn = GenericAccountingLogic.getSumDebit(certificateRequestBean.getCertRequestBean().getProp(), certificateRequestBean.getCertRequestBean().getCoy(), certificateRequestBean.getCertRequestBean().getBldgcode(), "20404517");
		
		if(CollectionUtils.isNotEmpty(totalPrvCertDetailList)){
			DecPricertTPayment = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertPayamount()).sum();
			DecPriCertTMwctaxamt = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertMwctaxamount()).sum();
			DecPriCertTdsamount = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertTdsamount()).sum();
			DecPriCertAdvadj = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertAdvadjusted()).sum();
			DecPriCertRetained = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertRetained()).sum();
			DecPriCertTrips = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertTrips()).sum();
			DecPritdssur = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertTdssur()).sum();
			DecPriCertDebit = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertDebit()).sum();
			DecPriWriteOfAmt = totalPrvCertDetailList.stream().mapToDouble(cert -> cert.getCertWriteoffamt()).sum();
		}

		//		 ' Calculating Total Retention O/S Amt ...
		//        '' The field cert_t_retained actually contains total  retention outstanding ...
		//L TYPE 
		List<Cert> totalprvcertdetailWithCerttypeLList = this.certRepository.fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertType(certificateRequestBean.getCertRequestBean().getContract().trim(), CertTypeEnum.L.getValue(), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for Totalprvcertdetailswithcerttype :: {}", totalprvcertdetailWithCerttypeLList);
		if(CollectionUtils.isNotEmpty(totalprvcertdetailWithCerttypeLList)) 
			DecPriTotalCertAmt = totalprvcertdetailWithCerttypeLList.stream().mapToDouble(cert -> cert.getCertCertamount()).sum();
		

		DecPriCertRetained = DecPriCertRetained - DecPriTotalCertAmt;
				if((certificateRequestBean.getCertRequestBean().getPriSpentAmount() > BigInteger.ZERO.doubleValue() || certificateRequestBean.getCertRequestBean().getSpendSoFar() > BigInteger.ZERO.doubleValue()) && certificateRequestBean.getCertRequestBean().getConamount() > 0)
//					 to avoid divide by zero error ...
			            DecPriCertTPerpaid = ((certificateRequestBean.getCertRequestBean().getSpendSoFar()) / certificateRequestBean.getCertRequestBean().getConamount()) * 100;
		

		//SAME QUERY AS ABOVE BUT A TYPE  
		List<Cert> totalprvcertdetailWithCerttypeAList = this.certRepository.fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertType(certificateRequestBean.getCertRequestBean().getContract().trim(), CertTypeEnum.A.getValue(), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for Totalprvcertdetailswithcerttype :: {}", totalprvcertdetailWithCerttypeAList);
		if(CollectionUtils.isNotEmpty(totalprvcertdetailWithCerttypeAList)) {
			DecPricerttadvpaid = totalprvcertdetailWithCerttypeAList.stream().mapToDouble(cert -> cert.getCertPayamount()).sum();
			DecLoccertAdvgranted = totalprvcertdetailWithCerttypeAList.stream().mapToDouble(cert -> cert.getCertCertamount()).sum();
		}


		List<Cert> certTotyTwoptcDetailList = this.certRepository.fetchCertByBldgCodeAndWorkCodeAndPartyCodeNotAndCertStatusAndCertType(certificateRequestBean.getCertRequestBean().getBldgcode().trim(),certificateRequestBean.getCertRequestBean().getWorkcode().trim(),certificateRequestBean.getCertRequestBean().getPartycode().trim(), CertTypeEnum.L.getValue(), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for certTotyTwoptcDetailList :: {}", certTotyTwoptcDetailList);
		if(CollectionUtils.isNotEmpty(certTotyTwoptcDetailList)) 
			DecPriCertTotyTwoptc = certTotyTwoptcDetailList.stream().mapToDouble(cert -> Objects.nonNull(cert.getCertCertamount()) ?  cert.getCertCertamount() : BigInteger.ZERO.doubleValue()).sum() - certTotyTwoptcDetailList.stream().mapToDouble(cert -> Objects.nonNull(cert.getCertAdvadjusted()) ?  cert.getCertAdvadjusted() : BigInteger.ZERO.doubleValue()).sum();
		

		//CERT TYPE A, L 
		List<Cert> certAdvAdjustedList = this.certRepository.fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertTypeNotIn(certificateRequestBean.getCertRequestBean().getContract().trim(), Arrays.asList(CertTypeEnum.A.getValue(), CertTypeEnum.L.getValue()), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for certAdvAdjustedist :: {}", certAdvAdjustedList);
		if(CollectionUtils.isNotEmpty(certAdvAdjustedList)) 
			DecLoccertAdvadjusted = certAdvAdjustedList.stream().mapToDouble(cert -> cert.getCertAdvadjusted()).sum();
		

		//        ' Previous amount for the current partycode.
		//SAME QUERY AS ABOVE BUT A L M CERT TYPE  
		List<Cert> prvcertTotyTwoptcDetailList = this.certRepository.fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertTypeNotIn(certificateRequestBean.getCertRequestBean().getContract().trim(), Arrays.asList(CertTypeEnum.A.getValue(), CertTypeEnum.L.getValue(), CertTypeEnum.M.getValue()), CertStatusEnum.FOUR.getValue(), Arrays.asList(CertStatusEnum.SIX.getValue(), CertStatusEnum.EIGHT.getValue()));
		LOGGER.info("Cert Details for prvcertTotyTwoptcDetailList :: {}", prvcertTotyTwoptcDetailList);
		if(CollectionUtils.isNotEmpty(prvcertTotyTwoptcDetailList)) 
			DecPriCertTotyTwtptc = prvcertTotyTwoptcDetailList.stream().mapToDouble(cert -> cert.getCertCertamount()).sum();

		//FuncGetTotalPrvCertDetails END
		DecLocCertTAdvos = DecLoccertAdvgranted - DecLoccertAdvadjusted;

		if(Objects.nonNull(certificateRequestBean.getCertRequestBean())){
			
			certificateRequestBean.getCertRequestBean().setCertrevnum(BigInteger.ZERO.intValue());
			certificateRequestBean.getCertRequestBean().setBillamount(BigInteger.ZERO.doubleValue());
			certificateRequestBean.getCertRequestBean().setPrinted(BigInteger.ZERO.intValue());
			certificateRequestBean.getCertRequestBean().setCertstatus(BigInteger.ONE.toString());
			certificateRequestBean.getCertRequestBean().setPartytype(PartyType.E.toString());
			certificateRequestBean.getCertRequestBean().setOrconamount(BigInteger.ZERO.doubleValue());
			certificateRequestBean.getCertRequestBean().setClearacyn("Y");
			certificateRequestBean.getCertRequestBean().setProvyn("N");
			certificateRequestBean.getCertRequestBean().setBillamount(BigInteger.ZERO.doubleValue());
			certificateRequestBean.getCertRequestBean().setPayamount(1d);//---------------> to do on button click

			//prv details for cert
			certificateRequestBean.getCertRequestBean().setPrvcertnum(StrLocMaxCertPrvnum);
			certificateRequestBean.getCertRequestBean().setPrvdate(StrLocPrvdate);
			certificateRequestBean.getCertRequestBean().setPrvtype(StrPriCertPrvType);
			certificateRequestBean.getCertRequestBean().setPrvamt(DecLocCertPrvAmt);
			//T_ details for cert
			certificateRequestBean.getCertRequestBean().setTpayment(DecPricertTPayment);
			certificateRequestBean.getCertRequestBean().setTmwctaxamt(DecPriCertTMwctaxamt);
			certificateRequestBean.getCertRequestBean().setTtdsamt(DecPriCertTdsamount);
			certificateRequestBean.getCertRequestBean().setTadvpaid(DecPricerttadvpaid);
			certificateRequestBean.getCertRequestBean().setTadvadj(DecPriCertAdvadj);
			certificateRequestBean.getCertRequestBean().setTretained(DecPriCertRetained);
			certificateRequestBean.getCertRequestBean().setTtrips(DecPriCertTrips);
			certificateRequestBean.getCertRequestBean().setTperpaid(DecPriCertTPerpaid);
			certificateRequestBean.getCertRequestBean().setTottwoptc(DecPriCertTotyTwoptc);
			certificateRequestBean.getCertRequestBean().setTtdssur(DecPritdssur);
			certificateRequestBean.getCertRequestBean().setTadvos(DecLocCertTAdvos);
			certificateRequestBean.getCertRequestBean().setTdebsoc(DecPriCertDebsocyn);
			certificateRequestBean.getCertRequestBean().setTdebsoc(DecPriCertDebit);
			certificateRequestBean.getCertRequestBean().setTwriteoffamt(DecPriWriteOfAmt);
			
			//TO DO
			certificateRequestBean.getCertRequestBean().setServicetaxperc(null);
				certificateRequestBean.getCertRequestBean().setServicetaxamt(BigInteger.ZERO.intValue());
					


			
			certificateRequestBean.getCertRequestBean().setOrigsite(GenericAuditContextHolder.getContext().getSite());
			
	
			
			
			//TO WRITE GETPREVIOUS DETAILS FUNCTION a 

			this.certRepository.save(CertMapper.addCertPojoEntityMapper.apply(new Object[] {certificateRequestBean.getCertRequestBean(), certNumber}));
		}

		if(CollectionUtils.isNotEmpty(certificateRequestBean.getCertworknarrdtlRequestBeanList())) { 
			certificateRequestBean.getCertworknarrdtlRequestBeanList().stream().map(authmatgroupnarrdtlRequestBean ->{
				authmatgroupnarrdtlRequestBean.setAmount(BigInteger.ZERO.intValue());
				authmatgroupnarrdtlRequestBean.setQuantity(BigInteger.ZERO.doubleValue());
				return authmatgroupnarrdtlRequestBean;
			}).collect(Collectors.toList());
			this.certworknarrdtlRepository.saveAll(CertworknarrdtlMapper.addCertworknarrdtlPojoEntityMapper.apply(new Object [] {certificateRequestBean.getCertworknarrdtlRequestBeanList(), certNumber}));
		}

		if(CollectionUtils.isNotEmpty(certificateRequestBean.getAdvrecvoucherRequestBeanList())) {
			certificateRequestBean.getAdvrecvoucherRequestBeanList().stream().map(advrecvoucherRequestBean ->{
				advrecvoucherRequestBean.setOrigsys("CE");
				advrecvoucherRequestBean.setPartytype(PartyType.E.toString());
				advrecvoucherRequestBean.setCoy(certificateRequestBean.getCertRequestBean().getCoy());
				advrecvoucherRequestBean.setOrigdocdate(certificateRequestBean.getCertRequestBean().getCertdate());
				return advrecvoucherRequestBean;
			}).collect(Collectors.toList());

			this.advrecvoucherRepository.saveAll(AdvrecvoucherEntityPojoMapper.addAdvrecvoucherEntityPojoMapper.apply(new Object [] {certificateRequestBean.getAdvrecvoucherRequestBeanList(), certNumber}));
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data Added Successfully with Cert No : ".concat(certNumber)).build());
	}
	
	@Override
	public ResponseEntity<ServiceResponseBean> checkIsDebitAmountValid(String recId, String billNo, Double debitAmount) {
		Cbillh cbillhEntity = this.cbillhRepository.findCblhByRecIdAndContractBillNo(recId.trim(),billNo.trim());
		if(Objects.nonNull(cbillhEntity)) {
			Double billAmount = cbillhEntity.getCblhAmount();
			Double billDebitAmnt = Objects.nonNull(cbillhEntity.getCblhDebitamt()) ? cbillhEntity.getCblhDebitamt() : BigIntegers.ZERO.doubleValue();
			LOGGER.info("Bill Debit Amount: {}", billDebitAmnt);
			billAmount = billAmount - billDebitAmnt;
			if(debitAmount > billAmount) {
				 DecimalFormat decfor = new DecimalFormat("0.00");  
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Debit Amount is more than bill-debit(Rs." +decfor.format(billAmount) +") amount......").build());
			}		
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> addContractDebitNote(CdbnotehRequestBean cdbnotehRequestBean) {
		String transer = "";
		Cbillh cblhEntity = this.cbillhRepository.findCblhByRecIdAndContractBillNo(cdbnotehRequestBean.getContract().trim(),
				cdbnotehRequestBean.getContbillno().trim());
		
		Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(cdbnotehRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());
		if(Objects.nonNull(cblhEntity)) {
			Double debitAmnt = Objects.nonNull(cblhEntity.getCblhDebitamt()) ? cblhEntity.getCblhDebitamt() : BigInteger.ZERO.doubleValue();			
			Double tdsAmnt = Objects.nonNull(cblhEntity.getCblhTdsamount()) ? cblhEntity.getCblhTdsamount() : BigInteger.ZERO.doubleValue();			
			cblhEntity.setCblhDebitamt(debitAmnt + cdbnotehRequestBean.getAmount());
			cblhEntity.setCblhTdsamount(tdsAmnt + cdbnotehRequestBean.getTdsamount());
			cblhEntity.setCblhToday(LocalDateTime.now());	
			cblhEntity.setCblhUserid(GenericAuditContextHolder.getContext().getUserid().trim());
			cblhEntity.setCblhSite(GenericAuditContextHolder.getContext().getSite().trim());
			this.cbillhRepository.save(cblhEntity);
		}
		this.cdbnotehRepository.save(CdbnotehMapper.addCdbnotehPojoEntityMapper.apply(cdbnotehRequestBean));
		List<Cdbnoted> cdbnotedList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(cdbnotehRequestBean.getCdbnotedRequestBean())){
			cdbnotehRequestBean.getCdbnotedRequestBean().stream().forEach(cdbnotedRequest -> {
				Cbilld cbilldEntity = this.cbilldRepository.findByCbilldCK_CbldSerAndCbilldCK_CbldLineno(cblhEntity.getCbillhCK().getCblhSer().trim(), 
						Double.valueOf(cdbnotedRequest.getLineno()));
				if(Objects.nonNull(cbilldEntity)) {
					Double localBillQty = cbilldEntity.getCbldQuantity();
					Double localBillDbQty = cbilldEntity.getCbldDbqty();
					Double localQty = cdbnotedRequest.getQuantity();
					cbilldEntity.setCbldQuantity(localBillQty - localQty);
					cbilldEntity.setCbldDbqty(localBillDbQty + localQty);
					cbilldEntity.setCbldToday(LocalDateTime.now());
					cbilldEntity.setCbldUserid(GenericAuditContextHolder.getContext().getUserid().trim());
					cbilldEntity.setCbldSite(GenericAuditContextHolder.getContext().getSite().trim());
					this.cbilldRepository.save(cbilldEntity);
				}
				cdbnotedList.add(CbdnotedMapper.addCdbnotedPojoEntityMapper.apply(cdbnotedRequest));
			});	
		}
		this.cdbnotedRepository.saveAll(cdbnotedList);
		
		this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
				.transer(transer)
				.trantype(TranTypeEnum.DC.toString())
				.trandate(cdbnotehRequestBean.getDate())	
				.ledgcode("PL")
				.partytype(CommonConstraints.INSTANCE.SUPPLIERS)
				.partycode(cdbnotehRequestBean.getPartycode())
				.tranamt(Double.valueOf(cdbnotehRequestBean.getAmount() - cdbnotehRequestBean.getTdsamount()))
				.voudate(cdbnotehRequestBean.getContbilldt())
				.vounum(cdbnotehRequestBean.getContbillno())
				.postedyn("Y")
				.balancedyn("Y")
				.closingjvyn("N")
				.bbookyn("N")
				.cbookyn("N")
				.proprietor(companyEntity.getCompanyCK().getCoyProp().trim())
				.coy(cdbnotehRequestBean.getCoy())
				.site(GenericAuditContextHolder.getContext().getSite())
				.userid( GenericAuditContextHolder.getContext().getUserid())
				.clearacyn("Y")
				.reverseyn("N")
				.narrative(cdbnotehRequestBean.getNarration())
				.build()}));
		
		List<ActrandBean> actrandList = new ArrayList<>();
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(cdbnotehRequestBean.getBldgcode().trim());
		Double sumOfTaxableAmt = cdbnotehRequestBean.getCdbnotedRequestBean().parallelStream().mapToDouble(CdbnotedRequestBean::getTaxableamt).sum();
		Double sumOfCgstAmt = cdbnotehRequestBean.getCdbnotedRequestBean().parallelStream().mapToDouble(CdbnotedRequestBean::getCgstamt).sum();
		Double sumOfSgstAmt = cdbnotehRequestBean.getCdbnotedRequestBean().parallelStream().mapToDouble(CdbnotedRequestBean::getSgstamt).sum();
		Double sumOfIgstAmt = cdbnotehRequestBean.getCdbnotedRequestBean().parallelStream().mapToDouble(CdbnotedRequestBean::getIgstamt).sum();
		Double sumOfUgstAmt = cdbnotehRequestBean.getCdbnotedRequestBean().parallelStream().mapToDouble(CdbnotedRequestBean::getUgstamt).sum();
		Integer bunumCounter = BigInteger.ZERO.intValue();
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(cdbnotehRequestBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
		Actrand actd = this.actrandRepository.findActdMinorByTranserAndBnum(cblhEntity.getCbillhCK().getCblhSer().trim(), 1);
		Actrand actdEntity = this.actrandRepository.findActdMinorByTranserAndBnum(cblhEntity.getCbillhCK().getCblhSer().trim(), 2);
		if(!cdbnotehRequestBean.getAmount().equals(BigInteger.ZERO.doubleValue())) {
			if(cdbnotehRequestBean.getAmount() > BigInteger.ZERO.doubleValue()) {
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401021", " ", actd.getActdMincode(), "E", cdbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), actdEntity.getActdAcminor());
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(actdEntity.getActdAcmajor(),actdEntity.getActdMintype(), actd.getActdMincode(), " ", cdbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), actdEntity.getActdAcminor());
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401021", "DC");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(actdEntity.getActdAcmajor(), "DC");
				bunumCounter += 1;
				System.out.println("Counter: " + bunumCounter);

				//actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfTaxableAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
			}
			Map<String, String> minTypeAndMinCode = this.fetchMinTypeAndMinorDetailsByWorkCode(cdbnotehRequestBean.getWorkcode().trim(), cdbnotehRequestBean.getBilltype().trim());
			//CGST BREAKUP
			if(!sumOfCgstAmt.equals(BigInteger.ZERO.doubleValue())) {
				
				AccountingBean acmajor = getAcMajor("11402441", "20404391", "20404395", partyEntity.getParGstno());
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(" ",minTypeAndMinCode.get("minType") , " ", " ", cdbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), " ");
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(" ", null, "", "", cdbnotehRequestBean.getPartycode(), null, null);
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "DC");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "DC");
				bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
				System.out.println("Counter: " + bunumCounter);
				//actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfCgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
			}
			//SGST BREAKUP
			if(!sumOfSgstAmt.equals(BigInteger.ZERO.doubleValue())) {
				AccountingBean acmajor = getAcMajor("11402443", "20404392", "20404396", partyEntity.getParGstno());
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), " ", " ", " "," ", buildingEntity.getBldgProject(), " ");
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), " ", "", "", cdbnotehRequestBean.getPartycode(), "GL", " ");
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

				bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
				System.out.println("Counter: " + bunumCounter);
				//actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfSgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotevatRequests().get(0).getQuantity()));
			}
			//IGST BREAKUP
			if(!sumOfIgstAmt.equals(BigInteger.ZERO.doubleValue())) {
				AccountingBean acmajor = getAcMajor("11402445", "20404393", "20404397", partyEntity.getParGstno());
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), " ", " ", " ", " ", "GL", " ");
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), " ",minTypeAndMinCode.get("minType"), ""," ", "GL", " ");
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

				bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
				System.out.println("Counter: " + bunumCounter);
				//actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfIgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
			}
		}
		
		return null;
	}
	
	private AccountingBean getAcMajor(String nonGstAcMajor, String GstXAcMajor, String nonGstXAcMajor, String gstno) {
		return StringUtils.isNotBlank(gstno) ?
				AccountingBean.builder()
				.acMajor("11401019")
				.xAcMajor(GstXAcMajor)
				.build() :
					AccountingBean.builder()
					.acMajor(nonGstAcMajor)
					.xAcMajor(nonGstXAcMajor)
					.build();
	}
	
	private Map<String, String> fetchMinTypeAndMinorDetailsByWorkCode(String workCode, String billtype){
		Map<String, String> data = new HashMap<>();
		String glChartDetails;
		String epWorkAcMajor = "";
		String epWorkAcMnior = " ";
		String minType= "";
		String minCode = "";
		if("T".equals(billtype)) {
			epWorkAcMajor = "40503005";
		}
		else {
			Query queryForepWorkName = this.entityManager.createNativeQuery("SELECT ep_acmajor FROM EPWORKS WHERE trim(ep_workcode) = :workcode and (EP_CLOSEDATE IS NULL OR EP_CLOSEDATE = :closeDate)");//used native query because uniqne index is null
			queryForepWorkName.setParameter("workcode", workCode.trim());		
			queryForepWorkName.setParameter("closeDate", CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
			epWorkAcMajor = (String) queryForepWorkName.getSingleResult();
			
		}
		glChartDetails = this.glchartRepository.findchartMinorAndchartValidPartiesByCharAcnum(epWorkAcMnior.trim());
		if(Objects.nonNull(glChartDetails) || StringUtils.isNotBlank(glChartDetails)) {
			String[] detail = glChartDetails.split(",");
			String minorYN = detail[0];
			String minors = detail[1];
			String parties = detail[2];
			if(minorYN.trim().equals("Y") && minors.trim().equals("W")) {
				minCode = "W";
				minType = workCode.trim();			
			}
		}
		data.put("minType", minType);
		data.put("minCode", minCode);		
		return data;
	}
}
