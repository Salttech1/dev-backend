package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Matcertestimateactual;
import kraheja.purch.entity.MatcertestimateactualCK;


@Repository
public interface MatcertestimateactualRepository extends JpaRepository<Matcertestimateactual, MatcertestimateactualCK> {

	 
//	Matcertestimateactual findByMatcertestimateactualCK_ClseProjcodeAndMatcertestimateactualCK_ClseMatcerttypeAndMatcertestimateactualCK_ClseMatcertcodeAndMatcertestimateactualCK_ClseGroupcodeAndMatcertestimateactualCK_ClseSubgroupcodeAndMatcertestimateactualCK_ClsePartytypeAndMatcertestimateactualCK_ClsePartycode(String projcode, String matcerttype, String matcertcode, String groupcode, String subgroupcode, String partytype, String partycode) ; 

	Matcertestimateactual findByMatcertestimateactualCK_ClseProjcodeAndMatcertestimateactualCK_ClseMatcerttypeAndMatcertestimateactualCK_ClseMatcertcodeAndMatcertestimateactualCK_ClsePartycodeAndMatcertestimateactualCK_ClsePartytype(String projcode, String matcerttype, String matcertcode, String partycode, String partytype) ; 

	@Query("select e.clseContractval + round(0.02 * e.clseContractval, 0) from Matcertestimateactual e where e.matcertestimateactualCK.clseMatcerttype = :matcerttype and e.matcertestimateactualCK.clsePartytype = :partytype and e.matcertestimateactualCK.clsePartycode=:partycode AND e.matcertestimateactualCK.clseProjcode= :projcode AND e.matcertestimateactualCK.clseMatcertcode = :matcertcode")
	Double findClseContractValByClseProjcodeAndClseMatcerttypeAndClseMatcertcodeAndClsePartycode(String projcode, String matcerttype, String matcertcode, String partytype, String partycode);

	@Query("select e.clseEstimateamt + round(0.02 * e.clseEstimateamt, 0) from Matcertestimateactual e where e.matcertestimateactualCK.clseMatcerttype = :matcerttype and e.matcertestimateactualCK.clsePartytype = :partytype and e.matcertestimateactualCK.clsePartycode=:partycode AND e.matcertestimateactualCK.clseProjcode= :projcode AND e.matcertestimateactualCK.clseMatcertcode = :matcertcode")
	Double findClseEstimateamtValByClseProjcodeAndClseMatcerttypeAndClseMatcertcodeAndClsePartycode(String projcode, String matcerttype, String matcertcode, String partytype, String partycode) ; 
}