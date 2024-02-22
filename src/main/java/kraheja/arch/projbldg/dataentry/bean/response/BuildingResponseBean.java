package kraheja.arch.projbldg.dataentry.bean.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import kraheja.commons.bean.response.AddressResponseBean;
import kraheja.commons.bean.response.MinorsResponseBean;
import kraheja.sales.bean.response.FlatsResponseBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class BuildingResponseBean {

	private String acclearyn ;
	private Double adminrate ;
	private String altbldg ;
	private String amencoy ;
	private Double areaarch ;
	private Double areaengg ;
	private Double areasales ;
	private String bhk ;
	private String bldgnum ;
	private String bldgtype ;
	private String ccdate ;
	private String city ;
	private String closedate ;
	private String code ;
	private String collrecdwobj ;
	private String complex ;
	private String concoy ;
	private String coy ;
	private String coy4 ;
	private String debsocyn ;
	private String ea ;
	private String elecsupp ;
	private String eng_group ;
	private Double flatssold ;
	private String garagecoy ;
	private String ho2sales ;
	private String icsaleyn ;
	private String infrayn ;
	private String intbrokcoy ;
	private Double intrate ;
	private Double lastyrcomp ;
	private Double lastyrinc ;
	private Double lastyrmat ;
	private Double lastyrwork ;
	private String leasingcoy ;
	private String mainbldg ;
	private String maintcoy ;
	private Double maintrate ;
	private String misbldg ;
	private String misproject ;
	private String modagdoc ;
	private String name ;
	private String occdate ;
	private String opendate ;
	private String opt2engg ;
	private String opt2sales ;
	private String origsite ;
	private String outformat ;
	private String parentbldg ;
	private String paycoy ;
	private String possdate ;
	private String progstat ;
	private String project ;
	private String prop ;
	private String propbucaarea ;
	private String property ;
	private String region ;
	private String remark ;
	private String salesadline1 ;
	private String salesadline2 ;
	private String salesadline3 ;
	private String salesadline4 ;
	private String salesadline5 ;
	private String salescity ;
	private String salescountry ;
	private String salesctsnum ;
	private String salesimages ;
	private String salesname ;
	private String salesname1 ;
	private String salesname2 ;
	private String salespincode ;
	private String salesstate ;
	private String salestownship ;
	private String saleyn ;
	private String schposs ;
	private String shortname ;
	private String site ;
	private String staxcost ;
	private String surveynum ;
	private String tenure ;
	private Double thisyrcomp ;
	private Double thisyrinc ;
	private Double thisyrmat ;
	private Double thisyrwork ;
	private Double todatecomp ;
	private Double todateinc ;
	private Double todatemat ;
	private Double todatework ;
	private LocalDateTime today ;
	private Double totalflats ;
	private String township ;
	private String ulcdate ;
	private String userid ;
	private String validyn ;
	private String xfered ;
	MinorsResponseBean minorsResponseBean;
	AddressResponseBean addressResponseBean;
	BldgmapResponseBean bldgmapResponseBean;
	MailinfoResponseBean mailinfoResponseBean;
	List<FlatsResponseBean> flatsResponseBeanList;

}