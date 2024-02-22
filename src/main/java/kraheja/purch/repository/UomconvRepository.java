package kraheja.purch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Uomconv;
import kraheja.purch.entity.UomconvCK;

@Repository
public interface UomconvRepository extends JpaRepository<Uomconv, UomconvCK> {

	Uomconv findByUomconvCK_UcnvFrcodeAndUomconvCK_UcnvTocode(String frcode, String tocode) ; 

}