package kraheja.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.ReportMaster;

@Repository
public interface ReportMasterRepository extends JpaRepository<ReportMaster, Long> {

    Optional<ReportMaster> findByName(String name);
}
