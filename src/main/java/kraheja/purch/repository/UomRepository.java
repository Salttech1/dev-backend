package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Uom;
import kraheja.purch.entity.UomCK;

@Repository
public interface UomRepository extends JpaRepository<Uom, UomCK> {

	 
	Uom findByUomCK_UomMatgroupAndUomCK_UomCode(String matgroup, String code) ; 
	
	
	List<Uom> findByUomCK_UomCode(String uom) ;
	
	

}