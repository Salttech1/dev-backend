package kraheja.payroll.computationofsalary.service.impl;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.apache.poi.hpsf.Decimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.filter.GenericAuditContextHolder;
import kraheja.commons.repository.EntityRepository;
import kraheja.fd.deposit.entity.Tdepint;
import kraheja.payroll.bean.EmppaymonthBean;
import kraheja.payroll.bean.SalaryInitCheckInputBean;
import kraheja.payroll.bean.SalaryInitCompletedBean;
import kraheja.payroll.bean.request.EmppaymonthRequestBean;
import kraheja.payroll.bean.response.GetPackageforEmployeeBean;
import kraheja.payroll.computationofsalary.mappers.EmppaymonthEntityPojoMapper;
import kraheja.payroll.computationofsalary.service.SalaryInitialisationService;
import kraheja.payroll.entity.Emppaymonth;
import kraheja.payroll.masterdetail.mappers.EmpassetinfoEntityPojoMapper;
import kraheja.payroll.repository.EmppaymonthRepository;
import kraheja.payroll.repository.SalaryInitialisationRepository;
import kraheja.payroll.salarycomputation.service.impl.SalaryComputationServiceImpl;
import kraheja.commons.utils.CommonConstraints;
import kraheja.commons.utils.CommonUtils;
import kraheja.commons.utils.ValueContainer;

@Service
@Transactional
public class SalaryInitialisationServiceImpl implements SalaryInitialisationService {
	
	@Autowired
	private SalaryInitialisationRepository salaryInitialisationRepository;

	@Autowired
	private  EntityRepository entityRepository;
	
	@Autowired
	private EmppaymonthRepository emppaymonthRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(SalaryInitialisationServiceImpl.class);
	
	@Override
	public ResponseEntity<?> GetCheckInputDetails(String coyCode, String salarytype) {
		List<String> coyCodes = Arrays.asList(coyCode.split(","));
		List<String> salarytypes = Arrays.asList(salarytype.split(","));
		
		List<Tuple> tInputDetails =  this.salaryInitialisationRepository.GetCheckInputDetails(coyCodes,salarytypes);
		if(tInputDetails.size()>0) {
			List<SalaryInitCheckInputBean> salaryInitCheckInputBean = 
					tInputDetails.stream().map(t -> {return new SalaryInitCheckInputBean(
			                t.get(0, String.class),
			                t.get(1, String.class),
			                t.get(2, String.class),
			                t.get(3, String.class),
			                t.get(4, String.class),
			                t.get(5, Character.class),
			                t.get(6, BigDecimal.class),
			                t.get(7, String.class));
					}
							).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(salaryInitCheckInputBean).build());
		} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Retrieve Failed").build());
		}

	}
	
	@Override
	public ResponseEntity<?> StartSalaryInitialisation(String coyCode, String salarytype) {
		List<String> coyCodes = Arrays.asList(coyCode.split(","));
		List<String> salarytypes = Arrays.asList(salarytype.split(","));
		List<SalaryInitCheckInputBean> salaryInitCheckInputBean = new ArrayList<SalaryInitCheckInputBean>();
		List<EmppaymonthBean> emppaymonthList = new ArrayList<EmppaymonthBean>();
		List<SalaryInitCompletedBean> salaryInitCompletedBean = new ArrayList<SalaryInitCompletedBean>();
		
		
		List<Tuple> tInputDetails =  this.salaryInitialisationRepository.GetCheckInputDetails(coyCodes,salarytypes);
		
		if(tInputDetails.size()>0) {
			salaryInitCheckInputBean = 
					tInputDetails.stream().map(t -> {return new SalaryInitCheckInputBean(
			                t.get(0, String.class),
			                t.get(1, String.class),
			                t.get(2, String.class),
			                t.get(3, String.class),
			                t.get(4, String.class),
			                t.get(5, Character.class),
			                t.get(6, BigDecimal.class),
			                t.get(7, String.class));
					}
							).collect(Collectors.toList());
			} else {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Retrieve Failed").build());
		}
		logger.info("salaryInitCheckInputBean {} ",salaryInitCheckInputBean);
		
		salaryInitCheckInputBean.forEach(row -> {
			List<Tuple> emppaymonthTuple = new ArrayList<>();
			String status = "";
			// Calucate date differences in days
			Integer datediff = this.salaryInitialisationRepository.FindDateDiffForInit(row.getCompany().trim(), row.getSalType().toString());
			
			if ("INITIALISE".equals(row.getStatus().trim())) {
				LocalDateTime LocaldateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());

				logger.info("Initialedon {} ",CommonUtils.INSTANCE.convertStringToDateFormat(row.getInitialedon()));
				logger.info("LocaldateTime {} ",LocaldateTime);
				logger.info("datediff {} ",datediff);
				if(ChronoUnit.DAYS.between(CommonUtils.INSTANCE.convertStringToDateFormat(row.getInitialedon()), LocaldateTime) < 7){
					status = "Last Initialiastion was done just before " + datediff + " days Only....Initialisation NOT ALLOWED for " + row.getSalaryType() + " of " + row.getCompany() + "......";
					
					//find hold employees in previous month  
					Double holdCount = this.salaryInitialisationRepository.FindEmplOnHold(row.getCompany(), row.getCurrentMth(), row.getSalType().toString());
					//set values in bean to send to frontend
					SalaryInitCompletedBean completedBean = new SalaryInitCompletedBean();
					completedBean.setSalaryType(row.getSalaryType());
					completedBean.setCompany(row.getCompany());
				    completedBean.setCurrentMth(row.getCurrentMth());
				    completedBean.setNewMth(row.getNewMth());
				    completedBean.setStatus(row.getStatus());
				    completedBean.setSalType(row.getSalType()); 
				    completedBean.setYrSalRevNo(row.getYrSalRevNo());
				    completedBean.setRowInitial(new BigDecimal(0));
				    completedBean.setLastMthHold(new BigDecimal(holdCount));
				    completedBean.setStatus(status);
				    salaryInitCompletedBean.add(completedBean);
					
					
					return;
				}else {
		        	status = "READY TO PAY";
			        }	
			} else {  //when it is Re-INITIALISE
				status = "READY TO PAY";
			}
			
			if ("S".equals(salarytype)) {
				emppaymonthTuple = this.salaryInitialisationRepository.RowToInsertInEmppaymonth(row.getCompany(), row.getNewMth().toString(), row.getSalType().toString());
			} else {
				emppaymonthTuple = this.salaryInitialisationRepository.RowToInsertInEmppaymonthForBonus(row.getCompany(), row.getNewMth().toString(), row.getSalType().toString());
			}
			
			if(emppaymonthTuple.size()>0) {
				List<EmppaymonthBean> emppaymonthBean = 
						emppaymonthTuple.stream().map(emppaymontht->{return new EmppaymonthBean(
								emppaymontht.get(0,String.class),
								emppaymontht.get(1,String.class),
								emppaymontht.get(2,String.class),
								emppaymontht.get(3,String.class),
								emppaymontht.get(4,Character.class),
								emppaymontht.get(5,String.class),
								emppaymontht.get(6,BigDecimal.class),
								emppaymontht.get(7,BigDecimal.class),
								emppaymontht.get(8,BigDecimal.class),
								emppaymontht.get(9,BigDecimal.class),
								emppaymontht.get(10,BigDecimal.class),
								emppaymontht.get(11,String.class),
								emppaymontht.get(12,String.class),
								emppaymontht.get(13,Character.class),
								emppaymontht.get(14,String.class),
								emppaymontht.get(15,String.class),
								emppaymontht.get(16,String.class),
								emppaymontht.get(17,BigDecimal.class),
								emppaymontht.get(18,BigDecimal.class),
								emppaymontht.get(19,BigDecimal.class),
								emppaymontht.get(20,BigDecimal.class),
								emppaymontht.get(21,BigDecimal.class),
								emppaymontht.get(22,BigDecimal.class),
								emppaymontht.get(23,String.class),
								emppaymontht.get(24,String.class),
								emppaymontht.get(25,BigDecimal.class),
								emppaymontht.get(26,Character.class),
								emppaymontht.get(27,String.class)
								);
						}).collect(Collectors.toList());
				emppaymonthList.addAll(emppaymonthBean)		;
				
			    String site = Objects.nonNull(GenericAuditContextHolder.getContext().getSite()) ? GenericAuditContextHolder.getContext().getSite() : "MUM"; 
			    String userid = Objects.nonNull(GenericAuditContextHolder.getContext().getUserid()) ? GenericAuditContextHolder.getContext().getUserid(): "KRaheja";
			    String machineName = CommonUtils.INSTANCE.getClientConfig().getMachineName();
			    String ipAddress = CommonUtils.INSTANCE.getClientConfig().getIpAddress();
			    String coy = row.getCompany().trim();
			    String newPaymonth = row.getNewMth();
			    String salaryty = row.getSalType().toString().trim();
//				update coymthsaltypes
				this.salaryInitialisationRepository.updatecoymthsaltypes(coy,newPaymonth,salaryty,site,  userid, machineName, ipAddress);
				
//				update salaryplan
				this.salaryInitialisationRepository.updatesalaryplan(coy,newPaymonth,salaryty,site,  userid, machineName, ipAddress);
				
				}
			//find hold employees in previous month  
			Double holdCount = this.salaryInitialisationRepository.FindEmplOnHold(row.getCompany(), row.getCurrentMth(), row.getSalType().toString());
			//set values in bean to send to frontend
			SalaryInitCompletedBean completedBean = new SalaryInitCompletedBean();
			completedBean.setSalaryType(row.getSalaryType());
			completedBean.setCompany(row.getCompany());
		    completedBean.setCurrentMth(row.getCurrentMth());
		    completedBean.setNewMth(row.getNewMth());
		    completedBean.setStatus(row.getStatus());
		    completedBean.setSalType(row.getSalType()); 
		    completedBean.setYrSalRevNo(row.getYrSalRevNo());
		    completedBean.setRowInitial(new BigDecimal(emppaymonthTuple.size()));
		    completedBean.setLastMthHold(new BigDecimal(holdCount));
		    completedBean.setStatus(status);
		    salaryInitCompletedBean.add(completedBean);

		});

//		rows are added in emppaymonth
		this.emppaymonthRepository.saveAll(EmppaymonthEntityPojoMapper.addEmppaymonthEntityMapper.apply(emppaymonthList));
		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(salaryInitCompletedBean).build());
	}


}
