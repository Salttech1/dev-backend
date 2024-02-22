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
import kraheja.adminexp.vehicleexp.dataentry.bean.request.EquipRequestBean;
import kraheja.adminexp.vehicleexp.dataentry.service.EquipService;

@RestController
@RequestMapping("/equip")
public class EquipController {

	@Autowired
	private EquipService equipService;

	@GetMapping("/fetch-equip-by-Eqptype-and-Eqpnum")
	public ResponseEntity<?> fetchEquipByEqptypeAndEqpnum(@RequestParam(value = "eqptype") String  eqptype, @RequestParam(value = "eqpnum") String  eqpnum) throws ParseException {
		return this.equipService.fetchEquipByEqptypeAndEqpnum(eqptype, eqpnum) ; 
	}

	@PostMapping("/add-equip")
	public ResponseEntity<?> addEquip(@Valid @RequestBody EquipRequestBean equipRequestBean) throws ParseException {
		return this.equipService.addEquip(equipRequestBean);
	}

	@PutMapping("/update-equip")
	public ResponseEntity<?> updateEquip(@Valid @RequestBody EquipRequestBean equipRequestBean) throws ParseException {
		return this.equipService.updateEquip(equipRequestBean);
	}

	@GetMapping("/check-Eqptype-and-Eqpnum-exists")
	public ResponseEntity<?> checkEqptypeAndEqpnumExists(@RequestParam(value = "eqptype") String  eqptype, @RequestParam(value = "eqpnum") String  eqpnum) throws ParseException {
		return this.equipService.checkEqptypeAndEqpnumExists(eqptype, eqpnum);
	}

	@DeleteMapping("/delete-equip")
	public ResponseEntity<?> deleteEquip(@RequestParam(value = "eqptype") String  eqptype, @RequestParam(value = "eqpnum") String  eqpnum) throws ParseException {
		return this.equipService.deleteEquip(eqptype, eqpnum) ; 
	}
}