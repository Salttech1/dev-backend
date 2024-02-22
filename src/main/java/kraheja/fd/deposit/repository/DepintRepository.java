package kraheja.fd.deposit.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import kraheja.fd.deposit.entity.Depint;
import kraheja.fd.deposit.entity.DepintCK;


public interface DepintRepository extends JpaRepository<Depint, DepintCK>, CrudRepository<Depint, DepintCK>{
	@Query("select NVL(TO_CHAR(max(e.depintCK.dinIntupto), 'DD/MM/YYYY'), ' ') FROM Depint e WHERE trim(e.depintCK.dinReceiptnum) = :dinReceiptnum and trim(e.depintCK.dinDepositor) = :dinDepositor and trim(e.depintCK.dinCoy) = :dinCoy" )
	String findMaxIntUpto(String dinReceiptnum,String dinDepositor, String dinCoy);

	@Query("SELECT NVL(TO_CHAR(e.depintCK.dinIntfrom, 'DD/MM/YYYY'), ' '), NVL(TO_CHAR(e.depintCK.dinIntupto, 'DD/MM/YYYY'), ' ') FROM Depint e WHERE trim(e.depintCK.dinReceiptnum)=:receiptNumber")
	public List<String> findByReceiptNumber(String receiptNumber);

	//	@Query("select SUM(e.dinInterest) FROM Depint e WHERE trim(e.depintCK.dinDepositor) = :depositor and trim(e.depintCK.dinCoy) = :coy AND e.depintCK.dinIntupto " )
	//	Double findSumOfIntPaidByCoyAndDepositor(String coy, String depositor, LocalDate intUpto);

	public List<Depint> findByDepintCK_DinIntfromGreaterThanEqualAndDepintCK_DinIntuptoAndDepintCK_DinCoyAndDinSessidIsNotNull(LocalDate DinIntfrom, LocalDate DinIntupto, String coy);

	public List<Depint> findByDepintCK_DinDepositorAndDepintCK_DinReceiptnumAndDepintCK_DinCoy(String depositor, String dinReceiptNum,  String coy);
	
	@Query(value ="select din_coy as dinCoy ,dIN_depositor as dinDepositor,sum(din_interest) as interest,sum(din_tds) as tds,sum(din_interest - din_tds) as cheqamt, COUNT(*) as receiptyn,\r\n"
			+ "(select upper(Trim(deptr_title)) || ' ' || upper(Trim(deptr_name)) as names from depositor where deptr_depositor = dIN_depositor and deptr_coy = din_coy) as partyName,\r\n"
			+ "(SELECT RTRIM(ADR_ADLINE1) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND rtrim(adr_adowner) = rtrim(din_coy) || rtrim(dIN_depositor)) as adrAdline1,\r\n"
			+ "(SELECT RTRIM(ADR_ADLINE2) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND rtrim(adr_adowner) = rtrim(din_coy) || rtrim(dIN_depositor)) as adrAdline2,\r\n"
			+ "(SELECT (RTRIM(ADR_ADLINE3) || ' ' || RTRIM(ADR_ADLINE4) || ' ' || RTRIM(ADR_ADLINE5)) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND rtrim(adr_adowner) = rtrim(din_coy) || rtrim(dIN_depositor)) as adrAdline3,\r\n"
			+ "(SELECT ((Case RTRIM(ADR_TOWNSHIP) when '.' then '' else RTRIM(ADR_TOWNSHIP) end) || ' ' || RTRIM(ADR_CITY) || '-' || RTRIM(ADR_PINCODE)) FROM v_address_decode WHERE adr_adsegment = 'PARTY' AND rtrim(adr_adowner) = rtrim(din_coy) || rtrim(dIN_depositor)) as adrAdline4,\r\n"
			+ "(SELECT deptr_intpaidytd FROM depositor WHERE deptr_depositor = dIN_depositor AND deptr_coy = din_coy) as intPaidytd,\r\n"
			+ "(SELECT deptr_taxpaidytd FROM depositor WHERE deptr_depositor = dIN_depositor AND deptr_coy = din_coy) as taxPaidytd\r\n"
			+ "from depint where \r\n"
			+ "TRIM(din_coy) = :coy and (din_intupto BETWEEN :fromDate AND :toDate) and\r\n"
			+ "(TRIM(din_depositor) BETWEEN :depFrom AND :depTo)\r\n"
			+ "group by (din_coy,din_depositor)" ,nativeQuery = true)
	public List<Tuple> fetchInterestChequePrintingDetail(String coy, String fromDate, String toDate, String depFrom, String depTo);
}
