package kraheja.commons.repository;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Actrand;
import kraheja.commons.entity.ActrandCK;

@Repository
public interface ActrandRepository extends JpaRepository<Actrand, ActrandCK> {
	
	@Modifying 
	@Query("delete Actrand e where trim(e.actrandCK.actdTranser)=:actdTranser")
	public void deleteActrand(String actdTranser);
	
	@Query("SELECT e from Actrand e where e.actrandCK.actdTranser=:transer")
	public List<Actrand> findByActdTranser(String transer);
		
	@Query("SELECT e from Actrand e where e.actrandCK.actdTranser=:transer AND e.actrandCK.actdCoy=:coy AND e.actdProprietor=:propritor")
	public Actrand findByActdTranserAndActdCoyAndActdProp(String transer, String coy, String propritor);
	
	
	@Query("SELECT e from Actrand e where trim(e.actrandCK.actdTranser) =:transerNo order by e.actrandCK.actdTranser asc, e.actrandCK.actdBunum ASC")
	public List<Actrand> findActdByTranserNo(String transerNo);

	
	public List<Actrand> findByActrandCK_ActdTranserAndActrandCK_ActdCoy(String transer, String coy);
	
	@Query("Select a from Actrand a where trim(a.actrandCK.actdTranser) = :transer and a.actrandCK.actdBunum = :bnum")
	Actrand findActdMinorByTranserAndBnum(String transer, Integer bnum);
	

	
	@Query(value = "select ac_acmajor,acname,ac_acminor,acminorname,ac_mintype,sum(ac_amount) as ac_amount "
			+ "from(select actd_acmajor as ac_acmajor,"
			+ "(select chart_acname from glchart where chart_acnum = actd_acmajor and chart_closedate is null) as acname,"
			+ "nvl(trim(actd_acminor),'') as ac_acminor,"
			+ "FUNC_GETMINORSNAME (actd_Acmajor, actd_acminor, actd_mintype, actd_trandate) as acminorname,"
			+ "actd_mintype as ac_mintype,"
			+ "actd_tranamt as ac_amount "
			+ "from actrand, actranh "
			+ "where trim(actd_coy) = ? "
			+ "and trim(actd_project) = ? "
			+ "and (actd_acmajor LIKE '4%' or  actd_acmajor='20404071' ) "
			+ "and acth_transer = actd_transer and acth_coy = actd_coy and acth_postedyn = 'Y' and acth_reverseyn = 'N' "
			+ "and trunc(actd_trandate) >= ? "
			+ "and trunc(actd_trandate) <= ? "
			+ "and ((ACTD_TRANSER NOT LIKE 'IA%' ) OR ACTD_TRANSER NOT IN ('J000202502','J000202503','J000202504','J000202506','J000202507'))) "
			+ "group by ac_acmajor, acname, ac_acminor,  acminorname,ac_mintype "
			+ "having sum(ac_amount) <> 0  order by ac_acmajor"
			, nativeQuery = true)
	List<Tuple> fetchTraiBalance(String companyCode, String projectCode, LocalDate fromDate, LocalDate toDate);
	
	@Query(value = "select ac_acmajor,acname,ac_acminor,acminorname,ac_mintype,sum(ac_amount) as ac_amount "
			+ "from(select actd_acmajor as ac_acmajor,"
			+ "(select chart_acname from glchart where chart_acnum = actd_acmajor and chart_closedate is null) as acname,"
			+ "nvl(trim(actd_acminor),'') as ac_acminor,"
			+ "FUNC_GETMINORSNAME (actd_Acmajor, actd_acminor, actd_mintype, actd_trandate) as acminorname,"
			+ "actd_mintype as ac_mintype,"
			+ "actd_tranamt as ac_amount "
			+ "from actrand, actranh "
			+ "where trim(actd_coy) = ? "
			+ "and trim(actd_project) = ? "
			+ "and (actd_acmajor LIKE '4%' or  actd_acmajor='20404071' ) "
			+ "and acth_transer = actd_transer and acth_coy = actd_coy and acth_postedyn = 'Y' and acth_reverseyn = 'N' "
			+ "and ((ACTD_TRANSER NOT LIKE 'IA%' ) OR ACTD_TRANSER NOT IN ('J000202502','J000202503','J000202504','J000202506','J000202507'))) "
			+ "group by ac_acmajor, acname, ac_acminor,  acminorname,ac_mintype "
			+ "having sum(ac_amount) <> 0  order by ac_acmajor"
			, nativeQuery = true)
	List<Tuple> fetchTraiBalance(String companyCode, String projectCode);
	
	@Query(value = "select getASSYear(to_date(?, 'dd/MM/yyyy'))  from dual", nativeQuery = true)
	String getAssYear(String recDate);
}
