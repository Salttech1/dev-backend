package kraheja.payroll.masterdetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.commons.bean.response.ServiceResponseBean;
import kraheja.payroll.bean.response.CalculateSalaryForTheseEmployees;
import kraheja.payroll.bean.response.GetInputScreenBean;
import kraheja.payroll.bean.response.GetPackageforEmployeeBean;
import kraheja.payroll.repository.SalaryComputationRepository;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private SalaryComputationRepository salaryComputationRepository;
	
	@GetMapping("/demo")  
//	public ResponseEntity<ServiceResponseBean> salarydemocontroller(String coy,String paymonth,String saltype,String empcode, String emptype) {
//		List<CalculateSalaryForTheseEmployees> findByComputeEmployee = this.salaryComputationRepository.findByComputeEmployee(coy, paymonth, saltype, empcode, emptype);
//		System.out.println("count is "+ findByComputeEmployee.size());
//	
//		return ResponseEntity.ok(ServiceResponseBean.builder().data(findByComputeEmployee).build());
//	}
	
	public ResponseEntity<ServiceResponseBean> salarydemocontroller(String coy,String paymonth,String empcode) {
		List<Tuple> GetPackageforEmpl = this.salaryComputationRepository.findByGetPackage(coy, paymonth, empcode);
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
			System.out.println("count is "+ GetPackageforEmployeeList.size());
			return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(GetPackageforEmployeeList).build());
			}
		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.FALSE).message("No Package found...").build());
}
	
//	public ResponseEntity<ServiceResponseBean> salarydemocontroller(String coy,String empcode,String emptype,String saltype) {
//		List<GetInputScreenBean> getInputScreenBean = this.salaryComputationRepository.findByInputScreen(coy,empcode, emptype,saltype);
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(getInputScreenBean).build());
//}
//	public ResponseEntity<ServiceResponseBean> salarydemocontroller(String coy,String empcode,String emptype,String saltype) {
//		List<GetInputScreenBean> getInputScreenBean = this.salaryComputationRepository.findByInputScreenForProj(coy,empcode, emptype,saltype);
//		return ResponseEntity.ok(ServiceResponseBean.builder().status(Boolean.TRUE).data(getInputScreenBean).build());
//}
	
//	findByInputScreenForProj
	
}
