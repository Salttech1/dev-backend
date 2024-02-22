package kraheja.adminexp.vehicleexp.dataentry.service.serviceimpl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexpdRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexphRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.PreviousAdmexphDetailsBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.UnPostedCertBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.UnPosterCertDetailBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.VehCertCancellationBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.VehicleInfoBean;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexpdCK;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexph;
import kraheja.adminexp.vehicleexp.dataentry.mappers.AdmexpdEntityPojoMapper;
import kraheja.adminexp.vehicleexp.dataentry.mappers.AdmexphEntityPojoMapper;
import kraheja.adminexp.vehicleexp.dataentry.repository.AdmexpdRepository;
import kraheja.adminexp.vehicleexp.dataentry.repository.AdmexphRepository;
import kraheja.adminexp.vehicleexp.dataentry.repository.EquipRepository;
import kraheja.adminexp.vehicleexp.dataentry.service.AdmexphService;
import kraheja.commons.bean.AccountingBean;
import kraheja.commons.bean.ActrandBean;
import kraheja.commons.bean.ActranhBean;
import kraheja.commons.bean.DetnarrBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.TranTypeEnum;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddPojoEntityMapper;
import kraheja.commons.mappers.pojoentity.DetnarrMapper;
import kraheja.commons.mappers.pojoentity.UpdatePojoEntityMapper;
import kraheja.commons.repository.ActrandRepository;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.DetnarrRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.ExnarrRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.service.PartyService;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericAccountingLogic;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;


@Service
@Transactional
public class AdmexphServiceImpl implements AdmexphService {

	private static final Logger logger = LoggerFactory.getLogger(AdmexphServiceImpl.class);

	@PersistenceContext
	private  EntityManager entityManager;

	@Autowired
	private AdmexphRepository admexphRepository;

	@Autowired
	private  ActrandRepository actrandRepository;	

	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private AdmexpdRepository admexpdRepository;

	@Autowired
	private EquipRepository equipRepository;

	@Autowired
	private PartyService partyService;

	@Autowired
	private  ActranhRepository actranhRepository;

	@Autowired
	private  PartyRepository partyRepository;

	@Autowired
	private ExnarrRepository exnarrRepository;
	
	@Autowired
	private DetnarrRepository detnarrRepository;

	@Override
	public ResponseEntity<?> fetchAdmexphByCertnumAndEquipid(String  certnum, String  equipid) {
		Admexph admexphEntity = this.admexphRepository.findByAdmexphCK_AdmhCertnumAndAdmhEquipid(certnum, equipid);
		logger.info("AdmexphEntity :: {}", admexphEntity);

		if(Objects.nonNull(admexphEntity)) {
			List<Admexpd> admexpdEntityList = this.admexpdRepository.findByAdmexpdCK_AdmdCertnum(certnum);
			logger.info("AdmexpdEntity :: {}", admexpdEntityList);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(AdmexphEntityPojoMapper.fetchAdmexphEntityPojoMapper.apply(new Object [] {admexphEntity, admexpdEntityList})).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No record found for your selections in Admexph").build());
	}

	@Override
	public ResponseEntity<?> addAdmexph(AdmexphRequestBean admexphRequestBean)  {
		Party partyNameGst = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(admexphRequestBean.getPartycode(), CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.miscellaneousParty);
		logger.info("PartyNameGst :: {}", partyNameGst);
		String partyGst = "";
		partyGst = partyNameGst.getParGstno();
		//		Values are set for following column 
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		//		String certnum = GenericCounterIncrementLogicUtil.generateTranNoWithSite("#CER", "#ACER", GenericAuditContextHolder.getContext().getSite());
		String certnum = "";
		String billnum = "";
		String trandate = getGstTranDate(admexphRequestBean.getPartybilldate());

		//on update
		if(admexphRequestBean.getIsUpdate()) {
			certnum = admexphRequestBean.getCertnum();
			billnum = admexphRequestBean.getBillno();

			//update admexph and admexpd
			Admexph admexphEntity = this.admexphRepository.findByAdmexphCK_AdmhCertnumAndAdmhEquipid(admexphRequestBean.getCertnum(), admexphRequestBean.getEquipid());
			logger.info("AdmexphEntity :: {}", admexphEntity);

			List<Admexpd> admexpdEntityList = this.admexpdRepository.findByAdmexpdCK_AdmdCertnum(admexphRequestBean.getCertnum());	

			if(Objects.nonNull(admexphEntity)) {
				
				if(Objects.nonNull(admexphRequestBean)) {
					//List<Admexpd> admexpdEntityUpdatedList = new ArrayList<>();
					this.admexphRepository.save(AdmexphEntityPojoMapper.updateAdmexphEntityPojoMapper.apply( admexphEntity, admexphRequestBean));
					ValueContainer<String> billDate = new ValueContainer<String>(admexphEntity.getAdmhPartybilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					ValueContainer<String> billRefDate = new ValueContainer<String>(admexphEntity.getAdmhPartybillref());

					if(!admexphRequestBean.getPartybilldate().equals(admexphEntity.getAdmhPartybilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)))
						billDate.setValue(admexphRequestBean.getPartybilldate());

					if(!admexphRequestBean.getPartybillref().equals(admexphEntity.getAdmhPartybillref()))
						billRefDate.setValue(admexphRequestBean.getPartybillref());
					this.admexpdRepository.deleteAll(admexpdEntityList);
					
					//this.admexpdRepository.deleteAdmexpdByCertNum(admexphRequestBean.getCertnum());
					
//					admexphRequestBean.getAdmexpdRequestBean().stream().map(admexpdRequestBean -> 
//					admexpdEntityList.stream().filter(admexpdEntity -> admexpdEntity.getAdmexpdCK().getAdmdCertnum().equals(admexpdRequestBean.getCertnum()) && 
//							admexpdEntity.getAdmexpdCK().getAdmdBunum().equals(admexpdRequestBean.getBunum()))
//					.map(admexpdEntity -> 
//						admexpdEntityUpdatedList.add(this.admexpdRepository.save(AdmexpdEntityPojoMapper.updateAdmexpdEntityPojoMapper.apply(new Object[] {admexpdEntity, admexpdRequestBean, billDate.getValue(), billRefDate.getValue()})))
//					).collect(Collectors.toList())
//					).collect(Collectors.toList());
//					this.admexpdRepository.saveAll(admexpdEntityUpdatedList);
					
					List<Admexpd> admxpdList = AdmexpdEntityPojoMapper.addAdmexpdEntityPojoMapper.apply(new Object[] {admexphRequestBean.getAdmexpdRequestBean(),siteFromDBEntity,certnum});
					Integer counter = 1;
					if(CollectionUtils.isNotEmpty(admxpdList))
						for(Admexpd admexpdEntity : admxpdList) {
							logger.info("admexpdEntity :: {}",admexpdEntity);
							admexpdEntity.setAdmexpdCK(AdmexpdCK.builder()
									.admdCertnum(certnum)
									.admdBunum(counter).build());
							admexpdEntity.setAdmdBillref(admexphRequestBean.getPartybillref());
							admexpdEntity.setAdmdBilldate(LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
							counter++;
							this.admexpdRepository.save(admexpdEntity);
						}
				}
			}
			//update on actranh
			Actranh actranh = this.actranhRepository.findByActranhCK_ActhTranserAndActranhCK_ActhCoy(admexphRequestBean.getBillno().trim(), admexphRequestBean.getCoy().trim());
			logger.info("actranh :: {}", actranh);
			ActranhBean actranhBean = ActranhBean.builder()
					.partycode(admexphRequestBean.getPartycode())
					.tranamt(Double.valueOf(admexphRequestBean.getCertamount()))
					.vounum(admexphRequestBean.getPartybillref())
					.voudate(admexphRequestBean.getPartybilldate())
					.site(GenericAuditContextHolder.getContext().getSite())
					.userid(GenericAuditContextHolder.getContext().getUserid())
					.build(); 

			this.actranhRepository.save(UpdatePojoEntityMapper.updateActranhEntityPojoMapper.apply(actranh, actranhBean));
			this.actrandRepository.deleteActrand(admexphRequestBean.getBillno().trim());
			//delete on actrand as it will be inserted again in method below
			this.detnarrRepository.deleteDetnarr(admexphRequestBean.getBillno().trim());
			
		} else {
			//on add
			certnum = GenericCounterIncrementLogicUtil.generateTranNo("#CER", "#ACER");
			billnum = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#BO");

		admexphRequestBean.setCertnum(certnum);
		admexphRequestBean.setCertdate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		admexphRequestBean.setCertrevnum(BigInteger.ZERO.doubleValue());
		admexphRequestBean.setCerttype(null);
		admexphRequestBean.setPrinted(BigInteger.ZERO.doubleValue());
		admexphRequestBean.setPrintedon(null);
		admexphRequestBean.setPassedon(null);
		admexphRequestBean.setCertstatus("1");
		admexphRequestBean.setSocid("VEHICLE"); 
		admexphRequestBean.setPartytype("Z");
		admexphRequestBean.setBillno(billnum);
		admexphRequestBean.setGstyn((StringUtils.isBlank(partyGst)) ? "N" : "Y");


		//		values are set for previous bills details columns
		PreviousAdmexphDetailsBean previousAdmexphDetailsBean = this.admexphRepository.findByAdmhEquipidAdmhPartycodeAdmexphCK_AdmhCertnum(admexphRequestBean.getEquipid().trim(),admexphRequestBean.getPartycode().trim(),admexphRequestBean.getCertnum().trim());
		if(Objects.nonNull(previousAdmexphDetailsBean)){
			admexphRequestBean.setPrv_certnum(previousAdmexphDetailsBean.getCertnum());
			admexphRequestBean.setPrv_date(previousAdmexphDetailsBean.getCertdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
			admexphRequestBean.setPrv_type(previousAdmexphDetailsBean.getCerttype());
			admexphRequestBean.setPrv_amt(previousAdmexphDetailsBean.getCertamount());
			admexphRequestBean.setT_payment(previousAdmexphDetailsBean.getPayamount());
		}

		this.admexphRepository.save(AdmexphEntityPojoMapper.addAdmexphEntityPojoMapper.apply(new Object[] {admexphRequestBean,siteFromDBEntity,certnum}));

		//		this.admexpdRepository.saveAll(AdmexpdEntityPojoMapper.addAdmexpdEntityPojoMapper.apply(new Object[] {admexphRequestBean.getAdmexpdRequestBean(),siteFromDBEntity,certnum}).stream().map(admexpdRequestBean -> {
		//			Integer counter = 1;
		//			admexpdRequestBean.setAdmexpdCK(AdmexpdCK.builder()
		//					.admdCertnum(certnum)
		//					.admdBunum(counter).build());
		//			admexpdRequestBean.setAdmdBillref(admexphRequestBean.getPartybillref());
		//			admexpdRequestBean.setAdmdBilldate(LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		//					counter++;
		//			return admexpdRequestBean;
		//		}).collect(Collectors.toList()));

		List<Admexpd> admxpdList = AdmexpdEntityPojoMapper.addAdmexpdEntityPojoMapper.apply(new Object[] {admexphRequestBean.getAdmexpdRequestBean(),siteFromDBEntity,certnum});
		Integer counter = 1;
		if(CollectionUtils.isNotEmpty(admxpdList))
			for(Admexpd admexpdEntity : admxpdList) {
				admexpdEntity.setAdmexpdCK(AdmexpdCK.builder()
						.admdCertnum(certnum)
						.admdBunum(counter).build());
				admexpdEntity.setAdmdBillref(admexphRequestBean.getPartybillref());
				admexpdEntity.setAdmdBilldate(LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				counter++;
				this.admexpdRepository.save(admexpdEntity);
			}
		//Insert bill in accounts when adding.
		this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
				.transer(billnum)
				.trantype(TranTypeEnum.BO.toString())
				.trandate(trandate)	
				.ledgcode("PL")
				.partytype(CommonConstraints.INSTANCE.miscellaneousParty)
				.partycode(admexphRequestBean.getPartycode())
				.tranamt(Double.valueOf(admexphRequestBean.getCertamount() ))
				.voudate(trandate)
				.vounum(admexphRequestBean.getPartybillref())
				.postedyn("Y")
				.balancedyn("Y")
				.closingjvyn("N")
				.bbookyn("N")
				.cbookyn("N")
				.narrative(" ")
				.proprietor(admexphRequestBean.getProp())
				.coy(admexphRequestBean.getCoy())
				.site(GenericAuditContextHolder.getContext().getSite())
				.userid( GenericAuditContextHolder.getContext().getUserid())
				.clearacyn("Y")
				.reverseyn("N")
				.build()}));
		}


		//			for actrand entry
		List<ActrandBean> actrandList = new ArrayList<>();
		
		// for Detnarr entry
		List<DetnarrBean> detnarrBean = new ArrayList<>();


		//			for getting sum in the grid
		Double sumOfBillAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getBillamount).sum();
		Double sumOfTaxableAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getTds).sum();
		Double sumOfCgstAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getCgst).sum();
		Double sumOfSgstAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getSgst).sum();
		Double sumOfIgstAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getIgst).sum();
		Double sumOfUgstAmt = admexphRequestBean.getAdmexpdRequestBean().parallelStream().mapToDouble(AdmexpdRequestBean::getUgst).sum();
		String reffrom = admexphRequestBean.getAdmexpdRequestBean().get(0).getDurationfrom();
		String refto = admexphRequestBean.getAdmexpdRequestBean().get(0).getDurationupto();
		
		Integer bunumCounter = BigInteger.ZERO.intValue();
		//BREAKUP 1
		if(!sumOfBillAmt.equals(BigInteger.ZERO.doubleValue())) {
			String mintype = " ";
			String mincode = "";
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401026", mintype, mincode, CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", admexphRequestBean.getPartycode());
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails("40620003", mintype, mincode, "", "", "GL", "");
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401026", "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow("40620003", "BO");

			bunumCounter += 1;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfBillAmt, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		//CGST BREAKUP
		if(!sumOfCgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402441", "20404391", "20404395", partyGst);
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", admexphRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BO");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfCgstAmt, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		//SGST BREAKUP
		if(!sumOfSgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402443", "20404392", "20404396", partyGst);
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", admexphRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BO");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfSgstAmt, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		//IGST BREAKUP
		if(!sumOfIgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402445", "20404393", "20404397", partyGst);
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", admexphRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BO");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfIgstAmt, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		//UGST BREAKUP
		if(!sumOfUgstAmt.equals(BigInteger.ZERO.doubleValue())) {
			AccountingBean acmajor = getAcMajor("11402447", "20404394", "20404398", partyGst);
			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getAcMajor(), null, "", CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(acmajor.getXAcMajor(), null, "", "", admexphRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow(acmajor.getAcMajor(), "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(acmajor.getXAcMajor(), "BO");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfUgstAmt, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		//TDS BREAKUP
		if(!sumOfTaxableAmt.equals(BigInteger.ZERO.doubleValue())) {
			String tdsacmajor;
			tdsacmajor = "11401828";

			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401026", null, "", CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", "");
			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails(tdsacmajor, null, "", "", admexphRequestBean.getPartycode(), null, null);
			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401026", "BO");
			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow(tdsacmajor, "BO");

			bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, sumOfTaxableAmt * -1, "", "", "", trandate, "BO","PL","O",billnum,detnarrBean,"",reffrom,refto,"",""));
		}
		logger.info("actrandlist  {} ::", actrandList);
		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));	
		
		//end of insert bill
		
//		for detnarr
		this.detnarrRepository.saveAll(DetnarrMapper.addDetnarrPojoEntityMapper.apply(detnarrBean));


		GenericAuditContextHolder.getContext().setTransactionNo(admexphRequestBean.getCertnum());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Please note down certificate number : " + certnum ).build());
	}

	private List<ActrandBean> insActrand(Integer bunumCounter, AdmexphRequestBean admexphRequestBean, AccountingBean accoutingDataForTran, AccountingBean accoutingDataForContraTran,  AccountingBean accoutingDataCashFlow,  AccountingBean accoutingDataCashFlowForContra
			, Double tranamt, String bldgcode, String property, String deuom, String trandate, String trantype, String ledgcode, String domain, String transerno, List<DetnarrBean> detnarrBean, String workcode, String reffrom, String refto, String narration, String paymode) {

		List<ActrandBean> actrandBeanList = new ArrayList<>();

		if(StringUtils.isNotBlank(deuom))
			if(deuom.length() > 4)
				deuom = deuom.substring(0,4);

		for(int i=1; i <= 2; i++){
			if(i % 2 == 1) {
				actrandBeanList.add(ActrandBean.builder()
						.transer(transerno)
						.bunum(bunumCounter)
						.trantype(trantype)
						.trandate(trandate)
						.ledgcode(ledgcode)
						.proprietor(admexphRequestBean.getProp())
						.coy(admexphRequestBean.getCoy().trim())
						.mintype(accoutingDataForTran.getMinType())
						.partytype(accoutingDataForTran.getPartyType())
						.partycode(accoutingDataForTran.getPartyCode())
						.acmajor(accoutingDataForTran.getAcMajor())
						.acminor(accoutingDataForTran.getAcminor())
						.mincode(accoutingDataForTran.getMinCode())
						.vounum(admexphRequestBean.getPartybillref())
						.voudate(admexphRequestBean.getPartybilldate())
						.tranamt(tranamt * -1)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(narration)
						.reffrom(reffrom)
						.refupto(refto)
						.project(accoutingDataForTran.getProject())
						.bldgcode(bldgcode)
						.property(property)
						.domain(domain)
						.matgroup("")
						.matcode("")
						.workcode(workcode)
						.sku(deuom)
						.itemqty(BigInteger.ZERO.doubleValue())
						.cfgroup(accoutingDataCashFlow.getCfGroup())
						.cfcode(accoutingDataCashFlow.getCfCode())
						.docnum(admexphRequestBean.getPartybillref())
						.docdate(admexphRequestBean.getPartybilldate())
						.doctype(CommonConstraints.INSTANCE.BLANK_STRING)
						.docpartype(CommonConstraints.INSTANCE.miscellaneousParty)
						.docparcode(admexphRequestBean.getPartycode())
						.paymode(paymode)
						//X columns (Contra entry)
						.xproject(accoutingDataForContraTran.getProject())
						.xacmajor(accoutingDataForContraTran.getAcMajor())
						.xacminor(accoutingDataForContraTran.getAcminor())
						.xmintype(accoutingDataForContraTran.getMinType())
						.xpartycode(accoutingDataForContraTran.getPartyCode())
						.xpartytype(accoutingDataForContraTran.getPartyType())
						.xreftranser(admexphRequestBean.getBillno())
						.xrefbunum(bunumCounter+1)
						.xreftrandate(trandate)
						.site(GenericAuditContextHolder.getContext().getSite())
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.today(LocalDateTime.now())
						.build()
						);
				
				detnarrBean.add(DetnarrBean.builder()
						.bunum(bunumCounter)
						.coy(admexphRequestBean.getCoy().trim())
						.dettype("V")
						.narrative(" Vehicle No. '" + admexphRequestBean.getEquipid().trim() + "' ")
						.site(GenericAuditContextHolder.getContext().getSite())
						.today(LocalDateTime.now())
						.transer(transerno)
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.build());
					
			}else {
				actrandBeanList.add(ActrandBean.builder()
						.transer(transerno)
						.bunum(bunumCounter + 1)
						.trantype(trantype)
						.trandate(trandate)
						.ledgcode(ledgcode)
						.proprietor(admexphRequestBean.getProp())
						.coy(admexphRequestBean.getCoy().trim())
						.mintype(accoutingDataForContraTran.getMinType())
						.mincode(accoutingDataForContraTran.getMinCode())
						.partytype(accoutingDataForContraTran.getPartyType())
						.partycode(accoutingDataForContraTran.getPartyCode())
						.project(accoutingDataForContraTran.getProject())
						.acminor(accoutingDataForContraTran.getAcminor())
						.acmajor(accoutingDataForContraTran.getAcMajor())
						.vounum(admexphRequestBean.getPartybillref())
						.voudate(admexphRequestBean.getPartybilldate())
						.tranamt(tranamt)
						.period(CommonConstraints.INSTANCE.BLANK_STRING)
						.narrative(narration)
						.reffrom(reffrom)
						.refupto(refto)
						.project(accoutingDataForContraTran.getProject())
						.bldgcode(bldgcode)
						.property(property)
						.domain(domain)
						.matgroup("")
						.matcode("")
						.workcode(workcode)
						.sku(deuom)
						.itemqty(BigInteger.ZERO.doubleValue())
						.cfgroup(accoutingDataCashFlowForContra.getCfGroup())
						.cfcode(accoutingDataCashFlowForContra.getCfCode())
						.docnum(admexphRequestBean.getPartybillref())
						.docdate(admexphRequestBean.getPartybilldate())
						.doctype(CommonConstraints.INSTANCE.BLANK_STRING)
						.docpartype(CommonConstraints.INSTANCE.miscellaneousParty)
						.docparcode(admexphRequestBean.getPartycode())
						.paymode(paymode)
						//X columns (Contra entry)
						.xreftranser(admexphRequestBean.getBillno())
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
				detnarrBean.add(DetnarrBean.builder()
						.bunum(bunumCounter + 1)
						.coy(admexphRequestBean.getCoy().trim())
						.dettype("V")
						.narrative(" Vehicle No. '" + admexphRequestBean.getEquipid().trim() + "' ")
						.site(GenericAuditContextHolder.getContext().getSite())
						.today(LocalDateTime.now())
						.transer(transerno)
						.userid(GenericAuditContextHolder.getContext().getUserid())
						.build());
			}
		}
		return actrandBeanList;
	}

	private AccountingBean getAcMajor(String nonGstAcMajor, String GstXAcMajor, String nonGstXAcMajor, String gstno) {
		return StringUtils.isNotBlank(gstno) ?
				AccountingBean.builder()
				.acMajor("11401026")
				.xAcMajor(GstXAcMajor)
				.build() :
					AccountingBean.builder()
					.acMajor(nonGstAcMajor)
					.xAcMajor(nonGstXAcMajor)
					.build();
	}

	@Override
	public ResponseEntity<?> updateAdmexph(AdmexphRequestBean admexphRequestBean) {
		Admexph admexphEntity = this.admexphRepository.findByAdmexphCK_AdmhCertnumAndAdmhEquipid(admexphRequestBean.getCertnum(), admexphRequestBean.getEquipid());
		logger.info("AdmexphEntity :: {}", admexphEntity);

		List<Admexpd> admexpdEntityList = this.admexpdRepository.findByAdmexpdCK_AdmdCertnum(admexphRequestBean.getCertnum());	

		if(Objects.nonNull(admexphEntity)) {
			
			if(Objects.nonNull(admexphRequestBean)) {
				List<Admexpd> admexpdEntityUpdatedList = new ArrayList<>();
				this.admexphRepository.save(AdmexphEntityPojoMapper.updateAdmexphEntityPojoMapper.apply( admexphEntity, admexphRequestBean));
				ValueContainer<String> billDate = new ValueContainer<String>(admexphEntity.getAdmhPartybilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				ValueContainer<String> billRefDate = new ValueContainer<String>(admexphEntity.getAdmhPartybillref());

				if(!admexphRequestBean.getPartybilldate().equals(admexphEntity.getAdmhPartybilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)))
					billDate.setValue(admexphRequestBean.getPartybilldate());

				if(!admexphRequestBean.getPartybillref().equals(admexphEntity.getAdmhPartybillref()))
					billRefDate.setValue(admexphRequestBean.getPartybillref());
				
				admexphRequestBean.getAdmexpdRequestBean().stream().map(admexpdRequestBean -> 
				admexpdEntityList.stream().filter(admexpdEntity -> admexpdEntity.getAdmexpdCK().getAdmdCertnum().equals(admexpdRequestBean.getCertnum()) && 
						admexpdEntity.getAdmexpdCK().getAdmdBunum().equals(admexpdRequestBean.getBunum()))
				.map(admexpdEntity -> 
					admexpdEntityUpdatedList.add(this.admexpdRepository.save(AdmexpdEntityPojoMapper.updateAdmexpdEntityPojoMapper.apply(new Object[] {admexpdEntity, admexpdRequestBean, billDate.getValue(), billRefDate.getValue()})))
				).collect(Collectors.toList())
				).collect(Collectors.toList());
				this.admexpdRepository.saveAll(admexpdEntityUpdatedList);
				

				GenericAuditContextHolder.getContext().setTransactionNo(admexphRequestBean.getCertnum());
				GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Please Check your selections.").build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> deleteAdmexph(String  certnum, String  coy)  {
		Admexph admexphEntity = this.admexphRepository.findByAdmexphCK_AdmhCertnumAndAdmexphCK_AdmhCoy(certnum , coy ) ; 

		if(Objects.nonNull(admexphEntity)) {

			this.admexphRepository.delete(admexphEntity);
			GenericAuditContextHolder.getContext().setTransactionNo(admexphEntity.getAdmexphCK().getAdmhCertnum());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Deleted Successfully").build());
		} 
		else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
		}
	}

	@Override
	public ResponseEntity<?> getVehicleInfo(String  vehnum){
		VehicleInfoBean VehInfoEntity = this.equipRepository.findByEquipNum(CommonUtils.INSTANCE.closeDate(), vehnum);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(VehicleInfoBean.builder()
				.vehtype(VehInfoEntity.getVehtype())
				.eqpAllottedto(VehInfoEntity.getEqpAllottedto())
				.eqpCoy(VehInfoEntity.getEqpCoy())
				.coyname(VehInfoEntity.getCoyname())
				.gstno(VehInfoEntity.getGstno())
				.eqpProp(VehInfoEntity.getEqpProp())
				.propname(VehInfoEntity.getPropname())
				.startmeter(VehInfoEntity.getStartmeter()).build()).build());

	}

	@Override
	public ResponseEntity<?> getPartyInfo(String partycode,String vehicleno){
		Query query = this.entityManager.createNativeQuery("SELECT	count(*) FROM admexph	WHERE trim(admh_partycode) = :partycode AND admh_socid = 'VEHICLE' AND trim(admh_equipid)  = :vehicleno AND admh_certstatus < 5");
		query.setParameter("partycode", partycode);
		query.setParameter("vehicleno",vehicleno);
		BigDecimal count = (BigDecimal) query.getSingleResult();
		if(count.intValue() > 0) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Unpassed certificates found for [" + partycode + "] [" + vehicleno + " ]...Can not add new certificate.").build());
		} else {
			ResponseEntity<ServiceResponseBean> partyNameGstInfo = this.partyService.getPartyNameGstInfo(partycode, "Z");
			return partyNameGstInfo;
		}

	}

	private String getGstTranDate(String partybilldt) {
		Query ltdQuery = this.entityManager.createNativeQuery("select to_char((FUNC_GetGSTBillTranDate(TO_DATE(:partybilldt,'DD/MM/YYYY'))), 'DD/MM/YYYY') from dual");

		ltdQuery.setParameter("partybilldt", partybilldt);

		Object singleResult = ltdQuery.getSingleResult();

		return singleResult.toString();

	}
	
	private String getnarration(String workcode) {
		Query ltdQuery = this.entityManager.createNativeQuery("SELECT ent_name FROM entity WHERE ent_class = 'VWORK'  AND	ent_id = :workcode ");
		ltdQuery.setParameter("workcode", workcode);

		Object singleResult = ltdQuery.getSingleResult();

		return singleResult.toString();
	}

	@Override
	public ResponseEntity<?> passVehicleCert(AdmexphRequestBean admexphRequestBean) {
		Admexph admexphEntity = this.admexphRepository.findByAdmexphCK_AdmhCertnumAndAdmhEquipid(admexphRequestBean.getCertnum(), admexphRequestBean.getEquipid());
		logger.info("AdmexphEntity :: {}", admexphEntity);

		//		Payment entry in accounts		
		String transer = GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#P");

		//		to update admexph table
		admexphRequestBean.setPrinted(BigInteger.ONE.doubleValue());
		admexphRequestBean.setPrintedon(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		admexphRequestBean.setPassedon(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		admexphRequestBean.setCertstatus("5");
		admexphRequestBean.setTranser(transer);

		this.admexphRepository.save(AdmexphEntityPojoMapper.updateAdmexphEntityPojoMapper.apply(admexphEntity, admexphRequestBean));

		//		end of update admexph table
		// for Detnarr entry
		List<DetnarrBean> detnarrBean = new ArrayList<>();
				
		this.actranhRepository.save(AddPojoEntityMapper.addActranhPojoEntityMapping.apply(new Object[] {ActranhBean.builder()
				.transer(transer)
				.trantype(TranTypeEnum.PF.toString())
				.trandate(LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))	
				.ledgcode("GL")
				.partytype(CommonConstraints.INSTANCE.miscellaneousParty)
				.partycode(admexphRequestBean.getPartycode())
				.tranamt(Double.valueOf(admexphRequestBean.getPayamount() ))
				.postedyn("N")
				.bbookyn("N")
				.cbookyn("N")
				.narrative(" ")
				.proprietor(admexphRequestBean.getProp())
				.coy(admexphRequestBean.getCoy())
				.site(GenericAuditContextHolder.getContext().getSite())
				.userid( GenericAuditContextHolder.getContext().getUserid())
				.clearacyn("Y")
				.reverseyn("N")
				.provyn("N")
				.build()}));

//		Actrand
		List<ActrandBean> actrandList = new ArrayList<>();
		Integer bunumCounter = BigInteger.ZERO.intValue();
		String workcode,narration,reffrom,refto;
		Double billamount,taxamount;
		Integer counter = 1;

		List<Admexpd> admexpdEntityList = this.admexpdRepository.findByAdmexpdCK_AdmdCertnum(admexphRequestBean.getCertnum());
		if(CollectionUtils.isNotEmpty(admexpdEntityList)) {
			for (Admexpd admexpdlist: admexpdEntityList) {
				workcode = admexpdlist.getAdmdWorkcode();
				reffrom = admexpdlist.getAdmdDurationfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
				refto = admexpdlist.getAdmdDurationupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER);
				narration = "For " + getnarration(workcode) ;
				taxamount = admexpdlist.getAdmdTds();
				
				if (taxamount>0){
					billamount = admexpdlist.getAdmdBillamount() - admexpdlist.getAdmdTds();
				}else {
					billamount = admexpdlist.getAdmdBillamount() + admexpdlist.getAdmdCgst() + admexpdlist.getAdmdSgst() + admexpdlist.getAdmdIgst()+ admexpdlist.getAdmdUgst();
				}
				//following code has be done for billamount = 12.4565652239 issue
				//SELECT actd_acmajor, actd_bunum, sum(actd_tranamt) FROM actrand WHERE actd_transer = 'P000812677' group by actd_acmajor, actd_bunum ;
				//In bove select was given 12.4565652239 issue
				BigDecimal bd = new BigDecimal(billamount).setScale(2, RoundingMode.HALF_UP);  
				billamount = bd.doubleValue();
				
				String mintype = " ";
				String mincode = ""; 
				AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401026", mintype, mincode, CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", admexphRequestBean.getPartycode());
				AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails("80000006", mintype, mincode, "", "", "GL", "");
				AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401026", "P");
				AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow("80000006", "P");

				bunumCounter = bunumCounter.equals(BigInteger.ZERO.intValue()) ? bunumCounter + 1 : bunumCounter + 2;
				actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, billamount * -1 , "", "", "", LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), "PF","GL","",transer,detnarrBean,workcode, reffrom, refto, narration,"Q"));		
	            counter++;
	        }
			
		}		
//		if(!admexphRequestBean.getPayamount().equals(BigInteger.ZERO.doubleValue())) {
//			String mintype = " ";
//			String mincode = ""; 
//			AccountingBean accoutingDataForTran =  GenericAccountingLogic.gatherMinorPartyDetails("11401026", mintype, mincode, CommonConstraints.INSTANCE.miscellaneousParty, admexphRequestBean.getPartycode(), "GL", admexphRequestBean.getPartycode());
//			AccountingBean accoutingDataForContraTran = GenericAccountingLogic.gatherMinorPartyDetails("80000006", mintype, mincode, "", "", "GL", "");
//			AccountingBean accoutingDataCashFlow = GenericAccountingLogic.getCashFlow("11401026", "P");
//			AccountingBean accoutingDataCashFlowForContra = GenericAccountingLogic.getCashFlow("80000006", "P");
//
//			bunumCounter += 1;
//			actrandList.addAll(insActrand(bunumCounter, admexphRequestBean, accoutingDataForTran, accoutingDataForContraTran, accoutingDataCashFlow, accoutingDataCashFlowForContra, admexphRequestBean.getPayamount()* -1 , "", "", "", LocalDate.now().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER), "PF","GL","",transer,detnarrBean));		
//		}
//		end of Actand

		this.actrandRepository.saveAll(AddPojoEntityMapper.addActrandPojoEntityMapping.apply(actrandList));	
		
//		for detnarr
		this.detnarrRepository.saveAll(DetnarrMapper.addDetnarrPojoEntityMapper.apply(detnarrBean));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Trans serial number is  : " + transer ).build());


	}

	@Override
	public ResponseEntity<?> checkCertnumAndCoyAndCerttypeExists(String  certnum, String  coy) {
		return null;
	}

	@Override
	public ResponseEntity<?> findByEquipid(String equipid) {
		List<Tuple> unPosterCerts = this.admexphRepository.findByEquipid(equipid.trim());
		if(unPosterCerts.size()>0) {
			List<UnPostedCertBean> unPosterCertBeanList = 
					unPosterCerts.stream().map(t -> {return new UnPostedCertBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, String.class).trim(),
							t.get(3, String.class).trim(),
							t.get(4, String.class).trim(),
							t.get(5, String.class).trim(),
							t.get(6, String.class).trim());
					}
							).collect(Collectors.toList());
			List<Tuple> unPosterCertsDet = this.admexphRepository.findbyVehDetailByEquipid(equipid.trim());
			List<UnPosterCertDetailBean> unPosterCertDetailList = 
					unPosterCertsDet.stream().map(t -> {return new UnPosterCertDetailBean(
							t.get(0, String.class).trim(),
							t.get(1, String.class).trim(),
							t.get(2, String.class).trim(),
							t.get(3, String.class).trim(),
							t.get(4, String.class).trim(),
							t.get(5, String.class).trim(),
							t.get(6, String.class).trim());
					}
							).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(VehCertCancellationBean.builder().unPostedCertBean(unPosterCertBeanList).unPosterCertDetailBean(unPosterCertDetailList).build()).build());			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No UnPosted Certificate found...").build());
	}

	@Override
	public ResponseEntity<?> cancelUnPostedCertificate(String certnum, String transer) {
		this.actrandRepository.deleteActrand(transer.trim());
		this.actranhRepository.deleteActranh(transer.trim());
		this.exnarrRepository.deleteExnarrByTranser(transer.trim());

		this.admexphRepository.cancelUnPostedCertificate(certnum.trim(), transer.trim(),GenericAuditContextHolder.getContext().getSite(),GenericAuditContextHolder.getContext().getUserid(),LocalDateTime.now());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Certificate "+certnum+" Cancelled Sucessfully").build());			
	}
}