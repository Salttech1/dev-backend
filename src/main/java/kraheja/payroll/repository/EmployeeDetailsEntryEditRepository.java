package kraheja.payroll.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.poi.hpsf.Decimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.payroll.entity.Empjobinfo;
import kraheja.payroll.entity.EmpjobinfoCK;

@Repository
@Transactional
public interface EmployeeDetailsEntryEditRepository extends JpaRepository<Empjobinfo, EmpjobinfoCK>{
	@Query(value = "select ('Select ' || efor_formula || ' from v_empsalarypackage where vspk_empcode = ' || '''WD009''' || ' and vspk_todate = ' || '''01-JAN-2050''') as Q from emppayformula where efor_coy = 'UNIQ' and efor_emptype = 'S' and efor_jobtype = 'P' and efor_earndedcode = 'MTHLYGROSS'", nativeQuery = true)
	String GetMonthGrossQuery(String empCode,String empCoy,String empType,String empJobtype);
	
	@Query(value = ":MyQuery", nativeQuery = true)
	Double GetMonthGross(String MyQuery);
	
	@Query(value="SELECT ejin_company as coy,\r\n"
			+ "       ejin_emptype as emptype,\r\n"
			+ "       ejin_jobtype as jobtype \r\n"
			+ "FROM   empjobinfo\r\n"
			+ "WHERE  trim(ejin_empcode) = :empCode \r\n"
			+ "       AND ejin_effectiveupto = (SELECT MAX(ejin_effectiveupto)\r\n"
			+ "                                 FROM   empjobinfo\r\n"
			+ "                                 WHERE  trim(ejin_empcode) = :empCode )",nativeQuery = true)
	Tuple GetEmployeeJobinfoForFormula(String empCode);
	
	
	@Query(value="SELECT 'Select ' || efor_formula || ' from ' || efor_table || ' where ' || efor_condition as formula\r\n"
			+ "FROM   emppayformula\r\n"
			+ "WHERE  trim(efor_coy) = :empCoy \r\n"
			+ "       AND trim(efor_emptype) = :empType\r\n"
			+ "       AND trim(efor_jobtype) = :empJobtype\r\n"
			+ "       AND trim(efor_earndedcode) = :earndedcode",nativeQuery = true)
	String GetFormula(String empCoy,char empType,char empJobtype,String earndedcode);
	
	@Query(value="SELECT CSPK_EARNDEDCODE AS earndedcode,\r\n"
			+ "       EDHM_DISPLAYNAME AS EarnDesc,\r\n"
			+ "       CSPK_PAYCYCLE    AS paycycle,\r\n"
			+ "       CSPK_RATECYCLE   AS ratecycle\r\n"
			+ "FROM   coysalarypackage,\r\n"
			+ "       EARNDEDMASTER\r\n"
			+ "WHERE  EDHM_EARNDEDCODE = CSPK_EARNDEDCODE\r\n"
			+ "       AND TRIM(CSPK_COY) = :coycode\r\n"
			+ "       AND CSPK_EFFECTIVEUPTO = :closeDate\r\n"
			+ "       AND CSPK_SHOWINSALPACK = 'Y'\r\n"
			+ "       AND CSPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE\r\n"
			+ "                                FROM   EARNDEDMASTER\r\n"
			+ "                                WHERE  EDHM_EARNDEDTYPE = 'A')\r\n"
			+ "ORDER  BY EDHM_COMPUTENO,\r\n"
			+ "          CSPK_RATECYCLE DESC,\r\n"
			+ "          CSPK_PAYCYCLE DESC,\r\n"
			+ "          CSPK_EARNDEDCODE",nativeQuery = true)
	List<Tuple> fetchCompanySalPackage(String coycode,String closeDate);
	
	@Query(value="SELECT CSPK_EARNDEDCODE,\r\n"
			+ "       EDHM_DISPLAYNAME AS DedDesc,\r\n"
			+ "       CSPK_PAYCYCLE    AS payment,\r\n"
			+ "       CSPK_RATECYCLE   AS Modes\r\n"
			+ "FROM   coysalarypackage,\r\n"
			+ "       EARNDEDMASTER\r\n"
			+ "WHERE  EDHM_EARNDEDCODE = CSPK_EARNDEDCODE\r\n"
			+ "       AND Trim(CSPK_COY) = :coycode\r\n"
			+ "       AND CSPK_EFFECTIVEUPTO = :closeDate\r\n"
			+ "       AND CSPK_SHOWINSALPACK = 'Y'\r\n"
			+ "       AND CSPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE\r\n"
			+ "                                FROM   EARNDEDMASTER\r\n"
			+ "                                WHERE  EDHM_EARNDEDTYPE = 'D')\r\n"
			+ "       AND edhm_schemeyn = 'N'\r\n"
			+ "ORDER  BY EDHM_COMPUTENO,\r\n"
			+ "          CSPK_RATECYCLE DESC,\r\n"
			+ "          CSPK_PAYCYCLE DESC,\r\n"
			+ "          CSPK_EARNDEDCODE",nativeQuery = true)
	List<Tuple> fetchCompanySalDedPackage(String coycode,String closeDate);
	
	@Query(value="SELECT CSPK_EARNDEDCODE As SchemeCode,\r\n"
			+ "       EDHM_DISPLAYNAME AS SchemeDesc,\r\n"
			+ "       edhm_schemenoautoyn as Autoyn,\r\n"
			+ "       Case when edhm_schemenoautoyn = 'Y' then cspk_schemeno when edhm_schemenoautoyn = 'N' then '.' else '.' end SchemeNo,\r\n"
			+"        Case when CSPK_EARNDEDCODE = 'PF'  then (select pfsl_percempl from pfslab where :joinpaymonth between pfsl_stmonthyr and pfsl_endmonthyr)  when CSPK_EARNDEDCODE = 'PF&PENS' then (select pfsl_percempl from pfslab where :joinpaymonth between pfsl_stmonthyr and pfsl_endmonthyr) else 0 END SchemePercentage,\r\n"
			+"        Case when CSPK_EARNDEDCODE = 'ESIC' then '  ' else '.' end schemecentre,\r\n"
			+"        Case when CSPK_EARNDEDCODE = 'ESIC' then 'Y' else 'N' end applicableyn\r\n"
			+ "FROM   coysalarypackage,EARNDEDMASTER\r\n"
			+ "WHERE  cspk_earndedcode IN (SELECT edhm_earndedcode\r\n"
			+ "                            FROM   earndedmaster\r\n"
			+ "                            WHERE  edhm_schemeyn = 'Y')\r\n"
			+ "       AND EDHM_EARNDEDCODE = CSPK_EARNDEDCODE\r\n"
			+ "       AND Trim(cspk_coy) = :coycode\r\n"
			+ "       AND cspk_effectiveupto = :closeDate\r\n"
			+ "       AND CSPK_SHOWINSALPACK = 'Y'",nativeQuery = true)
	List<Tuple> fetchCompanySchemeDetails(String coycode,String joinpaymonth,String closeDate);
	
	@Query(value="SELECT CLVM_LEAVECODE       AS leavecode,\r\n"
			+ "       CLVM_MAXDAYSALLOWED  AS maxdaysallowed,\r\n"
			+ "       CLVM_ALLOWEDTOCF     AS allowedtocf,\r\n"
			+ "       CLVM_ALLOWEDTOENCASH AS allowedtoencash,\r\n"
			+ "       CLVM_MAXDAYSENC      AS maxdaysenc,\r\n"
			+ "       CLVM_MAXDAYSCF       AS maxdayscf\r\n"
			+ "FROM   coyleavemaster\r\n"
			+ "WHERE  Trim(clvm_coy) = :coycode\r\n"
			+ "       AND Trim(clvm_emptype) = :emptype\r\n"
			+ "       AND CLVM_LEAVECODE <> 'O'\r\n"
			+ "       AND :joindate BETWEEN clvm_effectivefrom AND clvm_effectiveupto",nativeQuery = true)
	List<Tuple> fetchCompanyLeaveDetails(String coycode,String joindate,String emptype);
	
	@Query(value="select GETACYEAR(to_date(:joindate,'dd-MM-yyyy'),'PAY') from dual",nativeQuery = true)
	String GetAcyear(String joindate);
	
	@Query(value="SELECT CASE\r\n"
			+ "         WHEN EXTRACT(MONTH FROM Trunc(To_date(:joindate), 'MM')) < 4 THEN ((:CoyEntitleDays/12) * (Last_day(:joindate) - To_date(:joindate) + 1) / (Last_day(:joindate) - Trunc(To_date(:joindate), 'MM') + 1)) + ( (:CoyEntitleDays/12) * (12 - ( EXTRACT(MONTH FROM Trunc(To_date(:joindate), 'MM')) + 9 ) ))\r\n"
			+ "         ELSE ((:CoyEntitleDays/12) * (Last_day(:joindate) - To_date(:joindate) + 1) / (Last_day(:joindate) - Trunc(To_date(:joindate), 'MM') + 1)) + ( (:CoyEntitleDays/12) * (12 - ( EXTRACT(MONTH FROM Trunc(To_date(:joindate), 'MM')) - 3 ) ))\r\n"
			+ "       END                                                               Entitle\r\n"
			+ "FROM   dual",nativeQuery = true)
	double CalLeaveEntitle(String joindate,double CoyEntitleDays);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select e.entityCk.entChar1 as lastcode from DbEntity e  where e.entityCk.entClass = 'PAYRL' and e.entityCk.entId = 'CODE1'")
	String GetLastcode();
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select e.entityCk.entChar3 as lastcode from DbEntity e  where e.entityCk.entClass = 'PAYRL' and e.entityCk.entId = 'CODE1'")
	String GetLastcodeChar3();
	
	@Query(value="select ent_char2 as prefix from entity where ent_class = 'PAYRL' and ent_id = 'CODE1'",nativeQuery = true)
	String GetPrefix();
	
	@Modifying
	@Query("UPDATE DbEntity e SET e.entityCk.entChar1 = :entChar WHERE trim(e.entityCk.entClass) = :entClass AND trim(e.entityCk.entId) = :entId")
	public void updateIncrementEmpcode(String entClass, String entId, String entChar);
	
	@Modifying
	@Query("UPDATE DbEntity e SET e.entityCk.entChar3 = :entChar WHERE trim(e.entityCk.entClass) = :entClass AND trim(e.entityCk.entId) = :entId")
	public void updateIncrementEmpcodeEntChar3(String entClass, String entId, String entChar);
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT COALESCE(c.cspkSchemeno, '.') \r\n" +
		       "FROM Coysalarypackage c \r\n" +
		       "WHERE c.coysalarypackageCK.cspkEarndedcode IN (SELECT e.earndedmasterCK.edhmEarndedcode \r\n" +
		                                   "FROM Earndedmaster e \r\n" +
		                                   "WHERE e.edhmSchemeyn = 'Y') \r\n" +
		       "AND TRIM(c.coysalarypackageCK.cspkCoy) = :coyCode \r\n" +
		       "AND TRIM(c.coysalarypackageCK.cspkEarndedcode) = :schemeCode\r\n" +
		       "AND c.cspkEffectiveupto = :closedate \r\n" +
		       "AND c.cspkShowinsalpack = 'Y'\r\n")
	String GetSchemeNo(String coyCode, String schemeCode,LocalDate closedate);
	
	
	@Modifying
	@Query("UPDATE Coysalarypackage c \r\n"
			+ "SET c.cspkSchemeno = :schemeNo \r\n"
			+ "WHERE Trim(c.coysalarypackageCK.cspkCoy) = :coyCode \r\n"
			+ "  AND Trim(c.coysalarypackageCK.cspkEarndedcode) = :schemeCode")
	public void updateIncrementEmplSchemeCode(String coyCode, String schemeCode, String schemeNo);
	
	@Modifying
	@Query("UPDATE Coysalarypackage c \r\n"
			+ "SET c.cspkSchemeno = :schemeNo \r\n"
			+ "WHERE Trim(c.coysalarypackageCK.cspkCoy) = :coyCode \r\n"
			+ "  AND Trim(c.coysalarypackageCK.cspkEarndedcode) in :schemeCode ")
	public void updateIncrementEmplSchemeCodePF(String coyCode, List<String> schemeCode, String schemeNo);
	
	@Query("SELECT COUNT(e) FROM Empsalarypaid e WHERE trim(e.empsalarypaidCK.espdEmpcode) = :empCode")
	double GetEmpSalarypaidCountForEmpcode(String empCode);	
	
	@Modifying 
	@Query("delete Emppersonal e where trim(e.emppersonalCK.eperEmpcode)=:empcode")
	public void deleteEmppersonal(String empcode);
	
	@Modifying 
	@Query("delete Empjobinfo e where trim(e.empjobinfoCK.ejinEmpcode)=:empcode")
	public void deleteEmpjobinfo(String empcode);
	
	@Modifying 
	@Query("delete Empassetinfo e where trim(e.empassetinfoCK.eassEmpcode)=:empcode")
	public void deleteEmpassetinfo(String empcode);
	
	@Modifying 
	@Query("delete Empeducation e where trim(e.empeducationCK.eeduEmpcode)=:empcode")
	public void deleteEmpeducation(String empcode);
	
	@Modifying 
	@Query("delete Empfamily e where trim(e.empfamilyCK.efamEmpcode)=:empcode")
	public void deleteEmpfamily(String empcode);
	
	@Modifying 
	@Query("delete Empreference e where trim(e.empreferenceCK.erefEmpcode)=:empcode")
	public void deleteEmpreference(String empcode);
	
	@Modifying 
	@Query("delete Empexperience e where trim(e.empexperienceCK.eexpEmpcode)=:empcode")
	public void deleteEmpexperience(String empcode);
	
	@Modifying 
	@Query("delete Empsalarypackage e where trim(e.empsalarypackageCK.espkEmpcode)=:empcode and e.empsalarypackageCK.espkEarndedcode in (select m.earndedmasterCK.edhmEarndedcode from Earndedmaster m  where m.edhmEarndedtype = 'A')")
	public void deleteEmpsalarypackageA(String empcode);
	
	@Modifying 
	@Query("delete Empsalarypackage e where trim(e.empsalarypackageCK.espkEmpcode)=:empcode and e.empsalarypackageCK.espkEarndedcode in (select m.earndedmasterCK.edhmEarndedcode from Earndedmaster m  where m.edhmEarndedtype = 'D')")
	public void deleteEmpsalarypackageD(String empcode);
	
	@Modifying 
	@Query("delete Empschemeinfo e where trim(e.empschemeinfoCK.eschEmpcode)=:empcode")
	public void deleteEmpschemeinfo(String empcode);
	
	@Modifying 
	@Query("delete Empleaveinfo e where trim(e.empleaveinfoCK.elinEmpcode)=:empcode")
	public void deleteEmpleaveinfo(String empcode);
	
}

