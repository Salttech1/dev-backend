package kraheja.commons.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kraheja.commons.entity.Glchart;
import kraheja.commons.entity.GlchartCK;
import kraheja.sales.bean.entitiesresponse.GlchartDBResponse;
import kraheja.sales.bean.entitiesresponse.GlchartEntityResponse;

@Repository
public interface GlchartRepository extends JpaRepository<Glchart, GlchartCK> {
//	@Query("SELECT e FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum AND e.glChartCK.chartClosedate = '01/JAN/2050'")
//	Glchart findBychartAcnum(String acnum); 
	
	@Query("SELECT e FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum")
	Glchart findBychartAcMajor(String acnum); 
	
	@Query("SELECT NVL(e.chartPb1len, 0), NVL(e.chartPb1start, 0), NVL(e.chartPb2len, 0), NVL(e.chartPb2start, 0), NVL(e.chartMb1len, 0), NVL(e.chartMb1start, 0) FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum")
	String findBychartAcnum(String acnum); 
	
	@Query("SELECT NVL(e.chartCfpaygroup, ' '), NVL(e.chartPgroupc, ' ') FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum")
	String findPaygroupByCharAcnum(String acnum);
	
	@Query("SELECT NVL(e.chartCfrecgroup, ' '), NVL(e.chartRgroupc, ' ') FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum")
	String findRecgroupByCharAcnum(String acnum);
	
	@Query("SELECT new kraheja.sales.bean.entitiesresponse.GlchartEntityResponse(COALESCE(e.chartMinoryn, ' ') as chartMinoryn, "
			+ "COALESCE(e.chartValidminors, ' ') as chartValidminors,"
			+ "COALESCE(e.chartPostprojonly, ' ') as chartPostprojonly,"
			+ "COALESCE(e.chartPostglonly, ' ') as chartPostglonly,"
			+ "COALESCE(e.chartPb1start, 0) as pb1Start,"
			+ "COALESCE(e.chartPb1len, 0) as pb1Len,"
			+ "COALESCE(e.chartPb2start, 0) as pb2Start,"
			+ "COALESCE(e.chartPb2len, 0) as pb2Len,"
			+ "COALESCE(e.chartMb1start, 0) as mb1Start,"
			+ "COALESCE(e.chartMb1len, 0) as mb1Len) "
			+ "FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum AND (e.glChartCK.chartClosedate IS NULL OR e.glChartCK.chartClosedate = :closeDate)")
	GlchartEntityResponse findchartMinorAndchartPostByCharAcnum(String acnum, LocalDateTime closeDate);
	
	@Query("SELECT NVL(e.chartMinoryn, ' '), NVL(e.chartValidminors, ' '), NVL(e.chartValidparties, ' ') FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum ")
	String findchartMinorAndchartValidPartiesByCharAcnum(String acnum);
	
	@Query("select new kraheja.sales.bean.entitiesresponse.GlchartDBResponse(trim(g.chartCfrecgroup), trim(g.chartRgroupc)) from Glchart g where trim(g.glChartCK.chartAcnum)= :chartAcnum and g.glChartCK.chartClosedate is null")
	GlchartDBResponse fetchChartCfrecgroup(String chartAcnum);

	@Query("SELECT NVL(e.chartValidminors, ' '), NVL(e.chartValidparties, ' ') FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum ")
	List<String> findValidMinorAndValidPartiesByCharAcnum(String acnum);
	
	@Query("SELECT e.chartValidminors FROM Glchart e WHERE trim(e.glChartCK.chartAcnum) = :acnum ")
	String findValidMinorByChartAcnum(String acnum);
}