package kraheja.purch.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Pbilld ;
import kraheja.purch.entity.PbilldCK;
@Repository
public interface PbilldRepository extends JpaRepository<Pbilld, PbilldCK> {
	 
	Pbilld findByPbilldCK_PbldSerAndPbilldCK_PbldLineno(String ser, Integer lineno) ; 
	
	Pbilld findByPbldSuppbillnoAndPbldSuppbilldt(String billNo, LocalDate billDate) ; 

	@Query("select pd from Pbilld  pd where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldSuppbillno) = :suppBillNo")
	Pbilld findByPbldPartyCodeAndPbldSuppbillno(String partyCode, String suppBillNo) ; 
	
	@Query("select pd from Pbilld  pd where trim(pd.pbilldCK.pbldSer) in :ser")
	List<Pbilld> findByBySerIn(Set<String> ser) ; 
	
	@Modifying
	@Query("Delete Pbilld p where trim(p.pbilldCK.pbldSer) = :ser")
	void deletePbilldBySer(String ser);
	
	@Query("Select pd.pbldQuantity, pd.pbilldCK.pbldSer, pd.pbldDbqty from Pbilld pd where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldSuppbillno) = :suppBillNo and trim(pd.pbldMatgroup) =:matGroup")
	List<Object[]> findPbilldQtyAndSerByPartyAndSuppBillAndMatGroup(String partyCode, String suppBillNo, String matGroup);
	
	@Modifying
	@Query("Update Pbilld pd SET pd.pbldQuantity = :quantity, pd.pbldDequantity = :quantity, pd.pbldDbqty = :dbQty, pd.pbldUserid = :userid, pd.pbldSite = :site, pd.pbldToday = :today where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldSuppbillno) = :suppBillNo and trim(pd.pbldMatgroup) =:matGroup")
	void updatePbilldQtyByPartyAndSuppBillAndMatGroup(Double quantity, Double dbQty,String userid, String site, LocalDateTime today, String partyCode, String suppBillNo, String matGroup);

}