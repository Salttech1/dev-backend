package kraheja.adminexp.overheads.dataentry.service.serviceimpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.adminexp.overheads.dataentry.bean.OverheadconsDetailsBean;
import kraheja.adminexp.overheads.dataentry.bean.request.OverheadconsRequestBean;
import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.Overheadmeter;
import kraheja.adminexp.overheads.dataentry.mappers.OverheadconsEntityPojoMapper;
import kraheja.adminexp.overheads.dataentry.mappers.OverheadmeterEntityPojoMapper;
import kraheja.adminexp.overheads.dataentry.repository.OverheadconsRepository;
import kraheja.adminexp.overheads.dataentry.repository.OverheadmeterRepository;
import kraheja.adminexp.overheads.dataentry.service.OverheadconsService;
import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.repository.EntityRepository;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;


@Service
@Transactional
public class OverheadconsServiceImpl implements OverheadconsService {

	private static final Logger logger = LoggerFactory.getLogger(OverheadconsServiceImpl.class);
	static String StrPriStatus="";

	@Autowired
	private OverheadconsRepository overheadconsRepository;

	@Autowired
	private OverheadmeterRepository overheadmeterRepository; 

	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public ResponseEntity<?> findByohdhconnocode(String ohdhconnocode)
	{
		
		
		Overheadcons overheadEntity=this.overheadconsRepository.findByOverheadconsCK_OhdhConnocode(ohdhconnocode.trim());
		logger.info("overheadEntity:: {}",overheadEntity);
		
		List<Overheadmeter> overheadmeterList=this.overheadmeterRepository.findByOverheadmeterCK_Ohmeconnocode(ohdhconnocode.trim());
		logger.info("overheadmeterList:: {}",overheadmeterList);
		
		if (overheadEntity!=null) {
			logger.info("Conn code ::{}",ohdhconnocode);
			StrPriStatus=Objects.nonNull(overheadEntity.getOhdhStatus())? overheadEntity.getOhdhStatus().toString():"";
			if(StrPriStatus.equalsIgnoreCase("H"))
			{
				return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This consumer No is Disconnected Cannot Modify/Enter Connection ").build());
			}
			else {
				return ResponseEntity.ok(ServiceResponseBean.builder().data(OverheadconsEntityPojoMapper.fetchOverheadconsEntityPojoMapper.apply(new Object[]
						{overheadEntity, overheadmeterList})).status(Boolean.TRUE).build());	
			}
			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Conn code : " + ohdhconnocode + " not found").build());
		
//		String StrLocOverheadtxnBillcount = "";
//		
//		StrLocOverheadtxnBillcount = CommonResultsetGenerator.queryToSingalValue(
//				"select count(*) from overheadtxn where  ohdd_connocode='"
//						.concat(ohdhconnocode.trim()));
		
		//if(StrLocOverheadtxnBillcount=="" || StrLocOverheadtxnBillcount=="0")
		//{
//			List<Overheadmeter> overheadmeterList=this.overheadmeterRepository.findByOverheadmeterCK_Ohmeconnocode(ohdhconnocode.trim());
//			logger.info("overheadmeterList:: {}",overheadmeterList);
//			
//			if (overheadEntity!=null) {
//				logger.info("Conn code ::{}",ohdhconnocode);
//				StrPriStatus=Objects.nonNull(overheadEntity.getOhdhStatus())? overheadEntity.getOhdhStatus().toString():"";
//				if(StrPriStatus.equalsIgnoreCase("H"))
//				{
//					return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("This consumer No is Disconnected Cannot Modify/Enter Connection ").build());
//				}
//				else {
//					return ResponseEntity.ok(ServiceResponseBean.builder().data(OverheadconsEntityPojoMapper.fetchOverheadconsEntityPojoMapper.apply(new Object[]
//							{overheadEntity, overheadmeterList})).status(Boolean.TRUE).build());	
//				}
//				
//			}
//			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Conn code : " + ohdhconnocode + " not found").build());
		//}
		//else {
			
		//	return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("There is bill in  Cannot Modify/Enter Connection ").build());
		//}
		
		
	}

	@Override
	public ResponseEntity<?> addOverheadcons(OverheadconsRequestBean overheadconsRequestBean) {
		// TODO Auto-generated method stub
		String conncode="";
		String StrEntID="01";
				
		String siteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);

		if (overheadconsRequestBean.getOhdhbilltype().trim().equals("E")){
			//conncode= GenericCounterIncrementLogicUtil.generateTranNo("OHDH", overheadconsRequestBean.getOhdhbilltype());
			StrEntID="01";
			conncode= GenericCounterIncrementLogicUtil.generateTranNo("OHDH", StrEntID);
		}
		else if (overheadconsRequestBean.getOhdhbilltype().trim().equals("W")){
			//conncode = GenericCounterIncrementLogicUtil.generateTranNo("OHDH", overheadconsRequestBean.getOhdhbilltype());
			StrEntID="03";
			conncode= GenericCounterIncrementLogicUtil.generateTranNo("OHDH", StrEntID);
		}
		else if (overheadconsRequestBean.getOhdhbilltype().trim().equals("G")){
			//conncode = GenericCounterIncrementLogicUtil.generateTranNo("OHDH", overheadconsRequestBean.getOhdhbilltype());
			StrEntID="04";
			conncode= GenericCounterIncrementLogicUtil.generateTranNo("OHDH", StrEntID);
		}
		else if (overheadconsRequestBean.getOhdhbilltype().trim().equals("T")){
			//conncode = GenericCounterIncrementLogicUtil.generateTranNo("OHDH", overheadconsRequestBean.getOhdhbilltype());
			StrEntID="02";
			conncode= GenericCounterIncrementLogicUtil.generateTranNo("OHDH", StrEntID);
		}
		this.overheadconsRepository.save(OverheadconsEntityPojoMapper.addOverheadconsEntityPojoMapper.apply(new Object[]
				{overheadconsRequestBean, siteFromDBEntity, conncode}));
		
		this.overheadmeterRepository.saveAll(OverheadmeterEntityPojoMapper.addOverheadmeterEntityPojoMapper.apply(new Object[]
				{overheadconsRequestBean.getOverheadmeterRequestBeanList(), conncode}));
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("Conn code : " + conncode + " Added Successfully").build());
		
	}

	@Override
	public ResponseEntity<?> updateOverheadcons(OverheadconsRequestBean overheadconsRequestBean) {
		// TODO Auto-generated method stub								
		Overheadcons overheadconsEntity  =  this.overheadconsRepository.findByOverheadconsCK_OhdhConnocode(overheadconsRequestBean.getOhdhconnocode());
		logger.info("overheadEntity:: {}",overheadconsEntity);
		String ohdhconnocode = overheadconsRequestBean.getOhdhconnocode().trim();

		if (overheadconsEntity!=null) {
			logger.info("Conn code ::{}",ohdhconnocode);

			String SiteFromDBEntity = this.entityRepository.findByEntityCk_EntClassAndEntityCk_EntChar1(CommonConstraints.INSTANCE.ENTITY_SITE, CommonConstraints.INSTANCE.ENTITY_CHAR1);
			logger.info("Entity :: {}" , SiteFromDBEntity);

			List<Overheadmeter> overheadmeterEntityList = this.overheadmeterRepository.findByOverheadmeterCK_Ohmeconnocode(ohdhconnocode);
			logger.info("overheadMeter :: {}",overheadmeterEntityList);
			
			this.overheadconsRepository.save(OverheadconsEntityPojoMapper.updateOverheadconsEntityPojoMapper.apply(overheadconsEntity, overheadconsRequestBean));

				if(CollectionUtils.isNotEmpty(overheadmeterEntityList)  && Objects.nonNull(overheadconsRequestBean.getOverheadmeterRequestBeanList())) {
				overheadconsRequestBean.getOverheadmeterRequestBeanList().stream().map(overheadmeterRequestBean -> {
					overheadmeterEntityList.stream().filter(overheadmeterFilter -> {
						
						return overheadmeterRequestBean.getConnocode().equals(overheadmeterFilter.getOverheadmeterCK().getOhmeconnocode()) && 
								overheadmeterRequestBean.getInsertUpdateMode().equals("N");
					}).map(
							overheadmeterEntity -> {
								this.overheadmeterRepository.saveAll(OverheadmeterEntityPojoMapper.addOverheadmeterEntityPojoMapper.apply(new Object[]{Arrays.asList(overheadmeterRequestBean), overheadconsRequestBean.getOhdhconnocode()}));
								return overheadmeterEntity;
							}
							).collect(Collectors.toList());
					
					return overheadmeterRequestBean;
				}).collect(Collectors.toList());
				
						overheadconsRequestBean.getOverheadmeterRequestBeanList().stream()
						.filter(overheadmeterFilter -> {
							//return overheadmeterFilter.getInsertUpdateMode().equals("E");
							return overheadmeterFilter.getInsertUpdateMode().equals("E") && !overheadmeterFilter.getMeterno().equals(overheadmeterFilter.getMeternoOld());
						}).map(overheadmeterRequestBean -> {
							Query updateQuery = entityManager.createNativeQuery("update overheadmeter set ohme_meterno=:ohmeMeterNoNew, ohme_site=:site,\r\n"
									+ " ohme_userid=:userid, ohme_today=:today where trim(ohme_connocode)=:ohmeConnocode and \r\n"
									+ "trim(ohme_meterno)=:ohmeMeterNoOld "); // 
							updateQuery.setParameter("ohmeMeterNoNew", overheadmeterRequestBean.getMeterno());
							updateQuery.setParameter("userid","SANDY");
							updateQuery.setParameter("site", "MUM");
							updateQuery.setParameter("today",  LocalDateTime.now());
							updateQuery.setParameter("ohmeConnocode",  overheadmeterRequestBean.getConnocode().trim());
							updateQuery.setParameter("ohmeMeterNoOld",  overheadmeterRequestBean.getMeternoOld());
							Integer rowCount = updateQuery.executeUpdate();
							logger.info("Updated Row count :: {}", rowCount);
							return overheadmeterRequestBean;
						}).collect(Collectors.toList());
						
						overheadconsRequestBean.getOverheadmeterRequestBeanList().stream()
						.filter(overheadmeterFilter -> {
							return overheadmeterFilter.getInsertUpdateMode().equals("D") && overheadmeterFilter.getMeterno().equals(overheadmeterFilter.getMeternoOld());
						}).map(overheadmeterRequestBean -> {
							Query updateQuery = entityManager.createNativeQuery(" delete from overheadmeter \r\n"
									+ " where trim(ohme_connocode)=:ohmeConnocode and \r\n"
									+ "trim(ohme_meterno)=:ohmeMeterNo "); // 
							updateQuery.setParameter("ohmeMeterNo", overheadmeterRequestBean.getMeterno().trim());
							updateQuery.setParameter("ohmeConnocode",  overheadmeterRequestBean.getConnocode().trim());
							Integer rowCount = updateQuery.executeUpdate();
							logger.info("Updated Row count :: {}", rowCount);
							return overheadmeterRequestBean;
						}).collect(Collectors.toList());
			
				}
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("conno Code : " + ohdhconnocode + " Updated Successfully").build());
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Data Found").build());
	}

	@Override
	public ResponseEntity<?> checkConnocodeExists(String connocode) {
		// TODO Auto-generated method stub
		
		Overheadcons overheadconsEntity = this.overheadconsRepository
				.findByOverheadconsCK_OhdhConnocode(connocode);

		if (Objects.isNull(overheadconsEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("This Connection Code alreadly Exist.").build());

	}
	
	public ResponseEntity<?> fetchLocnamePaycoynameBillcoyname(String connocode) {
		// TODO Auto-generated method stub
 		List<Tuple> tuplesList = this.overheadconsRepository.fetchLocnamePaycoynameBillcoyname(connocode);
		
		//if(CollectionUtils.isNotEmpty(tuplesList)) {
			List<OverheadconsDetailsBean> overheadconsDetailsBeanList = 
					tuplesList.stream().map(t -> {return new OverheadconsDetailsBean(
	                t.get(0, String.class),
	                t.get(1, String.class),
	                t.get(2, String.class));
			}
	        ).collect(Collectors.toList());
			logger.info("overheadconsDetailsBeanList {} :: ", overheadconsDetailsBeanList);
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE)
				.data(overheadconsDetailsBeanList).build());
		
	}

	@Override
	public ResponseEntity<?> checkConsumnerNoExists(String consumerno) {

		Overheadcons overheadconsEntity = this.overheadconsRepository
				.findByOverheadconsCK_OhdhConsumerno(consumerno);

		if (Objects.isNull(overheadconsEntity))
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).build());
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE)
				.message("This Consumer No alreadly Exist.").build());
	}


}
