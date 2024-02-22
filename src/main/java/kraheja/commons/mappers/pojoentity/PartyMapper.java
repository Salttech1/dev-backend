package kraheja.commons.mappers.pojoentity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.bean.request.PartyRequestBean;
import kraheja.commons.bean.response.PartyAddressResponseBean;
import kraheja.commons.bean.response.PartyResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.entity.PartyCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public class PartyMapper {

	public static Function<Object[], Party> addPartyPojoEntityMapping = objectArray -> {
		PartyRequestBean partyRequestBean =(PartyRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String partycode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		return Party.builder()
				.partyCk(PartyCK.builder()
						.parPartycode(partycode)
						.parPartytype(partyRequestBean.getPartytype())
						.parClosedate(CommonUtils.INSTANCE.closeDateInLocalDateTime()) 
						.build())
				.parTitle(StringUtils.isNotBlank(partyRequestBean.getTitle()) ? partyRequestBean.getTitle().trim() : null)
				.parPartyname(StringUtils.isNotBlank(partyRequestBean.getPartyname()) ? partyRequestBean.getPartyname().trim() : null)
				.parPartyconstt(StringUtils.isNotBlank(partyRequestBean.getConstt()) ? partyRequestBean.getConstt().trim() : null)
				.parCity(StringUtils.isNotBlank(partyRequestBean.getCity()) ? partyRequestBean.getCity().trim() : null)
				.parOpendate(Objects.nonNull(partyRequestBean.getOpendate()) ?  CommonUtils.INSTANCE.convertToLocalDateViaInstant(partyRequestBean.getOpendate()) : LocalDateTime.now().toLocalDate())
				.parPmtacnum(StringUtils.isNotBlank(partyRequestBean.getPmtacnum()) ? partyRequestBean.getPmtacnum().trim() : null)
				.parStaxregncen(StringUtils.isNotBlank(partyRequestBean.getStaxregncen()) ? partyRequestBean.getStaxregncen().trim() : null)
				.parStaxregnst(StringUtils.isNotBlank(partyRequestBean.getStaxregnst()) ? partyRequestBean.getStaxregnst().trim() : null)
				.parOStaxregncen(StringUtils.isNotBlank(partyRequestBean.getOstaxregncen()) ? partyRequestBean.getOstaxregncen().trim() : null)
				.parOStaxregnst(StringUtils.isNotBlank(partyRequestBean.getOstaxregnst()) ? partyRequestBean.getOstaxregnst().trim() : null)
				.parRcnum(StringUtils.isNotBlank(partyRequestBean.getRcnum()) ? partyRequestBean.getRcnum().trim() : null)
				.parValidparty(CommonConstraints.INSTANCE.validMinor)
				.parValidminor(CommonConstraints.INSTANCE.validParty)
				.parSite(site)
				.parUserid(GenericAuditContextHolder.getContext().getUserid())
				.parToday(LocalDateTime.now())
				.parSupptype(StringUtils.isNotBlank(partyRequestBean.getSupptype()) ? partyRequestBean.getSupptype().trim() : null)
				.parLtdcoyn(StringUtils.isNotBlank(partyRequestBean.getLtdcoyn()) ? partyRequestBean.getLtdcoyn().trim() : null)
				.parLastintpaid(Objects.nonNull(partyRequestBean.getLastintpaid()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getLastintpaid()) : null)
				.parVateffectivefrom(Objects.nonNull(partyRequestBean.getVatEffectiveFrom()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getVatEffectiveFrom()) : null)
				.parVatnum(StringUtils.isNotBlank(partyRequestBean.getVatnum()) ? partyRequestBean.getVatnum().trim() : null)
				.parTinnum(StringUtils.isNotBlank(partyRequestBean.getTinnum()) ? partyRequestBean.getTinnum().trim() : null)
				.parServicetaxnum(StringUtils.isNotBlank(partyRequestBean.getServicetaxnum()) ? partyRequestBean.getServicetaxnum().trim() : null)
				.parGstno(StringUtils.isNotBlank(partyRequestBean.getGstno()) ? partyRequestBean.getGstno().trim() : null)
				.parPayeebankcode1(StringUtils.isNotBlank(partyRequestBean.getPayeebankcode1()) ? partyRequestBean.getPayeebankcode1().trim() : null)
				.parPayeebranch1(StringUtils.isNotBlank(partyRequestBean.getPayeeBranch1()) ? partyRequestBean.getPayeeBranch1().trim() : null)
				.parPayeeacnum1(StringUtils.isNotBlank(partyRequestBean.getPayeeacNum1()) ? partyRequestBean.getPayeeacNum1().trim() : null)
				.parPayeeifsc1(StringUtils.isNotBlank(partyRequestBean.getPayeeIfsc1()) ? partyRequestBean.getPayeeIfsc1().trim() : null)
				.parPayeebankcode2(StringUtils.isNotBlank(partyRequestBean.getPayeeBranch2()) ? partyRequestBean.getPayeeBranch2().trim() : null)
				.parPayeebranch2(StringUtils.isNotBlank(partyRequestBean.getPayeeBranch2()) ? partyRequestBean.getPayeeBranch2().trim() : null)
				.parPayeeacnum2(StringUtils.isNotBlank(partyRequestBean.getPayeeacNum2()) ? partyRequestBean.getPayeeacNum2().trim() : null)
				.parPayeeifsc2(StringUtils.isNotBlank(partyRequestBean.getPayeeIfsc2()) ? partyRequestBean.getPayeeIfsc2().trim() : null)
				.parTanno(StringUtils.isNotBlank(partyRequestBean.getTanNo()) ? partyRequestBean.getTanNo().trim() : null)
				.parProfessiontaxno(StringUtils.isNotBlank(partyRequestBean.getProfessionTaxno()) ? partyRequestBean.getProfessionTaxno().trim() : null)
				.parPfno(StringUtils.isNotBlank(partyRequestBean.getPfNo()) ? partyRequestBean.getPfNo().trim() : null)
				.parEsicno(StringUtils.isNotBlank(partyRequestBean.getEsicno()) ? partyRequestBean.getEsicno().trim() : null)
				.parCino(StringUtils.isNotBlank(partyRequestBean.getCino()) ? partyRequestBean.getCino().trim() : null)
				.parAadharno(StringUtils.isNotBlank(partyRequestBean.getAadharno()) ? partyRequestBean.getAadharno().trim() : null)
				.parAadharPanLinkedYN(StringUtils.isNotBlank(partyRequestBean.getAadharpanlinkedyn()) ? partyRequestBean.getAadharpanlinkedyn().trim() : null)
				.build();
	};
//	.parOpendate(LocalDateTime.now().toLocalDate())
	public static BiFunction<Party, PartyRequestBean, Party> updatePartyEntityPojoMapper = (partyEntity, partyRequestBean) -> {
		partyEntity.getPartyCk().setParPartycode(Objects.nonNull(partyRequestBean.getPartycode()) ?  partyRequestBean.getPartycode().trim() : partyEntity.getPartyCk().getParPartycode());
		partyEntity.getPartyCk().setParPartytype(Objects.nonNull(partyRequestBean.getPartytype()) ?  partyRequestBean.getPartytype().trim() : partyEntity.getPartyCk().getParPartytype());
		partyEntity.getPartyCk().setParClosedate(Objects.nonNull(partyRequestBean.getClosedate()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getClosedate()) : partyEntity.getPartyCk().getParClosedate());
		partyEntity.setParAadharno(Objects.nonNull(partyRequestBean.getAadharno()) ?  partyRequestBean.getAadharno().trim() : partyEntity.getParAadharno());
		partyEntity.setParCino(Objects.nonNull(partyRequestBean.getCino()) ?  partyRequestBean.getCino().trim() : partyEntity.getParCino());
		partyEntity.setParCity(Objects.nonNull(partyRequestBean.getCity()) ?  partyRequestBean.getCity().trim() : partyEntity.getParCity());
		partyEntity.setParEsicno(Objects.nonNull(partyRequestBean.getEsicno()) ?  partyRequestBean.getEsicno().trim() : partyEntity.getParEsicno());
		partyEntity.setParGstno(Objects.nonNull(partyRequestBean.getGstno()) ?  partyRequestBean.getGstno().trim() : partyEntity.getParGstno());
		partyEntity.setParLastintpaid(Objects.nonNull(partyRequestBean.getLastintpaid()) ?   CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getLastintpaid()) : partyEntity.getParLastintpaid());
		partyEntity.setParLtdcoyn(Objects.nonNull(partyRequestBean.getLtdcoyn()) ?  partyRequestBean.getLtdcoyn().trim() : partyEntity.getParLtdcoyn());
		partyEntity.setParOldrefdate(Objects.nonNull(partyRequestBean.getOldrefdate()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getOldrefdate()) : partyEntity.getParOldrefdate());
		partyEntity.setParOpendate(Objects.nonNull(partyRequestBean.getOpendate()) ?  CommonUtils.INSTANCE.convertToLocalDateViaInstant(partyRequestBean.getOpendate()) : partyEntity.getParOpendate());
		partyEntity.setParStaxregncen(Objects.nonNull(partyRequestBean.getStaxregncen()) ?  partyRequestBean.getStaxregncen().trim() : partyEntity.getParStaxregncen());
		partyEntity.setParStaxregnst(Objects.nonNull(partyRequestBean.getStaxregnst()) ?  partyRequestBean.getStaxregnst().trim() : partyEntity.getParStaxregnst());
		partyEntity.setParPartyconstt(Objects.nonNull(partyRequestBean.getConstt()) ?  partyRequestBean.getConstt().trim() : partyEntity.getParPartyconstt());
		partyEntity.setParPartyname(Objects.nonNull(partyRequestBean.getPartyname()) ?  partyRequestBean.getPartyname().trim() : partyEntity.getParPartyname());
		partyEntity.setParPayeeacnum1(Objects.nonNull(partyRequestBean.getPayeeacNum1()) ?  partyRequestBean.getPayeeacNum1().trim() : partyEntity.getParPayeeacnum1());
		partyEntity.setParPayeeacnum2(Objects.nonNull(partyRequestBean.getPayeeacNum2()) ?  partyRequestBean.getPayeeacNum2().trim() : partyEntity.getParPayeeacnum2());
		partyEntity.setParPayeebankcode1(Objects.nonNull(partyRequestBean.getPayeebankcode1()) ?  partyRequestBean.getPayeebankcode1().trim() : partyEntity.getParPayeebankcode1());
		partyEntity.setParPayeebankcode2(Objects.nonNull(partyRequestBean.getPayeebankcode2()) ?  partyRequestBean.getPayeebankcode2().trim() : partyEntity.getParPayeebankcode2());
		partyEntity.setParPayeebankname1(Objects.nonNull(partyRequestBean.getPayeebankname1()) ?  partyRequestBean.getPayeebankname1().trim() : partyEntity.getParPayeebankname1());
		partyEntity.setParPayeebankname2(Objects.nonNull(partyRequestBean.getPayeebankname2()) ?  partyRequestBean.getPayeebankname2().trim() : partyEntity.getParPayeebankname2());
		partyEntity.setParPayeebranch1(Objects.nonNull(partyRequestBean.getPayeeBranch1()) ?  partyRequestBean.getPayeeBranch1().trim() : partyEntity.getParPayeebranch1());
		partyEntity.setParPayeebranch2(Objects.nonNull(partyRequestBean.getPayeeBranch2()) ?  partyRequestBean.getPayeeBranch2().trim() : partyEntity.getParPayeebranch2());
		partyEntity.setParPayeeifsc1(Objects.nonNull(partyRequestBean.getPayeeIfsc1()) ?  partyRequestBean.getPayeeIfsc1().trim() : partyEntity.getParPayeeifsc1());
		partyEntity.setParPayeeifsc2(Objects.nonNull(partyRequestBean.getPayeeIfsc2()) ?  partyRequestBean.getPayeeIfsc2().trim() : partyEntity.getParPayeeifsc2());
		partyEntity.setParPfno(Objects.nonNull(partyRequestBean.getPfNo()) ?  partyRequestBean.getPfNo().trim() : partyEntity.getParPfno());
		partyEntity.setParPmtacnum(Objects.nonNull(partyRequestBean.getPmtacnum()) ?  partyRequestBean.getPmtacnum().trim() : partyEntity.getParPmtacnum());
		partyEntity.setParProfessiontaxno(Objects.nonNull(partyRequestBean.getProfessionTaxno()) ?  partyRequestBean.getProfessionTaxno().trim() : partyEntity.getParProfessiontaxno());
		partyEntity.setParRcnum(Objects.nonNull(partyRequestBean.getRcnum()) ?  partyRequestBean.getRcnum().trim() : partyEntity.getParRcnum());
		partyEntity.setParServicetaxnum(Objects.nonNull(partyRequestBean.getServicetaxnum()) ?  partyRequestBean.getServicetaxnum().trim() : partyEntity.getParServicetaxnum());
		partyEntity.setParSite(GenericAuditContextHolder.getContext().getSite());
		partyEntity.setParStaxregncen(Objects.nonNull(partyRequestBean.getStaxregncen()) ?  partyRequestBean.getStaxregncen().trim() : partyEntity.getParStaxregncen());
		partyEntity.setParStaxregnst(Objects.nonNull(partyRequestBean.getStaxregnst()) ?  partyRequestBean.getStaxregnst().trim() : partyEntity.getParStaxregnst());
		partyEntity.setParSupptype(Objects.nonNull(partyRequestBean.getSupptype()) ?  partyRequestBean.getSupptype().trim() : partyEntity.getParSupptype());
		partyEntity.setParTanno(Objects.nonNull(partyRequestBean.getTanNo()) ?  partyRequestBean.getTanNo().trim() : partyEntity.getParTanno());
		partyEntity.setParTinnum(Objects.nonNull(partyRequestBean.getTinnum()) ?  partyRequestBean.getTinnum().trim() : partyEntity.getParTinnum());
		partyEntity.setParTitle(Objects.nonNull(partyRequestBean.getTitle()) ?  partyRequestBean.getTitle().trim() : partyEntity.getParTitle());
		partyEntity.setParToday(Objects.nonNull(partyRequestBean.getToday()) ?  LocalDateTime.now() : partyEntity.getParToday());
		partyEntity.setParUserid(GenericAuditContextHolder.getContext().getUserid());
		partyEntity.setParValidminor(Objects.nonNull(partyRequestBean.getValidminor()) ?  partyRequestBean.getValidminor().trim() : partyEntity.getParValidminor());
		partyEntity.setParValidparty(Objects.nonNull(partyRequestBean.getValidparty()) ?  partyRequestBean.getValidparty().trim() : partyEntity.getParValidparty());
		partyEntity.setParVatcanceldt(Objects.nonNull(partyRequestBean.getVatcanceldt()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getVatcanceldt()) : partyEntity.getParVatcanceldt());
		partyEntity.setParVateffectivefrom(Objects.nonNull(partyRequestBean.getVatEffectiveFrom()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(partyRequestBean.getVatEffectiveFrom()) : partyEntity.getParVateffectivefrom());
		partyEntity.setParVatnum(Objects.nonNull(partyRequestBean.getVatnum()) ?  partyRequestBean.getVatnum().trim() : partyEntity.getParVatnum());
		return partyEntity;
	};

	public static Function<Object[], PartyResponseBean> fetchPartyEntityPojoMapper = objectArray -> {
		Party partyEntity = (Party) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);

		return PartyResponseBean.builder()
				.partycode(partyEntity.getPartyCk().getParPartycode())
				.partytype(partyEntity.getPartyCk().getParPartytype())
				.closedate(Objects.nonNull(partyEntity.getPartyCk().getParClosedate()) ?  partyEntity.getPartyCk().getParClosedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.aadharno(partyEntity.getParAadharno())
				.cino(partyEntity.getParCino())
				.city(partyEntity.getParCity())
				.esicno(partyEntity.getParEsicno())
				.gstno(partyEntity.getParGstno())
				.lastintpaid(Objects.nonNull(partyEntity.getParLastintpaid()) ?  partyEntity.getParLastintpaid().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.ltdcoyn(partyEntity.getParLtdcoyn())
				.oldrefdate(Objects.nonNull(partyEntity.getParOldrefdate()) ?  partyEntity.getParOldrefdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.opendate(Objects.nonNull(partyEntity.getParOpendate()) ?  partyEntity.getParOpendate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.staxregncen(partyEntity.getParStaxregncen())
				.staxregnst(partyEntity.getParStaxregnst())
				.partyconstt(partyEntity.getParPartyconstt())
				.partyname(partyEntity.getParPartyname())
				.payeeacnum1(partyEntity.getParPayeeacnum1())
				.payeeacnum2(partyEntity.getParPayeeacnum2())
				.payeebankcode1(partyEntity.getParPayeebankcode1())
				.payeebankcode2(partyEntity.getParPayeebankcode2())
				.payeebankname1(partyEntity.getParPayeebankname1())
				.payeebankname2(partyEntity.getParPayeebankname2())
				.payeebranch1(partyEntity.getParPayeebranch1())
				.payeebranch2(partyEntity.getParPayeebranch2())
				.payeeifsc1(partyEntity.getParPayeeifsc1())
				.payeeifsc2(partyEntity.getParPayeeifsc2())
				.pfno(partyEntity.getParPfno())
				.pmtacnum(partyEntity.getParPmtacnum())
				.professiontaxno(partyEntity.getParProfessiontaxno())
				.rcnum(partyEntity.getParRcnum())
				.servicetaxnum(partyEntity.getParServicetaxnum())
				.site(partyEntity.getParSite())
				.staxregncen(partyEntity.getParStaxregncen())
				.staxregnst(partyEntity.getParStaxregnst())
				.supptype(partyEntity.getParSupptype())
				.tanno(partyEntity.getParTanno())
				.tinnum(partyEntity.getParTinnum())
				.title(partyEntity.getParTitle())
				.today(Objects.nonNull(partyEntity.getParToday()) ?  partyEntity.getParToday().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.userid(partyEntity.getParUserid())
				.validminor(partyEntity.getParValidminor())
				.validparty(partyEntity.getParValidparty())
				.vatcanceldt(Objects.nonNull(partyEntity.getParVatcanceldt()) ?  partyEntity.getParVatcanceldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.vateffectivefrom(Objects.nonNull(partyEntity.getParVateffectivefrom()) ?  partyEntity.getParVateffectivefrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
				.vatnum(partyEntity.getParVatnum()).build()
				;
	};
	
	public static BiFunction<Party, Address, PartyAddressResponseBean> fetchPartyAndAddressEntityPojoMapper = (party, address) -> {
		PartyAddressResponseBean.PartyAddressResponseBeanBuilder partyAddressResponseBean = PartyAddressResponseBean.builder();
		if(Objects.nonNull(party))
			partyAddressResponseBean.partyResponseBean(PartyMapper.fetchPartyEntityPojoMapper.apply(new Object[] {party}));
		
		if(Objects.nonNull(address))
			partyAddressResponseBean.addressResponseBean(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {address}));
		
		return partyAddressResponseBean.build();
	};
}
