package kraheja.adminexp.billing.dataentry.adminBill.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminBill.bean.response.AdminBillResponseBean;
import kraheja.adminexp.billing.dataentry.adminBill.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminBill.service.AdminBillCancellationService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/admbill-bill-cancellation")
public class AdminBillCancellationController {

	@Autowired
	AdminBillCancellationService adminBillCancellationService;

	@GetMapping("/fetch-admbill-by-Ser")
	public GenericResponse<AdminBillResponseBean> fetchAdmbillBySer(@RequestParam(value = "ser") String ser) {
		return this.adminBillCancellationService.fetchAdmbillhBySer(ser.trim());
	}

	@PutMapping("/cancel-admin-bill-by-Ser")
	public GenericResponse<String> cancelAdminBill(@RequestParam(value = "ser") String ser) throws ParseException {
		log.info("In Admin Bill Cancellation Method");
		return this.adminBillCancellationService.cancelAdminBill(ser.trim());

	}

}
