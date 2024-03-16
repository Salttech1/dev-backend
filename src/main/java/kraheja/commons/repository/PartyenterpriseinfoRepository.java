package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Partyenterpriseinfo;
import kraheja.commons.entity.PartyenterpriseinfoCK;

import java.time.LocalDateTime;

@Repository
public interface PartyenterpriseinfoRepository extends JpaRepository<Partyenterpriseinfo, PartyenterpriseinfoCK> {

	Partyenterpriseinfo findByPartyenterpriseinfoCK_PeiPartycodeAndPartyenterpriseinfoCK_PeiPartytypeAndPartyenterpriseinfoCK_PeiEntcodeAndPartyenterpriseinfoCK_PeiEnttypeAndPartyenterpriseinfoCK_PeiEnddate(
			String partycode, String partytype, String entcode, String enttype, LocalDateTime enddate);

	@Query(value = "select * from PARTYENTERPRISEINFO where trim(pei_partycode)=:partyCode and trim(pei_partytype)= :partyType", nativeQuery = true)
	Partyenterpriseinfo findByPartyTypeandPartyCode(String partyCode, String partyType);

}
