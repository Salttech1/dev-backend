package kraheja.adminexp.overheads.dataentry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.overheads.dataentry.entity.Overheaddepositdtls;
import kraheja.adminexp.overheads.dataentry.entity.OverheaddepositdtlsCK;

@Repository
public interface OverheaddepositdtlsRepository extends JpaRepository<Overheaddepositdtls, OverheaddepositdtlsCK> {

	 
	Overheaddepositdtls findByOverheaddepositdtlsCK_OhdeConnocodeAndOverheaddepositdtlsCK_OhdePeriod(String connocode, String period) ; 

	@Query(value ="select sum(nvl(ohde_depositeamt,0)) from  overheaddepositdtls where trim(ohde_conno)=:connocode ",nativeQuery = true)
	public Double fetchDepositeAmtbyConnocode(String connocode);
}