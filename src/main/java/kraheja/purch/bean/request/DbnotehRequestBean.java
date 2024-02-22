package kraheja.purch.bean.request;

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

public class DbnotehRequestBean {

	private Double amount ;
	private String authdate ;
	private String authno ;
	private String billtype ;
	private String bldgcode ;
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
	private Double noofprint ;
	private String omspurcyn ;
	private String origsite ;
	private String partycode ;
	private String partytype ;
	private String passedby ;
	private String prepby ;
	private String printdate ;
	private Double prints ;
	private String printuser ;
	private String project ;
	private String prop ;
	private String recdby ;
	private String site ;
	private String suppbilldt ;
	private String suppbillno ;
	private Double tdsamount ;
	private Double origDbAmnt;
	private Double origTranAmnt;
	private Double tdsperc ;
	private LocalDateTime today ;
	private String transer ;
	private String userid ;
	
	List<DbnotedRequestBean> dbnotedRequests;
	List<DbnotevatRequestBean> dbnotevatRequests;
}