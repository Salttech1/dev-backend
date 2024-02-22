// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Bldgwingmap Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.BldgwingmapRequestBean;
import kraheja.sales.bean.response.BldgwingmapResponseBean;
import kraheja.sales.bean.response.BldgwingmapResponseBean.BldgwingmapResponseBeanBuilder;
import kraheja.sales.bean.response.BookingResponseBean;
import kraheja.sales.bean.response.BookingResponseBean.BookingResponseBeanBuilder;
import kraheja.sales.entity.Bldgwingmap;
import kraheja.sales.entity.BldgwingmapCK;
import kraheja.sales.entity.Booking;
import kraheja.sales.entity.BookingCK;

public interface BldgwingmapEntityPojoMapper {
		@SuppressWarnings("unchecked")
	public static Function<Object[], 	BldgwingmapResponseBean> fetchBldgwingmapEntityPojoMapper = objectArray -> {
	Bldgwingmap bldgwingmapEntity = (Bldgwingmap) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
					? objectArray[BigInteger.ZERO.intValue()] : null);
			BldgwingmapResponseBeanBuilder bldgwingmapResponseBean = BldgwingmapResponseBean.builder();
			bldgwingmapResponseBean

	.bldgcode(bldgwingmapEntity.getBldgwingmapCK().getBwmapBldgcode())
						.altwing(bldgwingmapEntity.getBldgwingmapCK().getBwmapBldgwing())
						.bldgcode(bldgwingmapEntity.getBwmapAltbldgcode())
						.bldgwing(bldgwingmapEntity.getBwmapAltwing())
						.infrabldgcode(bldgwingmapEntity.getBwmapInfrabldgcode())
						.infrawing(bldgwingmapEntity.getBwmapInfrawing())
						.maintbldgcode(bldgwingmapEntity.getBwmapMaintbldgcode())
						.maintwing(bldgwingmapEntity.getBwmapMaintwing())
						.origsite(bldgwingmapEntity.getBwmapOrigsite())
						.site(bldgwingmapEntity.getBwmapSite())
						.today(bldgwingmapEntity.getBwmapToday())
						.userid(bldgwingmapEntity.getBwmapUserid())
	.build();


				return bldgwingmapResponseBean.build();
	};
	
	
	public static Function<Object[], Bldgwingmap> addBldgwingmapPojoEntityMapper = objectArray -> {

		BldgwingmapRequestBean bldgwingmapRequestBean = (BldgwingmapRequestBean) (Objects
				.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		Bldgwingmap.BldgwingmapBuilder bldgwingmapBuilder = Bldgwingmap.builder();

		bldgwingmapBuilder
				.bldgwingmapCK(BldgwingmapCK.builder().bwmapBldgcode(bldgwingmapRequestBean.getBldgcode())
						.bwmapBldgwing(bldgwingmapRequestBean.getBldgwing()).build())
				.bwmapAltbldgcode(bldgwingmapRequestBean.getAltbldgcode())
				.bwmapAltwing(bldgwingmapRequestBean.getAltwing())
				.bwmapMaintbldgcode(bldgwingmapRequestBean.getMaintbldgcode())
				.bwmapMaintwing(bldgwingmapRequestBean.getMaintwing())
				.bwmapInfrabldgcode(bldgwingmapRequestBean.getInfrabldgcode())
				.bwmapInfrawing(bldgwingmapRequestBean.getInfrawing())
				.bwmapSite(bldgwingmapRequestBean.getSite())
				.bwmapOrigsite(bldgwingmapRequestBean.getSite())
				.bwmapUserid(bldgwingmapRequestBean.getUserid())
//				.bwmapSite(GenericAuditContextHolder.getContext().getSite())
//				.bwmapUserid(GenericAuditContextHolder.getContext().getUserid())
				.bwmapToday(LocalDateTime.now());

		return bldgwingmapBuilder.build();

	};

	public static BiFunction<Bldgwingmap, BldgwingmapRequestBean, Bldgwingmap> updateBldgwingmapEntityPojoMapper = (
			bldgwingmapEntity, bldgwingmapRequestBean) -> {
		bldgwingmapEntity.getBldgwingmapCK()
				.setBwmapBldgcode(Objects.nonNull(bldgwingmapRequestBean.getBldgcode())
						? bldgwingmapRequestBean.getBldgcode().trim()
						: bldgwingmapEntity.getBldgwingmapCK().getBwmapBldgcode());
		bldgwingmapEntity.getBldgwingmapCK()
				.setBwmapBldgwing(Objects.nonNull(bldgwingmapRequestBean.getBldgwing())
						? bldgwingmapRequestBean.getBldgwing()
						: bldgwingmapEntity.getBldgwingmapCK().getBwmapBldgwing());

		bldgwingmapEntity.setBwmapAltbldgcode(Objects.nonNull(bldgwingmapRequestBean.getAltbldgcode())
				? bldgwingmapRequestBean.getAltbldgcode().trim()
				: bldgwingmapEntity.getBwmapAltbldgcode());
		bldgwingmapEntity.setBwmapAltwing(
				Objects.nonNull(bldgwingmapRequestBean.getAltwing()) ? bldgwingmapRequestBean.getAltwing().trim()
						: bldgwingmapEntity.getBwmapAltwing());
		bldgwingmapEntity.setBwmapInfrabldgcode(Objects.nonNull(bldgwingmapRequestBean.getInfrabldgcode())
				? bldgwingmapRequestBean.getInfrabldgcode().trim()
				: bldgwingmapEntity.getBwmapInfrabldgcode());
		bldgwingmapEntity.setBwmapInfrawing(
				Objects.nonNull(bldgwingmapRequestBean.getInfrawing()) ? bldgwingmapRequestBean.getInfrawing().trim()
						: bldgwingmapEntity.getBwmapInfrawing());
		bldgwingmapEntity.setBwmapMaintbldgcode(Objects.nonNull(bldgwingmapRequestBean.getMaintbldgcode())
				? bldgwingmapRequestBean.getMaintbldgcode()
				: bldgwingmapEntity.getBwmapMaintbldgcode());
		bldgwingmapEntity.setBwmapMaintwing(
				Objects.nonNull(bldgwingmapRequestBean.getMaintwing()) ? bldgwingmapRequestBean.getMaintwing().trim()
						: bldgwingmapEntity.getBwmapMaintwing());
//		bldgwingmapEntity.setBwmapOrigsite(GenericAuditContextHolder.getContext().getSite());
//		bldgwingmapEntity.setBwmapSite(GenericAuditContextHolder.getContext().getSite());
		bldgwingmapEntity.setBwmapOrigsite(bldgwingmapRequestBean.getSite());
		bldgwingmapEntity.setBwmapSite(bldgwingmapRequestBean.getSite());
		bldgwingmapEntity.setBwmapToday(LocalDateTime.now());
		bldgwingmapEntity.setBwmapUserid(bldgwingmapRequestBean.getUserid());

		return bldgwingmapEntity;
	};

}
