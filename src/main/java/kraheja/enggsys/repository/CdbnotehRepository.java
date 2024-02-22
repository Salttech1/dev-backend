package kraheja.enggsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Cdbnoteh;
import kraheja.enggsys.entity.Cdbnoteh.CdbnotehCK;

@Repository
public interface CdbnotehRepository extends JpaRepository<Cdbnoteh, CdbnotehCK> {

	 
	Cdbnoteh findByCdbnotehCK_CdbnhDbnoteser(String dbnoteser) ; 

}