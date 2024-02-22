package kraheja.enggsys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.enggsys.entity.Cbillh;
import kraheja.enggsys.entity.Cbillh.CbillhCK;

@Repository
public interface CbillhRepository extends JpaRepository<Cbillh, CbillhCK> {

//	@Query("SELECT new kraheja.enggsys.bean.CertificateDetailBean(c.cblhPartycode AS conttConttor, c.cblhBldgcode AS conttBldgcode, c.cblhProp AS conttProprietor, c.cblhCoy AS conttCoy, c.cblhProject AS conttProject, c.cblhProperty AS conttProperty, c.cblhWorkcode AS conttWorkcode, SUM(c.cblhTdsamount) AS cblhTdsamount, 0 AS conttTdsper, c.cblhPartytype AS conttPartytype, c.cblhDebsocyn, (SELECT p.propName FROM kraheja.commons.entity.Proprietor p WHERE p.proprietorCK.propCode = c.cblhProp) AS PropName, (SELECT cm.cmapEcoyname FROM kraheja.commons.entity.Coymap cm WHERE cm.coymapCK.cmapEcoycode = c.cblhCoy) AS CompName, (SELECT pj.projName FROM kraheja.arch.projbldg.dataentry.entity.Project pj WHERE pj.projectCK.projCode = c.cblhProject AND pj.projCompany = c.cblhCoy) AS ProjectName, CASE 'R' WHEN 'L' THEN SUM(c.cblhRetainos) ELSE (SUM(c.cblhAmount) - SUM(NVL(c.cblhDebitamt, 0))) END AS CertAmt, CASE 'R' WHEN 'L' THEN SUM(c.cblhRetainos) ELSE ((NVL((SELECT SUM(NVL(cd.cbldTaxableamt, 0)) FROM kraheja.enggsys.entity.Cbilld cd WHERE trim(cd.cbilldCK.cbldSer) IN :billNo), 0)) + (SUM(NVL(c.cblhFreight, 0)) + SUM(NVL(c.cblhTransport, 0)) + SUM(NVL(c.cblhPacking, 0)) + SUM(NVL(c.cblhOthercharges, 0))) - SUM(NVL((SELECT NVL(SUM(cnd.cdbndTaxableamt), 0) + NVL(SUM(cnd.cdbndFotoamt), 0) FROM kraheja.enggsys.entity.Cdbnoteh cn, kraheja.enggsys.entity.Cdbnoted cnd WHERE cn.cdbnotehCK.cdbnhDbnoteser = cnd.cdbnotedCK.cdbndDbnoteser AND cn.cdbnhContract = c.cblhContract AND cn.cdbnhContbillno = c.cblhContbillno), 0))) END AS BasicAmt, SUM(c.cblhAdvanceadj) AS cblhAdvanceadj, CASE 'R' WHEN 'L' THEN 0 ELSE (SELECT SUM(ch.cblhRetention) FROM kraheja.enggsys.entity.Cbillh ch WHERE trim(ch.cbillhCK.cblhSer) IN :billNo) END AS cblhRetention, (SELECT MIN(ch.cblhDurfrom) FROM kraheja.enggsys.entity.Cbillh ch WHERE trim(ch.cbillhCK.cblhSer) IN :billNo) AS cblhDurfrom, (SELECT MAX(ch.cblhDurto) FROM kraheja.enggsys.entity.Cbillh ch WHERE trim(ch.cbillhCK.cblhSer) IN :billNo) AS cblhDurto, (SELECT SUM(cd.cbldCgstamt) FROM kraheja.enggsys.entity.Cbilld cd WHERE trim(cd.cbilldCK.cbldSer) IN :billNo) - SUM(NVL((SELECT NVL(SUM(cno.cdbndCgstamt), 0) FROM kraheja.enggsys.entity.Cdbnoteh cn, kraheja.enggsys.entity.Cdbnoted cno WHERE cn.cdbnotehCK.cdbnhDbnoteser = cno.cdbnotedCK.cdbndDbnoteser AND cn.cdbnhContract = c.cblhContract AND cn.cdbnhContbillno = c.cblhContbillno), 0)) AS CGSTAmt, (SELECT SUM(cd.cbldSgstamt) FROM kraheja.enggsys.entity.Cbilld cd WHERE trim(cd.cbilldCK.cbldSer) IN :billNo) - SUM(NVL((SELECT NVL(SUM(cnot.cdbndSgstamt), 0) FROM kraheja.enggsys.entity.Cdbnoteh cn, kraheja.enggsys.entity.Cdbnoted cnot WHERE cn.cdbnotehCK.cdbnhDbnoteser = cnot.cdbnotedCK.cdbndDbnoteser AND cn.cdbnhContract = c.cblhContract AND cn.cdbnhContbillno = c.cblhContbillno), 0)) AS SGSTAmt, (SELECT SUM(cd.cbldIgstamt) FROM kraheja.enggsys.entity.Cbilld cd WHERE trim(cd.cbilldCK.cbldSer) IN :billNo) - SUM(NVL((SELECT NVL(SUM(cnote.cdbndIgstamt), 0) FROM kraheja.enggsys.entity.Cdbnoteh cn, kraheja.enggsys.entity.Cdbnoted cnote WHERE cn.cdbnotehCK.cdbnhDbnoteser = cnote.cdbnotedCK.cdbndDbnoteser AND cn.cdbnhContract = c.cblhContract AND cn.cdbnhContbillno = c.cblhContbillno), 0)) AS IGSTAmt, (SELECT SUM(cd.cbldUgstamt) FROM kraheja.enggsys.entity.Cbilld cd WHERE trim(cd.cbilldCK.cbldSer) IN :billNo) - SUM(NVL((SELECT NVL(SUM(cnoted.cdbndUgstamt), 0) FROM kraheja.enggsys.entity.Cdbnoteh cn, kraheja.enggsys.entity.Cdbnoted cnoted WHERE cn.cdbnotehCK.cdbnhDbnoteser = cnoted.cdbnotedCK.cdbndDbnoteser AND cn.cdbnhContract = c.cblhContract AND cn.cdbnhContbillno = c.cblhContbillno), 0)) AS UGSTAmt)\r\n"
//			+ "FROM kraheja.enggsys.entity.Cbillh c\r\n"
//			+ "WHERE trim(c.cbillhCK.cblhSer) IN :billNo\r\n"
//			+ "GROUP BY c.cblhPartycode, c.cblhBldgcode, c.cblhProp, c.cblhCoy, c.cblhProject, c.cblhProperty, c.cblhContract, c.cblhWorkcode, c.cblhPartytype, c.cblhDebsocyn")
//	CertificateDetailBean fetchCertificateDetailsByBillnoIn(Set<String> billNo);
//	
	Cbillh findByCblhContract(String contract);
	
	List<Cbillh> findByCblhCertnum(String certNum);
	
	@Query("SELECT e FROM Cbillh e where trim(e.cbillhCK.cblhSer) = :ser AND e.cblhCertnum IS NULL AND NVL(e.cblhDebitamt,0) = 0")
	Cbillh findByCblhSer(String ser);
	
	@Query("Select e From Cbillh e where trim(e.cblhContract) = :recId AND trim(e.cblhContbillno) = :billNo")
	Cbillh findCblhByRecIdAndContractBillNo(String recId, String billNo);
}