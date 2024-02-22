package kraheja.adminexp.overheads.dataentry.controller;

import java.text.ParseException;

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

import kraheja.adminexp.overheads.dataentry.bean.request.OverheadtxnRequestBean;
import kraheja.adminexp.overheads.dataentry.service.OverheadtxnService;

@RestController
@RequestMapping("/overheadtxn")
public class OverheadtxnController {

	@Autowired
	private OverheadtxnService overheadtxnService;

	//fetchOverheadconbyConncode
	
	@GetMapping("/fetch-overheadtxn-by-Connocode-and-Billperiod-and-Supplementarybill")
	public ResponseEntity<?> fetchOverheadtxnByConnocodeAndBillperiodAndSupplementarybill(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "billperiod") String  billperiod, @RequestParam(value = "supplementarybill") String  supplementarybill) throws ParseException {
		return this.overheadtxnService.fetchOverheadtxnByConnocodeAndBillperiodAndSupplementarybill(connocode, billperiod, supplementarybill) ; 
	}

	@GetMapping("/fetch-overheadtxn-by-Connocode")
	public ResponseEntity<?>fetchOverheadtxnByConnocode(@RequestParam(value="connocode")String connocode) throws ParseException{
		return this.overheadtxnService.fetchOverheadtxnByConnocode(connocode);
	}
	
	@GetMapping("/fetch-overheadtxn-PrvBillData-by-Connocode")
	public ResponseEntity<?> fetchPrvBillData(String connocode) throws ParseException{
			return this.overheadtxnService.fetchPrvBillData(connocode);
	}
	
	@PostMapping("/add-overheadtxn")
	public ResponseEntity<?> addOverheadtxn(@Valid @RequestBody OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {
		return this.overheadtxnService.addOverheadtxn(overheadtxnRequestBean);
	}
	// for vacant flat only
	@PostMapping("/addexceldata-overheadtxn")
	public ResponseEntity<?> addExcelOverheadtxn(@Valid @RequestBody OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {
		return this.overheadtxnService.addExcelOverheadtxn(overheadtxnRequestBean);
	}
	
	@PutMapping("/update-overheadtxn")
	public ResponseEntity<?> updateOverheadtxn(@Valid @RequestBody OverheadtxnRequestBean overheadtxnRequestBean) throws ParseException {
		return this.overheadtxnService.updateOverheadtxn(overheadtxnRequestBean);
	}

	@GetMapping("/check-Connocode-and-Billperiod-and-Supplementarybill-exists")
	public ResponseEntity<?> checkConnocodeAndBillperiodAndSupplementarybillExists(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "billperiod") String  billperiod, @RequestParam(value = "supplementarybill") String  supplementarybill) throws ParseException {
		return this.overheadtxnService.checkConnocodeAndBillperiodAndSupplementarybillExists(connocode, billperiod, supplementarybill);
	}

	@DeleteMapping("/delete-overheadtxn")
	public ResponseEntity<?> deleteOverheadtxn(@RequestParam(value = "connocode") String  connocode, @RequestParam(value = "billperiod") String  billperiod, @RequestParam(value = "supplementarybill") String  supplementarybill) throws ParseException {
		return this.overheadtxnService.deleteOverheadtxn(connocode, billperiod, supplementarybill) ; 
	}	
}
