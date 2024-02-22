package kraheja.enggsys.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Certworknarrdtl;
import kraheja.enggsys.entity.Certworknarrdtl.CertworknarrdtlCK;
@Repository
public interface CertworknarrdtlRepository extends JpaRepository<Certworknarrdtl, CertworknarrdtlCK> {
	List<Certworknarrdtl> findByCertworknarrdtlCK_CwndCertnum(String certnum) ; 
}