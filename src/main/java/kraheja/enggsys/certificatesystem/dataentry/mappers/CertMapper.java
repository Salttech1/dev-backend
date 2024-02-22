package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.enggsys.bean.request.CertRequestBean;
import kraheja.enggsys.bean.response.CertResponseBean;
import kraheja.enggsys.bean.response.CertResponseBean.CertResponseBeanBuilder;
import kraheja.enggsys.entity.Cert;
import kraheja.enggsys.entity.Cert.CertCK;

public interface CertMapper {
	@SuppressWarnings("unchecked")
public static Function<Object[], CertResponseBean> fetchCertEntityPojoMapper = objectArray -> {
Cert certEntity = (Cert) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);

		CertResponseBeanBuilder certResponseBean = CertResponseBean.builder();
		certResponseBean.contract(certEntity.getCertContract())
					.runser(certEntity.getCertRunser())
					.passedon(Objects.nonNull(certEntity.getCertPassedon()) ? certEntity.getCertPassedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certnum(certEntity.getCertCK().getCertCertnum())
					.transer(certEntity.getCertTranser())
					.certstatus(certEntity.getCertCertstatus())
					.certtype(certEntity.getCertCerttype())
					.coy(certEntity.getCertCoy())
					.advadjusted(certEntity.getCertAdvadjusted())
					.asstaxfrom(Objects.nonNull(certEntity.getCertAsstaxfrom()) ? certEntity.getCertAsstaxfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.asstaxupto(Objects.nonNull(certEntity.getCertAsstaxupto()) ? certEntity.getCertAsstaxupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.basicamt(certEntity.getCertBasicamt())
					.billamount(certEntity.getCertBillamount())
					.billdate(Objects.nonNull(certEntity.getCertBilldate()) ? certEntity.getCertBilldate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.billnos(certEntity.getCertBillnos())
					.billref(certEntity.getCertBillref())
					.bldgcode(certEntity.getCertBldgcode())
					.certamount(certEntity.getCertCertamount())
					.certdate(Objects.nonNull(certEntity.getCertCertdate()) ? certEntity.getCertCertdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certrevnum(certEntity.getCertCertrevnum())
					.cfcode(certEntity.getCertCfcode())
					.cfgroup(certEntity.getCertCfgroup())
					.city(certEntity.getCertCity())
					.clearacyn(certEntity.getCertClearacyn())
					.conamount(certEntity.getCertConamount())
					.debit(certEntity.getCertDebit())
					.debitingparty(certEntity.getCertDebitingparty())
					.debitingreason(certEntity.getCertDebitingreason())
					.debsocyn(certEntity.getCertDebsocyn())
					.description(certEntity.getCertDescription())
					.domain(certEntity.getCertDomain())
					.durfrom(Objects.nonNull(certEntity.getCertDurfrom()) ? certEntity.getCertDurfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.durto(Objects.nonNull(certEntity.getCertDurto()) ? certEntity.getCertDurto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.equipid(certEntity.getCertEquipid())
					.flat(certEntity.getCertFlat())
					.krishicessamt(certEntity.getCertKrishicessamt())
					.krishicessperc(certEntity.getCertKrishicessperc())
					.mainmatgroup(certEntity.getCertMainmatgroup())
					.mainparty(certEntity.getCertMainparty())
					.mainrecid(certEntity.getCertMainrecid())
					.matcode(certEntity.getCertMatcode())
					.matgroup(certEntity.getCertMatgroup())
					.misbldg(certEntity.getCertMisbldg())
					.misproject(certEntity.getCertMisproject())
					.mwctaxamount(certEntity.getCertMwctaxamount())
					.orconamount(certEntity.getCertOrconamount())
					.orcontract(certEntity.getCertOrcontract())
					.originator(certEntity.getCertOriginator())
					.origsite(certEntity.getCertOrigsite())
					.orpartycode(certEntity.getCertOrpartycode())
					.orpartytype(certEntity.getCertOrpartytype())
					.partycode(certEntity.getCertPartycode())
					.partytype(certEntity.getCertPartytype())
					.payamount(certEntity.getCertPayamount())
					.paydate(Objects.nonNull(certEntity.getCertPaydate()) ? certEntity.getCertPaydate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.payref(certEntity.getCertPayref())
					.paytender(certEntity.getCertPaytender())
					.perdone(certEntity.getCertPerdone())
					.printed(certEntity.getCertPrinted())
					.printedon(Objects.nonNull(certEntity.getCertPrintedon()) ? certEntity.getCertPrintedon().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.project(certEntity.getCertProject())
					.prop(certEntity.getCertProp())
					.property(certEntity.getCertProperty())
					.provyn(certEntity.getCertProvyn())
					.prvamt(certEntity.getCertPrvAmt())
					.prvcertnum(certEntity.getCertPrvCertnum())
					.prvdate(Objects.nonNull(certEntity.getCertPrvDate()) ? certEntity.getCertPrvDate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.prvtype(certEntity.getCertPrvType())
					.remarks(certEntity.getCertRemarks())
					.reqdate(Objects.nonNull(certEntity.getCertReqdate()) ? certEntity.getCertReqdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.reqnum(certEntity.getCertReqnum())
					.retained(certEntity.getCertRetained())
					.servicetaxamt(certEntity.getCertServicetaxamt())
					.servicetaxperc(certEntity.getCertServicetaxperc())
					.site(certEntity.getCertSite())
					.socid(certEntity.getCertSocid())
					.swachhcessamt(certEntity.getCertSwachhcessamt())
					.swachhcessperc(certEntity.getCertSwachhcessperc())
					.taxlevel(certEntity.getCertTaxlevel())
					.tdsamount(certEntity.getCertTdsamount())
					.tdssur(certEntity.getCertTdssur())
					.today(certEntity.getCertToday())
					.tottwoptc(certEntity.getCertTotTwoptc())
					.tottwtptc(certEntity.getCertTotTwtptc())
					.trips(certEntity.getCertTrips())
					.tadvadj(certEntity.getCertTAdvadj())
					.tadvos(certEntity.getCertTAdvos())
					.tadvpaid(certEntity.getCertTAdvpaid())
					.tdebit(certEntity.getCertTDebit())
					.tdebsoc(certEntity.getCertTDebsoc())
					.tmwctaxamt(certEntity.getCertTMwctaxamt())
					.tpayment(certEntity.getCertTPayment())
					.tperpaid(certEntity.getCertTPerpaid())
					.tretained(certEntity.getCertTRetained())
					.ttdsamt(certEntity.getCertTTdsamt())
					.ttdssur(certEntity.getCertTTdssur())
					.ttrips(certEntity.getCertTTrips())
					.twriteoffamt(certEntity.getCertTWriteoffamt())
					.userid(certEntity.getCertUserid())
					.vatamt(certEntity.getCertVatamt())
					.vatperc(certEntity.getCertVatperc())
					.wing(certEntity.getCertWing())
					.workcode(certEntity.getCertWorkcode())
					.workgroup(certEntity.getCertWorkgroup())
					.writeoffamt(certEntity.getCertWriteoffamt())
.build();
			return certResponseBean.build();
};



	public static Function<Object[], Cert> addCertPojoEntityMapper = objectArray -> {
		CertRequestBean certRequestBean =(CertRequestBean) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String certnum = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		
return Cert.builder()
		.certCK(CertCK.builder()
		.certCertnum(certnum)
		.build())
		.certRunser(Objects.nonNull(certRequestBean.getRunser()) ? certRequestBean.getRunser() : BigInteger.ZERO.intValue())
		.certPassedon(Objects.nonNull(certRequestBean.getPassedon()) ? LocalDate.parse(certRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
		.certTranser(certRequestBean.getTranser())
		.certCertstatus(certRequestBean.getCertstatus())
		.certCerttype(certRequestBean.getCerttype())
		.certCoy(certRequestBean.getCoy())
					.certContract(certRequestBean.getContract())
					.certAdvadjusted(Objects.nonNull(certRequestBean.getAdvadjusted()) ? certRequestBean.getAdvadjusted() : BigInteger.ZERO.doubleValue())
					.certAsstaxfrom(Objects.nonNull(certRequestBean.getAsstaxfrom()) ? LocalDate.parse(certRequestBean.getAsstaxfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certAsstaxupto(Objects.nonNull(certRequestBean.getAsstaxupto()) ? LocalDate.parse(certRequestBean.getAsstaxupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certBasicamt(Objects.nonNull(certRequestBean.getBasicamt()) ? certRequestBean.getBasicamt() : BigInteger.ZERO.intValue())
					.certBillamount(Objects.nonNull(certRequestBean.getBillamount()) ? certRequestBean.getBillamount() : BigInteger.ZERO.doubleValue())
					.certBilldate(Objects.nonNull(certRequestBean.getBilldate()) ? LocalDate.parse(certRequestBean.getBilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certBillnos(certRequestBean.getBillnos())
					.certBillref(certRequestBean.getBillref())
					.certBldgcode(certRequestBean.getBldgcode())
					.certCertamount(Objects.nonNull(certRequestBean.getCertamount()) ? certRequestBean.getCertamount() : BigInteger.ZERO.doubleValue())
					.certCertdate(Objects.nonNull(certRequestBean.getCertdate()) ? LocalDate.parse(certRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certCertrevnum(Objects.nonNull(certRequestBean.getCertrevnum()) ? certRequestBean.getCertrevnum() : BigInteger.ZERO.intValue())
					.certCfcode(certRequestBean.getCfcode())
					.certCfgroup(certRequestBean.getCfgroup())
					.certCity(certRequestBean.getCity())
					.certClearacyn(certRequestBean.getClearacyn())
					.certConamount(Objects.nonNull(certRequestBean.getConamount()) ? certRequestBean.getConamount() : BigInteger.ZERO.doubleValue())
					.certDebit(Objects.nonNull(certRequestBean.getDebit()) ? certRequestBean.getDebit() : BigInteger.ZERO.doubleValue())
					.certDebitingparty(certRequestBean.getDebitingparty())
					.certDebitingreason(certRequestBean.getDebitingreason())
					.certDebsocyn(certRequestBean.getDebsocyn())
					.certDescription(certRequestBean.getDescription())
					.certDomain(certRequestBean.getDomain())
					.certDurfrom(Objects.nonNull(certRequestBean.getDurfrom()) ? LocalDate.parse(certRequestBean.getDurfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certDurto(Objects.nonNull(certRequestBean.getDurto()) ? LocalDate.parse(certRequestBean.getDurto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certEquipid(certRequestBean.getEquipid())
					.certFlat(certRequestBean.getFlat())
					.certKrishicessamt(Objects.nonNull(certRequestBean.getKrishicessamt()) ? certRequestBean.getKrishicessamt() : BigInteger.ZERO.intValue())
					.certKrishicessperc(Objects.nonNull(certRequestBean.getKrishicessperc()) ? certRequestBean.getKrishicessperc() : BigInteger.ZERO.intValue())
					.certMainmatgroup(certRequestBean.getMainmatgroup())
					.certMainparty(certRequestBean.getMainparty())
					.certMainrecid(certRequestBean.getMainrecid())
					.certMatcode(certRequestBean.getMatcode())
					.certMatgroup(certRequestBean.getMatgroup())
					.certMisbldg(certRequestBean.getMisbldg())
					.certMisproject(certRequestBean.getMisproject())
					.certMwctaxamount(Objects.nonNull(certRequestBean.getMwctaxamount()) ? certRequestBean.getMwctaxamount() : BigInteger.ZERO.doubleValue())
					.certOrconamount(Objects.nonNull(certRequestBean.getOrconamount()) ? certRequestBean.getOrconamount() : BigInteger.ZERO.doubleValue())
					.certOrcontract(certRequestBean.getOrcontract())
					.certOriginator(certRequestBean.getOriginator())
					.certOrigsite(GenericAuditContextHolder.getContext().getSite())
					.certOrpartycode(certRequestBean.getOrpartycode())
					.certOrpartytype(certRequestBean.getOrpartytype())
					.certPartycode(certRequestBean.getPartycode())
					.certPartytype(certRequestBean.getPartytype())
					.certPayamount(Objects.nonNull(certRequestBean.getPayamount()) ? certRequestBean.getPayamount() : BigInteger.ZERO.doubleValue())
					.certPaydate(Objects.nonNull(certRequestBean.getPaydate()) ? LocalDate.parse(certRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certPayref(certRequestBean.getPayref())
					.certPaytender(certRequestBean.getPaytender())
					.certPerdone(Objects.nonNull(certRequestBean.getPerdone()) ? certRequestBean.getPerdone() : BigInteger.ZERO.doubleValue())
					.certPrinted(Objects.nonNull(certRequestBean.getPrinted()) ? certRequestBean.getPrinted() : BigInteger.ZERO.intValue())
					.certPrintedon(Objects.nonNull(certRequestBean.getPrintedon()) ? LocalDate.parse(certRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certProject(certRequestBean.getProject())
					.certProp(certRequestBean.getProp())
					.certProperty(certRequestBean.getProperty())
					.certProvyn(certRequestBean.getProvyn())
					.certPrvAmt(Objects.nonNull(certRequestBean.getPrvamt()) ? certRequestBean.getPrvamt() : BigInteger.ZERO.doubleValue())
					.certPrvCertnum(certRequestBean.getPrvcertnum())
//					.certPrvDate(Objects.nonNull(certRequestBean.getPrvdate()) ? LocalDate.parse(certRequestBean.getPrvdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certPrvType(certRequestBean.getPrvtype())
					.certRemarks(certRequestBean.getRemarks())
					.certReqdate(Objects.nonNull(certRequestBean.getReqdate()) ? LocalDate.parse(certRequestBean.getReqdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
					.certReqnum(certRequestBean.getReqnum())
					.certRetained(Objects.nonNull(certRequestBean.getRetained()) ? certRequestBean.getRetained() : BigInteger.ZERO.doubleValue())
					.certServicetaxamt(Objects.nonNull(certRequestBean.getServicetaxamt()) ? certRequestBean.getServicetaxamt() : BigInteger.ZERO.intValue())
					.certServicetaxperc(Objects.nonNull(certRequestBean.getServicetaxperc()) ? certRequestBean.getServicetaxperc() : BigInteger.ZERO.intValue())
					.certSite(GenericAuditContextHolder.getContext().getSite())
					.certSocid(certRequestBean.getSocid())
					.certSwachhcessamt(Objects.nonNull(certRequestBean.getSwachhcessamt()) ? certRequestBean.getSwachhcessamt() : BigInteger.ZERO.intValue())
					.certSwachhcessperc(Objects.nonNull(certRequestBean.getSwachhcessperc()) ? certRequestBean.getSwachhcessperc() : BigInteger.ZERO.intValue())
					.certTaxlevel(certRequestBean.getTaxlevel())
					.certTdsamount(Objects.nonNull(certRequestBean.getTdsamount()) ? certRequestBean.getTdsamount() : BigInteger.ZERO.doubleValue())
					.certTdssur(Objects.nonNull(certRequestBean.getTdssur()) ? certRequestBean.getTdssur() : BigInteger.ZERO.doubleValue())
					.certToday(LocalDateTime.now())
					.certTotTwoptc(Objects.nonNull(certRequestBean.getTottwoptc()) ? certRequestBean.getTottwoptc() : BigInteger.ZERO.doubleValue())
					.certTotTwtptc(Objects.nonNull(certRequestBean.getTottwtptc()) ? certRequestBean.getTottwtptc() : BigInteger.ZERO.doubleValue())
					.certTrips(Objects.nonNull(certRequestBean.getTrips()) ? certRequestBean.getTrips() : BigInteger.ZERO.intValue())
					.certTAdvadj(Objects.nonNull(certRequestBean.getTadvadj()) ? certRequestBean.getTadvadj() : BigInteger.ZERO.doubleValue())
					.certTAdvos(Objects.nonNull(certRequestBean.getTadvos()) ? certRequestBean.getTadvos() : BigInteger.ZERO.doubleValue())
					.certTAdvpaid(Objects.nonNull(certRequestBean.getTadvpaid()) ? certRequestBean.getTadvpaid() : BigInteger.ZERO.doubleValue())
					.certTDebit(Objects.nonNull(certRequestBean.getTdebit()) ? certRequestBean.getTdebit() : BigInteger.ZERO.doubleValue())
					.certTDebsoc(Objects.nonNull(certRequestBean.getTdebsoc()) ? certRequestBean.getTdebsoc() : BigInteger.ZERO.doubleValue())
					.certTMwctaxamt(Objects.nonNull(certRequestBean.getTmwctaxamt()) ? certRequestBean.getTmwctaxamt() : BigInteger.ZERO.doubleValue())
					.certTPayment(Objects.nonNull(certRequestBean.getTpayment()) ? certRequestBean.getTpayment() : BigInteger.ZERO.doubleValue())
					.certTPerpaid(Objects.nonNull(certRequestBean.getTperpaid()) ? certRequestBean.getTperpaid() : BigInteger.ZERO.doubleValue())
					.certTRetained(Objects.nonNull(certRequestBean.getTretained()) ? certRequestBean.getTretained() : BigInteger.ZERO.doubleValue())
					.certTTdsamt(Objects.nonNull(certRequestBean.getTtdsamt()) ? certRequestBean.getTtdsamt() : BigInteger.ZERO.doubleValue())
					.certTTdssur(Objects.nonNull(certRequestBean.getTtdssur()) ? certRequestBean.getTtdssur() : BigInteger.ZERO.doubleValue())
					.certTTrips(Objects.nonNull(certRequestBean.getTtrips()) ? certRequestBean.getTtrips() : BigInteger.ZERO.doubleValue())
					.certTWriteoffamt(Objects.nonNull(certRequestBean.getTwriteoffamt()) ? certRequestBean.getTwriteoffamt() : BigInteger.ZERO.doubleValue())
					.certUserid(GenericAuditContextHolder.getContext().getUserid())
					.certVatamt(Objects.nonNull(certRequestBean.getVatamt()) ? certRequestBean.getVatamt() : BigInteger.ZERO.intValue())
					.certVatperc(Objects.nonNull(certRequestBean.getVatperc()) ? certRequestBean.getVatperc() : BigInteger.ZERO.intValue())
					.certWing(certRequestBean.getWing())
					.certWorkcode(certRequestBean.getWorkcode())
					.certWorkgroup(certRequestBean.getWorkgroup())
					.certWriteoffamt(Objects.nonNull(certRequestBean.getWriteoffamt()) ? certRequestBean.getWriteoffamt() : BigInteger.ZERO.doubleValue())
					.build();
} ;

	public static BiFunction<Cert, CertRequestBean, Cert> updateCertEntityPojoMapper = (certEntity, certRequestBean) -> {
		certEntity.setCertContract(Objects.nonNull(certRequestBean.getContract()) ? certRequestBean.getContract().trim() : certEntity.getCertContract());
		certEntity.setCertRunser(Objects.nonNull(certRequestBean.getRunser()) ? certRequestBean.getRunser() : certEntity.getCertRunser());
		certEntity.setCertPassedon(Objects.nonNull(certRequestBean.getPassedon()) ? LocalDate.parse(certRequestBean.getPassedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertPassedon());
		certEntity.getCertCK().setCertCertnum(Objects.nonNull(certRequestBean.getCertnum()) ? certRequestBean.getCertnum().trim() : certEntity.getCertCK().getCertCertnum());
		certEntity.setCertTranser(Objects.nonNull(certRequestBean.getTranser()) ? certRequestBean.getTranser().trim() : certEntity.getCertTranser());
		certEntity.setCertCertstatus(Objects.nonNull(certRequestBean.getCertstatus()) ? certRequestBean.getCertstatus().trim() : certEntity.getCertCertstatus());
		certEntity.setCertCerttype(Objects.nonNull(certRequestBean.getCerttype()) ? certRequestBean.getCerttype().trim() : certEntity.getCertCerttype());
		certEntity.setCertCoy(Objects.nonNull(certRequestBean.getCoy()) ? certRequestBean.getCoy().trim() : certEntity.getCertCoy());
		certEntity.setCertAdvadjusted(Objects.nonNull(certRequestBean.getAdvadjusted()) ? certRequestBean.getAdvadjusted() : certEntity.getCertAdvadjusted());
		certEntity.setCertAsstaxfrom(Objects.nonNull(certRequestBean.getAsstaxfrom()) ? LocalDate.parse(certRequestBean.getAsstaxfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertAsstaxfrom());
		certEntity.setCertAsstaxupto(Objects.nonNull(certRequestBean.getAsstaxupto()) ? LocalDate.parse(certRequestBean.getAsstaxupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertAsstaxupto());
		certEntity.setCertBasicamt(Objects.nonNull(certRequestBean.getBasicamt()) ? certRequestBean.getBasicamt() : certEntity.getCertBasicamt());
		certEntity.setCertBillamount(Objects.nonNull(certRequestBean.getBillamount()) ? certRequestBean.getBillamount() : certEntity.getCertBillamount());
		certEntity.setCertBilldate(Objects.nonNull(certRequestBean.getBilldate()) ? LocalDate.parse(certRequestBean.getBilldate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertBilldate());
		certEntity.setCertBillnos(Objects.nonNull(certRequestBean.getBillnos()) ? certRequestBean.getBillnos().trim() : certEntity.getCertBillnos());
		certEntity.setCertBillref(Objects.nonNull(certRequestBean.getBillref()) ? certRequestBean.getBillref().trim() : certEntity.getCertBillref());
		certEntity.setCertBldgcode(Objects.nonNull(certRequestBean.getBldgcode()) ? certRequestBean.getBldgcode().trim() : certEntity.getCertBldgcode());
		certEntity.setCertCertamount(Objects.nonNull(certRequestBean.getCertamount()) ? certRequestBean.getCertamount() : certEntity.getCertCertamount());
		certEntity.setCertCertdate(Objects.nonNull(certRequestBean.getCertdate()) ? LocalDate.parse(certRequestBean.getCertdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertCertdate());
		certEntity.setCertCertrevnum(Objects.nonNull(certRequestBean.getCertrevnum()) ? certRequestBean.getCertrevnum() : certEntity.getCertCertrevnum());
		certEntity.setCertCfcode(Objects.nonNull(certRequestBean.getCfcode()) ? certRequestBean.getCfcode().trim() : certEntity.getCertCfcode());
		certEntity.setCertCfgroup(Objects.nonNull(certRequestBean.getCfgroup()) ? certRequestBean.getCfgroup().trim() : certEntity.getCertCfgroup());
		certEntity.setCertCity(Objects.nonNull(certRequestBean.getCity()) ? certRequestBean.getCity().trim() : certEntity.getCertCity());
		certEntity.setCertClearacyn(Objects.nonNull(certRequestBean.getClearacyn()) ? certRequestBean.getClearacyn().trim() : certEntity.getCertClearacyn());
		certEntity.setCertConamount(Objects.nonNull(certRequestBean.getConamount()) ? certRequestBean.getConamount() : certEntity.getCertConamount());
		certEntity.setCertDebit(Objects.nonNull(certRequestBean.getDebit()) ? certRequestBean.getDebit() : certEntity.getCertDebit());
		certEntity.setCertDebitingparty(Objects.nonNull(certRequestBean.getDebitingparty()) ? certRequestBean.getDebitingparty().trim() : certEntity.getCertDebitingparty());
		certEntity.setCertDebitingreason(Objects.nonNull(certRequestBean.getDebitingreason()) ? certRequestBean.getDebitingreason().trim() : certEntity.getCertDebitingreason());
		certEntity.setCertDebsocyn(Objects.nonNull(certRequestBean.getDebsocyn()) ? certRequestBean.getDebsocyn().trim() : certEntity.getCertDebsocyn());
		certEntity.setCertDescription(Objects.nonNull(certRequestBean.getDescription()) ? certRequestBean.getDescription().trim() : certEntity.getCertDescription());
		certEntity.setCertDomain(Objects.nonNull(certRequestBean.getDomain()) ? certRequestBean.getDomain().trim() : certEntity.getCertDomain());
		certEntity.setCertDurfrom(Objects.nonNull(certRequestBean.getDurfrom()) ? LocalDate.parse(certRequestBean.getDurfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertDurfrom());
		certEntity.setCertDurto(Objects.nonNull(certRequestBean.getDurto()) ? LocalDate.parse(certRequestBean.getDurto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertDurto());
		certEntity.setCertEquipid(Objects.nonNull(certRequestBean.getEquipid()) ? certRequestBean.getEquipid().trim() : certEntity.getCertEquipid());
		certEntity.setCertFlat(Objects.nonNull(certRequestBean.getFlat()) ? certRequestBean.getFlat().trim() : certEntity.getCertFlat());
		certEntity.setCertKrishicessamt(Objects.nonNull(certRequestBean.getKrishicessamt()) ? certRequestBean.getKrishicessamt() : certEntity.getCertKrishicessamt());
		certEntity.setCertKrishicessperc(Objects.nonNull(certRequestBean.getKrishicessperc()) ? certRequestBean.getKrishicessperc() : certEntity.getCertKrishicessperc());
		certEntity.setCertMainmatgroup(Objects.nonNull(certRequestBean.getMainmatgroup()) ? certRequestBean.getMainmatgroup().trim() : certEntity.getCertMainmatgroup());
		certEntity.setCertMainparty(Objects.nonNull(certRequestBean.getMainparty()) ? certRequestBean.getMainparty().trim() : certEntity.getCertMainparty());
		certEntity.setCertMainrecid(Objects.nonNull(certRequestBean.getMainrecid()) ? certRequestBean.getMainrecid().trim() : certEntity.getCertMainrecid());
		certEntity.setCertMatcode(Objects.nonNull(certRequestBean.getMatcode()) ? certRequestBean.getMatcode().trim() : certEntity.getCertMatcode());
		certEntity.setCertMatgroup(Objects.nonNull(certRequestBean.getMatgroup()) ? certRequestBean.getMatgroup().trim() : certEntity.getCertMatgroup());
		certEntity.setCertMisbldg(Objects.nonNull(certRequestBean.getMisbldg()) ? certRequestBean.getMisbldg().trim() : certEntity.getCertMisbldg());
		certEntity.setCertMisproject(Objects.nonNull(certRequestBean.getMisproject()) ? certRequestBean.getMisproject().trim() : certEntity.getCertMisproject());
		certEntity.setCertMwctaxamount(Objects.nonNull(certRequestBean.getMwctaxamount()) ? certRequestBean.getMwctaxamount() : certEntity.getCertMwctaxamount());
		certEntity.setCertOrconamount(Objects.nonNull(certRequestBean.getOrconamount()) ? certRequestBean.getOrconamount() : certEntity.getCertOrconamount());
		certEntity.setCertOrcontract(Objects.nonNull(certRequestBean.getOrcontract()) ? certRequestBean.getOrcontract().trim() : certEntity.getCertOrcontract());
		certEntity.setCertOriginator(Objects.nonNull(certRequestBean.getOriginator()) ? certRequestBean.getOriginator().trim() : certEntity.getCertOriginator());
		certEntity.setCertOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		certEntity.setCertOrpartycode(Objects.nonNull(certRequestBean.getOrpartycode()) ? certRequestBean.getOrpartycode().trim() : certEntity.getCertOrpartycode());
		certEntity.setCertOrpartytype(Objects.nonNull(certRequestBean.getOrpartytype()) ? certRequestBean.getOrpartytype().trim() : certEntity.getCertOrpartytype());
		certEntity.setCertPartycode(Objects.nonNull(certRequestBean.getPartycode()) ? certRequestBean.getPartycode().trim() : certEntity.getCertPartycode());
		certEntity.setCertPartytype(Objects.nonNull(certRequestBean.getPartytype()) ? certRequestBean.getPartytype().trim() : certEntity.getCertPartytype());
		certEntity.setCertPayamount(Objects.nonNull(certRequestBean.getPayamount()) ? certRequestBean.getPayamount() : certEntity.getCertPayamount());
		certEntity.setCertPaydate(Objects.nonNull(certRequestBean.getPaydate()) ? LocalDate.parse(certRequestBean.getPaydate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertPaydate());
		certEntity.setCertPayref(Objects.nonNull(certRequestBean.getPayref()) ? certRequestBean.getPayref().trim() : certEntity.getCertPayref());
		certEntity.setCertPaytender(Objects.nonNull(certRequestBean.getPaytender()) ? certRequestBean.getPaytender().trim() : certEntity.getCertPaytender());
		certEntity.setCertPerdone(Objects.nonNull(certRequestBean.getPerdone()) ? certRequestBean.getPerdone() : certEntity.getCertPerdone());
		certEntity.setCertPrinted(Objects.nonNull(certRequestBean.getPrinted()) ? certRequestBean.getPrinted() : certEntity.getCertPrinted());
		certEntity.setCertPrintedon(Objects.nonNull(certRequestBean.getPrintedon()) ? LocalDate.parse(certRequestBean.getPrintedon(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertPrintedon());
		certEntity.setCertProject(Objects.nonNull(certRequestBean.getProject()) ? certRequestBean.getProject().trim() : certEntity.getCertProject());
		certEntity.setCertProp(Objects.nonNull(certRequestBean.getProp()) ? certRequestBean.getProp().trim() : certEntity.getCertProp());
		certEntity.setCertProperty(Objects.nonNull(certRequestBean.getProperty()) ? certRequestBean.getProperty().trim() : certEntity.getCertProperty());
		certEntity.setCertProvyn(Objects.nonNull(certRequestBean.getProvyn()) ? certRequestBean.getProvyn().trim() : certEntity.getCertProvyn());
		certEntity.setCertPrvAmt(Objects.nonNull(certRequestBean.getPrvamt()) ? certRequestBean.getPrvamt() : certEntity.getCertPrvAmt());
		certEntity.setCertPrvCertnum(Objects.nonNull(certRequestBean.getPrvcertnum()) ? certRequestBean.getPrvcertnum().trim() : certEntity.getCertPrvCertnum());
//		certEntity.setCertPrvDate(Objects.nonNull(certRequestBean.getPrvdate()) ? LocalDate.parse(certRequestBean.getPrvdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertPrvDate());
		certEntity.setCertPrvType(Objects.nonNull(certRequestBean.getPrvtype()) ? certRequestBean.getPrvtype().trim() : certEntity.getCertPrvType());
		certEntity.setCertRemarks(Objects.nonNull(certRequestBean.getRemarks()) ? certRequestBean.getRemarks().trim() : certEntity.getCertRemarks());
		certEntity.setCertReqdate(Objects.nonNull(certRequestBean.getReqdate()) ? LocalDate.parse(certRequestBean.getReqdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : certEntity.getCertReqdate());
		certEntity.setCertReqnum(Objects.nonNull(certRequestBean.getReqnum()) ? certRequestBean.getReqnum().trim() : certEntity.getCertReqnum());
		certEntity.setCertRetained(Objects.nonNull(certRequestBean.getRetained()) ? certRequestBean.getRetained() : certEntity.getCertRetained());
		certEntity.setCertServicetaxamt(Objects.nonNull(certRequestBean.getServicetaxamt()) ? certRequestBean.getServicetaxamt() : certEntity.getCertServicetaxamt());
		certEntity.setCertServicetaxperc(Objects.nonNull(certRequestBean.getServicetaxperc()) ? certRequestBean.getServicetaxperc() : certEntity.getCertServicetaxperc());
		certEntity.setCertSite(GenericAuditContextHolder.getContext().getSite()) ; 
		certEntity.setCertSocid(Objects.nonNull(certRequestBean.getSocid()) ? certRequestBean.getSocid().trim() : certEntity.getCertSocid());
		certEntity.setCertSwachhcessamt(Objects.nonNull(certRequestBean.getSwachhcessamt()) ? certRequestBean.getSwachhcessamt() : certEntity.getCertSwachhcessamt());
		certEntity.setCertSwachhcessperc(Objects.nonNull(certRequestBean.getSwachhcessperc()) ? certRequestBean.getSwachhcessperc() : certEntity.getCertSwachhcessperc());
		certEntity.setCertTaxlevel(Objects.nonNull(certRequestBean.getTaxlevel()) ? certRequestBean.getTaxlevel().trim() : certEntity.getCertTaxlevel());
		certEntity.setCertTdsamount(Objects.nonNull(certRequestBean.getTdsamount()) ? certRequestBean.getTdsamount() : certEntity.getCertTdsamount());
		certEntity.setCertTdssur(Objects.nonNull(certRequestBean.getTdssur()) ? certRequestBean.getTdssur() : certEntity.getCertTdssur());
		certEntity.setCertToday(LocalDateTime.now()) ; 
		certEntity.setCertTotTwoptc(Objects.nonNull(certRequestBean.getTottwoptc()) ? certRequestBean.getTottwoptc() : certEntity.getCertTotTwoptc());
		certEntity.setCertTotTwtptc(Objects.nonNull(certRequestBean.getTottwtptc()) ? certRequestBean.getTottwtptc() : certEntity.getCertTotTwtptc());
		certEntity.setCertTrips(Objects.nonNull(certRequestBean.getTrips()) ? certRequestBean.getTrips() : certEntity.getCertTrips());
		certEntity.setCertTAdvadj(Objects.nonNull(certRequestBean.getTadvadj()) ? certRequestBean.getTadvadj() : certEntity.getCertTAdvadj());
		certEntity.setCertTAdvos(Objects.nonNull(certRequestBean.getTadvos()) ? certRequestBean.getTadvos() : certEntity.getCertTAdvos());
		certEntity.setCertTAdvpaid(Objects.nonNull(certRequestBean.getTadvpaid()) ? certRequestBean.getTadvpaid() : certEntity.getCertTAdvpaid());
		certEntity.setCertTDebit(Objects.nonNull(certRequestBean.getTdebit()) ? certRequestBean.getTdebit() : certEntity.getCertTDebit());
		certEntity.setCertTDebsoc(Objects.nonNull(certRequestBean.getTdebsoc()) ? certRequestBean.getTdebsoc() : certEntity.getCertTDebsoc());
		certEntity.setCertTMwctaxamt(Objects.nonNull(certRequestBean.getTmwctaxamt()) ? certRequestBean.getTmwctaxamt() : certEntity.getCertTMwctaxamt());
		certEntity.setCertTPayment(Objects.nonNull(certRequestBean.getTpayment()) ? certRequestBean.getTpayment() : certEntity.getCertTPayment());
		certEntity.setCertTPerpaid(Objects.nonNull(certRequestBean.getTperpaid()) ? certRequestBean.getTperpaid() : certEntity.getCertTPerpaid());
		certEntity.setCertTRetained(Objects.nonNull(certRequestBean.getTretained()) ? certRequestBean.getTretained() : certEntity.getCertTRetained());
		certEntity.setCertTTdsamt(Objects.nonNull(certRequestBean.getTtdsamt()) ? certRequestBean.getTtdsamt() : certEntity.getCertTTdsamt());
		certEntity.setCertTTdssur(Objects.nonNull(certRequestBean.getTtdssur()) ? certRequestBean.getTtdssur() : certEntity.getCertTTdssur());
		certEntity.setCertTTrips(Objects.nonNull(certRequestBean.getTtrips()) ? certRequestBean.getTtrips() : certEntity.getCertTTrips());
		certEntity.setCertTWriteoffamt(Objects.nonNull(certRequestBean.getTwriteoffamt()) ? certRequestBean.getTwriteoffamt() : certEntity.getCertTWriteoffamt());
		certEntity.setCertUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		certEntity.setCertVatamt(Objects.nonNull(certRequestBean.getVatamt()) ? certRequestBean.getVatamt() : certEntity.getCertVatamt());
		certEntity.setCertVatperc(Objects.nonNull(certRequestBean.getVatperc()) ? certRequestBean.getVatperc() : certEntity.getCertVatperc());
		certEntity.setCertWing(Objects.nonNull(certRequestBean.getWing()) ? certRequestBean.getWing().trim() : certEntity.getCertWing());
		certEntity.setCertWorkcode(Objects.nonNull(certRequestBean.getWorkcode()) ? certRequestBean.getWorkcode().trim() : certEntity.getCertWorkcode());
		certEntity.setCertWorkgroup(Objects.nonNull(certRequestBean.getWorkgroup()) ? certRequestBean.getWorkgroup().trim() : certEntity.getCertWorkgroup());
		certEntity.setCertWriteoffamt(Objects.nonNull(certRequestBean.getWriteoffamt()) ? certRequestBean.getWriteoffamt() : certEntity.getCertWriteoffamt());
		return certEntity;
	};

}
