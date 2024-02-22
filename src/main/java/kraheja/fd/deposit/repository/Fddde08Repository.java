package kraheja.fd.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kraheja.fd.deposit.entity.Fdddde08;
import kraheja.fd.deposit.entity.Fdddde08CK;


public interface Fddde08Repository extends JpaRepository<Fdddde08, Fdddde08CK>{
	
	@Modifying
	@Query(value = "TRUNCATE TABLE Fdddde08", nativeQuery = true)
	void truncate();
}
