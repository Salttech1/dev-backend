package kraheja.purch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Pbillvat ;
import kraheja.purch.entity.PbillvatCK;
@Repository
public interface PbillvatRepository extends JpaRepository<Pbillvat, PbillvatCK> {

	List<Pbillvat> findByPbillvatCK_PblvSer(String ser) ; 
	
	@Modifying
	@Query("Delete Pbillvat p where trim(p.pbillvatCK.pblvSer) = :ser")
	void deletePbillvatBySer(String ser);

}