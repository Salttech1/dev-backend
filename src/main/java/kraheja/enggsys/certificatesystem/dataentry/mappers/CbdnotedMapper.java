package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.enggsys.bean.request.CdbnotedRequestBean;
import kraheja.enggsys.bean.response.CdbnotedResponseBean;
import kraheja.enggsys.entity.Cdbnoted;
import kraheja.enggsys.entity.Cdbnoted.CdbnotedCK;

public class CbdnotedMapper {

	public static Function	<Object[], List<CdbnotedResponseBean>> fetchCdbnotedEntityPojoMapper = objectArray -> {
		List<Cdbnoted>  cdbnotedEntityList =  (List<Cdbnoted>)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return cdbnotedEntityList.stream().map(cdbnotedEntity -> {
		return CdbnotedResponseBean.builder()
		.dbnoteser(cdbnotedEntity.getCdbnotedCK().getCdbndDbnoteser())
							.lineno(cdbnotedEntity.getCdbnotedCK().getCdbndLineno())
							.amount(cdbnotedEntity.getCdbndAmount())
							.cgstamt(cdbnotedEntity.getCdbndCgstamt())
							.cgstperc(cdbnotedEntity.getCdbndCgstperc())
							.discountamt(cdbnotedEntity.getCdbndDiscountamt())
							.fotoamt(cdbnotedEntity.getCdbndFotoamt())
							.igstamt(cdbnotedEntity.getCdbndIgstamt())
							.igstperc(cdbnotedEntity.getCdbndIgstperc())
							.origsite(cdbnotedEntity.getCdbndOrigsite())
							.quantity(cdbnotedEntity.getCdbndQuantity())
							.rate(cdbnotedEntity.getCdbndRate())
							.saccode(cdbnotedEntity.getCdbndSaccode())
							.sacdesc(cdbnotedEntity.getCdbndSacdesc())
							.sgstamt(cdbnotedEntity.getCdbndSgstamt())
							.sgstperc(cdbnotedEntity.getCdbndSgstperc())
							.site(cdbnotedEntity.getCdbndSite())
							.taxableamt(cdbnotedEntity.getCdbndTaxableamt())
							.today(cdbnotedEntity.getCdbndToday())
							.ugstamt(cdbnotedEntity.getCdbndUgstamt())
							.ugstperc(cdbnotedEntity.getCdbndUgstperc())
							.uom(cdbnotedEntity.getCdbndUom())
							.userid(cdbnotedEntity.getCdbndUserid())
							.vatamount(cdbnotedEntity.getCdbndVatamount())
							.vatpercent(cdbnotedEntity.getCdbndVatpercent())
		.build(); 
		}).collect(Collectors.toList());
		};

	public static Function<CdbnotedRequestBean, Cdbnoted> addCdbnotedPojoEntityMapper = cdbnotedRequestBean -> {
return Cdbnoted.builder().cdbnotedCK(CdbnotedCK.builder()
					.cdbndDbnoteser(cdbnotedRequestBean.getDbnoteser())
					.cdbndLineno(Objects.nonNull(cdbnotedRequestBean.getLineno()) ? cdbnotedRequestBean.getLineno() : BigInteger.ZERO.intValue())
		.build())
					.cdbndAmount(Objects.nonNull(cdbnotedRequestBean.getAmount()) ? cdbnotedRequestBean.getAmount() : BigInteger.ZERO.doubleValue())
					.cdbndCgstamt(Objects.nonNull(cdbnotedRequestBean.getCgstamt()) ? cdbnotedRequestBean.getCgstamt() : BigInteger.ZERO.doubleValue())
					.cdbndCgstperc(Objects.nonNull(cdbnotedRequestBean.getCgstperc()) ? cdbnotedRequestBean.getCgstperc() : BigInteger.ZERO.doubleValue())
					.cdbndDiscountamt(Objects.nonNull(cdbnotedRequestBean.getDiscountamt()) ? cdbnotedRequestBean.getDiscountamt() : BigInteger.ZERO.doubleValue())
					.cdbndFotoamt(Objects.nonNull(cdbnotedRequestBean.getFotoamt()) ? cdbnotedRequestBean.getFotoamt() : BigInteger.ZERO.doubleValue())
					.cdbndIgstamt(Objects.nonNull(cdbnotedRequestBean.getIgstamt()) ? cdbnotedRequestBean.getIgstamt() : BigInteger.ZERO.doubleValue())
					.cdbndIgstperc(Objects.nonNull(cdbnotedRequestBean.getIgstperc()) ? cdbnotedRequestBean.getIgstperc() : BigInteger.ZERO.doubleValue())
					.cdbndOrigsite(GenericAuditContextHolder.getContext().getSite())
					.cdbndQuantity(Objects.nonNull(cdbnotedRequestBean.getQuantity()) ? cdbnotedRequestBean.getQuantity() : BigInteger.ZERO.doubleValue())
					.cdbndRate(Objects.nonNull(cdbnotedRequestBean.getRate()) ? cdbnotedRequestBean.getRate() : BigInteger.ZERO.doubleValue())
					.cdbndSaccode(cdbnotedRequestBean.getSaccode())
					.cdbndSacdesc(cdbnotedRequestBean.getSacdesc())
					.cdbndSgstamt(Objects.nonNull(cdbnotedRequestBean.getSgstamt()) ? cdbnotedRequestBean.getSgstamt() : BigInteger.ZERO.doubleValue())
					.cdbndSgstperc(Objects.nonNull(cdbnotedRequestBean.getSgstperc()) ? cdbnotedRequestBean.getSgstperc() : BigInteger.ZERO.doubleValue())
					.cdbndSite(GenericAuditContextHolder.getContext().getSite())
					.cdbndTaxableamt(Objects.nonNull(cdbnotedRequestBean.getTaxableamt()) ? cdbnotedRequestBean.getTaxableamt() : BigInteger.ZERO.doubleValue())
					.cdbndToday(LocalDateTime.now())
					.cdbndUgstamt(Objects.nonNull(cdbnotedRequestBean.getUgstamt()) ? cdbnotedRequestBean.getUgstamt() : BigInteger.ZERO.doubleValue())
					.cdbndUgstperc(Objects.nonNull(cdbnotedRequestBean.getUgstperc()) ? cdbnotedRequestBean.getUgstperc() : BigInteger.ZERO.doubleValue())
					.cdbndUom(cdbnotedRequestBean.getUom())
					.cdbndUserid(GenericAuditContextHolder.getContext().getUserid())
					.cdbndVatamount(Objects.nonNull(cdbnotedRequestBean.getVatamount()) ? cdbnotedRequestBean.getVatamount() : BigInteger.ZERO.doubleValue())
					.cdbndVatpercent(Objects.nonNull(cdbnotedRequestBean.getVatpercent()) ? cdbnotedRequestBean.getVatpercent() : BigInteger.ZERO.doubleValue())
		
.build();
} ;
	public static BiFunction<Cdbnoted, CdbnotedRequestBean, Cdbnoted> updateCdbnotedEntityPojoMapper = (cdbnotedEntity, cdbnotedRequestBean) -> {
		cdbnotedEntity.getCdbnotedCK().setCdbndDbnoteser(Objects.nonNull(cdbnotedRequestBean.getDbnoteser()) ? cdbnotedRequestBean.getDbnoteser().trim() : cdbnotedEntity.getCdbnotedCK().getCdbndDbnoteser());
		cdbnotedEntity.getCdbnotedCK().setCdbndLineno(Objects.nonNull(cdbnotedRequestBean.getLineno()) ? cdbnotedRequestBean.getLineno() : cdbnotedEntity.getCdbnotedCK().getCdbndLineno());
		cdbnotedEntity.setCdbndAmount(Objects.nonNull(cdbnotedRequestBean.getAmount()) ? cdbnotedRequestBean.getAmount() : cdbnotedEntity.getCdbndAmount());
		cdbnotedEntity.setCdbndCgstamt(Objects.nonNull(cdbnotedRequestBean.getCgstamt()) ? cdbnotedRequestBean.getCgstamt() : cdbnotedEntity.getCdbndCgstamt());
		cdbnotedEntity.setCdbndCgstperc(Objects.nonNull(cdbnotedRequestBean.getCgstperc()) ? cdbnotedRequestBean.getCgstperc() : cdbnotedEntity.getCdbndCgstperc());
		cdbnotedEntity.setCdbndDiscountamt(Objects.nonNull(cdbnotedRequestBean.getDiscountamt()) ? cdbnotedRequestBean.getDiscountamt() : cdbnotedEntity.getCdbndDiscountamt());
		cdbnotedEntity.setCdbndFotoamt(Objects.nonNull(cdbnotedRequestBean.getFotoamt()) ? cdbnotedRequestBean.getFotoamt() : cdbnotedEntity.getCdbndFotoamt());
		cdbnotedEntity.setCdbndIgstamt(Objects.nonNull(cdbnotedRequestBean.getIgstamt()) ? cdbnotedRequestBean.getIgstamt() : cdbnotedEntity.getCdbndIgstamt());
		cdbnotedEntity.setCdbndIgstperc(Objects.nonNull(cdbnotedRequestBean.getIgstperc()) ? cdbnotedRequestBean.getIgstperc() : cdbnotedEntity.getCdbndIgstperc());
		cdbnotedEntity.setCdbndOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		cdbnotedEntity.setCdbndQuantity(Objects.nonNull(cdbnotedRequestBean.getQuantity()) ? cdbnotedRequestBean.getQuantity() : cdbnotedEntity.getCdbndQuantity());
		cdbnotedEntity.setCdbndRate(Objects.nonNull(cdbnotedRequestBean.getRate()) ? cdbnotedRequestBean.getRate() : cdbnotedEntity.getCdbndRate());
		cdbnotedEntity.setCdbndSaccode(Objects.nonNull(cdbnotedRequestBean.getSaccode()) ? cdbnotedRequestBean.getSaccode().trim() : cdbnotedEntity.getCdbndSaccode());
		cdbnotedEntity.setCdbndSacdesc(Objects.nonNull(cdbnotedRequestBean.getSacdesc()) ? cdbnotedRequestBean.getSacdesc().trim() : cdbnotedEntity.getCdbndSacdesc());
		cdbnotedEntity.setCdbndSgstamt(Objects.nonNull(cdbnotedRequestBean.getSgstamt()) ? cdbnotedRequestBean.getSgstamt() : cdbnotedEntity.getCdbndSgstamt());
		cdbnotedEntity.setCdbndSgstperc(Objects.nonNull(cdbnotedRequestBean.getSgstperc()) ? cdbnotedRequestBean.getSgstperc() : cdbnotedEntity.getCdbndSgstperc());
		cdbnotedEntity.setCdbndSite(GenericAuditContextHolder.getContext().getSite()) ; 
		cdbnotedEntity.setCdbndTaxableamt(Objects.nonNull(cdbnotedRequestBean.getTaxableamt()) ? cdbnotedRequestBean.getTaxableamt() : cdbnotedEntity.getCdbndTaxableamt());
		cdbnotedEntity.setCdbndToday(LocalDateTime.now()) ; 
		cdbnotedEntity.setCdbndUgstamt(Objects.nonNull(cdbnotedRequestBean.getUgstamt()) ? cdbnotedRequestBean.getUgstamt() : cdbnotedEntity.getCdbndUgstamt());
		cdbnotedEntity.setCdbndUgstperc(Objects.nonNull(cdbnotedRequestBean.getUgstperc()) ? cdbnotedRequestBean.getUgstperc() : cdbnotedEntity.getCdbndUgstperc());
		cdbnotedEntity.setCdbndUom(Objects.nonNull(cdbnotedRequestBean.getUom()) ? cdbnotedRequestBean.getUom().trim() : cdbnotedEntity.getCdbndUom());
		cdbnotedEntity.setCdbndUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		cdbnotedEntity.setCdbndVatamount(Objects.nonNull(cdbnotedRequestBean.getVatamount()) ? cdbnotedRequestBean.getVatamount() : cdbnotedEntity.getCdbndVatamount());
		cdbnotedEntity.setCdbndVatpercent(Objects.nonNull(cdbnotedRequestBean.getVatpercent()) ? cdbnotedRequestBean.getVatpercent() : cdbnotedEntity.getCdbndVatpercent());


		return cdbnotedEntity;
	};

}
