package kraheja.adminexp.billing.dataentry.invoiceCreation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.Invpartymaster;
import kraheja.adminexp.billing.dataentry.invoiceCreation.entity.InvpartymasterCK;
import org.springframework.data.jpa.repository.Query;

public interface InvPartyMasterRepository extends JpaRepository<Invpartymaster, InvpartymasterCK> {
	@Query(value = "SELECT " + "    IPMS_COYCODE, " + "    IPMS_PARTYTYPE, " + "    IPMS_PARTYCODE, "
			+ "    (select par_partyname from party where par_partytype=IPMS_PARTYTYPE and par_partycode=IPMS_PARTYCODE and par_closedate='01/JAN/2050') as Partyname, "
			+ "    IPMS_BILLTYPE, " + "    IPMS_ITEM_BILL_CODE, " + "    IPMS_ITEM_BILL_DESC, " + "    IPMS_RATE, "
			+ "    IPMS_QTY, " + "    IPMS_SERVICENATURE, " + "    IPMS_HSNSAC, " + "    IPMS_SUBJECT, "
			+ "    IPMS_SIGNAUTH, " + "    nvl(ipms_acmajor,'') as ipms_acmajor, " + "    IPMS_BILLNOTE "
			+ "FROM INVPARTYMASTER " + "ORDER BY IPMS_COYCODE, IPMS_PARTYTYPE, IPMS_PARTYCODE", nativeQuery = true)
	List<Object[]> findInvpartyMasterData();
}
