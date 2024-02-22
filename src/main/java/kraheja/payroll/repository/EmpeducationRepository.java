package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empeducation ;
import kraheja.payroll.entity.EmpeducationCK;
@Repository
public interface EmpeducationRepository extends JpaRepository<Empeducation, EmpeducationCK> {

	 
	Empeducation findByEmpeducationCK_EeduEmpcodeAndEmpeducationCK_EeduEducsrno(String empcode, Double educsrno) ; 
	
	List<Empeducation> findByEmpeducationCK_EeduEmpcode(String empcode) ;

}