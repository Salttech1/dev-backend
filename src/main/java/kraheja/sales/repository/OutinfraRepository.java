package kraheja.sales.repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.bean.entitiesresponse.Balance;
import kraheja.sales.entity.Outinfra;
import kraheja.sales.entity.OutinfraCK;

@Repository
public interface OutinfraRepository extends JpaRepository<Outinfra, OutinfraCK> {

	Outinfra findByOutinfraCK_InfBldgcodeAndOutinfraCK_InfOwneridAndOutinfraCK_InfRecnumAndOutinfraCK_InfMonthAndOutinfraCK_InfNarrcode(
			String bldgcode, String ownerid, String recnum, String month, String narrcode);

	@Query("select  sum(nvl(o.infAmtpaid,0)) as amtPaid,  sum(nvl(o.infAmtint,0)) as amtint,  sum(nvl(o.infCgst,0)) as CGST, sum(nvl(o.infAdmincharges, 0)) as admincharges,  o.outinfraCK.infMonth, o.outinfraCK.infOwnerid,  sum(nvl(o.infSgst,0)) as SGST,  sum(nvl(o.infIgst,0)) as IGST,  sum(nvl(o.infTds,0)) as TDS from Outinfra o where o.outinfraCK.infOwnerid=:ownerid  and o.infCancelledyn='N'  and o.outinfraCK.infMonth>=:month  and o.infChargecode=:chargeCode  and o.infRectype =:recType  and o.infGstyn= 'Y' group by o.outinfraCK.infOwnerid, o.outinfraCK.infBldgcode, o.infWing, o.infFlatnum, o.outinfraCK.infMonth order by o.outinfraCK.infOwnerid, o.outinfraCK.infBldgcode, o.infWing, o.infFlatnum, o.outinfraCK.infMonth")
	List<Tuple> findPrevOgRecords(String ownerid, String month, String chargeCode, String recType);

	@Query("select distinct o.infGstyn from Outinfra o where trim(o.outinfraCK.infRecnum)= :recNum and o.infCancelledyn='N'")
	String findByOutinfraCK_InfRecNum(String recNum);

	@Query("select new kraheja.sales.bean.entitiesresponse.Balance(sum(nvl(o.infAmtpaid,0)) as amtPaid,"
			+ "sum(nvl(o.infAmtint,0)) as amtint,  sum(nvl(o.infCgst,0)) as cgst, "
			+ "sum(nvl(o.infAdmincharges, 0)) as adminharges,  (o.outinfraCK.infMonth) as month , "
			+ "(o.outinfraCK.infOwnerid) as ownerid,  sum(nvl(o.infSgst,0)) as sgst,  sum(nvl(o.infIgst,0)) as igst,  "
			+ "sum(nvl(o.infTds,0)) as tds) from Outinfra o where o.outinfraCK.infOwnerid=:ownerid  "
			+ "and o.outinfraCK.infMonth>=:month and o.infChargecode=:chargeCode and o.infRectype =:recType "
			+ "and o.infGstyn= 'Y' and o.infCancelledyn='N' group by o.outinfraCK.infOwnerid, o.outinfraCK.infBldgcode, "
			+ "o.infWing, o.infFlatnum, o.outinfraCK.infMonth  order by o.outinfraCK.infOwnerid, o.outinfraCK.infBldgcode, "
			+ "o.infWing, o.infFlatnum, o.outinfraCK.infMonth ")
	List<Balance> findPreviousBalance(String ownerid, String month, String chargeCode, String recType);

//	@Query(value = "select distinct inf_recdate, "
//			+ "sum(nvl(inf_amtpaid,0) +  nvl(inf_admincharges,0) + nvl(inf_servtax,0) +  nvl(inf_swachhcess,0) +  nvl(inf_krishicess,0) + nvl(inf_cgst,0) +  nvl(inf_sgst,0) +  nvl(inf_igst,0)) as amtpaid, "
//			+ "sum(nvl(inf_amtint,0)) as intpaid from outinfra " + "where trim(inf_bldgcode)=? "
//			+ "and trim(inf_chargecode) = ? " + "and trim(inf_wing)=?  " + "and trim(inf_flatnum)=?  "
//			+ "and inf_recdate between ? " + " and ? " + "and inf_rectype=? " + "and inf_cancelledyn = 'N' "
//			+ "group by inf_recdate", nativeQuery = true)
//	List<Tuple> fetchRecDateAndAmtPaidAndIntPaid(String bldgcode, String chargecode, String wing, String flatnum,
//			LocalDate lastbillDate, LocalDate requestDate, String billtype);

	@Query(value = "SELECT DISTINCT inf_recdate, " +
            "SUM(" +
            "  NVL(inf_amtpaid, 0) + " +
            "  NVL(inf_admincharges, 0) + " +
            "  NVL(inf_servtax, 0) + " +
            "  NVL(inf_swachhcess, 0) + " +
            "  NVL(inf_krishicess, 0) + " +
            "  NVL(inf_cgst, 0) + " +
            "  NVL(inf_sgst, 0) + " +
            "  NVL(inf_igst, 0) " +
            ") AS amtpaid, " +
            "SUM(NVL(inf_amtint, 0)) AS intpaid " +
            "FROM outinfra " +
            "WHERE trim(inf_ownerId) = ? " +
            "AND trim(inf_chargecode) = ? " +
            "AND inf_recdate > ? " +
            "AND inf_recdate <= ? " +
            "AND inf_rectype = ? " +
            "AND inf_cancelledyn = 'N' " +
            "GROUP BY inf_recdate", nativeQuery = true)
	List<Tuple> fetchRecDateAndAmtPaidAndIntPaid(String ownerId, String chargecode,LocalDate lastbillDateFrom, LocalDate lastbillDateTo, String billtype);

	@Query(value = "SELECT DISTINCT inf_recdate, " +
            "SUM(" +
            "  NVL(inf_amtpaid, 0) + " +
            "  NVL(inf_admincharges, 0) + " +
            "  NVL(inf_servtax, 0) + " +
            "  NVL(inf_swachhcess, 0) + " +
            "  NVL(inf_krishicess, 0) + " +
            "  NVL(inf_cgst, 0) + " +
            "  NVL(inf_sgst, 0) + " +
            "  NVL(inf_igst, 0) " +
            ") AS amtpaid, " +
            "SUM(NVL(inf_amtint, 0)) AS intpaid " +
            "FROM outinfra " +
            "WHERE inf_recnum = ? " +
            "AND inf_cancelledyn = 'N' " +
            "GROUP BY inf_recdate", nativeQuery = true)
	List<Tuple> fetchRecDateAndAmtPaidAndIntPaid(String recnum);

	
	@Query(value = "select max(inf_recdate) from outinfra where trim(inf_ownerid)=? and trim(inf_chargecode) = ? and trim(inf_rectype)= ? and inf_cancelledyn = 'N'", nativeQuery = true)
	Timestamp fetchLastReceptDate(String ownerid, String chargecode, String billType);
	
	@Query(value = "select max(inf_recnum) from outinfra where trim(inf_ownerid)= ? and trim(inf_chargecode) = ? and trim(inf_rectype) = ? and  inf_gstyn= 'Y'", nativeQuery = true)
	String lastRecNum(String ownerId, String chargecode, String rectype);
}