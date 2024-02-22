package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.mappers.pojoentity.AddressMapper.AddressEntityPojoMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.BrokerRequestBean;
import kraheja.sales.bean.response.BrokerResponseBean;
import kraheja.sales.bean.response.BrokerResponseBean.BrokerResponseBeanBuilder;
import kraheja.sales.entity.Broker;
import kraheja.sales.entity.BrokerCK;

public interface BrokerEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<BrokerResponseBean>> fetchBrokerEntityPojoMapper = objectArray -> {
		BrokerResponseBeanBuilder brokerBeanBuilder = BrokerResponseBean.builder();
		List<Broker> brokerEntityList = (List<Broker>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);
		Address addressEntity = (Address) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);
		Party partyEntity = (Party) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
						: null);

		return brokerEntityList.stream().map(brokerEntity -> {
			brokerBeanBuilder.brokCode( brokerEntity.getBrokerCK().getBrokCode()).title(brokerEntity.getBrokTitle())
			.broklastyr(brokerEntity.getBrokBroklastyr()).brokthisyr(brokerEntity.getBrokBrokthisyr())
			.broktodate(brokerEntity.getBrokBroktodate()).busslastyr(brokerEntity.getBrokBusslastyr())
			.bussthisyr(brokerEntity.getBrokBussthisyr()).busstodate(brokerEntity.getBrokBusstodate())
			.city(brokerEntity.getBrokCity()).contactperson(brokerEntity.getBrokContactperson())
			.designation(brokerEntity.getBrokDesignation()).name(brokerEntity.getBrokName())
			.origsite(brokerEntity.getBrokOrigsite()).rera(brokerEntity.getBrokRera())
			.site(brokerEntity.getBrokSite()).tdslastyr(brokerEntity.getBrokTdslastyr())
			.tdsthisyr(brokerEntity.getBrokTdsthisyr()).tdstodate(brokerEntity.getBrokTdstodate())
			.today(brokerEntity.getBrokToday()).type(brokerEntity.getBrokType())
			.userid(brokerEntity.getBrokUserid());

			if(Objects.nonNull(addressEntity)) {
				brokerBeanBuilder.addressResponseBean(AddressEntityPojoMapper.fetchAddressEntityPojoMapper.apply(new Object[] {addressEntity}));
			}

			if(Objects.nonNull(partyEntity)) {
				brokerBeanBuilder.partyResponseBean(PartyMapper.fetchPartyEntityPojoMapper.apply(new Object[] {partyEntity}));
			}

			return brokerBeanBuilder.build();
		}).collect(Collectors.toList());

	};

	public static Function<Object[], Broker> addBrokerPojoEntityMapping = objectArray -> {
		BrokerRequestBean brokerRequestBean = (BrokerRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()] ) || objectArray[BigInteger.ONE.intValue()].equals(" ")
				? objectArray[BigInteger.ONE.intValue()]
						: null);

		String brokerCode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		Broker.BrokerBuilder brokerbuilder = Broker.builder();
		brokerbuilder
		.brokerCK(BrokerCK.builder()
				.brokCode(brokerCode.trim())
				.build())
		.brokTitle(brokerRequestBean.getTitle()).brokBroklastyr(0.00)
		.brokBrokthisyr(0.00).brokBroktodate(0.00)
		.brokBusslastyr(0.00).brokBussthisyr(0.00)
		.brokBusstodate(0.00).brokCity(brokerRequestBean.getCity())
		.brokContactperson(brokerRequestBean.getContactperson()).brokDesignation(brokerRequestBean.getDesignation())
		.brokName(brokerRequestBean.getName()).brokOrigsite(site.trim())
		.brokRera(brokerRequestBean.getRera()).brokSite(site.trim())
		.brokTdslastyr(0.00).brokTdsthisyr(0.00)
		.brokTdstodate(0.00)
		.brokToday(LocalDateTime.now())
		.brokType("S").brokUserid(brokerRequestBean.getUserid());

		return brokerbuilder.build();
	};

	public static BiFunction<Broker, BrokerRequestBean, Broker> updateBrokerPojoEntityMapping = (brokerEntity, brokerRequestBean) -> {
		brokerEntity.getBrokerCK().setBrokCode(Objects.nonNull(brokerRequestBean.getBrokCode()) ?  brokerRequestBean.getBrokCode() : brokerEntity.getBrokerCK().getBrokCode());
		brokerEntity.setBrokBroklastyr(Objects.nonNull(brokerRequestBean.getBroklastyr()) ?  brokerRequestBean.getBroklastyr() : brokerEntity.getBrokBroklastyr());
		brokerEntity.setBrokBrokthisyr(Objects.nonNull(brokerRequestBean.getBrokthisyr()) ?  brokerRequestBean.getBrokthisyr() : brokerEntity.getBrokBrokthisyr());
		brokerEntity.setBrokBroktodate(Objects.nonNull(brokerRequestBean.getBroktodate()) ?  brokerRequestBean.getBroktodate() : brokerEntity.getBrokBroktodate());
		brokerEntity.setBrokBusslastyr(Objects.nonNull(brokerRequestBean.getBusslastyr()) ?  brokerRequestBean.getBusslastyr() : brokerEntity.getBrokBusslastyr());
		brokerEntity.setBrokBussthisyr(Objects.nonNull(brokerRequestBean.getBrokthisyr()) ?  brokerRequestBean.getBrokthisyr() : brokerEntity.getBrokBrokthisyr());
		brokerEntity.setBrokBusstodate(Objects.nonNull(brokerRequestBean.getBusstodate()) ?  brokerRequestBean.getBusstodate() : brokerEntity.getBrokBusstodate());
		brokerEntity.setBrokCity(Objects.nonNull(brokerRequestBean.getCity()) ?  brokerRequestBean.getCity() : brokerEntity.getBrokCity());
		brokerEntity.setBrokContactperson(Objects.nonNull(brokerRequestBean.getContactperson()) ?  brokerRequestBean.getContactperson() : brokerEntity.getBrokContactperson());
		brokerEntity.setBrokDesignation(Objects.nonNull(brokerRequestBean.getDesignation()) ?  brokerRequestBean.getDesignation() : brokerEntity.getBrokDesignation());
		brokerEntity.setBrokName(Objects.nonNull(brokerRequestBean.getName()) ?  brokerRequestBean.getName() : brokerEntity.getBrokName());
		brokerEntity.setBrokOrigsite(Objects.nonNull(brokerRequestBean.getOrigsite()) ?  brokerRequestBean.getOrigsite() : brokerEntity.getBrokOrigsite());
		brokerEntity.setBrokRera(Objects.nonNull(brokerRequestBean.getRera()) ?  brokerRequestBean.getRera() : brokerEntity.getBrokRera());
		brokerEntity.setBrokSite(Objects.nonNull(brokerRequestBean.getSite()) ?  brokerRequestBean.getSite() : brokerEntity.getBrokSite());
		brokerEntity.setBrokTitle(Objects.nonNull(brokerRequestBean.getTitle()) ?  brokerRequestBean.getTitle() : brokerEntity.getBrokTitle());
		brokerEntity.setBrokToday(LocalDateTime.now());
		brokerEntity.setBrokType(Objects.nonNull(brokerRequestBean.getType()) ?  brokerRequestBean.getType() : brokerEntity.getBrokType());
		brokerEntity.setBrokUserid(Objects.nonNull(brokerRequestBean.getUserid()) ?  brokerRequestBean.getUserid() : brokerEntity.getBrokUserid());

		return brokerEntity;
	};

}
