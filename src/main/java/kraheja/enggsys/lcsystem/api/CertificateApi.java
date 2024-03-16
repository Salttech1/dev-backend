package kraheja.enggsys.lcsystem.api;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.lcsystem.payload.request.LcCertificateRequest;
import kraheja.enggsys.lcsystem.payload.response.ContractResponse;
import kraheja.enggsys.lcsystem.service.LcCertificateService;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * This API is use for create LC Certificate, retrieve LC Certificate and update
 * LC Certificate. Here we are delete AUTHBOE for every time while update LC
 * Certificate.
 * </p>
 * 
 * @author sazzad.alom
 * @since FEB-2024
 * @version 1.0.0
 * 
 */
@Log4j2
@RestController
@RequestMapping("/certificate")
public class CertificateApi {

	private LcCertificateService lcCertificateService;

	public CertificateApi(LcCertificateService lcCertificateService) {
		this.lcCertificateService = lcCertificateService;
	}

	@GetMapping("/get-contract-detail")
	public ResponseEntity<ContractResponse> getContractDetail(@RequestParam String recId, @RequestParam String certType,
			@RequestParam String lcerCertnum) {
		log.debug("post/request/certificate/recId: {} certificateType: {} lcerCertnum: {}", recId, certType,
				lcerCertnum);

		ContractResponse response = lcCertificateService.getContract(recId, certType, lcerCertnum);
		log.debug("post/response/certificate: {}", response);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-lc-certificate")
	public ResponseEntity<GenericResponse> makeCertificate(@Valid @RequestParam String recId,
			@RequestParam String certType, @RequestParam String lcerCertnum,
			@Valid @RequestBody LcCertificateRequest request) {
		log.debug("post/request/certificate/create-lc-certificate: {}", request);

		GenericResponse response = lcCertificateService.makeCertificate(request, recId, certType, lcerCertnum);
		log.debug("post/response/certificate/create-lc-certificate: {}", response);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/retrieve-lc-certificate")
	public ResponseEntity<LcCertificateRequest> retrieveCertificate(@RequestParam String recId,
			@RequestParam String certType, @RequestParam String lcerCertnum) {
		log.debug("post/request/certificate/recId: {} certificateType: {} lcerCertnum: {}", recId, certType,
				lcerCertnum);

		LcCertificateRequest response = lcCertificateService.retrieveCertificate(recId, certType, lcerCertnum);
		log.debug("post/response/certificate: {}", response);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/retrieve-last-lc-certificate")
	public ResponseEntity<ContractResponse> retrieveLastCertficateNo(@NotEmpty @RequestParam String recId) {
		String lastLCCertificateNumber = lcCertificateService.fetchLastCertficateNo(recId);
		ContractResponse contractResponse = ContractResponse.builder().prvCertNum(lastLCCertificateNumber).build();
		return ResponseEntity.ok(contractResponse);

	}
}
