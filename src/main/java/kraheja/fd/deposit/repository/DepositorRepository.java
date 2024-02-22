package kraheja.fd.deposit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.fd.deposit.entity.Depositor;
import kraheja.fd.deposit.entity.DepositorCK;

@Repository
public interface DepositorRepository extends JpaRepository<Depositor, DepositorCK>, CrudRepository<Depositor, DepositorCK>{

	@Query("select e  from Depositor e WHERE trim(e.depositorCK.deptrDepositor) = :depositorId AND trim(e.depositorCK.deptrCoy) = :deptrCoy")
	Depositor findByDepositorIdAndCompanyCode(String depositorId, String deptrCoy); 
	
	List<Depositor> findByDepositorCK_DeptrDepositor(String depositor);
	
	List<Depositor> findByDepositorCK_DeptrCoy(String coy);

//	Depositor findByDepositorCK_DeptrDepositorAndDepositorCK_DeptrCoy(String depositorId, String companyCode);

	@Query("select SUM(e.deptrIntpaidytd) FROM Depositor e WHERE trim(e.depositorCK.deptrDepositor) = :depositor AND trim(e.depositorCK.deptrCoy) = :coy")
	Double findSumOfIntPaidByCoyAndDepositor(String coy, String depositor);
	
	@Query("select SUM(e.deptrTaxpaidytd) FROM Depositor e WHERE trim(e.depositorCK.deptrDepositor) = :depositor AND trim(e.depositorCK.deptrCoy) = :coy")
	Double findSumOfTaxPaidByCoyAndDepositor(String coy, String depositor);
	
	@Query("select COUNT(e) FROM Depositor e WHERE trim(e.depositorCK.deptrCoy) = :coy")
	Integer countOfAllDepositors(String coy);
	
	@Modifying
	@Query("UPDATE Depositor e \r\n"
			+ "SET e.deptrIntpaidytd=(SELECT t.tdeptrIntpaidytd FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositorCK.deptrDepositor AND ROWNUM = 1),\r\n"
			+ "e.deptrTaxpaidytd=(SELECT t.tdeptrTaxpaidytd FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositorCK.deptrDepositor AND ROWNUM = 1),\r\n"
			+ "e.deptrAccint=(SELECT t.tdeptrAccint FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositorCK.deptrDepositor AND ROWNUM = 1),\r\n"
			+ "e.deptrTds=(SELECT t.tdeptrTds FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositorCK.deptrDepositor AND ROWNUM = 1),\r\n"
			+ "e.deptrSite=:site,\r\n"
			+ "e.deptrUserid=:userid,\r\n"
			+ "e.deptrToday=:today\r\n"
			+ " WHERE e.depositorCK.deptrCoy=:coy and e.depositorCK.deptrDepositor IN (SELECT t.tDepintCK.tdinDepositor FROM Tdepint t WHERE t.tdinSessid =:sessionId)")
	void updateDepositorsYtdInterestCalc(String coy, Double sessionId, String site, String userid, LocalDateTime today);
}