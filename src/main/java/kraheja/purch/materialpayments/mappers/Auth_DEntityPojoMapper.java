package kraheja.purch.materialpayments.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.Auth_DRequestBean;
import kraheja.purch.bean.response.Auth_DResponseBean;
import kraheja.purch.bean.response.Auth_DResponseBean.Auth_DResponseBeanBuilder;
import kraheja.purch.entity.Auth_D;
import kraheja.purch.entity.Auth_DCK;

public interface Auth_DEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Auth_DResponseBean>> fetchAuth_DEntityPojoMapper = objectArray -> {
		Auth_DResponseBeanBuilder auth_dBeanBuilder = Auth_DResponseBean.builder();
		List<Auth_D> auth_dEntityList = (List<Auth_D>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return auth_dEntityList.stream().map(auth_dEntity -> {
			auth_dBeanBuilder.advadj(auth_dEntity.getAutdAdvadj())
					.partycode(auth_dEntity.getAutdPartycode())
					.bldgcode(auth_dEntity.getAutdBldgcode())
					.matgroup(auth_dEntity.getAutdMatgroup())
					.suppbillno(auth_dEntity.getAuth_dCK().getAutdSuppbillno())
					.authnum(auth_dEntity.getAuth_dCK().getAutdAuthnum())
					.authamount(auth_dEntity.getAutdAuthamount())
					.authqty(auth_dEntity.getAutdAuthqty())
					.authtdsamt(auth_dEntity.getAutdAuthtdsamt())
					.authtype(auth_dEntity.getAutdAuthtype())
					.billtype(auth_dEntity.getAutdBilltype())
					.matcode(auth_dEntity.getAutdMatcode())
					.origsite(auth_dEntity.getAutdOrigsite())
					.relretamt(auth_dEntity.getAutdRelretamt())
					.retainamt(auth_dEntity.getAutdRetainamt())
					.retentionadj(auth_dEntity.getAutdRetentionadj())
					.site(auth_dEntity.getAutdSite())
					.suppbilldt(Objects.nonNull(auth_dEntity.getAutdSuppbilldt()) ? auth_dEntity.getAutdSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.today(auth_dEntity.getAutdToday())
					.userid(auth_dEntity.getAutdUserid())
;
			return auth_dBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List<Auth_D>> addAuth_DEntityPojoMapper = objectArray -> {
		List<Auth_DRequestBean> auth_dRequestBeanList = (List<Auth_DRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String authnum = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

		return auth_dRequestBeanList.stream().map(auth_dRequestBean -> {
			return Auth_D.builder().auth_dCK(Auth_DCK.builder()
					.autdAuthnum(authnum)
					.autdSuppbillno(auth_dRequestBean.getSuppbillno())
		.build())
				.autdPartycode(auth_dRequestBean.getPartycode())
				.autdBldgcode(auth_dRequestBean.getBldgcode())
				.autdMatgroup(auth_dRequestBean.getMatgroup())
					.autdAdvadj(auth_dRequestBean.getAdvadj())
					.autdAuthamount(auth_dRequestBean.getAuthamount())
					.autdAuthqty(auth_dRequestBean.getAuthqty())
					.autdAuthtdsamt(auth_dRequestBean.getAuthtdsamt())
					.autdAuthtype(auth_dRequestBean.getAuthtype())
					.autdBilltype(auth_dRequestBean.getBilltype())
					.autdMatcode(auth_dRequestBean.getMatcode())
					.autdOrigsite(GenericAuditContextHolder.getContext().getSite())
					.autdRelretamt(auth_dRequestBean.getRelretamt())
					.autdRetainamt(auth_dRequestBean.getRetainamt())
					.autdRetentionadj(auth_dRequestBean.getRetentionadj())
					.autdSite(GenericAuditContextHolder.getContext().getSite())
					.autdSuppbilldt(Objects.nonNull(auth_dRequestBean.getSuppbilldt()) ? LocalDate.parse(auth_dRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.autdToday(LocalDateTime.now())
					.autdUserid(GenericAuditContextHolder.getContext().getUserid()).build();
		}).collect(Collectors.toList());
};

	public static BiFunction<Auth_D, Auth_DRequestBean, Auth_D> updateAuth_DEntityPojoMapper = (auth_dEntity, auth_dRequestBean) -> {
	 
		auth_dEntity.setAutdPartycode(Objects.nonNull(auth_dRequestBean.getPartycode()) ? auth_dRequestBean.getPartycode() : auth_dEntity.getAutdPartycode());
		auth_dEntity.setAutdBldgcode(Objects.nonNull(auth_dRequestBean.getBldgcode()) ? auth_dRequestBean.getBldgcode() : auth_dEntity.getAutdBldgcode());
		auth_dEntity.setAutdMatgroup(Objects.nonNull(auth_dRequestBean.getMatgroup()) ? auth_dRequestBean.getMatgroup() : auth_dEntity.getAutdMatgroup());
		auth_dEntity.setAutdAdvadj(Objects.nonNull(auth_dRequestBean.getAdvadj()) ? auth_dRequestBean.getAdvadj() : auth_dEntity.getAutdAdvadj());
		auth_dEntity.setAutdAuthamount(Objects.nonNull(auth_dRequestBean.getAuthamount()) ? auth_dRequestBean.getAuthamount() : auth_dEntity.getAutdAuthamount());
		auth_dEntity.setAutdAuthqty(Objects.nonNull(auth_dRequestBean.getAuthqty()) ? auth_dRequestBean.getAuthqty() : auth_dEntity.getAutdAuthqty());
		auth_dEntity.setAutdAuthtdsamt(Objects.nonNull(auth_dRequestBean.getAuthtdsamt()) ? auth_dRequestBean.getAuthtdsamt() : auth_dEntity.getAutdAuthtdsamt());
		auth_dEntity.setAutdAuthtype(Objects.nonNull(auth_dRequestBean.getAuthtype()) ? auth_dRequestBean.getAuthtype().trim() : auth_dEntity.getAutdAuthtype());
		auth_dEntity.setAutdBilltype(Objects.nonNull(auth_dRequestBean.getBilltype()) ? auth_dRequestBean.getBilltype().trim() : auth_dEntity.getAutdBilltype());
		auth_dEntity.setAutdMatcode(Objects.nonNull(auth_dRequestBean.getMatcode()) ? auth_dRequestBean.getMatcode().trim() : auth_dEntity.getAutdMatcode());
		auth_dEntity.setAutdOrigsite(Objects.nonNull(auth_dRequestBean.getOrigsite()) ? auth_dRequestBean.getOrigsite().trim() : auth_dEntity.getAutdOrigsite());
		auth_dEntity.setAutdRelretamt(Objects.nonNull(auth_dRequestBean.getRelretamt()) ? auth_dRequestBean.getRelretamt() : auth_dEntity.getAutdRelretamt());
		auth_dEntity.setAutdRetainamt(Objects.nonNull(auth_dRequestBean.getRetainamt()) ? auth_dRequestBean.getRetainamt() : auth_dEntity.getAutdRetainamt());
		auth_dEntity.setAutdRetentionadj(Objects.nonNull(auth_dRequestBean.getRetentionadj()) ? auth_dRequestBean.getRetentionadj() : auth_dEntity.getAutdRetentionadj());
		auth_dEntity.setAutdSite(GenericAuditContextHolder.getContext().getSite());
		auth_dEntity.setAutdSuppbilldt(Objects.nonNull(auth_dRequestBean.getSuppbilldt()) ? LocalDate.parse(auth_dRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : auth_dEntity.getAutdSuppbilldt());
		auth_dEntity.setAutdToday(Objects.nonNull(auth_dRequestBean.getToday()) ? auth_dRequestBean.getToday() : auth_dEntity.getAutdToday());
		auth_dEntity.setAutdUserid(GenericAuditContextHolder.getContext().getUserid());
		return auth_dEntity;
	};
}
