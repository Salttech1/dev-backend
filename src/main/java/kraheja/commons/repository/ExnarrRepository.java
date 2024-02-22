package kraheja.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Exnarr;
import kraheja.commons.entity.ExnarrCK;

@Repository
public interface ExnarrRepository extends JpaRepository<Exnarr, ExnarrCK> {
	
	
	@Modifying
	@Query("Delete from Exnarr e where trim(e.exnarrCK.exnTranser) = :transer ")
	void deleteExnarrByTranser(String transer);

}
