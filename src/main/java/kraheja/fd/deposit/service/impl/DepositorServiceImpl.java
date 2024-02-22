
package kraheja.fd.deposit.service.impl;

import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.entity.Payeebank;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.repository.PayeebankRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.UpdatePartyUtility;
import kraheja.fd.deposit.bean.request.DepositorRequestBean;
import kraheja.fd.deposit.bean.response.DepositResponseBean;
import kraheja.fd.deposit.bean.response.DepositorResponseBean;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.mappers.FdPojoEntityMapper;
import kraheja.fd.deposit.repository.DepintRepository;
import kraheja.fd.deposit.repository.DepositorRepository;
import kraheja.fd.deposit.service.DepositorService;
import kraheja.purch.repository.AuthDRepository;


@Service
@Transactional
public class DepositorServiceImpl implements DepositorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private  DepositorRepository depositorRepository;

	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private  PartyRepository partyRepository;

	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private  PayeebankRepository payeebankRepository;

	@Autowired
	private  DepintRepository depintRepository;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private AuthDRepository authDRepository;
	
	@Autowired
	private UpdatePartyUtility updatePartyUtility;


	@Override
	public ResponseEntity<?> fetchDepositorByCompanycode(String body) {
		//		List<Depositor> depositorEntityList = this.depositorRepository.findByDepositorCK_DeptrCoy(body);
		//		LOGGER.info("DepositorEntityList :: {}", depositorEntityList);
		//
		//		if (depositorEntityList != null && !depositorEntityList.isEmpty()) {
		//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
		//					.data(depositorEntityList.stream().map(depositorEntity -> {
		//						return DepositorResponseBean.builder().name(depositorEntity.getDeptrName())
		//								.depositor(depositorEntity.getDepositorCK().getDeptrDepositor()).build();
		//					}).collect(Collectors.toList())).build());
		//		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<?> fetchDepositorByDepositorIdAndCompanycode(DepositorRequestBean depositorRequestBean) {
		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(
				depositorRequestBean.getDepositorId().trim(), depositorRequestBean.getCompanyCode().trim());
		LOGGER.info("Depositor Entity :: {}" , depositorEntity);

		if (depositorEntity != null) {
			String partyCode = depositorRequestBean.getCompanyCode().trim()
					.concat(depositorRequestBean.getDepositorId().trim());
			LOGGER.info("Party Code :: {}" , partyCode);

			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode,
					CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.DEPOSITORS);
			LOGGER.info("Party Entity :: {}" , partyEntity);

			Address addressEntity = this.addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(
					partyCode, CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype,
					CommonConstraints.INSTANCE.adrAdser);
			LOGGER.info("AddressEntity :: {} " , addressEntity);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(DepositorResponseBean.builder()
					.name(depositorEntity.getDeptrName())
					.depositor(depositorEntity.getDepositorCK().getDeptrDepositor())
					.coy(depositorEntity.getDepositorCK().getDeptrCoy())
					.title(depositorEntity.getDeptrTitle())
					.deptype(depositorEntity.getDeptrDeptype())
					.taxalwaysyn(depositorEntity.getDeptrTaxalwaysyn())
					.birthdate(Objects.nonNull(depositorEntity.getDeptrBirthdate()) ? CommonUtils.INSTANCE.toEpochMilli(depositorEntity.getDeptrBirthdate()) : null )
					.remarks(depositorEntity.getDeptrRemarks())
					.panum1(depositorEntity.getDeptrPanum1())
					.panum2(depositorEntity.getDeptrPanum2())
					.tds15gyn(depositorEntity.getDeptrTds15gyn())
					.tds15hyn(depositorEntity.getDeptrTds15hyn())
					.isTransferSeries((Integer.parseInt(depositorRequestBean.getDepositorId().trim()) >= 9000 
					&& Integer.parseInt(depositorRequestBean.getDepositorId().trim()) <= 9999 ))
					.partyResponseBean(PartyResponseBean.builder()
							.payeebankcode1(partyEntity.getParPayeebankcode1())
							.payeebankname1(partyEntity.getParPayeebankname1())
							.payeebranch1(partyEntity.getParPayeebranch1())
							.payeeifsc1(partyEntity.getParPayeeifsc1())
							.payeeacnum1(partyEntity.getParPayeeacnum1())
							.payeebankcode2(partyEntity.getParPayeebankcode2())
							.payeebankname2(partyEntity.getParPayeebankname2())
							.payeebranch2(partyEntity.getParPayeebranch2())
							.payeeifsc2(partyEntity.getParPayeeifsc2())
							.payeeacnum2(partyEntity.getParPayeeacnum2())
							.aadharno(partyEntity.getParAadharno())
							.build())
					.addressResponseBean(AddressResponseBean.builder()
							.adline1(addressEntity.getAdrAdline1())
							.adline2(addressEntity.getAdrAdline2())
							.adline3(addressEntity.getAdrAdline3())
							.adline4(addressEntity.getAdrAdline4())
							.adline5(addressEntity.getAdrAdline5())
							.pincode(addressEntity.getAdrPincode())
							.fax(addressEntity.getAdrFax())
							.township(addressEntity.getAdrTownship())
							.city(addressEntity.getAdrCity())
							.state(addressEntity.getAdrState())
							.country(addressEntity.getAdrCountry())
							.phonemobile(addressEntity.getAdrPhonemobile())
							.phoneoff(addressEntity.getAdrPhoneoff())
							.phoneres(addressEntity.getAdrPhoneres())
							.email(addressEntity.getAdrEmail()).build())
					.build()).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Depositor found with this depositor Id").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> addDepositor(DepositorRequestBean depositorRequestBean) {

		Integer newDepositorId = Boolean.TRUE.equals(depositorRequestBean.getIsTransferSeries()) ? (this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("FDDTF", depositorRequestBean.getCompanyCode().trim()).intValue() + 1) 
				: (this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntId("FDDNO", depositorRequestBean.getCompanyCode().trim()).intValue() + 1);

		String partyCode = depositorRequestBean.getCompanyCode().trim()
				.concat(newDepositorId.toString());
		LOGGER.info("Party Code :: {}" , partyCode);

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		LOGGER.info("Entity :: {}" , siteFromDBEntity);

		Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode,
				CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.DEPOSITORS);
		LOGGER.info("Party Entity :: {}" , partyEntity);

		Address addressEntity = this.addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(
				partyCode, CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype,
				CommonConstraints.INSTANCE.adrAdser);
		LOGGER.info("AddressEntity :: {}" , addressEntity);

		if(depositorRequestBean.getPartyRequestBean().getPayeeIfsc1() != null && depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().length() > BigInteger.ZERO.intValue()) {
			if(depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().length() == 11) {
				Payeebank payeebank = this.payeebankRepository.findByPybkCode(depositorRequestBean.getPartyRequestBean().getPayeebankcode1());
				if(payeebank == null)
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please enter bank details before adding IFSC details.").build());
				if(!depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().substring(BigInteger.ZERO.intValue(), CommonConstraints.INSTANCE.FOUR).equals(payeebank.getPybkCode()))
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please enter proper IFSC code.").build());
			}else
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("IFSC length should be equal to or greater than 11").build());
		}

		this.depositorRepository.save(FdPojoEntityMapper.addDepositorFdPojoEntityMapping.apply(new Object[] {depositorRequestBean, siteFromDBEntity, newDepositorId.toString()}));

		depositorRequestBean.getAddressRequestBean().setAdsegment(AdSegment.PARTY.toString());
		depositorRequestBean.getAddressRequestBean().setSer(CommonConstraints.INSTANCE.adrAdser);
		depositorRequestBean.getAddressRequestBean().setAdtype(AdType.LOC.toString());
		depositorRequestBean.getAddressRequestBean().setTopser(CommonConstraints.INSTANCE.adrAdser);
		depositorRequestBean.getAddressRequestBean().setBirthday(Objects.nonNull(depositorRequestBean.getBirthdate()) ? depositorRequestBean.getBirthdate() : null);

		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {depositorRequestBean.getAddressRequestBean(), siteFromDBEntity, partyCode}));

		depositorRequestBean.getPartyRequestBean().setValidminor(CommonConstraints.INSTANCE.validMinor);
		depositorRequestBean.getPartyRequestBean().setValidparty(CommonConstraints.INSTANCE.validParty);
		depositorRequestBean.getPartyRequestBean().setPartytype(CommonConstraints.INSTANCE.DEPOSITORS);

		this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {depositorRequestBean.getPartyRequestBean(), siteFromDBEntity, partyCode}));

		if(depositorRequestBean.getIsTransferSeries())
			this.entityRepository.updateIncrementCounter("FDDTF", depositorRequestBean.getCompanyCode().trim(), Double.valueOf(newDepositorId)); 
		else
			this.entityRepository.updateIncrementCounter("FDDNO", depositorRequestBean.getCompanyCode().trim(), Double.valueOf(newDepositorId));
		
		GenericAuditContextHolder.getContext().setTransactionNo(newDepositorId.toString());
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(newDepositorId).message("Despositor with Id " + newDepositorId + " added successfully.").build());
	}

	@Override
	public ResponseEntity<?> updateDepositor(DepositorRequestBean depositorRequestBean) {

		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(
				depositorRequestBean.getDepositorId().trim(), depositorRequestBean.getCompanyCode().trim());
		LOGGER.info("Depositor Entity :: {}" , depositorEntity);

		if(depositorEntity != null) {

			String partyCode = depositorRequestBean.getCompanyCode().trim()
					.concat(depositorRequestBean.getDepositorId().trim());
			LOGGER.info("Party Code :: {}" , partyCode);

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			LOGGER.info("Entity :: {}" , SiteFromDBEntity);

			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode,
					CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.DEPOSITORS);
			LOGGER.info("Party Entity :: {}" , partyEntity);

			Address addressEntity = this.addressRepository.findByAdrAdownerAndAdrAdsegmentAndAdrAdtypeAndAdrAdser(
					partyCode, CommonConstraints.INSTANCE.adrAdsegment, CommonConstraints.INSTANCE.addresstype,
					CommonConstraints.INSTANCE.adrAdser);
			LOGGER.info("AddressEntity :: {}" , addressEntity);

			if(depositorRequestBean.getPartyRequestBean().getPayeeIfsc1() != null && depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().length() > BigInteger.ZERO.intValue()) {
				if(depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().length() == 11) {
					Payeebank payeebank = this.payeebankRepository.findByPybkCode(depositorRequestBean.getPartyRequestBean().getPayeebankcode1());
					if(!depositorRequestBean.getPartyRequestBean().getPayeeIfsc1().substring(BigInteger.ZERO.intValue(), CommonConstraints.INSTANCE.FOUR).equals(payeebank.getPybkCode()))
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please enter proper IFSC code.").build());
				}else
					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("IFSC length should be equal to or greater than 11").build());
			}


			this.depositorRepository.save(FdPojoEntityMapper.updateDepositorPojoEntityMapping.apply(depositorEntity, depositorRequestBean));

			this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressEntity, depositorRequestBean.getAddressRequestBean()));
			depositorRequestBean.getPartyRequestBean().setValidminor(CommonConstraints.INSTANCE.validMinor);
			depositorRequestBean.getPartyRequestBean().setValidparty(CommonConstraints.INSTANCE.validParty);
			depositorRequestBean.getPartyRequestBean().setPartytype(CommonConstraints.INSTANCE.DEPOSITORS);
			
			this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, depositorRequestBean.getPartyRequestBean()));
//			this.updatePartyUtility.updateParty(partyEntity, depositorRequestBean.getPartyRequestBean());

			GenericAuditContextHolder.getContext().setTransactionNo(depositorEntity.getDepositorCK().getDeptrDepositor());
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Updated Successfully").build());
		}	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Depositor not found.").build());
	}

	@Override
	public ResponseEntity<?> checkDepositorIdAndCompanyCodeExists(String depositorId, String companyCode) {
		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositorId.trim(), companyCode.trim());
		LOGGER.info("Depositor Entity :: {}" , depositorEntity);

		return ResponseEntity.ok(Objects.isNull(depositorEntity) ? ServiceResponseBean.builder().status(Boolean.TRUE).build() : ServiceResponseBean.builder().status(Boolean.FALSE).message("Depositor Id already exists.").build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> ageByDepositorIdAndCompanycode(String depositorId, String companyCode) {

		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(
				depositorId.trim(), companyCode.trim());
		LOGGER.info("Depositor Entity :: {}"  , depositorEntity);

		Double rateOfInterest = null;
		if(depositorEntity != null) {

			Double validage = this.entityRepository.findNum2ByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId("FDINT","FD","SEN");
			LOGGER.info("Valid Age {}"  , validage);

			if(Objects.isNull(depositorEntity.getDeptrBirthdate())) {
				rateOfInterest = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId("FDINT","FD","OTH");
				LOGGER.info("Rate of Interest {}"  , rateOfInterest);

				return ResponseEntity.ok(ServiceResponseBean.builder().data(DepositResponseBean.builder().rateOfInterest(rateOfInterest).build()).status(Boolean.TRUE).build());
			}

			int age = Period.between(depositorEntity.getDeptrBirthdate().toLocalDate() , LocalDate.now()).getYears();
			LOGGER.info("Difference In Year {}"  , age);

			if(age >= validage.intValue()) 
				rateOfInterest = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId("FDINT","FD","SEN");
			else 
				rateOfInterest = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId("FDINT","FD","OTH");
			LOGGER.info("Rate of Interest {}"  , rateOfInterest);

			Query query = entityManager.createNativeQuery("select Trunc(Trunc(MONTHS_BETWEEN(to_date(?1,'dd-mm-yyyy') , to_date(?2,'dd-mm-yyyy'))/12))|| ' Yrs ' || (MOD(Trunc(MONTHS_BETWEEN(to_date(?1,'dd-mm-yyyy'),to_date(?2,'dd-mm-yyyy'))),12)) || ' Mths' from dual");
			query.setParameter(1, CommonUtils.INSTANCE.stringDateFormatter(CommonUtils.INSTANCE.currentDate()));
			query.setParameter(2, depositorEntity.getDeptrBirthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));

			List<Object[]> birthdate = query.getResultList();					
			LOGGER.info("Birthdate :: {}", birthdate);

			return ResponseEntity.ok(ServiceResponseBean.builder().data(DepositResponseBean.builder().age(birthdate).rateOfInterest(rateOfInterest).birthDate(depositorEntity.getDeptrBirthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)).build()).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> form15hgByDepositorIdAndCompanycode(String depositorId, String companyCode) {
		Depositor depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositorId.trim(), companyCode.trim());
		LOGGER.info("Depositor Entity :: {}" , depositorEntity);
		if (depositorEntity != null) {
			return ResponseEntity.ok(ServiceResponseBean.builder().data(DepositorResponseBean.builder().tds15gyn(depositorEntity.getDeptrTds15gyn()).tds15hyn(depositorEntity.getDeptrTds15hyn()).build()).status(Boolean.TRUE).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Depositor found with this depositor Id").build());
	}

	@Override
	public String fetchDepositorAddress(String companyCode, String depositorId) {
		String depositorAddress = "";
		Query query = entityManager.createNativeQuery("SELECT RTRIM(a.ADR_ADLINE1) as ADR_ADLINE1,	\r\n"
				+ "RTRIM(a.ADR_ADLINE2) as ADR_ADLINE2,\r\n"
				+ "RTRIM(a.ADR_ADLINE3) as ADR_ADLINE3,\r\n"
				+ "RTRIM(a.ADR_ADLINE4)as ADR_ADLINE4,\r\n"
				+ "RTRIM(a.ADR_ADLINE5) as ADR_ADLINE5,\r\n"
				+ "RTRIM(t.ENT_NAME) as town,\r\n"
				+ "RTRIM(c.ENT_NAME) as city ,\r\n"
				+ "RTRIM(a.ADR_PINCODE) as pincode \r\n"
				+ "FROM  address  a, ENTITY C, ENTITY t \r\n"
				+ "WHERE( adr_adowner 	= " +("'" + companyCode.trim().concat(depositorId.trim()) + "'")+ "  AND adr_adsegment 	= 'PARTY' )\r\n"
				+ "AND		( a.ADR_CITY = c.ENT_ID(+) )AND( a.ADR_TOWNSHIP = t.ENT_ID(+) )AND ( ( c.ENT_CLASS = 'CITY' )AND ( t.ENT_CLASS = 'TOWN' ) )");
		List<Object[]> address = query.getResultList();
		return depositorAddress.concat(Objects.nonNull(address.get(0)[0])  ? address.get(0)[0].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[1]) ? address.get(0)[1].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[2]) ? address.get(0)[2].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[3])  ? address.get(0)[3].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[4]) ? address.get(0)[4].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[5])  ? address.get(0)[5].toString().concat(" ") : "").
				concat(Objects.nonNull(address.get(0)[6])  ? address.get(0)[6]+"-" : "").
				concat(address.get(0)[7].toString());
	}
}
