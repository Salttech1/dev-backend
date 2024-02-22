package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Authmatgroupnarrdtl;
import kraheja.purch.entity.AuthmatgroupnarrdtlCK;

@Repository
public interface AuthmatgroupnarrdtlRepository extends JpaRepository<Authmatgroupnarrdtl, AuthmatgroupnarrdtlCK> {

	
	@Modifying
	@Query("Delete from Authmatgroupnarrdtl a where trim(a.authmatgroupnarrdtlCK.amndAuthnum) = :authNum")
	void deleteAuthmatgroupnarrdtlByAuthNum(String authNum);
}