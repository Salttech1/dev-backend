package kraheja.purch.debitnotes.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.purch.bean.request.DbnotevatRequestBean;
import kraheja.purch.bean.response.DbnotevatResponseBean;
import kraheja.purch.bean.response.DbnotevatResponseBean.DbnotevatResponseBeanBuilder;
import kraheja.purch.entity.Dbnotevat;
import kraheja.purch.entity.DbnotevatCK;

public interface DbnotevatMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<DbnotevatResponseBean>> fetchDbnotevatEntityPojoMapper = objectArray -> {
		DbnotevatResponseBeanBuilder dbnotevatBeanBuilder = DbnotevatResponseBean.builder();
		List<Dbnotevat> dbnotevatEntityList = (List<Dbnotevat>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return dbnotevatEntityList.stream().map(dbnotevatEntity -> {
			dbnotevatBeanBuilder					.amount(dbnotevatEntity.getDbnvAmount())
					.cgstamt(dbnotevatEntity.getDbnvCgstamt())
					.cgstperc(dbnotevatEntity.getDbnvCgstperc())
					.discountamt(dbnotevatEntity.getDbnvDiscountamt())
					.fotoamt(dbnotevatEntity.getDbnvFotoamt())
					.hsncode(dbnotevatEntity.getDbnvHsncode())
					.hsndesc(dbnotevatEntity.getDbnvHsndesc())
					.igstamt(dbnotevatEntity.getDbnvIgstamt())
					.igstperc(dbnotevatEntity.getDbnvIgstperc())
					.itemcode(dbnotevatEntity.getDbnvItemcode())
					.matcode(dbnotevatEntity.getDbnvMatcode())
					.matgroup(dbnotevatEntity.getDbnvMatgroup())
					.origsite(dbnotevatEntity.getDbnvOrigsite())
					.quantity(dbnotevatEntity.getDbnvQuantity())
					.rate(dbnotevatEntity.getDbnvRate())
					.sgstamt(dbnotevatEntity.getDbnvSgstamt())
					.sgstperc(dbnotevatEntity.getDbnvSgstperc())
					.site(dbnotevatEntity.getDbnvSite())
					.taxableamt(dbnotevatEntity.getDbnvTaxableamt())
					.ugstamt(dbnotevatEntity.getDbnvUgstamt())
					.ugstperc(dbnotevatEntity.getDbnvUgstperc())
					.uom(dbnotevatEntity.getDbnvUom())
					.userid(dbnotevatEntity.getDbnvUserid())
					.vatamount(dbnotevatEntity.getDbnvVatamount())
					.vatpercent(dbnotevatEntity.getDbnvVatpercent())
;
			return dbnotevatBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Dbnotevat> addDbnotevatEntityPojoMapper = objectArray -> {
		DbnotevatRequestBean dbnotevatRequestBean = (DbnotevatRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		Dbnotevat.		DbnotevatBuilder dbnotevatbuilder = Dbnotevat.builder();

dbnotevatbuilder
			.dbnotevatCK(DbnotevatCK.builder()
					.dbnvSer(dbnotevatRequestBean.getSer())
					.dbnvLineno(dbnotevatRequestBean.getLineno())
		.build())
					.dbnvAmount(dbnotevatRequestBean.getAmount())
					.dbnvCgstamt(dbnotevatRequestBean.getCgstamt())
					.dbnvCgstperc(dbnotevatRequestBean.getCgstperc())
					.dbnvDiscountamt(dbnotevatRequestBean.getDiscountamt())
					.dbnvFotoamt(dbnotevatRequestBean.getFotoamt())
					.dbnvHsncode(dbnotevatRequestBean.getHsncode())
					.dbnvHsndesc(dbnotevatRequestBean.getHsndesc())
					.dbnvIgstamt(dbnotevatRequestBean.getIgstamt())
					.dbnvIgstperc(dbnotevatRequestBean.getIgstperc())
					.dbnvItemcode(dbnotevatRequestBean.getItemcode())
					.dbnvMatcode(dbnotevatRequestBean.getMatcode())
					.dbnvMatgroup(dbnotevatRequestBean.getMatgroup())
					.dbnvOrigsite(GenericAuditContextHolder.getContext().getSite())
					.dbnvQuantity(dbnotevatRequestBean.getQuantity())
					.dbnvRate(dbnotevatRequestBean.getRate())
					.dbnvSgstamt(dbnotevatRequestBean.getSgstamt())
					.dbnvSgstperc(dbnotevatRequestBean.getSgstperc())
					.dbnvSite(GenericAuditContextHolder.getContext().getSite())
					.dbnvTaxableamt(dbnotevatRequestBean.getTaxableamt())
					.dbnvToday(LocalDateTime.now())
					.dbnvUgstamt(dbnotevatRequestBean.getUgstamt())
					.dbnvUgstperc(dbnotevatRequestBean.getUgstperc())
					.dbnvUom(dbnotevatRequestBean.getUom())
					.dbnvUserid(GenericAuditContextHolder.getContext().getUserid())
					.dbnvVatamount(dbnotevatRequestBean.getVatamount())
					.dbnvVatpercent(dbnotevatRequestBean.getVatpercent())
		;

		return dbnotevatbuilder.build();
};

	public static BiFunction<Dbnotevat, DbnotevatRequestBean, Dbnotevat> updateDbnotevatEntityPojoMapper = (dbnotevatEntity, dbnotevatRequestBean) -> {
		dbnotevatEntity.setDbnvAmount(Objects.nonNull(dbnotevatRequestBean.getAmount()) ? dbnotevatRequestBean.getAmount() : dbnotevatEntity.getDbnvAmount());
		dbnotevatEntity.setDbnvCgstamt(Objects.nonNull(dbnotevatRequestBean.getCgstamt()) ? dbnotevatRequestBean.getCgstamt() : dbnotevatEntity.getDbnvCgstamt());
		dbnotevatEntity.setDbnvCgstperc(Objects.nonNull(dbnotevatRequestBean.getCgstperc()) ? dbnotevatRequestBean.getCgstperc() : dbnotevatEntity.getDbnvCgstperc());
		dbnotevatEntity.setDbnvDiscountamt(Objects.nonNull(dbnotevatRequestBean.getDiscountamt()) ? dbnotevatRequestBean.getDiscountamt() : dbnotevatEntity.getDbnvDiscountamt());
		dbnotevatEntity.setDbnvFotoamt(Objects.nonNull(dbnotevatRequestBean.getFotoamt()) ? dbnotevatRequestBean.getFotoamt() : dbnotevatEntity.getDbnvFotoamt());
		dbnotevatEntity.setDbnvHsncode(StringUtils.isNotBlank(dbnotevatRequestBean.getHsncode()) ? dbnotevatRequestBean.getHsncode().trim() : dbnotevatEntity.getDbnvHsncode());
		dbnotevatEntity.setDbnvHsndesc(StringUtils.isNotBlank(dbnotevatRequestBean.getHsndesc()) ? dbnotevatRequestBean.getHsndesc().trim() : dbnotevatEntity.getDbnvHsndesc());
		dbnotevatEntity.setDbnvIgstamt(Objects.nonNull(dbnotevatRequestBean.getIgstamt()) ? dbnotevatRequestBean.getIgstamt() : dbnotevatEntity.getDbnvIgstamt());
		dbnotevatEntity.setDbnvIgstperc(Objects.nonNull(dbnotevatRequestBean.getIgstperc()) ? dbnotevatRequestBean.getIgstperc() : dbnotevatEntity.getDbnvIgstperc());
		dbnotevatEntity.setDbnvItemcode(StringUtils.isNotBlank(dbnotevatRequestBean.getItemcode()) ? dbnotevatRequestBean.getItemcode().trim() : dbnotevatEntity.getDbnvItemcode());
		dbnotevatEntity.setDbnvMatcode(StringUtils.isNotBlank(dbnotevatRequestBean.getMatcode()) ? dbnotevatRequestBean.getMatcode().trim() : dbnotevatEntity.getDbnvMatcode());
		dbnotevatEntity.setDbnvMatgroup(StringUtils.isNotBlank(dbnotevatRequestBean.getMatgroup()) ? dbnotevatRequestBean.getMatgroup().trim() : dbnotevatEntity.getDbnvMatgroup());
		dbnotevatEntity.setDbnvOrigsite(StringUtils.isNotBlank(dbnotevatRequestBean.getOrigsite()) ? dbnotevatRequestBean.getOrigsite().trim() : dbnotevatEntity.getDbnvOrigsite());
		dbnotevatEntity.setDbnvQuantity(Objects.nonNull(dbnotevatRequestBean.getQuantity()) ? dbnotevatRequestBean.getQuantity() : dbnotevatEntity.getDbnvQuantity());
		dbnotevatEntity.setDbnvRate(Objects.nonNull(dbnotevatRequestBean.getRate()) ? dbnotevatRequestBean.getRate() : dbnotevatEntity.getDbnvRate());
		dbnotevatEntity.setDbnvSgstamt(Objects.nonNull(dbnotevatRequestBean.getSgstamt()) ? dbnotevatRequestBean.getSgstamt() : dbnotevatEntity.getDbnvSgstamt());
		dbnotevatEntity.setDbnvSgstperc(Objects.nonNull(dbnotevatRequestBean.getSgstperc()) ? dbnotevatRequestBean.getSgstperc() : dbnotevatEntity.getDbnvSgstperc());
		dbnotevatEntity.setDbnvSite(GenericAuditContextHolder.getContext().getSite());
		dbnotevatEntity.setDbnvTaxableamt(Objects.nonNull(dbnotevatRequestBean.getTaxableamt()) ? dbnotevatRequestBean.getTaxableamt() : dbnotevatEntity.getDbnvTaxableamt());
		dbnotevatEntity.setDbnvToday(LocalDateTime.now());
		dbnotevatEntity.setDbnvUgstamt(Objects.nonNull(dbnotevatRequestBean.getUgstamt()) ? dbnotevatRequestBean.getUgstamt() : dbnotevatEntity.getDbnvUgstamt());
		dbnotevatEntity.setDbnvUgstperc(Objects.nonNull(dbnotevatRequestBean.getUgstperc()) ? dbnotevatRequestBean.getUgstperc() : dbnotevatEntity.getDbnvUgstperc());
		dbnotevatEntity.setDbnvUom(StringUtils.isNotBlank(dbnotevatRequestBean.getUom()) ? dbnotevatRequestBean.getUom().trim() : dbnotevatEntity.getDbnvUom());
		dbnotevatEntity.setDbnvUserid(GenericAuditContextHolder.getContext().getUserid());
		dbnotevatEntity.setDbnvVatamount(Objects.nonNull(dbnotevatRequestBean.getVatamount()) ? dbnotevatRequestBean.getVatamount() : dbnotevatEntity.getDbnvVatamount());
		dbnotevatEntity.setDbnvVatpercent(Objects.nonNull(dbnotevatRequestBean.getVatpercent()) ? dbnotevatRequestBean.getVatpercent() : dbnotevatEntity.getDbnvVatpercent());


		return dbnotevatEntity;
	};

}