package kraheja.adminexp.billing.dataentry.debitNote.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;

import kraheja.adminexp.billing.dataentry.debitNote.bean.request.AdbnotehRequestBean;
import kraheja.adminexp.billing.dataentry.debitNote.bean.response.AdbnotehResponseBean;
import kraheja.adminexp.billing.dataentry.debitNote.bean.response.AdbnotehResponseBean.AdbnotehResponseBeanBuilder;
import kraheja.adminexp.billing.dataentry.debitNote.entity.Adbnoteh;
import kraheja.adminexp.billing.dataentry.debitNote.entity.AdbnotehCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface AdbnotehEntityPojoMapper {
//	@SuppressWarnings("unchecked")
//	public static Function<Object[], AdbnotehResponseBean> fetchAdbnotehEntityPojoMapper = objectArray -> {
//		Adbnoteh adbnotehEntity = (Adbnoteh) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()]
//				: null);
//		AdbnotehResponseBeanBuilder adbnotehResponseBean = AdbnotehResponseBean.builder();
//		adbnotehResponseBean
//
//				.dbnoteser(adbnotehEntity.getAdbnotehCK().getAdbnhDbnoteser()).amount(adbnotehEntity.getAdbnhAmount())
//				.billtype(adbnotehEntity.getAdbnhBilltype()).bldgcode(adbnotehEntity.getAdbnhBldgcode())
//				.coy(adbnotehEntity.getAdbnhCoy())
//				.date(Objects.nonNull(adbnotehEntity.getAdbnhDate())
//						? adbnotehEntity.getAdbnhDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//						: null)
//				.description1(adbnotehEntity.getAdbnhDescription1()).fotoamt(adbnotehEntity.getAdbnhFotoamt())
//				.invbilldt(Objects.nonNull(adbnotehEntity.getAdbnhInvbilldt())
//						? adbnotehEntity.getAdbnhInvbilldt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//						: null)
//				.invbillno(adbnotehEntity.getAdbnhInvbillno()).narration(adbnotehEntity.getAdbnhNarration())
//				.partycode(adbnotehEntity.getAdbnhPartycode()).partytype(adbnotehEntity.getAdbnhPartytype())
//				.passedby(adbnotehEntity.getAdbnhPassedby()).prepby(adbnotehEntity.getAdbnhPrepby())
//				.project(adbnotehEntity.getAdbnhProject()).prop(adbnotehEntity.getAdbnhProp())
//				.site(adbnotehEntity.getAdbnhSite()).tdsamount(adbnotehEntity.getAdbnhTdsamount())
//				.tdsperc(adbnotehEntity.getAdbnhTdsperc()).today(adbnotehEntity.getAdbnhToday())
//				.userid(adbnotehEntity.getAdbnhUserid()).build();
//
//		return adbnotehResponseBean.build();
//	};

	public static Function<AdbnotehRequestBean, Adbnoteh> addAdbnotehPojoEntityMapper = adbnotehRequestBean -> {
		return Adbnoteh.builder()
				.adbnotehCK(AdbnotehCK.builder().adbnhDbnoteser(adbnotehRequestBean.getDbnoteser()).build())

				.adbnhAmount(Objects.nonNull(adbnotehRequestBean.getAmount()) ? adbnotehRequestBean.getAmount()
						: BigInteger.ZERO.doubleValue())
				.adbnhBilltype(adbnotehRequestBean.getBilltype()).adbnhBldgcode(adbnotehRequestBean.getBldgcode())
				.adbnhCoy(adbnotehRequestBean.getCoy())
				.adbnhDate(Objects.nonNull(adbnotehRequestBean.getDate())
					    ? LocalDate.parse(adbnotehRequestBean.getDate(), DateTimeFormatter.ISO_LOCAL_DATE)
					    : null)
				.adbnhDescription1(adbnotehRequestBean.getDescription1())
				.adbnhFotoamt(Objects.nonNull(adbnotehRequestBean.getFotoamt()) ? adbnotehRequestBean.getFotoamt()
						: BigInteger.ZERO.doubleValue())
				.adbnhInvbilldt(
						Objects.nonNull(adbnotehRequestBean.getInvbilldt()) ? adbnotehRequestBean.getInvbilldt() : null)
				.adbnhInvbillno(adbnotehRequestBean.getInvbillno()).adbnhNarration(adbnotehRequestBean.getNarration())
				.adbnhPartycode(adbnotehRequestBean.getPartycode()).adbnhPartytype(adbnotehRequestBean.getPartytype().trim())
				.adbnhPassedby(adbnotehRequestBean.getPassedby()).adbnhPrepby(adbnotehRequestBean.getPrepby())
				.adbnhProject(adbnotehRequestBean.getProject()).adbnhProp(adbnotehRequestBean.getProp())
				.adbnhSite(GenericAuditContextHolder.getContext().getSite())
				.adbnhTdsamount(Objects.nonNull(adbnotehRequestBean.getTdsamount()) ? adbnotehRequestBean.getTdsamount()
						: BigInteger.ZERO.intValue())
				.adbnhTdsperc(Objects.nonNull(adbnotehRequestBean.getTdsperc()) ? adbnotehRequestBean.getTdsperc()
						: BigInteger.ZERO.intValue())
				.adbnhToday(LocalDateTime.now()).adbnhUserid(GenericAuditContextHolder.getContext().getUserid())

				.build();
	};

	public static BiFunction<Adbnoteh, AdbnotehRequestBean, Adbnoteh> updateAdbnotehEntityPojoMapper = (adbnotehEntity,
			adbnotehRequestBean) -> {
		adbnotehEntity.getAdbnotehCK().setAdbnhDbnoteser(
				Objects.nonNull(adbnotehRequestBean.getDbnoteser()) ? adbnotehRequestBean.getDbnoteser().trim()
						: adbnotehEntity.getAdbnotehCK().getAdbnhDbnoteser());

		adbnotehEntity.setAdbnhAmount(Objects.nonNull(adbnotehRequestBean.getAmount()) ? adbnotehRequestBean.getAmount()
				: adbnotehEntity.getAdbnhAmount());
		adbnotehEntity.setAdbnhBilltype(
				Objects.nonNull(adbnotehRequestBean.getBilltype()) ? adbnotehRequestBean.getBilltype().trim()
						: adbnotehEntity.getAdbnhBilltype());
		adbnotehEntity.setAdbnhBldgcode(
				Objects.nonNull(adbnotehRequestBean.getBldgcode()) ? adbnotehRequestBean.getBldgcode().trim()
						: adbnotehEntity.getAdbnhBldgcode());
		adbnotehEntity.setAdbnhCoy(Objects.nonNull(adbnotehRequestBean.getCoy()) ? adbnotehRequestBean.getCoy().trim()
				: adbnotehEntity.getAdbnhCoy());
		adbnotehEntity.setAdbnhDate(Objects.nonNull(adbnotehRequestBean.getDate())
				? LocalDate.parse(adbnotehRequestBean.getDate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
				: adbnotehEntity.getAdbnhDate());
		adbnotehEntity.setAdbnhDescription1(
				Objects.nonNull(adbnotehRequestBean.getDescription1()) ? adbnotehRequestBean.getDescription1().trim()
						: adbnotehEntity.getAdbnhDescription1());
		adbnotehEntity
				.setAdbnhFotoamt(Objects.nonNull(adbnotehRequestBean.getFotoamt()) ? adbnotehRequestBean.getFotoamt()
						: adbnotehEntity.getAdbnhFotoamt());
		adbnotehEntity.setAdbnhInvbilldt(
				Objects.nonNull(adbnotehRequestBean.getInvbilldt()) ? adbnotehRequestBean.getInvbilldt()
						: adbnotehEntity.getAdbnhInvbilldt());
		adbnotehEntity.setAdbnhInvbillno(
				Objects.nonNull(adbnotehRequestBean.getInvbillno()) ? adbnotehRequestBean.getInvbillno().trim()
						: adbnotehEntity.getAdbnhInvbillno());
		adbnotehEntity.setAdbnhNarration(
				Objects.nonNull(adbnotehRequestBean.getNarration()) ? adbnotehRequestBean.getNarration().trim()
						: adbnotehEntity.getAdbnhNarration());
		adbnotehEntity.setAdbnhPartycode(
				Objects.nonNull(adbnotehRequestBean.getPartycode()) ? adbnotehRequestBean.getPartycode().trim()
						: adbnotehEntity.getAdbnhPartycode());
		adbnotehEntity.setAdbnhPartytype(
				Objects.nonNull(adbnotehRequestBean.getPartytype()) ? adbnotehRequestBean.getPartytype().trim()
						: adbnotehEntity.getAdbnhPartytype());
		adbnotehEntity.setAdbnhPassedby(
				Objects.nonNull(adbnotehRequestBean.getPassedby()) ? adbnotehRequestBean.getPassedby().trim()
						: adbnotehEntity.getAdbnhPassedby());
		adbnotehEntity.setAdbnhPrepby(
				Objects.nonNull(adbnotehRequestBean.getPrepby()) ? adbnotehRequestBean.getPrepby().trim()
						: adbnotehEntity.getAdbnhPrepby());
		adbnotehEntity.setAdbnhProject(
				Objects.nonNull(adbnotehRequestBean.getProject()) ? adbnotehRequestBean.getProject().trim()
						: adbnotehEntity.getAdbnhProject());
		adbnotehEntity
				.setAdbnhProp(Objects.nonNull(adbnotehRequestBean.getProp()) ? adbnotehRequestBean.getProp().trim()
						: adbnotehEntity.getAdbnhProp());
		adbnotehEntity.setAdbnhSite(GenericAuditContextHolder.getContext().getSite());
		adbnotehEntity.setAdbnhTdsamount(
				Objects.nonNull(adbnotehRequestBean.getTdsamount()) ? adbnotehRequestBean.getTdsamount()
						: adbnotehEntity.getAdbnhTdsamount());
		adbnotehEntity
				.setAdbnhTdsperc(Objects.nonNull(adbnotehRequestBean.getTdsperc()) ? adbnotehRequestBean.getTdsperc()
						: adbnotehEntity.getAdbnhTdsperc());
		adbnotehEntity.setAdbnhToday(LocalDateTime.now());
		adbnotehEntity.setAdbnhUserid(GenericAuditContextHolder.getContext().getUserid());

		return adbnotehEntity;
	};

}
