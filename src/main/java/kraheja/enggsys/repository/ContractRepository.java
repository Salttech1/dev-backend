package kraheja.enggsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Contract;
import kraheja.enggsys.entity.Contract.ContractCK;
@Repository
public interface ContractRepository extends JpaRepository<Contract, ContractCK> {

	 
	Contract findByContractCK_ConttContract(String contract) ; 
	
	Contract findByConttBldgcodeAndConttCoyAndConttWorkcodeAndConttPartycode(String bldgcode, String coy, String workcode, String partycode) ; 

	Contract findByConttBldgcodeAndConttCoyAndConttWorkcodeAndConttConttor(String bldgcode, String coy, String workcode, String conttcode) ; 
	
//	@Query("SELECT c.conttWorkcode , c.conttPartycode , (SELECT b.bldgProject FROM Building b WHERE trim(b.buildingCK.bldgCode) = trim(c.conttBldgcode) AND " +
//            "(b.bldgClosedate IS NULL OR b.bldgClosedate = :closedate)) AS c.conttProject , " +
//            "(SELECT e.bldgBldgtype FROM Building e WHERE trim(e.buildingCK.bldgCode) = " +
//            "(SELECT r.bldgmapCK.bmapAbldgcode FROM Bldgmap r WHERE trim(r.bldgmapCK.bmapEbldgcode) = c.conttBldgcode)) AS BldgType " +
//            "FROM contract c WHERE trim(c.contractCK.conttContract) = :contractid")
//	 Tuple findCodesByContract(String contractid);
	
	
	

}