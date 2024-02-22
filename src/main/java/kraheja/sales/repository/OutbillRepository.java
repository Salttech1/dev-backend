package kraheja.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import kraheja.sales.entity.OutBill;
import kraheja.sales.entity.OutBill.OutbillCK;

public interface OutbillRepository extends JpaRepository<OutBill, OutbillCK> {
	@Procedure(name = "OutBill.inserttempowner")
	static int inserttempowner() {
		return 0;
	}
}
