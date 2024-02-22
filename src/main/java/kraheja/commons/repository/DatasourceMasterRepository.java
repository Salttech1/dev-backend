package kraheja.commons.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.DatasourceMaster;

@Repository
public interface DatasourceMasterRepository extends JpaRepository<DatasourceMaster, Long> {

    Optional<DatasourceMaster> findByName(String name);
    
    Optional<DatasourceMaster> findById(Long name);
}
