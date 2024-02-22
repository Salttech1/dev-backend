package kraheja.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import kraheja.payroll.entity.Empjobinfo ;
import kraheja.payroll.entity.EmpjobinfoCK;
@Repository
public interface EmpjobinfoRepository extends JpaRepository<Empjobinfo, EmpjobinfoCK> {

	 
	Empjobinfo findByEmpjobinfoCK_EjinEmpcodeAndEmpjobinfoCK_EjinEffectivefrom(String empcode, LocalDateTime effectivefrom) ;
	
	List<Empjobinfo> findByEmpjobinfoCK_EjinEmpcode(String empcode) ;

}