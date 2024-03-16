package kraheja.payroll.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empleaveinfo ;
import kraheja.payroll.entity.EmpleaveinfoCK;
@Repository
public interface EmpleaveinfoRepository extends JpaRepository<Empleaveinfo, EmpleaveinfoCK> {

	 
	Empleaveinfo findByEmpleaveinfoCK_ElinEmpcodeAndEmpleaveinfoCK_ElinLeavecodeAndEmpleaveinfoCK_ElinAcyear(String empcode, String leavecode, String acyear) ; 
	
	List<Empleaveinfo> findByEmpleaveinfoCK_ElinEmpcodeOrderByEmpleaveinfoCK_ElinAcyearDesc(String empcode) ;
	
	@Query(value = "  SELECT ELIN_ACYEAR as acyear,   \r\n"
			+ "         ELIN_COMPOFFEARNED as compoffearned,   \r\n"
			+ "         ELIN_DAYEXCESSADJ as dayexcessadj,   \r\n"
			+ "         ELIN_DAYSAVAILED as daysavailed,   \r\n"
			+ "         ELIN_DAYSBF as daysbf,   \r\n"
			+ "         ELIN_DAYSENCASHED as daysencashed,   \r\n"
			+ "         ELIN_DAYSENTITLED as daysentitled,   \r\n"
			+ "         ELIN_EMPCODE as empcode,   \r\n"
			+ "         ELIN_IPADDRESS as ipaddress,   \r\n"
			+ "         ELIN_LEAVECODE as leavecode,   \r\n"
			+ "         ELIN_MACHINENAME as machinename,   \r\n"
			+ "         ELIN_MAXDAYSCF as maxdayscf,   \r\n"
			+ "         ELIN_MAXDAYSENC as maxdaysenc,   \r\n"
			+ "         ELIN_MODIFIEDON as modifiedon,   \r\n"
			+ "         ELIN_MODULE as module,   \r\n"
			+ "         ELIN_REMARK as remark,   \r\n"
			+ "         ELIN_SITE as site,   \r\n"
			+ "         ELIN_USERID as userid,\r\n"
			+ "         Case when ((select getacyear( sysdate ,'PAY') from dual) = elin_acyear or (select getacyear( sysdate - 400 ,'PAY') from dual) = elin_acyear) then 'A' else 'D' end as active\r\n"
			+ "    FROM EMPLEAVEINFO  \r\n"
			+ "   where Trim(ELIN_EMPCODE) = :empcode \r\n"
			+ "order by ELIN_ACYEAR desc " , nativeQuery = true)
	List<Tuple> findByEmpleaveinfoEmpCode(String empcode);

}