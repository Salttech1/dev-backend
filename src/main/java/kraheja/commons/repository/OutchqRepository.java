package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Outchq;
import kraheja.commons.entity.OutchqCK;

@Repository
public interface OutchqRepository extends JpaRepository<Outchq, OutchqCK> {
	
//	Outchq findByOutchqCK_OutchqProprietorAndOutchqCK_OutchqCoyAndOutchqCK_OutchqBankAndOutchqCK_OutchqSeriesAndOutchqCK_OutchqNumAndOutchqCK_OutchqTranser(String proprietor, String coy, String bank, String series, String num, String transer) ;

	@Query("select e from Outchq e where TRIM(e.outchqCK.outchqProprietor) = :proprietor and TRIM(e.outchqCK.outchqCoy) = :coy and TRIM(e.outchqCK.outchqBank) =:bank and "
			+ "TRIM(e.outchqCK.outchqNum) =:chqnum")
	Outchq findByOutchqCK_OutchqProprietorAndOutchqCK_OutchqCoyAndOutchqCK_OutchqBankAndOutchqCK_OutchqNum(
			String proprietor, String coy, String bank, String chqnum);
}


