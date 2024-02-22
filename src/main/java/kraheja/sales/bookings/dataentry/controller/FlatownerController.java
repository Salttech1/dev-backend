// Developed By  - 	sandesh.c
// Developed on - 17-08-23
// Mode  - Data Entry
// Purpose - Flatowner Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.bookings.dataentry.controller;

import java.util.List;

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

import kraheja.sales.bean.request.FlatownerRequestBean;
import kraheja.sales.bookings.dataentry.service.FlatownerService;

@RestController
@RequestMapping("/flatowner")
public class FlatownerController {

	@Autowired
	private FlatownerService flatownerService;

	@GetMapping("/fetch-flatowner-by-Ownerid-and-Bldgcode-and-Wing-and-Flatnum-and-Ownertype")
	public ResponseEntity<?> fetchFlatownerByOwneridAndBldgcodeAndWingAndFlatnumAndOwnertype(@RequestParam(value = "ownerid") String  ownerid, @RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "ownertype") String  ownertype) {
		return this.flatownerService.fetchFlatownerByOwneridAndBldgcodeAndWingAndFlatnumAndOwnertype(ownerid, bldgcode, wing, flatnum, ownertype) ; 
	}

	@PostMapping("/add-flatowner")
	public ResponseEntity<?> addFlatowner(@Valid @RequestBody List<FlatownerRequestBean> flatownerRequestBean)  {
		return this.flatownerService.addFlatowner(flatownerRequestBean);
	}

	@PutMapping("/update-flatowner")
	public ResponseEntity<?> updateFlatowner(@Valid @RequestBody List<FlatownerRequestBean> flatownerRequestBean,@RequestParam(value = "ownerid") String  ownerid)  {
		return this.flatownerService.updateFlatowner(flatownerRequestBean,ownerid);
	}

	@GetMapping("/check-Ownerid-and-Bldgcode-and-Wing-and-Flatnum-and-Ownertype-exists")
	public ResponseEntity<?> checkOwneridAndBldgcodeAndWingAndFlatnumAndOwnertypeExists(@RequestParam(value = "ownerid") String  ownerid, @RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "ownertype") String  ownertype)  {
		return this.flatownerService.checkOwneridAndBldgcodeAndWingAndFlatnumAndOwnertypeExists(ownerid, bldgcode, wing, flatnum, ownertype);
	}

	@DeleteMapping("/delete-flatowner")
	public ResponseEntity<?> deleteFlatowner(@RequestParam(value = "ownerid") String  ownerid, @RequestParam(value = "bldgcode") String  bldgcode, @RequestParam(value = "wing") String  wing, @RequestParam(value = "flatnum") String  flatnum, @RequestParam(value = "ownertype") String  ownertype)  {
		return this.flatownerService.deleteFlatowner(ownerid, bldgcode, wing, flatnum, ownertype) ; 
	}
}