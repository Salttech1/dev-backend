package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.sales.bean.request.FlatsRequestBean;
import kraheja.sales.bean.response.FlatsResponseBean;
import kraheja.sales.bean.response.FlatsResponseBean.FlatsResponseBeanBuilder;
import kraheja.sales.entity.Flats;
import kraheja.sales.entity.FlatsCK;

public interface FlatsEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], FlatsResponseBean> fetchFlatsEntityPojoMapper = objectArray -> {
		FlatsResponseBeanBuilder flatsBeanBuilder = FlatsResponseBean.builder();
		Flats flatsEntity = (Flats) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
			flatsBeanBuilder.bldgcode(flatsEntity.getFlatsCK().getFlatBldgcode())
.wing(flatsEntity.getFlatsCK().getFlatWing())
.flatnum(flatsEntity.getFlatsCK().getFlatFlatnum())
					.accomtype(flatsEntity.getFlatAccomtype())
					.agprice(flatsEntity.getFlatAgprice())
					.amtos(flatsEntity.getFlatAmtos())
					.amtrec(flatsEntity.getFlatAmtrec())
					.bamenarea(flatsEntity.getFlatBamenarea())
					.bparkarea(flatsEntity.getFlatBparkarea())
					.broker(flatsEntity.getFlatBroker())
					.bteraarea(flatsEntity.getFlatBteraarea())
					.bunitarea(flatsEntity.getFlatBunitarea())
					.camenarea(flatsEntity.getFlatCamenarea())
					.config(flatsEntity.getFlatConfig())
					.contracton(flatsEntity.getFlatContracton())
					.coy(flatsEntity.getFlatCoy())
					.cparkarea(flatsEntity.getFlatCparkarea())
					.cteraarea(flatsEntity.getFlatCteraarea())
					.cunitarea(flatsEntity.getFlatCunitarea())
					.curera(flatsEntity.getFlatCurera())
					.custid(flatsEntity.getFlatCustid())
					.custtype(flatsEntity.getFlatCusttype())
					.discount(flatsEntity.getFlatDiscount())
					.enclbalcrera(flatsEntity.getFlatEnclbalcrera())
					.flatpark(flatsEntity.getFlatFlatpark())
					.floor(flatsEntity.getFlatFloor())
					.ho2owner(flatsEntity.getFlatHo2owner())
					.intrate(flatsEntity.getFlatIntrate())
					.leasedto(flatsEntity.getFlatLeasedto())
					.leaseref(flatsEntity.getFlatLeaseref())
					.loanamt(flatsEntity.getFlatLoanamt())
					.loanbranch(flatsEntity.getFlatLoanbranch())
					.loanclosedate(flatsEntity.getFlatLoanclosedate())
					.loanco(flatsEntity.getFlatLoanco())
					.loannum(flatsEntity.getFlatLoannum())
					.loanpaid(flatsEntity.getFlatLoanpaid())
					.loanyn(flatsEntity.getFlatLoanyn())
					.maintrate(flatsEntity.getFlatMaintrate())
					.mflatbldg(flatsEntity.getFlatMflatbldg())
					.mflatno(flatsEntity.getFlatMflatno())
					.mflatwing(flatsEntity.getFlatMflatwing())
					.mpaiddate(flatsEntity.getFlatMpaiddate())
					.mpaidref(flatsEntity.getFlatMpaidref())
					.mpaidyymm(flatsEntity.getFlatMpaidyymm())
					.nocdt(flatsEntity.getFlatNocdt())
					.nocrcvddate(flatsEntity.getFlatNocrcvddate())
					.noctype(flatsEntity.getFlatNoctype())
					.occupdate(flatsEntity.getFlatOccupdate())
					.origcoy(flatsEntity.getFlatOrigcoy())
					.origsite(flatsEntity.getFlatOrigsite())
					.overon(flatsEntity.getFlatOveron())
					.ownerid(flatsEntity.getFlatOwnerid()) //NS 14.02.2023
					.poacode(flatsEntity.getFlatPoacode())
					.poaname(flatsEntity.getFlatPoaname())
					.proptax(flatsEntity.getFlatProptax())
					.psind(flatsEntity.getFlatPsind())
					.ratesft(flatsEntity.getFlatRatesft())
					.rebaterfnd(flatsEntity.getFlatRebaterfnd())
					.refunddate(flatsEntity.getFlatRefunddate())
					.remarks(flatsEntity.getFlatRemarks())
					.salestatus(flatsEntity.getFlatSalestatus())
					.saletype(flatsEntity.getFlatSaletype())
					.site(flatsEntity.getFlatSite())
					.soldyn(flatsEntity.getFlatSoldyn())
					.stampduty(flatsEntity.getFlatStampduty())
					.today(flatsEntity.getFlatToday()) //NS 14.02.2023
					.ufdiscount(flatsEntity.getFlatUfdiscount())
					.userid(flatsEntity.getFlatUserid())
					//.userid(GenericAuditContextHolder.getContext().getUserid()) //NS 14.02.2023
					.xtradate(flatsEntity.getFlatXtradate())
					.xtrarfnd(flatsEntity.getFlatXtrarfnd())
;
			return flatsBeanBuilder.build();
	};

	public static Function<Object[], Flats> addFlatsEntityPojoMapper = objectArray -> {
		FlatsRequestBean flatsRequestBean = (FlatsRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		String buildingCode = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO])
		? objectArray[CommonConstraints.INSTANCE.TWO]
: null);

		Flats.FlatsBuilder flatsbuilder = Flats.builder();

flatsbuilder
.flatsCK(FlatsCK.builder()
.flatBldgcode(buildingCode)
.flatWing(flatsRequestBean.getWing())
.flatFlatnum(flatsRequestBean.getFlatnum())
.build())
					.flatAccomtype(flatsRequestBean.getAccomtype())
					.flatAgprice(flatsRequestBean.getAgprice())
					.flatAmtos(flatsRequestBean.getAmtos())
					.flatAmtrec(flatsRequestBean.getAmtrec())
					.flatBamenarea(flatsRequestBean.getBamenarea())
					.flatBparkarea(flatsRequestBean.getBparkarea())
					.flatBroker(flatsRequestBean.getBroker())
					.flatBteraarea(flatsRequestBean.getBteraarea())
					.flatBunitarea(flatsRequestBean.getBunitarea())
					.flatCamenarea(flatsRequestBean.getCamenarea())
					.flatConfig(flatsRequestBean.getConfig())
					.flatContracton(Objects.nonNull(flatsRequestBean.getContracton()) ? LocalDateTime.parse(flatsRequestBean.getContracton(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatCoy(flatsRequestBean.getCoy())
					.flatCparkarea(flatsRequestBean.getCparkarea())
					.flatCteraarea(flatsRequestBean.getCteraarea())
					.flatCunitarea(flatsRequestBean.getCunitarea())
					.flatCurera(flatsRequestBean.getCurera())
					.flatCustid(flatsRequestBean.getCustid())
					.flatCusttype(flatsRequestBean.getCusttype())
					.flatDiscount(flatsRequestBean.getDiscount())
					.flatEnclbalcrera(flatsRequestBean.getEnclbalcrera())
					.flatFlatpark(flatsRequestBean.getFlatpark())
					.flatFloor(flatsRequestBean.getFloor())
					.flatHo2owner(Objects.nonNull(flatsRequestBean.getHo2owner()) ?  LocalDateTime.parse(flatsRequestBean.getHo2owner(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null  )
					.flatIntrate(flatsRequestBean.getIntrate())
					.flatLeasedto(flatsRequestBean.getLeasedto())
					.flatLeaseref(flatsRequestBean.getLeaseref())
					.flatLoanamt(flatsRequestBean.getLoanamt())
					.flatLoanbranch(flatsRequestBean.getLoanbranch())
					.flatLoanclosedate(Objects.nonNull(flatsRequestBean.getLoanclosedate()) ? LocalDateTime.parse(flatsRequestBean.getLoanclosedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
					.flatLoanco(flatsRequestBean.getLoanco())
					.flatLoannum(flatsRequestBean.getLoannum())
					.flatLoanpaid(flatsRequestBean.getLoanpaid())
					.flatLoanyn(flatsRequestBean.getLoanyn())
					.flatMaintrate(flatsRequestBean.getMaintrate())
					.flatMflatbldg(flatsRequestBean.getMflatbldg())
					.flatMflatno(flatsRequestBean.getMflatno())
					.flatMflatwing(flatsRequestBean.getMflatwing())
					.flatMpaiddate(Objects.nonNull(flatsRequestBean.getMpaiddate()) ? LocalDateTime.parse(flatsRequestBean.getMpaiddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null )
					.flatMpaidref(flatsRequestBean.getMpaidref())
					.flatMpaidyymm(flatsRequestBean.getMpaidyymm())
					.flatNocdt(Objects.nonNull( flatsRequestBean.getNocdt()) ? LocalDateTime.parse( flatsRequestBean.getNocdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatNocrcvddate(Objects.nonNull(flatsRequestBean.getNocrcvddate()) ? LocalDateTime.parse(flatsRequestBean.getNocdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatNoctype(flatsRequestBean.getNoctype())
					.flatOccupdate(Objects.nonNull(flatsRequestBean.getOccupdate()) ? LocalDate.parse(flatsRequestBean.getOccupdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER).atStartOfDay() : null)
					.flatOrigcoy(flatsRequestBean.getOrigcoy())
					//.flatOrigsite(flatsRequestBean.getOrigsite())
					.flatOrigsite(null)//NS 14.02.2023
					.flatOveron(Objects.nonNull(flatsRequestBean.getOveron()) ? LocalDateTime.parse(flatsRequestBean.getOveron(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatOwnerid(flatsRequestBean.getOwnerid()) //NS 14.02.2023
					.flatPoacode(flatsRequestBean.getPoacode())
					.flatPoaname(flatsRequestBean.getPoaname())
					.flatProptax(flatsRequestBean.getProptax())
					.flatPsind(flatsRequestBean.getPsind())
					.flatRatesft(flatsRequestBean.getRatesft())
					.flatRebaterfnd(flatsRequestBean.getRebaterfnd())
					.flatRefunddate(Objects.nonNull(flatsRequestBean.getRefunddate()) ? LocalDateTime.parse(flatsRequestBean.getRefunddate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatRemarks(flatsRequestBean.getRemarks())
					.flatSalestatus(flatsRequestBean.getSalestatus())
					.flatSaletype(flatsRequestBean.getSaletype())
					.flatSite(GenericAuditContextHolder.getContext().getSite())//NS 15.02.2023
					.flatSoldyn(flatsRequestBean.getSoldyn())
					.flatStampduty(flatsRequestBean.getStampduty())
					//.flatToday(flatRequestBean.getToday())
					.flatToday(LocalDateTime.now())//NS 14.02.2023
					.flatUfdiscount(flatsRequestBean.getUfdiscount())
					.flatUserid(flatsRequestBean.getUserid())
					//.flatUserid(GenericAuditContextHolder.getContext().getUserid()) //NS 14.02.2023
					.flatXtradate(Objects.nonNull(flatsRequestBean.getXtradate()) ? LocalDateTime.parse(flatsRequestBean.getXtradate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.flatXtrarfnd(flatsRequestBean.getXtrarfnd())
;

		return flatsbuilder.build();
};

	public static BiFunction<Flats, FlatsRequestBean, Flats> updateFlatsEntityPojoMapper = (flatsEntity, flatsRequestBean) -> {
		flatsEntity.getFlatsCK().setFlatBldgcode(StringUtils.isNotBlank(flatsRequestBean.getBldgcode()) ? flatsRequestBean.getBldgcode().trim() : flatsEntity.getFlatsCK().getFlatBldgcode());
		flatsEntity.getFlatsCK().setFlatWing(StringUtils.isNotBlank(flatsRequestBean.getWing()) ? flatsRequestBean.getWing().trim() : flatsEntity.getFlatsCK().getFlatWing());
		flatsEntity.getFlatsCK().setFlatFlatnum(StringUtils.isNotBlank(flatsRequestBean.getFlatnum()) ? flatsRequestBean.getFlatnum().trim() : flatsEntity.getFlatsCK().getFlatFlatnum());
		flatsEntity.setFlatAccomtype(StringUtils.isNotBlank(flatsRequestBean.getAccomtype()) ? flatsRequestBean.getAccomtype().trim() : flatsEntity.getFlatAccomtype());
		flatsEntity.setFlatAgprice(Objects.nonNull(flatsRequestBean.getAgprice()) ? flatsRequestBean.getAgprice() : flatsEntity.getFlatAgprice());
		flatsEntity.setFlatAmtos(Objects.nonNull(flatsRequestBean.getAmtos()) ? flatsRequestBean.getAmtos() : flatsEntity.getFlatAmtos());
		flatsEntity.setFlatAmtrec(Objects.nonNull(flatsRequestBean.getAmtrec()) ? flatsRequestBean.getAmtrec() : flatsEntity.getFlatAmtrec());
		flatsEntity.setFlatBamenarea(Objects.nonNull(flatsRequestBean.getBamenarea()) ? flatsRequestBean.getBamenarea() : flatsEntity.getFlatBamenarea());
		flatsEntity.setFlatBparkarea(Objects.nonNull(flatsRequestBean.getBparkarea()) ? flatsRequestBean.getBparkarea() : flatsEntity.getFlatBparkarea());
		flatsEntity.setFlatBroker(StringUtils.isNotBlank(flatsRequestBean.getBroker()) ? flatsRequestBean.getBroker().trim() : flatsEntity.getFlatBroker());
		flatsEntity.setFlatBteraarea(Objects.nonNull(flatsRequestBean.getBteraarea()) ? flatsRequestBean.getBteraarea() : flatsEntity.getFlatBteraarea());
		flatsEntity.setFlatBunitarea(Objects.nonNull(flatsRequestBean.getBunitarea()) ? flatsRequestBean.getBunitarea() : flatsEntity.getFlatBunitarea());
		flatsEntity.setFlatCamenarea(Objects.nonNull(flatsRequestBean.getCamenarea()) ? flatsRequestBean.getCamenarea() : flatsEntity.getFlatCamenarea());
		flatsEntity.setFlatConfig(StringUtils.isNotBlank(flatsRequestBean.getConfig()) ? flatsRequestBean.getConfig().trim() : flatsEntity.getFlatConfig());
		flatsEntity.setFlatContracton(Objects.nonNull(flatsRequestBean.getContracton()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getContracton()) : flatsEntity.getFlatContracton());
		flatsEntity.setFlatCoy(StringUtils.isNotBlank(flatsRequestBean.getCoy()) ? flatsRequestBean.getCoy().trim() : flatsEntity.getFlatCoy());
		flatsEntity.setFlatCparkarea(Objects.nonNull(flatsRequestBean.getCparkarea()) ? flatsRequestBean.getCparkarea() : flatsEntity.getFlatCparkarea());
		flatsEntity.setFlatCteraarea(Objects.nonNull(flatsRequestBean.getCteraarea()) ? flatsRequestBean.getCteraarea() : flatsEntity.getFlatCteraarea());
		flatsEntity.setFlatCunitarea(Objects.nonNull(flatsRequestBean.getCunitarea()) ? flatsRequestBean.getCunitarea() : flatsEntity.getFlatCunitarea());
		flatsEntity.setFlatCurera(Objects.nonNull(flatsRequestBean.getCurera()) ? flatsRequestBean.getCurera() : flatsEntity.getFlatCurera());
		flatsEntity.setFlatCustid(StringUtils.isNotBlank(flatsRequestBean.getCustid()) ? flatsRequestBean.getCustid().trim() : flatsEntity.getFlatCustid());
		flatsEntity.setFlatCusttype(StringUtils.isNotBlank(flatsRequestBean.getCusttype()) ? flatsRequestBean.getCusttype().trim() : flatsEntity.getFlatCusttype());
		flatsEntity.setFlatDiscount(Objects.nonNull(flatsRequestBean.getDiscount()) ? flatsRequestBean.getDiscount() : flatsEntity.getFlatDiscount());
		flatsEntity.setFlatEnclbalcrera(Objects.nonNull(flatsRequestBean.getEnclbalcrera()) ? flatsRequestBean.getEnclbalcrera() : flatsEntity.getFlatEnclbalcrera());
		flatsEntity.setFlatFlatpark(StringUtils.isNotBlank(flatsRequestBean.getFlatpark()) ? flatsRequestBean.getFlatpark().trim() : flatsEntity.getFlatFlatpark());
		flatsEntity.setFlatFloor(StringUtils.isNotBlank(flatsRequestBean.getFloor()) ? flatsRequestBean.getFloor().trim() : flatsEntity.getFlatFloor());
		flatsEntity.setFlatHo2owner(Objects.nonNull(flatsRequestBean.getHo2owner()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getHo2owner()) : flatsEntity.getFlatHo2owner());
		flatsEntity.setFlatIntrate(Objects.nonNull(flatsRequestBean.getIntrate()) ? flatsRequestBean.getIntrate() : flatsEntity.getFlatIntrate());
		flatsEntity.setFlatLeasedto(StringUtils.isNotBlank(flatsRequestBean.getLeasedto()) ? flatsRequestBean.getLeasedto().trim() : flatsEntity.getFlatLeasedto());
		flatsEntity.setFlatLeaseref(StringUtils.isNotBlank(flatsRequestBean.getLeaseref()) ? flatsRequestBean.getLeaseref().trim() : flatsEntity.getFlatLeaseref());
		flatsEntity.setFlatLoanamt(Objects.nonNull(flatsRequestBean.getLoanamt()) ? flatsRequestBean.getLoanamt() : flatsEntity.getFlatLoanamt());
		flatsEntity.setFlatLoanbranch(StringUtils.isNotBlank(flatsRequestBean.getLoanbranch()) ? flatsRequestBean.getLoanbranch().trim() : flatsEntity.getFlatLoanbranch());
		flatsEntity.setFlatLoanclosedate(Objects.nonNull(flatsRequestBean.getLoanclosedate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getLoanclosedate()) : flatsEntity.getFlatLoanclosedate());
		flatsEntity.setFlatLoanco(StringUtils.isNotBlank(flatsRequestBean.getLoanco()) ? flatsRequestBean.getLoanco().trim() : flatsEntity.getFlatLoanco());
		flatsEntity.setFlatLoannum(StringUtils.isNotBlank(flatsRequestBean.getLoannum()) ? flatsRequestBean.getLoannum().trim() : flatsEntity.getFlatLoannum());
		flatsEntity.setFlatLoanpaid(Objects.nonNull(flatsRequestBean.getLoanpaid()) ? flatsRequestBean.getLoanpaid() : flatsEntity.getFlatLoanpaid());
		flatsEntity.setFlatLoanyn(StringUtils.isNotBlank(flatsRequestBean.getLoanyn()) ? flatsRequestBean.getLoanyn().trim() : flatsEntity.getFlatLoanyn());
		flatsEntity.setFlatMaintrate(Objects.nonNull(flatsRequestBean.getMaintrate()) ? flatsRequestBean.getMaintrate() : flatsEntity.getFlatMaintrate());
		flatsEntity.setFlatMflatbldg(StringUtils.isNotBlank(flatsRequestBean.getMflatbldg()) ? flatsRequestBean.getMflatbldg().trim() : flatsEntity.getFlatMflatbldg());
		flatsEntity.setFlatMflatno(StringUtils.isNotBlank(flatsRequestBean.getMflatno()) ? flatsRequestBean.getMflatno().trim() : flatsEntity.getFlatMflatno());
		flatsEntity.setFlatMflatwing(StringUtils.isNotBlank(flatsRequestBean.getMflatwing()) ? flatsRequestBean.getMflatwing().trim() : flatsEntity.getFlatMflatwing());
		flatsEntity.setFlatMpaiddate(Objects.nonNull(flatsRequestBean.getMpaiddate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getMpaiddate()) : flatsEntity.getFlatMpaiddate());
		flatsEntity.setFlatMpaidref(StringUtils.isNotBlank(flatsRequestBean.getMpaidref()) ? flatsRequestBean.getMpaidref().trim() : flatsEntity.getFlatMpaidref());
		flatsEntity.setFlatMpaidyymm(StringUtils.isNotBlank(flatsRequestBean.getMpaidyymm()) ? flatsRequestBean.getMpaidyymm().trim() : flatsEntity.getFlatMpaidyymm());
		flatsEntity.setFlatNocdt(Objects.nonNull(flatsRequestBean.getNocdt()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getNocdt()) : flatsEntity.getFlatNocdt());
		flatsEntity.setFlatNocrcvddate(Objects.nonNull(flatsRequestBean.getNocrcvddate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getNocrcvddate()) : flatsEntity.getFlatNocrcvddate());
		flatsEntity.setFlatNoctype(StringUtils.isNotBlank(flatsRequestBean.getNoctype()) ? flatsRequestBean.getNoctype().trim() : flatsEntity.getFlatNoctype());
		flatsEntity.setFlatOccupdate(Objects.nonNull(flatsRequestBean.getOccupdate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getOccupdate()) : flatsEntity.getFlatOccupdate());
		flatsEntity.setFlatOrigcoy(StringUtils.isNotBlank(flatsRequestBean.getOrigcoy()) ? flatsRequestBean.getOrigcoy().trim() : flatsEntity.getFlatOrigcoy());
		flatsEntity.setFlatOrigsite(StringUtils.isNotBlank(flatsRequestBean.getOrigsite()) ? flatsRequestBean.getOrigsite().trim() : flatsEntity.getFlatOrigsite());
		flatsEntity.setFlatOveron(Objects.nonNull(flatsRequestBean.getOveron()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getOveron()) : flatsEntity.getFlatOveron());
		flatsEntity.setFlatOwnerid(Objects.nonNull(flatsRequestBean.getOwnerid()) ? flatsRequestBean.getOwnerid() : flatsEntity.getFlatOwnerid()); //NS, YC(NEOSOFT) 14.02.2023
		flatsEntity.setFlatPoacode(StringUtils.isNotBlank(flatsRequestBean.getPoacode()) ? flatsRequestBean.getPoacode().trim() : flatsEntity.getFlatPoacode());
		flatsEntity.setFlatPoaname(StringUtils.isNotBlank(flatsRequestBean.getPoaname()) ? flatsRequestBean.getPoaname().trim() : flatsEntity.getFlatPoaname());
		flatsEntity.setFlatProptax(Objects.nonNull(flatsRequestBean.getProptax()) ? flatsRequestBean.getProptax() : flatsEntity.getFlatProptax());
		flatsEntity.setFlatPsind(StringUtils.isNotBlank(flatsRequestBean.getPsind()) ? flatsRequestBean.getPsind().trim() : flatsEntity.getFlatPsind());
		flatsEntity.setFlatRatesft(Objects.nonNull(flatsRequestBean.getRatesft()) ? flatsRequestBean.getRatesft() : flatsEntity.getFlatRatesft());
		flatsEntity.setFlatRebaterfnd(Objects.nonNull(flatsRequestBean.getRebaterfnd()) ? flatsRequestBean.getRebaterfnd() : flatsEntity.getFlatRebaterfnd());
		flatsEntity.setFlatRefunddate(Objects.nonNull(flatsRequestBean.getRefunddate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getRefunddate()) : flatsEntity.getFlatRefunddate());
		flatsEntity.setFlatRemarks(StringUtils.isNotBlank(flatsRequestBean.getRemarks()) ? flatsRequestBean.getRemarks().trim() : flatsEntity.getFlatRemarks());
		flatsEntity.setFlatSalestatus(StringUtils.isNotBlank(flatsRequestBean.getSalestatus()) ? flatsRequestBean.getSalestatus().trim() : flatsEntity.getFlatSalestatus());
		flatsEntity.setFlatSaletype(StringUtils.isNotBlank(flatsRequestBean.getSaletype()) ? flatsRequestBean.getSaletype().trim() : flatsEntity.getFlatSaletype());
		
		flatsEntity.setFlatSoldyn(StringUtils.isNotBlank(flatsRequestBean.getSoldyn()) ? flatsRequestBean.getSoldyn().trim() : flatsEntity.getFlatSoldyn());
		flatsEntity.setFlatStampduty(Objects.nonNull(flatsRequestBean.getStampduty()) ? flatsRequestBean.getStampduty() : flatsEntity.getFlatStampduty());
		//flatsEntity.setFlatToday(Objects.nonNull(flatsRequestBean.getToday()) ? LocalDateTime.now() : flatsEntity.getFlatToday());
		flatsEntity.setFlatToday(LocalDateTime.now());//NS 14.02.2023
		flatsEntity.setFlatUfdiscount(StringUtils.isNotBlank(flatsRequestBean.getUfdiscount()) ? flatsRequestBean.getUfdiscount().trim() : flatsEntity.getFlatUfdiscount());
		flatsEntity.setFlatUserid(StringUtils.isNotBlank(flatsRequestBean.getUserid()) ? flatsRequestBean.getUserid().trim() : flatsEntity.getFlatUserid());
		flatsEntity.setFlatSite(StringUtils.isNotBlank(flatsRequestBean.getSite()) ? flatsRequestBean.getSite().trim() : flatsEntity.getFlatSite());
		
//		flatsEntity.setFlatSite(GenericAuditContextHolder.getContext().getSite()); //NS 15.02.2023
//		flatsEntity.setFlatUserid(StringUtils.isNotBlank(GenericAuditContextHolder.getContext().getUserid()) 
//				? GenericAuditContextHolder.getContext().getUserid().trim() : GenericAuditContextHolder.getContext().getUserid()); //NS 14.02.2023
		
		flatsEntity.setFlatXtradate(Objects.nonNull(flatsRequestBean.getXtradate()) ? CommonUtils.INSTANCE.convertStringToDateFormat(flatsRequestBean.getXtradate()) : flatsEntity.getFlatXtradate());
		flatsEntity.setFlatXtrarfnd(Objects.nonNull(flatsRequestBean.getXtrarfnd()) ? flatsRequestBean.getXtrarfnd() : flatsEntity.getFlatXtrarfnd());


		return flatsEntity;
	};

}
