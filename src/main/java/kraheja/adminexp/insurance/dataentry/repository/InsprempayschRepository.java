package kraheja.adminexp.insurance.dataentry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.insurance.dataentry.entity.Inspolicy;
import kraheja.adminexp.insurance.dataentry.entity.Insprempaysch ;
import kraheja.adminexp.insurance.dataentry.entity.InsprempayschCK;
@Repository
public interface InsprempayschRepository extends JpaRepository<Insprempaysch, InsprempayschCK> {

	 
	Insprempaysch findByInsprempayschCK_IppsPolicyidAndInsprempayschCK_IppsPolicynumberAndInsprempayschCK_IppsLinenumber(String policyid, String policynumber, Double linenumber) ;

	List<Insprempaysch> findByInsprempayschCK_IppsPolicyidAndInsprempayschCK_IppsPolicynumber(String policyid, String policynumber) ;

}  