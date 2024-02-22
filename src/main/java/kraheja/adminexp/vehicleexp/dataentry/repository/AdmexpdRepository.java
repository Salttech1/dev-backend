package kraheja.adminexp.vehicleexp.dataentry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.vehicleexp.dataentry.entity.Admexpd ;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexpdCK;
@Repository
public interface AdmexpdRepository extends JpaRepository<Admexpd, AdmexpdCK> {

	 
	Admexpd findByAdmexpdCK_AdmdCertnumAndAdmexpdCK_AdmdBunum(String certnum, Double bunum) ; 
	
	List<Admexpd> findByAdmexpdCK_AdmdCertnum(String certnum);
	
	
	@Modifying
	@Query("Delete Admexpd ad where trim(ad.admexpdCK.admdCertnum) = :certnum ")
	void deleteAdmexpdByCertNum(String certnum);

	
}