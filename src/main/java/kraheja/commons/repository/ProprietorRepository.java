package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Proprietor;
import kraheja.commons.entity.ProprietorCK;

@Repository
public interface ProprietorRepository extends JpaRepository<Proprietor, ProprietorCK> {
	 
	Proprietor findByProprietorCK_PropCode(String code) ; 
}