package kraheja.enggsys.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.bean.CertificateDetailBean;
import kraheja.enggsys.entity.Cert;
import kraheja.enggsys.entity.Lccert;
import kraheja.enggsys.entity.Cert.CertCK;

@Repository
public interface CertRepository extends JpaRepository<Cert, CertCK> {
	Cert findByCertCK_CertCertnum(String certnum); 

	@Query("select max(c.certCK.certCertnum) from Cert c where  trim(c.certContract) = :recId")
	String findMaxCertNumByRecId(String recId); 
	
	@Query("SELECT new kraheja.enggsys.bean.CertificateDetailBean(c.certCK.certCertnum,c.certCertrevnum, c.certRunser, c.certCerttype, c.certCertdate, c.certCertstatus, c.certBldgcode, c.certWing, c.certDomain, c.certWorkgroup, c.certWorkcode, c.certCfgroup, c.certCfcode, c.certSocid, c.certEquipid, c.certPartytype, c.certPartycode, c.certContract, c.certConamount, c.certDurfrom, c.certDurto, c.certCertamount, c.certPerdone, c.certBillref, c.certBillamount, c.certPayref, c.certPaydate, c.certPayamount, c.certTranser, c.certMwctaxamount, c.certTdsamount, c.certTdssur, c.certAdvadjusted, c.certRetained, c.certPaytender, c.certTrips, c.certRemarks, c.certOriginator, c.certTPayment, c.certDebit, c.certDebsocyn, c.certAsstaxfrom, c.certAsstaxupto, c.certTaxlevel, c.certProp, c.certCoy, c.certCity, c.certProject,\r\n"
			+ "(SELECT p.propName FROM Proprietor p WHERE p.proprietorCK.propCode = c.certProp) AS proprietor, \r\n"
			+ "(SELECT cm.cmapAcoyname FROM Coymap cm WHERE cm.coymapCK.cmapEcoycode = c.certCoy) AS company, \r\n"
			+ "(SELECT e.entName FROM DbEntity e WHERE e.entityCk.entClass = 'CITY' AND e.entityCk.entId = c.certCity) AS cityName, \r\n"
			+ "(SELECT proj.projName FROM Project proj WHERE proj.projectCK.projCode = c.certProject AND proj.projCompany = c.certCoy) AS projectName, \r\n"
			+ "(SELECT bm.bmapEbldgname FROM Bldgmap bm WHERE bm.bldgmapCK.bmapEbldgcode = c.certBldgcode) AS buildingName, \r\n"
			+ "(SELECT ep.epWorkname FROM Epworks ep WHERE ep.epworksCK.epWorkcode = c.certWorkcode AND ROWNUM = 1) AS workName, \r\n"
			+ "(SELECT par.parPartyname FROM Party par WHERE par.partyCk.parPartytype = 'E' AND par.partyCk.parPartycode = c.certPartycode AND par.partyCk.parClosedate = TO_DATE('01.01.2050', 'dd.mm.yyyy')) AS partyName, \r\n"
			+ "(SELECT par.parPmtacnum FROM Party par WHERE par.partyCk.parPartytype = 'E' AND par.partyCk.parPartycode = c.certPartycode AND par.partyCk.parClosedate = TO_DATE('01.01.2050', 'dd.mm.yyyy')) AS panNo, \r\n"
			+ "(SELECT par.parGstno FROM Party par WHERE par.partyCk.parPartytype = 'E' AND par.partyCk.parPartycode = c.certPartycode AND par.partyCk.parClosedate = TO_DATE('01.01.2050', 'dd.mm.yyyy')) AS gstNo, \r\n"
			+ "c.certMainrecid, c.certMainparty, c.certMainmatgroup, c.certServicetaxperc, c.certServicetaxamt, c.certDescription, c.certDebitingparty, c.certDebitingreason, \r\n"
			+ "c.certSwachhcessperc, c.certSwachhcessamt, c.certVatperc, c.certVatamt, c.certBasicamt, c.certKrishicessperc, c.certKrishicessamt, \r\n"
			+ "(SELECT SUM(cd.cbldCgstamt) FROM Cbilld cd WHERE TRIM(cd.cbldCertnum) = :certnum) AS cgstamt, \r\n"
			+ "(SELECT SUM(cd.cbldSgstamt) FROM Cbilld cd WHERE TRIM(cd.cbldCertnum) = :certnum) AS sgstamt, \r\n"
			+ "(SELECT SUM(cd.cbldIgstamt) FROM Cbilld cd WHERE TRIM(cd.cbldCertnum) = :certnum) AS igstamt, \r\n"
			+ "(SELECT SUM(cd.cbldUgstamt) FROM Cbilld cd WHERE TRIM(cd.cbldCertnum) = :certnum) AS ugstamt) \r\n"
			+ "FROM Cert c \r\n"
			+ "WHERE TRIM(c.certCK.certCertnum) = :certnum AND c.certContract = :recId")
	CertificateDetailBean fetchCertEntryDetails(String recId, String certnum); 

	@Query("SELECT c FROM Cert c WHERE TRIM(c.certContract) = :contract AND c.certCertstatus > :certStatus  AND c.certCertstatus NOT IN :certStatusSet")
	List<Cert> fetchTotalPrvCertDetailByContractIdAndCertStatus(String contract,String certStatus,List<String> certStatusSet); 
	
	@Query("SELECT c FROM Cert c WHERE TRIM(c.certContract) = :contract AND TRIM(c.certCerttype) = :certType AND c.certCertstatus > :certStatus  AND c.certCertstatus NOT IN :certStatusSet")
	List<Cert> fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertType(String contract,String certType,String certStatus,List<String> certStatusSet); 

	@Query("SELECT c FROM Cert c WHERE TRIM(c.certContract) = :contract AND TRIM(c.certCerttype) NOT IN :certType AND c.certCertstatus > :certStatus  AND c.certCertstatus NOT IN :certStatusSet")
	List<Cert> fetchTotalPrvCertDetailByContractIdAndCertStatusAndCertTypeNotIn(String contract,List<String> certType,String certStatus,List<String> certStatusSet); 
	
	@Query("SELECT c FROM Cert c WHERE TRIM(c.certBldgcode) = :bldgCode AND TRIM(c.certWorkcode) = :workCode AND TRIM(c.certPartycode) <> :partyCode AND TRIM(c.certCerttype) <> :certType AND c.certCertstatus > :certStatus  AND c.certCertstatus NOT IN :certStatusSet")
	List<Cert> fetchCertByBldgCodeAndWorkCodeAndPartyCodeNotAndCertStatusAndCertType(String bldgCode,String workCode, String partyCode, String certType,String certStatus,List<String> certStatusSet); 
	
	@Query("SELECT max(c.certCertdate) FROM Cert c WHERE TRIM(c.certContract) = :contract AND c.certCertstatus > :certStatus")
	LocalDate fetchMaxCertDateByContractIdAndCertStatus(String contract,String certStatus); 
	
	@Query("SELECT max(c.certCK.certCertnum) FROM Cert c WHERE TRIM(c.certContract) = :contract AND c.certCertstatus > :certStatus AND trunc(c.certCertdate) = :certDate")
	String fetchMaxCertNumByContractIdAndCertStatusAndCertDate(String contract, String certStatus, LocalDate certDate);
	
	@Query("SELECT c FROM Cert c WHERE TRIM(c.certContract) = :contract AND TRIM(c.certCerttype) != :certType AND c.certPassedon = :passedOn ")
	Cert fetchByContractIdAndCertTypeNotAndPassedOn(String contract,String certType,LocalDate passedOn); 

	// Not added one condition auth_num like '%' I think it is not required
	@Query(value = "select * from cert where cert_printedon IS null and trim(cert_userid) =?", nativeQuery =true)
//	List<Cert> findByCertPrintedonAndCertUserid(LocalDate printedon, String userid);
	List<Cert> findByCertPrintedonISNULLAndCertUserid(String userid);
	
	@Query("SELECT e FROM Cert e WHERE TRIM(e.certCK.certCertnum) IN :authNumList")
	List<Cert> findByCertCK_CertCertnumIn(List<String> authNumList);
	
}
