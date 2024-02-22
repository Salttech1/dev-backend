package kraheja.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Inchq;
import kraheja.commons.entity.InchqCK;

@Repository
public interface InchqRepository extends JpaRepository<Inchq, InchqCK> {

	@Query("Select e from Inchq e Where trim(e.inchqPartycode)= :partyCode AND trim(e.inchqCk.inchqRecnum)= :receiptNum")
	List<Inchq> findByPartyCodeAndRecieptNum(String partyCode, String receiptNum);
}
