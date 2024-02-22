package kraheja.sales.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Flats;
import kraheja.sales.entity.FlatsCK;
@Repository
public interface FlatsRepository extends JpaRepository<Flats, FlatsCK> {

	List<Flats> findByFlatsCK_FlatBldgcode(String bldgcode) ; 
	
	@Query("select e  from Flats e WHERE e.flatsCK.flatBldgcode = :bldgcode AND e.flatsCK.flatWing = :wing AND e.flatsCK.flatFlatnum = :flatnum")
	Flats findByFlatsCK_FlatBldgcodeAndFlatsCK_FlatWingAndFlatsCK_FlatFlatnum(String bldgcode, String wing, String flatnum) ;
}