package kraheja.sales.bookings.dataentry.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.BrokerRequestBean;
import kraheja.sales.bean.request.LoanhistoryRequestBean;

public interface BrokerService {

	ResponseEntity<?> fetchBrokerByCode(String  code) ;

	ResponseEntity<?> addBroker(BrokerRequestBean brokerRequestBean) throws ParseException;
//
	ResponseEntity<?> updateBroker(BrokerRequestBean brokerRequestBean) throws ParseException;
	
	ResponseEntity<?> deleteBroker(String  code) throws ParseException;
//
//	ResponseEntity<?> checkBrokerCodeExists(String brokCode);

}