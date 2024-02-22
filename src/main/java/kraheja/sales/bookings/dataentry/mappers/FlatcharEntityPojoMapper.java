// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Flatchar Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.sales.bean.request.FlatcharRequestBean;
import kraheja.sales.bean.response.FlatcharResponseBean;
import kraheja.sales.entity.Flatchar;
import kraheja.sales.entity.FlatcharCK;

public interface FlatcharEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<List<Flatchar>, List<FlatcharResponseBean>> fetchFlatcharEntityPojoMapper = flatcharEntityList -> {
		return flatcharEntityList.stream().map(flatcharEntity -> {
			return FlatcharResponseBean.builder()
				.bldgcode(flatcharEntity.getFlatcharCK().getFchBldgcode())
				.flatnum(flatcharEntity.getFlatcharCK().getFchFlatnum())
				.accomtype(flatcharEntity.getFlatcharCK().getFchAccomtype())
				.chargecode(flatcharEntity.getFlatcharCK().getFchChargecode())
				.wing(flatcharEntity.getFlatcharCK().getFchWing()).amtdue(flatcharEntity.getFchAmtdue())
				.amtpaid(flatcharEntity.getFchAmtpaid()).origsite(flatcharEntity.getFchOrigsite())
				.site(flatcharEntity.getFchSite()).sqftwiseyn(flatcharEntity.getFchSqftwiseyn())
				.today(flatcharEntity.getFchToday()).userid(flatcharEntity.getFchUserid())
				.build();

		}).collect(Collectors.toList());
	};
	
	public static Function<List<FlatcharRequestBean>, List<Flatchar>> addFlatcharPojoEntityMapper = flatcharRequestBeanList -> {
		return flatcharRequestBeanList.stream().map(flatcharRequestBean -> {
				return Flatchar.builder()
				.flatcharCK(FlatcharCK.builder().fchBldgcode(flatcharRequestBean.getBldgcode())
						.fchFlatnum(flatcharRequestBean.getFlatnum()).fchAccomtype(flatcharRequestBean.getAccomtype())
						.fchChargecode(flatcharRequestBean.getChargecode()).fchWing(flatcharRequestBean.getWing())
						.build())

				.fchAmtdue(Objects.nonNull(flatcharRequestBean.getAmtdue()) ? flatcharRequestBean.getAmtdue()
						: BigInteger.ZERO.doubleValue())
				.fchAmtpaid(Objects.nonNull(flatcharRequestBean.getAmtpaid()) ? flatcharRequestBean.getAmtpaid()
						: BigInteger.ZERO.doubleValue())
				.fchSqftwiseyn(flatcharRequestBean.getSqftwiseyn()).fchToday(LocalDateTime.now())
				//.fchOrigsite(flatcharRequestBean.getSite())
				//.fchSite(flatcharRequestBean.getSite())
				//.fchUserid(flatcharRequestBean.getUserid())
//				.fchOrigsite(GenericAuditContextHolder.getContext().getSite())
//				.fchSite(GenericAuditContextHolder.getContext().getSite())
//				.fchUserid(GenericAuditContextHolder.getContext().getUserid())
				.fchOrigsite("MUM")
				.fchSite("MUM")
				.fchUserid("SANDY")
				.build();
		}).collect(Collectors.toList());
	};
	

	public static BiFunction<Flatchar, FlatcharRequestBean, Flatchar> updateFlatcharEntityPojoMapper = (flatcharEntity,
			flatcharRequestBean) -> {
		flatcharEntity.getFlatcharCK().setFchBldgcode(
				Objects.nonNull(flatcharRequestBean.getBldgcode()) ? flatcharRequestBean.getBldgcode().trim()
						: flatcharEntity.getFlatcharCK().getFchBldgcode());
		flatcharEntity.getFlatcharCK().setFchFlatnum(
				Objects.nonNull(flatcharRequestBean.getFlatnum()) ? flatcharRequestBean.getFlatnum().trim()
						: flatcharEntity.getFlatcharCK().getFchFlatnum());
		flatcharEntity.getFlatcharCK().setFchAccomtype(
				Objects.nonNull(flatcharRequestBean.getAccomtype()) ? flatcharRequestBean.getAccomtype().trim()
						: flatcharEntity.getFlatcharCK().getFchAccomtype());
		flatcharEntity.getFlatcharCK()
				.setFchChargecode(Objects.nonNull(flatcharRequestBean.getChargecode())
						? flatcharRequestBean.getChargecode().trim()
						: flatcharEntity.getFlatcharCK().getFchChargecode());
		flatcharEntity.getFlatcharCK()
				.setFchWing(Objects.nonNull(flatcharRequestBean.getWing()) ? flatcharRequestBean.getWing().trim()
						: flatcharEntity.getFlatcharCK().getFchWing());

		flatcharEntity.setFchAmtdue(Objects.nonNull(flatcharRequestBean.getAmtdue()) ? flatcharRequestBean.getAmtdue()
				: flatcharEntity.getFchAmtdue());
		flatcharEntity
				.setFchAmtpaid(Objects.nonNull(flatcharRequestBean.getAmtpaid()) ? flatcharRequestBean.getAmtpaid()
						: flatcharEntity.getFchAmtpaid());
		flatcharEntity.setFchOrigsite(GenericAuditContextHolder.getContext().getSite());
		flatcharEntity.setFchSite(GenericAuditContextHolder.getContext().getSite());
		flatcharEntity.setFchSqftwiseyn(
				Objects.nonNull(flatcharRequestBean.getSqftwiseyn()) ? flatcharRequestBean.getSqftwiseyn().trim()
						: flatcharEntity.getFchSqftwiseyn());
		flatcharEntity.setFchOrigsite(GenericAuditContextHolder.getContext().getSite());
		flatcharEntity.setFchSite(flatcharRequestBean.getSite());
		flatcharEntity.setFchToday(LocalDateTime.now());
		flatcharEntity.setFchUserid(flatcharRequestBean.getUserid());
		//flatcharEntity.setFchSite(GenericAuditContextHolder.getContext().getSite());
		//flatcharEntity.setFchUserid(GenericAuditContextHolder.getContext().getUserid());
		//flatcharEntity.setFchUserid(GenericAuditContextHolder.getContext().getUserid());

		return flatcharEntity;
	};

}
