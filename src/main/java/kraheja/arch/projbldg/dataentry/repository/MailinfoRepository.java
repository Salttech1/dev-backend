package kraheja.arch.projbldg.dataentry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.arch.projbldg.dataentry.entity.Mailinfo;
import kraheja.arch.projbldg.dataentry.entity.MailinfoCK;
@Repository
public interface MailinfoRepository extends JpaRepository<Mailinfo, MailinfoCK> {

	 
	Mailinfo findByMailinfoCK_MinfBldgcode(String bldgcode) ; 

}