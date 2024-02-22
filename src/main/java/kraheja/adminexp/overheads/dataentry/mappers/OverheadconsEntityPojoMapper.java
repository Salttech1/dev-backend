package kraheja.adminexp.overheads.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadconsResponseBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadconsResponseBean.OverheadconsResponseBeanBuilder;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.OverheadconsCK;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface OverheadconsEntityPojoMapper {
	@SuppressWarnings("unchecked")

	public static Function<Object[],OverheadconsResponseBean> fetchOverheadconsEntityPojoMapper=objectArray ->
	{
		OverheadconsResponseBeanBuilder overheadconsBeanBuilder=OverheadconsResponseBean.builder();
		Overheadcons overheadconsEntity=(Overheadcons)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()] : null);
		
		List<Overheadmeter> overheadconsEntityList=(List<Overheadmeter>)(Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()] : null);

		//final List<OverheadmeterResponseBean> overheadmeterResponses = new ArrayList<>();

		overheadconsBeanBuilder
		.ohdhconnocode(overheadconsEntity.getOverheadconsCK().getOhdhConnocode())
		.ohdhbillcoy(overheadconsEntity.getOhdhBillcoy())
		.ohdhbilltype(overheadconsEntity.getOhdhBilltype())
		.ohdhbldgcode(overheadconsEntity.getOhdhBldgcode())
		.ohdhconno(overheadconsEntity.getOhdhConno())
		.ohdhdepositeamt(overheadconsEntity.getOhdhDepositeamt())
		.ohdhflatnum(overheadconsEntity.getOhdhFlatnum())
		.ohdhload(overheadconsEntity.getOhdhLoad())
		.ohdhlocation(overheadconsEntity.getOhdhLocation())
		.ohdhpaycoy(overheadconsEntity.getOhdhPaycoy())
		.ohdhsite(overheadconsEntity.getOhdhSite())
		.ohdhstatus(overheadconsEntity.getOhdhStatus())
		.ohdhtmpmeteryn(overheadconsEntity.getOhdhTmpmeteryn())
		.ohdhtoday(overheadconsEntity.getOhdhToday())
		.ohdhuserid(overheadconsEntity.getOhdhUserid())
		.ohdhvacantflatyn(overheadconsEntity.getOhdhVacantflatyn());
		
		if(CollectionUtils.isNotEmpty(overheadconsEntityList)) {
			overheadconsBeanBuilder.
			overheadmeterResponseBeanList(
					OverheadmeterEntityPojoMapper.fetchOverheadmeterEntityPojoMapper
					.apply(new Object[] {overheadconsEntityList}));
					
		}
		
		return overheadconsBeanBuilder.build();
	};

	public static Function<Object[], Overheadcons> addOverheadconsEntityPojoMapper = objectArray -> {
		OverheadconsRequestBean overheadconsRequestBean = (OverheadconsRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);
		String ConnoCode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
						: null);
		
		Overheadcons.OverheadconsBuilder overheadconsbuilder = Overheadcons.builder();
		overheadconsbuilder
			.ohdhBillcoy(overheadconsRequestBean.getOhdhbillcoy())
			.ohdhBilltype(overheadconsRequestBean.getOhdhbilltype())
			.ohdhBldgcode(overheadconsRequestBean.getOhdhbldgcode())
			.ohdhConno(overheadconsRequestBean.getOhdhconno())
			.overheadconsCK(OverheadconsCK.builder()
					.ohdhConnocode(ConnoCode)
					.ohdhConsumerno(overheadconsRequestBean.getOhdhconsumerno())
					.build()
					)
			.ohdhDepositeamt(overheadconsRequestBean.getOhdhdepositeamt())
			.ohdhFlatnum(overheadconsRequestBean.getOhdhflatnum())
			.ohdhLoad(overheadconsRequestBean.getOhdhload())
			.ohdhLocation(overheadconsRequestBean.getOhdhlocation())
			.ohdhPaycoy(overheadconsRequestBean.getOhdhpaycoy())
			.ohdhSite(site)
			.ohdhStatus(overheadconsRequestBean.getOhdhstatus())
			.ohdhTmpmeteryn(overheadconsRequestBean.getOhdhtmpmeteryn())
			.ohdhToday(LocalDateTime.now())
			.ohdhUserid(overheadconsRequestBean.getOhdhuserid())
			.ohdhVacantflatyn(overheadconsRequestBean.getOhdhvacantflatyn());
		return overheadconsbuilder.build();
	};

	public static BiFunction<Overheadcons, OverheadconsRequestBean, Overheadcons> updateOverheadconsEntityPojoMapper = (overheadconsEntity, overheadconsRequestBean) -> {
		overheadconsEntity.setOhdhBillcoy(Objects.nonNull(overheadconsRequestBean.getOhdhbillcoy()) ? overheadconsRequestBean.getOhdhbillcoy().trim() : overheadconsEntity.getOhdhBillcoy());
		overheadconsEntity.setOhdhBilltype(Objects.nonNull(overheadconsRequestBean.getOhdhbilltype()) ? overheadconsRequestBean.getOhdhbilltype().trim() : overheadconsEntity.getOhdhBilltype());
		overheadconsEntity.setOhdhBldgcode(Objects.nonNull(overheadconsRequestBean.getOhdhbldgcode()) ? overheadconsRequestBean.getOhdhbldgcode().trim() : overheadconsEntity.getOhdhBldgcode());
		overheadconsEntity.setOhdhConno(Objects.nonNull(overheadconsRequestBean.getOhdhconno()) ? overheadconsRequestBean.getOhdhconno().trim() : overheadconsEntity.getOhdhConno());
		overheadconsEntity.setOhdhDepositeamt(Objects.nonNull(overheadconsRequestBean.getOhdhdepositeamt()) ? overheadconsRequestBean.getOhdhdepositeamt() : overheadconsEntity.getOhdhDepositeamt());
		overheadconsEntity.setOhdhFlatnum(Objects.nonNull(overheadconsRequestBean.getOhdhflatnum()) ? overheadconsRequestBean.getOhdhflatnum().trim() : overheadconsEntity.getOhdhFlatnum());
		overheadconsEntity.setOhdhLoad(Objects.nonNull(overheadconsRequestBean.getOhdhload()) ? overheadconsRequestBean.getOhdhload().trim() : overheadconsEntity.getOhdhLoad());
		overheadconsEntity.setOhdhLocation(Objects.nonNull(overheadconsRequestBean.getOhdhlocation()) ? overheadconsRequestBean.getOhdhlocation().trim() : overheadconsEntity.getOhdhLocation());
		overheadconsEntity.setOhdhPaycoy(Objects.nonNull(overheadconsRequestBean.getOhdhpaycoy()) ? overheadconsRequestBean.getOhdhpaycoy().trim() : overheadconsEntity.getOhdhPaycoy());
		//overheadconsEntity.setOhdhSite("MUM");
		overheadconsEntity.setOhdhSite(GenericAuditContextHolder.getContext().getSite());
		overheadconsEntity.setOhdhStatus(Objects.nonNull(overheadconsRequestBean.getOhdhstatus()) ? overheadconsRequestBean.getOhdhstatus().trim() : overheadconsEntity.getOhdhStatus());
		overheadconsEntity.setOhdhTmpmeteryn(Objects.nonNull(overheadconsRequestBean.getOhdhtmpmeteryn()) ? overheadconsRequestBean.getOhdhtmpmeteryn().trim() : overheadconsEntity.getOhdhTmpmeteryn());
		overheadconsEntity.setOhdhToday(LocalDateTime.now());
		overheadconsEntity.setOhdhUserid(GenericAuditContextHolder.getContext().getUserid());
		//overheadconsEntity.setOhdhUserid("SANDY");
		overheadconsEntity.setOhdhVacantflatyn(Objects.nonNull(overheadconsRequestBean.getOhdhvacantflatyn()) ? overheadconsRequestBean.getOhdhvacantflatyn().trim() : overheadconsEntity.getOhdhVacantflatyn());
		return overheadconsEntity;
		
	};
}
