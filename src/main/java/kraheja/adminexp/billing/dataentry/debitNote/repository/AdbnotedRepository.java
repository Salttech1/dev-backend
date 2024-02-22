package kraheja.adminexp.billing.dataentry.debitNote.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.debitNote.entity.Adbnoted;
import kraheja.adminexp.billing.dataentry.debitNote.entity.AdbnotedCK;

@Repository
public interface AdbnotedRepository extends JpaRepository<Adbnoted, AdbnotedCK> {

	Adbnoted findByAdbnotedCK_AdbndDbnoteserAndAdbnotedCK_AdbndLineno(String dbnoteser, Integer lineno);

	@Query(value = "SELECT ADBND_SACCODE, ADBND_SACDESC, ADBND_QUANTITY, ADBND_RATE, ADBND_AMOUNT, ADBND_DISCOUNTAMT, ADBND_TAXABLEAMT, ADBND_CGSTPERC, ADBND_CGSTAMT, ADBND_SGSTPERC, ADBND_SGSTAMT, ADBND_IGSTPERC, ADBND_IGSTAMT, ADBND_UGSTPERC, ADBND_UGSTAMT FROM adbnoted WHERE ADBND_DBNOTESER = :dbnoteser ", nativeQuery = true)
	List<Object[]> findByAdbndDbnoteser(@Param("dbnoteser") String dbnoteser);

	List<Adbnoted> findByAdbnotedCK_AdbndDbnoteser(@Param("dbnoteser") String dbnoteser);
	
	void deleteByAdbnotedCK_AdbndDbnoteser(@Param("dbnoteser") String dbnoteser);

}