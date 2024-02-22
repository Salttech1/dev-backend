package kraheja.adminexp.billing.dataentry.invoiceCreation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invoiceheader;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.InvoiceheaderCK;
@Repository
public interface InvoiceheaderRepository extends JpaRepository<Invoiceheader, InvoiceheaderCK> { 
	Invoiceheader findByInvoiceheaderCK_InvhInvoiceno(String invoiceno) ; 
}