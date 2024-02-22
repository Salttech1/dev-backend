// Developed By  - 	vikram.p
// Developed on - 22-11-23
// Mode  - Data Entry
// Purpose - Lprop Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.sales.entity.Lprop ;
import kraheja.sales.entity.LpropCK;
@Repository
public interface LpropRepository extends JpaRepository<Lprop, LpropCK> {
	Lprop findByLpropCK_LessorPropcode(String propcode) ; 
	void deleteByLpropCK_LessorPropcode(String propcode) ; 

}