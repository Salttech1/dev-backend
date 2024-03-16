package kraheja.payroll.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.payroll.entity.Emppersonal ;
import kraheja.payroll.entity.EmppersonalCK;

@Repository
@Transactional
public interface EmppersonalRepository extends JpaRepository<Emppersonal, EmppersonalCK> {
	List<Emppersonal> findByEmppersonalCK_EperEmpcode(String empcode) ;
	
	Emppersonal findByEmppersonalCK_EperEmpcodeAndEmppersonalCK_EperEffectivefrom(String empcode, LocalDate effectivefrom) ;
	
	@Query("select e from Emppersonal e where trim(e.emppersonalCK.eperEmpcode)=:empcode and eperEffectiveupto = :closedate")
	Emppersonal GetEmppersonalDetails(String empcode, LocalDate closedate);
	
	void deleteByEmppersonalCK_EperEmpcodeAndEmppersonalCK_EperEffectivefrom(String empcode, LocalDate effectivefrom);
	
	void deleteByEmppersonalCK_EperEmpcode(String empcode);
	
	@Modifying
	@Query(" Update Emppersonal e \r\n"
			+ "    SET e.eperEffectiveupto = To_date(:lastday,'dd/mm/yyyy'), \r\n"
			+ "        e.eperSite = :site, \r\n"
			+ "        e.eperUserid = :userid, \r\n"
			+ "        e.eperMachinename = :machinename, \r\n"
			+ "        e.eperModifiedon = sysdate, \r\n"
			+ "        e.eperIpaddress = :ipaddress \r\n"
			+ "  WHERE Trim(e.emppersonalCK.eperEmpcode) = :empcode AND \r\n"
			+ "        e.eperEffectiveupto = To_date(:closedate,'dd/mm/yyyy') \r\n")
	public void updateemppersonal(String empcode,String lastday,String site, String userid, String machinename, String ipaddress,String closedate );
}
