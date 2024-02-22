package kraheja.purch.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.TempMatAuthprint;
import kraheja.purch.entity.TempMatAuthprintCK;

@Repository
public interface TempMatAuthprintRepository extends JpaRepository<TempMatAuthprint, TempMatAuthprintCK> {
	TempMatAuthprint findByTempmatauthprintCK_SessidAndTempmatauthprintCK_AuthAuthnumAndTempmatauthprintCK_AutdSuppbillno(Double sessid, String authNum, String billNum);
	
	List<TempMatAuthprint> findByTempmatauthprintCK_Sessid(Double sessid);
	
	@Modifying
	@Query(value = "delete from TempMatAuthprint t where t.tempmatauthprintCK.sessid = :sessid")
	void deleteBySessid(Double sessid);
}	