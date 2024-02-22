package kraheja.adminexp.insurance.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolendorsementRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InspolendorsementResponseBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InspolendorsementResponseBean.InspolendorsementResponseBeanBuilder;
import kraheja.adminexp.insurance.dataentry.entity.Inspolendorsement;
import kraheja.adminexp.insurance.dataentry.entity.InspolendorsementCK;

public interface InspolendorsementEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<InspolendorsementResponseBean>> fetchInspolendorsementEntityPojoMapper = objectArray -> {
		InspolendorsementResponseBeanBuilder inspolendorsementBeanBuilder = InspolendorsementResponseBean.builder();
		List<Inspolendorsement> inspolendorsementEntityList = (List<Inspolendorsement>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return inspolendorsementEntityList.stream().map(inspolendorsementEntity -> {
			inspolendorsementBeanBuilder.polendorseid(inspolendorsementEntity.getInspolendorsementCK().getInpePolendorseid())
			
					.policyid(inspolendorsementEntity.getInpepolicyid())
					.policyno(inspolendorsementEntity.getInpepolicyno())
					.polendorsenum(inspolendorsementEntity.getInpePolendorsenum())
					.certnum(inspolendorsementEntity.getInpeCertnum())
					.certstat(inspolendorsementEntity.getInpeCertstat())
					.endorsetp(inspolendorsementEntity.getInpeEndorsetp())
					.endrdetail(inspolendorsementEntity.getInpeEndrdetail())
					.endrfrdt(Objects.nonNull(inspolendorsementEntity.getInpeEndrfrdt()) ? inspolendorsementEntity.getInpeEndrfrdt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.endrinsval(inspolendorsementEntity.getInpeEndrinsval())
					.endrpayby(inspolendorsementEntity.getInpeEndrpayby())
					.endrpaymode(inspolendorsementEntity.getInpeEndrpaymode())
					.endrpremium(inspolendorsementEntity.getInpeEndrpremium())
					.endruptodt(Objects.nonNull(inspolendorsementEntity.getInpeEndruptodt()) ? inspolendorsementEntity.getInpeEndruptodt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.ipaddress(inspolendorsementEntity.getInpeIpaddress())
					.machinename(inspolendorsementEntity.getInpeMachinename())
					.modifiedon(Objects.nonNull(inspolendorsementEntity.getInpeModifiedon()) ? inspolendorsementEntity.getInpeModifiedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.module(inspolendorsementEntity.getInpeModule())
					.polendorsedt(Objects.nonNull(inspolendorsementEntity.getInpePolendorsedt()) ? inspolendorsementEntity.getInpePolendorsedt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.site(inspolendorsementEntity.getInpeSite())
					.transer(inspolendorsementEntity.getInpeTranser())
					.userid(inspolendorsementEntity.getInpeUserid())
;
			return inspolendorsementBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Inspolendorsement> addInspolendorsementEntityPojoMapper = objectArray -> {
		InspolendorsementRequestBean inspolendorsementRequestBean = (InspolendorsementRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String Polendorseid = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		Inspolendorsement.InspolendorsementBuilder inspolendorsementbuilder = Inspolendorsement.builder();

inspolendorsementbuilder
			.inspolendorsementCK(InspolendorsementCK.builder()
					.inpePolendorseid(Polendorseid)
		.build())
					.inpepolicyid(inspolendorsementRequestBean.getPolicyid())
					.inpepolicyno(inspolendorsementRequestBean.getPolicyno())
					.inpePolendorsenum(inspolendorsementRequestBean.getPolendorsenum())
					.inpeCertnum(inspolendorsementRequestBean.getCertnum())
					.inpeCertstat(inspolendorsementRequestBean.getCertstat())
					.inpeEndorsetp(Objects.nonNull(inspolendorsementRequestBean.getEndorsetp()) ? inspolendorsementRequestBean.getEndorsetp().trim(): null)
					.inpeEndrdetail(inspolendorsementRequestBean.getEndrdetail())
					.inpeEndrfrdt(Objects.nonNull(inspolendorsementRequestBean.getEndrfrdt()) ? LocalDate.parse(inspolendorsementRequestBean.getEndrfrdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inpeEndrinsval(inspolendorsementRequestBean.getEndrinsval())
					.inpeEndrpayby(Objects.nonNull(inspolendorsementRequestBean.getEndrpayby()) ? inspolendorsementRequestBean.getEndrpayby().trim(): null)
					.inpeEndrpaymode(Objects.nonNull(inspolendorsementRequestBean.getEndrpaymode()) ? inspolendorsementRequestBean.getEndrpaymode().trim(): null)
					.inpeEndrpremium(inspolendorsementRequestBean.getEndrpremium())
					.inpeEndruptodt(Objects.nonNull(inspolendorsementRequestBean.getEndruptodt()) ? LocalDate.parse(inspolendorsementRequestBean.getEndruptodt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inpeIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.inpeMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.inpeModifiedon(LocalDateTime.now())
					.inpeModule(inspolendorsementRequestBean.getModule())
					.inpePolendorsedt(Objects.nonNull(inspolendorsementRequestBean.getPolendorsedt()) ? LocalDate.parse(inspolendorsementRequestBean.getPolendorsedt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inpeSite(GenericAuditContextHolder.getContext().getSite())
					.inpeTranser(inspolendorsementRequestBean.getTranser())
					.inpeUserid(GenericAuditContextHolder.getContext().getUserid());


		return inspolendorsementbuilder.build();
};

	public static BiFunction<Inspolendorsement, InspolendorsementRequestBean, Inspolendorsement> updateInspolendorsementEntityPojoMapper = (inspolendorsementEntity, inspolendorsementRequestBean) -> {
		inspolendorsementEntity.getInspolendorsementCK().setInpePolendorseid(Objects.nonNull(inspolendorsementRequestBean.getPolendorseid()) ? inspolendorsementRequestBean.getPolendorseid().trim() : inspolendorsementEntity.getInspolendorsementCK().getInpePolendorseid());
		inspolendorsementEntity.setInpepolicyid(Objects.nonNull(inspolendorsementRequestBean.getPolicyid()) ? inspolendorsementRequestBean.getPolicyid().trim() : inspolendorsementEntity.getInpepolicyid());
		inspolendorsementEntity.setInpepolicyno(Objects.nonNull(inspolendorsementRequestBean.getPolicyno()) ? inspolendorsementRequestBean.getPolicyno().trim() : inspolendorsementEntity.getInpepolicyno());
		inspolendorsementEntity.setInpePolendorsenum(Objects.nonNull(inspolendorsementRequestBean.getPolendorsenum()) ? inspolendorsementRequestBean.getPolendorsenum().trim() : inspolendorsementEntity.getInpePolendorsenum());
		inspolendorsementEntity.setInpeCertnum(Objects.nonNull(inspolendorsementRequestBean.getCertnum()) ? inspolendorsementRequestBean.getCertnum().trim() : inspolendorsementEntity.getInpeCertnum());
		inspolendorsementEntity.setInpeCertstat(Objects.nonNull(inspolendorsementRequestBean.getCertstat()) ? inspolendorsementRequestBean.getCertstat().trim() : inspolendorsementEntity.getInpeCertstat());
		inspolendorsementEntity.setInpeEndorsetp(Objects.nonNull(inspolendorsementRequestBean.getEndorsetp()) ? inspolendorsementRequestBean.getEndorsetp().trim() : inspolendorsementEntity.getInpeEndorsetp());
		inspolendorsementEntity.setInpeEndrdetail(Objects.nonNull(inspolendorsementRequestBean.getEndrdetail()) ? inspolendorsementRequestBean.getEndrdetail().trim() : inspolendorsementEntity.getInpeEndrdetail());
		inspolendorsementEntity.setInpeEndrfrdt(Objects.nonNull(inspolendorsementRequestBean.getEndrfrdt()) ? LocalDate.parse(inspolendorsementRequestBean.getEndrfrdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : inspolendorsementEntity.getInpeEndrfrdt());
		inspolendorsementEntity.setInpeEndrinsval(Objects.nonNull(inspolendorsementRequestBean.getEndrinsval()) ? inspolendorsementRequestBean.getEndrinsval() : inspolendorsementEntity.getInpeEndrinsval());
		inspolendorsementEntity.setInpeEndrpayby(Objects.nonNull(inspolendorsementRequestBean.getEndrpayby()) ? inspolendorsementRequestBean.getEndrpayby().trim() : inspolendorsementEntity.getInpeEndrpayby());
		inspolendorsementEntity.setInpeEndrpaymode(Objects.nonNull(inspolendorsementRequestBean.getEndrpaymode()) ? inspolendorsementRequestBean.getEndrpaymode().trim() : inspolendorsementEntity.getInpeEndrpaymode());
		inspolendorsementEntity.setInpeEndrpremium(Objects.nonNull(inspolendorsementRequestBean.getEndrpremium()) ? inspolendorsementRequestBean.getEndrpremium() : inspolendorsementEntity.getInpeEndrpremium());
		inspolendorsementEntity.setInpeEndruptodt(Objects.nonNull(inspolendorsementRequestBean.getEndruptodt()) ? LocalDate.parse(inspolendorsementRequestBean.getEndruptodt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : inspolendorsementEntity.getInpeEndruptodt());
		inspolendorsementEntity.setInpeIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress());
		inspolendorsementEntity.setInpeMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName());
		inspolendorsementEntity.setInpeModifiedon(LocalDateTime.now());
		inspolendorsementEntity.setInpeModule(Objects.nonNull(inspolendorsementRequestBean.getModule()) ? inspolendorsementRequestBean.getModule().trim() : inspolendorsementEntity.getInpeModule());
		inspolendorsementEntity.setInpePolendorsedt(Objects.nonNull(inspolendorsementRequestBean.getPolendorsedt()) ? LocalDate.parse(inspolendorsementRequestBean.getPolendorsedt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : inspolendorsementEntity.getInpePolendorsedt());
		inspolendorsementEntity.setInpeSite(GenericAuditContextHolder.getContext().getSite());
		inspolendorsementEntity.setInpeTranser(Objects.nonNull(inspolendorsementRequestBean.getTranser()) ? inspolendorsementRequestBean.getTranser().trim() : inspolendorsementEntity.getInpeTranser());
		inspolendorsementEntity.setInpeUserid(GenericAuditContextHolder.getContext().getUserid());
		inspolendorsementEntity.setInpeSite(GenericAuditContextHolder.getContext().getSite());
		inspolendorsementEntity.setInpeTranser(StringUtils.isNotBlank(inspolendorsementRequestBean.getTranser()) ? inspolendorsementRequestBean.getTranser().trim() : inspolendorsementEntity.getInpeTranser());
		inspolendorsementEntity.setInpeUserid(GenericAuditContextHolder.getContext().getUserid());
		inspolendorsementEntity.setInpeTranser(Objects.nonNull(inspolendorsementRequestBean.getTranser()) ? inspolendorsementRequestBean.getTranser().trim() : inspolendorsementEntity.getInpeTranser());
		


		return inspolendorsementEntity;
	};

}
