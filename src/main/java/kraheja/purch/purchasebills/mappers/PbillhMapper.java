package kraheja.purch.purchasebills.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.PbillhRequestBean;
import kraheja.purch.bean.response.PbillhResponseBean;
import kraheja.purch.bean.response.PbillhResponseBean.PbillhResponseBeanBuilder;
import kraheja.purch.entity.Dc;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillh;
import kraheja.purch.entity.PbillhCK;
import kraheja.purch.entity.Pbillvat;

public interface PbillhMapper {

	@SuppressWarnings("unchecked")
	public static Function<Object[], PbillhResponseBean> fetchPbillhEntityPojoMapper = objectArray -> {
		Pbillh pbillhEntity = (Pbillh) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		Pbilld pbilldEntity = (Pbilld) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		List<Pbillvat> pbillvatEntityList = (List<Pbillvat>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		List<Dc> dcEntityList = (List<Dc>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE]) ? objectArray[CommonConstraints.INSTANCE.THREE] : null);
		Party partyEntity = (Party) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FOUR]) ? objectArray[CommonConstraints.INSTANCE.FOUR] : null);
		Address addressEntity = (Address) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FIVE]) ? objectArray[CommonConstraints.INSTANCE.FIVE] : null);

		PbillhResponseBeanBuilder pbillhResponseBean = PbillhResponseBean.builder();

		pbillhResponseBean					
		.advanceadj(pbillhEntity.getPblhAdvanceadj())
		.advancepaid(pbillhEntity.getPblhAdvancepaid())
		.amount(pbillhEntity.getPblhAmount())
		.authdate(Objects.nonNull(pbillhEntity.getPblhAuthdate()) ? pbillhEntity.getPblhAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.bankcode(pbillhEntity.getPblhBankcode())
		.billtype(pbillhEntity.getPblhBilltype())
		.bldgcode(pbillhEntity.getPblhBldgcode())
		.chequeno(pbillhEntity.getPblhChequeno())
		.coy(pbillhEntity.getPblhCoy())
		.crdays(pbillhEntity.getPblhCrdays())
		.date(Objects.nonNull(pbillhEntity.getPblhDate()) ? pbillhEntity.getPblhDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.debitamt(pbillhEntity.getPblhDebitamt())
		.debsocyn(pbillhEntity.getPblhDebsocyn())
		.misbldg(pbillhEntity.getPblhMisbldg())
		.misproject(pbillhEntity.getPblhMisproject())
		.narration(pbillhEntity.getPblhNarration())
		.omspurcyn(pbillhEntity.getPblhOmspurcyn())
		.orderedby(pbillhEntity.getPblhOrderedby())
		.origsite(pbillhEntity.getPblhOrigsite())
		.paidamt(pbillhEntity.getPblhPaidamt())
		.paiddate(Objects.nonNull(pbillhEntity.getPblhPaiddate()) ? pbillhEntity.getPblhPaiddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.paidref(pbillhEntity.getPblhPaidref())
		.partycode(pbillhEntity.getPblhPartycode())
		.partytype(pbillhEntity.getPblhPartytype())
		.project(pbillhEntity.getPblhProject())
		.prop(pbillhEntity.getPblhProp())
		.property(pbillhEntity.getPblhProperty())
		.retainos(pbillhEntity.getPblhRetainos())
		.retention(pbillhEntity.getPblhRetention())
		.site(pbillhEntity.getPblhSite())
		.suppbilldt(Objects.nonNull(pbillhEntity.getPblhSuppbilldt()) ? pbillhEntity.getPblhSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.tcsamount(pbillhEntity.getPblhTcsamount())
		.tdcertdt(Objects.nonNull(pbillhEntity.getPblhTdcertdt()) ? pbillhEntity.getPblhTdcertdt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.tdsamount(pbillhEntity.getPblhTdsamount())
		.tdsbankcode(pbillhEntity.getPblhTdsbankcode())
		.tdscertno(pbillhEntity.getPblhTdscertno())
		.tdschaldt(Objects.nonNull(pbillhEntity.getPblhTdschaldt()) ? pbillhEntity.getPblhTdschaldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.tdschalno(pbillhEntity.getPblhTdschalno())
		.tdsperc(pbillhEntity.getPblhTdsperc())
		.tdsprint(pbillhEntity.getPblhTdsprint())
		.today(pbillhEntity.getPblhToday())
		.authnum(pbillhEntity.getPblhAuthnum())
		.userid(pbillhEntity.getPblhUserid()).build();

		if(Objects.nonNull(pbilldEntity))
			pbillhResponseBean.pbilldResponseBean(PbilldMapper.fetchPbilldEntityPojoMapper.apply(pbilldEntity));

		if(CollectionUtils.isNotEmpty(pbillvatEntityList)) 
			pbillhResponseBean.pbillvatResponseBean(PbillvatMapper.fetchPbillvatEntityPojoMapper.apply(pbillvatEntityList));

		if(CollectionUtils.isNotEmpty(dcEntityList)) 
			pbillhResponseBean.dcResponseBean(DcMapper.fetchDcEntityPojoMapper.apply(dcEntityList));
		
		if(Objects.nonNull(partyEntity)) 
			pbillhResponseBean.partyResponseBean(PartyMapper.fetchPartyEntityPojoMapper.apply(new Object [] {partyEntity}));
		
		if(Objects.nonNull(addressEntity)) 
			pbillhResponseBean.addressResponseBean(AddressMapper.AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressEntity}));
		
		return pbillhResponseBean.build();

	};

	public static Function<PbillhRequestBean, Pbillh> addPbillhPojoEntityMapper = pbillhRequestBean -> {
		return Pbillh.builder()
				.pbillhCK(PbillhCK.builder()
						.pblhSer(pbillhRequestBean.getSer())
						.build())
				.pblhAdvanceadj(Objects.nonNull(pbillhRequestBean.getAdvanceadj()) ? pbillhRequestBean.getAdvanceadj() : BigInteger.ZERO.doubleValue())
				.pblhAdvancepaid(Objects.nonNull(pbillhRequestBean.getAdvancepaid()) ? pbillhRequestBean.getAdvancepaid() : BigInteger.ZERO.doubleValue())
				.pblhAmount(pbillhRequestBean.getAmount())
				.pblhAuthdate(Objects.nonNull(pbillhRequestBean.getAuthdate()) ? LocalDate.parse(pbillhRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhBankcode(pbillhRequestBean.getBankcode())
				.pblhBilltype(pbillhRequestBean.getBilltype())
				.pblhBldgcode(pbillhRequestBean.getBldgcode())
				.pblhChequeno(pbillhRequestBean.getChequeno())
				.pblhCoy(pbillhRequestBean.getCoy())
				.pblhCrdays(Objects.nonNull(pbillhRequestBean.getCrdays()) ? pbillhRequestBean.getCrdays() : BigInteger.ZERO.intValue())
				.pblhDate(Objects.nonNull(pbillhRequestBean.getDate()) ? LocalDate.parse(pbillhRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhDebitamt(Objects.nonNull(pbillhRequestBean.getDebitamt()) ? pbillhRequestBean.getDebitamt() : BigInteger.ZERO.doubleValue())
				.pblhDebsocyn(pbillhRequestBean.getDebsocyn())
				.pblhMisbldg(pbillhRequestBean.getMisbldg())
				.pblhMisproject(pbillhRequestBean.getMisproject())
				.pblhNarration(pbillhRequestBean.getNarration())
				.pblhOmspurcyn(pbillhRequestBean.getOmspurcyn())
				.pblhOrderedby(pbillhRequestBean.getOrderedby())
				.pblhOrigsite(GenericAuditContextHolder.getContext().getSite())
				.pblhPaidamt(Objects.nonNull(pbillhRequestBean.getPaidamt()) ? pbillhRequestBean.getPaidamt() : BigInteger.ZERO.intValue())
				.pblhPaiddate(Objects.nonNull(pbillhRequestBean.getPaiddate()) ? LocalDate.parse(pbillhRequestBean.getPaiddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhPaidref(pbillhRequestBean.getPaidref())
				.pblhPartycode(pbillhRequestBean.getPartycode())
				.pblhPartytype(pbillhRequestBean.getPartytype())
				.pblhProject(pbillhRequestBean.getProject())
				.pblhProp(pbillhRequestBean.getProp())
				.pblhProperty(pbillhRequestBean.getProperty())
				.pblhRetainos(Objects.nonNull(pbillhRequestBean.getRetainos()) ? pbillhRequestBean.getRetainos() : BigInteger.ZERO.doubleValue())
				.pblhRetention(pbillhRequestBean.getRetention())
				.pblhSite(GenericAuditContextHolder.getContext().getSite())
				.pblhSuppbillno(pbillhRequestBean.getSuppbillno())
				.pblhSuppbilldt(Objects.nonNull(pbillhRequestBean.getSuppbilldt()) ? LocalDate.parse(pbillhRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhTcsamount(pbillhRequestBean.getTcsamount())
				.pblhTdcertdt(Objects.nonNull(pbillhRequestBean.getTdcertdt()) ? LocalDate.parse(pbillhRequestBean.getTdcertdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhTdsamount(pbillhRequestBean.getTdsamount())
				.pblhTdsbankcode(pbillhRequestBean.getTdsbankcode())
				.pblhTdscertno(pbillhRequestBean.getTdscertno())
				.pblhTdschaldt(Objects.nonNull(pbillhRequestBean.getTdschaldt()) ? LocalDate.parse(pbillhRequestBean.getTdschaldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
				.pblhTdschalno(pbillhRequestBean.getTdschalno())
				.pblhTdsperc(pbillhRequestBean.getTdsperc())
				.pblhTdsprint(pbillhRequestBean.getTdsprint())
				.pblhToday(LocalDateTime.now())
				.pblhUserid(GenericAuditContextHolder.getContext().getUserid()).build()
				;

	};

	public static BiFunction<Pbillh, PbillhRequestBean, Pbillh> updatePbillhPojoEntityMapper = (pbillhEntity, pbillhRequestBean) -> {
		pbillhEntity.setPblhAdvanceadj(Objects.nonNull(pbillhRequestBean.getAdvanceadj()) ? pbillhRequestBean.getAdvanceadj() : pbillhEntity.getPblhAdvanceadj());
		pbillhEntity.setPblhAdvancepaid(Objects.nonNull(pbillhRequestBean.getAdvancepaid()) ? pbillhRequestBean.getAdvancepaid() : pbillhEntity.getPblhAdvancepaid());
		pbillhEntity.setPblhAmount(Objects.nonNull(pbillhRequestBean.getAmount()) ? pbillhRequestBean.getAmount() : pbillhEntity.getPblhAmount());
		pbillhEntity.setPblhAuthdate(Objects.nonNull(pbillhRequestBean.getAuthdate()) ? LocalDate.parse(pbillhRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhAuthdate());
		pbillhEntity.setPblhBankcode(Objects.nonNull(pbillhRequestBean.getBankcode()) ? pbillhRequestBean.getBankcode().trim() : pbillhEntity.getPblhBankcode());
		pbillhEntity.setPblhBilltype(Objects.nonNull(pbillhRequestBean.getBilltype()) ? pbillhRequestBean.getBilltype().trim() : pbillhEntity.getPblhBilltype());
		pbillhEntity.setPblhBldgcode(Objects.nonNull(pbillhRequestBean.getBldgcode()) ? pbillhRequestBean.getBldgcode().trim() : pbillhEntity.getPblhBldgcode());
		pbillhEntity.setPblhChequeno(Objects.nonNull(pbillhRequestBean.getChequeno()) ? pbillhRequestBean.getChequeno().trim() : pbillhEntity.getPblhChequeno());
		pbillhEntity.setPblhCoy(Objects.nonNull(pbillhRequestBean.getCoy()) ? pbillhRequestBean.getCoy().trim() : pbillhEntity.getPblhCoy());
		pbillhEntity.setPblhCrdays(Objects.nonNull(pbillhRequestBean.getCrdays()) ? pbillhRequestBean.getCrdays() : pbillhEntity.getPblhCrdays());
		pbillhEntity.setPblhDate(Objects.nonNull(pbillhRequestBean.getDate()) ? LocalDate.parse(pbillhRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhDate());
		pbillhEntity.setPblhDebitamt(Objects.nonNull(pbillhRequestBean.getDebitamt()) ? pbillhRequestBean.getDebitamt() : pbillhEntity.getPblhDebitamt());
		pbillhEntity.setPblhDebsocyn(Objects.nonNull(pbillhRequestBean.getDebsocyn()) ? pbillhRequestBean.getDebsocyn().trim() : pbillhEntity.getPblhDebsocyn());
		pbillhEntity.setPblhMisbldg(Objects.nonNull(pbillhRequestBean.getMisbldg()) ? pbillhRequestBean.getMisbldg().trim() : pbillhEntity.getPblhMisbldg());
		pbillhEntity.setPblhMisproject(Objects.nonNull(pbillhRequestBean.getMisproject()) ? pbillhRequestBean.getMisproject().trim() : pbillhEntity.getPblhMisproject());
		pbillhEntity.setPblhNarration(Objects.nonNull(pbillhRequestBean.getNarration()) ? pbillhRequestBean.getNarration().trim() : pbillhEntity.getPblhNarration());
		pbillhEntity.setPblhOmspurcyn(Objects.nonNull(pbillhRequestBean.getOmspurcyn()) ? pbillhRequestBean.getOmspurcyn().trim() : pbillhEntity.getPblhOmspurcyn());
		pbillhEntity.setPblhOrderedby(Objects.nonNull(pbillhRequestBean.getOrderedby()) ? pbillhRequestBean.getOrderedby().trim() : pbillhEntity.getPblhOrderedby());
		pbillhEntity.setPblhOrigsite(Objects.nonNull(pbillhRequestBean.getOrigsite()) ? pbillhRequestBean.getOrigsite().trim() : pbillhEntity.getPblhOrigsite());
		pbillhEntity.setPblhPaidamt(Objects.nonNull(pbillhRequestBean.getPaidamt()) ? pbillhRequestBean.getPaidamt() : pbillhEntity.getPblhPaidamt());
		pbillhEntity.setPblhPaiddate(Objects.nonNull(pbillhRequestBean.getPaiddate()) ? LocalDate.parse(pbillhRequestBean.getPaiddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhPaiddate());
		pbillhEntity.setPblhPaidref(Objects.nonNull(pbillhRequestBean.getPaidref()) ? pbillhRequestBean.getPaidref().trim() : pbillhEntity.getPblhPaidref());
		pbillhEntity.setPblhPartycode(Objects.nonNull(pbillhRequestBean.getPartycode()) ? pbillhRequestBean.getPartycode().trim() : pbillhEntity.getPblhPartycode());
		pbillhEntity.setPblhPartytype(Objects.nonNull(pbillhRequestBean.getPartytype()) ? pbillhRequestBean.getPartytype().trim() : pbillhEntity.getPblhPartytype());
		pbillhEntity.setPblhProject(Objects.nonNull(pbillhRequestBean.getProject()) ? pbillhRequestBean.getProject().trim() : pbillhEntity.getPblhProject());
		pbillhEntity.setPblhProp(Objects.nonNull(pbillhRequestBean.getProp()) ? pbillhRequestBean.getProp().trim() : pbillhEntity.getPblhProp());
		pbillhEntity.setPblhProperty(Objects.nonNull(pbillhRequestBean.getProperty()) ? pbillhRequestBean.getProperty().trim() : pbillhEntity.getPblhProperty());
		pbillhEntity.setPblhRetainos(Objects.nonNull(pbillhRequestBean.getRetainos()) ? pbillhRequestBean.getRetainos() : pbillhEntity.getPblhRetainos());
		pbillhEntity.setPblhRetention(Objects.nonNull(pbillhRequestBean.getRetention()) ? pbillhRequestBean.getRetention() : pbillhEntity.getPblhRetention());
		pbillhEntity.setPblhSite(GenericAuditContextHolder.getContext().getSite());
		pbillhEntity.setPblhSuppbilldt(Objects.nonNull(pbillhRequestBean.getSuppbilldt()) ? LocalDate.parse(pbillhRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhSuppbilldt());
		pbillhEntity.setPblhSuppbillno(Objects.nonNull(pbillhRequestBean.getSuppbillno()) ? pbillhRequestBean.getSuppbillno().trim() : pbillhEntity.getPblhSuppbillno());
		pbillhEntity.setPblhAuthnum(Objects.nonNull(pbillhRequestBean.getAuthnum()) ? pbillhRequestBean.getAuthnum().trim() : pbillhEntity.getPblhAuthnum());
		pbillhEntity.setPblhTcsamount(Objects.nonNull(pbillhRequestBean.getTcsamount()) ? pbillhRequestBean.getTcsamount() : pbillhEntity.getPblhTcsamount());
		pbillhEntity.setPblhTdcertdt(Objects.nonNull(pbillhRequestBean.getTdcertdt()) ? LocalDate.parse(pbillhRequestBean.getTdcertdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhTdcertdt());
		pbillhEntity.setPblhTdsamount(Objects.nonNull(pbillhRequestBean.getTdsamount()) ? pbillhRequestBean.getTdsamount() : pbillhEntity.getPblhTdsamount());
		pbillhEntity.setPblhTdsbankcode(Objects.nonNull(pbillhRequestBean.getTdsbankcode()) ? pbillhRequestBean.getTdsbankcode().trim() : pbillhEntity.getPblhTdsbankcode());
		pbillhEntity.setPblhTdscertno(Objects.nonNull(pbillhRequestBean.getTdscertno()) ? pbillhRequestBean.getTdscertno().trim() : pbillhEntity.getPblhTdscertno());
		pbillhEntity.setPblhTdschaldt(Objects.nonNull(pbillhRequestBean.getTdschaldt()) ? LocalDate.parse(pbillhRequestBean.getTdschaldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : pbillhEntity.getPblhTdschaldt());
		pbillhEntity.setPblhTdschalno(Objects.nonNull(pbillhRequestBean.getTdschalno()) ? pbillhRequestBean.getTdschalno().trim() : pbillhEntity.getPblhTdschalno());
		pbillhEntity.setPblhTdsperc(Objects.nonNull(pbillhRequestBean.getTdsperc()) ? pbillhRequestBean.getTdsperc() : pbillhEntity.getPblhTdsperc());
		pbillhEntity.setPblhTdsprint(Objects.nonNull(pbillhRequestBean.getTdsprint()) ? pbillhRequestBean.getTdsprint() : pbillhEntity.getPblhTdsprint());
		pbillhEntity.setPblhToday(LocalDateTime.now());
		pbillhEntity.setPblhUserid(GenericAuditContextHolder.getContext().getUserid());


		return pbillhEntity;
	};

}
