package kraheja.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Booking;
import kraheja.sales.entity.BookingCK;

@Repository
public interface BookingRepository extends JpaRepository<Booking, BookingCK>, CrudRepository<Booking, BookingCK>{


@Query("select e  from Booking e WHERE trim(e.bookingCK.bookBldgcode) = :BldgCode AND trim(e.bookingCK.bookWing) = :Wing AND trim(e.bookingCK.bookFlatnum) = :FlatNum")
	Booking findByBldgCodeAndWingAndFlatNum(String BldgCode,String Wing, String FlatNum); 
	
	//List<Booking> findByBookingCK_bookOwnerid(String ownerId);
	Booking findByBookingCK_bookOwnerid(String ownerId);
	
	@Query("select e from Booking e where trim(e.bookBroker) = :BrokCode")
	List<Booking> findByBrokCode(String BrokCode); 
	
	Booking findByBookingCK_BookBldgcodeAndBookingCK_BookWingAndBookingCK_BookFlatnumAndBookingCK_BookOwnerid(String bldgcode, String wing, String flatnum, String ownerid) ;
	
	
}
