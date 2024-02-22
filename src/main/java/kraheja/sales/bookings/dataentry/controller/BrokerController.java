package kraheja.sales.bookings.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.sales.bean.request.BrokerRequestBean;
import kraheja.sales.bookings.dataentry.service.BrokerService;


@RestController
@RequestMapping("/broker")
public class BrokerController {

	@Autowired
	private BrokerService brokerService;
	

	@GetMapping("/fetch-broker-by-Code")
	public ResponseEntity<?> fetchBrokerByCode(@RequestParam(value = "brokerCode") String brokerCode) throws ParseException{
			return this.brokerService.fetchBrokerByCode(brokerCode);
	}

	@PostMapping("/add-broker")
	public ResponseEntity<?> addBroker(@Valid @RequestBody BrokerRequestBean brokerRequestBean) throws ParseException {
		return this.brokerService.addBroker(brokerRequestBean);
	}
//
	@PutMapping("/update-broker")
	public ResponseEntity<?> updateBroker(@Valid @RequestBody BrokerRequestBean brokerRequestBean) throws ParseException {
		return this.brokerService.updateBroker(brokerRequestBean);
	}

	@DeleteMapping("/delete-broker")
	public ResponseEntity<?> deleteBroker(@RequestParam(value = "brokerCode") String brokerCode) throws ParseException {
		return this.brokerService.deleteBroker(brokerCode);
	}
	
	//
//	@GetMapping("/check-Code-exists")
//	public ResponseEntity<?> checkCodeExists(@RequestParam(value = "code") String  code) throws ParseException {
//		return this.brokerService.checkBrokerCodeExists(code);
//	}

}