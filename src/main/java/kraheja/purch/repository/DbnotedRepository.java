package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Dbnoted;
import kraheja.purch.entity.DbnotedCK;

@Repository
public interface DbnotedRepository extends JpaRepository<Dbnoted, DbnotedCK> {

	List<Dbnoted> findByDbnotedCK_DbndDbnoteser(String dbnoteser) ;
	
	List<Dbnoted> findByDbndSuppBillNo(String supplierBillNo) ;

	@Query("select sum(d.dbndQuantity) from Dbnoted d where trim(d.dbndPartyCode)  = :partyCode and trim(d.dbndSuppBillNo) = :supplierBillNo and trim(d.dbnotedCK.dbndDbnoteser)  <> :dbNoteSer")
	Integer findSumOfQuantity(String partyCode, String supplierBillNo, String dbNoteSer);
	
	@Modifying
	@Query("Delete Dbnoted d where trim(d.dbnotedCK.dbndDbnoteser) = :ser")
	void deleteDbnotedBySer(String ser);
}