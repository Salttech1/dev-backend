package kraheja.enggsys.lcsystem.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.enggsys.lcsystem.payload.request.AuthorizationRequest;
import kraheja.enggsys.lcsystem.payload.response.AuthorizationResponse;
import kraheja.enggsys.lcsystem.service.AuthorizationService;
import kraheja.payload.GenericResponse;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * This API is use for create LC Authorization, retrieve LC Authorization and
 * update LC Authorization. Here we are delete AUTHBOE for every time while
 * update LC Authorization.
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
public class AuthorizationApi {

	private final AuthorizationService authorizationService;

	public AuthorizationApi(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@GetMapping("/get-supplier-detail")
	public ResponseEntity<SupplierDBResponse> getContractDetail(@RequestParam String supplier,
			@RequestParam String building, @RequestParam String authType, @RequestParam String authnum) {
		log.debug("post/request/authorization supplier: {} building: {} authType: {} authnum: {}", supplier, building,
				authType, authnum);

		SupplierDBResponse response = authorizationService.getSupplierDetail(supplier, building, authType, authnum);
		log.debug("post/response/authorization: {}", response);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/retrieve-lc-authorization")
	public ResponseEntity<AuthorizationRequest> retrieveAuth(@RequestParam String supplier,
			@RequestParam String building, @RequestParam String authType, @RequestParam String authnum) {
		log.debug("post/request/authorization supplier: {} building: {} authType: {} authnum: {}", supplier, building,
				authType, authnum);

		AuthorizationRequest response = authorizationService.retrieveAuthorization(supplier, building, authType,
				authnum);
		log.debug("post/response/authorization: {}", response);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/create-lc-authorization")
	public ResponseEntity<GenericResponse> makeAuthorization(@Valid @RequestParam String supplier,
			@RequestParam String building, @RequestParam String authType, @RequestParam String authnum,
			@Valid @RequestBody AuthorizationRequest request) {
		log.debug("post/request/authorization/create-lc-certificate: {}", request);

		GenericResponse response = authorizationService.makeAuthorization(request, supplier, building, authType,
				authnum);
		log.debug("post/response/authorization/create-lc-authorization: {}", response);

		return ResponseEntity.ok(response);
	}
	@GetMapping("/retrieve-last-lc-authorization")
	public ResponseEntity<AuthorizationResponse> retrieveLastAuthNo(@RequestParam String supplier,
			@RequestParam String building, @RequestParam String authType) {
		String lastauthNumber = authorizationService.fetchlastauthNumber(supplier,building,authType);
		AuthorizationResponse response = AuthorizationResponse.builder().authNum(lastauthNumber).build();
		return ResponseEntity.ok(response);
	}
}
