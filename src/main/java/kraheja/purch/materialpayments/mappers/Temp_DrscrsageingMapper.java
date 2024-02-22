package kraheja.purch.materialpayments.mappers;

import java.math.BigInteger;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.purch.bean.request.Temp_DrscrsageingRequestBean;
import kraheja.purch.entity.TempDrscrsageingCK;
import kraheja.purch.entity.Temp_Drscrsageing;

public interface Temp_DrscrsageingMapper {
	//	@SuppressWarnings("unchecked")
	//	public static Function<Object[], List<Temp_DrscrsageingResponseBean>> fetchTemp_DrscrsageingEntityPojoMapper = objectArray -> {
	//		Temp_DrscrsageingResponseBeanBuilder temp_drscrsageingBeanBuilder = Temp_DrscrsageingResponseBean.builder();
	//		List<Temp_Drscrsageing> temp_drscrsageingEntityList = (List<Temp_Drscrsageing>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
	//				? objectArray[BigInteger.ZERO.intValue()]
	//				: null);
	//		return temp_drscrsageingEntityList.stream().map(temp_drscrsageingEntity -> {
	//			temp_drscrsageingBeanBuilder.sesid(temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempSesid())
	//					.srno(temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempSrno())
	//					.partycode(temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempPartycode())
	//					.0to365(temp_drscrsageingEntity.getTemp0to365())
	//					.366to1095(temp_drscrsageingEntity.getTemp366to1095())
	//					.above1095(temp_drscrsageingEntity.getTempAbove1095())
	//					.above180(temp_drscrsageingEntity.getTempAbove180())
	//					.advance(temp_drscrsageingEntity.getTempAdvance())
	//					.billdate(Objects.nonNull(temp_drscrsageingEntity.getTempBilldate()) ? temp_drscrsageingEntity.getTempBilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
	//					.billno(temp_drscrsageingEntity.getTempBillno())
	//					.ipaddress(temp_drscrsageingEntity.getTempIpaddress())
	//					.machinename(temp_drscrsageingEntity.getTempMachinename())
	//					.modifiedon(temp_drscrsageingEntity.getTempModifiedon())
	//					.module(temp_drscrsageingEntity.getTempModule())
	//					.retention(temp_drscrsageingEntity.getTempRetention())
	//					.site(temp_drscrsageingEntity.getTempSite())
	//					.trantype(temp_drscrsageingEntity.getTempTrantype())
	//					.userid(temp_drscrsageingEntity.getTempUserid())
	//;
	//			return temp_drscrsageingBeanBuilder.build();
	//		}).collect(Collectors.toList());
	//	};

	@SuppressWarnings("unchecked")
	public static Function<Object[], List<Temp_Drscrsageing>> addTemp_DrscrsageingEntityPojoMapper = objectArray -> {
		List<Temp_DrscrsageingRequestBean> temp_drscrsageingRequestBeanList = (List<Temp_DrscrsageingRequestBean>) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String machineName = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		return temp_drscrsageingRequestBeanList.stream().map(temp_drscrsageingRequestBean ->
		 Temp_Drscrsageing.builder()
		.tempDrscrsageingCK(TempDrscrsageingCK.builder()
				.tempSesid(temp_drscrsageingRequestBean.getSesid())
				.tempSrno(temp_drscrsageingRequestBean.getSrno())
				.tempPartycode(temp_drscrsageingRequestBean.getPartycode())
				.build())
		.temp0to365(temp_drscrsageingRequestBean.getDays0to365())
		.temp366to1095(temp_drscrsageingRequestBean.getDays366to1095())
		.tempAbove1095(temp_drscrsageingRequestBean.getAbove1095())
		.tempAbove180(temp_drscrsageingRequestBean.getAbove180())
		.tempAdvance(temp_drscrsageingRequestBean.getAdvance())
		.tempBilldate(Objects.nonNull(temp_drscrsageingRequestBean.getBilldate()) ? temp_drscrsageingRequestBean.getBilldate(): null)
		.tempBillno(temp_drscrsageingRequestBean.getBillno())
		.tempIpaddress(GenericAuditContextHolder.getContext().getIpAddress())
		.tempMachinename(machineName)
		.tempModifiedon(LocalDateTime.now())
		.tempModule(temp_drscrsageingRequestBean.getModule())
		.tempRetention(temp_drscrsageingRequestBean.getRetention())
		.tempSite(GenericAuditContextHolder.getContext().getSite())
		.tempTrantype(temp_drscrsageingRequestBean.getTrantype())
		.tempUserid(GenericAuditContextHolder.getContext().getUserid()).build()
				).collect(Collectors.toList());
		

		
	};

	//	public static BiFunction<Temp_Drscrsageing, Temp_DrscrsageingRequestBean, Temp_Drscrsageing> updateTemp_DrscrsageingEntityPojoMapper = (temp_drscrsageingEntity, temp_drscrsageingRequestBean) -> {
	//		temp_drscrsageingEntity.getTemp_DrscrsageingCK().setTempSesid(Objects.nonNull(temp_drscrsageingRequestBean.getSesid()) ? temp_drscrsageingRequestBean.getSesid() : temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempSesid());
	//		temp_drscrsageingEntity.getTemp_DrscrsageingCK().setTempSrno(Objects.nonNull(temp_drscrsageingRequestBean.getSrno()) ? temp_drscrsageingRequestBean.getSrno() : temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempSrno());
	//		temp_drscrsageingEntity.getTemp_DrscrsageingCK().setTempPartycode(Objects.nonNull(temp_drscrsageingRequestBean.getPartycode()) ? temp_drscrsageingRequestBean.getPartycode().trim() : temp_drscrsageingEntity.getTemp_DrscrsageingCK().getTempPartycode());
	//		temp_drscrsageingEntity.setTemp0to365(Objects.nonNull(temp_drscrsageingRequestBean.get0to365()) ? temp_drscrsageingRequestBean.get0to365() : temp_drscrsageingEntity.getTemp0to365());
	//		temp_drscrsageingEntity.setTemp366to1095(Objects.nonNull(temp_drscrsageingRequestBean.get366to1095()) ? temp_drscrsageingRequestBean.get366to1095() : temp_drscrsageingEntity.getTemp366to1095());
	//		temp_drscrsageingEntity.setTempAbove1095(Objects.nonNull(temp_drscrsageingRequestBean.getAbove1095()) ? temp_drscrsageingRequestBean.getAbove1095() : temp_drscrsageingEntity.getTempAbove1095());
	//		temp_drscrsageingEntity.setTempAbove180(Objects.nonNull(temp_drscrsageingRequestBean.getAbove180()) ? temp_drscrsageingRequestBean.getAbove180() : temp_drscrsageingEntity.getTempAbove180());
	//		temp_drscrsageingEntity.setTempAdvance(Objects.nonNull(temp_drscrsageingRequestBean.getAdvance()) ? temp_drscrsageingRequestBean.getAdvance() : temp_drscrsageingEntity.getTempAdvance());
	//		temp_drscrsageingEntity.setTempBilldate(Objects.nonNull(temp_drscrsageingRequestBean.getBilldate()) ? LocalDate.parse(temp_drscrsageingRequestBean.getBilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : temp_drscrsageingEntity.getTempBilldate());
	//		temp_drscrsageingEntity.setTempBillno(Objects.nonNull(temp_drscrsageingRequestBean.getBillno()) ? temp_drscrsageingRequestBean.getBillno().trim() : temp_drscrsageingEntity.getTempBillno());
	//		temp_drscrsageingEntity.setTempIpaddress(Objects.nonNull(temp_drscrsageingRequestBean.getIpaddress()) ? temp_drscrsageingRequestBean.getIpaddress().trim() : temp_drscrsageingEntity.getTempIpaddress());
	//		temp_drscrsageingEntity.setTempMachinename(Objects.nonNull(temp_drscrsageingRequestBean.getMachinename()) ? temp_drscrsageingRequestBean.getMachinename().trim() : temp_drscrsageingEntity.getTempMachinename());
	//		temp_drscrsageingEntity.setTempModifiedon(Objects.nonNull(temp_drscrsageingRequestBean.getModifiedon()) ? temp_drscrsageingRequestBean.getModifiedon() : temp_drscrsageingEntity.getTempModifiedon());
	//		temp_drscrsageingEntity.setTempModule(Objects.nonNull(temp_drscrsageingRequestBean.getModule()) ? temp_drscrsageingRequestBean.getModule().trim() : temp_drscrsageingEntity.getTempModule());
	//		temp_drscrsageingEntity.setTempRetention(Objects.nonNull(temp_drscrsageingRequestBean.getRetention()) ? temp_drscrsageingRequestBean.getRetention() : temp_drscrsageingEntity.getTempRetention());
	//		temp_drscrsageingEntity.setTempSite(Objects.nonNull(temp_drscrsageingRequestBean.getSite()) ? temp_drscrsageingRequestBean.getSite().trim() : temp_drscrsageingEntity.getTempSite());
	//		temp_drscrsageingEntity.setTempTrantype(Objects.nonNull(temp_drscrsageingRequestBean.getTrantype()) ? temp_drscrsageingRequestBean.getTrantype().trim() : temp_drscrsageingEntity.getTempTrantype());
	//		temp_drscrsageingEntity.setTempUserid(Objects.nonNull(temp_drscrsageingRequestBean.getUserid()) ? temp_drscrsageingRequestBean.getUserid().trim() : temp_drscrsageingEntity.getTempUserid());
	//
	//
	//		return temp_drscrsageingEntity;
	//	};

}