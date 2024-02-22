package kraheja.enggsys.managementreport.contractsumController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.bean.request.contractSumRequestBean;
import kraheja.enggsys.managementreport.contractSumService.contractSumService;

@RestController
@RequestMapping("/contractsummaryreport")
public class contractsumController {

	@Autowired
	private contractSumService contractSumService;

	@PostMapping("/add-into-contractsummary-temp-table")
	public ResponseEntity<?> addIntoContractsummaryTempTable(
			@RequestBody contractSumRequestBean contractSumRequestBean) {
		return this.contractSumService.addIntoContractSummaryTempTable(contractSumRequestBean);
	}

	@DeleteMapping("/delete-contractsummary-from-sessionId")
	public ResponseEntity<?> truncateTempTable(Integer sessionId) {
		return this.contractSumService.deleteContractSummaryFromSessionId(sessionId);
	}
	
	@GetMapping("/fetch-matcertgroup")
	public ResponseEntity<?> fetchMatcertGroup(String bldgCode) {
		return this.contractSumService.fetchMatcertGroup(bldgCode);
	}

}