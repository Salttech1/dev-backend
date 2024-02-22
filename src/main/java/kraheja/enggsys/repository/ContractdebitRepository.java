package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Contractdebit;
import kraheja.enggsys.entity.Contractdebit.ContractdebitCK;

@Repository
public interface ContractdebitRepository extends JpaRepository<Contractdebit, ContractdebitCK> {

	 
	Contractdebit findByContractdebitCK_ccdDebitno(String debitNo) ; 
	
	@Query("Select cd from Contractdebit cd where trim(cd.ccdDebittype) = :debitType and"
			+ " ('ALL' IN (:authnum) or trim(cd.ccdAuthnum) in (:authnum)) and"
			+ " ('ALL' IN (:recId) or trim(cd.ccdContract) in (:recId)) and"
			+ " ('ALL' IN (:certType) or trim(cd.ccdCerttype) in (:certType)) and"
			+ " ('ALL' IN (:runser) or trim(cd.ccdRunser) in (:runser)) and"
			+ " ('ALL' IN (:bldgCode) or trim(cd.ccdBldgcode) in (:bldgCode))"
			+ "order by cd.ccdContraContract")
	List<Contractdebit> findContractDebitByDebitTypeAndAuthnumAndRecIdAndCertAndRunserAndBldg(String debitType, String authnum, 
			String recId, String certType, String runser, String bldgCode) ;
	
	@Query("Select cd from Contractdebit cd where trim(cd.ccdDebittype) = :debitType and trim(cd.ccdContract) = :recId and trim(cd.ccdCerttype) = :certType and trim(cd.ccdRunser) = :runser order by cd.ccdContraContract")
	List<Contractdebit> findByCcdDebittypeAndCcdContractAndCcdCerttypeAndCcdRunser(String debitType,String recId, String certType,String runser);
	
	@Query("Select cd from Contractdebit cd where trim(cd.ccdDebittype) = :debitType and trim(cd.ccdBldgcode) = :bldgCode order by cd.ccdContraContract")
	List<Contractdebit> findByCcdDebittypeAndCcdBldgcode(String debitType,String bldgCode);
	
	@Query("Select sum(cd.ccdDebitAmount) From  Contractdebit cd   where trim(cd.ccdDebittype) = :debitType and trim(cd.ccdAuthnum) = :authnum")
	Double findSumOfDebitAmountByDebitTypeAndAuthnum(String debitType, String authnum);
	
	@Modifying
	@Query("Delete from Contractdebit a where trim(a.ccdAuthnum) = :authNum")
	void deleteContractdebitByAuthNum(String authNum);

}