package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.enggsys.bean.request.ContractRequestBean;
import kraheja.enggsys.bean.response.ContractResponseBean;
import kraheja.enggsys.bean.response.ContractResponseBean.ContractResponseBeanBuilder;
import kraheja.enggsys.entity.Contract;
import kraheja.enggsys.entity.Contract.ContractCK;

public interface ContractMapper {
	public static Function<Object[], ContractResponseBean> fetchContractEntityPojoMapper = objectArray -> {
		Contract contractEntity = (Contract) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		Party partyEntity = (Party) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		Address addressEntity = (Address) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		String city = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE]) ? objectArray[CommonConstraints.INSTANCE.THREE] : null);
		ContractResponseBeanBuilder contractResponseBean = ContractResponseBean.builder();
		contractResponseBean
				.contract(contractEntity.getContractCK().getConttContract())
				.advtax(contractEntity.getConttAdvtax())
				.advtaxca(contractEntity.getConttAdvtaxca())
				.advtaxyr(contractEntity.getConttAdvtaxyr())
				.bldgcode(contractEntity.getConttBldgcode())
				.closedate(Objects.nonNull(contractEntity.getConttClosedate()) ? contractEntity.getConttClosedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.conttor(contractEntity.getConttConttor())
				.coy(contractEntity.getConttCoy())
				.estval(contractEntity.getConttEstval())
				.exmpref(contractEntity.getConttExmpref())
				.krishicessperc(contractEntity.getConttKrishicessperc())
				.matcode(contractEntity.getConttMatcode())
				.matgroup(contractEntity.getConttMatgroup())
				.name(contractEntity.getConttName())
				.nextadv(contractEntity.getConttNextadv())
				.nextfin(contractEntity.getConttNextfin())
				.nextint(contractEntity.getConttNextint())
				.nextmis(contractEntity.getConttNextmis())
				.nextrret(contractEntity.getConttNextrret())
				.nextrun(contractEntity.getConttNextrun())
				.nextset(contractEntity.getConttNextset())
				.origsite(contractEntity.getConttOrigsite())
				.origval(contractEntity.getConttOrigval())
				.paidval(contractEntity.getConttPaidval())
				.partycode(contractEntity.getConttPartycode())
				.partytype(contractEntity.getConttPartytype())
				.prevtds(contractEntity.getConttPrevtds())
				.project(contractEntity.getConttProject())
				.property(contractEntity.getConttProperty())
				.proprietor(contractEntity.getConttProprietor())
				.retainval(contractEntity.getConttRetainval())
				.servtaxperc(contractEntity.getConttServtaxperc())
				.site(contractEntity.getConttSite())
				.sptdsper(contractEntity.getConttSptdsper())
				.swachhcessperc(contractEntity.getConttSwachhcessperc())
				.tdscode(contractEntity.getConttTdscode())
				.tdsfrom(Objects.nonNull(contractEntity.getConttTdsfrom()) ? contractEntity.getConttTdsfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.tdsper(contractEntity.getConttTdsper())
				.tdsptype(contractEntity.getConttTdsptype())
				.tdsupto(Objects.nonNull(contractEntity.getConttTdsupto()) ? contractEntity.getConttTdsupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.today(contractEntity.getConttToday())
				.userid(contractEntity.getConttUserid())
				.vatperc(contractEntity.getConttVatperc())
				.wctaxper(contractEntity.getConttWctaxper())
				.wing(contractEntity.getConttWing())
				.workcode(contractEntity.getConttWorkcode())
				.workgroup(contractEntity.getConttWorkgroup())
				.city(city)
				.build();
		
		if(Objects.nonNull(partyEntity)) 
			contractResponseBean.partyResponseBean(PartyMapper.fetchPartyEntityPojoMapper.apply(new Object [] {partyEntity}));
		
		if(Objects.nonNull(addressEntity)) 
			contractResponseBean.addressResponseBean(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressEntity}));
		
		return contractResponseBean.build();
	};


	public static Function<ContractRequestBean, Contract> addContractPojoEntityMapper = contractRequestBean -> {
		return Contract.builder().contractCK(ContractCK.builder()
				.conttContract(contractRequestBean.getContract())
				.build())
				.conttAdvtax(Objects.nonNull(contractRequestBean.getAdvtax()) ? contractRequestBean.getAdvtax() : BigInteger.ZERO.doubleValue())
				.conttAdvtaxca(Objects.nonNull(contractRequestBean.getAdvtaxca()) ? contractRequestBean.getAdvtaxca() : BigInteger.ZERO.doubleValue())
				.conttAdvtaxyr(contractRequestBean.getAdvtaxyr())
				.conttBldgcode(contractRequestBean.getBldgcode())
				.conttClosedate(Objects.nonNull(contractRequestBean.getClosedate()) ? LocalDate.parse(contractRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.conttConttor(contractRequestBean.getConttor())
				.conttCoy(contractRequestBean.getCoy())
				.conttEstval(Objects.nonNull(contractRequestBean.getEstval()) ? contractRequestBean.getEstval() : BigInteger.ZERO.doubleValue())
				.conttExmpref(contractRequestBean.getExmpref())
				.conttKrishicessperc(Objects.nonNull(contractRequestBean.getKrishicessperc()) ? contractRequestBean.getKrishicessperc() : BigInteger.ZERO.intValue())
				.conttMatcode(contractRequestBean.getMatcode())
				.conttMatgroup(contractRequestBean.getMatgroup())
				.conttName(contractRequestBean.getContractorname())
				.conttNextadv(contractRequestBean.getNextadv())
				.conttNextfin(contractRequestBean.getNextfin())
				.conttNextint(contractRequestBean.getNextint())
				.conttNextmis(contractRequestBean.getNextmis())
				.conttNextrret(contractRequestBean.getNextrret())
				.conttNextrun(contractRequestBean.getNextrun())
				.conttNextset(contractRequestBean.getNextset())
				.conttOrigsite(GenericAuditContextHolder.getContext().getSite())
				.conttOrigval(Objects.nonNull(contractRequestBean.getOrigval()) ? contractRequestBean.getOrigval() : BigInteger.ZERO.doubleValue())
				.conttPaidval(Objects.nonNull(contractRequestBean.getPaidval()) ? contractRequestBean.getPaidval() : BigInteger.ZERO.doubleValue())
				.conttPartycode(contractRequestBean.getPartycode())
				.conttPartytype(contractRequestBean.getPartytype())
				.conttPrevtds(Objects.nonNull(contractRequestBean.getPrevtds()) ? contractRequestBean.getPrevtds() : BigInteger.ZERO.doubleValue())
				.conttProject(contractRequestBean.getProject())
				.conttProperty(contractRequestBean.getProperty())
				.conttProprietor(contractRequestBean.getProprietor())
				.conttRetainval(Objects.nonNull(contractRequestBean.getRetainval()) ? contractRequestBean.getRetainval() : BigInteger.ZERO.doubleValue())
				.conttServtaxperc(Objects.nonNull(contractRequestBean.getServtaxperc()) ? contractRequestBean.getServtaxperc() : BigInteger.ZERO.intValue())
				.conttSite(GenericAuditContextHolder.getContext().getSite())
				.conttSptdsper(contractRequestBean.getSptdsper())
				.conttSwachhcessperc(Objects.nonNull(contractRequestBean.getSwachhcessperc()) ? contractRequestBean.getSwachhcessperc() : BigInteger.ZERO.intValue())
				.conttTdscode(contractRequestBean.getTdscode())
				.conttTdsfrom(Objects.nonNull(contractRequestBean.getTdsfrom()) ? LocalDate.parse(contractRequestBean.getTdsfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.conttTdsper(Objects.nonNull(contractRequestBean.getTdsper()) ? contractRequestBean.getTdsper() : BigInteger.ZERO.intValue())
				.conttTdsptype(contractRequestBean.getTdsptype())
				.conttTdsupto(Objects.nonNull(contractRequestBean.getTdsupto()) ? LocalDate.parse(contractRequestBean.getTdsupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.conttToday(LocalDateTime.now())
				.conttUserid(GenericAuditContextHolder.getContext().getUserid())
				.conttVatperc(Objects.nonNull(contractRequestBean.getVatperc()) ? contractRequestBean.getVatperc() : BigInteger.ZERO.intValue())
				.conttWctaxper(Objects.nonNull(contractRequestBean.getWctaxper()) ? contractRequestBean.getWctaxper() : BigInteger.ZERO.intValue())
				.conttWing(contractRequestBean.getWing())
				.conttWorkcode(contractRequestBean.getWorkcode())
				.conttWorkgroup(contractRequestBean.getWorkgroup())

				.build();
	} ;
	
	public static BiFunction<Contract, ContractRequestBean, Contract> updateContractEntityPojoMapper = (contractEntity, contractRequestBean) -> {
		contractEntity.getContractCK().setConttContract(Objects.nonNull(contractRequestBean.getContract()) ? contractRequestBean.getContract().trim() : contractEntity.getContractCK().getConttContract());
		contractEntity.setConttAdvtax(Objects.nonNull(contractRequestBean.getAdvtax()) ? contractRequestBean.getAdvtax() : contractEntity.getConttAdvtax());
		contractEntity.setConttAdvtaxca(Objects.nonNull(contractRequestBean.getAdvtaxca()) ? contractRequestBean.getAdvtaxca() : contractEntity.getConttAdvtaxca());
		contractEntity.setConttAdvtaxyr(Objects.nonNull(contractRequestBean.getAdvtaxyr()) ? contractRequestBean.getAdvtaxyr().trim() : contractEntity.getConttAdvtaxyr());
		contractEntity.setConttBldgcode(Objects.nonNull(contractRequestBean.getBldgcode()) ? contractRequestBean.getBldgcode().trim() : contractEntity.getConttBldgcode());
		contractEntity.setConttClosedate(Objects.nonNull(contractRequestBean.getClosedate()) ? LocalDate.parse(contractRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : contractEntity.getConttClosedate());
		contractEntity.setConttConttor(Objects.nonNull(contractRequestBean.getConttor()) ? contractRequestBean.getConttor().trim() : contractEntity.getConttConttor());
		contractEntity.setConttCoy(Objects.nonNull(contractRequestBean.getCoy()) ? contractRequestBean.getCoy().trim() : contractEntity.getConttCoy());
		contractEntity.setConttEstval(Objects.nonNull(contractRequestBean.getEstval()) ? contractRequestBean.getEstval() : contractEntity.getConttEstval());
		contractEntity.setConttExmpref(Objects.nonNull(contractRequestBean.getExmpref()) ? contractRequestBean.getExmpref().trim() : contractEntity.getConttExmpref());
		contractEntity.setConttKrishicessperc(Objects.nonNull(contractRequestBean.getKrishicessperc()) ? contractRequestBean.getKrishicessperc() : contractEntity.getConttKrishicessperc());
		contractEntity.setConttMatcode(Objects.nonNull(contractRequestBean.getMatcode()) ? contractRequestBean.getMatcode().trim() : contractEntity.getConttMatcode());
		contractEntity.setConttMatgroup(Objects.nonNull(contractRequestBean.getMatgroup()) ? contractRequestBean.getMatgroup().trim() : contractEntity.getConttMatgroup());
		contractEntity.setConttName(Objects.nonNull(contractRequestBean.getName()) ? contractRequestBean.getName().trim() : contractEntity.getConttName());
		contractEntity.setConttNextadv(Objects.nonNull(contractRequestBean.getNextadv()) ? contractRequestBean.getNextadv() : contractEntity.getConttNextadv());
		contractEntity.setConttNextfin(Objects.nonNull(contractRequestBean.getNextfin()) ? contractRequestBean.getNextfin() : contractEntity.getConttNextfin());
		contractEntity.setConttNextint(Objects.nonNull(contractRequestBean.getNextint()) ? contractRequestBean.getNextint() : contractEntity.getConttNextint());
		contractEntity.setConttNextmis(Objects.nonNull(contractRequestBean.getNextmis()) ? contractRequestBean.getNextmis() : contractEntity.getConttNextmis());
		contractEntity.setConttNextrret(Objects.nonNull(contractRequestBean.getNextrret()) ? contractRequestBean.getNextrret() : contractEntity.getConttNextrret());
		contractEntity.setConttNextrun(Objects.nonNull(contractRequestBean.getNextrun()) ? contractRequestBean.getNextrun() : contractEntity.getConttNextrun());
		contractEntity.setConttNextset(Objects.nonNull(contractRequestBean.getNextset()) ? contractRequestBean.getNextset() : contractEntity.getConttNextset());
		contractEntity.setConttOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		contractEntity.setConttOrigval(Objects.nonNull(contractRequestBean.getOrigval()) ? contractRequestBean.getOrigval() : contractEntity.getConttOrigval());
		contractEntity.setConttPaidval(Objects.nonNull(contractRequestBean.getPaidval()) ? contractRequestBean.getPaidval() : contractEntity.getConttPaidval());
		contractEntity.setConttPartycode(Objects.nonNull(contractRequestBean.getPartycode()) ? contractRequestBean.getPartycode().trim() : contractEntity.getConttPartycode());
		contractEntity.setConttPartytype(Objects.nonNull(contractRequestBean.getPartytype()) ? contractRequestBean.getPartytype().trim() : contractEntity.getConttPartytype());
		contractEntity.setConttPrevtds(Objects.nonNull(contractRequestBean.getPrevtds()) ? contractRequestBean.getPrevtds() : contractEntity.getConttPrevtds());
		contractEntity.setConttProject(Objects.nonNull(contractRequestBean.getProject()) ? contractRequestBean.getProject().trim() : contractEntity.getConttProject());
		contractEntity.setConttProperty(Objects.nonNull(contractRequestBean.getProperty()) ? contractRequestBean.getProperty().trim() : contractEntity.getConttProperty());
		contractEntity.setConttProprietor(Objects.nonNull(contractRequestBean.getProprietor()) ? contractRequestBean.getProprietor().trim() : contractEntity.getConttProprietor());
		contractEntity.setConttRetainval(Objects.nonNull(contractRequestBean.getRetainval()) ? contractRequestBean.getRetainval() : contractEntity.getConttRetainval());
		contractEntity.setConttServtaxperc(Objects.nonNull(contractRequestBean.getServtaxperc()) ? contractRequestBean.getServtaxperc() : contractEntity.getConttServtaxperc());
		contractEntity.setConttSite(GenericAuditContextHolder.getContext().getSite()) ; 
		contractEntity.setConttSptdsper(Objects.nonNull(contractRequestBean.getSptdsper()) ? contractRequestBean.getSptdsper() : contractEntity.getConttSptdsper());
		contractEntity.setConttSwachhcessperc(Objects.nonNull(contractRequestBean.getSwachhcessperc()) ? contractRequestBean.getSwachhcessperc() : contractEntity.getConttSwachhcessperc());
		contractEntity.setConttTdscode(Objects.nonNull(contractRequestBean.getTdscode()) ? contractRequestBean.getTdscode().trim() : contractEntity.getConttTdscode());
		contractEntity.setConttTdsfrom(Objects.nonNull(contractRequestBean.getTdsfrom()) ? LocalDate.parse(contractRequestBean.getTdsfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : contractEntity.getConttTdsfrom());
		contractEntity.setConttTdsper(Objects.nonNull(contractRequestBean.getTdsper()) ? contractRequestBean.getTdsper() : contractEntity.getConttTdsper());
		contractEntity.setConttTdsptype(Objects.nonNull(contractRequestBean.getTdsptype()) ? contractRequestBean.getTdsptype().trim() : contractEntity.getConttTdsptype());
		contractEntity.setConttTdsupto(Objects.nonNull(contractRequestBean.getTdsupto()) ? LocalDate.parse(contractRequestBean.getTdsupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : contractEntity.getConttTdsupto());
		contractEntity.setConttToday(LocalDateTime.now()) ; 
		contractEntity.setConttUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		contractEntity.setConttVatperc(Objects.nonNull(contractRequestBean.getVatperc()) ? contractRequestBean.getVatperc() : contractEntity.getConttVatperc());
		contractEntity.setConttWctaxper(Objects.nonNull(contractRequestBean.getWctaxper()) ? contractRequestBean.getWctaxper() : contractEntity.getConttWctaxper());
		contractEntity.setConttWing(Objects.nonNull(contractRequestBean.getWing()) ? contractRequestBean.getWing().trim() : contractEntity.getConttWing());
		contractEntity.setConttWorkcode(Objects.nonNull(contractRequestBean.getWorkcode()) ? contractRequestBean.getWorkcode().trim() : contractEntity.getConttWorkcode());
		contractEntity.setConttWorkgroup(Objects.nonNull(contractRequestBean.getWorkgroup()) ? contractRequestBean.getWorkgroup().trim() : contractEntity.getConttWorkgroup());


		return contractEntity;
	};

}