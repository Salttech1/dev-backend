package kraheja.purch.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Dbnoteh;
import kraheja.purch.entity.DbnotehCK;

@Repository
public interface DbnotehRepository extends JpaRepository<Dbnoteh, DbnotehCK> {
	 
	Dbnoteh findByDbnotehCK_DbnhDbnoteser(String dbnoteser) ; 
	
	@Query("SELECT dh FROM Dbnoteh dh WHERE	 trim(dh.dbnotehCK.dbnhDbnoteser) in :dbnoteser ")
	List<Dbnoteh> findByDbnotehCK_DbnhDbnoteserIn(Set<String> dbnoteser) ; 
	
	List<Dbnoteh> findByDbnhSuppbillno(String suppBillNo) ; 
	
	List<Dbnoteh> findByDbnhAuthnoIn(Set<String> authNum) ; 
	
	@Query("SELECT dh.dbnhTranser FROM Dbnoteh dh WHERE	trim(dh.dbnhCoy) = :coy AND trim(dh.dbnhBldgcode) = :buildingCode AND trim(dh.dbnhPartycode) = :partyCode  AND trim(dh.dbnhSuppbillno) = :suppBillNo ")
	String dbnotehSerByCoyAndBuildingAndSupplierAndSuppBillNo(String coy, String buildingCode, String partyCode, String suppBillNo);
	
	@Query("SELECT count(dh) FROM Dbnoteh dh WHERE	trim(dh.dbnhCoy) = :coy AND trim(dh.dbnhBldgcode) = :buildingCode AND trim(dh.dbnhPartycode) = :partyCode  AND trim(dh.dbnhSuppbillno) = :suppBillNo ")
	Integer dbnotehCountByCoyAndBuildingCodeAndPartyAndSuppBillNo(String coy, String buildingCode, String partyCode, String suppBillNo);
	
	@Modifying
	@Query("Delete Dbnoteh d where trim(d.dbnotehCK.dbnhDbnoteser) = :ser")
	void deleteDbnotehBySer(String ser);

	@Query("SELECT	d.dbnotehCK.dbnhDbnoteser FROM Dbnoteh d WHERE trim(d.dbnhAuthno) = :authNum")
	List<String> fetchDbnotehSerListByAuthNum(String authNum);

	@Query("select d.dbnhAuthno  from Dbnoteh d where trim(d.dbnotehCK.dbnhDbnoteser) = :ser AND d.dbnhAuthno is not null")
	String findDnotehAuthNumBySer(String ser);
	
	@Modifying
	@Query("Update Dbnoteh d SET d.dbnhAuthno = null , d.dbnhAuthdate = null, d.dbnhSite = :site, d.dbnhToday = :today, d.dbnhUserid = :userid where trim(d.dbnhAuthno) = :authNo")
	void revertDbnotehAuthorisation(String authNo, String site, LocalDateTime today, String userid);
	
	@Query("SELECT dh FROM Dbnoteh dh WHERE	 trim(dh.dbnhBldgcode) = :buildingCode AND trim(dh.dbnhPartycode) = :partyCode  AND trim(dh.dbnhSuppbillno) in :suppBillNo ")
	List<Dbnoteh> dbnotehSerByCoyAndBuildingAndSupplierAndSuppBillNoIn(String buildingCode, String partyCode, Set<String> suppBillNo);
}