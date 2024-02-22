package kraheja.sales.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Outrate ;
import kraheja.sales.entity.OutrateCK;
@Repository
public interface OutrateRepository extends JpaRepository<Outrate, OutrateCK> {

	 
	Outrate findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWingAndOutrateCK_OutrStartdate(String bldgcode, String flatnum, String wing, String startdate) ;  
	
	List<Outrate> findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnumAndOutrateCK_OutrWing(String bldgcode, String flatnum, String wing) ;
	
//	List<Outrate> findByOutrateCK_OutrBldgcodeAndOutrateCK_OutrFlatnum(String bldgcode, String flatnum) ;

	@Query("select COALESCE(min(o.outrateCK.outrStartdate), '201707') from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrFlatnum=:flatno and o.outrateCK.outrWing=:wing and o.outrBilltype=:billtype and ((o.outrateCK.outrStartdate) BETWEEN '201707' and '209912')")
	List<String> fetchStartDate(String bldgcode, String wing, String flatno, String billtype);  
	
	@Query("select min(o.outrateCK.outrStartdate) from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrFlatnum=:flatno and o.outrateCK.outrWing=' ' and o.outrBilltype=:billtype and ((o.outrateCK.outrStartdate) BETWEEN '201707' and '209912')")
	List<String> fetchStartDate(String bldgcode, String flatno, String billtype);  
	
	@Query("select max(o.outrEnddate) from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrFlatnum=:flatno and o.outrateCK.outrWing=:wing and o.outrBilltype=:billtype and ((o.outrateCK.outrStartdate) BETWEEN '201707' and '209912')")
	List<String> fetchEndDate(String bldgcode, String wing, String flatno, String billtype);  
	
	@Query("select max(o.outrEnddate) from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrFlatnum=:flatno and o.outrateCK.outrWing=' ' and o.outrBilltype=:billtype and ((o.outrateCK.outrStartdate) BETWEEN '201707' and '209912')")
	List<String> fetchEndDate(String bldgcode, String flatno, String billtype);  
	
	//@Query("select o.outrAuxirate as outrAuxirate from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatno and o.outrateCK.outrStartdate<=:month and o.outrEnddate>=:month and o.outrBilltype='F'") //NS 14.08.2023
//	@Query("select o.outrAuxirate from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatno and o.outrateCK.outrStartdate<=:month and o.outrEnddate>=:month and o.outrBilltype=:billtype") //NS 14.08.2023
//	String fetchMaintainanceRateForAuxilliary(String bldgcode, String wing, String flatno, String month, String billtype);
	@Query("select o.outrAuxirate from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatno and o.outrBilltype=:billtype") //NS 08.09.2023(Change has been done to the API to reduce the requests by permission of Utpal sir.
	String fetchMaintainanceRateForAuxilliary(String bldgcode, String wing, String flatno, String billtype);
	
	@Query("select o.outrAuxiadmin from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatno and o.outrBilltype=:billtype") //NS 17.08.2023 //NS 08.09.2023(Change has been done to the API to reduce the requests by permission of Utpal sir.
	String fetchAdminRateForAuxilliary(String bldgcode, String wing, String flatno, String billtype);
	
	@Query("select o.outrAuxi_Tds from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatno and o.outrBilltype=:billtype") //NS 17.08.2023 //NS 08.09.2023(Change has been done to the API to reduce the requests by permission of Utpal sir.
	String fetchTDSRateForAuxilliary(String bldgcode, String wing, String flatno, String billtype);
	
	@Query("select o.outrateCK.outrStartdate from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatnum and o.outrBilltype=:billtype") //NS 04.09.2023
	String fetchStartdateForAuxiGSTFirst(String bldgcode, String wing, String flatnum, String billtype);
	
	@Query("select o.outrEnddate from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrWing=:wing and o.outrateCK.outrFlatnum =:flatnum and o.outrBilltype=:billtype") //NS 04.09.2023
	String fetchEndDeteForAuxiGSTFirst(String bldgcode, String wing, String flatnum, String billtype);

	//	OUTR_INFRRATE OUTRINFRRATE RATE FETCHING START BY SAZZAD
	@Query(value = "select nvl(outr_infrrate, 0) where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	double findOutrAuxiRateMonthWiseForInfra(String bldgcode, String wing, String flatno, String billtype, String startDate);
	
	//outrInfradmin
	@Query(value = "select nvl(outr_infradmin, 0.00) from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	double findAdminRateMonthWiseForInfra(String bldgcode, String wing, String flatno, String billtype, String startDate);
	
	
	@Query(value = "select nvl(outr_auxirate, 0) from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and TRIM(outr_billtype)=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	String findOutrAuxiRateMonthWiseForAuxi(String bldgcode, String wing, String flatno, String billtype, String startDate);
	
//	@Query(value = "select outr_auxiadmin from outrate where trim(outr_bldgcode)=? and outr_wing=? and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	@Query(value = "select nvl(outr_auxiadmin, 0) from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and TRIM(outr_billtype)=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	String findAdminRateMonthWiseForAuxi(String bldgcode, String wing, String flatno, String billtype, String startDate);
	
	@Query(value = "select nvl(outr_auxiadmin, 0) from outrate where outr_bldgcode=? and outr_wing=' ' and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912' ", nativeQuery = true)
	double findAdminRateForEmptyWing(String bldgCode, String flatNumber, String billType, String startDate);
	
	@Query(value = "select nvl(outr_infradmin, 0) from outrate where outr_bldgcode=? and outr_wing=' ' and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912' ", nativeQuery = true)
	String findAdminRateForInfrEmptyWing(String bldgCode, String flatNumber, String billType, String startDate);
	
	@Query(value = "select nvl(outr_auxirate, 0) from outrate where outr_bldgcode=? and outr_wing=' ' and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912' ", nativeQuery = true)
	String findAuxiRateForEmptyWing(String bldgCode, String flatNumber, String billType, String startDate);
	
	@Query(value = "select nvl(outr_infrrate, 0) from outrate where outr_bldgcode=? and outr_wing=' ' and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912' ", nativeQuery = true)
	String findAuxiRateForInfrEmptyWing(String bldgCode, String flatNumber, String billType, String startDate);
//	OUTR_INFRRATE OUTRINFRRATE RATE FETCHING END BY SAZZAD
	
	@Query("select COALESCE(min(o.outrateCK.outrStartdate), '201707') from Outrate o where o.outrateCK.outrBldgcode=:bldgcode and o.outrateCK.outrFlatnum=:flatno and o.outrateCK.outrWing=:wing and o.outrBilltype=:billtype")
	String findAdminRateMonthWiseForAuxi(String bldgcode, String wing, String flatno, String billtype);  
	
	@Query(value = "select nvl(outr_infra_tds, 0) from outrate where outr_bldgcode=? and outr_wing=' ' and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	double findTdsRateForEmptyWingMonthWise(String bldgcode, String flatno, String billtype, String startDate);
	
	@Query(value = "select nvl(outr_infra_tds, 0) from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and outr_billtype=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	double findTdsRateMonthWise(String bldgcode, String wing, String flatno, String billtype, String startDate);
	
	@Query(value = "select outr_startdate from outrate where trim(outr_bldgcode)=? and outr_wing= ? and trim(outr_flatnum)=? and outr_billtype = ? and trim(outr_startdate) BETWEEN ? and '209912'", nativeQuery = true)
	Character fetchStartDate(String bldgcode, String wing, String flatnum, String billtype, String date);
	
	@Query(value = "select outr_startdate from outrate where trim(outr_bldgcode)=? and outr_wing= ? and trim(outr_flatnum)=? and outr_billtype = ? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	String fetchStartDateBybldgCodeWingFlatBillTypeAndBetweenDate(String bldgcode, String wing, String flatnum, String billtype, String date);
	
	@Query(value = "select o.outr_enddate from outrate o where trim(o.outr_bldgcode)=? and trim(o.outr_wing)=? and trim(o.outr_flatnum)=? and trim(o.outr_billtype)=? and outr_startdate BETWEEN ? and '209912'", nativeQuery = true)
	String fetchEndDateBybldgCodeWingFlatBillTypeAndBetweenDate(String bldgcode, String wing, String flatnum, String billtype, String date);

	@Query(value = "select min(outr_startdate) from outrate where outr_bldgcode=? and outr_flatnum=? and outr_wing=? and outr_billtype='N' and (nvl(outr_infrrate,0)) > 0", nativeQuery = true)
	String fetchInapNormalStartDate(String buildingCode, String wing, String flatNum, String billType);
	
	@Query(value = "select min(outr_startdate) from outrate where outr_bldgcode=? and outr_flatnum=? and outr_wing=' ' and outr_billtype='N' and (nvl(outr_infrrate,0)) > 0", nativeQuery = true)
	String fetchInapNormalStartDateWithEmptyWing(String buildingCode, String flatNum, String billType);

	@Query(value = "select min(outr_startdate) from outrate where outr_bldgcode=? and outr_flatnum=? and outr_wing=? and outr_billtype=?", nativeQuery = true)
	String fetchInapFirstStartDate(String buildingCode, String flatNum, String wing, String billType);
	
	@Query(value = "select min(outr_startdate) from outrate where trim(outr_bldgcode)=? and trim(outr_flatnum)=? and outr_wing=' ' and outr_billtype=?", nativeQuery = true)
	String fetchInapFirstStartDate(String buildingCode, String flatNum, String billType);

	@Query(value = "select min(outr_startdate) from outrate where outr_bldgcode=? and TRIM(outr_flatnum)=?  and TRIM(outr_wing)=? and outr_billtype=? and (nvl(outr_auxirate,0)) > 0", nativeQuery = true)
	String fetchAuxiNormalStartDate(String buildingCode, String flatNum, String wing, String billType);
	
	@Query(value = "select min(outr_startdate) from outrate where outr_bldgcode=? and TRIM(outr_flatnum)=?  and outr_wing=' ' and outr_billtype=? and (nvl(outr_auxirate,0)) > 0", nativeQuery = true)
	String fetchAuxiNormalStartDate(String buildingCode, String flatNum, String billType);

	@Query(value = "select min(outr_startdate) from outrate where TRIM(outr_bldgcode)=? and TRIM(outr_flatnum)=? and TRIM(outr_wing)=? and outr_billtype=?", nativeQuery = true)
	String fetchAuxiFirstStartDate(String buildingCode, String flatNum, String wing, String billType);
	
	@Query(value = "select min(outr_startdate) from outrate where TRIM(outr_bldgcode)=? and TRIM(outr_flatnum)=? and outr_wing=' ' and outr_billtype=?", nativeQuery = true)
	String fetchAuxiFirstStartDate(String buildingCode, String flatNum, String billType);
	
	@Query(value = "select outrate0_.outr_infrrate as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchInfrRateForInfraWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
	
	@Query(value = "select outrate0_.outr_infrrate as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchInfrRateForInfra(String buildingCode, String flatNum, String wing, String startDate, String billType);
	
	@Query(value = "select outrate0_.outr_infradmin as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchAdminRateForInfraWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
	@Query(value = "select outrate0_.outr_infradmin as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchAdminRateForInfra(String buildingCode, String flatNum, String wing, String startDate, String billType);
	

	@Query(value = "select outrate0_.outr_auxirate as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchInfrRateForAuxiWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
//	@Query(value = "select outrate0_.outr_auxirate as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
//	String fetchInfrRateForAuxi(String bldgcode, String flatNum, String wing, String startDate, String billType);
	
	@Query(value = "select outr_auxirate from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and trim(outr_billtype)=? and (trim(outr_startdate) between ? and '209912')", nativeQuery = true)
	String fetchInfrRateForAuxi(String bldgcode, String wing, String flatno ,String billtype, String startMonth);  
	
	@Query(value = "select outrate0_.outr_auxiadmin as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchAdminRateForAuxiWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
	@Query(value = "select outr_auxiadmin from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and trim(outr_billtype)=? and (trim(outr_startdate) between ? and '209912')", nativeQuery = true)
//	@Query(value = "select outrate0_.outr_auxiadmin as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchAdminRateForAuxi(String bldgcode,String wing,String flatNum, String billType, String startDate);
	
	@Query(value = "select outrate0_.outr_infra_tds as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchTdsRateForInfraWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
	@Query(value = "select outrate0_.outr_infra_tds as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchTdsRateForInfra(String buildingCode, String flatNum,String wing, String startDate, String billType);
	

	@Query(value = "select outrate0_.outr_auxi_tds as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=' ' and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	String fetchTdsRateForAuxiWithEmptyWing(String bldgcode, String flatNum, String startDate, String billType);
	
//	@Query(value = "select outrate0_.outr_auxi_tds as col_0_0_ from outrate outrate0_ where outrate0_.outr_bldgcode=? and TRIM(outrate0_.outr_flatnum)=? and outrate0_.outr_wing=? and (outrate0_.outr_startdate between ? and '209912') and outrate0_.outr_billtype=?", nativeQuery = true)
	@Query(value = "select outr_auxi_tds from outrate where trim(outr_bldgcode)=? and trim(outr_wing)=? and trim(outr_flatnum)=? and trim(outr_billtype)=? and (trim(outr_startdate) between ? and '209912')", nativeQuery = true)
	String fetchTdsRateForAuxi(String bldgcode,String wing, String flatNum,String billType, String startDate);
}