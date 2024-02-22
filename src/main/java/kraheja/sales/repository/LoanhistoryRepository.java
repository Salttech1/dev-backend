package kraheja.sales.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Loanhistory;
import kraheja.sales.entity.LoanhistoryCK;

@Repository
public interface LoanhistoryRepository extends JpaRepository<Loanhistory, LoanhistoryCK>, CrudRepository<Loanhistory, LoanhistoryCK>{
	@Query(value="select * from loanhistory WHERE trim(lhist_ownerid) = :ownerid ",nativeQuery = true)
	List<Loanhistory> findByLoanOwnerid(String ownerid) ;
	//MB0E F0402
	List<Loanhistory> findByLoanhistoryCK_LhistOwnerid(String ownerid) ;
	
	Loanhistory findByLoanhistoryCK_LhistLoancoAndLoanhistoryCK_LhistLoannumAndLoanhistoryCK_LhistLoanclosedateAndLoanhistoryCK_LhistOwnerid(String loanco, String loannum, LocalDateTime loanclosedate, String ownerid) ;
}