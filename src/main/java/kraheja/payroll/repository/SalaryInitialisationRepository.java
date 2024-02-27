package kraheja.payroll.repository;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.apache.poi.hpsf.Decimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import kraheja.payroll.bean.EmppaymonthBean;
import kraheja.payroll.entity.Coymthsaltypes;
import kraheja.payroll.entity.CoymthsaltypesCK;
import kraheja.payroll.entity.Emppaymonth;

@Repository
@Transactional
public interface SalaryInitialisationRepository extends JpaRepository<Coymthsaltypes,CoymthsaltypesCK> {
	@Query(value = "SELECT (SELECT TRIM(ENT_NAME) \r\n"
			+ "             FROM   ENTITY \r\n"
			+ "             WHERE  ENT_CLASS = 'PAYRL' \r\n"
			+ "                    AND ENT_ID = 'SALTP' \r\n"
			+ "                    AND trim(ENT_CHAR1) = trim(CMST_SALARYTYPE))    AS SalaryType, \r\n"
			+ "       CMST_COY                                                 AS Company, \r\n"
			+ "       CMST_PAYMONTH                                            AS CurrentMth, \r\n"
			+ "       ( CASE \r\n"
			+ "           WHEN Getsalinitstat(cmst_coy, cmst_paymonth, CMST_SALARYTYPE) = 'RE-INITIALISE' THEN CMST_PAYMONTH \r\n"
			+ "           ELSE Getnextyrmth(cmst_paymonth) \r\n"
			+ "         END )                                                  AS NewMth, \r\n"
			+ "       Getsalinitstat(cmst_coy, cmst_paymonth, CMST_SALARYTYPE) AS Status, \r\n"
			+ "       CMST_SALARYTYPE                                          AS SalType, \r\n"
			+ "       CMST_YRSALREVNO                                          AS YrSalRevNo, \r\n"
			+ "       TO_Char(cmst_initialisedon,'DD/MM/YYYY')                 AS Initialisedon \r\n"
			+ "FROM   COYMTHSALTYPES \r\n"
			+ "WHERE  Trim(cmst_coy) in  (:coyCode) \r\n"
			+ "       AND Trim(cmst_salarytype) in  (:salarytype) \r\n"
			+ "ORDER  BY SalaryType, \r\n"
			+ "          Company ", nativeQuery = true)
	List<Tuple> GetCheckInputDetails(List<String> coyCode, List<String> salarytype);
	
//			+ "       Getdaysinyrmth(:paymonth) as EMPM_DAYSPAID,  \r\n"
	@Query(value = "SELECT ejin_empcode as EMPM_EMPCODE,  \r\n"
			+ "       To_CHAR((CASE \r\n"
			+ "         WHEN ejin_joindate >= Getfirstdateofyrmth(:paymonth) THEN ejin_joindate \r\n"
			+ "         ELSE Getfirstdateofyrmth(:paymonth) \r\n"
			+ "       END),'DD/MM/YYYY') as EMPM_PAIDFROM,  \r\n"
			+ "       To_CHAR((Getlastdateofyrmth(:paymonth)),'DD/MM/YYYY') as EMPM_PAIDUPTO,  \r\n"
			+ "       :paymonth  as EMPM_PAYMONTH,  \r\n"
			+ "       'L' EMPM_PAYSTATUS,  \r\n"
			+ "       :Saltype as EMPM_SALARYTYPE,  \r\n"
			+ "       (Getlastdateofyrmth(:paymonth) -  \r\n" 
			+ "       CASE \r\n"
			+ "         WHEN ejin_joindate >= Getfirstdateofyrmth(:paymonth) THEN ejin_joindate \r\n"
			+ "         ELSE Getfirstdateofyrmth(:paymonth) \r\n"
			+ "       END + 1) as EMPM_DAYSPAID,  \r\n"
			+ "       0 as EMPM_DAYSENCASHED,  \r\n"
			+ "       0 as EMPM_OTHOURS,  \r\n"   
			+ "       0 as EMPM_OTHRSLASTMTH,  \r\n"   
			+ "       0 as EMPM_OTHRSPRVLASTMTH,  \r\n" 
			+ "       NULL AS EMPM_REMARK,  \r\n"
			+ "       '' AS EMPM_PAYDATE,  \r\n"
			+ "       ejin_paymode as EMPM_INSTRUMENTTYPE,  \r\n"
			+ "       NULL AS EMPM_INSTRUMENTNO,  \r\n"
			+ "       ejin_bank as EMPM_INSTRUMENTBANK,  \r\n"
			+ "       '' as EMPM_INSTRUMENTDATE,  \r\n"
			+ "       0 as EMPM_DAYSUNIONFUND,  \r\n"   
			+ "       0 as EMPM_DAYSABSENT,  \r\n"   
			+ "       0 as EMPM_DAYSADJLASTMTH,  \r\n"   
			+ "       0 as EMPM_DAYSADJPRVLASTMTH,  \r\n"   
			+ "       0 as EMPM_DAYSNHPAY,  \r\n"   
			+ "       0 as EMPM_DAYSARREARS,  \r\n"   
			+ "       Null as EMPM_ACVOUNUM,  \r\n"   
			+ "       Null as EMPM_ACTRANSER,  \r\n"   
			+ "       0 as EMPM_ITDECLREVNO,  \r\n"   
			+ "       'R' as EMPM_REG_SETTLE,  \r\n"   
			+ "       'SALARYINIT' as EMPM_MODULE  \r\n"
			+ " FROM   EMPJOBINFO \r\n"
			+ " WHERE  ejin_company = :coy \r\n"
			+ "       AND ( ( ejin_jobstatus = 'L' ) \r\n"
			+ "              OR ( ejin_jobstatus = 'T' \r\n"
			+ "                   AND ejin_termdate IS NOT NULL \r\n"
			+ "                   AND ejin_termdate >= Getfirstdateofyrmth(:paymonth) ) ) \r\n"
			+ "       AND ejin_empcode NOT IN (SELECT empm_empcode \r\n"
			+ "                                FROM   emppaymonth \r\n"
			+ "                                WHERE  empm_paymonth = :paymonth \r\n"
			+ "                                       AND empm_salarytype = :Saltype) \r\n"
			+ "       AND ejin_joindate <= Getlastdateofyrmth(:paymonth) \r\n"
			+ "       AND :paymonth BETWEEN To_char(ejin_effectivefrom, 'YYYYMM') AND To_char(ejin_effectiveupto, 'YYYYMM') \r\n"
			+ "       AND ejin_empcode IN (SELECT ESPK_EMPCODE \r\n"
			+ "                            FROM   EMPSALARYPACKAGE \r\n"
			+ "                            WHERE  ( ( ESPK_EARNDEDCODE IN (SELECT ENT_CHAR2 \r\n"
			+ "                                                            FROM   ENTITY \r\n"
			+ "                                                            WHERE  ENT_CLASS = 'PAYRL' \r\n"
			+ "                                                                   AND ENT_ID = 'SINIT' \r\n"
			+ "                                                                   AND Rtrim(ENT_CHAR1) = :Saltype) ) \r\n"
			+ "                                      OR ( ESPK_EARNDEDCODE IN (SELECT ENT_CHAR3 \r\n"
			+ "                                                                FROM   ENTITY \r\n"
			+ "                                                                WHERE  ENT_CLASS = 'PAYRL' \r\n"
			+ "                                                                       AND ENT_ID = 'SINIT' \r\n"
			+ "                                                                       AND Rtrim(ENT_CHAR1) = :Saltype) ) \r\n"
			+ "                                      OR ( ESPK_EARNDEDCODE IN (SELECT ENT_CHAR4 \r\n"
			+ "                                                                FROM   ENTITY \r\n"
			+ "                                                                WHERE  ENT_CLASS = 'PAYRL' \r\n"
			+ "                                                                       AND ENT_ID = 'SINIT' \r\n"
			+ "                                                                       AND Rtrim(ENT_CHAR1) = :Saltype) ) ) \r\n"
			+ "                                   AND :paymonth BETWEEN To_char(ESPK_EFFECTIVEFROM, 'YYYYMM') AND To_char(ESPK_EFFECTIVEUPTO, 'YYYYMM')) ", nativeQuery = true)
	List<Tuple> RowToInsertInEmppaymonth(String coy,String paymonth,String Saltype);
	
	@Query(value = "SELECT ejin_empcode as EMPM_EMPCODE,  \r\n"
			+ "       GetFirstDateOfYrMth(:paymonth) as EMPM_PAIDFROM, \r\n"
			+ "       GetLastDateOfYrMth(:paymonth) as EMPM_PAIDUPTO,\r\n"
			+ "       :paymonth as EMPM_PAYMONTH, \r\n"
			+ "       ebon_paystatus as  EMPM_PAYSTATUS, \r\n"
			+ "       :Saltype as EMPM_SALARYTYPE, \r\n"
			+ "       GetDaysinYrMth(:paymonth) as EMPM_DAYSPAID, \r\n"
			+ "       0 as EMPM_DAYSENCASHED,  \r\n"
			+ "       0 as EMPM_OTHOURS,  \r\n"   
			+ "       0 as EMPM_OTHRSLASTMTH,  \r\n"   
			+ "       0 as EMPM_OTHRSPRVLASTMTH,  \r\n" 
			+ "       NULL AS EMPM_REMARK,  \r\n"
			+ "       '' AS EMPM_PAYDATE,  \r\n"
			+ "       ejin_paymode as EMPM_INSTRUMENTTYPE, \r\n"
			+ "       NULL AS EMPM_INSTRUMENTNO,  \r\n"
			+ "       ''  as EMPM_INSTRUMENTDATE,  \r\n"
			+ "       ejin_bank as EMPM_INSTRUMENTBANK,  \r\n"
			+ "       '' as EMPM_INSTRUMENTDATE,  \r\n"
			+ "       0 as EMPM_DAYSUNIONFUND,  \r\n"   
			+ "       0 as EMPM_DAYSABSENT,  \r\n"   
			+ "       0 as EMPM_DAYSADJLASTMTH,  \r\n"   
			+ "       0 as EMPM_DAYSADJPRVLASTMTH,  \r\n"   
			+ "       0 as EMPM_DAYSNHPAY,  \r\n"   
			+ "       0 as EMPM_DAYSARREARS,  \r\n"   
			+ "       Null as EMPM_ACVOUNUM,  \r\n"   
			+ "       Null as EMPM_ACTRANSER,  \r\n"   
			+ "       0 as EMPM_ITDECLREVNO,  \r\n"   
			+ "       'R' as EMPM_REG_SETTLE,  \r\n"
			+ "       'SALARYINIT'  as EMPM_MODULE  \r\n"
			+ "       FROM EMPJOBINFO A,EMPBONUS  \r\n"
			+ "       where ejin_company = ebon_company  \r\n"
			+ "       and ejin_empcode = ebon_empcode  \r\n"
			+ "       and ebon_salarytype = :Saltype  \r\n"
			+ "       AND eBON_paymonth = :paymonth \r\n"
			+ "       and ejin_company = :coy \r\n"
			+ "       and ejin_empcode not in (SELECT empm_empcode FROM emppaymonth WHERE empm_paymonth = :paymonth and empm_salarytype = :Saltype )  \r\n"
			+ "       and ejin_effectiveupto in (select max(ejin_effectiveupto) from empjobinfo b where b.ejin_empcode =  a.ejin_empcode) ", nativeQuery = true)
	List<Tuple> RowToInsertInEmppaymonthForBonus(String coy,String paymonth,String Saltype);
	
	@Query(value="SELECT count(*) \r\n"
			+ "FROM   emppaymonth, \r\n"
			+ "       empjobinfo \r\n"
			+ "WHERE  empm_empcode = ejin_empcode \r\n"
			+ "       AND ejin_company = :coy \r\n"
			+ "       AND :paymonth BETWEEN To_char(ejin_EFFECTIVEFROM, 'YYYYMM') AND To_char(Ejin_EFFECTIVEUPTO, 'YYYYMM') \r\n"
			+ "       AND empm_paymonth = :paymonth \r\n"
			+ "       AND empm_paystatus = 'H' \r\n"
			+ "       AND empm_salarytype = :Saltype \r\n"
			+ "       AND empm_empcode IN (SELECT ESPK_EMPCODE \r\n"
			+ "                            FROM   EMPSALARYPACKAGE \r\n"
			+ "                            WHERE  ESPK_EARNDEDCODE = Getsalaryhead('S') \r\n"
			+ "                                   AND :paymonth BETWEEN To_char(ESPK_EFFECTIVEFROM, 'YYYYMM') AND To_char(ESPK_EFFECTIVEUPTO, 'YYYYMM')) ", nativeQuery = true)
	Double FindEmplOnHold(String coy,String paymonth,String Saltype);
	
	@Query(value="select (sysdate - cmst_initialisedon) as datediff\r\n"
			+ "			FROM   COYMTHSALTYPES \r\n"
			+ "			WHERE  Trim(cmst_coy) = :coy \r\n"
			+ "			       AND Trim(cmst_salarytype) = :Saltype ", nativeQuery = true)
	Integer FindDateDiffForInit(String coy,String Saltype);
	
	@Modifying
	@Query("UPDATE Coymthsaltypes c \r\n"
			+ "SET c.cmstPaymonth = :paymonth, \r\n"
			+ "c.cmstInitialisedon = sysdate, \r\n"
			+ "c.cmstYrsalrevno =  (CASE WHEN SUBSTR(:paymonth,5,2) = '04' THEN 0 ELSE (c.cmstYrsalrevno + 1) END) , \r\n"
			+ "c.cmstMthsalrevno = 0, \r\n"
			+ "c.cmstBankadvno = 0, \r\n"
			+ "c.cmstYrstartmonth = GetStartYrMthOfAcYear(:paymonth) , \r\n"
			+ "c.cmstYrendmonth = GetEndYrMthOfAcYear(:paymonth) , \r\n"
			+ "c.cmstSite = :site, \r\n"
			+ "c.cmstUserid = :userid, \r\n"
			+ "c.cmstModule = 'SALARYINIT', \r\n"
			+ "c.cmstMachinename = :machineName, \r\n"
			+ "c.cmstModifiedon = sysdate, \r\n"
			+ "c.cmstIpaddress =  :ipAddress \r\n"
			+ "WHERE Trim(c.coymthsaltypesCK.cmstCoy) = :coy \r\n"
			+ "  AND Trim(c.coymthsaltypesCK.cmstSalarytype) = :Saltype ")
	public void updatecoymthsaltypes(String coy,String paymonth,String Saltype,String site, String userid, String machineName, String ipAddress);
	
	@Modifying
	@Query("Update Salaryplan s \r\n"
			+ " SET s.splanInitdoneyn = 'Y', \r\n"
			+ " s.splanSite = :site, \r\n"
			+ " s.splanUserid = :userid, \r\n"
			+ " s.splanModule = 'SALARYINIT', \r\n"
			+ " s.splanMachinename = :machineName, \r\n"
			+ " s.splanModifiedon = sysdate, \r\n"
			+ " s.splanIpaddress = :ipAddress \r\n"
			+ " WHERE Trim(s.salaryplanCK.splanSalarytype) = :Saltype \r\n" 
			+ " AND Trim(s.salaryplanCK.splanCoy) = :coy \r\n"
			+ " AND Trim(s.salaryplanCK.splanPaymonth) = :paymonth ")
	public void updatesalaryplan(String coy,String paymonth,String Saltype,String site, String userid, String machineName, String ipAddress);
		
}
