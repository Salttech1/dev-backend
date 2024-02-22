package kraheja.adminexp.billing.dataentry.adminBill.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.billing.dataentry.adminBill.bean.request.AdmbilldRequestBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdmbilldResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdmbilldResponseBean.AdmbilldResponseBeanBuilder;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.AdmbilldCK;


import kraheja.commons.filter.GenericAuditContextHolder;

public interface AdmbilldEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<AdmbilldResponseBean>> fetchAdmbilldEntityPojoMapper = objectArray -> {
		AdmbilldResponseBeanBuilder admbilldBeanBuilder = AdmbilldResponseBean.builder();
		List<Admbilld> admbilldEntityList = (List<Admbilld>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return admbilldEntityList.stream().map(admbilldEntity -> {
			admbilldBeanBuilder.ser(admbilldEntity.getAdmbilldCK().getAdbldSer())
					.lineno(admbilldEntity.getAdmbilldCK().getAdbldLineno())
					.amount(admbilldEntity.getAdbldAmount())
					.cgstamt(admbilldEntity.getAdbldCgstamt())
					.cgstperc(admbilldEntity.getAdbldCgstperc())
					.dbamt(admbilldEntity.getAdbldDbamt())
					.dbqty(admbilldEntity.getAdbldDbqty())
					.discountamt(admbilldEntity.getAdbldDiscountamt())
					.hsnsaccode(admbilldEntity.getAdbldHsnsaccode())
					.igstamt(admbilldEntity.getAdbldIgstamt())
					.igstperc(admbilldEntity.getAdbldIgstperc())
					.itemdesc(admbilldEntity.getAdbldItemdesc())
					.origsite(admbilldEntity.getAdbldOrigsite())
					.quantity(admbilldEntity.getAdbldQuantity())
					.rate(admbilldEntity.getAdbldRate())
					.sgstamt(admbilldEntity.getAdbldSgstamt())
					.sgstperc(admbilldEntity.getAdbldSgstperc())
					.site(admbilldEntity.getAdbldSite())
					.taxableamt(admbilldEntity.getAdbldTaxableamt())
					.today(admbilldEntity.getAdbldToday())
					.ugstamt(admbilldEntity.getAdbldUgstamt())
					.ugstperc(admbilldEntity.getAdbldUgstperc())
					.uom(admbilldEntity.getAdbldUom())
					.userid(admbilldEntity.getAdbldUserid())
;
			return admbilldBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<List<AdmbilldRequestBean>, List<Admbilld>> addAdmbilldEntityPojoMapper(String ser) {
	    return admbilldRequestBeanList ->
	        admbilldRequestBeanList.stream().map(admbilldRequestBean ->
	            Admbilld.builder()
	                .admbilldCK(AdmbilldCK.builder()
	                    .adbldSer(ser)
	                    .adbldLineno(admbilldRequestBean.getLineno())
	                    .build())
	                .adbldAmount(admbilldRequestBean.getAmount())
	                .adbldCgstamt(admbilldRequestBean.getCgstamt())
	                .adbldCgstperc(admbilldRequestBean.getCgstperc())
	                .adbldDbamt(admbilldRequestBean.getDbamt())
	                .adbldDbqty(admbilldRequestBean.getDbqty())
	                .adbldDiscountamt(admbilldRequestBean.getDiscountamt())
	                .adbldHsnsaccode(admbilldRequestBean.getHsnsaccode())
	                .adbldIgstamt(admbilldRequestBean.getIgstamt())
	                .adbldIgstperc(admbilldRequestBean.getIgstperc())
	                .adbldItemdesc(admbilldRequestBean.getItemdesc())
	                .adbldOrigsite(GenericAuditContextHolder.getContext().getSite())
	                .adbldQuantity(admbilldRequestBean.getQuantity())
	                .adbldRate(admbilldRequestBean.getRate())
	                .adbldSgstamt(admbilldRequestBean.getSgstamt())
	                .adbldSgstperc(admbilldRequestBean.getSgstperc())
	                .adbldSite(GenericAuditContextHolder.getContext().getSite())
	                .adbldTaxableamt(admbilldRequestBean.getTaxableamt())
	                .adbldToday(LocalDateTime.now())
	                .adbldUgstamt(admbilldRequestBean.getUgstamt())
	                .adbldUgstperc(admbilldRequestBean.getUgstperc())
	                .adbldUom(admbilldRequestBean.getUom())
	                .adbldUserid(GenericAuditContextHolder.getContext().getUserid())
	                .build()
	        ).collect(Collectors.toList());
	}



	public static BiFunction<Admbilld, AdmbilldRequestBean, Admbilld> updateAdmbilldEntityPojoMapper = (admbilldEntity, admbilldRequestBean) -> {
		admbilldEntity.getAdmbilldCK().setAdbldSer(Objects.nonNull(admbilldRequestBean.getSer()) ? admbilldRequestBean.getSer().trim() : admbilldEntity.getAdmbilldCK().getAdbldSer());
		admbilldEntity.getAdmbilldCK().setAdbldLineno(Objects.nonNull(admbilldRequestBean.getLineno()) ? admbilldRequestBean.getLineno() : admbilldEntity.getAdmbilldCK().getAdbldLineno());
		admbilldEntity.setAdbldAmount(Objects.nonNull(admbilldRequestBean.getAmount()) ? admbilldRequestBean.getAmount() : admbilldEntity.getAdbldAmount());
		admbilldEntity.setAdbldCgstamt(Objects.nonNull(admbilldRequestBean.getCgstamt()) ? admbilldRequestBean.getCgstamt() : admbilldEntity.getAdbldCgstamt());
		admbilldEntity.setAdbldCgstperc(Objects.nonNull(admbilldRequestBean.getCgstperc()) ? admbilldRequestBean.getCgstperc() : admbilldEntity.getAdbldCgstperc());
		admbilldEntity.setAdbldDbamt(Objects.nonNull(admbilldRequestBean.getDbamt()) ? admbilldRequestBean.getDbamt() : admbilldEntity.getAdbldDbamt());
		admbilldEntity.setAdbldDbqty(Objects.nonNull(admbilldRequestBean.getDbqty()) ? admbilldRequestBean.getDbqty() : admbilldEntity.getAdbldDbqty());
		admbilldEntity.setAdbldDiscountamt(Objects.nonNull(admbilldRequestBean.getDiscountamt()) ? admbilldRequestBean.getDiscountamt() : admbilldEntity.getAdbldDiscountamt());
		admbilldEntity.setAdbldHsnsaccode(Objects.nonNull(admbilldRequestBean.getHsnsaccode()) ? admbilldRequestBean.getHsnsaccode().trim() : admbilldEntity.getAdbldHsnsaccode());
		admbilldEntity.setAdbldIgstamt(Objects.nonNull(admbilldRequestBean.getIgstamt()) ? admbilldRequestBean.getIgstamt() : admbilldEntity.getAdbldIgstamt());
		admbilldEntity.setAdbldIgstperc(Objects.nonNull(admbilldRequestBean.getIgstperc()) ? admbilldRequestBean.getIgstperc() : admbilldEntity.getAdbldIgstperc());
		admbilldEntity.setAdbldItemdesc(Objects.nonNull(admbilldRequestBean.getItemdesc()) ? admbilldRequestBean.getItemdesc().trim() : admbilldEntity.getAdbldItemdesc());
		admbilldEntity.setAdbldOrigsite(Objects.nonNull(admbilldRequestBean.getOrigsite()) ? admbilldRequestBean.getOrigsite().trim() : admbilldEntity.getAdbldOrigsite());
		admbilldEntity.setAdbldQuantity(Objects.nonNull(admbilldRequestBean.getQuantity()) ? admbilldRequestBean.getQuantity() : admbilldEntity.getAdbldQuantity());
		admbilldEntity.setAdbldRate(Objects.nonNull(admbilldRequestBean.getRate()) ? admbilldRequestBean.getRate() : admbilldEntity.getAdbldRate());
		admbilldEntity.setAdbldSgstamt(Objects.nonNull(admbilldRequestBean.getSgstamt()) ? admbilldRequestBean.getSgstamt() : admbilldEntity.getAdbldSgstamt());
		admbilldEntity.setAdbldSgstperc(Objects.nonNull(admbilldRequestBean.getSgstperc()) ? admbilldRequestBean.getSgstperc() : admbilldEntity.getAdbldSgstperc());
		admbilldEntity.setAdbldSite(Objects.nonNull(admbilldRequestBean.getSite()) ? admbilldRequestBean.getSite().trim() : admbilldEntity.getAdbldSite());
		admbilldEntity.setAdbldTaxableamt(Objects.nonNull(admbilldRequestBean.getTaxableamt()) ? admbilldRequestBean.getTaxableamt() : admbilldEntity.getAdbldTaxableamt());
		admbilldEntity.setAdbldToday(Objects.nonNull(admbilldRequestBean.getToday()) ? admbilldRequestBean.getToday() : admbilldEntity.getAdbldToday());
		admbilldEntity.setAdbldUgstamt(Objects.nonNull(admbilldRequestBean.getUgstamt()) ? admbilldRequestBean.getUgstamt() : admbilldEntity.getAdbldUgstamt());
		admbilldEntity.setAdbldUgstperc(Objects.nonNull(admbilldRequestBean.getUgstperc()) ? admbilldRequestBean.getUgstperc() : admbilldEntity.getAdbldUgstperc());
		admbilldEntity.setAdbldUom(Objects.nonNull(admbilldRequestBean.getUom()) ? admbilldRequestBean.getUom().trim() : admbilldEntity.getAdbldUom());
		admbilldEntity.setAdbldUserid(Objects.nonNull(admbilldRequestBean.getUserid()) ? admbilldRequestBean.getUserid().trim() : admbilldEntity.getAdbldUserid());


		return admbilldEntity;
	};

}
