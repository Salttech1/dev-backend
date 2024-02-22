package kraheja.commons.mappers.entityentity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.Actrandx;
import kraheja.commons.entity.ActrandxCK;

public interface ActrandxEntityEntityMapper {
//	@SuppressWarnings("unchecked")
//	public static Function<Object[], List<ActrandxResponseBean>> fetchActrandxEntityPojoMapper = objectArray -> {
//		ActrandxResponseBeanBuilder actrandxBeanBuilder = ActrandxResponseBean.builder();
//		List<Actrandx> actrandxEntityList = (List<Actrandx>) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()]
//				: null);
//		return actrandxEntityList.stream().map(actrandxEntity -> {
//			actrandxBeanBuilder.revision(actrandxEntity.getActrandxCK().getActdRevision())
//					.today(actrandxEntity.getActrandxCK().getActdToday())
//					.transer(actrandxEntity.getActrandxCK().getActdTranser())
//					.bunum(actrandxEntity.getActrandxCK().getActdBunum())
//					.bldgcode(actrandxEntity.getActdBldgcode())
//					.cfcode(actrandxEntity.getActdCfcode())
//					.cfgroup(actrandxEntity.getActdCfgroup())
//					.contract(actrandxEntity.getActdContract())
//					.docdate(Objects.nonNull(actrandxEntity.getActdDocdate()) ? actrandxEntity.getActdDocdate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.docnum(actrandxEntity.getActdDocnum())
//					.doctype(actrandxEntity.getActdDoctype())
//					.flat(actrandxEntity.getActdFlat())
//					.ledgcode(actrandxEntity.getActdLedgcode())
//					.matcode(actrandxEntity.getActdMatcode())
//					.matgroup(actrandxEntity.getActdMatgroup())
//					.mincode(actrandxEntity.getActdMincode())
//					.mintype(actrandxEntity.getActdMintype())
//					.narrative(actrandxEntity.getActdNarrative())
//					.partytype(actrandxEntity.getActdPartytype())
//					.period(actrandxEntity.getActdPeriod())
//					.project(actrandxEntity.getActdProject())
//					.reffrom(Objects.nonNull(actrandxEntity.getActdReffrom()) ? actrandxEntity.getActdReffrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.refupto(Objects.nonNull(actrandxEntity.getActdRefupto()) ? actrandxEntity.getActdRefupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.site(actrandxEntity.getActdSite())
//					.tranamt(actrandxEntity.getActdTranamt())
//					.trantype(actrandxEntity.getActdTrantype())
//					.userid(actrandxEntity.getActdUserid())
//					.voudate(Objects.nonNull(actrandxEntity.getActdVoudate()) ? actrandxEntity.getActdVoudate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.vounum(actrandxEntity.getActdVounum())
//					.wing(actrandxEntity.getActdWing())
//					.workcode(actrandxEntity.getActdWorkcode())
//					.workgroup(actrandxEntity.getActdWorkgroup())
//					.xacmajor(actrandxEntity.getActdXacmajor())
//					.xacminor(actrandxEntity.getActdXacminor())
//					.xmintype(actrandxEntity.getActdXmintype())
//					.xpartycode(actrandxEntity.getActdXpartycode())
//					.xpartytype(actrandxEntity.getActdXpartytype())
//					.xproject(actrandxEntity.getActdXproject())
//					.xref_bunum(actrandxEntity.getActdXref_Bunum())
//					.xref_trandate(Objects.nonNull(actrandxEntity.getActdXref_Trandate()) ? actrandxEntity.getActdXref_Trandate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//					.xref_transer(actrandxEntity.getActdXref_Transer())
//;
//			return actrandxBeanBuilder.build();
//		}).collect(Collectors.toList());
//	};

	public static BiFunction<List<Actrand>, String, List<Actrandx>> addActrandxEntityPojoMapper = (actrandList, revision) -> {

		return actrandList.stream().map(actrand -> {
			return Actrandx.builder()
					.actrandxCK(ActrandxCK.builder()
							.actdRevision(revision)
							.actdToday(LocalDateTime.now())
							.actdTranser(actrand.getActrandCK().getActdTranser())
							.actdBunum(actrand.getActrandCK().getActdBunum())
							.build())
							.actdBldgcode(actrand.getActdBldgcode())
							.actdCfcode(actrand.getActdCfcode())
							.actdCfgroup(actrand.getActdCfgroup())
							.actdContract(actrand.getActdContract())
							.actdDocdate(actrand.getActdDocdate())
							.actdDocnum(actrand.getActdDocnum())
							.actdDoctype(actrand.getActdDoctype())
							.actdFlat(actrand.getActdFlat())
							.actdLedgcode(actrand.getActdLedgcode())
							.actdMatcode(actrand.getActdMatcode())
							.actdMatgroup(actrand.getActdMatgroup())
							.actdMincode(actrand.getActdMincode())
							.actdMintype(actrand.getActdMintype())
							.actdNarrative(actrand.getActdNarrative())
							.actdPartytype(actrand.getActdPartytype())
							.actdPeriod(actrand.getActdPeriod())
							.actdProject(actrand.getActdProject())
							.actdReffrom(actrand.getActdReffrom())
							.actdRefupto(actrand.getActdRefupto())
							.actdSite(actrand.getActdSite())
							.actdTranamt(actrand.getActdTranamt())
							.actdTrantype(actrand.getActdTrantype())
							.actdUserid(actrand.getActdUserid())
							.actdVoudate(actrand.getActdVoudate())
							.actdVounum(actrand.getActdVounum())
							.actdWing(actrand.getActdWing())
							.actdWorkcode(actrand.getActdWorkcode())
							.actdWorkgroup(actrand.getActdWorkgroup())
							.actdXacmajor(actrand.getActdXacmajor())
							.actdXacminor(actrand.getActdXacminor())
							.actdXmintype(actrand.getActdXmintype())
							.actdXpartycode(actrand.getActdXpartycode())
							.actdXpartytype(actrand.getActdXpartytype())
							.actdXproject(actrand.getActdXproject())
							.actdXrefBunum(actrand.getActdXrefBunum())
							.actdXrefTrandate(actrand.getActdXrefTrandate())
							.actdXrefTranser(actrand.getActdXrefTranser())
							.build();
		}).collect(Collectors.toList());

};

//	public static BiFunction<Actrandx, ActrandxRequestBean, Actrandx> updateActrandxEntityPojoMapper = (actrandxEntity, actrandxRequestBean) -> {
//		actrandxEntity.getActrandxCK().setActdRevision(Objects.nonNull(actrandxRequestBean.getRevision()) ? actrandxRequestBean.getRevision().trim() : actrandxEntity.getActrandxCK().getActdRevision());
//		actrandxEntity.getActrandxCK().setActdToday		actrandxEntity.getActrandxCK().setActdTranser(Objects.nonNull(actrandxRequestBean.getTranser()) ? actrandxRequestBean.getTranser().trim() : actrandxEntity.getActrandxCK().getActdTranser());
//		actrandxEntity.getActrandxCK().setActdBunum(Objects.nonNull(actrandxRequestBean.getBunum()) ? actrandxRequestBean.getBunum() : actrandxEntity.getActrandxCK().getActdBunum());
//		actrandxEntity.setActdBldgcode(Objects.nonNull(actrandxRequestBean.getBldgcode()) ? actrandxRequestBean.getBldgcode().trim() : actrandxEntity.getActdBldgcode());
//		actrandxEntity.setActdCfcode(Objects.nonNull(actrandxRequestBean.getCfcode()) ? actrandxRequestBean.getCfcode().trim() : actrandxEntity.getActdCfcode());
//		actrandxEntity.setActdCfgroup(Objects.nonNull(actrandxRequestBean.getCfgroup()) ? actrandxRequestBean.getCfgroup().trim() : actrandxEntity.getActdCfgroup());
//		actrandxEntity.setActdContract(Objects.nonNull(actrandxRequestBean.getContract()) ? actrandxRequestBean.getContract().trim() : actrandxEntity.getActdContract());
//		actrandxEntity.setActdDocdate(Objects.nonNull(actrandxRequestBean.getDocdate()) ? LocalDate.parse(actrandxRequestBean.getDocdate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actrandxEntity.getActdDocdate());
//		actrandxEntity.setActdDocnum(Objects.nonNull(actrandxRequestBean.getDocnum()) ? actrandxRequestBean.getDocnum().trim() : actrandxEntity.getActdDocnum());
//		actrandxEntity.setActdDoctype(Objects.nonNull(actrandxRequestBean.getDoctype()) ? actrandxRequestBean.getDoctype().trim() : actrandxEntity.getActdDoctype());
//		actrandxEntity.setActdFlat(Objects.nonNull(actrandxRequestBean.getFlat()) ? actrandxRequestBean.getFlat().trim() : actrandxEntity.getActdFlat());
//		actrandxEntity.setActdLedgcode(Objects.nonNull(actrandxRequestBean.getLedgcode()) ? actrandxRequestBean.getLedgcode().trim() : actrandxEntity.getActdLedgcode());
//		actrandxEntity.setActdMatcode(Objects.nonNull(actrandxRequestBean.getMatcode()) ? actrandxRequestBean.getMatcode().trim() : actrandxEntity.getActdMatcode());
//		actrandxEntity.setActdMatgroup(Objects.nonNull(actrandxRequestBean.getMatgroup()) ? actrandxRequestBean.getMatgroup().trim() : actrandxEntity.getActdMatgroup());
//		actrandxEntity.setActdMincode(Objects.nonNull(actrandxRequestBean.getMincode()) ? actrandxRequestBean.getMincode().trim() : actrandxEntity.getActdMincode());
//		actrandxEntity.setActdMintype(Objects.nonNull(actrandxRequestBean.getMintype()) ? actrandxRequestBean.getMintype().trim() : actrandxEntity.getActdMintype());
//		actrandxEntity.setActdNarrative(Objects.nonNull(actrandxRequestBean.getNarrative()) ? actrandxRequestBean.getNarrative().trim() : actrandxEntity.getActdNarrative());
//		actrandxEntity.setActdPartytype(Objects.nonNull(actrandxRequestBean.getPartytype()) ? actrandxRequestBean.getPartytype().trim() : actrandxEntity.getActdPartytype());
//		actrandxEntity.setActdPeriod(Objects.nonNull(actrandxRequestBean.getPeriod()) ? actrandxRequestBean.getPeriod().trim() : actrandxEntity.getActdPeriod());
//		actrandxEntity.setActdProject(Objects.nonNull(actrandxRequestBean.getProject()) ? actrandxRequestBean.getProject().trim() : actrandxEntity.getActdProject());
//		actrandxEntity.setActdReffrom(Objects.nonNull(actrandxRequestBean.getReffrom()) ? LocalDate.parse(actrandxRequestBean.getReffrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actrandxEntity.getActdReffrom());
//		actrandxEntity.setActdRefupto(Objects.nonNull(actrandxRequestBean.getRefupto()) ? LocalDate.parse(actrandxRequestBean.getRefupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actrandxEntity.getActdRefupto());
//		actrandxEntity.setActdSite(Objects.nonNull(actrandxRequestBean.getSite()) ? actrandxRequestBean.getSite().trim() : actrandxEntity.getActdSite());
//		actrandxEntity.setActdTranamt(Objects.nonNull(actrandxRequestBean.getTranamt()) ? actrandxRequestBean.getTranamt() : actrandxEntity.getActdTranamt());
//		actrandxEntity.setActdTrantype(Objects.nonNull(actrandxRequestBean.getTrantype()) ? actrandxRequestBean.getTrantype().trim() : actrandxEntity.getActdTrantype());
//		actrandxEntity.setActdUserid(Objects.nonNull(actrandxRequestBean.getUserid()) ? actrandxRequestBean.getUserid().trim() : actrandxEntity.getActdUserid());
//		actrandxEntity.setActdVoudate(Objects.nonNull(actrandxRequestBean.getVoudate()) ? LocalDate.parse(actrandxRequestBean.getVoudate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actrandxEntity.getActdVoudate());
//		actrandxEntity.setActdVounum(Objects.nonNull(actrandxRequestBean.getVounum()) ? actrandxRequestBean.getVounum().trim() : actrandxEntity.getActdVounum());
//		actrandxEntity.setActdWing(Objects.nonNull(actrandxRequestBean.getWing()) ? actrandxRequestBean.getWing().trim() : actrandxEntity.getActdWing());
//		actrandxEntity.setActdWorkcode(Objects.nonNull(actrandxRequestBean.getWorkcode()) ? actrandxRequestBean.getWorkcode().trim() : actrandxEntity.getActdWorkcode());
//		actrandxEntity.setActdWorkgroup(Objects.nonNull(actrandxRequestBean.getWorkgroup()) ? actrandxRequestBean.getWorkgroup().trim() : actrandxEntity.getActdWorkgroup());
//		actrandxEntity.setActdXacmajor(Objects.nonNull(actrandxRequestBean.getXacmajor()) ? actrandxRequestBean.getXacmajor().trim() : actrandxEntity.getActdXacmajor());
//		actrandxEntity.setActdXacminor(Objects.nonNull(actrandxRequestBean.getXacminor()) ? actrandxRequestBean.getXacminor().trim() : actrandxEntity.getActdXacminor());
//		actrandxEntity.setActdXmintype(Objects.nonNull(actrandxRequestBean.getXmintype()) ? actrandxRequestBean.getXmintype().trim() : actrandxEntity.getActdXmintype());
//		actrandxEntity.setActdXpartycode(Objects.nonNull(actrandxRequestBean.getXpartycode()) ? actrandxRequestBean.getXpartycode().trim() : actrandxEntity.getActdXpartycode());
//		actrandxEntity.setActdXpartytype(Objects.nonNull(actrandxRequestBean.getXpartytype()) ? actrandxRequestBean.getXpartytype().trim() : actrandxEntity.getActdXpartytype());
//		actrandxEntity.setActdXproject(Objects.nonNull(actrandxRequestBean.getXproject()) ? actrandxRequestBean.getXproject().trim() : actrandxEntity.getActdXproject());
//		actrandxEntity.setActdXref_Bunum(Objects.nonNull(actrandxRequestBean.getXref_Bunum()) ? actrandxRequestBean.getXref_Bunum() : actrandxEntity.getActdXref_Bunum());
//		actrandxEntity.setActdXref_Trandate(Objects.nonNull(actrandxRequestBean.getXref_Trandate()) ? LocalDate.parse(actrandxRequestBean.getXref_Trandate(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : actrandxEntity.getActdXref_Trandate());
//		actrandxEntity.setActdXref_Transer(Objects.nonNull(actrandxRequestBean.getXref_Transer()) ? actrandxRequestBean.getXref_Transer().trim() : actrandxEntity.getActdXref_Transer());
//
//
//		return actrandxEntity;
//	};
}