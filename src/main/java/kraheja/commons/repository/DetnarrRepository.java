// Developed By  - 	kalpana.m
// Developed on - 04-08-23
// Mode  - Data Entry
// Purpose - Detnarr Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.commons.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kraheja.commons.entity.Detnarr ;
import kraheja.commons.entity.DetnarrCK;
@Repository
public interface DetnarrRepository extends JpaRepository<Detnarr, DetnarrCK> {

	@Modifying
	@Query("delete Detnarr e where trim(e.detnarrCK.detTranser)=:detTranser")
	public void deleteDetnarr(String detTranser);
	 
	List<Detnarr> findByDetnarrCK_DetCoyAndDetnarrCK_DetTranserAndDetnarrCK_DetBunum(String coy, String transer, Double bunum) ; 

}