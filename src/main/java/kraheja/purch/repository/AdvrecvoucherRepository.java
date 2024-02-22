package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Advrecvoucher;
import kraheja.purch.entity.AdvrecvoucherCK;

@Repository
public interface AdvrecvoucherRepository extends JpaRepository<Advrecvoucher, AdvrecvoucherCK> {

	@Query("SELECT sum(e.adrvAmount),sum(e.adrvCgstamt),sum(e.adrvSgstamt),sum(e.adrvIgstamt),sum(e.adrvUgstamt) FROM Advrecvoucher e  WHERE e.advrecvoucherCK.adrvOrigdocnum = :authnum")
	String findSumOfGstPerc(String authnum);
	
	@Modifying
	@Query("Delete FROM Advrecvoucher a WHERE trim(a.adrvOrigsys) = :origSys and trim(a.advrecvoucherCK.adrvOrigdocnum) = :docNum")
	void deleteAdvreVoucherByOrigSysAndOrigDocNum(String origSys, String docNum);
	
	List<Advrecvoucher> findByAdvrecvoucherCK_adrvOrigdocnum(String origdocnum);
}