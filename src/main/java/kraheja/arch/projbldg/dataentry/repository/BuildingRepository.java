package kraheja.arch.projbldg.dataentry.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.arch.projbldg.dataentry.entity.Building ;
import kraheja.arch.projbldg.dataentry.entity.BuildingCK;
import kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse;
import kraheja.sales.bean.entitiesresponse.AuxiBuildingDBResponse;
@Repository
public interface BuildingRepository extends JpaRepository<Building, BuildingCK> {

	 
	Building findByBuildingCK_BldgCode(String code) ; 
	
	List<Building> findByBldgClosedateIsNullOrBldgClosedate(LocalDate closeDate) ; 
	
	@Query("select e.projCompany  from Project e WHERE trim(e.projectCK.projCode) = :code")
	String findProjectCompanyByCode(String code) ; //NS 16.03.2023 
	
	@Query("select new kraheja.sales.bean.entitiesresponse.AuxiBuildingDBResponse(b.bldgProp, b.bldgProperty, b.bldgProject, b.bldgCoy, b.bldgMaintcoy) from Building b where b.buildingCK.bldgCode= :bldgCode")
	AuxiBuildingDBResponse findBuildingByCode(String bldgCode);
	
	@Query("select b.bldgSalesstate from Building b where b.buildingCK.bldgCode= :bldgCode")
	String findBldgSalesstateByBuildingCK_BldgCode(String bldgCode);
	
	@Query("select b.bldgCity from Building b where trim(b.buildingCK.bldgCode)= :bldgCode")
	String findBldgCityByBuildingCK_BldgCode(String bldgCode);
	
	@Query("select b.bldgCoy from Building b where trim(b.buildingCK.bldgCode)= :bldgCode")
	String findBldgCompanyByBldgCode(String bldgCode);

	@Query("select new kraheja.enggsys.lcsystem.payload.db.SupplierDBResponse(b.bldgCoy, b.bldgProp,b.bldgProject,b.bldgMisproject,b.bldgProperty,b.bldgMisbldg) from Building b where b.buildingCK.bldgCode= :bldgCode")
	SupplierDBResponse findSupplierDetails(String bldgCode) ; 
}