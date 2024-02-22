package kraheja.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import feign.Param;
import kraheja.sales.entity.Flatpay;
import kraheja.sales.entity.FlatpayCK;

@Repository
public interface FlatpayRepository extends JpaRepository<Flatpay, FlatpayCK>, CrudRepository<Flatpay, FlatpayCK>{
	//@Modifying
	@Query(value="select * from flatpay WHERE trim(fpay_ownerid) = :ownerid ",nativeQuery = true)
	List<Flatpay> FindByFlatpayOwnerId(@Param("ownerid") String ownerid);
	
	//@Modifying
	//	@Query("select e  from  Flatpay e WHERE trim(e.flatpayCK.fpayOwnerid)= :ownerId")
	//	List<Flatpay> findByFlatpaydata(String ownerId);
	
	List<Flatpay> findByFlatpayCK_FpayOwnerid(String FpayOwnerid);
	
	List<Flatpay> findByFlatpayCK_FpayBldgcodeAndFlatpayCK_FpayFlatnum(String BldgCode,String Flatnum);
	
	Flatpay findByFlatpayCK_FpayBldgcodeAndFlatpayCK_FpayFlatnumAndFlatpayCK_FpayOwneridAndFlatpayCK_FpayDuedateAndFlatpayCK_FpayNarrative(String bldgcode, String flatnum, String ownerid, String duedate, String narrative) ;

	@Query(value = "delete from flatpay WHERE trim(fpay_ownerid) = ?", nativeQuery = true )
	void deleteflatPayByOwnerId(String ownerId);
	
}