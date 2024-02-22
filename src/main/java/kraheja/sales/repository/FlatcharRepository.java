package kraheja.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import kraheja.sales.entity.Flatchar;
import kraheja.sales.entity.FlatcharCK;

@Repository
public interface FlatcharRepository extends JpaRepository<Flatchar, FlatcharCK>, CrudRepository<Flatchar, FlatcharCK> {
	Flatchar findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchAccomtypeAndFlatcharCK_FchChargecodeAndFlatcharCK_FchWing(
			String bldgcode, String flatnum, String accomtype, String chargecode, String wing);

	@Query(value = "select * from flatchar WHERE trim(fch_bldgcode) = :bldgcode and trim(fch_flatnum)=:flatnum and trim(fch_wing)=:wing and fch_accomtype=:accomtype and fch_chargecode=:chargecode", nativeQuery = true)
	Flatchar findCountBybldgwingflatchargecode(String bldgcode, String flatnum, String accomtype, String chargecode,
			String wing);

	List<Flatchar> findByFlatcharCK_FchBldgcodeAndFlatcharCK_FchFlatnumAndFlatcharCK_FchWing(String bldgcode,
			String flatnum, String wing);

}