package kraheja.commons.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Hsnsacmaster;
import kraheja.commons.entity.HsnsacmasterCK;
@Repository
public interface HsnsacmasterRepository extends JpaRepository<Hsnsacmaster, HsnsacmasterCK> {

	 
	Hsnsacmaster findByHsnsacmasterCK_HsmsCodeAndHsnsacmasterCK_HsmsTypeAndHsnsacmasterCK_HsmsOpendate(String code, String type, LocalDateTime opendate) ; 

	List<Hsnsacmaster> findByHsnsacmasterCK_HsmsTypeAndHsmsClosedate(String type, LocalDate closedate) ; 
	Hsnsacmaster findByHsnsacmasterCKHsmsCode(String hsmsCode);
	
	@Query("select h from Hsnsacmaster h where h.hsnsacmasterCK.hsmsCode= :hsmsCode and :date between h.hsnsacmasterCK.hsmsOpendate and  h.hsmsClosedate")
	Hsnsacmaster gstRate(String hsmsCode, LocalDate date);
	
	
}