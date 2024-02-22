package kraheja.payroll.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.payroll.entity.Emppersonal ;
import kraheja.payroll.entity.EmppersonalCK;

@Repository
public interface EmppersonalRepository extends JpaRepository<Emppersonal, EmppersonalCK> {
	List<Emppersonal> findByEmppersonalCK_EperEmpcode(String empcode) ;
	
	Emppersonal findByEmppersonalCK_EperEmpcodeAndEmppersonalCK_EperEffectivefrom(String empcode, LocalDate effectivefrom) ;
	
	@Query("select e from Emppersonal e where trim(e.emppersonalCK.eperEmpcode)=:empcode and eperEffectiveupto = :closedate")
	Emppersonal GetEmppersonalDetails(String empcode, LocalDate closedate);
	
	void deleteByEmppersonalCK_EperEmpcodeAndEmppersonalCK_EperEffectivefrom(String empcode, LocalDate effectivefrom);
	
	void deleteByEmppersonalCK_EperEmpcode(String empcode);
}
