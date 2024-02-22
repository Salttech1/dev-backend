package kraheja.adminexp.billing.dataentry.adminAdvancePayment.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmadvanceResponseBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmadvanceResponseBean.AdmadvanceResponseBeanBuilder;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.AdmadvanceCK1;
import kraheja.commons.filter.GenericAuditContextHolder;

public interface AdmadvanceEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], AdmadvanceResponseBean> fetchAdmadvanceEntityPojoMapper = objectArray -> {
		AdmadvanceResponseBeanBuilder admadvanceResponseBean = AdmadvanceResponseBean.builder();
		Admadvance1 admadvanceEntity = (Admadvance1) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);

		admadvanceResponseBean.ser(admadvanceEntity.getAdmadvanceCK().getAdvnSer())
				.actranser(admadvanceEntity.getAdvnActranser()).advanceamt(admadvanceEntity.getAdvnAdvanceamt())
				.basicamt(admadvanceEntity.getAdvnBasicamt()).bldgcode(admadvanceEntity.getAdvnBldgcode())
				.coy(admadvanceEntity.getAdvnCoy())
				.date(Objects.nonNull(admadvanceEntity.getAdvnDate()) ? admadvanceEntity.getAdvnDate() : null)
				.fotoamount(admadvanceEntity.getAdvnFotoamount()).gstamt(admadvanceEntity.getAdvnGstamt())
				.gstperc(admadvanceEntity.getAdvnGstperc()).narration(admadvanceEntity.getAdvnNarration())
				.orderby(admadvanceEntity.getAdvnOrderby()).origsite(admadvanceEntity.getAdvnOrigsite())
				.paidamount(admadvanceEntity.getAdvnPaidamount())
				.paiddate(
						Objects.nonNull(admadvanceEntity.getAdvnPaiddate()) ? admadvanceEntity.getAdvnPaiddate() : null)
				.paidref(admadvanceEntity.getAdvnPaidref()).partycode(admadvanceEntity.getAdvnPartycode())
				.partytype(admadvanceEntity.getAdvnPartytype())
				.passedon(
						Objects.nonNull(admadvanceEntity.getAdvnPassedon()) ? admadvanceEntity.getAdvnPassedon() : null)
				.pinvdate(
						Objects.nonNull(admadvanceEntity.getAdvnPinvdate()) ? admadvanceEntity.getAdvnPinvdate() : null)
				.pinvno(admadvanceEntity.getAdvnPinvno()).project(admadvanceEntity.getAdvnProject())
				.site(admadvanceEntity.getAdvnSite()).status(admadvanceEntity.getAdvnStatus())
				.tdsacmajor(admadvanceEntity.getAdvnTdsacmajor()).tdsamount(admadvanceEntity.getAdvnTdsamount())
				.tdsperc(admadvanceEntity.getAdvnTdsperc()).today(admadvanceEntity.getAdvnToday())
				.userid(admadvanceEntity.getAdvnUserid());
		return admadvanceResponseBean.build();
	};

	public static Function<AdmadvanceRequestBean, Admadvance1> addAdmadvancePojoEntityMapper(String ser) {
		return admadvanceRequestBean -> {
			return Admadvance1.builder().admadvanceCK(AdmadvanceCK1.builder().advnSer(ser).build())
					.advnActranser(admadvanceRequestBean.getActranser())
					.advnAdvanceamt(Objects.nonNull(admadvanceRequestBean.getAdvanceamt())
							? admadvanceRequestBean.getAdvanceamt()
							: BigInteger.ZERO.doubleValue())
					.advnBasicamt(
							Objects.nonNull(admadvanceRequestBean.getBasicamt()) ? admadvanceRequestBean.getBasicamt()
									: BigInteger.ZERO.doubleValue())
					.advnBldgcode(admadvanceRequestBean.getBldgcode()).advnCoy(admadvanceRequestBean.getCoy())
					.advnDate(Objects.nonNull(admadvanceRequestBean.getDate()) ? admadvanceRequestBean.getDate() : null)
					.advnFotoamount(Objects.nonNull(admadvanceRequestBean.getFotoamount())
							? admadvanceRequestBean.getFotoamount()
							: BigInteger.ZERO.doubleValue())
					.advnGstamt(Objects.nonNull(admadvanceRequestBean.getGstamt()) ? admadvanceRequestBean.getGstamt()
							: BigInteger.ZERO.doubleValue())
					.advnGstperc(
							Objects.nonNull(admadvanceRequestBean.getGstperc()) ? admadvanceRequestBean.getGstperc()
									: BigInteger.ZERO.doubleValue())
					.advnNarration(admadvanceRequestBean.getNarration().toUpperCase()).advnOrderby(admadvanceRequestBean.getOrderby().toUpperCase())
					.advnOrigsite(GenericAuditContextHolder.getContext().getSite())
					.advnPaidamount(Objects.nonNull(admadvanceRequestBean.getPaidamount())
							? admadvanceRequestBean.getPaidamount()
							: BigInteger.ZERO.doubleValue())
					.advnPaiddate(
							Objects.nonNull(admadvanceRequestBean.getPaiddate()) ? admadvanceRequestBean.getPaiddate()
									: null)
					.advnPaidref(admadvanceRequestBean.getPaidref()).advnPartycode(admadvanceRequestBean.getPartycode())
					.advnPartytype(admadvanceRequestBean.getPartytype())
					.advnPassedon(null)
					.advnPinvdate(
							Objects.nonNull(admadvanceRequestBean.getPinvdate()) ? admadvanceRequestBean.getPinvdate()
									: null)
					.advnPinvno(admadvanceRequestBean.getPinvno()).advnProject(admadvanceRequestBean.getProject())
					.advnSite(GenericAuditContextHolder.getContext().getSite())
					.advnStatus(null)
					.advnTdsamount(
							Objects.nonNull(admadvanceRequestBean.getTdsamount()) ? admadvanceRequestBean.getTdsamount()
									: BigInteger.ZERO.doubleValue())
					.advnTdsacmajor(Objects.nonNull(admadvanceRequestBean.getTdsacmajor())? admadvanceRequestBean.getTdsacmajor():null)
					.advnTdsperc(
							Objects.nonNull(admadvanceRequestBean.getTdsperc()) ? admadvanceRequestBean.getTdsperc()
									: BigInteger.ZERO.doubleValue())
					.advnToday(LocalDateTime.now()).advnUserid(GenericAuditContextHolder.getContext().getUserid())

					.build();

		};
	}

	public static BiFunction<Admadvance1, AdmadvanceRequestBean, Admadvance1> updateAdmadvanceEntityPojoMapper = (
			admadvanceEntity, admadvanceRequestBean) -> {
		admadvanceEntity.getAdmadvanceCK()
				.setAdvnSer(Objects.nonNull(admadvanceRequestBean.getSer()) ? admadvanceRequestBean.getSer().trim()
						: admadvanceEntity.getAdmadvanceCK().getAdvnSer());

		admadvanceEntity.setAdvnActranser(
				Objects.nonNull(admadvanceRequestBean.getActranser()) ? admadvanceRequestBean.getActranser().trim()
						: admadvanceEntity.getAdvnActranser());
		admadvanceEntity.setAdvnAdvanceamt(
				Objects.nonNull(admadvanceRequestBean.getAdvanceamt()) ? admadvanceRequestBean.getAdvanceamt()
						: admadvanceEntity.getAdvnAdvanceamt());
		admadvanceEntity.setAdvnBasicamt(
				Objects.nonNull(admadvanceRequestBean.getBasicamt()) ? admadvanceRequestBean.getBasicamt()
						: admadvanceEntity.getAdvnBasicamt());
		admadvanceEntity.setAdvnBldgcode(
				Objects.nonNull(admadvanceRequestBean.getBldgcode()) ? admadvanceRequestBean.getBldgcode().trim()
						: admadvanceEntity.getAdvnBldgcode());
		admadvanceEntity
				.setAdvnCoy(Objects.nonNull(admadvanceRequestBean.getCoy()) ? admadvanceRequestBean.getCoy().trim()
						: admadvanceEntity.getAdvnCoy());
		admadvanceEntity.setAdvnDate(Objects.nonNull(admadvanceRequestBean.getDate()) ? admadvanceRequestBean.getDate()
				: admadvanceEntity.getAdvnDate());
		admadvanceEntity.setAdvnFotoamount(
				Objects.nonNull(admadvanceRequestBean.getFotoamount()) ? admadvanceRequestBean.getFotoamount()
						: admadvanceEntity.getAdvnFotoamount());
		admadvanceEntity
				.setAdvnGstamt(Objects.nonNull(admadvanceRequestBean.getGstamt()) ? admadvanceRequestBean.getGstamt()
						: admadvanceEntity.getAdvnGstamt());
		admadvanceEntity
				.setAdvnGstperc(Objects.nonNull(admadvanceRequestBean.getGstperc()) ? admadvanceRequestBean.getGstperc()
						: admadvanceEntity.getAdvnGstperc());
		admadvanceEntity.setAdvnNarration(
				Objects.nonNull(admadvanceRequestBean.getNarration()) ? admadvanceRequestBean.getNarration().trim().toUpperCase()
						: admadvanceEntity.getAdvnNarration());
		admadvanceEntity.setAdvnOrderby(
				Objects.nonNull(admadvanceRequestBean.getOrderby()) ? admadvanceRequestBean.getOrderby().trim().toUpperCase()
						: admadvanceEntity.getAdvnOrderby());
		admadvanceEntity.setAdvnOrigsite(GenericAuditContextHolder.getContext().getSite());
		admadvanceEntity.setAdvnPaidamount(
				Objects.nonNull(admadvanceRequestBean.getPaidamount()) ? admadvanceRequestBean.getPaidamount()
						: admadvanceEntity.getAdvnPaidamount());
		admadvanceEntity.setAdvnPaiddate(
				Objects.nonNull(admadvanceRequestBean.getPaiddate()) ? admadvanceRequestBean.getPaiddate()
						: admadvanceEntity.getAdvnPaiddate());
		admadvanceEntity.setAdvnPaidref(
				Objects.nonNull(admadvanceRequestBean.getPaidref()) ? admadvanceRequestBean.getPaidref().trim()
						: admadvanceEntity.getAdvnPaidref());
		admadvanceEntity.setAdvnPartycode(
				Objects.nonNull(admadvanceRequestBean.getPartycode()) ? admadvanceRequestBean.getPartycode().trim()
						: admadvanceEntity.getAdvnPartycode());
		admadvanceEntity.setAdvnPartytype(
				Objects.nonNull(admadvanceRequestBean.getPartytype()) ? admadvanceRequestBean.getPartytype().trim()
						: admadvanceEntity.getAdvnPartytype());
		admadvanceEntity.setAdvnPassedon(null);
		admadvanceEntity.setAdvnPinvdate(
				Objects.nonNull(admadvanceRequestBean.getPinvdate()) ? admadvanceRequestBean.getPinvdate()
						: admadvanceEntity.getAdvnPinvdate());
		admadvanceEntity.setAdvnPinvno(
				Objects.nonNull(admadvanceRequestBean.getPinvno()) ? admadvanceRequestBean.getPinvno().trim()
						: admadvanceEntity.getAdvnPinvno());
		admadvanceEntity.setAdvnProject(
				Objects.nonNull(admadvanceRequestBean.getProject()) ? admadvanceRequestBean.getProject().trim()
						: admadvanceEntity.getAdvnProject());
		admadvanceEntity.setAdvnSite(GenericAuditContextHolder.getContext().getSite());
		admadvanceEntity.setAdvnStatus(null);
		admadvanceEntity.setAdvnTdsacmajor(
				Objects.nonNull(admadvanceRequestBean.getTdsacmajor()) ? admadvanceRequestBean.getTdsacmajor().trim()
						: admadvanceEntity.getAdvnTdsacmajor());
		admadvanceEntity.setAdvnTdsamount(
				Objects.nonNull(admadvanceRequestBean.getTdsamount()) ? admadvanceRequestBean.getTdsamount()
						: admadvanceEntity.getAdvnTdsamount());
		admadvanceEntity
				.setAdvnTdsperc(Objects.nonNull(admadvanceRequestBean.getTdsperc()) ? admadvanceRequestBean.getTdsperc()
						: admadvanceEntity.getAdvnTdsperc());
		admadvanceEntity.setAdvnToday(LocalDateTime.now());
		admadvanceEntity.setAdvnUserid(GenericAuditContextHolder.getContext().getUserid());

		return admadvanceEntity;
	};

}