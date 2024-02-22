package kraheja.fd.deposit.service.impl;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.Inchq;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.ActranhRepository;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.fd.deposit.bean.DepintBean;
import kraheja.fd.deposit.bean.request.DepintRequestBean;
import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.DepintCK;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.mappers.FdEntityPojoMapper;
import kraheja.fd.deposit.repository.DepintRepository;
import kraheja.fd.deposit.repository.DepositRepository;
import kraheja.fd.deposit.repository.DepositorRepository;
import kraheja.fd.deposit.service.DepIntService;
import kraheja.fd.deposit.utils.InterestCalculationLogic;
//fixes
@Service
@Transactional
public class DepIntServiceImpl implements DepIntService {

	private static final Logger logger = LoggerFactory.getLogger(DepIntServiceImpl.class);

	@Autowired
	private DepintRepository depintRepository;

	@Autowired
	private DepositorRepository depositorRepository;

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private  EntityRepository entityRepository;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ActranhRepository actranhRepository;

	@Override
	public ResponseEntity<ServiceResponseBean> fetchChequeByInterestFromDateAndInterestUpToDate(String intFrom, String intUpTo, String coy) {
		List<Depint> depIntEntityList = this.depintRepository.findByDepintCK_DinIntfromGreaterThanEqualAndDepintCK_DinIntuptoAndDepintCK_DinCoyAndDinSessidIsNotNull(LocalDate.parse(intFrom, CommonConstraints.INSTANCE.DDMMMYYYY_FORMATTER).atStartOfDay().toLocalDate(), LocalDate.parse(intUpTo, CommonConstraints.INSTANCE.DDMMMYYYY_FORMATTER).atStartOfDay().toLocalDate(), coy.trim());
		logger.info("DepositEntity :: {}", depIntEntityList);

		/* MAP for depositor ID -> depositor Name*/
		Map<String, String> depositorNameMap = this.depositorRepository.findByDepositorCK_DeptrCoy(coy).stream().collect(Collectors.toMap(depositor -> depositor.getDepositorCK().getDeptrDepositor(), Depositor :: getDeptrName));
		logger.info("Depositor Name Map :: {}", depositorNameMap);

		if(CollectionUtils.isNotEmpty(depIntEntityList)) {
			depIntEntityList = depIntEntityList.stream().sorted(Comparator.comparing(depIntEntity -> depIntEntity.getDepintCK().getDinDepositor())).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().data(FdEntityPojoMapper.fetchDepintEntityPojoMapper.apply(new Object[] {depIntEntityList, depositorNameMap})).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> updateBankCodeAndChequeNumber(DepintRequestBean depintRequestBean) {
		if(CollectionUtils.isNotEmpty(depintRequestBean.getDepintBeanList())) {

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);


			depintRequestBean.getDepintBeanList().stream().map(depint ->{
				String oldCheckNumCond = CommonConstraints.INSTANCE.BLANK_STRING;
				if(StringUtils.isNotBlank(depint.getOldChequeNum()))
					oldCheckNumCond = "=:din_oldchqnum";
				else
					oldCheckNumCond = "IS NULL";
				Query updateQuery = entityManager.createNativeQuery("update depint set din_chqnum=:din_chqnum, din_bankcode=:din_bankcode, DIN_SITE=:din_site, DIN_TODAY=:din_today, DIN_USERID=:din_userid where trim(din_depositor)=:din_depositor and trim(din_receiptnum)=:din_receiptnum and din_intfrom=:din_intfrom and din_intupto=:din_intupto and trim(din_coy)=:din_coy and trim(din_chqnum)" + oldCheckNumCond); // 
				updateQuery.setParameter("din_chqnum", depint.getChqnum().trim());
				updateQuery.setParameter("din_bankcode", depint.getBankcode().trim());
				updateQuery.setParameter("din_site", SiteFromDBEntity.trim());
				updateQuery.setParameter("din_today",  LocalDateTime.now());
				updateQuery.setParameter("din_userid",  depintRequestBean.getUserid());
				updateQuery.setParameter("din_depositor", depint.getDepositor().trim());
				updateQuery.setParameter("din_receiptnum", depint.getReceiptnum().trim());
				updateQuery.setParameter("din_intfrom", LocalDate.parse(depint.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				updateQuery.setParameter("din_intupto", LocalDate.parse(depint.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
				updateQuery.setParameter("din_coy", depint.getCoy().trim());
				if(StringUtils.isNotBlank(depint.getOldChequeNum()))
					updateQuery.setParameter("din_oldchqnum", depint.getOldChequeNum().trim());
				Integer rowCount = updateQuery.executeUpdate();
				logger.info("Updated Row count :: {}", rowCount);
				return depint;
			}).collect(Collectors.toList());

			GenericAuditContextHolder.getContext().setTransactionNo(CommonConstraints.INSTANCE.SPACE_STRING);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Cheque details updated successfully").build());	
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Please check your selection...").build());	
	}

	@Override
	public ResponseEntity<ServiceResponseBean> fetchChequeByDepositorAndReceiptNumAndCoy(String depositorId,
			String receiptNum, String coy) {
		List<Depint> depIntEntityList = this.depintRepository.findByDepintCK_DinDepositorAndDepintCK_DinReceiptnumAndDepintCK_DinCoy(depositorId.trim(), receiptNum.trim(), coy.trim());
		logger.info("DepositEntity :: {}", depIntEntityList);

		/* MAP for depositor ID -> depositor Name*/
		Map<String, String> depositorNameMap = this.depositorRepository.findByDepositorCK_DeptrCoy(coy).stream().collect(Collectors.toMap(depositor -> depositor.getDepositorCK().getDeptrDepositor(), Depositor :: getDeptrName));
		logger.info("Depositor Name Map :: {}", depositorNameMap);

		if(CollectionUtils.isNotEmpty(depIntEntityList)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().data(FdEntityPojoMapper.fetchDepintEntityPojoMapper.apply(new Object[] {depIntEntityList, depositorNameMap})).status(Boolean.TRUE).build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<ServiceResponseBean> updateChequeByDepositorAndReceiptNumAndCoy(DepintRequestBean depintRequestBean) {
		if(CollectionUtils.isNotEmpty(depintRequestBean.getDepintBeanList())) {
			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);

			Deposit  depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum( depintRequestBean.getDepositorId().trim(), depintRequestBean.getCoy().trim(), depintRequestBean.getReceiptNum().trim()); 
			logger.info("depositEntity :: {}", depositEntity);
			int index = -1;
			for(DepintBean depintBean: depintRequestBean.getDepintBeanList()) {
				index++;
				Actranh actranhEntity = actranhRepository.findByActranhCK_ActhTranser(depintBean.getTranser().trim());
				logger.info("actranhEntity :: {}", actranhEntity);
				if(!depintBean.getTranser().trim().startsWith("D")) {
					if(Objects.nonNull(actranhEntity)) {
						if(!actranhEntity.getActhPartycode().trim().equals(depintRequestBean.getCoy().trim()
								.concat(depintRequestBean.getDepositorId().trim()))) {
							return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(index).message("Invalid Transer ".concat(depintBean.getTranser().trim())).build());
						}
					}else
						return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).data(index).message("Invalid Transer ".concat(depintBean.getTranser().trim())).build());
					}
				}
			depintRequestBean.getDepintBeanList().stream().map(depint ->{
				if(!depint.getIsAdd()) {
					Query updateQuery = entityManager.createNativeQuery("update depint set din_interest=:din_interest, din_tds=:din_tds, din_chqnum=:din_chqnum, din_transer=:din_transer, DIN_SITE=:din_site, DIN_TODAY=:din_today, DIN_USERID=:din_userid where trim(din_depositor)=:din_depositor and trim(din_receiptnum)=:din_receiptnum and din_intfrom=:din_intfrom and din_intupto=:din_intupto and trim(din_coy)=:din_coy and trim(din_chqnum)=:din_oldchqnum"); // 
					updateQuery.setParameter("din_interest", depint.getInterest());
					updateQuery.setParameter("din_tds", depint.getTds());
					updateQuery.setParameter("din_chqnum", depint.getChqnum());
					updateQuery.setParameter("din_transer", depint.getTranser());
					updateQuery.setParameter("din_site", GenericAuditContextHolder.getContext().getSite());
					updateQuery.setParameter("din_today",  LocalDateTime.now());
					updateQuery.setParameter("din_userid",  GenericAuditContextHolder.getContext().getUserid());
					updateQuery.setParameter("din_depositor", depintRequestBean.getDepositorId().trim());
					updateQuery.setParameter("din_receiptnum", depintRequestBean.getReceiptNum().trim());
					updateQuery.setParameter("din_intfrom", LocalDate.parse(depint.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					updateQuery.setParameter("din_intupto", LocalDate.parse(depint.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
					updateQuery.setParameter("din_coy", depintRequestBean.getCoy().trim());
					updateQuery.setParameter("din_oldchqnum", depint.getOldChequeNum());

					Integer rowCount = updateQuery.executeUpdate();
					logger.info("Updated Row count :: {}", rowCount);
				}
				if(depint.getIsAdd()) {
					this.depintRepository.save(Depint.builder()
							.depintCK(DepintCK.builder()
									.dinDepositor(depintRequestBean.getDepositorId())
									.dinCoy(depintRequestBean.getCoy())
									.dinChqnum(depint.getChqnum())
									.dinIntfrom(LocalDate.parse(depint.getIntfrom(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.dinIntupto(LocalDate.parse(depint.getIntupto(), CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER))
									.dinReceiptnum(depintRequestBean.getReceiptNum())
									.build())
							.dinToday(LocalDateTime.now())
							.dinSite(GenericAuditContextHolder.getContext().getSite())
							.dinOrigsite(GenericAuditContextHolder.getContext().getSite())
							.dinUserid(GenericAuditContextHolder.getContext().getUserid())
							.dinInterest(depint.getInterest())
							.dinProprietor(depositEntity.getDepProprietor())
							.dinTds(depint.getTds())
							.dinTranser(depint.getTranser())
							.dinFromdate(Objects.nonNull(depositEntity.getDepDepdate()) ? depositEntity.getDepDepdate().toLocalDate() : null)
							.dinTodate(Objects.nonNull(depositEntity.getDepMatdate()) ? depositEntity.getDepMatdate().toLocalDate() : null)
							.build());
				}
				
				return depint;
			}).collect(Collectors.toList());

			GenericAuditContextHolder.getContext().setTransactionNo(CommonConstraints.INSTANCE.SPACE_STRING);
			GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);

			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Data saved  successfully").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Please check your selection...").build());
	}


	@Override
	public ResponseEntity<ServiceResponseBean> fetchInterestByDepositorAndReceiptNumAndCoy(String depositorId, String receiptNum, String coy, String fromdate, String todate) {
		Deposit  depositEntity = this.depositRepository.findByDepositorIdAndCompanyCodeAndReceiptNum(depositorId.trim(), coy.trim(), receiptNum.trim()); 
		logger.info("depositEntity :: {}", depositEntity);
		
		Depositor  depositorEntity = this.depositorRepository.findByDepositorIdAndCompanyCode(depositorId.trim(), coy.trim()); 
		logger.info("depositEntity :: {}", depositorEntity);

		Long interest = InterestCalculationLogic.calculateInterest(Double.valueOf(depositEntity.getDepDepamount()), fromdate, todate, depositEntity.getDepIntrate	(), depositEntity.getDepIntfreq().intValue());
		logger.info("Interest :: {}", interest);
		
		String tdsLimitAndRate = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates("TDS", "INSEC", depositEntity.getDepMatdate().toLocalDate());
		Double tdsLimit = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[0]);
		Double tdsRate = Double.valueOf(tdsLimitAndRate.split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);


		Long interestForStaff = BigInteger.ZERO.longValue();
		Double decimalTdsAmount = BigInteger.ZERO.doubleValue();
		Double staffIntRate = BigInteger.ZERO.doubleValue();
		long roundTds = BigInteger.ZERO.longValue();

		if(depositEntity.getDepStaffyn().equals("Y")) {
			staffIntRate = Double.valueOf(this.entityRepository.findByNumsEntityCk_EntClassAndEntityCk_EntId("FDINT", "FDINT").split(CommonConstraints.INSTANCE.COMMA_STRING)[1]);
			interestForStaff = InterestCalculationLogic.calculateInterestForStaff(Double.valueOf(depositEntity.getDepDepamount()), fromdate, todate, depositEntity.getDepIntrate(), depositEntity.getDepStaffyn(), staffIntRate);
			logger.info("Interest :: {}", interest);
		}
		
		if(depositorEntity.getDeptrTds15hyn().equals("N") && depositorEntity.getDeptrTds15gyn().equals("N")) {
			if((depositorEntity.getDeptrIntpaidytd() + interest + interestForStaff) > tdsLimit) {
				decimalTdsAmount = ((interest + interestForStaff) / 100D) * tdsRate;
				 roundTds = Math.round(decimalTdsAmount);
			}
		}
		
		Map<String, Long> responseMap = new HashMap();
		responseMap.put("interest", interest + interestForStaff); 
		responseMap.put("tds", roundTds); 
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(responseMap).build());
	}
}