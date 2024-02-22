package kraheja.adminexp.billing.dataentry.adminBill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.adminBill.entity.Admbillh;
import kraheja.adminexp.billing.dataentry.adminBill.entity.AdmbillhCK;


@Repository
public interface AdmbillhRepository extends JpaRepository<Admbillh, AdmbillhCK> {
	 
	Admbillh findByAdmbillhCK_AdblhSer(String ser) ;
	
	@Query(value="select sum(adblh_advnadjust) from admbillh where trim(adblh_partycode)=:partyCode and trim(adblh_partytype)=:partyType and trim(adblh_coy)=:coy and trim(adblh_bldgcode)=:bldgCode",nativeQuery = true)
	Double findAdblhAdjustSumByAdblhPartycodeAndAdblhBldgcodeAndAdblhCoyAndAdblhtypeAndadblhadvanceAdjust(String partyCode, String bldgCode, String coy, String partyType);

	//List<Admbillh> findByAdmbillhCK_Adblh_serOrAdblh_Suppbillno(String ser, String suppbillno);

	//List<Admbillh> findByAdmbillhCK_Adblh_serOrAdblh_Suppbillno(String ser, String suppbillno);
	
//	@Query(value="select adblh_ser from admbillh where trim(adblh_partytype) = :partyType AND trim(adblh_partycode) = :partyCode AND TRIM(UPPER(adblh_suppbillno)) = :suppBillNo",nativeQuery=true)
//	String findByPartyTypeAndPartyCodeAndSuppBillNo(String partyType, String partyCode, String suppBillNo);

	@Query("SELECT	e FROM Admbillh e WHERE TRIM(e.adblhPartytype)= :partyType AND TRIM(e.adblhPartycode) = :partyCode AND TRIM(adblhSuppbillno)=:suppBillNo")
	Admbillh findByPartyTypeAndPartyCodeAndSuppBillNo(String partyType, String partyCode, String suppBillNo);
	
	@Query("SELECT	e FROM Admbillh e WHERE TRIM(adblhSuppbillno)=:suppBillNo")
	Admbillh findByAdblhSuppBillNo(String suppBillNo);
	
	@Query(value="SELECT FUNC_GetGSTBillTranDate(TO_DATE(:suppBilldate, 'YYYY-MM-DD')) FROM dual",nativeQuery = true)
	String findGstTranDate(String suppBilldate );
	
	@Modifying
	@Query("delete Admbillh e where trim(e.admbillhCK.adblhSer)=:ser")
	void deleteByAdmbillhCK_AdblhSer(String ser);
}