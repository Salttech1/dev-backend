package kraheja.commons.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Epworks;
import kraheja.commons.entity.EpworksCK;
@Repository
public interface EpworksRepository extends JpaRepository<Epworks, EpworksCK> {

	Epworks findByEpworksCK_EpWorkcodeAndEpworksCK_EpAworkcodeAndEpworksCK_EpClosedateAndEpworksCK_EpOpendate(String workcode, String aworkcode, LocalDate closedate, LocalDate opendate) ;
	
	Epworks findByEpworksCK_EpWorkcodeAndEpworksCK_EpAworkcode(String workcode, String aworkcode);
	
	@Query("Select e from Epworks e where trim(e.epworksCK.epWorkcode) = :workcode and (e.epworksCK.epClosedate is null or e.epworksCK.epClosedate = :closeDate)")
	Epworks findByWorkCodeAndCloseDate(String workcode, LocalDate closeDate);
}