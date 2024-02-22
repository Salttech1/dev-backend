package kraheja.enggsys.managementreport.lnVendorCountController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.managementreport.bean.response.GenericResponse;
import lombok.extern.log4j.Log4j2;
import kraheja.enggsys.managementreport.VendorCountService.VendorCountService;

@RestController
@RequestMapping("/boqreport")
@Log4j2
public class lnVendorCountController {

	@Autowired
	private VendorCountService vendorCountService;

	@GetMapping("/fetch-vendor-count-by-logicNoteNum")
	public GenericResponse<String> fetchVendorCountByLogicNoteNum(@RequestParam(value = "logicNoteNum") String logicNoteNum) {
		log.info("In logic note Fetch Method");
		return this.vendorCountService.fetchVendorCountByLogicNoteNum(logicNoteNum);
	}
}