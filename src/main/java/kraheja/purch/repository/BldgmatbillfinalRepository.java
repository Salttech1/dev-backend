package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Bldgmatbillfinal;
import kraheja.purch.entity.BldgmatbillfinalCK;
@Repository
public interface BldgmatbillfinalRepository extends JpaRepository<Bldgmatbillfinal, BldgmatbillfinalCK> {

	Bldgmatbillfinal findByBldgmatbillfinalCK_BmbfBldgcodeAndBldgmatbillfinalCK_BmbfMgrcode(String bldgcode, String mgrcode) ; 

}