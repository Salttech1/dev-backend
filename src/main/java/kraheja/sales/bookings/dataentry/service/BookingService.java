package kraheja.sales.bookings.dataentry.service;

import org.springframework.http.ResponseEntity;

import kraheja.sales.bean.request.BookingAltBldgRequestBean;
import kraheja.sales.bean.request.BookingRequestBean;
import kraheja.sales.bean.request.BookingWrapperBean;

public interface BookingService {

////	ResponseEntity<?> fetchBookingByBldgCodeAndWingAndFlatNum(Sting brokercode) throws ParseException;
//
//	ResponseEntity<?> addBooking(BookingRequestBean bookingRequestBean) throws ParseException;
//
//	ResponseEntity<?> updateBooking(BookingRequestBean bookingRequestBean) throws ParseException;
//
//	ResponseEntity<?> checkBldgCodeAndWingAndFlatnumExists(String bldgCode,String Wing,String flatNum);
	
	//ResponseEntity<?> fetchBookingByBldgCodeAndWingAndFlatNum(String brokercode) throws ParseException;
	
	ResponseEntity<?> fetchBookingByBldgcodeAndWingAndFlatnumAndOwnerid(String  bldgcode, String  wing, String  flatnum, String  ownerid) ;

	//ResponseEntity<?> addBooking(BookingRequestBean bookingRequestBean,BookingAltBldgRequestBean bookingAltBldgRequestBean)  ;

	ResponseEntity<?> addBooking(BookingWrapperBean wrapperBean);
	
	//ResponseEntity<?> updateBooking(BookingRequestBean bookingRequestBean,BookingAltBldgRequestBean bookingAltBldgRequestBean)  ;
	
	ResponseEntity<?> updateBooking(BookingWrapperBean wrapperBean);
	
	ResponseEntity<?> updateBooking1(BookingRequestBean bookingRequestBean,BookingAltBldgRequestBean bookingAltBldgRequestBean)  ;
	
	//ResponseEntity<?> updateBooking(BookingRequestBean bookingRequestBean)  ;

	ResponseEntity<?> deleteBooking(String  bldgcode, String  wing, String  flatnum, String  ownerid); 

	ResponseEntity<?> checkBldgcodeAndWingAndFlatnumAndOwneridExists(String  bldgcode, String  wing, String  flatnum, String  ownerid) ;

	ResponseEntity<?> fetchBookingByCopyFromAccourdingTabControl(String ownerId,Integer tabIndex);
	
	//ResponseEntity<?> fetchBookingStampdutyAmount(Integer IntPrmFlatCost,String StrPrmUniType,String StrPrmBookDate);
	public Integer fetchBookingStampdutyAmount(Integer IntPrmFlatCost,String StrPrmUniType,String StrPrmBookDate);
}
