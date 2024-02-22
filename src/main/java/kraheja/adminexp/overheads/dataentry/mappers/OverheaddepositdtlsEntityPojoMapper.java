package kraheja.adminexp.overheads.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheaddepositdtlsRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheaddepositdtlsResponseBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheaddepositdtlsResponseBean.OverheaddepositdtlsResponseBeanBuilder;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.Overheaddepositdtls;
import kraheja.adminexp.overheads.dataentry.entity.OverheaddepositdtlsCK;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.commons.utils.CommonConstraints;

public interface OverheaddepositdtlsEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<OverheaddepositdtlsResponseBean>> fetchOverheaddepositdtlsEntityPojoMapper = objectArray -> {
		OverheaddepositdtlsResponseBeanBuilder overheaddepositdtlsBeanBuilder = OverheaddepositdtlsResponseBean
				.builder();
		
		List<Overheaddepositdtls> overheaddepositdtlsEntityList = (List<Overheaddepositdtls>) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		
		Overheadcons overheadconsEntity = (Overheadcons) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);
		
		List<Overheadmeter> overheadmeterEntityList  =(List<Overheadmeter>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
						: null); 
		
		return overheaddepositdtlsEntityList.stream().map(overheaddepositdtlsEntity -> {
			overheaddepositdtlsBeanBuilder
					.connocode(overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdeConnocode())
					.period(overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdePeriod())
					.adddeduction(overheaddepositdtlsEntity.getOhdeAdddeduction())
					.billtype(overheaddepositdtlsEntity.getOhdeBilltype())
					.bldgcode(overheaddepositdtlsEntity.getOhdeBldgcode())
					.conno(overheaddepositdtlsEntity.getOhdeConno()).coycode(overheaddepositdtlsEntity.getOhdeCoycode())
					.depositeamt(overheaddepositdtlsEntity.getOhdeDepositeamt())
					.remarks(overheaddepositdtlsEntity.getOhdeRemarks()).site(overheaddepositdtlsEntity.getOhdeSite())
					.today(overheaddepositdtlsEntity.getOhdeToday()).transer(overheaddepositdtlsEntity.getOhdeTranser())
					.userid(overheaddepositdtlsEntity.getOhdeUserid());
			
			if(Objects.nonNull(overheadconsEntity)) {
				overheaddepositdtlsBeanBuilder
				.overheadconsResponseBean(OverheadconsEntityPojoMapper
						.fetchOverheadconsEntityPojoMapper.apply(new Object[] {overheadconsEntity,overheadmeterEntityList}));
			}
			return overheaddepositdtlsBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Overheaddepositdtls> addOverheaddepositdtlsEntityPojoMapper = objectArray -> {
		OverheaddepositdtlsRequestBean overheaddepositdtlsRequestBean = (OverheaddepositdtlsRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
				: null);

		Overheaddepositdtls.OverheaddepositdtlsBuilder overheaddepositdtlsbuilder = Overheaddepositdtls.builder();

		overheaddepositdtlsbuilder
				.overheaddepositdtlsCK(
						OverheaddepositdtlsCK.builder().ohdeConnocode(overheaddepositdtlsRequestBean.getConnocode())
								.ohdePeriod(overheaddepositdtlsRequestBean.getPeriod()).build())
				.ohdeAdddeduction(overheaddepositdtlsRequestBean.getAdddeduction())
				.ohdeBilltype(overheaddepositdtlsRequestBean.getBilltype())
				.ohdeBldgcode(overheaddepositdtlsRequestBean.getBldgcode())
				.ohdeConno(overheaddepositdtlsRequestBean.getConno())
				.ohdeCoycode(overheaddepositdtlsRequestBean.getCoycode())
				.ohdeDepositeamt(overheaddepositdtlsRequestBean.getDepositeamt())
				.ohdeRemarks(overheaddepositdtlsRequestBean.getRemarks()).ohdeSite(site).ohdeToday(LocalDateTime.now())
				.ohdeTranser(overheaddepositdtlsRequestBean.getTranser())
				.ohdeUserid(overheaddepositdtlsRequestBean.getUserid());

		return overheaddepositdtlsbuilder.build();
	};

	public static BiFunction<Overheaddepositdtls, OverheaddepositdtlsRequestBean, Overheaddepositdtls> updateOverheaddepositdtlsEntityPojoMapper = (
			overheaddepositdtlsEntity, overheaddepositdtlsRequestBean) -> {
		overheaddepositdtlsEntity.getOverheaddepositdtlsCK()
				.setOhdeConnocode(Objects.nonNull(overheaddepositdtlsRequestBean.getConnocode())
						? overheaddepositdtlsRequestBean.getConnocode().trim()
						: overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdeConnocode());
		overheaddepositdtlsEntity.getOverheaddepositdtlsCK()
				.setOhdePeriod(Objects.nonNull(overheaddepositdtlsRequestBean.getPeriod())
						? overheaddepositdtlsRequestBean.getPeriod().trim()
						: overheaddepositdtlsEntity.getOverheaddepositdtlsCK().getOhdePeriod());
		overheaddepositdtlsEntity.setOhdeAdddeduction(Objects.nonNull(overheaddepositdtlsRequestBean.getAdddeduction())
				? overheaddepositdtlsRequestBean.getAdddeduction().trim()
				: overheaddepositdtlsEntity.getOhdeAdddeduction());
		overheaddepositdtlsEntity.setOhdeBilltype(Objects.nonNull(overheaddepositdtlsRequestBean.getBilltype())
				? overheaddepositdtlsRequestBean.getBilltype().trim()
				: overheaddepositdtlsEntity.getOhdeBilltype());
		overheaddepositdtlsEntity.setOhdeBldgcode(Objects.nonNull(overheaddepositdtlsRequestBean.getBldgcode())
				? overheaddepositdtlsRequestBean.getBldgcode().trim()
				: overheaddepositdtlsEntity.getOhdeBldgcode());
		overheaddepositdtlsEntity.setOhdeConno(Objects.nonNull(overheaddepositdtlsRequestBean.getConno())
				? overheaddepositdtlsRequestBean.getConno().trim()
				: overheaddepositdtlsEntity.getOhdeConno());
		overheaddepositdtlsEntity.setOhdeCoycode(Objects.nonNull(overheaddepositdtlsRequestBean.getCoycode())
				? overheaddepositdtlsRequestBean.getCoycode().trim()
				: overheaddepositdtlsEntity.getOhdeCoycode());
		overheaddepositdtlsEntity.setOhdeDepositeamt(Objects.nonNull(overheaddepositdtlsRequestBean.getDepositeamt())
				? overheaddepositdtlsRequestBean.getDepositeamt()
				: overheaddepositdtlsEntity.getOhdeDepositeamt());
		overheaddepositdtlsEntity.setOhdeRemarks(Objects.nonNull(overheaddepositdtlsRequestBean.getRemarks())
				? overheaddepositdtlsRequestBean.getRemarks().trim()
				: overheaddepositdtlsEntity.getOhdeRemarks());
		overheaddepositdtlsEntity.setOhdeSite(Objects.nonNull(overheaddepositdtlsRequestBean.getSite())
				? overheaddepositdtlsRequestBean.getSite().trim()
				: overheaddepositdtlsEntity.getOhdeSite());
		overheaddepositdtlsEntity.setOhdeToday(
				Objects.nonNull(overheaddepositdtlsRequestBean.getToday()) ? overheaddepositdtlsRequestBean.getToday()
						: overheaddepositdtlsEntity.getOhdeToday());
		overheaddepositdtlsEntity.setOhdeTranser(Objects.nonNull(overheaddepositdtlsRequestBean.getTranser())
				? overheaddepositdtlsRequestBean.getTranser().trim()
				: overheaddepositdtlsEntity.getOhdeTranser());
		overheaddepositdtlsEntity.setOhdeUserid(Objects.nonNull(overheaddepositdtlsRequestBean.getUserid())
				? overheaddepositdtlsRequestBean.getUserid().trim()
				: overheaddepositdtlsEntity.getOhdeUserid());

		return overheaddepositdtlsEntity;
	};

}
