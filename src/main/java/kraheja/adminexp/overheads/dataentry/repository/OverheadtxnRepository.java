package kraheja.adminexp.overheads.dataentry.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.overheads.dataentry.entity.Overheadtxn;
import kraheja.adminexp.overheads.dataentry.entity.OverheadtxnCK;
@Repository

public interface OverheadtxnRepository extends JpaRepository<Overheadtxn, OverheadtxnCK> {
	Overheadtxn findByOverheadtxnCK_OhddConnocodeAndOverheadtxnCK_OhddBillperiodAndOverheadtxnCK_OhddSupplementarybill(String connocode, String billperiod, String supplementarybill) ; 
	
	List<Overheadtxn> findByOverheadtxnCK_OhddConnocode(String connocode) ;
	
	@Query(value ="select nvl(ohdd_advance,0) as ohdd_advance ,nvl(ohdd_prvadvamt,0) as ohdd_prvadvamt ,"
			+ "nvl(Ohdd_PrvActPay,0) as Ohdd_PrvActPay ,nvl(ohdd_payamt,0) as ohdd_payamt,max(nvl(ohdd_cumamt,0)) as ohdd_cumamt, "
			+"(select sum(nvl(ohde_depositeamt,0)) from  overheaddepositdtls where trim(ohde_conno)=:connocode) as ohdd_depositeamt "
			+ "from overheadtxn where ohdd_billperiod=(select max(ohdd_billperiod) from overheadtxn where trim(ohdd_connocode)=:connocode ) and trim(ohdd_connocode)=:connocode "
			+ "group by ohdd_advance,ohdd_prvadvamt,Ohdd_PrvActPay,ohdd_payamt " ,nativeQuery = true)
	
	public List<Tuple> fetchPrvBillData(String connocode);
	
	
}
