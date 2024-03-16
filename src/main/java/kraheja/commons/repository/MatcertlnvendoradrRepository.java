package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Matcertlnvendoradr;
import kraheja.commons.entity.MatcertlnvendoradrCK;

@Repository
public interface MatcertlnvendoradrRepository extends JpaRepository<Matcertlnvendoradr, MatcertlnvendoradrCK> {

	 
	Matcertlnvendoradr findByMatcertlnvendoradrCK_McvaAdsegmentAndMatcertlnvendoradrCK_McvaAdownerAndMatcertlnvendoradrCK_McvaAdtypeAndMatcertlnvendoradrCK_McvaAdser(String adsegment, String adowner, String adtype, String adser) ; 

	
	@Query(value="Select * from MATCERTLNVENDORADR where trim(mcva_partycode) =:partyCode and mcva_partytype=:partyType",nativeQuery=true)
	Matcertlnvendoradr findByMcvaPartycodeAndMcvaPartytype(String partyCode, String partyType);
}
