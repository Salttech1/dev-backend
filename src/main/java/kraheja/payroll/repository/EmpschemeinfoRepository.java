package kraheja.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import kraheja.payroll.entity.Empschemeinfo ;
import kraheja.payroll.entity.EmpschemeinfoCK;
@Repository
public interface EmpschemeinfoRepository extends JpaRepository<Empschemeinfo, EmpschemeinfoCK> {

	 
	Empschemeinfo findByEmpschemeinfoCK_EschEmpcodeAndEmpschemeinfoCK_EschSchemecodeAndEmpschemeinfoCK_EschEffectivefrom(String empcode, String schemecode, LocalDateTime effectivefrom) ; 
	
	List<Empschemeinfo> findByEmpschemeinfoCK_EschEmpcode(String empcode) ;

}