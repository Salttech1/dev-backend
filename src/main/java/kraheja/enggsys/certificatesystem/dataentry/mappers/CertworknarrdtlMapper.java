package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.enggsys.bean.request.CertworknarrdtlRequestBean;
import kraheja.enggsys.bean.response.CertworknarrdtlResponseBean;
import kraheja.enggsys.bean.response.CertworknarrdtlResponseBean.CertworknarrdtlResponseBeanBuilder;
import kraheja.enggsys.entity.Certworknarrdtl;
import kraheja.enggsys.entity.Certworknarrdtl.CertworknarrdtlCK;

public interface CertworknarrdtlMapper {
	@SuppressWarnings("unchecked")
	public static Function<Object[], 	List<CertworknarrdtlResponseBean>> fetchCertworknarrdtlEntityPojoMapper = objectArray -> {
		List<Certworknarrdtl> certworknarrdtlEntityList = (List<Certworknarrdtl>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		return certworknarrdtlEntityList.stream().map(certworknarrdtlEntity -> { 
			return CertworknarrdtlResponseBean.builder().certnum(certworknarrdtlEntity.getCertworknarrdtlCK().getCwndCertnum())
					.srno(certworknarrdtlEntity.getCertworknarrdtlCK().getCwndSrno())
					.amount(certworknarrdtlEntity.getCwndAmount())
					.itemdesc(certworknarrdtlEntity.getCwndItemdesc())
					.quantity(certworknarrdtlEntity.getCwndQuantity())
					.site(certworknarrdtlEntity.getCwndSite())
					.today(certworknarrdtlEntity.getCwndToday())
					.userid(certworknarrdtlEntity.getCwndUserid())
					.workcode(certworknarrdtlEntity.getCwndWorkcode())
					.build();
		}
				).collect(Collectors.toList());
	};



	public static Function<Object[], List<Certworknarrdtl>> addCertworknarrdtlPojoEntityMapper = objectArray -> {
		List<CertworknarrdtlRequestBean> certworknarrdtlRequestBeanList = (List<CertworknarrdtlRequestBean>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String certnum = (String) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		Integer srnoCounter = 1;
		List<Certworknarrdtl> certworknarrdtlList = new ArrayList();
		
		for(CertworknarrdtlRequestBean certworknarrdtlRequestBean : certworknarrdtlRequestBeanList) {
			certworknarrdtlList.add(Certworknarrdtl.builder().certworknarrdtlCK(CertworknarrdtlCK.builder()
					.cwndCertnum(certnum)
					.cwndSrno(srnoCounter).build())
					.cwndAmount(Objects.nonNull(certworknarrdtlRequestBean.getAmount()) ? certworknarrdtlRequestBean.getAmount() : BigInteger.ZERO.intValue())
					.cwndItemdesc(certworknarrdtlRequestBean.getItemdesc())
					.cwndQuantity(Objects.nonNull(certworknarrdtlRequestBean.getQuantity()) ? certworknarrdtlRequestBean.getQuantity() : BigInteger.ZERO.doubleValue())
					.cwndSite(GenericAuditContextHolder.getContext().getSite())
					.cwndToday(LocalDateTime.now())
					.cwndUserid(GenericAuditContextHolder.getContext().getUserid())
					.cwndWorkcode(certworknarrdtlRequestBean.getWorkcode())
					.build());
			srnoCounter++;
		}
		return certworknarrdtlList;
	} ;

	public static BiFunction<Certworknarrdtl, CertworknarrdtlRequestBean, Certworknarrdtl> updateCertworknarrdtlEntityPojoMapper = (certworknarrdtlEntity, certworknarrdtlRequestBean) -> {
		certworknarrdtlEntity.getCertworknarrdtlCK().setCwndCertnum(Objects.nonNull(certworknarrdtlRequestBean.getCertnum()) ? certworknarrdtlRequestBean.getCertnum().trim() : certworknarrdtlEntity.getCertworknarrdtlCK().getCwndCertnum());
		certworknarrdtlEntity.getCertworknarrdtlCK().setCwndSrno(Objects.nonNull(certworknarrdtlRequestBean.getSrno()) ? certworknarrdtlRequestBean.getSrno() : certworknarrdtlEntity.getCertworknarrdtlCK().getCwndSrno());
		certworknarrdtlEntity.setCwndAmount(Objects.nonNull(certworknarrdtlRequestBean.getAmount()) ? certworknarrdtlRequestBean.getAmount() : certworknarrdtlEntity.getCwndAmount());
		certworknarrdtlEntity.setCwndItemdesc(Objects.nonNull(certworknarrdtlRequestBean.getItemdesc()) ? certworknarrdtlRequestBean.getItemdesc().trim() : certworknarrdtlEntity.getCwndItemdesc());
		certworknarrdtlEntity.setCwndQuantity(Objects.nonNull(certworknarrdtlRequestBean.getQuantity()) ? certworknarrdtlRequestBean.getQuantity() : certworknarrdtlEntity.getCwndQuantity());
		certworknarrdtlEntity.setCwndSite(GenericAuditContextHolder.getContext().getSite()) ; 
		certworknarrdtlEntity.setCwndToday(LocalDateTime.now()) ; 
		certworknarrdtlEntity.setCwndUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		certworknarrdtlEntity.setCwndWorkcode(Objects.nonNull(certworknarrdtlRequestBean.getWorkcode()) ? certworknarrdtlRequestBean.getWorkcode().trim() : certworknarrdtlEntity.getCwndWorkcode());
		return certworknarrdtlEntity;
	};

}
