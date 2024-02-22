package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Temp_Drscrsageing;
import kraheja.purch.entity.TempDrscrsageingCK;
@Repository
public interface Temp_DrscrsageingRepository extends JpaRepository<Temp_Drscrsageing, TempDrscrsageingCK> {

	 
	Temp_Drscrsageing findByTempDrscrsageingCK_TempSesidAndTempDrscrsageingCK_TempSrnoAndTempDrscrsageingCK_TempPartycode(Double sesid, Double srno, String partycode) ; 

}