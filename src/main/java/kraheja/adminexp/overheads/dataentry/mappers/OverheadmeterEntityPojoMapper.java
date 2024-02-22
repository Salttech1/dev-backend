package kraheja.adminexp.overheads.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadmeterRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadmeterResponseBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadmeterResponseBean.OverheadmeterResponseBeanBuilder;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.entity.OverheadmeterCK;
import kraheja.commons.filter.GenericAuditContextHolder;

public interface OverheadmeterEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<OverheadmeterResponseBean>> fetchOverheadmeterEntityPojoMapper = objectArray -> {
		OverheadmeterResponseBeanBuilder overheadmeterBeanBuilder = OverheadmeterResponseBean.builder();
		List<Overheadmeter> overheadmeterEntityList = (List<Overheadmeter>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return overheadmeterEntityList.stream().map(overheadmeterEntity -> {
			overheadmeterBeanBuilder.connocode(overheadmeterEntity.getOverheadmeterCK().getOhmeconnocode())
					.meterno(overheadmeterEntity.getOverheadmeterCK().getOhmeMeterno())
					.meternoOld(overheadmeterEntity.getOverheadmeterCK().getOhmeMeterno())
					.insertUpdateMode("E")
					.site(overheadmeterEntity.getSite())
					.today(overheadmeterEntity.getToday())
					.userid(overheadmeterEntity.getUserid());
			
			return overheadmeterBeanBuilder.build();
		}).collect(Collectors.toList());
	};
	
	public static Function<Object[], List<Overheadmeter>> addOverheadmeterEntityPojoMapper = objectArray -> {
		List<OverheadmeterRequestBean> overheadmeterRequestBeanList = (List<OverheadmeterRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		
		String ConnoCode = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);

		return overheadmeterRequestBeanList.stream().map(overheadmeterRequestBean ->
		{
			return Overheadmeter.builder()
					.overheadmeterCK(OverheadmeterCK.builder()
							.ohmeMeterno(overheadmeterRequestBean.getMeterno())
							.ohmeconnocode(ConnoCode)
					.build())
	//				.site("MUM")
					.site(GenericAuditContextHolder.getContext().getSite())
					.today(LocalDateTime.now())
					//.userid("sandy").build();
					.userid(GenericAuditContextHolder.getContext().getUserid()).build();

		}).collect(Collectors.toList());
};

	  public static BiFunction<Overheadmeter, OverheadmeterRequestBean, Overheadmeter> updateOverheadmeterEntityPojoMapper = (overheadmeterEntity, overheadmeterRequestBean) -> {
			overheadmeterEntity.getOverheadmeterCK().setOhmeconnocode(Objects.nonNull(overheadmeterRequestBean.getConnocode()) ? overheadmeterRequestBean.getConnocode().trim() : 
						overheadmeterEntity.getOverheadmeterCK().getOhmeconnocode());
			overheadmeterEntity.getOverheadmeterCK().setOhmeMeterno(Objects.nonNull(overheadmeterRequestBean.getMeterno()) ? 
					overheadmeterRequestBean.getMeterno().trim() : overheadmeterEntity.getOverheadmeterCK().getOhmeMeterno());
			overheadmeterEntity.setSite(Objects.nonNull(overheadmeterRequestBean.getSite()) ? 
					overheadmeterRequestBean.getSite().trim() : overheadmeterEntity.getSite());
			overheadmeterEntity.setToday(Objects.nonNull(overheadmeterRequestBean.getToday()) ? 
					overheadmeterRequestBean.getToday() : overheadmeterEntity.getToday());
			overheadmeterEntity.setUserid(Objects.nonNull(overheadmeterRequestBean.getUserid()) ? 
					overheadmeterRequestBean.getUserid().trim() : overheadmeterEntity.getUserid());

			return overheadmeterEntity;
		};


}
