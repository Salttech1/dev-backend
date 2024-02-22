package kraheja.arch.projbldg.dataentry.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;






/**
 * The persistent class for the PROJECT database table.
**/
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
		@Index(name = "PROJ1", columnList = "projProprietor Asc, projCompany Asc, projCode Asc", unique = true)
})

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProjectCK projectCK;
	
	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String projProprietor ; 

	@Column
	@Type(type = "kraheja.commons.utils.CharType")
	private String projCompany ; 

	@Column(name="PROJ_ACCLEARYN")
	@Type(type = "kraheja.commons.utils.CharType")    //Used to remove/trim white space .....Shahaji(10/01/2023)
	private String projAcclearyn ;

	@Column(name="PROJ_AMENITY1")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projAmenity1 ;

	@Column(name="PROJ_AMENITY2")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projAmenity2 ;

	@Column(name="PROJ_AMENITY3")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projAmenity3 ;

	@Column(name="PROJ_AMENITY4")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projAmenity4 ;

	@Column(name="PROJ_AMENITY5")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projAmenity5 ;

	@Column(name="PROJ_AREAARCH")
	private Double projAreaarch ;

	@Column(name="PROJ_AREAENGG")
	private Double projAreaengg ;

	@Column(name="PROJ_AREASALES")
	private Double projAreasales ;

	@Column(name="PROJ_BCOUNT1")
	private Double projBcount1 ;

	@Column(name="PROJ_BCOUNT2")
	private Double projBcount2 ;

	@Column(name="PROJ_BCOUNT3")
	private Double projBcount3 ;

	@Column(name="PROJ_BCOUNT4")
	private Double projBcount4 ;

	@Column(name="PROJ_BCOUNT5")
	private Double projBcount5 ;

	@Column(name="PROJ_BLDGCOUNT")
	private Double projBldgcount ;

	@Column(name="PROJ_BTYPE1")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projBtype1 ;

	@Column(name="PROJ_BTYPE2")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projBtype2 ;

	@Column(name="PROJ_BTYPE3")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projBtype3 ;

	@Column(name="PROJ_BTYPE4")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projBtype4 ;

	@Column(name="PROJ_BTYPE5")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projBtype5 ;

	@Column(name="PROJ_CITY")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projCity ;

	@Column(name="PROJ_DESC1")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDesc1 ;

	@Column(name="PROJ_DESC2")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDesc2 ;

	@Column(name="PROJ_DESC3")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDesc3 ;

	@Column(name="PROJ_DESC4")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDesc4 ;

	@Column(name="PROJ_DESC5")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDesc5 ;

	@Column(name="PROJ_DETAILS")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projDetails ;

	@Column(name="PROJ_ESTVAL")
	private Double projEstval ;

	@Column(name="PROJ_EXPTHISYR")
	private Double projExpthisyr ;

	@Column(name="PROJ_EXPTODATE")
	private Double projExptodate ;

	@Column(name="PROJ_GRPPROJECT")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projGrpproject ;

	@Column(name="PROJ_NAME")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projName ;

	@Column(name="PROJ_ORIGSITE")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projOrigsite ;

	@Column(name="PROJ_SITE")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projSite ;

	@Column(name="PROJ_SURVEYNUM")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projSurveynum ;

	@Column(name="PROJ_TENURE")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projTenure ;

	@Column(name="PROJ_TODAY")
	private LocalDateTime projToday ;

	@Column(name="PROJ_TOWNSHIP")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projTownship ;

	@Column(name="PROJ_USERID")
	@Type(type = "kraheja.commons.utils.CharType")
	private String projUserid ;

}