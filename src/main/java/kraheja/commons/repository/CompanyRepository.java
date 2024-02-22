package kraheja.commons.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Company;
import kraheja.commons.entity.CompanyCK;
import kraheja.commons.entity.Party;

@Repository
public interface CompanyRepository extends JpaRepository<Company, CompanyCK>{

	@Query("SELECT company FROM Company company WHERE trim(company.companyCK.coyCode)=:companyCode AND (company.companyCK.coyClosedate = :closeDate OR company.companyCK.coyClosedate is null)")
	Company findByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(String companyCode, Date closeDate); 
	
	@Query("SELECT company.companyCK.coyClosedate FROM Company company WHERE trim(company.companyCK.coyCode)=:companyCode")
	LocalDateTime findCloseDateByCompanyCK_CoyCode(String companyCode); 
	
	@Query("SELECT trim(company.companyCK.coyProp) FROM Company company WHERE trim(company.companyCK.coyCode)=:companyCode AND trim(company.coyActive) = 'Y' AND (company.companyCK.coyClosedate = :closeDate OR company.companyCK.coyClosedate is null)")
	String findProprietorByCompanyCK_CoyCodeAndCompanyCK_CoyClosedate(String companyCode, Date closeDate);
	
	List<Company> findByCompanyCK_CoyClosedate(Date closeDate);
	
	@Query("Select e FROM Company e WHERE trim(e.companyCK.coyCode) = :companyCode  AND e.coyOpendate<= :billDate AND (e.companyCK.coyClosedate >= :billDate or e.companyCK.coyClosedate is null) ")
	Company findByCompanyCK_CoyCodeAndBillDate(String companyCode, Date billDate);
	
	@Query(value = "select coy_name from company where trim(coy_code)= ? ", nativeQuery = true)
	String findByCompanyCKCoyCode(String companyCode);
}
