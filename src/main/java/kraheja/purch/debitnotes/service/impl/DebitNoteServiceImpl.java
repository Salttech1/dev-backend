package kraheja.purch.debitnotes.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.entityentity.ActrandxEntityEntityMapper;
import kraheja.commons.mappers.entityentity.ActranhxEntityEntityMapper;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.UpdatePojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActrandxRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.ActranhxRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.purch.bean.request.DbnotehRequestBean;
import kraheja.purch.bean.request.DbnotevatRequestBean;
import kraheja.purch.debitnotes.mappers.DbnotedMapper;
import kraheja.purch.debitnotes.mappers.DbnotehMapper;
import kraheja.purch.debitnotes.mappers.DbnotevatMapper;
import kraheja.purch.debitnotes.service.DebitNoteService;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.Dbnotevat;
import kraheja.purch.entity.Material;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.purch.entity.Pbillvat;
import kraheja.purch.entity.Uom;
import kraheja.purch.enums.BillTypeEnum;
import kraheja.purch.purchasebills.mappers.PbillhMapper;
import kraheja.purch.repository.DbnotedRepository;
import kraheja.purch.repository.DbnotehRepository;
import kraheja.purch.repository.DbnotevatRepository;
import kraheja.purch.repository.MaterialRepository;
import kraheja.purch.repository.PbilldRepository;
import kraheja.purch.repository.PbillhRepository;
import kraheja.purch.repository.PbillvatRepository;
import kraheja.purch.repository.UomRepository;

@Service
@Transactional
public class DebitNoteServiceImpl implements DebitNoteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	DbnotedRepository dbnotedRepository;

	@Autowired
	DbnotehRepository dbnotehRepository;
	
	@Autowired
	private  UomRepository uomRepository;

	@Autowired
	DbnotevatRepository dbnotevatRepository;

	@Autowired
	private PbillvatRepository pbillvatRepository;

	@Autowired
	private PbilldRepository pbilldRepository;

	@Autowired
	private PbillhRepository pbillhRepository;

	@Autowired
	private ActrandRepository actrandRepository;

	@Autowired
	private ActranhRepository actranhRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private  ActrandxRepository actrandxRepository;

	@Autowired
	private  ActranhxRepository actranhxRepository;

	@PersistenceContext
	private  EntityManager entityManager;
	
	ObjectMapper objectMapper = new ObjectMapper(); 

	@Override
	public ResponseEntity<?> fetchDbnoteByDbnoteser(String ser) {

		String message = "";
		Dbnoteh  dbnotehEntity = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(ser);
		LOGGER.info("dbnotehEntity {} :: ", dbnotehEntity);

		if(Objects.nonNull(dbnotehEntity)){
			List<Dbnoted>  dbnotedEntityList = this.dbnotedRepository.findByDbnotedCK_DbndDbnoteser(ser);
			LOGGER.info("dbnotedEntityList {} :: ", dbnotedEntityList);

			List<Dbnotevat> dbnotevatEntityList = this.dbnotevatRepository.findByDbnotevatCK_DbnvSer(ser);
			LOGGER.info("dbnotevatEntityList {} :: ", dbnotevatEntityList);

			Pbillh pbillEntity = this.pbillhRepository.findByPblhPartytypeAndPblhPartycodeAndPblhSuppbillno(dbnotehEntity.getDbnhPartytype().trim(),
					dbnotehEntity.getDbnhPartycode().trim(), dbnotehEntity.getDbnhSuppbillno().trim());
			if(Objects.nonNull(pbillEntity)) {
				if(Objects.nonNull(pbillEntity.getPblhAuthnum()) && Objects.nonNull(pbillEntity.getPblhChequeno())) {
					Double locOsAmnt = pbillEntity.getPblhAmount() - pbillEntity.getPblhDebitamt();
					if(locOsAmnt > pbillEntity.getPblhPaidamt()) {
						message = message +"Partial Payment, Payment of Rs."+ pbillEntity.getPblhPaidamt() +" for this bill".concat(Objects.nonNull(pbillEntity.getPblhPaiddate()) ? " on " + CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER.format(pbillEntity.getPblhPaiddate()) : "");
					}
					else {
						message = message + "Fully Paid, Fully paid bill.Debit Note can't be raised.";
					}
				}

			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(DbnotehMapper.fetchDbnotehEntityPojoMapper.apply(new Object [] {Arrays.asList(dbnotehEntity), dbnotedEntityList, dbnotevatEntityList})).message(message).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@Override
	public ResponseEntity<?> fetchPbillhByPartyCodeAndSuppBillNo(String partyCode,String supplierBillNo) {
		Pbillh pbillEntity = this.pbillhRepository.findByPblhPartycodeAndPblhSuppbillno(partyCode.trim(), supplierBillNo.trim());
		if(Objects.nonNull(pbillEntity)) {
			LOGGER.info("PBillEntity :: {}" , pbillEntity);
			if(StringUtils.isNotEmpty(pbillEntity.getPblhAuthnum()))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Purchase Bill of this debite note # is Authorized " + "You Cannot Add Debit Note").build());
			Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(pbillEntity.getPbillhCK().getPblhSer().trim());
			List<Pbillvat> pbillvatEntityList = this.pbillvatRepository.findByPbillvatCK_PblvSer(pbillEntity.getPbillhCK().getPblhSer().trim());
			LOGGER.info("Pbillvat :: {}", pbillvatEntityList);

			Pbilld pbilldEntity = this.pbilldRepository.findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(pbillEntity.getPbillhCK().getPblhSer().trim(), BigInteger.ONE.intValue());
			LOGGER.info("Pbilld :: {}", pbilldEntity);
			
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PbillhMapper.fetchPbillhEntityPojoMapper.apply(new Object [] {pbillhEntity, pbilldEntity, pbillvatEntityList, null, null, null})).message(Objects.nonNull(pbillEntity.getPblhDebitamt())  && pbillEntity.getPblhDebitamt() > 0 ? "Debit note of Rs." + String.format("%.0f", pbillEntity.getPblhDebitamt()) + " already entered. Please Check......." : null).build());
		}
//		catch(Exception e) {
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Purchase Bill of this debite note # is Authorized " + "You Cannot Add Debit Note").build());
//		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}

	@Override
	public ResponseEntity<?> updateDbnote(DbnotehRequestBean dbnotehRequestBean) {
		Dbnoteh  dbnotehEntity = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(dbnotehRequestBean.getDbnoteser());
		LOGGER.info("dbnotehEntity {} :: ", dbnotehEntity);
		
		List<Dbnoted> updateDnotedEntityList = new ArrayList<>();
		List<Dbnotevat> updateDnotevatEntityList = new ArrayList<>();

		if(Objects.nonNull(dbnotehEntity)){
			Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndActranhCK_ActhCoy(dbnotehRequestBean.getDbnoteser(), dbnotehRequestBean.getCoy().trim());
			if(Objects.nonNull(actranh)) {
				Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(dbnotehRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());
				if(StringUtils.isNotBlank(actranh.getActhBbookyn()) && actranh.getActhBbookyn().trim().equals("Y")) {
					Integer counter = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("#REVN", "#REVN").intValue();

					this.actranhxRepository.save(ActranhxEntityEntityMapper.addActranhxEntityPojoMapper.apply(new Object[] {actranh, counter.toString(), companyEntity.getCoyCurrepnum() + 1 }));
					companyEntity.setCoyCurrepnum(companyEntity.getCoyCurrepnum() + 1);
					this.companyRepository.save(companyEntity);
					List<Actrand> actrandList = this.actrandRepository.findByActrandCK_ActdTranserAndActrandCK_ActdCoy(dbnotehRequestBean.getDbnoteser(), dbnotehRequestBean.getCoy());
					if(CollectionUtils.isNotEmpty(actrandList)) {
						this.actrandxRepository.saveAll(ActrandxEntityEntityMapper.addActrandxEntityPojoMapper.apply(actrandList, counter.toString()));
					}
				}

				Pbillh pbillhEntity = this.pbillhRepository.findByPblhPartytypeAndPblhPartycodeAndPblhSuppbillno(dbnotehRequestBean.getPartytype().trim(), dbnotehRequestBean.getPartycode().trim(), dbnotehRequestBean.getSuppbillno().trim());
				LOGGER.info("pbillEntity :: {}" , pbillhEntity);
				if(Objects.nonNull(pbillhEntity)) {
					pbillhEntity.setPblhDebitamt(pbillhEntity.getPblhDebitamt() - dbnotehRequestBean.getOrigDbAmnt()+ dbnotehRequestBean.getAmount());
					pbillhEntity.setPblhTdsamount(pbillhEntity.getPblhTdsamount() + dbnotehRequestBean.getOrigTranAmnt() - dbnotehRequestBean.getTdsamount());	
					pbillhEntity.setPblhToday(LocalDateTime.now());
					pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid().trim());
					pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite().trim());
					this.pbillhRepository.save(pbillhEntity);
				}

				Pbilld pbilldEntity = this.pbilldRepository.findByPbldPartyCodeAndPbldSuppbillno(dbnotehRequestBean.getPartycode().trim(), dbnotehRequestBean.getSuppbillno().trim());
				LOGGER.info("pbillEntity :: {}" , pbilldEntity);
				
				List<Dbnoted>  dbnotedEntityList = this.dbnotedRepository.findByDbnotedCK_DbndDbnoteser(dbnotehRequestBean.getDbnoteser());
				LOGGER.info("dbnotedEntityList {} :: ", dbnotedEntityList);

				List<Dbnotevat> dbnotevatEntityList = this.dbnotevatRepository.findByDbnotevatCK_DbnvSer(dbnotehRequestBean.getDbnoteser());
				LOGGER.info("dbnotevatEntityList {} :: ", dbnotevatEntityList);
				
				if(Objects.nonNull(dbnotedEntityList) && Objects.nonNull(dbnotevatEntityList)) {

					this.dbnotehRepository.save(DbnotehMapper.updateDbnotehEntityPojoMapper.apply(dbnotehEntity, dbnotehRequestBean));

					dbnotehRequestBean.getDbnotedRequests().stream().map(dbnoted ->{
						Double localBillQty = pbilldEntity.getPbldQuantity();
						Double localBillDbQty = pbilldEntity.getPbldDbqty();
						Double localBillDeqty = pbilldEntity.getPbldDequantity();
						
						Double localDeqty = dbnoted.getDequantity();
//start of commented by vicky 14-09-2023
//						if(Objects.nonNull(pbilldEntity) && dbnoted.getQuantity() > 0 ) {
//							pbilldEntity.setPbldQuantity(localBillQty - dbnoted.getQuantity());
//							pbilldEntity.setPbldDbqty(localBillDbQty + dbnoted.getQuantity());
//							pbilldEntity.setPbldDequantity(localBillDeqty - localDeqty);
//							pbilldEntity.setPbldToday(LocalDateTime.now());
//							pbilldEntity.setPbldUserid(GenericAuditContextHolder.getContext().getUserid().trim());
//							pbilldEntity.setPbldSite(GenericAuditContextHolder.getContext().getSite().trim());
//							this.pbilldRepository.save(pbilldEntity);
//						}
//end of commented by vicky 14-09-2023

						dbnotedEntityList.stream().filter(dbNotedFilter -> {
							return dbnoted.getDbnoteser().equals(dbNotedFilter.getDbnotedCK().getDbndDbnoteser()) && dbnoted.getLineno().equals(dbNotedFilter.getDbnotedCK().getDbndLineno());
						}).map(dbnotedEntity ->{
							//start of added by vicky 14-09-2023
							if(Objects.nonNull(pbilldEntity) && dbnoted.getQuantity() > 0 ) {
								pbilldEntity.setPbldQuantity(localBillQty + dbnotedEntity.getDbndDequantity() - dbnoted.getQuantity());
								pbilldEntity.setPbldDbqty(localBillDbQty - dbnotedEntity.getDbndDequantity() + dbnoted.getQuantity());
								pbilldEntity.setPbldDequantity(localBillDeqty + dbnotedEntity.getDbndDequantity() - localDeqty);
								pbilldEntity.setPbldToday(LocalDateTime.now());
								pbilldEntity.setPbldUserid(GenericAuditContextHolder.getContext().getUserid().trim());
								pbilldEntity.setPbldSite(GenericAuditContextHolder.getContext().getSite().trim());
								this.pbilldRepository.save(pbilldEntity);
							}
							//end of added by vicky 14-09-2023
//							if(Objects.nonNull(pbilldEntity)) {
//								pbilldEntity.setPbldQuantity(dbnotedEntity.getDbndAmount());  need to ask about this
//								pbilldEntity.setPbldToday(LocalDateTime.now());
//								pbilldEntity.setPbldUserid(GenericAuditContextHolder.getContext().getUserid().trim());
//								pbilldEntity.setPbldSite(GenericAuditContextHolder.getContext().getSite().trim());
//								this.pbilldRepository.save(pbilldEntity);
//							}
							updateDnotedEntityList.add(DbnotedMapper.updateDbnotedEntityPojoMapper.apply(dbnotedEntity, dbnoted));
							return 	dbnotedEntity;
						}).collect(Collectors.toList());
						return dbnoted;
					}).collect(Collectors.toList());

					dbnotehRequestBean.getDbnotevatRequests().stream().map(dbnotevat ->{
						dbnotevatEntityList.stream().filter(dbnotevatEntityFilter -> {
							return dbnotevat.getSer().equals(dbnotevatEntityFilter.getDbnotevatCK().getDbnvSer()) && dbnotevat.getLineno().equals(dbnotevatEntityFilter.getDbnotevatCK().getDbnvLineno());
						}).map(dbnotevatEntity ->{
							updateDnotevatEntityList.add(DbnotevatMapper.updateDbnotevatEntityPojoMapper.apply(dbnotevatEntity, dbnotevat));
							return 	dbnotevatEntity;
						}).collect(Collectors.toList());
						return dbnotevat;
					}).collect(Collectors.toList());
				}
				
				this.dbnotedRepository.saveAll(updateDnotedEntityList);
				this.dbnotevatRepository.saveAll(updateDnotevatEntityList);

				ActranhBean actranhBean = ActranhBean.builder()
						.proprietor(dbnotehRequestBean.getProp())
						.coy(dbnotehRequestBean.getCoy())
						.partycode(dbnotehRequestBean.getPartycode())
						.tranamt(dbnotehRequestBean.getAmount())
						.narrative(dbnotehRequestBean.getNarration())
						.vounum(dbnotehRequestBean.getSuppbillno())
						.voudate(dbnotehRequestBean.getSuppbilldt())
						.bbookyn("N")
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.build(); 

				this.actranhRepository.save(UpdatePojoEntityMapper.updateActranhEntityPojoMapper.apply(actranh, actranhBean));
				this.actrandRepository.deleteActrand(dbnotehRequestBean.getDbnoteser().trim());

				Material materialEntity = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(dbnotehRequestBean.getDbnotedRequests().get(0).getMatgroup(), "1");
				Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(dbnotehRequestBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
				Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(dbnotehRequestBean.getBldgcode().trim());
				List<ActrandBean> actrandList = new ArrayList<>();
				dbnotehRequestBean.setNarration(Objects.nonNull(dbnotehRequestBean.getNarration()) ? dbnotehRequestBean.getNarration().trim() : null); //For Update Narration Length 		
				Double sumOfTaxableAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getTaxableamt).sum();
				Double sumOfCgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getCgstamt).sum();
				Double sumOfSgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getSgstamt).sum();
				Double sumOfIgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getIgstamt).sum();
				Double sumOfUgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getUgstamt).sum();
				Integer bunumCounter = BigInteger.ZERO.intValue();
				if(!dbnotehRequestBean.getAmount().equals(BigInteger.ZERO.doubleValue())) {
					//S. CRS. (S) 

					// added by vicky on 11/11/2021 to post in accounts only if net billamt less tcs is > 0
					if(dbnotehRequestBean.getAmount() > BigInteger.ZERO.doubleValue()) {
						AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), materialEntity.getMatAcminor());
						AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(materialEntity.getMatAcmajor(), null, null, "", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), null);
						AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "PF");
						AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(materialEntity.getMatAcmajor(), "PF");

						bunumCounter += 1;
						actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfTaxableAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
					}
				}
				//CGST BREAKUP
				if(!sumOfCgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402441", "20404391", "20404395", partyEntity.getParGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfCgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
				}
				//SGST BREAKUP
				if(!sumOfSgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402443", "20404392", "20404396", partyEntity.getParGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfSgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
				}
				//IGST BREAKUP
				if(!sumOfIgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402445", "20404393", "20404397", partyEntity.getParGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfIgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
				}
				//UGST BREAKUP
				if(!sumOfUgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402447", "20404394", "20404398", partyEntity.getParGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfUgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
				}
				//TDS BREAKUP
				if(!dbnotehRequestBean.getTdsamount().equals(BigInteger.ZERO.doubleValue())) {
					String tdsacmajor;
					if(dbnotehRequestBean.getBilltype().equals(BillTypeEnum.MATERIAL.getValue()))
						tdsacmajor = "11401824";
					else
						tdsacmajor = "11401828";

					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(tdsacmajor, null, "", "", dbnotehRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "PF");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(tdsacmajor, "PF");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, dbnotehRequestBean.getTdsamount(), buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProp(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
				}

				this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));
//				GenericAuditContextHolder.getContext().setTransactionNo(dbnotehRequestBean.getDbnoteser());
//				GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Successfully updated DebitNote#:  "+ dbnotehRequestBean.getDbnoteser()).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> checkIsQuantityValid(String partyCode, String supplierBillNo, String dbNoteSer, String itemQTy, Double debitAmount) {
		Integer dbNotedQuantitySum = this.dbnotedRepository.findSumOfQuantity(partyCode, supplierBillNo, dbNoteSer);
		Integer locDbQty = Objects.nonNull(dbNotedQuantitySum) ? dbNotedQuantitySum : 0;
		LOGGER.info("DbNoted Quantity Sum :: {}", locDbQty);

		Pbilld pbilldEntity = this.pbilldRepository.findByPbldPartyCodeAndPbldSuppbillno(partyCode, supplierBillNo);
		if(Objects.nonNull(pbilldEntity)) {
			Double locBillQty = pbilldEntity.getPbldDequantity();
			Double locRate = pbilldEntity.getPbldRate();
			Double priQty = Double.valueOf((itemQTy != null && !itemQTy.isEmpty())? itemQTy : BigInteger.ZERO.toString());
			LOGGER.info("Pbilld Dequantity:: {}", locBillQty);
			LOGGER.info("Pbilld Rate:: {}", locRate);
			LOGGER.info("Item Quantity:: {}", priQty);
			if(priQty > (locBillQty - locDbQty) && priQty > 0) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("DN Quantity cannot be more than Bill quantity - Prev. DN Quantity....").build());
			}
			if((priQty * locRate)!= debitAmount && priQty > 0 ) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Difference in Rate Purchased and that in Debit Note.").build());
			}
			if(priQty > 0) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(debitAmount/priQty).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> addDbnote(DbnotehRequestBean dbnotehRequestBean) {
		String transerStr = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "DNSER", GenericAuditContextHolder.getContext().getSite().trim());
		LOGGER.info("TranserStr :: {}" , transerStr);

		Pbillh pbillhEntity = this.pbillhRepository.findByPblhPartytypeAndPblhPartycodeAndPblhSuppbillno(dbnotehRequestBean.getPartytype().trim(), dbnotehRequestBean.getPartycode().trim(), dbnotehRequestBean.getSuppbillno().trim());
		LOGGER.info("pbillEntity :: {}" , pbillhEntity);

		Pbilld pbilldEntity = this.pbilldRepository.findByPbldPartyCodeAndPbldSuppbillno(dbnotehRequestBean.getPartycode().trim(), dbnotehRequestBean.getSuppbillno().trim());
		LOGGER.info("pbillEntity :: {}" , pbilldEntity);

		Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(dbnotehRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());
		if(Objects.nonNull(pbillhEntity)) {
			pbillhEntity.setPblhDebitamt(pbillhEntity.getPblhDebitamt() + dbnotehRequestBean.getAmount());
			pbillhEntity.setPblhTdsamount(pbillhEntity.getPblhTdsamount() - dbnotehRequestBean.getTdsamount());		
			pbillhEntity.setPblhToday(LocalDateTime.now());
			pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid().trim());
			pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite().trim());
			this.pbillhRepository.save(pbillhEntity);
		}

		dbnotehRequestBean.setDbnoteser(transerStr);
		dbnotehRequestBean.setTranser(transerStr);
		this.dbnotehRepository.save(DbnotehMapper.addDbnotehEntityPojoMapper.apply(new Object []{dbnotehRequestBean}));
		if(CollectionUtils.isNotEmpty(dbnotehRequestBean.getDbnotedRequests())) {
			List<Dbnoted> dbnotedEntityList = new ArrayList<>();
			dbnotehRequestBean.getDbnotedRequests().stream().map(
					dbnotedRequest -> {
						
						Double localBillQty = pbilldEntity.getPbldQuantity();
						Double localBillDbQty = pbilldEntity.getPbldDbqty();
						Double localBillDeqty = pbilldEntity.getPbldDequantity();
						Double localDeqty = dbnotedRequest.getDequantity();
						if(Objects.nonNull(pbilldEntity) && dbnotedRequest.getQuantity() > 0 ) {
							pbilldEntity.setPbldQuantity(localBillQty - dbnotedRequest.getQuantity());
							pbilldEntity.setPbldDbqty(localBillDbQty + dbnotedRequest.getQuantity());
							pbilldEntity.setPbldDequantity(localBillDeqty - localDeqty);
							pbilldEntity.setPbldToday(LocalDateTime.now());
							pbilldEntity.setPbldUserid(GenericAuditContextHolder.getContext().getUserid().trim());
							pbilldEntity.setPbldSite(GenericAuditContextHolder.getContext().getSite().trim());
							this.pbilldRepository.save(pbilldEntity);
						}

						dbnotedRequest.setDbnoteser(transerStr);
						dbnotedEntityList.add(DbnotedMapper.addDbnotedEntityPojoMapper.apply(new Object []{dbnotedRequest}));
						return dbnotedRequest;
					}
					).collect(Collectors.toList());
			this.dbnotedRepository.saveAll(dbnotedEntityList);
		}

		if(CollectionUtils.isNotEmpty(dbnotehRequestBean.getDbnotevatRequests())) {
			List<Dbnotevat> dbnotevatEntityList = new ArrayList<>();
			dbnotehRequestBean.getDbnotevatRequests().stream().map(
					dbnotevatRequest -> {
						dbnotevatRequest.setSer(transerStr);
						dbnotevatEntityList.add(DbnotevatMapper.addDbnotevatEntityPojoMapper.apply(new Object []{dbnotevatRequest}));
						return dbnotevatRequest;
					}
					).collect(Collectors.toList());
			this.dbnotevatRepository.saveAll(dbnotevatEntityList);
		}

		this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
				.transer(dbnotehRequestBean.getDbnoteser())
				.trantype(TranTypeEnum.D.toString())
				.trandate(dbnotehRequestBean.getDate())	
				.ledgcode("PL")
				.partytype(CommonConstraints.INSTANCE.SUPPLIERS)
				.partycode(dbnotehRequestBean.getPartycode())
				.tranamt(Double.valueOf(dbnotehRequestBean.getAmount() - dbnotehRequestBean.getTdsamount()))
				.voudate(dbnotehRequestBean.getSuppbilldt())
				.vounum(dbnotehRequestBean.getSuppbillno())
				.postedyn("Y")
				.balancedyn("Y")
				.closingjvyn("N")
				.bbookyn("N")
				.cbookyn("N")
				.proprietor(companyEntity.getCompanyCK().getCoyProp().trim())
				.coy(dbnotehRequestBean.getCoy())
				.site(GenericAuditContextHolder.getContext().getSite())
				.userid( GenericAuditContextHolder.getContext().getUserid())
				.clearacyn("Y")
				.reverseyn("N")
				.narrative(dbnotehRequestBean.getNarration())
				.build()}));

		Material materialEntity = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(dbnotehRequestBean.getDbnotedRequests().get(0).getMatgroup(), "1");
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(dbnotehRequestBean.getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
		Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(dbnotehRequestBean.getBldgcode().trim());
		List<ActrandBean> actrandList = new ArrayList<>();
		Double sumOfTaxableAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getTaxableamt).sum();
		Double sumOfCgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getCgstamt).sum();
		Double sumOfSgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getSgstamt).sum();
		Double sumOfIgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getIgstamt).sum();
		Double sumOfUgstAmt = dbnotehRequestBean.getDbnotevatRequests().parallelStream().mapToDouble(DbnotevatRequestBean::getUgstamt).sum();
		Integer bunumCounter = BigInteger.ZERO.intValue();
		if(!dbnotehRequestBean.getAmount().equals(BigInteger.ZERO.doubleValue())) {
			//S. CRS. (S) 

			// added by vicky on 11/11/2021 to post in accounts only if net billamt less tcs is > 0
			if(dbnotehRequestBean.getAmount() > BigInteger.ZERO.doubleValue()) {
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), materialEntity.getMatAcminor());
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(materialEntity.getMatAcmajor(), null, null, "", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), null);
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "PF");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(materialEntity.getMatAcmajor(), "PF");

				bunumCounter += 1;
				System.out.println("Counter: " + bunumCounter);

				actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfTaxableAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
			}
		}
		//CGST BREAKUP
		if(!sumOfCgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402441", "20404391", "20404395", partyEntity.getParGstno());
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			System.out.println("Counter: " + bunumCounter);
			actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfCgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
		}
		//SGST BREAKUP
		if(!sumOfSgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402443", "20404392", "20404396", partyEntity.getParGstno());
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			System.out.println("Counter: " + bunumCounter);
			actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfSgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotevatRequests().get(0).getQuantity()));
		}
		//IGST BREAKUP
		if(!sumOfIgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402445", "20404393", "20404397", partyEntity.getParGstno());
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			System.out.println("Counter: " + bunumCounter);
			actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfIgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
		}
		//UGST BREAKUP
		if(!sumOfUgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402447", "20404394", "20404398", partyEntity.getParGstno());
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", dbnotehRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "PF");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "PF");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			System.out.println("Counter: " + bunumCounter);
			actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfUgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), dbnotehRequestBean.getDbnotedRequests().get(0).getDequantity()));
		}
		//TDS BREAKUP
		if(!dbnotehRequestBean.getTdsamount().equals(BigInteger.ZERO.doubleValue())) {
			String tdsacmajor;
			if(dbnotehRequestBean.getBilltype().equals(BillTypeEnum.MATERIAL.getValue()))
				tdsacmajor = "11401824";
			else
				tdsacmajor = "11401828";

			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", dbnotehRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(tdsacmajor, null, "", "", dbnotehRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "PF");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(tdsacmajor, "PF");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			System.out.println("Counter: " + bunumCounter);
			actrandList.addAll(insActrand(bunumCounter, dbnotehRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, dbnotehRequestBean.getTdsamount() * -1, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), dbnotehRequestBean.getDbnotedRequests().get(0).getUom().trim(), dbnotehRequestBean.getDate(), BigInteger.ZERO.doubleValue()));
		}

		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));
		
		GenericAuditContextHolder.getContext().setTransactionNo(transerStr);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Successfully added DebitNote#:  "+ transerStr).build());
	}

	@Override
	public ResponseEntity<?> checkIsDebitAmountValid(String partyCode, String supplierBillNo, Double debitAmount) {
		List<Pbillh> pbillEntity = this.pbillhRepository.findByPblhSuppbillnoAndPartyCode(supplierBillNo, partyCode.trim());
		LOGGER.info("PBillEntity :: {}" , pbillEntity);
		if(!pbillEntity.isEmpty()){
			Double billAmount = pbillEntity.get(pbillEntity.size() - 1).getPblhAmount();
			Double pbillhDebitAmount = pbillEntity.get(pbillEntity.size() - 1).getPblhDebitamt();
			LOGGER.info("Bill Debit Amount: {}", pbillhDebitAmount);
			billAmount = billAmount - pbillhDebitAmount;
			if(debitAmount > billAmount) {
				 DecimalFormat decfor = new DecimalFormat("0.00");  
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Debit Amount is more than bill-debit(Rs." +decfor.format(billAmount) +") amount......").build());
			}
			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> reverseDbnote(String dbNoteSer, String matGroup, Double qty) {
		Dbnoteh dbnoteh = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(dbNoteSer.trim());
		if(Objects.nonNull(dbnoteh)) {
			List<Object[]>  pbilldQtyAndSer = this.pbilldRepository.findPbilldQtyAndSerByPartyAndSuppBillAndMatGroup(dbnoteh.getDbnhPartycode().trim(), dbnoteh.getDbnhSuppbillno().trim(), matGroup.trim());
			Double oldQty = Objects.nonNull(pbilldQtyAndSer.get(0)[0]) ? Double.valueOf(pbilldQtyAndSer.get(0)[0].toString()) : 0;
			String pbilldSer = Objects.nonNull(pbilldQtyAndSer.get(0)[1]) ? pbilldQtyAndSer.get(0)[1].toString() : "";
			Double oldDbQty = Objects.nonNull(pbilldQtyAndSer.get(0)[2]) ? Double.valueOf(pbilldQtyAndSer.get(0)[2].toString()) : 0;
			
			LOGGER.info("Pbilld Quantity: {} " ,oldQty );
			LOGGER.info("Pbilld Ser: {} ",pbilldSer);

			List<Object[]> pbillhDebitAndPaidAmnt = this.pbillhRepository.findPbillDebitAndPaidAmntBySer(pbilldSer.trim());
			Double oldDemitAmnt = Objects.nonNull(pbillhDebitAndPaidAmnt.get(0)[0]) ? Double.valueOf(pbillhDebitAndPaidAmnt.get(0)[0].toString()) : 0;
			Double oldPaidAmnt = Objects.nonNull(pbillhDebitAndPaidAmnt.get(0)[1]) ? Double.valueOf(pbillhDebitAndPaidAmnt.get(0)[1].toString()) : 0;
			Double oldTdsAmount = Objects.nonNull(pbillhDebitAndPaidAmnt.get(0)[2]) ? Double.valueOf(pbillhDebitAndPaidAmnt.get(0)[2].toString()) : 0;
			LOGGER.info("Pbillh Debit Amount: {} " ,oldDemitAmnt );
			LOGGER.info("Pbillh Paid Amount: {} ",oldPaidAmnt );
			LOGGER.info("Pbillh TDS Amount: {} ",oldTdsAmount );
			Double newPaidAmount = oldPaidAmnt + dbnoteh.getDbnhAmount();
			Double newDebitAmount = oldDemitAmnt - dbnoteh.getDbnhAmount(); 
			Double newQty = oldQty + qty;
			Double newDbQty = oldDbQty - qty;
			Double newTdsAmount = oldTdsAmount + dbnoteh.getDbnhTdsamount();
			LOGGER.info("New Paid Amount: {} " ,newPaidAmount );
			LOGGER.info("New Debit Amount: {} ",newDebitAmount );
			LOGGER.info("New Quantity: {} ",newQty );
			LOGGER.info("New DB Qty: {} ",newDbQty );
			LOGGER.info("New DB TDS: {} ",newTdsAmount );
			this.pbilldRepository.updatePbilldQtyByPartyAndSuppBillAndMatGroup(newQty, newDbQty,GenericAuditContextHolder.getContext().getUserid().trim(), GenericAuditContextHolder.getContext().getSite().trim(), LocalDateTime.now(), dbnoteh.getDbnhPartycode().trim(), dbnoteh.getDbnhSuppbillno().trim(), matGroup.trim());
			this.pbillhRepository.updatePbillhDebitAndPaidAmntBySer(newDebitAmount,newTdsAmount, newPaidAmount, GenericAuditContextHolder.getContext().getUserid().trim(), GenericAuditContextHolder.getContext().getSite().trim(), LocalDateTime.now(), pbilldSer);
			this.dbnotehRepository.delete(dbnoteh);
			this.dbnotedRepository.deleteDbnotedBySer(dbNoteSer.trim());
			this.dbnotevatRepository.deleteDbnotevatBySer(dbNoteSer.trim());
			String reversalTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "DNSER",GenericAuditContextHolder.getContext().getSite().trim());	
			LOGGER.info("Debit Note Reversal Transer Number : {}" ,reversalTranserNo);
			insertInAccountingForReverseDebitNote(dbNoteSer.trim(), reversalTranserNo, dbnoteh.getDbnhSuppbillno().trim(), dbnoteh.getDbnhPartycode().trim());
			this.actranhRepository.updateActranhReverseYNWithUserAndSiteAndToday("Y", dbNoteSer.trim(), GenericAuditContextHolder.getContext().getUserid().trim(),GenericAuditContextHolder.getContext().getSite().trim(), LocalDateTime.now());
			GenericAuditContextHolder.getContext().setTransactionNo(reversalTranserNo);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Cancelled / Reversed Debitnote # " + dbNoteSer.trim() + " New Transer # " + reversalTranserNo).build());
		}	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}


	public void insertInAccountingForReverseDebitNote(String dbNoteSer, String reversalTranserNo, String suppBillNo, String partyCode) {
		Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndReverseYN(dbNoteSer.trim(), "Y");
		LOGGER.info("ActranH Entity : {} ", actranh);
		if(Objects.nonNull(actranh)) {
			ActranhBean actranhBean = ActranhBean.builder()
					.balancedyn("Y")
					.bbookyn("N")
					.cbookyn("N")
					.clearacyn("Y")
					.closingjvyn("N")
					.coy(Objects.nonNull(actranh.getActranhCK().getActhCoy()) ? actranh.getActranhCK().getActhCoy() : CommonConstraints.INSTANCE.BLANK_STRING)
					.ledgcode("PL")
					.narrative(("Reversal of Transer# '" +dbNoteSer+ "' for suppbillno '" +suppBillNo.trim()+"'").length() > 60 ? ("Reversal of Transer# '" +dbNoteSer+ "' for suppbillno '" +suppBillNo.trim()+"'").substring(0, 60): "Reversal of Transer# '" +dbNoteSer+ "' for suppbillno '" +suppBillNo.trim()+"'" )
					.partycode(partyCode)
					.partytype("S")
					.postedyn("Y")
					.proprietor(Objects.nonNull(actranh.getActhProprietor()) ? actranh.getActhProprietor() : CommonConstraints.INSTANCE.BLANK_STRING)
					.reverseyn("Y")
					.site(GenericAuditContextHolder.getContext().getSite().trim())
					.userid(GenericAuditContextHolder.getContext().getUserid().trim())
					.voudate(Objects.nonNull(actranh.getActhVoudate()) ? actranh.getActhVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : CommonConstraints.INSTANCE.BLANK_STRING )
					.vounum(suppBillNo)
					.today(LocalDateTime.now())
					.trandate(Objects.nonNull(actranh.getActhTrandate())  ?  actranh.getActhTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : CommonConstraints.INSTANCE.BLANK_STRING)
					.tranamt(actranh.getActhTranamt() * -1)
					.transer(reversalTranserNo)
					.trantype("D")			
					.build();		
			this.actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));

			List<Actrand> actrandList = this.actrandRepository.findActdByTranserNo(dbNoteSer);
			LOGGER.info("ActranD List: {}", actrandList.size());
			List<ActrandBean> actrandBeanList = new ArrayList<>();
			for(int i =0 ; i < actrandList.size(); i++) {
				actrandBeanList.add(ActrandBean.builder()
						.acmajor(Objects.nonNull(actrandList.get(i).getActdAcmajor()) ?  actrandList.get(i).getActdAcmajor() : CommonConstraints.INSTANCE.BLANK_STRING)
						.acminor(Objects.nonNull(actrandList.get(i).getActdAcminor()) ? actrandList.get(i).getActdAcminor() : CommonConstraints.INSTANCE.SPACE_STRING )
						.bldgcode(Objects.nonNull(actrandList.get(i).getActdBldgcode()) ? actrandList.get(i).getActdBldgcode() : CommonConstraints.INSTANCE.BLANK_STRING)
						.bunum(actrandList.get(i).getActrandCK().getActdBunum())
						.cfcode(CommonConstraints.INSTANCE.BLANK_STRING)
						.cfgroup(CommonConstraints.INSTANCE.BLANK_STRING)
						.coy(Objects.nonNull(actrandList.get(i).getActrandCK().getActdCoy()) ? actrandList.get(i).getActrandCK().getActdCoy() : CommonConstraints.INSTANCE.BLANK_STRING )
						.docnum(Objects.nonNull(actrandList.get(i).getActdDocnum()) ? actrandList.get(i).getActdDocnum() : CommonConstraints.INSTANCE.BLANK_STRING)
						.docdate(Objects.nonNull(actrandList.get(i).getActdDocdate()) ?  actrandList.get(i).getActdDocdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : CommonConstraints.INSTANCE.BLANK_STRING) 
						.doctype(Objects.nonNull(actrandList.get(i).getActdDoctype()) ? actrandList.get(i).getActdDoctype() : CommonConstraints.INSTANCE.BLANK_STRING)
						.docparcode(partyCode)
						.docpartype("S")
						.matcode(Objects.nonNull(actrandList.get(i).getActdMatcode()) ? actrandList.get(i).getActdMatcode() : CommonConstraints.INSTANCE.BLANK_STRING)
						.matgroup(Objects.nonNull(actrandList.get(i).getActdMatgroup()) ? actrandList.get(i).getActdMatgroup() : CommonConstraints.INSTANCE.BLANK_STRING )
						.mincode(Objects.nonNull(actrandList.get(i).getActdMincode()) ? actrandList.get(i).getActdMincode() : null)
						.mintype(Objects.nonNull(actrandList.get(i).getActdMintype()) ? actrandList.get(i).getActdMintype() : CommonConstraints.INSTANCE.SPACE_STRING)
						.itemqty(Objects.nonNull(actrandList.get(i).getActdItemqty()) ? actrandList.get(i).getActdItemqty() : 0)
						.ledgcode("PL")
						.partycode((Objects.nonNull(actrandList.get(i).getActdPartycode())) ? actrandList.get(i).getActdPartycode() : CommonConstraints.INSTANCE.BLANK_STRING)
						.partytype((Objects.nonNull(actrandList.get(i).getActdPartytype())) ? actrandList.get(i).getActdPartytype() : null)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.project(Objects.nonNull(actrandList.get(i).getActdProject()) ?  actrandList.get(i).getActdProject() : "GL")
						.property(Objects.nonNull(actrandList.get(i).getActdProperty()) ? actrandList.get(i).getActdProperty()  :  CommonConstraints.INSTANCE.BLANK_STRING)
						.proprietor(actrandList.get(i).getActdProprietor())
						.narrative(Objects.nonNull(actrandList.get(i).getActdNarrative()) ? actrandList.get(i).getActdNarrative() : CommonConstraints.INSTANCE.BLANK_STRING )
						.sku(Objects.nonNull(actrandList.get(i).getActdSku()) ? actrandList.get(i).getActdSku() : CommonConstraints.INSTANCE.BLANK_STRING)
						.voudate(Objects.nonNull(actrandList.get(i).getActdVoudate()) ? actrandList.get(i).getActdVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : CommonConstraints.INSTANCE.BLANK_STRING )
						.vounum(Objects.nonNull(actrandList.get(i).getActdVounum()) ? actrandList.get(i).getActdVounum() : CommonConstraints.INSTANCE.BLANK_STRING)
						.trandate(actrandList.get(i).getActdTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.tranamt(actrandList.get(i).getActdTranamt() * -1)
						.trantype("D")
						.xproject(Objects.nonNull(actrandList.get(i).getActdXproject()) ? actrandList.get(i).getActdXproject() : null)
						.transer(reversalTranserNo)
						.xacminor(Objects.nonNull(actrandList.get(i).getActdXacminor()) ? actrandList.get(i).getActdXacminor() : null)
						.xacmajor(Objects.nonNull(actrandList.get(i).getActdXacmajor()) ? actrandList.get(i).getActdXacmajor() : null)
						.site(GenericAuditContextHolder.getContext().getSite().trim())
						.userid(GenericAuditContextHolder.getContext().getUserid().trim())
						.build());
			}
			this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));	
		}
	}

	@Override
	public ResponseEntity<?> retrieveDbnoteForReversal(String dbNoteSer) {
		Dbnoteh  dbnotehEntity = this.dbnotehRepository.findByDbnotehCK_DbnhDbnoteser(dbNoteSer);
		LOGGER.info("dbnotehEntity {} :: ", dbnotehEntity);
		if(Objects.nonNull(dbnotehEntity)){
			String reverseYN = this.actranhRepository.findActranhReversedYNByTranserAndCoy(dbNoteSer.trim(), dbnotehEntity.getDbnhCoy().trim());
			LOGGER.info("Actrang Reverse Y/N : {} ", reverseYN);
			if(reverseYN.trim().equals("Y")) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Debit Note # " + dbNoteSer.trim() + " already reversed !").build());	
			}
			if(StringUtils.isNotBlank(dbnotehEntity.getDbnhAuthno()) || Objects.nonNull(dbnotehEntity.getDbnhAuthno())){
				try {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(objectMapper.readValue(new JSONObject().put("isBillAuthorized", true).toString(), Object.class)).message("Can't reverse this Debit Note, first cancel the authorisation # " + dbnotehEntity.getDbnhAuthno().trim()).build());
				} catch (JsonProcessingException | JSONException e) {
					e.printStackTrace();
				}						
			}
			else {
				List<Dbnoted>  dbnotedEntityList = this.dbnotedRepository.findByDbnotedCK_DbndDbnoteser(dbNoteSer.trim());
				LOGGER.info("dbnotedEntityList {} :: ", dbnotedEntityList);

				List<Dbnotevat> dbnotevatEntityList = this.dbnotevatRepository.findByDbnotevatCK_DbnvSer(dbNoteSer.trim());
				LOGGER.info("dbnotevatEntityList {} :: ", dbnotevatEntityList);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(DbnotehMapper.fetchDbnotehEntityPojoMapper.apply(new Object [] {Arrays.asList(dbnotehEntity), dbnotedEntityList, dbnotevatEntityList})).build());
			}		
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	private List<ActrandBean> insActrand(Integer bunumCounter, DbnotehRequestBean dbnotehRequestBean, AccountingBean accoutingDataForTran, AccountingBean accoutingDataForContraTran,  AccountingBean accoutingDataCashFlow,  AccountingBean accoutingDataCashFlowForContra
			, Double tranamt, String bldgcode, String property, String deuom, String trandate, Double itemQty) {
		List<ActrandBean> actrandBeanList = new ArrayList<>();
			
		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				if(accoutingDataForTran.getAcMajor().startsWith("4"))
					itemQty *= -1;
				actrandBeanList.add(ActrandBean.builder()
						.transer(dbnotehRequestBean.getDbnoteser())
						.bunum(bunumCounter)
						.trantype("D")
						.trandate(trandate)
						.ledgcode("PL")
						.proprietor(dbnotehRequestBean.getProp())
						.coy(dbnotehRequestBean.getCoy().trim())
						.mintype(accoutingDataForTran.getMinType())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(accoutingDataForTran.getAcMajor())
						.acminor(accoutingDataForTran.getAcminor())
						.mincode(accoutingDataForTran.getMinCode())
						.vounum(dbnotehRequestBean.getSuppbillno())
						.voudate(dbnotehRequestBean.getSuppbilldt())
						.tranamt(tranamt)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(dbnotehRequestBean.getNarration())
						.project(accoutingDataForTran.getProject())
						.bldgcode(bldgcode)
						.property(null)
						.matgroup(dbnotehRequestBean.getDbnotedRequests().get(0).getMatgroup())
						.matcode(dbnotehRequestBean.getDbnotedRequests().get(0).getMatcode())
						.sku(StringUtils.isNotBlank(deuom) ? deuom.substring(0,deuom.length()) : null)
						.itemqty(itemQty)//tds item 0
						.docnum(dbnotehRequestBean.getSuppbillno())
						.docdate(dbnotehRequestBean.getSuppbilldt())
						.doctype("M")
						.docpartype(PartyType.S.toString())
						.docparcode(dbnotehRequestBean.getPartycode())
						//X columns (Contra entry)
						.xproject(accoutingDataForContraTran.getProject())
						.xacmajor(accoutingDataForContraTran.getAcMajor())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(dbnotehRequestBean.getPartycode())
						.xpartytype(PartyType.S.toString())
						.xreftranser(dbnotehRequestBean.getDbnoteser())
						.xrefbunum(bunumCounter+1)
						.xreftrandate(trandate)
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.today(LocalDateTime.now())
						.build()
						);
			}else {
				if(accoutingDataForContraTran.getAcMajor().startsWith("4"))
					itemQty *= -1;
				actrandBeanList.add(ActrandBean.builder()
						.transer(dbnotehRequestBean.getDbnoteser())
						.bunum(bunumCounter + 1)
						.trantype("D")
						.trandate(trandate)
						.ledgcode("PL")
						.proprietor(dbnotehRequestBean.getProp())
						.coy(dbnotehRequestBean.getCoy().trim())
						.mintype(accoutingDataForContraTran.getMinType())
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(accoutingDataForContraTran.getAcminor())
						.acmajor(accoutingDataForContraTran.getAcMajor())
						.vounum(dbnotehRequestBean.getSuppbillno())
						.voudate(dbnotehRequestBean.getSuppbilldt())
						.tranamt(tranamt * -1)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(dbnotehRequestBean.getNarration())
						.project(accoutingDataForContraTran.getProject())
						.bldgcode(bldgcode)
						.property(property)
						.matgroup(dbnotehRequestBean.getDbnotedRequests().get(0).getMatgroup())
						.matcode(dbnotehRequestBean.getDbnotedRequests().get(0).getMatcode())
						.sku(StringUtils.isNotBlank(deuom) ? deuom.substring(0,deuom.length()) : null)
						.itemqty(itemQty)
						.docnum(dbnotehRequestBean.getSuppbillno())
						.docdate(dbnotehRequestBean.getSuppbilldt())
						.doctype("M")
						.docpartype(PartyType.S.toString())
						.docparcode(dbnotehRequestBean.getPartycode())
						//X columns (Contra entry)
						.xreftranser(dbnotehRequestBean.getDbnoteser())
						.xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(accoutingDataForTran.getAcMajor())
						.xmintype(accoutingDataForTran.getMinType())
						.xpartycode(dbnotehRequestBean.getPartycode())
						.xproject(accoutingDataForTran.getProject())
						.xreftrandate(trandate)
						.xpartytype(PartyType.S.toString())
						.xrefbunum(bunumCounter)
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.today(LocalDateTime.now())
						.build()
						);
			}
		}
		return actrandBeanList;
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

	@Override
	public ResponseEntity<?> checkIsUomValid(String matgroup, String code) {
		Uom uomEntity = this.uomRepository.findByUomCK_UomMatgroupAndUomCK_UomCode(matgroup.trim(), code.trim());
		if(Objects.isNull(uomEntity)){
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid UOM code.").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());		
	}
}