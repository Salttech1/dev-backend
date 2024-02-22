package kraheja.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Outcorp;
import kraheja.sales.entity.OutcorpCK;

@Repository
public interface OutcorpRepository extends JpaRepository<Outcorp, OutcorpCK>, CrudRepository<Outcorp, OutcorpCK>{

//	@Query("select e  from Depositor e WHERE trim(e.depositorCK.deptrDepositor) = :depositorId AND trim(e.depositorCK.deptrCoy) = :deptrCoy")
//	Depositor findByDepositorIdAndCompanyCode(String depositorId, String deptrCoy); 
//	
//	List<Depositor> findByDepositorCK_DeptrCoy(String companyCode);
//
////	Depositor findByDepositorCK_DeptrDepositorAndDepositorCK_DeptrCoy(String depositorId, String companyCode);
//
//	@Procedure(name = "SPR_DEPR_INSERTDETAILS")
//	void addDepositor(
//			@Param("depositor") String depositor,
//			@Param("title") String title,
//			@Param("name") String name,
//			@Param("proprietor") String proprietor,
//			@Param("coy") String coy,
//			@Param("depamount") Double depamount,
//			@Param("grossint") Double grossint,
//			@Param("accint") Double accint,
//			@Param("tds") Double tds,
//			@Param("remarks") String remarks,
//			@Param("city") String city,
//			@Param("site") String site,
//			@Param("userid") String userid,
//			@Param("today") Date today,
//			@Param("origsite") String origsite,
//			@Param("deptype") String deptype,
//			@Param("taxyn") String taxyn,
//			@Param("tds15hyn") String tds15hyn,
//			@Param("tds15gyn") String tds15gyn,
//			@Param("intpayedytd") Double intpayedytd,
//			@Param("taxpaidytd") Double taxpaidytd,
//			@Param("taxrecytd") Double taxrecytd,
//			@Param("clubref") String clubref,
//			@Param("pannum1") String pannum1,
//			@Param("pannum2") String pannum2,
//			@Param("birthdate") Date birthdate,
//			@Param("insupd") String insupd
//			)
;
}