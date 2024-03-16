package kraheja.payroll.repository;

import java.util.List;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.payroll.entity.Emppaymonth;
import kraheja.payroll.entity.EmppaymonthCK;

@Repository
@Transactional
public interface SalaryPaymentDateRepository extends JpaRepository<Emppaymonth, EmppaymonthCK>{
	@Query("SELECT empp.emppaymonthCK.empmEmpcode AS Empcode, \r\n"
			+ "       (SELECT emp.eperFullname \r\n"
			+ "        FROM Emppersonal emp \r\n"
			+ "        WHERE emp.emppersonalCK.eperEmpcode = empp.emppaymonthCK.empmEmpcode \r\n"
			+ "               AND trim(empp.emppaymonthCK.empmPaymonth) BETWEEN To_char(emp.emppersonalCK.eperEffectivefrom, 'YYYYMM') AND To_char(emp.eperEffectiveupto, 'YYYYMM')) AS Fullname, \r\n"
			+ "       (SELECT SUM(esp.espdEarndedamt) \r\n"
			+ "        FROM Empsalarypaid esp \r\n"
			+ "        WHERE esp.empsalarypaidCK.espdEmpcode = empp.emppaymonthCK.empmEmpcode \r\n"
			+ "               AND esp.empsalarypaidCK.espdEarndedcode IN ( 'NETPAY', 'UNIFORM', 'ATTIRE', 'MEDICAL' ) \r\n"
			+ "               AND esp.empsalarypaidCK.espdSalarytype = empp.emppaymonthCK.empmSalarytype \r\n"
			+ "               AND esp.empsalarypaidCK.espdPaymonth = empp.emppaymonthCK.empmPaymonth) AS NetPay, \r\n"
			+ "       TO_CHAR(empp.empmPaydate, 'DD/MM/YYYY') AS PayDate \r\n"
			+ "FROM Emppaymonth empp \r\n"
			+ "WHERE Trim(empp.emppaymonthCK.empmPaymonth) = :paymonth \r\n"
			+ "       AND Trim(empp.emppaymonthCK.empmSalarytype) = :salarytype \r\n"
			+ "       AND Trim(empp.empmInstrumenttype) in (:instrumenttype) \r\n"
			+ "       AND empp.empmPaystatus = 'P' \r\n"
			+ "       AND empp.emppaymonthCK.empmEmpcode IN (SELECT ejin.empjobinfoCK.ejinEmpcode \r\n"
			+ "                                              FROM Empjobinfo ejin, \r\n"
			+ "                                                   Empsalarypaid espd \r\n"
			+ "                                              WHERE ejin.empjobinfoCK.ejinEmpcode = espd.empsalarypaidCK.espdEmpcode \r\n"
			+ "                                                    AND trim(ejin.ejinCompany) = :company \r\n"
			+ "                                                    AND espd.empsalarypaidCK.espdSalarytype = empp.emppaymonthCK.empmSalarytype \r\n"
			+ "                                                    AND espd.empsalarypaidCK.espdPaymonth = empp.emppaymonthCK.empmPaymonth \r\n"
			+ "                                                    AND trim(empp.emppaymonthCK.empmPaymonth) BETWEEN To_char(ejin.empjobinfoCK.ejinEffectivefrom, 'YYYYMM') AND To_char(ejin.ejinEffectiveupto, 'YYYYMM') \r\n"
			+ "                                                    AND espd.espdEarndedamt > 0) \r\n"
			+ "ORDER BY empp.emppaymonthCK.empmEmpcode ")
	List<Tuple> GetCheckInputDetailsforPaymentDate(String paymonth, String salarytype, List<String> instrumenttype, String company);

//	this is native query for the above JPA query this is not used now
//	@Query(value = "SELECT empm_empcode                                                                                                       AS Empcode,\r\n"
//			+ "       (SELECT eper_fullname\r\n"
//			+ "        FROM   emppersonal\r\n"
//			+ "        WHERE  eper_empcode = empm_empcode\r\n"
//			+ "               AND Trim(empm_paymonth) BETWEEN To_char(eper_effectivefrom, 'YYYYMM') AND To_char(eper_effectiveupto, 'YYYYMM')) AS Fullname,\r\n"
//			+ "       (SELECT SUM(espd_earndedamt)\r\n"
//			+ "        FROM   empsalarypaid\r\n"
//			+ "        WHERE  espd_empcode = empm_empcode\r\n"
//			+ "               AND espd_earndedcode IN ( 'NETPAY', 'UNIFORM', 'ATTIRE', 'MEDICAL' ) \r\n"
//			+ "               AND espd_salarytype = empm_salarytype \r\n"
//			+ "               AND espd_paymonth = empm_paymonth)                                                                         AS NetPay, \r\n"
//			+ "       To_char(empm_paydate, 'dd/mm/yyyy')                                                                                AS PayDate \r\n"
//			+ "FROM   emppaymonth \r\n"
//			+ "WHERE  Trim(empm_paymonth) = :paymonth \r\n"
//			+ "       AND Trim(empm_salarytype) = :salarytype \r\n"
//			+ "       AND Trim(empm_instrumenttype) in (:instrumenttype) \r\n"
//			+ "       AND empm_paystatus = 'P' \r\n"
//			+ "       AND empm_empcode IN (SELECT ejin_empcode \r\n"
//			+ "                            FROM   empjobinfo, \r\n"
//			+ "                                   empsalarypaid \r\n"
//			+ "                            WHERE  ejin_empcode = espd_empcode \r\n"
//			+ "                                   AND Trim(ejin_company) = :company \r\n"
//			+ "                                   AND espd_salarytype = empm_salarytype \r\n"
//			+ "                                   AND espd_paymonth = empm_paymonth \r\n"
//			+ "                                   AND Trim(empm_paymonth) BETWEEN To_char(ejin_effectivefrom, 'YYYYMM') AND To_char(ejin_effectiveupto, 'YYYYMM') \r\n"
//			+ "                                   AND espd_earndedamt > 0) \r\n"
//			+ "ORDER  BY empm_empcode", nativeQuery = true)
//List<Tuple> GetCheckInputDetailsforPaymentDate(String paymonth, String salarytype, List<String> instrumenttype, String company);
	
	@Query("SELECT c.cmstPaymonth FROM Coymthsaltypes c where c.coymthsaltypesCK.cmstCoy = :company and c.coymthsaltypesCK.cmstSalarytype = :salarytype")
	String GetPaymonthByCoyAndSalarytype(String company, String salarytype);
	
}
