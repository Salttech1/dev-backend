package kraheja.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.bean.entitiesresponse.InfrBillDBResponse;
import kraheja.sales.entity.Print;
import kraheja.sales.entity.PrintCK;

@Repository
public interface PrintRepository extends JpaRepository<Print, PrintCK>{

	@Query("select new kraheja.sales.bean.entitiesresponse.InfrBillDBResponse("
			+ "b.printCK.saogrpBillnum as infrBillnum,"
			+ "b.printCK.saogrpInvoiceno as infrInvoiceNo,"
			+ "b.saogrpIrnno as infrIrnNo,"
			+ "b.saogrpOwnerid as infrOwnerId,"
			+ "b.saogrpBldgcode as infrBldgCode,"
			+ "b.saogrpWing as infrWing,"
			+ "b.saogrpFlatnum as infrFlatnum,"
			+ "b.saogrpBillmonth as infrMonth,"
			+ "b.saogrpOutrate as infrRate,"
			+ "b.saogrpBillamt as infrBillamt,"
			+ "b.saogrpBilldate as infrBilldate,"
			+ "b.saogrpBillfrom as infrFromdate,"
			+ "b.saogrpBillto as infrTodate,"
			+ "b.saogrpOutrate as infrAmtos,"
			+ "b.saogrpBillarrears as infrArrears,"
			+ "b.saogrpIntarrears as infrIntarrears,"
			+ "b.saogrpInterest as infrInterest,"
			+ "b.saogrpAdmincharges as infrAdmincharges,"
			+ "b.saogrpCgst as infrCgst,"
			+ "b.saogrpSgst as infrSgst,"
			+ "b.saogrpIgst as infrIgst,"
			+ "b.saogrpCgstperc as infrCgstperc,"
			+ "b.saogrpSgstperc as infrSgstperc,"
			+ "b.saogrpIgstperc as infrIgstperc) from Print b where b.printCK.saogrpSessid= :sessId")
	InfrBillDBResponse getInfrsaogrp01Print(double sessId);
	
	@Query("select p from Print p where p.printCK.saogrpSessid= :sessId and trim(p.saogrpOwnerid)= :ownerId ")
	Print findByInfrsaogrp01_printCKSaogrpSessidAndSaogrpOwnerid(double sessId, String ownerId);

	void removeByprintCKSaogrpSessidAndPrintCKSaogrpBillnum(Double sessid, String billnum);
	
	@Query(value = "select saogrp_sessid from INFRSAOGRP01_PRINT where trim(saogrp_billnum) = ? and trim(saogrp_billmonth) = ? and trim(saogrp_ownerid) = ?", nativeQuery = true)
	List<String> findSessionIdByBillNumAndMonthAndOwnerId(String billnum, String billMonth, String ownerId);
	
	Print findByprintCKSaogrpSessidAndPrintCKSaogrpBillnum(Double sessid, String billnum);
	
	@Modifying
	@Query(value = "update infrsaogrp01_print set saogrp_invoiceno = ? where trim(saogrp_billnum) = ? and trim(saogrp_sessid) = ?", nativeQuery = true)
	void updateInvoiceNumber(String invoiceno, String billnum, double sessId);
	
	  @Modifying
	    @Query("DELETE FROM Print p WHERE p.printCK.saogrpSessid = :sessId and  printCK.saogrpBillnum = :billnum AND p.printCK.saogrpInvoiceno IS NULL")
		void deleteprint(double sessId, String billnum);
}
