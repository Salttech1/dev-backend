package kraheja.adminexp.billing.dataentry.adminAdvancePayment.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmadvanceResponseBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.AdminAdvanceBillPaymentService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/admadvance-bill")
@Log4j2
public class AdmadvancePaymentController {
	@Autowired
	private AdminAdvanceBillPaymentService adminAdvanceBillPaymentService;

	@GetMapping("/fetch-admadvance-bill-by-Ser")
	public GenericResponse<AdmadvanceResponseBean> fetchAdmadvanceBySer(@RequestParam(value = "ser") String ser) {
		log.info("In Admin Advance Payment Fetch Method");
		return this.adminAdvanceBillPaymentService.fetchAdmadvanceBySer(ser);
	}

	@PostMapping("/save-admadvance-bill")
	public GenericResponse<String> addAdmadvance(@Valid @RequestBody AdmadvanceRequestBean admAdvanceRequestBean)
			throws ParseException {
		log.info("In Admin Advance Payment Save Method");
		return this.adminAdvanceBillPaymentService.addAdmadvance(admAdvanceRequestBean);
	}

	@PutMapping("/update-admadvance-bill")
	public GenericResponse<Void> updateAdmadvance(@Valid @RequestBody AdmadvanceRequestBean admAdvanceRequestBean)
			throws ParseException {
		log.info("In Admin Advance Payment Update Method");
		return this.adminAdvanceBillPaymentService.updateAdmadvance(admAdvanceRequestBean);

	}
}
