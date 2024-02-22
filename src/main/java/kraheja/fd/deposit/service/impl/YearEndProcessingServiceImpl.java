package kraheja.fd.deposit.service.impl;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.utils.CommonConstraints;
import kraheja.fd.deposit.service.YearEndProcessingService;

@Service
@Transactional
public class YearEndProcessingServiceImpl implements YearEndProcessingService {
	
	private static final Logger logger = LoggerFactory.getLogger(YearEndProcessingServiceImpl.class);
	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> intitaliseOrRecalculateYTD(String companyCode, String fromDate, String uptoDate) {
		Query updateDepositQuery = this.entityManager.createNativeQuery("update deposit " +
                " set dep_intpaidytd = ( select nvl(sum(din_interest),0) from depint " +
                " where din_coy = dep_coy and din_depositor = dep_depositor " +
                " and din_receiptnum = dep_receiptnum and din_intfrom >= :fromDate" +
                " and din_intupto <= :uptoDate), " +
                " dep_taxpaidytd = (select nvl(sum(din_tds),0) from depint " +
                " where din_coy = dep_coy and din_depositor = dep_depositor " +
                " and din_receiptnum = dep_receiptnum " +
                " and din_intfrom >= :fromDate and din_intupto <= :uptoDate)"
                + " where trim(dep_coy)= :dep_coy");
		System.out.println("From Date: " +fromDate);
		updateDepositQuery.setParameter("fromDate",LocalDate.parse(fromDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateDepositQuery.setParameter("uptoDate",LocalDate.parse(uptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateDepositQuery.setParameter("dep_coy",companyCode.trim());
		Integer depositCount = updateDepositQuery.executeUpdate();
		logger.info("Updated Row count Deposit For Intitalise-YTD :: {}", depositCount);
		
		Query updateDepositorQuery = this.entityManager.createNativeQuery(" update depositor"
				+" set deptr_intpaidytd = ( select nvl(sum(din_interest),0) from depint "
				+ " where din_coy = deptr_coy and din_depositor = deptr_depositor and "
				+ " din_intfrom >= :fromDate and din_intupto<= :uptoDate),"
				+ " deptr_taxpaidytd = (select nvl(sum(din_tds),0) from depint " 
				+ " where din_coy = deptr_coy "
				+ " and din_depositor = deptr_depositor and din_intfrom >= :fromDate"
				+ " and din_intupto <= :uptoDate) " 
				+ " where trim(deptr_coy) = :deptr_coy");
		updateDepositorQuery.setParameter("fromDate",LocalDate.parse(fromDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateDepositorQuery.setParameter("uptoDate",LocalDate.parse(uptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateDepositorQuery.setParameter("deptr_coy",companyCode.trim());
		Integer depositorCount = updateDepositorQuery.executeUpdate();
		logger.info("Updated Row count Depositor For Intitalise-YTD :: {}", depositorCount);
		
		GenericAuditContextHolder.getContext().setTransactionNo(CommonConstraints.INSTANCE.SPACE_STRING);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Records updated successfully").build());	
	}

	@Override
	public ResponseEntity<?> recalculateBrokerYTD(String companyCode, String fromDate, String uptoDate) {
		Query updateBrokerQuery = this.entityManager.createNativeQuery("update broker " +
                " set brok_brokthisyr = ( select    nvl(sum(dep_brokerage),0) from deposit " +
                " where dep_broker = brok_code and trunc(dep_depdate) >= :fromDate  " +
                " and trunc(dep_depdate) <= :uptoDate), " +
                " brok_tdsthisyr = (select nvl(sum(dep_broktds),0) from deposit " +
                " where dep_broker = brok_code and trunc(dep_depdate) >= :fromDate " + 
                " and trunc(dep_depdate) <= :uptoDate) " +
                " where substr(brok_code,1,4) = :coy ");
		updateBrokerQuery.setParameter("fromDate", LocalDate.parse(fromDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateBrokerQuery.setParameter("uptoDate",LocalDate.parse(uptoDate, CommonConstraints.INSTANCE.DDMMYYYY_FORMATTER));
		updateBrokerQuery.setParameter("coy", companyCode.trim());
		Integer depositCount = updateBrokerQuery.executeUpdate();
		logger.info("Updated Row count Broker Recalculate-YTD :: {}", depositCount);
		
		GenericAuditContextHolder.getContext().setTransactionNo(CommonConstraints.INSTANCE.SPACE_STRING);
		GenericAuditContextHolder.getContext().setAuditable(Boolean.TRUE);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Records updated successfully").build());	
	}

}
