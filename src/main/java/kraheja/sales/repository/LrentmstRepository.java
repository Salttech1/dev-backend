// Developed By  - 	vikram.p
// Developed on - 03-01-24
// Mode  - Data Entry
// Purpose - Lrentmst Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

import kraheja.purch.entity.Dbnoted;
import kraheja.sales.entity.Lrentmst ;
import kraheja.sales.entity.LrentmstCK;
@Repository
public interface LrentmstRepository extends JpaRepository<Lrentmst, LrentmstCK> {

	 
	List<Lrentmst> findByLrentmstCK_RentmUnitidAndLrentmstCK_RentmUnitgroupAndLrentmstCK_RentmTypeAndLrentmstCK_RentmToAndLrentmstCK_RentmPropcodeAndLrentmstCK_RentmUnitno(String unitid, String unitgroup, String type, LocalDateTime to, String propcode, String unitno) ; 
//	List<Lrentmst> deleteLrentmstByLrentmstCK_RentmUnitidAndLrentmstCK_RentmUnitgroupAndLrentmstCK_RentmTypeAndLrentmstCK_RentmToAndLrentmstCK_RentmPropcodeAndLrentmstCK_RentmUnitno(String unitid, String unitgroup, String type, LocalDateTime to, String propcode, String unitno) ;
//	void deleteDbnotedBySer(String ser);
	void deleteLrentmstByLrentmstCK_RentmUnitidAndLrentmstCK_RentmUnitgroupAndLrentmstCK_RentmTypeAndLrentmstCK_RentmToAndLrentmstCK_RentmPropcodeAndLrentmstCK_RentmUnitno(String unitid, String unitgroup, String type, LocalDateTime to, String propcode, String unitno) ;

//	@Query("select sum(d.dbndQuantity) from Dbnoted d where trim(d.dbndPartyCode)  = :partyCode and trim(d.dbndSuppBillNo) = :supplierBillNo and trim(d.dbnotedCK.dbndDbnoteser)  <> :dbNoteSer")
//	Integer findSumOfQuantity(String partyCode, String supplierBillNo, String dbNoteSer);
//	
//	@Modifying
//	@Query("Delete Dbnoted d where trim(d.dbnotedCK.dbndDbnoteser) = :ser")
	

}