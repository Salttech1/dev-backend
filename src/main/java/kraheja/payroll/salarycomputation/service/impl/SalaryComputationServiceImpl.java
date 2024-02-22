package kraheja.payroll.salarycomputation.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.commons.utils.GenericCounterIncrementLogicUtil;
import kraheja.payroll.bean.ComputationDetailRequestBean;
import kraheja.payroll.bean.EmpsalaryprocessRequestBean;
import kraheja.payroll.bean.EmpsalaryprocessRequestBean.EmpsalaryprocessRequestBeanBuilder;
import kraheja.payroll.bean.TotalComputationDetailRequestBean;
import kraheja.payroll.bean.response.CalculateSalaryForTheseEmployees;
import kraheja.payroll.bean.response.GetInputScreenBean;
import kraheja.payroll.bean.response.GetPackageforEmployeeBean;
import kraheja.payroll.repository.SalaryComputationRepository;
import kraheja.payroll.salarycomputation.service.SalaryComputationService;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
public class SalaryComputationServiceImpl implements SalaryComputationService{
	
	@Autowired
	private SalaryComputationRepository salaryComputationRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(SalaryComputationServiceImpl.class);
	
	public ResponseEntity<ServiceResponseBean> fetchEmplForCalculation(ComputationDetailRequestBean computationDetailRequestBean) {
		List<GetInputScreenBean> getInputScreenBean = this.salaryComputationRepository.findByInputScreen(computationDetailRequestBean.getCoy(), computationDetailRequestBean.getEmpcode().trim(), computationDetailRequestBean.getEmptype().trim(), computationDetailRequestBean.getSaltype().trim());
		logger.info("Display Screen :: {}", getInputScreenBean);
		if(Objects.nonNull(getInputScreenBean)) {
			List<TotalComputationDetailRequestBean>totcompdetBean = getInputScreenBean.stream().map(UserInputs->
				TotalComputationDetailRequestBean.builder()
				.coy(UserInputs.getCoy())
				.paymonth(UserInputs.getPaymonth())
				.saltype(UserInputs.getSalarytype())
				.empcode(computationDetailRequestBean.getEmpcode().trim())
				.emptype(computationDetailRequestBean.getEmptype().trim())
				.build()).collect(Collectors.toList());
			logger.info("coytocompute :: {}", totcompdetBean);
			logger.info("coytocomputesize :: {}", totcompdetBean.size());
			
//			List<List<CalculateSalaryForTheseEmployees>> CalcSalForTheseEmpl = totcompdetBean.stream().map(
//					e->{List<CalculateSalaryForTheseEmployees> findByComputeEmployee = this.salaryComputationRepository.findByComputeEmployee(e.getCoy(), e.getPaymonth(), e.getSaltype(), e.getEmpcode(), e.getEmptype());
//					return findByComputeEmployee;
//					}
//					).collect(Collectors.toList());
//			logger.info("employeelist :: {}", CalcSalForTheseEmpl);
			
			
			String sessionId = GenericCounterIncrementLogicUtil.generateTranNo("#SESS", "#SESS");
			List<CalculateSalaryForTheseEmployees> CompEmpl = new ArrayList<CalculateSalaryForTheseEmployees>();
//			for(int i =0 ; i < totcompdetBean.size() ; i++) {
			IntStream.range(0, totcompdetBean.size())
			  .forEach(i -> {
				  List<CalculateSalaryForTheseEmployees> findByComputeEmployee = this.salaryComputationRepository.findByComputeEmployee(totcompdetBean.get(i).getCoy() , totcompdetBean.get(i).getPaymonth(), totcompdetBean.get(i).getSaltype(), totcompdetBean.get(i).getEmpcode(), totcompdetBean.get(i).getEmptype());
				if(Objects.nonNull(findByComputeEmployee)) {
					List<CalculateSalaryForTheseEmployees> CompEmplloop = findByComputeEmployee.stream().map(ce->
							CalculateSalaryForTheseEmployees.builder()
							.company(ce.getCompany())
							.department(ce.getDepartment())
							.employeeType(ce.getEmployeeType())
							.empcode(ce.getEmpcode())
							.paymode(ce.getPaymode())
							.empfullname(ce.getEmpfullname())
							.emptypename(ce.getEmptypename())
							.paymonth(ce.getPaymonth())
							.build()).collect(Collectors.toList());
					CompEmpl.addAll(CompEmplloop);
				}
			  });
//			}
			logger.info("CompEmpl :: {}", CompEmpl);
			
			//loop for getting salarypackage for individual employee
			List<GetPackageforEmployeeBean> GetPackageforEmployee = new ArrayList<GetPackageforEmployeeBean>();
			IntStream.range(0, CompEmpl.size())
			  .forEachOrdered(i -> {
				  List<Tuple> GetPackageforEmpl = this.salaryComputationRepository.findByGetPackage(CompEmpl.get(i).getCompany(),CompEmpl.get(i).getPaymonth(),CompEmpl.get(i).getEmpcode());
				  logger.info("Package :: {}", GetPackageforEmpl);
					if(GetPackageforEmpl.size()>0) {
					List<GetPackageforEmployeeBean> GetPackageforEmployeeList = 
							GetPackageforEmpl.stream().map(t -> {return new GetPackageforEmployeeBean(
									t.get(0, String.class).trim(),
									t.get(1, String.class).trim(),
									t.get(2, Character.class).toString(),
									t.get(3, BigDecimal.class).intValue(),
									t.get(4, Character.class).toString(),
									t.get(5, Character.class).toString(),
									t.get(6, Character.class).toString(),
									t.get(7, Character.class).toString(),
									t.get(8, BigDecimal.class).doubleValue(),
									t.get(9, Character.class).toString(),
									t.get(10, Character.class).toString(),
									t.get(11, Character.class).toString());
							}
									).collect(Collectors.toList());
					GetPackageforEmployee.addAll(GetPackageforEmployeeList);
					}

			logger.info("Employ code :: {}", GetPackageforEmployee );
			
			//add records in empsalaryprocess
			List<EmpsalaryprocessRequestBean> empsalprocess = new ArrayList<>();
			if (GetPackageforEmployee.size()>0) {
//				try {
			EmpsalaryprocessRequestBeanBuilder empsalprocessbuilder = EmpsalaryprocessRequestBean.builder();
			empsalprocess.add(empsalprocessbuilder
					.sessionid(Double.valueOf(sessionId))
					.empcode(GetPackageforEmployee.get(i).getEmpcode())
					.company(CompEmpl.get(i).getCompany())
					.paidfrom(null)
					.paidupto(null)
					.salarytype(null)
					.paymonth(null)
					.earndedcode(null)
					.earndedamt(null)
					.currrate(null)
					.lastmthrate(null)
					.prevmthrate(null)
					.lastyrrate(null)
					.salrevison(null)
					.onhold(null)
					.computecount(null)
					.recompcount(null)
					.computeno(null)
					.schemeapplyn(null)
					.site(null)
					.userid(null)
					.module(null)
					.machinename(null)
					.modifiedon(null)
					.ipaddress(null)
					.build());
//			return ;
//				}catch(Exception e) {return ;}
			}
			  });		
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(GetPackageforEmployee).build());
			
		}		
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).message("completed").build());

	
}
}