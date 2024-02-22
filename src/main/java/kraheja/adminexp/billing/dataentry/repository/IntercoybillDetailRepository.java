package kraheja.adminexp.billing.dataentry.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.entity.Intercoybilldetail;
import kraheja.adminexp.billing.dataentry.entity.IntercoybilldetailCK;

@Repository
public interface IntercoybillDetailRepository extends JpaRepository<Intercoybilldetail, IntercoybilldetailCK> {
	
	@Query(value = "SELECT DISTINCT icbed_srno, icbed_acmajor,(select chart_acname from glchart where chart_acnum = icbed_acmajor and chart_closedate is null) as acname,icbed_minor,"
			+ "FUNC_GETMINORSNAME (icbed_acmajor, icbed_minor, icbed_mintype, sysdate) as acminorname,icbed_mintype,icbed_partytype,nvl(icbed_acamount,0) as icbed_acamount "
			+ "FROM intercoybilldetail WHERE icbed_groupinvoiceno=? ORDER BY icbed_srno", nativeQuery = true)
	List<Tuple> fetchMajorFromDetail(String invoceGroup);
	
	@Query(value = "select * from intercoybilldetail where trim(icbed_invoiceno)= ? order by icbed_srno", nativeQuery = true)
	List<Intercoybilldetail> headerWisePartyDate(String locInvoceNumber);
	
	@Query(value = "select * from intercoybilldetail where trim(icbed_groupinvoiceno)= ? order by icbed_srno", nativeQuery = true)
	List<Intercoybilldetail> headerWiseParty(String locInvoceNumber);
	
	@Query(value = "select * from intercoybilldetail where trim(icbed_groupinvoiceno) = ? and icbed_invoiceno= ? and trim(icbed_acmajor)= ? and icbed_acamount= ?", nativeQuery = true)
	Intercoybilldetail findParty(String groupInvoice, String locInvoceNumber, String acMajor, Double acAmt );
	
	@Query(value = "select * from intercoybilldetail where trim(icbed_groupinvoiceno)= ?", nativeQuery = true)
	List<Intercoybilldetail> fetchAllMajorFromDetail(String invoceGroup);
	
}
