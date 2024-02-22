package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Actranhx;
import kraheja.commons.entity.ActranhxCK;

@Repository
public interface ActranhxRepository extends JpaRepository<Actranhx, ActranhxCK> {

	 

}