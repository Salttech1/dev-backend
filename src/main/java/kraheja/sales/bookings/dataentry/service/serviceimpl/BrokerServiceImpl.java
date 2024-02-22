package kraheja.sales.bookings.dataentry.service.serviceimpl;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.Party;
import kraheja.commons.enums.AdSegment;
import kraheja.commons.enums.AdType;
import kraheja.commons.mappers.pojoentity.AddressMapper;
import kraheja.commons.mappers.pojoentity.PartyMapper;
import kraheja.commons.repository.AddressRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.repository.PartyRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.sales.bean.request.BrokerRequestBean;
import kraheja.sales.bookings.dataentry.mappers.BrokerEntityPojoMapper;
import kraheja.sales.bookings.dataentry.service.BrokerService;
import kraheja.sales.entity.Booking;
import kraheja.sales.entity.Broker;
import kraheja.sales.repository.BookingRepository;
import kraheja.sales.repository.BrokerRepository;

@Service
@Transactional
public class BrokerServiceImpl implements BrokerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceImpl.class);
	//private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private BrokerRepository brokerRepository;

	@Autowired
	private  AddressRepository addressRepository;

	@Autowired
	private  PartyRepository partyRepository;

	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private  BookingRepository bookingRepository;

	@Override
	public ResponseEntity<?> fetchBrokerByCode(String code) {
		// TODO Auto-generated method stub
		Broker brokerEntity = this.brokerRepository.findByBrokerCK_BrokCode(code);
		LOGGER.info("BrokerEntity :: {}", brokerEntity);

		if (brokerEntity != null) {
			LOGGER.info("Party Code :: {}" , code);

			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(code,
					CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.brokers);
			LOGGER.info("Party Entity :: {}" , partyEntity);

			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					code, AdSegment.PARTY.toString(), AdType.PMT.toString(), 
					CommonConstraints.INSTANCE.adrAdser);
			LOGGER.info("AddressEntity :: {} " , addressEntity);

			return ResponseEntity.ok(ServiceResponseBean.builder().data(BrokerEntityPojoMapper.fetchBrokerEntityPojoMapper.apply(new Object[] { Arrays.asList(brokerEntity), addressEntity, partyEntity })).status(Boolean.TRUE).build());

		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Broker Code : " + code + " not found").build());
	}

	@Override
	public ResponseEntity<?> addBroker(BrokerRequestBean brokerRequestBean) throws ParseException {

		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
		String brokerCode = GenericCounterIncrementLogicUtil.generateTranNo("#BROK", "#BROK");

		this.brokerRepository.save(BrokerEntityPojoMapper.addBrokerPojoEntityMapping.apply(new Object[] {brokerRequestBean,siteFromDBEntity,brokerCode}));

		brokerRequestBean.getAddressRequestBean().setAdsegment(AdSegment.PARTY.toString());
		brokerRequestBean.getAddressRequestBean().setSer(CommonConstraints.INSTANCE.adrAdser);
		brokerRequestBean.getAddressRequestBean().setAdtype(AdType.PMT.toString());
		brokerRequestBean.getAddressRequestBean().setTopser(CommonConstraints.INSTANCE.adrAdser);
		brokerRequestBean.getAddressRequestBean().setState(brokerRequestBean.getGstValdiationBean().state);

		this.addressRepository.save(AddressMapper.addAddressPojoEntityMapping.apply(new Object[] {brokerRequestBean.getAddressRequestBean(), siteFromDBEntity, brokerCode}));

		brokerRequestBean.getPartyRequestBean().setPartytype(CommonConstraints.INSTANCE.brokers);
		brokerRequestBean.getPartyRequestBean().setGstno(brokerRequestBean.getGstValdiationBean().gstNumber);
		brokerRequestBean.getPartyRequestBean().setPannum1(brokerRequestBean.getGstValdiationBean().panCardNumber);;
		
		this.partyRepository.save(PartyMapper.addPartyPojoEntityMapping.apply(new Object[] {brokerRequestBean.getPartyRequestBean(), siteFromDBEntity, brokerCode}));

		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Broker with Code : " + brokerCode + " Added Successfully").build());
	}

	@Override
	public ResponseEntity<?> updateBroker(BrokerRequestBean brokerRequestBean) throws ParseException {
		Broker brokerEntity = this.brokerRepository.findByBrokerCK_BrokCode(brokerRequestBean.getBrokCode());
		LOGGER.info("BrokerEntity :: {}", brokerEntity);

		String partyCode = brokerRequestBean.getBrokCode().trim();

		if(Objects.nonNull(brokerEntity)) { 
			LOGGER.info("Party Code :: {}" , partyCode);

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			LOGGER.info("Entity :: {}" , SiteFromDBEntity);

			Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(partyCode,
					CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.brokers);
			LOGGER.info("Party Entity :: {}" , partyEntity);

			Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
					partyCode, AdSegment.PARTY.toString(), AdType.PMT.toString(),
					CommonConstraints.INSTANCE.adrAdser);
			LOGGER.info("AddressEntity :: {}" , addressEntity);

			if(Objects.nonNull(brokerRequestBean))
				this.brokerRepository.save(BrokerEntityPojoMapper.updateBrokerPojoEntityMapping.apply(brokerEntity, brokerRequestBean));

			if(Objects.nonNull(brokerRequestBean.getAddressRequestBean())) {
				brokerRequestBean.getAddressRequestBean().setState(brokerRequestBean.getGstValdiationBean().state);
				this.addressRepository.save(AddressMapper.updateAddressPojoEntityMapping.apply(addressEntity, brokerRequestBean.getAddressRequestBean()));
			}

			if(Objects.nonNull(brokerRequestBean.getPartyRequestBean())) {

				brokerRequestBean.getPartyRequestBean().setPannum1(brokerRequestBean.getGstValdiationBean().panCardNumber);;
				brokerRequestBean.getPartyRequestBean().setGstno(brokerRequestBean.getGstValdiationBean().gstNumber);
				this.partyRepository.save(PartyMapper.updatePartyEntityPojoMapper.apply(partyEntity, brokerRequestBean.getPartyRequestBean()));
			}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Broker Code : " + partyCode + " Updated Successfully").build());
		}
		else { 		
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Broker Code : " + partyCode + " not found").build());
		}
	}

	@Override
	public ResponseEntity<?> deleteBroker(String code) throws ParseException {

		List<Booking> bookingEntityList = this.bookingRepository.findByBrokCode(code.trim());
		LOGGER.info("BookingEntity :: {}", bookingEntityList);
		if(CollectionUtils.isEmpty(bookingEntityList)) {
			Broker brokerEntity = this.brokerRepository.findByBrokerCK_BrokCode(code);
			LOGGER.info("BrokerEntity :: {}", brokerEntity);

			if(Objects.nonNull(brokerEntity)) { 
				Party partyEntity = this.partyRepository.findByPartyCk_ParPartycodeAndPartyCk_ParClosedateAndPartyCk_ParPartytype(code,
						CommonUtils.INSTANCE.closeDateInLocalDateTime(), CommonConstraints.INSTANCE.brokers);
				LOGGER.info("Party Entity :: {}" , partyEntity);

				Address addressEntity = this.addressRepository.findByAddressCK_AdrAdownerAndAddressCK_AdrAdsegmentAndAddressCK_AdrAdtypeAndAddressCK_AdrAdser(
						code, AdSegment.PARTY.toString(), AdType.PMT.toString(),
						CommonConstraints.INSTANCE.adrAdser);
				LOGGER.info("AddressEntity :: {}" , addressEntity);

				this.brokerRepository.delete(brokerEntity);
				this.addressRepository.delete(addressEntity);
				this.partyRepository.delete(partyEntity);

				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Broker Code : " + code + " Deleted Successfully").build());
			}
			else { 		
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Broker Code : " + code + " not found").build());
			}
		}else
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Cannot Delete Broker Code : " + code + " as bookings found").build());
	}
}
