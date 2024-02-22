package kraheja.adminexp.insurance.dataentry.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.insurance.dataentry.entity.Inspolicy ;
import kraheja.adminexp.insurance.dataentry.entity.InspolicyCK;
@Repository
public interface InspolicyRepository extends JpaRepository<Inspolicy, InspolicyCK> {

	 
	Inspolicy findByInspolicyCK_InpPolicyid(String policyid); 
	
	Inspolicy findByInspolicyCK_InpPolicynumber(String policynumber); 
	
	@Modifying
	@Query("Update Inspolicy ip SET ip.inpRenewedyn = :status , ip.inpSite = :site, ip.inpUserid = :userid, ip.inpModifiedon = :today WHERE trim(ip.inspolicyCK.inpPolicyid) = :policyId")
	void updateRenewedYNStatus(String status, String site, String userid, LocalDateTime today, String policyId);

}