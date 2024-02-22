package kraheja.commons.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Actranh;
import kraheja.commons.entity.ActranhCK;

@Repository
public interface ActranhRepository extends JpaRepository<Actranh, ActranhCK> {
	
	@Modifying
	@Query("delete Actranh e where e.actranhCK.acthTranser=:acthTranser")
	public void deleteActranh(String acthTranser);
	
	@Modifying
	@Query("Update Actranh a SET a.acthReverseyn = :reverseyn where trim(a.actranhCK.acthTranser) = :transer ")
	public void updateActranhReverseYN(String reverseyn ,String transer);
	
	@Modifying
	@Query("Update Actranh a SET a.acthReverseyn = :reverseyn , a.acthUserid=:userid, a.acthSite =:site, a.acthToday =:today where trim(a.actranhCK.acthTranser) = :transer")
	public void updateActranhReverseYNWithUserAndSiteAndToday(String reverseyn , String transer, String userid, String site, LocalDateTime today);

	@Query("Select a from Actranh a where trim(a.actranhCK.acthTranser) = :ser and trim(a.acthReverseyn) <> :reverseyn")
	public Actranh findByActranhCK_ActhTranserAndReverseYN(String ser, String reverseyn);
	
	@Query("Select a from Actranh a where trim(a.actranhCK.acthTranser) = :ser")
	public Actranh findByActranhCK_ActhTranser(String ser);
	
	Actranh findByActranhCK_ActhTranserAndActranhCK_ActhCoy(String transer, String coy);
	
	@Query("Select a.acthReverseyn From Actranh a where trim(a.actranhCK.acthTranser)= :transer and trim(a.actranhCK.acthCoy)= :coy")
	String findActranhReversedYNByTranserAndCoy(String transer, String coy);
	
	@Query("Select a.acthReverseyn From Actranh a where trim(a.actranhCK.acthTranser) = :transer")
	String findActranhReversedYNByTranser(String transer);
	
	@Query("Select to_char(max(a.acthTrandate),'dd/mm/yyyy') from Actranh a where trim(a.actranhCK.acthTranser) in (:transerNos)")
	String findMaxranDateByTranserNos(List<String> transerNos);
	
	@Query("select a from Actranh a where a.actranhCK.acthTranser= :transer")
	Actranh fetchActranh(String transer);
}