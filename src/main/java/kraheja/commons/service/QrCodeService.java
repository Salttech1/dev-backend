package kraheja.commons.service;

import org.springframework.http.ResponseEntity;

public interface QrCodeService {

	public ResponseEntity<?> generateQrCode(String invoiceNo);
}
