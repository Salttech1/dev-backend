package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Dc;
import kraheja.purch.entity.DcCK;
@Repository
public interface DcRepository extends JpaRepository<Dc, DcCK> {

	List<Dc> findByDcCK_DcpEntryno(String dcpEntryno);
	
	Dc findByDcCK_DcpEntrynoAndDcCK_DcpDcnoAndDcCK_DcpSuppcodeNot(String ser, String dcno, String partycode);
	
	Dc findByDcCK_DcpDcnoAndDcCK_DcpSuppcode(String dcno, String partycode);
	
	@Modifying
	@Query("DELETE From Dc d where trim(d.dcCK.dcpSuppcode) = :suppCode and trim(d.dcpSuppbill) = :suppBill")
	void deleteDcBySuppCodeAndSuppBill(String suppCode, String suppBill);
	
	@Modifying
	@Query("Delete Dc d where trim(d.dcCK.dcpEntryno) = :entryno")
	void deleteDcByEntryNo(String entryno);
	
//	@Query("SELECT distinct dcp_dcno,dcp_billdt, dcp_suppbill FROM dc WHERE dcp_dcno = :dcno AND dcp_suppcode = :partycode and dcp_entryno != :serNumber")
	
}