package kraheja.adminexp.insurance.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.insurance.dataentry.bean.request.InsprempayschRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InsprempayschResponseBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InsprempayschResponseBean.InsprempayschResponseBeanBuilder;
import kraheja.adminexp.insurance.dataentry.entity.Insprempaysch;
import kraheja.adminexp.insurance.dataentry.entity.InsprempayschCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;

public interface InsprempayschEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<InsprempayschResponseBean>> fetchInsprempayschEntityPojoMapperlist = objectArray -> {
		InsprempayschResponseBeanBuilder insprempayschBeanBuilder = InsprempayschResponseBean.builder();
		List<Insprempaysch> insprempayschEntityList = (List<Insprempaysch>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return insprempayschEntityList.stream().map(insprempayschEntity -> {
			insprempayschBeanBuilder.policyid(insprempayschEntity.getInsprempayschCK().getIppsPolicyid())
					.policynumber(insprempayschEntity.getInsprempayschCK().getIppsPolicynumber())
					.linenumber(insprempayschEntity.getInsprempayschCK().getIppsLinenumber())
					.certnum(insprempayschEntity.getIppsCertnum())
					.ipaddress(insprempayschEntity.getIppsIpaddress())
					.machinename(insprempayschEntity.getIppsMachinename())
					.modifiedon(insprempayschEntity.getIppsModifiedon())
					.module(insprempayschEntity.getIppsModule())
					.payamt(insprempayschEntity.getIppsPayamt())
					.paydate(Objects.nonNull(insprempayschEntity.getIppsPaydate()) ? insprempayschEntity.getIppsPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.remark(insprempayschEntity.getIppsRemark())
					.site(insprempayschEntity.getIppsSite())
					.status(insprempayschEntity.getIppsStatus())
					.transer(insprempayschEntity.getIppsTranser())
					.userid(insprempayschEntity.getIppsUserid())
;
			return insprempayschBeanBuilder.build();
		}).collect(Collectors.toList());
	};
	
	@SuppressWarnings("unchecked")
	public static Function<Insprempaysch, InsprempayschResponseBean> fetchInsprempayschEntityPojoMapper = insprempayschEntity -> {
		return InsprempayschResponseBean.builder().policyid(insprempayschEntity.getInsprempayschCK().getIppsPolicyid())
					.policynumber(insprempayschEntity.getInsprempayschCK().getIppsPolicynumber())
					.linenumber(insprempayschEntity.getInsprempayschCK().getIppsLinenumber())
					.certnum(insprempayschEntity.getIppsCertnum())
					.ipaddress(insprempayschEntity.getIppsIpaddress())
					.machinename(insprempayschEntity.getIppsMachinename())
					.modifiedon(insprempayschEntity.getIppsModifiedon())
					.module(insprempayschEntity.getIppsModule())
					.payamt(insprempayschEntity.getIppsPayamt())
					.paydate(Objects.nonNull(insprempayschEntity.getIppsPaydate()) ? insprempayschEntity.getIppsPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.remark(insprempayschEntity.getIppsRemark())
					.site(insprempayschEntity.getIppsSite())
					.status(insprempayschEntity.getIppsStatus())
					.transer(insprempayschEntity.getIppsTranser())
					.userid(insprempayschEntity.getIppsUserid()).build();
	};

	public static Function<Object[], List<Insprempaysch>> addInsprempayschEntityPojoMapper = objectArray -> {
		List<InsprempayschRequestBean> insprempayschRequestBeanList = (List<InsprempayschRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String policyId = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		return insprempayschRequestBeanList.stream().map(
				insprempayschRequestBean -> {
					return Insprempaysch.builder()
							.insprempayschCK(InsprempayschCK.builder()
					.ippsPolicyid(policyId)
					.ippsPolicynumber(insprempayschRequestBean.getPolicynumber())
					.ippsLinenumber(insprempayschRequestBean.getLinenumber())
		.build())
					.ippsCertnum(insprempayschRequestBean.getCertnum())
					.ippsIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.ippsMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.ippsModifiedon(LocalDateTime.now())
					.ippsModule(insprempayschRequestBean.getModule())
					.ippsPayamt(insprempayschRequestBean.getPayamt())
					.ippsPaydate(Objects.nonNull(insprempayschRequestBean.getPaydate()) ? LocalDate.parse(insprempayschRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.ippsRemark(insprempayschRequestBean.getRemark())
					.ippsSite(GenericAuditContextHolder.getContext().getSite())
					.ippsStatus(insprempayschRequestBean.getStatus())
					.ippsTranser(insprempayschRequestBean.getTranser())
					.ippsUserid(GenericAuditContextHolder.getContext().getUserid())
					.build();
	}).collect(Collectors.toList());
};

	public static BiFunction<Insprempaysch, InsprempayschRequestBean, Insprempaysch> updateInsprempayschEntityPojoMapper = (insprempayschEntity, insprempayschRequestBean) -> {
		insprempayschEntity.getInsprempayschCK().setIppsPolicyid(Objects.nonNull(insprempayschRequestBean.getPolicyid()) ? insprempayschRequestBean.getPolicyid().trim() : insprempayschEntity.getInsprempayschCK().getIppsPolicyid());
		insprempayschEntity.getInsprempayschCK().setIppsPolicynumber(Objects.nonNull(insprempayschRequestBean.getPolicynumber()) ? insprempayschRequestBean.getPolicynumber().trim() : insprempayschEntity.getInsprempayschCK().getIppsPolicynumber());
		insprempayschEntity.getInsprempayschCK().setIppsLinenumber(Objects.nonNull(insprempayschRequestBean.getLinenumber()) ? insprempayschRequestBean.getLinenumber() : insprempayschEntity.getInsprempayschCK().getIppsLinenumber());
		insprempayschEntity.setIppsCertnum(Objects.nonNull(insprempayschRequestBean.getCertnum()) ? insprempayschRequestBean.getCertnum().trim() : insprempayschEntity.getIppsCertnum());
		insprempayschEntity.setIppsIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress());
		insprempayschEntity.setIppsMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName());
		insprempayschEntity.setIppsModifiedon(Objects.nonNull(insprempayschRequestBean.getModifiedon()) ? insprempayschRequestBean.getModifiedon() : insprempayschEntity.getIppsModifiedon());
		insprempayschEntity.setIppsModule(Objects.nonNull(insprempayschRequestBean.getModule()) ? insprempayschRequestBean.getModule().trim() : insprempayschEntity.getIppsModule());
		insprempayschEntity.setIppsPayamt(Objects.nonNull(insprempayschRequestBean.getPayamt()) ? insprempayschRequestBean.getPayamt() : insprempayschEntity.getIppsPayamt());
		insprempayschEntity.setIppsPaydate(Objects.nonNull(insprempayschRequestBean.getPaydate()) ? LocalDate.parse(insprempayschRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : insprempayschEntity.getIppsPaydate());
		insprempayschEntity.setIppsRemark(Objects.nonNull(insprempayschRequestBean.getRemark()) ? insprempayschRequestBean.getRemark().trim() : insprempayschEntity.getIppsRemark());
		insprempayschEntity.setIppsSite(GenericAuditContextHolder.getContext().getSite());
		insprempayschEntity.setIppsStatus(Objects.nonNull(insprempayschRequestBean.getStatus()) ? insprempayschRequestBean.getStatus().trim() : insprempayschEntity.getIppsStatus());
		insprempayschEntity.setIppsTranser(Objects.nonNull(insprempayschRequestBean.getTranser()) ? insprempayschRequestBean.getTranser().trim() : insprempayschEntity.getIppsTranser());
		insprempayschEntity.setIppsUserid(GenericAuditContextHolder.getContext().getUserid());

		return insprempayschEntity;
	};

}