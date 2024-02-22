package kraheja.adminexp.billing.dataentry.invoiceCreation.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoicedetail;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.InvoicedetailCK;

@Repository
public interface InvoicedetailRepository extends JpaRepository<Invoicedetail, InvoicedetailCK> {

	List<Invoicedetail> findByInvoicedetailCK_InvdTrtypeAndInvoicedetailCK_InvdInvoicenoAndInvoicedetailCK_InvdCodeAndInvoicedetailCK_InvdSrno(
			String trtype, String invoiceno, String code, Double srno);
	
	@Query("select d from Invoicedetail d where trim(d.invoicedetailCK.invdInvoiceno) = :invoiceNum")
	List<Invoicedetail> findByInvoicedetailCK_InvdInvoiceno(String invoiceNum);

	@Modifying
	@Query("DELETE FROM Invoicedetail d WHERE TRIM(d.invoicedetailCK.invdInvoiceno) = :invoiceNum")
	void deleteByInvoiceNum(String invoiceNum);
}