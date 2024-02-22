package kraheja.adminexp.billing.dataentry.adminAdvancePayment.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.request.AdmadvanceRequestBean;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.AdmAdvancePaymentFetchResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.bean.response.GenericResponse;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.service.AdminAdvancePaymentPassingService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/admadvance-bill-passing")
@Log4j2
public class AdmAdvancePaymentPassingController {

	@Autowired
	private AdminAdvancePaymentPassingService adminAdvanceBillPaymentPassingService;

	@GetMapping("/fetch-admadvance-bill-by-Pinv-and-Ser")
	public GenericResponse<Admadvance1> fetchAdmadvanceBySer(@RequestParam(value = "pinv") String pinv,
			@RequestParam(value = "ser") String ser) {
		log.info("In Admin Advance Payment Passing Fetch Method");
		log.info("ser :: {} , pinv :: {}",ser,pinv);

		return this.adminAdvanceBillPaymentPassingService.fetchAdmadvanceByPinvAndSer(pinv, ser);
	}
	
	@PutMapping("/update-admadvance-bill")
	public GenericResponse<Void> updateAdmadvance(@Valid @RequestBody String ser)
			throws ParseException {
		log.info("In Admin Advance Payment Update Method");
		 ser = ser.trim().replace("\"", "");
		 log.info("ser : {} ", ser);
		return this.adminAdvanceBillPaymentPassingService.updateAdmadvance(ser);

	}
	
}
