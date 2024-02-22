package kraheja.enggsys.certificatesystem.dataentry.mappers;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.enggsys.bean.request.ContractdebitRequestBean;
import kraheja.enggsys.bean.response.ContractdebitResponseBean;
import kraheja.enggsys.entity.Contractdebit;
import kraheja.enggsys.entity.Contractdebit.ContractdebitCK;

public class ContractdebitMapper {
	@SuppressWarnings("unchecked")
	public static Function	<List<Contractdebit>, List<ContractdebitResponseBean>> fetchContractdebitEntityPojoMapper = contractdebitEntityList -> {
		return contractdebitEntityList.stream().map(contractdebitEntity -> {
			return ContractdebitResponseBean.builder()
					.debitno(Objects.nonNull(contractdebitEntity.getContractdebitCK().getCcdDebitno()) ? contractdebitEntity.getContractdebitCK().getCcdDebitno().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.amountadj(contractdebitEntity.getCcdAmountAdj())
					.authnum(Objects.nonNull(contractdebitEntity.getCcdAuthnum()) ? contractdebitEntity.getCcdAuthnum().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.bldgcode(Objects.nonNull(contractdebitEntity.getCcdBldgcode()) ? contractdebitEntity.getCcdBldgcode().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.certtype(Objects.nonNull(contractdebitEntity.getCcdCerttype()) ? contractdebitEntity.getCcdCerttype().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.contract(Objects.nonNull(contractdebitEntity.getCcdContract()) ?  contractdebitEntity.getCcdContract().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.contracontract(Objects.nonNull(contractdebitEntity.getCcdContraContract()) ? contractdebitEntity.getCcdContraContract().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.debittype(Objects.nonNull(contractdebitEntity.getCcdDebittype()) ? contractdebitEntity.getCcdDebittype().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.debitamount(contractdebitEntity.getCcdDebitAmount())
					.remarks(Objects.nonNull(contractdebitEntity.getCcdRemarks()) ? contractdebitEntity.getCcdRemarks().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.runser(Objects.nonNull(contractdebitEntity.getCcdRunser()) ? contractdebitEntity.getCcdRunser().trim() : CommonConstraints.INSTANCE.BLANK_STRING)
					.site(contractdebitEntity.getCcdSite())
					.today(contractdebitEntity.getCcdToday())
					.userid(contractdebitEntity.getCcdUserid())
					.build(); 
		}).collect(Collectors.toList());

	};

	public static Function<ContractdebitRequestBean, Contractdebit> addContractdebitPojoEntityMapper = contractdebitRequestBean -> {
		return Contractdebit.builder()
				.contractdebitCK(ContractdebitCK.builder().ccdDebitno(contractdebitRequestBean.getDebitno()).build())
				.ccdAmountAdj(Objects.nonNull(contractdebitRequestBean.getAmountadj())
						? contractdebitRequestBean.getAmountadj()
						: BigInteger.ZERO.doubleValue())
				.ccdAuthnum(contractdebitRequestBean.getAuthnum()).ccdBldgcode(contractdebitRequestBean.getBldgcode())
				.ccdCerttype(contractdebitRequestBean.getCerttype()).ccdContract(contractdebitRequestBean.getContract())
				.ccdContraContract(contractdebitRequestBean.getContracontract())
				.ccdDebittype(contractdebitRequestBean.getDebittype())
				.ccdDebitAmount(Objects.nonNull(contractdebitRequestBean.getDebitamount())
						? contractdebitRequestBean.getDebitamount()
						: BigInteger.ZERO.doubleValue())
				.ccdRemarks(contractdebitRequestBean.getRemarks()).ccdRunser(contractdebitRequestBean.getRunser())
				.ccdSite(GenericAuditContextHolder.getContext().getSite()).ccdToday(LocalDateTime.now())
				.ccdUserid(GenericAuditContextHolder.getContext().getUserid())
				.build();
	};
	
	public static BiFunction<Contractdebit, ContractdebitRequestBean, Contractdebit> updateContractdebitEntityPojoMapper = (contractdebitEntity, contractdebitRequestBean) -> {
		contractdebitEntity.getContractdebitCK().setCcdDebitno(Objects.nonNull(contractdebitRequestBean.getDebitno()) ? contractdebitRequestBean.getDebitno().trim() : contractdebitEntity.getContractdebitCK().getCcdDebitno());
		contractdebitEntity.setCcdAmountAdj(Objects.nonNull(contractdebitRequestBean.getAmountadj()) ? contractdebitRequestBean.getAmountadj() : contractdebitEntity.getCcdAmountAdj());
		contractdebitEntity.setCcdAuthnum(Objects.nonNull(contractdebitRequestBean.getAuthnum()) ? contractdebitRequestBean.getAuthnum().trim() : contractdebitEntity.getCcdAuthnum());
		contractdebitEntity.setCcdBldgcode(Objects.nonNull(contractdebitRequestBean.getBldgcode()) ? contractdebitRequestBean.getBldgcode().trim() : contractdebitEntity.getCcdBldgcode());
		contractdebitEntity.setCcdCerttype(Objects.nonNull(contractdebitRequestBean.getCerttype()) ? contractdebitRequestBean.getCerttype().trim() : contractdebitEntity.getCcdCerttype());
		contractdebitEntity.setCcdContract(Objects.nonNull(contractdebitRequestBean.getContract()) ? contractdebitRequestBean.getContract().trim() : contractdebitEntity.getCcdContract());
		contractdebitEntity.setCcdContraContract(Objects.nonNull(contractdebitRequestBean.getContracontract()) ? contractdebitRequestBean.getContracontract().trim() : contractdebitEntity.getCcdContraContract());
		contractdebitEntity.setCcdDebittype(Objects.nonNull(contractdebitRequestBean.getDebittype()) ? contractdebitRequestBean.getDebittype().trim() : contractdebitEntity.getCcdDebittype());
		contractdebitEntity.setCcdDebitAmount(Objects.nonNull(contractdebitRequestBean.getDebitamount()) ? contractdebitRequestBean.getDebitamount() : contractdebitEntity.getCcdDebitAmount());
		contractdebitEntity.setCcdRemarks(Objects.nonNull(contractdebitRequestBean.getRemarks()) ? contractdebitRequestBean.getRemarks().trim() : contractdebitEntity.getCcdRemarks());
		contractdebitEntity.setCcdRunser(Objects.nonNull(contractdebitRequestBean.getRunser()) ? contractdebitRequestBean.getRunser().trim() : contractdebitEntity.getCcdRunser());
		contractdebitEntity.setCcdSite(GenericAuditContextHolder.getContext().getSite()) ; 
		contractdebitEntity.setCcdToday(LocalDateTime.now()) ; 
		contractdebitEntity.setCcdUserid(GenericAuditContextHolder.getContext().getUserid()) ; 
		return contractdebitEntity;
	};
	


}
