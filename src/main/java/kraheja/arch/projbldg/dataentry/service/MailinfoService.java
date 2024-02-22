package kraheja.arch.projbldg.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.arch.projbldg.dataentry.bean.request.MailinfoRequestBean;
public interface MailinfoService {

	ResponseEntity<?> fetchMailinfoByBldgcode(String  bldgcode) ;

	ResponseEntity<?> addMailinfo(MailinfoRequestBean mailinfoRequestBean) ;

	ResponseEntity<?> updateMailinfo(MailinfoRequestBean mailinfoRequestBean) ;

}