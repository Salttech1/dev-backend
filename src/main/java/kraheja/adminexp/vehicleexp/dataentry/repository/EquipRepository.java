package kraheja.adminexp.vehicleexp.dataentry.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.vehicleexp.dataentry.bean.response.VehicleInfoBean;
import kraheja.adminexp.vehicleexp.dataentry.entity.Equip ;
import kraheja.adminexp.vehicleexp.dataentry.entity.EquipCK;

@Repository
public interface EquipRepository extends JpaRepository<Equip, EquipCK> {

	 
	Equip findByEquipCK_EqpEqptypeAndEquipCK_EqpEqpnum(String eqptype, String eqpnum) ; 

	@Query("select new kraheja.adminexp.vehicleexp.dataentry.bean.response.VehicleInfoBean((select et.entName from DbEntity et where et.entityCk.entId = e.eqpVehtype and et.entityCk.entClass = 'VEHTY' and et.entityCk.entId != '0000')  as vehtype,\r\n"
			+ "e.eqpAllottedto,\r\n"
			+ "e.eqpCoy,\r\n"
			+ "(select c.coyName from Company c where c.companyCK.coyCode = e.eqpCoy and c.companyCK.coyClosedate = :closedate) as coyname,\r\n"
			+ "(select o.coyGstno from Company o where o.companyCK.coyCode = e.eqpCoy and o.companyCK.coyClosedate = :closedate) as gstno,\r\n"
			+ "e.eqpProp,\r\n"
			+ "(select p.propName from Proprietor p where p.proprietorCK.propCode = e.eqpProp) as propname, \r\n"
			+ "(Select a.admhEmeterred from Admexph a where a.admexphCK.admhCertnum = (SELECT	max(h.admexphCK.admhCertnum) FROM Admexph h, Admexpd d WHERE	h.admhEquipid = e.equipCK.eqpEqpnum  and h.admhSocid = 'VEHICLE' and d.admexpdCK.admdCertnum = h.admexphCK.admhCertnum  and d.admdWorkcode = 'PETR')) as startmeter) "
			+ "from Equip e where trim(e.equipCK.eqpEqpnum) = :equipnum")
	VehicleInfoBean findByEquipNum(Date closedate, String equipnum);
}