package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empexperience ;
import kraheja.payroll.entity.EmpexperienceCK;
@Repository
public interface EmpexperienceRepository extends JpaRepository<Empexperience, EmpexperienceCK> {

	 
	Empexperience findByEmpexperienceCK_EexpEmpcodeAndEmpexperienceCK_EexpJobsrno(String empcode, Double jobsrno) ; 
	
	List<Empexperience> findByEmpexperienceCK_EexpEmpcode(String empcode) ;

}