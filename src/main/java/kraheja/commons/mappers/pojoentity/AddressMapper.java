package kraheja.commons.mappers.pojoentity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.bean.request.AddressRequestBean;
import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.entity.Address;
import kraheja.commons.entity.AddressCK;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public class AddressMapper {

	public static Function<Object[], Address> addAddressPojoEntityMapping = objectArray -> {
		AddressRequestBean addressRequestBean =(AddressRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String partycode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		return Address.builder()
				.addressCK(AddressCK.builder()
						.adrAdowner(partycode)
						.adrAdsegment(addressRequestBean.getAdsegment())
						.adrAdser(addressRequestBean.getSer())
						.adrAdtype(addressRequestBean.getAdtype())
						.build())
				.adrAdtopser(addressRequestBean.getTopser())
				.adrAdtitle(StringUtils.isNotBlank(addressRequestBean.getTitle()) ? addressRequestBean.getTitle().trim() : null)
				.adrAdfname(addressRequestBean.getFname())
				.adrAdmname(addressRequestBean.getMname())
				.adrAdlname(addressRequestBean.getLname())
				.adrAdline1(StringUtils.isNotBlank(addressRequestBean.getAd1()) ? addressRequestBean.getAd1().trim() : null)
				.adrAdline2(StringUtils.isNotBlank(addressRequestBean.getAd2()) ? addressRequestBean.getAd2().trim(): null)
				.adrAdline3(StringUtils.isNotBlank(addressRequestBean.getAd3()) ? addressRequestBean.getAd3().trim() : null)
				.adrAdline4(StringUtils.isNotBlank(addressRequestBean.getAd4()) ? addressRequestBean.getAd4().trim() : null)
				.adrAdline5(StringUtils.isNotBlank(addressRequestBean.getAd5()) ? addressRequestBean.getAd5().trim() : null)
				.adrTownship(StringUtils.isNotBlank(addressRequestBean.getTown()) ? addressRequestBean.getTown().trim() : null)
				.adrCity(StringUtils.isNotBlank(addressRequestBean.getCity()) ? addressRequestBean.getCity().trim() : null)
				.adrState(StringUtils.isNotBlank(addressRequestBean.getState()) ? addressRequestBean.getState().trim() : null)
				.adrCountry(StringUtils.isNotBlank(addressRequestBean.getCountry()) ? addressRequestBean.getCountry().trim() : null)
				.adrPincode(StringUtils.isNotBlank(addressRequestBean.getPincode()) ? addressRequestBean.getPincode().trim() : null)
				.adrPhoneoff(StringUtils.isNotBlank(addressRequestBean.getPhoneoff()) ? addressRequestBean.getPhoneoff().trim() : null)
				.adrPhoneres(StringUtils.isNotBlank(addressRequestBean.getPhoneres()) ? addressRequestBean.getPhoneres().trim() : null)
				.adrFax(StringUtils.isNotBlank(addressRequestBean.getFax()) ? addressRequestBean.getFax().trim() : null)
				.adrEmail(StringUtils.isNotBlank(addressRequestBean.getEmail()) ? addressRequestBean.getEmail().trim() : null)
				.adrBirthday(Objects.nonNull(addressRequestBean.getBirthday()) ?  CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(addressRequestBean.getBirthday()) : null)
				.adrSite(site)
				.adrUserid(addressRequestBean.getUserid())
				.adrToday(LocalDateTime.now())
				.adrOrigsite(site)
				.adrPhonemobile(StringUtils.isNotBlank(addressRequestBean.getPhonemobile()) ? addressRequestBean.getPhonemobile().trim() : null)
				.build();
	};

	public static BiFunction<Address, AddressRequestBean, Address> updateAddressPojoEntityMapping = (addressEntity, addressRequestBean) -> {
		addressEntity.getAddressCK().setAdrAdowner(Objects.nonNull(addressRequestBean.getOwner()) ? addressRequestBean.getOwner().trim() : addressEntity.getAddressCK().getAdrAdowner()); // to do in servuce impl
		addressEntity.getAddressCK().setAdrAdsegment(Objects.nonNull(addressRequestBean.getAdsegment()) ? addressRequestBean.getAdsegment().trim() : addressEntity.getAddressCK().getAdrAdsegment());
		addressEntity.getAddressCK().setAdrAdser(Objects.nonNull(addressRequestBean.getSer()) ? addressRequestBean.getSer().trim() : addressEntity.getAddressCK().getAdrAdser());
		addressEntity.getAddressCK().setAdrAdtype(Objects.nonNull(addressRequestBean.getAdtype()) ? addressRequestBean.getAdtype().trim() : addressEntity.getAddressCK().getAdrAdtype());
		addressEntity.setAdrAdtopser(Objects.nonNull(addressRequestBean.getTopser()) ? addressRequestBean.getTopser().trim() : addressEntity.getAdrAdtopser());
		addressEntity.setAdrAdtitle(Objects.nonNull(addressRequestBean.getTitle()) ? addressRequestBean.getTitle().trim() : addressEntity.getAdrAdtitle());
		addressEntity.setAdrAdfname(Objects.nonNull(addressRequestBean.getFname()) ? addressRequestBean.getFname().trim() : addressEntity.getAdrAdfname());
		addressEntity.setAdrAdmname(Objects.nonNull(addressRequestBean.getMname()) ? addressRequestBean.getMname().trim() : addressEntity.getAdrAdmname());
		addressEntity.setAdrAdlname(Objects.nonNull(addressRequestBean.getLname()) ? addressRequestBean.getLname().trim() : addressEntity.getAdrAdlname());
		addressEntity.setAdrAdline1(Objects.nonNull(addressRequestBean.getAd1()) ? addressRequestBean.getAd1().trim() : addressEntity.getAdrAdline1());
		addressEntity.setAdrAdline2(Objects.nonNull(addressRequestBean.getAd2()) ? addressRequestBean.getAd2().trim() : addressEntity.getAdrAdline2());
		addressEntity.setAdrAdline3(Objects.nonNull(addressRequestBean.getAd3()) ? addressRequestBean.getAd3().trim() : addressEntity.getAdrAdline3());
		addressEntity.setAdrAdline4(Objects.nonNull(addressRequestBean.getAd4()) ? addressRequestBean.getAd4().trim() : addressEntity.getAdrAdline4());
		addressEntity.setAdrAdline5(Objects.nonNull(addressRequestBean.getAd5()) ? addressRequestBean.getAd5().trim() : addressEntity.getAdrAdline5());
		addressEntity.setAdrTownship(Objects.nonNull(addressRequestBean.getTown()) ? addressRequestBean.getTown().trim() : addressEntity.getAdrTownship());
		addressEntity.setAdrCity(Objects.nonNull(addressRequestBean.getCity()) ? addressRequestBean.getCity().trim() : addressEntity.getAdrCity());
		addressEntity.setAdrState(Objects.nonNull(addressRequestBean.getState()) ? addressRequestBean.getState().trim() : addressEntity.getAdrState());
		addressEntity.setAdrCountry(Objects.nonNull(addressRequestBean.getCountry()) ? addressRequestBean.getCountry().trim() : addressEntity.getAdrCountry());
		addressEntity.setAdrPincode(Objects.nonNull(addressRequestBean.getPincode()) ? addressRequestBean.getPincode().trim() : addressEntity.getAdrPincode());
		addressEntity.setAdrPhoneoff(Objects.nonNull(addressRequestBean.getPhoneoff()) ? addressRequestBean.getPhoneoff().trim() : addressEntity.getAdrPhoneoff());
		addressEntity.setAdrPhoneres(Objects.nonNull(addressRequestBean.getPhoneres()) ? addressRequestBean.getPhoneres().trim() : addressEntity.getAdrPhoneres());
		addressEntity.setAdrFax(Objects.nonNull(addressRequestBean.getFax()) ? addressRequestBean.getFax().trim() : addressEntity.getAdrFax());
		addressEntity.setAdrEmail(Objects.nonNull(addressRequestBean.getEmail()) ? addressRequestBean.getEmail().trim() : addressEntity.getAdrEmail());
		addressEntity.setAdrBirthday(Objects.nonNull(addressRequestBean.getBirthday()) ? CommonUtils.INSTANCE.convertToLocalDateTimeViaInstant(addressRequestBean.getBirthday()) : addressEntity.getAdrBirthday());
		addressEntity.setAdrSite(Objects.nonNull(addressRequestBean.getSite()) ? addressRequestBean.getSite().trim() : addressEntity.getAdrSite());
		addressEntity.setAdrUserid(Objects.nonNull(addressRequestBean.getUserid()) ? addressRequestBean.getUserid().trim() : addressEntity.getAdrUserid());
		addressEntity.setAdrToday(Objects.nonNull(addressRequestBean.getToday()) ?  LocalDateTime.now() : addressEntity.getAdrToday());
		addressEntity.setAdrOrigsite(Objects.nonNull(addressRequestBean.getOrigsite()) ? addressRequestBean.getOrigsite().trim() : addressEntity.getAdrOrigsite());
		addressEntity.setAdrPhonemobile(Objects.nonNull(addressRequestBean.getPhonemobile()) ? addressRequestBean.getPhonemobile().trim() : addressEntity.getAdrPhonemobile());
		return addressEntity;
	};


	public interface AddressEntityPojoMapper {
		@SuppressWarnings("unchecked")
		public static Function<Object[], AddressResponseBean> fetchAddressEntityPojoMapper = objectArray -> {
			Address addressEntity = (Address) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
					? objectArray[BigInteger.ZERO.intValue()]
							: null);
			return AddressResponseBean.builder()
					.adsegment(addressEntity.getAddressCK().getAdrAdsegment())
					.adowner(addressEntity.getAddressCK().getAdrAdowner())
					.adtype(addressEntity.getAddressCK().getAdrAdtype())
					.adser(addressEntity.getAddressCK().getAdrAdser())
					.adfname(addressEntity.getAdrAdfname())
					.adline1(addressEntity.getAdrAdline1())
					.adline2(addressEntity.getAdrAdline2())
					.adline3(addressEntity.getAdrAdline3())
					.adline4(addressEntity.getAdrAdline4())
					.adline5(addressEntity.getAdrAdline5())
					.adlname(addressEntity.getAdrAdlname())
					.admname(addressEntity.getAdrAdmname())
					.adtitle(addressEntity.getAdrAdtitle())
					.adtopser(addressEntity.getAdrAdtopser())
					.birthday(Objects.nonNull(addressEntity.getAdrBirthday()) ?  addressEntity.getAdrBirthday().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.city(addressEntity.getAdrCity())
					.country(addressEntity.getAdrCountry())
					.email(addressEntity.getAdrEmail())
					.fax(addressEntity.getAdrFax())
					.origsite(addressEntity.getAdrOrigsite())
					.phonemobile(addressEntity.getAdrPhonemobile())
					.phoneoff(addressEntity.getAdrPhoneoff())
					.phoneres(addressEntity.getAdrPhoneres())
					.pincode(addressEntity.getAdrPincode())
					.site(addressEntity.getAdrSite())
					.state(addressEntity.getAdrState())
					.today(Objects.nonNull(addressEntity.getAdrToday()) ?  addressEntity.getAdrToday().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.township(addressEntity.getAdrTownship())
					.userid(addressEntity.getAdrUserid())
					.build()
					;
		};
	}
}
