package kraheja.adminexp.insurance.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.GenericApplicationContext;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.purch.entity.Pbilld;
import kraheja.purch.entity.Pbillvat;
import kraheja.purch.purchasebills.mappers.PbilldMapper;
import kraheja.purch.purchasebills.mappers.PbillvatMapper;
import kraheja.adminexp.insurance.dataentry.bean.request.InspolicyRequestBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InspolicyResponseBean;
import kraheja.adminexp.insurance.dataentry.bean.response.InspolicyResponseBean.InspolicyResponseBeanBuilder;
import kraheja.adminexp.insurance.dataentry.entity.Insassetiteminsured;
import kraheja.adminexp.insurance.dataentry.entity.Inspolicy;
import kraheja.adminexp.insurance.dataentry.entity.InspolicyCK;
import kraheja.adminexp.insurance.dataentry.entity.Insprempaysch;

public interface InspolicyEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<InspolicyResponseBean>> fetchInspolicyEntityPojoMapper = objectArray -> {
		InspolicyResponseBeanBuilder inspolicyBeanBuilder = InspolicyResponseBean.builder();
		List<Inspolicy> inspolicyEntityList = (List<Inspolicy>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		List<Insprempaysch> insprempayschEntity = (List<Insprempaysch>) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		List<Insassetiteminsured> insassetiteminsuredEntity = (List<Insassetiteminsured>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		return inspolicyEntityList.stream().map(inspolicyEntity -> {
			inspolicyBeanBuilder.policyid(inspolicyEntity.getInspolicyCK().getInpPolicyid())
					.policynumber(inspolicyEntity.getInspolicyCK().getInpPolicynumber())
					.acholdername(inspolicyEntity.getInpAcholdername())
					.acnumber(inspolicyEntity.getInpAcnumber())
					.agent(inspolicyEntity.getInpAgent())
					.assetlocation(inspolicyEntity.getInpAssetlocation())
					.bank(inspolicyEntity.getInpBank())
					.bldgcode(inspolicyEntity.getInpBldgcode())
					.branch(inspolicyEntity.getInpBranch())
					.covunder(inspolicyEntity.getInpCovunder())
					.coycode(inspolicyEntity.getInpCoycode())
					.deductible(inspolicyEntity.getInpDeductible())
					.fromdate(Objects.nonNull(inspolicyEntity.getInpFromdate()) ? inspolicyEntity.getInpFromdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.ifsc(inspolicyEntity.getInpIfsc())
					.inscoy(inspolicyEntity.getInpInscoy())
					.insuredval(inspolicyEntity.getInpInsuredval())
					.ipaddress(inspolicyEntity.getInpIpaddress())
					.it_section(inspolicyEntity.getInpIt_Section())
					.machinename(inspolicyEntity.getInpMachinename())
					.maturitydate(Objects.nonNull(inspolicyEntity.getInpMaturitydate()) ? inspolicyEntity.getInpMaturitydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.modifiedon(Objects.nonNull(inspolicyEntity.getInpModifiedon()) ? inspolicyEntity.getInpModifiedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.module(inspolicyEntity.getInpModule())
					.nominationdet(inspolicyEntity.getInpNominationdet())
					.payby(inspolicyEntity.getInpPayby())
					.paymode(inspolicyEntity.getInpPaymode())
					.personinsured(inspolicyEntity.getInpPersoninsured())
					.policysubtype(inspolicyEntity.getInpPolicysubtype())
					.policytype(inspolicyEntity.getInpPolicytype())
					.premiumfreq(inspolicyEntity.getInpPremiumfreq())
					.prevpolicynumber(inspolicyEntity.getInpPrevpolicynumber())
					.renewedyn(inspolicyEntity.getInpRenewedyn())
					.site(inspolicyEntity.getInpSite())
					.staff1(inspolicyEntity.getInpStaff1())
					.staff2(inspolicyEntity.getInpStaff2())
					.status(inspolicyEntity.getInpStatus())
					.userid(inspolicyEntity.getInpUserid())
;
			if(CollectionUtils.isNotEmpty(insprempayschEntity))
				inspolicyBeanBuilder.insprempayschResponseBeanList(InsprempayschEntityPojoMapper.fetchInsprempayschEntityPojoMapperlist.apply(new Object[] {insprempayschEntity}));

			if(CollectionUtils.isNotEmpty(insassetiteminsuredEntity)) 
				inspolicyBeanBuilder.insassetiteminsuredResponseBeanList(InsassetiteminsuredEntityPojoMapper.fetchInsassetiteminsuredEntityPojoMapper.apply(new Object[] {insassetiteminsuredEntity}));
			
			return inspolicyBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List <Inspolicy>> addInspolicyEntityPojoMapper = objectArray -> {
		@SuppressWarnings("unchecked")
		List <InspolicyRequestBean> inspolicyRequestBeanList = (List <InspolicyRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String policyId = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
	return	inspolicyRequestBeanList.stream().map(inspolicyRequestBean ->{
		return	Inspolicy.builder()
			.inspolicyCK(InspolicyCK.builder()
					.inpPolicyid(policyId)
					.inpPolicynumber(inspolicyRequestBean.getPolicynumber())
		.build())
					.inpAcholdername(inspolicyRequestBean.getAcholdername())
					.inpAcnumber(inspolicyRequestBean.getAcnumber())
					.inpAgent(inspolicyRequestBean.getAgent())
					.inpAssetlocation(inspolicyRequestBean.getAssetlocation())
					.inpBank(inspolicyRequestBean.getBank())
					.inpBldgcode(inspolicyRequestBean.getBldgcode())
					.inpBranch(inspolicyRequestBean.getBranch())
					.inpCovunder(inspolicyRequestBean.getCovunder())
					.inpCoycode(inspolicyRequestBean.getCoycode())
					.inpDeductible(inspolicyRequestBean.getDeductible())
					.inpFromdate(Objects.nonNull(inspolicyRequestBean.getFromdate()) ? LocalDate.parse(inspolicyRequestBean.getFromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inpIfsc(inspolicyRequestBean.getIfsc())
					.inpInscoy(inspolicyRequestBean.getInscoy())
					.inpInsuredval(inspolicyRequestBean.getInsuredval())
					.inpIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress())
					.inpIt_Section(inspolicyRequestBean.getIt_section())
					.inpMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName())
					.inpMaturitydate(Objects.nonNull(inspolicyRequestBean.getMaturitydate()) ? LocalDate.parse(inspolicyRequestBean.getMaturitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.inpModifiedon(LocalDateTime.now())
					.inpModule(inspolicyRequestBean.getModule())
					.inpNominationdet(inspolicyRequestBean.getNominationdet())
					.inpPayby(inspolicyRequestBean.getPayby())
					.inpPaymode(inspolicyRequestBean.getPaymode())
					.inpPersoninsured(inspolicyRequestBean.getPersoninsured())
					.inpPolicysubtype(inspolicyRequestBean.getPolicysubtype())
					.inpPolicytype(inspolicyRequestBean.getPolicytype())
					.inpPremiumfreq(inspolicyRequestBean.getPremiumfreq())
					.inpPrevpolicynumber(inspolicyRequestBean.getPrevpolicynumber())
					.inpRenewedyn(inspolicyRequestBean.getRenewedyn())
					.inpSite(GenericAuditContextHolder.getContext().getSite())
					.inpStaff1(inspolicyRequestBean.getStaff1())
					.inpStaff2(inspolicyRequestBean.getStaff2())
					.inpStatus(inspolicyRequestBean.getStatus())
					.inpUserid(GenericAuditContextHolder.getContext().getUserid())
					.build()
		;
		}).collect(Collectors.toList());
};

	public static BiFunction<Inspolicy, InspolicyRequestBean, Inspolicy> updateInspolicyEntityPojoMapper = (inspolicyEntity, inspolicyRequestBean) -> {
		inspolicyEntity.getInspolicyCK().setInpPolicyid(Objects.nonNull(inspolicyRequestBean.getPolicyid()) ? inspolicyRequestBean.getPolicyid().trim() : inspolicyEntity.getInspolicyCK().getInpPolicyid());
		inspolicyEntity.getInspolicyCK().setInpPolicynumber(Objects.nonNull(inspolicyRequestBean.getPolicynumber()) ? inspolicyRequestBean.getPolicynumber().trim() : inspolicyEntity.getInspolicyCK().getInpPolicynumber());
		inspolicyEntity.setInpAcholdername(Objects.nonNull(inspolicyRequestBean.getAcholdername()) ? inspolicyRequestBean.getAcholdername().trim() : inspolicyEntity.getInpAcholdername());
		inspolicyEntity.setInpAcnumber(Objects.nonNull(inspolicyRequestBean.getAcnumber()) ? inspolicyRequestBean.getAcnumber().trim() : inspolicyEntity.getInpAcnumber());
		inspolicyEntity.setInpAgent(Objects.nonNull(inspolicyRequestBean.getAgent()) ? inspolicyRequestBean.getAgent().trim() : inspolicyEntity.getInpAgent());
		inspolicyEntity.setInpAssetlocation(Objects.nonNull(inspolicyRequestBean.getAssetlocation()) ? inspolicyRequestBean.getAssetlocation().trim() : inspolicyEntity.getInpAssetlocation());
		inspolicyEntity.setInpBank(Objects.nonNull(inspolicyRequestBean.getBank()) ? inspolicyRequestBean.getBank().trim() : inspolicyEntity.getInpBank());
		inspolicyEntity.setInpBldgcode(Objects.nonNull(inspolicyRequestBean.getBldgcode()) ? inspolicyRequestBean.getBldgcode().trim() : inspolicyEntity.getInpBldgcode());
		inspolicyEntity.setInpBranch(Objects.nonNull(inspolicyRequestBean.getBranch()) ? inspolicyRequestBean.getBranch().trim() : inspolicyEntity.getInpBranch());
		inspolicyEntity.setInpCovunder(Objects.nonNull(inspolicyRequestBean.getCovunder()) ? inspolicyRequestBean.getCovunder().trim() : inspolicyEntity.getInpCovunder());
		inspolicyEntity.setInpCoycode(Objects.nonNull(inspolicyRequestBean.getCoycode()) ? inspolicyRequestBean.getCoycode().trim() : inspolicyEntity.getInpCoycode());
		inspolicyEntity.setInpDeductible(Objects.nonNull(inspolicyRequestBean.getDeductible()) ? inspolicyRequestBean.getDeductible().trim() : inspolicyEntity.getInpDeductible());
		inspolicyEntity.setInpFromdate(Objects.nonNull(inspolicyRequestBean.getFromdate()) ? LocalDate.parse(inspolicyRequestBean.getFromdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : inspolicyEntity.getInpFromdate());
		inspolicyEntity.setInpIfsc(Objects.nonNull(inspolicyRequestBean.getIfsc()) ? inspolicyRequestBean.getIfsc().trim() : inspolicyEntity.getInpIfsc());
		inspolicyEntity.setInpInscoy(Objects.nonNull(inspolicyRequestBean.getInscoy()) ? inspolicyRequestBean.getInscoy().trim() : inspolicyEntity.getInpInscoy());
		inspolicyEntity.setInpInsuredval(Objects.nonNull(inspolicyRequestBean.getInsuredval()) ? inspolicyRequestBean.getInsuredval() : inspolicyEntity.getInpInsuredval());
		inspolicyEntity.setInpIpaddress(CommonUtils.INSTANCE.getClientConfig().getIpAddress());
		inspolicyEntity.setInpIt_Section(Objects.nonNull(inspolicyRequestBean.getIt_section()) ? inspolicyRequestBean.getIt_section().trim() : inspolicyEntity.getInpIt_Section());
		inspolicyEntity.setInpMachinename(CommonUtils.INSTANCE.getClientConfig().getMachineName());
		inspolicyEntity.setInpMaturitydate(Objects.nonNull(inspolicyRequestBean.getMaturitydate()) ? LocalDate.parse(inspolicyRequestBean.getMaturitydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : inspolicyEntity.getInpMaturitydate());
		inspolicyEntity.setInpModifiedon(LocalDateTime.now());
		inspolicyEntity.setInpModule(Objects.nonNull(inspolicyRequestBean.getModule()) ? inspolicyRequestBean.getModule().trim() : inspolicyEntity.getInpModule());
		inspolicyEntity.setInpNominationdet(Objects.nonNull(inspolicyRequestBean.getNominationdet()) ? inspolicyRequestBean.getNominationdet().trim() : inspolicyEntity.getInpNominationdet());
		inspolicyEntity.setInpPayby(Objects.nonNull(inspolicyRequestBean.getPayby()) ? inspolicyRequestBean.getPayby().trim() : inspolicyEntity.getInpPayby());
		inspolicyEntity.setInpPaymode(Objects.nonNull(inspolicyRequestBean.getPaymode()) ? inspolicyRequestBean.getPaymode().trim() : inspolicyEntity.getInpPaymode());
		inspolicyEntity.setInpPersoninsured(Objects.nonNull(inspolicyRequestBean.getPersoninsured()) ? inspolicyRequestBean.getPersoninsured().trim() : inspolicyEntity.getInpPersoninsured());
		inspolicyEntity.setInpPolicysubtype(Objects.nonNull(inspolicyRequestBean.getPolicysubtype()) ? inspolicyRequestBean.getPolicysubtype().trim() : inspolicyEntity.getInpPolicysubtype());
		inspolicyEntity.setInpPolicytype(Objects.nonNull(inspolicyRequestBean.getPolicytype()) ? inspolicyRequestBean.getPolicytype().trim() : inspolicyEntity.getInpPolicytype());
		inspolicyEntity.setInpPremiumfreq(Objects.nonNull(inspolicyRequestBean.getPremiumfreq()) ? inspolicyRequestBean.getPremiumfreq().trim() : inspolicyEntity.getInpPremiumfreq());
		inspolicyEntity.setInpPrevpolicynumber(Objects.nonNull(inspolicyRequestBean.getPrevpolicynumber()) ? inspolicyRequestBean.getPrevpolicynumber().trim() : inspolicyEntity.getInpPrevpolicynumber());
		inspolicyEntity.setInpRenewedyn(Objects.nonNull(inspolicyRequestBean.getRenewedyn()) ? inspolicyRequestBean.getRenewedyn().trim() : inspolicyEntity.getInpRenewedyn());
		inspolicyEntity.setInpSite(GenericAuditContextHolder.getContext().getSite());
		inspolicyEntity.setInpStaff1(Objects.nonNull(inspolicyRequestBean.getStaff1()) ? inspolicyRequestBean.getStaff1().trim() : inspolicyEntity.getInpStaff1());
		inspolicyEntity.setInpStaff2(Objects.nonNull(inspolicyRequestBean.getStaff2()) ? inspolicyRequestBean.getStaff2().trim() : inspolicyEntity.getInpStaff2());
		inspolicyEntity.setInpStatus(Objects.nonNull(inspolicyRequestBean.getStatus()) ? inspolicyRequestBean.getStatus().trim() : inspolicyEntity.getInpStatus());
		inspolicyEntity.setInpUserid(GenericAuditContextHolder.getContext().getUserid());


		return inspolicyEntity;
	};

}