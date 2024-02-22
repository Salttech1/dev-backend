package kraheja.adminexp.overheads.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import kraheja.adminexp.overheads.dataentry.bean.request.LocationRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.response.LocationResponseBean;
import kraheja.adminexp.overheads.dataentry.bean.response.LocationResponseBean.LocationResponseBeanBuilder;
import kraheja.adminexp.overheads.dataentry.entity.Location;
import kraheja.commons.utils.CommonConstraints;

public interface LocationEntityPojoMapper {

	@SuppressWarnings("unchecked")
	public static Function<Object[],LocationResponseBean> fetchLocationEntityPojoMapper=objectArray ->
	{
		LocationResponseBeanBuilder locationBeanBuilder=LocationResponseBean.builder();
		Location locationEntity=(Location)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);
		
		locationBeanBuilder
		.code(locationEntity.getCode())
		.bldgcode(locationEntity.getLocBldgcode())
		.duefromsociety(locationEntity.getLocDuefromsociety())
		.name(locationEntity.getLocName())
		.site(locationEntity.getLocSite())
		.userid(locationEntity.getLocUserid())
		.today(locationEntity.getLocToday());
		
		return locationBeanBuilder.build();
	};
	public static Function<Object[],Location> addLocationEntityPojoMapper=objectArray->
	{
			
		LocationRequestBean locationRequestBean = (LocationRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		
	
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()] ) || objectArray[BigInteger.ONE.intValue()].equals(" ")
				? objectArray[BigInteger.ONE.intValue()]
						: null);
		
		String locationCode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		
		Location.LocationBuilder locationbuilder = Location.builder();
		
		locationbuilder
		.code(locationCode.trim())
		.locBldgcode(locationRequestBean.getBldgcode())
		.locDuefromsociety(locationRequestBean.getDuefromsociety())
		.locName(locationRequestBean.getName())
		.locSite(site.trim())
		.locUserid(locationRequestBean.getUserid())
		.locToday(LocalDateTime.now());
		
		return locationbuilder.build();
				
	};
	public static BiFunction<Location, LocationRequestBean, Location> updateLocationEntityPojoMapper=(locationEntity, locationRequestBean)->
	{
		
		locationEntity.setCode(Objects.nonNull(locationRequestBean.getCode()) ?  
				locationRequestBean.getCode() : locationEntity.getCode());
		
		locationEntity.setLocBldgcode (Objects.nonNull(locationRequestBean.getBldgcode()) ?  
				locationRequestBean.getBldgcode() : locationEntity.getLocBldgcode());
		
		locationEntity.setLocDuefromsociety(Objects.nonNull(locationRequestBean.getDuefromsociety()) ?  
				locationRequestBean.getDuefromsociety() : locationEntity.getLocDuefromsociety());
		
		locationEntity.setLocName(Objects.nonNull(locationRequestBean.getName()) ?  
				locationRequestBean.getName() : locationEntity.getLocName());
		
		locationEntity.setLocSite(Objects.nonNull(locationRequestBean.getSite()) ?  
				locationRequestBean.getSite() : locationEntity.getLocSite());
		
		locationEntity.setLocUserid(Objects.nonNull(locationRequestBean.getUserid()) ?  
				locationRequestBean.getUserid() : locationEntity.getLocUserid());
		
		locationEntity.setLocToday(LocalDateTime.now());
		
		return locationEntity;
	};
	
}
