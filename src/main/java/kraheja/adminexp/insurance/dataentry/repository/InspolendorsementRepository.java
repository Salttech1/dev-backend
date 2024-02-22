package kraheja.adminexp.insurance.dataentry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.adminexp.insurance.dataentry.entity.Inspolendorsement ;
import kraheja.adminexp.insurance.dataentry.entity.InspolendorsementCK;
@Repository
public interface InspolendorsementRepository extends JpaRepository<Inspolendorsement, InspolendorsementCK> {

	 
	Inspolendorsement findByInpePolendorsenum(String polendorsenum) ;

	Inspolendorsement findByInspolendorsementCK_inpePolendorseid(String polendorseid); 
	
	Inspolendorsement findByInpepolicyno(String policyNo); 
}