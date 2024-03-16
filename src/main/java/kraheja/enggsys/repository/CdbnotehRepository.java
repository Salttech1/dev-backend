package kraheja.enggsys.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Cdbnoteh;
import kraheja.enggsys.entity.Cdbnoteh.CdbnotehCK;
import kraheja.purch.entity.Dbnoteh;

@Repository
public interface CdbnotehRepository extends JpaRepository<Cdbnoteh, CdbnotehCK> {

	 
	Cdbnoteh findByCdbnotehCK_CdbnhDbnoteser(String dbnoteser) ; 
	
	List<Cdbnoteh> findByCdbnhCertnoIn(Set<String> certNum) ; 
	
	@Query("SELECT cdh FROM Cdbnoteh cdh WHERE	 trim(cdh.cdbnotehCK.cdbnhDbnoteser) in :cdbnoteser ")
	List<Cdbnoteh> findByCdbnotehCK_CdbnhDbnoteserIn(Set<String> cdbnoteser) ; 
	
}