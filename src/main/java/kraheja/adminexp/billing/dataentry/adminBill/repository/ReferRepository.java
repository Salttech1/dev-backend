package kraheja.adminexp.billing.dataentry.adminBill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.adminexp.billing.dataentry.adminBill.entity.Refer;
import kraheja.adminexp.billing.dataentry.adminBill.entity.ReferCK;

@Repository
public interface ReferRepository extends JpaRepository<Refer, ReferCK> {

	Refer findByReferCK_RefReftypeAndReferCK_RefRefcode(String reftype, String refcode);

	@Query(value = "select e.referCK.refRefcode from Refer e where trim(e.referCK.refRefcode) not in ('F', 'P', 'U') and trim(e.referCK.refReftype)='D' and trim(e.refAcmajor)= :acMajor")
	String findByReferCK_RefReftypeAndReferCK_RefRefcodeAndRefAcmajor(String acMajor);
}
