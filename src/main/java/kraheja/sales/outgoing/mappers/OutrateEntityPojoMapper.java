package kraheja.sales.outgoing.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.sales.bean.request.OutrateRequestBean;
import kraheja.sales.bean.response.OutrateResponseBean;
import kraheja.sales.bean.response.OutrateResponseBean.OutrateResponseBeanBuilder;
import kraheja.sales.entity.Outrate;
import kraheja.sales.entity.OutrateCK;
import kraheja.sales.outgoing.service.impl.OutrateServiceImpl;

public interface OutrateEntityPojoMapper {
	
	static final Logger logger = LoggerFactory.getLogger(OutrateServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public static Function<Object[], List<OutrateResponseBean>> fetchOutrateEntityPojoMapper = objectArray -> {
		OutrateResponseBeanBuilder outrateBeanBuilder = OutrateResponseBean.builder();
		List<Outrate> outrateEntityList = (List<Outrate>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		return outrateEntityList.stream().map(outrateEntity -> {
			outrateBeanBuilder.bldgcode(outrateEntity.getOutrateCK().getOutrBldgcode())
					.flatnum(outrateEntity.getOutrateCK().getOutrFlatnum())
					.wing(outrateEntity.getOutrateCK().getOutrWing())
					.startdate(outrateEntity.getOutrateCK().getOutrStartdate())
					.admincharges(outrateEntity.getOutrAdmincharges())
					.auxiadmin(outrateEntity.getOutrAuxiadmin())
					.auxirate(outrateEntity.getOutrAuxirate())
					.auxi_tds(outrateEntity.getOutrAuxi_Tds())
					.billtype(outrateEntity.getOutrBilltype())
					.elect(outrateEntity.getOutrElect())
					.enddate(outrateEntity.getOutrEnddate())
					.infra(outrateEntity.getOutrInfra())
					.infradmin(outrateEntity.getOutrInfradmin())
					.infra_tds(outrateEntity.getOutrInfra_Tds())
					.infrrate(outrateEntity.getOutrInfrrate())
					.maint(outrateEntity.getOutrMaint())
					.maint_tds(outrateEntity.getOutrMaint_Tds())
					.natax(outrateEntity.getOutrNatax())
					.oldadmin_notused(outrateEntity.getOutrOldadmin_Notused())
					.proponbucaarea(outrateEntity.getOutrProponbucaarea())
					.proprate(outrateEntity.getOutrProprate())
					.propratesqft(outrateEntity.getOutrPropratesqft())
					.rate(outrateEntity.getOutrRate())
					.site(outrateEntity.getOutrSite())
					.today(outrateEntity.getOutrToday())
					.userid(outrateEntity.getOutrUserid())
					.water(outrateEntity.getOutrWater())
;
			return outrateBeanBuilder.build();
		}).collect(Collectors.toList());
	};

	public static Function<List <OutrateRequestBean>, List <Outrate>> addOutrateEntityPojoMapper = outrateRequestBeanList -> {
		
		return outrateRequestBeanList.stream().map(outrateRequestBean -> Outrate.builder() 
				.outrateCK(OutrateCK.builder()
						.outrBldgcode(outrateRequestBean.getBldgcode())
						.outrFlatnum(outrateRequestBean.getFlatnum())
						.outrWing(outrateRequestBean.getWing())
						.outrStartdate(outrateRequestBean.getStartdate())
			.build())
						.outrAdmincharges(outrateRequestBean.getAdmincharges())
						.outrAuxiadmin(outrateRequestBean.getAuxiadmin())
						.outrAuxirate(outrateRequestBean.getAuxirate())
						.outrAuxi_Tds(outrateRequestBean.getAuxi_tds())
						.outrBilltype(outrateRequestBean.getBilltype())
						.outrElect(outrateRequestBean.getElect())
						.outrEnddate(outrateRequestBean.getEnddate())
						.outrInfra(outrateRequestBean.getInfra())
						.outrInfradmin(outrateRequestBean.getInfradmin())
						.outrInfra_Tds(outrateRequestBean.getInfra_tds())
						.outrInfrrate(outrateRequestBean.getInfrrate())
						.outrMaint(outrateRequestBean.getMaint())
						.outrMaint_Tds(outrateRequestBean.getMaint_tds())
						.outrNatax(outrateRequestBean.getNatax())
						.outrOldadmin_Notused(outrateRequestBean.getOldadmin_notused())
						.outrProponbucaarea(outrateRequestBean.getProponbucaarea())
						.outrProprate(outrateRequestBean.getProprate())
						.outrPropratesqft(outrateRequestBean.getPropratesqft())
						.outrRate(outrateRequestBean.getRate())
//						.outrSite("MUM")
						.outrSite(GenericAuditContextHolder.getContext().getSite())
						.outrToday(LocalDateTime.now())
//						.outrUserid("RS")
						.outrUserid(GenericAuditContextHolder.getContext().getUserid())
						.outrWater(outrateRequestBean.getWater()).build()).collect(Collectors.toList());

		};

//		
//		
		
	public static BiFunction< Outrate, OutrateRequestBean, Outrate> updateOutrateEntityPojoMapper = (outrateEntity, outrateRequestBean) -> {
//		return (List<Outrate>) outrateEntityList.stream().map(outrateRequestBean -> Outrate.builder() 
//				.outrateCK(OutrateCK.builder()
//						.outrBldgcode(outrateRequestBean.getBldgcode())
//						.outrFlatnum(outrateRequestBean.getFlatnum())
//						.outrWing(outrateRequestBean.getWing())
//						.outrStartdate(outrateRequestBean.getStartdate())
//			.build())
//						.outrAdmincharges(outrateRequestBean.getAdmincharges())
//						.outrAuxiadmin(outrateRequestBean.getAuxiadmin())
//						.outrAuxirate(outrateRequestBean.getAuxirate())
//						.outrAuxi_Tds(outrateRequestBean.getAuxi_tds())
//						.outrBilltype(outrateRequestBean.getBilltype())
//						.outrElect(outrateRequestBean.getElect())
//						.outrEnddate(outrateRequestBean.getEnddate())
//						.outrInfra(outrateRequestBean.getInfra())
//						.outrInfradmin(outrateRequestBean.getInfradmin())
//						.outrInfra_Tds(outrateRequestBean.getInfra_tds())
//						.outrInfrrate(outrateRequestBean.getInfrrate())
//						.outrMaint(outrateRequestBean.getMaint())
//						.outrMaint_Tds(outrateRequestBean.getMaint_tds())
//						.outrNatax(outrateRequestBean.getNatax())
//						.outrOldadmin_Notused(outrateRequestBean.getOldadmin_notused())
//						.outrProponbucaarea(outrateRequestBean.getProponbucaarea())
//						.outrProprate(outrateRequestBean.getProprate())
//						.outrPropratesqft(outrateRequestBean.getPropratesqft())
//						.outrRate(outrateRequestBean.getRate())
//						.outrSite("MUM")
//						.outrToday(LocalDateTime.now())
//						.outrUserid("RS")
//						.outrWater(outrateRequestBean.getWater()).build()).collect(Collectors.toList());
//			
			
			
		outrateEntity.getOutrateCK().setOutrBldgcode(Objects.nonNull(outrateRequestBean.getBldgcode()) ? outrateRequestBean.getBldgcode().trim() : outrateEntity.getOutrateCK().getOutrBldgcode());
		outrateEntity.getOutrateCK().setOutrFlatnum(Objects.nonNull(outrateRequestBean.getFlatnum()) ? outrateRequestBean.getFlatnum().trim() : outrateEntity.getOutrateCK().getOutrFlatnum());
		outrateEntity.getOutrateCK().setOutrWing(Objects.nonNull(outrateRequestBean.getWing()) ? outrateRequestBean.getWing().trim() : outrateEntity.getOutrateCK().getOutrWing());
		outrateEntity.getOutrateCK().setOutrStartdate(Objects.nonNull(outrateRequestBean.getStartdate()) ? outrateRequestBean.getStartdate().trim() : outrateEntity.getOutrateCK().getOutrStartdate());
		outrateEntity.setOutrAdmincharges(Objects.nonNull(outrateRequestBean.getAdmincharges()) ? outrateRequestBean.getAdmincharges() : outrateEntity.getOutrAdmincharges());
		outrateEntity.setOutrAuxiadmin(Objects.nonNull(outrateRequestBean.getAuxiadmin()) ? outrateRequestBean.getAuxiadmin() : outrateEntity.getOutrAuxiadmin());
		outrateEntity.setOutrAuxirate(Objects.nonNull(outrateRequestBean.getAuxirate()) ? outrateRequestBean.getAuxirate() : outrateEntity.getOutrAuxirate());
		outrateEntity.setOutrAuxi_Tds(Objects.nonNull(outrateRequestBean.getAuxi_tds()) ? outrateRequestBean.getAuxi_tds() : outrateEntity.getOutrAuxi_Tds());
		outrateEntity.setOutrBilltype(Objects.nonNull(outrateRequestBean.getBilltype()) ? outrateRequestBean.getBilltype().trim() : outrateEntity.getOutrBilltype());
		outrateEntity.setOutrElect(Objects.nonNull(outrateRequestBean.getElect()) ? outrateRequestBean.getElect() : outrateEntity.getOutrElect());
		outrateEntity.setOutrEnddate(Objects.nonNull(outrateRequestBean.getEnddate()) ? outrateRequestBean.getEnddate().trim() : outrateEntity.getOutrEnddate());
		outrateEntity.setOutrInfra(Objects.nonNull(outrateRequestBean.getInfra()) ? outrateRequestBean.getInfra() : outrateEntity.getOutrInfra());
		outrateEntity.setOutrInfradmin(Objects.nonNull(outrateRequestBean.getInfradmin()) ? outrateRequestBean.getInfradmin() : outrateEntity.getOutrInfradmin());
		outrateEntity.setOutrInfra_Tds(Objects.nonNull(outrateRequestBean.getInfra_tds()) ? outrateRequestBean.getInfra_tds() : outrateEntity.getOutrInfra_Tds());
		outrateEntity.setOutrInfrrate(Objects.nonNull(outrateRequestBean.getInfrrate()) ? outrateRequestBean.getInfrrate() : outrateEntity.getOutrInfrrate());
		outrateEntity.setOutrMaint(Objects.nonNull(outrateRequestBean.getMaint()) ? outrateRequestBean.getMaint() : outrateEntity.getOutrMaint());
		outrateEntity.setOutrMaint_Tds(Objects.nonNull(outrateRequestBean.getMaint_tds()) ? outrateRequestBean.getMaint_tds() : outrateEntity.getOutrMaint_Tds());
		outrateEntity.setOutrNatax(Objects.nonNull(outrateRequestBean.getNatax()) ? outrateRequestBean.getNatax() : outrateEntity.getOutrNatax());
		outrateEntity.setOutrOldadmin_Notused(Objects.nonNull(outrateRequestBean.getOldadmin_notused()) ? outrateRequestBean.getOldadmin_notused() : outrateEntity.getOutrOldadmin_Notused());
		outrateEntity.setOutrProponbucaarea(Objects.nonNull(outrateRequestBean.getProponbucaarea()) ? outrateRequestBean.getProponbucaarea().trim() : outrateEntity.getOutrProponbucaarea());
		outrateEntity.setOutrProprate(Objects.nonNull(outrateRequestBean.getProprate()) ? outrateRequestBean.getProprate() : outrateEntity.getOutrProprate());
		outrateEntity.setOutrPropratesqft(Objects.nonNull(outrateRequestBean.getPropratesqft()) ? outrateRequestBean.getPropratesqft() : outrateEntity.getOutrPropratesqft());
		outrateEntity.setOutrRate(Objects.nonNull(outrateRequestBean.getRate()) ? outrateRequestBean.getRate() : outrateEntity.getOutrRate());
		outrateEntity.setOutrSite(GenericAuditContextHolder.getContext().getSite());
//		outrateEntity.setOutrSite("MUM");
		outrateEntity.setOutrToday(LocalDateTime.now());
		//outrateEntity.setOutrUserid(Objects.nonNull(outrateRequestBean.getUserid()) ? outrateRequestBean.getUserid().trim() : outrateEntity.getOutrUserid());
		outrateEntity.setOutrUserid(GenericAuditContextHolder.getContext().getUserid());
//		outrateEntity.setOutrUserid("RS");
		outrateEntity.setOutrWater(Objects.nonNull(outrateRequestBean.getWater()) ? outrateRequestBean.getWater() : outrateEntity.getOutrWater());


		return outrateEntity;
	};

}
