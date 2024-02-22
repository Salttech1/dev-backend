package kraheja.commons.repository;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Payinslip;
import kraheja.commons.entity.PayinslipCK;


@Repository
public interface PayinslipRepository extends JpaRepository<Payinslip, PayinslipCK> {

	 
	Payinslip findByPayinslipCK_PslipProprietorAndPayinslipCK_PslipCoyAndPayinslipCK_PslipBankAndPayinslipCK_PslipBooknumAndPayinslipCK_PslipLeafnumAndPayinslipCK_PslipTranser(String proprietor, String coy, String bank, String booknum, String leafnum, String transer) ; 
	
	@Modifying
	@Query("Update Payinslip p SET p.pslipAmount = :pslipAmount , p.payinslipCK.pslipTranser = :pslipTranser , p.pslipDepdate = :pslipDepdate, p.pslipSite = :pslipSite, p.pslipUserid = :pslipUserid, p.pslipToday = :pslipToday where trim(p.payinslipCK.pslipProprietor) =:pslipProprietor and trim(p.payinslipCK.pslipCoy) = :pslipCoy and trim(p.payinslipCK.pslipBank) = :pslipBank and trim(p.payinslipCK.pslipLeafnum) = :pslipLeafnum")
	void updatePayInSlipByProprietorAndCoyAndBankAndLeafNum(Double pslipAmount, String pslipTranser, LocalDate pslipDepdate, String pslipSite, String pslipUserid, LocalDateTime pslipToday, String pslipProprietor, String pslipCoy, String pslipBank, String pslipLeafnum);

}