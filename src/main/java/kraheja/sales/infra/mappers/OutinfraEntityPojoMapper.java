package kraheja.sales.infra.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.OutinfraRequestBean;
import kraheja.sales.bean.response.OutinfraResponseBean;
import kraheja.sales.entity.Outinfra;
import kraheja.sales.entity.OutinfraCK;
import kraheja.sales.bean.response.OutinfraResponseBean.OutinfraResponseBeanBuilder;

public interface OutinfraEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<OutinfraResponseBean>> fetchOutinfraEntityPojoMapper = objectArray -> {
		OutinfraResponseBeanBuilder outinfraBeanBuilder = OutinfraResponseBean.builder();
		List<Outinfra> outinfraEntityList = (List<Outinfra>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return outinfraEntityList.stream().map(outinfraEntity -> {
			outinfraBeanBuilder.bldgcode(outinfraEntity.getOutinfraCK().getInfBldgcode())
					.ownerid(outinfraEntity.getOutinfraCK().getInfOwnerid())
					.recnum(outinfraEntity.getOutinfraCK().getInfRecnum())
					.month(outinfraEntity.getOutinfraCK().getInfMonth())
					.narrcode(outinfraEntity.getOutinfraCK().getInfNarrcode())
					.admincharges(outinfraEntity.getInfAdmincharges())
					.amtdue(outinfraEntity.getInfAmtdue())
					.amtint(outinfraEntity.getInfAmtint())
					.amtos(outinfraEntity.getInfAmtos())
					.amtpaid(outinfraEntity.getInfAmtpaid())
					.canceldate(Objects.nonNull(outinfraEntity.getInfCanceldate()) ? outinfraEntity.getInfCanceldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.cancelledyn(outinfraEntity.getInfCancelledyn())
					.cgst(outinfraEntity.getInfCgst())
					.cgstperc(outinfraEntity.getInfCgstperc())
					.chargecode(outinfraEntity.getInfChargecode())
					.coy(outinfraEntity.getInfCoy())
					.flatnum(outinfraEntity.getInfFlatnum())
					.gstyn(outinfraEntity.getInfGstyn())
					.igst(outinfraEntity.getInfIgst())
					.igstperc(outinfraEntity.getInfIgstperc())
					.krishicess(outinfraEntity.getInfKrishicess())
					.origint(outinfraEntity.getInfOrigint())
					.origsite(outinfraEntity.getInfOrigsite())
					.recdate(Objects.nonNull(outinfraEntity.getInfRecdate()) ? outinfraEntity.getInfRecdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.recprinton(Objects.nonNull(outinfraEntity.getInfRecprinton()) ? outinfraEntity.getInfRecprinton().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.recprintyn(outinfraEntity.getInfRecprintyn())
					.recrev(outinfraEntity.getInfRecrev())
					.rectype(outinfraEntity.getInfRectype())
					.remarks(outinfraEntity.getInfRemarks())
					.servtax(outinfraEntity.getInfServtax())
					.sgst(outinfraEntity.getInfSgst())
					.sgstperc(outinfraEntity.getInfSgstperc())
					.site(outinfraEntity.getInfSite())
					.swachhcess(outinfraEntity.getInfSwachhcess())
					.tds(outinfraEntity.getInfTds())
					.today(outinfraEntity.getInfToday())
					.userid(outinfraEntity.getInfUserid())
					.wing(outinfraEntity.getInfWing())
;
			return outinfraBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], Outinfra> addOutinfraEntityPojoMapper = objectArray -> {
		OutinfraRequestBean outinfraRequestBean = (OutinfraRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		Outinfra.		OutinfraBuilder outinfrabuilder = Outinfra.builder();

outinfrabuilder
			.outinfraCK(OutinfraCK.builder()
					.infBldgcode(outinfraRequestBean.getBldgcode())
					.infOwnerid(outinfraRequestBean.getOwnerid())
					.infRecnum(outinfraRequestBean.getRecnum())
					.infMonth(outinfraRequestBean.getMonth())
					.infNarrcode(outinfraRequestBean.getNarrcode())
		.build())
					.infAdmincharges(outinfraRequestBean.getAdmincharges())
					.infAmtdue(outinfraRequestBean.getAmtdue())
					.infAmtint(outinfraRequestBean.getAmtint())
					.infAmtos(outinfraRequestBean.getAmtos())
					.infAmtpaid(outinfraRequestBean.getAmtpaid())
					.infCanceldate(Objects.nonNull(outinfraRequestBean.getCanceldate()) ? LocalDate.parse(outinfraRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.infCancelledyn(outinfraRequestBean.getCancelledyn())
					.infCgst(outinfraRequestBean.getCgst())
					.infCgstperc(outinfraRequestBean.getCgstperc())
					.infChargecode(outinfraRequestBean.getChargecode())
					.infCoy(outinfraRequestBean.getCoy())
					.infFlatnum(outinfraRequestBean.getFlatnum())
					.infGstyn(outinfraRequestBean.getGstyn())
					.infIgst(outinfraRequestBean.getIgst())
					.infIgstperc(outinfraRequestBean.getIgstperc())
					.infKrishicess(outinfraRequestBean.getKrishicess())
					.infOrigint(outinfraRequestBean.getOrigint())
					.infOrigsite(outinfraRequestBean.getOrigsite())
					.infRecdate(Objects.nonNull(outinfraRequestBean.getRecdate()) ? LocalDate.parse(outinfraRequestBean.getRecdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.infRecprinton(Objects.nonNull(outinfraRequestBean.getRecprinton()) ? LocalDate.parse(outinfraRequestBean.getRecprinton(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.infRecprintyn(outinfraRequestBean.getRecprintyn())
					.infRecrev(outinfraRequestBean.getRecrev())
					.infRectype(outinfraRequestBean.getRectype())
					.infRemarks(outinfraRequestBean.getRemarks())
					.infServtax(outinfraRequestBean.getServtax())
					.infSgst(outinfraRequestBean.getSgst())
					.infSgstperc(outinfraRequestBean.getSgstperc())
					.infSite(site)
					.infSwachhcess(outinfraRequestBean.getSwachhcess())
					.infTds(outinfraRequestBean.getTds())
					.infToday(LocalDateTime.now())
					.infUserid(outinfraRequestBean.getUserid())
					.infWing(outinfraRequestBean.getWing())
		;

		return outinfrabuilder.build();
};

	public static BiFunction<Outinfra, OutinfraRequestBean, Outinfra> updateOutinfraEntityPojoMapper = (outinfraEntity, outinfraRequestBean) -> {
		outinfraEntity.getOutinfraCK().setInfBldgcode(Objects.nonNull(outinfraRequestBean.getBldgcode()) ? outinfraRequestBean.getBldgcode().trim() : outinfraEntity.getOutinfraCK().getInfBldgcode());
		outinfraEntity.getOutinfraCK().setInfOwnerid(Objects.nonNull(outinfraRequestBean.getOwnerid()) ? outinfraRequestBean.getOwnerid().trim() : outinfraEntity.getOutinfraCK().getInfOwnerid());
		outinfraEntity.getOutinfraCK().setInfRecnum(Objects.nonNull(outinfraRequestBean.getRecnum()) ? outinfraRequestBean.getRecnum().trim() : outinfraEntity.getOutinfraCK().getInfRecnum());
		outinfraEntity.getOutinfraCK().setInfMonth(Objects.nonNull(outinfraRequestBean.getMonth()) ? outinfraRequestBean.getMonth().trim() : outinfraEntity.getOutinfraCK().getInfMonth());
		outinfraEntity.getOutinfraCK().setInfNarrcode(Objects.nonNull(outinfraRequestBean.getNarrcode()) ? outinfraRequestBean.getNarrcode().trim() : outinfraEntity.getOutinfraCK().getInfNarrcode());
		outinfraEntity.setInfAdmincharges(Objects.nonNull(outinfraRequestBean.getAdmincharges()) ? outinfraRequestBean.getAdmincharges() : outinfraEntity.getInfAdmincharges());
		outinfraEntity.setInfAmtdue(Objects.nonNull(outinfraRequestBean.getAmtdue()) ? outinfraRequestBean.getAmtdue() : outinfraEntity.getInfAmtdue());
		outinfraEntity.setInfAmtint(Objects.nonNull(outinfraRequestBean.getAmtint()) ? outinfraRequestBean.getAmtint() : outinfraEntity.getInfAmtint());
		outinfraEntity.setInfAmtos(Objects.nonNull(outinfraRequestBean.getAmtos()) ? outinfraRequestBean.getAmtos() : outinfraEntity.getInfAmtos());
		outinfraEntity.setInfAmtpaid(Objects.nonNull(outinfraRequestBean.getAmtpaid()) ? outinfraRequestBean.getAmtpaid() : outinfraEntity.getInfAmtpaid());
		outinfraEntity.setInfCanceldate(Objects.nonNull(outinfraRequestBean.getCanceldate()) ? LocalDate.parse(outinfraRequestBean.getCanceldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : outinfraEntity.getInfCanceldate());
		outinfraEntity.setInfCancelledyn(Objects.nonNull(outinfraRequestBean.getCancelledyn()) ? outinfraRequestBean.getCancelledyn().trim() : outinfraEntity.getInfCancelledyn());
		outinfraEntity.setInfCgst(Objects.nonNull(outinfraRequestBean.getCgst()) ? outinfraRequestBean.getCgst() : outinfraEntity.getInfCgst());
		outinfraEntity.setInfCgstperc(Objects.nonNull(outinfraRequestBean.getCgstperc()) ? outinfraRequestBean.getCgstperc() : outinfraEntity.getInfCgstperc());
		outinfraEntity.setInfChargecode(Objects.nonNull(outinfraRequestBean.getChargecode()) ? outinfraRequestBean.getChargecode().trim() : outinfraEntity.getInfChargecode());
		outinfraEntity.setInfCoy(Objects.nonNull(outinfraRequestBean.getCoy()) ? outinfraRequestBean.getCoy().trim() : outinfraEntity.getInfCoy());
		outinfraEntity.setInfFlatnum(Objects.nonNull(outinfraRequestBean.getFlatnum()) ? outinfraRequestBean.getFlatnum().trim() : outinfraEntity.getInfFlatnum());
		outinfraEntity.setInfGstyn(Objects.nonNull(outinfraRequestBean.getGstyn()) ? outinfraRequestBean.getGstyn().trim() : outinfraEntity.getInfGstyn());
		outinfraEntity.setInfIgst(Objects.nonNull(outinfraRequestBean.getIgst()) ? outinfraRequestBean.getIgst() : outinfraEntity.getInfIgst());
		outinfraEntity.setInfIgstperc(Objects.nonNull(outinfraRequestBean.getIgstperc()) ? outinfraRequestBean.getIgstperc() : outinfraEntity.getInfIgstperc());
		outinfraEntity.setInfKrishicess(Objects.nonNull(outinfraRequestBean.getKrishicess()) ? outinfraRequestBean.getKrishicess() : outinfraEntity.getInfKrishicess());
		outinfraEntity.setInfOrigint(Objects.nonNull(outinfraRequestBean.getOrigint()) ? outinfraRequestBean.getOrigint() : outinfraEntity.getInfOrigint());
		outinfraEntity.setInfOrigsite(Objects.nonNull(outinfraRequestBean.getOrigsite()) ? outinfraRequestBean.getOrigsite().trim() : outinfraEntity.getInfOrigsite());
		outinfraEntity.setInfRecdate(Objects.nonNull(outinfraRequestBean.getRecdate()) ? LocalDate.parse(outinfraRequestBean.getRecdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : outinfraEntity.getInfRecdate());
		outinfraEntity.setInfRecprinton(Objects.nonNull(outinfraRequestBean.getRecprinton()) ? LocalDate.parse(outinfraRequestBean.getRecprinton(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : outinfraEntity.getInfRecprinton());
		outinfraEntity.setInfRecprintyn(Objects.nonNull(outinfraRequestBean.getRecprintyn()) ? outinfraRequestBean.getRecprintyn().trim() : outinfraEntity.getInfRecprintyn());
		outinfraEntity.setInfRecrev(Objects.nonNull(outinfraRequestBean.getRecrev()) ? outinfraRequestBean.getRecrev() : outinfraEntity.getInfRecrev());
		outinfraEntity.setInfRectype(Objects.nonNull(outinfraRequestBean.getRectype()) ? outinfraRequestBean.getRectype().trim() : outinfraEntity.getInfRectype());
		outinfraEntity.setInfRemarks(Objects.nonNull(outinfraRequestBean.getRemarks()) ? outinfraRequestBean.getRemarks().trim() : outinfraEntity.getInfRemarks());
		outinfraEntity.setInfServtax(Objects.nonNull(outinfraRequestBean.getServtax()) ? outinfraRequestBean.getServtax() : outinfraEntity.getInfServtax());
		outinfraEntity.setInfSgst(Objects.nonNull(outinfraRequestBean.getSgst()) ? outinfraRequestBean.getSgst() : outinfraEntity.getInfSgst());
		outinfraEntity.setInfSgstperc(Objects.nonNull(outinfraRequestBean.getSgstperc()) ? outinfraRequestBean.getSgstperc() : outinfraEntity.getInfSgstperc());
		outinfraEntity.setInfSite(Objects.nonNull(outinfraRequestBean.getSite()) ? outinfraRequestBean.getSite().trim() : outinfraEntity.getInfSite());
		outinfraEntity.setInfSwachhcess(Objects.nonNull(outinfraRequestBean.getSwachhcess()) ? outinfraRequestBean.getSwachhcess() : outinfraEntity.getInfSwachhcess());
		outinfraEntity.setInfTds(Objects.nonNull(outinfraRequestBean.getTds()) ? outinfraRequestBean.getTds() : outinfraEntity.getInfTds());
		outinfraEntity.setInfToday(Objects.nonNull(outinfraRequestBean.getToday()) ? outinfraRequestBean.getToday() : outinfraEntity.getInfToday());
		outinfraEntity.setInfUserid(Objects.nonNull(outinfraRequestBean.getUserid()) ? outinfraRequestBean.getUserid().trim() : outinfraEntity.getInfUserid());
		outinfraEntity.setInfWing(Objects.nonNull(outinfraRequestBean.getWing()) ? outinfraRequestBean.getWing() : outinfraEntity.getInfWing());


		return outinfraEntity;
	};

}
