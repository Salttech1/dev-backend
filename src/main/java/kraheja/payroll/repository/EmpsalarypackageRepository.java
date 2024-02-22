package kraheja.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Tuple;

import kraheja.payroll.bean.EmpsalarypackageEarnDedBean;
import kraheja.payroll.entity.Empsalarypackage ;
import kraheja.payroll.entity.EmpsalarypackageCK;
@Repository
public interface EmpsalarypackageRepository extends JpaRepository<Empsalarypackage, EmpsalarypackageCK> {

	 
	Empsalarypackage findByEmpsalarypackageCK_EspkEmpcodeAndEmpsalarypackageCK_EspkEarndedcodeAndEmpsalarypackageCK_EspkEffectivefrom(String empcode, String earndedcode, LocalDateTime effectivefrom) ; 
	
	List<Empsalarypackage> findByEmpsalarypackageCK_EspkEmpcode(String empcode) ;
	
	@Query(value = "SELECT ESPK_EARNDEDCODE as earndedcode, \r\n"
			+ "		 (SELECT EDHM_DISPLAYNAME \r\n"
			+ "		  FROM EARNDEDMASTER  \r\n"
			+ "		  WHERE EDHM_EARNDEDCODE=ESPK_EARNDEDCODE)as earndeddesc,\r\n"
			+ "		 ESPK_PAYCYCLE as paycycle, \r\n"
			+ "		 ESPK_RATECYCLE  as ratecycle, \r\n"
			+ "		 ESPK_EARNDEDRATE as earndedrate, \r\n"
			+ "		 ESPK_EFFECTIVEFROM as effectivefrom, \r\n"
			+ "		 ESPK_EFFECTIVEUPTO as effectiveupto, \r\n"
			+ "		 ESPK_EMPCODE as empcode, \r\n"
			+ "		 ESPK_SITE as site, \r\n"
			+ "		 ESPK_USERID as userid, \r\n"
			+ "		 ESPK_MODULE as module, \r\n"
			+ "		 ESPK_MACHINENAME as machinename, \r\n"
			+ "		 ESPK_MODIFIEDON as modifiedon, \r\n"
			+ "		 ESPK_IPADDRESS as ipaddress, \r\n"
			+ "		 'E' as state, \r\n"
			+ "		 'A' as Active \r\n"
			+ "  FROM EMPSALARYPACKAGE,EARNDEDMASTER \r\n"
			+ " WHERE (trim(ESPK_EMPCODE)=:empcode) and  \r\n"
			+ "ESPK_EARNDEDCODE = EDHM_EARNDEDCODE AND \r\n"
			+ "			ESPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE  \r\n"
			+ "										  FROM EARNDEDMASTER  \r\n"
			+ "										 WHERE EDHM_EARNDEDTYPE = 'A' ) and  \r\n"
			+ "			ESPK_EFFECTIVEUPTO = (select max(espk_effectiveupto)  \r\n"
			+ "											from empsalarypackage  \r\n"
			+ "										  where trim(espk_empcode) = :empcode)\r\n"
			+ "Order by EDHM_COMPUTENO,ESPK_RATECYCLE,ESPK_EARNDEDCODE", nativeQuery = true)
	List<Tuple> findByCurrentEarnPackage(String empcode);
	
	@Query(value = "SELECT ESPK_EARNDEDCODE as earndedcode, \r\n"
			+ "		 (SELECT EDHM_DISPLAYNAME \r\n"
			+ "		  FROM EARNDEDMASTER  \r\n"
			+ "		  WHERE EDHM_EARNDEDCODE=ESPK_EARNDEDCODE)as earndeddesc,\r\n"
			+ "		 ESPK_PAYCYCLE as paycycle, \r\n"
			+ "		 ESPK_RATECYCLE  as ratecycle, \r\n"
			+ "		 ESPK_EARNDEDRATE as earndedrate, \r\n"
			+ "		 ESPK_EFFECTIVEFROM as effectivefrom, \r\n"
			+ "		 ESPK_EFFECTIVEUPTO as effectiveupto, \r\n"
			+ "		 ESPK_EMPCODE as empcode, \r\n"
			+ "		 ESPK_SITE as site, \r\n"
			+ "		 ESPK_USERID as userid, \r\n"
			+ "		 ESPK_MODULE as module, \r\n"
			+ "		 ESPK_MACHINENAME as machinename, \r\n"
			+ "		 ESPK_MODIFIEDON as modifiedon, \r\n"
			+ "		 ESPK_IPADDRESS as ipaddress, \r\n"
			+ "		 'D'  as state, \r\n"
			+ "		 'A' as Active \r\n"
			+ "  FROM EMPSALARYPACKAGE,EARNDEDMASTER \r\n"
			+ " WHERE (trim(ESPK_EMPCODE)=:empcode) and  \r\n"
			+ "ESPK_EARNDEDCODE = EDHM_EARNDEDCODE AND \r\n"
			+ "			ESPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE  \r\n"
			+ "										  FROM EARNDEDMASTER  \r\n"
			+ "										 WHERE EDHM_EARNDEDTYPE = 'D' ) and  \r\n"
			+ "			ESPK_EFFECTIVEUPTO = (select max(espk_effectiveupto)  \r\n"
			+ "											from empsalarypackage  \r\n"
			+ "										  where trim(espk_empcode) = :empcode)\r\n"
			+ "Order by EDHM_COMPUTENO,ESPK_RATECYCLE,ESPK_EARNDEDCODE", nativeQuery = true)
	List<Tuple> findByCurrentDedPackage(String empcode);
	
	@Query(value = "SELECT ESPK_EARNDEDCODE as earndedcode, \r\n"
			+ "		 (SELECT EDHM_DISPLAYNAME \r\n"
			+ "		  FROM EARNDEDMASTER  \r\n"
			+ "		  WHERE EDHM_EARNDEDCODE=ESPK_EARNDEDCODE)as earndeddesc,\r\n"
			+ "		 ESPK_PAYCYCLE as paycycle, \r\n"
			+ "		 ESPK_RATECYCLE  as ratecycle, \r\n"
			+ "		 ESPK_EARNDEDRATE as earndedrate, \r\n"
			+ "		 ESPK_EFFECTIVEFROM as effectivefrom, \r\n"
			+ "		 ESPK_EFFECTIVEUPTO as effectiveupto, \r\n"
			+ "		 ESPK_EMPCODE as empcode, \r\n"
			+ "		 ESPK_SITE as site, \r\n"
			+ "		 ESPK_USERID as userid, \r\n"
			+ "		 ESPK_MODULE as module, \r\n"
			+ "		 ESPK_MACHINENAME as machinename, \r\n"
			+ "		 ESPK_MODIFIEDON as modifiedon, \r\n"
			+ "		 ESPK_IPADDRESS as ipaddress, \r\n"
			+ "		 'E' as state, \r\n"
			+ "		 Case when (select max(espk_effectiveupto) from empsalarypackage where trim(espk_empcode) = :empcode) = ESPK_EFFECTIVEUPTO then 'A' else 'D' end as active \r\n"
			+ "  FROM EMPSALARYPACKAGE,EARNDEDMASTER \r\n"
			+ " WHERE (trim(ESPK_EMPCODE)=:empcode) and  \r\n"
			+ "ESPK_EARNDEDCODE = EDHM_EARNDEDCODE AND \r\n"
			+ "			ESPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE  \r\n"
			+ "										  FROM EARNDEDMASTER  \r\n"
			+ "										 WHERE EDHM_EARNDEDTYPE = 'A' )  \r\n"
			+ "Order by ESPK_EFFECTIVEUPTO DESC,EDHM_COMPUTENO,ESPK_RATECYCLE,ESPK_EARNDEDCODE", nativeQuery = true)
	List<Tuple> findByAllEarnPackage(String empcode);
	
	@Query(value = "SELECT ESPK_EARNDEDCODE as earndedcode, \r\n"
			+ "		 (SELECT EDHM_DISPLAYNAME \r\n"
			+ "		  FROM EARNDEDMASTER  \r\n"
			+ "		  WHERE EDHM_EARNDEDCODE=ESPK_EARNDEDCODE)as earndeddesc,\r\n"
			+ "		 ESPK_PAYCYCLE as paycycle, \r\n"
			+ "		 ESPK_RATECYCLE  as ratecycle, \r\n"
			+ "		 ESPK_EARNDEDRATE as earndedrate, \r\n"
			+ "		 ESPK_EFFECTIVEFROM as effectivefrom, \r\n"
			+ "		 ESPK_EFFECTIVEUPTO as effectiveupto, \r\n"
			+ "		 ESPK_EMPCODE as empcode, \r\n"
			+ "		 ESPK_SITE as site, \r\n"
			+ "		 ESPK_USERID as userid, \r\n"
			+ "		 ESPK_MODULE as module, \r\n"
			+ "		 ESPK_MACHINENAME as machinename, \r\n"
			+ "		 ESPK_MODIFIEDON as modifiedon, \r\n"
			+ "		 ESPK_IPADDRESS as ipaddress, \r\n"
			+ "		 'D'  as state, \r\n"
			+ "		 Case when (select max(espk_effectiveupto) from empsalarypackage where trim(espk_empcode) = :empcode) = ESPK_EFFECTIVEUPTO then 'A' else 'D' end as active \r\n"
			+ "  FROM EMPSALARYPACKAGE,EARNDEDMASTER \r\n"
			+ " WHERE (trim(ESPK_EMPCODE)=:empcode) and  \r\n"
			+ "ESPK_EARNDEDCODE = EDHM_EARNDEDCODE AND \r\n"
			+ "			ESPK_EARNDEDCODE IN (SELECT EDHM_EARNDEDCODE  \r\n"
			+ "										  FROM EARNDEDMASTER  \r\n"
			+ "										 WHERE EDHM_EARNDEDTYPE = 'D' )  \r\n"
			+ "Order by ESPK_EFFECTIVEUPTO DESC,EDHM_COMPUTENO,ESPK_RATECYCLE,ESPK_EARNDEDCODE", nativeQuery = true)
	List<Tuple> findByAllDedPackage(String empcode);

}