package kraheja.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import kraheja.payroll.entity.Empjobinfo ;
import kraheja.payroll.entity.EmpjobinfoCK;


@Repository
@Transactional
public interface EmpjobinfoRepository extends JpaRepository<Empjobinfo, EmpjobinfoCK> {

	 
	Empjobinfo findByEmpjobinfoCK_EjinEmpcodeAndEmpjobinfoCK_EjinEffectivefrom(String empcode, LocalDateTime effectivefrom) ;
	
	List<Empjobinfo> findByEmpjobinfoCK_EjinEmpcode(String empcode) ;
	
	@Query("select e from Empjobinfo e where trim(e.empjobinfoCK.ejinEmpcode)=:empcode and ejinEffectiveupto = :closedate")
	Empjobinfo GetEmpjobinfoDetails(String empcode, LocalDate closedate);
	
	@Modifying
	@Query(" UPDATE Empjobinfo e \r\n"
			+ "    SET e.ejinEffectiveupto = To_date(:lastday,'dd/mm/yyyy'), \r\n"
			+ "        e.ejinSite = :site, \r\n"
			+ "        e.ejinUserid = :userid, \r\n"
			+ "        e.ejinMachinename = :machinename, \r\n"
			+ "        e.ejinModifiedon = sysdate, \r\n"
			+ "        e.ejinIpaddress = :ipaddress \r\n"
			+ "  WHERE Trim(e.empjobinfoCK.ejinEmpcode) = :empcode AND \r\n"
			+ "        e.ejinEffectiveupto = To_date(:closedate,'dd/mm/yyyy') \r\n")
	public void updateempjobinfo(String empcode,String lastday,String site, String userid, String machinename, String ipaddress,String closedate );
	

}