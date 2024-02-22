package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Actrandx;
import kraheja.commons.entity.ActrandxCK;

@Repository
public interface ActrandxRepository extends JpaRepository<Actrandx, ActrandxCK> {


}