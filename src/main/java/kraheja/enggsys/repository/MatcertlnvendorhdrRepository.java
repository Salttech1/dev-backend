package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kraheja.enggsys.entity.Matcertlnvendorhdr;
import kraheja.enggsys.entity.MatcertlnvendorhdrCK;

@Repository
public interface MatcertlnvendorhdrRepository extends JpaRepository<Matcertlnvendorhdr, MatcertlnvendorhdrCK> {

	@Query(value="select count(*) from matcertlnvendorhdr where trim(mcvh_logicnotenum)=:logicNoteNum",nativeQuery=true)
	int getVendorCount(String logicNoteNum);
	
	@Query(value="select distinct mcbh_logicnotenum from matcertlnboqqtydtls where trim(mcbh_boqno)=:TxtBOQNo",nativeQuery=true)
	String getlogicnotenum(String TxtBOQNo);
	
	@Query(value="select mclw_matcerttype from matcertlnworkcodedtls where trim(mclw_logicnotenum)=:logicNoteNum",nativeQuery=true)
	String getmatcerttype(String logicNoteNum);
	
	List<Matcertlnvendorhdr> findByMatcertlnvendorhdrCKMcvhLogicnotenumOrderByMatcertlnvendorhdrCKMcvhVendorsrno(String logicNoteNum);

}