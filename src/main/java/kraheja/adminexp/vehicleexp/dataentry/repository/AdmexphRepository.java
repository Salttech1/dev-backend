package kraheja.adminexp.vehicleexp.dataentry.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.vehicleexp.dataentry.bean.response.PreviousAdmexphDetailsBean;
import kraheja.adminexp.vehicleexp.dataentry.entity.Admexph ;
import kraheja.adminexp.vehicleexp.dataentry.entity.AdmexphCK;
@Repository
public interface AdmexphRepository extends JpaRepository<Admexph, AdmexphCK> {
	 
	Admexph findByAdmexphCK_AdmhCertnumAndAdmexphCK_AdmhCoy(String certnum, String coy) ;
	
	Admexph findByAdmexphCK_AdmhCertnumAndAdmhEquipid(String certnum, String equipid);
	
	@Query("SELECT new kraheja.adminexp.vehicleexp.dataentry.bean.response.PreviousAdmexphDetailsBean(a.admexphCK.admhCertnum,a.admhCertdate,a.admhCerttype,a.admhCertamount, a.admhPayamount) FROM Admexph a  \r\n "
			+ " WHERE a.admexphCK.admhCertnum in (SELECT max(b.admexphCK.admhCertnum) FROM Admexph b \r\n "
			+ " WHERE trim(b.admhEquipid) = :equipnum and trim(b.admhPartycode) = :partycode and trim(b.admhSocid) = 'VEHICLE'  \r\n"
			+ " and trim(b.admexphCK.admhCertnum) != :certnum)")
	PreviousAdmexphDetailsBean findByAdmhEquipidAdmhPartycodeAdmexphCK_AdmhCertnum(String equipnum,String partycode,String certnum);
	
	@Query(value = "SELECT  \r\n "
			+ "       a.ADMH_CERTNUM,\r\n"
			+ "       (SELECT ent_name\r\n"
			+ "        FROM   entity\r\n"
			+ "        WHERE  ent_class = 'VEHTY'\r\n"
			+ "               AND ent_id = e.eqp_vehtype) AS vehtype,\r\n"
			+ "       a.ADMH_COY,\r\n"
			+ "       a.ADMH_PROP,\r\n"
			+ "       TRIM(a.ADMH_PARTYCODE)              AS ADMH_PARTYCODE,\r\n"
			+ "       E.eqp_allottedto,\r\n"
			+ "       A.ADMH_TRANSER\r\n"
			+ "FROM   ADMEXPH a,\r\n"
			+ "       equip e,\r\n"
			+ "       actranh\r\n"
			+ "WHERE  a.admh_certstatus = '5'\r\n"
			+ "       AND a.admh_transer = acth_transer\r\n"
			+ "       AND a.admh_coy = acth_coy\r\n"
			+ "       AND acth_postedyn = 'N'\r\n"
			+ "       AND acth_reverseyn = 'N'\r\n"
			+ "       AND a.ADMH_COY = e.eqp_coy\r\n"
			+ "       AND a.ADMH_PROP = e.eqp_prop\r\n"
			+ "       AND a.admh_equipid = e.eqp_eqpnum\r\n"
			+ "       AND A.admh_socid = 'VEHICLE'\r\n"
			+ "       AND trim(e.eqp_eqpnum) = :equipnum\r\n"
			+ "       AND a.ADMH_PASSEDON IS NOT NULL\r\n"
			+ "ORDER  BY a.ADMH_CERTNUM " ,nativeQuery = true)
	List<Tuple> findByEquipid(String equipnum);
	
	
	@Query(value = "select ADMd_CERTNUM,\r\n"
			+ "			admd_workcode,\r\n"
			+ "			admd_billref,\r\n"
			+ "			To_char(admd_billdate,'dd/mm/yyyy') as admd_billdate,\r\n"
			+ "			To_char(admd_billamount) as admd_billamount,\r\n"
			+ "			To_char(admd_durationfrom,'dd/mm/yyyy') as admd_durationfrom,\r\n"
			+ "			To_char(admd_durationupto,'dd/mm/yyyy') as admd_durationupto \r\n"
			+ "from admexpd \r\n"
			+ "where admd_certnum in (\r\n"
			+ "SELECT a.ADMH_CERTNUM\r\n"
			+ "FROM   ADMEXPH a,\r\n"
			+ "       equip e,\r\n"
			+ "       actranh\r\n"
			+ "WHERE  a.admh_certstatus = '5'\r\n"
			+ "       AND a.admh_transer = acth_transer\r\n"
			+ "       AND a.admh_coy = acth_coy\r\n"
			+ "       AND acth_postedyn = 'N'\r\n"
			+ "       AND acth_reverseyn = 'N'\r\n"
			+ "       AND a.ADMH_COY = e.eqp_coy\r\n"
			+ "       AND a.ADMH_PROP = e.eqp_prop\r\n"
			+ "       AND a.admh_equipid = e.eqp_eqpnum\r\n"
			+ "       AND A.admh_socid = 'VEHICLE'\r\n"
			+ "       AND trim(e.eqp_eqpnum) = :equipnum\r\n"
			+ "       AND a.ADMH_PASSEDON IS NOT NULL\r\n"
			+ ")",nativeQuery = true)
	List<Tuple> findbyVehDetailByEquipid(String equipnum);
	
	@Modifying
	@Query(value="Update Admexph a set a.admhCertstatus = '1', a.admhPrintedon = null, a.admhPassedon = null, a.admhSite = :site, a.admhUserid = :userid, a.admhToday = :today  "
			+ "where trim(a.admexphCK.admhCertnum)= :certnum and a.admhCertstatus = '5' and trim(a.admhTranser)= :transer")
	void cancelUnPostedCertificate(String certnum, String transer, String site, String userid, LocalDateTime today);
}