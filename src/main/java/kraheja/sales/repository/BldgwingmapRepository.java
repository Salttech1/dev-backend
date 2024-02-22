package kraheja.sales.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Bldgwingmap;
import kraheja.sales.entity.BldgwingmapCK;

@Repository
public interface BldgwingmapRepository extends JpaRepository<Bldgwingmap, BldgwingmapCK>, CrudRepository<Bldgwingmap, BldgwingmapCK>{

	Bldgwingmap findByBldgwingmapCK_BwmapBldgcodeAndBldgwingmapCK_BwmapBldgwing(String bldgcode, String bldgwing) ; 
	
}