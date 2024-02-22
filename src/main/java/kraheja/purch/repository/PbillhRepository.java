package kraheja.purch.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.bean.response.PaidBillResponseBean;
import kraheja.purch.entity.Pbillh ;
import kraheja.purch.entity.PbillhCK;

@Repository
public interface PbillhRepository extends JpaRepository<Pbillh, PbillhCK> {
	 
	Pbillh findByPbillhCK_PblhSer(String ser);
	
	@Query("select p  from Pbillh p WHERE   trim(p.pbillhCK.pblhSer) in :ser")
	List<Pbillh> findByPbillhCK_PblhSerIn(Set<String> ser);
	
	List<Pbillh> findByPblhSuppbillno(String supplierNumber);
	
	Pbillh findByPblhPartycodeAndPblhSuppbillno(String partyCode, String suppCode);
	
	@Query("select p  from Pbillh p WHERE  trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) in :supplierNumber AND trim(p.pblhBldgcode) = :buildingCode ORDER BY p.pbillhCK.pblhSer")
	List<Pbillh> findByPblhPartycodeAndPblhBldgcodeAndPblhSuppbillnoInOrderByPbillhCK_PblhSer(String partyCode, String buildingCode, Set<String> supplierNumber);
	
	@Query("select p  from Pbillh p WHERE  trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) in :supplierNumber AND trim(p.pblhBldgcode) = :buildingCode AND (p.pblhAuthnum is null AND p.pblhAuthdate  is null)")
	List<Pbillh> findByPblhPartycodeAndPblhBldgcodeAndPblhSuppbillnoInAndPblhAuthdateAndpblhAuthNumOrderByPbillhCK_PblhSer(String partyCode, String buildingCode, Set<String> supplierNumber);
	
	@Query("select p  from Pbillh p WHERE  trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :supplierNumber")
	List<Pbillh> findByPblhSuppbillnoAndPartyCode(String supplierNumber, String partyCode);
	
	@Query("select p.pblhAmount  from Pbillh p WHERE trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :supplierNumber AND trim(p.pblhBldgcode) = :buildingCode AND trim(p.pblhSuppbilldt) = :suppBilldt AND trim(p.pblhAuthnum) =:authNum")
	Double findByPblhSuppbillnoAndPartyCodeAndBldgcodeAndSuppbilldtAndAuthnum(String supplierNumber, String partyCode, String buildingCode, String  suppBilldt, String authNum);
	
	@Query("select p  from Pbillh p WHERE trim(p.pblhPartytype) = :partyType AND trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :supplierNumber")
	Pbillh findByPblhPartytypeAndPblhPartycodeAndPblhSuppbillno(String partyType, String partyCode, String supplierNumber);
	
	@Query("select p.pblhAuthnum  from Pbillh p WHERE trim(p.pblhBldgcode) = :blgCode AND trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :suppBillNo AND p.pblhAuthnum is not null")
	String findPblhAuthNumByPartyCodeAndSuppbillnsoAndBldgcode(String partyCode,String blgCode , String suppBillNo);
	
	@Query("select p.pbillhCK.pblhSer from Pbillh p WHERE trim(p.pblhBldgcode) = :blgCode AND trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :supplierNumber")
	String findSerByPblhBldgcodeAndPblhPartycodeAndPblhSuppbillno(String blgCode, String partyCode, String supplierNumber);

	@Query("select p  from Pbillh p WHERE  trim(p.pblhPartycode) = :partyCode AND trim(p.pblhSuppbillno) = :supplierNumber")
	List<Object> findByPblhSuppbillnoAndPartyCodeAndSer(String supplierNumber, String partyCode);
	
	@Query("select p.pblhDebitamt, p.pblhPaidamt, p.pblhTdsamount  from Pbillh p WHERE trim(p.pbillhCK.pblhSer) = :ser")
	List<Object[]> findPbillDebitAndPaidAmntBySer(String ser);
	
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhDebitamt = :debitAmnt, ph.pblhPaidamt = :paidAmnt, ph.pblhTdsamount = :tdsAmount, ph.pblhUserid = :userid, ph.pblhSite = :site, ph.pblhToday = :today WHERE trim(ph.pbillhCK.pblhSer) = :ser")
	void updatePbillhDebitAndPaidAmntBySer(Double debitAmnt, Double tdsAmount, Double paidAmnt, String userid, String site, LocalDateTime today, String ser);
	
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhAdvancepaid = 0,ph.pblhAdvanceadj = 0 , ph.pblhSite = :site, ph.pblhUserid = :userid , ph.pblhToday = :today WHERE trim(ph.pblhAuthnum) = :authNum")
	void updatePbillhAuthNumAndAuthDateAndAdvPaidAndAdvAdjByAuthNum(String userid, String site, LocalDateTime today, String authNum);
	
	
	//For N type cancellation
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhAdvancepaid = 0,ph.pblhAdvanceadj = 0 , ph.pblhSite = :site, ph.pblhUserid = :userid , ph.pblhToday = :today  WHERE  trim(ph.pblhPartycode) = :partyCode and trim(ph.pblhBldgcode) = :bldgCode and trim(ph.pbillhCK.pblhSer) IN (select trim(pd.pbilldCK.pbldSer) from Pbilld pd where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldMatgroup) =:matGroup and trim(pd.pbldSuppbillno) = :authdSuppBillNo and trim(pd.pbldBldgcode) = :bldgCode and pd.pbldSuppbilldt = :authdSuppBilldt) AND trim(ph.pblhSuppbillno) = :authdSuppBillNo AND ph.pblhSuppbilldt = :authdSuppBilldt")
	void updatePbillhAuthNumAndAuthDateAndAdvPaidAndAdvAdjByPartyCodeAndBldCodeAndMatGroup(String userid, String site, LocalDateTime today, String partyCode, String bldgCode, String matGroup,String authdSuppBillNo, LocalDate authdSuppBilldt);
	//
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhRetainos = :authdRelRetamt,  ph.pblhDebitamt = ph.pblhDebitamt - :debitAmnt ,ph.pblhSite = :site, ph.pblhUserid = :userid, ph.pblhToday = :today WHERE trim(ph.pblhAuthnum) = :authNum AND trim(ph.pblhSuppbillno) = :authdSuppBillNo AND ph.pblhSuppbilldt = :authdSuppBilldt ")
	void updatePbillhAuthNumAndAuthDateAndRetainNosAndDebitAmntByAuthNumAndSuppBillNoAndSuppBillDate(Double authdRelRetamt, Double debitAmnt, String userid, String site, LocalDateTime today, String authNum,String authdSuppBillNo, LocalDate authdSuppBilldt);
	
	// Updating Pbillh for L Type Cancellation
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhRetainos = ph.pblhRetainos + :authdRelRetamt ,ph.pblhSite = :site, ph.pblhUserid = :userid, ph.pblhToday = :today  WHERE  trim(ph.pblhPartycode) = :partyCode and trim(ph.pblhBldgcode) = :bldgCode and trim(ph.pbillhCK.pblhSer) IN (select trim(pd.pbilldCK.pbldSer) from Pbilld pd where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldMatgroup) =:matGroup and trim(pd.pbldSuppbillno) = :authdSuppBillNo and trim(pd.pbldBldgcode) = :bldgCode and pd.pbldSuppbilldt = :authdSuppBilldt) AND trim(ph.pblhSuppbillno) = :authdSuppBillNo AND ph.pblhSuppbilldt = :authdSuppBilldt")
	void updatePbillhAuthNumAndAuthDateAndRetainNosByPartyCodeAndBldCodeAndMatGroup(Double authdRelRetamt , String userid, String site, LocalDateTime today, String partyCode, String bldgCode, String matGroup,String authdSuppBillNo, LocalDate authdSuppBilldt);
	
	
	// Updating Pbillh for R Type Cancellation
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhRetainos =  ph.pblhRetainos + :authdRelRetamt,  ph.pblhDebitamt = ph.pblhDebitamt - :debitAmnt ,ph.pblhSite = :site, ph.pblhUserid = :userid, ph.pblhToday = :today WHERE  trim(ph.pblhPartycode) = :partyCode and trim(ph.pblhBldgcode) = :bldgCode and trim(ph.pbillhCK.pblhSer) IN (select trim(pd.pbilldCK.pbldSer) from Pbilld pd where trim(pd.pbldPartycode) = :partyCode and trim(pd.pbldMatgroup) =:matGroup and trim(pd.pbldSuppbillno) = :authdSuppBillNo and trim(pd.pbldBldgcode) = :bldgCode and pd.pbldSuppbilldt = :authdSuppBilldt) AND trim(ph.pblhSuppbillno) = :authdSuppBillNo AND ph.pblhSuppbilldt = :authdSuppBilldt ")
	void updatePbillhAuthNumAndAuthDateAndRetainNosAndDebitAmntByPartyCodeAndBldgCodeAndMatGroupAndSuppBillNoAndSuppBillDate(Double authdRelRetamt, Double debitAmnt, String userid, String site, LocalDateTime today, String partyCode, String bldgCode, String matGroup,String authdSuppBillNo, LocalDate authdSuppBilldt);
	
	@Modifying
	@Query("Update Pbillh ph SET ph.pblhAuthnum = null, ph.pblhAuthdate = null, ph.pblhRetainos = :authdRelRetamt,  ph.pblhSite = :site, ph.pblhUserid = :userid, ph.pblhToday = :today WHERE trim(ph.pblhAuthnum) = :authNum AND trim(ph.pblhSuppbillno) = :authdSuppBillNo AND ph.pblhSuppbilldt = :authdSuppBilldt ")
	void updatePbillhAuthNumAndAuthDateAndRetainNosByAuthNumAndSuppBillNoAndSuppBillDate(Double authdRelRetamt, String userid, String site, LocalDateTime today, String authNum,String authdSuppBillNo, LocalDate authdSuppBilldt);

	@Query("SELECT new kraheja.purch.bean.response.PaidBillResponseBean(h.pblhSuppbillno,h.pblhSuppbilldt,h.pblhAmount,h.pblhAuthnum,h.pblhChequeno,h.pblhDebitamt,h.pblhTdsamount,h.pblhPaidamt) FROM "
			+ "Pbilld d,Pbillh h WHERE (d.pbldPartycode = h.pblhPartycode) and (d.pbldSuppbillno = h.pblhSuppbillno) and ((trim(h.pblhPartycode)= :partyCode) "
			+ "AND (trim(h.pblhBldgcode)= :bldgCode) AND (trim(d.pbldMatgroup) = :matgroup) AND (h.pblhAuthnum is not NULL) "
			+ "AND (h.pblhAuthdate is not NULL))")
	List<PaidBillResponseBean> findbyPartycodeBldgCodeAndMatgroup(String partyCode, String bldgCode, String matgroup);
	
	@Query("SELECT COUNT(DISTINCT d.pbldMatgroup) FROM Pbilld d,Pbillh h WHERE trim(d.pbldMatgroup) = :matgroup and (trim(h.pblhPartycode)= :partyCode) "
			+ "AND (trim(h.pblhBldgcode)= :bldgCode)")
	Integer findCountPartycodeBldgCodeAndMatgroup(String partyCode, String bldgCode, String matgroup);
	
	
	
}