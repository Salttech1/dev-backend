package kraheja.commons.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.DbEntity;
import kraheja.commons.entity.EntityCK;

@Repository
public interface EntityRepository extends JpaRepository<DbEntity, EntityCK> {

//	@Query("SELECT e FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1")
//	public DbEntity findByEntityCk_EntClassAndEntityCk_EntChar1(String entClass, String entChar1); 

	@Query("SELECT NVL(e.entityCk.entId,' ') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1")
	public String findByEntityCk_EntClassAndEntityCk_EntChar1(String entClass, String entChar1);

	@Query("SELECT NVL(e.entNum1, 0.0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public Double findByEntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("SELECT NVL(e.entName, '') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public String findEntNameEntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("SELECT e.entityCk.entChar2, e.entDate3 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public Object[] findEntChar2AndDate3EntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Modifying
	@Query("UPDATE DbEntity e SET e.entNum1 = :entNum1 WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public void updateIncrementCounter(String entClass, String entId, Double entNum1);

	@Modifying
	@Query("UPDATE DbEntity e SET e.entNum1 = :entNum1 WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId  AND trim(e.entityCk.entId)=:entId AND  Trim(e.entityCk.entChar1)=trim(:site)")
	public void updateIncrementCounterWithSite(String entClass, String entId, Double entNum1, String site);

	@Query("SELECT NVL(e.entNum1,0),NVL(e.entNum2,0),NVL(e.entNum4,0),NVL(e.entNum5,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1 AND trim(e.entityCk.entId)=:entId and (:matDate Between e.entDate1 and e.entDate2 or :depDate Between e.entDate1 and e.entDate2 )")
	public String findByEntityCk_EntClassAndEntityCk_EntIdAndEntityCk_EntChar1(String entClass, String entId,
			String entChar1, LocalDate matDate, LocalDate depDate);

	@Query("SELECT NVL(e.entNum1,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1 AND trim(e.entityCk.entId)=:entId")
	public Double findByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId(String entId, String entClass,
			String entChar1);

	@Query("SELECT NVL(e.entNum2,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1 AND trim(e.entityCk.entId)=:entId")
	public Double findNum2ByEntityCk_EntClassAndEntityCk_EntChar1AndEntityCk_EntId(String entId, String entClass,
			String entChar1);

	@Lock(LockModeType.PESSIMISTIC_READ) // SPECIFY THE LOCK MODE HERE FOR RESTRIC DUPLICAT TRANSER CREATION
											// @Sazzad-01-11-23
	@Query("SELECT e.entNum1,e.entityCk.entChar1,e.entityCk.entChar2 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public String fetchByEntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("SELECT e.entNum1,e.entityCk.entChar2,e.entityCk.entChar3 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId AND trim(e.entityCk.entChar1)=:entChar1")
	public String fetchByEntityCk_EntClassAndEntityCk_EntIdAndEntityCk_EntChar1(String entClass, String entId,
			String entChar1);

	@Query("SELECT e.entityCk.entChar3 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId ")
	public String fetchByChar3EntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("SELECT e.entNum1,entNum2 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId AND :date BETWEEN e.entDate1 AND e.entDate2")
	public String findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates(String entClass, String entId,
			LocalDate date);

	@Query("Select e.entNum1 FROM DbEntity e WHERE	trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)!=:entId AND entNum2 <=:depmonths and entNum3 >=:depmonths")
	public Double findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityNum2AndNum3(String entClass, String entId,
			Double depmonths);

	@Query("SELECT NVL(e.entName,' ') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1")
	public String findEntNameEntityCk_EntClassAndEntityCk_EntChar1(String entClass, String entChar1);

	@Query("SELECT NVL(e.entNum1,0), NVL(e.entNum2,0), NVL(e.entNum3,0), NVL(e.entNum4,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public String findByNumsEntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("SELECT NVL(e.entityCk.entId,' '), NVL(e.entName,' ') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass and e.entityCk.entId <> '00000'")
	public List<Tuple> findIdAndNameByEntClass(String entClass);

	@Query("SELECT trim(ent_char1) FROM DbEntity  WHERE trim(ent_class) = 'ENGG' AND trim(ent_id) = 'FINBL' AND trim(ent_char1) =:partyCode AND ent_char2 = 'S' AND trim(ent_char3) =:buildingCode AND trim(ent_char4) = 'ALLNEWPART'")
	public String findByEntityCk_EntClassAndEntityCk_EntChar1_EntChar3(String partyCode, String buildingCode);

	@Query("SELECT trim(ent_char2) FROM DbEntity WHERE trim(ent_class) = 'ENGG' AND trim(ent_id) = 'FINBL' AND trim(ent_char1) = 'ALLCERTYPE'")
	public String findEntityForFinalBill();

	@Query("SELECT NVL(e.entNum1,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1 AND :date BETWEEN e.entDate1 AND e.entDate2")
	public Integer findEntNum1ByEntityCk_EntClassAndEntityCk_EntChar1BetweenEntityDates(String entClass,
			String entChar1, LocalDate date);

	@Query("SELECT NVL(e.entNum2,0) FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entChar1)=:entChar1 AND :date BETWEEN e.entDate1 AND e.entDate2")
	public Integer findEntNum2ByEntityCk_EntClassAndEntityCk_EntChar1BetweenEntityDates(String entClass,
			String entChar1, LocalDate date);

	@Query("SELECT NVL(e.entRemark,'') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass")
	public List<String> findEntRemarkByEntityCk_EntClass(String entClass);

	@Query("SELECT e.entityCk.entChar1 FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId AND trim(e.entityCk.entChar1)=:entChar1 AND trim(e.entityCk.entChar2)=:entChar2 AND trim(e.entityCk.entChar3)=:entChar3 AND trim(e.entChar4)=:entChar4 ")
	public String fetchByChar1EntityCk_EntClassAndEntityCk_EntIdAndChar1ToChar4(String entClass, String entId,
			String entChar1, String entChar2, String entChar3, String entChar4);

	@Query("SELECT NVL(e.entRemark,'') FROM DbEntity e WHERE trim(e.entityCk.entClass)=:entClass AND trim(e.entityCk.entId)=:entId")
	public String findEntRemarkByEntityCk_EntClassAndEntityCk_EntId(String entClass, String entId);

	@Query("select count(e) from DbEntity e where e.entityCk.entClass = 'STATE' and e.entityCk.entId= :state and e.entityCk.entChar3= 'U'")
	Integer getEntityCount(String state);

	@Query(value = "select nvl(ent_num1, 0) from entity where ent_class = '#OGIN' and ent_id = '#OGIN' and ent_char1='Q'", nativeQuery = true)
	double fetchIntereRate();

	@Query(value = "select entName from DbEntity e where trim(e.entityCk.entClass)='STATE' AND trim(e.entityCk.entId)=:stateCode")
	String fetchStateNameByEntClassAndEntId(String stateCode);

	@Query(value = "select ENT_CHAR1 from ENTITY where ENT_CLASS='TXIBL' AND TRIM(ENT_ID)=:entId", nativeQuery = true)
	String fetchChar1ByEntClassAndEntId(String entId);

	@Query(value = "select ent_char1,ent_char2,ent_char3,ent_char4,ent_num1,ent_num2 from entity where trim(ent_class)='INCBE' and trim(ent_id)<>'00000' and trim(ent_id)= ? AND ENT_DATE2='01/JAN/2050' order by ent_num4", nativeQuery = true)
	List<Tuple> fetchCompanyEntity(String companyCode);

	@Query(value = "select ent_name,ent_char1 from entity where ent_class='INVIC' and ent_id<>'00000'", nativeQuery = true)
	List<Tuple> fetchEntityAcMejor();

	@Query(value = "select nvl(ent_char2,' ') from entity where ent_class='INVIC' and trim(ent_char1) = ?", nativeQuery = true)
	String fetchLocalPartyGSTYN(String acMajor);

	@Query(value = "select nvl(ent_num1,' ') from entity where ent_class='INVIC' and trim(ent_char1) = ?", nativeQuery = true)
	String fetchLocalPartyGSTAmount(String acMajor);

	@Query("SELECT COUNT(e) FROM DbEntity e WHERE trim(e.entityCk.entClass) = 'ADMBL' AND trim(e.entityCk.entChar1) = :acMajor AND trim(e.entityCk.entId) = 'ACMAJ' AND trim(e.entityCk.entChar2)= 'N'")
	long countByEntClassAndEntChar1(String acMajor);

	@Query("SELECT COUNT(e) FROM DbEntity e WHERE trim(e.entityCk.entClass) = 'GSTRC' AND trim(e.entityCk.entChar1) = :acMajor AND e.entityCk.entChar3 IS NULL AND (trim(e.entityCk.entId) = '02' OR trim(e.entityCk.entId) = '01')")
	long countByGSTRCConditions(String acMajor);
	
	@Query(value="Select ent_num1 from entity where ent_class = 'TDSPA' AND ent_id = 'AAPAN' AND :suppBillDate between ent_date1 and ent_date2", nativeQuery=true)
	public Double findByEntityCk_EntClassAndEntityCk_EntIdBetweenEntityDates2(LocalDate suppBillDate);

}
