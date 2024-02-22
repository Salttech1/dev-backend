// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatpay Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.FlatpayRequestBean;
import kraheja.sales.bean.response.FlatpayOtherBldgResponseBean;
import kraheja.sales.bean.response.FlatpayResponseBean;
import kraheja.sales.entity.Flatpay;
import kraheja.sales.entity.FlatpayCK;

public interface FlatpayEntityPojoMapper {
	@SuppressWarnings("unchecked")

	public static Function<List<Flatpay>, List<FlatpayResponseBean>> fetchFlatpayEntityPojoMapper = flatpayEntityList -> {
		return flatpayEntityList.stream().map(flatpayEntity -> {
			return FlatpayResponseBean.builder().bldgcode(flatpayEntity.getFlatpayCK().getFpayBldgcode())
					.flatnum(flatpayEntity.getFlatpayCK().getFpayFlatnum())
					.ownerid(flatpayEntity.getFlatpayCK().getFpayOwnerid())
					.duedate(Objects.nonNull(flatpayEntity.getFlatpayCK().getFpayDuedate()) ? flatpayEntity
							.getFlatpayCK().getFpayDuedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					.dueamount(flatpayEntity.getFpayDueamount())
//					.paiddate(Objects.nonNull(flatpayEntity.getFlatpayCK().getFpayPaiddate()) ? flatpayEntity
//							.getFlatpayCK().getFpayPaiddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//							: null)

					.paiddate(Objects.nonNull(flatpayEntity.getFpayPaiddate())
							? flatpayEntity.getFpayPaiddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)

					.narrative(flatpayEntity.getFlatpayCK().getFpayNarrative()).wing(flatpayEntity.getFpayWing())
					.site(flatpayEntity.getFpaySite()).userid(flatpayEntity.getFpayUserid())
					.today(flatpayEntity.getFpayToday()).build();
		}).collect(Collectors.toList());
	};

	@SuppressWarnings("unchecked")

	public static Function<List<Flatpay>, List<FlatpayOtherBldgResponseBean>> fetchFlatpayOtherBldgEntityPojoMapper = flatpayEntityList -> {
		return flatpayEntityList.stream().map(flatpayEntity -> {
			return FlatpayOtherBldgResponseBean.builder().otherbldgcode(flatpayEntity.getFlatpayCK().getFpayBldgcode())
					.otherbldgflatnum(flatpayEntity.getFlatpayCK().getFpayFlatnum())
					.otherbldgownerid(flatpayEntity.getFlatpayCK().getFpayOwnerid())
					.otherbldgdueamount(flatpayEntity.getFpayDueamount())
					.otherbldgnarrative(flatpayEntity.getFlatpayCK().getFpayNarrative())
					// .otherbldgDueDate(flatpayEntity.getFlatpayCK().getFpayDuedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
					// .otherbldgDueDate(flatpayEntity.getFlatpayCK().getFpayDuedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))

					.otherbldgDueDate(Objects.nonNull(flatpayEntity.getFlatpayCK().getFpayDuedate())
							? flatpayEntity.getFlatpayCK().getFpayDuedate().toString()
							: null)
//					.otherbldgDueDate(flatpayEntity.getFlatpayCK().getFpayDuedate())
//							? flatpayEntity.getFlatpayCK().getFpayDuedate().toString()
//							: null)
//					.otherbldgDueDate(Objects.nonNull(flatpayEntity.getFlatpayCK().getFpayDuedate())
//							? flatpayEntity.getFlatpayCK().getFpayDuedate().toString()
//							: null)
					.otherbldgwing(flatpayEntity.getFpayWing())
					// .otherbldgDueDate(flatpayEntity.getFlatpayCK().getFpayDuedate())
					.site(flatpayEntity.getFpaySite()).userid(flatpayEntity.getFpayUserid())
					.today(flatpayEntity.getFpayToday()).build();
			// .otherbldgDueDate(flatpayEntity.getFlatpayCK().getFpayDuedate())

		}).collect(Collectors.toList());
	};

	@SuppressWarnings("unchecked")

	public static Function<List<FlatpayRequestBean>, List<Flatpay>> addFlatpayPojoEntityMapper = flatpayRequestBeanList -> {
		return flatpayRequestBeanList.stream().map(flatpayRequestBean -> {
			return Flatpay.builder().flatpayCK(FlatpayCK.builder()
					.fpayBldgcode(flatpayRequestBean.getBldgcode())
					.fpayFlatnum(flatpayRequestBean.getFlatnum())
					.fpayDuedate(Objects.nonNull(flatpayRequestBean.getDuedate()) ? LocalDate.parse(
							flatpayRequestBean.getDuedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					
					.fpayNarrative(flatpayRequestBean.getNarrative())
					.fpayOwnerid(flatpayRequestBean.getOwnerid())
					.build())
					.fpayDueamount(flatpayRequestBean.getDueamount())
					.fpayPaidamount(flatpayRequestBean.getPaidamount())
//				.fpayPaiddate(Objects.nonNull(flatpayRequestBean.getPaiddate()) ?
//						 LocalDate.parse(flatpayRequestBean.getPaiddate(),
//						 CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.fpayWing(flatpayRequestBean.getWing())
					.fpayPaidref(flatpayRequestBean.getPaidref())
					// .fpaySite(flatpayRequestBean.getSite())
					.fpaySite("MMM").fpayUserid("SANDY").fpayToday(LocalDateTime.now()).build();
		}).collect(Collectors.toList());
	};

	@SuppressWarnings("unchecked")

	public static BiFunction<Flatpay, FlatpayRequestBean, Flatpay> updateFlatpayEntityPojoMapper = (flatpayEntity,
			flatpayRequestBean) -> {
		flatpayEntity.getFlatpayCK().setFpayBldgcode(
				Objects.nonNull(flatpayRequestBean.getBldgcode()) ? flatpayRequestBean.getBldgcode().trim()
						: flatpayEntity.getFlatpayCK().getFpayBldgcode());
		flatpayEntity.getFlatpayCK().setFpayFlatnum(
				Objects.nonNull(flatpayRequestBean.getFlatnum()) ? flatpayRequestBean.getFlatnum().trim()
						: flatpayEntity.getFlatpayCK().getFpayFlatnum());

		flatpayEntity.getFlatpayCK().setFpayOwnerid(
				Objects.nonNull(flatpayRequestBean.getOwnerid()) ? flatpayRequestBean.getOwnerid().trim()
						: flatpayEntity.getFlatpayCK().getFpayOwnerid());

		flatpayEntity.getFlatpayCK()
				.setFpayDuedate(Objects.nonNull(flatpayRequestBean.getDuedate())
						? LocalDate.parse(flatpayRequestBean.getDuedate(),
								CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: flatpayEntity.getFlatpayCK().getFpayDuedate());

//		flatpayEntity.getFlatpayCK()
//				.setFpayDuedate(Objects.nonNull(flatpayRequestBean.getPaiddate())
//						? LocalDate.parse(flatpayRequestBean.getPaiddate(),
//								CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//						: flatpayEntity.getFlatpayCK().getFpayDuedate());

		flatpayEntity.getFlatpayCK().setFpayNarrative(
				Objects.nonNull(flatpayRequestBean.getNarrative()) ? flatpayRequestBean.getNarrative().trim()
						: flatpayEntity.getFlatpayCK().getFpayNarrative());

		flatpayEntity
				.setFpayDueamount(Objects.nonNull(flatpayRequestBean.getDueamount()) ? flatpayRequestBean.getDueamount()
						: flatpayEntity.getFpayDueamount());
		flatpayEntity.setFpayOrigsite(GenericAuditContextHolder.getContext().getSite());
		flatpayEntity.setFpayPaidamount(
				Objects.nonNull(flatpayRequestBean.getPaidamount()) ? flatpayRequestBean.getPaidamount()
						: flatpayEntity.getFpayPaidamount());
		flatpayEntity.setFpayPaidref(
				Objects.nonNull(flatpayRequestBean.getPaidref()) ? flatpayRequestBean.getPaidref().trim()
						: flatpayEntity.getFpayPaidref());
		flatpayEntity.setFpayWing(Objects.nonNull(flatpayRequestBean.getWing()) ? flatpayRequestBean.getWing().trim()
				: flatpayEntity.getFpayWing());

		flatpayEntity.setFpaySite(flatpayRequestBean.getSite());
		flatpayEntity.setFpayToday(LocalDateTime.now());
		flatpayEntity.setFpayUserid(flatpayRequestBean.getUserid());

//		flatpayEntity.setFpaySite(GenericAuditContextHolder.getContext().getSite());
//		flatpayEntity.setFpayToday(LocalDateTime.now());
//		flatpayEntity.setFpayUserid(GenericAuditContextHolder.getContext().getUserid());

		return flatpayEntity;
	};

}
