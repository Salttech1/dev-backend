package kraheja.enggsys.repository;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Tdsexempt;
import kraheja.enggsys.entity.Tdsexempt.TdsexemptCK;

@Repository
public interface TdsexemptRepository extends JpaRepository<Tdsexempt, TdsexemptCK> {
	 
	Tdsexempt findByTdsexemptCK_TdsxContract(String contract) ; 

}