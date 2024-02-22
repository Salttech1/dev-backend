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
import kraheja.purch.bean.request.AdvrecvoucherRequestBean;
import kraheja.purch.bean.response.AdvrecvoucherResponseBean;
import kraheja.purch.bean.response.AdvrecvoucherResponseBean.AdvrecvoucherResponseBeanBuilder;
import kraheja.purch.entity.Advrecvoucher;
import kraheja.purch.entity.AdvrecvoucherCK;

public interface AdvrecvoucherEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<AdvrecvoucherResponseBean>> fetchAdvrecvoucherEntityPojoMapper = objectArray -> {
		AdvrecvoucherResponseBeanBuilder advrecvoucherBeanBuilder = AdvrecvoucherResponseBean.builder();
		List<Advrecvoucher> advrecvoucherEntityList = (List<Advrecvoucher>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
						: null);
		return advrecvoucherEntityList.stream().map(advrecvoucherEntity -> {
			advrecvoucherBeanBuilder					.amount(advrecvoucherEntity.getAdrvAmount())
			.lineno(advrecvoucherEntity.getAdvrecvoucherCK().getAdrvLineno())
			.origdocnum(advrecvoucherEntity.getAdvrecvoucherCK().getAdrvOrigdocnum())
			.coy(advrecvoucherEntity.getAdrvCoy())
			.bldgcode(advrecvoucherEntity.getAdrvBldgcode())
			.partycode(advrecvoucherEntity.getAdrvPartycode())
			.partytype(advrecvoucherEntity.getAdrvPartytype())
			.matcertcode(advrecvoucherEntity.getAdrvMatcertcode())
			.origsys(advrecvoucherEntity.getAdrvOrigsys())
			.cgstamt(advrecvoucherEntity.getAdrvCgstamt())
			.cgstperc(advrecvoucherEntity.getAdrvCgstperc())
			.hsnsaccode(advrecvoucherEntity.getAdrvHsnsaccode())
			.igstamt(advrecvoucherEntity.getAdrvIgstamt())
			.igstperc(advrecvoucherEntity.getAdrvIgstperc())
			.itemdesc(advrecvoucherEntity.getAdrvItemdesc())
			.origdocdate(Objects.nonNull(advrecvoucherEntity.getAdrvOrigdocdate()) ? advrecvoucherEntity.getAdrvOrigdocdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.origsite(advrecvoucherEntity.getAdrvOrigsite())
			.profinvdt(Objects.nonNull(advrecvoucherEntity.getAdrvProfinvdt()) ? advrecvoucherEntity.getAdrvProfinvdt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.profinvno(advrecvoucherEntity.getAdrvProfinvno())
			.recvoudt(Objects.nonNull(advrecvoucherEntity.getAdrvRecvoudt()) ? advrecvoucherEntity.getAdrvRecvoudt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
			.recvounum(advrecvoucherEntity.getAdrvRecvounum())
			.sgstamt(advrecvoucherEntity.getAdrvSgstamt())
			.sgstperc(advrecvoucherEntity.getAdrvSgstperc())
			.site(advrecvoucherEntity.getAdrvSite())
			.today(advrecvoucherEntity.getAdrvToday())
			.ugstamt(advrecvoucherEntity.getAdrvUgstamt())
			.ugstperc(advrecvoucherEntity.getAdrvUgstperc())
			.userid(advrecvoucherEntity.getAdrvUserid())
			;
			return advrecvoucherBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List<Advrecvoucher>> addAdvrecvoucherEntityPojoMapper = objectArray -> {
		List<AdvrecvoucherRequestBean> advrecvoucherRequestBeanList = (List<AdvrecvoucherRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String docnum = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);

		return advrecvoucherRequestBeanList.stream().map(advrecvoucherRequestBean -> {
			return Advrecvoucher.builder()
					.advrecvoucherCK(AdvrecvoucherCK.builder()
							.adrvOrigdocnum(docnum)
							.adrvLineno(advrecvoucherRequestBean.getLineno())
							.build())
					.adrvCoy(advrecvoucherRequestBean.getCoy())
					.adrvBldgcode(advrecvoucherRequestBean.getBldgcode())
					.adrvPartycode(advrecvoucherRequestBean.getPartycode())
					.adrvPartytype(advrecvoucherRequestBean.getPartytype())
					.adrvMatcertcode(advrecvoucherRequestBean.getMatcertcode())
					.adrvOrigsys(advrecvoucherRequestBean.getOrigsys())
					.adrvAmount(advrecvoucherRequestBean.getAmount())
					.adrvCgstamt(advrecvoucherRequestBean.getCgstamt())
					.adrvCgstperc(advrecvoucherRequestBean.getCgstperc())
					.adrvHsnsaccode(advrecvoucherRequestBean.getHsnsaccode())
					.adrvIgstamt(advrecvoucherRequestBean.getIgstamt())
					.adrvIgstperc(advrecvoucherRequestBean.getIgstperc())
					.adrvItemdesc(advrecvoucherRequestBean.getItemdesc())
					.adrvOrigdocdate(Objects.nonNull(advrecvoucherRequestBean.getOrigdocdate()) ? LocalDate.parse(advrecvoucherRequestBean.getOrigdocdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.adrvOrigsite(GenericAuditContextHolder.getContext().getSite())
					.adrvProfinvdt(Objects.nonNull(advrecvoucherRequestBean.getProfinvdt()) ? LocalDate.parse(advrecvoucherRequestBean.getProfinvdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.adrvProfinvno(advrecvoucherRequestBean.getProfinvno())
					.adrvRecvoudt(Objects.nonNull(advrecvoucherRequestBean.getRecvoudt()) ? LocalDate.parse(advrecvoucherRequestBean.getRecvoudt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.adrvRecvounum(advrecvoucherRequestBean.getRecvounum())
					.adrvSgstamt(advrecvoucherRequestBean.getSgstamt())
					.adrvSgstperc(advrecvoucherRequestBean.getSgstperc())
					.adrvSite(GenericAuditContextHolder.getContext().getSite())
					.adrvToday(LocalDateTime.now())
					.adrvUgstamt(advrecvoucherRequestBean.getUgstamt())
					.adrvUgstperc(advrecvoucherRequestBean.getUgstperc())
					.adrvUserid(GenericAuditContextHolder.getContext().getUserid()).build();
		}).collect(Collectors.toList());
	};

	public static BiFunction<Advrecvoucher, AdvrecvoucherRequestBean, Advrecvoucher> updateAdvrecvoucherEntityPojoMapper = (advrecvoucherEntity, advrecvoucherRequestBean) -> {
		advrecvoucherEntity.setAdrvCoy(Objects.nonNull(advrecvoucherRequestBean.getCoy()) ? advrecvoucherRequestBean.getCoy() : advrecvoucherEntity.getAdrvCoy());
		advrecvoucherEntity.setAdrvBldgcode(Objects.nonNull(advrecvoucherRequestBean.getBldgcode()) ? advrecvoucherRequestBean.getBldgcode() : advrecvoucherEntity.getAdrvBldgcode());
		advrecvoucherEntity.setAdrvMatcertcode(Objects.nonNull(advrecvoucherRequestBean.getMatcertcode()) ? advrecvoucherRequestBean.getMatcertcode() : advrecvoucherEntity.getAdrvMatcertcode());
		advrecvoucherEntity.setAdrvPartytype(Objects.nonNull(advrecvoucherRequestBean.getPartytype()) ? advrecvoucherRequestBean.getPartytype() : advrecvoucherEntity.getAdrvPartytype());
		advrecvoucherEntity.setAdrvPartycode(Objects.nonNull(advrecvoucherRequestBean.getPartycode()) ? advrecvoucherRequestBean.getPartycode() : advrecvoucherEntity.getAdrvPartycode());
		advrecvoucherEntity.setAdrvOrigsys(Objects.nonNull(advrecvoucherRequestBean.getOrigsys()) ? advrecvoucherRequestBean.getOrigsys() : advrecvoucherEntity.getAdrvOrigsys());
		advrecvoucherEntity.setAdrvAmount(Objects.nonNull(advrecvoucherRequestBean.getAmount()) ? advrecvoucherRequestBean.getAmount() : advrecvoucherEntity.getAdrvAmount());
		advrecvoucherEntity.setAdrvAmount(Objects.nonNull(advrecvoucherRequestBean.getAmount()) ? advrecvoucherRequestBean.getAmount() : advrecvoucherEntity.getAdrvAmount());
		advrecvoucherEntity.setAdrvCgstamt(Objects.nonNull(advrecvoucherRequestBean.getCgstamt()) ? advrecvoucherRequestBean.getCgstamt() : advrecvoucherEntity.getAdrvCgstamt());
		advrecvoucherEntity.setAdrvCgstperc(Objects.nonNull(advrecvoucherRequestBean.getCgstperc()) ? advrecvoucherRequestBean.getCgstperc() : advrecvoucherEntity.getAdrvCgstperc());
		advrecvoucherEntity.setAdrvHsnsaccode(Objects.nonNull(advrecvoucherRequestBean.getHsnsaccode()) ? advrecvoucherRequestBean.getHsnsaccode().trim() : advrecvoucherEntity.getAdrvHsnsaccode());
		advrecvoucherEntity.setAdrvIgstamt(Objects.nonNull(advrecvoucherRequestBean.getIgstamt()) ? advrecvoucherRequestBean.getIgstamt() : advrecvoucherEntity.getAdrvIgstamt());
		advrecvoucherEntity.setAdrvIgstperc(Objects.nonNull(advrecvoucherRequestBean.getIgstperc()) ? advrecvoucherRequestBean.getIgstperc() : advrecvoucherEntity.getAdrvIgstperc());
		advrecvoucherEntity.setAdrvItemdesc(Objects.nonNull(advrecvoucherRequestBean.getItemdesc()) ? advrecvoucherRequestBean.getItemdesc().trim() : advrecvoucherEntity.getAdrvItemdesc());
		advrecvoucherEntity.setAdrvOrigdocdate(Objects.nonNull(advrecvoucherRequestBean.getOrigdocdate()) ? LocalDate.parse(advrecvoucherRequestBean.getOrigdocdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : advrecvoucherEntity.getAdrvOrigdocdate());
		advrecvoucherEntity.setAdrvOrigsite(Objects.nonNull(advrecvoucherRequestBean.getOrigsite()) ? advrecvoucherRequestBean.getOrigsite().trim() : advrecvoucherEntity.getAdrvOrigsite());
		advrecvoucherEntity.setAdrvProfinvdt(Objects.nonNull(advrecvoucherRequestBean.getProfinvdt()) ? LocalDate.parse(advrecvoucherRequestBean.getProfinvdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : advrecvoucherEntity.getAdrvProfinvdt());
		advrecvoucherEntity.setAdrvProfinvno(Objects.nonNull(advrecvoucherRequestBean.getProfinvno()) ? advrecvoucherRequestBean.getProfinvno().trim() : advrecvoucherEntity.getAdrvProfinvno());
		advrecvoucherEntity.setAdrvRecvoudt(Objects.nonNull(advrecvoucherRequestBean.getRecvoudt()) ? LocalDate.parse(advrecvoucherRequestBean.getRecvoudt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : advrecvoucherEntity.getAdrvRecvoudt());
		advrecvoucherEntity.setAdrvRecvounum(Objects.nonNull(advrecvoucherRequestBean.getRecvounum()) ? advrecvoucherRequestBean.getRecvounum().trim() : advrecvoucherEntity.getAdrvRecvounum());
		advrecvoucherEntity.setAdrvSgstamt(Objects.nonNull(advrecvoucherRequestBean.getSgstamt()) ? advrecvoucherRequestBean.getSgstamt() : advrecvoucherEntity.getAdrvSgstamt());
		advrecvoucherEntity.setAdrvSgstperc(Objects.nonNull(advrecvoucherRequestBean.getSgstperc()) ? advrecvoucherRequestBean.getSgstperc() : advrecvoucherEntity.getAdrvSgstperc());
		advrecvoucherEntity.setAdrvSite(GenericAuditContextHolder.getContext().getSite());
		advrecvoucherEntity.setAdrvToday(LocalDateTime.now());
		advrecvoucherEntity.setAdrvUgstamt(Objects.nonNull(advrecvoucherRequestBean.getUgstamt()) ? advrecvoucherRequestBean.getUgstamt() : advrecvoucherEntity.getAdrvUgstamt());
		advrecvoucherEntity.setAdrvUgstperc(Objects.nonNull(advrecvoucherRequestBean.getUgstperc()) ? advrecvoucherRequestBean.getUgstperc() : advrecvoucherEntity.getAdrvUgstperc());
		advrecvoucherEntity.setAdrvUserid(GenericAuditContextHolder.getContext().getUserid());
		return advrecvoucherEntity;
	};

}