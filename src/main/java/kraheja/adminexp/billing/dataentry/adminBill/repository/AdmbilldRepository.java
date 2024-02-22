package kraheja.adminexp.billing.dataentry.adminBill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbilld;
import kraheja.adminexp.billing.dataentry.adminBill.entity.AdmbilldCK;


@Repository
public interface AdmbilldRepository extends JpaRepository<Admbilld, AdmbilldCK> {

	List<Admbilld> findByAdmbilldCK_AdbldSer(String ser);

	@Query("select e from Admbilld e where trim(e.admbilldCK.adbldSer)=:ser")
	Admbilld findByAdmbilldCK_AdbldSerNum(String ser);

	@Query("select e from Admbilld e where trim(e.admbilldCK.adbldSer)=:ser and trim(e.admbilldCK.adbldLineno)=:lineNo")
	Admbilld findByAdmbilldCK_AdbldSerAndAdmbilldCK_AdbldLineno(String ser, String lineNo);
	
	@Query("select e from Admbilld e where trim(e.admbilldCK.adbldSer)=:ser and trim(e.adbldHsnsaccode)=:hsnSacCode")
	Admbilld findByAdmbilldCK_AdbldSerAndAdbldHsnsaccode(String ser, String hsnSacCode);
	
	@Modifying
	@Query("delete Admbilld e where trim(e.admbilldCK.adbldSer)=:ser")
	public void deleteByAdmbilldCK_AdbldSerNum(String ser);

}