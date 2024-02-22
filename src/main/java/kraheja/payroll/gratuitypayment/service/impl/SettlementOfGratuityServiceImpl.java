package kraheja.payroll.gratuitypayment.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.payroll.gratuitypayment.service.SettlementOfGratuityService;
import kraheja.payroll.repository.ReportParametersRepository;

@Service
public class SettlementOfGratuityServiceImpl implements SettlementOfGratuityService {

	@Autowired
	private ReportParametersRepository reportParametersRepository;
	
	@Override
	public ResponseEntity<?> GetGratuityMonthForEmpl(String empCode) {
		String gratuityPaymonth = this.reportParametersRepository.GetGratuityMonthForEmployee(empCode) ;
		if(Objects.isNull(gratuityPaymonth)) {
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("Data Not Found").build());			
		}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(gratuityPaymonth).build());		
	}
	

}
