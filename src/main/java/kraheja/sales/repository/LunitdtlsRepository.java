// Developed By  - 	vikram.p
// Developed on - 16-12-23
// Mode  - Data Entry
// Purpose - Lunitdtls Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Lunitdtls;
import kraheja.sales.entity.LunitdtlsCK;

@Repository
public interface LunitdtlsRepository extends JpaRepository<Lunitdtls, LunitdtlsCK> {

	Lunitdtls findByLunitdtlsCK_LessorPropcodeAndLunitdtlsCK_LessorUnitidAndLunitdtlsCK_LessorUnitno(String propcode,
			String unitid, String unitno);

	@Query("SELECT COUNT(*) FROM Lunitdtls WHERE trim(LESSOR_PROPCODE) = :propCode")
	Integer findCountPropcode(String propCode);

}