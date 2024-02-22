package kraheja.commons.mappers.pojoentity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.bean.request.MinorsRequestBean;
import kraheja.commons.bean.response.MinorsResponseBean;
import kraheja.commons.bean.response.MinorsResponseBean.MinorsResponseBeanBuilder;
import kraheja.commons.entity.Minors;
import kraheja.commons.entity.MinorsCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface MinorsEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], MinorsResponseBean> fetchMinorsEntityPojoMapper = objectArray -> {
		MinorsResponseBeanBuilder minorsBeanBuilder = MinorsResponseBean.builder();
		Minors minorsEntity = (Minors) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
			minorsBeanBuilder.minorscode(minorsEntity.getMinorsCK().getMinMinorscode())
.minorstype(minorsEntity.getMinorsCK().getMinMinorstype()).closedate(Objects.nonNull(minorsEntity.getMinorsCK().getMinClosedate()) ? minorsEntity.getMinorsCK().getMinClosedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.minorsname(minorsEntity.getMinMinorsname())
					.opendate(Objects.nonNull(minorsEntity.getMinOpendate()) ? minorsEntity.getMinOpendate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.site(minorsEntity.getMinSite())
					.today(minorsEntity.getMinToday())
					.userid(minorsEntity.getMinUserid())
					.validminor(minorsEntity.getMinValidminor())
;
			return minorsBeanBuilder.build();
	};

	public static Function<Object[], Minors> addMinorsEntityPojoMapper = objectArray -> {
		MinorsRequestBean minorsRequestBean = (MinorsRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		String code = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
		? objectArray[CommonConstraints.INSTANCE.TWO]
: null);
		

		Minors.MinorsBuilder minorsbuilder = Minors.builder();

minorsbuilder
			.minorsCK(MinorsCK.builder()
.minMinorscode(code)
.minMinorstype(minorsRequestBean.getMinorstype())
.minClosedate(StringUtils.isNotBlank(minorsRequestBean.getClosedate()) ? LocalDate.parse(minorsRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
.build())
					.minMinorsname(minorsRequestBean.getMinorsname())
					.minOpendate(StringUtils.isNotBlank(minorsRequestBean.getOpendate()) ? LocalDate.parse(minorsRequestBean.getOpendate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.minSite(GenericAuditContextHolder.getContext().getSite())
					.minToday(LocalDateTime.now())
					.minUserid(GenericAuditContextHolder.getContext().getUserid())
					.minValidminor(minorsRequestBean.getValidminor())
;

		return minorsbuilder.build();
};

	public static BiFunction<Minors, MinorsRequestBean, Minors> updateMinorsEntityPojoMapper = (minorsEntity, minorsRequestBean) -> {
		//minorsEntity.getMinorsCK().setMinMinorscode(StringUtils.isNotBlank(minorsRequestBean.getMinorscode()) ? minorsRequestBean.getMinorscode().trim() : minorsEntity.getMinorsCK().getMinMinorscode());
		//minorsEntity.getMinorsCK().setMinMinorstype(StringUtils.isNotBlank(minorsRequestBean.getMinorstype()) ? minorsRequestBean.getMinorstype().trim() : minorsEntity.getMinorsCK().getMinMinorstype());
		//minorsEntity.getMinorsCK().setMinClosedate( StringUtils.isNotBlank(minorsRequestBean.getClosedate()) ? LocalDate.parse(minorsRequestBean.getClosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : minorsEntity.getMinorsCK().getMinClosedate());
		minorsEntity.setMinMinorsname(StringUtils.isNotBlank(minorsRequestBean.getMinorsname()) ? minorsRequestBean.getMinorsname().trim() : minorsEntity.getMinMinorsname());
		minorsEntity.setMinOpendate( StringUtils.isNotBlank(minorsRequestBean.getOpendate()) ? LocalDate.parse(minorsRequestBean.getOpendate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : minorsEntity.getMinOpendate());
		minorsEntity.setMinSite(StringUtils.isNotBlank(minorsRequestBean.getSite()) ? minorsRequestBean.getSite().trim() : minorsEntity.getMinSite());
		minorsEntity.setMinToday(Objects.nonNull(minorsRequestBean.getToday()) ?  LocalDateTime.now()  : minorsEntity.getMinToday());
		minorsEntity.setMinUserid(StringUtils.isNotBlank(minorsRequestBean.getUserid()) ? minorsRequestBean.getUserid().trim() : minorsEntity.getMinUserid());
		minorsEntity.setMinValidminor(StringUtils.isNotBlank(minorsRequestBean.getValidminor()) ? minorsRequestBean.getValidminor().trim() : minorsEntity.getMinValidminor());
		return minorsEntity;
	};

}
