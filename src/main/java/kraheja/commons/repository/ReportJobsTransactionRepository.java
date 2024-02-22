package kraheja.commons.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.ReportJobsTransaction;

@Repository
public interface ReportJobsTransactionRepository extends JpaRepository<ReportJobsTransaction, Integer> {

    Optional<ReportJobsTransaction> findByReportMasterId(Long reportMasterId);
    
    List<ReportJobsTransaction> findByCreatedByAndStatusIn(String userid, Set<String> status);
}
