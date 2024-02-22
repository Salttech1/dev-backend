package kraheja.purch.materialpayments.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.purch.bean.request.AuthmatgroupnarrdtlRequestBean;
import kraheja.purch.bean.response.AuthmatgroupnarrdtlResponseBean;
import kraheja.purch.bean.response.AuthmatgroupnarrdtlResponseBean.AuthmatgroupnarrdtlResponseBeanBuilder;
import kraheja.purch.entity.Authmatgroupnarrdtl;
import kraheja.purch.entity.AuthmatgroupnarrdtlCK;

public interface AuthmatgroupnarrdtlEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<AuthmatgroupnarrdtlResponseBean>> fetchAuthmatgroupnarrdtlEntityPojoMapper = objectArray -> {
		AuthmatgroupnarrdtlResponseBeanBuilder authmatgroupnarrdtlBeanBuilder = AuthmatgroupnarrdtlResponseBean.builder();
		List<Authmatgroupnarrdtl> authmatgroupnarrdtlEntityList = (List<Authmatgroupnarrdtl>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return authmatgroupnarrdtlEntityList.stream().map(authmatgroupnarrdtlEntity -> {
			authmatgroupnarrdtlBeanBuilder.authnum(authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().getAmndAuthnum())
					.srno(authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().getAmndSrno())
					.amount(authmatgroupnarrdtlEntity.getAmndAmount())
					.itemdesc(authmatgroupnarrdtlEntity.getAmndItemdesc())
					.matgroup(authmatgroupnarrdtlEntity.getAmndMatgroup())
					.quantity(authmatgroupnarrdtlEntity.getAmndQuantity())
					.site(authmatgroupnarrdtlEntity.getAmndSite())
					.today(authmatgroupnarrdtlEntity.getAmndToday())
					.userid(authmatgroupnarrdtlEntity.getAmndUserid())
;
			return authmatgroupnarrdtlBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List<Authmatgroupnarrdtl>> addAuthmatgroupnarrdtlEntityPojoMapper = objectArray -> {
		List<AuthmatgroupnarrdtlRequestBean> authmatgroupnarrdtlRequestBeanList = (List<AuthmatgroupnarrdtlRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String authnum = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

	return 	authmatgroupnarrdtlRequestBeanList.stream().map(authmatgroupnarrdtlRequestBean -> {
			return Authmatgroupnarrdtl.builder()
					.authmatgroupnarrdtlCK(AuthmatgroupnarrdtlCK.builder()
							.amndAuthnum(authnum)
							.amndSrno(authmatgroupnarrdtlRequestBean.getSrno()).build())
							.amndAmount(authmatgroupnarrdtlRequestBean.getAmount())
							.amndItemdesc(authmatgroupnarrdtlRequestBean.getItemdesc())
							.amndMatgroup(authmatgroupnarrdtlRequestBean.getMatgroup())
							.amndQuantity(authmatgroupnarrdtlRequestBean.getQuantity())
							.amndSite(GenericAuditContextHolder.getContext().getSite())
							.amndToday(LocalDateTime.now())
							.amndUserid(GenericAuditContextHolder.getContext().getUserid()).build();
			
		}).collect(Collectors.toList());
};

	public static BiFunction<Authmatgroupnarrdtl, AuthmatgroupnarrdtlRequestBean, Authmatgroupnarrdtl> updateAuthmatgroupnarrdtlEntityPojoMapper = (authmatgroupnarrdtlEntity, authmatgroupnarrdtlRequestBean) -> {
		authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().setAmndAuthnum(Objects.nonNull(authmatgroupnarrdtlRequestBean.getAuthnum()) ? authmatgroupnarrdtlRequestBean.getAuthnum().trim() : authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().getAmndAuthnum());
		authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().setAmndSrno(Objects.nonNull(authmatgroupnarrdtlRequestBean.getSrno()) ? authmatgroupnarrdtlRequestBean.getSrno() : authmatgroupnarrdtlEntity.getAuthmatgroupnarrdtlCK().getAmndSrno());
		authmatgroupnarrdtlEntity.setAmndAmount(Objects.nonNull(authmatgroupnarrdtlRequestBean.getAmount()) ? authmatgroupnarrdtlRequestBean.getAmount() : authmatgroupnarrdtlEntity.getAmndAmount());
		authmatgroupnarrdtlEntity.setAmndItemdesc(Objects.nonNull(authmatgroupnarrdtlRequestBean.getItemdesc()) ? authmatgroupnarrdtlRequestBean.getItemdesc().trim() : authmatgroupnarrdtlEntity.getAmndItemdesc());
		authmatgroupnarrdtlEntity.setAmndMatgroup(Objects.nonNull(authmatgroupnarrdtlRequestBean.getMatgroup()) ? authmatgroupnarrdtlRequestBean.getMatgroup().trim() : authmatgroupnarrdtlEntity.getAmndMatgroup());
		authmatgroupnarrdtlEntity.setAmndQuantity(Objects.nonNull(authmatgroupnarrdtlRequestBean.getQuantity()) ? authmatgroupnarrdtlRequestBean.getQuantity() : authmatgroupnarrdtlEntity.getAmndQuantity());
		authmatgroupnarrdtlEntity.setAmndSite(Objects.nonNull(authmatgroupnarrdtlRequestBean.getSite()) ? authmatgroupnarrdtlRequestBean.getSite().trim() : authmatgroupnarrdtlEntity.getAmndSite());
		authmatgroupnarrdtlEntity.setAmndToday(Objects.nonNull(authmatgroupnarrdtlRequestBean.getToday()) ? authmatgroupnarrdtlRequestBean.getToday() : authmatgroupnarrdtlEntity.getAmndToday());
		authmatgroupnarrdtlEntity.setAmndUserid(Objects.nonNull(authmatgroupnarrdtlRequestBean.getUserid()) ? authmatgroupnarrdtlRequestBean.getUserid().trim() : authmatgroupnarrdtlEntity.getAmndUserid());
		return authmatgroupnarrdtlEntity;
	};

}