package kraheja.commons.service.impl;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kraheja.commons.entity.Menu;
import kraheja.commons.entity.Transactionlog;
import kraheja.commons.entity.TransactionlogCK;
import kraheja.commons.filter.AuditMasterConfigCache;
import kraheja.commons.filter.GenericAuditDto;
import kraheja.commons.repository.MenuRepository;
import kraheja.commons.repository.TransactionlogRepository;
import kraheja.commons.service.AuditService;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.commons.utils.ValueContainer;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

	private static final Logger logger = LoggerFactory.getLogger(AuditServiceImpl.class);

	@Autowired
	MenuRepository menuRepository;
	
	@Autowired
	TransactionlogRepository transactionlogRepository;
	
	@Autowired
	AuditMasterConfigCache auditMasterConfigCache;

	@Override
	public void audit(GenericAuditDto genericAuditDto) {
		
		Menu menuEntity = auditMasterConfigCache.fetchCachedTables(genericAuditDto.getMenucode());
		logger.info(" menuEntity :: {}", menuEntity);
		
		ValueContainer<String> transactionMode = new ValueContainer<>();
		
		if (genericAuditDto.getUri().equals(menuEntity.getAddUri())){
			transactionMode.setValue("N");
		}
		else if (genericAuditDto.getUri().equals(menuEntity.getUpdateUri())){
			transactionMode.setValue("E");
		}
		else if (genericAuditDto.getUri().equals(menuEntity.getDeleteUri())){
			transactionMode.setValue("D");
		}
		
		if(CollectionUtils.isNotEmpty(genericAuditDto.getTransactionList())) {
			genericAuditDto.getTransactionList().stream().map(transactionNo -> {
				this.transactionlogRepository.save(Transactionlog.builder()
						.tlogFormname(menuEntity.getFormname())
						.tlogNeumonic(menuEntity.getNeumonic())
						.tlogOrigsite(genericAuditDto.getSite())
						.tlogSite(genericAuditDto.getSite())
						.transactionlogCK(TransactionlogCK.builder().tlogSrno(Double.valueOf(GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#TLOG"))).build())
						.tlogTables(menuEntity.getTableNames())
						.tlogToday(LocalDateTime.now())
						.tlogTransactionmode(transactionMode.getValue())
						.tlogTransactionno(transactionNo)
						.tlogUserid(genericAuditDto.getUserid())
						.build());
				return transactionNo;
			}).collect(Collectors.toList());
		}else {
		this.transactionlogRepository.save(Transactionlog.builder()
				.tlogFormname(menuEntity.getFormname())
				.tlogNeumonic(menuEntity.getNeumonic())
				.tlogOrigsite(genericAuditDto.getSite())
				.tlogSite(genericAuditDto.getSite())
				.transactionlogCK(TransactionlogCK.builder().tlogSrno(Double.valueOf(GenericCounterIncrementLogicUtil.generateTranNo("#TSER", "#TLOG"))).build())
				.tlogTables(menuEntity.getTableNames())
				.tlogToday(LocalDateTime.now())
				.tlogTransactionmode(transactionMode.getValue())
				.tlogTransactionno(genericAuditDto.getTransactionNo())
				.tlogUserid(genericAuditDto.getUserid())
				.build());
		}
		}
}
