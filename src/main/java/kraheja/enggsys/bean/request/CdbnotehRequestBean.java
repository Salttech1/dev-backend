package kraheja.enggsys.bean.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor

public class CdbnotehRequestBean {

	private Double amount ;
	private String billtype ;
	private String bldgcode ;
	private String certdate ;
	private String certno ;
	private String contbilldt ;
	private String contbillno ;
	private String contract ;
	private String coy ;
	private String date ;
	private String dbnoteser ;
	private String description1 ;
	private String description2 ;
	private String description3 ;
	private String description4 ;
	private String description5 ;
	private String misbldg ;
	private String misproject ;
	private String narration ;
	private Integer noofprint ;
	private String omsservyn ;
	private String origsite ;
	private String partycode ;
	private String partytype ;
	private String passedby ;
	private String prepby ;
	private String printdate ;
	private Integer prints ;
	private String printuser ;
	private String project ;
	private String prop ;
	private String recdby ;
	private String site ;
	private Integer tdsamount ;
	private Integer tdsperc ;
	private LocalDateTime today ;
	private String userid ;
	private String workcode ;
	private List<CdbnotedRequestBean> cdbnotedRequestBean;
}