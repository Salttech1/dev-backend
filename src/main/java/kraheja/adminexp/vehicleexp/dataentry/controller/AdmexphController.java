package kraheja.adminexp.vehicleexp.dataentry.controller;

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
import java.text.ParseException;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.AdmexphRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.service.AdmexphService;

@RestController
@RequestMapping("/admexph")
public class AdmexphController {

	@Autowired
	private AdmexphService admexphService;

//	@GetMapping("/fetch-admexph-by-Certnum-and-Coy-and-Certtype")
//	public ResponseEntity<?> fetchAdmexphByCertnumAndCoyAndCerttype(@RequestParam(value = "certnum") String  certnum, @RequestParam(value = "coy") String  coy)  {
//		return this.admexphService.fetchAdmexphByCertnumAndCoyAndCerttype(certnum, coy) ; 
//	}
	
	@GetMapping("/fetch-admexph-by-Certnum-and-Equipid")
	public ResponseEntity<?> fetchAdmexphByCertnumAndEquipid(@RequestParam(value = "certnum") String  certnum, @RequestParam(value = "equipid") String  equipid)  {
		return this.admexphService.fetchAdmexphByCertnumAndEquipid(certnum, equipid);
	}
	
	@GetMapping("/fetch-vehicleInfo")
	public ResponseEntity<?> getVehicleInfo(@RequestParam(value = "vehnum") String  vehnum){
		return this.admexphService.getVehicleInfo(vehnum);
	}

	@PostMapping("/add-admexph")
	public ResponseEntity<?> addAdmexph(@Valid @RequestBody AdmexphRequestBean admexphRequestBean)  {
		return this.admexphService.addAdmexph(admexphRequestBean);
	}

	@PutMapping("/update-admexph")
	public ResponseEntity<?> updateAdmexph(@Valid @RequestBody AdmexphRequestBean admexphRequestBean) {
		return this.admexphService.updateAdmexph(admexphRequestBean);
	}

	@GetMapping("/check-Certnum-and-Coy-and-Certtype-exists")
	public ResponseEntity<?> checkCertnumAndCoyAndCerttypeExists(@RequestParam(value = "certnum") String  certnum, @RequestParam(value = "coy") String  coy)  {
		return this.admexphService.checkCertnumAndCoyAndCerttypeExists(certnum, coy);
	}
	
	@GetMapping("/fetch-partyInfo")
	public ResponseEntity<?> getPartyInfo(@RequestParam(value = "partycode") String partycode,@RequestParam(value = "vehicleno") String vehicleno){
		return this.admexphService.getPartyInfo(partycode, vehicleno);
	}

	@DeleteMapping("/delete-admexph")
	public ResponseEntity<?> deleteAdmexph(@RequestParam(value = "certnum") String  certnum, @RequestParam(value = "coy") String  coy)  {
		return this.admexphService.deleteAdmexph(certnum, coy) ; 
	}
	
	@PostMapping("/pass-vehiclecert")
	public ResponseEntity<?> passVehicleCert(@Valid @RequestBody AdmexphRequestBean admexphRequestBean) {
		return this.admexphService.passVehicleCert(admexphRequestBean);
	}
	
	@GetMapping("/fetch-unposted-Cert-by-Equipid")
	public ResponseEntity<?> findByEquipid(@RequestParam(value = "equipid") String  equipid)  {
		return this.admexphService.findByEquipid(equipid);
	}
	
	@PutMapping("/cancel-unposted-certificate")
	public ResponseEntity<?> cancelUnPostedCertificate(@RequestParam(value = "certnum") String certnum, @RequestParam(value = "transer") String transer) {
		return this.admexphService.cancelUnPostedCertificate(certnum, transer);
	}
}