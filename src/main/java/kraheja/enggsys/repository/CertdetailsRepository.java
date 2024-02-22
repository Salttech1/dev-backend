package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Certdetails;
import kraheja.enggsys.entity.Certdetails.CertdetailsCK;
@Repository
public interface CertdetailsRepository extends JpaRepository<Certdetails, CertdetailsCK> {
	Certdetails findByCertdetailsCK_CrtdCertnumAndCertdetailsCK_CrtdContractAndCertdetailsCK_CrtdContbillno(String certnum, String contract, String contbillno) ; 

	List<Certdetails> findByCertdetailsCK_CrtdCertnum(String certnum); 

}