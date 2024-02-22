package kraheja.adminexp.billing.dataentry.invoiceCreation.mappers;


import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.commons.collections4.CollectionUtils;

import kraheja.adminexp.billing.dataentry.invoiceCreation.bean.response.InvpartymasterResponseBean;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invpartymaster;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import java.util.stream.Collectors;


public interface InvpartymasterEntityPojoMapper {
	//@SuppressWarnings("unchecked")
//public static Function	<List<Invpartymaster>, List<InvpartymasterResponseBean>> fetchInvpartymasterEntityPojoMapper = invpartymasterEntityList -> {
//return invpartymasterEntityList.stream().map(invpartymasterEntity -> {
//return InvpartymasterResponseBean.builder()
//.coycode(invpartymasterEntity.getInvpartymasterCK().getIpmsCoycode())
//					.partytype(invpartymasterEntity.getInvpartymasterCK().getIpmsPartytype())
//					.partycode(invpartymasterEntity.getInvpartymasterCK().getIpmsPartycode())
//					.billtype(invpartymasterEntity.getInvpartymasterCK().getIpmsBilltype())
//					.item_bill_code(invpartymasterEntity.getInvpartymasterCK().getIpmsItem_Bill_Code())
//					.acmajor(invpartymasterEntity.getIpmsAcmajor())
//					.billnote(invpartymasterEntity.getIpmsBillnote())
//					.billprocesstype(invpartymasterEntity.getIpmsBillprocesstype())
//					.emailid(invpartymasterEntity.getIpmsEmailid())
//					.hsnsac(invpartymasterEntity.getIpmsHsnsac())
//					.item_bill_desc(invpartymasterEntity.getIpmsItem_Bill_Desc())
//					.narration(invpartymasterEntity.getIpmsNarration())
//					.qty(invpartymasterEntity.getIpmsQty())
//					.rate(invpartymasterEntity.getIpmsRate())
//					.servicenature(invpartymasterEntity.getIpmsServicenature())
//					.signauth(invpartymasterEntity.getIpmsSignauth())
//					.site(invpartymasterEntity.getIpmsSite())
//					.subject(invpartymasterEntity.getIpmsSubject())
//					.today(invpartymasterEntity.getIpmsToday())
//					.user(invpartymasterEntity.getIpmsUser())
//.build(); 
//}).collect(Collectors.toList());
//
//};


//	public static Function<List<InvpartymasterRequestBean>, List <Invpartymaster>> addInvpartymasterPojoEntityMapper = (invpartymasterRequestBeanList) -> { 
//return invpartymasterRequestBeanList.stream().map(invpartymasterRequestBean -> {
//return Invpartymaster.builder().invpartymasterCK(InvpartymasterCK.builder()
//					.ipmsCoycode(invpartymasterRequestBean.getCoycode())
//					.ipmsPartytype(invpartymasterRequestBean.getPartytype())
//					.ipmsPartycode(invpartymasterRequestBean.getPartycode())
//					.ipmsBilltype(invpartymasterRequestBean.getBilltype())
//					.ipmsItem_Bill_Code(invpartymasterRequestBean.getItem_Bill_Code())
//		.build())
//					.ipmsAcmajor(invpartymasterRequestBean.getAcmajor())
//					.ipmsBillnote(invpartymasterRequestBean.getBillnote())
//					.ipmsBillprocesstype(invpartymasterRequestBean.getBillprocesstype())
//					.ipmsEmailid(invpartymasterRequestBean.getEmailid())
//					.ipmsHsnsac(invpartymasterRequestBean.getHsnsac())
//					.ipmsItem_Bill_Desc(invpartymasterRequestBean.getItem_Bill_Desc())
//					.ipmsNarration(invpartymasterRequestBean.getNarration())
//					.ipmsQty(Objects.nonNull(invpartymasterRequestBean.getQty()) ? invpartymasterRequestBean.getQty() : BigInteger.ZERO.intValue())
//					.ipmsRate(Objects.nonNull(invpartymasterRequestBean.getRate()) ? invpartymasterRequestBean.getRate() : BigInteger.ZERO.doubleValue())
//					.ipmsServicenature(invpartymasterRequestBean.getServicenature())
//					.ipmsSignauth(invpartymasterRequestBean.getSignauth())
//					.ipmsSite(GenericAuditContextHolder.getContext().getSite())
//					.ipmsSubject(invpartymasterRequestBean.getSubject())
//					.ipmsToday(LocalDateTime.now())
//					.ipmsUser(invpartymasterRequestBean.getUser())
//		
//.build();
//}).collect(Collectors.toList());
//} ;
//	public static BiFunction<Invpartymaster, InvpartymasterRequestBean, Invpartymaster> updateInvpartymasterEntityPojoMapper = (invpartymasterEntity, invpartymasterRequestBean) -> {
//		invpartymasterEntity.getInvpartymasterCK().setIpmsCoycode(Objects.nonNull(invpartymasterRequestBean.getCoycode()) ? invpartymasterRequestBean.getCoycode().trim() : invpartymasterEntity.getInvpartymasterCK().getIpmsCoycode());
//		invpartymasterEntity.getInvpartymasterCK().setIpmsPartytype(Objects.nonNull(invpartymasterRequestBean.getPartytype()) ? invpartymasterRequestBean.getPartytype().trim() : invpartymasterEntity.getInvpartymasterCK().getIpmsPartytype());
//		invpartymasterEntity.getInvpartymasterCK().setIpmsPartycode(Objects.nonNull(invpartymasterRequestBean.getPartycode()) ? invpartymasterRequestBean.getPartycode().trim() : invpartymasterEntity.getInvpartymasterCK().getIpmsPartycode());
//		invpartymasterEntity.getInvpartymasterCK().setIpmsBilltype(Objects.nonNull(invpartymasterRequestBean.getBilltype()) ? invpartymasterRequestBean.getBilltype().trim() : invpartymasterEntity.getInvpartymasterCK().getIpmsBilltype());
//		invpartymasterEntity.getInvpartymasterCK().setIpmsItem_Bill_Code(Objects.nonNull(invpartymasterRequestBean.getItem_Bill_Code()) ? invpartymasterRequestBean.getItem_Bill_Code().trim() : invpartymasterEntity.getInvpartymasterCK().getIpmsItem_Bill_Code());
//		invpartymasterEntity.setIpmsAcmajor(Objects.nonNull(invpartymasterRequestBean.getAcmajor()) ? invpartymasterRequestBean.getAcmajor().trim() : invpartymasterEntity.getIpmsAcmajor());
//		invpartymasterEntity.setIpmsBillnote(Objects.nonNull(invpartymasterRequestBean.getBillnote()) ? invpartymasterRequestBean.getBillnote().trim() : invpartymasterEntity.getIpmsBillnote());
//		invpartymasterEntity.setIpmsBillprocesstype(Objects.nonNull(invpartymasterRequestBean.getBillprocesstype()) ? invpartymasterRequestBean.getBillprocesstype().trim() : invpartymasterEntity.getIpmsBillprocesstype());
//		invpartymasterEntity.setIpmsEmailid(Objects.nonNull(invpartymasterRequestBean.getEmailid()) ? invpartymasterRequestBean.getEmailid().trim() : invpartymasterEntity.getIpmsEmailid());
//		invpartymasterEntity.setIpmsHsnsac(Objects.nonNull(invpartymasterRequestBean.getHsnsac()) ? invpartymasterRequestBean.getHsnsac().trim() : invpartymasterEntity.getIpmsHsnsac());
//		invpartymasterEntity.setIpmsItem_Bill_Desc(Objects.nonNull(invpartymasterRequestBean.getItem_bill_desc()) ? invpartymasterRequestBean.getItem_bill_desc().trim() : invpartymasterEntity.getIpmsItem_Bill_Desc());
//		invpartymasterEntity.setIpmsNarration(Objects.nonNull(invpartymasterRequestBean.getNarration()) ? invpartymasterRequestBean.getNarration().trim() : invpartymasterEntity.getIpmsNarration());
//		invpartymasterEntity.setIpmsQty(Objects.nonNull(invpartymasterRequestBean.getQty()) ? invpartymasterRequestBean.getQty() : invpartymasterEntity.getIpmsQty());
//		invpartymasterEntity.setIpmsRate(Objects.nonNull(invpartymasterRequestBean.getRate()) ? invpartymasterRequestBean.getRate() : invpartymasterEntity.getIpmsRate());
//		invpartymasterEntity.setIpmsServicenature(Objects.nonNull(invpartymasterRequestBean.getServicenature()) ? invpartymasterRequestBean.getServicenature().trim() : invpartymasterEntity.getIpmsServicenature());
//		invpartymasterEntity.setIpmsSignauth(Objects.nonNull(invpartymasterRequestBean.getSignauth()) ? invpartymasterRequestBean.getSignauth().trim() : invpartymasterEntity.getIpmsSignauth());
//		invpartymasterEntity.setIpmsSite(GenericAuditContextHolder.getContext().getSite()) ; 
//		invpartymasterEntity.setIpmsSubject(Objects.nonNull(invpartymasterRequestBean.getSubject()) ? invpartymasterRequestBean.getSubject().trim() : invpartymasterEntity.getIpmsSubject());
//		invpartymasterEntity.setIpmsToday(LocalDateTime.now()) ; 
//		invpartymasterEntity.setIpmsUser(Objects.nonNull(invpartymasterRequestBean.getUser()) ? invpartymasterRequestBean.getUser().trim() : invpartymasterEntity.getIpmsUser());
//
//
//		return invpartymasterEntity;
//	};

}

