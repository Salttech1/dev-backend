// Developed By  - 	sandesh.c
// Developed on - 18-08-23
// Mode  - Data Entry
// Purpose - Loanhistory Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.mappers;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.sales.bean.request.LoanhistoryRequestBean;
import kraheja.sales.bean.response.LoanhistoryResponseBean;
import kraheja.sales.entity.Loanhistory;
import kraheja.sales.entity.LoanhistoryCK;

public interface LoanhistoryEntityPojoMapper {
	@SuppressWarnings("unchecked")
	public static Function<List<Loanhistory>, List<LoanhistoryResponseBean>> fetchLoanhistoryEntityPojoMapper = loanhistoryEntityList -> {
		return loanhistoryEntityList.stream().map(loanhistoryEntity -> {
			return LoanhistoryResponseBean.builder().loanco(loanhistoryEntity.getLoanhistoryCK().getLhistLoanco())
					.loannum(loanhistoryEntity.getLoanhistoryCK().getLhistLoannum())
//					.loanclosedate(Objects.nonNull(loanhistoryEntity.getLoanhistoryCK().getLhistLoanclosedate())
//							? loanhistoryEntity.getLoanhistoryCK().getLhistLoanclosedate()
//									.format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//							: null)
					
					.loanclosedate(Objects.nonNull(loanhistoryEntity.getLoanhistoryCK().getLhistLoanclosedate())
							? loanhistoryEntity.getLoanhistoryCK().getLhistLoanclosedate()
									.toString()
							: null)
					
					.ownerid(loanhistoryEntity.getLoanhistoryCK().getLhistOwnerid())
					.loanamt(loanhistoryEntity.getLhistLoanamt()).loanbranch(loanhistoryEntity.getLhistLoanbranch())
					.loanpaid(loanhistoryEntity.getLhistLoanpaid())
					
//					.nocdt(Objects.nonNull(loanhistoryEntity.getLhistNocdt())
//							? loanhistoryEntity.getLhistNocdt().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
//							: null)
					
					.nocdt(Objects.nonNull(loanhistoryEntity.getLhistNocdt())
							? loanhistoryEntity.getLhistNocdt().toString()
							: null)
					
//					.nocrcvddate(Objects.nonNull(loanhistoryEntity.getLhistNocrcvddate()) ? loanhistoryEntity
//							.getLhistNocrcvddate().format(CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					
					.nocrcvddate(Objects.nonNull(loanhistoryEntity.getLhistNocrcvddate()) ? loanhistoryEntity
							.getLhistNocrcvddate().toString() : null)
					
					.noctype(loanhistoryEntity.getLhistNoctype()).origsite(loanhistoryEntity.getLhistOrigsite())
					.site(loanhistoryEntity.getLhistSite()).today(loanhistoryEntity.getLhistToday())
					.userid(loanhistoryEntity.getLhistUserid()).build();
		}).collect(Collectors.toList());

	};

	public static Function<List<LoanhistoryRequestBean>, List<Loanhistory>> addLoanhistoryPojoEntityMapper = loanhistoryRequestBeanList -> {
		return loanhistoryRequestBeanList.stream().map(loanhistoryRequestBean -> {
				return Loanhistory.builder()
				.loanhistoryCK(LoanhistoryCK.builder()
					// .lhistLoanco(null)
					.lhistLoanco(loanhistoryRequestBean.getLoanco()).lhistLoannum(loanhistoryRequestBean.getLoannum())
					.lhistLoanclosedate(Objects.nonNull(loanhistoryRequestBean.getLoanclosedate())
							? LocalDate.parse(loanhistoryRequestBean.getLoanclosedate(),
									CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					.lhistOwnerid(loanhistoryRequestBean.getOwnerid()).build())

				.lhistLoanamt(
							Objects.nonNull(loanhistoryRequestBean.getLoanamt()) ? loanhistoryRequestBean.getLoanamt()
									: BigInteger.ZERO.doubleValue())
					.lhistLoanbranch(loanhistoryRequestBean.getLoanbranch())
					.lhistLoanpaid(
							Objects.nonNull(loanhistoryRequestBean.getLoanpaid()) ? loanhistoryRequestBean.getLoanpaid()
									: BigInteger.ZERO.doubleValue())
					.lhistNocdt(Objects.nonNull(loanhistoryRequestBean.getNocdt()) ? LocalDate.parse(
							loanhistoryRequestBean.getNocdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER) : null)
					.lhistNocrcvddate(Objects.nonNull(loanhistoryRequestBean.getNocrcvddate())
							? LocalDate.parse(loanhistoryRequestBean.getNocrcvddate(),
									CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
							: null)
					.lhistNoctype(loanhistoryRequestBean.getNoctype())
					.lhistOrigsite(GenericAuditContextHolder.getContext().getSite())
					.lhistSite(GenericAuditContextHolder.getContext().getSite()).lhistToday(LocalDateTime.now())
					.lhistUserid(GenericAuditContextHolder.getContext().getUserid())

				.build();
		}).collect(Collectors.toList());
	};

	public static BiFunction<Loanhistory, LoanhistoryRequestBean, Loanhistory> updateLoanhistoryEntityPojoMapper = (
			loanhistoryEntity, loanhistoryRequestBean) -> {
		loanhistoryEntity.getLoanhistoryCK().setLhistLoanco(
				Objects.nonNull(loanhistoryRequestBean.getLoanco()) ? loanhistoryRequestBean.getLoanco().trim()
						: loanhistoryEntity.getLoanhistoryCK().getLhistLoanco());
		loanhistoryEntity.getLoanhistoryCK()
				.setLhistLoannum(Objects.nonNull(loanhistoryRequestBean.getLoannum())
						? loanhistoryRequestBean.getLoannum().trim()
						: loanhistoryEntity.getLoanhistoryCK().getLhistLoannum());
		loanhistoryEntity.getLoanhistoryCK()
				.setLhistLoanclosedate(Objects.nonNull(loanhistoryRequestBean.getLoanclosedate())
						? LocalDate.parse(loanhistoryRequestBean.getLoanclosedate(),
								CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: loanhistoryEntity.getLoanhistoryCK().getLhistLoanclosedate());

		loanhistoryEntity.getLoanhistoryCK()
				.setLhistOwnerid(Objects.nonNull(loanhistoryRequestBean.getOwnerid())
						? loanhistoryRequestBean.getOwnerid().trim()
						: loanhistoryEntity.getLoanhistoryCK().getLhistOwnerid());

		loanhistoryEntity.setLhistLoanamt(
				Objects.nonNull(loanhistoryRequestBean.getLoanamt()) ? loanhistoryRequestBean.getLoanamt()
						: loanhistoryEntity.getLhistLoanamt());
		loanhistoryEntity.setLhistLoanbranch(
				Objects.nonNull(loanhistoryRequestBean.getLoanbranch()) ? loanhistoryRequestBean.getLoanbranch().trim()
						: loanhistoryEntity.getLhistLoanbranch());
		loanhistoryEntity.setLhistLoanpaid(
				Objects.nonNull(loanhistoryRequestBean.getLoanpaid()) ? loanhistoryRequestBean.getLoanpaid()
						: loanhistoryEntity.getLhistLoanpaid());

		loanhistoryEntity.setLhistNocdt(Objects.nonNull(loanhistoryRequestBean.getNocdt())
				? LocalDate.parse(loanhistoryRequestBean.getNocdt(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
				: loanhistoryEntity.getLhistNocdt());

		loanhistoryEntity
				.setLhistNocrcvddate(Objects.nonNull(loanhistoryRequestBean.getNocrcvddate())
						? LocalDate.parse(loanhistoryRequestBean.getNocrcvddate(),
								CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER)
						: loanhistoryEntity.getLhistNocrcvddate());

		loanhistoryEntity.setLhistNoctype(
				Objects.nonNull(loanhistoryRequestBean.getNoctype()) ? loanhistoryRequestBean.getNoctype().trim()
						: loanhistoryEntity.getLhistNoctype());
		loanhistoryEntity.setLhistOrigsite(GenericAuditContextHolder.getContext().getSite());
		loanhistoryEntity.setLhistSite(GenericAuditContextHolder.getContext().getSite());
		loanhistoryEntity.setLhistToday(LocalDateTime.now());
		loanhistoryEntity.setLhistUserid(GenericAuditContextHolder.getContext().getUserid());

		return loanhistoryEntity;
	};

}
