package kraheja.commons.controller;

import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kraheja.commons.service.QrCodeService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/qr")
@Log4j2
public class QrController {

	@Autowired
	QrCodeService qrCodeService;

	@GetMapping("/generate-qr")
	public ResponseEntity<?> fetchQr(@RequestParam(value = "invoiceNo") String invoiceNo) throws ParseException {
		log.info("/qr/generate-qr");
		log.info("invoiceNo : {}", invoiceNo);
		return qrCodeService.generateQrCode(invoiceNo);

	}

}
