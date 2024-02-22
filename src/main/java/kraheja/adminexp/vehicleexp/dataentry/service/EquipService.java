package kraheja.adminexp.vehicleexp.dataentry.service;

import java.text.ParseException;
import org.springframework.http.ResponseEntity;
import kraheja.adminexp.vehicleexp.dataentry.bean.request.EquipRequestBean;
public interface EquipService {

	ResponseEntity<?> fetchEquipByEqptypeAndEqpnum(String  eqptype, String  eqpnum) ;

	ResponseEntity<?> addEquip(EquipRequestBean equipRequestBean) throws ParseException;

	ResponseEntity<?> updateEquip(EquipRequestBean equipRequestBean) throws ParseException;

	ResponseEntity<?> deleteEquip(String  eqptype, String  eqpnum) throws ParseException; 

	ResponseEntity<?> checkEqptypeAndEqpnumExists(String  eqptype, String  eqpnum) ;
}