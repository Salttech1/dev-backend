
package kraheja.purch.purchasebills.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kraheja.arch.projbldg.dataentry.entity.Building;
import kraheja.arch.projbldg.dataentry.repository.BuildingRepository;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.request.PartyAddressRequestBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Company;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.PartyType;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.entityentity.ActrandxEntityEntityMapper;
import kraheja.commons.mappers.entityentity.ActranhxEntityEntityMapper;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.mappers.pojoentity.UpdatePojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActrandxRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.ActranhxRepository;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.CompanyRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;
import kraheja.purch.bean.TempDrscrsageingBean;
import kraheja.purch.bean.request.PbillhRequestBean;
import kraheja.purch.bean.request.PbillvatRequestBean;
import kraheja.purch.bean.request.PurchBillOutstandingRequestBean;
import kraheja.purch.bean.request.Temp_DrscrsageingRequestBean;
import kraheja.purch.bean.response.MatGroupResponseBean;
import kraheja.purch.entity.Dc;
import kraheja.purch.entity.DcCK;
import kraheja.purch.entity.Matcertestimateactual;
import kraheja.purch.entity.Material;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.purch.entity.Pbillvat;
import kraheja.purch.entity.TempMatAuthprint;
import kraheja.purch.entity.Uom;
import kraheja.purch.entity.Uomconv;
import kraheja.purch.enums.BillTypeEnum;
import kraheja.purch.materialpayments.mappers.Temp_DrscrsageingMapper;
import kraheja.purch.purchasebills.mappers.DcMapper;
import kraheja.purch.purchasebills.mappers.PbilldMapper;
import kraheja.purch.purchasebills.mappers.PbillhMapper;
import kraheja.purch.purchasebills.mappers.PbillvatMapper;
import kraheja.purch.purchasebills.service.BillService;
import kraheja.purch.repository.DbnotedRepository;
import kraheja.purch.repository.DbnotehRepository;
import kraheja.purch.repository.DbnotevatRepository;
import kraheja.purch.repository.DcRepository;
import kraheja.purch.repository.MatcertestimateactualRepository;
import kraheja.purch.repository.MatcertlnhdrRepository;
import kraheja.purch.repository.MaterialRepository;
import kraheja.purch.repository.PbilldRepository;
import kraheja.purch.repository.PbillhRepository;
import kraheja.purch.repository.PbillvatRepository;
import kraheja.purch.repository.Temp_DrscrsageingRepository;
import kraheja.purch.repository.UomRepository;
import kraheja.purch.repository.UomconvRepository;

@Service
@Transactional
public class BillServiceImpl implements BillService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private PbillhRepository pbillhRepository;

	@Autowired
	private PbilldRepository pbilldRepository;

	@Autowired
	private PbillvatRepository pbillvatRepository;

	@Autowired
	private  DcRepository dcRepository;

	@Autowired
	private  CompanyRepository companyRepository;

	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private  ActrandxRepository actrandxRepository;

	@Autowired
	private  ActranhxRepository actranhxRepository;

	@Autowired
	private  PartyRepository partyRepository;

	@PersistenceContext
	private  EntityManager entityManager;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MatcertestimateactualRepository matcertestimateactualRepository;

	@Autowired
	private  UomconvRepository uomconvRepository;

	@Autowired
	private  UomRepository uomRepository;

	@Autowired
	private  ActranhRepository actranhRepository;

	@Autowired
	private  ActrandRepository actrandRepository;

	@Autowired
	private  BuildingRepository buildingRepository;

	@Autowired
	private EntityRepository entityRepository;

	@Autowired
	private MatcertlnhdrRepository matcertlnhdrRepository;

	@Autowired
	private DbnotehRepository dbnotehRepository;

	@Autowired
	private DbnotedRepository dbnotedRepository;

	@Autowired
	private DbnotevatRepository dbnotevatRepository;

	@Autowired
	private Temp_DrscrsageingRepository temp_DrscrsageingRepository;

	ObjectMapper objectMapper = new ObjectMapper(); 

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> fetchPurchaseBillBySer(String ser, Boolean isPurchBill) {
		Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(ser.trim());
		LOGGER.info("Pbillh :: {}", pbillhEntity);

		if(Objects.nonNull(pbillhEntity)) {
			if(Objects.nonNull(pbillhEntity.getPblhTdscertno()) && pbillhEntity.getPblhTdscertno().trim().length() > 0)
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("TDS Certificate already printed..  TDS Certificate No = "+ pbillhEntity.getPblhTdscertno() +" use must cancel/reverse this bill for modification.").build());
			else if(ChronoUnit.DAYS.between(pbillhEntity.getPblhToday().toLocalDate(), LocalDate.now()) > 30)// incase if no. of days calc is incorrect : date + BigInteger.ONE.intValue()
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This bill is too old to be modified. If you want to modify this bill, you must cancel / reverse this bill.").build());
			else if(StringUtils.isNotEmpty(pbillhEntity.getPblhAuthnum()))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Authorization for this bill exists. You must cancel that first before bill modification.").build());
			else if(Objects.nonNull(pbillhEntity.getPblhDebitamt()) && !pbillhEntity.getPblhDebitamt().equals(BigInteger.ZERO.doubleValue()) && isPurchBill)
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Debit Note(s) exist for this bill. You must cancel that first before bill modification.").build());
			else {
				Pbilld pbilldEntity = this.pbilldRepository.findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(ser.trim(), BigInteger.ONE.intValue());
				LOGGER.info("Pbilld :: {}", pbilldEntity);

				List<Pbillvat> pbillvatEntityList = this.pbillvatRepository.findByPbillvatCK_PblvSer(ser.trim());
				LOGGER.info("Pbillvat :: {}", pbillvatEntityList);

				Query query = this.entityManager.createNativeQuery("SELECT * FROM DC WHERE dcp_entryno = '"+ser.trim()+"'", Tuple.class);
				List<Tuple> tuplesList = query.getResultList();
				List<Dc> dcEntityList = new ArrayList<Dc>();
				if(CollectionUtils.isNotEmpty(tuplesList)) {
					dcEntityList = tuplesList.stream().map(t -> 
					Dc.builder()
					.dcCK(DcCK.builder().dcpEntryno(t.get(0, String.class)).dcpSuppcode(t.get(1, String.class)).dcpDcno(t.get(4, String.class)).build())
					.dcpBilldt(t.get(2, Timestamp.class).toLocalDateTime().toLocalDate())
					.dcpSuppbill(t.get(3, String.class))
					.dcpDcdate(t.get(5, Timestamp.class).toLocalDateTime().toLocalDate())
					.dcpPukka(t.get(6, Character.class))
					.dcpSite(t.get(7, String.class))
					.dcpUserid(t.get(8, String.class))
					.dcpToday(t.get(9, Timestamp.class).toLocalDateTime())
					.dcpOrigsite(t.get(10, String.class))
					.build()).collect(Collectors.toList());
					LOGGER.info("Tuple :: {}", dcEntityList);
				}

				Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(pbillhEntity.getPblhPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), pbillhEntity.getPblhPartytype().trim());
				LOGGER.info("Party :: {}", partyEntity);

				Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(pbillhEntity.getPblhPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
				LOGGER.info("Address :: {}", addressEntity);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PbillhMapper.fetchPbillhEntityPojoMapper.apply(new Object [] {pbillhEntity, pbilldEntity, pbillvatEntityList, dcEntityList, partyEntity, addressEntity})).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> addNewGstBill(PbillhRequestBean pbillhRequestBean) {
		Building building = this.buildingRepository.findByBuildingCK_BldgCode(pbillhRequestBean.getBldgcode());
		if(Objects.nonNull(building)) {
			LOGGER.info(" Building Entity :{} ", building);
			Query debitTypeCheckQuery = this.entityManager.createNativeQuery("SELECT INSTR(bmap_blockcerttype,'".concat(Objects.nonNull(pbillhRequestBean.getDebsocyn()) && StringUtils.isNotBlank(pbillhRequestBean.getDebsocyn()) ? pbillhRequestBean.getDebsocyn().trim() : CommonConstraints.INSTANCE.SPACE_STRING).concat("') from bldgmap where bmap_ebldgcode = '").concat(pbillhRequestBean.getBldgcode().trim()).concat("'"));
			if(!debitTypeCheckQuery.getResultList().isEmpty() && 
					(debitTypeCheckQuery.getResultList().get(0)!=null && Integer.parseInt(debitTypeCheckQuery.getResultList().get(0).toString()) > 0)) {
				try {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This Expense type is blocked ...Can't proceed.").data(objectMapper.readValue(new JSONObject().put("isDebitType", true).toString(), Object.class)).build());
				} catch (JsonProcessingException | JSONException e) {
					e.printStackTrace();
				}	
			}
			if(Objects.nonNull(building.getBldgBldgtype()) && (building.getBldgBldgtype().equals("H") || building.getBldgBldgtype().equals("M"))) {
				Integer matcertlnhdrCount = this.matcertlnhdrRepository.findMatcertlnhdrCountByProjCode(pbillhRequestBean.getBldgcode().trim());   
				LOGGER.info(" matcertlnhdrCount :{} ", matcertlnhdrCount);
				
				Query getVendormatcertamtdtlsCountQuery = entityManager.createNativeQuery("SELECT count(*) FROM v_lnvendormatcertamtdtls  WHERE mclh_projcode = '".concat(pbillhRequestBean.getBldgcode().trim()).concat("' AND mclw_matcerttype = '").concat("Material").concat("' AND mclw_matcertcode = '").concat(pbillhRequestBean.getPbilldRequestBean().getMatgroup().trim()).concat("'"));
				LOGGER.info("getVendormatcertamtdtlsCountQuery :{} ", getVendormatcertamtdtlsCountQuery.getSingleResult());
				
				Integer matcertAmtdlsCount = ((Number) getVendormatcertamtdtlsCountQuery.getSingleResult()).intValue();
				LOGGER.info(" matcertAmtdlsCount :{} ", matcertAmtdlsCount);
				
				if(matcertlnhdrCount > 0 && matcertAmtdlsCount >0) {
					Query checkLogicNoteExistsQuery = entityManager.createNativeQuery("SELECT mclh_logicnotenum , finalbillcloseyn FROM v_lnvendormatcertamtdtls WHERE mclh_projcode = '".concat(pbillhRequestBean.getBldgcode().trim())
							.concat("'  AND mclw_matcerttype = '").concat("Material").concat("' AND mclw_matcertcode = '").concat( pbillhRequestBean.getPbilldRequestBean().getMatgroup().trim()).concat("' AND mcvh_partytype = '").concat("S").concat("' AND mcvh_partycode = '").concat(pbillhRequestBean.getPartycode().trim()).concat("'"));				   
					List<Object[]> logicNoteQueryResult = checkLogicNoteExistsQuery.getResultList();
					if(logicNoteQueryResult.isEmpty()) {
						String entityChar1 = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1_EntChar3(pbillhRequestBean.getPartycode().trim(), pbillhRequestBean.getBldgcode().trim());
						String entityPartyCode = Objects.nonNull(entityChar1) ? entityChar1 : "";
						if(!entityPartyCode.equals(pbillhRequestBean.getPartycode())) {
							try {
								return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(objectMapper.readValue(new JSONObject().put("isLogicNote", true).toString(), Object.class)).message("Logic note for this project+vendor is not entered. Please check.....").build());
							} catch (JsonProcessingException | JSONException e) {
								e.printStackTrace();
							}	
						}
					}
					else {
						String finalBillCloseYN = Objects.nonNull(logicNoteQueryResult.get(0)[1]) ? logicNoteQueryResult.get(0)[1].toString() : CommonConstraints.INSTANCE.BLANK_STRING;
						LOGGER.info("FinalBill Close Y/N : {}", finalBillCloseYN);
						if(finalBillCloseYN.trim().equals("Y")) {
							String finalBillCloseFromEntity = this.entityRepository.findEntityForFinalBill();
							if(finalBillCloseFromEntity.equals("N")) {
								try {
									return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(objectMapper.readValue(new JSONObject().put("isFinalBill", true).toString(), Object.class)).message("Purchase Bill Entry is not allowed as Final billing is already done.....").build());
								} catch (JsonProcessingException | JSONException e) {
									e.printStackTrace();
								}	
							}
						}
					}
				}
			}
		}

		//Validation check for total supplier bill amount
		Matcertestimateactual matcertestimateactualEntity  = this.matcertestimateactualRepository.findByMatcertestimateactualCK_ClseProjcodeAndMatcertestimateactualCK_ClseMatcerttypeAndMatcertestimateactualCK_ClseMatcertcodeAndMatcertestimateactualCK_ClsePartycodeAndMatcertestimateactualCK_ClsePartytype(pbillhRequestBean.getBldgcode(), "Material", pbillhRequestBean.getPbilldRequestBean().getMatgroup(), pbillhRequestBean.getPartycode(), pbillhRequestBean.getPartytype());
		LOGGER.info("MatcertestimateactualEntity :: {}", matcertestimateactualEntity);

		if(Objects.nonNull(matcertestimateactualEntity)) {
			Double totalAmountPaidToSupplier;
			Double contractValue;
			Double estimateValue;
			Boolean bollocallowpayment = Boolean.FALSE;
			Query createNativeQuery = this.entityManager.createNativeQuery("SELECT SUM(case AUTH_AUTHTYPE when 'A' then 0 when 'L' then 0  else AUTH_AUTHAMOUNT end) + SUM(case AUTH_AUTHTYPE when 'A' then AUTH_AUTHAMOUNT else 0 end) - SUM(case AUTH_AUTHTYPE when 'A' then 0 else auth_advadjust end) from auth_h where auth_authstatus not in ('6','8') and TRIM(auth_partycode)=:partycode AND TRIM(auth_bldgcode)=:bldgcode AND TRIM(auth_matgroup)= :matgroup");
			createNativeQuery.setParameter("partycode", pbillhRequestBean.getPartycode());
			createNativeQuery.setParameter("bldgcode", pbillhRequestBean.getBldgcode());
			createNativeQuery.setParameter("matgroup", pbillhRequestBean.getPbilldRequestBean().getMatgroup());

			Object singleResult = createNativeQuery.getSingleResult();
			totalAmountPaidToSupplier = Objects.nonNull(singleResult) ? Double.valueOf(singleResult.toString()) : BigInteger.ZERO.doubleValue();

			contractValue = this.matcertestimateactualRepository.findClseContractValByClseProjcodeAndClseMatcerttypeAndClseMatcertcodeAndClsePartycode(pbillhRequestBean.getBldgcode(), "Material", pbillhRequestBean.getPbilldRequestBean().getMatgroup(), pbillhRequestBean.getPartytype(), pbillhRequestBean.getPartycode());
			estimateValue = this.matcertestimateactualRepository.findClseEstimateamtValByClseProjcodeAndClseMatcerttypeAndClseMatcertcodeAndClsePartycode(pbillhRequestBean.getBldgcode(), "Material", pbillhRequestBean.getPbilldRequestBean().getMatgroup(), pbillhRequestBean.getPartytype(), pbillhRequestBean.getPartycode());
			if(!pbillhRequestBean.getIsUpdate())//TODO :: NEED TO CHECK if this line is neccessary
				totalAmountPaidToSupplier += pbillhRequestBean.getAmount();


			if(contractValue.equals(BigInteger.ZERO.doubleValue()) && estimateValue > BigInteger.ZERO.doubleValue())
				bollocallowpayment = Boolean.TRUE;

			if(totalAmountPaidToSupplier > contractValue && (!bollocallowpayment))
				return  ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("The total bill amount of this supplier is exceeding the contract value...").build());

		}
		Company companyEntity = this.companyRepository.findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(pbillhRequestBean.getCoy().trim(), CommonUtils.INSTANCE.closeDate());

		//TDS 194Q
		Double totalBillAmt194q = BigInteger.ZERO.doubleValue(); //'TOTAL purchase less debit note upto sysdate
		//get tran date based on add or update from actrnh or function
		Integer trandateyyyymm = Integer.valueOf(getGstTranDateYYYYMM(pbillhRequestBean.getSuppbilldt(), pbillhRequestBean.getSer(), pbillhRequestBean.getIsUpdate()));

		if(pbillhRequestBean.getBilltype().equals(BillTypeEnum.MATERIAL.getValue())) {
			if(pbillhRequestBean.getTcsamount() > BigInteger.ZERO.doubleValue()) {
				pbillhRequestBean.setTdsamount(BigInteger.ZERO.doubleValue());
				pbillhRequestBean.setTdsperc(BigInteger.ZERO.doubleValue());
			}else {
				Double num1 = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("T194Q", companyEntity.getCompanyCK().getCoyProp().trim());
				if(Objects.nonNull(num1)) {
					Query sumQuery = this.entityManager.createNativeQuery("SELECT nvl(SUM(pblh_amount - pblh_debitamt),0)  as TotAmt,nvl(SUM(pblh_tcsamount),0)  as TottcsAmt,nvl(SUM(pblh_tdsamount),0) as TottdsAmt FROM	pbillh,actranh WHERE TRIM(pblh_prop) = :prop AND pblh_billtype = 'M' AND TRIM(pblh_partycode) = :partycode AND ACTh_coy = PBLH_coy AND ACTh_TRANSER = PBLH_SER AND ACTH_POSTEDYN = 'Y' and	acth_reverseyn = 'N' and trunc(acth_trandate) BETWEEN GETFIRSTDATEOFYRMTH(GETSTARTYRMTHOFACYEAR(:trandateyyyymm)) AND SYSDATE", Tuple.class);

					sumQuery.setParameter("prop", companyEntity.getCompanyCK().getCoyProp().trim());
					sumQuery.setParameter("partycode", pbillhRequestBean.getPartycode().trim());
					sumQuery.setParameter("trandateyyyymm", trandateyyyymm);

					List<Tuple> result = sumQuery.getResultList();
					if(CollectionUtils.isNotEmpty(result)) {
						totalBillAmt194q = result.stream().mapToDouble(tuple -> tuple.get(0, BigDecimal.class).doubleValue()).sum();

						String numArray = this.entityRepository.findByNumsEntityCk_EntClassAndEntityCk_EntId("T194Q", companyEntity.getCompanyCK().getCoyProp().trim());
						String[] array = numArray.split(CommonConstraints.INSTANCE.COMMA_STRING);
						Double perc194q = Double.valueOf(array[0]);
						Double limit194q = Double.valueOf(array[1]);

						if(!pbillhRequestBean.getIsUpdate())
							totalBillAmt194q += pbillhRequestBean.getAmount();

						if(totalBillAmt194q > limit194q)
							totalBillAmt194q -= limit194q;
						else {
							totalBillAmt194q = BigInteger.ZERO.doubleValue();
							perc194q = BigInteger.ZERO.doubleValue();
						}
						pbillhRequestBean.setTdsperc(perc194q);
						if(perc194q != BigInteger.ZERO.doubleValue()) {
							pbillhRequestBean.setTdsamount(Double.valueOf(Math.round((pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(PbillvatRequestBean::getTaxableamt).sum() * perc194q) / 100) + 1 ));
						}else
							pbillhRequestBean.setTdsamount(BigInteger.ZERO.doubleValue());
					}
				}
			}
		}

		//UOM Conversion 
		Material materialEntity = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(pbillhRequestBean.getPbilldRequestBean().getMatgroup(), "1");
		Uomconv uomconvEntity = this.uomconvRepository.findByUomconvCK_UcnvFrcodeAndUomconvCK_UcnvTocode(pbillhRequestBean.getPbilldRequestBean().getDeuom(), materialEntity.getMatSku());

		Double quantity = BigInteger.ZERO.doubleValue();
		Double rate = BigInteger.ZERO.doubleValue();
		if(Objects.nonNull(uomconvEntity)) {
			quantity = pbillhRequestBean.getPbilldRequestBean().getQuantity() * uomconvEntity.getUcnvFactor();
			rate = pbillhRequestBean.getPbilldRequestBean().getDerate() * (1 / uomconvEntity.getUcnvFactor());

		}
		ValueContainer<String> matAcMajor = new ValueContainer<>(materialEntity.getMatAcmajor());
		ValueContainer<String> acMinor = new ValueContainer<>(materialEntity.getMatAcminor());

		ValueContainer<Double> tranAmount = new ValueContainer<>(BigInteger.ZERO.doubleValue());
		if(StringUtils.isNotBlank(pbillhRequestBean.getDebsocyn()))
			if(pbillhRequestBean.getDebsocyn().trim().equals("A") || pbillhRequestBean.getDebsocyn().trim().equals("S")) {
				matAcMajor.setValue("20404517");
				acMinor.setValue(pbillhRequestBean.getBldgcode());
			}
		if(pbillhRequestBean.getBilltype().equals(BillTypeEnum.TRANSPORT.getValue())) {
			matAcMajor.setValue("40503005");
			acMinor.setValue(" "); 
		}

		//Qty and rate is already calculated above
		String uom;
		if(Objects.isNull(uomconvEntity))
			uom = pbillhRequestBean.getPbilldRequestBean().getDeuom();
		else
			uom = uomconvEntity.getUomconvCK().getUcnvTocode();

		String serNumber = CommonConstraints.INSTANCE.BLANK_STRING;
		if(pbillhRequestBean.getIsUpdate()) {
			serNumber = pbillhRequestBean.getSer();
		}else {
			if(Objects.nonNull(companyEntity)) {
				serNumber = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "BPSER", GenericAuditContextHolder.getContext().getSite());
				pbillhRequestBean.setProp(companyEntity.getCompanyCK().getCoyProp().trim());
				pbillhRequestBean.setProject(building.getBldgProject());
				pbillhRequestBean.setPartytype(PartyType.S.toString());
				pbillhRequestBean.setDate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				pbillhRequestBean.setSer(serNumber);
			}
		}
		String gstTranDate = getGstTranDate(pbillhRequestBean.getSuppbilldt(), pbillhRequestBean.getSer(), pbillhRequestBean.getIsUpdate());
		//Accounting Logic and entry 
		//Actranh
		if(pbillhRequestBean.getIsUpdate()) {
			Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndActranhCK_ActhCoy(pbillhRequestBean.getSer(), pbillhRequestBean.getCoy().trim());
			if(Objects.nonNull(actranh)) {
				if(StringUtils.isNotBlank(actranh.getActhBbookyn()) && actranh.getActhBbookyn().trim().equals("Y")) {
					Integer counter = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("#REVN", "#REVN").intValue();

					this.actranhxRepository.save(ActranhxEntityEntityMapper.addActranhxEntityPojoMapper.apply(new Object[] {actranh, counter.toString(), companyEntity.getCoyCurrepnum() + 1 }));
					companyEntity.setCoyCurrepnum(companyEntity.getCoyCurrepnum() + 1);
					this.companyRepository.save(companyEntity);
					List<Actrand> actrandList = this.actrandRepository.findByActrandCK_ActdTranserAndActrandCK_ActdCoy(pbillhRequestBean.getSer(), pbillhRequestBean.getCoy());
					if(CollectionUtils.isNotEmpty(actrandList)) {
						this.actrandxRepository.saveAll(ActrandxEntityEntityMapper.addActrandxEntityPojoMapper.apply(actrandList, counter.toString()));
					}
				}

				Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(pbillhRequestBean.getSer().trim());
				LOGGER.info("Pbillh :: {}", pbillhEntity);

				Pbilld pbilldEntity = this.pbilldRepository.findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(pbillhRequestBean.getSer().trim(), BigInteger.ONE.intValue());
				LOGGER.info("Pbilld :: {}", pbilldEntity);

				pbillhRequestBean.setOmspurcyn(Objects.nonNull(pbillhRequestBean.getState()) && pbillhRequestBean.getState().equals("MAH") ? "N" : "Y");

				//				if(pbillhRequestBean.getRetention() > 0 && pbillhRequestBean.getRetainos() <= 0) // Condition added after being added in old erp today as on 03/05/2023
				//                	pbillhRequestBean.setRetainos(pbillhRequestBean.getRetention());

				this.pbillhRepository.save(PbillhMapper.updatePbillhPojoEntityMapper.apply(pbillhEntity, pbillhRequestBean));
				this.pbilldRepository.save(PbilldMapper.updatePbilldEntityPojoMapper.apply(pbilldEntity, pbillhRequestBean.getPbilldRequestBean()));

				this.pbillvatRepository.deletePbillvatBySer(pbillhRequestBean.getSer().trim());
				// We delete exisiting records and insert new ones
				this.pbillvatRepository.saveAll(PbillvatMapper.addPbillvatPojoEntityMapper.apply(new Object[] {pbillhRequestBean.getPbillvatRequestBean(), pbillhRequestBean.getSer(), uom, quantity, rate}));

				// We delete exisiting records and insert new ones
				//				this.dcRepository.deleteDcByEntryNo(pbillhRequestBean.getSer());
				Query createQuery = this.entityManager.createQuery("Delete Dc d where trim(d.dcCK.dcpEntryno) = :entryno");
				createQuery.setParameter("entryno", pbillhRequestBean.getSer());
				int rowCount = createQuery.executeUpdate();
				List<Dc> apply = DcMapper.addDcEntityPojoMapper.apply(pbillhRequestBean.getDcRequestBean(), pbillhRequestBean.getSer());
				//				if(rowCount != 0)
				this.dcRepository.saveAll(apply);
			}
			ActranhBean actranhBean = ActranhBean.builder()
					.proprietor(pbillhRequestBean.getProp())
					.coy(pbillhRequestBean.getCoy())
					.partycode(pbillhRequestBean.getPartycode())
					.tranamt(Double.valueOf(pbillhRequestBean.getAmount() - pbillhRequestBean.getTdsamount()))
					.narrative(pbillhRequestBean.getNarration())
					.vounum(pbillhRequestBean.getSuppbillno())
					.voudate(pbillhRequestBean.getSuppbilldt())
					.bbookyn("N")
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid(GenericAuditContextHolder.getContext().getUserid())
					.build(); 

			this.actranhRepository.save(UpdatePojoEntityMapper.updateActranhEntityPojoMapper.apply(actranh, actranhBean));
			this.actrandRepository.deleteActrand(pbillhRequestBean.getSer().trim());
		}else {
			this.pbillhRepository.save(PbillhMapper.addPbillhPojoEntityMapper.apply(pbillhRequestBean));

			pbillhRequestBean.getPbilldRequestBean().setUom(uom);
			pbillhRequestBean.getPbilldRequestBean().setQuantity(quantity);
			this.pbilldRepository.save(PbilldMapper.addPbilldPojoEntityMapper.apply(pbillhRequestBean.getPbilldRequestBean(), serNumber));

			this.pbillvatRepository.saveAll(PbillvatMapper.addPbillvatPojoEntityMapper.apply(new Object[] {pbillhRequestBean.getPbillvatRequestBean(), serNumber, uom, quantity, rate}));
			this.dcRepository.saveAll(DcMapper.addDcEntityPojoMapper.apply(pbillhRequestBean.getDcRequestBean(), serNumber));

			this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
					.transer(pbillhRequestBean.getSer())
					.trantype(TranTypeEnum.BA.toString())
					.trandate(gstTranDate)	
					.ledgcode("PL")
					.partytype(CommonConstraints.INSTANCE.SUPPLIERS)
					.partycode(pbillhRequestBean.getPartycode())
					.tranamt(Double.valueOf(pbillhRequestBean.getAmount() - pbillhRequestBean.getTdsamount()))
					.voudate(pbillhRequestBean.getSuppbilldt())
					.vounum(pbillhRequestBean.getSuppbillno())
					.postedyn("Y")
					.balancedyn("Y")
					.closingjvyn("N")
					.bbookyn("N")
					.cbookyn("N")
					.narrative(pbillhRequestBean.getNarration())
					.proprietor(companyEntity.getCompanyCK().getCoyProp().trim())
					.coy(pbillhRequestBean.getCoy())
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid( GenericAuditContextHolder.getContext().getUserid())
					.clearacyn("Y")
					.reverseyn("N")
					.build()}));
		}


		//		Actrand actrand = this.actrandRepository.findByActdTranserAndActdCoyAndActdProp(serNumber, pbillhRequestBean.getCoy().trim(), companyEntity.getCompanyCK().getCoyProp().trim());
		//		if(Objects.nonNull(actrand))
		//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Entry with same ser present in actrand").build());

		//Already fetched material entity above 
		if(Objects.nonNull(materialEntity)) {
			List<ActrandBean> actrandList = new ArrayList<>();
			//In bove select was given 12.4565652239 issue
			Double sumOfTaxableAmt = pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(data -> data.getTaxableamt() + data.getFotoamt()).sum();
			// start of multiple decimals problem issue in actrand  vicky 20240301
			BigDecimal bdsumOfTaxableAmt = new BigDecimal(sumOfTaxableAmt).setScale(2, RoundingMode.HALF_UP);  
			sumOfTaxableAmt = bdsumOfTaxableAmt.doubleValue();
			// 	end of multiple decimals problem issue in actrand  vicky 20240301			
			Double sumOfCgstAmt = pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(PbillvatRequestBean::getCgstamt).sum();
			// start of multiple decimals problem issue in actrand  vicky 20240301
			BigDecimal bdsumOfCgstAmt = new BigDecimal(sumOfCgstAmt).setScale(2, RoundingMode.HALF_UP);  
			sumOfCgstAmt = bdsumOfCgstAmt.doubleValue();
			// 	end of multiple decimals problem issue in actrand  vicky 20240301			
			Double sumOfSgstAmt = pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(PbillvatRequestBean::getSgstamt).sum();
			// start of multiple decimals problem issue in actrand  vicky 20240301
			BigDecimal bdsumOfSgstAmt = new BigDecimal(sumOfSgstAmt).setScale(2, RoundingMode.HALF_UP);  
			sumOfSgstAmt = bdsumOfSgstAmt.doubleValue();
			// end of multiple decimals problem issue in actrand  vicky 20240301			
			Double sumOfIgstAmt = pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(PbillvatRequestBean::getIgstamt).sum();
			// start of multiple decimals problem issue in actrand  vicky 20240301
			BigDecimal bdsumOfIgstAmt = new BigDecimal(sumOfIgstAmt).setScale(2, RoundingMode.HALF_UP);  
			sumOfIgstAmt = bdsumOfIgstAmt.doubleValue();
			// end of multiple decimals problem issue in actrand  vicky 20240301			
			Double sumOfUgstAmt = pbillhRequestBean.getPbillvatRequestBean().parallelStream().mapToDouble(PbillvatRequestBean::getUgstamt).sum();
			// start of multiple decimals problem issue in actrand  vicky 20240301
			BigDecimal bdsumOfUgstAmt = new BigDecimal(sumOfUgstAmt).setScale(2, RoundingMode.HALF_UP);  
			sumOfUgstAmt = bdsumOfUgstAmt.doubleValue();
			// end of multiple decimals problem issue in actrand  vicky 20240301			
			Building buildingEntity = this.buildingRepository.findByBuildingCK_BldgCode(pbillhRequestBean.getBldgcode().trim());
			if(Objects.nonNull(buildingEntity)) {
				Integer bunumCounter = BigInteger.ZERO.intValue();
				//From here we check conditions for breakup types
				//TODO : might generate few differences because of logic flow
				//if gstno is blank
				tranAmount.setValue(StringUtils.isNotBlank(pbillhRequestBean.getPartyRequestBean().getGstno()) ? 
						pbillhRequestBean.getAmount() - sumOfCgstAmt - sumOfSgstAmt - sumOfIgstAmt - sumOfUgstAmt
						: pbillhRequestBean.getAmount());

				//if tcs is not 0
				if(!pbillhRequestBean.getTcsamount().equals(BigInteger.ZERO.doubleValue()))
					tranAmount.setValue(pbillhRequestBean.getAmount() - pbillhRequestBean.getTcsamount());

				//BREAKUP 1
				if(!pbillhRequestBean.getAmount().equals(BigInteger.ZERO.doubleValue())) {
					//S. CRS. (S) 

					// added by vicky on 11/11/2021 to post in accounts only if net billamt less tcs is > 0
					if(tranAmount.getValue() > BigInteger.ZERO.doubleValue()) {
						String mintype = "";
						String mincode = "";
						if(matAcMajor.getValue().trim().equals("20104005")) {
							mincode = materialEntity.getMatAcmatgroup();
							mintype = "M";
						}
						else if(matAcMajor.getValue().trim().equals("20404517")) {
							mincode = building.getBuildingCK().getBldgCode();
							mintype = "B"; 
						}
						AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", mintype, mincode, PartyType.S.toString(), pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), acMinor.getValue());
						AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(matAcMajor.getValue(), mintype, mincode, PartyType.S.toString(), pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), acMinor.getValue());
						AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "BA");
						AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(matAcMajor.getValue(), "BA");

						bunumCounter += 1;
						actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfTaxableAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
					}
				}
				//CGST BREAKUP
				if(!sumOfCgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402441", "20404391", "20404395", pbillhRequestBean.getPartyRequestBean().getGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfCgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}
				//SGST BREAKUP
				if(!sumOfSgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402443", "20404392", "20404396", pbillhRequestBean.getPartyRequestBean().getGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfSgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}
				//IGST BREAKUP
				if(!sumOfIgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402445", "20404393", "20404397", pbillhRequestBean.getPartyRequestBean().getGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfIgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}
				//UGST BREAKUP
				if(!sumOfUgstAmt.equals(BigInteger.ZERO.doubleValue())) {
					AccountingBean acmajor = getAcMajor("11402447", "20404394", "20404398", pbillhRequestBean.getPartyRequestBean().getGstno());
					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfUgstAmt, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}
				//TDS BREAKUP
				if(!pbillhRequestBean.getTdsamount().equals(BigInteger.ZERO.doubleValue())) {
					String tdsacmajor;
					if(pbillhRequestBean.getBilltype().equals(BillTypeEnum.MATERIAL.getValue()))
						tdsacmajor = "11401824";
					else
						tdsacmajor = "11401828";

					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(tdsacmajor, null, "", "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(tdsacmajor, "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, pbillhRequestBean.getTdsamount() * -1, buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}
				//TCS BREAKUP
				if(!pbillhRequestBean.getTcsamount().equals(BigInteger.ZERO.doubleValue())) {
					String tcsacmajor = "20404565";

					Query ltdQuery = this.entityManager.createNativeQuery("select (GETASSYEAR(TO_DATE(:trandate,'DD/MM/YYYY'))) from dual");
					ltdQuery.setParameter("trandate", gstTranDate);

					String assyear = ltdQuery.getSingleResult().toString();
					LOGGER.info("SingleResult :: ", assyear.toString());

					AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401019", null, "", "S", pbillhRequestBean.getPartycode(), buildingEntity.getBldgProject(), "");
					AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(tcsacmajor, "A", assyear, "", pbillhRequestBean.getPartycode(), null, null);
					AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401019", "BA");
					AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(tcsacmajor, "BA");

					bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
					actrandList.addAll(insActrand(bunumCounter, pbillhRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, pbillhRequestBean.getTcsamount(), buildingEntity.getBuildingCK().getBldgCode(), buildingEntity.getBldgProperty(), uom, gstTranDate));
				}

				this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));
			}
		}
		GenericAuditContextHolder.getContext().setTransactionNo(serNumber);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Transaction saved successfully. Entry No. "+ serNumber+" and Tds Amount = "+pbillhRequestBean.getTdsamount()+" and Tds Percent% = "+pbillhRequestBean.getTdsperc()).data(serNumber).build());
	}

	@Override
	public ResponseEntity<?> fetchPartyAndAddressDetails(String partyCode) {
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode.trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
		LOGGER.info("Party :: {}", partyEntity);

		Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(partyCode.trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
		LOGGER.info("Address :: {}", addressEntity);

		if(Objects.nonNull(partyEntity)) {
			if(Objects.nonNull(addressEntity)) {
				if(CommonUtils.INSTANCE.checkIfDateInBetween(addressEntity.getAdrToday().toLocalDate(), null, LocalDate.now(), 6L, Boolean.TRUE))
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PartyMapper.fetchPartyAndAddressEntityPojoMapper.apply(partyEntity,  addressEntity)).extraData(Boolean.FALSE.toString()).build());
				else
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PartyMapper.fetchPartyAndAddressEntityPojoMapper.apply(partyEntity,  addressEntity)).extraData(Boolean.TRUE.toString()).build());
			}else
				return ResponseEntity.ok(ServiceResponseBean.builder().message("Please add address for the party before proceeding.").status(Boolean.FALSE).build());
		}else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());

	}

	@Override
	public ResponseEntity<?> updatePartyAndAddressDetails(PartyAddressRequestBean partyAddressRequestBean) {
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyAddressRequestBean.getPartyRequestBean().getPartycode().trim(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), partyAddressRequestBean.getPartyRequestBean().getPartytype().trim());
		LOGGER.info("Party :: {}", partyEntity);

		Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(partyAddressRequestBean.getPartyRequestBean().getPartycode().trim(), CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype, CommonConstraints.INSTANCE.adrAdser);
		LOGGER.info("Address :: {}", addressEntity);

		if(Objects.nonNull(partyEntity) || Objects.nonNull(addressEntity)) {
			this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, partyAddressRequestBean.getPartyRequestBean()));
			this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressEntity, partyAddressRequestBean.getAddressRequestBean()));
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());	
		}else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found for party code " + partyAddressRequestBean.getPartyRequestBean().getPartycode().trim()).build());
	}

	@Override
	public ResponseEntity<?> checkSupplierBillNumberExists(String supplierNumber, String partyCode) {
		List<Pbillh> pbillEntity = this.pbillhRepository.findByPblhSuppbillnoAndPartyCode(supplierNumber, partyCode.trim());
		LOGGER.info("PBillEntity :: {}" , pbillEntity);
		if(!pbillEntity.isEmpty()){
			if(Objects.nonNull(pbillEntity.get(pbillEntity.size() - 1).getPblhSuppbilldt()))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This bill dated "+pbillEntity.get(pbillEntity.size() - 1).getPblhSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) + " is already received.").build());
			else
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This supplier bill is already present").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> addIntoPurchBillOutstandingTempTable(PurchBillOutstandingRequestBean purchBillOutstandingRequestBean) {
		String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");

		String whereCondition = " ";
		String whereConditionForAgeingReport = " ";
		if(Objects.isNull(purchBillOutstandingRequestBean.getSuppBillFromDate()) && Objects.isNull(purchBillOutstandingRequestBean.getSuppBillToDate())){
			purchBillOutstandingRequestBean.setSuppBillFromDate("01/01/2000");
			purchBillOutstandingRequestBean.setSuppBillToDate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		}
		whereCondition =  " and (trunc(d.autd_suppbilldt) between   to_date('".concat(purchBillOutstandingRequestBean.getSuppBillFromDate()).concat("' ,  'dd.MM.yyyy')  and to_date('".concat(purchBillOutstandingRequestBean.getSuppBillToDate()).concat("' , 'dd.MM.yyyy'))\r\n"));
		whereConditionForAgeingReport  = " and (trunc(max_date) between to_date('".concat(purchBillOutstandingRequestBean.getSuppBillFromDate()).concat("',  'dd/MM/yyyy') and to_date('".concat(purchBillOutstandingRequestBean.getSuppBillToDate()).concat("', 'dd/MM/yyyy'))\r\n"));
		Query addIntoTempTableQuery = this.entityManager.createNativeQuery("INSERT INTO\r\n"
				+ "Temp_PBil_OutStd_Rep\r\n"
				+ "SELECT \r\n"
				+ "SuppBillNo,\r\n"
				+ "SuppBillDt,\r\n"
				+ "MatGroup,\r\n"
				+ "Coy,\r\n"
				+ "PartyCode,\r\n"
				+ "AuthAmount,\r\n"
				+ "AUTD_RETAINAMT,\r\n"
				+ " (SELECT par_partyname\r\n"
				+ "  FROM   party\r\n"
				+ "  WHERE  par_partycode = PartyCode\r\n"
				+ "			AND par_partytype = 'S'\r\n"
				+ "			AND par_closedate >= To_date('01.01.2050', 'dd.mm.yyyy')) AS Partyname,\r\n"
				+ " (SELECT COY_name\r\n"
				+ "  FROM   COMPANY\r\n"
				+ "  WHERE  COY_CODE = coy\r\n"
				+ "			AND coy_closedate = '01-JAN-2050')                                      AS coyname,\r\n"
				+ " (SELECT mat_matname\r\n"
				+ "  FROM   material\r\n"
				+ "  WHERE  mat_matgroup = MatGroup\r\n"
				+ "			AND mat_level = '1')                                      AS matname,\r\n"
				+ " (SELECT bldg_name\r\n"
				+ "  FROM   building\r\n"
				+ "  WHERE  bldg_code = BldgCode)                              AS bldgname,\r\n"
				+ "OutStandingAmt,\r\n"
				+ "BldgCode,\r\n"
				+ ":sessionId,\r\n"
				+ ":userId,\r\n"
				+ ":today,\r\n"
				+ "NULL,\r\n"
				+ "AuthNum,\r\n"
				+ "AuthDate\r\n"
				+ "FROM   (SELECT SuppBillNo,\r\n"
				+ "               SuppBillDt,\r\n"
				+ "               MatGroup,\r\n"
				+ "               Coy,\r\n"
				+ "               PartyCode,\r\n"
				+ "               AUTD_RETAINAMT,\r\n"
				+ "               BldgCode,\r\n"
				+ "               AuthNum,\r\n"
				+ "               AuthDate,\r\n"
				+ "               OS_amt,\r\n"
				+ "               RelRetAmount,\r\n"
				+ "               RetAdjAmt,\r\n"
				+ "               AdvAdjust,\r\n"
				+ "					TDSAmt,\r\n"
				+ "               AuthStatus,\r\n"
				+ "               AuthAmount,\r\n"
				+ "               CASE\r\n"
				+ "                 WHEN AuthStatus = '5' THEN AuthAmount          - NVL(RelRetAmount,0) - NVL(RetAdjAmt,0) - NVL(AdvAdjust,0) - NVL(TDSAmt,0)\r\n"
				+ "                 WHEN AuthStatus = '7' THEN AuthAmount - NVL(OS_amt,0) - NVL(RelRetAmount,0) - NVL(RetAdjAmt,0)\r\n"
				+ "                 ELSE 0\r\n"
				+ "               END AS OutStandingAmt\r\n"
				+ "        FROM   (SELECT D.AUTD_SUPPBILLNO                                                 AS SuppBillNo,\r\n"
				+ "                       D.AUTD_SUPPBILLDT                                                 AS SuppBillDt,\r\n"
				+ "                       A.AUTH_MATGROUP                                                   AS MatGroup,\r\n"
				+ "                       A.AUTH_COY                                                        AS Coy,\r\n"
				+ "                       A.AUTH_PARTYCODE                                                  AS PartyCode,\r\n"
				+ "                       D.AUTD_AUTHAMOUNT                                                 AS AuthAmount,\r\n"
				+ "                       A.AUTH_AUTHSTATUS                                                 AS AuthStatus,\r\n"
				+ "                       ( D.AUTD_RETAINAMT - d.autd_retentionadj )                        AS AUTD_RETAINAMT,\r\n"
				+ "                       A.AUTH_BLDGCODE                                                   AS BldgCode,\r\n"
				+ "                       A.AUTH_AUTHNUM                                                    AS AuthNum,\r\n"
				+ "                       A.AUTH_AUTHDATE                                                   AS AuthDate,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_AuthAmt - VA_RetainAmt) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "								and		VA_AuthType = 'N'\r\n"
				+ "							  ) as OS_amt,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_RetainAmt) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "								and		VA_AuthType = 'L'\r\n"
				+ "							  ) as RetainedAmt,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_RetainAdj) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "								and		VA_AuthType <> 'N'\r\n"
				+ "							  ) as RetAdjAmt,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_RelRetAmt) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "							  ) as RelRetAmount,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_AdvAdjAmt) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "								and		VA_AuthType = 'N'\r\n"
				+ "							  ) as AdvAdjust,\r\n"
				+ "							  (\r\n"
				+ "								select 	sum(VA_TDSAmt) \r\n"
				+ "								from 		v_auth_amounts \r\n"
				+ "								where 	VA_PartyCode = a.auth_partycode \r\n"
				+ "								and 		VA_SuppBillNo = d.autd_suppbillno \r\n"
				+ "								and 		VA_SuppBillDt = d.autd_suppbilldt\r\n"
				+ "							  ) as TDSAmt\r\n"
				+ "                FROM   AUTH_H A,\r\n"
				+ "                       AUTH_D D\r\n"
				+ "                WHERE  ( D.AUTD_AUTHNUM = A.AUTH_AUTHNUM )\r\n"
				+ "                       AND AUTH_AUTHTYPE <> 'L'\r\n"
				+ "                       AND AUTH_AUTHTYPE <> 'R'\r\n"
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getCoyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR A.AUTH_COY in (".concat(String.join(",", purchBillOutstandingRequestBean.getCoyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getBldgCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR a.auth_bldgcode in (".concat(String.join(",", purchBillOutstandingRequestBean.getBldgCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getMatGroup().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR a.auth_matgroup in (".concat(String.join(",", purchBillOutstandingRequestBean.getMatGroup().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getPartyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR a.auth_partycode in (".concat(String.join(",", purchBillOutstandingRequestBean.getPartyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				.concat(whereCondition)
				+ " ))\r\n"
				+ "WHERE  OutStandingAmt > 0\r\n"
				+ "ORDER  BY Coy,\r\n"
				+ "          MatGroup,\r\n"
				+ "          PartyCode,\r\n"
				+ "          SuppBillNo,\r\n"
				+ "          SuppBillDt");
		addIntoTempTableQuery.setParameter("sessionId", sessionId);
		addIntoTempTableQuery.setParameter("userId", GenericAuditContextHolder.getContext().getUserid());
		addIntoTempTableQuery.setParameter("today", LocalDateTime.now());

		LOGGER.info("QUERY :: {}", addIntoTempTableQuery);
		Integer rowCount = addIntoTempTableQuery.executeUpdate();

		if(rowCount == 0) {
			this.entityRepository.updateIncrementCounter("#SESS", "#SESS", Double.valueOf(sessionId));
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(sessionId).message("Added successfully").build());
	}

	@Override
	public ResponseEntity<?> addIntoAgeingTempTable(PurchBillOutstandingRequestBean purchBillOutstandingRequestBean) {
		String whereConditionForAgeingReport = " ";
		String endingParanthesis = CommonConstraints.INSTANCE.CLOSE_ROUND_BRACKET_STRING;
		if(Objects.nonNull(purchBillOutstandingRequestBean.getSuppBillFromDate()) && Objects.nonNull(purchBillOutstandingRequestBean.getSuppBillToDate())){
			whereConditionForAgeingReport  = " and (trunc(max_date) between to_date('".concat(purchBillOutstandingRequestBean.getSuppBillFromDate()).concat("',  'dd.MM.yyyy') and to_date('".concat(purchBillOutstandingRequestBean.getSuppBillToDate()).concat("', 'dd.MM.yyyy')))\r\n"));
			endingParanthesis = "";
		}

		String asOnDate;
		if(StringUtils.isBlank(purchBillOutstandingRequestBean.getSuppBillFromDate()))
			purchBillOutstandingRequestBean.setSuppBillFromDate("01/01/2000");
		//		if(StringUtils.isBlank(purchBillOutstandingRequestBean.getSuppBillToDate()))
		//			purchBillOutstandingRequestBean.setSuppBillToDate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		if(StringUtils.isBlank(purchBillOutstandingRequestBean.getSuppBillToDate())) {
			purchBillOutstandingRequestBean.setSuppBillToDate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
			asOnDate = LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
		}
		else
			asOnDate = purchBillOutstandingRequestBean.getSuppBillToDate();
		Query addIntoTempDrscrsageingTableQuery = this.entityManager.createNativeQuery("SELECT 'PURC' AS trantype,(select bldg_project from building where bldg_code = auth_bldgcode and (bldg_closedate is null or bldg_closedate = '01-JAN-2050')) as project,auth_partycode AS PARTY,party_name AS PARTYNAME,       'ADV' AS BILLNO,max_date AS BILLDATE,auth_amt AS BILLAMT,0 AS DRNOTEAMT,(adv_amt + other_amt) AS PAIDAMT,(final_amt * -1) AS OSAMT        FROM \r\n"
				+ "(SELECT auth_bldgcode,bldg_name,auth_matgroup,matname,auth_partycode,party_name,auth_coy,coyname,bldg_region,auth_amt,adv_amt,other_amt,final_amt,max_date FROM v_mat_os_advance_posted       where \r\n"
				+ "								('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getCoyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR AUTH_COY in (".concat(String.join(",", purchBillOutstandingRequestBean.getCoyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getBldgCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR auth_bldgcode in (".concat(String.join(",", purchBillOutstandingRequestBean.getBldgCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getMatGroup().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR auth_matgroup in (".concat(String.join(",", purchBillOutstandingRequestBean.getMatGroup().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))\r\n"))
				+ "								and ('ALL' IN (".concat(String.join(",", purchBillOutstandingRequestBean.getPartyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(") OR auth_partycode in (".concat(String.join(",", purchBillOutstandingRequestBean.getPartyCode().stream().map(name -> ("'" + name + "'")).collect(Collectors.toList()))).concat(" ))"+endingParanthesis+"\r\n"))
				.concat(whereConditionForAgeingReport)
				+ "UNION ALL SELECT 'PURC' AS trantype,(select bldg_project from building where trim(bldg_code) = trim(auth_bldgcode) and (bldg_closedate is null or bldg_closedate = '01-JAN-2050')) as project,auth_partycode AS PARTY,partyname AS PARTYNAME,AUTD_SUPPBILLNO AS BILLNO,AUTD_SUPPBILLDT AS BILLDATE,AUTD_AUTHAMOUNT AS BILLAMT, 0 AS DRNOTEAMT, (AUTD_AUTHAMOUNT - OS_AMT) AS PAIDAMT, OS_AMT AS OSAMT FROM ( SELECT AUTD_SUPPBILLNO,  AUTD_SUPPBILLDT, AUTH_MATGROUP,  AUTH_COY,  AUTH_PARTYCODE,  AUTD_AUTHAMOUNT, AUTD_RETAINAMT,  partyname, coyname,  matname, bldgname,  OS_AMT,  AUTH_BLDGCODE, remarks, AUTH_AUTHNUM, AUTH_AUTHDATE  FROM Temp_PBil_OutStd_Rep WHERE OS_AMT > 0 and TRIM(sessid) = :sessionId) ORDER BY  PARTY, PROJECT, BILLNO", Tuple.class);
		addIntoTempDrscrsageingTableQuery.setParameter("sessionId", purchBillOutstandingRequestBean.getSessionId());
		List<Tuple> resultSetList = addIntoTempDrscrsageingTableQuery.getResultList();	

		List<TempDrscrsageingBean> tempDrscrsageingBeanList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(resultSetList)) {
			resultSetList.stream().map(t -> {
				return tempDrscrsageingBeanList.add(TempDrscrsageingBean.builder()
						.trantype(t.get(0, String.class)) 
						.project(t.get(1, String.class)) 
						.party(t.get(2, String.class)) 
						.partyName(t.get(3, String.class))
						.billNo(t.get(4, String.class)) 
						.billDate(t.get(5, Timestamp.class).toLocalDateTime().toLocalDate())
						.billamt(Objects.nonNull(t.get(6, BigDecimal.class)) ? t.get(6, BigDecimal.class).doubleValue() : null)
						.drnoteamt(Objects.nonNull(t.get(7, BigDecimal.class)) ? t.get(7, BigDecimal.class).doubleValue() : null)
						.paidamt(Objects.nonNull(t.get(8, BigDecimal.class)) ? t.get(8, BigDecimal.class).doubleValue() : null)
						.osamt(Objects.nonNull(t.get(9, BigDecimal.class)) ? t.get(9, BigDecimal.class).doubleValue() : null)
						.build());

			}).collect(Collectors.toList());				

			LOGGER.info("resultSetList :: {}" , tempDrscrsageingBeanList);
			ValueContainer<Double> srCounter = new ValueContainer<>(BigInteger.ONE.doubleValue());
			List<Temp_DrscrsageingRequestBean> temp_DrscrsageingRequestBeanList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(tempDrscrsageingBeanList)) {
				tempDrscrsageingBeanList.stream().map(tempDrscrsageingBean -> {
					Long diffInDays = ChronoUnit.DAYS.between(tempDrscrsageingBean.getBillDate(), LocalDate.parse(asOnDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) + BigInteger.ONE.intValue();
					Double dayAbove180 = BigInteger.ZERO.doubleValue();
					Double days0To365 = BigInteger.ZERO.doubleValue();
					Double days366To1095 = BigInteger.ZERO.doubleValue();
					Double daysAbove1095 = BigInteger.ZERO.doubleValue();
					if(diffInDays < 181)
						dayAbove180 = tempDrscrsageingBean.getOsamt();
					else if(diffInDays < 366)
						days0To365 = tempDrscrsageingBean.getOsamt();
					else if(diffInDays < 1096)
						days366To1095 = tempDrscrsageingBean.getOsamt();
					else
						daysAbove1095 = tempDrscrsageingBean.getOsamt();

					temp_DrscrsageingRequestBeanList.add(Temp_DrscrsageingRequestBean.builder()
							.trantype(tempDrscrsageingBean.getTrantype())
							.partycode(tempDrscrsageingBean.getParty())
							.billno(tempDrscrsageingBean.getBillNo())
							.billdate(tempDrscrsageingBean.getBillDate())
							.srno(srCounter.getValue())
							.advance(BigInteger.ONE.doubleValue())
							.retention(BigInteger.ONE.doubleValue())
							.days0to365(days0To365)
							.days366to1095(days366To1095)
							.above180(dayAbove180)
							.above1095(daysAbove1095)
							.sesid(Double.valueOf(purchBillOutstandingRequestBean.getSessionId()))
							.module("DRCRAGEING")
							.build());
					srCounter.setValue(srCounter.getValue()+1);
					return tempDrscrsageingBean;
				}).collect(Collectors.toList());

				try {
					String computerName;
					InetAddress inetAddress;
					inetAddress = InetAddress.getByName(GenericAuditContextHolder.getContext().getIpAddress());
					computerName = inetAddress.getHostName();
					if (computerName.equalsIgnoreCase("localhost")) {
						computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
					}
					computerName = StringUtils.isNotBlank(computerName) && computerName.trim().length() > 10 ? InetAddress.getLocalHost().getHostName() : computerName.trim();
					this.temp_DrscrsageingRepository.saveAll(Temp_DrscrsageingMapper.addTemp_DrscrsageingEntityPojoMapper.apply(new Object[] {temp_DrscrsageingRequestBeanList, computerName}));			
				}
				catch (UnknownHostException e) {
					LOGGER.info("Exception :: {}", e.getMessage());
				}
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}

	@Override
	public ResponseEntity<?> checkIsBillTypeValid(String partyCode, String billType) {
		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode, CommonUtils.INSTANCE.closeDateInLocalDateTime(), "S");
		if(Objects.nonNull(partyEntity)) {
			if(Objects.isNull(partyEntity.getParSupptype())) 
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());

			if((partyEntity.getParSupptype().trim().equals("T") || partyEntity.getParSupptype().trim().equals("M") || partyEntity.getParSupptype().trim().equals("W"))
					&& !billType.equals(partyEntity.getParSupptype().trim())) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Incorrect bill type for this Supplier.").build());
			}
			else if((partyEntity.getParSupptype().equals("B")) 
					&& (!billType.equals("T") && !billType.equals("M") && !billType.equals("C"))) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Incorrect bill type for this Supplier.").build());
			}
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> checkIsDebitTypeValid(String buildingCode, String debitType) {
		Query debitTypeCheckQuery = this.entityManager.createNativeQuery("SELECT INSTR(bmap_blockcerttype,'".concat(Objects.nonNull(debitType) && StringUtils.isNotBlank(debitType) ? debitType.trim() : CommonConstraints.INSTANCE.SPACE_STRING).concat("') from bldgmap where bmap_ebldgcode = '").concat(buildingCode.trim()).concat("'"));
		List<Object> resultList = debitTypeCheckQuery.getResultList();
		if(CollectionUtils.isNotEmpty(resultList) && 
				(resultList.get(0)!=null && Integer.parseInt(resultList.get(0).toString()) > 0)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This Expense type is blocked ...Can't proceed.").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	//Calculation of tds amount and rate on screen on focus change
	@Override
	public ResponseEntity<?> calculateTds(String suppbilldt, Double amount, String billtype, String partycode, String coy) {
		Double tdsPercentage = BigInteger.ZERO.doubleValue();
		Integer tdsAmount = BigInteger.ZERO.intValue();
		if(billtype.equals(BillTypeEnum.TRANSPORT.getValue()) || billtype.equals(BillTypeEnum.LABOUR.getValue())) {
			if(isCoyLtd(suppbilldt, partycode).equals("Y")) {
				if(StringUtils.isNotBlank(suppbilldt)) {
					Integer tdsrate = this.entityRepository.findEntNum1ByEntityCk_EntClassAndEntityCk_EntChar1BetweenEntityDates("TDSPA", PartyType.S.toString(), LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					tdsPercentage = tdsrate != null ? Double.valueOf(tdsrate) : BigInteger.ZERO.doubleValue();
				}else {
					Integer tdsrate = this.entityRepository.findEntNum2ByEntityCk_EntClassAndEntityCk_EntChar1BetweenEntityDates("TDSPA", PartyType.S.toString(), LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					tdsPercentage = tdsrate != null ? Double.valueOf(tdsrate) : BigInteger.ZERO.doubleValue();
				}
			}
			if(isCoyLtd(suppbilldt, partycode).equals("Y")) {
				String tdsrate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDSPA", "00000", LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).split(CommonConstraints.INSTANCE.COMMA_STRING)[0];
				tdsPercentage = StringUtils.isNotBlank(tdsrate) ? Double.valueOf(tdsrate) : BigInteger.ZERO.doubleValue();
			}else {
				String tdsrate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDSPA", "00000", LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).split(CommonConstraints.INSTANCE.COMMA_STRING)[1];
				tdsPercentage = StringUtils.isNotBlank(tdsrate) ? Double.valueOf(tdsrate) : BigInteger.ZERO.doubleValue();
			}

			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partycode, CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
			if(billtype.equals(BillTypeEnum.TRANSPORT.getValue()) && StringUtils.isNotBlank(partyEntity.getParPmtacnum())) {
				if(LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.parse("01/04/2014", CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) < 0)
					tdsPercentage = BigInteger.ZERO.doubleValue();
				else 
					if(LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.parse("14/05/2020", CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) < 0 || 
							LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.parse("31/03/2021", CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) > 0)
						tdsPercentage = 2D;
					else
						tdsPercentage = 1.5;
			}
			if(billtype.equals(BillTypeEnum.LABOUR.getValue()) && StringUtils.isNotBlank(partyEntity.getParPmtacnum())) {
				if(LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.parse("14/05/2020", CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) < 0 || 
						LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.parse("31/03/2021", CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)) > 0)
					tdsPercentage = 2D;
				else
					tdsPercentage = 1.5;
			}
			if(StringUtils.isBlank(suppbilldt))
				suppbilldt = "01/01/1980";
			Integer tdsPerc = this.entityRepository.findEntNum1ByEntityCk_EntClassAndEntityCk_EntChar1BetweenEntityDates("TDSPA", partycode, LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
//            'start of aadhar pan link tds rule vicky 27/10/2023 old system
//            If TxtBILLTYPE.SelectedIndex = 0 Or TxtBILLTYPE.SelectedIndex = 3 Then
//                Dim strPanAadharLinkedYN As String
//                strPanAadharLinkedYN = Cls_Common_Functions.ClsGetDescription.FuncGetDescription( _
//                    "SELECT par_aadharpanlinkedyn FROM party WHERE par_partycode = '" & TxtPBLH_PARTYCODE.Text.Trim & "' AND par_partytype = 'S' " & _
//                    "AND sysdate BETWEEN par_opendate AND par_closedate")
//                If strPanAadharLinkedYN = "N" Then
//                    DecLocTDSPerc = Cls_Common_Functions.ClsGetNumericValue.FuncGetNumericValue( _
//                    "Select ent_num1 from entity where ent_class = 'TDSPA' AND	ent_id = 'AAPAN' AND to_date('" & strlocsuppbilldt & "','dd-mm-yyyy') between ent_date1 and ent_date2")
//                End If
//            End If
//          'end of aadhar pan link tds rule vicky 27/10/2023 old system
//          'start of aadhar pan link tds rule vicky 27/10/2023 new system
			if(billtype.equals(BillTypeEnum.TRANSPORT.getValue()) || billtype.equals(BillTypeEnum.LABOUR.getValue())) {
				Party partyEntity1 = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partycode, CommonUtils.INSTANCE.closeDateInLocalDateTime(), PartyType.S.toString());
				if(partyEntity1.getParAadharPanLinkedYN().trim().equals("N")) {
					String tdsrate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDSPA", "AAPAN", LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).split(CommonConstraints.INSTANCE.COMMA_STRING)[0];
					tdsPercentage = StringUtils.isNotBlank(tdsrate) ? Double.valueOf(tdsrate) : BigInteger.ZERO.doubleValue();
				}
			}
//          'end of aadhar pan link tds rule vicky 27/10/2023 new system
			
			if(tdsPerc != null)
				tdsPercentage = tdsPerc.doubleValue();
			tdsAmount = (int) Math.round((amount * tdsPercentage) / 100);

			Map<String, Object> tdsValuesMap = new HashMap<>();
			tdsValuesMap.put("tdsPercentage", tdsPercentage);
			tdsValuesMap.put("tdsAmount", tdsAmount);
			return ResponseEntity.ok(ServiceResponseBean.builder().data(tdsValuesMap).status(Boolean.TRUE).build());
		}
		else {
			Map<String, Object> tdsValuesMap = new HashMap<>();
			tdsValuesMap.put("tdsPercentage", tdsPercentage);
			tdsValuesMap.put("tdsAmount", tdsAmount);
			return ResponseEntity.ok(ServiceResponseBean.builder().data(tdsValuesMap).status(Boolean.TRUE).build());
		}
	}

	private String isCoyLtd(String suppbilldt, String partycode) {
		if(StringUtils.isBlank(suppbilldt))
			suppbilldt = LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);

		Query ltdQuery = this.entityManager.createNativeQuery("SELECT FUNC_GetCompanyType(:partytype, :partycode, :suppbilldt) FROM dual");

		ltdQuery.setParameter("partytype", PartyType.S.toString());
		ltdQuery.setParameter("partycode", partycode);
		ltdQuery.setParameter("suppbilldt", LocalDate.parse(suppbilldt, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));

		Object singleResult = ltdQuery.getSingleResult();
		LOGGER.info("SingleResult :: ", singleResult.toString());

		if(singleResult.toString().equals("Individual") || singleResult.toString().equals("HUF"))
			return "N";
		else 
			return "Y";
	}

	@Override
	public ResponseEntity<?> fetchUomByMatGroupAndMatLevel(String matGroup, String matLevel, String suppBillDate, String billType, String deuom) {
		MatGroupResponseBean.MatGroupResponseBeanBuilder matGroupResponseBean = MatGroupResponseBean.builder();
		if(isValidMatGrp(matGroup, matLevel, suppBillDate) == 0)
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid Mat group.").build());
		else {
			if(isMatLevel2Allowed(matGroup, matLevel) == 0)
				matGroupResponseBean.level2(Boolean.FALSE);
			else
				matGroupResponseBean.level2(Boolean.TRUE);

			Material materialEntity = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(matGroup.trim(), "1");
			//Added by Yash P. qty/item should be disabled on these bill types 25/05/2023
			if(Objects.nonNull(materialEntity)) {
				if(StringUtils.isBlank(materialEntity.getMatSku()))
					matGroupResponseBean.setBillDetailValues(Boolean.FALSE);
				else if(billType.equals(BillTypeEnum.TRANSPORT.getValue()) || billType.equals(BillTypeEnum.OTH_CHARGES.getValue()) || billType.equals(BillTypeEnum.LABOUR.getValue()))
					if(matGroup.equals("BRBA") && GenericAuditContextHolder.getContext().getSite().equals("PUNE"))
						matGroupResponseBean.setBillDetailValues(Boolean.TRUE);
					else
						matGroupResponseBean.setBillDetailValues(Boolean.FALSE);
				else 
					matGroupResponseBean.setBillDetailValues(Boolean.TRUE);
				//			if(StringUtils.isBlank(deuom))
				//				matGroupResponseBean.setBillDetailValues(Boolean.FALSE);
			}
		}
		//Added by Yash P. qty/item should be disabled on these bill types 25/05/2023
		if(billType.equals(BillTypeEnum.TRANSPORT.getValue()) || billType.equals(BillTypeEnum.OTH_CHARGES.getValue()) || billType.equals(BillTypeEnum.LABOUR.getValue()))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					matGroupResponseBean.uom(null)
					.qty(BigInteger.ZERO.intValue())
					.rate(BigInteger.ZERO.intValue()).build()
					).build());
		Material material = this.materialRepository.findByMaterialCK_MatMatgroupAndMatLevel(matGroup, matLevel);
		if(Objects.nonNull(material) && Objects.nonNull(material.getMatSku())) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
					matGroupResponseBean.uom(material.getMatSku().trim())
					.qty(BigInteger.ZERO.intValue())
					.rate(BigInteger.ZERO.intValue()).build()
					).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(
				matGroupResponseBean
				.uom(material.getMatSku())
				.qty(BigInteger.ZERO.intValue())
				.rate(BigInteger.ZERO.intValue()).build()
				).build());
	}

	private Integer isValidMatGrp(String matGroup, String matLevel, String suppBillDate) {
		return this.materialRepository.findByMaterialGrpCount(matGroup, matLevel, "****", "********", LocalDate.parse(suppBillDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), CommonUtils.INSTANCE.closeDateInLocalDateTime().toLocalDate());
	}

	private Integer isMatLevel2Allowed(String matGroup, String matLevel) {
		return this.materialRepository.materialLevel2Count(matGroup, matLevel, "****", "********", "LEVL2");
	}

	@Override
	public ResponseEntity<?> validateSuppBillDate(String date) {
		if(LocalDate.parse(date, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).compareTo(LocalDate.of(LocalDate.now().getYear() - 1, 4, 1)) < 0)
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Date cannot be less than 01/04/"+(LocalDate.now().getYear()-1)).build());
		else 
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> validateChallanDetail(String dcno, String partycode, String serNo, String suppbilldt) {
		Dc dcEntity = StringUtils.isNotBlank(serNo) ? 
				this.dcRepository.findByDcCK_DcpEntrynoAndDcCK_DcpDcnoAndDcCK_DcpSuppcodeNot(serNo, dcno, partycode) : 
					this.dcRepository.findByDcCK_DcpDcnoAndDcCK_DcpSuppcode(dcno, partycode);
		if(Objects.nonNull(dcEntity)) {
			if(CommonUtils.INSTANCE.dateToFinancialYear(dcEntity.getDcpBilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).equals(CommonUtils.INSTANCE.dateToFinancialYear(suppbilldt)))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Challan already received in CURRENT YEAR... Bill No. :"+dcEntity.getDcpSuppbill() + " Bill date : "+dcEntity.getDcpBilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).build());
			else
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Info : Challan already entered in EARLIER YEAR... Bill No. : "+dcEntity.getDcpSuppbill() + "Bill date : "+dcEntity.getDcpBilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).build());
		}return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}

	@Override
	public ResponseEntity<?> updatePurchBillAdjustement(String ser) {
		Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(ser.trim());
		LOGGER.info("Pbillh :: {}", pbillhEntity);
		if(Objects.nonNull(pbillhEntity)) {
			// Edited by Yash P 20/04/2023 - As per directed by Vikram Sir 
			// If debit note is exists for this bill then subtract the debit amount from the retention and OS.
			pbillhEntity.setPblhRetainos(pbillhEntity.getPblhAmount() - pbillhEntity.getPblhDebitamt());
			pbillhEntity.setPblhRetention(pbillhEntity.getPblhAmount() - pbillhEntity.getPblhDebitamt());
			pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid());
			pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite());
			pbillhEntity.setPblhToday(LocalDateTime.now());

			this.pbillhRepository.save(pbillhEntity);

			GenericAuditContextHolder.getContext().setTransactionNo(pbillhEntity.getPbillhCK().getPblhSer());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}

	@Override
	public ResponseEntity<?> uomConversionAndRate(String matgroup, String deUom, Double amount, Double qty) {
		if(matgroup.matches("SAND|ME12|ME34|STPW|BRBA|RUBB")) {
			Uomconv uomconvEntity = this.uomconvRepository.findByUomconvCK_UcnvFrcodeAndUomconvCK_UcnvTocode(deUom, "BRAS");
			LOGGER.info("Uomconv :: {}", uomconvEntity);

			if(Objects.nonNull(uomconvEntity)) {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(Double.valueOf(String.format(".2f",(amount / qty) / uomconvEntity.getUcnvFactor()))).build());
			}else {
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Check UOM entered, Rate Conversion failure.").build());
			}
		}else if(matgroup.equals("BRIC")) {
			if(deUom.equals("NOS."))
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(Double.valueOf(String.format(".2f",(amount / qty) * 1000))).build());
		}else
			if(amount > 0)
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(Double.valueOf(String.format("%.2f", (amount / qty)))).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Something went wrong..").build());
	}

	@Override
	public ResponseEntity<?> validateUomDetail(String deUom) {
		List<Uom> uomEntityList = this.uomRepository.findByUomCK_UomCode(deUom);
		if(CollectionUtils.isEmpty(uomEntityList))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Invalid U o M entered.").build()); 
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build()); 
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

	private List<ActrandBean> insActrand(Integer bunumCounter, PbillhRequestBean pbillhRequestBean, AccountingBean accoutingDataForTran, AccountingBean accoutingDataForContraTran,  AccountingBean accoutingDataCashFlow,  AccountingBean accoutingDataCashFlowForContra
			, Double tranamt, String bldgcode, String property, String deuom, String trandate) {
		List<ActrandBean> actrandBeanList = new ArrayList<>();
		String narrationMsg = CommonConstraints.INSTANCE.BLANK_STRING;	
		if(Objects.nonNull(pbillhRequestBean.getDebsocyn()) && pbillhRequestBean.getDebsocyn().equals("G")){
			String debitTypeGNarration = this.entityRepository.findEntRemarkByEntityCk_EntClassAndEntityCk_EntId("DEBIT", pbillhRequestBean.getDebsocyn());
			if(StringUtils.isNotBlank(debitTypeGNarration))
				narrationMsg = debitTypeGNarration;
		}
		else if(StringUtils.isNotBlank(pbillhRequestBean.getNarration()))
			narrationMsg = pbillhRequestBean.getNarration();
		else {
			if(pbillhRequestBean.getPbilldRequestBean().getMatgroupname().length() > 40)
				narrationMsg = pbillhRequestBean.getPbilldRequestBean().getMatgroupname().substring(0, 40);
			else
				narrationMsg = pbillhRequestBean.getPbilldRequestBean().getMatgroupname();
		}
		if(StringUtils.isNotBlank(deuom))
			if(deuom.length() > 4)
				deuom = deuom.substring(0,4);

		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder()
						.transer(pbillhRequestBean.getSer())
						.bunum(bunumCounter)
						.trantype("BA")
						.trandate(trandate)
						.ledgcode("PL")
						.proprietor(pbillhRequestBean.getProp())
						.coy(pbillhRequestBean.getCoy().trim())
						.mintype(accoutingDataForTran.getMinType())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(accoutingDataForTran.getAcMajor())
						.acminor(accoutingDataForTran.getAcminor())
						.mincode(accoutingDataForTran.getMinCode())
						.vounum(pbillhRequestBean.getSuppbillno())
						.voudate(pbillhRequestBean.getSuppbilldt())
						.tranamt(tranamt * -1)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(narrationMsg)
						.project(accoutingDataForTran.getProject())
						.bldgcode(bldgcode)
						.property(property)
						.domain("M")
						.matgroup(pbillhRequestBean.getPbilldRequestBean().getMatgroup())
						.matcode(pbillhRequestBean.getPbilldRequestBean().getMatcode())
						.sku(deuom)
						.itemqty(pbillhRequestBean.getPbilldRequestBean().getDequantity())
						.cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode())
						.docnum(pbillhRequestBean.getSuppbillno())
						.docdate(pbillhRequestBean.getSuppbilldt())
						.doctype(CommonConstraints.INSTANCE.BLANK_STRING)
						.docpartype(PartyType.S.toString())
						.docparcode(pbillhRequestBean.getPartycode())
						//X columns (Contra entry)
						.xproject(accoutingDataForContraTran.getProject())
						.xacmajor(accoutingDataForContraTran.getAcMajor())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(accoutingDataForContraTran.getPartyCode())
						.xpartytype(accoutingDataForContraTran.getPartyType())
						.xreftranser(pbillhRequestBean.getSer())
						.xrefbunum(bunumCounter+1)
						.xreftrandate(trandate)
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.today(LocalDateTime.now())
						.build()
						);
			}else {
				actrandBeanList.add(ActrandBean.builder()
						.transer(pbillhRequestBean.getSer())
						.bunum(bunumCounter + 1)
						.trantype("BA")
						.trandate(trandate)
						.ledgcode("PL")
						.proprietor(pbillhRequestBean.getProp())
						.coy(pbillhRequestBean.getCoy().trim())
						.mintype(accoutingDataForContraTran.getMinType())
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(accoutingDataForContraTran.getAcminor())
						.acmajor(accoutingDataForContraTran.getAcMajor())
						.vounum(pbillhRequestBean.getSuppbillno())
						.voudate(pbillhRequestBean.getSuppbilldt())
						.tranamt(tranamt)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(narrationMsg)
						.project(accoutingDataForContraTran.getProject())
						.bldgcode(bldgcode)
						.property(property)
						.domain("M")
						.matgroup(pbillhRequestBean.getPbilldRequestBean().getMatgroup())
						.matcode(pbillhRequestBean.getPbilldRequestBean().getMatcode())
						.sku(deuom)
						.itemqty(pbillhRequestBean.getPbilldRequestBean().getDequantity())
						.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
						.cfcode(accoutingDataCashFlowForContra.getCfCode())
						.docnum(pbillhRequestBean.getSuppbillno())
						.docdate(pbillhRequestBean.getSuppbilldt())
						.doctype(CommonConstraints.INSTANCE.BLANK_STRING)
						.docpartype(PartyType.S.toString())
						.docparcode(pbillhRequestBean.getPartycode())
						//X columns (Contra entry)
						.xreftranser(pbillhRequestBean.getSer())
						.xacminor(accoutingDataForTran.getAcminor())
						.xacmajor(accoutingDataForTran.getAcMajor())
						.xmintype(accoutingDataForTran.getMinType())
						.xpartycode(accoutingDataForTran.getPartyCode())
						.xproject(accoutingDataForTran.getProject())
						.xreftrandate(trandate)
						.xpartytype(accoutingDataForTran.getPartyType())
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

	private String getGstTranDate(String suppbilldt, String ser, Boolean isUpdate) {
		if(isUpdate) {
			Query ltdQuery = this.entityManager.createNativeQuery("SELECT to_char(acth_trandate,'DD/MM/YYYY') FROM actranh WHERE acth_transer = :ser");
			ltdQuery.setParameter("ser", ser);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}else {
			Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:suppbilldt,'DD/MM/YYYY'))), 'DD/MM/YYYY') from dual");

			ltdQuery.setParameter("suppbilldt", suppbilldt);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}
	}

	private String getGstTranDateYYYYMM(String suppbilldt, String ser, Boolean isUpdate) {
		if(isUpdate) {
			Query ltdQuery = this.entityManager.createNativeQuery("SELECT to_char(TO_DATE(acth_trandate,'DD/MM/YYYY'),'YYYYMM') FROM actranh WHERE acth_transer = :ser");
			ltdQuery.setParameter("ser", ser);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}else {
			Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:suppbilldt,'DD/MM/YYYY'))),'YYYYMM') from dual");

			ltdQuery.setParameter("suppbilldt", suppbilldt);

			Object singleResult = ltdQuery.getSingleResult();
			LOGGER.info("SingleResult :: ", singleResult.toString());

			return singleResult.toString();
		}
	}

	@Override
	public ResponseEntity<?> findPblhAmountBySuppNumberAndPartyCodeAndBuildingCodeAndSuppBilldtAndAuthNum(
			String supplierNumber, String partyCode, String buildingCode, String pblhSuppbilldt, String authNum) {
		Double billAmount = this.pbillhRepository.findByPblhSuppbillnoAndPartyCodeAndBldgcodeAndSuppbilldtAndAuthnum(supplierNumber, partyCode, buildingCode, pblhSuppbilldt, authNum);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data((billAmount!=null) ? billAmount : BigInteger.ZERO).build());
	}

	@Override
	public ResponseEntity<?> fetchPbillSerialNoByBuildingAndPartyAndSuppBilNo(String blgCode, String partyCode, String supplierNumber) {
		String ser = this.pbillhRepository.findSerByPblhBldgcodeAndPblhPartycodeAndPblhSuppbillno(blgCode, partyCode, supplierNumber);
		LOGGER.info("Bill Ser: {} ", ser );
		if(Objects.nonNull(ser) && !ser.isEmpty()) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(ser).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Bill Ser Not Found").build()); 
	}

	@Override
	public ResponseEntity<?> fetchPurchaseBillBySerForReversal(String ser) {
		String validationMessage = "";
		String reverseBillType = "";
		Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(ser.trim());
		LOGGER.info("Pbillh :: {}", pbillhEntity);

		if(Objects.nonNull(pbillhEntity)) {
			Pbilld pbilldEntity = this.pbilldRepository.findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(ser.trim(), BigInteger.ONE.intValue());
			LOGGER.info("Pbilld :: {}", pbilldEntity);

			List<Pbillvat> pbillvatEntityList = this.pbillvatRepository.findByPbillvatCK_PblvSer(ser.trim());
			LOGGER.info("Pbillvat :: {}", pbillvatEntityList);
			if(StringUtils.isNotBlank(pbillhEntity.getPblhAuthnum()) || Objects.nonNull(pbillhEntity.getPblhAuthnum())) {
				try {
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(objectMapper.readValue(new JSONObject().put("isBillAuthorized", true).toString(), Object.class)).message("Can't reverse this bill, first cancel the authorisation # " + pbillhEntity.getPblhAuthnum().trim()).build());
				} catch (JsonProcessingException | JSONException e) {
					e.printStackTrace();
				}						
			}
			else {
				Actranh acteanhEntity = this.actranhRepository.findByActranhCK_ActhTranserAndReverseYN(ser.trim(), "Y");
				if(Objects.nonNull(acteanhEntity)) {
					if(acteanhEntity.getActhReverseyn().trim().equals("Y")) {
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Supplier Bill# " + pbillhEntity.getPblhSuppbillno().trim() + " already reversed.").build());
					}
					else {
						Integer debitNoteCount = this.dbnotehRepository.dbnotehCountByCoyAndBuildingCodeAndPartyAndSuppBillNo(pbillhEntity.getPblhCoy().trim(), pbillhEntity.getPblhBldgcode().trim(), pbillhEntity.getPblhPartycode().trim(), pbillhEntity.getPblhSuppbillno().trim());
						if(debitNoteCount > 1) {
							validationMessage = validationMessage + "This bill has got more than one Debit Note.";
						}
						reverseBillType = (debitNoteCount > 0)? "BOTH" : "ONLYBILL";
					}
				}
			}	
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(PbillhMapper.fetchPbillhEntityPojoMapper.apply(new Object [] {pbillhEntity, pbilldEntity, pbillvatEntityList, null, null, null})).extraData(reverseBillType).message(validationMessage).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No data found.").build());
	}


	@Override
	public ResponseEntity<?> reversePurchaseBill(String ser, String reverseBillType) {
		Pbillh pbillhEntity = this.pbillhRepository.findByPbillhCK_PblhSer(ser.trim());
		if(Objects.nonNull(pbillhEntity)){
			if(reverseBillType.equals("BOTH")) {
				String debitNoteranserNo = this.dbnotehRepository.dbnotehSerByCoyAndBuildingAndSupplierAndSuppBillNo(pbillhEntity.getPblhCoy().trim(), pbillhEntity.getPblhBldgcode().trim(), pbillhEntity.getPblhPartycode().trim(), pbillhEntity.getPblhSuppbillno().trim());				
				LOGGER.info("Debit Note Transer: {} ", debitNoteranserNo);
				String reversalDebitNoteTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "DNSER", GenericAuditContextHolder.getContext().getSite().trim());

				LOGGER.info("Reversal Debit Note Transer: {} ", reversalDebitNoteTranserNo);
				insertIntoAccountingTableForBillReversal(debitNoteranserNo.trim(), reversalDebitNoteTranserNo, pbillhEntity.getPblhSuppbillno().trim().trim(), true, pbillhEntity.getPblhPartycode().trim());
				this.actranhRepository.updateActranhReverseYNWithUserAndSiteAndToday("Y", debitNoteranserNo.trim(), GenericAuditContextHolder.getContext().getUserid().trim(), GenericAuditContextHolder.getContext().getSite().trim(), LocalDateTime.now());
				this.dbnotehRepository.deleteDbnotehBySer(debitNoteranserNo.trim());
				this.dbnotedRepository.deleteDbnotedBySer(debitNoteranserNo.trim());
				this.dbnotevatRepository.deleteDbnotevatBySer(debitNoteranserNo.trim());							
			}
			String reversalBillTranserNo = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#NSER", "BPSER", GenericAuditContextHolder.getContext().getSite().trim());
			LOGGER.info("Reversal Bill Transer: {} ", reversalBillTranserNo);

			insertIntoAccountingTableForBillReversal(ser.trim(), reversalBillTranserNo, pbillhEntity.getPblhSuppbillno().trim(), false, pbillhEntity.getPblhPartycode().trim());
			this.actranhRepository.updateActranhReverseYNWithUserAndSiteAndToday("Y", ser.trim(), GenericAuditContextHolder.getContext().getUserid().trim(), GenericAuditContextHolder.getContext().getSite().trim(), LocalDateTime.now());
			this.pbillhRepository.delete(pbillhEntity);
			this.pbilldRepository.deletePbilldBySer(ser.trim());
			this.pbillvatRepository.deletePbillvatBySer(ser.trim());
			this.dcRepository.deleteDcBySuppCodeAndSuppBill(pbillhEntity.getPblhPartycode().trim(), pbillhEntity.getPblhSuppbillno().trim());
			GenericAuditContextHolder.getContext().setTransactionNo(reversalBillTranserNo);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Bill No: " +ser.trim()+ " Reversed successfully. New Transer No: "+reversalBillTranserNo).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).build());
	}

	public void insertIntoAccountingTableForBillReversal(String transerNo, String newTranserNo, String suppBillNo, boolean isDebitNote, String partyCode) {
		Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndReverseYN(transerNo, "'Y'");
		LOGGER.info("Actranh Entity: {}" ,actranh);
		List<Actrand> actrandList = this.actrandRepository.findActdByTranserNo(transerNo.trim());
		LOGGER.info("Actrand List Size: {} " , actrandList.size());
		if(Objects.nonNull(actranh) && !actrandList.isEmpty()) {
			ActranhBean actranhBean = ActranhBean.builder()
					.balancedyn(Objects.nonNull(actranh.getActhBalancedyn()) ? actranh.getActhBalancedyn() : null) 
					.bankcode(Objects.nonNull(actranh.getActhBankcode()) ? actranh.getActhBankcode() : null)
					.bbookyn(Objects.nonNull(actranh.getActhBbookyn()) ? actranh.getActhBbookyn() : null)
					.cbookyn(Objects.nonNull(actranh.getActhCbookyn()) ? actranh.getActhCbookyn() : null)
					.clearacyn(Objects.nonNull(actranh.getActhClearacyn()) ? actranh.getActhClearacyn() : null)				
					.closingjvyn(Objects.nonNull(actranh.getActhClosingjvyn()) ? actranh.getActhClosingjvyn() : null)
					.coy(actranh.getActranhCK().getActhCoy())
					.crepno(Objects.nonNull(actranh.getActhCrepno()) ? actranh.getActhCrepno() : 0)
					.ledgcode(Objects.nonNull(actranh.getActhLedgcode()) ? actranh.getActhLedgcode() : null)
					.narrative(("Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'").length() > 60 ? ("Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'").substring(0, 60): "Reversal of Transer# '" +transerNo+ "' for suppbillno '" +actranh.getActhVounum().trim()+"'" )
					.partytype(Objects.nonNull(actranh.getActhPartytype()) ? actranh.getActhPartytype() : null)
					.partycode(Objects.nonNull(actranh.getActhPartycode()) ? actranh.getActhPartycode() : null)
					.postedyn(Objects.nonNull(actranh.getActhPostedyn()) ? actranh.getActhPostedyn() : null)
					.proprietor(actranh.getActhProprietor())
					.provyn(Objects.nonNull(actranh.getActhProvyn()) ? actranh.getActhProvyn() : null)
					.reverseyn("Y")				
					.site(GenericAuditContextHolder.getContext().getSite().trim())
					.userid(GenericAuditContextHolder.getContext().getUserid().trim())
					.voudate(Objects.nonNull(actranh.getActhVoudate()) ? actranh.getActhVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.vounum(Objects.nonNull(actranh.getActhVounum()) ? actranh.getActhVounum() : null)
					.tranamt(Objects.nonNull(actranh.getActhTranamt()) ? actranh.getActhTranamt() * -1 : null)
					.transer(newTranserNo)
					.trantype(actranh.getActhTrantype())
					.trandate(actranh.getActhTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
					.build();	

			this.actranhRepository.saveAndFlush(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {actranhBean}));
			List<ActrandBean> actrandBeanList = new ArrayList<>();

			for(int i =0 ; i < actrandList.size() ; i++) {
				actrandBeanList.add(ActrandBean.builder()
						.acmajor(actrandList.get(i).getActdAcmajor())
						.acminor(Objects.nonNull(actrandList.get(i).getActdAcminor()) ?  actrandList.get(i).getActdAcminor() : " " )
						.bldgcode(Objects.nonNull(actrandList.get(i).getActdBldgcode()) ? actrandList.get(i).getActdBldgcode() : null)
						.bunum(Objects.nonNull(actrandList.get(i).getActrandCK().getActdBunum()) ? actrandList.get(i).getActrandCK().getActdBunum() : null)
						.cfcode(Objects.nonNull(actrandList.get(i).getActdCfcode()) ?  actrandList.get(i).getActdCfcode() : null)
						.cfgroup(Objects.nonNull(actrandList.get(i).getActdCfgroup()) ? actrandList.get(i).getActdCfgroup() : null)
						.contrabunum(Objects.nonNull(actrandList.get(0).getActdContrabunum()) ? actrandList.get(0).getActdContrabunum() : null)
						.contract(Objects.nonNull(actrandList.get(i).getActdContract()) ?  actrandList.get(i).getActdContract() : null )
						.coy(actrandList.get(0).getActrandCK().getActdCoy())
						.docdate(Objects.nonNull(actrandList.get(i).getActdDocdate()) ? actrandList.get(i).getActdDocdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
						.docnum(Objects.nonNull(actrandList.get(i).getActdDocnum()) ? actrandList.get(i).getActdDocnum()  : null )
						.docparcode(Objects.nonNull(actrandList.get(i).getActdDocparcode()) && !isDebitNote ? actrandList.get(i).getActdDocparcode() : partyCode )
						.docpartype(Objects.nonNull(actrandList.get(i).getActdDocpartype()) && !isDebitNote ? actrandList.get(i).getActdDocpartype() : "S")
						.doctype(Objects.nonNull(actrandList.get(i).getActdDoctype()) ? actrandList.get(i).getActdDoctype() : null )
						.domain(Objects.nonNull(actrandList.get(i).getActdDomain()) ? actrandList.get(i).getActdDomain() : null )
						.flat(Objects.nonNull(actrandList.get(i).getActdFlat()) ? actrandList.get(i).getActdFlat() : null)
						.itemqty(Objects.nonNull(actrandList.get(i).getActdItemqty()) ? actrandList.get(i).getActdItemqty() : null)
						.matgroup(Objects.nonNull(actrandList.get(i).getActdMatgroup()) ? actrandList.get(i).getActdMatgroup()  : null )	
						.matcode(Objects.nonNull(actrandList.get(i).getActdMatcode()) ? actrandList.get(i).getActdMatcode() : null )
						.mincode(Objects.nonNull(actrandList.get(i).getActdMincode()) ? actrandList.get(i).getActdMincode() : null )
						.mintype((Objects.nonNull(actrandList.get(i).getActdMintype())) ? actrandList.get(i).getActdMintype() :  null)
						.oldbunum(Objects.nonNull(actrandList.get(i).getActdOldbunum()) ? actrandList.get(i).getActdOldbunum() : null)
						.ledgcode(actrandList.get(0).getActdLedgcode())
						.narrative(Objects.nonNull(actrandList.get(i).getActdNarrative()) ? actrandList.get(i).getActdNarrative() : null )
						.partycode(Objects.nonNull(actrandList.get(i).getActdPartycode()) ? actrandList.get(i).getActdPartycode() :  null)
						.partytype(Objects.nonNull(actrandList.get(i).getActdPartytype()) ? actrandList.get(i).getActdPartytype() : null )
						.paymode(Objects.nonNull(actrandList.get(i).getActdPaymode()) ? actrandList.get(i).getActdPaymode() : null )
						.period(Objects.nonNull(actrandList.get(i).getActdPeriod()) ? actrandList.get(i).getActdPeriod() : null)
						.project((Objects.isNull(actrandList.get(i).getActdProject()) ||  StringUtils.isBlank(actrandList.get(i).getActdProject().trim())) ? "GL" : actrandList.get(i).getActdProject()) 
						.property(Objects.nonNull(actrandList.get(i).getActdProperty()) ? actrandList.get(i).getActdProperty() : null)
						.proprietor(actrandList.get(0).getActdProprietor())
						.reffrom(Objects.nonNull(actrandList.get(i).getActdReffrom()) ? actrandList.get(i).getActdReffrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
						.refupto(Objects.nonNull(actrandList.get(i).getActdRefupto()) ? actrandList.get(i).getActdRefupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null  )
						.sku(Objects.nonNull(actrandList.get(i).getActdSku()) ? actrandList.get(i).getActdSku() : null )
						.tranamt(actrandList.get(i).getActdTranamt() * -1)
						.trandate(actrandList.get(i).getActdTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
						.transer(newTranserNo)
						.trantype(actrandList.get(i).getActdTrantype())
						.vounum(actrandList.get(i).getActdVounum())
						.voudate(actrandList.get(i).getActdVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
						.workcode(Objects.nonNull(actrandList.get(i).getActdWorkcode()) ? actrandList.get(i).getActdWorkcode() : null )
						.workgroup(Objects.nonNull(actrandList.get(i).getActdWorkgroup()) ? actrandList.get(i).getActdWorkgroup() : null)						
						.wing(Objects.nonNull(actrandList.get(i).getActdWing()) ? actrandList.get(i).getActdWing() : null )
						.xacmajor(Objects.nonNull(actrandList.get(i).getActdXacmajor()) ?  actrandList.get(i).getActdXacmajor() : null)
						.xacminor(Objects.nonNull(actrandList.get(i).getActdXacminor()) ? actrandList.get(i).getActdXacminor() : null )
						.xrefbunum(Objects.nonNull(actrandList.get(i).getActdXrefBunum()) && !isDebitNote ?  actrandList.get(i).getActdXrefBunum() : null)
						.xmintype(Objects.nonNull(actrandList.get(i).getActdXmintype()) ? actrandList.get(i).getActdXmintype() : null )
						.xpartycode(Objects.nonNull(actrandList.get(i).getActdXpartycode())  && !isDebitNote ? actrandList.get(i).getActdXpartycode()  : null )
						.xpartytype(Objects.nonNull(actrandList.get(i).getActdXpartytype())  && !isDebitNote ?  actrandList.get(i).getActdXpartytype() : null)
						.xproject(Objects.nonNull(actrandList.get(i).getActdXproject()) ? actrandList.get(i).getActdXproject()  : null )
						.xreftrandate(Objects.nonNull(actrandList.get(i).getActdXrefTrandate()) && !isDebitNote ? actrandList.get(i).getActdXrefTrandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
						.xreftranser(Objects.nonNull(actrandList.get(i).getActdXrefTranser()) && !isDebitNote ? actrandList.get(i).getActdXrefTranser() : null )
						.site(GenericAuditContextHolder.getContext().getSite().trim())
						.userid(GenericAuditContextHolder.getContext().getUserid().trim())
						.build());
			}	
			this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandBeanList));	
		}

	}

	@Override
	public ResponseEntity<?> deleteTempTableFromSessionId(Integer sessionId, Boolean isAgeing) {
		if(isAgeing) {
			Query deleteBySessIdTempDrscrsageingTableQuery = this.entityManager.createNativeQuery("delete from TEMP_DRSCRSAGEING where TEMP_SESID = :sessionId");
			deleteBySessIdTempDrscrsageingTableQuery.setParameter("sessionId", sessionId);
			deleteBySessIdTempDrscrsageingTableQuery.executeUpdate();
		}
		Query deleteBySessIdTempTableQuery = this.entityManager.createNativeQuery("delete from Temp_PBil_OutStd_Rep where SESSID = :sessionId");
		deleteBySessIdTempTableQuery.setParameter("sessionId", sessionId);
		deleteBySessIdTempTableQuery.executeUpdate();

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
	}
}
