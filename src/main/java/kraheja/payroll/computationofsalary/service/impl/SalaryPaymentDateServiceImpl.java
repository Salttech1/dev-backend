package kraheja.payroll.computationofsalary.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.payroll.bean.SalaryPaymentDateBean;
import kraheja.payroll.computationofsalary.service.SalaryPaymentDateService;
import kraheja.payroll.repository.SalaryPaymentDateRepository;

@Service
@Transactional
public class SalaryPaymentDateServiceImpl implements SalaryPaymentDateService {
	
	@Autowired
	private SalaryPaymentDateRepository salaryPaymentDateRepository;

	@Override
	public ResponseEntity<?> GetCheckInputDetailsforPaymentDate(String paymonth, String salarytype, String instrumenttype, String company) {
		List<String> instrumenttypes = Arrays.asList(instrumenttype.split(","));
		List<Tuple> tInputDetailsforPaymentDate =  this.salaryPaymentDateRepository.GetCheckInputDetailsforPaymentDate(paymonth, salarytype, instrumenttypes, company);
		if(tInputDetailsforPaymentDate.size()>0) {
			List<SalaryPaymentDateBean> salaryPaymentDateBean =
					tInputDetailsforPaymentDate.stream().map(t -> {return new SalaryPaymentDateBean(
			                t.get(0, String.class),
			                t.get(1, String.class),
			                t.get(2, Double.class),
			                t.get(3, String.class),
			                t.get(4,Boolean.class));
					}
							).collect(Collectors.toList());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(salaryPaymentDateBean).build());
		} else {	
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Retrieve Failed").build());
	}
	}
	
	@Override
	public ResponseEntity<?> GetPaymonthByCoyAndSalarytype(String company, String salarytype){
		String spaymonth = this.salaryPaymentDateRepository.GetPaymonthByCoyAndSalarytype(company, salarytype);
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(spaymonth).build());
	}

}
