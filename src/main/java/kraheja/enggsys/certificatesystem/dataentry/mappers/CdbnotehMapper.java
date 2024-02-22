package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.enggsys.bean.request.CdbnotehRequestBean;
import kraheja.enggsys.bean.response.CdbnotehResponseBean;
import kraheja.enggsys.bean.response.CdbnotehResponseBean.CdbnotehResponseBeanBuilder;
import kraheja.enggsys.entity.Cdbnoted;
import kraheja.enggsys.entity.Cdbnoteh;
import kraheja.enggsys.entity.Cdbnoteh.CdbnotehCK;
import kraheja.purch.debitnotes.mappers.DbnotedMapper;

public interface CdbnotehMapper {

	public static Function <Object[], CdbnotehResponseBean> fetchCdbnotehEntityPojoMapper = objectArray -> {
		CdbnotehResponseBeanBuilder cdbnotehBuilder = CdbnotehResponseBean.builder();
		Cdbnoteh  cdbnotehEntity =  (Cdbnoteh)(Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		
		List<Cdbnoted>  cdbnotedEntityList =  (List<Cdbnoted>)(Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
				: null);
		
		cdbnotehBuilder
		.dbnoteser(cdbnotehEntity.getCdbnotehCK().getCdbnhDbnoteser())
							.amount(cdbnotehEntity.getCdbnhAmount())
							.billtype(cdbnotehEntity.getCdbnhBilltype())
							.bldgcode(cdbnotehEntity.getCdbnhBldgcode())
							.certdate(Objects.nonNull(cdbnotehEntity.getCdbnhCertdate()) ? cdbnotehEntity.getCdbnhCertdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.certno(cdbnotehEntity.getCdbnhCertno())
							.contbilldt(Objects.nonNull(cdbnotehEntity.getCdbnhContbilldt()) ? cdbnotehEntity.getCdbnhContbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.contbillno(cdbnotehEntity.getCdbnhContbillno())
							.contract(cdbnotehEntity.getCdbnhContract())
							.coy(cdbnotehEntity.getCdbnhCoy())
							.date(Objects.nonNull(cdbnotehEntity.getCdbnhDate()) ? cdbnotehEntity.getCdbnhDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.description1(cdbnotehEntity.getCdbnhDescription1())
							.description2(cdbnotehEntity.getCdbnhDescription2())
							.description3(cdbnotehEntity.getCdbnhDescription3())
							.description4(cdbnotehEntity.getCdbnhDescription4())
							.description5(cdbnotehEntity.getCdbnhDescription5())
							.misbldg(cdbnotehEntity.getCdbnhMisbldg())
							.misproject(cdbnotehEntity.getCdbnhMisproject())
							.narration(cdbnotehEntity.getCdbnhNarration())
							.noofprint(cdbnotehEntity.getCdbnhNoofprint())
							.omsservyn(cdbnotehEntity.getCdbnhOmsservyn())
							.origsite(cdbnotehEntity.getCdbnhOrigsite())
							.partycode(cdbnotehEntity.getCdbnhPartycode())
							.partytype(cdbnotehEntity.getCdbnhPartytype())
							.passedby(cdbnotehEntity.getCdbnhPassedby())
							.prepby(cdbnotehEntity.getCdbnhPrepby())
							.printdate(Objects.nonNull(cdbnotehEntity.getCdbnhPrintdate()) ? cdbnotehEntity.getCdbnhPrintdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.prints(cdbnotehEntity.getCdbnhPrints())
							.printuser(cdbnotehEntity.getCdbnhPrintuser())
							.project(cdbnotehEntity.getCdbnhProject())
							.prop(cdbnotehEntity.getCdbnhProp())
							.recdby(cdbnotehEntity.getCdbnhRecdby())
							.site(cdbnotehEntity.getCdbnhSite())
							.tdsamount(cdbnotehEntity.getCdbnhTdsamount())
							.tdsperc(cdbnotehEntity.getCdbnhTdsperc())
							.today(cdbnotehEntity.getCdbnhToday())
							.userid(cdbnotehEntity.getCdbnhUserid())
							.workcode(cdbnotehEntity.getCdbnhWorkcode());
							if(CollectionUtils.isNotEmpty(cdbnotedEntityList)) {
								cdbnotehBuilder.cdbnotedResponses(CbdnotedMapper.fetchCdbnotedEntityPojoMapper.apply(new Object[] {cdbnotedEntityList}));
							}
							return cdbnotehBuilder.build();
	};
		
	public static Function<CdbnotehRequestBean, Cdbnoteh> addCdbnotehPojoEntityMapper = cdbnotehRequestBean -> {
return Cdbnoteh.builder().cdbnotehCK(CdbnotehCK.builder()
					.cdbnhDbnoteser(cdbnotehRequestBean.getDbnoteser())
		.build())
					.cdbnhAmount(Objects.nonNull(cdbnotehRequestBean.getAmount()) ? cdbnotehRequestBean.getAmount() : BigInteger.ZERO.doubleValue())
					.cdbnhBilltype(cdbnotehRequestBean.getBilltype())
					.cdbnhBldgcode(cdbnotehRequestBean.getBldgcode())
					.cdbnhCertdate(Objects.nonNull(cdbnotehRequestBean.getCertdate()) ? LocalDate.parse(cdbnotehRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.cdbnhCertno(cdbnotehRequestBean.getCertno())
					.cdbnhContbilldt(Objects.nonNull(cdbnotehRequestBean.getContbilldt()) ? LocalDate.parse(cdbnotehRequestBean.getContbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.cdbnhContbillno(cdbnotehRequestBean.getContbillno())
					.cdbnhContract(cdbnotehRequestBean.getContract())
					.cdbnhCoy(cdbnotehRequestBean.getCoy())
					.cdbnhDate(Objects.nonNull(cdbnotehRequestBean.getDate()) ? LocalDate.parse(cdbnotehRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.cdbnhDescription1(cdbnotehRequestBean.getDescription1())
					.cdbnhDescription2(cdbnotehRequestBean.getDescription2())
					.cdbnhDescription3(cdbnotehRequestBean.getDescription3())
					.cdbnhDescription4(cdbnotehRequestBean.getDescription4())
					.cdbnhDescription5(cdbnotehRequestBean.getDescription5())
					.cdbnhMisbldg(cdbnotehRequestBean.getMisbldg())
					.cdbnhMisproject(cdbnotehRequestBean.getMisproject())
					.cdbnhNarration(cdbnotehRequestBean.getNarration())
					.cdbnhNoofprint(Objects.nonNull(cdbnotehRequestBean.getNoofprint()) ? cdbnotehRequestBean.getNoofprint() : BigInteger.ZERO.intValue())
					.cdbnhOmsservyn(cdbnotehRequestBean.getOmsservyn())
					.cdbnhOrigsite(GenericAuditContextHolder.getContext().getSite())
					.cdbnhPartycode(cdbnotehRequestBean.getPartycode())
					.cdbnhPartytype(cdbnotehRequestBean.getPartytype())
					.cdbnhPassedby(cdbnotehRequestBean.getPassedby())
					.cdbnhPrepby(cdbnotehRequestBean.getPrepby())
					.cdbnhPrintdate(Objects.nonNull(cdbnotehRequestBean.getPrintdate()) ? LocalDate.parse(cdbnotehRequestBean.getPrintdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.cdbnhPrints(Objects.nonNull(cdbnotehRequestBean.getPrints()) ? cdbnotehRequestBean.getPrints() : BigInteger.ZERO.intValue())
					.cdbnhPrintuser(cdbnotehRequestBean.getPrintuser())
					.cdbnhProject(cdbnotehRequestBean.getProject())
					.cdbnhProp(cdbnotehRequestBean.getProp())
					.cdbnhRecdby(cdbnotehRequestBean.getRecdby())
					.cdbnhSite(GenericAuditContextHolder.getContext().getSite())
					.cdbnhTdsamount(Objects.nonNull(cdbnotehRequestBean.getTdsamount()) ? cdbnotehRequestBean.getTdsamount() : BigInteger.ZERO.intValue())
					.cdbnhTdsperc(Objects.nonNull(cdbnotehRequestBean.getTdsperc()) ? cdbnotehRequestBean.getTdsperc() : BigInteger.ZERO.intValue())
					.cdbnhToday(LocalDateTime.now())
					.cdbnhUserid(GenericAuditContextHolder.getContext().getUserid())
					.cdbnhWorkcode(cdbnotehRequestBean.getWorkcode())
		
.build();
} ;
	public static BiFunction<Cdbnoteh, CdbnotehRequestBean, Cdbnoteh> updateCdbnotehEntityPojoMapper = (cdbnotehEntity, cdbnotehRequestBean) -> {
		cdbnotehEntity.getCdbnotehCK().setCdbnhDbnoteser(Objects.nonNull(cdbnotehRequestBean.getDbnoteser()) ? cdbnotehRequestBean.getDbnoteser().trim() : cdbnotehEntity.getCdbnotehCK().getCdbnhDbnoteser());
		cdbnotehEntity.setCdbnhAmount(Objects.nonNull(cdbnotehRequestBean.getAmount()) ? cdbnotehRequestBean.getAmount() : cdbnotehEntity.getCdbnhAmount());
		cdbnotehEntity.setCdbnhBilltype(Objects.nonNull(cdbnotehRequestBean.getBilltype()) ? cdbnotehRequestBean.getBilltype().trim() : cdbnotehEntity.getCdbnhBilltype());
		cdbnotehEntity.setCdbnhBldgcode(Objects.nonNull(cdbnotehRequestBean.getBldgcode()) ? cdbnotehRequestBean.getBldgcode().trim() : cdbnotehEntity.getCdbnhBldgcode());
		cdbnotehEntity.setCdbnhCertdate(Objects.nonNull(cdbnotehRequestBean.getCertdate()) ? LocalDate.parse(cdbnotehRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : cdbnotehEntity.getCdbnhCertdate());
		cdbnotehEntity.setCdbnhCertno(Objects.nonNull(cdbnotehRequestBean.getCertno()) ? cdbnotehRequestBean.getCertno().trim() : cdbnotehEntity.getCdbnhCertno());
		cdbnotehEntity.setCdbnhContbilldt(Objects.nonNull(cdbnotehRequestBean.getContbilldt()) ? LocalDate.parse(cdbnotehRequestBean.getContbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : cdbnotehEntity.getCdbnhContbilldt());
		cdbnotehEntity.setCdbnhContbillno(Objects.nonNull(cdbnotehRequestBean.getContbillno()) ? cdbnotehRequestBean.getContbillno().trim() : cdbnotehEntity.getCdbnhContbillno());
		cdbnotehEntity.setCdbnhContract(Objects.nonNull(cdbnotehRequestBean.getContract()) ? cdbnotehRequestBean.getContract().trim() : cdbnotehEntity.getCdbnhContract());
		cdbnotehEntity.setCdbnhCoy(Objects.nonNull(cdbnotehRequestBean.getCoy()) ? cdbnotehRequestBean.getCoy().trim() : cdbnotehEntity.getCdbnhCoy());
		cdbnotehEntity.setCdbnhDate(Objects.nonNull(cdbnotehRequestBean.getDate()) ? LocalDate.parse(cdbnotehRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : cdbnotehEntity.getCdbnhDate());
		cdbnotehEntity.setCdbnhDescription1(Objects.nonNull(cdbnotehRequestBean.getDescription1()) ? cdbnotehRequestBean.getDescription1().trim() : cdbnotehEntity.getCdbnhDescription1());
		cdbnotehEntity.setCdbnhDescription2(Objects.nonNull(cdbnotehRequestBean.getDescription2()) ? cdbnotehRequestBean.getDescription2().trim() : cdbnotehEntity.getCdbnhDescription2());
		cdbnotehEntity.setCdbnhDescription3(Objects.nonNull(cdbnotehRequestBean.getDescription3()) ? cdbnotehRequestBean.getDescription3().trim() : cdbnotehEntity.getCdbnhDescription3());
		cdbnotehEntity.setCdbnhDescription4(Objects.nonNull(cdbnotehRequestBean.getDescription4()) ? cdbnotehRequestBean.getDescription4().trim() : cdbnotehEntity.getCdbnhDescription4());
		cdbnotehEntity.setCdbnhDescription5(Objects.nonNull(cdbnotehRequestBean.getDescription5()) ? cdbnotehRequestBean.getDescription5().trim() : cdbnotehEntity.getCdbnhDescription5());
		cdbnotehEntity.setCdbnhMisbldg(Objects.nonNull(cdbnotehRequestBean.getMisbldg()) ? cdbnotehRequestBean.getMisbldg().trim() : cdbnotehEntity.getCdbnhMisbldg());
		cdbnotehEntity.setCdbnhMisproject(Objects.nonNull(cdbnotehRequestBean.getMisproject()) ? cdbnotehRequestBean.getMisproject().trim() : cdbnotehEntity.getCdbnhMisproject());
		cdbnotehEntity.setCdbnhNarration(Objects.nonNull(cdbnotehRequestBean.getNarration()) ? cdbnotehRequestBean.getNarration().trim() : cdbnotehEntity.getCdbnhNarration());
		cdbnotehEntity.setCdbnhNoofprint(Objects.nonNull(cdbnotehRequestBean.getNoofprint()) ? cdbnotehRequestBean.getNoofprint() : cdbnotehEntity.getCdbnhNoofprint());
		cdbnotehEntity.setCdbnhOmsservyn(Objects.nonNull(cdbnotehRequestBean.getOmsservyn()) ? cdbnotehRequestBean.getOmsservyn().trim() : cdbnotehEntity.getCdbnhOmsservyn());
		cdbnotehEntity.setCdbnhOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		cdbnotehEntity.setCdbnhPartycode(Objects.nonNull(cdbnotehRequestBean.getPartycode()) ? cdbnotehRequestBean.getPartycode().trim() : cdbnotehEntity.getCdbnhPartycode());
		cdbnotehEntity.setCdbnhPartytype(Objects.nonNull(cdbnotehRequestBean.getPartytype()) ? cdbnotehRequestBean.getPartytype().trim() : cdbnotehEntity.getCdbnhPartytype());
		cdbnotehEntity.setCdbnhPassedby(Objects.nonNull(cdbnotehRequestBean.getPassedby()) ? cdbnotehRequestBean.getPassedby().trim() : cdbnotehEntity.getCdbnhPassedby());
		cdbnotehEntity.setCdbnhPrepby(Objects.nonNull(cdbnotehRequestBean.getPrepby()) ? cdbnotehRequestBean.getPrepby().trim() : cdbnotehEntity.getCdbnhPrepby());
		cdbnotehEntity.setCdbnhPrintdate(Objects.nonNull(cdbnotehRequestBean.getPrintdate()) ? LocalDate.parse(cdbnotehRequestBean.getPrintdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : cdbnotehEntity.getCdbnhPrintdate());
		cdbnotehEntity.setCdbnhPrints(Objects.nonNull(cdbnotehRequestBean.getPrints()) ? cdbnotehRequestBean.getPrints() : cdbnotehEntity.getCdbnhPrints());
		cdbnotehEntity.setCdbnhPrintuser(Objects.nonNull(cdbnotehRequestBean.getPrintuser()) ? cdbnotehRequestBean.getPrintuser().trim() : cdbnotehEntity.getCdbnhPrintuser());
		cdbnotehEntity.setCdbnhProject(Objects.nonNull(cdbnotehRequestBean.getProject()) ? cdbnotehRequestBean.getProject().trim() : cdbnotehEntity.getCdbnhProject());
		cdbnotehEntity.setCdbnhProp(Objects.nonNull(cdbnotehRequestBean.getProp()) ? cdbnotehRequestBean.getProp().trim() : cdbnotehEntity.getCdbnhProp());
		cdbnotehEntity.setCdbnhRecdby(Objects.nonNull(cdbnotehRequestBean.getRecdby()) ? cdbnotehRequestBean.getRecdby().trim() : cdbnotehEntity.getCdbnhRecdby());
		cdbnotehEntity.setCdbnhSite(GenericAuditContextHolder.getContext().getSite()) ; 
		cdbnotehEntity.setCdbnhTdsamount(Objects.nonNull(cdbnotehRequestBean.getTdsamount()) ? cdbnotehRequestBean.getTdsamount() : cdbnotehEntity.getCdbnhTdsamount());
		cdbnotehEntity.setCdbnhTdsperc(Objects.nonNull(cdbnotehRequestBean.getTdsperc()) ? cdbnotehRequestBean.getTdsperc() : cdbnotehEntity.getCdbnhTdsperc());
		cdbnotehEntity.setCdbnhToday(LocalDateTime.now()) ; 
		cdbnotehEntity.setCdbnhUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		cdbnotehEntity.setCdbnhWorkcode(Objects.nonNull(cdbnotehRequestBean.getWorkcode()) ? cdbnotehRequestBean.getWorkcode().trim() : cdbnotehEntity.getCdbnhWorkcode());


		return cdbnotehEntity;
	};

}
