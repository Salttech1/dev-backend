package kraheja.purch.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Dbnotevat;
import kraheja.purch.entity.DbnotevatCK;

@Repository
public interface DbnotevatRepository extends JpaRepository<Dbnotevat, DbnotevatCK> {

	List<Dbnotevat> findByDbnotevatCK_DbnvSer(String ser) ; 
	
	@Modifying
	@Query("Delete Dbnotevat d where trim(d.dbnotevatCK.dbnvSer) = :ser")
	void deleteDbnotevatBySer(String ser);
}