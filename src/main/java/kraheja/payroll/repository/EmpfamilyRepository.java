package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empfamily ;
import kraheja.payroll.entity.EmpfamilyCK;
@Repository
public interface EmpfamilyRepository extends JpaRepository<Empfamily, EmpfamilyCK> {

	 
	Empfamily findByEmpfamilyCK_EfamEmpcodeAndEmpfamilyCK_EfamSrno(String empcode, Double srno) ;
	
	List<Empfamily> findByEmpfamilyCK_EfamEmpcode(String empcode) ;

}