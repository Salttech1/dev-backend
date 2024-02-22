package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Coymap;
import kraheja.commons.entity.Coymap.CoymapCK;

@Repository
public interface CoymapRepository extends JpaRepository<Coymap, CoymapCK> {
	 
	Coymap findByCoymapCK_CmapEcoycodeAndCoymapCK_CmapAcoycode(String ecoycode, String acoycode) ; 

}