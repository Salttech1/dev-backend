package kraheja.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import kraheja.payroll.entity.Empjobinfo ;
import kraheja.payroll.entity.EmpjobinfoCK;
import kraheja.payroll.entity.Emppersonal;
@Repository
public interface EmpjobinfoRepository extends JpaRepository<Empjobinfo, EmpjobinfoCK> {

	 
	Empjobinfo findByEmpjobinfoCK_EjinEmpcodeAndEmpjobinfoCK_EjinEffectivefrom(String empcode, LocalDateTime effectivefrom) ;
	
	List<Empjobinfo> findByEmpjobinfoCK_EjinEmpcode(String empcode) ;
	
	@Query("select e from Empjobinfo e where trim(e.empjobinfoCK.ejinEmpcode)=:empcode and ejinEffectiveupto = :closedate")
	Empjobinfo GetEmpjobinfoDetails(String empcode, LocalDate closedate);

}