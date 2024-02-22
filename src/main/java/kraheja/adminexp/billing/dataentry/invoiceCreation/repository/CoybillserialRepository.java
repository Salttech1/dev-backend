package kraheja.adminexp.billing.dataentry.invoiceCreation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Coybillserial;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.CoybillserialCK;

@Repository
public interface CoybillserialRepository extends JpaRepository<Coybillserial, CoybillserialCK> {

	Coybillserial findByCoybillserialCK_CbsCoycodeAndCoybillserialCK_CbsChargecodeAndCoybillserialCK_CbsBilltypeAndCoybillserialCK_CbsSitenameAndCoybillserialCK_CbsYear(
			String coycode, String chargecode, String billtype, String sitename, String year);

	@Query(value = "SELECT c FROM Coybillserial c WHERE TRIM(c.coybillserialCK.cbsCoycode) = :coycode AND TRIM(c.coybillserialCK.cbsChargecode) LIKE :chargecode AND TRIM(c.coybillserialCK.cbsBilltype) = :billtype AND TRIM(c.coybillserialCK.cbsSitename) = :sitename AND TRIM(c.coybillserialCK.cbsYear) = :year")
	Coybillserial findCustomResults(@Param("coycode") String coycode, @Param("chargecode") String chargecode,
			@Param("billtype") String billtype, @Param("sitename") String sitename, @Param("year") String year);

	@Query(value = "SELECT GetAcYear(TO_DATE(:year, 'YYYY'), 'ACC') AS Academic_Year FROM DUAL", nativeQuery = true)
	String findAcademicYear(@Param("year") String year);

	@Modifying
	@Query("UPDATE Coybillserial c " + "SET c.cbsSrno = :serial, " + "    c.cbsModifiedon = CURRENT_TIMESTAMP, "
			+ "    c.cbsUserid = :user " + "WHERE TRIM(c.coybillserialCK.cbsCoycode) = TRIM(:coycode) "
			+ "  AND TRIM(c.coybillserialCK.cbsChargecode) LIKE CONCAT('%', TRIM(:chargecode), '%') "
			+ "  AND TRIM(c.coybillserialCK.cbsBilltype) = TRIM(:billtype) "
			+ "  AND TRIM(c.coybillserialCK.cbsSitename) = TRIM(:sitename) "
			+ "  AND TRIM(c.coybillserialCK.cbsYear) = TRIM(:year)")
	void updateCoybillserial(@Param("serial") Double serial, @Param("user") String user,
			@Param("coycode") String coycode, @Param("chargecode") String chargecode,
			@Param("billtype") String billtype, @Param("sitename") String sitename, @Param("year") String year);
}
