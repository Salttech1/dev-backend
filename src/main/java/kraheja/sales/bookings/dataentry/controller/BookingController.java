package kraheja.sales.bookings.dataentry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kraheja.sales.bean.request.BookingWrapperBean;
import kraheja.sales.bookings.dataentry.service.BookingService;
//import kraheja.sales.bookings.dataentry.service.serviceimpl.BookingCommonFunction;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

//	@Autowired
//	private BookingCommonFunction bookingCommonFunction;

	@GetMapping("/fetch-booking-by-Bldgcode-and-Wing-and-Flatnum-and-Ownerid")
	public ResponseEntity<?> fetchBookingByBldgcodeAndWingAndFlatnumAndOwnerid(
			@RequestParam(value = "bldgcode") String bldgcode, @RequestParam(value = "wing") String wing,
			@RequestParam(value = "flatnum") String flatnum, @RequestParam(value = "ownerid") String ownerid) {
		return this.bookingService.fetchBookingByBldgcodeAndWingAndFlatnumAndOwnerid(bldgcode, wing, flatnum, ownerid);
	}

	@GetMapping("fetch-booking-by-CopyFrom-AccourdingTabControl")
	public ResponseEntity<?> fetchBookingByCopyFromAccourdingTabControl(@RequestParam(value = "ownerid") String ownerid,
			@RequestParam(value = "tabindex") Integer tabindex) {
		return this.bookingService.fetchBookingByCopyFromAccourdingTabControl(ownerid, tabindex);
	}

	@PostMapping("/add-booking")
	public ResponseEntity<?> addBooking(@Valid @RequestBody BookingWrapperBean wrapperBean) {
		return this.bookingService.addBooking(wrapperBean);
	}

//	@PutMapping("/update-booking1")
//	public ResponseEntity<?> updateBooking1(@Valid @RequestBody BookingRequestBean bookingRequestBean, @RequestBody BookingAltBldgRequestBean bookingAltBldgRequestBean)  {
//		return this.bookingService.updateBooking1(bookingRequestBean,bookingAltBldgRequestBean);
//	}

	@PostMapping("/update-booking")
	public ResponseEntity<?> updateBooking(@RequestBody BookingWrapperBean wrapperBean) {
		return this.bookingService.updateBooking(wrapperBean);
	}

	@GetMapping("/check-Bldgcode-and-Wing-and-Flatnum-and-Ownerid-exists")
	public ResponseEntity<?> checkBldgcodeAndWingAndFlatnumAndOwneridExists(
			@RequestParam(value = "bldgcode") String bldgcode, @RequestParam(value = "wing") String wing,
			@RequestParam(value = "flatnum") String flatnum, @RequestParam(value = "ownerid") String ownerid) {
		return this.bookingService.checkBldgcodeAndWingAndFlatnumAndOwneridExists(bldgcode, wing, flatnum, ownerid);
	}

	@DeleteMapping("/delete-booking")
	public ResponseEntity<?> deleteBooking(@RequestParam(value = "bldgcode") String bldgcode,
			@RequestParam(value = "wing") String wing, @RequestParam(value = "flatnum") String flatnum,
			@RequestParam(value = "ownerid") String ownerid) {
		return this.bookingService.deleteBooking(bldgcode, wing, flatnum, ownerid);
	}

	@GetMapping("fetch-Stampduty-booking")
	public Integer fetchBookingStampdutyAmount(@RequestParam(value = "intPrmFlatCost") Integer intPrmFlatCost,
			@RequestParam(value = "StrPrmUniType") String StrPrmUniType,
			@RequestParam(value = "StrPrmBookDate") String StrPrmBookDate) {
		return this.bookingService.fetchBookingStampdutyAmount(intPrmFlatCost, StrPrmUniType, StrPrmBookDate);

	}
}
