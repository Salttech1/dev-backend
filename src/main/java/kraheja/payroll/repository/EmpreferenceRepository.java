package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empreference ;
import kraheja.payroll.entity.EmpreferenceCK;
@Repository
public interface EmpreferenceRepository extends JpaRepository<Empreference, EmpreferenceCK> {

	 
	Empreference findByEmpreferenceCK_ErefEmpcodeAndEmpreferenceCK_ErefSrno(String empcode, Double srno) ;
	
	List<Empreference> findByEmpreferenceCK_ErefEmpcode(String empcode);

}