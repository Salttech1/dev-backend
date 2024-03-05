package kraheja.enggsys.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Lcauth;
import kraheja.enggsys.entity.Lccert;
import kraheja.enggsys.entity.LccertCK;

@Repository
public interface LccertRepository extends JpaRepository<Lccert, LccertCK> {
	Lccert findLccertByLccertCKLcerCertnum(String lcerCertnum);
	
	@Query(value = "SELECT lcer_certnum,lcer_certtype,lcer_runser,lcer_certdate,lcer_Durfrom,lcer_durto,lcer_certamount,"
			+ "(SELECT sum(lcer_payamount) FROM lccert a WHERE a.lcer_contract = b.lcer_contract) AS TotPayment, lcer_tot_twoptc "
			+ "FROM lccert b WHERE trim(lcer_contract) = ? AND lcer_certnum = (SELECT Max(lcer_certnum) FROM lccert c WHERE c.lcer_contract = b.lcer_contract)", nativeQuery = true)
	Tuple fetchHeaderDetailsForLCCert(String recId);
	
	@Query(value = "SELECT Max(lcer_certnum) FROM lccert WHERE trim(lcer_contract) = ?", nativeQuery = true)
	String fetchLastCertNumber(String recId);
	
	// Not added one condition auth_num like '%' I think it is not required
	@Query(value = "select * from lccert where lcer_printedon IS null and trim(lcer_userid) =?", nativeQuery =true)
	List<Lccert> findByLcerPrintedonISNULLAndLcerUserid(String userid);
	
	@Query("SELECT e FROM Lccert e WHERE TRIM(e.lccertCK.lcerCertnum) IN :authNumList")
	List<Lccert> findByLccertCK_LcerCertnumIn(List<String> authNumList);
	
}
