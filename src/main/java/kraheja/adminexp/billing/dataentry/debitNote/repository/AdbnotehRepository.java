package kraheja.adminexp.billing.dataentry.debitNote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kraheja.adminexp.billing.dataentry.debitNote.entity.Adbnoteh;
import kraheja.adminexp.billing.dataentry.debitNote.entity.AdbnotehCK;

@Repository
public interface AdbnotehRepository extends JpaRepository<Adbnoteh, AdbnotehCK> {

	@Query(value = "SELECT ADBNH_PARTYTYPE, entity.ent_name AS partydesc, ADBNH_PARTYCODE, par_partyname, ADBNH_BLDGCODE, bldg_name, ADBNH_COY, coy_name, proj_name, ADBNH_INVBILLNO, ADBNH_DATE, ADBNH_INVBILLDT, ADBNH_BILLTYPE, ADBNH_AMOUNT, ADBNH_TDSPERC, ADBNH_TDSAMOUNT, ADBNH_NARRATION, ADBNH_DESCRIPTION1, ADBNH_DBNOTESER, ADBNH_PROP, ADBNH_PROJECT, ADBNH_FOTOAMT FROM ADBNOTEH LEFT JOIN entity ON entity.ent_id = ADBNH_PARTYTYPE AND entity.ent_class = 'PARTY' LEFT JOIN party ON par_partycode = ADBNH_PARTYCODE AND par_partytype = 'Z' AND (par_closedate IS NULL OR par_closedate = TO_DATE('01/01/2050','dd/mm/yyyy')) LEFT JOIN building ON bldg_code = ADBNH_BLDGCODE AND (bldg_closedate IS NULL OR bldg_closedate = TO_DATE('01/01/2050','dd/mm/yyyy')) LEFT JOIN company ON coy_code = ADBNH_COY AND (coy_closedate IS NULL OR coy_closedate = TO_DATE('01/01/2050','dd/mm/yyyy')) LEFT JOIN project ON (proj_code = ADBNH_PROJECT AND proj_company = ADBNH_COY) WHERE ADBNH_DBNOTESER = :dbnoteser", nativeQuery = true)
	List<Object[]> findByAdbnhDbnoteserJoinQuery(@Param("dbnoteser") String dbnoteser);
	
	@Query(value="select sum(e.adbnhAmount) from Adbnoteh e where trim(upper(e.adbnhInvbillno))= :adbnhInvbillno ")
	Double findtotalDebitAmountByAdbnhInvbillno(@Param("adbnhInvbillno") String adbnhInvbillno);
	
	Adbnoteh findByAdbnotehCK_AdbnhDbnoteser(@Param("dbnoteser") String dbnoteser);
	
	void deleteByAdbnotehCK_AdbnhDbnoteser(@Param("dbnoteser") String dbnoteser);

}
