package kraheja.enggsys.certificatesystem.dataentry.mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.BiFunction;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.enggsys.bean.TdsexemptBean;
import kraheja.enggsys.entity.Tdsexempt;

public interface TdsexemptMapper {

//	public static Function<Object[], 	TdsexemptResponseBean> fetchTdsexemptEntityPojoMapper = objectArray -> {
//		Tdsexempt tdsexemptEntity = (Tdsexempt) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()] : null);
//		TdsexemptResponseBeanBuilder tdsexemptResponseBean = TdsexemptResponseBean.builder();
//		tdsexemptResponseBean
//		.contract(tdsexemptEntity.getTdsexemptCK().getTdsxContract())
//		.tdsfrom(Objects.nonNull(tdsexemptEntity.gettdsexemptCK().getTdsxTdsfrom()) ? tdsexemptEntity.getTdsexemptCK().getTdsxTdsfrom().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//		.exmpref(tdsexemptEntity.getTdsxExmpref())
//		.origsite(tdsexemptEntity.getTdsxOrigsite())
//		.site(tdsexemptEntity.getTdsxSite())
//		.tdsper(tdsexemptEntity.getTdsxTdsper())
//		.tdsupto(Objects.nonNull(tdsexemptEntity.getTdsxTdsupto()) ? tdsexemptEntity.getTdsxTdsupto().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//		.today(tdsexemptEntity.getTdsxToday())
//		.userid(tdsexemptEntity.getTdsxUserid())
//		.build();
//
//		return tdsexemptResponseBean.build();
//	};
//
//
//	public static Function<TdsexemptRequestBean, Tdsexempt> addTdsexemptPojoEntityMapper = tdsexemptRequestBean -> {
//		return Tdsexempt.builder().tdsexemptCK(TdsexemptCK.builder()
//				.tdsxContract(tdsexemptRequestBean.getContract())
//				.tdsxTdsfrom(Objects.nonNull(tdsexemptRequestBean.getTdsfrom()) ? LocalDate.parse(tdsexemptRequestBean.getTdsfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//				.build())
//				.tdsxExmpref(tdsexemptRequestBean.getExmpref())
//				.tdsxOrigsite(GenericAuditContextHolder.getContext().getSite())
//				.tdsxSite(GenericAuditContextHolder.getContext().getSite())
//				.tdsxTdsper(Objects.nonNull(tdsexemptRequestBean.getTdsper()) ? tdsexemptRequestBean.getTdsper() : BigInteger.ZERO.intValue())
//				.tdsxTdsupto(Objects.nonNull(tdsexemptRequestBean.getTdsupto()) ? LocalDate.parse(tdsexemptRequestBean.getTdsupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER): null)
//				.tdsxToday(LocalDateTime.now())
//				.tdsxUserid(GenericAuditContextHolder.getContext().getUserid())
//
//				.build();
//	} ;
	
	public static BiFunction<Tdsexempt, TdsexemptBean, Tdsexempt> updateTdsexemptEntityPojoMapper = (tdsexemptEntity, tdsexemptRequestBean) -> {
		tdsexemptEntity.getTdsexemptCK().setTdsxContract(Objects.nonNull(tdsexemptRequestBean.getContract()) ? tdsexemptRequestBean.getContract().trim() : tdsexemptEntity.getTdsexemptCK().getTdsxContract());
		tdsexemptEntity.setTdsxTdsfrom(Objects.nonNull(tdsexemptRequestBean.getTdsfrom()) ? LocalDate.parse(tdsexemptRequestBean.getTdsfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : tdsexemptEntity.getTdsxTdsfrom());
		tdsexemptEntity.setTdsxExmpref(Objects.nonNull(tdsexemptRequestBean.getExmpref()) ? tdsexemptRequestBean.getExmpref().trim() : tdsexemptEntity.getTdsxExmpref());
		tdsexemptEntity.setTdsxOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
		tdsexemptEntity.setTdsxSite(GenericAuditContextHolder.getContext().getSite()) ; 
		tdsexemptEntity.setTdsxTdsper(Objects.nonNull(tdsexemptRequestBean.getTdsper()) ? tdsexemptRequestBean.getTdsper() : tdsexemptEntity.getTdsxTdsper());
		tdsexemptEntity.setTdsxTdsupto(Objects.nonNull(tdsexemptRequestBean.getTdsupto()) ? LocalDate.parse(tdsexemptRequestBean.getTdsupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : tdsexemptEntity.getTdsxTdsupto());
		tdsexemptEntity.setTdsxToday(LocalDateTime.now()) ; 
		tdsexemptEntity.setTdsxUserid(GenericAuditContextHolder.getContext().getUserid()) ; 

		return tdsexemptEntity;
	};
}
