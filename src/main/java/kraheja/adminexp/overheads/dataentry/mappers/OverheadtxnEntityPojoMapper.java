package kraheja.adminexp.overheads.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadtxnRequestBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadtxnResponseBean;
import kraheja.adminexp.overheads.dataentry.bean.response.OverheadtxnResponseBean.OverheadtxnResponseBeanBuilder;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.entity.Overheadtxn;
import kraheja.adminexp.overheads.dataentry.entity.OverheadtxnCK;
import kraheja.commons.utils.CommonConstraints;

public interface OverheadtxnEntityPojoMapper {
	@SuppressWarnings("unchecked")

	public static Function<Object[], List<OverheadtxnResponseBean>> fetchOverheadtxnConnocodeEntityPojoMapper = objectArray -> {
		OverheadtxnResponseBeanBuilder overheadtxnBeanBuilder = OverheadtxnResponseBean.builder();

		List<Overheadtxn> overheadtxnEntityList = (List<Overheadtxn>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);

		return overheadtxnEntityList.stream().map(overheadtxnEntity -> {
			overheadtxnBeanBuilder.connocode(overheadtxnEntity.getOverheadtxnCK().getOhddConnocode())
			.billperiod(overheadtxnEntity.getOverheadtxnCK().getOhddBillperiod())
			.supplementarybill(overheadtxnEntity.getOverheadtxnCK().getOhddSupplementarybill())
			.advance(overheadtxnEntity.getOhddAdvance())
			.bilentdate(Objects.nonNull(overheadtxnEntity.getOhddBilentdate()) ? overheadtxnEntity.getOhddBilentdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.billamt(overheadtxnEntity.getOhddBillamt())
			.billno(overheadtxnEntity.getOhddBillno())
			.billtranser(overheadtxnEntity.getOhddBilltranser())
			.cgst(overheadtxnEntity.getOhddCgst())
			.cgstper(overheadtxnEntity.getOhddCgstper())
			.cumamt(overheadtxnEntity.getOhddCumamt())
			.custbilldate(Objects.nonNull(overheadtxnEntity.getOhddCustbilldate()) ? overheadtxnEntity.getOhddCustbilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.fromdate(Objects.nonNull(overheadtxnEntity.getOhddFromdate()) ? overheadtxnEntity.getOhddFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.hsncode(overheadtxnEntity.getOhddHsncode())
			.igst(overheadtxnEntity.getOhddIgst())
			.igstper(overheadtxnEntity.getOhddIgstper())
			.intrest(overheadtxnEntity.getOhddIntrest())
			.noofprint(overheadtxnEntity.getOhddNoofprint())
			.payamt(overheadtxnEntity.getOhddPayamt())
			.paydate(Objects.nonNull(overheadtxnEntity.getOhddPaydate()) ? overheadtxnEntity.getOhddPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.payref(overheadtxnEntity.getOhddPayref())
			.printflag(overheadtxnEntity.getOhddPrintflag())
			.prvactpay(overheadtxnEntity.getOhddPrvactpay())
			.prvadvamt(overheadtxnEntity.getOhddPrvadvamt())
			.remarks(overheadtxnEntity.getOhddRemarks())
			.sgst(overheadtxnEntity.getOhddSgst())
			.sgstper(overheadtxnEntity.getOhddSgstper())
			.site(overheadtxnEntity.getOhddSite())
			.todate(Objects.nonNull(overheadtxnEntity.getOhddTodate()) ? overheadtxnEntity.getOhddTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.today(overheadtxnEntity.getOhddToday())
			.transer(overheadtxnEntity.getOhddTranser())
			.unitno(overheadtxnEntity.getOhddUnitno())
			.userid(overheadtxnEntity.getOhddUserid())
			;
			return overheadtxnBeanBuilder.build();
		}).collect(Collectors.toList());
	};


	@SuppressWarnings("unchecked")
	public static Function<Object[], List<OverheadtxnResponseBean>> fetchOverheadtxnEntityPojoMapper = objectArray -> {
		OverheadtxnResponseBeanBuilder overheadtxnBeanBuilder = OverheadtxnResponseBean.builder();

		List<Overheadtxn> overheadtxnEntityList = (List<Overheadtxn>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);

		Overheadcons overheadconsEntity = (Overheadcons) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);		

		List<Overheadmeter> overheadmeterEntityList  =(List<Overheadmeter>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
		? objectArray[CommonConstraints.INSTANCE.TWO]
				: null); 
		
		return overheadtxnEntityList.stream().map(overheadtxnEntity -> {
			overheadtxnBeanBuilder.connocode(overheadtxnEntity.getOverheadtxnCK().getOhddConnocode())
			.billperiod(overheadtxnEntity.getOverheadtxnCK().getOhddBillperiod())
			.supplementarybill(overheadtxnEntity.getOverheadtxnCK().getOhddSupplementarybill())
			.advance(overheadtxnEntity.getOhddAdvance())
			.bilentdate(Objects.nonNull(overheadtxnEntity.getOhddBilentdate()) ? overheadtxnEntity.getOhddBilentdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.billamt(overheadtxnEntity.getOhddBillamt())
			.billno(overheadtxnEntity.getOhddBillno())
			.billtranser(overheadtxnEntity.getOhddBilltranser())
			.cgst(overheadtxnEntity.getOhddCgst())
			.cgstper(overheadtxnEntity.getOhddCgstper())
			.cumamt(overheadtxnEntity.getOhddCumamt())
			.custbilldate(Objects.nonNull(overheadtxnEntity.getOhddCustbilldate()) ? overheadtxnEntity.getOhddCustbilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.fromdate(Objects.nonNull(overheadtxnEntity.getOhddFromdate()) ? overheadtxnEntity.getOhddFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.hsncode(overheadtxnEntity.getOhddHsncode())
			.igst(overheadtxnEntity.getOhddIgst())
			.igstper(overheadtxnEntity.getOhddIgstper())
			.intrest(overheadtxnEntity.getOhddIntrest())
			.noofprint(overheadtxnEntity.getOhddNoofprint())
			.payamt(overheadtxnEntity.getOhddPayamt())
			.paydate(Objects.nonNull(overheadtxnEntity.getOhddPaydate()) ? overheadtxnEntity.getOhddPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.payref(overheadtxnEntity.getOhddPayref())
			.printflag(overheadtxnEntity.getOhddPrintflag())
			.prvactpay(overheadtxnEntity.getOhddPrvactpay())
			.prvadvamt(overheadtxnEntity.getOhddPrvadvamt())
			.remarks(overheadtxnEntity.getOhddRemarks())
			.sgst(overheadtxnEntity.getOhddSgst())
			.sgstper(overheadtxnEntity.getOhddSgstper())
			.site(overheadtxnEntity.getOhddSite())
			.todate(Objects.nonNull(overheadtxnEntity.getOhddTodate()) ? overheadtxnEntity.getOhddTodate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.today(overheadtxnEntity.getOhddToday())
			.transer(overheadtxnEntity.getOhddTranser())
			.unitno(overheadtxnEntity.getOhddUnitno())
			.userid(overheadtxnEntity.getOhddUserid())
			;

			if(Objects.nonNull(overheadconsEntity)&& CollectionUtils.isNotEmpty(overheadmeterEntityList)) {
				overheadtxnBeanBuilder
				.overheadconsResponseBean(OverheadconsEntityPojoMapper
						.fetchOverheadconsEntityPojoMapper.apply(new Object[] {overheadconsEntity,overheadmeterEntityList}));
			}
			
//			if(CollectionUtils.isNotEmpty(overheadmeterEntityList)) 
//			{
//						overheadtxnBeanBuilder.overheadmeterResponseBeanList(OverheadmeterEntityPojoMapper
//						.fetchOverheadmeterEntityPojoMapper.apply(new Object[] {overheadmeterEntityList}));
//			}
			
			return overheadtxnBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Overheadtxn> addOverheadtxnEntityPojoMapper = objectArray -> {
		OverheadtxnRequestBean overheadtxnRequestBean = (OverheadtxnRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
						: null);

		Overheadtxn.OverheadtxnBuilder overheadtxnbuilder = Overheadtxn.builder();

		overheadtxnbuilder
		.overheadtxnCK(OverheadtxnCK.builder()
				.ohddConnocode(overheadtxnRequestBean.getConnocode())
				.ohddBillperiod(overheadtxnRequestBean.getBillperiod())
				.ohddSupplementarybill(overheadtxnRequestBean.getSupplementarybill())
				.build())
		.ohddAdvance(overheadtxnRequestBean.getAdvance())
		.ohddBilentdate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate()) ? LocalDate.parse(overheadtxnRequestBean.getBilentdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null)
		.ohddBillamt(overheadtxnRequestBean.getBillamt())
		.ohddBillno(overheadtxnRequestBean.getBillno())
		.ohddBilltranser(overheadtxnRequestBean.getBilltranser())
		.ohddCgst(overheadtxnRequestBean.getCgst())
		.ohddCgstper(overheadtxnRequestBean.getCgstper())
		.ohddCumamt(overheadtxnRequestBean.getCumamt())
		.ohddCustbilldate(StringUtils.isNotBlank(overheadtxnRequestBean.getCustbilldate()) ? LocalDate.parse(overheadtxnRequestBean.getCustbilldate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
		.ohddFromdate(StringUtils.isNotBlank(overheadtxnRequestBean.getFromdate()) ? LocalDate.parse(overheadtxnRequestBean.getFromdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
		.ohddHsncode(overheadtxnRequestBean.getHsncode())
		.ohddIgst(overheadtxnRequestBean.getIgst())
		.ohddIgstper(overheadtxnRequestBean.getIgstper())
		.ohddIntrest(overheadtxnRequestBean.getIntrest())
		.ohddNoofprint(overheadtxnRequestBean.getNoofprint())
		.ohddPayamt(overheadtxnRequestBean.getPayamt())
		.ohddPaydate(StringUtils.isNotBlank(overheadtxnRequestBean.getPaydate()) ? LocalDate.parse(overheadtxnRequestBean.getPaydate().toString(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
		.ohddPayref(overheadtxnRequestBean.getPayref())
		.ohddPrintflag(overheadtxnRequestBean.getPrintflag())
		.ohddPrvactpay(overheadtxnRequestBean.getPrvactpay())
		.ohddPrvadvamt(overheadtxnRequestBean.getPrvadvamt())
		.ohddRemarks(overheadtxnRequestBean.getRemarks())
		.ohddSgst(overheadtxnRequestBean.getSgst())
		.ohddSgstper(overheadtxnRequestBean.getSgstper())
		.ohddSite(overheadtxnRequestBean.getSite())
		.ohddTodate(StringUtils.isNotBlank(overheadtxnRequestBean.getTodate()) ? LocalDate.parse(overheadtxnRequestBean.getTodate().toString(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
		.ohddToday(LocalDateTime.now())
		.ohddTranser(overheadtxnRequestBean.getTranser())
		.ohddUnitno(overheadtxnRequestBean.getUnitno())
		.ohddUserid(overheadtxnRequestBean.getUserid());

		return overheadtxnbuilder.build();
	};

	public static BiFunction<Overheadtxn, OverheadtxnRequestBean, Overheadtxn> updateOverheadtxnEntityPojoMapper = (overheadtxnEntity, overheadtxnRequestBean) -> {
		overheadtxnEntity.getOverheadtxnCK().setOhddConnocode(StringUtils.isNotBlank(overheadtxnRequestBean.getConnocode()) ? overheadtxnRequestBean.getConnocode().trim() : overheadtxnEntity.getOverheadtxnCK().getOhddConnocode());
		overheadtxnEntity.getOverheadtxnCK().setOhddBillperiod(StringUtils.isNotBlank(overheadtxnRequestBean.getBillperiod()) ? overheadtxnRequestBean.getBillperiod().trim() : overheadtxnEntity.getOverheadtxnCK().getOhddBillperiod());
		overheadtxnEntity.getOverheadtxnCK().setOhddSupplementarybill(StringUtils.isNotBlank(overheadtxnRequestBean.getSupplementarybill()) ? overheadtxnRequestBean.getSupplementarybill().trim() : overheadtxnEntity.getOverheadtxnCK().getOhddSupplementarybill());
		overheadtxnEntity.setOhddAdvance(Objects.nonNull(overheadtxnRequestBean.getAdvance()) ? overheadtxnRequestBean.getAdvance() : overheadtxnEntity.getOhddAdvance());
		overheadtxnEntity.setOhddBilentdate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate()) ? LocalDate.parse(overheadtxnRequestBean.getBilentdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null);
		overheadtxnEntity.setOhddBilentdate(StringUtils.isNotBlank(overheadtxnRequestBean.getBilentdate()) ? LocalDate.parse(overheadtxnRequestBean.getBilentdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null);
		overheadtxnEntity.setOhddBillamt(Objects.nonNull(overheadtxnRequestBean.getBillamt()) ? overheadtxnRequestBean.getBillamt() : overheadtxnEntity.getOhddBillamt());
		overheadtxnEntity.setOhddBillno(StringUtils.isNotBlank(overheadtxnRequestBean.getBillno()) ? overheadtxnRequestBean.getBillno().trim() : overheadtxnEntity.getOhddBillno());
		overheadtxnEntity.setOhddBilltranser(StringUtils.isNotBlank(overheadtxnRequestBean.getBilltranser()) ? overheadtxnRequestBean.getBilltranser().trim() : overheadtxnEntity.getOhddBilltranser());
		overheadtxnEntity.setOhddCgst(Objects.nonNull(overheadtxnRequestBean.getCgst()) ? overheadtxnRequestBean.getCgst() : overheadtxnEntity.getOhddCgst());
		overheadtxnEntity.setOhddCgstper(Objects.nonNull(overheadtxnRequestBean.getCgstper()) ? overheadtxnRequestBean.getCgstper() : overheadtxnEntity.getOhddCgstper());
		overheadtxnEntity.setOhddCumamt(Objects.nonNull(overheadtxnRequestBean.getCumamt()) ? overheadtxnRequestBean.getCumamt() : overheadtxnEntity.getOhddCumamt());
		overheadtxnEntity.setOhddCustbilldate(Objects.nonNull(overheadtxnRequestBean.getCustbilldate()) ? LocalDate.parse(overheadtxnRequestBean.getCustbilldate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : overheadtxnEntity.getOhddCustbilldate());
		overheadtxnEntity.setOhddFromdate(StringUtils.isNotBlank(overheadtxnRequestBean.getFromdate()) ? LocalDate.parse(overheadtxnRequestBean.getFromdate(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);
		overheadtxnEntity.setOhddTodate(StringUtils.isNotBlank(overheadtxnRequestBean.getTodate()) ? LocalDate.parse(overheadtxnRequestBean.getTodate().toString(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);
		overheadtxnEntity.setOhddHsncode(StringUtils.isNotBlank(overheadtxnRequestBean.getHsncode()) ? overheadtxnRequestBean.getHsncode().trim() : overheadtxnEntity.getOhddHsncode());
		overheadtxnEntity.setOhddIgst(Objects.nonNull(overheadtxnRequestBean.getIgst()) ? overheadtxnRequestBean.getIgst() : overheadtxnEntity.getOhddIgst());
		overheadtxnEntity.setOhddIgstper(Objects.nonNull(overheadtxnRequestBean.getIgstper()) ? overheadtxnRequestBean.getIgstper() : overheadtxnEntity.getOhddIgstper());
		overheadtxnEntity.setOhddIntrest(Objects.nonNull(overheadtxnRequestBean.getIntrest()) ? overheadtxnRequestBean.getIntrest() : overheadtxnEntity.getOhddIntrest());
		overheadtxnEntity.setOhddNoofprint(Objects.nonNull(overheadtxnRequestBean.getNoofprint()) ? overheadtxnRequestBean.getNoofprint() : overheadtxnEntity.getOhddNoofprint());
		overheadtxnEntity.setOhddPayamt(Objects.nonNull(overheadtxnRequestBean.getPayamt()) ? overheadtxnRequestBean.getPayamt() : overheadtxnEntity.getOhddPayamt());
		overheadtxnEntity.setOhddPaydate(StringUtils.isNotBlank(overheadtxnRequestBean.getPaydate()) ? LocalDate.parse(overheadtxnRequestBean.getPaydate().toString(),CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null);
		overheadtxnEntity.setOhddPayref(StringUtils.isNotBlank(overheadtxnRequestBean.getPayref()) ? overheadtxnRequestBean.getPayref().trim() : overheadtxnEntity.getOhddPayref());
		overheadtxnEntity.setOhddPrintflag(StringUtils.isNotBlank(overheadtxnRequestBean.getPrintflag()) ? overheadtxnRequestBean.getPrintflag().trim() : overheadtxnEntity.getOhddPrintflag());
		overheadtxnEntity.setOhddPrvactpay(Objects.nonNull(overheadtxnRequestBean.getPrvactpay()) ? overheadtxnRequestBean.getPrvactpay() : overheadtxnEntity.getOhddPrvactpay());
		overheadtxnEntity.setOhddPrvadvamt(Objects.nonNull(overheadtxnRequestBean.getPrvadvamt()) ? overheadtxnRequestBean.getPrvadvamt() : overheadtxnEntity.getOhddPrvadvamt());
		overheadtxnEntity.setOhddRemarks(StringUtils.isNotBlank(overheadtxnRequestBean.getRemarks()) ? overheadtxnRequestBean.getRemarks().trim() : overheadtxnEntity.getOhddRemarks());
		overheadtxnEntity.setOhddSgst(Objects.nonNull(overheadtxnRequestBean.getSgst()) ? overheadtxnRequestBean.getSgst() : overheadtxnEntity.getOhddSgst());
		overheadtxnEntity.setOhddSgstper(Objects.nonNull(overheadtxnRequestBean.getSgstper()) ? overheadtxnRequestBean.getSgstper() : overheadtxnEntity.getOhddSgstper());
		overheadtxnEntity.setOhddSite(StringUtils.isNotBlank(overheadtxnRequestBean.getSite()) ? overheadtxnRequestBean.getSite().trim() : overheadtxnEntity.getOhddSite());
		overheadtxnEntity.setOhddToday(LocalDateTime.now());
		overheadtxnEntity.setOhddTranser(StringUtils.isNotBlank(overheadtxnRequestBean.getTranser()) ? overheadtxnRequestBean.getTranser().trim() : overheadtxnEntity.getOhddTranser());
		overheadtxnEntity.setOhddUnitno(StringUtils.isNotBlank(overheadtxnRequestBean.getUnitno()) ? overheadtxnRequestBean.getUnitno().trim() : overheadtxnEntity.getOhddUnitno());
		overheadtxnEntity.setOhddUserid(StringUtils.isNotBlank(overheadtxnRequestBean.getUserid()) ? overheadtxnRequestBean.getUserid().trim() : overheadtxnEntity.getOhddUserid());

		return overheadtxnEntity;
	};


}
