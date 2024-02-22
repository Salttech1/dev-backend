package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Rightsoption;

@Repository
public interface RightsOptionRepository extends JpaRepository<Rightsoption, Long>{

}
