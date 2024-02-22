package kraheja.fd.deposit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import kraheja.fd.deposit.entity.Tdepint;
import kraheja.fd.deposit.entity.TdepintCK;


public interface TDepintRepository extends JpaRepository<Tdepint, TdepintCK>, CrudRepository<Tdepint, TdepintCK>{
	
	@Modifying
	@Query(value = "TRUNCATE TABLE TDEPINT", nativeQuery = true)
	void truncate();
	
	List<Tdepint> findByTdinSessid(Double sessionId);

	@Query("select count(distinct e.tDepintCK.tdinDepositor) from Tdepint e where e.tdinTds > 0")
	String findDistinctTdsCount();
	
}