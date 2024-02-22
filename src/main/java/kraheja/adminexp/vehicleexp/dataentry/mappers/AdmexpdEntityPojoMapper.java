package kraheja.adminexp.vehicleexp.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexpdRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexpdResponseBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexpdResponseBean.AdmexpdResponseBeanBuilder;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexpdCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface AdmexpdEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<AdmexpdResponseBean>> fetchAdmexpdEntityPojoMapper = objectArray -> {
		AdmexpdResponseBeanBuilder admexpdBeanBuilder = AdmexpdResponseBean.builder();
		List<Admexpd> admexpdEntityList = (List<Admexpd>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return admexpdEntityList.stream().map(admexpdEntity -> {
			admexpdBeanBuilder.certnum(admexpdEntity.getAdmexpdCK().getAdmdCertnum())
					.bunum(admexpdEntity.getAdmexpdCK().getAdmdBunum())
					.billamount(admexpdEntity.getAdmdBillamount())
					.billdate(Objects.nonNull(admexpdEntity.getAdmdBilldate()) ? admexpdEntity.getAdmdBilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.billref(admexpdEntity.getAdmdBillref())
					.cgst(admexpdEntity.getAdmdCgst())
					.cgstperc(admexpdEntity.getAdmdCgstperc())
					.durationfrom(Objects.nonNull(admexpdEntity.getAdmdDurationfrom()) ? admexpdEntity.getAdmdDurationfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.durationupto(Objects.nonNull(admexpdEntity.getAdmdDurationupto()) ? admexpdEntity.getAdmdDurationupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.hsmscode(admexpdEntity.getAdmdHsmscode())
					.igst(admexpdEntity.getAdmdIgst())
					.igstperc(admexpdEntity.getAdmdIgstperc())
					.sgst(admexpdEntity.getAdmdSgst())
					.sgstperc(admexpdEntity.getAdmdSgstperc())
					.site(admexpdEntity.getAdmdSite())
					.tds(admexpdEntity.getAdmdTds())
					.tdsperc(admexpdEntity.getAdmdTdsperc())
					.today(admexpdEntity.getAdmdToday())
					.ugst(admexpdEntity.getAdmdUgst())
					.ugstperc(admexpdEntity.getAdmdUgstperc())
					.userid(admexpdEntity.getAdmdUserid())
					.workcode(admexpdEntity.getAdmdWorkcode())
;
			return admexpdBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List<Admexpd>> addAdmexpdEntityPojoMapper = objectArray -> {
		List<AdmexpdRequestBean> admexpdRequestBeanList = (List<AdmexpdRequestBean>) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);
		
		return admexpdRequestBeanList.stream().map(admexpdRequestBean ->{
			return Admexpd.builder().admexpdCK(AdmexpdCK.builder()
					.admdCertnum(admexpdRequestBean.getCertnum())
					.admdBunum(admexpdRequestBean.getBunum())
		.build())
					.admdBillamount(admexpdRequestBean.getBillamount())
					.admdBilldate(Objects.nonNull(admexpdRequestBean.getBilldate()) ? LocalDate.parse(admexpdRequestBean.getBilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admdBillref(admexpdRequestBean.getBillref())
					.admdCgst(admexpdRequestBean.getCgst())
					.admdCgstperc(admexpdRequestBean.getCgstperc())
					.admdDurationfrom(Objects.nonNull(admexpdRequestBean.getDurationfrom()) ? LocalDate.parse(admexpdRequestBean.getDurationfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admdDurationupto(Objects.nonNull(admexpdRequestBean.getDurationupto()) ? LocalDate.parse(admexpdRequestBean.getDurationupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admdHsmscode(admexpdRequestBean.getHsmscode())
					.admdIgst(admexpdRequestBean.getIgst())
					.admdIgstperc(admexpdRequestBean.getIgstperc())
					.admdSgst(admexpdRequestBean.getSgst())
					.admdSgstperc(admexpdRequestBean.getSgstperc())
					.admdSite(GenericAuditContextHolder.getContext().getSite())
					.admdTds(admexpdRequestBean.getTds())
					.admdTdsperc(admexpdRequestBean.getTdsperc())
					.admdToday(LocalDateTime.now())
					.admdUgst(admexpdRequestBean.getUgst())
					.admdUgstperc(admexpdRequestBean.getUgstperc())
					.admdUserid(GenericAuditContextHolder.getContext().getUserid())
					.admdWorkcode(admexpdRequestBean.getWorkcode()).build();

		}).collect(Collectors.toList());
};
	public static Function<Object[], Admexpd > updateAdmexpdEntityPojoMapper = objectArray -> { //(admexpdEntity, admexpdRequestBean) -> {
		Admexpd admexpdEntity = (Admexpd) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		AdmexpdRequestBean admexpdRequestBean = (AdmexpdRequestBean) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		String billDate = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);
		String billRefDate = (String) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.THREE]) ? objectArray[CommonConstraints.INSTANCE.THREE] : null);
		
		admexpdEntity.getAdmexpdCK().setAdmdCertnum(Objects.nonNull(admexpdRequestBean.getCertnum()) ? admexpdRequestBean.getCertnum().trim() : admexpdEntity.getAdmexpdCK().getAdmdCertnum());
		admexpdEntity.getAdmexpdCK().setAdmdBunum(Objects.nonNull(admexpdRequestBean.getBunum()) ? admexpdRequestBean.getBunum() : admexpdEntity.getAdmexpdCK().getAdmdBunum());
		admexpdEntity.setAdmdBillamount(Objects.nonNull(admexpdRequestBean.getBillamount()) ? admexpdRequestBean.getBillamount() : admexpdEntity.getAdmdBillamount());
		admexpdEntity.setAdmdBilldate(Objects.nonNull(billDate) ? LocalDate.parse(billDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexpdEntity.getAdmdBilldate());
		admexpdEntity.setAdmdBillref(Objects.nonNull(billRefDate) ? billRefDate.trim() : admexpdEntity.getAdmdBillref());
		admexpdEntity.setAdmdCgst(Objects.nonNull(admexpdRequestBean.getCgst()) ? admexpdRequestBean.getCgst() : admexpdEntity.getAdmdCgst());
		admexpdEntity.setAdmdCgstperc(Objects.nonNull(admexpdRequestBean.getCgstperc()) ? admexpdRequestBean.getCgstperc() : admexpdEntity.getAdmdCgstperc());
		admexpdEntity.setAdmdDurationfrom(Objects.nonNull(admexpdRequestBean.getDurationfrom()) ? LocalDate.parse(admexpdRequestBean.getDurationfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexpdEntity.getAdmdDurationfrom());
		admexpdEntity.setAdmdDurationupto(Objects.nonNull(admexpdRequestBean.getDurationupto()) ? LocalDate.parse(admexpdRequestBean.getDurationupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexpdEntity.getAdmdDurationupto());
		admexpdEntity.setAdmdHsmscode(Objects.nonNull(admexpdRequestBean.getHsmscode()) ? admexpdRequestBean.getHsmscode().trim() : admexpdEntity.getAdmdHsmscode());
		admexpdEntity.setAdmdIgst(Objects.nonNull(admexpdRequestBean.getIgst()) ? admexpdRequestBean.getIgst() : admexpdEntity.getAdmdIgst());
		admexpdEntity.setAdmdIgstperc(Objects.nonNull(admexpdRequestBean.getIgstperc()) ? admexpdRequestBean.getIgstperc() : admexpdEntity.getAdmdIgstperc());
		admexpdEntity.setAdmdSgst(Objects.nonNull(admexpdRequestBean.getSgst()) ? admexpdRequestBean.getSgst() : admexpdEntity.getAdmdSgst());
		admexpdEntity.setAdmdSgstperc(Objects.nonNull(admexpdRequestBean.getSgstperc()) ? admexpdRequestBean.getSgstperc() : admexpdEntity.getAdmdSgstperc());
		admexpdEntity.setAdmdTds(Objects.nonNull(admexpdRequestBean.getTds()) ? admexpdRequestBean.getTds() : admexpdEntity.getAdmdTds());
		admexpdEntity.setAdmdTdsperc(Objects.nonNull(admexpdRequestBean.getTdsperc()) ? admexpdRequestBean.getTdsperc() : admexpdEntity.getAdmdTdsperc());
		admexpdEntity.setAdmdToday(LocalDateTime.now());
		admexpdEntity.setAdmdSite(GenericAuditContextHolder.getContext().getSite());
		admexpdEntity.setAdmdUserid(GenericAuditContextHolder.getContext().getUserid());
		admexpdEntity.setAdmdUgst(Objects.nonNull(admexpdRequestBean.getUgst()) ? admexpdRequestBean.getUgst() : admexpdEntity.getAdmdUgst());
		admexpdEntity.setAdmdUgstperc(Objects.nonNull(admexpdRequestBean.getUgstperc()) ? admexpdRequestBean.getUgstperc() : admexpdEntity.getAdmdUgstperc());
		admexpdEntity.setAdmdWorkcode(Objects.nonNull(admexpdRequestBean.getWorkcode()) ? admexpdRequestBean.getWorkcode().trim() : admexpdEntity.getAdmdWorkcode());


		return admexpdEntity;
	};

}