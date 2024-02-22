package kraheja.purch.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.bean.PreviousAuthDetailBean;
import kraheja.purch.bean.response.AuthHCancelMaterialBean;
import kraheja.purch.bean.response.AuthHPassMaterialBean;
import kraheja.purch.entity.Auth_H;
import kraheja.purch.entity.Auth_HCK;

@Repository
public interface AuthHRepository extends JpaRepository<Auth_H, Auth_HCK> {

	@Query("Select distinct  new kraheja.purch.bean.response.AuthHCancelMaterialBean(a.authAuthtype, a.authhCK.authAuthnum, to_char(a.authAuthdate,'dd/mm/yyyy'), a.authAuthamount, to_char(a.authPassedon,'dd/mm/yyyy'), a.authTranser, a.authPayref, to_char(a.authPaydate,'dd/mm/yyyy'), a.authPayamount, a.authAuthstatus, a.authPartycode, a.authAuthdate, a.authBldgcode, a.authMatgroup) from Auth_H a where trim(a.authPartycode) = :partyCode and trim(a.authBldgcode) = :buildingCode and trim(a.authMatgroup) = :matGroup order by a.authAuthdate desc, a.authhCK.authAuthnum desc")
	List<AuthHCancelMaterialBean> findByPartyCodeAndBuildingAndMatGroup(String partyCode, String buildingCode, String matGroup);

	@Query("Select a.authBldgcode, a.authCoy From Auth_H a where trim(a.authhCK.authAuthnum) = :authnum")
	List<Object[]> findBldgCodeAndCoyByAuthNum(String authnum);
	
	List<Auth_H> findByAuthAuthstatusLessThanAndAuthAuthtypeNotAndAuthPrintedonAndAuthPassedonOrderByAuthhCK_AuthAuthnum(String status, String type, LocalDate printedon, LocalDate Passedon);

	@Query("SELECT new kraheja.purch.bean.response.AuthHPassMaterialBean(e.authBldgcode,e.authPartytype,e.authPartycode,e.authhCK.authAuthnum,e.authAuthtype,e.authAuthdate,e.authPassedon,e.authAuthstatus,e.authCoy,e.authProp,e.authProject,e.authProperty,e.authAuthamount,e.authMatgroup,e.authTranser,e.authTdsamount,e.authAdvadjust,e.authRetained,e.authRelretain,e.authPayamount,(SELECT MAX(p.parPartyname) FROM Party p WHERE p.partyCk.parPartytype=e.authPartytype AND p.partyCk.parPartycode=e.authPartycode AND p.partyCk.parClosedate = :closedate) as partyName) FROM Auth_H e \r\n"
			+ "WHERE (e.authAuthstatus < :status) AND (e.authAuthtype <> :type)\r\n"
			+ "AND (e.authPrintedon is not null) AND (e.authPassedon is null) AND trim(e.authSite) = :site ORDER BY e.authhCK.authAuthnum ASC")
	List<AuthHPassMaterialBean> findByAuthAuthstatusLessThanAndAuthAuthtypeNotAndAuthPrintedonNotNullAndAuthPassedonOrderByAuthhCK_AuthAuthnumAndAuthsite(String status, String type, LocalDateTime closedate, String site);
					
	@Query("SELECT	NVL(SUM(e.authAuthamount), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType")
	Double findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(String partyCode, String bldgCode, String matGroup, String status, String authType);
	
	@Query("SELECT	NVL(SUM(e.authAuthamount), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) <> :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType")
	Double findAuthAmountSumByAuthPartycodeNotInAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(String partyCode, String bldgCode, String matGroup, String status, String authType); // FOR ADD MATERIAL

	@Query("SELECT	NVL(SUM(e.authAdvadjust), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType")
	Double findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(String partyCode, String bldgCode, String matGroup, String status, String authType);
	
	@Query("SELECT	NVL(SUM(e.authAdvadjust), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status")
	Double findAdvAdjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(String partyCode, String bldgCode, String matGroup, String status);
	
	@Query("SELECT	NVL(SUM(e.authAdvadjust), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) <> :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status")
	Double findAdvAdjustSumByAuthPartycodeNotINauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtype(String partyCode, String bldgCode, String matGroup, String status); // FOR ADD MATERIAL
	
	@Modifying
	@Query("Delete from Auth_H a where trim(a.authhCK.authAuthnum) = :authNum")
	void deleteAuthHByAuthNum(String authNum);
	
	Auth_H findByAuthhCK_AuthAuthnum(String authnum);
	
	List<Auth_H> findByAuthPrintedon(String printedon);
	
	@Modifying
	@Query("Update Auth_H a SET a.authAuthstatus = :authStatus, a.authSite = :site, a.authUserid = :userid, a.authToday = :today WHERE auth_authnum = :authNum")
	void updateAuthHStatusByAuthNum(String authNum, String site, String userid, LocalDateTime today);
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	Double findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authNo); 
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND  e.authAuthdate <= :today")
	Double findCountByAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate( String bldgCode, String matGroup, String status, LocalDate today); //---------------------------> authno to be removed for proper count
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE TRIM(e.authBldgcode)  = :bldgCode AND  e.authAuthstatus <> :status AND e.authAuthdate <= :today")//---------------------------> authno to be removed for proper count
	Double findCountByAuthBldgcodeAndAuthAuthstatusAndAuthAuthdate( String bldgCode, String status, LocalDate today);  
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND e.authAuthdate <= :today")
	Double findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(String partyCode, String bldgCode, String matGroup, String status, LocalDate today);//---------------------------> authno to be removed for proper count
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	Double findCountByAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo( String bldgCode, String matGroup, String status, String authNo);
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE TRIM(e.authBldgcode)  = :bldgCode AND  e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	Double findCountByAuthBldgcodeAndAuthAuthstatusAndAuthAuthNo( String bldgCode, String status, String authNo);
	
	@Query("SELECT	MAX(e.authhCK.authAuthnum)  FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	String findMaxAuthNoByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authNo);
	
	@Query("SELECT	MAX(e.authhCK.authAuthnum)  FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND  e.authAuthdate <= :today")
	String findMaxAuthNoByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(String partyCode, String bldgCode, String matGroup, String status, LocalDate today);//---------------------------> authno to be removed for proper count
	
	@Query("SELECT	e  FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  = :authNo")
	Auth_H findAuthHByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authNo);
	
	@Query("SELECT	new kraheja.purch.bean.PreviousAuthDetailBean(SUM(e.authAuthamount) - SUM(e.authAdvadjust) - SUM(e.authRelretain) - Sum(e.authRetentionadj),SUM(e.authAuthquanty), SUM(e.authRelretain), SUM(e.authRetained),SUM(e.authRetentionadj)) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	PreviousAuthDetailBean findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authNo);
	
	@Query("SELECT	new kraheja.purch.bean.PreviousAuthDetailBean(SUM(e.authAuthamount) - SUM(e.authAdvadjust) - SUM(e.authRelretain) - Sum(e.authRetentionadj),SUM(e.authAuthquanty), SUM(e.authRelretain), SUM(e.authRetained),SUM(e.authRetentionadj)) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND e.authAuthdate <= :today")
	PreviousAuthDetailBean findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthdate(String partyCode, String bldgCode, String matGroup, String status, LocalDate today); //---------------------------> authno to be removed for proper count

	@Query("SELECT	NVL(SUM(e.authAuthamount), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	Double findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authType, String authNo);

	@Query("SELECT	NVL(SUM(e.authAdvadjust), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType AND TRIM(e.authhCK.authAuthnum)  < :authNo")
	Double findAuthadvadjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthNo(String partyCode, String bldgCode, String matGroup, String status, String authType, String authNo);
	
	@Query("SELECT	NVL(SUM(e.authAuthamount), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType AND e.authAuthdate <= :today")
	Double findAuthAmountSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthAuthdate(String partyCode, String bldgCode, String matGroup, String status, String authType, LocalDate today);

	@Query("SELECT	NVL(SUM(e.authAdvadjust), 0) FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status AND TRIM(e.authAuthtype)  = :authType AND e.authAuthdate <= :today")
	Double findAuthadvadjustSumByAuthPartycodeAndauthBldgcodeAndAuthMatgroupAndAuthAuthstatusAndAuthAuthtypeAndAuthAuthdate(String partyCode, String bldgCode, String matGroup, String status, String authType, LocalDate today);
	
	@Query("SELECT	new kraheja.purch.bean.PreviousAuthDetailBean(SUM(e.authAuthamount) - SUM(e.authAdvadjust) - SUM(e.authRelretain) - Sum(e.authRetentionadj),SUM(e.authAuthquanty), SUM(e.authRelretain), SUM(e.authRetained),SUM(e.authRetentionadj)) FROM Auth_H e WHERE	TRIM(e.authPartycode) <> :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status")
	PreviousAuthDetailBean findPreviousAuthByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthstatus(String partyCode, String bldgCode, String matGroup, String status);
	
	@Query("SELECT	count(*)  FROM Auth_H e WHERE TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authPartycode) = :partyCode  AND TRIM(e.authMatgroup) = :matGroup AND TRIM(e.authhCK.authAuthnum)  not like '$%'")
	Double findCountByAuthPartycodeAndAuthBldgcodeAndAuthMatgroupAndAuthAuthNoNotLike(String bldgCode, String partyCode, String matGroup);
	
	@Query("SELECT	e FROM Auth_H e WHERE	TRIM(e.authPartycode) = :partyCode AND TRIM(e.authBldgcode)  = :bldgCode AND TRIM(e.authMatgroup) = :matGroup AND e.authAuthstatus <> :status  AND (e.authhCK.authAuthnum BETWEEN ' ' AND 'zzzzzzzzzz') AND e.authPassedon is null")
	List<Auth_H> findUnpassedDetail(String partyCode, String bldgCode, String matGroup, String status);
	
	// Not added one condition auth_num like '%' I think it is not required
	List<Auth_H> findByAuthPrintedonAndAuthUserid(LocalDate printedon, String userid);
	
	@Query("SELECT	e FROM Auth_H e WHERE	TRIM(e.authhCK.authAuthnum) IN :authNumList")
	List<Auth_H> findByAuthhCK_AuthAuthnumIn(List<String> authNumList);
}