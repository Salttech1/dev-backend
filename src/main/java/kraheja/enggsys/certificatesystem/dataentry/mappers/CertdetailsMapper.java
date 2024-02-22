package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.enggsys.bean.request.CertdetailsRequestBean;
import kraheja.enggsys.bean.response.CertdetailsResponseBean;
import kraheja.enggsys.entity.Certdetails;
import kraheja.enggsys.entity.Certdetails.CertdetailsCK;

public interface CertdetailsMapper {
	@SuppressWarnings("unchecked")
	public static Function	<List<Certdetails>, List<CertdetailsResponseBean>> fetchCertdetailsEntityPojoMapper = certdetailsEntityList -> {
		return certdetailsEntityList.stream().map(certdetailsEntity -> {
			return CertdetailsResponseBean.builder()
					.certnum(certdetailsEntity.getCertdetailsCK().getCrtdCertnum())
					.contract(certdetailsEntity.getCertdetailsCK().getCrtdContract())
					.contbillno(certdetailsEntity.getCertdetailsCK().getCrtdContbillno())
					.advadjusted(certdetailsEntity.getCrtdAdvadjusted())
					.billamount(certdetailsEntity.getCrtdBillamount())
					.origsite(certdetailsEntity.getCrtdOrigsite())
					.relretamt(certdetailsEntity.getCrtdRelretamt())
					.site(certdetailsEntity.getCrtdSite())
					.tdsamt(certdetailsEntity.getCrtdTdsamt())
					.today(certdetailsEntity.getCrtdToday())
					.userid(certdetailsEntity.getCrtdUserid())
					.build(); 
		}).collect(Collectors.toList());
	};

	public static Function<Object[], List <Certdetails>> addCertdetailsPojoEntityMapper = (objectArray) -> { 
		List<CertdetailsRequestBean> certdetailsRequestBeanList = (List<CertdetailsRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String certnum = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		
		return certdetailsRequestBeanList.stream().map(certdetailsRequestBean -> {
			return Certdetails.builder().certdetailsCK(CertdetailsCK.builder()
					.crtdCertnum(certnum)
					.crtdContract(certdetailsRequestBean.getContract())
					.crtdContbillno(certdetailsRequestBean.getContbillno())
					.build())
					.crtdAdvadjusted(Objects.nonNull(certdetailsRequestBean.getAdvadjusted()) ? certdetailsRequestBean.getAdvadjusted() : BigInteger.ZERO.doubleValue())
					.crtdBillamount(Objects.nonNull(certdetailsRequestBean.getBillamount()) ? certdetailsRequestBean.getBillamount() : BigInteger.ZERO.doubleValue())
					.crtdOrigsite(GenericAuditContextHolder.getContext().getSite())
					.crtdRelretamt(Objects.nonNull(certdetailsRequestBean.getRelretamt()) ? certdetailsRequestBean.getRelretamt() : BigInteger.ZERO.doubleValue())
					.crtdSite(GenericAuditContextHolder.getContext().getSite())
					.crtdTdsamt(Objects.nonNull(certdetailsRequestBean.getTdsamt()) ? certdetailsRequestBean.getTdsamt() : BigInteger.ZERO.doubleValue())
					.crtdToday(LocalDateTime.now())
					.crtdUserid(GenericAuditContextHolder.getContext().getUserid())
					.build();
		}).collect(Collectors.toList());
	} ;

	public static BiFunction<Certdetails, CertdetailsRequestBean, Certdetails> updateCertdetailsEntityPojoMapper = (certdetailsEntity, certdetailsRequestBean) -> {
		certdetailsEntity.getCertdetailsCK().setCrtdCertnum(Objects.nonNull(certdetailsRequestBean.getCertnum()) ? certdetailsRequestBean.getCertnum().trim() : certdetailsEntity.getCertdetailsCK().getCrtdCertnum());
		certdetailsEntity.getCertdetailsCK().setCrtdContract(Objects.nonNull(certdetailsRequestBean.getContract()) ? certdetailsRequestBean.getContract().trim() : certdetailsEntity.getCertdetailsCK().getCrtdContract());
		certdetailsEntity.getCertdetailsCK().setCrtdContbillno(Objects.nonNull(certdetailsRequestBean.getContbillno()) ? certdetailsRequestBean.getContbillno().trim() : certdetailsEntity.getCertdetailsCK().getCrtdContbillno());
		certdetailsEntity.setCrtdAdvadjusted(Objects.nonNull(certdetailsRequestBean.getAdvadjusted()) ? certdetailsRequestBean.getAdvadjusted() : certdetailsEntity.getCrtdAdvadjusted());
		certdetailsEntity.setCrtdBillamount(Objects.nonNull(certdetailsRequestBean.getBillamount()) ? certdetailsRequestBean.getBillamount() : certdetailsEntity.getCrtdBillamount());
		certdetailsEntity.setCrtdOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		certdetailsEntity.setCrtdRelretamt(Objects.nonNull(certdetailsRequestBean.getRelretamt()) ? certdetailsRequestBean.getRelretamt() : certdetailsEntity.getCrtdRelretamt());
		certdetailsEntity.setCrtdSite(GenericAuditContextHolder.getContext().getSite()) ; 
		certdetailsEntity.setCrtdTdsamt(Objects.nonNull(certdetailsRequestBean.getTdsamt()) ? certdetailsRequestBean.getTdsamt() : certdetailsEntity.getCrtdTdsamt());
		certdetailsEntity.setCrtdToday(LocalDateTime.now()) ; 
		certdetailsEntity.setCrtdUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		return certdetailsEntity;
	};

}
