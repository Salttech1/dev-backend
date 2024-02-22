package kraheja.adminexp.billing.dataentry.adminBill.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdmbillPassingService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/admin-bill-passing")
@Log4j2
public class AdminbillPassingController {

	@Autowired
	private AdmbillPassingService adminBillPassingService;

	@GetMapping("/fetch-admbill-by-Ser")
	public GenericResponse<Admbillh> fetchAdminBillBySer(@RequestParam(value = "ser") String ser) {
		log.info("In Admin Bill Passing Fetch Method");
		log.info("ser :: {}",ser);

		return this.adminBillPassingService.fetchAdmbillhSer(ser);
	}
	
	@PutMapping("/pass-admin-bill-by-Ser")
	public GenericResponse<String> passAdminBillBySer(@RequestParam(value = "ser") String ser)
			throws ParseException {
		log.info("In Admin Bill Passing Method");
		return this.adminBillPassingService.adminBillPassing(ser.trim());

	}
	
}
