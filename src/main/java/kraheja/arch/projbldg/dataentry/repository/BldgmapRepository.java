package kraheja.arch.projbldg.dataentry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kraheja.arch.projbldg.dataentry.entity.Bldgmap ;
import kraheja.arch.projbldg.dataentry.entity.BldgmapCK;

@Repository
public interface BldgmapRepository extends JpaRepository<Bldgmap, BldgmapCK> {

	//@Query("select e from bldgmap e where trim(e.bldgmapCK.bmapEbldgcode) = :ebldgcode AND trim(e.bldgmapCK.bmapAbldgcode) = :abldgcode")
	Bldgmap findByBldgmapCK_BmapEbldgcodeAndBldgmapCK_BmapAbldgcode(String ebldgcode, String abldgcode) ; 
}
