package kraheja.arch.projbldg.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.arch.projbldg.dataentry.bean.request.MailinfoRequestBean;
import kraheja.arch.projbldg.dataentry.bean.response.MailinfoResponseBean;
import kraheja.arch.projbldg.dataentry.bean.response.MailinfoResponseBean.MailinfoResponseBeanBuilder;
import kraheja.arch.projbldg.dataentry.entity.Mailinfo;
import kraheja.arch.projbldg.dataentry.entity.MailinfoCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface MailinfoEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], MailinfoResponseBean> fetchMailinfoEntityPojoMapper = objectArray -> {
		MailinfoResponseBeanBuilder mailinfoBeanBuilder = MailinfoResponseBean.builder();
		Mailinfo mailinfoEntity = (Mailinfo) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
			mailinfoBeanBuilder.bldgcode(mailinfoEntity.getMailinfoCK().getMinfBldgcode())
					.amenity1(mailinfoEntity.getBldgAmenity1())
					.amenity2(mailinfoEntity.getBldgAmenity2())
					.amenity3(mailinfoEntity.getBldgAmenity3())
					.amenity4(mailinfoEntity.getBldgAmenity4())
					.amenity5(mailinfoEntity.getBldgAmenity5())
					.area1(mailinfoEntity.getMinfArea1())
					.area2(mailinfoEntity.getMinfArea2())
					.area3(mailinfoEntity.getMinfArea3())
					.city(mailinfoEntity.getMinfCity())
					.config1(mailinfoEntity.getMinfConfig1())
					.config2(mailinfoEntity.getMinfConfig2())
					.config3(mailinfoEntity.getMinfConfig3())
					.config4(mailinfoEntity.getMinfConfig4())
					.coy(mailinfoEntity.getMinfCoy())
					.origsite(mailinfoEntity.getMinfOrigsite())
					.otherex1(mailinfoEntity.getMinfOtherex1())
					.otherex2(mailinfoEntity.getMinfOtherex2())
					.possdate(mailinfoEntity.getMinfPossdate())
					.project(mailinfoEntity.getMinfProject())
					.rate1(mailinfoEntity.getMinfRate1())
					.rate2(mailinfoEntity.getMinfRate2())
					.rate3(mailinfoEntity.getMinfRate3())
					.remark1(mailinfoEntity.getMinfRemark1())
					.remark2(mailinfoEntity.getMinfRemark2())
					.remark3(mailinfoEntity.getMinfRemark3())
					.site(mailinfoEntity.getMinfSite())
					.tenure(mailinfoEntity.getMinfTenure())
					.term1(mailinfoEntity.getMinfTerm1())
					.term2(mailinfoEntity.getMinfTerm2())
					.term3(mailinfoEntity.getMinfTerm3())
					.term4(mailinfoEntity.getMinfTerm4())
					.term5(mailinfoEntity.getMinfTerm5())
					.term6(mailinfoEntity.getMinfTerm6())
					.today(mailinfoEntity.getMinfToday())
					.township(mailinfoEntity.getMinfTownship())
					.userid(mailinfoEntity.getMinfUserid())
					.validity1(mailinfoEntity.getMinfValidity1())
					.validity2(mailinfoEntity.getMinfValidity2())
;
			return mailinfoBeanBuilder.build();
	};

	public static Function<Object[], Mailinfo> addMailinfoEntityPojoMapper = objectArray -> {
		MailinfoRequestBean mailinfoRequestBean = (MailinfoRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		
		String code = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
		? objectArray[CommonConstraints.INSTANCE.TWO]
: null);

		Mailinfo.MailinfoBuilder mailinfobuilder = Mailinfo.builder();

mailinfobuilder
			.mailinfoCK(MailinfoCK.builder()
.minfBldgcode(code)
.build())
					.bldgAmenity1(mailinfoRequestBean.getAmenity1())
					.bldgAmenity2(mailinfoRequestBean.getAmenity2())
					.bldgAmenity3(mailinfoRequestBean.getAmenity3())
					.bldgAmenity4(mailinfoRequestBean.getAmenity4())
					.bldgAmenity5(mailinfoRequestBean.getAmenity5())
					.minfArea1(mailinfoRequestBean.getArea1())
					.minfArea2(mailinfoRequestBean.getArea2())
					.minfArea3(mailinfoRequestBean.getArea3())
					.minfCity(mailinfoRequestBean.getCity())
					.minfConfig1(mailinfoRequestBean.getConfig1())
					.minfConfig2(mailinfoRequestBean.getConfig2())
					.minfConfig3(mailinfoRequestBean.getConfig3())
					.minfConfig4(mailinfoRequestBean.getConfig4())
					.minfCoy(mailinfoRequestBean.getCoy())
					.minfOrigsite(mailinfoRequestBean.getOrigsite())
					.minfOtherex1(mailinfoRequestBean.getOtherex1())
					.minfOtherex2(mailinfoRequestBean.getOtherex2())
					.minfPossdate(mailinfoRequestBean.getPossdate())
					.minfProject(mailinfoRequestBean.getProject())
					.minfRate1(mailinfoRequestBean.getRate1())
					.minfRate2(mailinfoRequestBean.getRate2())
					.minfRate3(mailinfoRequestBean.getRate3())
					.minfRemark1(mailinfoRequestBean.getRemark1())
					.minfRemark2(mailinfoRequestBean.getRemark2())
					.minfRemark3(mailinfoRequestBean.getRemark3())
					.minfSite(GenericAuditContextHolder.getContext().getSite())
					.minfTenure(mailinfoRequestBean.getTenure())
					.minfTerm1(mailinfoRequestBean.getTerm1())
					.minfTerm2(mailinfoRequestBean.getTerm2())
					.minfTerm3(mailinfoRequestBean.getTerm3())
					.minfTerm4(mailinfoRequestBean.getTerm4())
					.minfTerm5(mailinfoRequestBean.getTerm5())
					.minfTerm6(mailinfoRequestBean.getTerm6())
					.minfToday(LocalDateTime.now())
					.minfTownship(mailinfoRequestBean.getTownship())
					.minfUserid(GenericAuditContextHolder.getContext().getUserid())
					.minfValidity1(mailinfoRequestBean.getValidity1())
					.minfValidity2(mailinfoRequestBean.getValidity2())
;

		return mailinfobuilder.build();
};

	public static BiFunction<Mailinfo, MailinfoRequestBean, Mailinfo> updateMailinfoEntityPojoMapper = (mailinfoEntity, mailinfoRequestBean) -> {
		mailinfoEntity.getMailinfoCK().setMinfBldgcode(StringUtils.isNotBlank(mailinfoRequestBean.getBldgcode()) ? mailinfoRequestBean.getBldgcode().trim() : mailinfoEntity.getMailinfoCK().getMinfBldgcode());
		mailinfoEntity.setBldgAmenity1(StringUtils.isNotBlank(mailinfoRequestBean.getAmenity1()) ? mailinfoRequestBean.getAmenity1().trim() : mailinfoEntity.getBldgAmenity1());
		mailinfoEntity.setBldgAmenity2(StringUtils.isNotBlank(mailinfoRequestBean.getAmenity2()) ? mailinfoRequestBean.getAmenity2().trim() : mailinfoEntity.getBldgAmenity2());
		mailinfoEntity.setBldgAmenity3(StringUtils.isNotBlank(mailinfoRequestBean.getAmenity3()) ? mailinfoRequestBean.getAmenity3().trim() : mailinfoEntity.getBldgAmenity3());
		mailinfoEntity.setBldgAmenity4(StringUtils.isNotBlank(mailinfoRequestBean.getAmenity4()) ? mailinfoRequestBean.getAmenity4().trim() : mailinfoEntity.getBldgAmenity4());
		mailinfoEntity.setBldgAmenity5(StringUtils.isNotBlank(mailinfoRequestBean.getAmenity5()) ? mailinfoRequestBean.getAmenity5().trim() : mailinfoEntity.getBldgAmenity5());
		mailinfoEntity.setMinfArea1(StringUtils.isNotBlank(mailinfoRequestBean.getArea1()) ? mailinfoRequestBean.getArea1().trim() : mailinfoEntity.getMinfArea1());
		mailinfoEntity.setMinfArea2(StringUtils.isNotBlank(mailinfoRequestBean.getArea2()) ? mailinfoRequestBean.getArea2().trim() : mailinfoEntity.getMinfArea2());
		mailinfoEntity.setMinfArea3(StringUtils.isNotBlank(mailinfoRequestBean.getArea3()) ? mailinfoRequestBean.getArea3().trim() : mailinfoEntity.getMinfArea3());
		mailinfoEntity.setMinfCity(StringUtils.isNotBlank(mailinfoRequestBean.getCity()) ? mailinfoRequestBean.getCity().trim() : mailinfoEntity.getMinfCity());
		mailinfoEntity.setMinfConfig1(StringUtils.isNotBlank(mailinfoRequestBean.getConfig1()) ? mailinfoRequestBean.getConfig1().trim() : mailinfoEntity.getMinfConfig1());
		mailinfoEntity.setMinfConfig2(StringUtils.isNotBlank(mailinfoRequestBean.getConfig2()) ? mailinfoRequestBean.getConfig2().trim() : mailinfoEntity.getMinfConfig2());
		mailinfoEntity.setMinfConfig3(StringUtils.isNotBlank(mailinfoRequestBean.getConfig3()) ? mailinfoRequestBean.getConfig3().trim() : mailinfoEntity.getMinfConfig3());
		mailinfoEntity.setMinfConfig4(StringUtils.isNotBlank(mailinfoRequestBean.getConfig4()) ? mailinfoRequestBean.getConfig4().trim() : mailinfoEntity.getMinfConfig4());
		mailinfoEntity.setMinfCoy(StringUtils.isNotBlank(mailinfoRequestBean.getCoy()) ? mailinfoRequestBean.getCoy().trim() : mailinfoEntity.getMinfCoy());
		mailinfoEntity.setMinfOrigsite(StringUtils.isNotBlank(mailinfoRequestBean.getOrigsite()) ? mailinfoRequestBean.getOrigsite().trim() : mailinfoEntity.getMinfOrigsite());
		mailinfoEntity.setMinfOtherex1(StringUtils.isNotBlank(mailinfoRequestBean.getOtherex1()) ? mailinfoRequestBean.getOtherex1().trim() : mailinfoEntity.getMinfOtherex1());
		mailinfoEntity.setMinfOtherex2(StringUtils.isNotBlank(mailinfoRequestBean.getOtherex2()) ? mailinfoRequestBean.getOtherex2().trim() : mailinfoEntity.getMinfOtherex2());
		//mailinfoEntity.setMinfPossdate(StringUtils.isNotBlank(mailinfoRequestBean.getPossdate()) ? mailinfoRequestBean.getPossdate().trim() : mailinfoEntity.getMinfPossdate());
		mailinfoEntity.setMinfPossdate(StringUtils.isNotBlank(mailinfoRequestBean.getPossdate()) ? mailinfoRequestBean.getPossdate().trim() : null); //NS 01.04.2023
		mailinfoEntity.setMinfProject(StringUtils.isNotBlank(mailinfoRequestBean.getProject()) ? mailinfoRequestBean.getProject().trim() : mailinfoEntity.getMinfProject());
		mailinfoEntity.setMinfRate1(StringUtils.isNotBlank(mailinfoRequestBean.getRate1()) ? mailinfoRequestBean.getRate1().trim() : mailinfoEntity.getMinfRate1());
		mailinfoEntity.setMinfRate2(StringUtils.isNotBlank(mailinfoRequestBean.getRate2()) ? mailinfoRequestBean.getRate2().trim() : mailinfoEntity.getMinfRate2());
		mailinfoEntity.setMinfRate3(StringUtils.isNotBlank(mailinfoRequestBean.getRate3()) ? mailinfoRequestBean.getRate3().trim() : mailinfoEntity.getMinfRate3());
		mailinfoEntity.setMinfRemark1(StringUtils.isNotBlank(mailinfoRequestBean.getRemark1()) ? mailinfoRequestBean.getRemark1().trim() : mailinfoEntity.getMinfRemark1());
		mailinfoEntity.setMinfRemark2(StringUtils.isNotBlank(mailinfoRequestBean.getRemark2()) ? mailinfoRequestBean.getRemark2().trim() : mailinfoEntity.getMinfRemark2());
		mailinfoEntity.setMinfRemark3(StringUtils.isNotBlank(mailinfoRequestBean.getRemark3()) ? mailinfoRequestBean.getRemark3().trim() : mailinfoEntity.getMinfRemark3());
		mailinfoEntity.setMinfSite(StringUtils.isNotBlank(mailinfoRequestBean.getSite()) ? mailinfoRequestBean.getSite().trim() : mailinfoEntity.getMinfSite());
		mailinfoEntity.setMinfTenure(StringUtils.isNotBlank(mailinfoRequestBean.getTenure()) ? mailinfoRequestBean.getTenure().trim() : mailinfoEntity.getMinfTenure());
		mailinfoEntity.setMinfTerm1(StringUtils.isNotBlank(mailinfoRequestBean.getTerm1()) ? mailinfoRequestBean.getTerm1().trim() : mailinfoEntity.getMinfTerm1());
		mailinfoEntity.setMinfTerm2(StringUtils.isNotBlank(mailinfoRequestBean.getTerm2()) ? mailinfoRequestBean.getTerm2().trim() : mailinfoEntity.getMinfTerm2());
		mailinfoEntity.setMinfTerm3(StringUtils.isNotBlank(mailinfoRequestBean.getTerm3()) ? mailinfoRequestBean.getTerm3().trim() : mailinfoEntity.getMinfTerm3());
		mailinfoEntity.setMinfTerm4(StringUtils.isNotBlank(mailinfoRequestBean.getTerm4()) ? mailinfoRequestBean.getTerm4().trim() : mailinfoEntity.getMinfTerm4());
		mailinfoEntity.setMinfTerm5(StringUtils.isNotBlank(mailinfoRequestBean.getTerm5()) ? mailinfoRequestBean.getTerm5().trim() : mailinfoEntity.getMinfTerm5());
		mailinfoEntity.setMinfTerm6(StringUtils.isNotBlank(mailinfoRequestBean.getTerm6()) ? mailinfoRequestBean.getTerm6().trim() : mailinfoEntity.getMinfTerm6());
		mailinfoEntity.setMinfToday(Objects.nonNull(mailinfoRequestBean.getToday()) ?  LocalDateTime.now()  : mailinfoEntity.getMinfToday());
		mailinfoEntity.setMinfTownship(StringUtils.isNotBlank(mailinfoRequestBean.getTownship()) ? mailinfoRequestBean.getTownship().trim() : mailinfoEntity.getMinfTownship());
		mailinfoEntity.setMinfUserid(StringUtils.isNotBlank(mailinfoRequestBean.getUserid()) ? mailinfoRequestBean.getUserid().trim() : mailinfoEntity.getMinfUserid());
		mailinfoEntity.setMinfValidity1(StringUtils.isNotBlank(mailinfoRequestBean.getValidity1()) ? mailinfoRequestBean.getValidity1().trim() : mailinfoEntity.getMinfValidity1());
		mailinfoEntity.setMinfValidity2(StringUtils.isNotBlank(mailinfoRequestBean.getValidity2()) ? mailinfoRequestBean.getValidity2().trim() : mailinfoEntity.getMinfValidity2());


		return mailinfoEntity;
	};

}
