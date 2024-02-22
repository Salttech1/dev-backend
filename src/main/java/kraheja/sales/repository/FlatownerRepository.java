package kraheja.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Flatowner;
import kraheja.sales.entity.FlatownerCK;

@Repository
public interface FlatownerRepository extends JpaRepository<Flatowner, FlatownerCK> {

	@Query("select f.fownBillmode from Flatowner f where f.flatownerCK.fownOwnertype = '0' and trim(f.flatownerCK.fownOwnerid)= :ownerId")
	String getBillMode(String ownerId);

	@Query(value = "select fown_auximonths from flatowner where trim(fown_ownerid) = ? AND FOWN_OWNERTYPE = '0' ", nativeQuery = true)
	Integer getMonthCountAUXI(String ownerId);

	@Query(value = "select fown_inframonths from flatowner where trim(fown_ownerid) = ? AND FOWN_OWNERTYPE = '0' ", nativeQuery = true)
	Integer getMonthCountINAP(String ownerId);

	@Query(value = "select to_char(add_months(to_date(?,'dd/mm/yyyy') , ?  ),'yyyymm') from dual ", nativeQuery = true)
	String getMonthYearAcordingToChargeCode(String billDate, int count);

	List<Flatowner> findByFlatownerCK_FownOwnerid(String ownerid);

	Flatowner findByFlatownerCK_FownOwneridAndFlatownerCK_FownBldgcodeAndFlatownerCK_FownWingAndFlatownerCK_FownFlatnumAndFlatownerCK_FownOwnertype(
			String ownerid, String bldgcode, String wing, String flatnum, String ownertype);

	@Query(value = "delete from flatowner where trim(fown_ownerid) = ? ", nativeQuery= true)
	void deleteflatOwnerByOwnerId(String ownerId);
	
	@Query(value = "select TRIM(fown_name) from flatowner where fown_ownertype = '0' and trim(fown_ownerid) = ?", nativeQuery = true)
	String getFlatOwnerName(String ownerid);
	
}