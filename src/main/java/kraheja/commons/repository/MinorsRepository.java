package kraheja.commons.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Minors;
import kraheja.commons.entity.MinorsCK;
@Repository
public interface MinorsRepository extends JpaRepository<Minors, MinorsCK> {

 	Minors  findByMinorsCK_MinMinorscodeAndMinorsCK_MinMinorstypeAndMinorsCK_MinClosedate(String minorscode, String minMinorstype, LocalDate closeDate);
	
}