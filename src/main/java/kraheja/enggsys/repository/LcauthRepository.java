package kraheja.enggsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kraheja.enggsys.entity.Lcauth;
import kraheja.enggsys.entity.LcauthCK;

public interface LcauthRepository extends JpaRepository<Lcauth, LcauthCK> {

	Lcauth findLcauthByLcauthCKLcahAuthnum(String authnum);

	@Query(value = "select max(lcah_authnum) from lcauth where trim(LCAH_PARTYCODE) = ? and trim(LCAH_BLDGCODE) = ? and trim(LCAH_AUTHTYPE) = ?", nativeQuery = true)
	String findLastLcauthNumBySupplierAndBuildingAndAuthType(String supplier, String building, String authType);
	
	@Query(value = "select * from lcauth where trim(lcah_authnum) = ? ", nativeQuery = true)
	Lcauth findLastLcauthBySupplierAndBuildingAndAuthTypeAuthnum(String authnum);
}
