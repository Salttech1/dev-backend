package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empleaveinfo ;
import kraheja.payroll.entity.EmpleaveinfoCK;
@Repository
public interface EmpleaveinfoRepository extends JpaRepository<Empleaveinfo, EmpleaveinfoCK> {

	 
	Empleaveinfo findByEmpleaveinfoCK_ElinEmpcodeAndEmpleaveinfoCK_ElinLeavecodeAndEmpleaveinfoCK_ElinAcyear(String empcode, String leavecode, String acyear) ; 
	
	List<Empleaveinfo> findByEmpleaveinfoCK_ElinEmpcodeOrderByEmpleaveinfoCK_ElinAcyearDesc(String empcode) ;

}