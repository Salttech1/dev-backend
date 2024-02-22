package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Cdbnoted;
import kraheja.enggsys.entity.Cdbnoted.CdbnotedCK;

@Repository
public interface CdbnotedRepository extends JpaRepository<Cdbnoted, CdbnotedCK> {
	 
	Cdbnoted findByCdbnotedCK_CdbndDbnoteserAndCdbnotedCK_CdbndLineno(String dbnoteser, Double lineno);
	
	List<Cdbnoted> findByCdbnotedCK_CdbndDbnoteser(String dbnoteser);

}