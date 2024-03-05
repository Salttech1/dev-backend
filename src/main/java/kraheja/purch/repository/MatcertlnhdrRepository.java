package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Matcertlnhdr;
import kraheja.purch.entity.MatcertlnhdrCK;

@Repository
public interface MatcertlnhdrRepository extends JpaRepository<Matcertlnhdr, MatcertlnhdrCK> {

	 
	Matcertlnhdr findByMatcertlnhdrCK_MclhTendernumAndMatcertlnhdrCK_MclhLogicnotenum(String tendernum, String logicnotenum) ; 
	
	@Query("SELECT count(*) FROM Matcertlnhdr m  WHERE trim(m.mclhProjcode) = :projectCode")
	Integer findMatcertlnhdrCountByProjCode(String projectCode);
	
	@Query(value = "select COALESCE(mclh_finalbillcloseyn, 'N') from matcertlnhdr where mclh_logicnotenum=?", nativeQuery = true)
	String findByMatcertlnhdrCK_MclhLogicnotenum(String logicnotenum);
}
