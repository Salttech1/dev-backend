package kraheja.adminexp.billing.dataentry.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.entity.Intercoybillheader;
import kraheja.adminexp.billing.dataentry.entity.IntercoybillheaderCK;

@Repository
public interface IntercoybillheaderRepository extends JpaRepository<Intercoybillheader, IntercoybillheaderCK> {

	@Query(value = "select max(icbeh_periodto) from intercoybillheader where trim(icbeh_coy)=? and trim(icbeh_projcode)=?", nativeQuery = true)
	LocalDateTime fetchMaxPeriod(String companyCode, String projectCode);

//	@Query("select DISTINCT new kraheja.adminexp.billing.dataentry.intercompany.bean.db.InvoiceDateRetriveDBResponse(h.icbehTrandate, h.icbehPeriodfrom, h.icbehPeriodto) from Intercoybillheader where h.intercoybillheaderCK.icbehGroupinvoiceno= :invoiceGroup")
	@Query(value = "select DISTINCT ICBEH_TRANDATE, ICBEH_PERIODFROM, ICBEH_PERIODTO, ICBEH_NARRATION,ICBEH_COY, ICBEH_PROJCODE  from intercoybillheader where icbeh_groupinvoiceno=? ", nativeQuery = true)
	Tuple retriveInvoiceDate(String invoiceGroup);

	@Query(value = "select *  from intercoybillheader where trim(icbeh_groupinvoiceno)=? ", nativeQuery = true)
	List<Intercoybillheader> retriveHeader(String invoiceGroup);

	@Query(value = "select icbeh_invoiceno from intercoybillheader where trim(icbeh_coy) = ? "
			+ "and trim(icbeh_partycode)= ? " + "and trim(icbeh_groupinvoiceno) = ? "
			+ "and trim(icbeh_recbillprojcode) = ?", nativeQuery = true)
	String fetchLocCoyInvoiceNumber(String coy, String partyCode, String groupInvoiceNo, String locProjCode);

	@Query(value = "select * from intercoybillheader where trim(icbeh_coy) = ? " + "and trim(icbeh_partycode)= ? "
			+ "and trim(icbeh_groupinvoiceno) = ? " + "icbeh_invoiceno = ?"
			+ "and trim(icbeh_recbillprojcode) = ?", nativeQuery = true)
	Intercoybillheader retriveSingleHeader(String coy, String partyCode, String groupInvoiceNo, String invoiceno,
			String locProjCode);

	@Query(value = "select icbeh_invoiceno from intercoybillheader where trim(icbeh_coy) = ? "
			+ "and trim(icbeh_partycode)= ? " + "and trim(icbeh_groupinvoiceno) = ? "
			+ "and trim(icbeh_recbillprojcode) = ?", nativeQuery = true)
	String findLocCoyInvoiceNumber(String coy, String partyCode, String groupInvoiceNo, String locProjCode);

	@Query(value = "select * from intercoybillheader where trim(icbeh_coy) = ? " + "and trim(icbeh_partycode)= ? "
			+ "and trim(icbeh_groupinvoiceno) = ? " + "and trim(icbeh_recbillprojcode) = ?", nativeQuery = true)
	Intercoybillheader findHeaders(String coy, String partyCode, String groupInvoiceNo, String locProjCode);

	@Query(value = "select icbeh_postedyn from intercoybillheader where trim(icbeh_groupinvoiceno)=?", nativeQuery = true)
	String isPosted(String groupInvoice);

	@Query("SELECT e FROM Intercoybillheader e WHERE TRIM(e.intercoybillheaderCK.icbehInvoiceno)=:invoiceNumber AND TRIM(e.icbehCoy)=:companyCode")
	List<Intercoybillheader> fetchIntercoybillheadrerByInvoiceNoAndCoy(String invoiceNumber, String companyCode);

}
