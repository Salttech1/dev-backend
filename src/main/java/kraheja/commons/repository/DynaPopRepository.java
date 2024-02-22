package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Dynapop;

@Repository
public interface DynaPopRepository extends JpaRepository<Dynapop, String>{
	
	@Query("select e  from Dynapop e WHERE trim(e.dynPopid) = :dynapopId")
	Dynapop findByDynapopId(String dynapopId); 
}
