package kraheja.arch.projbldg.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.arch.projbldg.dataentry.bean.request.BldgmapRequestBean;
import kraheja.arch.projbldg.dataentry.bean.response.BldgmapResponseBean;
import kraheja.arch.projbldg.dataentry.bean.response.BldgmapResponseBean.BldgmapResponseBeanBuilder;
import kraheja.arch.projbldg.dataentry.entity.Bldgmap;
import kraheja.arch.projbldg.dataentry.entity.BldgmapCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface BldgmapEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], BldgmapResponseBean> fetchBldgmapEntityPojoMapper = objectArray -> {
		BldgmapResponseBeanBuilder bldgmapBeanBuilder = BldgmapResponseBean.builder();
		Bldgmap bldgmapEntity = (Bldgmap) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
			bldgmapBeanBuilder.ebldgcode(bldgmapEntity.getBldgmapCK().getBmapEbldgcode())
.abldgcode(bldgmapEntity.getBldgmapCK().getBmapAbldgcode())
					.abldgname(bldgmapEntity.getBmapAbldgname())
					.blockcerttype(bldgmapEntity.getBmapBlockcerttype())
					.closedate(Objects.nonNull(bldgmapEntity.getBmapClosedate()) ? bldgmapEntity.getBmapClosedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.coy1(bldgmapEntity.getBmapCoy1())
					.coy2(bldgmapEntity.getBmapCoy2())
					.coy3(bldgmapEntity.getBmapCoy3())
					.ebldgname(bldgmapEntity.getBmapEbldgname())
					.origsite(bldgmapEntity.getBmapOrigsite())
					.site(bldgmapEntity.getBmapSite())
					.today(bldgmapEntity.getBmapToday())
					.userid(bldgmapEntity.getBmapUserid())
;
			return bldgmapBeanBuilder.build();
	};

	public static Function<Object[], Bldgmap> addBldgmapEntityPojoMapper = objectArray -> {
		BldgmapRequestBean bldgmapRequestBean = (BldgmapRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		
		String code = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
		: null);


		Bldgmap.BldgmapBuilder bldgmapbuilder = Bldgmap.builder();

bldgmapbuilder
			.bldgmapCK(BldgmapCK.builder()
.bmapEbldgcode(code)
.bmapAbldgcode(code)
.build())
					.bmapAbldgname(bldgmapRequestBean.getAbldgname())
					.bmapBlockcerttype(bldgmapRequestBean.getBlockcerttype())
					.bmapClosedate(Objects.nonNull(bldgmapRequestBean.getClosedate()) ? LocalDate.parse(bldgmapRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.bmapCoy1(bldgmapRequestBean.getCoy1())
					.bmapCoy2(bldgmapRequestBean.getCoy2())
					.bmapCoy3(bldgmapRequestBean.getCoy3())
					.bmapEbldgname(bldgmapRequestBean.getEbldgname())
					.bmapOrigsite(bldgmapRequestBean.getOrigsite())
					.bmapSite(GenericAuditContextHolder.getContext().getSite())
					.bmapToday(LocalDateTime.now())
					.bmapUserid(GenericAuditContextHolder.getContext().getUserid())
;

		return bldgmapbuilder.build();
};

	public static BiFunction<Bldgmap, BldgmapRequestBean, Bldgmap> updateBldgmapEntityPojoMapper = (bldgmapEntity, bldgmapRequestBean) -> {
		bldgmapEntity.getBldgmapCK().setBmapEbldgcode(StringUtils.isNotBlank(bldgmapRequestBean.getEbldgcode()) ? bldgmapRequestBean.getEbldgcode().trim() : bldgmapEntity.getBldgmapCK().getBmapEbldgcode());
		bldgmapEntity.getBldgmapCK().setBmapAbldgcode(StringUtils.isNotBlank(bldgmapRequestBean.getAbldgcode()) ? bldgmapRequestBean.getAbldgcode().trim() : bldgmapEntity.getBldgmapCK().getBmapAbldgcode());
		bldgmapEntity.setBmapAbldgname(StringUtils.isNotBlank(bldgmapRequestBean.getAbldgname()) ? bldgmapRequestBean.getAbldgname().trim() : bldgmapEntity.getBmapAbldgname());
		bldgmapEntity.setBmapBlockcerttype(StringUtils.isNotBlank(bldgmapRequestBean.getBlockcerttype()) ? bldgmapRequestBean.getBlockcerttype().trim() : bldgmapEntity.getBmapBlockcerttype());
		bldgmapEntity.setBmapClosedate(Objects.nonNull(bldgmapRequestBean.getClosedate()) ? LocalDate.parse(bldgmapRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : bldgmapEntity.getBmapClosedate());
		bldgmapEntity.setBmapCoy1(StringUtils.isNotBlank(bldgmapRequestBean.getCoy1()) ? bldgmapRequestBean.getCoy1().trim() : bldgmapEntity.getBmapCoy1());
		bldgmapEntity.setBmapCoy2(StringUtils.isNotBlank(bldgmapRequestBean.getCoy2()) ? bldgmapRequestBean.getCoy2().trim() : bldgmapEntity.getBmapCoy2());
		bldgmapEntity.setBmapCoy3(StringUtils.isNotBlank(bldgmapRequestBean.getCoy3()) ? bldgmapRequestBean.getCoy3().trim() : bldgmapEntity.getBmapCoy3());
		bldgmapEntity.setBmapEbldgname(StringUtils.isNotBlank(bldgmapRequestBean.getEbldgname()) ? bldgmapRequestBean.getEbldgname().trim() : bldgmapEntity.getBmapEbldgname());
		bldgmapEntity.setBmapOrigsite(StringUtils.isNotBlank(bldgmapRequestBean.getOrigsite()) ? bldgmapRequestBean.getOrigsite().trim() : bldgmapEntity.getBmapOrigsite());
		bldgmapEntity.setBmapSite(StringUtils.isNotBlank(bldgmapRequestBean.getSite()) ? bldgmapRequestBean.getSite().trim() : bldgmapEntity.getBmapSite());
		bldgmapEntity.setBmapToday(Objects.nonNull(bldgmapRequestBean.getToday()) ?  LocalDateTime.now()  : bldgmapEntity.getBmapToday());
		bldgmapEntity.setBmapUserid(StringUtils.isNotBlank(bldgmapRequestBean.getUserid()) ? bldgmapRequestBean.getUserid().trim() : bldgmapEntity.getBmapUserid());


		return bldgmapEntity;
	};

}
