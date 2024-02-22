package kraheja.arch.projbldg.dataentry.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.arch.projbldg.dataentry.bean.request.MailinfoRequestBean;
import kraheja.arch.projbldg.dataentry.service.MailinfoService;

@RestController
@RequestMapping("/mailinfo")
public class MailinfoController {

	@Autowired
	private MailinfoService mailinfoService;

	@GetMapping("/fetch-mailinfo-by-Bldgcode")
	public ResponseEntity<?> fetchMailinfoByBldgcode(@RequestParam(value = "bldgcode") String  bldgcode) throws ParseException {
		return this.mailinfoService.fetchMailinfoByBldgcode(bldgcode) ; 
	}

	@PostMapping("/add-mailinfo")
	public ResponseEntity<?> addMailinfo(@Valid @RequestBody MailinfoRequestBean mailinfoRequestBean) throws ParseException {
		return this.mailinfoService.addMailinfo(mailinfoRequestBean);
	}

	@PutMapping("/update-mailinfo")
	public ResponseEntity<?> updateMailinfo(@Valid @RequestBody MailinfoRequestBean mailinfoRequestBean) throws ParseException {
		return this.mailinfoService.updateMailinfo(mailinfoRequestBean);
	}


}