package kraheja.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.payroll.entity.Empassetinfo ;
import kraheja.payroll.entity.EmpassetinfoCK;
@Repository
public interface EmpassetinfoRepository extends JpaRepository<Empassetinfo, EmpassetinfoCK> {

	 
	Empassetinfo findByEmpassetinfoCK_EassEmpcodeAndEmpassetinfoCK_EassAssetcode(String empcode, String assetcode) ; 

	List<Empassetinfo> findByEmpassetinfoCK_EassEmpcode(String empcode) ;

}