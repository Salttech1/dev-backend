package kraheja.arch.projbldg.dataentry.bean.request;

import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.request.AddressRequestBean;
import kraheja.commons.bean.GSTValdiationBean;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class ProjectRequestBean {

	private String acclearyn ;
	private String amenity1 ;
	private String amenity2 ;
	private String amenity3 ;
	private String amenity4 ;
	private String amenity5 ;
	private Double areaarch ;
	private Double areaengg ;
	private Double areasales ;
	private Double bcount1 ;
	private Double bcount2 ;
	private Double bcount3 ;
	private Double bcount4 ;
	private Double bcount5 ;
	private Double bldgcount ;
	private String btype1 ;
	private String btype2 ;
	private String btype3 ;
	private String btype4 ;
	private String btype5 ;
	private String city ;
	private String code ;
	private String company ;
	private String desc1 ;
	private String desc2 ;
	private String desc3 ;
	private String desc4 ;
	private String desc5 ;
	private String details ;
	private Double estval ;
	private Double expthisyr ;
	private Double exptodate ;
	private String grpproject ;
	private String name ;
	private String origsite ;
	private String proprietor ;
	private String site ;
	private String surveynum ;
	private String tenure ;
	private LocalDateTime today ;
	private String township ;
	private String userid ;
	
	private AddressRequestBean addressRequestBean;
}