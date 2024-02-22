package kraheja.adminexp.overheads.dataentry.repository;
import java.util.List;
import java.util.Set;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.overheads.dataentry.entity.Overheadcons;
import kraheja.adminexp.overheads.dataentry.entity.OverheadconsCK;

@Repository
public interface OverheadconsRepository extends JpaRepository<Overheadcons, OverheadconsCK> {
	
	Overheadcons findByOverheadconsCK_OhdhConnocode(String ohdhconnocode) ; 
	
	Overheadcons findByOverheadconsCK_OhdhConsumerno(String ohdhConsumerno) ; 

	@Query(value ="select (select loc_name from location where loc_code=ohdh_location) as locname,\r\n"
			+ "(SELECT ENT_NAME FROM ENTITY WHERE ENT_CLASS='OHCOY' AND ENT_CHAR1=ohdh_billcoy) as billcoyname,\r\n"
			+ "(SELECT COY_NAME FROM COMPANY WHERE COY_CODE=ohdh_paycoy and coy_closedate='01/JAN/2050') as paycompany\r\n"
			+ "from overheadcons where \r\n"
			+ "TRIM(ohdh_connocode) = :ohdhconnocode " ,nativeQuery = true)
	public List<Tuple> fetchLocnamePaycoynameBillcoyname(String ohdhconnocode);
														
	//@Query("select e  from  Overheadcons e WHERE trim(e.overheadconsCK.ohdhConno) in :conncodeList")
	@Query("select e  from  Overheadcons e WHERE trim(e.ohdhConno) in :conncodeList")
	public List<Overheadcons> findByOhdhConnoInAndOhdhBilltype(Set<String> conncodeList);
	
	public List<Overheadcons> findByOverheadconsCK_OhdhConsumernoAndOhdhBilltype(String string, String string2);
	
	@Query(value="select count(*)  from  overheadtxn  WHERE trim(ohdd_Connocode) = :ohdhconnocode"
			,nativeQuery = true)
	String findByBillGeneratedInoverheadtxn(String ohdhconnocode);
	
	
	@Query(value="select trim(coy_curryear) || '04' from company where trim(coy_code) =:ohddCoycode "
			,nativeQuery = true)
	String findMaxYearmonthIncompay(String ohddCoycode);
	
	
}
