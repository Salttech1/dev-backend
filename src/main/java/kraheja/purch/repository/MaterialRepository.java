package kraheja.purch.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.purch.entity.Material;
import kraheja.purch.entity.MaterialCK;


@Repository
public interface MaterialRepository extends JpaRepository<Material, MaterialCK> {
	 
	Material findByMaterialCK_MatMatgroupAndMaterialCK_MatMatcodeAndMaterialCK_MatItemcode(String matgroup, String matcode, String itemcode) ; 
	
	Material findByMaterialCK_MatMatgroupAndMaterialCK_MatMatcodeAndMaterialCK_MatItemcodeAndMatLevel(String matgroup, String matcode, String itemcode, String matlevel) ; 

	Material findByMaterialCK_MatMatgroupAndMatLevel(String matGroup, String matLevel);
	
	Material findByMaterialCK_MatMatgroupAndMatLevelAndMatClosedate(String matGroup, String matLevel, LocalDate closedate);
	
	@Query("SELECT distinct e.matWorkcode FROM Material e WHERE trim(e.materialCK.matMatgroup) = :matGroup")
	String findDistinctByMaterialCK_MatMatgroup(String matGroup);
	
	@Query("Select count(e) from Material e where trim(e.materialCK.matMatgroup) = :matgrp and trim(e.matLevel) =:matLevel and e.matAcmajor is not null and trim(e.materialCK.matMatcode) = :matCode AND trim(e.materialCK.matItemcode) =:matItemcode and :suppbilldt < nvl(e.matBlockdate, :closeDate) AND (e.matClosedate IS NULL OR e.matClosedate = :closeDate)")
	Integer findByMaterialGrpCount(String matgrp, String matLevel, String matCode, String matItemcode, LocalDate suppbilldt, LocalDate closeDate);

	@Query("Select count(e) from Material e where trim(e.materialCK.matMatgroup) = :matgrp and trim(e.matLevel) =:matLevel and e.matAcmajor is NOT NULL and trim(e.materialCK.matMatcode) = :matCode AND trim(e.materialCK.matItemcode) =:matItemcode and e.materialCK.matMatgroup IN (select d.entityCk.entId from DbEntity d where d.entityCk.entClass = :entClass)")
	Integer materialLevel2Count(String matgrp, String matLevel, String matCode, String matItemcode, String entClass);
	
	List<Material> findByMatLevel(String matLevel);
	
}