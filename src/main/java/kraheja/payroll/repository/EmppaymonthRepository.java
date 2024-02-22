// Developed By  - 	kalpana.m
// Developed on - 13-02-24
// Mode  - Data Entry
// Purpose - Emppaymonth Entry / Edit
// Modification Details
// =======================================================================================================================================================
// Date		Coder  Version User    Reason              
// =======================================================================================================================================================

package kraheja.payroll.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Emppaymonth ;
import kraheja.payroll.entity.EmppaymonthCK;
@Repository
public interface EmppaymonthRepository extends JpaRepository<Emppaymonth, EmppaymonthCK> {

	 
	List<Emppaymonth> findByEmppaymonthCK_EmpmEmpcodeAndEmppaymonthCK_EmpmSalarytypeAndEmppaymonthCK_EmpmPaymonth(String empcode, String salarytype, String paymonth) ; 

}