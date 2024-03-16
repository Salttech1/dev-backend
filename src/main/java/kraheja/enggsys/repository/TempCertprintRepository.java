package kraheja.enggsys.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kraheja.enggsys.entity.TempCertprint ;
import kraheja.enggsys.entity.TempCertprintCK;
@Repository
public interface TempCertprintRepository extends JpaRepository<TempCertprint, TempCertprintCK> {

	 
    TempCertprint findByTempcertprintCK_TmpCertnumAndTempcertprintCK_TmpTypeAndTempcertprintCK_TmpCodeAndTempcertprintCK_TmpSessionid(String tmpCertnum, String tmpType, String tmpCode, Integer tmpSessionid);

	List<TempCertprint> findByTempcertprintCK_TmpSessionid(Integer tmpSessionid);
	
	@Modifying
	@Query(value = "delete from TempCertprint t where t.tempcertprintCK.tmpSessionid = :tmpSessionid")
	void deleteByTmpSessionid(Integer tmpSessionid);

}


