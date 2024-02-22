package kraheja.adminexp.billing.dataentry.adminAdvancePayment.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.Admadvance1;
import kraheja.adminexp.billing.dataentry.adminAdvancePayment.entity.AdmadvanceCK1;


@Repository
public interface AdmadvanceRepository1 extends JpaRepository<Admadvance1, AdmadvanceCK1> {
	Admadvance1 findByAdmadvanceCK_AdvnSer(String ser);

	@Query("select a from Admadvance1 a where trim(a.admadvanceCK.advnSer) = :ser or trim(a.advnPinvno) = :pinvno")
	List<Admadvance1> findByAdmadvanceCK_AdvnSerOrAdvnPinvno(String ser, String pinvno);
	
	@Query("SELECT	NVL(SUM(e.advnAdvanceamt), 0) FROM Admadvance1 e WHERE (e.advnStatus = 5 OR e.advnStatus = 7) AND TRIM(e.advnPartycode) = :partyCode AND TRIM(e.advnBldgcode)  = :bldgCode AND TRIM(e.advnCoy) = :coy AND TRIM(e.advnPartytype)  = :partyType")
	Double findAdvnAmountSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhstatusAndAdblhtype(String partyCode, String bldgCode, String coy, String partyType);
	
}
