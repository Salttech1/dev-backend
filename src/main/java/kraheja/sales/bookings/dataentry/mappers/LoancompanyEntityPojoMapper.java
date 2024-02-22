// Developed By  - 	sandesh.c
// Developed on - 13-11-23
// Mode  - Data Entry
// Purpose - Loancompany Entry / Edit
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
//import kraheja.sales.bean.request.LoancompanyRequestBean;
//import kraheja.sales.bean.response.LoancompanyResponseBean;
//import kraheja.sales.bean.response.LoancompanyResponseBean.LoancompanyResponseBeanBuilder;
//import kraheja.sales.entity.Loancompany;
//import kraheja.sales.entity.LoancompanyCK;
//import kraheja.sales.entity.Loancompanyaddress;
//
//public interface LoancompanyEntityPojoMapper {
//	@SuppressWarnings("unchecked")
//	public static Function<Object[], LoancompanyResponseBean> fetchLoancompanyEntityPojoMapper = objectArray -> {
//
//		Loancompany loancompanyEntity = (Loancompany) (Objects.nonNull(objectArray[BigInteger.ZERO.intValue()])
//				? objectArray[BigInteger.ZERO.intValue()]
//				: null);
//
//		Loancompanyaddress loancompanyaddressEntity = (Loancompanyaddress) (Objects.nonNull(
//				objectArray[BigInteger.ONE.intValue()]) ? objectArray[BigInteger.ONE.intValue()] : null);
//		
//		LoancompanyResponseBeanBuilder loancompanyResponseBean = LoancompanyResponseBean.builder();
//		loancompanyResponseBean
//				.code(loancompanyEntity.getLoancompanyCK().getLcoyCode()).name(loancompanyEntity.getLcoyName())
//				.nocyn(loancompanyEntity.getLcoyNocyn()).site(loancompanyEntity.getLcoySite())
//				.today(loancompanyEntity.getLcoyToday()).userid(loancompanyEntity.getLcoyUserid()).build();
//		
//		if (Objects.nonNull(loancompanyaddressEntity))
//		
//			loancompanyResponseBean.loancompanyaddressResponseBean(
//					LoancompanyaddressEntityPojoMapper.fetchLoancompanyaddressEntityPojoMapper
//					.apply(new Object[] { loancompanyaddressEntity }));
//		
//		return loancompanyResponseBean.build();
//	};
//
//	public static Function<LoancompanyRequestBean, Loancompany> addLoancompanyPojoEntityMapper = loancompanyRequestBean -> {
//		return Loancompany.builder()
//				.loancompanyCK(LoancompanyCK.builder().lcoyCode(loancompanyRequestBean.getCode()).build())
//
//				.lcoyName(loancompanyRequestBean.getName()).lcoyNocyn(loancompanyRequestBean.getNocyn())
//				//.lcoySite(GenericAuditContextHolder.getContext().getSite())
//				.lcoySite("MUM")
//				.lcoyToday(LocalDateTime.now())
//				//.lcoyUserid(GenericAuditContextHolder.getContext().getUserid())
//				.lcoyUserid("SANDY")
//				.build();
//	};
//
//	public static BiFunction<Loancompany, LoancompanyRequestBean, Loancompany> updateLoancompanyEntityPojoMapper = (
//			loancompanyEntity, loancompanyRequestBean) -> {
//		loancompanyEntity.getLoancompanyCK()
//				.setLcoyCode(Objects.nonNull(loancompanyRequestBean.getCode()) ? loancompanyRequestBean.getCode().trim()
//						: loancompanyEntity.getLoancompanyCK().getLcoyCode());
//
//		loancompanyEntity
//				.setLcoyName(Objects.nonNull(loancompanyRequestBean.getName()) ? loancompanyRequestBean.getName().trim()
//						: loancompanyEntity.getLcoyName());
//		loancompanyEntity.setLcoyNocyn(
//				Objects.nonNull(loancompanyRequestBean.getNocyn()) ? loancompanyRequestBean.getNocyn().trim()
//						: loancompanyEntity.getLcoyNocyn());
//		loancompanyEntity.setLcoySite(GenericAuditContextHolder.getContext().getSite());
//		loancompanyEntity.setLcoyToday(LocalDateTime.now());
//		loancompanyEntity.setLcoyUserid(GenericAuditContextHolder.getContext().getUserid());
//
//		return loancompanyEntity;
//	};
//
//}
