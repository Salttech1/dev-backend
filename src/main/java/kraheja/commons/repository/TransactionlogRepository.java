package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Transactionlog;
import kraheja.commons.entity.TransactionlogCK;

@Repository
public interface TransactionlogRepository extends JpaRepository<Transactionlog, TransactionlogCK> {
	
}
