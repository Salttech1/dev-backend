package kraheja.fd.deposit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.fd.deposit.entity.Form15hg;
import kraheja.fd.deposit.entity.Form15hgCK;

@Repository
public interface Form15hgRepository extends JpaRepository<Form15hg, Form15hgCK>{
	
	public Form15hg findByForm15hgCK_FormDepositorAndForm15hgCK_FormCoyAndForm15hgCK_FormAcyearAndForm15hgCK_FormQuarter(String depositor, String coy, String acYear, String quater);

}