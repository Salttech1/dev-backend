
package kraheja.fd.deposit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.fd.deposit.bean.ActiveDepositBean;
import kraheja.fd.deposit.entity.Deposit;
import kraheja.fd.deposit.entity.DepositCK;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, DepositCK> {
	
	@Query("select e  from Deposit e WHERE trim(e.depositCK.depDepositor) = :depositor AND trim(e.depositCK.depCoy) = :companyCode AND trim(e.depositCK.depReceiptnum) = :receiptNum")
	Deposit findByDepositorIdAndCompanyCodeAndReceiptNum(String depositor, String companyCode, String receiptNum); 
	
	@Query("select e  from Deposit e WHERE trim(e.depositCK.depCoy) = :companyCode AND trim(e.depositCK.depReceiptnum) = :receiptNum")
	Deposit findByCompanyCodeAndReceiptNum(String companyCode, String receiptNum); 

	@Query("select e.depPrintrev  from Deposit e WHERE trim(e.depositCK.depDepositor) = :depositor AND trim(e.depositCK.depCoy) = :companyCode AND trim(e.depositCK.depReceiptnum) = :receiptNum")
	Double fetchDepPrintRev(String depositor, String companyCode, String receiptNum); 
	
	@Query("select e  from Deposit e WHERE trim(e.depositCK.depDepositor) = :depositor AND trim(e.depositCK.depCoy) = :companyCode")
	List<Deposit> findByDepositorIdAndCompanyCode(String depositor, String companyCode); 

	@Query("select e.depositCK.depReceiptnum  from Deposit e WHERE trim(e.depositCK.depDepositor) = :depositorId")
	List<String> findRecieptNumbersByDepositorId(String depositorId);

	@Modifying
	@Query("update Deposit d set d.depDisdate = :depDisdate, d.depToday=:today, d.depSite=:site, d.depUserid=:userid WHERE trim(d.depositCK.depDepositor) = :depositor AND trim(d.depositCK.depReceiptnum) = :receiptNum") 
	void updateMaturedRecieptDisDate(LocalDateTime depDisdate, String depositor, String receiptNum, LocalDateTime today, String site, String userid);

	@Query("select new kraheja.fd.deposit.bean.ActiveDepositBean(e.depositCK.depDepositor,e.depositCK.depReceiptnum,e.depStaffyn,e.depDepamount,e.depDepdate,e.depIntrate,e.depIntfreq,e.depMatdate,e.depProprietor,(select max(d.depintCK.dinIntupto) FROM Depint d WHERE d.depintCK.dinCoy=e.depositCK.depCoy and d.depintCK.dinDepositor=e.depositCK.depDepositor and d.depintCK.dinReceiptnum=e.depositCK.depReceiptnum) as maxIntupto,e.depositCK.depCoy,nvl(e.depAccint, 0),nvl(e.depIntpaidytd,0),nvl(e.depTaxpaidytd,0),nvl(e.depTds,0),\r\n"
			+ "	(select d.deptrIntpaidytd FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrIntpaidytd,\r\n"
			+ "	(select d.deptrTaxpaidytd FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrTaxpaidytd,\r\n"
			+ "	(select d.deptrAccint FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrAccint,\r\n"
			+ "	(select d.deptrTds FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrTds,\r\n"
			+ "	(select nvl(d.deptrTds15hyn, 'N') FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrTds15hyn,\r\n"
			+ "	(select nvl(d.deptrTds15gyn, 'N') FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrTds15gyn,\r\n"
			+ " (select nvl(d.deptrTaxalwaysyn, 'N') FROM Depositor d WHERE d.depositorCK.deptrCoy=e.depositCK.depCoy and d.depositorCK.deptrDepositor=e.depositCK.depDepositor ) as deptrTaxalwaysyn)\r\n"
			+ "	from Deposit e WHERE trim(e.depositCK.depCoy) = :depCoy and e.depDisdate is null and e.depMatdate >= :toUptodate ORDER BY e.depositCK.depDepositor, e.depositCK.depReceiptnum")
	List<ActiveDepositBean> fetchActiveDeposits(String depCoy, LocalDateTime toUptodate);
	
	@Modifying
	@Query("UPDATE Deposit e \r\n"
			+ "SET e.depIntpaidytd=(SELECT t.tdepIntpaidytd FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositCK.depDepositor AND t.tDepintCK.tdinReceiptnum = e.depositCK.depReceiptnum),\r\n"
			+ "e.depTaxpaidytd=(SELECT t.tdepTaxpaidytd FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositCK.depDepositor AND t.tDepintCK.tdinReceiptnum = e.depositCK.depReceiptnum),\r\n"
			+ "e.depAccint=(SELECT t.tdepAccint FROM Tdepint t WHERE t.tDepintCK.tdinDepositor =e.depositCK.depDepositor AND t.tDepintCK.tdinReceiptnum = e.depositCK.depReceiptnum),\r\n"
			+ "e.depTds=(SELECT t.tdepTds FROM Tdepint t WHERE t.tDepintCK.tdinDepositor = e.depositCK.depDepositor AND t.tDepintCK.tdinReceiptnum = e.depositCK.depReceiptnum),\r\n"
			+ "e.depSite=:site,\r\n"
			+ "e.depUserid=:userid,\r\n"
			+ "e.depToday=:today\r\n"
			+ " WHERE e.depositCK.depCoy=:coy and e.depositCK.depReceiptnum IN (SELECT d.tDepintCK.tdinReceiptnum FROM Tdepint d WHERE d.tdinSessid =:sessionId)")
	void updateDepositReceiptsOnInterestCalc(String coy, Double sessionId, String site, String userid, LocalDateTime today);
	
	@Query("select COUNT(e) FROM Deposit e WHERE trim(e.depositCK.depCoy) = :coy")
	Integer countOfAllDeposits(String coy);
	
	@Query("select COUNT(e) FROM Deposit e WHERE trim(e.depositCK.depCoy) = :coy AND e.depDisdate IS NULL")
	Integer countOfConsideredDeposits(String coy);
	
}
