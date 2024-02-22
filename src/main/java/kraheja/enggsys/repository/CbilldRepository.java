package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Cbilld;
import kraheja.enggsys.entity.Cbilld.CbilldCK;

@Repository
public interface CbilldRepository extends JpaRepository<Cbilld, CbilldCK> {

	 
	List<Cbilld> findByCbilldCK_CbldSer(String ser) ; 
	
	Cbilld findByCbilldCK_CbldSerAndCbilldCK_CbldLineno(String ser, Double lineno);

}