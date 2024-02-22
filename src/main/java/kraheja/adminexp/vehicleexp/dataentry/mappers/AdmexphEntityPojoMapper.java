package kraheja.adminexp.vehicleexp.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.commons.collections4.CollectionUtils;

import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexphRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexphResponseBean;
import kraheja.adminexp.vehicleexp.dataentry.bean.response.AdmexphResponseBean.AdmexphResponseBeanBuilder;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexph;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexphCK;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;

public interface AdmexphEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], AdmexphResponseBean> fetchAdmexphEntityPojoMapper = objectArray -> {
		AdmexphResponseBeanBuilder admexphBeanBuilder = AdmexphResponseBean.builder();
		Admexph admexphEntity = (Admexph) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
				? objectArray[BigInteger.ZERO.intValue()]
				: null);
		List<Admexpd> admexpdEntityList = (List<Admexpd>) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		 
		admexphBeanBuilder.certnum(admexphEntity.getAdmexphCK().getAdmhCertnum())
					.coy(admexphEntity.getAdmexphCK().getAdmhCoy())
					.certtype(admexphEntity.getAdmhCerttype())
					.average(admexphEntity.getAdmhAverage())
					.billno(admexphEntity.getAdmhBillno())
					.certamount(admexphEntity.getAdmhCertamount())
					.certdate(Objects.nonNull(admexphEntity.getAdmhCertdate()) ? admexphEntity.getAdmhCertdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certrevnum(admexphEntity.getAdmhCertrevnum())
					.certstatus(admexphEntity.getAdmhCertstatus())
					.emeterred(admexphEntity.getAdmhEmeterred())
					.equipid(admexphEntity.getAdmhEquipid())
					.estimatedkm(admexphEntity.getAdmhEstimatedkm())
					.gasqty(admexphEntity.getAdmhGasqty())
					.gstyn(admexphEntity.getAdmhGstyn())
					.meterid(admexphEntity.getAdmhMeterid())
					.partybilldate(Objects.nonNull(admexphEntity.getAdmhPartybilldate()) ? admexphEntity.getAdmhPartybilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.partybillref(admexphEntity.getAdmhPartybillref())
					.partycode(admexphEntity.getAdmhPartycode())
					.partytype(admexphEntity.getAdmhPartytype())
					.passedon(Objects.nonNull(admexphEntity.getAdmhPassedon()) ? admexphEntity.getAdmhPassedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.payamount(admexphEntity.getAdmhPayamount())
					.paydate(Objects.nonNull(admexphEntity.getAdmhPaydate()) ? admexphEntity.getAdmhPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.payref(admexphEntity.getAdmhPayref())
					.printed(admexphEntity.getAdmhPrinted())
					.printedon(Objects.nonNull(admexphEntity.getAdmhPrintedon()) ? admexphEntity.getAdmhPrintedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.prop(admexphEntity.getAdmhProp())
					.prv_amt(admexphEntity.getAdmhPrv_Amt())
					.prv_certnum(admexphEntity.getAdmhPrv_Certnum())
					.prv_date(Objects.nonNull(admexphEntity.getAdmhPrv_Date()) ? admexphEntity.getAdmhPrv_Date().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.prv_type(admexphEntity.getAdmhPrv_Type())
					.remarks(admexphEntity.getAdmhRemarks())
					.site(admexphEntity.getAdmhSite())
					.smeterred(admexphEntity.getAdmhSmeterred())
					.socid(admexphEntity.getAdmhSocid())
					.today(admexphEntity.getAdmhToday())
					.transer(admexphEntity.getAdmhTranser())
					.t_payment(admexphEntity.getAdmhT_Payment())
					.userid(admexphEntity.getAdmhUserid());
		
		
		if(CollectionUtils.isNotEmpty(admexpdEntityList))
			admexphBeanBuilder.admexpdResponseBean(AdmexpdEntityPojoMapper.fetchAdmexpdEntityPojoMapper.apply(new Object [] {admexpdEntityList}));  
	
		return admexphBeanBuilder.build();
	};

	public static Function<Object[], Admexph> addAdmexphEntityPojoMapper = objectArray -> {
		AdmexphRequestBean admexphRequestBean = (AdmexphRequestBean) (Objects
		.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String site = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()])
		? objectArray[BigInteger.ONE.intValue()]
: null);

		Admexph.		AdmexphBuilder admexphbuilder = Admexph.builder();

admexphbuilder
			.admexphCK(AdmexphCK.builder()
					.admhCertnum(admexphRequestBean.getCertnum())
					.admhCoy(admexphRequestBean.getCoy())
		.build())
					.admhAverage(admexphRequestBean.getAverage())
					.admhBillno(admexphRequestBean.getBillno())
					.admhCertamount(admexphRequestBean.getCertamount())
					.admhCertdate(Objects.nonNull(admexphRequestBean.getCertdate()) ? LocalDate.parse(admexphRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhCertrevnum(admexphRequestBean.getCertrevnum())
					.admhCertstatus(admexphRequestBean.getCertstatus())
					.admhEmeterred(admexphRequestBean.getEmeterred())
					.admhEquipid(admexphRequestBean.getEquipid())
					.admhEstimatedkm(admexphRequestBean.getEstimatedkm())
					.admhGasqty(admexphRequestBean.getGasqty())
					.admhGstyn(admexphRequestBean.getGstyn())
					.admhMeterid(admexphRequestBean.getMeterid())
					.admhPartybilldate(Objects.nonNull(admexphRequestBean.getPartybilldate()) ? LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhPartybillref(admexphRequestBean.getPartybillref())
					.admhPartycode(admexphRequestBean.getPartycode())
					.admhPartytype(admexphRequestBean.getPartytype())
					.admhPassedon(Objects.nonNull(admexphRequestBean.getPassedon()) ? LocalDate.parse(admexphRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhPayamount(admexphRequestBean.getPayamount())
					.admhPaydate(Objects.nonNull(admexphRequestBean.getPaydate()) ? LocalDate.parse(admexphRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhPayref(admexphRequestBean.getPayref())
					.admhPrinted(admexphRequestBean.getPrinted())
					.admhPrintedon(Objects.nonNull(admexphRequestBean.getPrintedon()) ? LocalDate.parse(admexphRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhProp(admexphRequestBean.getProp())
					.admhPrv_Amt(admexphRequestBean.getPrv_amt())
					.admhPrv_Certnum(admexphRequestBean.getPrv_certnum())
					.admhPrv_Date(Objects.nonNull(admexphRequestBean.getPrv_date()) ? LocalDate.parse(admexphRequestBean.getPrv_date(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.admhPrv_Type(admexphRequestBean.getPrv_type())
					.admhRemarks(admexphRequestBean.getRemarks())
					.admhSite(GenericAuditContextHolder.getContext().getSite())
					.admhSmeterred(admexphRequestBean.getSmeterred())
					.admhSocid(admexphRequestBean.getSocid())
					.admhToday(LocalDateTime.now())
					.admhTranser(admexphRequestBean.getTranser())
					.admhT_Payment(admexphRequestBean.getT_payment())
					.admhUserid(GenericAuditContextHolder.getContext().getUserid())
		;

		return admexphbuilder.build();
};

//@SuppressWarnings("unchecked")
//public static Function<Object[], AdmexphRequestBean> updateAdmexphEntityPojoMapper = objectArray -> {
//	AdmexphRequestBean admexphRequestBean = (AdmexphRequestBean)(Objects
//			.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
//	Admexph admexphEntity = (Admexph) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//			? objectArray[BigInteger.ZERO.intValue()]
//			: null);
//	List<Admexpd> admexpdEntityList = (List<Admexpd>) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
//	
//	admexphEntity.getAdmexphCK().setAdmhCertnum(Objects.nonNull(admexphRequestBean.getCertnum()) ? admexphRequestBean.getCertnum().trim() : admexphEntity.getAdmexphCK().getAdmhCertnum());
//	admexphEntity.getAdmexphCK().setAdmhCoy(Objects.nonNull(admexphRequestBean.getCoy()) ? admexphRequestBean.getCoy().trim() : admexphEntity.getAdmexphCK().getAdmhCoy());
//	admexphEntity.setAdmhCerttype(Objects.nonNull(admexphRequestBean.getCerttype()) ? admexphRequestBean.getCerttype().trim() : admexphEntity.getAdmhCerttype());
//	admexphEntity.setAdmhAverage(Objects.nonNull(admexphRequestBean.getAverage()) ? admexphRequestBean.getAverage() : admexphEntity.getAdmhAverage());
//	admexphEntity.setAdmhBillno(Objects.nonNull(admexphRequestBean.getBillno()) ? admexphRequestBean.getBillno().trim() : admexphEntity.getAdmhBillno());
//	admexphEntity.setAdmhCertamount(Objects.nonNull(admexphRequestBean.getCertamount()) ? admexphRequestBean.getCertamount() : admexphEntity.getAdmhCertamount());
//	admexphEntity.setAdmhCertdate(Objects.nonNull(admexphRequestBean.getCertdate()) ? LocalDate.parse(admexphRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhCertdate());
//	admexphEntity.setAdmhCertrevnum(Objects.nonNull(admexphRequestBean.getCertrevnum()) ? admexphRequestBean.getCertrevnum() : admexphEntity.getAdmhCertrevnum());
//	admexphEntity.setAdmhCertstatus(Objects.nonNull(admexphRequestBean.getCertstatus()) ? admexphRequestBean.getCertstatus().trim() : admexphEntity.getAdmhCertstatus());
//	admexphEntity.setAdmhEmeterred(Objects.nonNull(admexphRequestBean.getEmeterred()) ? admexphRequestBean.getEmeterred() : admexphEntity.getAdmhEmeterred());
//	admexphEntity.setAdmhEquipid(Objects.nonNull(admexphRequestBean.getEquipid()) ? admexphRequestBean.getEquipid().trim() : admexphEntity.getAdmhEquipid());
//	admexphEntity.setAdmhEstimatedkm(Objects.nonNull(admexphRequestBean.getEstimatedkm()) ? admexphRequestBean.getEstimatedkm() : admexphEntity.getAdmhEstimatedkm());
//	admexphEntity.setAdmhGasqty(Objects.nonNull(admexphRequestBean.getGasqty()) ? admexphRequestBean.getGasqty() : admexphEntity.getAdmhGasqty());
//	admexphEntity.setAdmhGstyn(Objects.nonNull(admexphRequestBean.getGstyn()) ? admexphRequestBean.getGstyn().trim() : admexphEntity.getAdmhGstyn());
//	admexphEntity.setAdmhMeterid(Objects.nonNull(admexphRequestBean.getMeterid()) ? admexphRequestBean.getMeterid().trim() : admexphEntity.getAdmhMeterid());
//	admexphEntity.setAdmhPartybilldate(Objects.nonNull(admexphRequestBean.getPartybilldate()) ? LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPartybilldate());
//	admexphEntity.setAdmhPartybillref(Objects.nonNull(admexphRequestBean.getPartybillref()) ? admexphRequestBean.getPartybillref().trim() : admexphEntity.getAdmhPartybillref());
//	admexphEntity.setAdmhPartycode(Objects.nonNull(admexphRequestBean.getPartycode()) ? admexphRequestBean.getPartycode().trim() : admexphEntity.getAdmhPartycode());
//	admexphEntity.setAdmhPartytype(Objects.nonNull(admexphRequestBean.getPartytype()) ? admexphRequestBean.getPartytype().trim() : admexphEntity.getAdmhPartytype());
//	admexphEntity.setAdmhPassedon(Objects.nonNull(admexphRequestBean.getPassedon()) ? LocalDate.parse(admexphRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPassedon());
//	admexphEntity.setAdmhPayamount(Objects.nonNull(admexphRequestBean.getPayamount()) ? admexphRequestBean.getPayamount() : admexphEntity.getAdmhPayamount());
//	admexphEntity.setAdmhPaydate(Objects.nonNull(admexphRequestBean.getPaydate()) ? LocalDate.parse(admexphRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPaydate());
//	admexphEntity.setAdmhPayref(Objects.nonNull(admexphRequestBean.getPayref()) ? admexphRequestBean.getPayref().trim() : admexphEntity.getAdmhPayref());
//	admexphEntity.setAdmhPrinted(Objects.nonNull(admexphRequestBean.getPrinted()) ? admexphRequestBean.getPrinted() : admexphEntity.getAdmhPrinted());
//	admexphEntity.setAdmhPrintedon(Objects.nonNull(admexphRequestBean.getPrintedon()) ? LocalDate.parse(admexphRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPrintedon());
//	admexphEntity.setAdmhProp(Objects.nonNull(admexphRequestBean.getProp()) ? admexphRequestBean.getProp().trim() : admexphEntity.getAdmhProp());
//	admexphEntity.setAdmhPrv_Amt(Objects.nonNull(admexphRequestBean.getPrv_amt()) ? admexphRequestBean.getPrv_amt() : admexphEntity.getAdmhPrv_Amt());
//	admexphEntity.setAdmhPrv_Certnum(Objects.nonNull(admexphRequestBean.getPrv_certnum()) ? admexphRequestBean.getPrv_certnum().trim() : admexphEntity.getAdmhPrv_Certnum());
//	admexphEntity.setAdmhPrv_Date(Objects.nonNull(admexphRequestBean.getPrv_date()) ? LocalDate.parse(admexphRequestBean.getPrv_date(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPrv_Date());
//	admexphEntity.setAdmhPrv_Type(Objects.nonNull(admexphRequestBean.getPrv_type()) ? admexphRequestBean.getPrv_type().trim() : admexphEntity.getAdmhPrv_Type());
//	admexphEntity.setAdmhRemarks(Objects.nonNull(admexphRequestBean.getRemarks()) ? admexphRequestBean.getRemarks().trim() : admexphEntity.getAdmhRemarks());
//	admexphEntity.setAdmhSite(Objects.nonNull(admexphRequestBean.getSite()) ? admexphRequestBean.getSite().trim() : admexphEntity.getAdmhSite());
//	admexphEntity.setAdmhSmeterred(Objects.nonNull(admexphRequestBean.getSmeterred()) ? admexphRequestBean.getSmeterred() : admexphEntity.getAdmhSmeterred());
//	admexphEntity.setAdmhSocid(Objects.nonNull(admexphRequestBean.getSocid()) ? admexphRequestBean.getSocid().trim() : admexphEntity.getAdmhSocid());
//	admexphEntity.setAdmhToday(Objects.nonNull(admexphRequestBean.getToday()) ? admexphRequestBean.getToday() : admexphEntity.getAdmhToday());
//	admexphEntity.setAdmhTranser(Objects.nonNull(admexphRequestBean.getTranser()) ? admexphRequestBean.getTranser().trim() : admexphEntity.getAdmhTranser());
//	admexphEntity.setAdmhT_Payment(Objects.nonNull(admexphRequestBean.getT_payment()) ? admexphRequestBean.getT_payment() : admexphEntity.getAdmhT_Payment());
//	admexphEntity.setAdmhUserid(Objects.nonNull(admexphRequestBean.getUserid()) ? admexphRequestBean.getUserid().trim() : admexphEntity.getAdmhUserid());
//
//	if(Objects.nonNull(admexpdEntityList))
//		admexphRequestBean.setAdmexpdRequestBean((List<AdmexpdRequestBean>) AdmexpdEntityPojoMapper.updateAdmexpdEntityPojoMapper.apply(Admexpd, admexpdEntityList));
//	return admexphRequestBean;
//};

	public static BiFunction<Admexph, AdmexphRequestBean, Admexph> updateAdmexphEntityPojoMapper = (admexphEntity, admexphRequestBean) -> {
		admexphEntity.getAdmexphCK().setAdmhCertnum(Objects.nonNull(admexphRequestBean.getCertnum()) ? admexphRequestBean.getCertnum().trim() : admexphEntity.getAdmexphCK().getAdmhCertnum());
		admexphEntity.getAdmexphCK().setAdmhCoy(Objects.nonNull(admexphRequestBean.getCoy()) ? admexphRequestBean.getCoy().trim() : admexphEntity.getAdmexphCK().getAdmhCoy());
		admexphEntity.setAdmhCerttype(Objects.nonNull(admexphRequestBean.getCerttype()) ? admexphRequestBean.getCerttype().trim() : admexphEntity.getAdmhCerttype());
		admexphEntity.setAdmhAverage(Objects.nonNull(admexphRequestBean.getAverage()) ? admexphRequestBean.getAverage() : admexphEntity.getAdmhAverage());
		admexphEntity.setAdmhBillno(Objects.nonNull(admexphRequestBean.getBillno()) ? admexphRequestBean.getBillno().trim() : admexphEntity.getAdmhBillno());
		admexphEntity.setAdmhCertamount(Objects.nonNull(admexphRequestBean.getCertamount()) ? admexphRequestBean.getCertamount() : admexphEntity.getAdmhCertamount());
		admexphEntity.setAdmhCertdate(Objects.nonNull(admexphRequestBean.getCertdate()) ? LocalDate.parse(admexphRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhCertdate());
		admexphEntity.setAdmhCertrevnum(Objects.nonNull(admexphRequestBean.getCertrevnum()) ? admexphRequestBean.getCertrevnum() : admexphEntity.getAdmhCertrevnum());
		admexphEntity.setAdmhCertstatus(Objects.nonNull(admexphRequestBean.getCertstatus()) ? admexphRequestBean.getCertstatus().trim() : admexphEntity.getAdmhCertstatus());
		admexphEntity.setAdmhEmeterred(Objects.nonNull(admexphRequestBean.getEmeterred()) ? admexphRequestBean.getEmeterred() : admexphEntity.getAdmhEmeterred());
		admexphEntity.setAdmhEquipid(Objects.nonNull(admexphRequestBean.getEquipid()) ? admexphRequestBean.getEquipid().trim() : admexphEntity.getAdmhEquipid());
		admexphEntity.setAdmhEstimatedkm(Objects.nonNull(admexphRequestBean.getEstimatedkm()) ? admexphRequestBean.getEstimatedkm() : admexphEntity.getAdmhEstimatedkm());
		admexphEntity.setAdmhGasqty(Objects.nonNull(admexphRequestBean.getGasqty()) ? admexphRequestBean.getGasqty() : admexphEntity.getAdmhGasqty());
		admexphEntity.setAdmhGstyn(Objects.nonNull(admexphRequestBean.getGstyn()) ? admexphRequestBean.getGstyn().trim() : admexphEntity.getAdmhGstyn());
		admexphEntity.setAdmhMeterid(Objects.nonNull(admexphRequestBean.getMeterid()) ? admexphRequestBean.getMeterid().trim() : admexphEntity.getAdmhMeterid());
		admexphEntity.setAdmhPartybilldate(Objects.nonNull(admexphRequestBean.getPartybilldate()) ? LocalDate.parse(admexphRequestBean.getPartybilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPartybilldate());
		admexphEntity.setAdmhPartybillref(Objects.nonNull(admexphRequestBean.getPartybillref()) ? admexphRequestBean.getPartybillref().trim() : admexphEntity.getAdmhPartybillref());
		admexphEntity.setAdmhPartycode(Objects.nonNull(admexphRequestBean.getPartycode()) ? admexphRequestBean.getPartycode().trim() : admexphEntity.getAdmhPartycode());
		admexphEntity.setAdmhPartytype(Objects.nonNull(admexphRequestBean.getPartytype()) ? admexphRequestBean.getPartytype().trim() : admexphEntity.getAdmhPartytype());
		admexphEntity.setAdmhPassedon(Objects.nonNull(admexphRequestBean.getPassedon()) ? LocalDate.parse(admexphRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPassedon());
		admexphEntity.setAdmhPayamount(Objects.nonNull(admexphRequestBean.getPayamount()) ? admexphRequestBean.getPayamount() : admexphEntity.getAdmhPayamount());
		admexphEntity.setAdmhPaydate(Objects.nonNull(admexphRequestBean.getPaydate()) ? LocalDate.parse(admexphRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPaydate());
		admexphEntity.setAdmhPayref(Objects.nonNull(admexphRequestBean.getPayref()) ? admexphRequestBean.getPayref().trim() : admexphEntity.getAdmhPayref());
		admexphEntity.setAdmhPrinted(Objects.nonNull(admexphRequestBean.getPrinted()) ? admexphRequestBean.getPrinted() : admexphEntity.getAdmhPrinted());
		admexphEntity.setAdmhPrintedon(Objects.nonNull(admexphRequestBean.getPrintedon()) ? LocalDate.parse(admexphRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPrintedon());
		admexphEntity.setAdmhProp(Objects.nonNull(admexphRequestBean.getProp()) ? admexphRequestBean.getProp().trim() : admexphEntity.getAdmhProp());
		admexphEntity.setAdmhPrv_Amt(Objects.nonNull(admexphRequestBean.getPrv_amt()) ? admexphRequestBean.getPrv_amt() : admexphEntity.getAdmhPrv_Amt());
		admexphEntity.setAdmhPrv_Certnum(Objects.nonNull(admexphRequestBean.getPrv_certnum()) ? admexphRequestBean.getPrv_certnum().trim() : admexphEntity.getAdmhPrv_Certnum());
		admexphEntity.setAdmhPrv_Date(Objects.nonNull(admexphRequestBean.getPrv_date()) ? LocalDate.parse(admexphRequestBean.getPrv_date(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : admexphEntity.getAdmhPrv_Date());
		admexphEntity.setAdmhPrv_Type(Objects.nonNull(admexphRequestBean.getPrv_type()) ? admexphRequestBean.getPrv_type().trim() : admexphEntity.getAdmhPrv_Type());
		admexphEntity.setAdmhRemarks(Objects.nonNull(admexphRequestBean.getRemarks()) ? admexphRequestBean.getRemarks().trim() : admexphEntity.getAdmhRemarks());
		admexphEntity.setAdmhSite(Objects.nonNull(admexphRequestBean.getSite()) ? admexphRequestBean.getSite().trim() : admexphEntity.getAdmhSite());
		admexphEntity.setAdmhSmeterred(Objects.nonNull(admexphRequestBean.getSmeterred()) ? admexphRequestBean.getSmeterred() : admexphEntity.getAdmhSmeterred());
		admexphEntity.setAdmhSocid(Objects.nonNull(admexphRequestBean.getSocid()) ? admexphRequestBean.getSocid().trim() : admexphEntity.getAdmhSocid());
		admexphEntity.setAdmhToday(Objects.nonNull(admexphRequestBean.getToday()) ? admexphRequestBean.getToday() : LocalDateTime.now());
		admexphEntity.setAdmhTranser(Objects.nonNull(admexphRequestBean.getTranser()) ? admexphRequestBean.getTranser().trim() : admexphEntity.getAdmhTranser());
		admexphEntity.setAdmhT_Payment(Objects.nonNull(admexphRequestBean.getT_payment()) ? admexphRequestBean.getT_payment() : admexphEntity.getAdmhT_Payment());
		admexphEntity.setAdmhUserid(Objects.nonNull(admexphRequestBean.getUserid()) ? admexphRequestBean.getUserid().trim() : admexphEntity.getAdmhUserid());


		return admexphEntity;
	};

}