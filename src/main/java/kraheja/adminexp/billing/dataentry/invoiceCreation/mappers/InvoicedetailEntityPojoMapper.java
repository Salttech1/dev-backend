package kraheja.adminexp.billing.dataentry.invoiceCreation.mappers;import java.math.BigInteger;import java.time.LocalDateTime;import java.util.List;import java.util.Objects;import java.util.function.BiFunction;import java.util.function.Function;import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.request.InvoicedetailRequestBean;import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.response.InvoicedetailResponseBean;import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoicedetail;import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.InvoicedetailCK;import kraheja.commons.filter.GenericAuditContextHolder;import java.util.stream.Collectors;import java.util.stream.IntStream;public interface InvoicedetailEntityPojoMapper {//	@SuppressWarnings("unchecked")//public static Function	<List<Invoicedetail>, List<InvoicedetailResponseBean>> fetchInvoicedetailEntityPojoMapper = invoicedetailEntityList -> {//return invoicedetailEntityList.stream().map(invoicedetailEntity -> {//return InvoicedetailResponseBean.builder()
//.trtype(invoicedetailEntity.getInvoicedetailCK().getInvdTrtype())//					.invoiceno(invoicedetailEntity.getInvoicedetailCK().getInvdInvoiceno())//					.code(invoicedetailEntity.getInvoicedetailCK().getInvdCode())//					.srno(invoicedetailEntity.getInvoicedetailCK().getInvdSrno())//					.acmajor(invoicedetailEntity.getInvdAcmajor())//					.acminor(invoicedetailEntity.getInvdAcminor())//					.cgstpayable(invoicedetailEntity.getInvdCgstpayable())//					.cgstper(invoicedetailEntity.getInvdCgstper())//					.gstyn(invoicedetailEntity.getInvdGstyn())//					.hsncode(invoicedetailEntity.getInvdHsncode())//					.igstpayable(invoicedetailEntity.getInvdIgstpayable())//					.igstper(invoicedetailEntity.getInvdIgstper())//					.minortype(invoicedetailEntity.getInvdMinortype())//					.narration(invoicedetailEntity.getInvdNarration())//					.origsite(invoicedetailEntity.getInvdOrigsite())//					.quantity(invoicedetailEntity.getInvdQuantity())//					.rate(invoicedetailEntity.getInvdRate())//					.sgstpayable(invoicedetailEntity.getInvdSgstpayable())//					.sgstper(invoicedetailEntity.getInvdSgstper())//					.site(invoicedetailEntity.getInvdSite())//					.today(invoicedetailEntity.getInvdToday())//					.tranamtgstpayable(invoicedetailEntity.getInvdTranamtgstpayable())//					.ugstpayable(invoicedetailEntity.getInvdUgstpayable())//					.ugstper(invoicedetailEntity.getInvdUgstper())//					.userid(invoicedetailEntity.getInvdUserid())//.build(); //}).collect(Collectors.toList());////
//};public static Function<InvoicedetailRequestBean, Invoicedetail> addInvoicedetailPojoSingleEntityMapper = (invoicedetailRequestBean) -> {    return Invoicedetail.builder()            .invoicedetailCK(InvoicedetailCK.builder()                    .invdTrtype(invoicedetailRequestBean.getTrtype())                    .invdInvoiceno(invoicedetailRequestBean.getInvoiceno())                    .invdCode(invoicedetailRequestBean.getCode())                    .invdSrno(Objects.nonNull(invoicedetailRequestBean.getSrno()) ? invoicedetailRequestBean.getSrno() : BigInteger.ZERO.intValue())                    .build())            .invdAcmajor(invoicedetailRequestBean.getAcmajor())            .invdAcminor(invoicedetailRequestBean.getAcminor())            .invdCgstpayable(Objects.nonNull(invoicedetailRequestBean.getCgstpayable()) ? invoicedetailRequestBean.getCgstpayable() : BigInteger.ZERO.intValue())            .invdCgstper(Objects.nonNull(invoicedetailRequestBean.getCgstper()) ? invoicedetailRequestBean.getCgstper() : BigInteger.ZERO.intValue())            .invdGstyn(invoicedetailRequestBean.getGstyn())            .invdHsncode(invoicedetailRequestBean.getHsncode())            .invdIgstpayable(Objects.nonNull(invoicedetailRequestBean.getIgstpayable()) ? invoicedetailRequestBean.getIgstpayable() : BigInteger.ZERO.intValue())            .invdIgstper(Objects.nonNull(invoicedetailRequestBean.getIgstper()) ? invoicedetailRequestBean.getIgstper() : BigInteger.ZERO.intValue())            .invdMinortype(invoicedetailRequestBean.getMinortype())            .invdNarration(invoicedetailRequestBean.getNarration())            .invdOrigsite(GenericAuditContextHolder.getContext().getSite())            .invdQuantity(Objects.nonNull(invoicedetailRequestBean.getQuantity()) ? invoicedetailRequestBean.getQuantity() : BigInteger.ZERO.intValue())            .invdRate(Objects.nonNull(invoicedetailRequestBean.getRate()) ? invoicedetailRequestBean.getRate() : BigInteger.ZERO.intValue())            .invdSgstpayable(Objects.nonNull(invoicedetailRequestBean.getSgstpayable()) ? invoicedetailRequestBean.getSgstpayable() : BigInteger.ZERO.intValue())            .invdSgstper(Objects.nonNull(invoicedetailRequestBean.getSgstper()) ? invoicedetailRequestBean.getSgstper() : BigInteger.ZERO.intValue())            .invdSite(GenericAuditContextHolder.getContext().getSite())            .invdToday(LocalDateTime.now())            .invdTranamtgstpayable(Objects.nonNull(invoicedetailRequestBean.getTranamtgstpayable()) ? invoicedetailRequestBean.getTranamtgstpayable() : BigInteger.ZERO.intValue())            .invdUgstpayable(Objects.nonNull(invoicedetailRequestBean.getUgstpayable()) ? invoicedetailRequestBean.getUgstpayable() : BigInteger.ZERO.intValue())            .invdUgstper(Objects.nonNull(invoicedetailRequestBean.getUgstper()) ? invoicedetailRequestBean.getUgstper() : BigInteger.ZERO.intValue())            .invdUserid(GenericAuditContextHolder.getContext().getUserid())            .build();};
	public static Function<List<InvoicedetailRequestBean>, List <Invoicedetail>> addInvoicedetailPojoEntityMapper = (invoicedetailRequestBeanList) -> { return invoicedetailRequestBeanList.stream().map(invoicedetailRequestBean -> {return Invoicedetail.builder().invoicedetailCK(InvoicedetailCK.builder()					.invdTrtype(invoicedetailRequestBean.getTrtype())					.invdInvoiceno(invoicedetailRequestBean.getInvoiceno())					.invdCode(invoicedetailRequestBean.getCode())					.invdSrno(Objects.nonNull(invoicedetailRequestBean.getSrno()) ? invoicedetailRequestBean.getSrno() : BigInteger.ZERO.intValue())		.build())
					.invdAcmajor(invoicedetailRequestBean.getAcmajor())					.invdAcminor(invoicedetailRequestBean.getAcminor())					.invdCgstpayable(Objects.nonNull(invoicedetailRequestBean.getCgstpayable()) ? invoicedetailRequestBean.getCgstpayable() : BigInteger.ZERO.intValue())					.invdCgstper(Objects.nonNull(invoicedetailRequestBean.getCgstper()) ? invoicedetailRequestBean.getCgstper() : BigInteger.ZERO.intValue())					.invdGstyn(invoicedetailRequestBean.getGstyn())					.invdHsncode(invoicedetailRequestBean.getHsncode())					.invdIgstpayable(Objects.nonNull(invoicedetailRequestBean.getIgstpayable()) ? invoicedetailRequestBean.getIgstpayable() : BigInteger.ZERO.intValue())					.invdIgstper(Objects.nonNull(invoicedetailRequestBean.getIgstper()) ? invoicedetailRequestBean.getIgstper() : BigInteger.ZERO.intValue())					.invdMinortype(invoicedetailRequestBean.getMinortype())					.invdNarration(invoicedetailRequestBean.getNarration())					.invdOrigsite(GenericAuditContextHolder.getContext().getSite())					.invdQuantity(Objects.nonNull(invoicedetailRequestBean.getQuantity()) ? invoicedetailRequestBean.getQuantity() : BigInteger.ZERO.intValue())					.invdRate(Objects.nonNull(invoicedetailRequestBean.getRate()) ? invoicedetailRequestBean.getRate() : BigInteger.ZERO.intValue())					.invdSgstpayable(Objects.nonNull(invoicedetailRequestBean.getSgstpayable()) ? invoicedetailRequestBean.getSgstpayable() : BigInteger.ZERO.intValue())					.invdSgstper(Objects.nonNull(invoicedetailRequestBean.getSgstper()) ? invoicedetailRequestBean.getSgstper() : BigInteger.ZERO.intValue())					.invdSite(GenericAuditContextHolder.getContext().getSite())					.invdToday(LocalDateTime.now())					.invdTranamtgstpayable(Objects.nonNull(invoicedetailRequestBean.getTranamtgstpayable()) ? invoicedetailRequestBean.getTranamtgstpayable() : BigInteger.ZERO.intValue())					.invdUgstpayable(Objects.nonNull(invoicedetailRequestBean.getUgstpayable()) ? invoicedetailRequestBean.getUgstpayable() : BigInteger.ZERO.intValue())					.invdUgstper(Objects.nonNull(invoicedetailRequestBean.getUgstper()) ? invoicedetailRequestBean.getUgstper() : BigInteger.ZERO.intValue())					.invdUserid(GenericAuditContextHolder.getContext().getUserid())		.build();}).collect(Collectors.toList());} ;
	public static BiFunction<Invoicedetail, InvoicedetailRequestBean, Invoicedetail> updateInvoicedetailEntityPojoMapper = (invoicedetailEntity, invoicedetailRequestBean) -> {		invoicedetailEntity.getInvoicedetailCK().setInvdTrtype(Objects.nonNull(invoicedetailRequestBean.getTrtype()) ? invoicedetailRequestBean.getTrtype().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdTrtype());		invoicedetailEntity.getInvoicedetailCK().setInvdInvoiceno(Objects.nonNull(invoicedetailRequestBean.getInvoiceno()) ? invoicedetailRequestBean.getInvoiceno().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdInvoiceno());		invoicedetailEntity.getInvoicedetailCK().setInvdCode(Objects.nonNull(invoicedetailRequestBean.getCode()) ? invoicedetailRequestBean.getCode().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdCode());		invoicedetailEntity.getInvoicedetailCK().setInvdSrno(Objects.nonNull(invoicedetailRequestBean.getSrno()) ? invoicedetailRequestBean.getSrno() : invoicedetailEntity.getInvoicedetailCK().getInvdSrno());
		invoicedetailEntity.setInvdAcmajor(Objects.nonNull(invoicedetailRequestBean.getAcmajor()) ? invoicedetailRequestBean.getAcmajor().trim() : invoicedetailEntity.getInvdAcmajor());		invoicedetailEntity.setInvdAcminor(Objects.nonNull(invoicedetailRequestBean.getAcminor()) ? invoicedetailRequestBean.getAcminor().trim() : invoicedetailEntity.getInvdAcminor());		invoicedetailEntity.setInvdCgstpayable(Objects.nonNull(invoicedetailRequestBean.getCgstpayable()) ? invoicedetailRequestBean.getCgstpayable() : invoicedetailEntity.getInvdCgstpayable());		invoicedetailEntity.setInvdCgstper(Objects.nonNull(invoicedetailRequestBean.getCgstper()) ? invoicedetailRequestBean.getCgstper() : invoicedetailEntity.getInvdCgstper());		invoicedetailEntity.setInvdGstyn(Objects.nonNull(invoicedetailRequestBean.getGstyn()) ? invoicedetailRequestBean.getGstyn().trim() : invoicedetailEntity.getInvdGstyn());		invoicedetailEntity.setInvdHsncode(Objects.nonNull(invoicedetailRequestBean.getHsncode()) ? invoicedetailRequestBean.getHsncode().trim() : invoicedetailEntity.getInvdHsncode());		invoicedetailEntity.setInvdIgstpayable(Objects.nonNull(invoicedetailRequestBean.getIgstpayable()) ? invoicedetailRequestBean.getIgstpayable() : invoicedetailEntity.getInvdIgstpayable());		invoicedetailEntity.setInvdIgstper(Objects.nonNull(invoicedetailRequestBean.getIgstper()) ? invoicedetailRequestBean.getIgstper() : invoicedetailEntity.getInvdIgstper());		invoicedetailEntity.setInvdMinortype(Objects.nonNull(invoicedetailRequestBean.getMinortype()) ? invoicedetailRequestBean.getMinortype().trim() : invoicedetailEntity.getInvdMinortype());		invoicedetailEntity.setInvdNarration(Objects.nonNull(invoicedetailRequestBean.getNarration()) ? invoicedetailRequestBean.getNarration().trim() : invoicedetailEntity.getInvdNarration());		invoicedetailEntity.setInvdOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 		invoicedetailEntity.setInvdQuantity(Objects.nonNull(invoicedetailRequestBean.getQuantity()) ? invoicedetailRequestBean.getQuantity() : invoicedetailEntity.getInvdQuantity());		invoicedetailEntity.setInvdRate(Objects.nonNull(invoicedetailRequestBean.getRate()) ? invoicedetailRequestBean.getRate() : invoicedetailEntity.getInvdRate());		invoicedetailEntity.setInvdSgstpayable(Objects.nonNull(invoicedetailRequestBean.getSgstpayable()) ? invoicedetailRequestBean.getSgstpayable() : invoicedetailEntity.getInvdSgstpayable());		invoicedetailEntity.setInvdSgstper(Objects.nonNull(invoicedetailRequestBean.getSgstper()) ? invoicedetailRequestBean.getSgstper() : invoicedetailEntity.getInvdSgstper());		invoicedetailEntity.setInvdSite(GenericAuditContextHolder.getContext().getSite()) ; 		invoicedetailEntity.setInvdToday(LocalDateTime.now()) ; 		invoicedetailEntity.setInvdTranamtgstpayable(Objects.nonNull(invoicedetailRequestBean.getTranamtgstpayable()) ? invoicedetailRequestBean.getTranamtgstpayable() : invoicedetailEntity.getInvdTranamtgstpayable());		invoicedetailEntity.setInvdUgstpayable(Objects.nonNull(invoicedetailRequestBean.getUgstpayable()) ? invoicedetailRequestBean.getUgstpayable() : invoicedetailEntity.getInvdUgstpayable());		invoicedetailEntity.setInvdUgstper(Objects.nonNull(invoicedetailRequestBean.getUgstper()) ? invoicedetailRequestBean.getUgstper() : invoicedetailEntity.getInvdUgstper());		invoicedetailEntity.setInvdUserid(GenericAuditContextHolder.getContext().getUserid()) ; 		return invoicedetailEntity;	};public static List<Invoicedetail> updateInvoicedetailEntitiesPojoMapper(List<Invoicedetail> invoicedetailEntities, List<InvoicedetailRequestBean> invoicedetailRequestBeans) {    return IntStream.range(0, Math.min(invoicedetailEntities.size(), invoicedetailRequestBeans.size()))        .mapToObj(i -> {            Invoicedetail invoicedetailEntity = invoicedetailEntities.get(i);            InvoicedetailRequestBean invoicedetailRequestBean = invoicedetailRequestBeans.get(i);            invoicedetailEntity.getInvoicedetailCK().setInvdTrtype(Objects.nonNull(invoicedetailRequestBean.getTrtype()) ? invoicedetailRequestBean.getTrtype().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdTrtype());            invoicedetailEntity.getInvoicedetailCK().setInvdInvoiceno(Objects.nonNull(invoicedetailRequestBean.getInvoiceno()) ? invoicedetailRequestBean.getInvoiceno().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdInvoiceno());            invoicedetailEntity.getInvoicedetailCK().setInvdCode(Objects.nonNull(invoicedetailRequestBean.getCode()) ? invoicedetailRequestBean.getCode().trim() : invoicedetailEntity.getInvoicedetailCK().getInvdCode());            invoicedetailEntity.getInvoicedetailCK().setInvdSrno(Objects.nonNull(invoicedetailRequestBean.getSrno()) ? invoicedetailRequestBean.getSrno() : invoicedetailEntity.getInvoicedetailCK().getInvdSrno());            invoicedetailEntity.setInvdAcmajor(Objects.nonNull(invoicedetailRequestBean.getAcmajor()) ? invoicedetailRequestBean.getAcmajor().trim() : invoicedetailEntity.getInvdAcmajor());            invoicedetailEntity.setInvdAcminor(Objects.nonNull(invoicedetailRequestBean.getAcminor()) ? invoicedetailRequestBean.getAcminor().trim() : invoicedetailEntity.getInvdAcminor());            invoicedetailEntity.setInvdCgstpayable(Objects.nonNull(invoicedetailRequestBean.getCgstpayable()) ? invoicedetailRequestBean.getCgstpayable() : invoicedetailEntity.getInvdCgstpayable());            invoicedetailEntity.setInvdCgstper(Objects.nonNull(invoicedetailRequestBean.getCgstper()) ? invoicedetailRequestBean.getCgstper() : invoicedetailEntity.getInvdCgstper());            invoicedetailEntity.setInvdGstyn(Objects.nonNull(invoicedetailRequestBean.getGstyn()) ? invoicedetailRequestBean.getGstyn().trim() : invoicedetailEntity.getInvdGstyn());            invoicedetailEntity.setInvdHsncode(Objects.nonNull(invoicedetailRequestBean.getHsncode()) ? invoicedetailRequestBean.getHsncode().trim() : invoicedetailEntity.getInvdHsncode());            invoicedetailEntity.setInvdIgstpayable(Objects.nonNull(invoicedetailRequestBean.getIgstpayable()) ? invoicedetailRequestBean.getIgstpayable() : invoicedetailEntity.getInvdIgstpayable());            invoicedetailEntity.setInvdIgstper(Objects.nonNull(invoicedetailRequestBean.getIgstper()) ? invoicedetailRequestBean.getIgstper() : invoicedetailEntity.getInvdIgstper());            invoicedetailEntity.setInvdMinortype(Objects.nonNull(invoicedetailRequestBean.getMinortype()) ? invoicedetailRequestBean.getMinortype().trim() : invoicedetailEntity.getInvdMinortype());            invoicedetailEntity.setInvdNarration(Objects.nonNull(invoicedetailRequestBean.getNarration()) ? invoicedetailRequestBean.getNarration().trim() : invoicedetailEntity.getInvdNarration());            invoicedetailEntity.setInvdOrigsite(GenericAuditContextHolder.getContext().getSite());            invoicedetailEntity.setInvdQuantity(Objects.nonNull(invoicedetailRequestBean.getQuantity()) ? invoicedetailRequestBean.getQuantity() : invoicedetailEntity.getInvdQuantity());            invoicedetailEntity.setInvdRate(Objects.nonNull(invoicedetailRequestBean.getRate()) ? invoicedetailRequestBean.getRate() : invoicedetailEntity.getInvdRate());            invoicedetailEntity.setInvdSgstpayable(Objects.nonNull(invoicedetailRequestBean.getSgstpayable()) ? invoicedetailRequestBean.getSgstpayable() : invoicedetailEntity.getInvdSgstpayable());            invoicedetailEntity.setInvdSgstper(Objects.nonNull(invoicedetailRequestBean.getSgstper()) ? invoicedetailRequestBean.getSgstper() : invoicedetailEntity.getInvdSgstper());            invoicedetailEntity.setInvdSite(GenericAuditContextHolder.getContext().getSite());            invoicedetailEntity.setInvdToday(LocalDateTime.now());            invoicedetailEntity.setInvdTranamtgstpayable(Objects.nonNull(invoicedetailRequestBean.getTranamtgstpayable()) ? invoicedetailRequestBean.getTranamtgstpayable() : invoicedetailEntity.getInvdTranamtgstpayable());            invoicedetailEntity.setInvdUgstpayable(Objects.nonNull(invoicedetailRequestBean.getUgstpayable()) ? invoicedetailRequestBean.getUgstpayable() : invoicedetailEntity.getInvdUgstpayable());            invoicedetailEntity.setInvdUgstper(Objects.nonNull(invoicedetailRequestBean.getUgstper()) ? invoicedetailRequestBean.getUgstper() : invoicedetailEntity.getInvdUgstper());            invoicedetailEntity.setInvdUserid(GenericAuditContextHolder.getContext().getUserid());            return invoicedetailEntity;        })        .collect(Collectors.toList());}}
