// Developed By  - 	sandesh.c
// Developed on - 17-08-23
// Mode  - Data Entry
// Purpose - Flatowner Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

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
import kraheja.sales.bean.request.FlatownerRequestBean;
import kraheja.sales.bean.response.FlatownerResponseBean;
import kraheja.sales.entity.Flatowner;
import kraheja.sales.entity.FlatownerCK;

public interface FlatownerEntityPojoMapper {

	@SuppressWarnings("unchecked")
	public static Function<List<Flatowner>, List<FlatownerResponseBean>> fetchFlatownerEntityPojoMapper = flatownerEntityList -> {
		return flatownerEntityList.stream().map(flatownerEntity -> {
			return FlatownerResponseBean.builder().ownerid(flatownerEntity.getFlatownerCK().getFownOwnerid())
					.bldgcode(flatownerEntity.getFlatownerCK().getFownBldgcode())
					.wing(flatownerEntity.getFlatownerCK().getFownWing())
					.flatnum(flatownerEntity.getFlatownerCK().getFownFlatnum())
					.ownertype(flatownerEntity.getFlatownerCK().getFownOwnertype())
					.aadharno(flatownerEntity.getFownAadharno()).adminrate(flatownerEntity.getFownAdminrate())
					.auxiadmin(flatownerEntity.getFownAuxiadmin()).auximonths(flatownerEntity.getFownAuximonths())
					.auxirate(flatownerEntity.getFownAuxirate())
					.billmode(flatownerEntity.getFownBillmode())
					.cencard(flatownerEntity.getFownCencard()).city(flatownerEntity.getFownCity())
					.custtype(flatownerEntity.getFownCusttype()).elect(flatownerEntity.getFownElect())
					.floor(flatownerEntity.getFownFloor()).gstno(flatownerEntity.getFownGstno())
					.infradmin(flatownerEntity.getFownInfradmin()).inframonths(flatownerEntity.getFownInframonths())
					.infrrate(flatownerEntity.getFownInfrrate()).maintrate(flatownerEntity.getFownMaintrate())
					.name(flatownerEntity.getFownName()).natax(flatownerEntity.getFownNatax())
					.nreacnum(flatownerEntity.getFownNreacnum()).nrebank(flatownerEntity.getFownNrebank())
					.nriipi7(flatownerEntity.getFownNriipi7()).nrinat(flatownerEntity.getFownNrinat())
					.nripass(flatownerEntity.getFownNripass()).nripassiss(flatownerEntity.getFownNripassiss())
					
					.nripedate(Objects.nonNull(flatownerEntity.getFownNripedate())
							? flatownerEntity.getFownNripedate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					
					.nripedate(Objects.nonNull(flatownerEntity.getFownNripedate())
							? flatownerEntity.getFownNripedate().toString()
							: null)
					
					.nrippidate(Objects.nonNull(flatownerEntity.getFownNrippidate())
							? flatownerEntity.getFownNrippidate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					
					.nrippidate(Objects.nonNull(flatownerEntity.getFownNrippidate())
							? flatownerEntity.getFownNrippidate().toString()
							: null)
					
					.nrippidate(Objects.nonNull(flatownerEntity.getFownNrippidate())
							? flatownerEntity.getFownNrippidate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)

					.nrippidate(Objects.nonNull(flatownerEntity.getFownNrippidate())
							? flatownerEntity.getFownNrippidate().toString()
							: null)
					
					.nripnat(flatownerEntity.getFownNripnat()).nriprof(flatownerEntity.getFownNriprof())
					.nriteloff(flatownerEntity.getFownNriteloff()).nritelres(flatownerEntity.getFownNritelres())
					.nroacnum(flatownerEntity.getFownNroacnum()).nrobank(flatownerEntity.getFownNrobank())
					.ogendmm(flatownerEntity.getFownOgendmm()).ogintpaid(flatownerEntity.getFownOgintpaid())
					.ogmonths(flatownerEntity.getFownOgmonths()).ogstartmm(flatownerEntity.getFownOgstartmm())
					.origsite(flatownerEntity.getFownOrigsite()).panno(flatownerEntity.getFownPanno())
					.poanat(flatownerEntity.getFownPoanat()).poapass(flatownerEntity.getFownPoapass())
					.poapassiss(flatownerEntity.getFownPoapassiss())
					
					.poappidate(Objects.nonNull(flatownerEntity.getFownPoappidate())
							? flatownerEntity.getFownPoappidate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					
					.poappidate(Objects.nonNull(flatownerEntity.getFownPoappidate())
							? flatownerEntity.getFownPoappidate().toString()
							: null)
					
					.poaprof(flatownerEntity.getFownPoaprof()).relation(flatownerEntity.getFownRelation())
					.site(flatownerEntity.getFownSite()).title(flatownerEntity.getFownTitle())
					.today(flatownerEntity.getFownToday()).township(flatownerEntity.getFownTownship())
					.userid(flatownerEntity.getFownUserid()).vipyn(flatownerEntity.getFownVipyn())
					.water(flatownerEntity.getFownWater()).build();
		}).collect(Collectors.toList());
	};


	public static Function<List<FlatownerRequestBean>, List <Flatowner>> addFlatownerPojoEntityMapper = (flatownerRequestBeanList) -> { 
		return flatownerRequestBeanList.stream().map(flatownerRequestBean -> {
		return Flatowner.builder().flatownerCK(FlatownerCK.builder()
							.fownOwnerid(flatownerRequestBean.getOwnerid())
							.fownBldgcode(flatownerRequestBean.getBldgcode())
							.fownWing(flatownerRequestBean.getWing())
							.fownFlatnum(flatownerRequestBean.getFlatnum())
							.fownOwnertype(flatownerRequestBean.getOwnertype())
				.build())

							.fownAadharno(flatownerRequestBean.getAadharno())
							.fownAdminrate(Objects.nonNull(flatownerRequestBean.getAdminrate()) ? flatownerRequestBean.getAdminrate() : BigInteger.ZERO.doubleValue())
							.fownAuxiadmin(Objects.nonNull(flatownerRequestBean.getAuxiadmin()) ? flatownerRequestBean.getAuxiadmin() : BigInteger.ZERO.doubleValue())
							.fownAuximonths(Objects.nonNull(flatownerRequestBean.getAuximonths()) ? flatownerRequestBean.getAuximonths() : BigInteger.ZERO.intValue())
							.fownAuxirate(Objects.nonNull(flatownerRequestBean.getAuxirate()) ? flatownerRequestBean.getAuxirate() : BigInteger.ZERO.doubleValue())
							//.fownBillmode(flatownerRequestBean.getBillmode())
							.fownBillmode("Q")
							.fownCencard(flatownerRequestBean.getCencard())
							.fownCity(flatownerRequestBean.getCity())
							.fownCusttype(flatownerRequestBean.getCusttype())
							.fownElect(Objects.nonNull(flatownerRequestBean.getElect()) ? flatownerRequestBean.getElect() : BigInteger.ZERO.doubleValue())
							.fownFloor(flatownerRequestBean.getFloor())
							.fownGstno(flatownerRequestBean.getGstno())
							.fownInfradmin(Objects.nonNull(flatownerRequestBean.getInfradmin()) ? flatownerRequestBean.getInfradmin() : BigInteger.ZERO.doubleValue())
							.fownInframonths(Objects.nonNull(flatownerRequestBean.getInframonths()) ? flatownerRequestBean.getInframonths() : BigInteger.ZERO.intValue())
							.fownInfrrate(Objects.nonNull(flatownerRequestBean.getInfrrate()) ? flatownerRequestBean.getInfrrate() : BigInteger.ZERO.doubleValue())
							.fownMaintrate(Objects.nonNull(flatownerRequestBean.getMaintrate()) ? flatownerRequestBean.getMaintrate() : BigInteger.ZERO.doubleValue())
							.fownName(flatownerRequestBean.getName())
							.fownNatax(Objects.nonNull(flatownerRequestBean.getNatax()) ? flatownerRequestBean.getNatax() : BigInteger.ZERO.doubleValue())
							.fownNreacnum(flatownerRequestBean.getNreacnum())
							.fownNrebank(flatownerRequestBean.getNrebank())
							.fownNriipi7(flatownerRequestBean.getNriipi7())
							.fownNrinat(flatownerRequestBean.getNrinat())
							.fownNripass(flatownerRequestBean.getNripass())
							.fownNripassiss(flatownerRequestBean.getNripassiss())
							.fownNripedate(Objects.nonNull(flatownerRequestBean.getNripedate()) ? LocalDate.parse(flatownerRequestBean.getNripedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.fownNripnat(flatownerRequestBean.getNripnat())
							.fownNrippidate(Objects.nonNull(flatownerRequestBean.getNrippidate()) ? LocalDate.parse(flatownerRequestBean.getNrippidate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.fownNriprof(flatownerRequestBean.getNriprof())
							.fownNriteloff(flatownerRequestBean.getNriteloff())
							.fownNritelres(flatownerRequestBean.getNritelres())
							.fownNroacnum(flatownerRequestBean.getNroacnum())
							.fownNrobank(flatownerRequestBean.getNrobank())
							.fownOgendmm(flatownerRequestBean.getOgendmm())
							.fownOgintpaid(Objects.nonNull(flatownerRequestBean.getOgintpaid()) ? flatownerRequestBean.getOgintpaid() : BigInteger.ZERO.doubleValue())
							.fownOgmonths(Objects.nonNull(flatownerRequestBean.getOgmonths()) ? flatownerRequestBean.getOgmonths() : BigInteger.ZERO.intValue())
							.fownOgstartmm(flatownerRequestBean.getOgstartmm())
							.fownOrigsite(GenericAuditContextHolder.getContext().getSite())
							.fownPanno(flatownerRequestBean.getPanno())
							.fownPoanat(flatownerRequestBean.getPoanat())
							.fownPoapass(flatownerRequestBean.getPoapass())
							.fownPoapassiss(flatownerRequestBean.getPoapassiss())
							.fownPoappidate(Objects.nonNull(flatownerRequestBean.getPoappidate()) 
									? LocalDate.parse(flatownerRequestBean.getPoappidate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
							.fownPoaprof(flatownerRequestBean.getPoaprof())
							.fownRelation(flatownerRequestBean.getRelation())
							//.fownSite(GenericAuditContextHolder.getContext().getSite())
							.fownSite("MUM")
							.fownTitle(flatownerRequestBean.getTitle())
							.fownToday(LocalDateTime.now())
							.fownTownship(flatownerRequestBean.getTownship())
							//.fownUserid(GenericAuditContextHolder.getContext().getUserid())
							.fownUserid("SANDY")
							.fownVipyn(flatownerRequestBean.getVipyn())
							.fownWater(Objects.nonNull(flatownerRequestBean.getWater()) ? flatownerRequestBean.getWater() : BigInteger.ZERO.doubleValue())
				
		.build();
		}).collect(Collectors.toList());
		} ;

	public static BiFunction<Flatowner, FlatownerRequestBean, Flatowner> updateFlatownerEntityPojoMapper = (
			flatownerEntity, flatownerRequestBean) -> {
		flatownerEntity.getFlatownerCK().setFownOwnerid(
				Objects.nonNull(flatownerRequestBean.getOwnerid()) ? flatownerRequestBean.getOwnerid().trim()
						: flatownerEntity.getFlatownerCK().getFownOwnerid());
		flatownerEntity.getFlatownerCK().setFownBldgcode(
				Objects.nonNull(flatownerRequestBean.getBldgcode()) ? flatownerRequestBean.getBldgcode().trim()
						: flatownerEntity.getFlatownerCK().getFownBldgcode());
		flatownerEntity.getFlatownerCK()
				.setFownWing(Objects.nonNull(flatownerRequestBean.getWing()) ? flatownerRequestBean.getWing().trim()
						: flatownerEntity.getFlatownerCK().getFownWing());
		flatownerEntity.getFlatownerCK().setFownFlatnum(
				Objects.nonNull(flatownerRequestBean.getFlatnum()) ? flatownerRequestBean.getFlatnum().trim()
						: flatownerEntity.getFlatownerCK().getFownFlatnum());
		flatownerEntity.getFlatownerCK()
				.setFownOwnertype(Objects.nonNull(flatownerRequestBean.getOwnertype())
						? flatownerRequestBean.getOwnertype().trim()
						: flatownerEntity.getFlatownerCK().getFownOwnertype());

		flatownerEntity.setFownAadharno(
				Objects.nonNull(flatownerRequestBean.getAadharno()) ? flatownerRequestBean.getAadharno().trim()
						: flatownerEntity.getFownAadharno());
		flatownerEntity.setFownAdminrate(
				Objects.nonNull(flatownerRequestBean.getAdminrate()) ? flatownerRequestBean.getAdminrate()
						: flatownerEntity.getFownAdminrate());
		flatownerEntity.setFownAuxiadmin(
				Objects.nonNull(flatownerRequestBean.getAuxiadmin()) ? flatownerRequestBean.getAuxiadmin()
						: flatownerEntity.getFownAuxiadmin());
		flatownerEntity.setFownAuximonths(
				Objects.nonNull(flatownerRequestBean.getAuximonths()) ? flatownerRequestBean.getAuximonths()
						: flatownerEntity.getFownAuximonths());
		flatownerEntity.setFownAuxirate(
				Objects.nonNull(flatownerRequestBean.getAuxirate()) ? flatownerRequestBean.getAuxirate()
						: flatownerEntity.getFownAuxirate());
		flatownerEntity.setFownBillmode(
				Objects.nonNull(flatownerRequestBean.getBillmode()) ? flatownerRequestBean.getBillmode().trim()
						: flatownerEntity.getFownBillmode());
		flatownerEntity.setFownCencard(
				Objects.nonNull(flatownerRequestBean.getCencard()) ? flatownerRequestBean.getCencard().trim()
						: flatownerEntity.getFownCencard());
		flatownerEntity
				.setFownCity(Objects.nonNull(flatownerRequestBean.getCity()) ? flatownerRequestBean.getCity().trim()
						: flatownerEntity.getFownCity());
		flatownerEntity.setFownCusttype(
				Objects.nonNull(flatownerRequestBean.getCusttype()) ? flatownerRequestBean.getCusttype().trim()
						: flatownerEntity.getFownCusttype());
		flatownerEntity.setFownElect(Objects.nonNull(flatownerRequestBean.getElect()) ? flatownerRequestBean.getElect()
				: flatownerEntity.getFownElect());
		flatownerEntity
				.setFownFloor(Objects.nonNull(flatownerRequestBean.getFloor()) ? flatownerRequestBean.getFloor().trim()
						: flatownerEntity.getFownFloor());
		flatownerEntity
				.setFownGstno(Objects.nonNull(flatownerRequestBean.getGstno()) ? flatownerRequestBean.getGstno().trim()
						: flatownerEntity.getFownGstno());
		flatownerEntity.setFownInfradmin(
				Objects.nonNull(flatownerRequestBean.getInfradmin()) ? flatownerRequestBean.getInfradmin()
						: flatownerEntity.getFownInfradmin());
		flatownerEntity.setFownInframonths(
				Objects.nonNull(flatownerRequestBean.getInframonths()) ? flatownerRequestBean.getInframonths()
						: flatownerEntity.getFownInframonths());
		flatownerEntity.setFownInfrrate(
				Objects.nonNull(flatownerRequestBean.getInfrrate()) ? flatownerRequestBean.getInfrrate()
						: flatownerEntity.getFownInfrrate());
		flatownerEntity.setFownMaintrate(
				Objects.nonNull(flatownerRequestBean.getMaintrate()) ? flatownerRequestBean.getMaintrate()
						: flatownerEntity.getFownMaintrate());
		flatownerEntity
				.setFownName(Objects.nonNull(flatownerRequestBean.getName()) ? flatownerRequestBean.getName().trim()
						: flatownerEntity.getFownName());
		flatownerEntity.setFownNatax(Objects.nonNull(flatownerRequestBean.getNatax()) ? flatownerRequestBean.getNatax()
				: flatownerEntity.getFownNatax());
		flatownerEntity.setFownNreacnum(
				Objects.nonNull(flatownerRequestBean.getNreacnum()) ? flatownerRequestBean.getNreacnum().trim()
						: flatownerEntity.getFownNreacnum());
		flatownerEntity.setFownNrebank(
				Objects.nonNull(flatownerRequestBean.getNrebank()) ? flatownerRequestBean.getNrebank().trim()
						: flatownerEntity.getFownNrebank());
		flatownerEntity.setFownNriipi7(
				Objects.nonNull(flatownerRequestBean.getNriipi7()) ? flatownerRequestBean.getNriipi7().trim()
						: flatownerEntity.getFownNriipi7());
		flatownerEntity.setFownNrinat(
				Objects.nonNull(flatownerRequestBean.getNrinat()) ? flatownerRequestBean.getNrinat().trim()
						: flatownerEntity.getFownNrinat());
		flatownerEntity.setFownNripass(
				Objects.nonNull(flatownerRequestBean.getNripass()) ? flatownerRequestBean.getNripass().trim()
						: flatownerEntity.getFownNripass());
		flatownerEntity.setFownNripassiss(
				Objects.nonNull(flatownerRequestBean.getNripassiss()) ? flatownerRequestBean.getNripassiss().trim()
						: flatownerEntity.getFownNripassiss());
		flatownerEntity.setFownNripedate(Objects.nonNull(flatownerRequestBean.getNripedate())
				? LocalDate.parse(flatownerRequestBean.getNripedate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
				: flatownerEntity.getFownNripedate());
		flatownerEntity.setFownNripnat(
				Objects.nonNull(flatownerRequestBean.getNripnat()) ? flatownerRequestBean.getNripnat().trim()
						: flatownerEntity.getFownNripnat());
		flatownerEntity.setFownNrippidate(Objects.nonNull(flatownerRequestBean.getNrippidate())
				? LocalDate.parse(flatownerRequestBean.getNrippidate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
				: flatownerEntity.getFownNrippidate());
		flatownerEntity.setFownNriprof(
				Objects.nonNull(flatownerRequestBean.getNriprof()) ? flatownerRequestBean.getNriprof().trim()
						: flatownerEntity.getFownNriprof());
		flatownerEntity.setFownNriteloff(
				Objects.nonNull(flatownerRequestBean.getNriteloff()) ? flatownerRequestBean.getNriteloff().trim()
						: flatownerEntity.getFownNriteloff());
		flatownerEntity.setFownNritelres(
				Objects.nonNull(flatownerRequestBean.getNritelres()) ? flatownerRequestBean.getNritelres().trim()
						: flatownerEntity.getFownNritelres());
		flatownerEntity.setFownNroacnum(
				Objects.nonNull(flatownerRequestBean.getNroacnum()) ? flatownerRequestBean.getNroacnum().trim()
						: flatownerEntity.getFownNroacnum());
		flatownerEntity.setFownNrobank(
				Objects.nonNull(flatownerRequestBean.getNrobank()) ? flatownerRequestBean.getNrobank().trim()
						: flatownerEntity.getFownNrobank());
		flatownerEntity.setFownOgendmm(
				Objects.nonNull(flatownerRequestBean.getOgendmm()) ? flatownerRequestBean.getOgendmm().trim()
						: flatownerEntity.getFownOgendmm());
		flatownerEntity.setFownOgintpaid(
				Objects.nonNull(flatownerRequestBean.getOgintpaid()) ? flatownerRequestBean.getOgintpaid()
						: flatownerEntity.getFownOgintpaid());
		flatownerEntity.setFownOgmonths(
				Objects.nonNull(flatownerRequestBean.getOgmonths()) ? flatownerRequestBean.getOgmonths()
						: flatownerEntity.getFownOgmonths());
		flatownerEntity.setFownOgstartmm(
				Objects.nonNull(flatownerRequestBean.getOgstartmm()) ? flatownerRequestBean.getOgstartmm().trim()
						: flatownerEntity.getFownOgstartmm());
		flatownerEntity.setFownOrigsite(GenericAuditContextHolder.getContext().getSite());
		flatownerEntity
				.setFownPanno(Objects.nonNull(flatownerRequestBean.getPanno()) ? flatownerRequestBean.getPanno().trim()
						: flatownerEntity.getFownPanno());
		flatownerEntity.setFownPoanat(
				Objects.nonNull(flatownerRequestBean.getPoanat()) ? flatownerRequestBean.getPoanat().trim()
						: flatownerEntity.getFownPoanat());
		flatownerEntity.setFownPoapass(
				Objects.nonNull(flatownerRequestBean.getPoapass()) ? flatownerRequestBean.getPoapass().trim()
						: flatownerEntity.getFownPoapass());
		flatownerEntity.setFownPoapassiss(
				Objects.nonNull(flatownerRequestBean.getPoapassiss()) ? flatownerRequestBean.getPoapassiss().trim()
						: flatownerEntity.getFownPoapassiss());
		flatownerEntity.setFownPoappidate(Objects.nonNull(flatownerRequestBean.getPoappidate())
				? LocalDate.parse(flatownerRequestBean.getPoappidate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
				: flatownerEntity.getFownPoappidate());
		flatownerEntity.setFownPoaprof(
				Objects.nonNull(flatownerRequestBean.getPoaprof()) ? flatownerRequestBean.getPoaprof().trim()
						: flatownerEntity.getFownPoaprof());
		flatownerEntity.setFownRelation(
				Objects.nonNull(flatownerRequestBean.getRelation()) ? flatownerRequestBean.getRelation().trim()
						: flatownerEntity.getFownRelation());
		flatownerEntity
				.setFownTitle(Objects.nonNull(flatownerRequestBean.getTitle()) ? flatownerRequestBean.getTitle().trim()
						: flatownerEntity.getFownTitle());
		flatownerEntity.setFownToday(LocalDateTime.now());
		flatownerEntity.setFownTownship(
				Objects.nonNull(flatownerRequestBean.getTownship()) ? flatownerRequestBean.getTownship().trim()
						: flatownerEntity.getFownTownship());
		flatownerEntity
		.setFownVipyn(Objects.nonNull(flatownerRequestBean.getVipyn()) ? flatownerRequestBean.getVipyn().trim()
				: flatownerEntity.getFownVipyn());
		//flatownerEntity.setFownSite(flatownerRequestBean.getSite());
		//flatownerEntity.setFownUserid(flatownerRequestBean.getUserid());
		//flatownerEntity.setFownSite(GenericAuditContextHolder.getContext().getSite());
		//flatownerEntity.setFownUserid(GenericAuditContextHolder.getContext().getUserid());
		flatownerEntity.setFownSite("MUM");
		flatownerEntity.setFownUserid("SANDY");
		flatownerEntity.setFownWater(Objects.nonNull(flatownerRequestBean.getWater()) ? flatownerRequestBean.getWater()
				: flatownerEntity.getFownWater());

		return flatownerEntity;
	};

}
