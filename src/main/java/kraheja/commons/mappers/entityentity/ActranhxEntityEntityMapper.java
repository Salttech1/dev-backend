package kraheja.commons.mappers.entityentity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Actranhx;
import kraheja.commons.entity.ActranhxCK;
import kraheja.commons.utils.CommonConstraints;

public interface ActranhxEntityEntityMapper {
//	@SuppressWarnings("unchecked")
//	public static Function<Object[], List<ActranhxResponseBean>> fetchActranhxEntityPojoMapper = objectArray -> {
//		ActranhxResponseBeanBuilder actranhxBeanBuilder = ActranhxResponseBean.builder();
//		List<Actranhx> actranhxEntityList = (List<Actranhx>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()]
//				: null);
//		return actranhxEntityList.stream().map(actranhxEntity -> {
//			actranhxBeanBuilder					.bankcode(actranhxEntity.getActhBankcode())
//					.crepno(actranhxEntity.getActhCrepno())
//					.ledgcode(actranhxEntity.getActhLedgcode())
//					.narrative(actranhxEntity.getActhNarrative())
//					.nrepno(actranhxEntity.getActhNrepno())
//					.partycode(actranhxEntity.getActhPartycode())
//					.partytype(actranhxEntity.getActhPartytype())
//					.postedyn(actranhxEntity.getActhPostedyn())
//					.site(actranhxEntity.getActhSite())
//					.tranamt(actranhxEntity.getActhTranamt())
//					.userid(actranhxEntity.getActhUserid())
//					.voudate(Objects.nonNull(actranhxEntity.getActhVoudate()) ? actranhxEntity.getActhVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//;
//			return actranhxBeanBuilder.build();
//		}).collect(Collectors.toList());
//	};

	public static Function<Object[], Actranhx> addActranhxEntityPojoMapper = objectArray -> {
		
		Actranh actranh =(Actranh) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()]) ? objectArray[BigInteger.ZERO.intValue()] : null);
		String revision = (String) (Objects.nonNull(objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
		Integer nrep = (Integer) (Objects.nonNull(objectArray[CommonConstraints.INSTANCE.TWO]) ? objectArray[CommonConstraints.INSTANCE.TWO] : null);

		return Actranhx.builder()
			.actranhxCK(ActranhxCK.builder().acthRevision(revision).acthToday(LocalDateTime.now()).acthTranser(actranh.getActranhCK().getActhTranser()).build())
					.acthBankcode(actranh.getActhBankcode())
					.acthCrepno(actranh.getActhCrepno())
					.acthLedgcode(actranh.getActhLedgcode())
					.acthNarrative(actranh.getActhNarrative())
					.acthNrepno(nrep)
					.acthPartycode(actranh.getActhPartycode())
					.acthPartytype(actranh.getActhPartytype())
					.acthPostedyn(actranh.getActhPostedyn())
					.acthProprietor(actranh.getActhProprietor())
					.acthSite(actranh.getActhSite())
					.acthTranamt(actranh.getActhTranamt())
					.acthTrandate(Objects.nonNull(actranh.getActhVoudate()) ? actranh.getActhTrandate().toLocalDate() : null)
					.acthTrantype(actranh.getActhTrantype())
					.acthUserid(actranh.getActhUserid())
					.acthVounum(actranh.getActhVounum())
					.acthVoudate(Objects.nonNull(actranh.getActhVoudate()) ? actranh.getActhVoudate() : null)
					.build();
	
};

//	public static BiFunction<Actranhx, ActranhxRequestBean, Actranhx> updateActranhxEntityPojoMapper = (actranhxEntity, actranhxRequestBean) -> {
//		actranhxEntity.setActhBankcode(Objects.nonNull(actranhxRequestBean.getBankcode()) ? actranhxRequestBean.getBankcode().trim() : actranhxEntity.getActhBankcode());
//		actranhxEntity.setActhCrepno(Objects.nonNull(actranhxRequestBean.getCrepno()) ? actranhxRequestBean.getCrepno() : actranhxEntity.getActhCrepno());
//		actranhxEntity.setActhLedgcode(Objects.nonNull(actranhxRequestBean.getLedgcode()) ? actranhxRequestBean.getLedgcode().trim() : actranhxEntity.getActhLedgcode());
//		actranhxEntity.setActhNarrative(Objects.nonNull(actranhxRequestBean.getNarrative()) ? actranhxRequestBean.getNarrative().trim() : actranhxEntity.getActhNarrative());
//		actranhxEntity.setActhNrepno(Objects.nonNull(actranhxRequestBean.getNrepno()) ? actranhxRequestBean.getNrepno() : actranhxEntity.getActhNrepno());
//		actranhxEntity.setActhPartycode(Objects.nonNull(actranhxRequestBean.getPartycode()) ? actranhxRequestBean.getPartycode().trim() : actranhxEntity.getActhPartycode());
//		actranhxEntity.setActhPartytype(Objects.nonNull(actranhxRequestBean.getPartytype()) ? actranhxRequestBean.getPartytype().trim() : actranhxEntity.getActhPartytype());
//		actranhxEntity.setActhPostedyn(Objects.nonNull(actranhxRequestBean.getPostedyn()) ? actranhxRequestBean.getPostedyn().trim() : actranhxEntity.getActhPostedyn());
//		actranhxEntity.setActhSite(Objects.nonNull(actranhxRequestBean.getSite()) ? actranhxRequestBean.getSite().trim() : actranhxEntity.getActhSite());
//		actranhxEntity.setActhTranamt(Objects.nonNull(actranhxRequestBean.getTranamt()) ? actranhxRequestBean.getTranamt() : actranhxEntity.getActhTranamt());
//		actranhxEntity.setActhUserid(Objects.nonNull(actranhxRequestBean.getUserid()) ? actranhxRequestBean.getUserid().trim() : actranhxEntity.getActhUserid());
//		actranhxEntity.setActhVoudate(Objects.nonNull(actranhxRequestBean.getVoudate()) ? LocalDate.parse(actranhxRequestBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actranhxEntity.getActhVoudate());
//
//
//		return actranhxEntity;
//	};

}