package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Partycontactinfo;
import kraheja.commons.entity.PartycontactinfoCK;

@Repository
public interface PartycontactinfoRepository extends JpaRepository<Partycontactinfo, PartycontactinfoCK> {

	Partycontactinfo findByPartycontactinfoCK_PciPartycode(String partycode);

}
