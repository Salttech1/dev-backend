// Developed By  - 	sandesh.c
// Developed on - 13-11-23
// Mode  - Data Entry
// Purpose - Loancompanyaddress Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

//package kraheja.sales.bookings.dataentry.mappers;
//
//import java.math.BigInteger;
//import java.time.LocalDateTime;
//import java.util.Objects;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
//import kraheja.commons.filter.GenericAuditContextHolder;
//import kraheja.sales.bean.request.LoancompanyaddressRequestBean;
//import kraheja.sales.bean.response.LoancompanyaddressResponseBean;
//import kraheja.sales.bean.response.LoancompanyaddressResponseBean.LoancompanyaddressResponseBeanBuilder;
//import kraheja.sales.entity.Loancompanyaddress;
//import kraheja.sales.entity.LoancompanyaddressCK;
//
//public interface LoancompanyaddressEntityPojoMapper {
//	@SuppressWarnings("unchecked")
//public static Function<Object[],LoancompanyaddressResponseBean> fetchLoancompanyaddressEntityPojoMapper = objectArray -> {
//Loancompanyaddress loancompanyaddressEntity = (Loancompanyaddress) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()] : null);
//		LoancompanyaddressResponseBeanBuilder loancompanyaddressResponseBean = LoancompanyaddressResponseBean.builder();
//		loancompanyaddressResponseBean
//
//.loancoycode(loancompanyaddressEntity.getLoancompanyaddressCK().getLcaLoancoycode())
//					.loanbranchcode(loancompanyaddressEntity.getLoancompanyaddressCK().getLcaLoanbranchcode())
//					.adline1(loancompanyaddressEntity.getLcaAdline1())
//					.adline2(loancompanyaddressEntity.getLcaAdline2())
//					.adline3(loancompanyaddressEntity.getLcaAdline3())
//					.adline4(loancompanyaddressEntity.getLcaAdline4())
//					.adline5(loancompanyaddressEntity.getLcaAdline5())
//					.city(loancompanyaddressEntity.getLcaCity())
//					.country(loancompanyaddressEntity.getLcaCountry())
//					.email(loancompanyaddressEntity.getLcaEmail())
//					.fax(loancompanyaddressEntity.getLcaFax())
//					.origsite(loancompanyaddressEntity.getLcaOrigsite())
//					.phonemobile(loancompanyaddressEntity.getLcaPhonemobile())
//					.phoneoff(loancompanyaddressEntity.getLcaPhoneoff())
//					.phoneres(loancompanyaddressEntity.getLcaPhoneres())
//					.pincode(loancompanyaddressEntity.getLcaPincode())
//					.site(loancompanyaddressEntity.getLcaSite())
//					.state(loancompanyaddressEntity.getLcaState())
//					.today(loancompanyaddressEntity.getLcaToday())
//					.township(loancompanyaddressEntity.getLcaTownship())
//					.userid(loancompanyaddressEntity.getLcaUserid())
//.build();
//
//
//			return loancompanyaddressResponseBean.build();
//};
//
//
//
//	public static Function<LoancompanyaddressRequestBean, Loancompanyaddress> addLoancompanyaddressPojoEntityMapper = loancompanyaddressRequestBean -> {
//return Loancompanyaddress.builder().loancompanyaddressCK(LoancompanyaddressCK.builder()
//					.lcaLoancoycode(loancompanyaddressRequestBean.getLoancoycode())
//					.lcaLoanbranchcode(loancompanyaddressRequestBean.getLoanbranchcode())
//		.build())
//
//					.lcaAdline1(loancompanyaddressRequestBean.getAdline1())
//					.lcaAdline2(loancompanyaddressRequestBean.getAdline2())
//					.lcaAdline3(loancompanyaddressRequestBean.getAdline3())
//					.lcaAdline4(loancompanyaddressRequestBean.getAdline4())
//					.lcaAdline5(loancompanyaddressRequestBean.getAdline5())
//					.lcaCity(loancompanyaddressRequestBean.getCity())
//					.lcaCountry(loancompanyaddressRequestBean.getCountry())
//					.lcaEmail(loancompanyaddressRequestBean.getEmail())
//					.lcaFax(loancompanyaddressRequestBean.getFax())
//					.lcaOrigsite(GenericAuditContextHolder.getContext().getSite())
//					.lcaPhonemobile(loancompanyaddressRequestBean.getPhonemobile())
//					.lcaPhoneoff(loancompanyaddressRequestBean.getPhoneoff())
//					.lcaPhoneres(loancompanyaddressRequestBean.getPhoneres())
//					.lcaPincode(loancompanyaddressRequestBean.getPincode())
//					//.lcaSite(GenericAuditContextHolder.getContext().getSite())
//					.lcaSite("MUM")
//					.lcaState(loancompanyaddressRequestBean.getState())
//					.lcaToday(LocalDateTime.now())
//					.lcaTownship(loancompanyaddressRequestBean.getTownship())
//					.lcaUserid("SANDY")
//					//.lcaUserid(GenericAuditContextHolder.getContext().getUserid())
//		
//.build();
//} ;
//
//	public static BiFunction<Loancompanyaddress, LoancompanyaddressRequestBean, Loancompanyaddress> updateLoancompanyaddressEntityPojoMapper = (loancompanyaddressEntity, loancompanyaddressRequestBean) -> {
//		loancompanyaddressEntity.getLoancompanyaddressCK().setLcaLoancoycode(Objects.nonNull(loancompanyaddressRequestBean.getLoancoycode()) ? loancompanyaddressRequestBean.getLoancoycode().trim() : loancompanyaddressEntity.getLoancompanyaddressCK().getLcaLoancoycode());
//		loancompanyaddressEntity.getLoancompanyaddressCK().setLcaLoanbranchcode(Objects.nonNull(loancompanyaddressRequestBean.getLoanbranchcode()) ? loancompanyaddressRequestBean.getLoanbranchcode().trim() : loancompanyaddressEntity.getLoancompanyaddressCK().getLcaLoanbranchcode());
//
//		loancompanyaddressEntity.setLcaAdline1(Objects.nonNull(loancompanyaddressRequestBean.getAdline1()) ? loancompanyaddressRequestBean.getAdline1().trim() : loancompanyaddressEntity.getLcaAdline1());
//		loancompanyaddressEntity.setLcaAdline2(Objects.nonNull(loancompanyaddressRequestBean.getAdline2()) ? loancompanyaddressRequestBean.getAdline2().trim() : loancompanyaddressEntity.getLcaAdline2());
//		loancompanyaddressEntity.setLcaAdline3(Objects.nonNull(loancompanyaddressRequestBean.getAdline3()) ? loancompanyaddressRequestBean.getAdline3().trim() : loancompanyaddressEntity.getLcaAdline3());
//		loancompanyaddressEntity.setLcaAdline4(Objects.nonNull(loancompanyaddressRequestBean.getAdline4()) ? loancompanyaddressRequestBean.getAdline4().trim() : loancompanyaddressEntity.getLcaAdline4());
//		loancompanyaddressEntity.setLcaAdline5(Objects.nonNull(loancompanyaddressRequestBean.getAdline5()) ? loancompanyaddressRequestBean.getAdline5().trim() : loancompanyaddressEntity.getLcaAdline5());
//		loancompanyaddressEntity.setLcaCity(Objects.nonNull(loancompanyaddressRequestBean.getCity()) ? loancompanyaddressRequestBean.getCity().trim() : loancompanyaddressEntity.getLcaCity());
//		loancompanyaddressEntity.setLcaCountry(Objects.nonNull(loancompanyaddressRequestBean.getCountry()) ? loancompanyaddressRequestBean.getCountry().trim() : loancompanyaddressEntity.getLcaCountry());
//		loancompanyaddressEntity.setLcaEmail(Objects.nonNull(loancompanyaddressRequestBean.getEmail()) ? loancompanyaddressRequestBean.getEmail().trim() : loancompanyaddressEntity.getLcaEmail());
//		loancompanyaddressEntity.setLcaFax(Objects.nonNull(loancompanyaddressRequestBean.getFax()) ? loancompanyaddressRequestBean.getFax().trim() : loancompanyaddressEntity.getLcaFax());
//		loancompanyaddressEntity.setLcaOrigsite(GenericAuditContextHolder.getContext().getSite()) ; 
//		loancompanyaddressEntity.setLcaPhonemobile(Objects.nonNull(loancompanyaddressRequestBean.getPhonemobile()) ? loancompanyaddressRequestBean.getPhonemobile().trim() : loancompanyaddressEntity.getLcaPhonemobile());
//		loancompanyaddressEntity.setLcaPhoneoff(Objects.nonNull(loancompanyaddressRequestBean.getPhoneoff()) ? loancompanyaddressRequestBean.getPhoneoff().trim() : loancompanyaddressEntity.getLcaPhoneoff());
//		loancompanyaddressEntity.setLcaPhoneres(Objects.nonNull(loancompanyaddressRequestBean.getPhoneres()) ? loancompanyaddressRequestBean.getPhoneres().trim() : loancompanyaddressEntity.getLcaPhoneres());
//		loancompanyaddressEntity.setLcaPincode(Objects.nonNull(loancompanyaddressRequestBean.getPincode()) ? loancompanyaddressRequestBean.getPincode().trim() : loancompanyaddressEntity.getLcaPincode());
//		loancompanyaddressEntity.setLcaSite(GenericAuditContextHolder.getContext().getSite()) ; 
//		loancompanyaddressEntity.setLcaState(Objects.nonNull(loancompanyaddressRequestBean.getState()) ? loancompanyaddressRequestBean.getState().trim() : loancompanyaddressEntity.getLcaState());
//		loancompanyaddressEntity.setLcaToday(LocalDateTime.now()) ; 
//		loancompanyaddressEntity.setLcaTownship(Objects.nonNull(loancompanyaddressRequestBean.getTownship()) ? loancompanyaddressRequestBean.getTownship().trim() : loancompanyaddressEntity.getLcaTownship());
//		loancompanyaddressEntity.setLcaUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
//
//
//		return loancompanyaddressEntity;
//	};
//
//}
