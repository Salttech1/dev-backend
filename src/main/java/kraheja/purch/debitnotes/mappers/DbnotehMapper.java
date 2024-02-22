package kraheja.purch.debitnotes.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.purch.bean.request.DbnotehRequestBean;
import kraheja.purch.bean.response.DbnotehResponseBean;
import kraheja.purch.bean.response.DbnotehResponseBean.DbnotehResponseBeanBuilder;
import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.DbnotehCK;
import kraheja.purch.entity.Dbnotevat;

public interface DbnotehMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<DbnotehResponseBean>> fetchDbnotehEntityPojoMapper = objectArray -> {
		DbnotehResponseBeanBuilder dbnotehBeanBuilder = DbnotehResponseBean.builder();
		List<Dbnoteh> dbnotehEntityList = (List<Dbnoteh>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		
		List<Dbnoted> dbnotedEntityList = (List<Dbnoted>) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
				? objectArray[BigInteger.ONE.intValue()]
				: null);
		
		List<Dbnotevat> dbnotevatEntityList = (List<Dbnotevat>) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
				? objectArray[CommonConstraints.INSTANCE.TWO]
				: null);
		return dbnotehEntityList.stream().map(dbnotehEntity -> {
			dbnotehBeanBuilder					
			.amount(dbnotehEntity.getDbnhAmount())
					.authdate(Objects.nonNull(dbnotehEntity.getDbnhAuthdate()) ? dbnotehEntity.getDbnhAuthdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.authno(dbnotehEntity.getDbnhAuthno())
					.billtype(dbnotehEntity.getDbnhBilltype())
					.bldgcode(dbnotehEntity.getDbnhBldgcode())
					.coy(dbnotehEntity.getDbnhCoy())
					.date(Objects.nonNull(dbnotehEntity.getDbnhDate()) ? dbnotehEntity.getDbnhDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.description1(dbnotehEntity.getDbnhDescription1())
					.description2(dbnotehEntity.getDbnhDescription2())
					.description3(dbnotehEntity.getDbnhDescription3())
					.description4(dbnotehEntity.getDbnhDescription4())
					.description5(dbnotehEntity.getDbnhDescription5())
					.misbldg(dbnotehEntity.getDbnhMisbldg())
					.misproject(dbnotehEntity.getDbnhMisproject())
					.narration(dbnotehEntity.getDbnhNarration())
					.noofprint(dbnotehEntity.getDbnhNoofprint())
					.omspurcyn(dbnotehEntity.getDbnhOmspurcyn())
					.origsite(dbnotehEntity.getDbnhOrigsite())
					.partycode(dbnotehEntity.getDbnhPartycode())
					.partytype(dbnotehEntity.getDbnhPartytype())
					.passedby(dbnotehEntity.getDbnhPassedby())
					.prepby(dbnotehEntity.getDbnhPrepby())
					.printdate(Objects.nonNull(dbnotehEntity.getDbnhPrintdate()) ? dbnotehEntity.getDbnhPrintdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.prints(dbnotehEntity.getDbnhPrints())
					.printuser(dbnotehEntity.getDbnhPrintuser())
					.project(dbnotehEntity.getDbnhProject())
					.prop(dbnotehEntity.getDbnhProp())
					.recdby(dbnotehEntity.getDbnhRecdby())
					.site(dbnotehEntity.getDbnhSite())
					.suppbilldt(Objects.nonNull(dbnotehEntity.getDbnhSuppbilldt()) ? dbnotehEntity.getDbnhSuppbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.suppbillno(dbnotehEntity.getDbnhSuppbillno())
					.tdsamount(dbnotehEntity.getDbnhTdsamount())
					.tdsperc(dbnotehEntity.getDbnhTdsperc())
					.transer(dbnotehEntity.getDbnhTranser())
					.userid(dbnotehEntity.getDbnhUserid())
;
			if(CollectionUtils.isNotEmpty(dbnotedEntityList)) {
				dbnotehBeanBuilder.dbnotedResponses(DbnotedMapper.fetchDbnotedEntityPojoMapper.apply(new Object[] {dbnotedEntityList}));
			}
			if(CollectionUtils.isNotEmpty(dbnotevatEntityList)) {
				dbnotehBeanBuilder.dbnotevatResponses(DbnotevatMapper.fetchDbnotevatEntityPojoMapper.apply(new Object[] {dbnotevatEntityList}));
			}
			return dbnotehBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Dbnoteh> addDbnotehEntityPojoMapper = objectArray -> {
		DbnotehRequestBean dbnotehRequestBean = (DbnotehRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		Dbnoteh.DbnotehBuilder dbnotehbuilder = Dbnoteh.builder();

dbnotehbuilder
			.dbnotehCK(DbnotehCK.builder().dbnhDbnoteser(dbnotehRequestBean.getDbnoteser())
		.build())
					.dbnhAmount(dbnotehRequestBean.getAmount())
					.dbnhAuthdate(Objects.nonNull(dbnotehRequestBean.getAuthdate()) ? LocalDate.parse(dbnotehRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dbnhAuthno(dbnotehRequestBean.getAuthno())
					.dbnhBilltype(dbnotehRequestBean.getBilltype())
					.dbnhBldgcode(dbnotehRequestBean.getBldgcode())
					.dbnhCoy(dbnotehRequestBean.getCoy())
					.dbnhDate(Objects.nonNull(dbnotehRequestBean.getDate()) ? LocalDate.parse(dbnotehRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dbnhDescription1(dbnotehRequestBean.getDescription1())
					.dbnhDescription2(dbnotehRequestBean.getDescription2())
					.dbnhDescription3(dbnotehRequestBean.getDescription3())
					.dbnhDescription4(dbnotehRequestBean.getDescription4())
					.dbnhDescription5(dbnotehRequestBean.getDescription5())
					.dbnhMisbldg(dbnotehRequestBean.getMisbldg())
					.dbnhMisproject(dbnotehRequestBean.getMisproject())
					.dbnhNarration(dbnotehRequestBean.getNarration())
					.dbnhNoofprint(dbnotehRequestBean.getNoofprint())
					.dbnhOmspurcyn(dbnotehRequestBean.getOmspurcyn())
					.dbnhOrigsite(GenericAuditContextHolder.getContext().getSite())
					.dbnhPartycode(dbnotehRequestBean.getPartycode())
					.dbnhPartytype(dbnotehRequestBean.getPartytype())
					.dbnhPassedby(dbnotehRequestBean.getPassedby())
					.dbnhPrepby(dbnotehRequestBean.getPrepby())
					.dbnhPrintdate(Objects.nonNull(dbnotehRequestBean.getPrintdate()) ? LocalDateTime.parse(dbnotehRequestBean.getPrintdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dbnhPrints(dbnotehRequestBean.getPrints())
					.dbnhPrintuser(dbnotehRequestBean.getPrintuser())
					.dbnhProject(dbnotehRequestBean.getProject())
					.dbnhProp(dbnotehRequestBean.getProp())
					.dbnhRecdby(dbnotehRequestBean.getRecdby())
					.dbnhSite(GenericAuditContextHolder.getContext().getSite())
					.dbnhSuppbilldt(Objects.nonNull(dbnotehRequestBean.getSuppbilldt()) ? LocalDate.parse(dbnotehRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.dbnhSuppbillno(dbnotehRequestBean.getSuppbillno())
					.dbnhTdsamount(dbnotehRequestBean.getTdsamount())
					.dbnhTdsperc(dbnotehRequestBean.getTdsperc())
					.dbnhToday(LocalDateTime.now())
					.dbnhTranser(dbnotehRequestBean.getTranser())
					.dbnhUserid(GenericAuditContextHolder.getContext().getUserid())

		;

		return dbnotehbuilder.build();
};

	public static BiFunction<Dbnoteh, DbnotehRequestBean, Dbnoteh> updateDbnotehEntityPojoMapper = (dbnotehEntity, dbnotehRequestBean) -> {
		dbnotehEntity.setDbnhAmount(Objects.nonNull(dbnotehRequestBean.getAmount()) ? dbnotehRequestBean.getAmount() : dbnotehEntity.getDbnhAmount());
		dbnotehEntity.setDbnhAuthdate(Objects.nonNull(dbnotehRequestBean.getAuthdate()) ? LocalDate.parse(dbnotehRequestBean.getAuthdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dbnotehEntity.getDbnhAuthdate());
		dbnotehEntity.setDbnhAuthno(StringUtils.isNotBlank(dbnotehRequestBean.getAuthno()) ? dbnotehRequestBean.getAuthno().trim() : dbnotehEntity.getDbnhAuthno());
		dbnotehEntity.setDbnhBilltype(StringUtils.isNotBlank(dbnotehRequestBean.getBilltype()) ? dbnotehRequestBean.getBilltype().trim() : dbnotehEntity.getDbnhBilltype());
		dbnotehEntity.setDbnhBldgcode(StringUtils.isNotBlank(dbnotehRequestBean.getBldgcode()) ? dbnotehRequestBean.getBldgcode().trim() : dbnotehEntity.getDbnhBldgcode());
		dbnotehEntity.setDbnhCoy(StringUtils.isNotBlank(dbnotehRequestBean.getCoy()) ? dbnotehRequestBean.getCoy().trim() : dbnotehEntity.getDbnhCoy());
		dbnotehEntity.setDbnhDate(Objects.nonNull(dbnotehRequestBean.getDate()) ? LocalDate.parse(dbnotehRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dbnotehEntity.getDbnhDate());
		dbnotehEntity.setDbnhDescription1(StringUtils.isNotBlank(dbnotehRequestBean.getDescription1()) ? dbnotehRequestBean.getDescription1().trim() : dbnotehEntity.getDbnhDescription1());
		dbnotehEntity.setDbnhDescription2(StringUtils.isNotBlank(dbnotehRequestBean.getDescription2()) ? dbnotehRequestBean.getDescription2().trim() : dbnotehEntity.getDbnhDescription2());
		dbnotehEntity.setDbnhDescription3(StringUtils.isNotBlank(dbnotehRequestBean.getDescription3()) ? dbnotehRequestBean.getDescription3().trim() : dbnotehEntity.getDbnhDescription3());
		dbnotehEntity.setDbnhDescription4(StringUtils.isNotBlank(dbnotehRequestBean.getDescription4()) ? dbnotehRequestBean.getDescription4().trim() : dbnotehEntity.getDbnhDescription4());
		dbnotehEntity.setDbnhDescription5(StringUtils.isNotBlank(dbnotehRequestBean.getDescription5()) ? dbnotehRequestBean.getDescription5().trim() : dbnotehEntity.getDbnhDescription5());
		dbnotehEntity.setDbnhMisbldg(StringUtils.isNotBlank(dbnotehRequestBean.getMisbldg()) ? dbnotehRequestBean.getMisbldg().trim() : dbnotehEntity.getDbnhMisbldg());
		dbnotehEntity.setDbnhMisproject(StringUtils.isNotBlank(dbnotehRequestBean.getMisproject()) ? dbnotehRequestBean.getMisproject().trim() : dbnotehEntity.getDbnhMisproject());
		dbnotehEntity.setDbnhNarration(StringUtils.isNotBlank(dbnotehRequestBean.getNarration()) ? dbnotehRequestBean.getNarration().trim() : dbnotehEntity.getDbnhNarration());
		dbnotehEntity.setDbnhNoofprint(Objects.nonNull(dbnotehRequestBean.getNoofprint()) ? dbnotehRequestBean.getNoofprint() : dbnotehEntity.getDbnhNoofprint());
		dbnotehEntity.setDbnhOmspurcyn(StringUtils.isNotBlank(dbnotehRequestBean.getOmspurcyn()) ? dbnotehRequestBean.getOmspurcyn().trim() : dbnotehEntity.getDbnhOmspurcyn());
		dbnotehEntity.setDbnhOrigsite(StringUtils.isNotBlank(dbnotehRequestBean.getOrigsite()) ? dbnotehRequestBean.getOrigsite().trim() : dbnotehEntity.getDbnhOrigsite());
		dbnotehEntity.setDbnhPartycode(StringUtils.isNotBlank(dbnotehRequestBean.getPartycode()) ? dbnotehRequestBean.getPartycode().trim() : dbnotehEntity.getDbnhPartycode());
		dbnotehEntity.setDbnhPartytype(StringUtils.isNotBlank(dbnotehRequestBean.getPartytype()) ? dbnotehRequestBean.getPartytype().trim() : dbnotehEntity.getDbnhPartytype());
		dbnotehEntity.setDbnhPassedby(StringUtils.isNotBlank(dbnotehRequestBean.getPassedby()) ? dbnotehRequestBean.getPassedby().trim() : dbnotehEntity.getDbnhPassedby());
		dbnotehEntity.setDbnhPrepby(StringUtils.isNotBlank(dbnotehRequestBean.getPrepby()) ? dbnotehRequestBean.getPrepby().trim() : dbnotehEntity.getDbnhPrepby());
		dbnotehEntity.setDbnhPrintdate(Objects.nonNull(dbnotehRequestBean.getPrintdate()) ? LocalDateTime.parse(dbnotehRequestBean.getPrintdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dbnotehEntity.getDbnhPrintdate());
		dbnotehEntity.setDbnhPrints(Objects.nonNull(dbnotehRequestBean.getPrints()) ? dbnotehRequestBean.getPrints() : dbnotehEntity.getDbnhPrints());
		dbnotehEntity.setDbnhPrintuser(StringUtils.isNotBlank(dbnotehRequestBean.getPrintuser()) ? dbnotehRequestBean.getPrintuser().trim() : dbnotehEntity.getDbnhPrintuser());
		dbnotehEntity.setDbnhProject(StringUtils.isNotBlank(dbnotehRequestBean.getProject()) ? dbnotehRequestBean.getProject().trim() : dbnotehEntity.getDbnhProject());
		dbnotehEntity.setDbnhProp(StringUtils.isNotBlank(dbnotehRequestBean.getProp()) ? dbnotehRequestBean.getProp().trim() : dbnotehEntity.getDbnhProp());
		dbnotehEntity.setDbnhRecdby(StringUtils.isNotBlank(dbnotehRequestBean.getRecdby()) ? dbnotehRequestBean.getRecdby().trim() : dbnotehEntity.getDbnhRecdby());
		dbnotehEntity.setDbnhSite(GenericAuditContextHolder.getContext().getSite().trim());
		dbnotehEntity.setDbnhSuppbilldt(Objects.nonNull(dbnotehRequestBean.getSuppbilldt()) ? LocalDate.parse(dbnotehRequestBean.getSuppbilldt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : dbnotehEntity.getDbnhSuppbilldt());
		dbnotehEntity.setDbnhSuppbillno(StringUtils.isNotBlank(dbnotehRequestBean.getSuppbillno()) ? dbnotehRequestBean.getSuppbillno().trim() : dbnotehEntity.getDbnhSuppbillno());
		dbnotehEntity.setDbnhTdsamount(Objects.nonNull(dbnotehRequestBean.getTdsamount()) ? dbnotehRequestBean.getTdsamount() : dbnotehEntity.getDbnhTdsamount());
		dbnotehEntity.setDbnhTdsperc(Objects.nonNull(dbnotehRequestBean.getTdsperc()) ? dbnotehRequestBean.getTdsperc() : dbnotehEntity.getDbnhTdsperc());
		dbnotehEntity.setDbnhToday(LocalDateTime.now());
		dbnotehEntity.setDbnhTranser(StringUtils.isNotBlank(dbnotehRequestBean.getTranser()) ? dbnotehRequestBean.getTranser().trim() : dbnotehEntity.getDbnhTranser());
		dbnotehEntity.setDbnhUserid(GenericAuditContextHolder.getContext().getUserid().trim());


		return dbnotehEntity;
	};

}