package kraheja.purch.purchasebills.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.PbillvatRequestBean;
import kraheja.purch.bean.response.PbillvatResponseBean;
import kraheja.purch.entity.Pbillvat;
import kraheja.purch.entity.PbillvatCK;

public interface PbillvatMapper {
	public static Function<List<Pbillvat>, List<PbillvatResponseBean>> fetchPbillvatEntityPojoMapper = pbillvatEntityList -> {
		return pbillvatEntityList.stream().map(pbillvatEntity -> {
			return PbillvatResponseBean.builder().ser(pbillvatEntity.getPbillvatCK().getPblvSer())
					.lineno(pbillvatEntity.getPbillvatCK().getPblvLineno())
					.amount(pbillvatEntity.getPblvAmount())
					.cgstamt(pbillvatEntity.getPblvCgstamt())
					.cgstperc(pbillvatEntity.getPblvCgstperc())
					.discountamt(pbillvatEntity.getPblvDiscountamt())
					.fotoamt(pbillvatEntity.getPblvFotoamt())
					.hsncode(pbillvatEntity.getPblvHsncode())
					.hsndesc(pbillvatEntity.getPblvHsndesc())
					.igstamt(pbillvatEntity.getPblvIgstamt())
					.igstperc(pbillvatEntity.getPblvIgstperc())
					.itemcode(pbillvatEntity.getPblvItemcode())
					.matcode(pbillvatEntity.getPblvMatcode())
					.matgroup(pbillvatEntity.getPblvMatgroup())
					.origsite(pbillvatEntity.getPblvOrigsite())
					.quantity(pbillvatEntity.getPblvQuantity())
					.rate(pbillvatEntity.getPblvRate())
					.sgstamt(pbillvatEntity.getPblvSgstamt())
					.sgstperc(pbillvatEntity.getPblvSgstperc())
					.site(pbillvatEntity.getPblvSite())
					.taxableamt(pbillvatEntity.getPblvTaxableamt())
					.today(pbillvatEntity.getPblvToday())
					.ugstamt(pbillvatEntity.getPblvUgstamt())
					.ugstperc(pbillvatEntity.getPblvUgstperc())
					.uom(pbillvatEntity.getPblvUom())
					.userid(pbillvatEntity.getPblvUserid())
					.vatamount(pbillvatEntity.getPblvVatamount())
					.vatpercent(pbillvatEntity.getPblvVatpercent()).build();
		}).collect(Collectors.toList());
	};


	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Pbillvat>> addPbillvatPojoEntityMapper = objectArray -> {
		List<PbillvatRequestBean> pbillvatRequestBeanList =(List<PbillvatRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String ser = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String uom = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		Double quantity = (Double) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE]) ? objectArray[CommonConstraints.INSTANCE.THREE] : null);
		Double rate = (Double) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.FOUR]) ? objectArray[CommonConstraints.INSTANCE.FOUR] : null);
		return pbillvatRequestBeanList.stream().map(pbillvatRequestBean -> {
			return Pbillvat.builder()
					.pbillvatCK(PbillvatCK.builder()
							.pblvSer(ser)
							.pblvLineno(pbillvatRequestBean.getLineno())
							.build())
					.pblvAmount(pbillvatRequestBean.getAmount())
					.pblvCgstamt(pbillvatRequestBean.getCgstamt())
					.pblvCgstperc(pbillvatRequestBean.getCgstperc())
					.pblvDiscountamt(pbillvatRequestBean.getDiscountamt())
					.pblvFotoamt(pbillvatRequestBean.getFotoamt())
					.pblvHsncode(pbillvatRequestBean.getHsncode())
					.pblvHsndesc(pbillvatRequestBean.getHsndesc())
					.pblvIgstamt(pbillvatRequestBean.getIgstamt())
					.pblvIgstperc(pbillvatRequestBean.getIgstperc())
					.pblvItemcode(pbillvatRequestBean.getItemcode())
					.pblvMatcode(pbillvatRequestBean.getMatcode())
					.pblvMatgroup(pbillvatRequestBean.getMatgroup())
					.pblvOrigsite(GenericAuditContextHolder.getContext().getSite())
					.pblvQuantity(quantity)
					.pblvRate(rate)
					.pblvSgstamt(pbillvatRequestBean.getSgstamt())
					.pblvSgstperc(pbillvatRequestBean.getSgstperc())
					.pblvSite(GenericAuditContextHolder.getContext().getSite())
					.pblvTaxableamt(pbillvatRequestBean.getTaxableamt())
					.pblvToday(LocalDateTime.now())
					.pblvUgstamt(pbillvatRequestBean.getUgstamt())
					.pblvUgstperc(pbillvatRequestBean.getUgstperc())
					.pblvUom(uom)
					.pblvUserid(GenericAuditContextHolder.getContext().getUserid())
					.pblvVatamount(pbillvatRequestBean.getVatamount())
					.pblvVatpercent(pbillvatRequestBean.getVatpercent()).build();
		}).collect(Collectors.toList());
	};

	public static BiFunction<Pbillvat, PbillvatRequestBean, Pbillvat> updatePbillvatEntityPojoMapper = (pbillvatEntity, pbillvatRequestBean) -> {
		pbillvatEntity.setPblvAmount(Objects.nonNull(pbillvatRequestBean.getAmount()) ? pbillvatRequestBean.getAmount() : pbillvatEntity.getPblvAmount());
		pbillvatEntity.setPblvCgstamt(Objects.nonNull(pbillvatRequestBean.getCgstamt()) ? pbillvatRequestBean.getCgstamt() : pbillvatEntity.getPblvCgstamt());
		pbillvatEntity.setPblvCgstperc(Objects.nonNull(pbillvatRequestBean.getCgstperc()) ? pbillvatRequestBean.getCgstperc() : pbillvatEntity.getPblvCgstperc());
		pbillvatEntity.setPblvDiscountamt(Objects.nonNull(pbillvatRequestBean.getDiscountamt()) ? pbillvatRequestBean.getDiscountamt() : pbillvatEntity.getPblvDiscountamt());
		pbillvatEntity.setPblvFotoamt(Objects.nonNull(pbillvatRequestBean.getFotoamt()) ? pbillvatRequestBean.getFotoamt() : pbillvatEntity.getPblvFotoamt());
		pbillvatEntity.setPblvHsncode(Objects.nonNull(pbillvatRequestBean.getHsncode()) ? pbillvatRequestBean.getHsncode().trim() : pbillvatEntity.getPblvHsncode());
		pbillvatEntity.setPblvHsndesc(Objects.nonNull(pbillvatRequestBean.getHsndesc()) ? pbillvatRequestBean.getHsndesc().trim() : pbillvatEntity.getPblvHsndesc());
		pbillvatEntity.setPblvIgstamt(Objects.nonNull(pbillvatRequestBean.getIgstamt()) ? pbillvatRequestBean.getIgstamt() : pbillvatEntity.getPblvIgstamt());
		pbillvatEntity.setPblvIgstperc(Objects.nonNull(pbillvatRequestBean.getIgstperc()) ? pbillvatRequestBean.getIgstperc() : pbillvatEntity.getPblvIgstperc());
		pbillvatEntity.setPblvItemcode(Objects.nonNull(pbillvatRequestBean.getItemcode()) ? pbillvatRequestBean.getItemcode().trim() : pbillvatEntity.getPblvItemcode());
		pbillvatEntity.setPblvMatcode(Objects.nonNull(pbillvatRequestBean.getMatcode()) ? pbillvatRequestBean.getMatcode().trim() : pbillvatEntity.getPblvMatcode());
		pbillvatEntity.setPblvMatgroup(Objects.nonNull(pbillvatRequestBean.getMatgroup()) ? pbillvatRequestBean.getMatgroup().trim() : pbillvatEntity.getPblvMatgroup());
		pbillvatEntity.setPblvOrigsite(Objects.nonNull(pbillvatRequestBean.getOrigsite()) ? pbillvatRequestBean.getOrigsite().trim() : pbillvatEntity.getPblvOrigsite());
		pbillvatEntity.setPblvQuantity(Objects.nonNull(pbillvatRequestBean.getQuantity()) ? pbillvatRequestBean.getQuantity() : pbillvatEntity.getPblvQuantity());
		pbillvatEntity.setPblvRate(Objects.nonNull(pbillvatRequestBean.getRate()) ? pbillvatRequestBean.getRate() : pbillvatEntity.getPblvRate());
		pbillvatEntity.setPblvSgstamt(Objects.nonNull(pbillvatRequestBean.getSgstamt()) ? pbillvatRequestBean.getSgstamt() : pbillvatEntity.getPblvSgstamt());
		pbillvatEntity.setPblvSgstperc(Objects.nonNull(pbillvatRequestBean.getSgstperc()) ? pbillvatRequestBean.getSgstperc() : pbillvatEntity.getPblvSgstperc());
		pbillvatEntity.setPblvSite(Objects.nonNull(pbillvatRequestBean.getSite()) ? pbillvatRequestBean.getSite().trim() : pbillvatEntity.getPblvSite());
		pbillvatEntity.setPblvTaxableamt(Objects.nonNull(pbillvatRequestBean.getTaxableamt()) ? pbillvatRequestBean.getTaxableamt() : pbillvatEntity.getPblvTaxableamt());
		pbillvatEntity.setPblvToday(Objects.nonNull(pbillvatRequestBean.getToday()) ? pbillvatRequestBean.getToday() : pbillvatEntity.getPblvToday());
		pbillvatEntity.setPblvUgstamt(Objects.nonNull(pbillvatRequestBean.getUgstamt()) ? pbillvatRequestBean.getUgstamt() : pbillvatEntity.getPblvUgstamt());
		pbillvatEntity.setPblvUgstperc(Objects.nonNull(pbillvatRequestBean.getUgstperc()) ? pbillvatRequestBean.getUgstperc() : pbillvatEntity.getPblvUgstperc());
		pbillvatEntity.setPblvUom(Objects.nonNull(pbillvatRequestBean.getUom()) ? pbillvatRequestBean.getUom().trim() : pbillvatEntity.getPblvUom());
		pbillvatEntity.setPblvUserid(Objects.nonNull(pbillvatRequestBean.getUserid()) ? pbillvatRequestBean.getUserid().trim() : pbillvatEntity.getPblvUserid());
		pbillvatEntity.setPblvVatamount(Objects.nonNull(pbillvatRequestBean.getVatamount()) ? pbillvatRequestBean.getVatamount() : pbillvatEntity.getPblvVatamount());
		pbillvatEntity.setPblvVatpercent(Objects.nonNull(pbillvatRequestBean.getVatpercent()) ? pbillvatRequestBean.getVatpercent() : pbillvatEntity.getPblvVatpercent());

		return pbillvatEntity;
	};
}
